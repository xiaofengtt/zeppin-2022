USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_THOLIDAYS @IN_HOLIDAY_ID      INT,            --节假日ID
                                   @IN_HOLIDAY_NAME    NVARCHAR(60),   --节假日名称
                                   @IN_HOLIDAY_DAY     INT,            --节假日mmdd
                                   @IN_CAL_FLAG        TINYINT,        --1公历 2农历
                                   @IN_CREATE_TASK     INT,            --是否创建服务任务 1是 2否
                                   @IN_SMS_GREETING    NVARCHAR(500),  --节日短信祝词内容
                                   @IN_EMAIL_GREETING  NVARCHAR(4000), --节日邮件祝词内容
                                   @IN_INPUT_MAN       INT
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT, @SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -10130000, @IBUSI_FLAG = 10130
    SELECT @SBUSI_NAME = N'修改节假日', @SSUMMARY = N'修改节假日'

    IF NOT EXISTS(SELECT 1 FROM THOLIDAYS WHERE HOLIDAY_ID = @IN_HOLIDAY_ID)
        RETURN -20402001 --记录不存在
    IF EXISTS(SELECT 1 FROM THOLIDAYS WHERE HOLIDAY_NAME = @IN_HOLIDAY_NAME AND HOLIDAY_ID <> @IN_HOLIDAY_ID)
        RETURN @V_RET_CODE - 1 --节假日名称已经存在
    IF ISDATE(20000000+@IN_HOLIDAY_DAY) = 0
        RETURN @V_RET_CODE - 2 --输入节日日期无效，需要mmdd形式
    IF @IN_CAL_FLAG NOT IN (1,2)
        RETURN @V_RET_CODE - 3 --输入参数无效
    IF @IN_CREATE_TASK NOT IN (1,2)
        RETURN @V_RET_CODE - 3 --输入参数无效

    BEGIN TRANSACTION

    UPDATE THOLIDAYS SET
        HOLIDAY_NAME   = @IN_HOLIDAY_NAME,
        HOLIDAY_DAY    = @IN_HOLIDAY_DAY,
        CAL_FLAG       = @IN_CAL_FLAG,
        CREATE_TASK    = @IN_CREATE_TASK,
        SMS_GREETING   = @IN_SMS_GREETING,
        EMAIL_GREETING = @IN_EMAIL_GREETING
    WHERE HOLIDAY_ID = @IN_HOLIDAY_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'修改节假日：' + @IN_HOLIDAY_NAME + CASE @IN_CAL_FLAG WHEN 1 THEN N'；公历：' ELSE N'；农历：' END + dbo.fn_getmonthday(@IN_HOLIDAY_DAY)
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
