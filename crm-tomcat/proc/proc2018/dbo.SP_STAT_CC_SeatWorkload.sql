USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_STAT_CC_SeatWorkload @IN_ManagerID  INTEGER, --客户经理ID
                                         @IN_Extension  INTEGER, --分机号码
                                         @IN_StartDate  INTEGER, --开始日期（yyyymmdd）
                                         @IN_EndDate    INTEGER, --结束日期（yyyymmdd）
                                         @IN_StatFlag   INTEGER, --统计方式按1日2周3月4季5年9输入区间
                                         @IN_INPUT_MAN  INTEGER  --操作员
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_BOOK_CODE INT, @V_INTRUST_OPERATOR INT
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

    DECLARE @VT_Managers TABLE(
        ManagerID   INTEGER,        --客户经理ID
        ManagerName NVARCHAR(30),   --客户经理名称
        Extension   INTEGER         --分机号码
    )
    DECLARE @VT_CC_SeatWorkload TABLE(
        id          bigint,         --编号
        channel     int,            --通道号
		statusName  NVARCHAR(30),   --状态名称
        starttime   datetime,       --状态起始时间
        endtime     datetime,       --状态结束时间
        extno       NVARCHAR(20),    --分机号
        uid         NVARCHAR(50),    --工号
        ManagerID   INTEGER,        --客户经理ID
        ManagerName NVARCHAR(30),   --客户经理名称
        lenSecond   INTEGER         --时长（秒）
    )
    INSERT INTO @VT_Managers(ManagerID,ManagerName,Extension)
        SELECT ManagerID,ManagerName,Extension
        FROM TCustManagers A
        WHERE (A.ManagerID    = @IN_ManagerID   OR @IN_ManagerID   IS NULL OR @IN_ManagerID   = 0)
           AND(A.Extension    = @IN_Extension   OR @IN_Extension   IS NULL OR @IN_Extension   = 0)
   

    IF @V_DT_CALL = 1 --启用分布式
    BEGIN
        INSERT INTO @VT_CC_SeatWorkload(id,channel,statusName,starttime,endtime,extno,uid,ManagerID,ManagerName)
            SELECT B.id, B.channel,B.status,B.starttime,B.endtime,B.extno,B.uid,A.ManagerID,A.ManagerName
            FROM @VT_Managers A, SRV_CallCenter.CallCenter.dbo.rc_statrecord B
            WHERE A.Extension = B.extno
                AND((B.starttime BETWEEN @V_STARTDATE AND @V_ENDDATE)OR(B.endtime BETWEEN @V_STARTDATE AND @V_ENDDATE))
                --统计工作量时只考虑以下状态，分别归类为：1空闲、2通话、4离线、3示忙(离开)
                --AND(B.status IN (0,  7,14,15,19,32,  20,  11,35,38))
    END
    UPDATE @VT_CC_SeatWorkload SET lenSecond = DATEDIFF(second,starttime,endtime)

    IF @IN_StatFlag = 1 --按日
    BEGIN
        SELECT ManagerID, ManagerName, SUM(lenSecond) AS Seconds, CONVERT(DEC(16,4),SUM(lenSecond)/3600.0) AS Hours,
               statusName, dbo.GETDATESTRYMD(dbo.GETDATEINT(starttime)) AS StatDate
        FROM @VT_CC_SeatWorkload
        WHERE ((starttime BETWEEN @V_STARTDATE AND @V_ENDDATE)OR(endtime BETWEEN @V_STARTDATE AND @V_ENDDATE))
        GROUP BY ManagerID, ManagerName, statusName, dbo.GETDATEINT(starttime)
    END
    ELSE IF @IN_StatFlag = 2 --按周
    BEGIN
        SELECT ManagerID, ManagerName, SUM(lenSecond) AS Seconds, CONVERT(DEC(16,4),SUM(lenSecond)/3600.0) AS Hours,
               statusName, CONVERT(NVARCHAR(20),DATEPART(year,starttime))+N'年第'+CONVERT(NVARCHAR(20),DATEPART(week,starttime))+N'周' AS StatDate
        FROM @VT_CC_SeatWorkload
        WHERE ((starttime BETWEEN @V_STARTDATE AND @V_ENDDATE)OR(endtime BETWEEN @V_STARTDATE AND @V_ENDDATE))
        GROUP BY ManagerID, ManagerName, statusName, DATEPART(year,starttime), DATEPART(week,starttime)
    END
    ELSE IF @IN_StatFlag = 3 --按月
    BEGIN
        SELECT ManagerID, ManagerName, SUM(lenSecond) AS Seconds, CONVERT(DEC(16,4),SUM(lenSecond)/3600.0) AS Hours,
              statusName, CONVERT(NVARCHAR(20),DATEPART(year,starttime))+N'年'+CONVERT(NVARCHAR(20),DATEPART(month,starttime))+N'月' AS StatDate
        FROM @VT_CC_SeatWorkload
        WHERE ((starttime BETWEEN @V_STARTDATE AND @V_ENDDATE)OR(endtime BETWEEN @V_STARTDATE AND @V_ENDDATE))
        GROUP BY ManagerID, ManagerName, statusName, DATEPART(year,starttime), DATEPART(month,starttime)
    END
    ELSE IF @IN_StatFlag = 4 --按季
    BEGIN
        SELECT ManagerID, ManagerName, SUM(lenSecond) AS Seconds, CONVERT(DEC(16,4),SUM(lenSecond)/3600.0) AS Hours,
               statusName, CONVERT(NVARCHAR(20),DATEPART(year,starttime))+N'年'+CONVERT(NVARCHAR(20),DATEPART(quarter,starttime))+N'季度' AS StatDate
        FROM @VT_CC_SeatWorkload
        WHERE ((starttime BETWEEN @V_STARTDATE AND @V_ENDDATE)OR(endtime BETWEEN @V_STARTDATE AND @V_ENDDATE))
        GROUP BY ManagerID, ManagerName, statusName, DATEPART(year,starttime), DATEPART(quarter,starttime)
    END
    ELSE IF @IN_StatFlag = 5 --按年
    BEGIN
        SELECT ManagerID, ManagerName, SUM(lenSecond) AS Seconds, CONVERT(DEC(16,4),SUM(lenSecond)/3600.0) AS Hours,
               statusName, CONVERT(NVARCHAR(20),DATEPART(year,starttime))+N'年' AS StatDate
        FROM @VT_CC_SeatWorkload
        WHERE ((starttime BETWEEN @V_STARTDATE AND @V_ENDDATE)OR(endtime BETWEEN @V_STARTDATE AND @V_ENDDATE))
        GROUP BY ManagerID, ManagerName, statusName, DATEPART(year,starttime)
    END
    ELSE IF @IN_StatFlag = 9 --按输入区间
    BEGIN
        SELECT ManagerID, ManagerName, SUM(lenSecond) AS Seconds, CONVERT(DEC(16,4),SUM(lenSecond)/3600.0) AS Hours,
               statusName, CONVERT(NVARCHAR(8),dbo.GETDATEINT(MIN(starttime)))+N' - '+CONVERT(NVARCHAR(8),dbo.GETDATEINT(MAX(endtime))) AS StatDate
        FROM @VT_CC_SeatWorkload
        WHERE ((starttime BETWEEN @V_STARTDATE AND @V_ENDDATE)OR(endtime BETWEEN @V_STARTDATE AND @V_ENDDATE))
        GROUP BY ManagerID, ManagerName, statusName
    END
    ELSE --统计方式无效返回明细
        SELECT ManagerID, ManagerName, lenSecond AS Seconds, CONVERT(DEC(16,4),lenSecond/3600.0) AS Hours,
               statusName, CONVERT(NVARCHAR(60),starttime,120)+N' - '+CONVERT(NVARCHAR(60),endtime,120) AS StatDate
        FROM @VT_CC_SeatWorkload
GO
