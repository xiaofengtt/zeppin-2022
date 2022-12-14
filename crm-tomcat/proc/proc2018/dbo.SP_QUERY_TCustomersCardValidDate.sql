USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCustomersCardValidDate
                                        @IN_CARD_VALID_DATE     INT,                   --证件有效期到期日，格式yymmdd
                                        @IN_INPUT_MAN           INT                    --操作员
                                        
WITH ENCRYPTION
AS
    DECLARE @RET INT, @IN_NODE_SHARE INT,@V_QUERYCUSTOMERS INT, @V_CUSTQZ0001 INT, @V_CCUSTMODI INT,@V_FACT_POTE_FLAG INT,@V_IS_DEAL INT,@V_ENCRYPTION_SIZE INT
    DECLARE @V_FLAG_ACCESS_ALL INT,@V_FLAG_ENCRYPTION INT,@V_USER_ID INT--, @V_SQL NVARCHAR(2000), @IN_CARD_ID18 NVARCHAR(18),@V_CONDITION_SQL NVARCHAR(600)
    DECLARE @V_FACT_POTENTIAL_FLAG_FZ INT
	SELECT @V_FACT_POTENTIAL_FLAG_FZ = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = 'FACT_POTENTIAL_FLAG_FZ'--方正客户信息加密条件
	SELECT @V_QUERYCUSTOMERS = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'QUERYCUSTOMERS'
	SELECT @V_USER_ID = USER_ID FROM TSYSTEMINFO
    IF @V_QUERYCUSTOMERS IS NULL SET @V_QUERYCUSTOMERS = 1
    SELECT @V_CUSTQZ0001 = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'CUSTQZ0001' --默认1
    SELECT @V_CUSTQZ0001 = ISNULL(@V_CUSTQZ0001,1)
    SELECT @V_CCUSTMODI = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'CCUSTMODI'
    SELECT @V_FACT_POTE_FLAG = VALUE FROM TSYSCONTROL WHERE  FLAG_TYPE = 'FACT_POTENTIAL_FLAG'--事实潜在客户加密条件
    SELECT @V_CCUSTMODI = ISNULL(@V_CCUSTMODI,2)

    IF @V_FACT_POTE_FLAG = 1  SET @V_IS_DEAL = 2 -- 潜在客户信息加密
    IF @V_FACT_POTE_FLAG = 2  SET @V_IS_DEAL = 1 -- 事实客户信息加密
    IF @V_FACT_POTE_FLAG = 3  SET @V_IS_DEAL = 0 -- 事实、潜在客户信息加密
    IF @V_FACT_POTE_FLAG = 4  SET @V_IS_DEAL = 3 -- 事实、潜在客户信息都不加密
	
	--加密显示位数	
	IF EXISTS(SELECT 1 FROM TDICTPARAM WHERE TYPE_VALUE = '800701')
		SELECT @V_ENCRYPTION_SIZE = CAST(TYPE_CONTENT AS INT) FROM TDICTPARAM WHERE TYPE_VALUE = '800701'
	ELSE
		SET  @V_ENCRYPTION_SIZE = 4
	DECLARE @V_TEMPCUST1 TABLE(CUST_ID INT) --根据输入条件能够访问的客户，根据查询条件 AND
    DECLARE @V_TEMPCUST2 TABLE(CUST_ID INT,AUTH_FLAG INT) --根据访问权限能够访问的客户，根据访问权限 OR
    
    SELECT @V_FLAG_ACCESS_ALL = 0 --访问全部标志
    SELECT @V_FLAG_ENCRYPTION = 0 --加密标志
    --客户经理级别树中，同节点客户经理是否共享客户
    IF EXISTS(SELECT 1 FROM TSYSCONTROL WHERE FLAG_TYPE = 'MANAGER002' AND VALUE = 1)
        SET @IN_NODE_SHARE = 0  --共享
    ELSE
        SET @IN_NODE_SHARE = 1  --不共享
    --能够访问所有客户信息
    IF EXISTS(SELECT 1 FROM INTRUST..TOPRIGHT WHERE OP_CODE = @IN_INPUT_MAN AND MENU_ID = N'999' AND FUNC_ID = 99903)
        SELECT @V_FLAG_ACCESS_ALL = 1
    --如果操作员的角色中存在访问所有客户信息权限的标志 则赋予能够访问所有客户信息权限
    IF EXISTS(SELECT 1 FROM TOPROLE WHERE OP_CODE = @IN_INPUT_MAN AND ROLE_ID IN(SELECT ROLE_ID FROM TROLE WHERE FLAG_ACCESS_ALL = 1))
        SELECT @V_FLAG_ACCESS_ALL = 1

    --如果操作员的角色中存在不保密限制的角色，则不进行保密限制，否则加密
    IF EXISTS(SELECT 1 FROM TOPROLE WHERE OP_CODE = @IN_INPUT_MAN AND ROLE_ID IN(SELECT ROLE_ID FROM TROLE WHERE FLAG_ENCRYPTION = 0))
        SELECT @V_FLAG_ENCRYPTION = 2
    ELSE
		SELECT @V_FLAG_ENCRYPTION = 1
    
    CREATE TABLE #TEMPCUST
    (
       CUST_ID                 INT,                 --客户ID
       CUST_NO                 NVARCHAR (8),        --客户编号
       CUST_NAME               NVARCHAR (100),      --客户名称
       POST_ADDRESS            NVARCHAR(60),        --联系地址
       POST_CODE               NVARCHAR(6),         --邮政编码
       CARD_ID                 NVARCHAR (40),       --证件ID
       CARD_VALID_DATE         INT,                 --证件有效期
       CUST_TEL                NVARCHAR (40),       --联系电话
       E_MAIL                  NVARCHAR (60),       --E-MAIL
       O_TEL                   NVARCHAR (40),       --公司电话
       MOBILE                  NVARCHAR (100) ,     --手机
       SERVICE_MAN             INT,                 --客户经理
       CUST_TYPE               INT,                 --客户类型
       CUST_TYPE_NAME          NVARCHAR(10),        --客户类型说明
       CARD_TYPE_NAME          NVARCHAR(30),        --证件类型
       CUST_SOURCE             NVARCHAR(20),
       CUST_SOURCE_NAME        NVARCHAR(60),
       COUNTRY                 NVARCHAR(20),
       COUNTRY_NAME            NVARCHAR(60),
       MONEY_SOURCE            NVARCHAR(300),
       MONEY_SOURCE_NAME       NVARCHAR(1000)
    )
    --------------------------------------------------------------------
    DECLARE @V_MANAGER_IDS TABLE(MANAGERID INT, MANAGERNAME NVARCHAR(60))
    --处理客户经理同级共享(共享给当前操作员的源客户经理)，共享时，同节点及下级节点的客户经理所辖客户，由于也具有访问权限，故一起共享了。故对经理树的处理放在下面
    INSERT INTO @V_MANAGER_IDS(MANAGERID)
        SELECT SourceManagerID FROM TAuthorizationShare WHERE ShareType = 1 AND Status = 1 AND ManagerID = @IN_INPUT_MAN
    --从客户经理树取当前操作员所辖客户经理,再取这些客户经理的客户
    INSERT INTO @V_MANAGER_IDS
        --1.根据节点主经理来判断当前操作员
        --所辖节点的主客户经理
        SELECT A.MANAGERID,A.MANAGERNAME FROM TCustManagerTree A, TCustManagerTree B
        WHERE (A.LEFT_ID BETWEEN B.LEFT_ID+@IN_NODE_SHARE AND B.RIGHT_ID) AND A.MANAGERID <> 0 AND A.MANAGERID IS NOT NULL
            AND (B.MANAGERID = @IN_INPUT_MAN OR EXISTS(SELECT 1 FROM @V_MANAGER_IDS Z WHERE B.MANAGERID = Z.MANAGERID) )
        --所辖节点的成员客户经理
        UNION ALL
        SELECT C.MANAGERID,C.MANAGERNAME FROM TCustManagerTree A, TCustManagerTree B, TCustManagerTreeMembers C
        WHERE (A.LEFT_ID BETWEEN B.LEFT_ID+@IN_NODE_SHARE AND B.RIGHT_ID) AND A.SERIAL_NO = C.TREE_ID
            AND (B.MANAGERID = @IN_INPUT_MAN OR EXISTS(SELECT 1 FROM @V_MANAGER_IDS Z WHERE B.MANAGERID = Z.MANAGERID) )
        --2.根据节点成员经理来判断当前操作员
        --所辖节点的主客户经理
        UNION ALL
        SELECT D.MANAGERID,D.MANAGERNAME FROM TCustManagerTreeMembers B, TCustManagerTree C, TCustManagerTree D
        WHERE B.TREE_ID = C.SERIAL_NO AND (D.LEFT_ID BETWEEN C.LEFT_ID+@IN_NODE_SHARE AND C.RIGHT_ID)
            AND (B.MANAGERID = @IN_INPUT_MAN OR EXISTS(SELECT 1 FROM @V_MANAGER_IDS Z WHERE B.MANAGERID = Z.MANAGERID) )
        --所辖节点的成员客户经理
        UNION ALL
        SELECT A.MANAGERID,A.MANAGERNAME FROM TCustManagerTreeMembers B, TCustManagerTree C, TCustManagerTree D, TCustManagerTreeMembers A
        WHERE B.TREE_ID = C.SERIAL_NO AND (D.LEFT_ID BETWEEN C.LEFT_ID+@IN_NODE_SHARE AND C.RIGHT_ID) AND A.TREE_ID = D.SERIAL_NO
            AND (B.MANAGERID = @IN_INPUT_MAN OR EXISTS(SELECT 1 FROM @V_MANAGER_IDS Z WHERE B.MANAGERID = Z.MANAGERID) )
    --处理授权给下级的(当前操作员的)客户授权集，注：被授权的客户没有级联共享给同级别经理
    INSERT INTO @V_TEMPCUST2(CUST_ID,AUTH_FLAG)
        SELECT DISTINCT A.CUST_ID,A.AUTH_FLAG FROM TAuthorizationCusts A, TAuthorizationShare B
        WHERE A.CA_ID = B.CA_ID AND B.ShareType = 2 AND B.Status = 1 AND B.ManagerID = @IN_INPUT_MAN
            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)
    --处理快捷授权的客户ShareType=3，仅查询
    INSERT INTO @V_TEMPCUST2(CUST_ID,AUTH_FLAG)
        SELECT CUST_ID,0 FROM TAuthorizationShare A WHERE ShareType = 3 AND Status = 1 AND CUST_ID <> 0 AND (GETDATE() BETWEEN ISNULL(A.START_TIME,GETDATE()) AND ISNULL(A.INVALID_TIME,GETDATE()+36000)) AND ManagerID = @IN_INPUT_MAN
            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)
        UNION ALL
        SELECT B.CUST_ID,0 FROM TAuthorizationShare A, TCustomers B
        WHERE A.SourceManagerID = B.SERVICE_MAN AND A.ShareType = 3 AND A.Status = 1 AND A.CUST_ID = 0 AND (GETDATE() BETWEEN ISNULL(A.START_TIME,GETDATE()) AND ISNULL(A.INVALID_TIME,GETDATE()+36000)) AND A.ManagerID = @IN_INPUT_MAN
            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)
    --以上客户经理的客户
    INSERT INTO @V_TEMPCUST2(CUST_ID)
        SELECT DISTINCT CUST_ID FROM TCustomers A
        WHERE (EXISTS( SELECT 1 FROM @V_MANAGER_IDS B WHERE A.SERVICE_MAN = B.MANAGERID)
                OR EXISTS( SELECT 1 FROM @V_MANAGER_IDS C WHERE (@V_QUERYCUSTOMERS = 1 AND A.INPUT_MAN = C.MANAGERID) ) )
            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)
    --团队领导的团队成员
    INSERT INTO @V_TEMPCUST2(CUST_ID)
		SELECT CUST_ID FROM TCustomers T WHERE EXISTS (
			SELECT MANAGERID FROM TManagerTeamMembers A WHERE EXISTS (SELECT TEAM_ID FROM TManagerTeams B WHERE A.TEAM_ID=B.TEAM_ID AND LEADER=@IN_INPUT_MAN)
				AND T.SERVICE_MAN=A.MANAGERID)
			AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE T.CUST_ID = C.CUST_ID)
    --------------------------------------------------------------------

    --20111025 DONGYG 如果不控制潜在客户的查询，将所有潜在客户添加到 @V_TEMPCUST2 中
    IF @V_CUSTQZ0001 <> 1
        INSERT INTO @V_TEMPCUST2(CUST_ID) SELECT CUST_ID FROM TCustomers WHERE IS_DEAL = 2

    --------------------------------------------------------------------
           INSERT INTO #TEMPCUST(CUST_ID,CUST_NO,CUST_NAME,POST_ADDRESS,POST_CODE,CARD_ID,CARD_VALID_DATE,
					CUST_TEL,E_MAIL,O_TEL,MOBILE,
                    SERVICE_MAN,CUST_TYPE,CUST_TYPE_NAME,CARD_TYPE_NAME,CUST_SOURCE,CUST_SOURCE_NAME,
                    COUNTRY,COUNTRY_NAME,MONEY_SOURCE,MONEY_SOURCE_NAME)
                SELECT A.CUST_ID,CUST_NO,CUST_NAME,POST_ADDRESS,POST_CODE,CARD_ID,CARD_VALID_DATE,
						CUST_TEL,E_MAIL,O_TEL,MOBILE,
						SERVICE_MAN,CUST_TYPE,CUST_TYPE_NAME,CARD_TYPE_NAME,A.CUST_SOURCE,A.CUST_SOURCE_NAME,
						A.COUNTRY,(SELECT TOP 1 TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_ID=9997 AND TYPE_VALUE=A.COUNTRY),A.MONEY_SOURCE,A.MONEY_SOURCE_NAME
					FROM TCustomers A
                    WHERE 
                        --操作员=客户资料录入人 或 有访问客户资料权限
                        ( ( @V_QUERYCUSTOMERS = 1 AND A.INPUT_MAN = @IN_INPUT_MAN) OR SERVICE_MAN = @IN_INPUT_MAN OR @V_FLAG_ACCESS_ALL = 1
                             OR EXISTS(SELECT 1 FROM @V_TEMPCUST2 B WHERE A.CUST_ID = B.CUST_ID) )
                        AND STATUS <> '112805'
                        AND (CARD_VALID_DATE<=@IN_CARD_VALID_DATE)
   
    -------------------------------------------------------------------------------------
    --开关控制 加密重要联系信息
	IF(@V_FLAG_ENCRYPTION = 1)
    BEGIN
		UPDATE #TEMPCUST
        SET CARD_ID = ISNULL(stuff(A.CARD_ID,len(A.CARD_ID)-@V_ENCRYPTION_SIZE+1,len(A.CARD_ID),'******'),'****'),
            CUST_TEL = ISNULL(stuff(A.CUST_TEL,len(A.CUST_TEL)-@V_ENCRYPTION_SIZE+1,len(A.CUST_TEL),'******'),'****'),
            --H_TEL = ISNULL(stuff(H_TEL,1,len(H_TEL)-@V_ENCRYPTION_SIZE,'******'),'****'),
            O_TEL = ISNULL(stuff(A.O_TEL,len(A.O_TEL)-@V_ENCRYPTION_SIZE+1,len(A.O_TEL),'******'),'****'),
            E_MAIL = ISNULL(stuff(A.E_MAIL,1,charindex('@',A.E_MAIL)-1,'******'),''),
            MOBILE = ISNULL(stuff(A.MOBILE,len(A.MOBILE)-@V_ENCRYPTION_SIZE+1,len(A.MOBILE),'******'),'****'),
            POST_ADDRESS = ISNULL(stuff(A.POST_ADDRESS,len(A.POST_ADDRESS)-@V_ENCRYPTION_SIZE+1,len(A.POST_ADDRESS),'******'),'****')
        FROM #TEMPCUST A
        WHERE ( (A.SERVICE_MAN <> @IN_INPUT_MAN ) )
            AND (NOT EXISTS(SELECT CUST_ID FROM @V_TEMPCUST2 B WHERE A.CUST_ID = B.CUST_ID))--不存在客户授权的
	END
    SELECT * FROM #TEMPCUST
    
GO
