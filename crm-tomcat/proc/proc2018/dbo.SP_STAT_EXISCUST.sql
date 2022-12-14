USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_STAT_EXISCUST  @IN_BEGIN_DATE INT, --开始日期
                                   @IN_END_DATE   INT, --结止日期
                                   @IN_SERVICEMAN_NAME NVARCHAR(20), --客户经理
                                   @IN_TEAM_NAME NVARCHAR(80), -- 团队名称
                                   @IN_INPUT_MAN  INT   --操作员
                                   
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    
    DECLARE @V_USER_ID INT,@V_TOTAL_CUST INT,@V_TOTAL_RGMONEY DEC(18,3),@V_DEPARTMENT_ROLEID INT
    DECLARE @V_TEAM_ID INT
    
    SELECT @V_USER_ID=USER_ID FROM TSYSTEMINFO
    SELECT @IN_BEGIN_DATE=ISNULL(@IN_BEGIN_DATE,0)
    SELECT @IN_END_DATE=ISNULL(@IN_END_DATE,20991231)
    SELECT @IN_SERVICEMAN_NAME=ISNULL(@IN_SERVICEMAN_NAME,'')
    SELECT @IN_TEAM_NAME=ISNULL(@IN_TEAM_NAME,'')
    SELECT @V_TEAM_ID=TEAM_ID FROM TManagerTeams WHERE TEAM_NAME=@IN_TEAM_NAME
    SET @V_TEAM_ID=ISNULL(@V_TEAM_ID,0)
    
    CREATE TABLE #TMP_CUST( --有权限的客户
				SERVICE_MAN INT,
				CUST_ID INT
				)
    CREATE TABLE #TMP_CUST_STAT(--待统计的客户临时表
				SERVICE_MAN INT,
				RG_MONEY_TOTAL DEC(16,3),   --当前累计认购金额
				RG_CUST_NUM INT,            --当前累计认购客户数
				RG_MONEY_D DEC(16,3),     --当时间区间内认购金额
				RG_CUST_NUM_D INT,        --当时间区间内认购的客户数
				)
    --根据权限，找出能查询的客户
    SELECT @V_DEPARTMENT_ROLEID=ISNULL(VALUE,0) FROM TSYSCONTROL WHERE FLAG_TYPE='AROLE5' --客户经理角色ID
    --如果是管理员角色，则查所有客户
    IF EXISTS (SELECT * FROM TOPROLE WHERE OP_CODE=@IN_INPUT_MAN AND ROLE_ID=1)
    BEGIN
		INSERT INTO #TMP_CUST(CUST_ID,SERVICE_MAN)
			SELECT CUST_ID,SERVICE_MAN FROM TCustomers WHERE DBO.GETDATEINT(INPUT_TIME)>@IN_BEGIN_DATE
    END
    ELSE
    BEGIN
		--客户经理自己的客户
		INSERT INTO #TMP_CUST(CUST_ID,SERVICE_MAN)
			SELECT CUST_ID,SERVICE_MAN FROM TCustomers WHERE ISNULL(SERVICE_MAN,0)=@IN_INPUT_MAN AND DBO.GETDATEINT(INPUT_TIME)>@IN_BEGIN_DATE
		--团队领导时，团队成员的客户
		INSERT INTO #TMP_CUST(CUST_ID,SERVICE_MAN)
			SELECT CUST_ID,SERVICE_MAN FROM TCustomers
				WHERE ISNULL(SERVICE_MAN,0)<>@IN_INPUT_MAN
					AND DBO.GETDATEINT(INPUT_TIME)>@IN_BEGIN_DATE --存量客户
					AND EXISTS (SELECT * FROM TManagerTeamMembers A LEFT JOIN TManagerTeams B ON A.TEAM_ID=B.TEAM_ID WHERE B.LEADER=@IN_INPUT_MAN AND A.MANAGERID<>@IN_INPUT_MAN AND ISNULL(TCustomers.SERVICE_MAN,0)=A.MANAGERID )
		--部门领导时，整个部门的客户
		IF @V_DEPARTMENT_ROLEID>0 AND EXISTS (SELECT * FROM TOPROLE WHERE OP_CODE=@IN_INPUT_MAN AND ROLE_ID=@V_DEPARTMENT_ROLEID)
		BEGIN
			DECLARE @V_DEPART_ID INT
			SELECT @V_DEPART_ID=DEPART_ID FROM TOPERATOR WHERE OP_CODE=@IN_INPUT_MAN
			INSERT INTO #TMP_CUST(CUST_ID,SERVICE_MAN)
			SELECT CUST_ID,SERVICE_MAN FROM TCustomers
				WHERE ISNULL(SERVICE_MAN,0)<>@IN_INPUT_MAN --自己除外
					AND DBO.GETDATEINT(INPUT_TIME)>@IN_BEGIN_DATE --存量客户
					AND EXISTS (SELECT * FROM TOPERATOR WHERE OP_CODE<>@IN_INPUT_MAN AND DEPART_ID=@V_DEPART_ID AND ISNULL(TCustomers.SERVICE_MAN,0)=TOPERATOR.OP_CODE) --整个部门的除自己外的客户
					AND NOT EXISTS(SELECT * FROM #TMP_CUST WHERE #TMP_CUST.CUST_ID=TCustomers.CUST_ID) --没加过的客户
		END
	END
	--待统计的客户经理
	INSERT INTO #TMP_CUST_STAT(SERVICE_MAN,RG_MONEY_TOTAL,RG_CUST_NUM,RG_MONEY_D,RG_CUST_NUM_D)
		SELECT DISTINCT SERVICE_MAN,0,0,0,0 FROM #TMP_CUST
	--累计认购金额
	SELECT B.SERVICE_MAN,SUM(RG_MONEY) RG_MONEY_TOTAL,COUNT(0) CUST_NUM INTO #TMP_RG FROM INTRUST..TCONTRACT A LEFT JOIN #TMP_CUST B ON A.CUST_ID=B.CUST_ID WHERE EXISTS (SELECT * FROM #TMP_CUST WHERE #TMP_CUST.CUST_ID=A.CUST_ID) GROUP BY B.SERVICE_MAN 
	UPDATE #TMP_CUST_STAT SET RG_MONEY_TOTAL=A.RG_MONEY_TOTAL,RG_CUST_NUM=A.CUST_NUM FROM #TMP_RG A WHERE #TMP_CUST_STAT.SERVICE_MAN=A.SERVICE_MAN
	--累计认购金额，再加上受让部分(客户数量不准备，先注释)
    --SELECT B.SERVICE_MAN,SUM(TO_AMOUNT) AS TRADE_MONEY INTO #TMP_RG2 FROM INTRUST..TBENCHANGES A LEFT JOIN #TMP_CUST B ON A.TO_CUST_ID=B.CUST_ID GROUP BY TO_CUST_ID
    --UPDATE #TMP_CUST_STAT SET RG_MONEY_TOTAL=ISNULL(#TMP_CUST_STAT.RG_MONEY_TOTAL,0)+A.RG_MONEY_TOTAL FROM #TMP_RG A WHERE #TMP_CUST_STAT.SERVICE_MAN=A.SERVICE_MAN
	
    --当前受益金额
	--SELECT CUST_ID,SUM(BEN_AMOUNT) BEN_AMOUNT INTO #TME_BEN_AMOUNT FROM INTRUST..TBENIFITOR WHERE BEN_AMOUNT>0 GROUP BY CUST_ID 
	--UPDATE #TMP_CUST_STAT SET BEN_AMOUNT=(SELECT BEN_AMOUNT FROM #TME_BEN_AMOUNT WHERE #TME_BEN_AMOUNT.CUST_ID=#TMP_CUST_STAT.CUST_ID)
	
	--时间区间内的认购
	TRUNCATE TABLE #TMP_RG
	INSERT INTO #TMP_RG(SERVICE_MAN,RG_MONEY_TOTAL,CUST_NUM)
		SELECT B.SERVICE_MAN,SUM(RG_MONEY) RG_MONEY_TOTAL,COUNT(0) CUST_NUM FROM INTRUST..TCONTRACT A LEFT JOIN #TMP_CUST B ON A.CUST_ID=B.CUST_ID
			WHERE EXISTS (SELECT * FROM #TMP_CUST WHERE #TMP_CUST.CUST_ID=A.CUST_ID)
				AND A.QS_DATE>=@IN_BEGIN_DATE AND A.QS_DATE<=@IN_END_DATE
			 GROUP BY B.SERVICE_MAN
	UPDATE #TMP_CUST_STAT SET RG_MONEY_D=A.RG_MONEY_TOTAL,RG_CUST_NUM_D=A.CUST_NUM FROM #TMP_RG A WHERE #TMP_CUST_STAT.SERVICE_MAN=A.SERVICE_MAN
	
    /*--建信要求加入合计项
	IF @V_USER_ID=15
	BEGIN
		SELECT @V_TOTAL_CUST=COUNT(0),@V_TOTAL_RGMONEY=SUM(ISNULL(RG_MONEY_TOTAL,0)) FROM #TMP_CUST_STAT
		INSERT INTO #TMP_CUST_STAT (CUST_NO, CUST_NAME,CUST_TYPE, TOTAL_MONEY,START_DATE,SERVICE_MAN,RECOMMENDED)
			SELECT '','合计：'+CAST(@V_TOTAL_CUST AS VARCHAR),'',@V_TOTAL_RGMONEY,NULL,'',''
	END*/
	--所属团队整理成表
	--SELECT * INTO #TMP_TEAM FROM (SELECT DISTINCT MANAGERNAME FROM TManagerTeamMembers) A 
	--	OUTER APPLY(SELECT TEAM_NAME=STUFF(REPLACE(REPLACE((SELECT TEAM_NAME FROM TManagerTeamMembers N WHERE MANAGERNAME=A.MANAGERNAME FOR XML AUTO), '<N TEAM_NAME="', ','), '"/>', ''), 1, 1, '')) B

	--输出
    SELECT B.OP_NAME,A.RG_MONEY_TOTAL,A.RG_CUST_NUM,A.RG_MONEY_D,A.RG_CUST_NUM_D FROM #TMP_CUST_STAT A LEFT JOIN TOPERATOR B ON A.SERVICE_MAN=B.OP_CODE
		WHERE (B.OP_NAME=@IN_SERVICEMAN_NAME OR ISNULL(@IN_SERVICEMAN_NAME,'')='')
			AND (@V_TEAM_ID=0 OR EXISTS (SELECT * FROM TManagerTeamMembers WHERE MANAGERID=A.SERVICE_MAN AND TEAM_ID=@V_TEAM_ID))
		
GO
