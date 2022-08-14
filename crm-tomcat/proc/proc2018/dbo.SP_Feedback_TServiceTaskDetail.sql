USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_Feedback_TServiceTaskDetail @IN_SERIAL_NO       INTEGER,         --序号（TSERVICETASKDETAIL.SERIAL_NO）
                                                @IN_FEEDBACKCONTENT NVARCHAR(800),   --反馈内容
                                                @IN_SATISFACTION    INTEGER,         --客户满意度1-5由低到高
                                                @IN_INPUT_MAN       INTEGER          --操作员

WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -50305000, @IBUSI_FLAG = 50307
    SELECT @SBUSI_NAME = N'对服务任务之明细进行反馈处理', @SSUMMARY = N'对服务任务之明细进行反馈处理'
    IF NOT EXISTS(SELECT 1 FROM TServiceTaskDetail WHERE Serial_no = @IN_SERIAL_NO )
        RETURN @V_RET_CODE - 2  --服务任务之明细信息不存在
    DECLARE @V_TASKSERIALNO INT, @V_TASK_STATUS INT,@V_CompleteCount INT
    SELECT @V_TASKSERIALNO = TaskSerialNO FROM TServiceTaskDetail WHERE Serial_no = @IN_SERIAL_NO

    BEGIN TRANSACTION
    --反馈处理后Status为4已处理
    UPDATE TServiceTaskDetail
    SET Status = 4,
        FeedbackContent = @IN_FEEDBACKCONTENT,
        Satisfaction = @IN_SATISFACTION
    WHERE Serial_no = @IN_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

     --修改任务的完成数
    SELECT @V_CompleteCount = COUNT(*) FROM TServiceTaskDetail WHERE TaskSerialNO = @V_TASKSERIALNO AND Status >= 4
    UPDATE TServiceTasks
        SET CompleteCount = @V_CompleteCount
        WHERE Serial_no = @V_TASKSERIALNO
    IF @@ERROR <> 0
    BEGIN
       ROLLBACK TRANSACTION
       RETURN -100
    END

    --20100118 DONGYG 取当前任务的明细的状态的最小值做任务状态即可，若任务状态是"4已处理8失败9作废"之一，设置完成时间
    SELECT @V_TASK_STATUS = MIN(Status) FROM TServiceTaskDetail WHERE TaskSerialNO = @V_TASKSERIALNO
    UPDATE TServiceTasks
        SET Status = @V_TASK_STATUS,
            CompleteTime = CASE WHEN @V_TASK_STATUS >= 4 THEN GETDATE() ELSE NULL END
        WHERE Serial_no = @V_TASKSERIALNO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'对服务任务之明细进行反馈处理，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    COMMIT TRANSACTION

    RETURN 100

GO
