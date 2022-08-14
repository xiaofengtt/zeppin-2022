USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TSQSTOPCONTRACT_CRM @IN_SERIAL_NO       INT,                   --序号
											 @IN_REASON          NVARCHAR(200),         --退款原因，字符串描述
											 @IN_SQ_DATE         INT,                   --申请办理退款日期
											 @IN_FEE             DECIMAL(16,2),         --申请退款手续费
											 @IN_INPUT_MAN       INT,                   --录入操作员
											 @IN_T_RG_FEE        INT           = 0,     --是否退认购费 0不退 1退
											 @IN_SQ_MONEY        DECIMAL(16,2) = NULL,  --申请退款金额
											 @IN_HT_FEE          DECIMAL(16,2) = 0      --退款金额对应的认购费/申购费
WITH ENCRYPTION
AS
    DECLARE @V_PRODUCT_ID INT,@V_PRODUCT_CODE NVARCHAR(6),@V_PRODUCT_NAME NVARCHAR(120),@V_CONTRACT_BH VARCHAR(16),@V_CONTRACT_SUB_BH NVARCHAR(50),
            @V_CUST_ID INT,@V_GS_RATE DECIMAL(5,4),@V_HT_TO_MONEY DECIMAL(16,2),@V_CHECK_FLAG INT,@V_HT_SERIAL_NO INT,@V_REBATE_FLAG INT
            
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME VARCHAR(40),@SSUMMARY VARCHAR(200)
    SET @V_RET_CODE = -20403000
    SET @IBUSI_FLAG = 20403

    BEGIN TRY
    BEGIN TRANSACTION
    
        IF NOT EXISTS(SELECT * FROM INTRUST..TSQSTOPCONTRACT WHERE SERIAL_NO = @IN_SERIAL_NO)
            RAISERROR('退款记录不存在！',16,1)

        SELECT @V_HT_SERIAL_NO = HT_SERIAL_NO,@V_CHECK_FLAG = CHECK_FLAG,@V_REBATE_FLAG = REBATE_FLAG
            FROM INTRUST..TSQSTOPCONTRACT 
            WHERE SERIAL_NO = @IN_SERIAL_NO
            
        IF @V_CHECK_FLAG <> 1
            RAISERROR('该退款记录已审核不能修改！',16,2)
            
        IF @V_REBATE_FLAG = 1 BEGIN
            SELECT @V_PRODUCT_ID = A.PRODUCT_ID,@V_PRODUCT_CODE = B.PRODUCT_CODE,@V_PRODUCT_NAME = B.PRODUCT_NAME,
                   @V_CONTRACT_BH = A.CONTRACT_BH,@V_CONTRACT_SUB_BH = A.CONTRACT_SUB_BH,@V_CUST_ID = A.CUST_ID,
                   @V_HT_TO_MONEY = A.TO_MONEY
                FROM INTRUST..TCONTRACT A,INTRUST..TPRODUCT B
                WHERE A.SERIAL_NO = @V_HT_SERIAL_NO AND A.PRODUCT_ID = B.PRODUCT_ID
        END
        ELSE BEGIN
            SELECT @V_PRODUCT_ID = A.PRODUCT_ID,@V_PRODUCT_CODE = B.PRODUCT_CODE,@V_PRODUCT_NAME = B.PRODUCT_NAME,
                   @V_CONTRACT_BH = A.CONTRACT_BH,@V_CONTRACT_SUB_BH = A.CONTRACT_SUB_BH,@V_CUST_ID = A.CUST_ID,
                   @V_HT_TO_MONEY = A.TO_MONEY
                FROM INTRUST..TCONTRACTSG A,INTRUST..TPRODUCT B
                WHERE A.SERIAL_NO = @V_HT_SERIAL_NO AND A.PRODUCT_ID = B.PRODUCT_ID
        END

        IF @IN_SQ_MONEY > @V_HT_TO_MONEY OR ISNULL(@IN_SQ_MONEY,0) = 0
            SET @IN_SQ_MONEY = @V_HT_TO_MONEY
            
        IF ISNULL(@IN_T_RG_FEE,0) = 0
            SET @IN_HT_FEE = 0

        --计算退款费
        IF @IN_FEE IS NULL
            EXEC INTRUST..SP_INNER_CAL_PRODUCTFEE @V_PRODUCT_ID,@V_CUST_ID,5,@IN_SQ_MONEY,@IN_SQ_DATE,@IN_FEE OUTPUT,@V_GS_RATE OUTPUT

        UPDATE INTRUST..TSQSTOPCONTRACT
            SET REASON      = @IN_REASON,
                SQ_DATE     = @IN_SQ_DATE,
                FEE         = @IN_FEE,
                GS_RATE     = @V_GS_RATE,
                T_RG_FEE    = @IN_T_RG_FEE,
                SQ_MONEY    = @IN_SQ_MONEY,
                RG_FEE      = @IN_HT_FEE
            WHERE SERIAL_NO = @IN_SERIAL_NO


        --日志记录
        SET @SBUSI_NAME = CASE @V_REBATE_FLAG WHEN 1 THEN '发行期退款申请' ELSE '申购期退款申请' END
        SET @SSUMMARY = @SBUSI_NAME + '，产品编号: ' + @V_PRODUCT_CODE 
                                    + '，产品名称: ' + @V_PRODUCT_NAME
                                    + '，合同序号: ' + @V_CONTRACT_BH 
                                    + '，合同编号: ' + @V_CONTRACT_SUB_BH
                                    + '，对应合同ID：' + CONVERT(NVARCHAR,@V_HT_SERIAL_NO)
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
