USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE dbo.SP_QUERY_TCustomers_AllInfo
                            @IN_CUST_ID    INT,      --客户ID
                            @IN_FLAG       INT,      --查询标识:0基本信息1客户现有合同2客户已结束合同3客户其他合同4客户受益信息100已注销的客户
                            @IN_INPUT_MAN  INT,
                            @IN_CUST_NAME  NVARCHAR(90)=NULL --客户名称
                                     
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @RET INT, @V_DT_INTRUST INT, @IN_NODE_SHARE INT, @V_CUSTQZ0001 INT, @V_QUERYCUSTOMERS INT
    DECLARE @V_FLAG_ACCESS_ALL INT, @V_FLAG_ENCRYPTION INT, @V_SQL NVARCHAR(2000),@V_USER_ID INT,@V_ENCRYPTION_SIZE INT
	DECLARE @V_FACT_POTENTIAL_FLAG_FZ INT
	
	SELECT @V_FACT_POTENTIAL_FLAG_FZ = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = 'FACT_POTENTIAL_FLAG_FZ'--方正客户信息加密条件
    --客户查询权限忽略
    SELECT @V_FLAG_ENCRYPTION = 0 --加密标志
    SELECT @V_QUERYCUSTOMERS = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'QUERYCUSTOMERS'
    SELECT @V_USER_ID = USER_ID FROM TSYSTEMINFO
    IF @V_QUERYCUSTOMERS IS NULL SET @V_QUERYCUSTOMERS = 1
	--加密显示位数	
	IF EXISTS(SELECT 1 FROM TDICTPARAM WHERE TYPE_VALUE = '800701')
		SELECT @V_ENCRYPTION_SIZE = CAST(TYPE_CONTENT AS INT) FROM TDICTPARAM WHERE TYPE_VALUE = '800701'
	ELSE
		SET  @V_ENCRYPTION_SIZE = 4
    SELECT @V_CUSTQZ0001 = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'CUSTQZ0001' --默认1
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
	
	IF @IN_FLAG=0  --查询客户基本资料
	BEGIN
		EXEC SP_QUERY_TCustomers @IN_CUST_ID,'','',0,@IN_INPUT_MAN,0,null --
		RETURN
	END
	IF @IN_FLAG=1  --查询客户现有合同
	BEGIN
	    SELECT PRODUCT_ID,PRODUCT_NAME,CONTRACT_SUB_BH,QS_DATE,END_DATE,RG_MONEY
		    FROM INTRUST..TCONTRACT
		    WHERE CUST_ID=@IN_CUST_ID AND HT_STATUS IN ('120101','120106')--正常/已续签
	    UNION ALL
	    SELECT PRODUCT_ID,PRODUCT_NAME,CONTRACT_SUB_BH,QS_DATE,END_DATE,SG_MONEY 
		    FROM INTRUST..TCONTRACTSG
		    WHERE CUST_ID=@IN_CUST_ID AND HT_STATUS IN ('120101','120106')--正常/已续签
		RETURN
	END
	IF @IN_FLAG=2  --查询客户已结束合同
	BEGIN
		SELECT PRODUCT_ID,PRODUCT_NAME,CONTRACT_SUB_BH, QS_DATE,END_DATE,RG_MONEY
			FROM INTRUST..TCONTRACT
			WHERE CUST_ID=@IN_CUST_ID AND HT_STATUS IN ('120104','120105')--已中止/已到期结算
		UNION ALL
		SELECT PRODUCT_ID,PRODUCT_NAME,CONTRACT_SUB_BH,QS_DATE,END_DATE,SG_MONEY 
			FROM INTRUST..TCONTRACTSG
			WHERE CUST_ID=@IN_CUST_ID AND HT_STATUS IN ('120104','120105')--已中止/已到期结算
		RETURN
	END
	IF @IN_FLAG=3  --查询客户其他合同
	BEGIN
		SELECT PRODUCT_ID,PRODUCT_NAME,CONTRACT_SUB_BH, QS_DATE,END_DATE,RG_MONEY
			FROM INTRUST..TCONTRACT A
			WHERE CUST_ID=@IN_CUST_ID AND HT_STATUS IN ('120102','120103')--已挂失/已冻结
		UNION ALL
		SELECT PRODUCT_ID,PRODUCT_NAME,CONTRACT_SUB_BH,QS_DATE,END_DATE,SG_MONEY 
			FROM INTRUST..TCONTRACTSG
			WHERE CUST_ID=@IN_CUST_ID AND HT_STATUS IN ('120102','120103')--已挂失/已冻结
		RETURN
	END
	IF @IN_FLAG=4  --查询客户受益信息
	BEGIN
		SELECT A.PRODUCT_ID,B.PRODUCT_NAME, BEN_DATE,BEN_END_DATE,BEN_MONEY,CUST_ACCT_NAME,BEN_STATUS,BEN_STATUS_NAME
			FROM INTRUST..TBENIFITOR A LEFT JOIN INTRUST..TPRODUCT B ON A.PRODUCT_ID=B.PRODUCT_ID
			WHERE A.CUST_ID=@IN_CUST_ID
			    AND BEN_AMOUNT>0
		RETURN
	END
	
	--判断是否显示真实信息
	DECLARE @V_IS_SHOW_REALPHONE NVARCHAR(50)
	SELECT @V_IS_SHOW_REALPHONE=A.CTRL_VALUE FROM TSYSTEM_CTRL A WHERE A.CTRL_TYPE='IS_SHOW_REALPHONE'
	
	IF @IN_FLAG=100  --已注销的客户
	BEGIN
		IF ISNULL(@V_IS_SHOW_REALPHONE,'0') = '1' 
			BEGIN
			SELECT A.CUST_ID,CUST_NO,CUST_NAME,CASE SEX WHEN 1 THEN N'男' WHEN 2 THEN '女' ELSE N'' END AS SEX,BIRTHDAY,POST_ADDRESS,POST_CODE,
						   CARD_ID,CUST_TEL,E_MAIL,O_TEL,MOBILE,BP,STATUS_NAME,LAST_RG_DATE, 0 CURR_TO_AMOUNT,TOTAL_MONEY,CURRENT_MONEY,
						   SERVICE_MAN,CUST_TYPE,GRADE_LEVEL,GRADE_LEVEL_NAME,CARD_TYPE_NAME,IS_DEAL,
						   CASE IS_DEAL WHEN 1 THEN N'事实客户' WHEN 2 THEN N'潜在客户' END AS IS_DEAL_NAME,
						   A.CUST_SOURCE,A.CUST_SOURCE_NAME,A.COUNTRY,N'',A.MONEY_SOURCE,A.MONEY_SOURCE_NAME,NULL,0,A.PRINT_DEPLOY_BILL,A.PRINT_POST_INFO,
						   CUST_SOURCE_EXPLAIN,DBO.GETDATEINT(INPUT_TIME)
						FROM TCustomers A 
						WHERE A.STATUS='112805'    --删除
							AND (ISNULL(@IN_CUST_NAME,'') = N'' OR CUST_NAME LIKE '%'+@IN_CUST_NAME+'%')
			END
		ELSE
			BEGIN
			SELECT TR.TC_TEL AS V_TEL_NUM,A.CUST_ID,CUST_NO,CUST_NAME,CASE SEX WHEN 1 THEN N'男' WHEN 2 THEN '女' ELSE N'' END AS SEX,BIRTHDAY,POST_ADDRESS,POST_CODE,
				   CARD_ID,
				   '' AS CUST_TEL,E_MAIL,'' AS O_TEL,TR.TC_TEL AS MOBILE,'' AS BP,STATUS_NAME,LAST_RG_DATE, 0 CURR_TO_AMOUNT,TOTAL_MONEY,CURRENT_MONEY,
				   SERVICE_MAN,CUST_TYPE,GRADE_LEVEL,GRADE_LEVEL_NAME,CARD_TYPE_NAME,IS_DEAL,
				   CASE IS_DEAL WHEN 1 THEN N'事实客户' WHEN 2 THEN N'潜在客户' END AS IS_DEAL_NAME,
				   A.CUST_SOURCE,A.CUST_SOURCE_NAME,A.COUNTRY,N'',A.MONEY_SOURCE,A.MONEY_SOURCE_NAME,NULL,0,A.PRINT_DEPLOY_BILL,A.PRINT_POST_INFO,
				   CUST_SOURCE_EXPLAIN,DBO.GETDATEINT(INPUT_TIME)
				FROM TCustomers A 
				--隐藏号码显示
				LEFT JOIN TNUMBER_RELATION TR ON TR.FK_TCUSTOMERS=A.CUST_ID 
				WHERE A.STATUS='112805'    --删除
					AND (ISNULL(@IN_CUST_NAME,'') = N'' OR CUST_NAME LIKE '%'+@IN_CUST_NAME+'%')
             END          
		RETURN
	END
GO
