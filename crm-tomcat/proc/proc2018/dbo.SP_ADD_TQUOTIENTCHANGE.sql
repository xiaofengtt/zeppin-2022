USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE SP_ADD_TQUOTIENTCHANGE @IN_SUBSCRIBE_ID     INT,          --非信托产品合同序号
                                        @IN_QUOTIENT_AMOUNT  DEC(16,3),    --份额
                                        @IN_CHANGE_DATE      INT,          --日期
                                        @IN_INPUT_MAN        INT           --操作员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200),@TABLE_ID NVARCHAR(60)
    DECLARE @V_CUST_ID INT,@V_SUBSCRIBE_MONEY DEC(16,3)
    SELECT @SBUSI_NAME = N'添加非信托产品份额信息'
    SELECT @SSUMMARY = N'添加非信托产品份额信息'
    SELECT @IBUSI_FLAG = 39011
    SELECT @V_RET_CODE = -39011000
    SELECT @V_CUST_ID = CUST_ID FROM TSUBSCRIBE WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID
    SELECT @V_SUBSCRIBE_MONEY = SUBSCRIBE_MONEY FROM TSUBSCRIBE WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID

    IF EXISTS(SELECT * FROM TQUOTIENTCHANGE WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID AND CHANGE_DATE = @IN_CHANGE_DATE AND CUST_ID = @V_CUST_ID)
        RETURN @V_RET_CODE - 21   --非信托产品份额信息已存在
    IF @IN_QUOTIENT_AMOUNT > @V_SUBSCRIBE_MONEY
        RETURN @V_RET_CODE - 22   --受益金额不能大于认购金额

    BEGIN TRANSACTION

    INSERT INTO TQUOTIENTCHANGE(SUBSCRIBE_ID, CUST_ID, CHANGE_TYPE,CHANGE_AMOUNT, CHANGE_MONEY, CHANGE_DATE,
                INPUT_MAN, INPUT_TIME, CHECK_FLAG, CHECK_MAN, CHECK_TIME)
            VALUES(@IN_SUBSCRIBE_ID, @V_CUST_ID, 1 ,@IN_QUOTIENT_AMOUNT ,@IN_QUOTIENT_AMOUNT,@IN_CHANGE_DATE,
                @IN_INPUT_MAN, GETDATE(), 1, @IN_INPUT_MAN, GETDATE())
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    SELECT @SSUMMARY = N'增加产品份额，合同编号：'+RTRIM(CONVERT(NVARCHAR(16),@IN_SUBSCRIBE_ID))+N'客户编号：'+RTRIM(CONVERT(CHAR(16),@V_CUST_ID))
                        +N'日期：'+RTRIM(CONVERT(NVARCHAR(16),@IN_CHANGE_DATE))
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
