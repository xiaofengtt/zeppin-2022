USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TCUSTOMERINFO @IN_BOOK_CODE                 INTEGER,                            --1.账套
                                      @IN_CUST_NAME                 NVARCHAR (100),                     --2.客户名称
                                      @IN_CUST_TEL                  NVARCHAR (20),                      --3.联系电话
                                      @IN_POST_ADDRESS              NVARCHAR (60),                      --4.联系地址
                                      @IN_POST_CODE                 NVARCHAR(6),                        --5.邮编
                                      @IN_CARD_TYPE                 NVARCHAR(10),                       --6.客户证件类型
                                      @IN_CARD_ID                   NVARCHAR (40),                      --7.客户证件号码
                                      @IN_AGE                       INTEGER,                            --8.年龄
                                      @IN_SEX                       INTEGER,                            --9.性别:1男2女
                                      @IN_O_TEL                     NVARCHAR (20),                      --10.办公电话
                                      @IN_H_TEL                     NVARCHAR (20),                      --11.家庭电话
                                      @IN_MOBILE                    NVARCHAR (100),                     --12.手机
                                      @IN_BP                        NVARCHAR (60),                      --13.BP机
                                      @IN_FAX                       NVARCHAR (20),                      --14.传真
                                      @IN_E_MAIL                    NVARCHAR (30),                      --15.电邮
                                      @IN_CUST_TYPE                 INTEGER,                            --16.客户类别：个人、机构
                                      @IN_TOUCH_TYPE                NVARCHAR (10),                      --17.联系方式
                                      @IN_CUST_SOURCE               NVARCHAR (10),                      --18.客户来源
                                      @IN_SUMMARY                   NVARCHAR (200),                     --19.备注1
                                      @IN_INPUT_MAN                 INTEGER,                            --20.操作员
                                      @IN_CUST_NO                   NVARCHAR(8),                        --21.客户编号
                                      @IN_LEGAL_MAN                 NVARCHAR(20),                       --22.法人代表
                                      @IN_LEGAL_ADDRESS             NVARCHAR(60),                       --23.法人代表地址
                                      @IN_BIRTHDAY                  INTEGER,                            --24.出生年月
                                      @IN_POST_ADDRESS2             NVARCHAR (60),                      --25.联系地址2
                                      @IN_POST_CODE2                NVARCHAR(6),                        --26.邮编2
                                      @IN_PRINT_DEPLOY_BILL         INTEGER,                            --27.
                                      @IN_PRINT_POST_INFO           INTEGER,                            --28.
                                      @IN_IS_LINK                   INTEGER,                            --29.
                                      @IN_SERVICE_MAN               INTEGER         = NULL,             --30.客户经理
                                      @OUT_CUST_ID                  INTEGER OUTPUT,                     --31.客户ID
                                      @IN_VIP_CARD_ID               NVARCHAR(20)    = NULL,             --32.VIP卡编号
                                      @IN_VIP_DATE                  INTEGER         = NULL,             --33.VIP发卡日期
                                      @IN_HGTZR_BH                  NVARCHAR(20)    = NULL,             --34.合格投资人编号
                                      @IN_VOC_TYPE                  NVARCHAR(10)    = '',               --35.个人职业/机构行业类别(1142/2142)
                                      @IN_CARD_VALID_DATE           INTEGER         = NULL,             --36.客户身份证件有效期限8位日期表示
                                      @IN_COUNTRY                   NVARCHAR(10)    = NULL,             --37.客户国籍(9997)
                                      @IN_JG_CUST_TYPE              NVARCHAR(10)    = NULL,             --38.机构客户类别(9921)，仅在CUST_TYPE=2机构时有效
                                      @IN_CONTACT_MAN               NVARCHAR(30)    = NULL,             --39.机构客户联系人
                                      @IN_MONEY_SOURCE              NVARCHAR(300)   = NULL,             --40.资金来源
                                      @IN_FACT_CONTROLLER           NVARCHAR(100)   = NULL,             --41.实际控制人/股东或实际控制人
                                      @IN_LINK_TYPE                 INTEGER         = NULL,             --42.关联类型(1302)
                                      @IN_LINK_GD_MONEY             DECIMAL(16,2)   = 0,                --43.股东投资入股信托公司金额
                                      @IN_GRADE_LEVEL               VARCHAR(10)     = '320301',         --44.风险等级（3203）
                                      @IN_COMPLETE_FLAG             INTEGER         = NULL,             --45.资料完整标志 1完整 2不完整 默认2
                                      @IN_GOV_PROV_REGIONAL         NVARCHAR(10)    = NULL,             --46.省级行政区域
                                      @IN_GOV_REGIONAL              NVARCHAR(10)    = NULL,             --47.行政区域
                                      @IN_LEGAL_TEL                 NVARCHAR(20)    = NULL,             --48.法人固定电话
                                      @IN_LEGAL_MOBILE              NVARCHAR(20)    = NULL,             --49.法人手机
                                      @IN_ISSUED_ORG                NVARCHAR(100)   = NULL,             --50.发证机关
                                      @IN_INDUSTRY_POST             NVARCHAR(10)    = NULL,             --51.行业岗位 --> 岗位
                                      @IN_CUST_INDUSTRY             NVARCHAR(10)    = NULL,             --52.行业
                                      @IN_CUST_CORP_NATURE          NVARCHAR(10)    = NULL,             --53.客户单位性质
                                      @IN_CORP_BRANCH               NVARCHAR(60)    = NULL,             --54.信托分支机构
                                      @IN_SERVICE_MAN_TEL           NVARCHAR(20)    = NULL,             --55.客户经理电话
                                      @IN_GRADE_SCORE               NVARCHAR(10)    = NULL,             --56.风险测评得分
                                      @IN_FC_CARD_TYPE              NVARCHAR(10)    = NULL,             --57.实际控制人 证件类型
                                      @IN_FC_CARD_ID                NVARCHAR(40)    = NULL,             --58.实际控制人 证件有号
                                      @IN_FC_CARD_VALID_DATE        INTEGER         = NULL,             --59.实际控制人 证件有效期
                                      @IN_SUMMARY1                  NVARCHAR(200)   = NULL,             --60.备注1
                                      @IN_SUMMARY2                  NVARCHAR(200)   = NULL,             --61.备注2
                                      @IN_LEGAL_POST_CODE           NVARCHAR(6)     = NULL,             --62.法人邮编
                                      @IN_TRANS_NAME                NVARCHAR(20)    = NULL,             --63.经办人姓名
                                      @IN_TRANS_TEL                 NVARCHAR(20)    = NULL,             --64.经办人固定电话
                                      @IN_TRANS_MOBILE              NVARCHAR(20)    = NULL,             --65.经办人手机
                                      @IN_TRANS_ADDRESS             NVARCHAR(120)   = NULL,             --66.经办人地址
                                      @IN_TRANS_POST_CODE           NVARCHAR(6)     = NULL,             --67.经办人邮编
                                      @IN_REGISTER_ADDRESS          NVARCHAR(120)   = NULL,             --68.注册地址
                                      @IN_REGISTER_POST_CODE        NVARCHAR(6)     = NULL,             --69.注册地邮编
                                      @IN_EAST_JG_TYPE              NVARCHAR(10)	= '',				--70.机构类型2109（依据EAST委托人类型WTRLX）
									  @IN_JG_WTRLX2                 NVARCHAR(2)		= ''				--80.机构有效：1金融性公司 2政府 3非金融性公司 4境外金融性公司

WITH ENCRYPTION
AS
    DECLARE @V_ERROR NVARCHAR(200),@V_ReceiveServices BIGINT
    DECLARE @V_CUST_ID INT,@V_CUST_NO NVARCHAR(8),@V_CARD_TYPE_NAME NVARCHAR(30),@V_MAX_ID INT
    DECLARE @V_CUST_TYPE_NAME NVARCHAR(10),@V_CUST_LEVEL NVARCHAR(10),@V_SEX_NAME NVARCHAR(10)
    DECLARE @V_VOC_TYPE_NAME NVARCHAR(30),@V_CUST_LEVEL_NAME NVARCHAR(30),@V_TOUCH_TYPE_NAME NVARCHAR(30),@V_CUST_SOURCE_NAME NVARCHAR(30)
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT
    DECLARE @V_GRADE_LEVEL_NAME NVARCHAR(30),@V_EAST_JG_TYPE_NAME NVARCHAR(40)
    DECLARE @SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_GOV_PROV_REGIONAL_NAME  NVARCHAR(30)     --省级行政区域说明
    DECLARE @V_GOV_REGIONAL_NAME       NVARCHAR(30)     --行政区域说明
    DECLARE @V_MONEY_SOURCE_NAME  NVARCHAR(30)          --信托资金来源说明
    DECLARE @V_CUST_INDUSTRY_NAME NVARCHAR(30),@V_CUST_CORP_NATURE_NAME NVARCHAR(30),@V_INDUSTRY_POST_NAME NVARCHAR(30)
    DECLARE @V_USER_ID INT,@V_CUST_ID2 INT,@V_MAX_ID2 INT

    BEGIN TRY
    BEGIN TRANSACTION

    SELECT @V_USER_ID = USER_ID FROM TSYSTEMINFO
    SELECT @V_RET_CODE = -20601000, @IBUSI_FLAG = 20601
    SELECT @SBUSI_NAME = N'增加客户基本信息', @SSUMMARY = N'增加客户基本信息'
    SELECT @V_CARD_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_CARD_TYPE
    SELECT @OUT_CUST_ID = 0
    IF ISNULL(@IN_CARD_ID,'') <> '' AND EXISTS(SELECT * FROM TSYSCONTROL WHERE FLAG_TYPE = 'A_CARDID' AND VALUE = 1)
    BEGIN
        IF @IN_CARD_TYPE = '110801' AND LEN(@IN_CARD_ID) = 15   --15位身份证号码转18位
            SELECT @IN_CARD_ID = dbo.IDCARD15TO18(@IN_CARD_ID)
    END
    IF ISNULL(@IN_CARD_ID,'') <> '' AND EXISTS(SELECT * FROM TCustomers WHERE CUST_NAME = @IN_CUST_NAME AND CARD_TYPE = @IN_CARD_TYPE AND CARD_ID = @IN_CARD_ID AND STATUS <>'112805') BEGIN
        SET @V_ERROR = '客户已存在!客户[' + @IN_CUST_NAME + ']，证件类型：' + @V_CARD_TYPE_NAME + '，证件号：' + @IN_CARD_ID
        RAISERROR(@V_ERROR,16,1)
        --RETURN @V_RET_CODE-2   -- 客户已存在
    END

    --IF NOT EXISTS(SELECT 1 FROM TSYSTEMINFO WHERE USER_ID = 15)
    IF @V_USER_ID <> 15 BEGIN
        IF ISNULL(@IN_CARD_ID,'') <> '' AND 
           EXISTS (SELECT * FROM TCustomers WHERE CARD_TYPE = @IN_CARD_TYPE AND CARD_ID = @IN_CARD_ID) AND
           EXISTS(SELECT 1 FROM TSYSCONTROL WHERE FLAG_TYPE = 'CUSTCARD' AND VALUE = 1) BEGIN
            SET @V_ERROR = '指定的证件已存在!证件类型：' + @V_CARD_TYPE_NAME + '，证件号：' + @IN_CARD_ID
            RAISERROR(@V_ERROR,16,2)
        END
    END

    IF @IN_CUST_TYPE = 1
        SELECT @V_CUST_TYPE_NAME = N'个人'
    ELSE
        SELECT @V_CUST_TYPE_NAME = N'机构'

    SELECT @IN_COUNTRY = ISNULL(@IN_COUNTRY,N'9997CHN')   --默认中国 add by luohh 200908

    
    IF @IN_SEX = 1 SET @V_SEX_NAME = N'男' ELSE SET @V_SEX_NAME = N'女'

    SELECT @V_CUST_LEVEL = '111101'
    SELECT @V_CUST_LEVEL_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @V_CUST_LEVEL
    IF ISNULL(@IN_TOUCH_TYPE,'') = '' SELECT @IN_TOUCH_TYPE = '110901'
    SELECT @V_TOUCH_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_TOUCH_TYPE
    IF ISNULL(@IN_CUST_SOURCE,'') = '' SELECT @IN_CUST_SOURCE = '111004'
    SELECT @V_CUST_SOURCE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_CUST_SOURCE

    --风险等级
    IF ISNULL(@IN_GRADE_LEVEL,'') = '' BEGIN
        IF @V_USER_ID = 21  --金汇要求风险等级可以不填写
            SELECT @IN_GRADE_LEVEL = ''
        ELSE
            SELECT @IN_GRADE_LEVEL = '320301'
    END

    IF @IN_GRADE_LEVEL = ''
        SELECT @V_GRADE_LEVEL_NAME = ''
    ELSE
        SELECT @V_GRADE_LEVEL_NAME = ISNULL(TYPE_CONTENT,'') FROM TDICTPARAM WHERE TYPE_VALUE = @IN_GRADE_LEVEL
    SELECT @V_GOV_PROV_REGIONAL_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_GOV_PROV_REGIONAL
    SELECT @V_GOV_REGIONAL_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_GOV_REGIONAL
    SELECT @V_MONEY_SOURCE_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_MONEY_SOURCE

    SELECT @V_CUST_ID = ISNULL(MAX(CUST_ID),0)+ 1 FROM TCustomers
    SELECT @V_CUST_ID2 = ISNULL(MAX(CUST_ID),0)+ 1 FROM INTRUST..TCUSTOMERINFO
    IF @V_CUST_ID2 > @V_CUST_ID
        SET @V_CUST_ID = @V_CUST_ID2
    --客户编号
    IF @IN_CUST_NO IS NULL OR @IN_CUST_NO = '' BEGIN
        SELECT @V_MAX_ID = ISNULL(MAX(LIST_ID),0) + 1 FROM TCustomers
        SELECT @V_MAX_ID2 = ISNULL(MAX(LIST_ID),0) + 1 FROM INTRUST..TCUSTOMERINFO
        IF @V_MAX_ID2>@V_MAX_ID SET @V_MAX_ID=@V_MAX_ID2
        SELECT @V_CUST_NO = CONVERT(CHAR(8),@IN_CUST_TYPE * 10000000 + @V_MAX_ID)
        --20050112 dongyg 个人客户编号以0开头，机构以J开头
        IF @IN_CUST_TYPE = 1
            SELECT @V_CUST_NO = '0' + RIGHT(@V_CUST_NO,7)
        ELSE
            SELECT @V_CUST_NO = 'J' + RIGHT(@V_CUST_NO,7)
        IF EXISTS(SELECT 1 FROM TCustomers WHERE CUST_NO = @V_CUST_NO) BEGIN
            --RETURN @V_RET_CODE - 1  --客户编号已存在
            RAISERROR('客户编号已存在',16,3)
        END
    END
    ELSE BEGIN
        IF EXISTS(SELECT 1 FROM TCustomers WHERE CUST_NO = @IN_CUST_NO) BEGIN
            --RETURN @V_RET_CODE - 1  --客户编号已存在
            RAISERROR('客户编号已存在',16,4)
        END
        SELECT @V_CUST_NO = @IN_CUST_NO
    END
    --如果是个人客户，证件类型是身份证，且未输入生日时，根据15位和18位的情况生成生日变量
    IF @IN_CUST_TYPE = 1 AND (@IN_BIRTHDAY IS NULL OR @IN_BIRTHDAY = 0) AND @IN_CARD_TYPE = '110801' BEGIN
        IF LEN(@IN_CARD_ID)= 15
            SELECT @IN_BIRTHDAY = CONVERT(INT,'19' + SUBSTRING(@IN_CARD_ID,7,6))
        ELSE IF LEN(@IN_CARD_ID) = 18
            SELECT @IN_BIRTHDAY = CONVERT(INT,SUBSTRING(@IN_CARD_ID,7,8))
    END
    IF @IN_SERVICE_MAN IS NULL OR @IN_SERVICE_MAN = 0 SELECT @IN_SERVICE_MAN = @IN_INPUT_MAN

    SELECT @V_VOC_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_VOC_TYPE
    
    SELECT @V_CUST_INDUSTRY_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_CUST_INDUSTRY
    SELECT @V_CUST_CORP_NATURE_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_CUST_CORP_NATURE
    SELECT @V_INDUSTRY_POST_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_INDUSTRY_POST
    --查找机构类型名称
    SELECT @V_EAST_JG_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_EAST_JG_TYPE
    
    SELECT @V_ReceiveServices = SUM(ServiceType) FROM TServiceDefine

    INSERT INTO TCustomers(ReceiveServices, --添加客户时，默认接收所有服务
          CUST_ID,CUST_NO,CUST_NAME,CUST_TEL,POST_ADDRESS,POST_ADDRESS2,
          CARD_TYPE,CARD_TYPE_NAME,CARD_ID,AGE,SEX,SEX_NAME,POST_CODE2,
          O_TEL,H_TEL,MOBILE,BP,FAX,E_MAIL,CUST_TYPE,CUST_TYPE_NAME,
          WT_FLAG,WT_FLAG_NAME,PASSWORD,
          TOUCH_TYPE,TOUCH_TYPE_NAME,CUST_SOURCE,CUST_SOURCE_NAME,
          SUMMARY,INPUT_MAN,POST_CODE,CHECK_FLAG,LIST_ID, LEGAL_MAN, LEGAL_ADDRESS, BIRTHDAY,
          PRINT_DEPLOY_BILL, PRINT_POST_INFO, IS_LINK, SERVICE_MAN,VIP_CARD_ID,VIP_DATE,HGTZR_BH,
          VOC_TYPE,VOC_TYPE_NAME,CARD_VALID_DATE,COUNTRY,JG_CUST_TYPE, CONTACT_MAN,MONEY_SOURCE,FACT_CONTROLLER,
          IS_DEAL,GOV_PROV_REGIONAL,GOV_PROV_REGIONAL_NAME,GOV_REGIONAL,
          GOV_REGIONAL_NAME,COMPLETE_FLAG,EAST_JG_TYPE,EAST_JG_TYPE_NAME,JG_WTRLX2)
    VALUES(@V_ReceiveServices,
          @V_CUST_ID,@V_CUST_NO,@IN_CUST_NAME,@IN_CUST_TEL,@IN_POST_ADDRESS,@IN_POST_ADDRESS2,
          @IN_CARD_TYPE,@V_CARD_TYPE_NAME,@IN_CARD_ID,@IN_AGE,@IN_SEX,@V_SEX_NAME,@IN_POST_CODE2,
          @IN_O_TEL,@IN_H_TEL,@IN_MOBILE,@IN_BP,@IN_FAX,@IN_E_MAIL,@IN_CUST_TYPE,@V_CUST_TYPE_NAME,
          2,'否','000000',
          @IN_TOUCH_TYPE,@V_TOUCH_TYPE_NAME,@IN_CUST_SOURCE,@V_CUST_SOURCE_NAME,
          @IN_SUMMARY,@IN_INPUT_MAN,@IN_POST_CODE,2,@V_MAX_ID, @IN_LEGAL_MAN, @IN_LEGAL_ADDRESS, @IN_BIRTHDAY,
          @IN_PRINT_DEPLOY_BILL, @IN_PRINT_POST_INFO, @IN_IS_LINK, @IN_SERVICE_MAN,@IN_VIP_CARD_ID,@IN_VIP_DATE,@IN_HGTZR_BH,
          @IN_VOC_TYPE,@V_VOC_TYPE_NAME,@IN_CARD_VALID_DATE,@IN_COUNTRY,@IN_JG_CUST_TYPE, @IN_CONTACT_MAN,@IN_MONEY_SOURCE,@IN_FACT_CONTROLLER,
          2,@IN_GOV_PROV_REGIONAL,@V_GOV_PROV_REGIONAL_NAME,@IN_GOV_REGIONAL,
          @V_GOV_REGIONAL_NAME,@IN_COMPLETE_FLAG,@IN_EAST_JG_TYPE,@V_EAST_JG_TYPE_NAME,@IN_JG_WTRLX2)

    /*UPDATE TCUSTOMERS
        SET PERSONAL_INCOME = @IN_PERSONAL_INCOME,
            HOUSEHOLD_INCOME = @IN_HOUSEHOLD_INCOME,
            FEEDING_NUM_PEOPLE = @IN_FEEDING_NUM_PEOPLE,
            MAIN_SOURCE = @IN_MAIN_SOURCE,
            OTHER_SOURCE = @IN_OTHER_SOURCE,
            HOUSE_POSITION = @IN_HOUSE_POSITION,
            HOUSE_PROPERTY = @IN_HOUSE_PROPERTY,
            HOUSE_AREA = @IN_HOUSE_AREA,
            PLAT_ENVALUATE = @IN_PLAT_ENVALUATE,
            MARKET_APPRAISAL = @IN_MARKET_APPRAISAL,
            VEHICLE_NUM = @IN_VEHICLE_NUM,
            VEHICLE_MAKE = @IN_VEHICLE_MAKE,
            VEHICLE_TYPE = @IN_VEHICLE_TYPE,
            CREDIT_TYPE = @IN_CREDIT_TYPE,
            CREDIT_NUM = @IN_CREDIT_NUM,
            CREDIT_START_DATE = @IN_CREDIT_START_DATE,
            CREDIT_END_DATE = @IN_CREDIT_END_DATE,
            OTHER_INVESTMENT_STATUS = @IN_OTHER_INVESTMENT_STATUS,
            TYPE_PREF = @IN_TYPE_PREF,
            TIME_LIMIT_PREF = @IN_TIME_LIMIT_PREF,
            INVEST_PREF = @IN_INVEST_PREF,
            HOBBY_PREF = @IN_HOBBY_PREF,
            SERVICE_PREF = @IN_SERVICE_PREF,
            CONTACT_PREF = @IN_CONTACT_PREF,
            HEAD_OFFICE_CUST_ID = @IN_HEAD_OFFICE_CUST_ID,
            STATURE = @IN_STATURE,WEIGHT = @IN_WEIGHT,
            SPOUSE_NAME = @IN_SPOUSE_NAME,
            SPOUSE_INFO = @IN_SPOUSE_INFO,
            CHILDREN_NAME = @IN_CHILDREN_NAME,
            CHILDREN_INFO = @IN_CHILDREN_INFO,
            NATION = @IN_NATION,
            MARITAL = @IN_MARITAL,
            HEALTH = @IN_HEALTH,
            EDUCATION = @IN_EDUCATION,
            POST = @IN_POST,
            HOLDEROFANOFFICE = @IN_HOLDEROFANOFFICE,
            COMPANY_CHARACTER = @IN_COMPANY_CHARACTER,
            COMPANY_STAFF = @IN_COMPANY_STAFF,
            BOCOM_STAFF = @IN_BOCOM_STAFF,
            CLIENT_QUALE = @IN_CLIENT_QUALE,
            REGISTERED_PLACE = @IN_REGISTERED_PLACE,
            REGISTERED_CAPITAL = @IN_REGISTERED_CAPITAL,
            EMPLOYEE_NUM = @IN_EMPLOYEE_NUM,
            HOLDING = @IN_HOLDING,
            OTHER_INVE_STATUS_NAME = @V_OTHER_INVE_STATUS_NAME,
            TYPE_PREF_NAME = @V_TYPE_PREF_NAME,
            TIME_LIMIT_PREF_NAME = @V_TIME_LIMIT_PREF_NAME,
            INVEST_PREF_NAME = @V_INVEST_PREF_NAME,
            HOBBY_PREF_NAME = @V_HOBBY_PREF_NAME,
            SERVICE_PREF_NAME = @V_SERVICE_PREF_NAME,
            CONTACT_PREF_NAME = @V_CONTACT_PREF_NAME,
            NATION_NAME = @V_NATION_NAME,
            HEALTH_NAME = @V_HEALTH_NAME,
            EDUCATION_NAME = @V_EDUCATION_NAME,
            COMPANY_CHARACTER_NAME = @V_COMPANY_CHARACTER_NAME,
            CLIENT_QUALE_NAME = @V_CLIENT_QUALE_NAME,
            RISK_PREF = @IN_RISK_PREF,
            RISK_PREF_NAME = @V_RISK_PREF_NAME,
            FREE_CASH_FLOW = @IN_FREE_CASH_FLOW,
            BUREND_OF_DEBT = @IN_BUREND_OF_DEBT,
            COMPLETE_FLAG = @IN_COMPLETE_FLAG,
            COMPANY_UNIT = @IN_COMPANY_UNIT,
            COMPANY_DEPART  = @IN_COMPANY_DEPART,
            COMPANY_POSITION = @IN_COMPANY_POSITION,
            CUST_AUM = @IN_CUST_AUM,
            CUST_AUM_NAME = @V_CUST_AUM_NAME,
            CUST_AGE_GROUP = @IN_CUST_AGE_GROUP,
            CUST_AGE_GROUP_NAME = @V_CUST_AGE_GROUP_NAME,
            INVES_EXPERINCE = @IN_INVES_EXPERINCE,
            CUST_SOURCE_EXPLAIN = @IN_CUST_SOURCE_EXPLAIN
        WHERE CUST_ID = @V_CUST_ID
    */
    INSERT INTO INTRUST..TCUSTOMERINFO(BOOK_CODE,CUST_ID,CUST_NO,CUST_NAME,CUST_TEL,POST_ADDRESS,POST_ADDRESS2,
                              CARD_TYPE,CARD_TYPE_NAME,CARD_ID,AGE,SEX,SEX_NAME,POST_CODE2,
                              O_TEL,H_TEL,MOBILE,BP,FAX,E_MAIL,CUST_TYPE,CUST_TYPE_NAME,
                              WT_FLAG,WT_FLAG_NAME,CUST_LEVEL,CUST_LEVEL_NAME,PASSWORD,
                              TOUCH_TYPE,TOUCH_TYPE_NAME,CUST_SOURCE,CUST_SOURCE_NAME,
                              SUMMARY,INPUT_MAN,POST_CODE,CHECK_FLAG,LIST_ID, LEGAL_MAN, LEGAL_ADDRESS, BIRTHDAY,
                              PRINT_DEPLOY_BILL, PRINT_POST_INFO, IS_LINK, SERVICE_MAN,VIP_CARD_ID,VIP_DATE,HGTZR_BH,
                              VOC_TYPE,VOC_TYPE_NAME,CARD_VALID_DATE,COUNTRY,JG_CUST_TYPE, CONTACT_MAN,MONEY_SOURCE,
                              FACT_CONTROLLER,LINK_TYPE,LINK_GD_MONEY,GRADE_LEVEL,GRADE_LEVEL_NAME,COMPLETE_FLAG,
                              GOV_PROV_REGIONAL,GOV_PROV_REGIONAL_NAME,GOV_REGIONAL,GOV_REGIONAL_NAME,LEGAL_TEL,LEGAL_MOBILE,ISSUED_ORG,
                              INDUSTRY_POST,INDUSTRY_POST_NAME,MONEY_SOURCE_NAME,
                              CUST_INDUSTRY,CUST_INDUSTRY_NAME,CUST_CORP_NATURE,CUST_CORP_NATURE_NAME,CORP_BRANCH,SERVICE_MAN_TEL,GRADE_SCORE,
                              FC_CARD_TYPE,FC_CARD_ID,FC_CARD_VALID_DATE,SUMMARY1,SUMMARY2,
                              LEGAL_POST_CODE,TRANS_NAME,TRANS_TEL,TRANS_MOBILE,TRANS_ADDRESS,TRANS_POST_CODE,REGISTER_ADDRESS,REGISTER_POST_CODE,
                              EAST_JG_TYPE,EAST_JG_TYPE_NAME,JG_WTRLX2)
    VALUES(@IN_BOOK_CODE,@V_CUST_ID,@V_CUST_NO,@IN_CUST_NAME,@IN_CUST_TEL,@IN_POST_ADDRESS,@IN_POST_ADDRESS2,
           @IN_CARD_TYPE,@V_CARD_TYPE_NAME,@IN_CARD_ID,@IN_AGE,@IN_SEX,@V_SEX_NAME,@IN_POST_CODE2,
           @IN_O_TEL,@IN_H_TEL,@IN_MOBILE,@IN_BP,@IN_FAX,@IN_E_MAIL,@IN_CUST_TYPE,@V_CUST_TYPE_NAME,
           2,'否',@V_CUST_LEVEL,@V_CUST_LEVEL_NAME,'000000',
           @IN_TOUCH_TYPE,@V_TOUCH_TYPE_NAME,@IN_CUST_SOURCE,@V_CUST_SOURCE_NAME,
           @IN_SUMMARY,@IN_INPUT_MAN,@IN_POST_CODE,2,@V_MAX_ID, @IN_LEGAL_MAN, @IN_LEGAL_ADDRESS, @IN_BIRTHDAY,
           @IN_PRINT_DEPLOY_BILL, @IN_PRINT_POST_INFO, @IN_IS_LINK, @IN_SERVICE_MAN,@IN_VIP_CARD_ID,@IN_VIP_DATE,@IN_HGTZR_BH,
           @IN_VOC_TYPE,@V_VOC_TYPE_NAME,@IN_CARD_VALID_DATE,@IN_COUNTRY,@IN_JG_CUST_TYPE, @IN_CONTACT_MAN,@IN_MONEY_SOURCE,
           @IN_FACT_CONTROLLER,@IN_LINK_TYPE,@IN_LINK_GD_MONEY,@IN_GRADE_LEVEL,@V_GRADE_LEVEL_NAME,@IN_COMPLETE_FLAG,
           @IN_GOV_PROV_REGIONAL,@V_GOV_PROV_REGIONAL_NAME,@IN_GOV_REGIONAL,@V_GOV_REGIONAL_NAME,@IN_LEGAL_TEL,@IN_LEGAL_MOBILE,@IN_ISSUED_ORG,
           @IN_INDUSTRY_POST,@V_INDUSTRY_POST_NAME,@V_MONEY_SOURCE_NAME,
           @IN_CUST_INDUSTRY,@V_CUST_INDUSTRY_NAME,@IN_CUST_CORP_NATURE,@V_CUST_CORP_NATURE_NAME,@IN_CORP_BRANCH,@IN_SERVICE_MAN_TEL,@IN_GRADE_SCORE,
           @IN_FC_CARD_TYPE,@IN_FC_CARD_ID,@IN_FC_CARD_VALID_DATE,@IN_SUMMARY1,@IN_SUMMARY2,
           @IN_LEGAL_POST_CODE,@IN_TRANS_NAME,@IN_TRANS_TEL,@IN_TRANS_MOBILE,@IN_TRANS_ADDRESS,@IN_TRANS_POST_CODE,@IN_REGISTER_ADDRESS,@IN_REGISTER_POST_CODE,
           @IN_EAST_JG_TYPE,@V_EAST_JG_TYPE_NAME,@IN_JG_WTRLX2)

    SELECT @SSUMMARY = N'增加客户基本信息，客户名称：' + @IN_CUST_NAME
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
    SELECT @OUT_CUST_ID = @V_CUST_ID

    /*DECLARE @V_RET INT
    DECLARE @V_GRADE_ID INT,@V_INDEX_ID INT,@V_GRADEDATE INT,@V_VALUE_FLAG INT,@V_OPTION_ID INT
    SELECT @V_GRADE_ID = GRADE_ID FROM INTRUST..TGRADEINFO WHERE GRADE_BH = '01'
    IF(ISNULL(@V_GRADE_ID,0) <> 0) BEGIN
        SELECT @V_GRADEDATE = dbo.GETDATEINT(GETDATE())
        SELECT * INTO #TEMP FROM INTRUST..TGRADEINDEX WHERE GRADE_ID = @V_GRADE_ID AND VALID_FLAG = 1 ORDER BY INDEX_ID ASC,GRADE_ID ASC,XH ASC

        DECLARE CUR_GRADEINDEX CURSOR FOR SELECT INDEX_ID FROM #TEMP
        OPEN CUR_GRADEINDEX
        FETCH NEXT FROM CUR_GRADEINDEX INTO @V_INDEX_ID
        WHILE @@FETCH_STATUS = 0 BEGIN
            SELECT @V_VALUE_FLAG = VALUE_FLAG FROM INTRUST..TGRADEINDEX WHERE INDEX_ID = @V_INDEX_ID
            IF(@V_VALUE_FLAG = 4)
                SELECT @V_OPTION_ID = OPTION_ID FROM TGRADEOPTIONS WHERE DF_SERIAL_NO = @V_INDEX_ID AND OPTION_VALUE = 0
            EXEC @V_RET = SP_ADD_KHPJ_TGRADEDETAIL @V_GRADE_ID,@V_INDEX_ID,@V_GRADEDATE,@V_CUST_ID,0,@IN_INPUT_MAN,0,@IN_INPUT_MAN,1,NULL,@V_OPTION_ID
            IF @V_RET < 0 RAISERROR('Raise Error in SP_ADD_KHPJ_TGRADEDETAIL',16,3)
            FETCH NEXT FROM CUR_GRADEINDEX INTO @V_INDEX_ID
        END
        CLOSE CUR_GRADEINDEX
        DEALLOCATE CUR_GRADEINDEX
        IF EXISTS(SELECT 1 FROM #TEMP) BEGIN
            --确保有评分明细才插入总评分结果
            EXEC @V_RET = SP_ADD_KHPJ_TGRADETOTAL @V_GRADE_ID,@V_CUST_ID,1,@IN_INPUT_MAN,@V_GRADEDATE,1,NULL
            IF @V_RET < 0 RAISERROR('Raise Error in SP_ADD_KHPJ_TGRADETOTAL',16,4)
        END
    END
    */

    COMMIT TRANSACTION
    END TRY

    BEGIN CATCH

        IF CURSOR_STATUS('global','CUR_GRADEINDEX') >= 0 BEGIN
            CLOSE CUR_GRADEINDEX
            DEALLOCATE CUR_GRADEINDEX
        END
        IF @@TRANCOUNT > 0 ROLLBACK TRANSACTION

        DECLARE @V_ERROR_STR NVARCHAR(1000),@V_ERROR_NUMBER INT,@V_ERROR_SEVERITY INT,@V_ERROR_STATE INT,
                @V_ERROR_PROCEDURE sysname,@V_ERROR_LINE INT,@V_ERROR_MESSAGE NVARCHAR(4000)

        SELECT @V_ERROR_STR = N'%s<br>Procedure:%s,Line:%d',
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
