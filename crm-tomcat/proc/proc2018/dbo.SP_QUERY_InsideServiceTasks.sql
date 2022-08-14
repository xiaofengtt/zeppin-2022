USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_InsideServiceTasks @IN_INPUT_MAN   INTEGER  --操作员（CRM.TOperator.OP_CODE）
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_SERIAL_NO INT, @V_SYS_DATE INT, @V_ROLE_ID INT
    SELECT @V_SYS_DATE = dbo.GETDATEINT(GETDATE())
    DECLARE @V_DT_INTRUST INT
    SELECT @V_DT_INTRUST = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'DT_INTRUST'
    --SELECT @V_ROLE_ID=ROLE_ID FROM TOPERATOR WHERE OP_CODE=@IN_INPUT_MAN

    SELECT [Serial_no]
		  ,[ManagerID]
		  ,[Executor]
		  ,[ServiceType]
		  ,[ServiceTypeName]
		  ,[ServiceTitle]
		  ,[ServiceWay]
		  ,[StartDateTime]
		  ,[EndDateTime]
		  ,[CompleteTime]
		  ,[Status]
		  ,[Result]
		  ,[CustomerCount]
		  ,[CompleteCount]
		  ,[ServiceRemark]
		  ,[QUES_ID]
		  ,[PRODUCT_ID]
		  ,[InsertMan]
		  ,[InsertTime]
		  ,[Originate]
		  ,[AutoFlag]
		  ,[TempID]
		  ,[TempTitle]
		  ,[ExecRoleID]
		  ,[IsRole], CONVERT(NVARCHAR(200),N'/affair/service/service_deal_add.jsp?serial_no='+CONVERT(NVARCHAR(20),Serial_no)+N'&servceType='+CONVERT(NVARCHAR(20),ServiceType)) AS URL,
           CONVERT(NVARCHAR(60),N'40304') AS MENU_ID
        INTO #TMP_InsideServiceTasks
        FROM TServiceTasks  --自己的任务，或者指定角色可处理的任务
        WHERE (Executor = @IN_INPUT_MAN OR (ISNULL(ExecRoleID,0) = @V_ROLE_ID)) AND Status IN (2,3) AND ISNULL(IsRole,0)=0
    --指定的角色可以处理任务
    /*INSERT INTO #TMP_InsideServiceTasks(ServiceType,ServiceTypeName,ManagerID,Executor,ServiceTitle,CustomerCount,URL,MENU_ID,ExecRoleID,IsRole)
    SELECT ServiceType,ServiceTypeName,ManagerID,Executor,ServiceTitle,CustomerCount, CONVERT(NVARCHAR(200),N'/affair/service/service_deal_add.jsp?serial_no='+CONVERT(NVARCHAR(20),Serial_no)+N'&servceType='+CONVERT(NVARCHAR(20),ServiceType)) AS URL,
           CONVERT(NVARCHAR(60),N'40304') AS MENU_ID,ExecRoleID,IsRole
       FROM TServiceTasks
       WHERE (ISNULL(ExecRoleID,0) = @V_ROLE_ID) AND Status IN (2,3) AND ISNULL(IsRole,0)=1
    */
    SELECT @V_SERIAL_NO = ISNULL(MAX(Serial_no),0)+1 FROM #TMP_InsideServiceTasks

    IF @V_DT_INTRUST = 1 --启用分布式
    BEGIN
        INSERT INTO #TMP_InsideServiceTasks(ServiceType,ServiceTypeName,ManagerID,Executor,ServiceTitle,CustomerCount,URL,MENU_ID,Status)
            SELECT 0,N'客户预登记',@IN_INPUT_MAN,@IN_INPUT_MAN,N'客户预登记('+B.CUST_NAME+')',1,N'/marketing/sell/checkin_list.jsp',N'30202',2
            FROM SRV_Intrust.INTRUST.dbo.TCUSTREGINFO A, TCustomers B
            WHERE A.CUST_ID = B.CUST_ID AND (B.SERVICE_MAN = @IN_INPUT_MAN OR A.LINK_MAN = @IN_INPUT_MAN)
				AND EXISTS (SELECT * FROM TSYSCONTROL WHERE FLAG_TYPE='PRECONTRACT_CHECK' AND VALUE=1) --预约要求审核才显示任务提示
        INSERT INTO #TMP_InsideServiceTasks(ServiceType,ServiceTypeName,ManagerID,Executor,ServiceTitle,CustomerCount,URL,MENU_ID,Status)
            SELECT 0,N'客户预约',@IN_INPUT_MAN,@IN_INPUT_MAN,N'客户('+B.CUST_NAME+')预约('+C.PRODUCT_JC+')',1,N'/marketing/sell/bespeak_list.jsp',N'30203',2
            FROM SRV_Intrust.INTRUST.dbo.TPRECONTRACT A, TCustomers B, SRV_Intrust.INTRUST.dbo.TPRODUCT C
            WHERE A.CUST_ID = B.CUST_ID AND A.PRODUCT_ID = C.PRODUCT_ID AND A.END_DATE >= @V_SYS_DATE
                AND (A.PRE_STATUS IN ('111301','111302')) AND (C.PRODUCT_STATUS = '110202' OR (C.PRODUCT_STATUS = '110203' AND C.OPEN_FLAG = 1))
                AND (B.SERVICE_MAN = @IN_INPUT_MAN OR A.LINK_MAN = @IN_INPUT_MAN)
                AND EXISTS (SELECT * FROM TSYSCONTROL WHERE FLAG_TYPE='PRECONTRACT_CHECK' AND VALUE=1) --预约要求审核才显示任务提示
    END
    ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
    BEGIN
        INSERT INTO #TMP_InsideServiceTasks(ServiceType,ServiceTypeName,ManagerID,Executor,ServiceTitle,CustomerCount,URL,MENU_ID,Status)
            SELECT 0,N'客户预登记',@IN_INPUT_MAN,@IN_INPUT_MAN,N'客户预登记('+B.CUST_NAME+')'+dbo.GETDATESTRYMD(A.REG_END_DATE),1,N'/marketing/sell/checkin_list.jsp',N'30202',2
            FROM INTRUST..TCUSTREGINFO A, TCustomers B
            WHERE A.CUST_ID = B.CUST_ID AND (B.SERVICE_MAN = @IN_INPUT_MAN OR A.LINK_MAN = @IN_INPUT_MAN)
                AND A.REG_END_DATE >= @V_SYS_DATE
                AND EXISTS (SELECT * FROM TSYSCONTROL WHERE FLAG_TYPE='PRECONTRACT_CHECK' AND VALUE=1) --预约要求审核才显示任务提示
        INSERT INTO #TMP_InsideServiceTasks(ServiceType,ServiceTypeName,ManagerID,Executor,ServiceTitle,CustomerCount,URL,MENU_ID,Status)
            SELECT 0,N'客户预约',@IN_INPUT_MAN,@IN_INPUT_MAN,N'客户('+B.CUST_NAME+')预约('+C.PRODUCT_JC+')',1,N'/marketing/sell/bespeak_list.jsp',N'30203',2
            FROM INTRUST..TPRECONTRACT A, TCustomers B, INTRUST..TPRODUCT C
            WHERE A.CUST_ID = B.CUST_ID AND A.PRODUCT_ID = C.PRODUCT_ID AND A.END_DATE >= @V_SYS_DATE
                AND (A.PRE_STATUS IN ('111301','111302')) AND (C.PRODUCT_STATUS = '110202' OR (C.PRODUCT_STATUS = '110203' AND C.OPEN_FLAG = 1))
                AND (B.SERVICE_MAN = @IN_INPUT_MAN OR A.LINK_MAN = @IN_INPUT_MAN)
                AND EXISTS (SELECT * FROM TSYSCONTROL WHERE FLAG_TYPE='PRECONTRACT_CHECK' AND VALUE=1) --预约要求审核才显示任务提示
    END
    SELECT * FROM #TMP_InsideServiceTasks ORDER BY SERIAL_NO DESC
GO
