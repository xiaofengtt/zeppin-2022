﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
    
CREATE PROCEDURE SP_MODI_CM_Contract         @IN_CONTRACT_ID         INTEGER,                --合同ID
                                             @IN_CUST_ID             INTEGER,                --客户ID
                                             @IN_CUST_NAME           NVARCHAR(100),          --客户名称
                                             @IN_CONTRACT_BH         NVARCHAR(60),           --合同编号
                                             @IN_CONTRACT_CONTENT    NVARCHAR(600),          --合同内容
                                             @IN_SIGN_DATE           INTEGER,                --签署日期
                                             @IN_END_DATE            INTEGER,                --结束日期
                                             @IN_SUM_MONEY           DEC(16,3),              --购买总金额  
                                             @IN_PAYMENT_TYPE        NVARCHAR(10) = N'',     --付款方式
                                             @IN_PAYMENT_TYPE_NAME   NVARCHAR(60),           --付款方式名称
                                             @IN_INPUT_MAN           INTEGER
                                           
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_CONTRACT_ID INT
    SELECT @SBUSI_NAME = N'修改合同'
    SELECT @SSUMMARY = N'修改合同'
    SELECT @IBUSI_FLAG = 20801
    SELECT @V_RET_CODE = -20801006  --合同编号已存在
    
        
    IF NOT EXISTS(SELECT * FROM CM_TCONTRACT WHERE CONTRACT_ID = @IN_CONTRACT_ID)
        RETURN @V_RET_CODE - 11   -- 合同不存在
    
    BEGIN TRANSACTION
    
    
    UPDATE CM_TCONTRACT SET 
        CUST_ID=@IN_CUST_ID,CUST_NAME=@IN_CUST_NAME,CONTRACT_BH=@IN_CONTRACT_BH,CONTRACT_CONTENT=@IN_CONTRACT_CONTENT,SIGN_DATE=@IN_SIGN_DATE,
	END_DATE=@IN_END_DATE,SUM_MONEY=@IN_SUM_MONEY,PAYMENT_TYPE=@IN_PAYMENT_TYPE,PAYMENT_TYPE_NAME=@IN_PAYMENT_TYPE_NAME,INPUT_MAN=@IN_INPUT_MAN
    WHERE CONTRACT_ID=@IN_CONTRACT_ID 
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END    
    SELECT @SSUMMARY = N'修改合同，合同ID：'+ RTRIM(CONVERT(NVARCHAR(16),@IN_CONTRACT_ID))
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
