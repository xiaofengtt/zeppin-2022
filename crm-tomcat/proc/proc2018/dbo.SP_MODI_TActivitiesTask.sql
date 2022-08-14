USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TActivitiesTask    @IN_SERIAL_NO           INT,            --活动任务序列号
                                            @IN_ActiveTaskTitle     NVARCHAR(64),   -- 活动标题
                                            @IN_ActiveTaskType      NVARCHAR(8),        -- 活动任务类别
                                            @IN_Content             NVARCHAR(512),  -- 活动任务详细内容
                                            @IN_BeginDate           DATETIME,       -- 开始时间
                                            @IN_EndDate             DATETIME,       -- 结束时间
                                            @IN_Executor            INT,            -- 执行人
                                            @IN_CompleteTime        DATETIME,       -- 完成时间
                                            @IN_Remark              NVARCHAR(512),  -- 完成描述
                                            @IN_TaskFlag            INT,            -- 活动任务标记
                                            @IN_CheckMan            INT,            -- 审核人
                                            @IN_CheckOption         NVARCHAR(512),  -- 审核描述
                                            @IN_INPUT_MAN           INT = 0         -- 操作员
WITH ENCRYPTION
AS
    DECLARE @V_ActiveTaskTypeName NVARCHAR(128)
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -30402000, @IBUSI_FLAG = 30402
    SELECT @SBUSI_NAME = N'添加营销活动任务信息', @SSUMMARY = N'添加营销活动任务信息'

    SELECT @V_ActiveTaskTypeName = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_ActiveTaskType
    SELECT @V_ActiveTaskTypeName = ISNULL(@V_ActiveTaskTypeName,'')

    BEGIN TRANSACTION
    UPDATE TActivitiesTask
    SET ActiveTaskTitle = @IN_ActiveTaskTitle,
        ActiveTaskType = @IN_ActiveTaskType,
        ActiveTaskTypeName = @V_ActiveTaskTypeName,
        Content = @IN_Content,
        BeginDate = @IN_BeginDate,
        EndDate = @IN_EndDate,
        Executor = @IN_Executor,
        CompleteTime = @IN_CompleteTime,
        Remark = @IN_Remark,
        TaskFlag = @IN_TaskFlag,
        CheckMan = @IN_CheckMan,
        Check_Options = @IN_CheckOption
    WHERE Serial_no = @IN_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'修改营销活动任务信息，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
