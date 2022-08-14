USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_STAT_CC_SeatAbandon @IN_ManagerID  INTEGER, --客户经理ID
                                        @IN_Extension  INTEGER, --分机号码
                                        @IN_StartDate  INTEGER, --开始日期（yyyymmdd）
                                        @IN_EndDate    INTEGER, --结束日期（yyyymmdd）
                                        @IN_INPUT_MAN  INTEGER  --操作员
WITH ENCRYPTION
AS
    DECLARE @V_LEFT_ID INT, @V_RIGHT_ID INT, @V_STARTDATE DATETIME, @V_ENDDATE DATETIME
    IF @IN_StartDate IS NULL SET @IN_StartDate = 0
    IF @IN_EndDate IS NULL SET @IN_EndDate = 0
    IF @IN_INPUT_MAN IS NULL SET @IN_INPUT_MAN = 0
    SELECT @V_LEFT_ID = LEFT_ID, @V_RIGHT_ID = RIGHT_ID FROM TCustManagerTree WHERE MANAGERID = @IN_INPUT_MAN
    IF ISDATE(@IN_StartDate) = 0 SET @IN_StartDate = 19000101
    IF ISDATE(@IN_EndDate) = 0 SET @IN_EndDate = 20991231
    SET @V_STARTDATE = dbo.GetDateTime(@IN_StartDate)
    SET @V_ENDDATE = dbo.GetDateTime(dbo.GETDATE(@IN_EndDate,1))

    SELECT A.*, B.ManagerName, D.CUST_NAME, C.Contactor
        FROM TCallRecords A LEFT JOIN TCustomerContacts C ON A.ContactID = C.ContactID
             LEFT JOIN TCustomers D ON A.CUST_ID = D.CUST_ID, TCustManagers B
        WHERE A.ManagerID = B.ManagerID AND A.Status = 9 --放弃接听
           AND(A.ManagerID    = @IN_ManagerID   OR @IN_ManagerID   IS NULL OR @IN_ManagerID   = 0)
           AND(A.Extension    = @IN_Extension   OR @IN_Extension   IS NULL OR @IN_Extension   = 0)
           AND(A.CallTime BETWEEN @V_STARTDATE AND @V_ENDDATE)
           AND(A.ManagerID = @IN_INPUT_MAN OR @IN_INPUT_MAN = 0
                OR A.ManagerID IN (SELECT MANAGERID FROM TCustManagerTree WHERE LEFT_ID BETWEEN @V_LEFT_ID AND @V_RIGHT_ID))
GO
