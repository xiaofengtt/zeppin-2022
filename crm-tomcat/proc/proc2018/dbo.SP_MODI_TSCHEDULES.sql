USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TSCHEDULES @IN_SERIAL_NO       INT,                --ID
                                    @IN_SCHEDULE_TYPE   NVARCHAR(10),        --日程类型 --约会/活动/计划
                                    @IN_START_DATE      DATETIME,           --起始时间
                                    @IN_END_DATE        DATETIME,           --结束时间
                                    @IN_CONTENT         NVARCHAR(256),      --日程内容
                                    @IN_CHECK_FLAG      INT = 1,            --日程状态 新建/已读/完成
                                    @IN_INPUT_MAN       INT = 0             --操作员
WITH ENCRYPTION
AS
    DECLARE @V_SCHEDULE_NAME NVARCHAR(64),@V_OP_NAME NVARCHAR(64)
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -50201000, @IBUSI_FLAG = 50201
    SELECT @SBUSI_NAME = N'修改日程记录', @SSUMMARY = N'修改日程记录'

    IF NOT EXISTS(SELECT 1 FROM TOPERATOR WHERE OP_CODE = @IN_INPUT_MAN)
        RETURN -10104003 --操作员不存在

    SELECT @V_SCHEDULE_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_SCHEDULE_TYPE
    SELECT @V_OP_NAME = OP_NAME FROM TOPERATOR WHERE OP_CODE = @IN_INPUT_MAN
    SELECT @V_SCHEDULE_NAME = ISNULL(@V_SCHEDULE_NAME,'')

    BEGIN TRANSACTION
    UPDATE TSCHEDULES
    SET SCHEDULE_TYPE = @IN_SCHEDULE_TYPE,
        SCHEDULE_NAME = @V_SCHEDULE_NAME,
        START_DATE    = @IN_START_DATE,
        END_DATE      = @IN_END_DATE,
        OP_CODE = @IN_INPUT_MAN,
        OP_NAME = @V_OP_NAME,
        CONTENT = @IN_CONTENT,
        CHECK_FLAG = @IN_CHECK_FLAG
    WHERE SERIAL_NO = @IN_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'修改日程记录，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
