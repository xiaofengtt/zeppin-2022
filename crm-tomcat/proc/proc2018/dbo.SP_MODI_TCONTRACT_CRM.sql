USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TCONTRACT_CRM @IN_SERIAL_NO                INTEGER,
									   @IN_RG_MONEY                 DECIMAL(16,3),
									   @IN_JK_TYPE                  NVARCHAR(10),
									   @IN_BANK_ID                  NVARCHAR(10),
									   @IN_BANK_ACCT                NVARCHAR(30),
									   @IN_VALID_PERIOD             INTEGER,                  -- 这个参数没有用
									   @IN_SERVICE_MAN              INTEGER,
									   @IN_SUMMARY                  NVARCHAR(200),
									   @IN_INPUT_MAN                INTEGER,
									   @IN_CHECK_MAN                INTEGER,
									   @IN_QS_DATE                  INTEGER,
									   @IN_JK_DATE                  INTEGER,
									   @IN_LINK_MAN                 INTEGER,
									   @IN_PRODUCT_ID               INTEGER,                  -- 这个参数没有用
									   @IN_BANK_SUB_NAME            NVARCHAR(60),
									   @IN_NEW_CONTRACT_BH          NVARCHAR(16),
									   @IN_ENTITY_PRICE             DECIMAL(16,3),
									   @IN_ENTITY_NAME              NVARCHAR(50),
									   @IN_ENTITY_TYPE              NVARCHAR(10),
									   @IN_CONTRACT_SUB_BH          NVARCHAR(50),
									   @IN_CITY_SERIAL_NO           INTEGER,
									   @IN_TOUCH_TYPE               NVARCHAR(40) = NULL,
									   @IN_TOUCH_TYPE_NAME          NVARCHAR(30) = NULL,
									   @IN_GAIN_ACCT                NVARCHAR(60) = NULL,
									   @IN_FEE_JK_TYPE              INTEGER      = 0,          --费用缴款方式：1从本金扣，2另外交,0不交
									   @IN_BANK_ACCT_TYPE           NVARCHAR(10) = NULL,       --银行账户类型(9920)
									   @IN_START_DATE               INTEGER      = NULL,       --允许从界面输入起始日期、结束日期
									   @IN_END_DATE                 INTEGER      = NULL,
									   @IN_BONUS_FLAG               INTEGER      = 1,          --1、兑付　2、转份额
									   @IN_WITH_BANK_FLAG           INTEGER      = 0,          --是否银信合作 1是 0 否
									   @IN_HT_BANK_ID               NVARCHAR(10) = NULL,       --合同银行
									   @IN_HT_BANK_SUB_NAME         NVARCHAR(60) = NULL,       --合同银行下级支行名称
									   @IN_CHANNEL_ID               INTEGER      = 0,          --销售渠道ID
									   @IN_WITH_SECURITY_FLAG       INTEGER      = 0,          --是否证信合作
									   @IN_WITH_PRIVATE_FLAG        INTEGER      = 0,          --是否私募基金合作
									   @IN_BONUS_RATE               DECIMAL(5,4) = 0,          --转份额比例
									   @IN_PROV_FLAG                INTEGER      = 1,          --1.优先，2一般，3劣后
									   @IN_PROV_LEVEL               NVARCHAR(10) = '120401',   --收益级别（1204）
									   @IN_CHANNEL_TYPE             NVARCHAR(10) = NULL,       --交银:渠道类别(5500)
									   @IN_CHANNEL_MEMO             NVARCHAR(200) = NULL,      --渠道附加信息
									   @IN_CHANNEL_COOPERTYPE       NVARCHAR(10)  = NULL,      --渠道合作方式(5502)
									   @IN_BZJ_FLAG                 INT = 2,                    --是否保证金  1 是 2 否  20120215  LUOHH
									   @IN_MARKET_MONEY            DECIMAL(16,3)    = 0,           --渠道费用
									   @IN_IS_YKGL				   VARCHAR(1)	    = '0',		   --用款方关联标志：1是
									   @IN_XTHTYJSYL			   NVARCHAR(1000)   = ''		   --信托合同预计收益率（文本对象）
WITH ENCRYPTION
AS
    DECLARE @V_ERROR NVARCHAR(200)
    DECLARE @V_BOOK_CODE INT,@V_PRODUCT_ID INT,@V_PRODUCT_CODE NVARCHAR(6),@V_SUB_PRODUCT_ID INT,
            @V_INTRUST_TYPE1 NVARCHAR(10),@V_PRE_CODE NVARCHAR(16),@V_PRE_MONEY DECIMAL(16,3),
            @V_OPEN_FLAG INT,@V_PERIOD_UNIT INT,@V_FACT_MONEY DECIMAL(16,3)
    DECLARE @V_CONTRACT_BH NVARCHAR(16),@V_RG_MONEY DECIMAL(16,3),@V_CUST_ID INT,@V_HT_STATUS NVARCHAR(10),
            @V_CARD_ID NVARCHAR(40),@V_CARD_TYPE NVARCHAR(10),@V_LINK_MAN INT,@V_CHECK_FLAG INT,
            @V_START_DATE INT,@V_END_DATE INT,@V_CONTRACT_SUB_BH NVARCHAR(50)
    DECLARE @V_HT_BANK_NAME NVARCHAR(30),@V_JK_TYPE_NAME NVARCHAR(30),@V_BANK_NAME NVARCHAR(30),
            @V_CITY_NAME NVARCHAR(20),@V_PROV_LEVEL_NAME NVARCHAR(30)
    DECLARE @V_RG_MONEY2 DECIMAL(16,3), @V_RG_FEE_RATE DECIMAL(5,4), @V_RG_FEE_MONEY DECIMAL(16,3),
            @V_JK_TOTAL_MONEY DECIMAL(16,3), @V_GS_RATE DECIMAL(5,4),@V_MODE_FLAG INT,@V_MEND_CHECK_FLAG INT
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200),@V_ENTITY_TYPE_NAME NVARCHAR(30)
    DECLARE @V_INTTEMP INT, @V_CHANNEL_COOPERTYPE_NAME NVARCHAR(30)

    SELECT @V_CHANNEL_COOPERTYPE_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_ID = 5502 AND TYPE_VALUE = @IN_CHANNEL_COOPERTYPE

    BEGIN TRY
    SELECT @V_RET_CODE = -20802000,@IBUSI_FLAG = 20802,@SBUSI_NAME = N'修改客户认购',@SSUMMARY = N'修改客户认购'

    --1.完整性检查
    IF NOT EXISTS(SELECT 1 FROM INTRUST..TCONTRACT WHERE SERIAL_NO = @IN_SERIAL_NO) BEGIN
        SET @V_ERROR = '合同记录不存在！'
        RAISERROR(@V_ERROR,16,3)
    END
    --
    IF ISNULL(@IN_BZJ_FLAG,0) =0
        SET @IN_BZJ_FLAG =2
    --    
    SELECT @V_BOOK_CODE = BOOK_CODE,@V_PRODUCT_CODE = PRODUCT_CODE,@V_PRODUCT_ID = PRODUCT_ID,
           @V_CONTRACT_BH = CONTRACT_BH,@V_LINK_MAN = LINK_MAN,@V_CHECK_FLAG = CHECK_FLAG,
           @V_HT_STATUS = HT_STATUS,@V_PRE_CODE = PRE_CODE,@V_RG_MONEY = RG_MONEY,
           @V_SUB_PRODUCT_ID = SUB_PRODUCT_ID,@V_CONTRACT_SUB_BH = CONTRACT_SUB_BH,@V_MODE_FLAG = MODE_FLAG
           ,@V_MEND_CHECK_FLAG=ISNULL(MEND_CHECK_FLAG,0)
        FROM INTRUST..TCONTRACT WHERE SERIAL_NO = @IN_SERIAL_NO
    SELECT @V_INTRUST_TYPE1 = INTRUST_TYPE1, @V_PERIOD_UNIT = PERIOD_UNIT ,@V_OPEN_FLAG = OPEN_FLAG,
           @IN_VALID_PERIOD = VALID_PERIOD,@V_FACT_MONEY = ISNULL(FACT_MONEY,0),@V_PRE_MONEY = PRE_MONEY
        FROM INTRUST..TPRODUCT WHERE PRODUCT_ID = @V_PRODUCT_ID
    --
    IF ISNULL(@V_SUB_PRODUCT_ID,0) <> 0
        SELECT @V_PERIOD_UNIT = PERIOD_UNIT,@IN_VALID_PERIOD = VALID_PERIOD
            FROM INTRUST..TSUBPRODUCT WHERE PRODUCT_ID = @V_PRODUCT_ID AND SUB_PRODUCT_ID = @V_SUB_PRODUCT_ID
    --1.1.合同期限
    IF ISNULL(@IN_START_DATE,0) = 0
        SET @V_START_DATE = @IN_JK_DATE
    ELSE
        SET @V_START_DATE = @IN_START_DATE
    IF ISNULL(@IN_END_DATE,0) = 0 BEGIN
        IF ISNULL(@V_PERIOD_UNIT,0) = 0
            SET @V_END_DATE = 21000101
        ELSE
            SET @V_END_DATE = INTRUST.dbo.GETDATEADD(@V_PERIOD_UNIT,@IN_VALID_PERIOD,@V_START_DATE)
    END
    ELSE
        SET @V_END_DATE = @IN_END_DATE
    --1.2.合同编号
    IF @IN_NEW_CONTRACT_BH IS NULL OR @IN_NEW_CONTRACT_BH = ''
        SET @IN_NEW_CONTRACT_BH = @V_CONTRACT_BH
    ELSE BEGIN
        WHILE LEN(@IN_NEW_CONTRACT_BH) < 3
            SET @IN_NEW_CONTRACT_BH = '0' + @IN_NEW_CONTRACT_BH
    END
    IF EXISTS(SELECT 1 FROM INTRUST..TCONTRACT WHERE SERIAL_NO <> @IN_SERIAL_NO AND PRODUCT_ID = @V_PRODUCT_ID AND CONTRACT_BH = @IN_NEW_CONTRACT_BH) BEGIN
        SET @V_ERROR = '录入合同编号[' + @IN_NEW_CONTRACT_BH  + ']已经被使用，请检查！'
        RAISERROR(@V_ERROR,16,3)
    END
    --1.3.实际合同编号
    IF RTRIM(ISNULL(@IN_CONTRACT_SUB_BH,'')) = ''
        SET @IN_CONTRACT_SUB_BH = @IN_NEW_CONTRACT_BH
    IF @V_CHECK_FLAG = 2 BEGIN
        IF EXISTS(SELECT 1 FROM INTRUST..TCONTRACT WHERE SERIAL_NO <> @IN_SERIAL_NO AND PRODUCT_ID = @V_PRODUCT_ID AND CONTRACT_SUB_BH = @IN_CONTRACT_SUB_BH) BEGIN
            SET @V_ERROR = '录入合同编号[' + @IN_NEW_CONTRACT_BH  + ']已经被使用，请检查！'
            RAISERROR(@V_ERROR,16,3)
        END
    END
    --1.4.费用
    EXEC INTRUST..SP_INNER_CAL_PRODUCTFEE @V_PRODUCT_ID,@V_CUST_ID,1,@IN_RG_MONEY,@IN_QS_DATE,@V_RG_FEE_MONEY OUTPUT, @V_GS_RATE OUTPUT, @V_RG_FEE_RATE OUTPUT
    IF @IN_FEE_JK_TYPE = 1
        --1.4.1.从本金中扣认购费
        SELECT @V_JK_TOTAL_MONEY = @IN_RG_MONEY, @V_RG_MONEY2 = @IN_RG_MONEY - @V_RG_FEE_MONEY
    ELSE IF @IN_FEE_JK_TYPE = 2
        --1.4.2另外缴认购费
        SELECT @V_JK_TOTAL_MONEY = @IN_RG_MONEY + @V_RG_FEE_MONEY, @V_RG_MONEY2 = @IN_RG_MONEY
    ELSE
        SELECT @V_RG_FEE_MONEY = 0, @V_RG_FEE_RATE = 0, @V_JK_TOTAL_MONEY = @IN_RG_MONEY, @V_RG_MONEY2 = @IN_RG_MONEY
    --1.5. 其他
    IF @V_CHECK_FLAG = 1 OR ISNULL(@V_MODE_FLAG,0) = 1 BEGIN
        IF @V_FACT_MONEY + @IN_RG_MONEY - @V_RG_MONEY  > @V_PRE_MONEY AND @V_OPEN_FLAG <> 1 BEGIN
            SET @V_ERROR = '认购金额超出产品总额度，请检查！'
            RAISERROR(@V_ERROR,16,3)
        END
    END
	IF @IN_LINK_MAN IS NOT NULL AND @IN_LINK_MAN <> 0
           SET @V_LINK_MAN = @IN_LINK_MAN
    SELECT @V_JK_TYPE_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE = @IN_JK_TYPE
    SELECT @V_ENTITY_TYPE_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE = @IN_ENTITY_TYPE
    SELECT @V_BANK_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE = @IN_BANK_ID
    SELECT @V_CITY_NAME = CITY_NAME FROM INTRUST..TPRODUCTCITY WHERE SERIAL_NO = @IN_CITY_SERIAL_NO
    SELECT @V_HT_BANK_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_ID = 1103 AND TYPE_VALUE = @IN_HT_BANK_ID
    IF ISNULL(@IN_PROV_LEVEL,'') = ''
        SET @IN_PROV_LEVEL = '120401'
    SELECT @V_PROV_LEVEL_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE = @IN_PROV_LEVEL

    SET @IN_SERVICE_MAN = @IN_LINK_MAN
    
    BEGIN TRANSACTION
    --2. 合同信息更新
    IF (@V_CHECK_FLAG = 2 OR @V_CHECK_FLAG = 9) AND ISNULL(@V_MODE_FLAG,0) <> 1	OR (@V_CHECK_FLAG = 2 AND @V_MODE_FLAG=1 AND @V_MEND_CHECK_FLAG=2)
    BEGIN
    --2.1. 对已复核的合同的更新:条件：合同已审核，且不是先资金后合同流程，或者是先资金后合同流程时的流程全部审核
        /*UPDATE INTRUST..TCONTRACT
            SET CONTRACT_SUB_BH     = @IN_CONTRACT_SUB_BH,
                QS_DATE             = @IN_QS_DATE,
                LINK_MAN            = @IN_LINK_MAN,
                SERVICE_MAN         = @IN_SERVICE_MAN,
                WITH_BANK_FLAG      = @IN_WITH_BANK_FLAG,
                HT_BANK_ID          = @IN_HT_BANK_ID,
                HT_BANK_NAME        = @V_HT_BANK_NAME,
                HT_BANK_SUB_NAME    = @IN_HT_BANK_SUB_NAME,
                WITH_SECURITY_FLAG  = @IN_WITH_SECURITY_FLAG,
                WITH_PRIVATE_FLAG   = @IN_WITH_PRIVATE_FLAG,
				CHANNEL_ID          =  @IN_CHANNEL_ID,
				CHANNEL_TYPE        =  @IN_CHANNEL_TYPE,
				CHANNEL_COOPERTYPE  =  @IN_CHANNEL_COOPERTYPE,
				CHANNEL_COOPERTYPE_NAME = @V_CHANNEL_COOPERTYPE_NAME,
				MARKET_MONEY        = @IN_MARKET_MONEY,
				CHECK_FLAG = 9,
				IS_YKGL				= @IN_IS_YKGL,		   --用款方关联标志：1是
				XTHTYJSYL			= @IN_XTHTYJSYL
            WHERE SERIAL_NO = @IN_SERIAL_NO
		*/
		IF EXISTS (SELECT * FROM TCONTRACTMODI WHERE CONTRACT_SERIAL=@IN_SERIAL_NO AND ISNULL(CHECK_FLAG,1)=1)
			RAISERROR('本合同已有修改未审核记录，请审核后再修改',16,3)
					
		INSERT INTO TCONTRACTMODI(CONTRACT_SERIAL,CONTRACT_SUB_BH,QS_DATE,JK_DATE,JK_TYPE,JK_TYPE_NAME,LINK_MAN
				,CHANNEL_ID,CHANNEL_TYPE,CHANNEL_MEMO,BONUS_FLAG,BONUS_RATE
				,CHANNEL_COOPERTYPE,CHANNEL_COOPERTYPE_NAME,MARKET_MONEY
				,BANK_ID,BANK_SUB_NAME,BANK_ACCT,GAIN_ACCT
				,SUMMARY,PROV_FLAG,PROV_LEVEL,PROV_LEVEL_NAME,CHECK_FLAG)
			SELECT @IN_SERIAL_NO,@IN_CONTRACT_SUB_BH,@IN_QS_DATE,@IN_JK_DATE,@IN_JK_TYPE,@V_JK_TYPE_NAME,@IN_LINK_MAN
			    ,@IN_CHANNEL_ID,@IN_CHANNEL_TYPE,@IN_CHANNEL_MEMO,@IN_BONUS_FLAG,@IN_BONUS_RATE
				,@IN_CHANNEL_COOPERTYPE,@V_CHANNEL_COOPERTYPE_NAME,@IN_MARKET_MONEY
				,@IN_BANK_ID,@IN_BANK_SUB_NAME,@IN_BANK_ACCT,@IN_GAIN_ACCT
				,@IN_SUMMARY,@IN_PROV_FLAG,@IN_PROV_LEVEL,@V_PROV_LEVEL_NAME,1

         --UPDATE INTRUST..TCONTRACTSG SET CONTRACT_SUB_BH = @IN_CONTRACT_SUB_BH WHERE CONTRACT_SERIAL_NO = @IN_SERIAL_NO
    END
    ELSE BEGIN
    --2.2. 对未复核的合同的更新
    --2.2.1 对预约信息的处理
        UPDATE INTRUST..TPRECONTRACT
            SET LINK_MAN = @V_LINK_MAN
            WHERE PRODUCT_ID = @V_PRODUCT_ID AND PRE_CODE = @V_PRE_CODE AND BOOK_CODE = @V_BOOK_CODE
    --2.2.2.更新合同
        IF @V_HT_STATUS = '120101' OR (@V_HT_STATUS = '120105' AND @V_MODE_FLAG = 1) BEGIN
        --2.2.2.1.正常合同进行更新
            UPDATE INTRUST..TCONTRACT
                SET RG_MONEY            =  @IN_RG_MONEY,
                    JK_TYPE             =  @IN_JK_TYPE,
                    JK_TYPE_NAME        =  @V_JK_TYPE_NAME,
                    BANK_ID             =  @IN_BANK_ID,
                    BANK_NAME           =  @V_BANK_NAME,
                    BANK_ACCT           =  @IN_BANK_ACCT,
                    BANK_SUB_NAME       =  @IN_BANK_SUB_NAME,
                    GAIN_ACCT           =  @IN_GAIN_ACCT,
                    START_DATE          =  @V_START_DATE,
                    END_DATE            =  @V_END_DATE,
                    VALID_PERIOD        =  @IN_VALID_PERIOD,
                    CHECK_MAN           =  @IN_CHECK_MAN,
                    QS_DATE             =  @IN_QS_DATE,
                    JK_DATE             =  @IN_JK_DATE,
                    SUMMARY             =  @IN_SUMMARY,
                    LINK_MAN            = @IN_LINK_MAN,
                    SERVICE_MAN         = @IN_SERVICE_MAN,
                    SERVICE_MAN_SALES   = @IN_SERVICE_MAN,
                    TOUCH_TYPE          =  @IN_TOUCH_TYPE,
                    TOUCH_TYPE_NAME     =  @IN_TOUCH_TYPE_NAME,
                    MODI_MAN            =  @IN_INPUT_MAN,
                    MODI_TIME           =  GETDATE(),
                    CONTRACT_BH         =  @IN_NEW_CONTRACT_BH,
                    ENTITY_PRICE        =  @IN_ENTITY_PRICE,
                    ENTITY_NAME         =  @IN_ENTITY_NAME,
                    ENTITY_TYPE         =  @IN_ENTITY_TYPE ,
                    ENTITY_TYPE_NAME    =  @V_ENTITY_TYPE_NAME,
                    CONTRACT_SUB_BH     =  @IN_CONTRACT_SUB_BH,
                    CITY_SERIAL_NO      =  @IN_CITY_SERIAL_NO,
                    CITY_NAME           =  @V_CITY_NAME,
                    FEE_JK_TYPE         =  @IN_FEE_JK_TYPE,
                    BANK_ACCT_TYPE      =  @IN_BANK_ACCT_TYPE,
                    RG_MONEY2           =  @V_RG_MONEY2,
                    RG_FEE_RATE         =  @V_RG_FEE_RATE,
                    RG_FEE_MONEY        =  @V_RG_FEE_MONEY,
                    JK_TOTAL_MONEY      =  @V_JK_TOTAL_MONEY,
                    BONUS_FLAG          =  @IN_BONUS_FLAG,
                    BONUS_RATE          =  @IN_BONUS_RATE,
                    WITH_BANK_FLAG      =  @IN_WITH_BANK_FLAG,
                    HT_BANK_ID          =  @IN_HT_BANK_ID,
                    HT_BANK_NAME        =  @V_HT_BANK_NAME,
                    HT_BANK_SUB_NAME    =  @IN_HT_BANK_SUB_NAME,
                    CHANNEL_ID          =  @IN_CHANNEL_ID,
                    WITH_SECURITY_FLAG  =  @IN_WITH_SECURITY_FLAG,
                    WITH_PRIVATE_FLAG   =  @IN_WITH_PRIVATE_FLAG,
                    PROV_FLAG           =  @IN_PROV_FLAG,
                    PROV_LEVEL          =  @IN_PROV_LEVEL,
                    PROV_LEVEL_NAME     =  @V_PROV_LEVEL_NAME,
                    CHANNEL_TYPE        =  @IN_CHANNEL_TYPE,
                    CHANNEL_MEMO        =  @IN_CHANNEL_MEMO,
                    CHANNEL_COOPERTYPE  =  @IN_CHANNEL_COOPERTYPE,
                    CHANNEL_COOPERTYPE_NAME = @V_CHANNEL_COOPERTYPE_NAME,
                    BZJ_FLAG            = @IN_BZJ_FLAG,
					MARKET_MONEY        = @IN_MARKET_MONEY,
					IS_YKGL				= @IN_IS_YKGL,		   --用款方关联标志：1是
					XTHTYJSYL			= @IN_XTHTYJSYL
                WHERE SERIAL_NO = @IN_SERIAL_NO
            --如果合同只有一个受益人，且LIST_ID=1，同时修改受益信息
            SELECT @V_INTTEMP = COUNT(*) FROM INTRUST..TBENIFITOR WHERE PRODUCT_ID = @V_PRODUCT_ID AND CONTRACT_BH = @V_CONTRACT_BH
            IF @V_INTTEMP = 1 BEGIN
                UPDATE INTRUST..TBENIFITOR
                    SET TO_AMOUNT       = @IN_RG_MONEY,
                        BEN_AMOUNT      = @IN_RG_MONEY,
                        BEN_MONEY       = @IN_RG_MONEY,
                        JK_TYPE         = @IN_JK_TYPE,
                        JK_TYPE_NAME    = @V_JK_TYPE_NAME,
                        BANK_ID         = @IN_BANK_ID,
                        BANK_NAME       = @V_BANK_NAME,
                        BANK_ACCT       = @IN_BANK_ACCT,
                        BANK_SUB_NAME   = @IN_BANK_SUB_NAME,
                        CUST_ACCT_NAME  = @IN_GAIN_ACCT,
                        BEN_DATE        = @IN_QS_DATE,
                        VALID_PERIOD    = @IN_VALID_PERIOD,
                        BEN_END_DATE    = @V_END_DATE,
                        CONTRACT_BH     = @IN_NEW_CONTRACT_BH,
                        BANK_ACCT_TYPE  = @IN_BANK_ACCT_TYPE,
                        BONUS_FLAG      = @IN_BONUS_FLAG,
                        BONUS_RATE      = @IN_BONUS_RATE,
                        PROV_FLAG       = @IN_PROV_FLAG,
                        PROV_LEVEL      = @IN_PROV_LEVEL,
                        PROV_LEVEL_NAME = @V_PROV_LEVEL_NAME,
                        BZJ_FLAG            = @IN_BZJ_FLAG
                    WHERE PRODUCT_ID = @V_PRODUCT_ID AND CONTRACT_BH = @V_CONTRACT_BH AND LIST_ID = 1
            END
            --当前合同的所有受益人的以下两个字段需要同步修改
            UPDATE INTRUST..TBENIFITOR
                SET CONTRACT_BH     = @IN_NEW_CONTRACT_BH,
                    PROV_FLAG       = @IN_PROV_FLAG
                WHERE PRODUCT_ID = @V_PRODUCT_ID AND CONTRACT_BH = @V_CONTRACT_BH

            ----添加一条客户银行帐号信息--
            SELECT @V_CUST_ID = CUST_ID FROM INTRUST..TCONTRACT WHERE SERIAL_NO = @IN_SERIAL_NO
            IF NOT EXISTS( SELECT 1 FROM INTRUST..TCUSTBANKACCT WHERE CUST_ID = @V_CUST_ID AND BANK_ID = @IN_BANK_ID AND BANK_ACCT = @IN_BANK_ACCT )
                AND (@IN_BANK_ID <> '') AND (NOT @IN_BANK_ID IS NULL)
                AND (@IN_BANK_ACCT <> '') AND (NOT @IN_BANK_ACCT IS NULL)
                AND (NOT @V_CUST_ID IS NULL) BEGIN
                SELECT @V_CARD_TYPE = CARD_TYPE, @V_CARD_ID = CARD_ID  FROM INTRUST..TCUSTOMERINFO WHERE CUST_ID = @V_CUST_ID
                INSERT INTO INTRUST..TCUSTBANKACCT(CUST_ID, BANK_ID, BANK_NAME, BANK_ACCT, CARD_TYPE, CARD_ID)
                      VALUES(@V_CUST_ID, @IN_BANK_ID, @V_BANK_NAME, @IN_BANK_ACCT, @V_CARD_TYPE, @V_CARD_ID)
            END
            ------------------------
        END
        ELSE BEGIN
        --2.2.2.2.其他状态合同只更新以下数据
            UPDATE INTRUST..TCONTRACT
                SET LINK_MAN    = @IN_LINK_MAN,
                    SERVICE_MAN = @IN_SERVICE_MAN,                
                    MODI_MAN  = @IN_INPUT_MAN,
                    MODI_TIME = GETDATE(),
                    QS_DATE   = @IN_QS_DATE,
                    JK_DATE   = @IN_JK_DATE
                WHERE SERIAL_NO = @IN_SERIAL_NO
            UPDATE INTRUST..TMONEYDETAIL
                SET DZ_DATE = @IN_JK_DATE
                WHERE PRODUCT_ID = @V_PRODUCT_ID AND CONTRACT_BH = @V_CONTRACT_BH
        END
    END

    SELECT @SSUMMARY = N'修改客户认购，产品编号：' + @V_PRODUCT_CODE +
                                      N',合同号：' + @V_CONTRACT_BH + N',合同实际编号从' + @V_CONTRACT_SUB_BH + N'-->' + @IN_CONTRACT_SUB_BH
    IF EXISTS(SELECT 1 FROM INTRUST..TCONTRACTSG WHERE CONTRACT_SERIAL_NO = @IN_SERIAL_NO)
            SELECT @SSUMMARY = @SSUMMARY + N',修改客户追加申购,产品编号：' + @V_PRODUCT_CODE +
                                      N',合同号：' + @V_CONTRACT_BH + N',合同实际编号从' + @V_CONTRACT_SUB_BH + N'-->' + @IN_CONTRACT_SUB_BH

    INSERT INTO INTRUST..TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)

    COMMIT TRANSACTION
    END TRY

    BEGIN CATCH
        IF @@TRANCOUNT > 0 ROLLBACK TRANSACTION

        DECLARE @V_ERROR_STR NVARCHAR(1000),@V_ERROR_NUMBER INT,@V_ERROR_SEVERITY INT,@V_ERROR_STATE INT,
                @V_ERROR_PROCEDURE sysname,@V_ERROR_LINE INT,@V_ERROR_MESSAGE NVARCHAR(4000)

        SELECT @V_ERROR_STR = N'Message:%s,Procedure:%s,Line:%d',
               @V_ERROR_NUMBER = ERROR_NUMBER(),
               @V_ERROR_SEVERITY = ERROR_SEVERITY(),
               @V_ERROR_STATE = ERROR_STATE(),
               @V_ERROR_PROCEDURE = ERROR_PROCEDURE(),
               @V_ERROR_LINE = ERROR_LINE(),
               @V_ERROR_MESSAGE = ERROR_MESSAGE()

        RAISERROR(@V_ERROR_STR,@V_ERROR_SEVERITY,1,@V_ERROR_MESSAGE,@V_ERROR_PROCEDURE,@V_ERROR_LINE)

        RETURN -100
    END CATCH
    RETURN 100
GO
