USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TACTIVITIES    @IN_SERIAL_NO           INT,            --活动序列号
                                        @IN_ACTIVE_TYPE         NVARCHAR(16),    --活动类别
                                        @IN_ACTIVE_THEME        NVARCHAR(128),   --活动主题
                                        @IN_START_DATE          DATETIME,       --开始日期
                                        @IN_END_DATE            DATETIME,       --结束日期
                                        @IN_MANAGE_CODE         INT ,           --负责人
                                        @IN_CUSTOMER_TYPE       NVARCHAR(64),   --对应客户群
                                        @IN_ACTIVE_PLAN         NVARCHAR(256),  --活动计划与目标
                                        @IN_ACTIVE_TRACE        NVARCHAR(640),  --活动记录
                                        @IN_ACTIVE_RESULT       NVARCHAR(256),  --活动结果评价
                                        @IN_ACTIVE_FLAG         INT,            --活动标记(1 - 新建；2 - 进行中；3 - 活动结束；)
                                        @IN_COMPLETE_TIME       DATETIME,       --活动完成时间
                                        @IN_CREATOR             INT,            --活动创建人
                                        @IN_INPUT_MAN           INT = 0         --操作员
WITH ENCRYPTION
AS
    DECLARE @V_ACTIVE_TYPE_NAME NVARCHAR(64),@V_MANAGE_MAN NVARCHAR(64),@V_CREATOR_NAME NVARCHAR(64)
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -30401000, @IBUSI_FLAG = 30401
    SELECT @SBUSI_NAME = N'修改营销活动记录信息', @SSUMMARY = N'修改营销活动记录信息'
    IF NOT EXISTS(SELECT 1 FROM TOPERATOR WHERE OP_CODE = @IN_MANAGE_CODE)
        RETURN -10104003   --客户经理不存在

    SELECT @V_ACTIVE_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_ACTIVE_TYPE
    SELECT @V_ACTIVE_TYPE_NAME = ISNULL(@V_ACTIVE_TYPE_NAME,'')
    SELECT @V_MANAGE_MAN = OP_NAME FROM TOPERATOR WHERE OP_CODE = @IN_MANAGE_CODE
    SELECT @V_CREATOR_NAME = OP_NAME FROM TOPERATOR WHERE OP_CODE = @IN_CREATOR

    BEGIN TRANSACTION
    UPDATE TACTIVITIES
    SET ACTIVE_TYPE= @IN_ACTIVE_TYPE,
        ACTIVE_TYPE_NAME= @V_ACTIVE_TYPE_NAME,
        ACTIVE_THEME = @IN_ACTIVE_THEME,
        START_DATE = @IN_START_DATE,
        END_DATE = @IN_END_DATE,
        MANAGE_CODE = @IN_MANAGE_CODE,
        MANAGE_MAN = @V_MANAGE_MAN,
        CUSTOMER_TYPE = @IN_CUSTOMER_TYPE,
        ACTIVE_PLAN = @IN_ACTIVE_PLAN,
        ACTIVE_TRACE = @IN_ACTIVE_TRACE,
        ACTIVE_RESULT = @IN_ACTIVE_RESULT,
        ACTIVE_FLAG = @IN_ACTIVE_FLAG,
        COMPLETE_TIME = @IN_COMPLETE_TIME,
        CREATOR = @IN_CREATOR,
        CREATOR_NAME = @V_CREATOR_NAME
    WHERE SERIAL_NO = @IN_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    IF @IN_ACTIVE_FLAG = 3
    BEGIN
        UPDATE TActivitiesTask
        SET TaskFlag = 3,
            CheckMan = @IN_INPUT_MAN,
            Check_Options = N'因活动完结，任务自动结束。'
        WHERE Active_Serial_no = @IN_SERIAL_NO
            AND TaskFlag != 3
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    SET @SSUMMARY = N'修改营销活动记录信息，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
