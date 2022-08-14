﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE dbo.SP_ADD_TCONTRACT_CRM @IN_PRODUCT_ID            INT,
                                      @IN_PRE_CODE              NVARCHAR(16),
                                      @IN_RG_MONEY              DECIMAL(16,3),
                                      @IN_JK_TYPE               NVARCHAR(10),
                                      @IN_BANK_ID               NVARCHAR(10),
                                      @IN_BANK_ACCT             NVARCHAR(30),
                                      @IN_VALID_PERIOD          INT,                       --7. 合同期限
                                      @IN_SERVICE_MAN           INT,
                                      @IN_SUMMARY               NVARCHAR(200),
                                      @IN_INPUT_MAN             INT,                       --10.操作员
                                      @IN_CHECK_MAN             INT,
                                      @IN_CONTRACT_BH           NVARCHAR(16),
                                      @IN_QS_DATE               INT,
                                      @IN_JK_DATE               INT,
                                      @IN_BANK_SUB_NAME         NVARCHAR(60),
                                      @IN_CITY_SERIAL_NO        INT,                       --推介地记录序号
                                      @OUT_SERIAL_NO            INT OUTPUT,
                                      @OUT_CONTRACT_BH          NVARCHAR(16) OUTPUT,
                                      @IN_ZY_FLAG               INT = 1,
                                      @IN_TOUCH_TYPE            NVARCHAR(40) = NULL,       --20.
                                      @IN_TOUCH_TYPE_NAME       NVARCHAR(30) = NULL,
                                      @IN_GAIN_ACCT             NVARCHAR(60) = NULL,
                                      @IN_FEE_JK_TYPE           INT = 0,                   --费用缴款方式：1从本金扣，2另外交,0不交
                                      @IN_CONTRACT_SUB_BH       NVARCHAR(80) = '',
                                      @IN_BANK_ACCT_TYPE        NVARCHAR(10) = NULL,       --银行账户类型(9920)
                                      @IN_BONUS_FLAG            INT = 1,                   --1、兑付　2、转份额
                                      @IN_SUB_PRODUCT_ID        INT = 0,                   --子产品ID ADD BY JINXR 2009/11/29
                                      @IN_WITH_BANK_FLAG        INT = 0,                   --是否银信合作 1是 0 否 add by liug 20101123
                                      @IN_HT_BANK_ID            NVARCHAR(10) = NULL,       --合同银行 add by liug 20101208
                                      @IN_HT_BANK_SUB_NAME      NVARCHAR(60) = NULL,       --30.合同银行下级支行名称 add by liug 20101208
                                      @IN_CHANNEL_ID            INT = 0,                   --销售渠道ID
                                      @IN_WITH_SECURITY_FLAG    INT = 0,                   --是否证信合作
                                      @IN_WITH_PRIVATE_FLAG     INT = 0,                   --是否私募基金合作
                                      @IN_BONUS_RATE            DECIMAL(5,4) = 0,          --转份额比例
                                      @IN_PROV_FLAG             INT = 1,                   --1.优先，2一般，3劣后
                                      @IN_PROV_LEVEL            NVARCHAR(10) = '120401',   --收益级别（1204）
                                      @IN_CHANNEL_TYPE          NVARCHAR(10) = NULL,       --交银:渠道类别(5500)
                                      @IN_CHANNEL_MEMO          NVARCHAR(200) = NULL,      --渠道附加信息
                                      @IN_CHANNEL_COOPERTYPE    NVARCHAR(10) = NULL,       --渠道合作方式(5502)
                                      @IN_BZJ_FLAG              INT = 2,                   --40.是否保证金  1 是 2 否  20120215  LUOHH
                                      @IN_MARKET_MONEY          DECIMAL(16,3)  = 0,        --渠道费用
                                      @IN_RECOMMEND_MAN         INT            = 0,        --合同推荐人(内部)
                                      @IN_BANK_PROVINCE         NVARCHAR(10)   = '',       --受益账户开户行所在省
                                      @IN_BANK_CITY             NVARCHAR(10)   = '',       --受益账户开户行所在市
                                      @IN_EXPECT_ROR_LOWER      DECIMAL(16,10)   = NULL,     --预期收益率区间
                                      @IN_EXPECT_ROR_UPPER      DECIMAL(16,10)   = NULL,     --预期收益率区间
                                      @IN_RECOMMEND_MAN_NAME    NVARCHAR(30)   = '',        --合同推荐人(外部)
                                      @IN_IS_YKGL				VARCHAR(1)	   = '0',		--用款方关联标志：1是
                                      @IN_XTHTYJSYL				NVARCHAR(1000) = '' ,       --信托合同预计收益率（文本对象）
                                      @IN_CONTACT_ID			INT			   = NULL,  	--50.本合同的联系人ID
                                      @IN_PERIOD_UNIT           INT            = NULL,      --合同期限的单位：0无期限 1天 2月 3年
                                      @IN_HT_CUST_NAME          NVARCHAR(200)   = '',           --合同联系人 姓名
								      @IN_HT_CUST_ADDRESS       NVARCHAR(200)   = '',           --合同联系人 地址
								      @IN_HT_CUST_TEL           NVARCHAR(200)   = '',           --合同联系人 电话
								      @IN_SPOT_DEAL             INTEGER         = 2,         --是否现场交易
								      @IN_MONEY_ORIGIN          NVARCHAR(10)    = NULL,      --56.资金/资产来源(全要素)
								      @IN_SUB_MONEY_ORIGIN      NVARCHAR(10)    = NULL,      --57.资金/资产来源二级分类(全要素)
									  @IN_CONTRACT_TYPE         INT             = 2          --1 前台销售人员合同2产品部门合同3代销合同
										
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_BOOK_CODE INT,@V_PRODUCT_CODE NVARCHAR(6),@V_PRODUCT_NAME NVARCHAR(60),@V_CURRENCY_ID NVARCHAR(2)
    DECLARE @V_CUST_ID INT,@V_LINK_MAN INT,@V_JK_TYPE_NAME NVARCHAR(30),@V_BANK_NAME NVARCHAR(30),@V_SUB_FLAG BIT
    DECLARE @V_START_DATE INT,@V_END_DATE INT,@V_PRE_STATUS NVARCHAR(10),@V_SERVICE_MAN INT, @V_PERIOD_UNIT INT
    DECLARE @V_PRE_NUM INT,@V_FACT_NUM INT,@V_NUM INT,@V_PRE_NUM1 INT,@V_RG_NUM1 INT, @V_CUST_NAME NVARCHAR(60)
    DECLARE @V_FACT_MONEY DECIMAL(16,3),@V_PRE_MONEY DECIMAL(16,3),@V_RG_MONEY DECIMAL(16,3), @V_CITY_NAME NVARCHAR(20)
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200),@V_OPEN_FLAG INT
    DECLARE @V_CARD_TYPE NVARCHAR(10), @V_CARD_ID NVARCHAR(40),@V_HT_PRE_CODE NVARCHAR(30), @V_INTRUST_TYPE1 NVARCHAR(10)
    DECLARE @V_RG_MONEY2 DECIMAL(16,3), @V_RG_FEE_RATE DECIMAL(5,4), @V_RG_FEE_MONEY DECIMAL(16,3), @V_JK_TOTAL_MONEY DECIMAL(16,3), @V_GS_RATE DECIMAL(5,4)
    DECLARE @V_CHECK_MAN INT,@V_INTRUST_FLAG1 INT,@V_MENU_ID NVARCHAR(10)
    DECLARE @V_SYS_DATE INT,@V_SERIAL_NO INT,@V_OP_NAME NVARCHAR(10),@V_LOGIN_NAME NVARCHAR(200), @V_BEN_SERIAL_NO INT, @V_LIST_ID INT
    DECLARE @V_CURRENT_NUM INT,@V_CUST_TYPE INT,@V_HT_BANK_NAME NVARCHAR(30)
    DECLARE @V_OBJECT_FLAG INT,@V_TASK_CODE INT,@V_PROV_LEVEL_NAME NVARCHAR(20)
    DECLARE @V_QUALIFIED_FLAG INT,@V_QUALIFIED_NUM INT,@V_QUALIFIED_MONEY DECIMAL(16,2), @V_CHANNEL_COOPERTYPE_NAME NVARCHAR(30)
    DECLARE @V_BANK_PROVINCE_NAME NVARCHAR(30),@V_BANK_CITY_NAME NVARCHAR(30)
    DECLARE @V_IS_DEAL INT,@V_CUST_STATUS NVARCHAR(40),@V_MONEY_ORIGIN_NAME NVARCHAR(30),@V_SUB_MONEY_ORIGIN_NAME NVARCHAR(30)

    SELECT @V_RET_CODE = -20801000
    SELECT @IBUSI_FLAG = 20801
    SELECT @SBUSI_NAME = N'客户认购'
    SELECT @SSUMMARY = N'客户认购'
    
    BEGIN TRY
    SELECT @V_PERIOD_UNIT = @IN_PERIOD_UNIT
    SELECT @V_HT_BANK_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_ID = 1103 AND TYPE_VALUE = @IN_HT_BANK_ID
    SELECT @V_CHANNEL_COOPERTYPE_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_ID = 5502 AND TYPE_VALUE = @IN_CHANNEL_COOPERTYPE
    SELECT @OUT_SERIAL_NO = 0
    IF @IN_QS_DATE IS NULL OR @IN_QS_DATE = 0
       SELECT @IN_QS_DATE = CONVERT(INT,CONVERT(CHAR(8),GETDATE(),112))
    IF @IN_JK_DATE IS NULL OR @IN_JK_DATE = 0
        SELECT @IN_JK_DATE = @IN_QS_DATE
    IF NOT EXISTS(SELECT 1 FROM INTRUST..TPRODUCT WHERE PRODUCT_ID = @IN_PRODUCT_ID)
        RAISERROR('产品编号不存在！',16,1)
    IF NOT EXISTS(SELECT 1 FROM INTRUST..TPRECONTRACT WHERE PRODUCT_ID = @IN_PRODUCT_ID AND PRE_CODE = @IN_PRE_CODE )
        RAISERROR('预约号不存在！',16,1)
    SELECT @V_INTRUST_TYPE1 = INTRUST_TYPE1/*, @V_PERIOD_UNIT = PERIOD_UNIT*/,@V_OPEN_FLAG = OPEN_FLAG,
           @V_SUB_FLAG = SUB_FLAG--,@IN_VALID_PERIOD = VALID_PERIOD
    FROM INTRUST..TPRODUCT WHERE PRODUCT_ID = @IN_PRODUCT_ID
    SELECT @V_MONEY_ORIGIN_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_ID = 1158 AND TYPE_VALUE = @IN_MONEY_ORIGIN
	SELECT @V_SUB_MONEY_ORIGIN_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_ID = 1158 AND TYPE_VALUE = @IN_SUB_MONEY_ORIGIN
    
    IF ISNULL(@IN_BZJ_FLAG,0) =0
        SET @IN_BZJ_FLAG =2
        
    --有子产品,必须选择子产品
    --合同期限界面不再输入,按照产品或者子产品来选择.
    IF @V_SUB_FLAG = 1
    BEGIN
        IF ISNULL(@IN_SUB_PRODUCT_ID,0) = 0
            SELECT @IN_SUB_PRODUCT_ID = MIN(SUB_PRODUCT_ID) FROM INTRUST..TSUBPRODUCT WHERE PRODUCT_ID = @IN_PRODUCT_ID
        ELSE
        BEGIN
            IF NOT EXISTS(SELECT 1 FROM INTRUST..TSUBPRODUCT WHERE PRODUCT_ID = @IN_PRODUCT_ID AND SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID)
                RAISERROR('产品编号不存在！',16,1)
        END
        SELECT @IN_VALID_PERIOD = CASE WHEN ISNULL(@IN_VALID_PERIOD,0)=0 THEN VALID_PERIOD ELSE @IN_VALID_PERIOD END,
                @V_PERIOD_UNIT = CASE WHEN @V_PERIOD_UNIT IS NOT NULL THEN @V_PERIOD_UNIT ELSE PERIOD_UNIT END
          FROM INTRUST..TSUBPRODUCT 
          WHERE PRODUCT_ID = @IN_PRODUCT_ID AND SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID
    END
    ELSE
        SELECT @IN_SUB_PRODUCT_ID = 0
        
    SELECT @IN_VALID_PERIOD = CASE WHEN ISNULL(@IN_VALID_PERIOD,0)=0 THEN VALID_PERIOD ELSE @IN_VALID_PERIOD END,
          @V_PERIOD_UNIT = CASE WHEN @V_PERIOD_UNIT IS NOT NULL THEN @V_PERIOD_UNIT ELSE PERIOD_UNIT END
      FROM INTRUST..TPRODUCT 
      WHERE PRODUCT_ID = @IN_PRODUCT_ID
      
    SELECT @V_START_DATE = @IN_JK_DATE
    IF ISNULL(@V_PERIOD_UNIT,0) = 0
        SELECT @V_END_DATE = 21000101
    ELSE
        SELECT @V_END_DATE  = INTRUST.dbo.GETDATEADD(@V_PERIOD_UNIT,@IN_VALID_PERIOD,@V_START_DATE)
    SELECT @V_CUST_ID = CUST_ID,@V_PRE_STATUS = PRE_STATUS,@V_LINK_MAN = LINK_MAN,@V_PRE_MONEY = PRE_MONEY,
           @V_PRE_NUM1 = PRE_NUM
        FROM INTRUST..TPRECONTRACT WHERE PRODUCT_ID = @IN_PRODUCT_ID AND PRE_CODE = @IN_PRE_CODE

    IF(@V_INTRUST_TYPE1 = '113801')
    BEGIN
        IF @IN_RG_MONEY IS NULL OR @IN_RG_MONEY <=0
            RAISERROR('认购金额应该大于0！',16,1)
        IF @IN_RG_MONEY - ROUND(@IN_RG_MONEY,0) > 0
            RAISERROR('认购金额不能是小数！',16,1)
        SELECT @V_MENU_ID = '2130103'
    END
    ELSE
        SELECT @V_MENU_ID = '2130202'

    SELECT @V_RG_NUM1 = 0
    SELECT @V_RG_MONEY = SUM(ISNULL(RG_MONEY,0)),@V_RG_NUM1 = COUNT(*) FROM INTRUST..TCONTRACT
        WHERE PRODUCT_ID = @IN_PRODUCT_ID AND CUST_ID = @V_CUST_ID AND PRE_CODE = @IN_PRE_CODE
    SELECT @V_RG_MONEY = ISNULL(@V_RG_MONEY,0) + @IN_RG_MONEY
    SELECT @V_RG_NUM1 = ISNULL(@V_RG_NUM1,0) + 1
    IF @V_RG_MONEY > @V_PRE_MONEY
        RAISERROR('认购金额不能超过预约金额！',16,1)
    
    SELECT @V_BANK_PROVINCE_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE = @IN_BANK_PROVINCE
    SELECT @V_BANK_CITY_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE = @IN_BANK_CITY
    SELECT @V_JK_TYPE_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE = @IN_JK_TYPE
    SELECT @V_BANK_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE = @IN_BANK_ID
    IF ISNULL(@IN_PROV_LEVEL,'') = ''
        SET @IN_PROV_LEVEL = '120401'
    SELECT @V_PROV_LEVEL_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE = @IN_PROV_LEVEL

    SELECT @V_BOOK_CODE = BOOK_CODE,@V_PRODUCT_CODE = PRODUCT_CODE,@V_PRODUCT_NAME = PRODUCT_NAME,
           @V_CURRENCY_ID = CURRENCY_ID,@V_PRE_NUM = PRE_NUM,@V_FACT_NUM = ISNULL(FACT_NUM,0),
           @V_PRE_MONEY = PRE_MONEY,@V_HT_PRE_CODE = ISNULL(PRE_CODE,''),@V_CHECK_MAN = ISNULL(CHECK_MAN,INPUT_MAN),
           @V_INTRUST_FLAG1 =INTRUST_FLAG1,@V_INTRUST_TYPE1 =INTRUST_TYPE1
    FROM INTRUST..TPRODUCT WHERE PRODUCT_ID = @IN_PRODUCT_ID
    --如果是单一产品,指定的审核人和录入人是同一个人,审核人选产品的审核人
    IF @V_INTRUST_FLAG1 = 1
    BEGIN
        IF ISNULL(@IN_CHECK_MAN,0) = 0 SELECT @IN_CHECK_MAN = @IN_INPUT_MAN
        IF @IN_INPUT_MAN = @IN_CHECK_MAN
            SELECT @IN_CHECK_MAN = @V_CHECK_MAN
    END
    --
    SELECT @V_CUST_NAME = CUST_NAME,@V_SERVICE_MAN = SERVICE_MAN,@V_CARD_TYPE = CARD_TYPE, @V_CARD_ID = CARD_ID,@V_CUST_TYPE = CUST_TYPE,@V_IS_DEAL=IS_DEAL
			,@V_CUST_STATUS=STATUS
		FROM TCustomers WHERE CUST_ID = @V_CUST_ID
	IF @V_CUST_STATUS='112806'/*禁购*/
		RAISERROR(N'禁购客户！不能认购',16,3)

    SELECT @V_QUALIFIED_FLAG = QUALIFIED_FLAG,@V_QUALIFIED_NUM = QUALIFIED_NUM,@V_QUALIFIED_MONEY = QUALIFIED_MONEY
        FROM INTRUST..TPRODUCTLIMIT WHERE BOOK_CODE = @V_BOOK_CODE AND PRODUCT_ID = @IN_PRODUCT_ID
    
    IF EXISTS(SELECT 1 FROM INTRUST..TBENIFITOR WHERE PRODUCT_ID = @IN_PRODUCT_ID AND CUST_ID = @V_CUST_ID AND PROV_FLAG <> @IN_PROV_FLAG) AND
       EXISTS(SELECT 1 FROM INTRUST..TPRODUCTLIMIT WHERE PRODUCT_ID = @IN_PRODUCT_ID AND ASFUND_FLAG = 2)
       RAISERROR('收益级别核算单位为：投资人时，同一投资人在一个产品中只能存在一种受益优先级！',16,1)
    --
    SELECT @V_FACT_NUM=COUNT(*),@V_FACT_MONEY=SUM(RG_MONEY) FROM INTRUST..TCONTRACT
        WHERE PRODUCT_ID = @IN_PRODUCT_ID AND HT_STATUS = '120101'
    IF @V_FACT_NUM IS NULL
        SELECT @V_FACT_NUM = 0
/*    IF @V_OPEN_FLAG <> 1 AND @V_FACT_NUM >= @V_PRE_NUM
        RAISERROR('认购份额已满不能认购！',16,1)
    SELECT @V_FACT_MONEY  = ISNULL(@V_FACT_MONEY,0) + @IN_RG_MONEY
    IF @V_OPEN_FLAG <> 1 AND @V_FACT_MONEY > @V_PRE_MONEY
        RAISERROR('认购金额已满不能认购！',16,1)
*/    -----------合同编号处理----------------------------------------
    IF @IN_CONTRACT_BH IS NULL OR @IN_CONTRACT_BH = ''
    BEGIN
        SELECT @V_NUM = COUNT(*) FROM INTRUST..TCONTRACT WHERE PRODUCT_ID = @IN_PRODUCT_ID
        SELECT @V_NUM = ISNULL(@V_NUM,0) + 1
        IF @V_NUM < 10
            SELECT @OUT_CONTRACT_BH = '00' + RTRIM(CONVERT(CHAR(10),@V_NUM))
        ELSE IF (@V_NUM < 100) AND (@V_NUM >= 10)
            SELECT @OUT_CONTRACT_BH = '0' + RTRIM(CONVERT(CHAR(10),@V_NUM))
        ELSE
            SELECT @OUT_CONTRACT_BH = RTRIM(CONVERT(CHAR(10),@V_NUM))
        WHILE EXISTS(SELECT 1 FROM INTRUST..TCONTRACT WHERE PRODUCT_ID = @IN_PRODUCT_ID AND CONTRACT_BH = SUBSTRING((RTRIM(@V_HT_PRE_CODE)+@OUT_CONTRACT_BH),1,16))
        BEGIN
            SELECT @V_NUM = @V_NUM + 1
            IF @V_NUM < 10
                SELECT @OUT_CONTRACT_BH = '00' + RTRIM(CONVERT(CHAR(10),@V_NUM))
            ELSE IF (@V_NUM < 100) AND (@V_NUM >= 10)
                SELECT @OUT_CONTRACT_BH = '0' + RTRIM(CONVERT(CHAR(10),@V_NUM))
            ELSE
                SELECT @OUT_CONTRACT_BH = RTRIM(CONVERT(CHAR(10),@V_NUM))
        END
    END
    ELSE
    BEGIN
        WHILE LEN(@IN_CONTRACT_BH)<3
            SELECT @IN_CONTRACT_BH = '0' + @IN_CONTRACT_BH
        SELECT @OUT_CONTRACT_BH = @IN_CONTRACT_BH
    END
    --MODI BY JINXR 2007/12/26
    IF RTRIM(ISNULL(@IN_CONTRACT_SUB_BH,'')) = '' SELECT @IN_CONTRACT_SUB_BH = @OUT_CONTRACT_BH

    --合同编号前缀的处理----------------
    SELECT @V_HT_PRE_CODE = LTRIM(RTRIM(@V_HT_PRE_CODE))
    IF RIGHT(@V_HT_PRE_CODE,1)=N'号'
        SELECT @V_HT_PRE_CODE = LEFT(@V_HT_PRE_CODE, LEN(@V_HT_PRE_CODE)-1)
    
    SELECT @IN_CONTRACT_SUB_BH = SUBSTRING(@V_HT_PRE_CODE+@IN_CONTRACT_SUB_BH, 1, 80)

    IF (SELECT USER_ID FROM INTRUST..TSYSTEMINFO)=25/*25 重庆信托 */ AND RIGHT(@IN_CONTRACT_SUB_BH,1)<>N'号'
        SELECT @IN_CONTRACT_SUB_BH = @IN_CONTRACT_SUB_BH + N'号'
    ------------------------------------------
    IF EXISTS(SELECT 1 FROM INTRUST..TCONTRACT WHERE PRODUCT_ID = @IN_PRODUCT_ID AND CONTRACT_BH = @OUT_CONTRACT_BH)
        RAISERROR('合同编号已存在！',16,1)
    SELECT @V_CITY_NAME = CITY_NAME FROM INTRUST..TPRODUCTCITY WHERE SERIAL_NO = @IN_CITY_SERIAL_NO
    IF @IN_GAIN_ACCT IS NULL OR @IN_GAIN_ACCT = '' SELECT @IN_GAIN_ACCT = @V_CUST_NAME

    EXEC INTRUST..SP_INNER_CAL_PRODUCTFEE @IN_PRODUCT_ID,@V_CUST_ID,1,@IN_RG_MONEY,@IN_QS_DATE,@V_RG_FEE_MONEY OUTPUT, @V_GS_RATE OUTPUT, @V_RG_FEE_RATE OUTPUT
    IF @IN_FEE_JK_TYPE = 1 --从本金中扣认购费
        SELECT @V_JK_TOTAL_MONEY = @IN_RG_MONEY, @V_RG_MONEY2 = @IN_RG_MONEY - @V_RG_FEE_MONEY
    ELSE IF @IN_FEE_JK_TYPE = 2 --另外缴认购费
        SELECT @V_JK_TOTAL_MONEY = @IN_RG_MONEY + @V_RG_FEE_MONEY, @V_RG_MONEY2 = @IN_RG_MONEY
    ELSE
        SELECT @V_RG_FEE_MONEY = 0, @V_RG_FEE_RATE = 0, @V_JK_TOTAL_MONEY = @IN_RG_MONEY, @V_RG_MONEY2 = @IN_RG_MONEY
    
    --1.10.单一产品分期，控制唯一委托人
    IF @V_INTRUST_FLAG1 = 1 AND 0 < (SELECT COUNT(*) FROM INTRUST..TCONTRACT WHERE PRODUCT_ID = @IN_PRODUCT_ID AND CUST_ID <> @V_CUST_ID) BEGIN
        RAISERROR('单一产品只能有一个委托人！',16,1)
    END
    
    BEGIN TRANSACTION

    INSERT INTO INTRUST..TCONTRACT(BOOK_CODE,PRODUCT_ID,PRODUCT_CODE,PRODUCT_NAME,CURRENCY_ID,CUST_ID,
                          PRE_CODE,CONTRACT_BH,LINK_MAN,VALID_PERIOD,PERIOD_UNIT,QS_DATE,START_DATE,END_DATE,
                          RG_MONEY,JK_DATE,JK_TYPE,JK_TYPE_NAME,BANK_ID,BANK_NAME,BANK_ACCT,GAIN_ACCT,
                          INPUT_MAN,SERVICE_MAN,SERVICE_MAN_SALES,SUMMARY,CHECK_MAN, BANK_SUB_NAME, CITY_SERIAL_NO, CITY_NAME,ZY_FLAG,
                          TOUCH_TYPE,TOUCH_TYPE_NAME,FEE_JK_TYPE,CONTRACT_SUB_BH, BANK_ACCT_TYPE,
                          RG_MONEY2,RG_FEE_RATE,RG_FEE_MONEY,JK_TOTAL_MONEY,BONUS_FLAG,SUB_PRODUCT_ID,WITH_BANK_FLAG,
                          HT_BANK_ID,HT_BANK_NAME,HT_BANK_SUB_NAME,CHANNEL_ID,CHANNEL_TYPE,CHANNEL_MEMO,CHANNEL_COOPERTYPE,CHANNEL_COOPERTYPE_NAME,
                          WITH_SECURITY_FLAG,WITH_PRIVATE_FLAG,BONUS_RATE,PROV_FLAG,PROV_LEVEL,PROV_LEVEL_NAME,BZJ_FLAG,MARKET_MONEY,
                          BANK_PROVINCE,BANK_PROVINCE_NAME,BANK_CITY,BANK_CITY_NAME,RECOMMEND_MAN,EXPECT_ROR_LOWER,EXPECT_ROR_UPPER,RECOMMEND_MAN_NAME,
                          IS_YKGL,XTHTYJSYL,CONTACT_ID,HT_CUST_NAME,HT_CUST_ADDRESS,HT_CUST_TEL,SPOT_DEAL,
                          MONEY_ORIGIN,MONEY_ORIGIN_NAME,SUB_MONEY_ORIGIN,SUB_MONEY_ORIGIN_NAME,CONTRACT_TYPE)
        VALUES(@V_BOOK_CODE,@IN_PRODUCT_ID,@V_PRODUCT_CODE,@V_PRODUCT_NAME,@V_CURRENCY_ID,@V_CUST_ID,
               @IN_PRE_CODE,@OUT_CONTRACT_BH,@V_LINK_MAN,@IN_VALID_PERIOD,@V_PERIOD_UNIT,@IN_QS_DATE,@V_START_DATE,@V_END_DATE,
               @IN_RG_MONEY,@IN_JK_DATE,@IN_JK_TYPE,@V_JK_TYPE_NAME,@IN_BANK_ID,@V_BANK_NAME,@IN_BANK_ACCT,@IN_GAIN_ACCT,
               @IN_INPUT_MAN,@V_SERVICE_MAN,@V_SERVICE_MAN,@IN_SUMMARY,@IN_CHECK_MAN, @IN_BANK_SUB_NAME, @IN_CITY_SERIAL_NO, @V_CITY_NAME,@IN_ZY_FLAG,
               @IN_TOUCH_TYPE,@IN_TOUCH_TYPE_NAME,@IN_FEE_JK_TYPE,@IN_CONTRACT_SUB_BH, @IN_BANK_ACCT_TYPE,
               @V_RG_MONEY2, @V_RG_FEE_RATE, @V_RG_FEE_MONEY, @V_JK_TOTAL_MONEY,@IN_BONUS_FLAG,@IN_SUB_PRODUCT_ID,@IN_WITH_BANK_FLAG,
               @IN_HT_BANK_ID,@V_HT_BANK_NAME,@IN_HT_BANK_SUB_NAME,@IN_CHANNEL_ID,@IN_CHANNEL_TYPE,@IN_CHANNEL_MEMO,@IN_CHANNEL_COOPERTYPE,@V_CHANNEL_COOPERTYPE_NAME,
               @IN_WITH_SECURITY_FLAG,@IN_WITH_PRIVATE_FLAG,@IN_BONUS_RATE,@IN_PROV_FLAG,@IN_PROV_LEVEL,@V_PROV_LEVEL_NAME,@IN_BZJ_FLAG,@IN_MARKET_MONEY,
               @IN_BANK_PROVINCE,@V_BANK_PROVINCE_NAME,@IN_BANK_CITY,@V_BANK_CITY_NAME,@IN_RECOMMEND_MAN,@IN_EXPECT_ROR_LOWER,@IN_EXPECT_ROR_UPPER,@IN_RECOMMEND_MAN_NAME,
               @IN_IS_YKGL,@IN_XTHTYJSYL,@IN_CONTACT_ID,@IN_HT_CUST_NAME,@IN_HT_CUST_ADDRESS,@IN_HT_CUST_TEL,@IN_SPOT_DEAL,
               @IN_MONEY_ORIGIN,@V_MONEY_ORIGIN_NAME,@IN_SUB_MONEY_ORIGIN,@V_SUB_MONEY_ORIGIN_NAME,@IN_CONTRACT_TYPE)
    SELECT @OUT_SERIAL_NO = @@IDENTITY
    
    IF @IN_ZY_FLAG = 1
    BEGIN
        INSERT INTO INTRUST..TBENIFITOR(
              BOOK_CODE,PRODUCT_ID,CONTRACT_BH,CUST_ID,TO_AMOUNT, BEN_AMOUNT,BEN_MONEY,BEN_DATE, VALID_PERIOD, BEN_END_DATE,
              JK_TYPE,JK_TYPE_NAME,INPUT_MAN,BANK_ID,BANK_NAME,BANK_ACCT,PROV_FLAG,PROV_LEVEL,PROV_LEVEL_NAME,
              BANK_SUB_NAME, BEN_STATUS, BEN_STATUS_NAME, CUST_ACCT_NAME, BANK_ACCT_TYPE,BONUS_FLAG,SUB_PRODUCT_ID,CHANNEL_ID,BONUS_RATE,BZJ_FLAG,
              BANK_PROVINCE,BANK_PROVINCE_NAME,BANK_CITY,BANK_CITY_NAME,EXPECT_ROR_LOWER,EXPECT_ROR_UPPER,
              BEN_CUST_NAME,BEN_CUST_ADDRESS,BEN_CUST_TEL,
              MONEY_ORIGIN,MONEY_ORIGIN_NAME,SUB_MONEY_ORIGIN,SUB_MONEY_ORIGIN_NAME)
         VALUES
              (@V_BOOK_CODE,@IN_PRODUCT_ID,@OUT_CONTRACT_BH,@V_CUST_ID,@IN_RG_MONEY, @IN_RG_MONEY,@IN_RG_MONEY,@IN_JK_DATE, @IN_VALID_PERIOD, @V_END_DATE,
               @IN_JK_TYPE,@V_JK_TYPE_NAME,@IN_INPUT_MAN,@IN_BANK_ID,@V_BANK_NAME,@IN_BANK_ACCT,@IN_PROV_FLAG,@IN_PROV_LEVEL,@V_PROV_LEVEL_NAME,
               @IN_BANK_SUB_NAME, '121101', N'正常',@IN_GAIN_ACCT, @IN_BANK_ACCT_TYPE,@IN_BONUS_FLAG,@IN_SUB_PRODUCT_ID,@IN_CHANNEL_ID,@IN_BONUS_RATE,@IN_BZJ_FLAG,
               @IN_BANK_PROVINCE,@V_BANK_PROVINCE_NAME,@IN_BANK_CITY,@V_BANK_CITY_NAME,@IN_EXPECT_ROR_LOWER,@IN_EXPECT_ROR_UPPER,
               @IN_HT_CUST_NAME,@IN_HT_CUST_ADDRESS,@IN_HT_CUST_TEL,
               @IN_MONEY_ORIGIN,@V_MONEY_ORIGIN_NAME,@IN_SUB_MONEY_ORIGIN,@V_SUB_MONEY_ORIGIN_NAME)
    END

    ----添加一条客户银行帐号信息--
    IF NOT EXISTS( SELECT 1 FROM INTRUST..TCUSTBANKACCT WHERE CUST_ID = @V_CUST_ID AND BANK_ID = @IN_BANK_ID AND BANK_ACCT = @IN_BANK_ACCT )
        AND @IN_BANK_ID <> '' AND NOT @IN_BANK_ID IS NULL AND @IN_BANK_ACCT <> '' AND NOT @IN_BANK_ACCT IS NULL
    BEGIN
        INSERT INTO INTRUST..TCUSTBANKACCT(CUST_ID, BANK_ID, BANK_NAME, BANK_ACCT, CARD_TYPE, CARD_ID,SUB_BANK_NAME,BANK_PROVINCE,BANK_CITY)
              VALUES(@V_CUST_ID, @IN_BANK_ID, @V_BANK_NAME, @IN_BANK_ACCT, @V_CARD_TYPE, @V_CARD_ID,@IN_BANK_SUB_NAME,@IN_BANK_PROVINCE,@IN_BANK_CITY)
    END
    
    --如果原来为潜在客户，则更新为事实客户
    IF @V_IS_DEAL=2
		UPDATE TCustomers SET IS_DEAL=1 WHERE CUST_ID=@V_CUST_ID
    --更新CRM预约表中的已签约额
    DECLARE @V_PREPRODUCT_ID INT
    SELECT @V_PREPRODUCT_ID=PREPRODUCT_ID FROM TPREPRODUCT WHERE BIND_PRODUCT_ID=@IN_PRODUCT_ID
    UPDATE TPRECONTRACT SET HT_RG_MONEY=ISNULL(HT_RG_MONEY,0)+@IN_RG_MONEY WHERE PREPRODUCT_ID=@V_PREPRODUCT_ID AND CUST_ID=@V_CUST_ID

    --工作提示
    SET @V_MENU_ID = '2130103'
    SELECT @V_TASK_CODE = TASK_CODE,@V_LOGIN_NAME = TASK_INFO FROM INTRUST..TTASKDICT WHERE TASK_CODE = 301
    SET @V_LOGIN_NAME = @V_LOGIN_NAME + N'产品编号：' + @V_PRODUCT_CODE + N',产品名称: ' + @V_PRODUCT_NAME
                                      + N'合同编号：' + @IN_CONTRACT_BH + N',实际合同编号: ' + @IN_CONTRACT_SUB_BH
    SELECT @V_SYS_DATE = CONVERT(INT,CONVERT(CHAR(8),GETDATE(),112))
    --关闭产品开立审核已通过提示
    EXEC @V_RET_CODE = INTRUST..SP_INNER_CLOSETASKINFO @V_TASK_CODE,@IN_PRODUCT_ID,@IN_PRODUCT_ID,0

    --生成认购合同已录入提示
    EXEC @V_RET_CODE = INTRUST..SP_INNER_MAKETASKINFO @V_TASK_CODE,@V_LOGIN_NAME,@V_LOGIN_NAME,@V_SYS_DATE,@IN_PRODUCT_ID,
                                             @IN_INPUT_MAN,@V_MENU_ID,@IN_CHECK_MAN,@OUT_SERIAL_NO,NULL

    SELECT @SSUMMARY = N'客户通过预约方式认购合同，产品编号：' + @V_PRODUCT_CODE + N'合同序号：' + @OUT_CONTRACT_BH + N', 合同编号: ' + @IN_CONTRACT_SUB_BH
    INSERT INTO INTRUST..TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)

    --新增产品日志记录 add by jinxr 2009/4/20
    INSERT INTO INTRUST..TPRODUCTLOG(PRODUCT_ID,BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY,BUSI_DATE)
        VALUES(@IN_PRODUCT_ID,@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY,@IN_QS_DATE)

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
