USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TCOMAINTAINDETAIL @IN_MAINTAINDETAIL_ID             INT,            --维护费明细ID
                                           @IN_COCONTRACT_ID                 INT,            --主合同ID，对应TCOCONTRACT. COCONTRACT_ID
                                           @IN_COPRODUCT_ID                  INT,            --产品ID，对应表TCOPRODUCT.COPRODUCT_ID
                                           @IN_COPRODUCT_NAME                NVARCHAR(200),  --产品名称，对应表TCOPRODUCT.COPRODUCT_NAME 
                                           @IN_XM_HT_MONEY                   DECIMAL(16,3),  --项目合同金额
                                           @IN_MAIN_RATE                     DECIMAL(9,6),   --维护费率
                                           @IN_MAIN_MONEY                    DECIMAL(16,3),  --维护金额
                                           @IN_INPUT_MAN                     INT             --录入操作员      
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_COCONTRACTMAINTAIN_SUB_BH     NVARCHAR(200)                    --维护合同编号
    DECLARE @V_MAINTAIN_ID INT   --维护费(维护合同)ID
    
    SELECT @SBUSI_NAME = N'合同管理之修改维护合同明细'
    SELECT @SSUMMARY = N'合同管理之修改维护合同明细'
    SELECT @IBUSI_FLAG = 50001
    SELECT @V_RET_CODE = -50001000 
    
    SELECT @V_MAINTAIN_ID =MAINTAIN_ID FROM TCOMAINTAINDETAIL WHERE MAINTAINDETAIL_ID =@IN_MAINTAINDETAIL_ID
    SELECT @V_COCONTRACTMAINTAIN_SUB_BH = COCONTRACTMAINTAIN_SUB_BH FROM TCOCONTRACTMAINTAIN WHERE MAINTAIN_ID =@V_MAINTAIN_ID
    
    BEGIN TRANSACTION
    
    UPDATE TCOMAINTAINDETAIL
        SET COCONTRACT_ID = @IN_COCONTRACT_ID,
            COPRODUCT_ID  = @IN_COPRODUCT_ID,
            COPRODUCT_NAME= @IN_COPRODUCT_NAME,  
            XM_HT_MONEY   = @IN_XM_HT_MONEY,
            MAIN_RATE     = @IN_MAIN_RATE,
            MAIN_MONEY    = @IN_MAIN_MONEY
         WHERE MAINTAINDETAIL_ID =@IN_MAINTAINDETAIL_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    --
    SELECT @SSUMMARY = N'合同管理之修改维护合同明细，维护合同编号：'+@V_COCONTRACTMAINTAIN_SUB_BH+'，维护产品名称：'+@IN_COPRODUCT_NAME
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
