USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TCONTRACTSG_SIMP_CRM @IN_CNTR_SERIAL_NO      INTEGER,                       --合同表SERIAL_NO
                                             @IN_SG_MONEY            DECIMAL(16,3),                 --申购金额
                                             @IN_SG_PRICE            DECIMAL(5,4),                  --申购价格（申请时此值无用）
                                             @IN_JK_TYPE             NVARCHAR(10),                  --缴款方式(1114)
                                             @IN_BANK_ID             NVARCHAR(10),                  --信托利益银行编号(1103)
                                             @IN_BANK_ACCT           NVARCHAR(30),                  --信托利益银行账号
                                             @IN_BANK_SUB_NAME       NVARCHAR(60),                  --支行名称
                                             @IN_GAIN_ACCT           NVARCHAR(60),                  --受益银行帐户户名
                                             @IN_QS_DATE             INTEGER,                       --签署日期
                                             @IN_JK_DATE             INTEGER,                       --10.缴款日期
                                             @IN_START_DATE          INTEGER,                       --起始日期
                                             @IN_VALID_PERIOD        INTEGER,                       --期限 20091224 该参数作废
                                             @IN_FEE_JK_TYPE         INTEGER,                       --0无需交，1从本金扣，2另外交
                                             @IN_SUMMARY             NVARCHAR(200),                 --描述
                                             @IN_INPUT_MAN           INTEGER,                       --操作员
                                             @IN_BANK_ACCT_TYPE      NVARCHAR(10)   = NULL,         --银行账户类型(9920)
                                             @IN_BONUS_FLAG          INTEGER        = 1,            --1、兑付　2、转份额
                                             @IN_WITH_BANK_FLAG      INTEGER        = 0,            --是否银信合作 1是 0 否
                                             @IN_HT_BANK_ID          NVARCHAR(10)   = NULL,         --合同银行
                                             @IN_HT_BANK_SUB_NAME    NVARCHAR(60)   = NULL,         --20.合同银行下级支行名称
                                             @IN_WITH_SECURITY_FLAG  INTEGER        = 0,            --是否证信合作
                                             @IN_WITH_PRIVATE_FLAG   INTEGER        = 0,            --是否私募基金合作
                                             @IN_BONUS_RATE          DECIMAL(5,4)   = 0,            --转份额比例
                                             @IN_CHANNEL_ID          INTEGER        = 0,            --销售渠道ID
                                             @IN_CHANNEL_TYPE        NVARCHAR(10)   = NULL,         --交银:渠道类别(5500)
                                             @IN_CHANNEL_MEMO        NVARCHAR(200)  = NULL,         --渠道附加信息
                                             @OUT_SERIAL_NO          INTEGER        = 0    OUTPUT ,
                                             @IN_BZJ_FLAG            INTEGER        = 2,            --是否保证金  1 是 2 否
                                             @IN_CHANNEL_COOPERTYPE  NVARCHAR(10)   = '',           --渠道合作方式(5502)
                                             @IN_MARKET_MONEY        DECIMAL(16,3)  = 0,            --30.渠道费用
                                             @IN_RECOMMEND_MAN       INT            = 0,            --合同推荐人
                                             @IN_BANK_PROVINCE       NVARCHAR(10)   = '',           --受益账户开户行所在省
                                             @IN_BANK_CITY           NVARCHAR(10)   = '',           --受益账户开户行所在市
                                             @IN_EXPECT_ROR_LOWER    DECIMAL(16,10)   = NULL,         --预期收益率区间
                                             @IN_EXPECT_ROR_UPPER    DECIMAL(16,10)   = NULL,         --预期收益率区间
                                             @IN_RECOMMEND_MAN_NAME  NVARCHAR(30)   = '',           --合同推荐人(外部)
                                             @IN_CONTACT_ID          INT            = NULL,         --本合同的客户联系人ID
                                             @IN_PERIOD_UNIT         INT            = NULL,          --合同期限的单位：0无期限 1天 2月 3年
                                             @IN_MONEY_ORIGIN        NVARCHAR(10)   = NULL,          --全要素资金/资产来源
                                             @IN_SUB_MONEY_ORIGIN    NVARCHAR(10)   = NULL,          --40.子产品资金来源
                                             @IN_HT_CUST_NAME        NVARCHAR(200)  = '',           --合同联系人 姓名
								             @IN_HT_CUST_ADDRESS     NVARCHAR(200)  = '',           --合同联系人 地址
								             @IN_HT_CUST_TEL         NVARCHAR(200)  = '',           --合同联系人 电话
								             @IN_SPOT_DEAL           INTEGER        = 2,            --是否现场交易
								             @IN_PROPERTY_SOURCE     NVARCHAR(200)  = '',           --45.信托财产来源 1167
                                             @IN_P_S_OTHER_EXPLAIN   NVARCHAR(1000) = '',           --46.信托财产来源之其他收入来源说明
											 @IN_CONTRACT_TYPE       INT = 0               --1 前台销售人员合同2产品部门合同3代销合同
WITH ENCRYPTION
AS
    SET NOCOUNT ON

    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT, @SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -20497000, @IBUSI_FLAG = 20497
    SELECT @SBUSI_NAME = N'添加申购记录', @SSUMMARY = N'添加申购记录'
    DECLARE @V_CHANNEL_COOPERTYPE_NAME NVARCHAR(30)
    DECLARE @V_STATUS NVARCHAR(10), @V_NUM INT, @V_HT_PRE_CODE NVARCHAR(30),@V_PERIOD_UNIT INT,@V_PUBLISH_CHECK_FLAG INT
    DECLARE @V_PRODUCT_ID INT, @V_CUST_ID INT, @V_CONTRACT_BH NVARCHAR(16), @V_BOOK_CODE INT,@V_PRODUCT_END_DATE INT
    DECLARE @V_PRODUCT_CODE NVARCHAR(6), @V_PRODUCT_NAME NVARCHAR(60), @V_CURRENCY_ID NVARCHAR(2), @V_END_DATE INT,
            @V_JK_TYPE_NAME NVARCHAR(30), @V_BANK_NAME NVARCHAR(30), @V_CITY_NAME NVARCHAR(20), @V_CUST_NAME NVARCHAR(100),
            @V_CARD_TYPE NVARCHAR(10), @V_CARD_ID NVARCHAR(40), @V_CITY_SERIAL_NO INT, @V_TOUCH_TYPE NVARCHAR(40),
            @V_TOUCH_TYPE_NAME NVARCHAR(30), @V_LINK_MAN INT, @V_SERVICE_MAN INT, @V_CONTRACT_SUB_BH NVARCHAR(200),
            @V_SUB_FLAG BIT, @V_SUB_PRODUCT_ID INT
    DECLARE @V_SG_MONEY2 DECIMAL(16,3), @V_RG_FEE_RATE DECIMAL(5,4), @V_RG_FEE_MONEY DECIMAL(16,3), @V_JK_TOTAL_MONEY DECIMAL(16,3), @V_GS_RATE DECIMAL(5,4)
    DECLARE @V_HT_BANK_NAME NVARCHAR(30)
    DECLARE @V_CHECK_MAN INT,@V_MENU_ID NVARCHAR(10),@V_TASK_CODE INT,@V_SYS_DATE INT,@V_LOGIN_NAME NVARCHAR(100),@V_MSG_TITLE NVARCHAR(120),@V_SERIAL_NO INT
    DECLARE @V_PROV_FLAG INT,@V_PROV_LEVEL NVARCHAR(10),@V_PROV_LEVEL_NAME NVARCHAR(30)
    DECLARE @V_BANK_PROVINCE_NAME NVARCHAR(30),@V_BANK_CITY_NAME NVARCHAR(30)
    DECLARE @V_MONEY_ORIGIN_NAME NVARCHAR(30), @V_SUB_MONEY_ORIGIN_NAME NVARCHAR(30)

    BEGIN TRY
    SELECT @V_PERIOD_UNIT = @IN_PERIOD_UNIT    
    SELECT @V_HT_BANK_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_ID = 1103 AND TYPE_VALUE = @IN_HT_BANK_ID
    IF NOT EXISTS(SELECT 1 FROM INTRUST..TCONTRACT WHERE SERIAL_NO = @IN_CNTR_SERIAL_NO)
        RAISERROR('记录不存在！',16,1)
    
    SELECT @V_PRODUCT_ID = PRODUCT_ID, @V_CUST_ID = CUST_ID, @V_CITY_SERIAL_NO = CITY_SERIAL_NO, @V_CONTRACT_BH = CONTRACT_BH,
           @V_BOOK_CODE = BOOK_CODE, @V_TOUCH_TYPE = TOUCH_TYPE, @V_TOUCH_TYPE_NAME = TOUCH_TYPE_NAME,
           @V_LINK_MAN = LINK_MAN, @V_SERVICE_MAN = SERVICE_MAN, @V_CONTRACT_SUB_BH = CONTRACT_SUB_BH,
           @V_PROV_FLAG = PROV_FLAG,@V_PROV_LEVEL = PROV_LEVEL,@V_PROV_LEVEL_NAME = PROV_LEVEL_NAME,@V_SUB_PRODUCT_ID = SUB_PRODUCT_ID,
           @IN_MONEY_ORIGIN = ISNULL(@IN_MONEY_ORIGIN,MONEY_ORIGIN),@IN_SUB_MONEY_ORIGIN = ISNULL(@IN_SUB_MONEY_ORIGIN,SUB_MONEY_ORIGIN)
        FROM INTRUST..TCONTRACT WHERE SERIAL_NO = @IN_CNTR_SERIAL_NO
    SELECT @V_PRODUCT_CODE = PRODUCT_CODE, @V_PRODUCT_NAME = PRODUCT_NAME, @V_CURRENCY_ID = CURRENCY_ID,
           @V_STATUS = PRODUCT_STATUS, @V_HT_PRE_CODE = ISNULL(PRE_CODE,'')/*,@V_PERIOD_UNIT =PERIOD_UNIT*/,
           @V_PRODUCT_END_DATE =END_DATE,@V_PUBLISH_CHECK_FLAG = PUBLISH_CHECK_FLAG,@V_SUB_FLAG = SUB_FLAG--,@IN_VALID_PERIOD = VALID_PERIOD
        FROM INTRUST..TPRODUCT WHERE PRODUCT_ID = @V_PRODUCT_ID
    IF @V_STATUS <> '110203'
        RAISERROR('产品非正常期不能申购！',16,1)
    IF @V_PUBLISH_CHECK_FLAG <> 2
        RAISERROR('产品成立财务未确认不能做申购！',16,1)
    --
    IF ISNULL(@IN_BZJ_FLAG,0) =0
        SET @IN_BZJ_FLAG =2
    --
    SELECT @V_CUST_NAME = CUST_NAME, @V_CARD_TYPE = CARD_TYPE, @V_CARD_ID = CARD_ID FROM INTRUST..TCUSTOMERINFO WHERE CUST_ID = @V_CUST_ID
    IF @IN_QS_DATE IS NULL OR @IN_QS_DATE = 0
        SET @IN_QS_DATE = CONVERT(INT,CONVERT(CHAR(8),GETDATE(),112))
    IF @IN_JK_DATE IS NULL OR @IN_JK_DATE = 0
        SET @IN_JK_DATE = @IN_QS_DATE
	IF @IN_START_DATE IS NULL OR @IN_START_DATE= 0
        SET @IN_START_DATE = @IN_JK_DATE	
    --有子产品,必须选择子产品
    IF @V_SUB_FLAG = 1
    BEGIN
          IF ISNULL(@V_SUB_PRODUCT_ID,0) = 0
              SELECT @V_SUB_PRODUCT_ID = MIN(SUB_PRODUCT_ID) FROM INTRUST..TSUBPRODUCT WHERE PRODUCT_ID = @V_PRODUCT_ID
          ELSE
          BEGIN
              IF NOT EXISTS(SELECT 1 FROM INTRUST..TSUBPRODUCT WHERE PRODUCT_ID = @V_PRODUCT_ID AND SUB_PRODUCT_ID = @V_SUB_PRODUCT_ID)
                  RAISERROR('产品编号不存在！',16,1)
          END
          SELECT @IN_VALID_PERIOD = CASE WHEN ISNULL(@IN_VALID_PERIOD,0)=0 THEN VALID_PERIOD ELSE @IN_VALID_PERIOD END,
                @V_PERIOD_UNIT = CASE WHEN @V_PERIOD_UNIT IS NOT NULL THEN @V_PERIOD_UNIT ELSE PERIOD_UNIT END
            FROM INTRUST..TSUBPRODUCT 
            WHERE PRODUCT_ID = @V_PRODUCT_ID AND SUB_PRODUCT_ID = @V_SUB_PRODUCT_ID
    END
    ELSE
        SELECT @V_SUB_PRODUCT_ID = 0
        
    SELECT @IN_VALID_PERIOD = CASE WHEN ISNULL(@IN_VALID_PERIOD,0)=0 THEN VALID_PERIOD ELSE @IN_VALID_PERIOD END,
           @V_PERIOD_UNIT = CASE WHEN @V_PERIOD_UNIT IS NOT NULL THEN @V_PERIOD_UNIT ELSE PERIOD_UNIT END
      FROM INTRUST..TPRODUCT 
      WHERE PRODUCT_ID = @V_PRODUCT_ID
            
    SELECT @V_END_DATE = INTRUST.dbo.GETDATEADD(@V_PERIOD_UNIT,@IN_VALID_PERIOD,@IN_START_DATE)
    --如果合同结束日期大于产品结束日，则已产品结束日期为限         add by luohh 20091030
    IF @V_END_DATE >@V_PRODUCT_END_DATE
        SET @V_END_DATE = @V_PRODUCT_END_DATE
    --
    IF @IN_START_DATE IS NULL OR @IN_START_DATE = 0
        SELECT @IN_START_DATE = @IN_QS_DATE
    SELECT @V_CHANNEL_COOPERTYPE_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE = @IN_CHANNEL_COOPERTYPE
    SELECT @V_JK_TYPE_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE = @IN_JK_TYPE
    SELECT @V_BANK_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE = @IN_BANK_ID
    SELECT @V_CITY_NAME = CITY_NAME FROM INTRUST..TPRODUCTCITY WHERE SERIAL_NO = @V_CITY_SERIAL_NO
    SELECT @V_BANK_PROVINCE_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE = @IN_BANK_PROVINCE
    SELECT @V_BANK_CITY_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE = @IN_BANK_CITY
    SELECT @V_MONEY_ORIGIN_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_ID = 1158 AND TYPE_VALUE = @IN_MONEY_ORIGIN
    SELECT @V_SUB_MONEY_ORIGIN_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_ID = 1158 AND TYPE_VALUE = @IN_SUB_MONEY_ORIGIN
    EXEC INTRUST..SP_INNER_CAL_PRODUCTFEE @V_PRODUCT_ID,@V_CUST_ID,2,@IN_SG_MONEY,@IN_QS_DATE,@V_RG_FEE_MONEY OUTPUT, @V_GS_RATE OUTPUT, @V_RG_FEE_RATE OUTPUT
    IF @IN_FEE_JK_TYPE = 1 --从本金中扣申购费
        SELECT @V_JK_TOTAL_MONEY = @IN_SG_MONEY, @V_SG_MONEY2 = @IN_SG_MONEY - @V_RG_FEE_MONEY
    ELSE IF @IN_FEE_JK_TYPE = 2 --另外缴申购费
        SELECT @V_JK_TOTAL_MONEY = @IN_SG_MONEY + @V_RG_FEE_MONEY, @V_SG_MONEY2 = @IN_SG_MONEY
    ELSE
        SELECT @V_RG_FEE_MONEY = 0, @V_RG_FEE_RATE = 0, @V_JK_TOTAL_MONEY = @IN_SG_MONEY, @V_SG_MONEY2 = @IN_SG_MONEY
 
    BEGIN TRANSACTION

    INSERT INTO INTRUST..TCONTRACTSG(BOOK_CODE,PRODUCT_ID,PRODUCT_CODE,PRODUCT_NAME,CURRENCY_ID,CUST_ID,
                            CONTRACT_BH,CONTRACT_SUB_BH,TOUCH_TYPE,TOUCH_TYPE_NAME,VALID_PERIOD,PERIOD_UNIT,QS_DATE,START_DATE,END_DATE,
                            SG_MONEY,SG_PRICE,JK_TYPE,JK_TYPE_NAME,JK_DATE,BANK_ID,BANK_NAME,BANK_SUB_NAME,
                            BANK_ACCT,GAIN_ACCT,INPUT_MAN,LINK_MAN,SERVICE_MAN,SERVICE_MAN_SALES,SUMMARY,CITY_SERIAL_NO,
                            CITY_NAME,FEE_JK_TYPE,BANK_ACCT_TYPE,
                            SG_MONEY2, JK_TOTAL_MONEY, SG_FEE_RATE, SG_FEE,BONUS_FLAG,SUB_PRODUCT_ID,
                            WITH_BANK_FLAG,HT_BANK_ID,HT_BANK_NAME,HT_BANK_SUB_NAME,
                            WITH_SECURITY_FLAG,WITH_PRIVATE_FLAG,BONUS_RATE,PROV_FLAG,PROV_LEVEL,PROV_LEVEL_NAME,
                            CHANNEL_ID,CHANNEL_TYPE,CHANNEL_MEMO,BZJ_FLAG,FIRST_FLAG,
                            CHANNEL_COOPERTYPE,CHANNEL_COOPERTYPE_NAME,MARKET_MONEY,
                            BANK_PROVINCE,BANK_PROVINCE_NAME,BANK_CITY,BANK_CITY_NAME,RECOMMEND_MAN,EXPECT_ROR_LOWER,
                            EXPECT_ROR_UPPER,RECOMMEND_MAN_NAME,CONTACT_ID,
                            MONEY_ORIGIN,MONEY_ORIGIN_NAME,SUB_MONEY_ORIGIN,SUB_MONEY_ORIGIN_NAME,
                            HT_CUST_NAME,HT_CUST_ADDRESS,HT_CUST_TEL,SPOT_DEAL,PROPERTY_SOURCE,OTHER_EXPLAIN,CONTRACT_TYPE)
        VALUES(@V_BOOK_CODE,@V_PRODUCT_ID,@V_PRODUCT_CODE,@V_PRODUCT_NAME,@V_CURRENCY_ID,@V_CUST_ID,
               @V_CONTRACT_BH,@V_CONTRACT_SUB_BH,@V_TOUCH_TYPE,@V_TOUCH_TYPE_NAME,@IN_VALID_PERIOD,@V_PERIOD_UNIT,@IN_QS_DATE,@IN_START_DATE,@V_END_DATE,
               @IN_SG_MONEY,@IN_SG_PRICE,@IN_JK_TYPE,@V_JK_TYPE_NAME,@IN_JK_DATE,@IN_BANK_ID,@V_BANK_NAME,@IN_BANK_SUB_NAME,
               @IN_BANK_ACCT,@IN_GAIN_ACCT,@IN_INPUT_MAN,@V_LINK_MAN,@V_SERVICE_MAN,@V_SERVICE_MAN,@IN_SUMMARY,@V_CITY_SERIAL_NO,
               @V_CITY_NAME,@IN_FEE_JK_TYPE,@IN_BANK_ACCT_TYPE,
               @V_SG_MONEY2, @V_JK_TOTAL_MONEY, @V_RG_FEE_RATE, @V_RG_FEE_MONEY,@IN_BONUS_FLAG,@V_SUB_PRODUCT_ID,
               @IN_WITH_BANK_FLAG,@IN_HT_BANK_ID,@V_HT_BANK_NAME,@IN_HT_BANK_SUB_NAME,
               @IN_WITH_SECURITY_FLAG,@IN_WITH_PRIVATE_FLAG,@IN_BONUS_RATE,@V_PROV_FLAG,@V_PROV_LEVEL,@V_PROV_LEVEL_NAME,
               @IN_CHANNEL_ID,@IN_CHANNEL_TYPE,@IN_CHANNEL_MEMO,@IN_BZJ_FLAG,2,
               @IN_CHANNEL_COOPERTYPE,@V_CHANNEL_COOPERTYPE_NAME,@IN_MARKET_MONEY,
               @IN_BANK_PROVINCE,@V_BANK_PROVINCE_NAME,@IN_BANK_CITY,@V_BANK_CITY_NAME,@IN_RECOMMEND_MAN,@IN_EXPECT_ROR_LOWER,
               @IN_EXPECT_ROR_UPPER,@IN_RECOMMEND_MAN_NAME,@IN_CONTACT_ID,
               @IN_MONEY_ORIGIN,@V_MONEY_ORIGIN_NAME,@IN_SUB_MONEY_ORIGIN,@V_SUB_MONEY_ORIGIN_NAME,
               @IN_HT_CUST_NAME,@IN_HT_CUST_ADDRESS,@IN_HT_CUST_TEL,@IN_SPOT_DEAL,@IN_PROPERTY_SOURCE,@IN_P_S_OTHER_EXPLAIN,@IN_CONTRACT_TYPE)
    SELECT @OUT_SERIAL_NO = @@IDENTITY
    
    ----添加一条客户银行帐号信息--
    SELECT @IN_BANK_ID = ISNULL(@IN_BANK_ID,'')
    SELECT @IN_BANK_ACCT = ISNULL(@IN_BANK_ACCT,'')
    IF @IN_BANK_ID<>'' AND @IN_BANK_ACCT<>'' AND NOT EXISTS( SELECT * FROM INTRUST..TCUSTBANKACCT
            WHERE CUST_ID = @V_CUST_ID AND BANK_ID = @IN_BANK_ID AND BANK_ACCT = @IN_BANK_ACCT )
    BEGIN
        INSERT INTO INTRUST..TCUSTBANKACCT(CUST_ID, BANK_ID, BANK_NAME, BANK_ACCT, CARD_TYPE, CARD_ID,SUB_BANK_NAME,BANK_PROVINCE,BANK_CITY)
              VALUES(@V_CUST_ID, @IN_BANK_ID, @V_BANK_NAME, @IN_BANK_ACCT, @V_CARD_TYPE, @V_CARD_ID,@IN_BANK_SUB_NAME,@IN_BANK_PROVINCE,@IN_BANK_CITY)
    END

    --工作提示
    --生成申购合同已录入提示
    SET @V_MENU_ID = '2130107'
    SELECT @V_TASK_CODE = TASK_CODE,@V_MSG_TITLE = TASK_INFO FROM INTRUST..TTASKDICT WHERE TASK_CODE = 401
    SET @V_LOGIN_NAME = @V_MSG_TITLE + N'产品编号：' + @V_PRODUCT_CODE + N',产品名称: ' + @V_PRODUCT_NAME
                                      + N',合同编号：' + @V_CONTRACT_BH + N',实际合同编号: ' + @V_CONTRACT_SUB_BH
                                      + N',申购人：' + @V_CUST_NAME + N',申购金额：' + RTRIM(CONVERT(CHAR,@IN_SG_MONEY))
    SELECT @V_SYS_DATE = CONVERT(INT,CONVERT(CHAR(8),GETDATE(),112))

    EXEC @V_RET_CODE = INTRUST..SP_INNER_MAKETASKINFO @V_TASK_CODE,@V_MSG_TITLE,@V_LOGIN_NAME,@V_SYS_DATE,@V_PRODUCT_ID,
                                             @IN_INPUT_MAN,@V_MENU_ID,@IN_INPUT_MAN,@OUT_SERIAL_NO,0
    
    --建信-发生信证合作
    IF EXISTS(SELECT 1 FROM INTRUST..TSYSTEMINFO WHERE USER_ID = 15) BEGIN
        IF @IN_WITH_SECURITY_FLAG = 1 BEGIN
            SELECT @V_TASK_CODE = TASK_CODE,@V_MSG_TITLE = TASK_INFO FROM INTRUST..TTASKDICT WHERE TASK_CODE = 903
            SET @V_LOGIN_NAME = ISNULL(@V_MSG_TITLE,'') + N'产品：' + @V_PRODUCT_CODE + @V_PRODUCT_NAME 
                                                        + N',申购合同编号: ' + @V_CONTRACT_SUB_BH
            EXEC @V_RET_CODE = INTRUST..SP_INNER_MAKETASKINFO @V_TASK_CODE,@V_MSG_TITLE,@V_LOGIN_NAME,@V_SYS_DATE,@V_PRODUCT_ID,
                                       @IN_INPUT_MAN,'',@IN_INPUT_MAN,@V_PRODUCT_ID,0
            IF @V_RET_CODE < 0 OR @@ERROR <> 0 RAISERROR('RAISE ERROR IN SP_INNER_MAKETASKINFO',16,97)
        END
    END


    SELECT @SSUMMARY = N'添加申购记录, 产品编号: ' + @V_PRODUCT_CODE + N', 合同序号: ' + @V_CONTRACT_BH + N', 合同编号: ' + @V_CONTRACT_SUB_BH
    INSERT INTO INTRUST..TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
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
