USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_DEL_THOLIDAYDATE @IN_SERIAL_NO    INT,     --序号
                                     @IN_INPUT_MAN    INT
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT, @SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -10130000, @IBUSI_FLAG = 10130
    SELECT @SBUSI_NAME = N'删除节假日例外日期', @SSUMMARY = N'删除节假日例外日期'

    IF NOT EXISTS(SELECT 1 FROM THOLIDAYDATE WHERE SERIAL_NO = @IN_SERIAL_NO)
        RETURN -20402001 --记录不存在
    DECLARE @V_HOLIDAY_ID INT, @V_CAL_FLAG INT, @V_MMDDINT INT, @V_HOLIDAY_NAME NVARCHAR(60)
    SELECT @V_HOLIDAY_ID = HOLIDAY_ID, @V_MMDDINT = MMDDINT FROM THOLIDAYDATE WHERE SERIAL_NO = @IN_SERIAL_NO
    SELECT @V_HOLIDAY_NAME = HOLIDAY_NAME, @V_CAL_FLAG = CAL_FLAG FROM THOLIDAYS WHERE HOLIDAY_ID = @V_HOLIDAY_ID

    BEGIN TRANSACTION

    DELETE FROM THOLIDAYDATE WHERE SERIAL_NO = @IN_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'删除节假日例外日期：' + @V_HOLIDAY_NAME + CASE @V_CAL_FLAG WHEN 1 THEN N'；公历：' ELSE N'；农历：' END + dbo.fn_getmonthday(@V_MMDDINT)
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
