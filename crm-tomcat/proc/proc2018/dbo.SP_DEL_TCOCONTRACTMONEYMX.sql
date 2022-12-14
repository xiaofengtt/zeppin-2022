USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_DEL_TCOCONTRACTMONEYMX @IN_MONEYMX_ID    INT,              --到账明细ID
                                           @IN_INPUT_MAN     INT               --录入人员，对应表TOPERATOR.OP_CODE   
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_COCONTRACT_ID INT,@V_COCONTRACT_SUB_BH NVARCHAR(200),@V_PAYPLAN_ID INT,@V_ARRIVE_TIME INT
    DECLARE @V_OLD_ARRIVE_MONEY  DECIMAL(16,3)  --旧到帐金额
    DECLARE @V_PAY_NUM_NAME NVARCHAR(60)        --期数说明
    DECLARE @V_PAY_MONEY   DECIMAL(16,3)        --计划付款金额
    DECLARE @V_ARRIVE_MONEY DECIMAL(16,3)       --实际已到帐金额
    
    SELECT @SBUSI_NAME = N'合同管理之删除合同到帐明细'
    SELECT @SSUMMARY = N'合同管理之删除合同到帐明细'
    SELECT @IBUSI_FLAG = 50001
    SELECT @V_RET_CODE = -50001000 
    
    SELECT @V_PAYPLAN_ID =PAYPLAN_ID,@V_OLD_ARRIVE_MONEY = ARRIVE_MONEY,@V_ARRIVE_TIME = ARRIVE_TIME FROM TCOCONTRACTMONEYMX WHERE MONEYMX_ID = @IN_MONEYMX_ID
    SELECT @V_PAY_NUM_NAME = PAY_NUM_NAME,@V_COCONTRACT_ID =COCONTRACT_ID,@V_PAY_MONEY = PAY_MONEY,@V_ARRIVE_MONEY = ARRIVE_MONEY
        FROM TCOCONTRACTPAYPLAN WHERE PAYPLAN_ID =@V_PAYPLAN_ID
    SELECT @V_COCONTRACT_SUB_BH = COCONTRACT_SUB_BH FROM TCOCONTRACT WHERE COCONTRACT_ID =@V_COCONTRACT_ID
    
    BEGIN TRANSACTION
    
    DELETE FROM TCOCONTRACTMONEYMX WHERE MONEYMX_ID = @IN_MONEYMX_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    --更新付款计划表中到帐金额
    UPDATE TCOCONTRACTPAYPLAN
        SET ARRIVE_MONEY = ISNULL(ARRIVE_MONEY,0) - ISNULL(@V_OLD_ARRIVE_MONEY,0),
            ARRIVEMONEY_FLAG =CASE WHEN (ISNULL(@V_ARRIVE_MONEY,0) - ISNULL(@V_OLD_ARRIVE_MONEY,0)) =0 THEN 1 WHEN (@V_PAY_MONEY <=  (ISNULL(@V_ARRIVE_MONEY,0) - ISNULL(@V_OLD_ARRIVE_MONEY,0))) THEN 3 ELSE 2 END
        WHERE PAYPLAN_ID =@V_PAYPLAN_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    --
    SELECT @SSUMMARY = N'合同管理之删除合同到帐明细，合同编号：'+@V_COCONTRACT_SUB_BH+'，合同期数说明：'+@V_PAY_NUM_NAME +'，到帐日期：'+CONVERT(NVARCHAR(8),@V_ARRIVE_TIME)+'，到帐金额：'+CONVERT(VARCHAR(30),@V_OLD_ARRIVE_MONEY)
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
