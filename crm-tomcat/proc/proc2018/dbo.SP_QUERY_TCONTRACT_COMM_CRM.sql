﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCONTRACT_COMM_CRM @IN_BOOK_CODE          INT,
                                             @IN_PRODUCT_ID         INT,
                                             @IN_CONTRACT_BH        NVARCHAR(16),
                                             @IN_CUST_NAME          NVARCHAR(100),
                                             @IN_CARD_ID            NVARCHAR(40),
                                             @IN_INPUT_MAN          INT,
                                             @IN_PRE_FLAG           INT = 1,
                                             @IN_MIN_TO_MONEY       DEC(16,3) = 0,
                                             @IN_MAX_TO_MONEY       DEC(16,3) = 0,
                                             @IN_PRODUCT_NAME       NVARCHAR(100)= NULL,
                                             @IN_CHECK_FLAG         INT = NULL,          --审核标志(1、未审核 2、已审核  0、全部 91事后修改未审核 93事后修改审核不通过)
                                             @IN_CONTRACT_SUB_BH    NVARCHAR(50) = '',   -- 合同编号查询  20080104 by wangc
                                             @IN_CONTRACT_ZJFLAG    INT = 0,             --合同性质：1 资金合同 2 财产合同 0 全部  20100629 luohh
                                             @IN_CUST_TYPE          INTEGER=NULL,        --客户类型
                                             @IN_MAX_RG_MONEY       DECIMAL(16,3)=NULL,  --最大认购金额
                                             @IN_MIN_RG_MONEY       DECIMAL(16,3)=NULL,  --最小认购金额
                                             @IN_GroupID            INTEGER=NULL,        --客户分组序号
                                             @IN_CLASSDETAIL_ID     INTEGER=NULL,        --客户分级ID
                                             @IN_CHANNEL_ID         INTEGER = 0,         --渠道ID
											 @IN_ManagerID       	INTEGER = 0,		 --客户经理
                                             --@OUT_QUALIFIED_NUM     INTEGER = 0 OUTPUT  --使用输出参数返回当前结果集内的合格投资人数量
                                             @IN_SUB_PRODUCT_ID     INT = 0,              --子产品ID
                                             @IN_TEAM_ID            INT = 0,              --团队ID
                                             @IN_JK_TYPE            NVARCHAR(50) = '',    --缴款类型
											 @IN_CONTRACT_TYPE      INT = 0               --1 前台销售人员合同2产品部门合同3代销合同
WITH ENCRYPTION
AS
    SET NOCOUNT ON

    DECLARE @V_DT_INTRUST INT
    SELECT @V_DT_INTRUST = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'DT_INTRUST'

    DECLARE @V_SW20409 INT
    DECLARE @V_PRODUCTS TABLE(PRODUCT_ID INT)
    DECLARE @V_IS_FLAG INT, @IN_CARD_ID18 NVARCHAR(18)

    BEGIN
        --20409: The Products can be accessed. START
        SELECT @V_SW20409 = VALUE FROM INTRUST..TSYSCONTROL WHERE FLAG_TYPE = 'SW20409'
        IF @V_SW20409 = 1
            INSERT INTO @V_PRODUCTS
                SELECT PRODUCT_ID FROM INTRUST..TPRODUCT WHERE BOOK_CODE = @IN_BOOK_CODE
                    AND(INTRUST_FLAG1 IN ( SELECT FUNC_ID-2040900 FROM INTRUST..TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID > 2040900 AND FUNC_ID <= 2040999 AND OP_CODE = @IN_INPUT_MAN )
                        OR PRODUCT_ID IN ( SELECT FUNC_ID FROM INTRUST..TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID < 2040900 AND OP_CODE = @IN_INPUT_MAN)
                        OR ADMIN_MANAGER = @IN_INPUT_MAN OR INPUT_MAN = @IN_INPUT_MAN OR ISNULL(@IN_INPUT_MAN,0)=0 )
        --20409: The Products can be accessed. END
        SELECT @V_IS_FLAG = 0
        IF EXISTS(SELECT * FROM INTRUST..TSYSCONTROL WHERE FLAG_TYPE = 'A_CARDID' AND VALUE = 1)
        BEGIN
            IF LEN(@IN_CARD_ID)=15   --15位身份证号码转18位
                SELECT @IN_CARD_ID18 = dbo.IDCARD15TO18(@IN_CARD_ID)
            ELSE IF LEN(@IN_CARD_ID)=18 --18位转成15位
                SELECT @IN_CARD_ID18 = dbo.IDCARD18TO15(@IN_CARD_ID)
        END
        SELECT @IN_CARD_ID18 = ISNULL(@IN_CARD_ID18, @IN_CARD_ID)
        --能够访问所有认购信息
        IF EXISTS(SELECT 1 FROM INTRUST..TOPRIGHT WHERE OP_CODE = @IN_INPUT_MAN AND MENU_ID ='20409' AND FUNC_ID = 2049906)
            SELECT @V_IS_FLAG = 1
        DECLARE @V_TEMPCUST2 TABLE(CUST_ID INT) --根据访问权限能够访问的客户，根据访问权限 OR
		DECLARE @IN_NODE_SHARE INT, @V_QUERYCUSTOMERS INT
		SELECT @V_QUERYCUSTOMERS = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'QUERYCUSTOMERS'
		IF @V_QUERYCUSTOMERS IS NULL SET @V_QUERYCUSTOMERS = 1
		--客户经理级别树中，同节点客户经理是否共享客户
		IF EXISTS(SELECT 1 FROM TSYSCONTROL WHERE FLAG_TYPE = 'MANAGER002' AND VALUE = 1)
			SET @IN_NODE_SHARE = 0  --共享
		ELSE
			SET @IN_NODE_SHARE = 1  --不共享
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
		--处理快捷授权的客户ShareType=3
		INSERT INTO @V_TEMPCUST2(CUST_ID)
			SELECT CUST_ID FROM TAuthorizationShare A WHERE ShareType = 3 AND Status = 1 AND (GETDATE() BETWEEN ISNULL(A.START_TIME,GETDATE()) AND ISNULL(A.INVALID_TIME,GETDATE()+36000)) AND ManagerID = @IN_INPUT_MAN
				AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)
			UNION ALL
			SELECT B.CUST_ID FROM TAuthorizationShare A, TCustomers B
			WHERE A.SourceManagerID = B.SERVICE_MAN AND A.ShareType = 3 AND A.Status = 1 AND A.CUST_ID = 0 AND (GETDATE() BETWEEN ISNULL(A.START_TIME,GETDATE()) AND ISNULL(A.INVALID_TIME,GETDATE()+36000)) AND A.ManagerID = @IN_INPUT_MAN
				AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)
		--以上客户经理的认购客户
		INSERT INTO @V_TEMPCUST2
			SELECT DISTINCT A.CUST_ID FROM INTRUST..TCONTRACT A, TCustomers Z
			WHERE A.CUST_ID = Z.CUST_ID AND (EXISTS( SELECT 1 FROM @V_MANAGER_IDS B WHERE Z.SERVICE_MAN = B.MANAGERID)
					OR EXISTS( SELECT 1 FROM @V_MANAGER_IDS C WHERE (@V_QUERYCUSTOMERS = 1 AND A.INPUT_MAN = C.MANAGERID) ) )
				AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)
				
		--团队的成员
		SELECT MANAGERID,@IN_TEAM_ID TEAM_ID INTO #TMP_TEMA_M FROM TManagerTeamMembers where TEAM_ID=@IN_TEAM_ID
		INSERT INTO #TMP_TEMA_M --子团队成员
		SELECT DISTINCT MANAGERID,@IN_TEAM_ID TEAM_ID FROM TManagerTeamMembers where TEAM_ID IN(SELECT TEAM_ID FROM dbo.GetTEAM_ID(@IN_TEAM_ID))
			AND MANAGERID NOT IN (SELECT MANAGERID FROM #TMP_TEMA_M)
		IF @IN_CHECK_FLAG IS NULL OR @IN_CHECK_FLAG<>91 AND @IN_CHECK_FLAG<>93		
		BEGIN
            SELECT A.*,B.CUST_NO,B.CUST_NAME,B.CARD_ID,(SELECT B.CHANNEL_NAME FROM INTRUST..TCHANNEL B WHERE B.CHANNEL_ID = A.CHANNEL_ID) AS CHANNEL_NAME,
                   B.CUST_TYPE,E.LIST_NAME AS SUB_PRODUCT_NAME,F.CONTENT AS PROV_FLAG_NAME,G.OP_NAME AS SERVICE_MAN_NAME,
                   (SELECT LEADER_NAME FROM TManagerTeams WHERE TEAM_ID=(SELECT TOP 1 TEAM_ID FROM TManagerTeamMembers WHERE MANAGERID=A.SERVICE_MAN)) LEADER_NAME,B.CUST_SOURCE_NAME,
			       CONVERT(VARCHAR(12),B.INPUT_TIME,111) AS CUST_ADD_TIME,CASE WHEN CUST_TYPE = 1 THEN '个人' ELSE '机构' END AS CUST_TYPE_NAME
			       ,CASE A.CHECK_FLAG WHEN 2 THEN '已审核' ELSE '未审核' END CHECK_FLAG_NAME
            INTO #TMP_QUERY_TCONTRACT_COMM_CRM2
            FROM INTRUST..TCONTRACT A 
                    LEFT JOIN INTRUST..TSUBPRODUCT E ON A.PRODUCT_ID = E.PRODUCT_ID AND A.SUB_PRODUCT_ID = E.SUB_PRODUCT_ID
                    LEFT JOIN INTRUST..TINTEGERPARAM F ON A.PROV_FLAG = F.TYPE_VALUE AND F.TYPE_ID = 3003
			    , TCustomers B
				    LEFT JOIN TOPERATOR G ON B.SERVICE_MAN = G.OP_CODE 	
				    --LEFT JOIN TManagerTeamMembers D ON D.MANAGERID = G.OP_CODE
				    --LEFT JOIN TManagerTeams H ON D.TEAM_ID = H.TEAM_ID
			    ,INTRUST..TPRODUCT C
                WHERE A.CUST_ID = B.CUST_ID AND A.BOOK_CODE = @IN_BOOK_CODE AND A.PRODUCT_ID = C.PRODUCT_ID
                    AND (A.CONTRACT_BH LIKE '%'+@IN_CONTRACT_BH+'%' OR @IN_CONTRACT_BH IS NULL OR @IN_CONTRACT_BH = '')
                    AND (A.CONTRACT_SUB_BH LIKE '%'+@IN_CONTRACT_SUB_BH+'%' OR @IN_CONTRACT_SUB_BH IS NULL OR @IN_CONTRACT_SUB_BH = '')
                    AND (C.PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID IS NULL OR @IN_PRODUCT_ID =0)
                    AND (E.SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID OR @IN_SUB_PRODUCT_ID IS NULL OR @IN_SUB_PRODUCT_ID =0)
                    AND (A.LINK_MAN = @IN_INPUT_MAN OR A.INPUT_MAN = @IN_INPUT_MAN OR A.SERVICE_MAN = @IN_INPUT_MAN OR @V_IS_FLAG = 1 OR EXISTS(SELECT 1 FROM @V_TEMPCUST2 Y WHERE A.CUST_ID = Y.CUST_ID))
                    AND (B.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%' OR @IN_CUST_NAME IS NULL OR @IN_CUST_NAME = '')
                    AND (B.CARD_ID LIKE '%'+@IN_CARD_ID+'%' OR B.CARD_ID LIKE '%'+@IN_CARD_ID18+'%' OR @IN_CARD_ID IS NULL OR @IN_CARD_ID = '')
                    AND (C.PRODUCT_STATUS IN ('110202','110203') ) --AND C.INTRUST_TYPE1 = '113801')
                    AND (PRE_FLAG = @IN_PRE_FLAG OR @IN_PRE_FLAG IS NULL OR @IN_PRE_FLAG = 0)
                   --AND (A.HT_STATUS NOT IN('120104','120105'))
                    AND(@V_SW20409 = 0 OR @V_SW20409 IS NULL OR A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCTS))
                    AND( (A.TO_MONEY BETWEEN @IN_MIN_TO_MONEY AND @IN_MAX_TO_MONEY)
                             OR (ISNULL(@IN_MIN_TO_MONEY,0) = 0 AND TO_MONEY <= @IN_MAX_TO_MONEY)
                             OR (ISNULL(@IN_MAX_TO_MONEY,0) = 0 AND TO_MONEY >= @IN_MIN_TO_MONEY)
                             OR (ISNULL(@IN_MIN_TO_MONEY,0) = 0 AND ISNULL(@IN_MAX_TO_MONEY,0) = 0)
                         )
                    AND (C.PRODUCT_NAME LIKE '%'+@IN_PRODUCT_NAME+'%' OR @IN_PRODUCT_NAME IS NULL OR @IN_PRODUCT_NAME='')
                    AND (A.CHECK_FLAG = @IN_CHECK_FLAG OR ISNULL(@IN_CHECK_FLAG,0) = 0)
                    AND (ISNULL(@IN_CONTRACT_ZJFLAG,0) =0 OR (@IN_CONTRACT_ZJFLAG =1 AND A.ENTITY_TYPE IS NULL) OR(@IN_CONTRACT_ZJFLAG =2 AND A.ENTITY_TYPE IS NOT NULL))
                    AND (ISNULL(@IN_CUST_TYPE,0)=0 OR B.CUST_TYPE=@IN_CUST_TYPE)
                    AND (ISNULL(@IN_CHANNEL_ID,0)=0 OR A.CHANNEL_ID=@IN_CHANNEL_ID)
                    AND (ISNULL(@IN_MAX_RG_MONEY,0)=0 OR A.RG_MONEY <= @IN_MAX_RG_MONEY)
                    AND (ISNULL(@IN_MIN_RG_MONEY,0)=0 OR A.RG_MONEY >= @IN_MIN_RG_MONEY)
                    AND (ISNULL(@IN_GroupID,0)=0 OR A.CUST_ID IN (SELECT CUST_ID FROM TCustGroupMembers WHERE GroupID = @IN_GroupID))
                    AND (ISNULL(@IN_CLASSDETAIL_ID,0)=0 OR A.CUST_ID IN (SELECT CUST_ID FROM TCustomerClass WHERE CLASSDETAIL_ID = @IN_CLASSDETAIL_ID))
				    AND (ISNULL(@IN_ManagerID,0)=0 OR A.SERVICE_MAN=@IN_ManagerID)
				    AND (ISNULL(@IN_TEAM_ID,0)=0 OR EXISTS (SELECT * FROM #TMP_TEMA_M TT WHERE TT.MANAGERID=A.SERVICE_MAN))
				    AND (ISNULL(@IN_JK_TYPE,'')='' OR A.JK_TYPE=@IN_JK_TYPE) --缴款类型
					AND (ISNULL(@IN_CONTRACT_TYPE,0)=0 OR A.CONTRACT_TYPE=@IN_CONTRACT_TYPE) --合同类型
                ORDER BY A.CHECK_FLAG,A.PRODUCT_ID DESC,A.CONTRACT_BH DESC
                --SELECT @OUT_QUALIFIED_NUM = COUNT(*) FROM #TMP_QUERY_TCONTRACT_COMM_CRM2 WHERE CUST_TYPE = 1 AND RG_MONEY < 3000000
		        --UPDATE #TMP_QUERY_TCONTRACT_COMM_CRM2 SET PRODUCT_NAME = PRODUCT_NAME+'('+SUB_PRODUCT_NAME+')'
		        --	WHERE ISNULL(SUB_PRODUCT_NAME,'') <> ''

                --修改临时表的PRODUCT_NAME列宽度，以免“将截断字符串或二进制数据”的错误
                ALTER TABLE #TMP_QUERY_TCONTRACT_COMM_CRM2 ALTER COLUMN PRODUCT_NAME NVARCHAR(800)
		        UPDATE #TMP_QUERY_TCONTRACT_COMM_CRM2 SET PRODUCT_NAME = PRODUCT_NAME+'('+SUB_PRODUCT_NAME+')'
			        WHERE ISNULL(SUB_PRODUCT_NAME,'') <> ''
    		
                SELECT A.*,B.CONTRACT_PRT_TEMPLATE FROM #TMP_QUERY_TCONTRACT_COMM_CRM2 A LEFT JOIN INTRUST..TPRODUCTLIMIT B ON A.PRODUCT_ID=B.PRODUCT_ID AND ISNULL(A.SUB_PRODUCT_ID,0)=ISNULL(B.SUB_PRODUCT_ID,0)
        END
        ELSE
        BEGIN
            SELECT  M.SERIAL_NO,M.CONTRACT_SERIAL,M.CHECK_FLAG
                    ,A.BOOK_CODE,A.PRODUCT_ID,A.PRODUCT_CODE,A.PRODUCT_NAME,A.CURRENCY_ID,A.CUST_ID
                    ,A.PRE_CODE,A.CONTRACT_BH,A.TOUCH_TYPE,A.TOUCH_TYPE_NAME,A.VALID_PERIOD,A.START_DATE,A.END_DATE
                    ,A.RG_MONEY,A.TO_MONEY,A.TO_AMOUNT,A.JK_STATUS,A.TRANS_FLAG,A.TRANS_FLAG_NAME,A.LAST_TRANS_DATE
                    ,A.INPUT_MAN,A.INPUT_TIME,A.MODI_MAN,A.MODI_TIME,A.CHECK_MAN,A.CHECK_TIME,A.ZJ_CHECK_MAN,A.ZJ_CHECK_TIME
                    ,A.HT_STATUS,A.HT_STATUS_NAME,A.ZJ_CHECK_FLAG,A.SERVICE_MAN,A.TEMP_STATUS,A.TEMP_END_DATE
                    ,A.TEMP_RG_MONEY,A.TEMP_QS_DATE,A.PRE_FLAG,A.ENTITY_NAME,A.ENTITY_TYPE,A.ENTITY_TYPE_NAME,A.ENTITY_PRICE
                    ,A.PZ_FLAG,A.CITY_SERIAL_NO,A.CITY_NAME,A.TERMINATE_DATE,A.ORIGINAL_END_DATE,A.DF_CUST_ID,A.ZY_FLAG
                    ,A.FEE_JK_TYPE,A.RG_MONEY2,A.RG_FEE_RATE,A.RG_FEE_MONEY,A.JK_TOTAL_MONEY,A.SUB_PRODUCT_ID
                    ,A.WITH_BANK_FLAG,A.HT_BANK_ID,A.HT_BANK_NAME,A.HT_BANK_SUB_NAME,A.WITH_SECURITY_FLAG,A.WITH_PRIVATE_FLAG
                    ,A.TA_FLAG,A.TA_ACCOUNT_ID,A.TRADE_ACCOUNT_ID,A.MODE_FLAG,A.MEND_CHECK_FLAG,A.BZJ_FLAG
                    ,A.BANK_PROVINCE,A.BANK_PROVINCE_NAME,A.BANK_CITY,A.BANK_CITY_NAME,A.RECOMMEND_MAN,A.RECOMMEND_MAN_NAME
                    ,A.SUBMIT_FLAG,A.EXPECT_ROR_LOWER,A.EXPECT_ROR_UPPER,A.FPRG_TIMES,A.SALE_CON_FLAG,A.FPRG_FLAG
                    ,A.IS_YKGL,A.XTHTYJSYL,A.PERIOD_UNIT,A.CONTACT_ID,A.LOST_DATE
                    ,A.HT_CUST_NAME,A.HT_CUST_ADDRESS,A.HT_CUST_TEL,A.MONEY_ORIGIN,A.MONEY_ORIGIN_NAME,A.SUB_MONEY_ORIGIN,A.SUB_MONEY_ORIGIN_NAME
                   ,M.CONTRACT_SUB_BH, M.BANK_ID, M.BANK_SUB_NAME, M.BANK_ACCT, M.GAIN_ACCT,M.BONUS_FLAG,M.BONUS_RATE
                   ,M.QS_DATE, M.JK_DATE, M.JK_TYPE, M.JK_TYPE_NAME,M.LINK_MAN,M.PROV_FLAG,M.PROV_LEVEL,M.PROV_LEVEL_NAME,M.SUMMARY
                   , M.CHANNEL_COOPERTYPE, M.CHANNEL_COOPERTYPE_NAME, M.CHANNEL_ID, M.CHANNEL_TYPE, M.CHANNEL_MEMO, M.MARKET_MONEY
                   ,B.CUST_NO,B.CUST_NAME,B.CARD_ID
                   ,(SELECT CHANNEL_NAME FROM INTRUST..TCHANNEL WHERE CHANNEL_ID = M.CHANNEL_ID) AS CHANNEL_NAME,
                   B.CUST_TYPE,E.LIST_NAME AS SUB_PRODUCT_NAME
                   ,F.CONTENT AS PROV_FLAG_NAME,G.OP_NAME AS SERVICE_MAN_NAME
                   ,(SELECT LEADER_NAME FROM TManagerTeams WHERE TEAM_ID=(SELECT TOP 1 TEAM_ID FROM TManagerTeamMembers WHERE MANAGERID=A.SERVICE_MAN)) LEADER_NAME,B.CUST_SOURCE_NAME,
			       CONVERT(VARCHAR(12),B.INPUT_TIME,111) AS CUST_ADD_TIME
			       ,CASE WHEN CUST_TYPE = 1 THEN '个人' ELSE '机构' END AS CUST_TYPE_NAME
			       ,CASE A.CHECK_FLAG WHEN 2 THEN '已审核' ELSE '未审核' END CHECK_FLAG_NAME
            INTO #TMP_QUERY_TCONTRACT_COMM_CRM3
            FROM INTRUST..TCONTRACT A 
                    LEFT JOIN INTRUST..TSUBPRODUCT E ON A.PRODUCT_ID = E.PRODUCT_ID AND A.SUB_PRODUCT_ID = E.SUB_PRODUCT_ID                    
			    , TCustomers B
				    LEFT JOIN TOPERATOR G ON B.SERVICE_MAN = G.OP_CODE 	
				    --LEFT JOIN TManagerTeamMembers D ON D.MANAGERID = G.OP_CODE
				    --LEFT JOIN TManagerTeams H ON D.TEAM_ID = H.TEAM_ID
			    ,INTRUST..TPRODUCT C
			    ,TCONTRACTMODI M
			        LEFT JOIN INTRUST..TINTEGERPARAM F ON M.PROV_FLAG = F.TYPE_VALUE AND F.TYPE_ID = 3003
                WHERE A.CUST_ID = B.CUST_ID AND A.BOOK_CODE = @IN_BOOK_CODE 
                    AND A.PRODUCT_ID = C.PRODUCT_ID
                    AND A.SERIAL_NO = M.CONTRACT_SERIAL
                    AND (A.CONTRACT_BH LIKE '%'+@IN_CONTRACT_BH+'%' OR @IN_CONTRACT_BH IS NULL OR @IN_CONTRACT_BH = '')
                    AND (M.CONTRACT_SUB_BH LIKE '%'+@IN_CONTRACT_SUB_BH+'%' OR ISNULL(@IN_CONTRACT_SUB_BH,'')='')
                    AND (C.PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID IS NULL OR @IN_PRODUCT_ID =0)
                    AND (E.SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID OR @IN_SUB_PRODUCT_ID IS NULL OR @IN_SUB_PRODUCT_ID =0)
                    AND (A.LINK_MAN = @IN_INPUT_MAN OR A.INPUT_MAN = @IN_INPUT_MAN OR A.SERVICE_MAN = @IN_INPUT_MAN OR @V_IS_FLAG = 1 OR EXISTS(SELECT 1 FROM @V_TEMPCUST2 Y WHERE A.CUST_ID = Y.CUST_ID))
                    AND (B.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%' OR @IN_CUST_NAME IS NULL OR @IN_CUST_NAME = '')
                    AND (B.CARD_ID LIKE '%'+@IN_CARD_ID+'%' OR B.CARD_ID LIKE '%'+@IN_CARD_ID18+'%' OR @IN_CARD_ID IS NULL OR @IN_CARD_ID = '')
                    AND (C.PRODUCT_STATUS IN ('110202','110203') ) --AND C.INTRUST_TYPE1 = '113801')
                    AND (PRE_FLAG = @IN_PRE_FLAG OR @IN_PRE_FLAG IS NULL OR @IN_PRE_FLAG = 0)
                   --AND (A.HT_STATUS NOT IN('120104','120105'))
                    AND(@V_SW20409 = 0 OR @V_SW20409 IS NULL OR A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCTS))
                    AND( (A.TO_MONEY BETWEEN @IN_MIN_TO_MONEY AND @IN_MAX_TO_MONEY)
                             OR (ISNULL(@IN_MIN_TO_MONEY,0) = 0 AND TO_MONEY <= @IN_MAX_TO_MONEY)
                             OR (ISNULL(@IN_MAX_TO_MONEY,0) = 0 AND TO_MONEY >= @IN_MIN_TO_MONEY)
                             OR (ISNULL(@IN_MIN_TO_MONEY,0) = 0 AND ISNULL(@IN_MAX_TO_MONEY,0) = 0)
                         )
                    AND (C.PRODUCT_NAME LIKE '%'+@IN_PRODUCT_NAME+'%' OR @IN_PRODUCT_NAME IS NULL OR @IN_PRODUCT_NAME='')
                    --AND (A.CHECK_FLAG = @IN_CHECK_FLAG OR ISNULL(@IN_CHECK_FLAG,0) = 0)
                    AND M.CHECK_FLAG=(CASE WHEN @IN_CHECK_FLAG=91 THEN 1 ELSE 3 END)
                    AND (ISNULL(@IN_CONTRACT_ZJFLAG,0) =0 OR (@IN_CONTRACT_ZJFLAG =1 AND A.ENTITY_TYPE IS NULL) OR(@IN_CONTRACT_ZJFLAG =2 AND A.ENTITY_TYPE IS NOT NULL))
                    AND (ISNULL(@IN_CUST_TYPE,0)=0 OR B.CUST_TYPE=@IN_CUST_TYPE)
                    AND (ISNULL(@IN_CHANNEL_ID,0)=0 OR M.CHANNEL_ID=@IN_CHANNEL_ID)
                    AND (ISNULL(@IN_MAX_RG_MONEY,0)=0 OR A.RG_MONEY <= @IN_MAX_RG_MONEY)
                    AND (ISNULL(@IN_MIN_RG_MONEY,0)=0 OR A.RG_MONEY >= @IN_MIN_RG_MONEY)
                    AND (ISNULL(@IN_GroupID,0)=0 OR A.CUST_ID IN (SELECT CUST_ID FROM TCustGroupMembers WHERE GroupID = @IN_GroupID))
                    AND (ISNULL(@IN_CLASSDETAIL_ID,0)=0 OR A.CUST_ID IN (SELECT CUST_ID FROM TCustomerClass WHERE CLASSDETAIL_ID = @IN_CLASSDETAIL_ID))
				    AND (ISNULL(@IN_ManagerID,0)=0 OR A.SERVICE_MAN=@IN_ManagerID)
				    AND (ISNULL(@IN_TEAM_ID,0)=0 OR EXISTS (SELECT * FROM #TMP_TEMA_M TT WHERE TT.MANAGERID=A.SERVICE_MAN))
				    AND (ISNULL(@IN_JK_TYPE,'')='' OR A.JK_TYPE=@IN_JK_TYPE) --缴款类型
					AND (ISNULL(@IN_CONTRACT_TYPE,0)=0 OR A.CONTRACT_TYPE=@IN_CONTRACT_TYPE) --合同类型
                ORDER BY A.CHECK_FLAG,A.PRODUCT_ID DESC,A.CONTRACT_BH DESC
                
            --SELECT @OUT_QUALIFIED_NUM = COUNT(*) FROM #TMP_QUERY_TCONTRACT_COMM_CRM3 WHERE CUST_TYPE = 1 AND RG_MONEY < 3000000
		    --UPDATE #TMP_QUERY_TCONTRACT_COMM_CRM3 SET PRODUCT_NAME = PRODUCT_NAME+'('+SUB_PRODUCT_NAME+')'
		    --	WHERE ISNULL(SUB_PRODUCT_NAME,'') <> ''

            --修改临时表的PRODUCT_NAME列宽度，以免“将截断字符串或二进制数据”的错误
            ALTER TABLE #TMP_QUERY_TCONTRACT_COMM_CRM3 ALTER COLUMN PRODUCT_NAME NVARCHAR(800)
		    UPDATE #TMP_QUERY_TCONTRACT_COMM_CRM3 SET PRODUCT_NAME = PRODUCT_NAME+'('+SUB_PRODUCT_NAME+')'
			    WHERE ISNULL(SUB_PRODUCT_NAME,'') <> ''
		
            SELECT A.*,B.CONTRACT_PRT_TEMPLATE FROM #TMP_QUERY_TCONTRACT_COMM_CRM3 A LEFT JOIN INTRUST..TPRODUCTLIMIT B ON A.PRODUCT_ID=B.PRODUCT_ID AND ISNULL(A.SUB_PRODUCT_ID,0)=ISNULL(B.SUB_PRODUCT_ID,0)
        END
    END
GO