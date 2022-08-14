USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TBENCHANGES @IN_SERIAL_NO       INTEGER,
                                     @IN_TRANS_FLAG      VARCHAR(10),
                                     @IN_TO_AMOUNT       DECIMAL(16,3),
                                     @IN_SX_FEE          DECIMAL(16,3),
                                     @IN_TO_CUST_ID      INTEGER,
                                     @IN_SUMMARY         VARCHAR(200),
                                     @IN_BANK_ID         VARCHAR(10),
                                     @IN_BANK_ACCT       VARCHAR(30),
                                     @IN_INPUT_MAN       INTEGER,
                                     @IN_CHECK_MAN       INTEGER,
                                     @IN_CHANGE_DATE     INTEGER,
                                     @IN_BANK_SUB_NAME   VARCHAR(30),
                                     @IN_FX_CHANGE_FLAG  INTEGER,
                                     @IN_SY_CHANGE_FLAG  INTEGER,
                                     @IN_TRANS_TYPE      VARCHAR(10),
                                     @IN_CHANGE_QS_DATE  INTEGER        = NULL,
                                     @IN_ZQR_NAME        VARCHAR(100)   = NULL,
                                     @IN_CHANGE_END_DATE INTEGER        = NULL,
                                     @IN_SX_FEE1         DECIMAL(16,3)  = NULL,
                                     @IN_SX_FEE2         DECIMAL(16,3)  = NULL,
                                     @IN_SX_FEE3         DECIMAL(16,3)  = NULL,
                                     @IN_RP_FLAG         INTEGER        = 1,
                                     @IN_RP_MONEY        DECIMAL(16,2)  = 0,
                                     @IN_BANK_ACCT_NAME  NVARCHAR(200)  = NULL        --银行账户名
WITH ENCRYPTION
AS
    DECLARE @V_TRANS_FLAG_NAME VARCHAR(30),@V_BANK_NAME VARCHAR(30),@V_CONTRACT_BH varchar(16),@V_CHECK_FLAG INT
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME VARCHAR(40),@SSUMMARY VARCHAR(200),@V_TRANS_TYPE_NAME VARCHAR(30)
    DECLARE @V_TO_LIST_ID INT, @V_PRODUCT_ID INT, @V_FROM_LIST_ID INT, @V_JK_TYPE VARCHAR(10),
            @V_FROM_CUST_ID INT, @V_TO_AMOUNT DECIMAL(16,3),@V_CAN_AMOUNT DECIMAL(16,3),@V_DF_REC_NO INT
    DECLARE @V_INTRUST_INPUT_MAN INT
    --SELECT @V_INTRUST_INPUT_MAN=INTRUST_Operator FROM TOPERATOR_MAP WHERE CRM_Operator=@IN_INPUT_MAN AND INTRUST_BOOKCODE=1
    SELECT @V_INTRUST_INPUT_MAN=@IN_INPUT_MAN
    
  BEGIN TRY
    SELECT @V_RET_CODE = -21702000,@IBUSI_FLAG = 21702
    SELECT @SBUSI_NAME = '修改受益权转让',@SSUMMARY = '修改受益权转让'

    SELECT @V_TRANS_TYPE_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE = @IN_TRANS_TYPE  --20050522谭鸿添加

    SELECT @V_CONTRACT_BH = CONTRACT_BH, @V_CHECK_FLAG = CHECK_FLAG, @V_PRODUCT_ID = PRODUCT_ID, @V_FROM_CUST_ID = FROM_CUST_ID,
           @V_TO_AMOUNT = TO_AMOUNT,@V_DF_REC_NO = DF_REC_NO,@V_FROM_LIST_ID = FROM_LIST_ID, @V_JK_TYPE = JK_TYPE
        FROM INTRUST..TBENCHANGES WHERE SERIAL_NO = @IN_SERIAL_NO
    IF @@ROWCOUNT=0 --RETURN @V_RET_CODE-1 --受益人转让信息不存在
        RAISERROR('受益人转让信息不存在',16,1)
    ELSE IF @V_CHECK_FLAG<>1 AND @V_CHECK_FLAG<>4 --RETURN @V_RET_CODE-2 --已审核不能修改
        RAISERROR('已审核不能修改',16,2)
    ELSE IF NOT EXISTS (SELECT * FROM INTRUST..TCUSTOMERINFO WHERE CUST_ID=@IN_TO_CUST_ID)--RETURN @V_RET_CODE-3 --受让人不存在
        RAISERROR('受让人不存在',16,3)

    IF @V_TO_AMOUNT <> @IN_TO_AMOUNT
    BEGIN
        IF @IN_TRANS_TYPE IN('181512','181514')
            SELECT @V_CAN_AMOUNT = ISNULL(TO_AMOUNT,0) - ISNULL(FROZEN_TMP,0) - ISNULL(FROZEN_MONEY,0) FROM INTRUST..TBENCHANGES WHERE SERIAL_NO = @V_DF_REC_NO
        ELSE
            SELECT @V_CAN_AMOUNT = ISNULL(BEN_AMOUNT,0) - ISNULL(FROZEN_TMP,0)
            FROM INTRUST..TBENIFITOR
            WHERE PRODUCT_ID = @V_PRODUCT_ID AND CONTRACT_BH = @V_CONTRACT_BH AND LIST_ID = @V_FROM_LIST_ID
            
        IF @V_CAN_AMOUNT < @IN_TO_AMOUNT-@V_TO_AMOUNT --RETURN @V_RET_CODE - 4 --受益份额不足
            RAISERROR('受益份额不足',16,3)
        
    END
    SELECT @V_BANK_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE = @IN_BANK_ID
    SELECT @V_TRANS_FLAG_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE = @IN_TRANS_FLAG


    BEGIN TRANSACTION

    UPDATE INTRUST..TBENCHANGES
        SET TRANS_FLAG      = @IN_TRANS_FLAG,
            TRANS_FLAG_NAME = @V_TRANS_FLAG_NAME,
            TO_AMOUNT       = @IN_TO_AMOUNT,
            SX_FEE          = @IN_SX_FEE,
            SX_FEE1         = @IN_SX_FEE1,
            SX_FEE2         = @IN_SX_FEE2,
            SX_FEE3         = @IN_SX_FEE3,
            SUMMARY         = @IN_SUMMARY,
            BANK_ID         = @IN_BANK_ID,
            BANK_NAME       = @V_BANK_NAME,
            BANK_SUB_NAME   = @IN_BANK_SUB_NAME,
            BANK_ACCT       = @IN_BANK_ACCT,
            CHECK_MAN       = @IN_CHECK_MAN,
            CHANGE_DATE     = @IN_CHANGE_DATE,
            TRANS_TYPE_NAME = @V_TRANS_TYPE_NAME,
            TRANS_TYPE      = @IN_TRANS_TYPE,
            FX_CHANGE_FLAG  = @IN_FX_CHANGE_FLAG,
            SY_CHANGE_FLAG  = @IN_SY_CHANGE_FLAG,
            CHANGE_QS_DATE  = @IN_CHANGE_QS_DATE,
            ZQR_NAME        = @IN_ZQR_NAME,
            CHANGE_END_DATE = @IN_CHANGE_END_DATE,
            INPUT_MAN       = @V_INTRUST_INPUT_MAN,
            RP_FLAG         = @IN_RP_FLAG,
            RP_MONEY        = @IN_RP_MONEY,
            BANK_ACCT_NAME  = @IN_BANK_ACCT_NAME,
            CHECK_FLAG      = CASE WHEN CHECK_FLAG=4 THEN 1 ELSE CHECK_FLAG END
        WHERE SERIAL_NO = @IN_SERIAL_NO

    --更新转让让临时冻结金额
    IF @V_TO_AMOUNT <> @IN_TO_AMOUNT BEGIN
        IF @IN_TRANS_TYPE IN('181512','181514')
            UPDATE INTRUST..TBENCHANGES
                SET FROZEN_TMP = ISNULL(FROZEN_TMP,0) + @IN_TO_AMOUNT - @V_TO_AMOUNT
                WHERE SERIAL_NO = @V_DF_REC_NO
        ELSE
            UPDATE INTRUST..TBENIFITOR
                SET FROZEN_TMP = ISNULL(FROZEN_TMP,0) + @IN_TO_AMOUNT - @V_TO_AMOUNT
                WHERE PRODUCT_ID = @V_PRODUCT_ID AND CONTRACT_BH = @V_CONTRACT_BH AND LIST_ID = @V_FROM_LIST_ID
    END

    SELECT @SSUMMARY = '修改受益权转让，合同编号：' + @V_CONTRACT_BH
    INSERT INTO INTRUST..TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@V_INTRUST_INPUT_MAN,@SSUMMARY)

    COMMIT TRANSACTION
  END TRY

  BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION

        DECLARE @V_ERROR_STR NVARCHAR(1000),@V_ERROR_NUMBER INT,@V_ERROR_SEVERITY INT,@V_ERROR_STATE INT,
                @V_ERROR_PROCEDURE sysname,@V_ERROR_LINE INT,@V_ERROR_MESSAGE NVARCHAR(4000)
        SELECT @V_ERROR_STR = N',Message %s,Error %d,Level %d,State %d,Procedure %s,Line %d',
               @V_ERROR_NUMBER = ERROR_NUMBER(),
               @V_ERROR_SEVERITY = ERROR_SEVERITY(),
               @V_ERROR_STATE = ERROR_STATE(),
               @V_ERROR_PROCEDURE = ERROR_PROCEDURE(),
               @V_ERROR_LINE = ERROR_LINE(),
               @V_ERROR_MESSAGE = ERROR_MESSAGE()           

        RAISERROR(@V_ERROR_STR,@V_ERROR_SEVERITY,1,@V_ERROR_MESSAGE,@V_ERROR_NUMBER,@V_ERROR_SEVERITY,
                      @V_ERROR_STATE,@V_ERROR_PROCEDURE,@V_ERROR_LINE)
        RETURN -100

  END CATCH
    RETURN 100
GO
