USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TCOCONTRACT @IN_CUST_ID             INT,             --客户ID，对应表TCustomers.CUST_ID
                                    @IN_COCONTRACT_SUB_BH   NVARCHAR(200),   --合同编号
                                    @IN_SIGN_DATE           INT,             --签署日期
                                    @IN_START_DATE          INT,             --开始日期
                                    @IN_END_DATE            INT,             --到期日期
                                    @IN_MAIN_END_DATE       INT,             --免费维护计划到期日
                                    @IN_HT_MONEY            DECIMAL(16,3),   --合同金额
                                    @IN_PAYMENT_TYPE        NVARCHAR(10),    --付款方式(5003)：500301一次首付 500302分期 500303 一次后付
                                    @IN_COCONTRACT_TYPE     NVARCHAR(10),    --合同类型（5004）：500401 软件销售 500402 软件维护 500403 技术开发 500404 硬件销售(中间件软件)
                                    @IN_INPUT_MAN           INT,              --录入人员，对应表TOPERATOR.OP_CODE
                                    @OUT_COCONTRACT_ID      INT    OUTPUT,    --合同ID
                                    @IN_SUMMARY             NVARCHAR(500) = NULL --备注

WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_PAYMENT_TYPE_NAME NVARCHAR(30),@V_COCONTRACT_TYPE_NAME NVARCHAR(30)
    SELECT @SBUSI_NAME = N'合同管理之新增主合同'
    SELECT @SSUMMARY = N'合同管理之新增主合同'
    SELECT @IBUSI_FLAG = 50001
    SELECT @V_RET_CODE = -50001000

    SELECT @V_PAYMENT_TYPE_NAME =TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE =@IN_PAYMENT_TYPE
    SELECT @V_COCONTRACT_TYPE_NAME =TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE =@IN_COCONTRACT_TYPE

    BEGIN TRANSACTION

    INSERT INTO TCOCONTRACT(CUST_ID,COCONTRACT_SUB_BH,SIGN_DATE,START_DATE,END_DATE,MAIN_END_DATE,HT_MONEY,
                    PAYMENT_TYPE,PAYMENT_TYPE_NAME,COCONTRACT_TYPE,COCONTRACT_TYPE_NAME,INPUT_MAN,SUMMARY)
                VALUES(@IN_CUST_ID,@IN_COCONTRACT_SUB_BH,@IN_SIGN_DATE,@IN_START_DATE,@IN_END_DATE,@IN_MAIN_END_DATE,@IN_HT_MONEY,
                    @IN_PAYMENT_TYPE,@V_PAYMENT_TYPE_NAME,@IN_COCONTRACT_TYPE,@V_COCONTRACT_TYPE_NAME,@IN_INPUT_MAN,@IN_SUMMARY)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    --
    SET @OUT_COCONTRACT_ID = @@IDENTITY
    --
    SELECT @SSUMMARY = N'合同管理之新增主合同，合同编号'+@IN_COCONTRACT_SUB_BH
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
