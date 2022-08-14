USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_Allocate_TServiceTasks    @IN_SERIALNO          INTEGER,         --序号（TServiceTask.Serial_no）
                                              @IN_EXECUTOR          INTEGER,         --分配给该执行者（TServiceTasks.Executor）
                                              @IN_INPUT_MAN         INTEGER          --操作员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -50301000, @IBUSI_FLAG = 50301
    SELECT @SBUSI_NAME = N'分配服务任务', @SSUMMARY = N'分配服务任务'
    IF NOT EXISTS(SELECT 1 FROM TServiceTasks WHERE Serial_no = @IN_SERIALNO )
        RETURN @V_RET_CODE - 1  --对应的任务列表不存在
	
	IF @IN_EXECUTOR = 0 SET @IN_EXECUTOR = NULL
		
    BEGIN TRANSACTION
	
    UPDATE TServiceTasks
        SET Executor = ISNULL(@IN_EXECUTOR,Executor), Status = 2
        WHERE Serial_no = @IN_SERIALNO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'分配服务任务，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
