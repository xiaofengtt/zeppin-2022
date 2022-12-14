USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE SP_ADD_TSUBSCRIBE @IN_NONPRODUCT_ID   INT,          --非信托产品ID
                                   @IN_CUST_ID         INT,          --客户ID
                                   @IN_SUBSCRIBE_BH    NVARCHAR(60), --购买合同编号
                                   @IN_SIGN_DATE       INT,          --签署日期
                                   @IN_PAY_DATE        INT,          --缴款日期
                                   @IN_SUBSCRIBE_MONEY DEC(16,3),    --购买金额
                                   @IN_BANK_ID         NVARCHAR(10), --银行(1803)
                                   @IN_BANK_SUB_NAME   NVARCHAR(60), --银行支行名称
                                   @IN_BANK_ACCT       NVARCHAR(60), --银行帐号
                                   @IN_INPUT_MAN       INT           --操作员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200),@V_NONPRODUCT_NAME NVARCHAR(60)
    DECLARE @V_BANK_NAME NVARCHAR(30), @V_DT_INTRUST INT
    SELECT @SBUSI_NAME = N'增加非信托产品认购信息'
    SELECT @SSUMMARY = N'增加非信托产品认购信息'
    SELECT @IBUSI_FLAG = 39006
    SELECT @V_RET_CODE = -39006000
    SELECT @V_DT_INTRUST = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'DT_INTRUST'
    SELECT @V_NONPRODUCT_NAME = NONPRODUCT_NAME FROM TNONPRODUCT WHERE NONPRODUCT_ID = @IN_NONPRODUCT_ID

    IF EXISTS(SELECT * FROM TSUBSCRIBE WHERE NONPRODUCT_ID <> @IN_NONPRODUCT_ID AND CUST_ID = @IN_CUST_ID AND SUBSCRIBE_BH = @IN_SUBSCRIBE_BH)
        RETURN @V_RET_CODE - 21   -- 非信托产品认购信息已存在

    BEGIN
        IF @V_DT_INTRUST = 1 --启用分布式
        BEGIN
            SELECT @V_BANK_NAME = TYPE_CONTENT FROM SRV_Intrust.INTRUST.dbo.TDICTPARAM WHERE TYPE_VALUE = @IN_BANK_ID
        END
        ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
        BEGIN
            SELECT @V_BANK_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE = @IN_BANK_ID
        END
    END

    BEGIN TRANSACTION

    INSERT INTO TSUBSCRIBE(NONPRODUCT_ID,NONPRODUCT_NAME,CUST_ID,SUBSCRIBE_BH,SIGN_DATE,PAY_DATE,
                           SUBSCRIBE_MONEY,BANK_ID,BANK_NAME,BANK_SUB_NAME,BANK_ACCT,STATUS,STATUS_NAME,INPUT_MAN,INPUT_TIME, CHECK_FLAG)
                    VALUES(@IN_NONPRODUCT_ID,@V_NONPRODUCT_NAME,@IN_CUST_ID,@IN_SUBSCRIBE_BH,@IN_SIGN_DATE,@IN_PAY_DATE,@IN_SUBSCRIBE_MONEY
                           ,@IN_BANK_ID,@V_BANK_NAME,@IN_BANK_SUB_NAME,@IN_BANK_ACCT,120101,'正常',@IN_INPUT_MAN,GETDATE(),1)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    SELECT @SSUMMARY = N'增加认购合同，产品编号：'+RTRIM(CONVERT(NVARCHAR(16),@IN_NONPRODUCT_ID))+N',产品名称：'+RTRIM(@V_NONPRODUCT_NAME)
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
