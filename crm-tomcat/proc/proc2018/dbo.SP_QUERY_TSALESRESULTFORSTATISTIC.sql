﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE dbo.SP_QUERY_TSALESRESULTFORSTATISTIC @IN_SERIAL_NO      INT,            --序号
                                          @IN_PRE_SERIAL_NO  INT,            --预约表主键(EFCRM..TPRECONTRACT.SERIAL_NO)
                                          @IN_PREPRODUCT_ID  INT,            --预发行产品ID
                                          @IN_START_DZ_DATE  INT,            --到账开始日期
                                          @IN_END_DZ_DATE    INT,            --到账结束日期
                                          @IN_PRE_STATUS     NVARCHAR(10),   --产品状态
                                          @IN_CUST_NAME      NVARCHAR(60),   --客户名称
                                          @IN_INPUT_MAN      INT,            --操作员
                                          @IN_PRE_LEVEL      NVARCHAR(10) = NULL,   --预约类别(1182)用于区分预约的级别
										  @IN_TEAM_ID        INT          = NULL,    --团队ID
										  @IN_PRE_PRODUCT_TYPE        INT          = NULL,    --产品类型
										  @IN_FK_TPREMONEYDETAIL       INT          = NULL,    --标准记录ID
										  @IN_RECORDS_COUNT       INT          = NULL,    --标准记录序号
										  @IN_STATUS       INT          = NULL    --标准记录状态
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    IF @IN_SERIAL_NO IS NULL SET @IN_SERIAL_NO = 0
    IF @IN_PRE_SERIAL_NO IS NULL SET @IN_PRE_SERIAL_NO = 0
    IF @IN_PREPRODUCT_ID IS NULL SET @IN_PREPRODUCT_ID = 0
	SET @IN_TEAM_ID = ISNULL(@IN_TEAM_ID,0)
    IF ISNULL(@IN_END_DZ_DATE,0) = 0 SET @IN_END_DZ_DATE = 299991231
	DECLARE @V_IS_FLAG INT,@V_TEAM_ID INT
	SELECT @V_TEAM_ID = TEAM_ID FROM TManagerTeamMembers WHERE MANAGERID = @IN_INPUT_MAN
    SELECT @V_IS_FLAG = 0
    
    IF ISNULL(@IN_STATUS,-2) = -2
	SET @IN_STATUS = 1
	
	IF ISNULL(@IN_PRE_SERIAL_NO,0) <> 0 and ISNULL(@IN_SERIAL_NO ,0)=0
		BEGIN
        SELECT A.RG_CUST_NAME, A.RG_CUST_TYPE, A.RG_CUST_TYPE_NAME, A.RG_PRODUCT_NAME, TC.TO_SERVICE_MAN,TC.TO_SERVICE_MAN_NAME
				, A.FK_TPREMONEYDETAIL, A.REMARK, A.PRE_PRODUCT_TYPE,A.PRE_PRODUCT_TYPE_NAME, D.CUST_TYPE_NAME, A.RECORDS_COUNT, A.SERIAL_NO, A.PRE_SERIAL_NO
				, DBO.GETDATEINT(A.DZ_DATE) AS DZ_DATE, A.DZ_DATE AS DZ_TIME, A.DZ_MONEY, A.REFUND_DATE
                , A.REFUND_MONEY, A.JK_TYPE, A.JK_TYPE_NAME, A.INPUT_MAN, A.INPUT_TIME, A.PRINT_COUNT, A.SMS1_CUSTOMER, A.SMS1_COUNT, A.SMS2_SERVICEMAN
                , A.SMS2_COUNT, A.CONTRACT_SERIAL_NO,D.CUST_NAME, D.MOBILE AS CUST_MOBILE, D.SERVICE_MAN
                , O.OP_NAME AS SERVICE_MAN_NAME, O.MOBILE AS SERVICE_MAN_MOBILE
                FROM TSALESRESULTFORSTATISTIC A 
                LEFT JOIN TPRECONTRACT B ON  A.PRE_SERIAL_NO = B.SERIAL_NO 
                LEFT JOIN TCustomers D ON B.CUST_ID = D.CUST_ID  
                LEFT JOIN TOPERATOR O ON D.SERVICE_MAN = O.OP_CODE
                LEFT JOIN TSALES_CHANGES TC ON A.FK_TSALES_CHANGES=TC.SERIAL_NO
                WHERE A.PRE_SERIAL_NO = @IN_PRE_SERIAL_NO
                ORDER BY A.PRE_SERIAL_NO,A.SERIAL_NO
		END 
		ELSE BEGIN
    --能够访问所有预约信息
    IF EXISTS(SELECT 1 FROM INTRUST..TOPRIGHT WITH (NOLOCK) WHERE OP_CODE = @IN_INPUT_MAN AND MENU_ID ='20409' AND FUNC_ID = 2049905)
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
	--以上客户经理的预约客户
    INSERT INTO @V_TEMPCUST2
        SELECT DISTINCT A.CUST_ID FROM TPRECONTRACT A,TCustomers Z
        WHERE A.CUST_ID = Z.CUST_ID AND (EXISTS( SELECT 1 FROM @V_MANAGER_IDS B WHERE Z.SERVICE_MAN = B.MANAGERID)
                OR EXISTS( SELECT 1 FROM @V_MANAGER_IDS C WHERE (@V_QUERYCUSTOMERS = 1 AND A.INPUT_MAN = C.MANAGERID) ) )
            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)
			
    DECLARE @V_OPERATOR_KEY NVARCHAR(10)
    SELECT @V_OPERATOR_KEY = Extension FROM TCustManagers WHERE ManagerID = @IN_INPUT_MAN
    IF ISNULL(@V_OPERATOR_KEY,0) = 0
        SET @V_OPERATOR_KEY = @IN_INPUT_MAN		
    IF ISNULL(@IN_SERIAL_NO,0) <> 0
        SELECT @IN_PRE_SERIAL_NO = PRE_SERIAL_NO FROM TSALESRESULTFORSTATISTIC WHERE SERIAL_NO = @IN_SERIAL_NO
    /*IF ISNULL(@IN_PRE_PRODUCT_TYPE,0) <> 0 AND @IN_PRE_PRODUCT_TYPE > 1 
    BEGIN*/
        SELECT A.RG_SERVICE_MAN,A.RG_SERVICE_MAN_NAME,A.RG_CUST_NAME, A.RG_CUST_TYPE, A.RG_CUST_TYPE_NAME, A.RG_PRODUCT_NAME
        , RG.TEAM_ID AS RG_TEAM_ID, RG.TEAM_NAME AS RG_TEAM_NAME
        , TT.TEAM_ID AS TT_TEAM_ID, TT.TEAM_NAME AS TT_TEAM_NAME,TC.TO_SERVICE_MAN,TC.TO_SERVICE_MAN_NAME
		, A.FK_TPREMONEYDETAIL, A.REMARK, A.PRE_PRODUCT_TYPE,A.PRE_PRODUCT_TYPE_NAME, D.CUST_TYPE_NAME, A.RECORDS_COUNT, A.SERIAL_NO, A.PRE_SERIAL_NO
		, DBO.GETDATEINT(A.DZ_DATE) AS DZ_DATE, A.DZ_DATE AS DZ_TIME, A.DZ_MONEY, A.REFUND_DATE
        , A.REFUND_MONEY, A.JK_TYPE, A.JK_TYPE_NAME, A.INPUT_MAN, A.INPUT_TIME, A.PRINT_COUNT, A.SMS1_CUSTOMER, A.SMS1_COUNT, A.SMS2_SERVICEMAN
        , A.SMS2_COUNT, A.CONTRACT_SERIAL_NO,B.CUST_ID,D.CUST_NAME, D.MOBILE AS CUST_MOBILE, D.SERVICE_MAN
        , O.OP_NAME AS SERVICE_MAN_NAME, O.MOBILE AS SERVICE_MAN_MOBILE,P.TEAM_NAME, C.PREPRODUCT_NAME
        , ISNULL(Z.PRODUCT_NAME,C.PREPRODUCT_NAME) AS PRODUCT_NAME,Z.CONTRACT_BH,Z.CONTRACT_SUB_BH,Z.RG_MONEY
        FROM TSALESRESULTFORSTATISTIC A 
        LEFT JOIN INTRUST..TCONTRACT Z WITH (NOLOCK) ON A.CONTRACT_SERIAL_NO = Z.SERIAL_NO
        LEFT JOIN TPRECONTRACT B ON  A.PRE_SERIAL_NO = B.SERIAL_NO 
        LEFT JOIN TPREPRODUCT C ON B.PREPRODUCT_ID = C.PREPRODUCT_ID
        LEFT JOIN TCustomers D ON B.CUST_ID = D.CUST_ID  
        LEFT JOIN TOPERATOR O ON D.SERVICE_MAN = O.OP_CODE
        LEFT JOIN TManagerTeamMembers P ON O.OP_CODE = P.MANAGERID 
        LEFT JOIN TSALES_CHANGES TC ON A.FK_TSALES_CHANGES=TC.SERIAL_NO
        LEFT JOIN TManagerTeamMembers TT ON TC.TO_SERVICE_MAN = TT.MANAGERID 
        LEFT JOIN TManagerTeamMembers RG ON A.RG_SERVICE_MAN = RG.MANAGERID
        WHERE (A.SERIAL_NO = @IN_SERIAL_NO OR @IN_SERIAL_NO = 0)
        AND (ISNULL(@IN_PRE_SERIAL_NO,0)=0 OR A.PRE_SERIAL_NO = @IN_PRE_SERIAL_NO)
        AND (ISNULL(@IN_TEAM_ID,0) = 0 OR A.RG_SERVICE_MAN IN (SELECT MANAGERID FROM TManagerTeamMembers WHERE TEAM_ID = @IN_TEAM_ID))	
        AND (@IN_PREPRODUCT_ID = 0 OR EXISTS(SELECT 1 FROM TPRECONTRACT Z WHERE A.PRE_SERIAL_NO = Z.SERIAL_NO AND (PREPRODUCT_ID = @IN_PREPRODUCT_ID OR @IN_PREPRODUCT_ID = 0)))
        AND (DBO.GETDATEINT(A.DZ_DATE) BETWEEN ISNULL(@IN_START_DZ_DATE,0) AND @IN_END_DZ_DATE)	
        AND (B.PRE_LEVEL = @IN_PRE_LEVEL OR ISNULL(@IN_PRE_LEVEL,'') = '') 
        AND (ISNULL(@IN_PRE_STATUS,'') = '' OR C.STATUS = @IN_PRE_STATUS)
        AND (ISNULL(@IN_CUST_NAME,'') = '' OR (D.CUST_NAME = @IN_CUST_NAME OR A.RG_CUST_NAME = @IN_CUST_NAME))
        AND (A.FK_TPREMONEYDETAIL = @IN_FK_TPREMONEYDETAIL OR ISNULL(@IN_FK_TPREMONEYDETAIL,'') = '')       
        AND (A.RECORDS_COUNT > @IN_RECORDS_COUNT OR ISNULL(@IN_RECORDS_COUNT,'') = '')
        AND (A.PRE_PRODUCT_TYPE = @IN_PRE_PRODUCT_TYPE OR ISNULL(@IN_PRE_PRODUCT_TYPE,0) = 0)
        AND (A.[STATUS] = @IN_STATUS OR ISNULL(@IN_STATUS,'-2') = '-2')
        ORDER BY A.PRE_SERIAL_NO,A.SERIAL_NO
    /*END
    ELSE
    SELECT TT.TEAM_NAME AS TT_TEAM_NAME, TC.TO_SERVICE_MAN,TC.TO_SERVICE_MAN_NAME, A.*,ISNULL(Z.PRODUCT_NAME,A.PREPRODUCT_NAME) AS PRODUCT_NAME,Z.CONTRACT_BH,Z.CONTRACT_SUB_BH,Z.RG_MONEY         
    FROM  --用一层子查询，在外层判断过滤SERIAL_NO，是为了在子查询中得到缴款序号
        ( SELECT A.RG_CUST_NAME, A.RG_CUST_TYPE, A.RG_CUST_TYPE_NAME, A.RG_PRODUCT_NAME, A.FK_TSALES_CHANGES, A.FK_TPREMONEYDETAIL, A.REMARK, A.PRE_PRODUCT_TYPE,A.PRE_PRODUCT_TYPE_NAME, D.CUST_TYPE_NAME, A.RECORDS_COUNT, A.SERIAL_NO, A.PRE_SERIAL_NO, DBO.GETDATEINT(A.DZ_DATE) AS DZ_DATE, A.DZ_DATE AS DZ_TIME, A.DZ_MONEY, A.REFUND_DATE
                , A.REFUND_MONEY, A.JK_TYPE, A.JK_TYPE_NAME, A.INPUT_MAN, A.INPUT_TIME, A.PRINT_COUNT, A.SMS1_CUSTOMER, A.SMS1_COUNT, A.SMS2_SERVICEMAN
                , A.SMS2_COUNT, A.CONTRACT_SERIAL_NO, A.ONWAY_FLAG, C.PREPRODUCT_NAME, 
               B.CUST_ID, D.CUST_NAME, D.MOBILE AS CUST_MOBILE, D.SERVICE_MAN, O.OP_NAME AS SERVICE_MAN_NAME, O.MOBILE AS SERVICE_MAN_MOBILE,
              B.PRE_LEVEL, B.PRE_LEVEL_NAME,P.TEAM_NAME
            FROM TSALESRESULTFORSTATISTIC A,TPRECONTRACT B 
            --LEFT JOIN INTRUST..TPRECONTRACT R WITH (NOLOCK) ON B.BIND_SERIAL_NO = R.SERIAL_NO
            , TPREPRODUCT C
            , TCustomers D 
            LEFT JOIN TOPERATOR O ON D.SERVICE_MAN = O.OP_CODE 
            LEFT JOIN TManagerTeamMembers P ON D.SERVICE_MAN = P.MANAGERID 
            WHERE A.PRE_SERIAL_NO = B.SERIAL_NO 
                AND B.PREPRODUCT_ID = C.PREPRODUCT_ID
                AND B.CUST_ID = D.CUST_ID 
                AND B.PRE_STATUS ='111301'
				AND(ISNULL(@IN_PRE_SERIAL_NO,0) <> 0 OR (D.SERVICE_MAN = @IN_INPUT_MAN OR @V_IS_FLAG = 1 
					OR EXISTS(SELECT 1 FROM @V_TEMPCUST2 Y WHERE D.CUST_ID = Y.CUST_ID)))
                AND (ISNULL(@IN_TEAM_ID,0) = 0 OR D.SERVICE_MAN IN (SELECT MANAGERID FROM TManagerTeamMembers WHERE TEAM_ID = @IN_TEAM_ID))		
                AND(A.PRE_SERIAL_NO = @IN_PRE_SERIAL_NO OR @IN_PRE_SERIAL_NO = 0)
                AND EXISTS(SELECT 1 FROM TPRECONTRACT Z WHERE A.PRE_SERIAL_NO = Z.SERIAL_NO AND (PREPRODUCT_ID = @IN_PREPRODUCT_ID OR @IN_PREPRODUCT_ID = 0) )
                AND (DBO.GETDATEINT(A.DZ_DATE) BETWEEN ISNULL(@IN_START_DZ_DATE,0) AND @IN_END_DZ_DATE)
                AND (ISNULL(@IN_PRE_STATUS,'') = '' OR C.STATUS = @IN_PRE_STATUS)
                AND (ISNULL(@IN_CUST_NAME,'') = '' OR D.CUST_NAME = @IN_CUST_NAME)
                AND (B.PRE_LEVEL = @IN_PRE_LEVEL OR ISNULL(@IN_PRE_LEVEL,'') = '')       
                AND (A.FK_TPREMONEYDETAIL = @IN_FK_TPREMONEYDETAIL OR ISNULL(@IN_FK_TPREMONEYDETAIL,'') = '')       
                AND (A.RECORDS_COUNT > @IN_RECORDS_COUNT OR ISNULL(@IN_RECORDS_COUNT,'') = '') 
                AND (A.[STATUS] = @IN_STATUS OR ISNULL(@IN_STATUS,'-2') = '-2')
                AND (A.PRE_PRODUCT_TYPE = @IN_PRE_PRODUCT_TYPE OR ISNULL(@IN_PRE_PRODUCT_TYPE,0) = 0)
        ) A  LEFT JOIN INTRUST..TCONTRACT Z WITH (NOLOCK) ON A.CONTRACT_SERIAL_NO = Z.SERIAL_NO
        LEFT JOIN TSALES_CHANGES TC ON A.FK_TSALES_CHANGES=TC.SERIAL_NO
        LEFT JOIN TManagerTeamMembers TT ON TC.TO_SERVICE_MAN = TT.MANAGERID 
        WHERE (A.SERIAL_NO = @IN_SERIAL_NO OR @IN_SERIAL_NO = 0)
        ORDER BY A.PRE_SERIAL_NO,A.SERIAL_NO*/
		END
GO
