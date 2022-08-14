﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_CHECK_TSUBSCRIBE @IN_SUBSCRIBE_ID   INT,    --非信托产品合同ID
                                     @IN_CUST_ID        INT,    --客户编号
                                     @IN_CHECK_FLAG     INT,    --2审核通过1审核不通过
                                     @IN_INPUT_MAN      INT     --录入操作员
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_FACE_MONEY DEC(16,3),@V_NONPRODUCT_ID INT
    SELECT @SBUSI_NAME = N'非信托产品认购信息审核'
    SELECT @SSUMMARY = N'非信托产品认购信息审核'
    SELECT @IBUSI_FLAG = 39007
    SELECT @V_RET_CODE = -39007000

    IF NOT EXISTS(SELECT 1 FROM TSUBSCRIBE WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID)
        RETURN @V_RET_CODE - 11   --非信托产品认购信息不存在
    IF NOT EXISTS(SELECT 1 FROM TSUBSCRIBE WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID AND CHECK_FLAG = 1)
        RETURN @V_RET_CODE - 12   --非信托产品认购信息已审核

    SELECT @V_FACE_MONEY = SUBSCRIBE_MONEY,@V_NONPRODUCT_ID = NONPRODUCT_ID
        FROM TSUBSCRIBE WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID

    BEGIN TRANSACTION

    IF @IN_CHECK_FLAG = 2 --审核通过
    BEGIN
        UPDATE TSUBSCRIBE SET CHECK_FLAG = 2, CHECK_TIME = GETDATE(), CHECK_MAN = @IN_INPUT_MAN
            WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
        INSERT INTO TQUOTIENTCHANGE(SUBSCRIBE_ID, CUST_ID, CHANGE_TYPE, CHANGE_AMOUNT, CHANGE_MONEY, CHANGE_DATE, INPUT_MAN, INPUT_TIME, CHECK_FLAG, CHECK_MAN, CHECK_TIME)
            SELECT SUBSCRIBE_ID, CUST_ID, 1, SUBSCRIBE_MONEY, SUBSCRIBE_MONEY, PAY_DATE, @IN_INPUT_MAN, GETDATE(), 1, @IN_INPUT_MAN, GETDATE()
            FROM TSUBSCRIBE WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    SET @SSUMMARY = N'非信托产品认购信息审核，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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