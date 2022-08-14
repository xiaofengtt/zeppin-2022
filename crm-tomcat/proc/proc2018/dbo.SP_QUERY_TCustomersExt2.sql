USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCustomersExt2 @IN_CUST_ID             INT,
                                        @IN_CUST_NO             NVARCHAR(8),
                                        @IN_CUST_NAME           NVARCHAR(100),
                                        @IN_CUST_SOURCE         NVARCHAR(10),
                                        @IN_CARD_TYPE           NVARCHAR(10),
                                        @IN_CARD_ID             NVARCHAR(20),
                                        @IN_TOUCH_TYPE          NVARCHAR(10),
                                        @IN_MIN_TIMES           INT,
                                        @IN_MAX_TIMES           INT,
                                        @IN_INPUT_MAN           INT,
                                        @IN_TEL                 NVARCHAR(20)   = NULL,
                                        @IN_ADDRESS             NVARCHAR(60)   = NULL,
                                        @IN_CUST_TYPE           INT            = NULL,
                                        @IN_PRODUCT_ID          INT            = NULL,
                                        @IN_FAMILY_NAME         NVARCHAR(40)   = NULL,
                                        @IN_ONLYEMAIL           INT = NULL,             --是否只返回有EMAIL的记录 1-是 0-否
                                        @IN_CUST_LEVEL          NVARCHAR(10)   = NULL,
                                        @IN_MIN_TOTAL_MONEY     MONEY          = NULL,  --认购金额下限
                                        @IN_MAX_TOTAL_MONEY     MONEY          = NULL,  --认购金额上限
                                        @IN_ONLY_THISPRODUCT    INT            = NULL,
                                        @IN_ORDERBY             NVARCHAR(100)  = NULL,
                                        @IN_BIRTHDAY            INT            = NULL,  --生日下限（输入格式yyyymmdd，过程内部处理掉yyyy，仅mmdd起作用）
                                        @IN_PRINT_DEPLOY_BILL   INT            = NULL,
                                        @IN_IS_LINK             INT            = NULL,
                                        @IN_PROV_LEVEL          NVARCHAR(10)   = NULL,
                                        @IN_BEN_AMOUNT_MIN      DECIMAL(16,3)  = NULL,
                                        @IN_BEN_AMOUNT_MAX      DECIMAL(16,3)  = NULL,
                                        @IN_VIP_CARD_ID         NVARCHAR(20)   = NULL,  --VIP卡编号
                                        @IN_HGTZR_BH            NVARCHAR(20)   = NULL,  --合格投资人编号
                                        @IN_WTR_FLAG            INTEGER        = 0,     --委托人标志：0 -- 委托人和受益人 1 受益人 2 委托人    针对只查询单一产品情况
                                        @IN_CLASSES             NVARCHAR(500)  = NULL,  --各客户级别的值拼成的字符串，半角逗号分隔。例如：1001,1102,2001
                                        @IN_IS_DEAL             INT            = 0,
                                        @IN_GroupID             INT            = 0,     --客户群组
                                        @IN_MONEY_SOURCE_NAME   NVARCHAR(1000) = NULL,  --资金来源名称
                                        @IN_COUNTRY             NVARCHAR(10)   = NULL,  --客户国籍(9997)
                                        @IN_ACCOUNTMANAGER      INTEGER        = 0,     --客户经理
                                        @IN_RECOMMENDED         NVARCHAR(60)   = NULL,  --推荐人
                                        @IN_PRODUCT_TO          NVARCHAR(30)   = NULL,  --投向
                                        @IN_START_AGE           INT            = NULL,  --年龄下限
                                        @IN_END_AGE             INT            = NULL,  --年龄上限
                                        @IN_CELL_ID             INT            = 0,     --产品单元ID
                                        @IN_BIRTHDAY_END        INT            = NULL,  --生日上限（输入格式yyyymmdd，过程内部处理掉yyyy，仅mmdd起作用）
                                        @IN_RG_BEGIN_DATE       INT            = NULL,  --认购日期下限
                                        @IN_RG_END_DATE         INT            = NULL   --认购日期上限
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    IF @IN_BIRTHDAY_END = 0 SET @IN_BIRTHDAY_END = NULL
    IF @IN_RG_END_DATE = 0 SET @IN_RG_END_DATE = NULL
    IF @IN_END_AGE = 0 SET @IN_END_AGE = NULL
    DECLARE @RET INT, @V_DT_INTRUST INT, @IN_NODE_SHARE INT,@V_QUERYCUSTOMERS INT, @V_CUSTQZ0001 INT
    DECLARE @V_FLAG_ACCESS_ALL INT,@V_FLAG_ENCRYPTION INT, @V_SQL NVARCHAR(2000), @IN_CARD_ID18 NVARCHAR(18),@V_CONDITION_SQL NVARCHAR(600)
    SELECT @V_DT_INTRUST = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'DT_INTRUST'
    SELECT @V_QUERYCUSTOMERS = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'QUERYCUSTOMERS'
    SELECT @V_CUSTQZ0001 = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'CUSTQZ0001' --默认1
    SELECT @V_CUSTQZ0001 = ISNULL(@V_CUSTQZ0001,1)
    IF EXISTS(SELECT * FROM TSYSCONTROL WHERE FLAG_TYPE = N'A_CARDID' AND VALUE = 1)
    BEGIN
        IF LEN(@IN_CARD_ID)=15   --15位身份证号码转18位
            SELECT @IN_CARD_ID18 = dbo.IDCARD15TO18(@IN_CARD_ID)
        ELSE IF LEN(@IN_CARD_ID)=18 --18位转成15位
            SELECT @IN_CARD_ID18 = dbo.IDCARD18TO15(@IN_CARD_ID)
    END
    DECLARE @V_TEMPCUST1 TABLE(CUST_ID INT) --根据输入条件能够访问的客户，根据查询条件 AND
    DECLARE @V_TEMPCUST2 TABLE(CUST_ID INT) --根据访问权限能够访问的客户，根据访问权限 OR
    DECLARE @V_PRODUCT_IDS TABLE(PRODUCT_ID INT)
    DECLARE @V_CELLALL TABLE(CELL_ID      INT,
                             CELL_CODE    NVARCHAR(10),
                             CELL_NAME    NVARCHAR(60),
                             CELL_TYPE    INT,
                             PRODUCT_ID   INT,
                             PRODUCT_CODE NVARCHAR(6),
                             PRODUCT_NAME NVARCHAR(60))
    IF @IN_CELL_ID IS NOT NULL AND @IN_CELL_ID <> 0
    BEGIN
        IF @V_DT_INTRUST = 1 --启用分布式
            INSERT INTO @V_CELLALL(CELL_ID,CELL_CODE,CELL_NAME,PRODUCT_ID,PRODUCT_CODE,PRODUCT_NAME)
                SELECT SERIAL_NO,CELL_CODE,CELL_NAME,PRODUCT_ID,PRODUCT_CODE,PRODUCT_NAME FROM SRV_Intrust.INTRUST.dbo.TPRODUCT_CELL
                WHERE SERIAL_NO = @IN_CELL_ID
        ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
            INSERT INTO @V_CELLALL(CELL_ID,CELL_CODE,CELL_NAME,PRODUCT_ID,PRODUCT_CODE,PRODUCT_NAME)
                SELECT SERIAL_NO,CELL_CODE,CELL_NAME,PRODUCT_ID,PRODUCT_CODE,PRODUCT_NAME FROM INTRUST..TPRODUCT_CELL
                WHERE SERIAL_NO = @IN_CELL_ID
        WHILE @@ROWCOUNT > 0
        BEGIN
            IF @V_DT_INTRUST = 1 --启用分布式
                INSERT INTO @V_CELLALL(CELL_ID)
                    SELECT A.SUB_CELL_ID
                    FROM  SRV_Intrust.INTRUST.dbo.TPRODUCT_CELL_DETAIL A, @V_CELLALL B
                    WHERE A.DF_SERIAL_NO = B.CELL_ID AND A.SUB_CELL_ID NOT IN(SELECT CELL_ID FROM @V_CELLALL)
            ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
                INSERT INTO @V_CELLALL(CELL_ID)
                    SELECT A.SUB_CELL_ID
                    FROM  INTRUST..TPRODUCT_CELL_DETAIL A, @V_CELLALL B
                    WHERE A.DF_SERIAL_NO = B.CELL_ID AND A.SUB_CELL_ID NOT IN(SELECT CELL_ID FROM @V_CELLALL)
        END
        IF @V_DT_INTRUST = 1 --启用分布式
            UPDATE @V_CELLALL
                SET CELL_CODE = B.CELL_CODE,
                    CELL_NAME = B.CELL_NAME,
                    CELL_TYPE = B.CELL_TYPE,
                    PRODUCT_ID = B.PRODUCT_ID,
                    PRODUCT_CODE = B.PRODUCT_CODE,
                    PRODUCT_NAME = B.PRODUCT_NAME
                FROM @V_CELLALL A , SRV_Intrust.INTRUST.dbo.TPRODUCT_CELL B
                WHERE A.CELL_ID = B.SERIAL_NO
        ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
            UPDATE @V_CELLALL
                SET CELL_CODE = B.CELL_CODE,
                    CELL_NAME = B.CELL_NAME,
                    CELL_TYPE = B.CELL_TYPE,
                    PRODUCT_ID = B.PRODUCT_ID,
                    PRODUCT_CODE = B.PRODUCT_CODE,
                    PRODUCT_NAME = B.PRODUCT_NAME
                FROM @V_CELLALL A , INTRUST..TPRODUCT_CELL B
                WHERE A.CELL_ID = B.SERIAL_NO
        INSERT INTO @V_PRODUCT_IDS(PRODUCT_ID) SELECT PRODUCT_ID FROM @V_CELLALL WHERE CELL_TYPE = 1 GROUP BY PRODUCT_ID
    END

    SELECT @IN_CARD_ID18 = ISNULL(@IN_CARD_ID18, @IN_CARD_ID)
    SELECT @V_FLAG_ACCESS_ALL = 0 --访问全部标志
    SELECT @V_FLAG_ENCRYPTION = 0 --加密标志
    --客户经理级别树中，同节点客户经理是否共享客户
    IF EXISTS(SELECT 1 FROM TSYSCONTROL WHERE FLAG_TYPE = 'MANAGER002' AND VALUE = 1)
        SET @IN_NODE_SHARE = 0  --共享
    ELSE
        SET @IN_NODE_SHARE = 1  --不共享
    --能够访问所有客户信息
    IF EXISTS(SELECT 1 FROM TOPRIGHT WHERE OP_CODE = @IN_INPUT_MAN AND MENU_ID = N'999' AND FUNC_ID = 99903)
        SELECT @V_FLAG_ACCESS_ALL = 1
    --如果操作员的角色中存在访问所有客户信息权限的标志 则赋予能够访问所有客户信息权限
    IF EXISTS(SELECT 1 FROM TOPROLE WHERE OP_CODE = @IN_INPUT_MAN AND ROLE_ID IN(SELECT ROLE_ID FROM TROLE WHERE FLAG_ACCESS_ALL = 1))
        SELECT @V_FLAG_ACCESS_ALL = 1
    --如果操作员的角色中存在保密限制的角色，则根据保密优先的原则，则进行保密限制
    IF EXISTS(SELECT 1 FROM TOPROLE WHERE OP_CODE = @IN_INPUT_MAN AND ROLE_ID IN(SELECT ROLE_ID FROM TROLE WHERE FLAG_ENCRYPTION = 1))
        SELECT @V_FLAG_ENCRYPTION = 1

    CREATE TABLE #TEMPCUST
    (
       CUST_ID                 INT,                 --客户ID
       CUST_NO                 NVARCHAR (8),        --客户编号
       CUST_NAME               NVARCHAR (100),      --客户名称
       SEX                     NVARCHAR(10),        --性别
       BIRTHDAY                INT,                 --生日
       POST_ADDRESS            NVARCHAR(60),        --联系地址
       POST_CODE               NVARCHAR(6),         --邮政编码
       CARD_ID                 NVARCHAR (40),       --证件ID
       CUST_TEL                NVARCHAR (40),       --联系电话
       E_MAIL                  NVARCHAR (30),       --E-MAIL
       O_TEL                   NVARCHAR (40),       --公司电话
       MOBILE                  NVARCHAR (100) ,     --手机
       BP                      NVARCHAR (60) ,      --BP
       STATUS_NAME             NVARCHAR (30),       --客户状态说明
       LAST_RG_DATE            INT,                 --最近一次购买时间
       CURR_TO_AMOUNT          DECIMAL(16,3),       --
       TOTAL_MONEY             DECIMAL(16, 3),     --已购买金额
       CURRENT_MONEY           DECIMAL(16, 3),     --存量金额
       BEN_AMOUNT              DECIMAL(16, 3),     --受益份额
       EXCHANGE_AMOUNT         DECIMAL(16, 3),     --转让份额
       BACK_AMOUNT             DECIMAL(16, 3),     --到期结算份额
       SERVICE_MAN             INT,                 --客户经理
       CUST_TYPE               INT,
       MODI_FLAG               INT DEFAULT 0,
       GRADE_LEVEL             NVARCHAR(10),        --客户风险等级
       GRADE_LEVEL_NAME        NVARCHAR(30),        --客户风险等级说明
       CARD_TYPE_NAME          NVARCHAR(30),        --证件类型
       IS_DEAL                 INT DEFAULT 2,
       IS_DEAL_NAME            NVARCHAR(30),
       CUST_SOURCE             NVARCHAR(20),
       CUST_SOURCE_NAME        NVARCHAR(60),
       COUNTRY                 NVARCHAR(20),
       COUNTRY_NAME            NVARCHAR(60),
       MONEY_SOURCE            NVARCHAR(300),
       MONEY_SOURCE_NAME       NVARCHAR(1000),
       FIRST_RG_DATE           INT,
       END_AMOUNT              DECIMAL(16,3),------当前份额（受益份额-到期结算份额）
       PRINT_DEPLOY_BILL       INT DEFAULT 0,
       PRINT_POST_INFO         INT DEFAULT 0,
       CUST_LEVEL              NVARCHAR(10),
	   SERVICE_MAN_NAME		   NVARCHAR (100),
	   
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
    INSERT INTO @V_TEMPCUST2
        SELECT DISTINCT A.CUST_ID FROM TAuthorizationCusts A, TAuthorizationShare B
        WHERE A.CA_ID = B.CA_ID AND B.ShareType = 2 AND B.Status = 1 AND B.ManagerID = @IN_INPUT_MAN
            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)
    --以上客户经理的客户
    INSERT INTO @V_TEMPCUST2
        SELECT DISTINCT CUST_ID FROM TCustomers A
        WHERE (EXISTS( SELECT 1 FROM @V_MANAGER_IDS B WHERE A.SERVICE_MAN = B.MANAGERID)
                OR EXISTS( SELECT 1 FROM @V_MANAGER_IDS C WHERE A.INPUT_MAN = C.MANAGERID) )
            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)
            AND(A.RECOMMENDED = @IN_RECOMMENDED OR @IN_RECOMMENDED = '' OR @IN_RECOMMENDED IS NULL)
            AND(A.SERVICE_MAN = @IN_ACCOUNTMANAGER OR @IN_ACCOUNTMANAGER = 0 OR @IN_ACCOUNTMANAGER IS NULL) --增加客户经理和推荐人的查询
            AND(ISNULL(AGE,0) BETWEEN ISNULL(@IN_START_AGE,0) AND ISNULL(@IN_END_AGE,1000) )
    --20110610 dongyg 按"产品客户经理"控制访问权限
    IF EXISTS(SELECT 1 FROM TSYSCONTROL WHERE FLAG_TYPE = 'PRODUCT001' AND VALUE = 1)
    BEGIN
        IF @V_DT_INTRUST = 1 --启用分布式
            INSERT INTO @V_TEMPCUST2
                SELECT DISTINCT A.CUST_ID FROM SRV_Intrust.INTRUST.dbo.TBENIFITOR A, SRV_Intrust.INTRUST.dbo.TPRODUCT B WHERE A.PRODUCT_ID = B.PRODUCT_ID AND B.SERVICE_MAN = @IN_INPUT_MAN
        ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
            INSERT INTO @V_TEMPCUST2
                SELECT DISTINCT A.CUST_ID FROM INTRUST..TBENIFITOR A, INTRUST..TPRODUCT B WHERE A.PRODUCT_ID = B.PRODUCT_ID AND B.SERVICE_MAN = @IN_INPUT_MAN
    END
    --------------------------------------------------------------------
    --根据 @IN_CLASSES 取满足条件的客户ID到临时表，若 @IN_CLASSES 为空，临时表也为空无记录
    IF @IN_CLASSES IS NULL SET @IN_CLASSES = N''
    IF dbo.chkCustClassDetailIDs(@IN_CLASSES) = 0
        RETURN -21111001 --输入参数无效
    CREATE TABLE #TMP_QUERY_CustByClass( CUST_ID INT )
    IF @IN_CLASSES <> ''
    BEGIN
        SET @V_SQL = N'INSERT INTO #TMP_QUERY_CustByClass(CUST_ID) SELECT CUST_ID FROM TCUSTOMERCLASS WHERE CLASSDETAIL_ID IN ('+@IN_CLASSES+') GROUP BY CUST_ID'
        EXEC sp_executesql @V_SQL
    END
    --------------------------------------------------------------------
    --20100121 dongyg start 增加对[认购份额、受益份额分级访问权限]的处理
    --认购份额、受益份额 按产品+客户计算汇总
    DECLARE @V_CUSTLEVELAUTH TABLE(
        PRODUCT_ID      INT,
        LEVEL_ID        INT,
        LEVEL_VALUE_ID  INT,
        MIN_VALUE       DEC(16,3),
        MAX_VALUE       DEC(16,3),
        LEVEL_TYPE      INT,        --条件类型：1任意一笔记录在范围内；2所有此类记录在范围内
        SUM_TYPE        INT         --金额汇总类型：1按客户+产品+合同；2按客户+产品；3按客户；为3时PRODUCT_ID值无效
    )
    --查询出操作员能够访问的 认购份额、受益份额分级
    INSERT INTO @V_CUSTLEVELAUTH(PRODUCT_ID, LEVEL_ID, LEVEL_VALUE_ID, MIN_VALUE, MAX_VALUE, LEVEL_TYPE, SUM_TYPE)
        SELECT A.PRODUCT_ID, B.LEVEL_ID, A.LEVEL_VALUE_ID, B.MIN_VALUE, B.MAX_VALUE, B.LEVEL_TYPE, B.SUM_TYPE
        FROM TCUSTLEVELAUTH A, TCUSTLEVEL B WHERE A.PRODUCT_ID = B.PRODUCT_ID AND A.LEVEL_VALUE_ID = B.LEVEL_VALUE_ID AND A.OP_CODE = @IN_INPUT_MAN
    ----------------------------------------
    --LEVEL_TYPE=1任意一笔记录在范围内
    --取出满足条件的认购客户: LEVEL_TYPE=1任意一笔记录在范围内
    IF @V_DT_INTRUST = 1 --启用分布式
    BEGIN
        INSERT INTO @V_TEMPCUST2(CUST_ID)
            SELECT DISTINCT B.CUST_ID
            FROM @V_CUSTLEVELAUTH A, (SELECT PRODUCT_ID, CONTRACT_BH, CUST_ID, SUM(RG_MONEY) AS RG_MONEY FROM SRV_Intrust.INTRUST.dbo.TCONTRACT GROUP BY PRODUCT_ID, CONTRACT_BH, CUST_ID) B
            WHERE A.LEVEL_TYPE = 1 AND A.SUM_TYPE = 1 --按客户+产品+合同汇总金额
                AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID) AND A.LEVEL_ID = 10 AND B.RG_MONEY BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647)
                AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
            UNION ALL
            SELECT DISTINCT B.CUST_ID
            FROM @V_CUSTLEVELAUTH A, (SELECT PRODUCT_ID, CUST_ID, SUM(RG_MONEY) AS RG_MONEY FROM SRV_Intrust.INTRUST.dbo.TCONTRACT GROUP BY PRODUCT_ID, CUST_ID) B
            WHERE A.LEVEL_TYPE = 1 AND A.SUM_TYPE = 2 --按客户+产品汇总金额
                AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID) AND A.LEVEL_ID = 10 AND B.RG_MONEY BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647)
                AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
            UNION ALL
            SELECT DISTINCT B.CUST_ID
            FROM @V_CUSTLEVELAUTH A, (SELECT CUST_ID, SUM(RG_MONEY) AS RG_MONEY FROM SRV_Intrust.INTRUST.dbo.TCONTRACT GROUP BY CUST_ID) B
            WHERE A.LEVEL_TYPE = 1 AND A.SUM_TYPE = 3 --按客户汇总金额
                AND A.LEVEL_ID = 10 AND B.RG_MONEY BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647)
                AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
    END
    ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
    BEGIN
        INSERT INTO @V_TEMPCUST2(CUST_ID)
            SELECT DISTINCT B.CUST_ID
            FROM @V_CUSTLEVELAUTH A, (SELECT PRODUCT_ID, CONTRACT_BH, CUST_ID, SUM(RG_MONEY) AS RG_MONEY FROM INTRUST..TCONTRACT GROUP BY PRODUCT_ID, CONTRACT_BH, CUST_ID) B
            WHERE A.LEVEL_TYPE = 1 AND A.SUM_TYPE = 1 --按客户+产品+合同汇总金额
                AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID) AND A.LEVEL_ID = 10 AND B.RG_MONEY BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647)
                AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
            UNION ALL
            SELECT DISTINCT B.CUST_ID
            FROM @V_CUSTLEVELAUTH A, (SELECT PRODUCT_ID, CUST_ID, SUM(RG_MONEY) AS RG_MONEY FROM INTRUST..TCONTRACT GROUP BY PRODUCT_ID, CUST_ID) B
            WHERE A.LEVEL_TYPE = 1 AND A.SUM_TYPE = 2 --按客户+产品汇总金额
                AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID) AND A.LEVEL_ID = 10 AND B.RG_MONEY BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647)
                AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
            UNION ALL
            SELECT DISTINCT B.CUST_ID
            FROM @V_CUSTLEVELAUTH A, (SELECT CUST_ID, SUM(RG_MONEY) AS RG_MONEY FROM INTRUST..TCONTRACT GROUP BY CUST_ID) B
            WHERE A.LEVEL_TYPE = 1 AND A.SUM_TYPE = 3 --按客户汇总金额
                AND A.LEVEL_ID = 10 AND B.RG_MONEY BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647)
                AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
    END
    --取出满足条件的受益客户: LEVEL_TYPE=1任意一笔记录在范围内
    IF @V_DT_INTRUST = 1 --启用分布式
    BEGIN
        INSERT INTO @V_TEMPCUST2(CUST_ID)
            SELECT DISTINCT B.CUST_ID
            FROM @V_CUSTLEVELAUTH A, (SELECT PRODUCT_ID, CONTRACT_BH, CUST_ID, SUM(BEN_AMOUNT) AS BEN_AMOUNT FROM SRV_Intrust.INTRUST.dbo.TBENIFITOR GROUP BY PRODUCT_ID, CONTRACT_BH, CUST_ID) B
            WHERE A.LEVEL_TYPE = 1 AND A.SUM_TYPE = 1 --按客户+产品+合同汇总金额
                AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID) AND A.LEVEL_ID = 20 AND B.BEN_AMOUNT BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647)
                AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
            UNION ALL
            SELECT DISTINCT B.CUST_ID
            FROM @V_CUSTLEVELAUTH A, (SELECT PRODUCT_ID, CUST_ID, SUM(BEN_AMOUNT) AS BEN_AMOUNT FROM SRV_Intrust.INTRUST.dbo.TBENIFITOR GROUP BY PRODUCT_ID, CUST_ID) B
            WHERE A.LEVEL_TYPE = 1 AND A.SUM_TYPE = 2 --按客户+产品汇总金额
                AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID) AND A.LEVEL_ID = 20 AND B.BEN_AMOUNT BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647)
                AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
            UNION ALL
            SELECT DISTINCT B.CUST_ID
            FROM @V_CUSTLEVELAUTH A, (SELECT CUST_ID, SUM(BEN_AMOUNT) AS BEN_AMOUNT FROM SRV_Intrust.INTRUST.dbo.TBENIFITOR GROUP BY CUST_ID) B
            WHERE A.LEVEL_TYPE = 1 AND A.SUM_TYPE = 3 --按客户汇总金额
                AND A.LEVEL_ID = 20 AND B.BEN_AMOUNT BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647)
                AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
    END
    ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
    BEGIN
        INSERT INTO @V_TEMPCUST2(CUST_ID)
            SELECT DISTINCT B.CUST_ID
            FROM @V_CUSTLEVELAUTH A, (SELECT PRODUCT_ID, CONTRACT_BH, CUST_ID, SUM(BEN_AMOUNT) AS BEN_AMOUNT FROM INTRUST..TBENIFITOR GROUP BY PRODUCT_ID, CONTRACT_BH, CUST_ID) B
            WHERE A.LEVEL_TYPE = 1 AND A.SUM_TYPE = 1 --按客户+产品+合同汇总金额
                AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID) AND A.LEVEL_ID = 20 AND B.BEN_AMOUNT BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647)
                AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
            UNION ALL
            SELECT DISTINCT B.CUST_ID
            FROM @V_CUSTLEVELAUTH A, (SELECT PRODUCT_ID, CUST_ID, SUM(BEN_AMOUNT) AS BEN_AMOUNT FROM INTRUST..TBENIFITOR GROUP BY PRODUCT_ID, CUST_ID) B
            WHERE A.LEVEL_TYPE = 1 AND A.SUM_TYPE = 2 --按客户+产品汇总金额
                AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID) AND A.LEVEL_ID = 20 AND B.BEN_AMOUNT BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647)
                AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
            UNION ALL
            SELECT DISTINCT B.CUST_ID
            FROM @V_CUSTLEVELAUTH A, (SELECT CUST_ID, SUM(BEN_AMOUNT) AS BEN_AMOUNT FROM INTRUST..TBENIFITOR GROUP BY CUST_ID) B
            WHERE A.LEVEL_TYPE = 1 AND A.SUM_TYPE = 3 --按客户汇总金额
                AND A.LEVEL_ID = 20 AND B.BEN_AMOUNT BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647)
                AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
    END
    --20100121 dongyg end 增加对[认购份额、受益份额分级访问权限]的处理
    ----------------------------------------
    --20100319 dongyg add start 增加对授权方式"2所有此类记录在范围内"的处理
    --LEVEL_TYPE=2所有此类记录在范围内
    --取出满足条件的认购客户: LEVEL_TYPE=2所有此类记录在范围内
    IF @V_DT_INTRUST = 1 --启用分布式
    BEGIN
        INSERT INTO @V_TEMPCUST2(CUST_ID)
            SELECT Y.CUST_ID FROM
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT PRODUCT_ID, CONTRACT_BH, CUST_ID, SUM(RG_MONEY) AS RG_MONEY FROM SRV_Intrust.INTRUST.dbo.TCONTRACT GROUP BY PRODUCT_ID, CONTRACT_BH, CUST_ID) B
                        ON (B.RG_MONEY BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 10 AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID)
                        AND A.SUM_TYPE = 1 --按客户+产品+合同汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Y LEFT OUTER JOIN
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT PRODUCT_ID, CONTRACT_BH, CUST_ID, SUM(RG_MONEY) AS RG_MONEY FROM SRV_Intrust.INTRUST.dbo.TCONTRACT GROUP BY PRODUCT_ID, CONTRACT_BH, CUST_ID) B
                        ON (NOT B.RG_MONEY BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 10 AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID)
                        AND A.SUM_TYPE = 1 --按客户+产品+合同汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Z ON (Y.CUST_ID = Z.CUST_ID) WHERE Z.CUST_ID IS NULL
            UNION ALL
            SELECT Y.CUST_ID FROM
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT PRODUCT_ID, CUST_ID, SUM(RG_MONEY) AS RG_MONEY FROM SRV_Intrust.INTRUST.dbo.TCONTRACT GROUP BY PRODUCT_ID, CUST_ID) B
                        ON (B.RG_MONEY BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 10 AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID)
                        AND A.SUM_TYPE = 2 --按客户+产品汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Y LEFT OUTER JOIN
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT PRODUCT_ID, CUST_ID, SUM(RG_MONEY) AS RG_MONEY FROM SRV_Intrust.INTRUST.dbo.TCONTRACT GROUP BY PRODUCT_ID, CUST_ID) B
                        ON (NOT B.RG_MONEY BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 10 AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID)
                        AND A.SUM_TYPE = 2 --按客户+产品汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Z ON (Y.CUST_ID = Z.CUST_ID) WHERE Z.CUST_ID IS NULL
            UNION ALL
            SELECT Y.CUST_ID FROM
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT CUST_ID, SUM(RG_MONEY) AS RG_MONEY FROM SRV_Intrust.INTRUST.dbo.TCONTRACT GROUP BY CUST_ID) B
                        ON (B.RG_MONEY BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 10
                        AND A.SUM_TYPE = 3 --按客户汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Y LEFT OUTER JOIN
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT CUST_ID, SUM(RG_MONEY) AS RG_MONEY FROM SRV_Intrust.INTRUST.dbo.TCONTRACT GROUP BY CUST_ID) B
                        ON (NOT B.RG_MONEY BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 10
                        AND A.SUM_TYPE = 3 --按客户汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Z ON (Y.CUST_ID = Z.CUST_ID) WHERE Z.CUST_ID IS NULL
    END
    ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
    BEGIN
        INSERT INTO @V_TEMPCUST2(CUST_ID)
            SELECT Y.CUST_ID FROM
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT PRODUCT_ID, CONTRACT_BH, CUST_ID, SUM(RG_MONEY) AS RG_MONEY FROM INTRUST.dbo.TCONTRACT GROUP BY PRODUCT_ID, CONTRACT_BH, CUST_ID) B
                        ON (B.RG_MONEY BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 10 AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID)
                        AND A.SUM_TYPE = 1 --按客户+产品+合同汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Y LEFT OUTER JOIN
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT PRODUCT_ID, CONTRACT_BH, CUST_ID, SUM(RG_MONEY) AS RG_MONEY FROM INTRUST.dbo.TCONTRACT GROUP BY PRODUCT_ID, CONTRACT_BH, CUST_ID) B
                        ON (NOT B.RG_MONEY BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 10 AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID)
                        AND A.SUM_TYPE = 1 --按客户+产品+合同汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Z ON (Y.CUST_ID = Z.CUST_ID) WHERE Z.CUST_ID IS NULL
            UNION ALL
            SELECT Y.CUST_ID FROM
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT PRODUCT_ID, CUST_ID, SUM(RG_MONEY) AS RG_MONEY FROM INTRUST.dbo.TCONTRACT GROUP BY PRODUCT_ID, CUST_ID) B
                        ON (B.RG_MONEY BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 10 AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID)
                        AND A.SUM_TYPE = 2 --按客户+产品汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Y LEFT OUTER JOIN
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT PRODUCT_ID, CUST_ID, SUM(RG_MONEY) AS RG_MONEY FROM INTRUST.dbo.TCONTRACT GROUP BY PRODUCT_ID, CUST_ID) B
                        ON (NOT B.RG_MONEY BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 10 AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID)
                        AND A.SUM_TYPE = 2 --按客户+产品汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Z ON (Y.CUST_ID = Z.CUST_ID) WHERE Z.CUST_ID IS NULL
            UNION ALL
            SELECT Y.CUST_ID FROM
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT CUST_ID, SUM(RG_MONEY) AS RG_MONEY FROM INTRUST.dbo.TCONTRACT GROUP BY CUST_ID) B
                        ON (B.RG_MONEY BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 10
                        AND A.SUM_TYPE = 3 --按客户汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Y LEFT OUTER JOIN
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT CUST_ID, SUM(RG_MONEY) AS RG_MONEY FROM INTRUST.dbo.TCONTRACT GROUP BY CUST_ID) B
                        ON (NOT B.RG_MONEY BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 10
                        AND A.SUM_TYPE = 3 --按客户汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Z ON (Y.CUST_ID = Z.CUST_ID) WHERE Z.CUST_ID IS NULL
    END
    --取出满足条件的受益客户: LEVEL_TYPE=2所有此类记录在范围内
    IF @V_DT_INTRUST = 1 --启用分布式
    BEGIN
        INSERT INTO @V_TEMPCUST2(CUST_ID)
            SELECT Y.CUST_ID FROM
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT PRODUCT_ID, CONTRACT_BH, CUST_ID, SUM(BEN_AMOUNT) AS BEN_AMOUNT FROM SRV_Intrust.INTRUST.dbo.TBENIFITOR GROUP BY PRODUCT_ID, CONTRACT_BH, CUST_ID) B
                        ON (B.BEN_AMOUNT BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 20 AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID)
                        AND A.SUM_TYPE = 1 --按客户+产品+合同汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Y LEFT OUTER JOIN
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT PRODUCT_ID, CONTRACT_BH, CUST_ID, SUM(BEN_AMOUNT) AS BEN_AMOUNT FROM SRV_Intrust.INTRUST.dbo.TBENIFITOR GROUP BY PRODUCT_ID, CONTRACT_BH, CUST_ID) B
                        ON (NOT B.BEN_AMOUNT BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 20 AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID)
                        AND A.SUM_TYPE = 1 --按客户+产品+合同汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Z ON (Y.CUST_ID = Z.CUST_ID) WHERE Z.CUST_ID IS NULL
            UNION ALL
            SELECT Y.CUST_ID FROM
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT PRODUCT_ID, CUST_ID, SUM(BEN_AMOUNT) AS BEN_AMOUNT FROM SRV_Intrust.INTRUST.dbo.TBENIFITOR GROUP BY PRODUCT_ID, CUST_ID) B
                        ON (B.BEN_AMOUNT BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 20 AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID)
                        AND A.SUM_TYPE = 2 --按客户+产品汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Y LEFT OUTER JOIN
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT PRODUCT_ID, CUST_ID, SUM(BEN_AMOUNT) AS BEN_AMOUNT FROM SRV_Intrust.INTRUST.dbo.TBENIFITOR GROUP BY PRODUCT_ID, CUST_ID) B
                        ON (NOT B.BEN_AMOUNT BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 20 AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID)
                        AND A.SUM_TYPE = 2 --按客户+产品汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Z ON (Y.CUST_ID = Z.CUST_ID) WHERE Z.CUST_ID IS NULL
            UNION ALL
            SELECT Y.CUST_ID FROM
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT CUST_ID, SUM(BEN_AMOUNT) AS BEN_AMOUNT FROM SRV_Intrust.INTRUST.dbo.TBENIFITOR GROUP BY CUST_ID) B
                        ON (B.BEN_AMOUNT BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 20
                        AND A.SUM_TYPE = 3 --按客户汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Y LEFT OUTER JOIN
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT CUST_ID, SUM(BEN_AMOUNT) AS BEN_AMOUNT FROM SRV_Intrust.INTRUST.dbo.TBENIFITOR GROUP BY CUST_ID) B
                        ON (NOT B.BEN_AMOUNT BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 20
                        AND A.SUM_TYPE = 3 --按客户汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Z ON (Y.CUST_ID = Z.CUST_ID) WHERE Z.CUST_ID IS NULL
    END
    ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
    BEGIN
        INSERT INTO @V_TEMPCUST2(CUST_ID)
            SELECT Y.CUST_ID FROM
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT PRODUCT_ID, CONTRACT_BH, CUST_ID, SUM(BEN_AMOUNT) AS BEN_AMOUNT FROM INTRUST.dbo.TBENIFITOR GROUP BY PRODUCT_ID, CONTRACT_BH, CUST_ID) B
                        ON (B.BEN_AMOUNT BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 20 AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID)
                        AND A.SUM_TYPE = 1 --按客户+产品+合同汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Y LEFT OUTER JOIN
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT PRODUCT_ID, CONTRACT_BH, CUST_ID, SUM(BEN_AMOUNT) AS BEN_AMOUNT FROM INTRUST.dbo.TBENIFITOR GROUP BY PRODUCT_ID, CONTRACT_BH, CUST_ID) B
                        ON (NOT B.BEN_AMOUNT BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 20 AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID)
                        AND A.SUM_TYPE = 1 --按客户+产品+合同汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Z ON (Y.CUST_ID = Z.CUST_ID) WHERE Z.CUST_ID IS NULL
            UNION ALL
            SELECT Y.CUST_ID FROM
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT PRODUCT_ID, CUST_ID, SUM(BEN_AMOUNT) AS BEN_AMOUNT FROM INTRUST.dbo.TBENIFITOR GROUP BY PRODUCT_ID, CUST_ID) B
                        ON (B.BEN_AMOUNT BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 20 AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID)
                        AND A.SUM_TYPE = 2 --按客户+产品汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Y LEFT OUTER JOIN
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT PRODUCT_ID, CUST_ID, SUM(BEN_AMOUNT) AS BEN_AMOUNT FROM INTRUST.dbo.TBENIFITOR GROUP BY PRODUCT_ID, CUST_ID) B
                        ON (NOT B.BEN_AMOUNT BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 20 AND(A.PRODUCT_ID = 0 OR A.PRODUCT_ID = B.PRODUCT_ID)
                        AND A.SUM_TYPE = 2 --按客户+产品汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Z ON (Y.CUST_ID = Z.CUST_ID) WHERE Z.CUST_ID IS NULL
            UNION ALL
            SELECT Y.CUST_ID FROM
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT CUST_ID, SUM(BEN_AMOUNT) AS BEN_AMOUNT FROM INTRUST.dbo.TBENIFITOR GROUP BY CUST_ID) B
                        ON (B.BEN_AMOUNT BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 20
                        AND A.SUM_TYPE = 3 --按客户汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Y LEFT OUTER JOIN
                (   SELECT DISTINCT B.CUST_ID
                    FROM @V_CUSTLEVELAUTH A JOIN (SELECT CUST_ID, SUM(BEN_AMOUNT) AS BEN_AMOUNT FROM INTRUST.dbo.TBENIFITOR GROUP BY CUST_ID) B
                        ON (NOT B.BEN_AMOUNT BETWEEN ISNULL(A.MIN_VALUE,-2147483647) AND ISNULL(A.MAX_VALUE,2147483647))
                    WHERE A.LEVEL_TYPE = 2 AND A.LEVEL_ID = 20
                        AND A.SUM_TYPE = 3 --按客户汇总金额
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE B.CUST_ID = C.CUST_ID)
                ) Z ON (Y.CUST_ID = Z.CUST_ID) WHERE Z.CUST_ID IS NULL
    END
    --20100319 dongyg end
    --20111025 DONGYG 如果不控制潜在客户的查询，将所有潜在客户添加到 @V_TEMPCUST2 中
    IF @V_CUSTQZ0001 <> 1
        INSERT INTO @V_TEMPCUST2(CUST_ID) SELECT CUST_ID FROM TCustomers WHERE IS_DEAL = 2

    --------------------------------------------------------------------
    IF @IN_PRODUCT_ID IS NULL SELECT @IN_PRODUCT_ID = 0
    IF @IN_CELL_ID IS NULL SELECT @IN_CELL_ID = 0
    IF ISNULL(@IN_CUST_ID,0)<>0
        INSERT INTO #TEMPCUST(CUST_ID,CUST_NO,CUST_NAME,SEX,BIRTHDAY,POST_ADDRESS,POST_CODE,CARD_ID,CUST_TEL,E_MAIL,O_TEL,MOBILE,BP,
                STATUS_NAME,LAST_RG_DATE,CURR_TO_AMOUNT,TOTAL_MONEY,CURRENT_MONEY,BEN_AMOUNT,EXCHANGE_AMOUNT,BACK_AMOUNT,SERVICE_MAN,
                CUST_TYPE,MODI_FLAG,GRADE_LEVEL,GRADE_LEVEL_NAME,CARD_TYPE_NAME,IS_DEAL,IS_DEAL_NAME,CUST_SOURCE,CUST_SOURCE_NAME,
                COUNTRY,COUNTRY_NAME,MONEY_SOURCE,MONEY_SOURCE_NAME,FIRST_RG_DATE,END_AMOUNT,PRINT_DEPLOY_BILL,PRINT_POST_INFO)
            SELECT CUST_ID,CUST_NO,CUST_NAME,CASE SEX WHEN 1 THEN N'男' ELSE N'女' END AS SEX,BIRTHDAY,POST_ADDRESS,POST_CODE,
                   CARD_ID,CUST_TEL,E_MAIL,O_TEL,MOBILE,BP,STATUS_NAME,LAST_RG_DATE, 0,TOTAL_MONEY,CURRENT_MONEY,0,0,0,
                   SERVICE_MAN,CUST_TYPE,0,GRADE_LEVEL,GRADE_LEVEL_NAME,CARD_TYPE_NAME,IS_DEAL,
                   CASE IS_DEAL WHEN 1 THEN N'事实客户' WHEN 2 THEN N'潜在客户' END AS IS_DEAL_NAME,
                   CUST_SOURCE,CUST_SOURCE_NAME,COUNTRY,N'',MONEY_SOURCE,MONEY_SOURCE_NAME,NULL,0,PRINT_DEPLOY_BILL,PRINT_POST_INFO
                FROM TCustomers WHERE CUST_ID = @IN_CUST_ID
    ELSE
    BEGIN
        IF @IN_PRODUCT_ID = 0 AND @IN_CELL_ID = 0 AND ISNULL(@IN_PRODUCT_TO,'')=''
        BEGIN
            INSERT INTO #TEMPCUST(CUST_ID,CUST_NO,CUST_NAME,SEX,BIRTHDAY,POST_ADDRESS,POST_CODE,CARD_ID,CUST_TEL,E_MAIL,O_TEL,MOBILE,BP,
                    STATUS_NAME,LAST_RG_DATE,CURR_TO_AMOUNT,TOTAL_MONEY,CURRENT_MONEY,BEN_AMOUNT,EXCHANGE_AMOUNT,BACK_AMOUNT,SERVICE_MAN,
                    CUST_TYPE,MODI_FLAG,GRADE_LEVEL,GRADE_LEVEL_NAME,CARD_TYPE_NAME,IS_DEAL,IS_DEAL_NAME,CUST_SOURCE,CUST_SOURCE_NAME,
                    COUNTRY,COUNTRY_NAME,MONEY_SOURCE,MONEY_SOURCE_NAME,FIRST_RG_DATE,END_AMOUNT,PRINT_DEPLOY_BILL,PRINT_POST_INFO)
                SELECT CUST_ID,CUST_NO,CUST_NAME,CASE SEX WHEN 1 THEN N'男' ELSE N'女' END AS SEX,BIRTHDAY,POST_ADDRESS,POST_CODE,
                       CARD_ID,CUST_TEL,E_MAIL,O_TEL,MOBILE,BP,STATUS_NAME,LAST_RG_DATE, 0,TOTAL_MONEY,CURRENT_MONEY,0,0,0,
                       SERVICE_MAN,CUST_TYPE,0,GRADE_LEVEL,GRADE_LEVEL_NAME,CARD_TYPE_NAME,IS_DEAL,
                       CASE IS_DEAL WHEN 1 THEN N'事实客户' WHEN 2 THEN N'潜在客户' END AS IS_DEAL_NAME,
                       A.CUST_SOURCE,A.CUST_SOURCE_NAME,A.COUNTRY,N'',A.MONEY_SOURCE,A.MONEY_SOURCE_NAME,NULL,0,A.PRINT_DEPLOY_BILL,A.PRINT_POST_INFO
                    FROM TCustomers A
                    WHERE (ISNULL(@IN_CUST_ID,0) = 0 OR CUST_ID = @IN_CUST_ID)
                        AND (ISNULL(@IN_CUST_NO,'') = N'' OR CUST_NO LIKE '%'+@IN_CUST_NO OR CUST_NO LIKE @IN_CUST_NO+'_______')
                        AND (ISNULL(@IN_CUST_NAME,'') = N'' OR CUST_NAME LIKE '%'+@IN_CUST_NAME+'%')
                        AND (ISNULL(@IN_CUST_SOURCE,'') = N'' OR CUST_SOURCE = @IN_CUST_SOURCE)
                        AND (ISNULL(@IN_CARD_TYPE,'') = N'' OR CARD_TYPE = @IN_CARD_TYPE)
                        AND (ISNULL(@IN_TOUCH_TYPE,'') = N'' OR ISNULL(@IN_TOUCH_TYPE,'') = N'0' OR TOUCH_TYPE = @IN_TOUCH_TYPE)
                        AND (ISNULL(@IN_CARD_ID,'') = N'' OR CARD_ID LIKE @IN_CARD_ID+'%' OR CARD_ID LIKE '%'+@IN_CARD_ID18+'%')
                        AND (ISNULL(@IN_MAX_TIMES,0) = 0 OR (RG_TIMES BETWEEN @IN_MIN_TIMES AND @IN_MAX_TIMES) )
                        --操作员=客户资料录入人 或 有访问客户资料权限
                        AND ( ( @V_QUERYCUSTOMERS = 1 AND A.INPUT_MAN = @IN_INPUT_MAN) OR SERVICE_MAN = @IN_INPUT_MAN OR @V_FLAG_ACCESS_ALL = 1
                             OR EXISTS(SELECT 1 FROM @V_TEMPCUST2 B WHERE A.CUST_ID = B.CUST_ID) )
                        AND (ISNULL(CUST_TEL,'') + ',' + ISNULL(O_TEL,'') + ',' + ISNULL(H_TEL,'') + ',' + ISNULL(MOBILE,'') + ',' + ISNULL(BP,'') + ',' + ISNULL(FAX,'') LIKE '%'+@IN_TEL+'%' OR @IN_TEL = N'' OR @IN_TEL IS NULL )
                        AND (POST_ADDRESS LIKE '%'+@IN_ADDRESS+'%' OR @IN_ADDRESS = N'' OR @IN_ADDRESS IS NULL )
                        AND (ISNULL(@IN_CUST_TYPE,0) = 0 OR CUST_TYPE = @IN_CUST_TYPE)
                        AND (A.IS_DEAL = @IN_IS_DEAL OR ISNULL(@IN_IS_DEAL,0) = 0 )
                        AND (CUST_ID IN (SELECT DISTINCT CUST_ID FROM TCustFamilyInfo WHERE FAMILY_NAME LIKE '%'+@IN_FAMILY_NAME+'%') OR ISNULL(@IN_FAMILY_NAME,'') = N'')
                        AND (@IN_ONLYEMAIL IS NULL OR @IN_ONLYEMAIL <> 1 OR (@IN_ONLYEMAIL = 1 AND E_MAIL LIKE '%@%'))
                        AND ((ISNULL(@IN_MIN_TOTAL_MONEY,0) =0 OR TOTAL_MONEY >= @IN_MIN_TOTAL_MONEY) AND (ISNULL(@IN_MAX_TOTAL_MONEY,0) =0 OR TOTAL_MONEY <= @IN_MAX_TOTAL_MONEY))
                        AND (ISNULL(BIRTHDAY,0)%10000 BETWEEN ISNULL(@IN_BIRTHDAY,0)%10000 AND ISNULL(@IN_BIRTHDAY_END,20101231)%10000)
                        AND (ISNULL(@IN_PRINT_DEPLOY_BILL,0) = 0 OR PRINT_DEPLOY_BILL = @IN_PRINT_DEPLOY_BILL)
                        AND (ISNULL(@IN_IS_LINK,0) = 0 OR IS_LINK = @IN_IS_LINK)
                        AND (ISNULL(@IN_VIP_CARD_ID,'') = N'' OR VIP_CARD_ID LIKE '%'+@IN_VIP_CARD_ID+'%')
                        AND (ISNULL(@IN_HGTZR_BH,'') = N'' OR HGTZR_BH LIKE '%'+@IN_HGTZR_BH+'%')
                        AND (ISNULL(@IN_MONEY_SOURCE_NAME,'') = N'' OR MONEY_SOURCE_NAME LIKE '%'+@IN_MONEY_SOURCE_NAME+'%')
                        AND (ISNULL(@IN_COUNTRY,'') = N'' OR COUNTRY = @IN_COUNTRY)
                        AND STATUS <> '112805'
                        AND (A.CUST_ID IN (SELECT C.CUST_ID FROM TCustGroupMembers C WHERE C.GroupID = @IN_GroupID) OR ISNULL(@IN_GroupID,0) = 0)
                        AND(A.RECOMMENDED = @IN_RECOMMENDED OR @IN_RECOMMENDED = '' OR @IN_RECOMMENDED IS NULL)
                        AND(A.SERVICE_MAN = @IN_ACCOUNTMANAGER OR @IN_ACCOUNTMANAGER = 0 OR @IN_ACCOUNTMANAGER IS NULL) --增加客户经理和推荐人的查询
                        AND(ISNULL(AGE,0) BETWEEN ISNULL(@IN_START_AGE,0) AND ISNULL(@IN_END_AGE,1000) )
        END
        ELSE
        BEGIN
            --MODI BY JINXR 2007/12/4
            IF @IN_WTR_FLAG = 0
            BEGIN
                -- 委托人和受益人
                IF @V_DT_INTRUST = 1 --启用分布式
                BEGIN
                    INSERT INTO @V_TEMPCUST1
                        SELECT CUST_ID FROM SRV_Intrust.INTRUST.dbo.TCONTRACT A
                            WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID  = 0) AND HT_STATUS <> '120104'
                                AND (A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
                                AND EXISTS(SELECT 1 FROM SRV_Intrust.INTRUST.dbo.TPRODUCT Y WHERE A.PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
                                AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST1 Z WHERE A.CUST_ID = Z.CUST_ID)
                            GROUP BY CUST_ID
                    INSERT INTO @V_TEMPCUST1
                        SELECT CUST_ID FROM SRV_Intrust.INTRUST.dbo.TBENIFITOR A
                            WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0) AND BEN_STATUS <>'121104' AND (PROV_LEVEL=@IN_PROV_LEVEL OR @IN_PROV_LEVEL='' OR @IN_PROV_LEVEL IS NULL)
                                AND (A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
                                AND EXISTS(SELECT 1 FROM SRV_Intrust.INTRUST.dbo.TPRODUCT Y WHERE A.PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
                                AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST1 Z WHERE A.CUST_ID = Z.CUST_ID)
                            GROUP BY CUST_ID
                END
                ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
                BEGIN
                    INSERT INTO @V_TEMPCUST1
                        SELECT CUST_ID FROM INTRUST.dbo.TCONTRACT A
                            WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)  AND HT_STATUS <> '120104'
                                AND (A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
                                AND EXISTS(SELECT 1 FROM INTRUST.dbo.TPRODUCT Y WHERE A.PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
                                AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST1 Z WHERE A.CUST_ID = Z.CUST_ID)
                            GROUP BY CUST_ID
                    INSERT INTO @V_TEMPCUST1
                        SELECT CUST_ID FROM INTRUST.dbo.TBENIFITOR A
                            WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)  AND BEN_STATUS <>'121104' AND (PROV_LEVEL=@IN_PROV_LEVEL OR @IN_PROV_LEVEL='' OR @IN_PROV_LEVEL IS NULL)
                                AND (A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
                                AND EXISTS(SELECT 1 FROM INTRUST.dbo.TPRODUCT Y WHERE A.PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
                                AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST1 Z WHERE A.CUST_ID = Z.CUST_ID)
                            GROUP BY CUST_ID
                END
            END
            ELSE IF @IN_WTR_FLAG = 1
            BEGIN
                --受益人
                IF @V_DT_INTRUST = 1 --启用分布式
                BEGIN
                    INSERT INTO @V_TEMPCUST1
                    SELECT CUST_ID FROM SRV_Intrust.INTRUST.dbo.TBENIFITOR A
                        WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)  AND BEN_STATUS <>'121104' AND (PROV_LEVEL=@IN_PROV_LEVEL OR @IN_PROV_LEVEL='' OR @IN_PROV_LEVEL IS NULL)
                            AND (A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
                            AND EXISTS(SELECT 1 FROM SRV_Intrust.INTRUST.dbo.TPRODUCT Y WHERE A.PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
                            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST1 Z WHERE A.CUST_ID = Z.CUST_ID)
                        GROUP BY CUST_ID
                END
                ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
                BEGIN
                    INSERT INTO @V_TEMPCUST1
                    SELECT CUST_ID FROM INTRUST.dbo.TBENIFITOR A
                        WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)  AND BEN_STATUS <>'121104' AND (PROV_LEVEL=@IN_PROV_LEVEL OR @IN_PROV_LEVEL='' OR @IN_PROV_LEVEL IS NULL)
                            AND (A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
                            AND EXISTS(SELECT 1 FROM INTRUST.dbo.TPRODUCT Y WHERE A.PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
                            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST1 Z WHERE A.CUST_ID = Z.CUST_ID)
                        GROUP BY CUST_ID
                END
            END
            ELSE
            BEGIN
                --委托人
                IF @V_DT_INTRUST = 1 --启用分布式
                BEGIN
                    INSERT INTO @V_TEMPCUST1
                    SELECT CUST_ID FROM SRV_Intrust.INTRUST.dbo.TCONTRACT A
                        WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)  AND HT_STATUS <> '120104'
                            AND (A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
                            AND EXISTS(SELECT 1 FROM SRV_Intrust.INTRUST.dbo.TPRODUCT Y WHERE A.PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
                            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST1 Z WHERE A.CUST_ID = Z.CUST_ID)
                        GROUP BY CUST_ID
                END
                ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
                BEGIN
                    INSERT INTO @V_TEMPCUST1
                    SELECT CUST_ID FROM INTRUST.dbo.TCONTRACT A
                        WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)  AND HT_STATUS <> '120104'
                           AND (A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
                           AND EXISTS(SELECT 1 FROM INTRUST.dbo.TPRODUCT Y WHERE A.PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
                           AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST1 Z WHERE A.CUST_ID = Z.CUST_ID)
                        GROUP BY CUST_ID
                END
            END
            IF @V_DT_INTRUST = 1 --启用分布式
            BEGIN
                INSERT INTO @V_TEMPCUST1 SELECT CUST_ID FROM SRV_Intrust.INTRUST.dbo.TPRECONTRACT A
                    WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)  AND PRE_STATUS='111301'
                        AND (A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
                        AND EXISTS(SELECT 1 FROM SRV_Intrust.INTRUST.dbo.TPRODUCT Y WHERE A.PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST1 Z WHERE A.CUST_ID = Z.CUST_ID)
                    GROUP BY CUST_ID
            END
            ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
            BEGIN

                INSERT INTO @V_TEMPCUST1 SELECT CUST_ID FROM INTRUST.dbo.TPRECONTRACT A
                    WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)  AND PRE_STATUS='111301'
                        AND (A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
                        AND EXISTS(SELECT 1 FROM INTRUST.dbo.TPRODUCT Y WHERE A.PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST1 Z WHERE A.CUST_ID = Z.CUST_ID)
                    GROUP BY CUST_ID
            END
            --
            INSERT INTO #TEMPCUST(CUST_ID,CUST_NO,CUST_NAME,SEX,BIRTHDAY,POST_ADDRESS,POST_CODE,CARD_ID,CUST_TEL,E_MAIL,O_TEL,MOBILE,BP,
                    STATUS_NAME,LAST_RG_DATE,CURR_TO_AMOUNT,TOTAL_MONEY,CURRENT_MONEY,BEN_AMOUNT,EXCHANGE_AMOUNT,BACK_AMOUNT,SERVICE_MAN,
                    CUST_TYPE,MODI_FLAG,GRADE_LEVEL,GRADE_LEVEL_NAME,CARD_TYPE_NAME,IS_DEAL,IS_DEAL_NAME,CUST_SOURCE,CUST_SOURCE_NAME,
                    COUNTRY,COUNTRY_NAME,MONEY_SOURCE,MONEY_SOURCE_NAME,FIRST_RG_DATE,END_AMOUNT,PRINT_DEPLOY_BILL,PRINT_POST_INFO)
                SELECT A.CUST_ID,A.CUST_NO,A.CUST_NAME,CASE SEX WHEN 1 THEN '男' ELSE '女' END AS SEX,BIRTHDAY,POST_ADDRESS,POST_CODE,
                       A.CARD_ID,A.CUST_TEL,A.E_MAIL,A.O_TEL,A.MOBILE,A.BP,A.STATUS_NAME,A.LAST_RG_DATE, 0,A.TOTAL_MONEY,A.CURRENT_MONEY,0,0,0,
                       A.SERVICE_MAN,A.CUST_TYPE,0,GRADE_LEVEL,GRADE_LEVEL_NAME,CARD_TYPE_NAME,IS_DEAL,
                       CASE IS_DEAL WHEN 1 THEN '事实客户' WHEN 2 THEN '潜在客户' END AS IS_DEAL_NAME,
                       A.CUST_SOURCE,A.CUST_SOURCE_NAME,A.COUNTRY,N'',A.MONEY_SOURCE,A.MONEY_SOURCE_NAME,NULL,0,A.PRINT_DEPLOY_BILL,A.PRINT_POST_INFO
                    FROM TCustomers A,@V_TEMPCUST1 B
                    WHERE (A.CUST_ID = @IN_CUST_ID OR @IN_CUST_ID = 0 OR @IN_CUST_ID IS NULL)
                        AND (A.CUST_NO LIKE '%'+@IN_CUST_NO OR CUST_NO LIKE @IN_CUST_NO+'_______' OR @IN_CUST_NO = N'' OR @IN_CUST_NO IS NULL)
                        AND (A.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%' OR @IN_CUST_NAME = N'' OR @IN_CUST_NAME IS NULL)
                        AND (A.CUST_SOURCE = @IN_CUST_SOURCE OR @IN_CUST_SOURCE = N'' OR @IN_CUST_SOURCE IS NULL)
                        AND (A.CARD_TYPE = @IN_CARD_TYPE OR @IN_CARD_TYPE = N'' OR @IN_CARD_TYPE IS NULL)
                        AND (A.TOUCH_TYPE = @IN_TOUCH_TYPE OR @IN_TOUCH_TYPE = N'' OR @IN_TOUCH_TYPE IS NULL)
                        AND (A.CARD_ID LIKE @IN_CARD_ID+'%' OR CARD_ID LIKE '%'+@IN_CARD_ID18+'%' OR @IN_CARD_ID = N'' OR @IN_CARD_ID IS NULL)
                        AND ((A.RG_TIMES BETWEEN @IN_MIN_TIMES AND @IN_MAX_TIMES) OR @IN_MAX_TIMES IS NULL OR @IN_MAX_TIMES = 0 )
                        --操作员=客户资料录入人 或 有访问客户资料权限
                        AND (( @V_QUERYCUSTOMERS = 1 AND A.INPUT_MAN = @IN_INPUT_MAN) OR A.SERVICE_MAN = @IN_INPUT_MAN OR @V_FLAG_ACCESS_ALL = 1
                             OR EXISTS(SELECT 1 FROM @V_TEMPCUST2 Y WHERE A.CUST_ID = Y.CUST_ID))
                        --当输入了产品时，只返回该产品签约客户资料
                        AND A.CUST_ID=B.CUST_ID
                        AND (ISNULL(CUST_TEL,'') + ',' + ISNULL(O_TEL,'') + ',' + ISNULL(H_TEL,'') + ',' + ISNULL(MOBILE,'') + ',' + ISNULL(BP,'') + ',' + ISNULL(FAX,'') LIKE '%'+@IN_TEL+'%' OR @IN_TEL = N'' OR @IN_TEL IS NULL )
                        AND (A.POST_ADDRESS LIKE '%'+@IN_ADDRESS+'%' OR @IN_ADDRESS = N'' OR @IN_ADDRESS IS NULL )
                        AND (A.CUST_TYPE = @IN_CUST_TYPE OR @IN_CUST_TYPE = 0 OR @IN_CUST_TYPE IS NULL)
                        AND (A.IS_DEAL = @IN_IS_DEAL OR ISNULL(@IN_IS_DEAL,0) = 0 )
                        AND (A.CUST_ID IN (SELECT DISTINCT CUST_ID FROM TCustFamilyInfo WHERE FAMILY_NAME LIKE '%'+@IN_FAMILY_NAME+'%') OR @IN_FAMILY_NAME IS NULL OR @IN_FAMILY_NAME = N'')
                        AND (@IN_ONLYEMAIL IS NULL OR @IN_ONLYEMAIL <> 1 OR (@IN_ONLYEMAIL = 1 AND E_MAIL LIKE '%@%'))
                        AND ((ISNULL(@IN_MIN_TOTAL_MONEY,0) =0 OR TOTAL_MONEY >= @IN_MIN_TOTAL_MONEY) AND (ISNULL(@IN_MAX_TOTAL_MONEY,0) =0 OR TOTAL_MONEY <= @IN_MAX_TOTAL_MONEY))
                        AND (ISNULL(BIRTHDAY,0)%10000 BETWEEN ISNULL(@IN_BIRTHDAY,0)%10000 AND ISNULL(@IN_BIRTHDAY_END,20101231)%10000)
                        AND (A.PRINT_DEPLOY_BILL = @IN_PRINT_DEPLOY_BILL OR ISNULL(@IN_PRINT_DEPLOY_BILL,0) = 0)
                        AND (A.IS_LINK = @IN_IS_LINK OR ISNULL(@IN_IS_LINK,0) = 0)
                        AND (VIP_CARD_ID LIKE '%'+@IN_VIP_CARD_ID+'%' OR ISNULL(@IN_VIP_CARD_ID,'') = N'')
                        AND (HGTZR_BH LIKE '%'+@IN_HGTZR_BH+'%' OR ISNULL(@IN_HGTZR_BH,'') = N'')
                        AND (ISNULL(@IN_MONEY_SOURCE_NAME,'') = N'' OR MONEY_SOURCE_NAME LIKE '%'+@IN_MONEY_SOURCE_NAME+'%')
                        AND (ISNULL(@IN_COUNTRY,'') = N'' OR COUNTRY = @IN_COUNTRY)
                        AND A.STATUS <> '112805'
                        AND (A.CUST_ID IN (SELECT C.CUST_ID FROM TCustGroupMembers C WHERE C.GroupID = @IN_GroupID) OR ISNULL(@IN_GroupID,0) = 0)
                        AND(A.RECOMMENDED = @IN_RECOMMENDED OR @IN_RECOMMENDED = '' OR @IN_RECOMMENDED IS NULL)
                        AND(A.SERVICE_MAN = @IN_ACCOUNTMANAGER OR @IN_ACCOUNTMANAGER = 0 OR @IN_ACCOUNTMANAGER IS NULL) --增加客户经理和推荐人的查询
                        AND(ISNULL(AGE,0) BETWEEN ISNULL(@IN_START_AGE,0) AND ISNULL(@IN_END_AGE,1000) )

        END
    END

    --受让金额
    --ADD BY JINXR 2006/08/17
    CREATE TABLE #TEMPCUST3(CUST_ID INT,TO_AMOUNT DECIMAL(16,3))
    IF @V_DT_INTRUST = 1 --启用分布式
    BEGIN
        INSERT INTO #TEMPCUST3
            SELECT TO_CUST_ID,SUM(TO_AMOUNT) FROM SRV_Intrust.INTRUST.dbo.TBENCHANGES
                WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)
                    AND (PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
                    AND EXISTS(SELECT 1 FROM SRV_Intrust.INTRUST.dbo.TPRODUCT Y WHERE PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
                    AND CHECK_FLAG = 2 AND TRANS_TYPE NOT IN('181511','181512','181513','181514')
                GROUP BY TO_CUST_ID
    END
    ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
    BEGIN
        INSERT INTO #TEMPCUST3
            SELECT TO_CUST_ID,SUM(TO_AMOUNT) FROM INTRUST.dbo.TBENCHANGES
                WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)
                    AND (PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
                    AND EXISTS(SELECT 1 FROM INTRUST.dbo.TPRODUCT Y WHERE PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
                    AND CHECK_FLAG = 2 AND TRANS_TYPE NOT IN('181511','181512','181513','181514')
                GROUP BY TO_CUST_ID
    END

    UPDATE #TEMPCUST
        SET EXCHANGE_AMOUNT = B.TO_AMOUNT
        FROM #TEMPCUST A, #TEMPCUST3 B
        WHERE A.CUST_ID = B.CUST_ID
    --转让金额
    DELETE FROM #TEMPCUST3
    IF @V_DT_INTRUST = 1 --启用分布式
    BEGIN
        INSERT INTO #TEMPCUST3
        SELECT FROM_CUST_ID,SUM(TO_AMOUNT) FROM SRV_Intrust.INTRUST.dbo.TBENCHANGES
            WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)
                AND (PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
                AND EXISTS(SELECT 1 FROM SRV_Intrust.INTRUST.dbo.TPRODUCT Y WHERE PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
                AND CHECK_FLAG = 2 AND TRANS_TYPE NOT IN('181511','181512','181513','181514')
            GROUP BY FROM_CUST_ID
    END
    ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
    BEGIN
        INSERT INTO #TEMPCUST3
        SELECT FROM_CUST_ID,SUM(TO_AMOUNT) FROM INTRUST.dbo.TBENCHANGES
            WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)
                AND (PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
                AND EXISTS(SELECT 1 FROM INTRUST.dbo.TPRODUCT Y WHERE PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
                AND CHECK_FLAG = 2 AND TRANS_TYPE NOT IN('181511','181512','181513','181514')
            GROUP BY FROM_CUST_ID
    END

    UPDATE #TEMPCUST
        SET EXCHANGE_AMOUNT = ISNULL(EXCHANGE_AMOUNT,0) - B.TO_AMOUNT
        FROM #TEMPCUST A, #TEMPCUST3 B
        WHERE A.CUST_ID = B.CUST_ID
    --到期本金
    DELETE FROM #TEMPCUST3
    IF @V_DT_INTRUST = 1 --启用分布式
    BEGIN
        INSERT INTO #TEMPCUST3
        SELECT CUST_ID,SUM(SY_AMOUNT)
            FROM SRV_Intrust.INTRUST.dbo.TDEPLOY
            WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0) AND SY_TYPE = N'111605' AND PZ_FLAG = 2
            AND (PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
            AND EXISTS(SELECT 1 FROM SRV_Intrust.INTRUST.dbo.TPRODUCT Y WHERE PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
            GROUP BY CUST_ID
    END
    ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
    BEGIN
        INSERT INTO #TEMPCUST3
        SELECT CUST_ID,SUM(SY_AMOUNT)
            FROM INTRUST.dbo.TDEPLOY
            WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0) AND SY_TYPE = N'111605' AND PZ_FLAG = 2
            AND (PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
            AND EXISTS(SELECT 1 FROM INTRUST.dbo.TPRODUCT Y WHERE PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
            GROUP BY CUST_ID
    END

    UPDATE #TEMPCUST
        SET BACK_AMOUNT = B.TO_AMOUNT
        FROM #TEMPCUST A, #TEMPCUST3 B
        WHERE A.CUST_ID = B.CUST_ID
    CREATE TABLE #TEMPCUST4(CUST_ID    INT,
                            BEN_AMOUNT DECIMAL(16,3),
                            TO_AMOUNT  DECIMAL(16,3),
                            MAX_DATE   INT,
                            PRODUCT_ID INT
                            )
    IF @V_DT_INTRUST = 1 --启用分布式
    BEGIN
        INSERT INTO #TEMPCUST4
        SELECT CUST_ID,SUM(ISNULL(BEN_AMOUNT,0)),SUM(ISNULL(TO_AMOUNT,0)),MAX(BEN_DATE),MAX(PRODUCT_ID)
            FROM SRV_Intrust.INTRUST.dbo.TBENIFITOR
            WHERE CHECK_FLAG = 2 AND (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)
            AND (PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
            AND EXISTS(SELECT 1 FROM SRV_Intrust.INTRUST.dbo.TPRODUCT Y WHERE PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
            GROUP BY CUST_ID
    END
    ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
    BEGIN
        INSERT INTO #TEMPCUST4
        SELECT CUST_ID,SUM(ISNULL(BEN_AMOUNT,0)),SUM(ISNULL(TO_AMOUNT,0)),MAX(BEN_DATE),MAX(PRODUCT_ID)
            FROM INTRUST.dbo.TBENIFITOR
            WHERE CHECK_FLAG = 2 AND (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)
            AND (PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
            AND EXISTS(SELECT 1 FROM INTRUST.dbo.TPRODUCT Y WHERE PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
            GROUP BY CUST_ID
    END

    UPDATE #TEMPCUST
        SET BEN_AMOUNT = B.BEN_AMOUNT,
            TOTAL_MONEY = B.TO_AMOUNT,
            LAST_RG_DATE = B.MAX_DATE
        FROM #TEMPCUST A, #TEMPCUST4 B
        WHERE A.CUST_ID = B.CUST_ID
    IF @IN_PRODUCT_ID <> 0
    BEGIN
        DELETE FROM #TEMPCUST3
        IF @V_DT_INTRUST = 1 --启用分布式
        BEGIN
            INSERT INTO #TEMPCUST3
                SELECT CUST_ID,SUM(ISNULL(TO_AMOUNT,0))
                    FROM SRV_Intrust.INTRUST.dbo.TBENIFITOR WHERE CHECK_FLAG = 2 AND PRODUCT_ID = @IN_PRODUCT_ID GROUP BY CUST_ID
        END
        ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
        BEGIN
            INSERT INTO #TEMPCUST3
                SELECT CUST_ID,SUM(ISNULL(TO_AMOUNT,0))
                    FROM INTRUST.dbo.TBENIFITOR WHERE CHECK_FLAG = 2 AND PRODUCT_ID = @IN_PRODUCT_ID GROUP BY CUST_ID
        END
        UPDATE #TEMPCUST
            SET CURR_TO_AMOUNT = B.TO_AMOUNT
            FROM #TEMPCUST A,#TEMPCUST3 B
            WHERE A.CUST_ID = B.CUST_ID
    END
    --20100302 dongyg EFCRM.TCustomers.TOTAL_MONEY/CURRENT_MONEY 的值未根据业务发生而变化，需要根据 INTRUST.TCUSTOMERINFO.TOTAL_MONEY/CURRENT_MONEY 修正
    IF @V_DT_INTRUST = 1 --启用分布式
    BEGIN
        UPDATE #TEMPCUST
            SET TOTAL_MONEY = B.TOTAL_MONEY, CURRENT_MONEY = B.CURRENT_MONEY, CUST_LEVEL = B.CUST_LEVEL
            FROM #TEMPCUST A, SRV_Intrust.INTRUST.dbo.TCUSTOMERINFO B
            WHERE A.CUST_ID = B.CUST_ID
		UPDATE #TEMPCUST
            SET SERVICE_MAN_NAME = B.OP_NAME
            FROM #TEMPCUST A, SRV_Intrust.INTRUST.dbo.TOPERATOR B
            WHERE A.SERVICE_MAN = B.OP_CODE
    END
    ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
    BEGIN
        UPDATE #TEMPCUST
            SET TOTAL_MONEY = B.TOTAL_MONEY, CURRENT_MONEY = B.CURRENT_MONEY, CUST_LEVEL = B.CUST_LEVEL
            FROM #TEMPCUST A, INTRUST..TCUSTOMERINFO B
            WHERE A.CUST_ID = B.CUST_ID
    END
    --20110531 dongyg 统计的已购买金额、受益份额、存量金额，将非信托产品计算在内（非信托产品的受益份额不在份额权限控制机制内）
--20111107 dongyg 北国投需求："综合查询"中统计的已购买金额、受益份额、存量金额，将非信托产品不计算在内.
--    UPDATE #TEMPCUST SET BEN_AMOUNT = A.BEN_AMOUNT + B.QUOTIENT_AMOUNT
--        FROM #TEMPCUST A, (SELECT CUST_ID,SUM(QUOTIENT_AMOUNT) AS QUOTIENT_AMOUNT FROM TQUOTIENT GROUP BY CUST_ID) B
--        WHERE A.CUST_ID = B.CUST_ID
--    UPDATE #TEMPCUST SET TOTAL_MONEY = A.TOTAL_MONEY + B.SUBSCRIBE_MONEY
--        FROM #TEMPCUST A, (SELECT CUST_ID,SUM(SUBSCRIBE_MONEY) AS SUBSCRIBE_MONEY FROM TSUBSCRIBE GROUP BY CUST_ID) B
--        WHERE A.CUST_ID = B.CUST_ID
--    UPDATE #TEMPCUST SET CURRENT_MONEY = A.CURRENT_MONEY + B.QUOTIENT_MONEY
--        FROM #TEMPCUST A, (SELECT CUST_ID,SUM(QUOTIENT_MONEY) AS QUOTIENT_MONEY FROM TQUOTIENT GROUP BY CUST_ID) B
--        WHERE A.CUST_ID = B.CUST_ID
    --增加当前份额（不含到期的 2011-10-09）
    UPDATE #TEMPCUST SET END_AMOUNT = TOTAL_MONEY-BEN_AMOUNT

--该段功能给谁做的未知，dbo.GETCUSTRIGHT 函数中使用的表，涉及的菜单已注释
    --如果有客户访问权限控制，则执行客户信息加密
    IF EXISTS(SELECT 1 FROM TSYSCONTROL WHERE FLAG_TYPE = N'CUSTRIGHT' AND VALUE = 1) AND (@V_FLAG_ACCESS_ALL = 0)
    BEGIN
       --修改有有权限的显示
        UPDATE #TEMPCUST
            SET MODI_FLAG = dbo.GETCUSTRIGHT(SERVICE_MAN,@IN_INPUT_MAN)
            WHERE SERVICE_MAN IN (SELECT ENABLE_OP_CODE FROM TOPCUSTRIGHT WHERE OP_CODE = @IN_INPUT_MAN)
                        OR SERVICE_MAN = @IN_INPUT_MAN

        UPDATE #TEMPCUST
            SET CARD_ID=replace('******',CARD_ID,CARD_ID),
                CUST_TEL=replace('******',CUST_TEL,CUST_TEL),
                E_MAIL=replace('******',E_MAIL,E_MAIL),
                MOBILE=replace('******',MOBILE,MOBILE),
                BP=replace('******',BP,BP)
            WHERE MODI_FLAG <>1
        --删除没有权限的客户
        DELETE FROM #TEMPCUST WHERE MODI_FLAG = 0
    END

    -- 修改资金来源名称 国别名称
    UPDATE #TEMPCUST
    SET COUNTRY_NAME = (SELECT B.TYPE_CONTENT FROM TDICTPARAM B WHERE B.TYPE_VALUE = COUNTRY)
    --开关控制 加密重要联系信息
    IF(@V_FLAG_ENCRYPTION = 1)
    BEGIN
        UPDATE #TEMPCUST
        SET --CARD_ID = ISNULL(stuff(CARD_ID,1,len(CARD_ID)-4,'******'),'****'),
            CUST_TEL = ISNULL(stuff(CUST_TEL,1,len(CUST_TEL)-4,'******'),'****'),
            O_TEL = ISNULL(stuff(O_TEL,1,len(O_TEL)-4,'******'),'****'),
            E_MAIL = ISNULL(stuff(E_MAIL,1,charindex('@',E_MAIL)-1,'******'),''),
            MOBILE = ISNULL(stuff(MOBILE,1,len(MOBILE)-4,'******'),'****'),
            BP = ISNULL(stuff(BP,1,len(BP)-4,'******'),'****')
    END
    --20110613 DONGYG 添加客户首次认购、末次认购日期及过滤
    IF @V_DT_INTRUST = 1 --启用分布式
    BEGIN
        UPDATE #TEMPCUST
            SET FIRST_RG_DATE = B.FIRST_RG_DATE
            FROM #TEMPCUST A, (SELECT CUST_ID, MIN(START_DATE) AS FIRST_RG_DATE FROM SRV_Intrust.INTRUST.dbo.TCONTRACT GROUP BY CUST_ID) B
            WHERE A.CUST_ID = B.CUST_ID
        UPDATE #TEMPCUST
            SET LAST_RG_DATE = B.LAST_RG_DATE
            FROM #TEMPCUST A, (SELECT CUST_ID, MAX(START_DATE) AS LAST_RG_DATE FROM SRV_Intrust.INTRUST.dbo.TCONTRACT GROUP BY CUST_ID) B
            WHERE A.CUST_ID = B.CUST_ID
    END
    ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
    BEGIN
        UPDATE #TEMPCUST
            SET FIRST_RG_DATE = B.FIRST_RG_DATE
            FROM #TEMPCUST A, (SELECT CUST_ID, MIN(START_DATE) AS FIRST_RG_DATE FROM INTRUST..TCONTRACT GROUP BY CUST_ID) B
            WHERE A.CUST_ID = B.CUST_ID
        UPDATE #TEMPCUST
            SET LAST_RG_DATE = B.LAST_RG_DATE
            FROM #TEMPCUST A, (SELECT CUST_ID, MAX(START_DATE) AS LAST_RG_DATE FROM INTRUST..TCONTRACT GROUP BY CUST_ID) B
            WHERE A.CUST_ID = B.CUST_ID
    END
    SELECT A.*, CASE A.CUST_TYPE WHEN 1 THEN N'个人' WHEN 2 THEN N'机构' END AS CUST_TYPE_NAME, B.AGE
        INTO #TEMP_QUERY_TCustomersExt
        FROM #TEMPCUST A, TCustomers B
        WHERE A.CUST_ID = B.CUST_ID AND (ISNULL(@IN_BEN_AMOUNT_MIN,0) = 0 OR A.BEN_AMOUNT >= ISNULL(@IN_BEN_AMOUNT_MIN,0))
          AND (ISNULL(@IN_BEN_AMOUNT_MAX,0) = 0 OR A.BEN_AMOUNT <= ISNULL(@IN_BEN_AMOUNT_MAX,0))
          AND (ISNULL(A.FIRST_RG_DATE,0) BETWEEN ISNULL(@IN_RG_BEGIN_DATE,0) AND ISNULL(@IN_RG_END_DATE,30001231)
            OR ISNULL(A.LAST_RG_DATE,0) BETWEEN ISNULL(@IN_RG_BEGIN_DATE,0) AND ISNULL(@IN_RG_END_DATE,30001231) )
          AND (A.CUST_LEVEL = @IN_CUST_LEVEL OR @IN_CUST_LEVEL IS NULL OR @IN_CUST_LEVEL = '')
    SET @V_SQL = N'
        SELECT A.*, B.CLASS10, B.CLASS11, B.CLASS12 AS CUST_LEVEL_NAME
        FROM #TEMP_QUERY_TCustomersExt A LEFT JOIN (SELECT CUST_ID,MAX([10]) AS CLASS10, MAX([11]) AS CLASS11, MAX([12]) AS CLASS12
                            FROM TCustomerClass PIVOT(MAX(CLASSDETAIL_NAME) FOR CLASSDEFINE_ID IN ([10],[11],[12])) AS Z
                            GROUP BY CUST_ID) AS B ON A.CUST_ID = B.CUST_ID WHERE 1=1'
    IF @IN_CLASSES <> ''
        SET @V_SQL = @V_SQL + ' AND EXISTS(SELECT 1 FROM #TMP_QUERY_CustByClass Z WHERE A.CUST_ID = Z.CUST_ID)'

    IF ISNULL(@IN_ORDERBY,'') <> ''
    BEGIN
        SET @V_SQL = @V_SQL + ' ORDER BY ' + @IN_ORDERBY
    END
    ELSE
    BEGIN
        SET @V_SQL = @V_SQL + ' ORDER BY A.CURRENT_MONEY DESC'
    END
    EXECUTE( @V_SQL )
GO
