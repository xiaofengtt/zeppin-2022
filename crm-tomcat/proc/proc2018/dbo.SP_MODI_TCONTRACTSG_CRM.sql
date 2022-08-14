USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TCONTRACTSG_CRM @IN_SERIAL_NO              INT,                        --序号
                                     @IN_NEW_CONTRACT_BH        NVARCHAR(16),               --合同编号
                                     @IN_CONTRACT_SUB_BH        NVARCHAR(200),               --合同实际编号
                                     @IN_SG_MONEY               DECIMAL(16,3),              --申购金额
                                     @IN_SG_PRICE               DECIMAL(16,10),             --申购价格（申请时此值无用）
                                     @IN_JK_TYPE                NVARCHAR(10),               --缴款方式(1114)
                                     @IN_BANK_ID                NVARCHAR(10),               --信托利益银行编号(1103)
                                     @IN_BANK_ACCT              NVARCHAR(30),               --信托利益银行账号
                                     @IN_BANK_SUB_NAME          NVARCHAR(60),               --支行名称
                                     @IN_GAIN_ACCT              NVARCHAR(60),               --10.受益银行帐户户名
                                     @IN_QS_DATE                INT,                        --签署日期
                                     @IN_JK_DATE                INT,                        --缴款日期
                                     @IN_START_DATE             INT,                        --起始日期
                                     @IN_VALID_PERIOD           INT,                        --合同期限
                                     @IN_LINK_MAN               INT,                        --联系人
                                     @IN_SERVICE_MAN            INT,                        --客户经理
                                     @IN_CITY_SERIAL_NO         INT,                        --推介地编号
                                     @IN_TOUCH_TYPE             NVARCHAR(40),               --联系方式
                                     @IN_TOUCH_TYPE_NAME        NVARCHAR(30),               --联系方式说明
                                     @IN_FEE_JK_TYPE            INT,                        --20.0无需交，1从本金扣，2另外交
                                     @IN_SUMMARY                NVARCHAR(200),              --描述
                                     @IN_INPUT_MAN              INT,                        --操作员
                                     @IN_BONUS_FLAG             INT = 1,                    --1、兑付　2、转份额
                                     @IN_BANK_ACCT_TYPE         NVARCHAR(10)    = NULL,        --银行账户类型(9920)
                                     @IN_WITH_BANK_FLAG         INT = 0,                       --是否银信合作 1是 0 否 add by liug 20110124
                                     @IN_HT_BANK_ID             NVARCHAR(10)    = NULL,        --合同银行 add by liug 20110124
                                     @IN_HT_BANK_SUB_NAME       NVARCHAR(60)    = NULL,        --合同银行下级支行名称 add by liug 20110124
                                     @IN_WITH_SECURITY_FLAG     INT = 0,                       --是否证信合作
                                     @IN_WITH_PRIVATE_FLAG      INT = 0,                       --是否私募基金合作
                                     @IN_BONUS_RATE             DECIMAL(5,4)    = 0,           --30.转份额比例
                                     @IN_PROV_FLAG              INT = 1,                       --1.优先，2一般，3劣后
                                     @IN_PROV_LEVEL             NVARCHAR(10)    = '120401',    --收益级别（1204）
                                     @IN_CHANNEL_ID             INTEGER         = 0,           --销售渠道ID
                                     @IN_CHANNEL_TYPE           NVARCHAR(10)    = NULL,        --交银:渠道类别(5500)
                                     @IN_CHANNEL_MEMO           NVARCHAR(200)   = NULL,        --渠道附加信息
                                     @IN_BZJ_FLAG               INTEGER         = 2,           --是否保证金  1 是 2 否  20120215  LUOHH
                                     @IN_CHANNEL_COOPERTYPE     NVARCHAR(10)    = '',          --渠道合作方式(5502)
                                     @IN_MARKET_MONEY           DECIMAL(16,3)   = 0,           --渠道费用
                                     @IN_SUB_PRODUCT_ID         INT             = 0,           --子产品ID   20120519  LUOHH
                                     @IN_RECOMMEND_MAN          INT             = 0,           --40.合同推荐人
                                     @IN_BANK_PROVINCE          NVARCHAR(10)    = '',          --受益账户开户行所在省
                                     @IN_BANK_CITY              NVARCHAR(10)    = '',          --受益账户开户行所在市
                                     @IN_EXPECT_ROR_LOWER       DECIMAL(16,10)    = NULL,        --预期收益率区间
                                     @IN_EXPECT_ROR_UPPER       DECIMAL(16,10)    = NULL,        --预期收益率区间
                                     @IN_RECOMMEND_MAN_NAME     NVARCHAR(30)    = '',           --合同推荐人(外部)
                                     @IN_CONTACT_ID				INT				= NULL,			--本合同的客户联系人ID
                                     @IN_PERIOD_UNIT            INT             = NULL,          --合同期限的单位：0无期限 1天 2月 3年
                                     @IN_MONEY_ORIGIN           NVARCHAR(10)    = NULL,   --全要素资金/资产来源
                                     @IN_SUB_MONEY_ORIGIN       NVARCHAR(10)    = NULL,      --子产品资金来源
                                     @IN_HT_CUST_NAME           NVARCHAR(200)   = '',         --50.合同联系人 姓名
								     @IN_HT_CUST_ADDRESS        NVARCHAR(200)   = '',         --合同联系人 地址
								     @IN_HT_CUST_TEL            NVARCHAR(200)   = '',         --合同联系人 电话
								     @IN_SPOT_DEAL              INTEGER         = 2,          --是否非现场交易
								     @IN_PROPERTY_SOURCE        NVARCHAR(200)   = '',         --54.信托财产来源 1167
                                     @IN_P_S_OTHER_EXPLAIN      NVARCHAR(1000)  = '',         --55.信托财产来源之其他收入来源说明
									 @IN_CONTRACT_TYPE          INT = 0               --1 前台销售人员合同2产品部门合同3代销合同

WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -20497000, @IBUSI_FLAG  = 20497
    SELECT @SBUSI_NAME = N'修改申购记录', @SSUMMARY = N'修改申购记录'
    DECLARE @V_CHANNEL_COOPERTYPE_NAME NVARCHAR(30)
    DECLARE @V_NUM INT, @V_HT_PRE_CODE NVARCHAR(30), @V_INTTEMP INT
    DECLARE @V_PRODUCT_CODE NVARCHAR(6), @V_PRODUCT_NAME NVARCHAR(60), @V_CURRENCY_ID NVARCHAR(2), @V_END_DATE INT,
            @V_JK_TYPE_NAME NVARCHAR(30), @V_BANK_NAME NVARCHAR(30), @V_CITY_NAME NVARCHAR(20), @V_CUST_NAME NVARCHAR(100),
            @V_CARD_TYPE NVARCHAR(10), @V_CARD_ID NVARCHAR(40)
    DECLARE @V_PRODUCT_ID INT, @V_CUST_ID INT, @V_CHECK_FLAG INT, @V_BOOK_CODE INT, @V_CONTRACT_BH NVARCHAR(16),
            @V_CONTRACT_SUB_BH NVARCHAR(200),@V_PERIOD_UNIT INT,@V_PRODUCT_END_DATE INT
    DECLARE @V_SG_MONEY2 DECIMAL(16,3), @V_RG_FEE_RATE DECIMAL(5,4), @V_RG_FEE_MONEY DECIMAL(16,3), @V_JK_TOTAL_MONEY DECIMAL(16,3), @V_GS_RATE DECIMAL(5,4)
    DECLARE @V_HT_BANK_NAME NVARCHAR(30),@V_PROV_LEVEL_NAME NVARCHAR(30),@V_SUB_PRODUCT_ID INT
    DECLARE @V_BANK_PROVINCE_NAME NVARCHAR(30),@V_BANK_CITY_NAME NVARCHAR(30)
    DECLARE @V_CONTACT_ID INT
    DECLARE @V_MONEY_ORIGIN_NAME NVARCHAR(30), @V_SUB_MONEY_ORIGIN_NAME NVARCHAR(30)
   
    SELECT @V_HT_BANK_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_ID = 1103 AND TYPE_VALUE = @IN_HT_BANK_ID
    IF NOT EXISTS(SELECT 1 FROM INTRUST..TCONTRACTSG WHERE SERIAL_NO = @IN_SERIAL_NO)
        RETURN @V_RET_CODE - 4  --记录不存在
    
    IF @IN_PERIOD_UNIT IS NULL    
        SELECT @V_PERIOD_UNIT=PERIOD_UNIT
          FROM INTRUST..TCONTRACTSG
          WHERE SERIAL_NO=@IN_SERIAL_NO
    ELSE 
        SELECT @V_PERIOD_UNIT=@IN_PERIOD_UNIT
          
    SELECT @V_PRODUCT_ID = PRODUCT_ID, @V_CUST_ID = CUST_ID, @V_CHECK_FLAG = CHECK_FLAG, @V_BOOK_CODE = BOOK_CODE,
           @V_CONTRACT_BH = CONTRACT_BH, @V_CONTRACT_SUB_BH = CONTRACT_SUB_BH,@V_SUB_PRODUCT_ID = SUB_PRODUCT_ID,@V_CONTACT_ID = CONTACT_ID
        FROM INTRUST..TCONTRACTSG WHERE SERIAL_NO = @IN_SERIAL_NO
    SELECT @V_PRODUCT_CODE = PRODUCT_CODE, @V_PRODUCT_NAME = PRODUCT_NAME, @V_CURRENCY_ID = CURRENCY_ID,
           @V_HT_PRE_CODE = ISNULL(PRE_CODE,''),@V_PRODUCT_END_DATE =END_DATE--,@V_PERIOD_UNIT =PERIOD_UNIT
        FROM INTRUST..TPRODUCT WHERE PRODUCT_ID = @V_PRODUCT_ID
    SELECT @V_CUST_NAME = CUST_NAME, @V_CARD_TYPE = CARD_TYPE, @V_CARD_ID = CARD_ID FROM INTRUST..TCUSTOMERINFO WHERE CUST_ID = @V_CUST_ID
    IF @IN_QS_DATE IS NULL OR @IN_QS_DATE = 0
       SELECT @IN_QS_DATE = CONVERT(INT,CONVERT(CHAR(8),GETDATE(),112))
    IF @IN_JK_DATE IS NULL OR @IN_JK_DATE = 0
        SELECT @IN_JK_DATE = @IN_QS_DATE
    SELECT @V_END_DATE = INTRUST.dbo.GETDATEADD(@V_PERIOD_UNIT,@IN_VALID_PERIOD,@IN_START_DATE)
    --
    IF ISNULL(@IN_BZJ_FLAG,0) =0
        SET @IN_BZJ_FLAG =2
    --
    IF ISNULL(@IN_SUB_PRODUCT_ID,0) <> 0
        SET @V_SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID
    --如果@IN_CONTACT_ID（联系人ID）为空，或者是0，则不更新联系人，以便不会覆盖原有的数据
    IF ISNULL(@IN_CONTACT_ID,0) = 0
    BEGIN
		SET @IN_CONTACT_ID = @V_CONTACT_ID
    END
    -- 
    --如果合同结束日期大于产品结束日，则已产品结束日期为限         add by luohh 20100430
    IF @V_END_DATE >@V_PRODUCT_END_DATE
        SET @V_END_DATE = @V_PRODUCT_END_DATE
    SELECT @V_CHANNEL_COOPERTYPE_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_CHANNEL_COOPERTYPE
    SELECT @V_JK_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_JK_TYPE
    SELECT @V_BANK_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE = @IN_BANK_ID
    SELECT @V_BANK_PROVINCE_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_BANK_PROVINCE
    SELECT @V_BANK_CITY_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_BANK_CITY
    IF ISNULL(@IN_PROV_LEVEL,'') = ''
        SET @IN_PROV_LEVEL = '120401'
    SELECT @V_PROV_LEVEL_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE = @IN_PROV_LEVEL
    SELECT @V_CITY_NAME = CITY_NAME FROM INTRUST..TPRODUCTCITY WHERE SERIAL_NO = @IN_CITY_SERIAL_NO
    SELECT @V_MONEY_ORIGIN_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_ID = 1158 AND TYPE_VALUE = @IN_MONEY_ORIGIN
    SELECT @V_SUB_MONEY_ORIGIN_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_ID = 1158 AND TYPE_VALUE = @IN_SUB_MONEY_ORIGIN
    IF @IN_NEW_CONTRACT_BH IS NULL OR @IN_NEW_CONTRACT_BH = ''
        SELECT @IN_NEW_CONTRACT_BH = @V_CONTRACT_BH
    ELSE
    BEGIN
        WHILE LEN(@IN_NEW_CONTRACT_BH)<3
            SELECT @IN_NEW_CONTRACT_BH = '0' + @IN_NEW_CONTRACT_BH
    END
    IF RTRIM(ISNULL(@IN_CONTRACT_SUB_BH,'')) = ''
        SELECT @IN_CONTRACT_SUB_BH = @V_CONTRACT_SUB_BH
/*
    IF @V_CONTRACT_SUB_BH <> @IN_CONTRACT_SUB_BH AND RTRIM(@V_HT_PRE_CODE) <> ''
        SELECT @IN_CONTRACT_SUB_BH = SUBSTRING((RTRIM(@V_HT_PRE_CODE)+@IN_NEW_CONTRACT_BH),1,50)
*/
    EXEC INTRUST..SP_INNER_CAL_PRODUCTFEE @V_PRODUCT_ID,@V_CUST_ID,2,@IN_SG_MONEY,@IN_QS_DATE,@V_RG_FEE_MONEY OUTPUT, @V_GS_RATE OUTPUT, @V_RG_FEE_RATE OUTPUT
    IF @IN_FEE_JK_TYPE = 1 --从本金中扣申购费
        SELECT @V_JK_TOTAL_MONEY = @IN_SG_MONEY, @V_SG_MONEY2 = @IN_SG_MONEY - @V_RG_FEE_MONEY
    ELSE IF @IN_FEE_JK_TYPE = 2 --另外缴申购费
        SELECT @V_JK_TOTAL_MONEY = @IN_SG_MONEY + @V_RG_FEE_MONEY, @V_SG_MONEY2 = @IN_SG_MONEY
    ELSE
        SELECT @V_RG_FEE_MONEY = 0, @V_RG_FEE_RATE = 0, @V_JK_TOTAL_MONEY = @IN_SG_MONEY, @V_SG_MONEY2 = @IN_SG_MONEY

    IF EXISTS(SELECT 1 FROM INTRUST..TBENIFITOR WHERE PRODUCT_ID = @V_PRODUCT_ID AND CUST_ID = @V_CUST_ID AND PROV_FLAG <> @IN_PROV_FLAG) AND
       EXISTS(SELECT 1 FROM INTRUST..TPRODUCTLIMIT WHERE PRODUCT_ID = @V_PRODUCT_ID AND ASFUND_FLAG = 2)
       RETURN @V_RET_CODE - 11 --收益级别核算单位为：投资人时，同一投资人在一个产品中只能存在一种受益优先级

    BEGIN TRANSACTION

    IF @V_CHECK_FLAG = 1    --未审核
    BEGIN
        --合同号有变时，需要判断新合同编号是否有效，两个表都需要判断
        IF @IN_NEW_CONTRACT_BH <> @V_CONTRACT_BH
        BEGIN
            IF EXISTS(SELECT 1 FROM INTRUST..TCONTRACT WHERE PRODUCT_ID = @V_PRODUCT_ID AND CONTRACT_BH = @IN_NEW_CONTRACT_BH)
            BEGIN
                ROLLBACK TRANSACTION
                RETURN @V_RET_CODE - 5  --合同编号已经存在
            END
            IF EXISTS(SELECT 1 FROM INTRUST..TCONTRACTSG WHERE PRODUCT_ID = @V_PRODUCT_ID AND CONTRACT_BH = @IN_NEW_CONTRACT_BH AND SERIAL_NO <> @IN_SERIAL_NO)
            BEGIN
                ROLLBACK TRANSACTION
                RETURN @V_RET_CODE - 5  --合同编号已经存在
            END
        END
        UPDATE INTRUST..TCONTRACTSG
            SET CONTRACT_BH                 = @IN_NEW_CONTRACT_BH,
                CONTRACT_SUB_BH             = @IN_CONTRACT_SUB_BH,
                SG_MONEY                    = @IN_SG_MONEY,
                SG_PRICE                    = @IN_SG_PRICE,
                JK_TYPE                     = @IN_JK_TYPE,
                JK_TYPE_NAME                = @V_JK_TYPE_NAME,
                BANK_ID                     = @IN_BANK_ID,
                BANK_NAME                   = @V_BANK_NAME,
                BANK_ACCT                   = @IN_BANK_ACCT,
                BANK_SUB_NAME               = @IN_BANK_SUB_NAME,
                GAIN_ACCT                   = @IN_GAIN_ACCT,
                QS_DATE                     = @IN_QS_DATE,
                JK_DATE                     = @IN_JK_DATE,
                START_DATE                  = @IN_START_DATE,
                VALID_PERIOD                = @IN_VALID_PERIOD,
                PERIOD_UNIT                 = @V_PERIOD_UNIT,
                END_DATE                    = @V_END_DATE,
                LINK_MAN                    = @IN_LINK_MAN,
                SERVICE_MAN                 = @IN_SERVICE_MAN,
                SERVICE_MAN_SALES           = @IN_SERVICE_MAN,
                CITY_SERIAL_NO              = @IN_CITY_SERIAL_NO,
                CITY_NAME                   = @V_CITY_NAME,
                TOUCH_TYPE                  = @IN_TOUCH_TYPE,
                TOUCH_TYPE_NAME             = @IN_TOUCH_TYPE_NAME,
                FEE_JK_TYPE                 = @IN_FEE_JK_TYPE,
                SUMMARY                     = @IN_SUMMARY,
                MODI_MAN                    = @IN_INPUT_MAN,
                MODI_TIME                   = GETDATE(),
                BANK_ACCT_TYPE              = @IN_BANK_ACCT_TYPE,
                SG_MONEY2                   = @V_SG_MONEY2,
                JK_TOTAL_MONEY              = @V_JK_TOTAL_MONEY,
                SG_FEE                      = @V_RG_FEE_MONEY,
                SG_FEE_RATE                 = @V_RG_FEE_RATE,
                BONUS_FLAG                  = @IN_BONUS_FLAG,
                WITH_BANK_FLAG              = @IN_WITH_BANK_FLAG,
                HT_BANK_ID                  = @IN_HT_BANK_ID,
                HT_BANK_NAME                = @V_HT_BANK_NAME,
                HT_BANK_SUB_NAME            = @IN_HT_BANK_SUB_NAME,
                WITH_SECURITY_FLAG          = @IN_WITH_SECURITY_FLAG,
                WITH_PRIVATE_FLAG           = @IN_WITH_PRIVATE_FLAG,
                BONUS_RATE                  = @IN_BONUS_RATE,
                PROV_FLAG                   = @IN_PROV_FLAG,
                PROV_LEVEL                  = @IN_PROV_LEVEL,
                PROV_LEVEL_NAME             = @V_PROV_LEVEL_NAME,
                CHANNEL_ID                  = @IN_CHANNEL_ID,
                CHANNEL_TYPE                = @IN_CHANNEL_TYPE,
                CHANNEL_MEMO                = @IN_CHANNEL_MEMO,
                BZJ_FLAG                    = @IN_BZJ_FLAG,
                CHANNEL_COOPERTYPE          = @IN_CHANNEL_COOPERTYPE,
                CHANNEL_COOPERTYPE_NAME     = @V_CHANNEL_COOPERTYPE_NAME,
                MARKET_MONEY                = @IN_MARKET_MONEY,
                SUB_PRODUCT_ID              = @V_SUB_PRODUCT_ID,
                BANK_PROVINCE               = @IN_BANK_PROVINCE,
                BANK_PROVINCE_NAME          = @V_BANK_PROVINCE_NAME,
                BANK_CITY                   = @IN_BANK_CITY,
                BANK_CITY_NAME              = @V_BANK_CITY_NAME,
                RECOMMEND_MAN               = @IN_RECOMMEND_MAN,
                RECOMMEND_MAN_NAME          = @IN_RECOMMEND_MAN_NAME,
                EXPECT_ROR_LOWER            = @IN_EXPECT_ROR_LOWER,
                EXPECT_ROR_UPPER            = @IN_EXPECT_ROR_UPPER,
                CONTACT_ID                  = @IN_CONTACT_ID,	--本合同的客户联系人ID
                HT_CUST_NAME                = @IN_HT_CUST_NAME,   --合同联系人 姓名
				HT_CUST_ADDRESS		        = @IN_HT_CUST_ADDRESS,--合同联系人 地址
				HT_CUST_TEL		            = @IN_HT_CUST_TEL,    --合同联系人 电话
                MONEY_ORIGIN                = @IN_MONEY_ORIGIN,
                MONEY_ORIGIN_NAME           = @V_MONEY_ORIGIN_NAME,
                SUB_MONEY_ORIGIN            = @IN_SUB_MONEY_ORIGIN,
                SUB_MONEY_ORIGIN_NAME       = @V_SUB_MONEY_ORIGIN_NAME,
                SPOT_DEAL                   = @IN_SPOT_DEAL,
                PROPERTY_SOURCE             = @IN_PROPERTY_SOURCE,
                OTHER_EXPLAIN               = @IN_P_S_OTHER_EXPLAIN,
				CONTRACT_TYPE               = @IN_CONTRACT_TYPE
            WHERE SERIAL_NO = @IN_SERIAL_NO
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
        ----添加一条客户银行帐号信息--
        IF NOT EXISTS( SELECT 1 FROM INTRUST..TCUSTBANKACCT WHERE CUST_ID = @V_CUST_ID AND BANK_ID = @IN_BANK_ID AND BANK_ACCT = @IN_BANK_ACCT )
            AND (@IN_BANK_ID <> '') AND (NOT @IN_BANK_ID IS NULL)
            AND (@IN_BANK_ACCT <> '') AND (NOT @IN_BANK_ACCT IS NULL)
            AND (NOT @V_CUST_ID IS NULL)
        BEGIN
            SELECT @V_CARD_TYPE = CARD_TYPE, @V_CARD_ID = CARD_ID  FROM INTRUST..TCUSTOMERINFO WHERE CUST_ID = @V_CUST_ID
            INSERT INTO INTRUST..TCUSTBANKACCT(CUST_ID, BANK_ID, BANK_NAME, BANK_ACCT, CARD_TYPE, CARD_ID,SUB_BANK_NAME,BANK_PROVINCE,BANK_CITY)
                  VALUES(@V_CUST_ID, @IN_BANK_ID, @V_BANK_NAME, @IN_BANK_ACCT, @V_CARD_TYPE, @V_CARD_ID,@IN_BANK_SUB_NAME,@IN_BANK_PROVINCE,@IN_BANK_CITY)
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END
        END
    END
    ELSE --已审核
    BEGIN
        UPDATE INTRUST..TCONTRACTSG
            SET LINK_MAN            = @IN_LINK_MAN,
                SERVICE_MAN         = @IN_SERVICE_MAN,
                TOUCH_TYPE          = @IN_TOUCH_TYPE,
                TOUCH_TYPE_NAME     = @IN_TOUCH_TYPE_NAME,
                SUMMARY             = @IN_SUMMARY,
                MODI_MAN            = @IN_INPUT_MAN,
                MODI_TIME           = GETDATE(),
                BONUS_FLAG          = @IN_BONUS_FLAG,
                BONUS_RATE          = @IN_BONUS_RATE,
                PROV_FLAG           = @IN_PROV_FLAG,
                PROV_LEVEL          = @IN_PROV_LEVEL,
                PROV_LEVEL_NAME     = @V_PROV_LEVEL_NAME,
                CONTRACT_SUB_BH     = @IN_CONTRACT_SUB_BH,
                EXPECT_ROR_LOWER    = @IN_EXPECT_ROR_LOWER,
                EXPECT_ROR_UPPER    = @IN_EXPECT_ROR_UPPER,
                CONTACT_ID          = @IN_CONTACT_ID,	--本合同的客户联系人ID
                HT_CUST_NAME        = @IN_HT_CUST_NAME,   --合同联系人 姓名
				HT_CUST_ADDRESS	    = @IN_HT_CUST_ADDRESS,--合同联系人 地址
				HT_CUST_TEL		    = @IN_HT_CUST_TEL,    --合同联系人 电话
                MONEY_ORIGIN        = @IN_MONEY_ORIGIN,
                MONEY_ORIGIN_NAME   = @V_MONEY_ORIGIN_NAME,
                SUB_MONEY_ORIGIN    = @IN_SUB_MONEY_ORIGIN,
                SUB_MONEY_ORIGIN_NAME = @V_SUB_MONEY_ORIGIN_NAME,
                PROPERTY_SOURCE       = @IN_PROPERTY_SOURCE,
                OTHER_EXPLAIN         = @IN_P_S_OTHER_EXPLAIN,
				CONTRACT_TYPE         = @IN_CONTRACT_TYPE
            WHERE SERIAL_NO = @IN_SERIAL_NO
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    SELECT @SSUMMARY = N'修改申购记录'
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
