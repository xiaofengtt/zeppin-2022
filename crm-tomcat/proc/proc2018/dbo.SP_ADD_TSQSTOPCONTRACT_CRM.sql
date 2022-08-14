USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TSQSTOPCONTRACT_CRM @IN_HT_SERIAL_NO    INT,                    --TCONTRACT.SERIAL_NO/TCONTRACTSG.SERIAL_NO
											@IN_REASON          NVARCHAR(200),          --退款原因，字符串描述
											@IN_SQ_DATE         INT,                    --申请办理退款日期
											@IN_FEE             DECIMAL(16,2),          --申请退款手续费
											@IN_INPUT_MAN       INT,                    --录入操作员
											@IN_T_RG_FEE        INT           = 0,      --是否退认购/申购费：0不退 1退
											@IN_SQ_MONEY        DECIMAL(16,2) = NULL,   --申请退款金额
											@IN_REBATE_FLAG     INT           = 1,      --1.发行期退款，2.申购期退款
											@IN_HT_FEE          DECIMAL(16,2) = 0       --退款金额对应的认购费/申购费
WITH ENCRYPTION
AS
    DECLARE @V_PRODUCT_ID INT,@V_PRODUCT_CODE NVARCHAR(6),@V_PRODUCT_NAME NVARCHAR(120),@V_CONTRACT_BH VARCHAR(16),@V_CONTRACT_SUB_BH NVARCHAR(50),
            @V_CUST_ID INT,@V_GS_RATE DECIMAL(5,4),@V_HT_TO_MONEY DECIMAL(16,2)
    
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SET @V_RET_CODE =  -20403000
    SET @IBUSI_FLAG  = 20403
    SET @SBUSI_NAME = CASE @IN_REBATE_FLAG WHEN 1 THEN '发行期退款申请' ELSE '申购期退款申请' END
    
    BEGIN TRY
    BEGIN TRANSACTION
    
        IF @IN_REBATE_FLAG = 1 BEGIN 
            IF NOT EXISTS(SELECT 1 FROM INTRUST..TCONTRACT WHERE SERIAL_NO = @IN_HT_SERIAL_NO)
                RAISERROR('认购合同不存在，无法录入发行期退款申请！',16,1)
                
            IF NOT EXISTS(SELECT 1 FROM INTRUST..TCONTRACT WHERE SERIAL_NO = @IN_HT_SERIAL_NO AND HT_STATUS = '120101')
                RAISERROR('认购合同为非正常状态，无法录入发行期退款申请！',16,2)
        END
        ELSE IF @IN_REBATE_FLAG = 2 BEGIN
            IF NOT EXISTS(SELECT 1 FROM INTRUST..TCONTRACTSG WHERE SERIAL_NO = @IN_HT_SERIAL_NO)
                RAISERROR('申购合同不存在，无法录入申购期退款申请！',16,1)
            
            IF NOT EXISTS(SELECT 1 FROM INTRUST..TCONTRACTSG WHERE SERIAL_NO = @IN_HT_SERIAL_NO AND HT_STATUS = '120101')
                RAISERROR('申购合同为非正常状态，无法录入申购期退款申请！',16,2)
                
            IF EXISTS(SELECT 1 FROM INTRUST..TCONTRACTSG WHERE SERIAL_NO = @IN_HT_SERIAL_NO AND CHECK_FLAG = 4)
                RAISERROR('申购合同已做份额确认，无法录入申购期退款申请！',16,3)
        END
            
        IF EXISTS(SELECT 1 FROM INTRUST..TSQSTOPCONTRACT WHERE HT_SERIAL_NO = @IN_HT_SERIAL_NO AND REBATE_FLAG = @IN_REBATE_FLAG AND CHECK_FLAG <> 3)
            RAISERROR('该合同存在未处理的退款记录，请先处理现有的退款记录！',16,4)
            
        IF @IN_REBATE_FLAG = 1 BEGIN
            SELECT @V_PRODUCT_ID = A.PRODUCT_ID,@V_PRODUCT_CODE = B.PRODUCT_CODE,@V_PRODUCT_NAME = B.PRODUCT_NAME,
                   @V_CONTRACT_BH = A.CONTRACT_BH,@V_CONTRACT_SUB_BH = A.CONTRACT_SUB_BH,@V_CUST_ID = A.CUST_ID,
                   @V_HT_TO_MONEY = A.TO_MONEY
                FROM INTRUST..TCONTRACT A,INTRUST..TPRODUCT B
                WHERE A.SERIAL_NO = @IN_HT_SERIAL_NO AND A.PRODUCT_ID = B.PRODUCT_ID
        END
        ELSE BEGIN
            SELECT @V_PRODUCT_ID = A.PRODUCT_ID,@V_PRODUCT_CODE = B.PRODUCT_CODE,@V_PRODUCT_NAME = B.PRODUCT_NAME,
                   @V_CONTRACT_BH = A.CONTRACT_BH,@V_CONTRACT_SUB_BH = A.CONTRACT_SUB_BH,@V_CUST_ID = A.CUST_ID,
                   @V_HT_TO_MONEY = A.TO_MONEY
                FROM INTRUST..TCONTRACTSG A,INTRUST..TPRODUCT B
                WHERE A.SERIAL_NO = @IN_HT_SERIAL_NO AND A.PRODUCT_ID = B.PRODUCT_ID
        END

        IF @IN_SQ_MONEY > @V_HT_TO_MONEY OR ISNULL(@IN_SQ_MONEY,0) = 0
            SET @IN_SQ_MONEY = @V_HT_TO_MONEY
            
        IF ISNULL(@IN_T_RG_FEE,0) = 0
            SET @IN_HT_FEE = 0

        --计算退款费
        IF @IN_FEE IS NULL
            EXEC INTRUST..SP_INNER_CAL_PRODUCTFEE @V_PRODUCT_ID,@V_CUST_ID,5,@IN_SQ_MONEY,@IN_SQ_DATE,@IN_FEE OUTPUT,@V_GS_RATE OUTPUT

        --插入退款申请
        INSERT INTO INTRUST..TSQSTOPCONTRACT(HT_SERIAL_NO,PRODUCT_ID,CONTRACT_BH,REASON,SQ_DATE,SQ_MONEY,FEE,GS_RATE,
                                    INPUT_MAN,T_RG_FEE,RG_FEE,REBATE_FLAG)
            VALUES(@IN_HT_SERIAL_NO,@V_PRODUCT_ID,@V_CONTRACT_BH,@IN_REASON,@IN_SQ_DATE,@IN_SQ_MONEY,@IN_FEE,@V_GS_RATE,
                   @IN_INPUT_MAN,@IN_T_RG_FEE,@IN_HT_FEE,@IN_REBATE_FLAG)
        
        --日志记录
        SET @SSUMMARY = @SBUSI_NAME + '，产品编号: ' + @V_PRODUCT_CODE 
                                    + '，产品名称: ' + @V_PRODUCT_NAME
                                    + '，合同序号: ' + @V_CONTRACT_BH 
                                    + '，合同编号: ' + @V_CONTRACT_SUB_BH
                                    + '，对应合同ID：' + CONVERT(NVARCHAR,@IN_HT_SERIAL_NO)
                                    + '，申请金额：' + CONVERT(NVARCHAR,@IN_SQ_MONEY)
        INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
            VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)

    COMMIT TRANSACTION
    RETURN 100
    END TRY
    
    BEGIN CATCH
        IF @@TRANCOUNT > 0 BEGIN
            ROLLBACK TRANSACTION
        END
        
        DECLARE @V_ERROR_STR NVARCHAR(1000),@V_ERROR_NUMBER INT,@V_ERROR_SEVERITY INT,@V_ERROR_STATE INT,
                @V_ERROR_PROCEDURE sysname,@V_ERROR_LINE INT,@V_ERROR_MESSAGE NVARCHAR(4000)

        SELECT @V_ERROR_STR = N'Message:%s,<br><font color = ''white''>Error:%d,Level:%d,State:%d,Procedure:%s,Line:%d </font>',
               @V_ERROR_NUMBER = ERROR_NUMBER(),
               @V_ERROR_SEVERITY = ERROR_SEVERITY(),
               @V_ERROR_STATE = ERROR_STATE(),
               @V_ERROR_PROCEDURE = ERROR_PROCEDURE(),
               @V_ERROR_LINE = ERROR_LINE(),
               @V_ERROR_MESSAGE = ERROR_MESSAGE()

        RAISERROR(@V_ERROR_STR,@V_ERROR_SEVERITY,1,@V_ERROR_MESSAGE,@V_ERROR_NUMBER,@V_ERROR_SEVERITY,@V_ERROR_STATE,
                  @V_ERROR_PROCEDURE,@V_ERROR_LINE)
        
        RETURN -100
    END CATCH
GO
