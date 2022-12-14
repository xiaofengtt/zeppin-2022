USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_SETTLE_CHECK_TSUBSCRIBE @IN_SUBSCRIBE_ID INT,    --合同编号
                                            @IN_CHECK_FLAG   INT,    --2审核通过1审核不通过
                                            @IN_INPUT_MAN    INT     --录入操作员
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_CUST_ID INT,@V_END_DATE INT
    SELECT @SBUSI_NAME = N'非信托产品合同结清审核'
    SELECT @SSUMMARY = N'非信托产品合同结清审核'
    SELECT @IBUSI_FLAG = 39022
    SELECT @V_RET_CODE = -39022000


    IF NOT EXISTS(SELECT 1 FROM TSUBSCRIBE WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID)
        RETURN @V_RET_CODE - 12   --非信托产品认购信息不存在

    IF NOT EXISTS(SELECT * FROM TQUOTIENT WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID)
        RETURN @V_RET_CODE - 13   --非信托产品份额信息不存在

    SELECT @V_CUST_ID = CUST_ID FROM TSUBSCRIBE WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID
    SELECT @V_END_DATE = END_DATE FROM TQUOTIENT WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID

    BEGIN TRANSACTION

    IF @IN_CHECK_FLAG = 1  --1审核未通过
    BEGIN
        UPDATE TQUOTIENT SET STATUS = TEMP_STATUS, STATUS_NAME = '正常'
            WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
        UPDATE TSUBSCRIBE SET STATUS = TEMP_STATUS, STATUS_NAME = '正常', CHECK_FLAG = 2
            WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
        DELETE FROM TPROFIT WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID AND SY_TYPE = '111605'
    END
    ELSE
    BEGIN
        INSERT INTO TQUOTIENTCHANGE(SUBSCRIBE_ID, CUST_ID, CHANGE_TYPE, CHANGE_AMOUNT, CHANGE_MONEY,
                CHANGE_DATE, INPUT_MAN, INPUT_TIME, CHECK_FLAG, CHECK_MAN, CHECK_TIME)
            SELECT SUBSCRIBE_ID, CUST_ID, 9, QUOTIENT_AMOUNT, QUOTIENT_MONEY, END_DATE, @IN_INPUT_MAN, GETDATE(), 2, @IN_INPUT_MAN, GETDATE()
            FROM TQUOTIENT WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
        UPDATE TQUOTIENT SET QUOTIENT_AMOUNT = 0, QUOTIENT_MONEY = 0, TEMP_STATUS = NULL
            WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
        UPDATE TSUBSCRIBE SET TEMP_STATUS = NULL, CHECK_FLAG = 2 WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID
    END
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'非信托产品合同结清审核，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
