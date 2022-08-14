USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TCONTRACTUNREAL @IN_PREPRODUCT_ID           INTEGER,     -- 预发行产品
                                        @IN_CUST_ID                 INTEGER,
                                        @IN_CONTRACT_SUB_BH         NVARCHAR(80), -- 合同的实际编号                                        
                                        @IN_PROV_FLAG               INTEGER         = 1,            --1.优先，2一般，3劣后
                                        @IN_PROV_LEVEL              NVARCHAR(10)    = '120401',     --收益级别（1204）
                                        @IN_QS_DATE                 INTEGER,
                                        @IN_RG_MONEY                DECIMAL(16,3),                                        
                                        @IN_JK_TYPE                 NVARCHAR(10),
                                        @IN_JK_DATE                 INTEGER,                                        
                                        @IN_BANK_ID                 NVARCHAR(10),  --受益银行(1103)
                                        @IN_BANK_SUB_NAME           NVARCHAR(60), --收益银行支行
                                        @IN_BANK_ACCT               NVARCHAR(30),
                                        @IN_BANK_ACCT_TYPE          NVARCHAR(10)    = NULL,         --银行账户类型(9920)
                                        @IN_GAIN_ACCT               NVARCHAR(60)    = NULL,         --银行帐号户名                                         
                                        @IN_SERVICE_MAN             INTEGER, -- 客户经理
                                        @IN_EXPECT_ROR_LOWER        DECIMAL(5,4)    = NULL,         --预期收益率区间
                                        @IN_EXPECT_ROR_UPPER        DECIMAL(5,4)    = NULL,         --预期收益率区间
                                        @IN_INPUT_MAN               INTEGER,
                                        @IN_SUMMARY                NVARCHAR(200),                                        
                                        @OUT_SERIAL_NO              INTEGER         OUTPUT                                                          
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_PRODUCT_CODE NVARCHAR(6),@V_PRODUCT_NAME NVARCHAR(60),@V_CURRENCY_ID NVARCHAR(2),
            @V_SUB_FLAG BIT,@V_PRE_NUM INT,@V_FACT_NUM INT,@V_OPEN_FLAG INT,@V_PERIOD_UNIT INT,
            @V_INTRUST_FLAG1 INT,@V_INTRUST_TYPE1 NVARCHAR(10),@V_STATUS NVARCHAR(10),
            @V_FACT_MONEY DECIMAL(16,3),@DT_INTRUST INT
    DECLARE @V_CUST_ID INT,@V_CUST_NAME NVARCHAR(60),@V_CUST_TYPE_NAME NVARCHAR(10),@V_SEX_NAME NVARCHAR(10)
    DECLARE @V_CARD_ID NVARCHAR(40),@V_ITEM_ID INT
    DECLARE @V_CARD_TYPE NVARCHAR(10),@V_CARD_TYPE_NAME NVARCHAR(30)
    DECLARE @V_CUST_LEVEL NVARCHAR(10)
    DECLARE @V_BANK_NAME NVARCHAR(30)
    DECLARE @V_START_DATE INT,@V_END_DATE INT
    DECLARE @V_CUST_TYPE INT
    DECLARE @V_PROV_LEVEL_NAME NVARCHAR(20), @V_FPRG_FLAG CHAR(1)

    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -50102000
    SELECT @IBUSI_FLAG = 50102
    SELECT @SBUSI_NAME = N'非正式合同录入'
    SELECT @SSUMMARY = N'非正式合同录入'
    SELECT @DT_INTRUST=VALUE FROM TSYSCONTROL WHERE FLAG_TYPE='DT_INTRUST'--0无信托1分布式查询和更新信托数据库2本地信托数据库

    BEGIN TRY
    --1. 合同数据完整性计算
    SET @V_CUST_LEVEL = '111101'
    SET @OUT_SERIAL_NO = 0
    IF ISNULL(@IN_PROV_FLAG,0) = 0 
        SET @IN_PROV_FLAG = 1
    IF ISNULL(@IN_PROV_LEVEL,'') = '' 
        SET @IN_PROV_LEVEL = '120401'
      
    IF ISNULL(@IN_QS_DATE,0) = 0
        SELECT @IN_QS_DATE = CONVERT(INT,CONVERT(CHAR(8),GETDATE(),112))
    IF ISNULL(@IN_JK_DATE,0) = 0
        SELECT @IN_JK_DATE = @IN_QS_DATE
        
    IF NOT EXISTS (SELECT 1 FROM TPREPRODUCT WHERE PREPRODUCT_ID = @IN_PREPRODUCT_ID)
        RAISERROR('预发行产品不存在！',16,1)
         
    SELECT @V_STATUS = STATUS, @V_PRODUCT_NAME = PREPRODUCT_NAME 
        FROM TPREPRODUCT WHERE PREPRODUCT_ID = @IN_PREPRODUCT_ID
    IF @V_STATUS <> '110202' 
        RAISERROR('产品不在推介期不能认购！',16,1)
    
    IF @DT_INTRUST=1 --分布式查询
		SELECT @V_CUST_NAME = CUST_NAME,@IN_SERVICE_MAN = SERVICE_MAN,@V_CUST_TYPE = CUST_TYPE,
           @V_CARD_TYPE = CARD_TYPE, @V_CARD_ID = CARD_ID
        FROM SRV_Intrust.INTRUST.dbo.TCUSTOMERINFO WHERE CUST_ID = @IN_CUST_ID
    ELSE IF @DT_INTRUST=2 --本地信托数据库
    SELECT @V_CUST_NAME = CUST_NAME,@IN_SERVICE_MAN = SERVICE_MAN,@V_CUST_TYPE = CUST_TYPE,
           @V_CARD_TYPE = CARD_TYPE, @V_CARD_ID = CARD_ID
        FROM INTRUST..TCUSTOMERINFO WHERE CUST_ID = @IN_CUST_ID

    --1.6.实际合同编号
    IF RTRIM(ISNULL(@IN_CONTRACT_SUB_BH,'')) = ''
        RAISERROR('实际合同编号为空！',16,1)

    IF EXISTS(SELECT 1 FROM TCONTRACTUNREAL WHERE PREPRODUCT_ID = @IN_PREPRODUCT_ID AND CONTRACT_SUB_BH = @IN_CONTRACT_SUB_BH)
        RAISERROR('实际合同编号已存在！',16,1)

    --1.9. 其他数据
    IF ISNULL(@IN_GAIN_ACCT,'')=''
        SELECT @IN_GAIN_ACCT = @V_CUST_NAME


    BEGIN TRANSACTION 
    
    INSERT INTO TCONTRACTUNREAL(PREPRODUCT_ID, CUST_ID, CONTRACT_SUB_BH, QS_DATE, RG_MONEY, JK_DATE, JK_TYPE,
                          BANK_ID, BANK_SUB_NAME, BANK_ACCT, BANK_ACCT_TYPE, GAIN_ACCT,
                          INPUT_MAN, INPUT_TIME, SERVICE_MAN, PROV_FLAG, PROV_LEVEL,
                          EXPECT_ROR_LOWER, EXPECT_ROR_UPPER, SUMMARY, STATUS)
        VALUES (@IN_PREPRODUCT_ID, @IN_CUST_ID, @IN_CONTRACT_SUB_BH, @IN_QS_DATE, @IN_RG_MONEY, @IN_JK_DATE, @IN_JK_TYPE,
                @IN_BANK_ID, @IN_BANK_SUB_NAME, @IN_BANK_ACCT, @IN_BANK_ACCT_TYPE, @IN_GAIN_ACCT,
                @IN_INPUT_MAN, GETDATE(), @IN_SERVICE_MAN, @IN_PROV_FLAG, @IN_PROV_LEVEL,
                @IN_EXPECT_ROR_LOWER, @IN_EXPECT_ROR_UPPER, @IN_SUMMARY, 1)
    SET @OUT_SERIAL_NO = @@IDENTITY    
   
    --2.3.添加一条客户银行帐号信息--
    IF ISNULL(@IN_BANK_ID,'') <> '' AND ISNULL(@IN_BANK_ACCT,'') <> '' 
        AND NOT EXISTS (SELECT 1 FROM INTRUST..TCUSTBANKACCT WHERE CUST_ID = @IN_CUST_ID AND BANK_ID = @IN_BANK_ID AND BANK_ACCT = @IN_BANK_ACCT )
    BEGIN
        SELECT @V_BANK_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_BANK_ID
        IF @DT_INTRUST=1 --分布式查询
            INSERT INTO SRV_Intrust.INTRUST.dbo.TCUSTBANKACCT(CUST_ID, BANK_ID, BANK_NAME, BANK_ACCT, CARD_TYPE, CARD_ID)
                VALUES(@IN_CUST_ID, @IN_BANK_ID, @V_BANK_NAME, @IN_BANK_ACCT, @V_CARD_TYPE, @V_CARD_ID)
        ELSE IF @DT_INTRUST=2 --本地信托数据库
            INSERT INTO INTRUST..TCUSTBANKACCT(CUST_ID, BANK_ID, BANK_NAME, BANK_ACCT, CARD_TYPE, CARD_ID)
                VALUES(@IN_CUST_ID, @IN_BANK_ID, @V_BANK_NAME, @IN_BANK_ACCT, @V_CARD_TYPE, @V_CARD_ID)
    END

    SELECT @SSUMMARY = N'非正式合同录入，预发行产品ID：' + RTRIM(CONVERT(CHAR,@IN_PREPRODUCT_ID)) +
                                      N',合同编号: ' + @IN_CONTRACT_SUB_BH   +
                                      N',委托人：'   + @V_CUST_NAME          +
                                      N',委托金额：' + RTRIM(CONVERT(CHAR,@IN_RG_MONEY))
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME, @IN_INPUT_MAN,@SSUMMARY)

    COMMIT TRANSACTION
    RETURN 100
    
    END TRY
    
    BEGIN CATCH
        IF @@TRANCOUNT > 0 BEGIN
            ROLLBACK TRANSACTION
        END
        
        DECLARE @V_ERROR_STR NVARCHAR(1000),@V_ERROR_NUMBER INT,@V_ERROR_SEVERITY INT,@V_ERROR_STATE INT,
                @V_ERROR_PROCEDURE sysname,@V_ERROR_LINE INT,@V_ERROR_MESSAGE NVARCHAR(4000)

        SELECT @V_ERROR_STR = N'Message:%s,Error:%d,Level:%d,State:%d,Procedure:%s,Line:%d',
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
