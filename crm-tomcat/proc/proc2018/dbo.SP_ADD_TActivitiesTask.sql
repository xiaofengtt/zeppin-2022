USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TActivitiesTask     @IN_Active_Serial_no    INT,            -- 活动编号
                                            @IN_ActiveTaskTitle     NVARCHAR(64),   -- 活动标题
                                            @IN_ActiveTaskType      NVARCHAR(8),        -- 活动任务类别
                                            @IN_Content             NVARCHAR(512),  -- 活动任务详细内容
                                            @IN_BeginDate           DATETIME,       -- 开始时间
                                            @IN_EndDate             DATETIME,       -- 结束时间
                                            @IN_Executor            INT,            -- 执行人
                                            @IN_INPUT_MAN           INT = 0         -- 操作员
WITH ENCRYPTION
AS
    DECLARE @V_ActiveTaskType_Name NVARCHAR(128),@V_TaskFlag INT
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -30402000, @IBUSI_FLAG = 30402
    SELECT @SBUSI_NAME = N'添加营销活动任务信息', @SSUMMARY = N'添加营销活动任务信息'

    SELECT @V_ActiveTaskType_Name = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_ActiveTaskType
    SELECT @V_ActiveTaskType_Name = ISNULL(@V_ActiveTaskType_Name,'')
    SET @V_TaskFlag = 1;

    BEGIN TRANSACTION
    INSERT INTO TActivitiesTask(Active_Serial_no,ActiveTaskTitle,ActiveTaskType,ActiveTaskTypeName,Content,BeginDate,EndDate,Executor,TaskFlag)
    VALUES(@IN_Active_Serial_no,@IN_ActiveTaskTitle,@IN_ActiveTaskType,@V_ActiveTaskType_Name,@IN_Content,@IN_BeginDate,@IN_EndDate,@IN_Executor,@V_TaskFlag);
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    -- 修改活动信息的状态 改为进行中
    UPDATE TACTIVITIES
    SET ACTIVE_FLAG = 2
    WHERE SERIAL_NO = @IN_Active_Serial_no
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'添加营销活动任务信息，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
