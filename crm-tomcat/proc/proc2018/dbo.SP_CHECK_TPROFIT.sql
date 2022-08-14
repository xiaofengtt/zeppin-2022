﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_CHECK_TPROFIT @IN_SERIAL_NO    INT,    --收益主键
                                  @IN_CHECK_FLAG   INT,    --1审核通过2审核不通过
                                  @IN_INPUT_MAN    INT     --录入操作员
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_SUBSCRIBE_ID INT
    SELECT @SBUSI_NAME = N'非信托产品收益信息审核'
    SELECT @SSUMMARY = N'非信托产品收益信息审核'
    SELECT @IBUSI_FLAG = 39017
    SELECT @V_RET_CODE = -39017000
    SELECT @V_SUBSCRIBE_ID = SUBSCRIBE_ID FROM TPROFIT WHERE SERIAL_NO = @IN_SERIAL_NO

    IF NOT EXISTS(SELECT 1 FROM TPROFIT WHERE SERIAL_NO = @IN_SERIAL_NO)
        RETURN @V_RET_CODE - 11   --非信托产品收益信息不存在

    BEGIN TRANSACTION

    IF @IN_CHECK_FLAG = 2
    BEGIN
    UPDATE TPROFIT SET CHECK_FLAG = @IN_CHECK_FLAG,CHECK_TIME = GETDATE(),CHECK_MAN = @IN_INPUT_MAN
        WHERE SERIAL_NO = @IN_SERIAL_NO

    UPDATE TQUOTIENT SET TEMP_STATUS = 121101 WHERE SUBSCRIBE_ID = @V_SUBSCRIBE_ID
    UPDATE TSUBSCRIBE SET TEMP_STATUS = 120101 WHERE SUBSCRIBE_ID = @V_SUBSCRIBE_ID
    END
    ELSE
    BEGIN
        DELETE FROM TPROFIT WHERE SERIAL_NO = @IN_SERIAL_NO
    END

    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'非信托产品收益信息审核，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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