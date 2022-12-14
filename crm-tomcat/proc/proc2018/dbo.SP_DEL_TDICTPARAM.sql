USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_DEL_TDICTPARAM @IN_SERIAL_NO    INT,
                                   @IN_INPUT_MAN    INT
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT, @SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -10201000, @IBUSI_FLAG = 10201
    SELECT @SBUSI_NAME = N'删除字典参数', @SSUMMARY = N'删除字典参数'

    DECLARE @V_TYPE_VALUE NVARCHAR(10)

    IF NOT EXISTS(SELECT * FROM TDICTPARAM WHERE SERIAL_NO = @IN_SERIAL_NO)
       RETURN @V_RET_CODE-11   -- 记录不存在

    SELECT @V_TYPE_VALUE = TYPE_VALUE FROM TDICTPARAM WHERE SERIAL_NO = @IN_SERIAL_NO

    BEGIN TRANSACTION

    DELETE FROM TDICTPARAM WHERE SERIAL_NO = @IN_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'删除字典参数，参数值：' + RTRIM(@V_TYPE_VALUE)
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
