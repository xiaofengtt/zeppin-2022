USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_STAT_CC_SeatActivity @IN_ManagerID  INTEGER, --客户经理ID
                                         @IN_Extension  INTEGER, --分机号码
                                         @IN_StartDate  INTEGER, --开始日期（yyyymmdd）
                                         @IN_EndDate    INTEGER, --结束日期（yyyymmdd）
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
    DECLARE @VT_CC_SeatActivity TABLE(
        id          bigint,         --编号
        channel     int,            --通道号
		statusName  NVARCHAR(30),   --状态名称
        starttime   datetime,       --状态起始时间
        endtime     datetime,       --状态结束时间
        extno       NVARCHAR(20),    --分机号
        uid         NVARCHAR(50),    --工号
        ManagerID   INTEGER,        --客户经理ID
        ManagerName NVARCHAR(30)   --客户经理名称

    )
    INSERT INTO @VT_Managers(ManagerID,ManagerName,Extension)
        SELECT ManagerID,ManagerName,Extension
        FROM TCustManagers A
        WHERE (A.ManagerID    = @IN_ManagerID   OR @IN_ManagerID   IS NULL OR @IN_ManagerID   = 0)
           AND(A.Extension    = @IN_Extension   OR @IN_Extension   IS NULL OR @IN_Extension   = 0)
         
    IF @V_DT_CALL = 1 --启用分布式
    BEGIN
        INSERT INTO @VT_CC_SeatActivity(id,channel,statusName,starttime,endtime,extno,uid,ManagerID,ManagerName)
            SELECT B.id, B.channel,B.status,B.starttime,B.endtime,B.extno,B.uid,A.ManagerID,A.ManagerName
            FROM @VT_Managers A, SRV_CallCenter.CallCenter.dbo.rc_statrecord B
            WHERE A.Extension = B.extno
                AND((B.starttime BETWEEN @V_STARTDATE AND @V_ENDDATE)OR(B.endtime BETWEEN @V_STARTDATE AND @V_ENDDATE))
    END
    ELSE IF (@V_DT_CALL = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'CallCenter') --本地数据库
    BEGIN
        INSERT INTO @VT_CC_SeatActivity(id,channel,statusName,starttime,endtime,extno,uid,ManagerID,ManagerName)
            SELECT B.id, B.channel,B.status,B.starttime,B.endtime,B.extno,B.uid,A.ManagerID,A.ManagerName
            FROM @VT_Managers A, CallCenter..statrecord B
            WHERE A.Extension = B.extno
                AND((B.starttime BETWEEN @V_STARTDATE AND @V_ENDDATE)OR(B.endtime BETWEEN @V_STARTDATE AND @V_ENDDATE))
    END
    SELECT * FROM @VT_CC_SeatActivity ORDER BY starttime
GO
