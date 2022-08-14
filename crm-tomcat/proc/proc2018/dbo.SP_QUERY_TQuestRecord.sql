USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TQuestRecord  @IN_SERIAL_NO           INTEGER = 0,
                                        @IN_ServiceTaskID       INTEGER = 0,            --任务ID
                                        @IN_ServiceTaskDetailID INTEGER = 0,            --任务明细ID
                                        @IN_TargetCustID        INTEGER = 0,            --客户ID
                                        @IN_Ques_ID             INTEGER = 0,            --问卷ID
                                        @IN_TOPIC_ID            INTEGER = 0,            --题目ID
                                        @IN_INPUT_MAN           INT = 0                 --操作员
WITH ENCRYPTION
AS
    IF ISNULL(@IN_SERIAL_NO,0) <> 0
        BEGIN
            SELECT SERIAL_NO,ServiceTaskID,TargetCustID,TargetCustName,TOPIC_ID,TOPIC_VALE,TOPIC_Score,InsertTime,InsertMan
            FROM TQuestRecord
            WHERE SERIAL_NO = @IN_SERIAL_NO
        END
    ELSE
        BEGIN
            SELECT SERIAL_NO,ServiceTaskID,TargetCustID,TargetCustName,TOPIC_ID,TOPIC_VALE,TOPIC_Score,InsertTime,InsertMan
            FROM TQuestRecord
            WHERE (ServiceTaskID = @IN_ServiceTaskID OR ISNULL(@IN_ServiceTaskID,0) = 0 )
                AND (ServiceTaskDetailID = @IN_ServiceTaskDetailID OR ISNULL(@IN_ServiceTaskDetailID,0) = 0 )
                AND (TargetCustID = @IN_TargetCustID OR ISNULL(@IN_TargetCustID,0) = 0 )
                AND (Ques_ID = @IN_Ques_ID OR ISNULL(@IN_Ques_ID,0) = 0 )
                AND (TOPIC_ID = @IN_TOPIC_ID OR ISNULL(@IN_TOPIC_ID,0) = 0 )
        END

    RETURN 100
GO
