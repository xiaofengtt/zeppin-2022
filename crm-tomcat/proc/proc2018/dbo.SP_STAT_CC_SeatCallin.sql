USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_STAT_CC_SeatCallin @IN_ManagerID  INTEGER, --客户经理ID
                                       @IN_Extension  INTEGER, --分机号码
                                       @IN_StartDate  INTEGER, --开始日期（yyyymmdd）
                                       @IN_EndDate    INTEGER, --结束日期（yyyymmdd）
                                       @IN_StatFlag   INTEGER, --统计方式按1日2周3月4季5年9输入区间
                                       @IN_INPUT_MAN  INTEGER  --操作员
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_LEFT_ID INT, @V_RIGHT_ID INT, @V_STARTDATE DATETIME, @V_ENDDATE DATETIME
    IF @IN_StartDate IS NULL SET @IN_StartDate = 0
    IF @IN_EndDate IS NULL SET @IN_EndDate = 0
    IF @IN_INPUT_MAN IS NULL SET @IN_INPUT_MAN = 0
    SELECT @V_LEFT_ID = LEFT_ID, @V_RIGHT_ID = RIGHT_ID FROM TCustManagerTree WHERE MANAGERID = @IN_INPUT_MAN
    IF ISDATE(@IN_StartDate) = 0 SET @IN_StartDate = 19000101
    IF ISDATE(@IN_EndDate) = 0 SET @IN_EndDate = 20991231
    SET @V_STARTDATE = dbo.GetDateTime(@IN_StartDate)
    SET @V_ENDDATE = dbo.GetDateTime(dbo.GETDATE(@IN_EndDate,1))

    IF @IN_StatFlag = 1 --按日
    BEGIN
        SELECT A.ManagerID, B.ManagerName, SUM(A.CallLength) AS Seconds, CONVERT(DEC(16,4),SUM(A.CallLength)/3600.0) AS Hours,
               dbo.GETDATESTRYMD(dbo.GETDATEINT(A.CallTime)) AS StatDate
        FROM TCallRecords A, TCustManagers B
        WHERE A.ManagerID = B.ManagerID AND A.Direction = 1 --呼入
           AND(A.ManagerID    = @IN_ManagerID   OR @IN_ManagerID   IS NULL OR @IN_ManagerID   = 0)
           AND(A.Extension    = @IN_Extension   OR @IN_Extension   IS NULL OR @IN_Extension   = 0)
           AND(A.CallTime BETWEEN @V_STARTDATE AND @V_ENDDATE)
           AND(A.ManagerID = @IN_INPUT_MAN OR @IN_INPUT_MAN = 0
                OR A.ManagerID IN (SELECT MANAGERID FROM TCustManagerTree WHERE LEFT_ID BETWEEN @V_LEFT_ID AND @V_RIGHT_ID))
        GROUP BY A.ManagerID, B.ManagerName, dbo.GETDATEINT(A.CallTime)
    END
    ELSE IF @IN_StatFlag = 2 --按周
    BEGIN
        SELECT A.ManagerID, B.ManagerName, SUM(A.CallLength) AS Seconds, CONVERT(DEC(16,4),SUM(A.CallLength)/3600.0) AS Hours,
               CONVERT(NVARCHAR(20),DATEPART(year,A.CallTime))+N'年第'+CONVERT(NVARCHAR(20),DATEPART(week,A.CallTime))+N'周' AS StatDate
        FROM TCallRecords A, TCustManagers B
        WHERE A.ManagerID = B.ManagerID AND A.Direction = 1 --呼入
           AND(A.ManagerID    = @IN_ManagerID   OR @IN_ManagerID   IS NULL OR @IN_ManagerID   = 0)
           AND(A.Extension    = @IN_Extension   OR @IN_Extension   IS NULL OR @IN_Extension   = 0)
           AND(A.CallTime BETWEEN @V_STARTDATE AND @V_ENDDATE)
           AND(A.ManagerID = @IN_INPUT_MAN OR @IN_INPUT_MAN = 0
                OR A.ManagerID IN (SELECT MANAGERID FROM TCustManagerTree WHERE LEFT_ID BETWEEN @V_LEFT_ID AND @V_RIGHT_ID))
        GROUP BY A.ManagerID, B.ManagerName, DATEPART(year,A.CallTime), DATEPART(week,A.CallTime)
    END
    ELSE IF @IN_StatFlag = 3 --按月
    BEGIN
        SELECT A.ManagerID, B.ManagerName, SUM(A.CallLength) AS Seconds, CONVERT(DEC(16,4),SUM(A.CallLength)/3600.0) AS Hours,
               CONVERT(NVARCHAR(20),DATEPART(year,A.CallTime))+N'年'+CONVERT(NVARCHAR(20),DATEPART(month,A.CallTime))+N'月' AS StatDate
        FROM TCallRecords A, TCustManagers B
        WHERE A.ManagerID = B.ManagerID AND A.Direction = 1 --呼入
           AND(A.ManagerID    = @IN_ManagerID   OR @IN_ManagerID   IS NULL OR @IN_ManagerID   = 0)
           AND(A.Extension    = @IN_Extension   OR @IN_Extension   IS NULL OR @IN_Extension   = 0)
           AND(A.CallTime BETWEEN @V_STARTDATE AND @V_ENDDATE)
           AND(A.ManagerID = @IN_INPUT_MAN OR @IN_INPUT_MAN = 0
                OR A.ManagerID IN (SELECT MANAGERID FROM TCustManagerTree WHERE LEFT_ID BETWEEN @V_LEFT_ID AND @V_RIGHT_ID))
        GROUP BY A.ManagerID, B.ManagerName, DATEPART(year,A.CallTime), DATEPART(month,A.CallTime)
    END
    ELSE IF @IN_StatFlag = 4 --按季
    BEGIN
        SELECT A.ManagerID, B.ManagerName, SUM(A.CallLength) AS Seconds, CONVERT(DEC(16,4),SUM(A.CallLength)/3600.0) AS Hours,
               CONVERT(NVARCHAR(20),DATEPART(year,A.CallTime))+N'年'+CONVERT(NVARCHAR(20),DATEPART(quarter,A.CallTime))+N'季度' AS StatDate
        FROM TCallRecords A, TCustManagers B
        WHERE A.ManagerID = B.ManagerID AND A.Direction = 1 --呼入
           AND(A.ManagerID    = @IN_ManagerID   OR @IN_ManagerID   IS NULL OR @IN_ManagerID   = 0)
           AND(A.Extension    = @IN_Extension   OR @IN_Extension   IS NULL OR @IN_Extension   = 0)
           AND(A.CallTime BETWEEN @V_STARTDATE AND @V_ENDDATE)
           AND(A.ManagerID = @IN_INPUT_MAN OR @IN_INPUT_MAN = 0
                OR A.ManagerID IN (SELECT MANAGERID FROM TCustManagerTree WHERE LEFT_ID BETWEEN @V_LEFT_ID AND @V_RIGHT_ID))
        GROUP BY A.ManagerID, B.ManagerName, DATEPART(year,A.CallTime), DATEPART(quarter,A.CallTime)
    END
    ELSE IF @IN_StatFlag = 5 --按年
    BEGIN
        SELECT A.ManagerID, B.ManagerName, SUM(A.CallLength) AS Seconds, CONVERT(DEC(16,4),SUM(A.CallLength)/3600.0) AS Hours,
               CONVERT(NVARCHAR(20),DATEPART(year,A.CallTime))+N'年' AS StatDate
        FROM TCallRecords A, TCustManagers B
        WHERE A.ManagerID = B.ManagerID AND A.Direction = 1 --呼入
           AND(A.ManagerID    = @IN_ManagerID   OR @IN_ManagerID   IS NULL OR @IN_ManagerID   = 0)
           AND(A.Extension    = @IN_Extension   OR @IN_Extension   IS NULL OR @IN_Extension   = 0)
           AND(A.CallTime BETWEEN @V_STARTDATE AND @V_ENDDATE)
           AND(A.ManagerID = @IN_INPUT_MAN OR @IN_INPUT_MAN = 0
                OR A.ManagerID IN (SELECT MANAGERID FROM TCustManagerTree WHERE LEFT_ID BETWEEN @V_LEFT_ID AND @V_RIGHT_ID))
        GROUP BY A.ManagerID, B.ManagerName, DATEPART(year,A.CallTime)
    END
    ELSE IF @IN_StatFlag = 9 --按输入区间
    BEGIN
        SELECT A.ManagerID, B.ManagerName, SUM(A.CallLength) AS Seconds, CONVERT(DEC(16,4),SUM(A.CallLength)/3600.0) AS Hours,
               CONVERT(NVARCHAR(8),dbo.GETDATEINT(MIN(A.CallTime)))+N' - '+CONVERT(NVARCHAR(8),dbo.GETDATEINT(MAX(A.CallTime))) AS StatDate
        FROM TCallRecords A, TCustManagers B
        WHERE A.ManagerID = B.ManagerID AND A.Direction = 1 --呼入
           AND(A.ManagerID    = @IN_ManagerID   OR @IN_ManagerID   IS NULL OR @IN_ManagerID   = 0)
           AND(A.Extension    = @IN_Extension   OR @IN_Extension   IS NULL OR @IN_Extension   = 0)
           AND(A.CallTime BETWEEN @V_STARTDATE AND @V_ENDDATE)
           AND(A.ManagerID = @IN_INPUT_MAN OR @IN_INPUT_MAN = 0
                OR A.ManagerID IN (SELECT MANAGERID FROM TCustManagerTree WHERE LEFT_ID BETWEEN @V_LEFT_ID AND @V_RIGHT_ID))
        GROUP BY A.ManagerID, B.ManagerName
    END
    ELSE --统计方式无效返回明细
        SELECT A.ManagerID, B.ManagerName, A.CallLength, CONVERT(DEC(16,4),A.CallLength/3600.0) AS Hours,
               CONVERT(NVARCHAR(60),A.CallTime,120) AS StatDate
        FROM TCallRecords A, TCustManagers B
        WHERE A.ManagerID = B.ManagerID AND A.Direction = 1 --呼入
           AND(A.ManagerID    = @IN_ManagerID   OR @IN_ManagerID   IS NULL OR @IN_ManagerID   = 0)
           AND(A.Extension    = @IN_Extension   OR @IN_Extension   IS NULL OR @IN_Extension   = 0)
           AND(A.CallTime BETWEEN @V_STARTDATE AND @V_ENDDATE)
           AND(A.ManagerID = @IN_INPUT_MAN OR @IN_INPUT_MAN = 0
                OR A.ManagerID IN (SELECT MANAGERID FROM TCustManagerTree WHERE LEFT_ID BETWEEN @V_LEFT_ID AND @V_RIGHT_ID))
GO
