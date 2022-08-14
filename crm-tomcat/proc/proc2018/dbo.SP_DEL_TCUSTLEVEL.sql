﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_DEL_TCUSTLEVEL @IN_SERIAL_NO INTEGER, --序号
                                   @IN_INPUT_MAN INTEGER  --操作员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'删除认购份额、受益份额分级设置情况'
    SELECT @SSUMMARY = N'删除认购份额、受益份额分级设置情况'
    SELECT @IBUSI_FLAG = 10110
    SELECT @V_RET_CODE = -10110000

    DECLARE @V_PRODUCT_ID INT, @V_LEVEL_ID INT

    IF NOT EXISTS(SELECT 1 FROM TCUSTLEVEL WHERE SERIAL_NO = @IN_SERIAL_NO)
        RETURN -20702001 --记录不存在

    BEGIN TRANSACTION

    DELETE FROM TCUSTLEVEL WHERE SERIAL_NO = @IN_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'删除认购份额、受益份额分级设置情况'
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
