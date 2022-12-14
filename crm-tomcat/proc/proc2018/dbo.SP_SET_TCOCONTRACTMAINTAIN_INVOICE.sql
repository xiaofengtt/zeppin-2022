USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_SET_TCOCONTRACTMAINTAIN_INVOICE @IN_MAINTAIN_ID     INT,            --维护合同(维护费)ID
                                                    @IN_BANK_ID         NVARCHAR(10),   --银行(1103)
                                                    @IN_BANK_ACCT       NVARCHAR(60),   --银行帐号
                                                    @IN_ACCT_NAME       NVARCHAR(60),   --银行帐号名称
                                                    @IN_INVOICE_TIME    INT,            --开票时间
                                                    @IN_INVOICE_MONEY   DECIMAL(16,3),  --开票金额
                                                    @IN_INPUT_MAN       INT,            --录入人员，对应表TOPERATOR.OP_CODE
                                                    @IN_SUMMARY         NVARCHAR(500) = NULL --开票备注
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_BANK_NAME  NVARCHAR(30)   --银行名称
    DECLARE @V_COCONTRACTMAINTAIN_SUB_BH  NVARCHAR(200)   --维护合同编号

    SELECT @SBUSI_NAME = N'合同管理之维护费开票'
    SELECT @SSUMMARY = N'合同管理之维护费开票'
    SELECT @IBUSI_FLAG = 50001
    SELECT @V_RET_CODE = -50001000

    SELECT @V_BANK_NAME =TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE =@IN_BANK_ID
    SELECT @V_COCONTRACTMAINTAIN_SUB_BH = COCONTRACTMAINTAIN_SUB_BH FROM TCOCONTRACTMAINTAIN WHERE MAINTAIN_ID = @IN_MAINTAIN_ID

    BEGIN TRANSACTION

    UPDATE TCOCONTRACTMAINTAIN
        SET BANK_ID       = @IN_BANK_ID,
            BANK_NAME     = @V_BANK_NAME,
            BANK_ACCT     = @IN_BANK_ACCT,
            ACCT_NAME     = @IN_ACCT_NAME,
            INVOICE_TIME  = @IN_INVOICE_TIME,
            INVOICE_MONEY = @IN_INVOICE_MONEY,
            INVOICE_MAN   = @IN_INPUT_MAN,
            INVOICE_SUMMARY = @IN_SUMMARY
         WHERE MAINTAIN_ID = @IN_MAINTAIN_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    --
    SELECT @SSUMMARY = N'合同管理之维护费开票，维护合同编号：'+@V_COCONTRACTMAINTAIN_SUB_BH+'，开票时间：'+CONVERT(NVARCHAR(8),@IN_INVOICE_TIME)+'，开票金额：'+CONVERT(NVARCHAR(30),@IN_INVOICE_MONEY)
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
