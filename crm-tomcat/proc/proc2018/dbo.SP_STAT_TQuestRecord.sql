USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_STAT_TQuestRecord  @IN_ServiceTaskID        INTEGER,            --任务ID
                                       @IN_TOPIC_ID             INTEGER             --题目ID
WITH ENCRYPTION
AS
    SELECT COUNT(*) AS STAT_NUM,TOPIC_VALE
    FROM TQuestRecord
    where ServiceTaskID = @IN_ServiceTaskID and TOPIC_ID = @IN_TOPIC_ID
    group by TOPIC_VALE

    RETURN 100
GO
