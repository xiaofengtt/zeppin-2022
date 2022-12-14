USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_Treat_TServiceTasks    @IN_SERIAL_NO       INTEGER,         --序号（TSERVICETASKDETAIL.SERIAL_NO）
                                           @IN_RESULT          NVARCHAR(800),   --处理结果描述。STATUS=8时需要要有失败原因
                                           @IN_INPUT_MAN       INTEGER          --操作员

WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -50301000, @IBUSI_FLAG = 50301
    SELECT @SBUSI_NAME = N'处理服务任务', @SSUMMARY = N'处理服务任务'
    IF NOT EXISTS(SELECT 1 FROM TServiceTasks WHERE Serial_no = @IN_SERIAL_NO )
        RETURN @V_RET_CODE - 1  --服务任务信息不存在

    BEGIN TRANSACTION
    --只写处理结果,服务任务的状态自动依据任务明细处理情况而变
    UPDATE TServiceTasks
    SET Result = @IN_RESULT
    WHERE Serial_no = @IN_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    SET @SSUMMARY = N'处理服务任务，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
