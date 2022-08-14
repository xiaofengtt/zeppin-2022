USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TCOCONTRACTPAYPLAN @IN_PAYPLAN_ID           INT,                  --付款计划ID
                                        @IN_PAY_NUM                  INT,                  --期数
                                        @IN_PAY_NUM_NAME             NVARCHAR(60),         --期数说明
                                        @IN_EXP_DATE                 INT,                  --预计付款日期
                                        @IN_PAY_RATE                 DECIMAL(9,6),         --付款比例
                                        @IN_PAY_MONEY                DECIMAL(16,3),        --付款金额
                                        @IN_PAY_SUMMARY              NVARCHAR(2000),       --付款条件说明
                                        @IN_INPUT_MAN                INT                 --录入人员，对应表TOPERATOR.OP_CODE   
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_COCONTRACT_SUB_BH NVARCHAR(200),@V_COCONTRACT_ID INT
    
    SELECT @SBUSI_NAME = N'合同管理之修改付款计划'
    SELECT @SSUMMARY = N'合同管理之修改付款计划'
    SELECT @IBUSI_FLAG = 50001
    SELECT @V_RET_CODE = -50001000 
    
    SELECT @V_COCONTRACT_ID =COCONTRACT_ID FROM TCOCONTRACTPAYPLAN WHERE PAYPLAN_ID =@IN_PAYPLAN_ID
    SELECT @V_COCONTRACT_SUB_BH = COCONTRACT_SUB_BH FROM TCOCONTRACT WHERE COCONTRACT_ID =@V_COCONTRACT_ID
    
    BEGIN TRANSACTION
    
    UPDATE TCOCONTRACTPAYPLAN
        SET PAY_NUM     = @IN_PAY_NUM,
            PAY_NUM_NAME = @IN_PAY_NUM_NAME,
            EXP_DATE    = @IN_EXP_DATE,
            PAY_RATE    = @IN_PAY_RATE,
            PAY_MONEY   = @IN_PAY_MONEY,
            PAY_SUMMARY = @IN_PAY_SUMMARY
         WHERE PAYPLAN_ID =@IN_PAYPLAN_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    --
    SELECT @SSUMMARY = N'合同管理之修改付款计划，合同编号：'+@V_COCONTRACT_SUB_BH+'，合同期数说明：'+@IN_PAY_NUM_NAME
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
