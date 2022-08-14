﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE SP_QUERY_TBENIFITOR  @IN_BOOK_CODE        INT,
                                     @IN_SERIAL_NO        INT,
                                     @IN_PRODUCT_ID       INT,
                                     @IN_CONTRACT_BH      VARCHAR(16),
                                     @IN_CONTRACT_SUB_BH  VARCHAR(50),
                                     @IN_SY_CUST_NO       VARCHAR(8),
                                     @IN_SY_CARD_ID       VARCHAR(40),
                                     @IN_SY_CUST_NAME     VARCHAR(100),
                                     @IN_CUST_TYPE        INT,
                                     @IN_PROV_LEVEL       VARCHAR(10),
                                     @IN_BEN_STATUS       VARCHAR(10),
                                     @IN_INPUT_MAN        INT,
                                     @IN_EXPORT_FLAG      INT= 0,       --导出标记：0非导出查询 1导出受益人信息
                                     @IN_EXPORT_SUMMARY   NVARCHAR(900) = 0,  --导出备注
									 @IN_TEAM_ID          INT     = 0 --团队ID
WITH ENCRYPTION
AS
--20409: The Products can be accessed. START
    DECLARE @V_SW20409 INT,@V_INTRUST_INPUT_MAN INT
    DECLARE @V_PRODUCTS TABLE(PRODUCT_ID INT)
	DECLARE @V_FLAG_ACCESS_ALL INT, @V_FLAG_ENCRYPTION INT,@V_QUERYCUSTOMERS INT,@V_ENCRYPTION_SIZE INT
	DECLARE @IN_NODE_SHARE INT,@V_USER_ID INT,@V_CHANGE_TIMES INT

    SELECT @V_SW20409 = VALUE FROM INTRUST..TSYSCONTROL WHERE FLAG_TYPE = 'SW20409'
	SELECT @V_INTRUST_INPUT_MAN=@IN_INPUT_MAN--INTRUST_Operator FROM TOPERATOR_MAP WHERE CRM_Operator=@IN_INPUT_MAN AND INTRUST_BOOKCODE=1
    
	  --能够访问所有客户信息
    SELECT @V_FLAG_ACCESS_ALL = 0 --访问全部标志
    SELECT @V_FLAG_ENCRYPTION = 0 --加密标志
    SELECT @V_QUERYCUSTOMERS = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'QUERYCUSTOMERS'
    SELECT @V_USER_ID = USER_ID FROM TSYSTEMINFO
    IF @V_QUERYCUSTOMERS IS NULL SET @V_QUERYCUSTOMERS = 1
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

    IF (@V_USER_ID = 8) AND EXISTS(SELECT 1 FROM TOPROLE WHERE OP_CODE = @IN_INPUT_MAN AND ROLE_ID = 5)--如果用户存在打印角色的时候 不需要加密
    BEGIN
        SET @V_FLAG_ENCRYPTION = 2
    END
    ELSE
    BEGIN
        --如果操作员的角色中存在保密限制的角色，则根据保密优先的原则，则进行保密限制
        IF EXISTS(SELECT 1 FROM TOPROLE WHERE OP_CODE = @IN_INPUT_MAN AND ROLE_ID IN(SELECT ROLE_ID FROM TROLE WHERE FLAG_ENCRYPTION = 1))
            SELECT @V_FLAG_ENCRYPTION = 1
    END
	--加密显示位数	
	IF EXISTS(SELECT 1 FROM TDICTPARAM WHERE TYPE_VALUE = '800701')
		SELECT @V_ENCRYPTION_SIZE = CAST(TYPE_CONTENT AS INT) FROM TDICTPARAM WHERE TYPE_VALUE = '800701'
	ELSE
		SET  @V_ENCRYPTION_SIZE = 4

    --SELECT @V_CUSTQZ0001 = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'CUSTQZ0001' --默认1
    --------------------------------------------------------------------
    DECLARE @V_TEMPCUST1 TABLE(CUST_ID INT) --根据输入条件能够访问的客户，根据查询条件 AND
    DECLARE @V_TEMPCUST2 TABLE(CUST_ID INT) --根据访问权限能够访问的客户，根据访问权限 OR
    DECLARE @V_INPUT_MAN_TEAM_ID INT    --操作员的团队ID
    DECLARE @V_FACT_POTE_FLAG INT ,@V_IS_DEAL INT
    SELECT @V_INPUT_MAN_TEAM_ID = TEAM_ID FROM TMANAGERTEAMMEMBERS WHERE MANAGERID = @IN_INPUT_MAN

    SELECT @V_FACT_POTE_FLAG = VALUE FROM TSYSCONTROL WHERE  FLAG_TYPE = 'FACT_POTENTIAL_FLAG'--事实潜在客户加密条件

    IF @V_FACT_POTE_FLAG = 1  SET @V_IS_DEAL = 2 -- 潜在客户信息加密
    IF @V_FACT_POTE_FLAG = 2  SET @V_IS_DEAL = 1 -- 事实客户信息加密
    IF @V_FACT_POTE_FLAG = 3  SET @V_IS_DEAL = 0 -- 事实、潜在客户信息加密
    IF @V_FACT_POTE_FLAG = 4  SET @V_IS_DEAL = 3 -- 事实、潜在客户信息都不加密
	
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
    --处理快捷授权的客户ShareType=3
    INSERT INTO @V_TEMPCUST2(CUST_ID)
        SELECT CUST_ID FROM TAuthorizationShare A WHERE ShareType = 3 AND Status = 1 AND (GETDATE() BETWEEN ISNULL(A.START_TIME,GETDATE()) AND ISNULL(A.INVALID_TIME,GETDATE()+36000)) AND ManagerID = @IN_INPUT_MAN
            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)
        UNION ALL
        SELECT B.CUST_ID FROM TAuthorizationShare A, TCustomers B
        WHERE A.SourceManagerID = B.SERVICE_MAN AND A.ShareType = 3 AND A.Status = 1 AND A.CUST_ID = 0 AND (GETDATE() BETWEEN ISNULL(A.START_TIME,GETDATE()) AND ISNULL(A.INVALID_TIME,GETDATE()+36000)) AND A.ManagerID = @IN_INPUT_MAN
            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)
    --以上客户经理的客户
    INSERT INTO @V_TEMPCUST2
        SELECT DISTINCT CUST_ID FROM TCustomers  A
        WHERE EXISTS( SELECT 1 FROM @V_MANAGER_IDS B WHERE A.SERVICE_MAN = B.MANAGERID)
                OR EXISTS( SELECT 1 FROM @V_MANAGER_IDS C WHERE (@V_QUERYCUSTOMERS = 1 AND A.INPUT_MAN = C.MANAGERID) )
            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)
    --20110610 dongyg 按"产品客户经理"控制访问权限
    IF EXISTS(SELECT 1 FROM TSYSCONTROL WHERE FLAG_TYPE = 'PRODUCT001' AND VALUE = 1)
    BEGIN
        INSERT INTO @V_TEMPCUST2
            SELECT DISTINCT A.CUST_ID FROM INTRUST..TBENIFITOR A, INTRUST..TPRODUCT B WHERE A.PRODUCT_ID = B.PRODUCT_ID AND B.SERVICE_MAN = @V_INTRUST_INPUT_MAN
    END
	
    IF @V_SW20409 = 1
    INSERT INTO @V_PRODUCTS
        SELECT PRODUCT_ID FROM INTRUST..TPRODUCT WHERE BOOK_CODE = @IN_BOOK_CODE
            AND(INTRUST_FLAG1 IN ( SELECT FUNC_ID-2040900 FROM INTRUST..TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID > 2040900 AND FUNC_ID <= 2040999 AND OP_CODE = @V_INTRUST_INPUT_MAN )
                OR PRODUCT_ID IN ( SELECT FUNC_ID FROM INTRUST..TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID < 2040900 AND OP_CODE = @V_INTRUST_INPUT_MAN)
                OR ADMIN_MANAGER = @V_INTRUST_INPUT_MAN OR INPUT_MAN = @V_INTRUST_INPUT_MAN OR ISNULL(@IN_INPUT_MAN,0)=0
                OR ADMIN_MANAGER IN(SELECT OP_CODE FROM INTRUST..TOPCUSTRIGHT WHERE ENABLE_OP_CODE = @V_INTRUST_INPUT_MAN))
    --20409: The Products can be accessed. END
    --懒人创建临时表
	SELECT A.SERIAL_NO, B.PRODUCT_ID, B.PRODUCT_NAME, D.CONTRACT_BH, D.CONTRACT_SUB_BH, A.LIST_ID,C.CUST_ID,C.CUST_NO,C.CUST_NAME, A.TO_AMOUNT, A.BEN_AMOUNT,
           A.FROZEN_MONEY, A.BEN_DATE,A.BEN_END_DATE,A.BEN_STATUS_NAME,CASE A.IS_TRANSFERENTIAL WHEN 1 THEN 0 ELSE D.RG_MONEY END AS TO_MONEY,
           A.BANK_ID,A.BANK_NAME,A.BANK_SUB_NAME,A.BANK_ACCT,A.CUST_ACCT_NAME,A.TEMP_ACCT_NAME,A.BONUS_FLAG,
           A.SUB_PRODUCT_ID,ISNULL(A.PROV_FLAG,1) AS PROV_FLAG,A.PROV_LEVEL,A.PROV_LEVEL_NAME,E.CONTENT AS PROV_FLAG_NAME,
           C.CUST_TYPE,C.TOUCH_TYPE,C.TOUCH_TYPE_NAME,C.CARD_TYPE,C.CARD_TYPE_NAME,C.CARD_ID,C.POST_ADDRESS,C.POST_ADDRESS2,
           C.POST_CODE,C.POST_CODE2,C.LEGAL_MAN,C.H_TEL,C.O_TEL,C.MOBILE,C.E_MAIL,C.BP,C.CUST_TYPE_NAME,A.JK_TYPE_NAME,
           D.QS_DATE,D.JK_DATE,D.SUB_PRODUCT_ID AS WT_SUB_PRODUCT_ID,D.FEE_JK_TYPE,C.E_MAIL AS WT_E_MAIL,
           C.CUST_NAME AS WT_CUST_NAME,C.CUST_TYPE_NAME AS WT_CUST_TYPE_NAME,C.POST_CODE AS WT_POST_CODE,C.POST_CODE2 AS WT_POST_CODE2,
           C.POST_ADDRESS AS WT_POST_ADDRESS,C.POST_ADDRESS2 AS WT_POST_ADDRESS2,C.CARD_TYPE_NAME AS WT_CARD_TYPE_NAME,C.CARD_ID AS WT_CARD_ID,
           C.LEGAL_MAN AS WT_LWGAL_MAN,C.TOUCH_TYPE_NAME AS WT_TOUCH_TYPE_NAME,C.O_TEL AS WT_O_TEL,C.H_TEL AS WT_H_TEL,C.MOBILE AS WT_MOBILE,
		   C.SERVICE_MAN,F.OP_NAME AS SERVICE_MAN_NAME,C.IS_DEAL, A.SUMMARY,CAST('' AS NVARCHAR(40)) MEND_CHECK_FLAG
		   ,D.HT_CUST_NAME,D.HT_CUST_TEL,D.HT_CUST_ADDRESS,CAST('' AS NVARCHAR(40)) SERVICE_MAN_SALES,A.ACCT_CHG_REASON,A.TEMP_BONUS_FLAG
		   ,A.BANK_PROVINCE,A.BANK_PROVINCE_NAME,A.BANK_CITY,A.BANK_CITY_NAME,A.JK_TYPE
		   ,A.TEMP_BANK_PROVINCE,A.TEMP_BANK_CITY,A.TEMP_JK_TYPE,A.TEMP_JK_TYPE_NAME
	INTO #TEMP_QUERY_TBENIFITER_VALUE
	FROM INTRUST..TBENIFITOR A, INTRUST..TPRODUCT B, 
		 TCustomers C LEFT JOIN TOPERATOR F ON C.SERVICE_MAN = F.OP_CODE,
		 INTRUST..TCONTRACT D,INTRUST..TINTEGERPARAM E
	WHERE 1=0
	
    IF ISNULL(@IN_SERIAL_NO,0) <> 0
    BEGIN
    	--统计受益账户已变更的次数
		SELECT @V_CHANGE_TIMES=COUNT(0) FROM INTRUST..TMODIBENACCTDETAIL WHERE BEN_SERIAL_NO=@IN_SERIAL_NO
		INSERT INTO #TEMP_QUERY_TBENIFITER_VALUE
        SELECT A.SERIAL_NO, B.PRODUCT_ID, B.PRODUCT_NAME, D.CONTRACT_BH, D.CONTRACT_SUB_BH, A.LIST_ID,C.CUST_ID,C.CUST_NO,C.CUST_NAME, A.TO_AMOUNT, A.BEN_AMOUNT,
               A.FROZEN_MONEY, A.BEN_DATE,A.BEN_END_DATE, A.BEN_STATUS_NAME,CASE A.IS_TRANSFERENTIAL WHEN 1 THEN 0 ELSE D.RG_MONEY END AS TO_MONEY,
               A.BANK_ID,A.BANK_NAME,A.BANK_SUB_NAME,A.BANK_ACCT,A.CUST_ACCT_NAME,A.TEMP_ACCT_NAME,A.BONUS_FLAG,
               A.SUB_PRODUCT_ID,ISNULL(A.PROV_FLAG,1) AS PROV_FLAG,A.PROV_LEVEL,A.PROV_LEVEL_NAME,E.CONTENT AS PROV_FLAG_NAME,
               C.CUST_TYPE,C.TOUCH_TYPE,C.TOUCH_TYPE_NAME,C.CARD_TYPE,C.CARD_TYPE_NAME,C.CARD_ID,C.POST_ADDRESS,C.POST_ADDRESS2,
               C.POST_CODE,C.POST_CODE2,C.LEGAL_MAN,C.H_TEL,C.O_TEL,C.MOBILE,C.E_MAIL,C.BP,C.CUST_TYPE_NAME,A.JK_TYPE_NAME,
               D.QS_DATE,D.JK_DATE,D.SUB_PRODUCT_ID AS WT_SUB_PRODUCT_ID,D.FEE_JK_TYPE,C.E_MAIL AS WT_E_MAIL,
               C.CUST_NAME AS WT_CUST_NAME,C.CUST_TYPE_NAME AS WT_CUST_TYPE_NAME,C.POST_CODE AS WT_POST_CODE,C.POST_CODE2 AS WT_POST_CODE2,
               C.POST_ADDRESS AS WT_POST_ADDRESS,C.POST_ADDRESS2 AS WT_POST_ADDRESS2,C.CARD_TYPE_NAME AS WT_CARD_TYPE_NAME,C.CARD_ID AS WT_CARD_ID,
               C.LEGAL_MAN AS WT_LWGAL_MAN,C.TOUCH_TYPE_NAME AS WT_TOUCH_TYPE_NAME,C.O_TEL AS WT_O_TEL,C.H_TEL AS WT_H_TEL,C.MOBILE AS WT_MOBILE,
			   C.SERVICE_MAN,F.OP_NAME AS SERVICE_MAN_NAME,C.IS_DEAL, A.SUMMARY,D.MEND_CHECK_FLAG
			   ,D.HT_CUST_NAME,D.HT_CUST_TEL,D.HT_CUST_ADDRESS,(SELECT OP_NAME FROM TOPERATOR WHERE OP_CODE=D.SERVICE_MAN_SALES),A.ACCT_CHG_REASON,A.TEMP_BONUS_FLAG
			   ,A.BANK_PROVINCE,A.BANK_PROVINCE_NAME,A.BANK_CITY,A.BANK_CITY_NAME,A.JK_TYPE
		   ,A.TEMP_BANK_PROVINCE,A.TEMP_BANK_CITY,A.TEMP_JK_TYPE,A.TEMP_JK_TYPE_NAME
		FROM INTRUST..TBENIFITOR A, INTRUST..TPRODUCT B, 
			TCustomers C LEFT JOIN TOPERATOR F ON C.SERVICE_MAN = F.OP_CODE,
			INTRUST..TCONTRACT D,INTRUST..TINTEGERPARAM E
        WHERE A.PRODUCT_ID = B.PRODUCT_ID AND A.CUST_ID = C.CUST_ID
            AND A.PRODUCT_ID = D.PRODUCT_ID AND A.CONTRACT_BH = D.CONTRACT_BH
            AND A.SERIAL_NO = @IN_SERIAL_NO AND ISNULL(A.PROV_FLAG,1) = E.TYPE_VALUE AND E.TYPE_ID = 3003
    END
    ELSE
    BEGIN
        IF ISNULL(@IN_BEN_STATUS,'') = ''
			INSERT INTO #TEMP_QUERY_TBENIFITER_VALUE
            SELECT A.SERIAL_NO, B.PRODUCT_ID, B.PRODUCT_NAME, D.CONTRACT_BH, D.CONTRACT_SUB_BH, A.LIST_ID,C.CUST_ID,C.CUST_NO,C.CUST_NAME, A.TO_AMOUNT, A.BEN_AMOUNT,
                   A.FROZEN_MONEY, A.BEN_DATE,A.BEN_END_DATE, A.BEN_STATUS_NAME,CASE A.IS_TRANSFERENTIAL WHEN 1 THEN 0 ELSE D.RG_MONEY END AS TO_MONEY,
                   A.BANK_ID,A.BANK_NAME,A.BANK_SUB_NAME,A.BANK_ACCT,A.CUST_ACCT_NAME,A.TEMP_ACCT_NAME,A.BONUS_FLAG,
                   A.SUB_PRODUCT_ID,ISNULL(A.PROV_FLAG,1) AS PROV_FLAG,A.PROV_LEVEL,A.PROV_LEVEL_NAME,E.CONTENT AS PROV_FLAG_NAME,
                   C.CUST_TYPE,C.TOUCH_TYPE,C.TOUCH_TYPE_NAME,C.CARD_TYPE,C.CARD_TYPE_NAME,C.CARD_ID,C.POST_ADDRESS,C.POST_ADDRESS2,
                   C.POST_CODE,C.POST_CODE2,C.LEGAL_MAN,C.H_TEL,C.O_TEL,C.MOBILE,C.E_MAIL,C.BP,C.CUST_TYPE_NAME,A.JK_TYPE_NAME,
                   D.QS_DATE,D.JK_DATE,D.SUB_PRODUCT_ID AS WT_SUB_PRODUCT_ID,D.FEE_JK_TYPE,C.E_MAIL AS WT_E_MAIL,
                   C.CUST_NAME AS WT_CUST_NAME,C.CUST_TYPE_NAME AS WT_CUST_TYPE_NAME,C.POST_CODE AS WT_POST_CODE,C.POST_CODE2 AS WT_POST_CODE2,
                   C.POST_ADDRESS AS WT_POST_ADDRESS,C.POST_ADDRESS2 AS WT_POST_ADDRESS2,C.CARD_TYPE_NAME AS WT_CARD_TYPE_NAME,C.CARD_ID AS WT_CARD_ID,
                   C.LEGAL_MAN AS WT_LWGAL_MAN,C.TOUCH_TYPE_NAME AS WT_TOUCH_TYPE_NAME,C.O_TEL AS WT_O_TEL,C.H_TEL AS WT_H_TEL,C.MOBILE AS WT_MOBILE,
				   C.SERVICE_MAN,F.OP_NAME AS SERVICE_MAN_NAME,C.IS_DEAL, A.SUMMARY, CASE ISNULL(M.DZ_COUNT,0) WHEN 0 THEN '不必审核' ELSE CASE D.MEND_CHECK_FLAG WHEN 1 THEN '未审核' WHEN 2 THEN '已审核' ELSE '其他' END END MEND_CHECK_FLAG
				   ,D.HT_CUST_NAME,D.HT_CUST_TEL,D.HT_CUST_ADDRESS,(SELECT OP_NAME FROM TOPERATOR WHERE OP_CODE=D.SERVICE_MAN_SALES),A.ACCT_CHG_REASON,A.TEMP_BONUS_FLAG
				   ,A.BANK_PROVINCE,A.BANK_PROVINCE_NAME,A.BANK_CITY,A.BANK_CITY_NAME,A.JK_TYPE
		   ,A.TEMP_BANK_PROVINCE,A.TEMP_BANK_CITY,A.TEMP_JK_TYPE,A.TEMP_JK_TYPE_NAME
			FROM INTRUST..TBENIFITOR A, INTRUST..TPRODUCT B,
				TCustomers C LEFT JOIN TOPERATOR F ON C.SERVICE_MAN = F.OP_CODE, 
				INTRUST..TCONTRACT D LEFT JOIN (SELECT CUST_ID,PRODUCT_ID,SUB_PRODUCT_ID,COUNT(0) DZ_COUNT FROM INTRUST..TMONEYDATA GROUP BY CUST_ID,PRODUCT_ID,SUB_PRODUCT_ID) M ON D.CUST_ID=M.CUST_ID AND D.PRODUCT_ID=M.PRODUCT_ID AND ISNULL(D.SUB_PRODUCT_ID,0)=ISNULL(M.SUB_PRODUCT_ID,0),
				INTRUST..TINTEGERPARAM E
            WHERE A.PRODUCT_ID = B.PRODUCT_ID AND A.CUST_ID = C.CUST_ID AND A.PRODUCT_ID = D.PRODUCT_ID AND A.CONTRACT_BH = D.CONTRACT_BH
                AND A.BOOK_CODE = @IN_BOOK_CODE
                AND(ISNULL(@IN_PRODUCT_ID,0) = 0 OR A.PRODUCT_ID = @IN_PRODUCT_ID)
                AND(@V_SW20409 = 0 OR @V_SW20409 IS NULL OR A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCTS))
                AND(ISNULL(@IN_CONTRACT_BH,'') = '' OR A.CONTRACT_BH = @IN_CONTRACT_BH)
                AND (D.CONTRACT_SUB_BH LIKE '%'+@IN_CONTRACT_SUB_BH+'%' OR @IN_CONTRACT_SUB_BH IS NULL OR @IN_CONTRACT_SUB_BH = '')
				AND (ISNULL(@IN_SY_CUST_NO,'') = '' OR C.CUST_NO LIKE '%'+@IN_SY_CUST_NO+'%')
                AND (ISNULL(@IN_SY_CUST_NAME,'') = '' OR C.CUST_NAME LIKE '%'+@IN_SY_CUST_NAME+'%')
                AND (ISNULL(@IN_PROV_LEVEL,'')='' OR A.PROV_LEVEL=@IN_PROV_LEVEL)
                AND (ISNULL(@IN_SY_CARD_ID,'') = '' OR C.CARD_ID LIKE @IN_SY_CARD_ID)
                --AND ((A.BEN_STATUS NOT IN('121103','121106')) OR (A.BEN_STATUS IN('121103','121106','121104') AND A.BEN_AMOUNT <> 0))
                AND ISNULL(A.PROV_FLAG,1) = E.TYPE_VALUE AND E.TYPE_ID = 3003
                AND (ISNULL(@IN_CUST_TYPE,0)=0 OR C.CUST_TYPE=@IN_CUST_TYPE)
        ELSE
        	INSERT INTO #TEMP_QUERY_TBENIFITER_VALUE
            SELECT A.SERIAL_NO, B.PRODUCT_ID, B.PRODUCT_NAME, D.CONTRACT_BH, D.CONTRACT_SUB_BH, A.LIST_ID ,C.CUST_ID,C.CUST_NO,C.CUST_NAME, A.TO_AMOUNT, A.BEN_AMOUNT,
                   A.FROZEN_MONEY, A.BEN_DATE,A.BEN_END_DATE, A.BEN_STATUS_NAME,CASE A.IS_TRANSFERENTIAL WHEN 1 THEN 0 ELSE D.RG_MONEY END AS TO_MONEY,
                   A.BANK_ID,A.BANK_NAME,A.BANK_SUB_NAME,A.BANK_ACCT,A.CUST_ACCT_NAME,A.TEMP_ACCT_NAME,A.BONUS_FLAG,
                   A.SUB_PRODUCT_ID,ISNULL(A.PROV_FLAG,1) AS PROV_FLAG,A.PROV_LEVEL,A.PROV_LEVEL_NAME,E.CONTENT AS PROV_FLAG_NAME,
                   C.CUST_TYPE,C.TOUCH_TYPE,C.TOUCH_TYPE_NAME,C.CARD_TYPE,C.CARD_TYPE_NAME,C.CARD_ID,C.POST_ADDRESS,C.POST_ADDRESS2,
                   C.POST_CODE,C.POST_CODE2,C.LEGAL_MAN,C.H_TEL,C.O_TEL,C.MOBILE,C.E_MAIL,C.BP,C.CUST_TYPE_NAME,A.JK_TYPE_NAME,
                   D.QS_DATE,D.JK_DATE,D.SUB_PRODUCT_ID AS WT_SUB_PRODUCT_ID,D.FEE_JK_TYPE,C.E_MAIL AS WT_E_MAIL,
                   C.CUST_NAME AS WT_CUST_NAME,C.CUST_TYPE_NAME AS WT_CUST_TYPE_NAME,C.POST_CODE AS WT_POST_CODE,C.POST_CODE2 AS WT_POST_CODE2,
                   C.POST_ADDRESS AS WT_POST_ADDRESS,C.POST_ADDRESS2 AS WT_POST_ADDRESS2,C.CARD_TYPE_NAME AS WT_CARD_TYPE_NAME,C.CARD_ID AS WT_CARD_ID,
                   C.LEGAL_MAN AS WT_LWGAL_MAN,C.TOUCH_TYPE_NAME AS WT_TOUCH_TYPE_NAME,C.O_TEL AS WT_O_TEL,C.H_TEL AS WT_H_TEL,C.MOBILE AS WT_MOBILE,
				   C.SERVICE_MAN,F.OP_NAME AS SERVICE_MAN_NAME,C.IS_DEAL, A.SUMMARY, CASE ISNULL(M.DZ_COUNT,0) WHEN 0 THEN '不必审核' ELSE CASE D.MEND_CHECK_FLAG WHEN 1 THEN '未审核' WHEN 2 THEN '已审核' ELSE '其他' END END MEND_CHECK_FLAG
				   ,D.HT_CUST_NAME,D.HT_CUST_TEL,D.HT_CUST_ADDRESS,(SELECT OP_NAME FROM TOPERATOR WHERE OP_CODE=D.SERVICE_MAN_SALES),A.ACCT_CHG_REASON,A.TEMP_BONUS_FLAG
				   ,A.BANK_PROVINCE,A.BANK_PROVINCE_NAME,A.BANK_CITY,A.BANK_CITY_NAME,A.JK_TYPE
		   ,A.TEMP_BANK_PROVINCE,A.TEMP_BANK_CITY,A.TEMP_JK_TYPE,A.TEMP_JK_TYPE_NAME
            FROM INTRUST..TBENIFITOR A, INTRUST..TPRODUCT B,
				TCustomers C LEFT JOIN TOPERATOR F ON C.SERVICE_MAN = F.OP_CODE, 
				INTRUST..TCONTRACT D LEFT JOIN (SELECT CUST_ID,PRODUCT_ID,SUB_PRODUCT_ID,COUNT(0) DZ_COUNT FROM INTRUST..TMONEYDATA GROUP BY CUST_ID,PRODUCT_ID,SUB_PRODUCT_ID) M ON D.CUST_ID=M.CUST_ID AND D.PRODUCT_ID=M.PRODUCT_ID AND ISNULL(D.SUB_PRODUCT_ID,0)=ISNULL(M.SUB_PRODUCT_ID,0),
				INTRUST..TINTEGERPARAM E
            WHERE A.PRODUCT_ID = B.PRODUCT_ID AND A.CUST_ID = C.CUST_ID AND A.PRODUCT_ID = D.PRODUCT_ID AND A.CONTRACT_BH = D.CONTRACT_BH
                AND A.BOOK_CODE = @IN_BOOK_CODE
                AND(ISNULL(@IN_PRODUCT_ID,0) = 0 OR A.PRODUCT_ID = @IN_PRODUCT_ID)
                AND(@V_SW20409 = 0 OR @V_SW20409 IS NULL OR A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCTS))
                AND(ISNULL(@IN_CONTRACT_BH,'') = '' OR A.CONTRACT_BH = @IN_CONTRACT_BH)
                AND (D.CONTRACT_SUB_BH LIKE '%'+@IN_CONTRACT_SUB_BH+'%' OR @IN_CONTRACT_SUB_BH IS NULL OR @IN_CONTRACT_SUB_BH = '')
				AND (ISNULL(@IN_SY_CUST_NO,'') = '' OR C.CUST_NO LIKE '%'+@IN_SY_CUST_NO+'%')
                AND (ISNULL(@IN_SY_CUST_NAME,'') = '' OR C.CUST_NAME LIKE '%'+@IN_SY_CUST_NAME+'%')
                AND (ISNULL(@IN_PROV_LEVEL,'')='' OR A.PROV_LEVEL=@IN_PROV_LEVEL)
                AND (ISNULL(@IN_SY_CARD_ID,'') = '' OR C.CARD_ID LIKE @IN_SY_CARD_ID)
                AND (A.BEN_STATUS = @IN_BEN_STATUS)
                AND ISNULL(A.PROV_FLAG,1) = E.TYPE_VALUE AND E.TYPE_ID = 3003
                AND (ISNULL(@IN_CUST_TYPE,0)=0 OR C.CUST_TYPE=@IN_CUST_TYPE)
    END
	--开关控制 加密重要联系信息
    IF(@V_FLAG_ENCRYPTION = 1)
    BEGIN	
        UPDATE #TEMP_QUERY_TBENIFITER_VALUE
        SET CARD_ID = ISNULL(stuff(CARD_ID,1,len(CARD_ID)-@V_ENCRYPTION_SIZE,'******'),'****'),
            O_TEL = ISNULL(stuff(O_TEL,1,len(O_TEL)-@V_ENCRYPTION_SIZE,'******'),'****'),
            H_TEL = ISNULL(stuff(H_TEL,1,len(H_TEL)-@V_ENCRYPTION_SIZE,'******'),'****'),
            E_MAIL = ISNULL(stuff(E_MAIL,1,charindex('@',E_MAIL)-1,'******'),'****'), 
            MOBILE = ISNULL(stuff(MOBILE,1,len(MOBILE)-@V_ENCRYPTION_SIZE,'******'),'****'),
            BP = ISNULL(stuff(BP,1,len(BP)-@V_ENCRYPTION_SIZE,'******'),'****'),
            POST_ADDRESS = ISNULL(stuff(POST_ADDRESS,1,len(POST_ADDRESS)-@V_ENCRYPTION_SIZE,'******'),'****'),
            POST_ADDRESS2 = ISNULL(stuff(POST_ADDRESS2,1,len(POST_ADDRESS)-@V_ENCRYPTION_SIZE,'******'),'****'),
            POST_CODE = '****',
            POST_CODE2 = '****',
            WT_POST_ADDRESS = ISNULL(STUFF(WT_POST_ADDRESS,1,LEN(WT_POST_ADDRESS)-@V_ENCRYPTION_SIZE,'******'),'****'),
            WT_CARD_ID = ISNULL(STUFF(WT_CARD_ID,1,LEN(WT_CARD_ID)-@V_ENCRYPTION_SIZE,'******'),'****'),
            WT_O_TEL = ISNULL(STUFF(WT_O_TEL,1,LEN(WT_O_TEL)-@V_ENCRYPTION_SIZE,'******'),'****'),
            WT_H_TEL = ISNULL(STUFF(WT_H_TEL,1,LEN(WT_H_TEL)-@V_ENCRYPTION_SIZE,'******'),'****'),
            WT_MOBILE = ISNULL(STUFF(WT_MOBILE,1,LEN(WT_MOBILE)-@V_ENCRYPTION_SIZE,'******'),'****'),
            HT_CUST_TEL = '****',
            HT_CUST_ADDRESS = ISNULL(STUFF(HT_CUST_ADDRESS,1,LEN(HT_CUST_ADDRESS)-@V_ENCRYPTION_SIZE,'******'),'****')
        FROM #TEMP_QUERY_TBENIFITER_VALUE A
        WHERE ISNULL(SERVICE_MAN,0) <> @IN_INPUT_MAN --不是客户经理本人的客户
            AND (IS_DEAL = @V_IS_DEAL OR ISNULL(@V_IS_DEAL,0)=0)--潜在or事实 客户加密
            AND (NOT EXISTS(SELECT CUST_ID FROM @V_TEMPCUST2 B WHERE A.CUST_ID = B.CUST_ID))--没有授权的客户都不能看到联系方式
    END
    --导出时，做日志记录	
	IF @IN_EXPORT_FLAG=1 --客户信息导出
		INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
			SELECT '38003','受益人信息导出',@IN_INPUT_MAN,'受益人信息导出：'+ISNULL(@IN_EXPORT_SUMMARY,'')

	    --20180607 团队的成员
	SELECT MANAGERID,@IN_TEAM_ID TEAM_ID INTO #TMP_TEMA_M FROM TManagerTeamMembers where TEAM_ID=@IN_TEAM_ID
	INSERT INTO #TMP_TEMA_M --子团队成员
	SELECT DISTINCT MANAGERID,@IN_TEAM_ID TEAM_ID FROM TManagerTeamMembers where TEAM_ID IN(SELECT TEAM_ID FROM dbo.GetTEAM_ID(@IN_TEAM_ID))
		AND MANAGERID NOT IN (SELECT MANAGERID FROM #TMP_TEMA_M)

	    	--判断是否显示真实信息
	DECLARE @V_IS_SHOW_REALPHONE NVARCHAR(50)
	SELECT @V_IS_SHOW_REALPHONE=A.CTRL_VALUE FROM TSYSTEM_CTRL A WHERE A.CTRL_TYPE='IS_SHOW_REALPHONE'
	IF ISNULL(@V_IS_SHOW_REALPHONE,'0') = '1' 
	BEGIN
	    SELECT A.*,B.LIST_NAME AS SUB_PRODUCT_NAME,@V_CHANGE_TIMES CHANGE_TIMES 
	    ,CAST(CONVERT(DECIMAL(16,4),ISNULL(L.GAIN_RATE,0)*100) AS VARCHAR)+'%' AS GAIN_RATE
	    FROM #TEMP_QUERY_TBENIFITER_VALUE A 
		LEFT JOIN INTRUST..TSUBPRODUCT B ON A.PRODUCT_ID = B.PRODUCT_ID AND A.SUB_PRODUCT_ID = B.SUB_PRODUCT_ID
		LEFT JOIN INTRUST..TGAINLEVEL L ON A.PRODUCT_ID = L.PRODUCT_ID AND A.SUB_PRODUCT_ID = L.SUB_PRODUCT_ID AND A.PROV_FLAG = L.PROV_FLAG AND A.PROV_LEVEL = L.PROV_LEVEL
	    --20180607
		WHERE (ISNULL(@IN_TEAM_ID,0)=0 OR EXISTS (SELECT * FROM #TMP_TEMA_M TT WHERE TT.MANAGERID=A.SERVICE_MAN))
	END
	ELSE
	BEGIN
		SELECT A.SERIAL_NO, A.PRODUCT_ID, A.PRODUCT_NAME, A.CONTRACT_BH, A.CONTRACT_SUB_BH, A.LIST_ID,A.CUST_ID,A.CUST_NO,A.CUST_NAME, A.TO_AMOUNT, A.BEN_AMOUNT,
           A.FROZEN_MONEY, A.BEN_DATE,A.BEN_END_DATE,A.BEN_STATUS_NAME,A.TO_MONEY,
           A.BANK_ID,A.BANK_NAME,A.BANK_SUB_NAME,A.BANK_ACCT,A.CUST_ACCT_NAME,A.TEMP_ACCT_NAME,A.BONUS_FLAG,
           A.SUB_PRODUCT_ID,A.PROV_FLAG,A.PROV_LEVEL,A.PROV_LEVEL_NAME,A.PROV_FLAG_NAME,
           A.CUST_TYPE,A.TOUCH_TYPE,A.TOUCH_TYPE_NAME,A.CARD_TYPE,A.CARD_TYPE_NAME,A.CARD_ID,A.POST_ADDRESS,A.POST_ADDRESS2,
           A.POST_CODE,A.POST_CODE2,A.LEGAL_MAN,
           '' AS H_TEL,'' AS O_TEL,TR.TC_TEL AS MOBILE,A.E_MAIL,'' AS BP,
           A.CUST_TYPE_NAME,A.JK_TYPE_NAME,
           A.QS_DATE,A.JK_DATE,A.WT_SUB_PRODUCT_ID,A.FEE_JK_TYPE,A.WT_E_MAIL,
           A.WT_CUST_NAME,A.WT_CUST_TYPE_NAME,A.WT_POST_CODE,A.WT_POST_CODE2,
           A.WT_POST_ADDRESS,A.WT_POST_ADDRESS2,A.WT_CARD_TYPE_NAME,A.WT_CARD_ID,
           A.WT_LWGAL_MAN,A.WT_TOUCH_TYPE_NAME,
           '' AS WT_O_TEL,'' AS WT_H_TEL,TR.TC_TEL AS WT_MOBILE,
		   A.SERVICE_MAN,A.SERVICE_MAN_NAME,A.IS_DEAL, A.SUMMARY,A.MEND_CHECK_FLAG
		   ,A.HT_CUST_NAME,'' AS HT_CUST_TEL,A.HT_CUST_ADDRESS,A.SERVICE_MAN_SALES,A.ACCT_CHG_REASON,A.TEMP_BONUS_FLAG
		   ,A.BANK_PROVINCE,A.BANK_PROVINCE_NAME,A.BANK_CITY,A.BANK_CITY_NAME,A.JK_TYPE
		   ,A.TEMP_BANK_PROVINCE,A.TEMP_BANK_CITY,A.TEMP_JK_TYPE,A.TEMP_JK_TYPE_NAME,
	    B.LIST_NAME AS SUB_PRODUCT_NAME,@V_CHANGE_TIMES CHANGE_TIMES
	    ,CAST(CONVERT(DECIMAL(16,4),ISNULL(L.GAIN_RATE,0)*100) AS VARCHAR)+'%' AS GAIN_RATE  
	    FROM #TEMP_QUERY_TBENIFITER_VALUE A 
		LEFT JOIN INTRUST..TSUBPRODUCT B ON A.PRODUCT_ID = B.PRODUCT_ID AND A.SUB_PRODUCT_ID = B.SUB_PRODUCT_ID
		LEFT JOIN INTRUST..TGAINLEVEL L ON A.PRODUCT_ID = L.PRODUCT_ID AND A.SUB_PRODUCT_ID = L.SUB_PRODUCT_ID AND A.PROV_FLAG = L.PROV_FLAG AND A.PROV_LEVEL = L.PROV_LEVEL

				--隐藏号码显示
		LEFT JOIN TNUMBER_RELATION TR ON TR.FK_TCUSTOMERS=A.CUST_ID
	    --20180607
		WHERE (ISNULL(@IN_TEAM_ID,0)=0 OR EXISTS (SELECT * FROM #TMP_TEMA_M TT WHERE TT.MANAGERID=A.SERVICE_MAN))
	END	
		
GO