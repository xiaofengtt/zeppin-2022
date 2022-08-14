USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_STAT_CC_CheckRecords @IN_ManagerID  INTEGER, --客户经理ID
                                         @IN_Extension  INTEGER, --分机号码
                                         @IN_StartDate  INTEGER, --开始日期（yyyymmdd）
                                         @IN_EndDate    INTEGER, --结束日期（yyyymmdd）
                                         @IN_INPUT_MAN  INTEGER  --操作员
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_BOOK_CODE INT, @V_USER_ID INT
    DECLARE @RET INT, @V_DT_CALL INT
    SELECT @V_DT_CALL = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'DT_CALL'

    DECLARE @V_LEFT_ID INT, @V_RIGHT_ID INT, @V_STARTDATE DATETIME, @V_ENDDATE DATETIME
    IF @IN_StartDate IS NULL SET @IN_StartDate = 0
    IF @IN_EndDate IS NULL SET @IN_EndDate = 0
    IF @IN_INPUT_MAN IS NULL SET @IN_INPUT_MAN = 0
    SELECT @V_LEFT_ID = LEFT_ID, @V_RIGHT_ID = RIGHT_ID FROM TCustManagerTree WHERE MANAGERID = @IN_INPUT_MAN
    IF ISDATE(@IN_StartDate) = 0 SET @IN_StartDate = 19000101
    IF ISDATE(@IN_EndDate) = 0 SET @IN_EndDate = 20991231
    SET @V_STARTDATE = dbo.GetDateTime(@IN_StartDate)
    SET @V_ENDDATE = dbo.GetDateTime(dbo.GETDATE(@IN_EndDate,1))

    SELECT A.[Serial_no]
		  ,[Direction]
		  ,[CallTime]
		  ,[CallLength]
		  ,[ManagerID]
		  ,[Extension]
		  ,[CUST_ID]
		  ,[ContactID]
		  ,[PhoneNumber]
		  ,[BusinessID]
		  ,[Content]
		  ,[Status]
		  ,[InsertTime]
		  ,[CallCenterID]
		  , CONVERT(NVARCHAR(260),'') AS FILENAME, CONVERT(NVARCHAR(260),'') AS URL,
           CONVERT(NVARCHAR(100),'') AS CUST_NAME, CONVERT(NVARCHAR(30),'') AS Contactor,
           CONVERT(NVARCHAR(60),'') AS ManagerName
        INTO #TMP_CheckRecords
        FROM TCallRecords A
        WHERE (A.ManagerID    = @IN_ManagerID   OR @IN_ManagerID   IS NULL OR @IN_ManagerID   = 0)
           AND(A.Extension    = @IN_Extension   OR @IN_Extension   IS NULL OR @IN_Extension   = 0)
           AND(A.CallTime BETWEEN @V_STARTDATE AND @V_ENDDATE)
           AND(A.ManagerID = @IN_INPUT_MAN OR @IN_INPUT_MAN = 0
                OR A.ManagerID IN (SELECT ManagerID FROM TCustManagerTree WHERE LEFT_ID BETWEEN @V_LEFT_ID AND @V_RIGHT_ID))

    SELECT TOP 1 @V_USER_ID = [USER_ID] FROM TSYSTEMINFO	-- WHERE [USER_ID]=15 --建信的USER_ID=15
    SET @V_USER_ID = ISNULL(@V_USER_ID,0)
    IF @V_DT_CALL = 1 --启用分布式
    BEGIN
		IF @V_USER_ID = 15 --建信
		BEGIN
			UPDATE #TMP_CheckRecords SET FILENAME = B.recordfile, URL = B.recordfile
				FROM #TMP_CheckRecords A, SRV_CallCenter.CCServer2008.dbo.Calllog B
				WHERE A.CallCenterID = B.id
		END
		ELSE
		BEGIN
			UPDATE #TMP_CheckRecords SET FILENAME = C.path, URL = C.path2
				FROM #TMP_CheckRecords A, SRV_CallCenter.HelpCenter.dbo.calllog B, SRV_CallCenter.tvc_voice.dbo.files C
				WHERE A.CallCenterID = B.id AND B.id = C.callid
        END
    END
    ELSE IF (@V_DT_CALL = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'HelpCenter') --本地数据库
        UPDATE #TMP_CheckRecords SET FILENAME = C.path, URL = C.path2
            FROM #TMP_CheckRecords A, HelpCenter..calllog B, tvc_voice..files C
            WHERE A.CallCenterID = B.id AND B.id = C.callid
    UPDATE #TMP_CheckRecords SET CUST_NAME = B.CUST_NAME
        FROM #TMP_CheckRecords A, TCustomers B
        WHERE A.CUST_ID = B.CUST_ID
    UPDATE #TMP_CheckRecords SET Contactor = B.Contactor
        FROM #TMP_CheckRecords A, TCustomerContacts B
        WHERE A.ContactID = B.ContactID
    UPDATE #TMP_CheckRecords SET ManagerName = B.ManagerName
        FROM #TMP_CheckRecords A, TCustManagers B
        WHERE A.ManagerID = B.ManagerID

    SELECT * FROM #TMP_CheckRecords
GO
