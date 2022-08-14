USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_IntradayCallNumber @IN_DATE INTEGER  --日期yyyymmdd
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_STARTDATE DATETIME, @V_ENDDATE DATETIME

    IF ISDATE(@IN_DATE) = 1
    BEGIN
        SET @V_STARTDATE = dbo.GetDateTime(@IN_DATE)
        SET @V_ENDDATE = dbo.GetDateTime(dbo.GETDATE(@IN_DATE,1))
    END
    DECLARE @VT_IntradayCallNumber TABLE(
        Direction   INT, --1被叫接听 2主叫拨打
        NumberWait  INT, --待处理数量
        NumberSum   INT  --总数量
    )
    --待处理的
    INSERT INTO @VT_IntradayCallNumber(Direction,NumberWait)
        SELECT Direction, COUNT(*) AS NUMBER
            FROM TCallRecords
            WHERE ((CallTime BETWEEN @V_STARTDATE AND @V_ENDDATE) OR @IN_DATE IS NULL OR @IN_DATE = 0)
                AND(Status = 2)
            GROUP BY Direction, Status
    --总的
    INSERT INTO @VT_IntradayCallNumber(Direction,NumberSum)
        SELECT Direction, COUNT(*) AS NUMBER
            FROM TCallRecords
            WHERE ((CallTime BETWEEN @V_STARTDATE AND @V_ENDDATE) OR @IN_DATE IS NULL OR @IN_DATE = 0)
            GROUP BY Direction, Status
    --按方向汇总输出
    SELECT Direction, SUM(NumberWait) AS NumberWait, SUM(NumberSum) AS NumberSum
        FROM @VT_IntradayCallNumber
        GROUP BY Direction
GO
