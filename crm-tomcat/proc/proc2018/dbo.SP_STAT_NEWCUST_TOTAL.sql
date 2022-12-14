USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_STAT_NEWCUST_TOTAL  @IN_BEGIN_DATE INT, --开始日期
                                        @IN_END_DATE   INT, --结日期
                                        @IN_CUST_TYPE  INT, --客户类型1个人2机构
                                        @IN_QUERY_TYPE INT, --1:0-100W; 2:100-300W; 3:300-1000W; 4:1000W
                                        @IN_INPUT_MAN  INT,  --操作员,
                                        @IN_CUSTOMER_MESSAGER NVARCHAR(20) = '', --客户经理
                                        @IN_TEAM_NAME NVARCHAR(80) = '' -- 团队名称
										
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    
    DECLARE @V_USER_ID INT,@V_TOTAL_CUST INT,@V_TOTAL_RGMONEY DEC(18,3)
    SELECT @V_USER_ID=USER_ID FROM TSYSTEMINFO
    SELECT @IN_BEGIN_DATE=0 WHERE @IN_BEGIN_DATE IS NULL
    SELECT @IN_END_DATE=20991231 WHERE ISNULL(@IN_END_DATE,0)=0
    SELECT @IN_CUST_TYPE=0 WHERE @IN_CUST_TYPE IS NULL OR @IN_CUST_TYPE NOT IN (0,1,2)
    SELECT @IN_QUERY_TYPE=0 WHERE @IN_QUERY_TYPE IS NULL OR @IN_QUERY_TYPE NOT IN (0,1,2,3,4)
    SELECT @IN_CUSTOMER_MESSAGER='' WHERE @IN_CUSTOMER_MESSAGER IS NULL
    SELECT @IN_TEAM_NAME='' WHERE @IN_TEAM_NAME IS NULL
    
    DECLARE @VT_BJITIC_NEWCUST TABLE(
        CUST_NO         NVARCHAR(8),
        CUST_NAME       NVARCHAR(100),
        CUST_TYPE       NVARCHAR(10),
        TRADE_MONEY     DEC(16,3),
        START_DATE      INTEGER,
        SERVICE_MAN     NVARCHAR(30),    --客户经理
        RECOMMENDED     NVARCHAR(60),    --联系人
        BD_BUSI_ID      INTEGER,         --认购\申购
        INPUT_DATE      INTEGER          --客户新建立日期 
    )
    DECLARE @V_GR_COUNT INT, @V_JG_COUNT INT
    --从份额变动记录中取认购、申购
    INSERT INTO @VT_BJITIC_NEWCUST (CUST_NO, CUST_NAME, CUST_TYPE, TRADE_MONEY, START_DATE,
                 SERVICE_MAN, RECOMMENDED, BD_BUSI_ID,INPUT_DATE)
         SELECT B.CUST_NO, B.CUST_NAME, B.CUST_TYPE, D.TO_MONEY AS TRADE_MONEY, D.START_DATE,
                  E.OP_NAME AS SERVICE_MAN, B.RECOMMENDED, 1,dbo.GETDATEINT(B.INPUT_TIME)
            FROM  TCUSTOMERS B, INTRUST..TCONTRACT D, INTRUST..TOPERATOR E
           WHERE B.CUST_ID = D.CUST_ID
			    AND B.SERVICE_MAN = E.OP_CODE 
                AND (E.OP_NAME = @IN_CUSTOMER_MESSAGER OR @IN_CUSTOMER_MESSAGER = '')
                AND(B.CUST_TYPE = @IN_CUST_TYPE OR ISNULL(@IN_CUST_TYPE,0) = 0)
                AND(D.START_DATE BETWEEN ISNULL(@IN_BEGIN_DATE,0) AND ISNULL(@IN_END_DATE,20991231))
                AND(NOT EXISTS(SELECT CUST_ID FROM INTRUST..TBENIFITOR Y WHERE Y.BEN_DATE < @IN_BEGIN_DATE AND D.CUST_ID = Y.CUST_ID))
         UNION ALL --包含转让的
         SELECT B.CUST_NO, B.CUST_NAME, B.CUST_TYPE, A.TO_AMOUNT AS TRADE_MONEY, A.CHANGE_DATE,
                  E.OP_NAME AS SERVICE_MAN, B.RECOMMENDED, 9,dbo.GETDATEINT(B.INPUT_TIME)
           FROM INTRUST..TBENCHANGES A, TCUSTOMERS B, INTRUST..TCONTRACT D, INTRUST..TOPERATOR E
            WHERE A.TO_CUST_ID = B.CUST_ID AND A.PRODUCT_ID = D.PRODUCT_ID AND A.CONTRACT_BH = D.CONTRACT_BH  AND B.CUST_ID = D.CUST_ID
                AND B.SERVICE_MAN = E.OP_CODE AND (E.OP_NAME = @IN_CUSTOMER_MESSAGER OR @IN_CUSTOMER_MESSAGER = '')
                AND(B.CUST_TYPE = @IN_CUST_TYPE OR ISNULL(@IN_CUST_TYPE,0) = 0)
                AND(A.CHANGE_DATE BETWEEN ISNULL(@IN_BEGIN_DATE,0) AND ISNULL(@IN_END_DATE,20991231))
                AND(NOT EXISTS(SELECT CUST_ID FROM INTRUST..TBENIFITOR Y WHERE Y.BEN_DATE < @IN_BEGIN_DATE AND A.TO_CUST_ID = Y.CUST_ID))
    DECLARE @VT_TOTAL_NEWCUST TABLE(
        CUST_NO         NVARCHAR(8),
        CUST_NAME       NVARCHAR(100),
        CUST_TYPE       NVARCHAR(10),
        TOTAL_MONEY     DEC(18,3),
        START_DATE      INTEGER,        --时间段内首次购买日期 
        SERVICE_MAN     NVARCHAR(30),   --客户经理
        RECOMMENDED     NVARCHAR(60),   --联系人
        INPUT_DATE      INTEGER         --客户新建立日期 
    )
	--汇总客户多笔交易记录
	INSERT INTO @VT_TOTAL_NEWCUST (CUST_NO, CUST_NAME,CUST_TYPE, TOTAL_MONEY,START_DATE,SERVICE_MAN,RECOMMENDED,INPUT_DATE)
    SELECT CUST_NO,CUST_NAME, CUST_TYPE,SUM(TRADE_MONEY) AS TOTAL_MONEY, MIN(START_DATE) AS START_DATE, SERVICE_MAN, RECOMMENDED,INPUT_DATE
        FROM @VT_BJITIC_NEWCUST
        GROUP BY  CUST_NO,CUST_NAME,CUST_TYPE,SERVICE_MAN,SERVICE_MAN,RECOMMENDED,INPUT_DATE
        ORDER BY CUST_NAME
	--建信要求加入合计项
	IF @V_USER_ID=15
	BEGIN
		SELECT @V_TOTAL_CUST=COUNT(0),@V_TOTAL_RGMONEY=SUM(ISNULL(TOTAL_MONEY,0)) FROM @VT_TOTAL_NEWCUST
		--INSERT INTO @VT_TOTAL_NEWCUST (CUST_NO, CUST_NAME,CUST_TYPE, TOTAL_MONEY,START_DATE,SERVICE_MAN,RECOMMENDED)
		--	SELECT '',' 合计：'+CAST(@V_TOTAL_CUST AS VARCHAR),'',@V_TOTAL_RGMONEY,NULL,'',''
		--所属团队整理成表
		SELECT MANAGERNAME,TEAM_NAME INTO #TMP_TEAM FROM (SELECT DISTINCT MANAGERNAME FROM TManagerTeamMembers) A 
			OUTER APPLY(SELECT TEAM_NAME=STUFF(REPLACE(REPLACE((SELECT TEAM_NAME FROM TManagerTeamMembers N WHERE MANAGERNAME=A.MANAGERNAME FOR XML AUTO), '<N TEAM_NAME="', ','), '"/>', ''), 1, 1, '')) B

		--输出
		SELECT dbo.GETDATESTRYMD(INPUT_DATE) INPUT_DATE,T.CUST_NAME,T.TOTAL_MONEY AS TRADE_MONEY,START_DATE ,T.SERVICE_MAN
				,T.RECOMMENDED,CAST(B.TEAM_NAME AS NVARCHAR) TEAM_NAME,C.CUST_ID
			FROM @VT_TOTAL_NEWCUST T LEFT JOIN #TMP_TEAM B ON T.SERVICE_MAN=B.MANAGERNAME
				LEFT JOIN TCustomers C ON T.CUST_NO=C.CUST_NO
			WHERE ( (@IN_QUERY_TYPE = 0)
					OR(@IN_QUERY_TYPE = 1 AND T.TOTAL_MONEY BETWEEN 0 AND 999999)
					OR(@IN_QUERY_TYPE = 2 AND T.TOTAL_MONEY BETWEEN 1000000 AND 2999999)
					OR(@IN_QUERY_TYPE = 3 AND T.TOTAL_MONEY BETWEEN 3000000 AND 9999999)
					OR(@IN_QUERY_TYPE = 4 AND T.TOTAL_MONEY >=10000000)
				  )
			 AND (@IN_TEAM_NAME='' 
					OR (SELECT SERVICE_MAN FROM TCUSTOMERS WHERE CUST_NO=T.CUST_NO) 
							IN (SELECT MANAGERID FROM TManagerTeamMembers where TEAM_ID=(SELECT TEAM_ID FROM TManagerTeams WHERE TEAM_NAME=@IN_TEAM_NAME)) ) 
			UNION ALL
			SELECT NULL,'合计：'+CAST(@V_TOTAL_CUST AS VARCHAR),@V_TOTAL_RGMONEY,NULL,NULL,NULL,NULL,NULL
		RETURN
	END
	
	--输出
    SELECT CUST_NAME,TOTAL_MONEY AS TRADE_MONEY,START_DATE ,SERVICE_MAN, RECOMMENDED--,CAST(B.TEAM_NAME AS NVARCHAR) TEAM_NAME
        FROM @VT_TOTAL_NEWCUST T-- LEFT JOIN #TMP_TEAM B ON T.SERVICE_MAN=B.MANAGERNAME
        WHERE ( (@IN_QUERY_TYPE = 0)
                OR(@IN_QUERY_TYPE = 1 AND TOTAL_MONEY BETWEEN 0 AND 999999)
                OR(@IN_QUERY_TYPE = 2 AND TOTAL_MONEY BETWEEN 1000000 AND 2999999)
                OR(@IN_QUERY_TYPE = 3 AND TOTAL_MONEY BETWEEN 3000000 AND 9999999)
                OR(@IN_QUERY_TYPE = 4 AND TOTAL_MONEY >=10000000)
              )
         AND (@IN_TEAM_NAME='' 
                OR (SELECT SERVICE_MAN FROM TCUSTOMERS WHERE CUST_NO=T.CUST_NO) 
                        IN (SELECT MANAGERID FROM TManagerTeamMembers where TEAM_ID=(SELECT TEAM_ID FROM TManagerTeams WHERE TEAM_NAME=@IN_TEAM_NAME)) ) 
        ORDER BY T.CUST_NAME
        
    RETURN @@ROWCOUNT
GO
