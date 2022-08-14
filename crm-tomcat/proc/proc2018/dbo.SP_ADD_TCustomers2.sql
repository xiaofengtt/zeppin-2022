USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TCustomers2 @IN_CUST_NAME                    NVARCHAR(100),
                                    @IN_CUST_TEL                     NVARCHAR(20),
                                    @IN_POST_ADDRESS                 NVARCHAR(60),
                                    @IN_POST_CODE                    NVARCHAR(6),
                                    @IN_CARD_TYPE                    NVARCHAR(10),
                                    @IN_CARD_ID                      NVARCHAR(40),
                                    @IN_AGE                          INT,
                                    @IN_SEX                          INT,
                                    @IN_O_TEL                        NVARCHAR(20),
                                    @IN_H_TEL                        NVARCHAR(20),
                                    @IN_MOBILE                       NVARCHAR(100),
                                    @IN_BP                           NVARCHAR(60),
                                    @IN_FAX                          NVARCHAR(20),
                                    @IN_E_MAIL                       NVARCHAR(30),
                                    @IN_CUST_TYPE                    INT,
                                    @IN_TOUCH_TYPE                   NVARCHAR(10),
                                    @IN_CUST_SOURCE                  NVARCHAR(10),
                                    @IN_SUMMARY                      NVARCHAR(200),
                                    @IN_INPUT_MAN                    INT,
                                    @IN_CUST_NO                      NVARCHAR(8),
                                    @IN_LEGAL_MAN                    NVARCHAR(20),
                                    @IN_LEGAL_ADDRESS                NVARCHAR(60),
                                    @IN_BIRTHDAY                     INT,
                                    @IN_POST_ADDRESS2                NVARCHAR(60),
                                    @IN_POST_CODE2                   NVARCHAR(6),
                                    @IN_PRINT_DEPLOY_BILL            INT,
                                    @IN_PRINT_POST_INFO              INT,
                                    @IN_IS_LINK                      INT,
                                    @IN_SERVICE_MAN                  INT = NULL,
                                    @OUT_CUST_ID                     INT OUTPUT,
                                    @IN_VIP_CARD_ID                  NVARCHAR(20) = NULL,    -- VIP卡编号
                                    @IN_VIP_DATE                     INT = NULL,             -- VIP发卡日期
                                    @IN_HGTZR_BH                     NVARCHAR(20) = NULL,    -- 合格投资人编号
                                    @IN_VOC_TYPE                     NVARCHAR(10) = N'',     -- 个人职业/机构行业类别(1142/2142)
                                    @IN_CARD_VALID_DATE              INT = NULL,             --客户身份证件有效期限8位日期表示
                                    @IN_COUNTRY                      NVARCHAR(10) = NULL,    --客户国籍(9997)
                                    @IN_JG_CUST_TYPE                 NVARCHAR(10) = NULL,    --机构客户类别(9921)，仅在CUST_TYPE=2机构时有效
                                    @IN_CONTACT_MAN                  NVARCHAR(30) = NULL,    --机构客户联系人
                                    @IN_MONEY_SOURCE                 NVARCHAR(300) = NULL,   --资金来源
                                    @IN_MONEY_SOURCE_NAME            NVARCHAR(1000) = NULL,  --资金来源名称
                                    @IN_FACT_CONTROLLER              NVARCHAR(100) = NULL,   --实际控制人
                                    @IN_POTENTIAL_MONEY              DECIMAL(16,3),          -- 费用金额
                                    @IN_GOV_PROV_REGIONAL            NVARCHAR(10),           --省级行政区域(9999)
                                    @IN_GOV_REGIONAL                 NVARCHAR(10),           --行政区域(9999)
                                    @IN_RECOMMENDED                  NVARCHAR = NULL,        --推荐人
                                    @IN_PERSONAL_INCOME              DEC(16,3),              --个人年收入
                                    @IN_HOUSEHOLD_INCOME             DEC(16,3),              --家庭年收入
                                    @IN_FEEDING_NUM_PEOPLE           INT,                    --供养人数
                                    @IN_MAIN_SOURCE                  NVARCHAR(160),          --主要经济来源
                                    @IN_OTHER_SOURCE                 NVARCHAR(160),          --其他经济来源
                                    @IN_HOUSE_POSITION               NVARCHAR(120),          --房产位置
                                    @IN_HOUSE_PROPERTY               INT,                    --属性（1商、2住）
                                    @IN_HOUSE_AREA                   INT,                    --面积
                                    @IN_PLAT_ENVALUATE               NVARCHAR(200),          --地段评价
                                    @IN_MARKET_APPRAISAL             DEC(16,3),              --市场估价
                                    @IN_VEHICLE_NUM                  INT,                    --车辆数量
                                    @IN_VEHICLE_MAKE                 NVARCHAR(100),          --品牌
                                    @IN_VEHICLE_TYPE                 NVARCHAR(100),          --型号
                                    @IN_CREDIT_TYPE                  NVARCHAR(60),           --贷款种类
                                    @IN_CREDIT_NUM                   INT,                    --贷款数量
                                    @IN_CREDIT_START_DATE            INT,                    --起始日
                                    @IN_CREDIT_END_DATE              INT,                    --终止日
                                    @IN_OTHER_INVESTMENT_STATUS      NVARCHAR(20),           --其他投资状况(1165)
                                    @IN_TYPE_PREF                    NVARCHAR(20),           --类型偏好(1166)
                                    @IN_TIME_LIMIT_PREF              NVARCHAR(20),           --期限偏好(1170)
                                    @IN_INVEST_PREF                  NVARCHAR(20),           --投向偏好(1167)
                                    @IN_HOBBY_PREF                   NVARCHAR(60),           --兴趣偏好(1168)
                                    @IN_SERVICE_PREF                 NVARCHAR(20),           --服务偏好(1169)
                                    @IN_CONTACT_PREF                 NVARCHAR(20),           --联系方式偏好(1109)
                                    @IN_HEAD_OFFICE_CUST_ID          NVARCHAR(60),           --总行客户号
                                    @IN_STATURE                      INT,                    --身高
                                    @IN_WEIGHT                       INT,                    --体重
                                    @IN_SPOUSE_NAME                  NVARCHAR(40),           --配偶姓名
                                    @IN_SPOUSE_INFO                  NVARCHAR(300),          --配偶信息
                                    @IN_CHILDREN_NAME                NVARCHAR(100),          --子女姓名
                                    @IN_CHILDREN_INFO                NVARCHAR(300),          --子女信息
                                    @IN_NATION                       NVARCHAR(20),           --民族(1161)
                                    @IN_MARITAL                      INT,                    --婚姻状况(1是、0否)
                                    @IN_HEALTH                       NVARCHAR(20),           --健康状况(1162)
                                    @IN_EDUCATION                    NVARCHAR(20),           --教育程度(1163)
                                    @IN_POST                         NVARCHAR(120),          --职务
                                    @IN_HOLDEROFANOFFICE             NVARCHAR(120),          --职称
                                    @IN_COMPANY_CHARACTER            NVARCHAR(20),           --单位性质(1164)
                                    @IN_COMPANY_STAFF                INT,                    --是否为公司员工；1是、0否
                                    @IN_BOCOM_STAFF                  INT,                    --是否为交行员工；1是、0否
                                    @IN_CLIENT_QUALE                 NVARCHAR(20),           --客户性质(机构)(1164)
                                    @IN_REGISTERED_PLACE             NVARCHAR(120),          --注册地(机构)
                                    @IN_REGISTERED_CAPITAL           DEC(16,3),              --注册资金(机构)
                                    @IN_EMPLOYEE_NUM                 INT,                    --员工数（机构）
                                    @IN_HOLDING                      NVARCHAR(400),          --控股及关联信息(机构)
                                    @IN_RISK_PREF                    NVARCHAR(20),           --风险偏好（1172）
                                    @IN_FREE_CASH_FLOW               DEC(16,3),              --自由现金流
                                    @IN_BUREND_OF_DEBT               DEC(16,3),              --债务压力
                                    @IN_COMPLETE_FLAG                INTEGER         = 2,    --资料完整 1完整 2不完整 默认2
                                    @IN_COMPANY_UNIT                 NVARCHAR(60)='',        --所在单位
                                    @IN_COMPANY_DEPART               NVARCHAR(30)='',        --所在部门
                                    @IN_COMPANY_POSITION             NVARCHAR(60)='',        --所在职位
                                    @IN_CUST_AUM                     NVARCHAR(10)='',        --客户AUM
                                    @IN_CUST_AGE_GROUP               NVARCHAR(10)='',        --年龄段
                                    @IN_INVES_EXPERINCE              NVARCHAR(10)='',        --有，无
                                    @IN_CUST_SOURCE_EXPLAIN          NVARCHAR(60)='',        --客户来源说明
                                    @IN_EAST_JG_TYPE                 NVARCHAR(10)='',        --机构类型2109（依据EAST委托人类型WTRLX）
                                    @IN_JG_WTRLX2                    NVARCHAR(2)='',         --机构有效：1金融性公司 2政府 3非金融性公司 4境外金融性公司
                                    @IN_CUST_FLAG                    NVARCHAR(20) ='',       --客户标识：第1位：客户的真实性核查[1未核查(默认)，2真实，3待核查(已经核实过但不确定结果)];第2位：证件号码进行唯一性校验[2不校验，1校验(默认)];第3位：是否事实客户[2潜在客户(默认)，1事实客户]
																						     --          第4位:是否禁购客户[1是0否]
                                    @IN_ImageIdentification          IMAGE = NULL            --身份证图片，注：该参数留在最后，java方法中正常添加参数
WITH ENCRYPTION
AS
    DECLARE @V_USER_ID INT,@V_STATUS NVARCHAR(20),@V_STATUS_NAME NVARCHAR(40)
    DECLARE @V_CUST_ID INT,@V_CUST_NO NVARCHAR(8),@V_MAX_ID INT,@V_CARD_TYPE_NAME NVARCHAR(30), @V_ReceiveServices BIGINT,
            @V_CUST_ID2 INT, @V_MAX_ID2 INT,@V_GOV_PROV_REGIONAL_NAME NVARCHAR(30),@V_GOV_REGIONAL_NAME NVARCHAR(30)
    DECLARE @V_CUST_TYPE_NAME NVARCHAR(10),@V_CUST_LEVEL NVARCHAR(10),@V_SEX_NAME NVARCHAR(10), @V_PinYinSimple NVARCHAR(100)
    DECLARE @V_VOC_TYPE_NAME NVARCHAR(30),@V_CUST_LEVEL_NAME NVARCHAR(30),@V_TOUCH_TYPE_NAME NVARCHAR(30),@V_CUST_SOURCE_NAME NVARCHAR(30)
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT, @SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)

    DECLARE @V_CUST_AUM_NAME NVARCHAR(30),@V_CUST_AGE_GROUP_NAME NVARCHAR(30)
    DECLARE @V_TRUE_FLAG INT,@V_IS_DEAL INT

    DECLARE @V_OTHER_INVE_STATUS_NAME NVARCHAR(60),@V_TYPE_PREF_NAME NVARCHAR(60)
    DECLARE @V_TIME_LIMIT_PREF_NAME NVARCHAR(60),@V_INVEST_PREF_NAME NVARCHAR(60)
    DECLARE @V_HOBBY_PREF_NAME NVARCHAR(60),@V_SERVICE_PREF_NAME NVARCHAR(60)
    DECLARE @V_CONTACT_PREF_NAME NVARCHAR(60),@V_NATION_NAME NVARCHAR(60)
    DECLARE @V_HEALTH_NAME NVARCHAR(60),@V_EDUCATION_NAME NVARCHAR(60)
    DECLARE @V_COMPANY_CHARACTER_NAME NVARCHAR(60),@V_CLIENT_QUALE_NAME NVARCHAR(60)
    DECLARE @V_RISK_PREF_NAME NVARCHAR(60),@V_EAST_JG_TYPE_NAME NVARCHAR(30)

    SELECT @V_RET_CODE = -20601000, @IBUSI_FLAG = 20601
    SELECT @SBUSI_NAME = N'增加客户基本信息', @SSUMMARY = N'增加客户基本信息'
    SELECT @IN_CUST_FLAG=ISNULL(@IN_CUST_FLAG,'')
    SELECT @V_TRUE_FLAG=SUBSTRING(@IN_CUST_FLAG,1,1) --取@IN_CUST_FLAG的第一位:客户的真实性核查
    IF @V_TRUE_FLAG=0 SET @V_TRUE_FLAG=1 --当没有输入项时，用默认值
    --SELECT @V_CARD_ID_CHECK=SUBSTRING(@IN_CUST_FLAG,2,1) --取@IN_CUST_FLAG的第二位:证件号码进行唯一性校验
    --IF @V_CARD_ID_CHECK=0 SET @V_CARD_ID_CHECK=1 --当没有输入项时，用默认值
    SELECT @V_IS_DEAL=SUBSTRING(@IN_CUST_FLAG,3,1) --取@IN_CUST_FLAG的第三位:是否事实客户[2潜在客户(默认)，1事实客户]
    IF @V_IS_DEAL=0 SET @V_IS_DEAL=2 --当没有输入项时，用默认值
    IF @V_USER_ID=21 SET @V_IS_DEAL=1/*金汇信托要求，新建客户时一律为潜在客户*/
    IF ISNULL(SUBSTRING(@IN_CUST_FLAG,4,1),'')='1' --判断第4位:是否禁购客户[1是0否]
    BEGIN
		SET @V_STATUS='112806'
		SET @V_STATUS_NAME='禁购'
	END
	ELSE
	BEGIN
		SET @V_STATUS='112801'
		SET @V_STATUS_NAME='新增'
	END

    DECLARE @V_BOOK_CODE INT, @V_INTRUST_OPERATOR INT
    DECLARE @RET INT, @V_DT_INTRUST INT
    SELECT @V_DT_INTRUST = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'DT_INTRUST'
    SELECT @V_ReceiveServices = SUM(ServiceType) FROM TServiceDefine

    BEGIN TRY
    
    SELECT @V_USER_ID=USER_ID FROM TSYSTEMINFO
    IF @V_USER_ID=15 --建信信托
		RAISERROR('客户新建，不在CRM中进行，请到业务系统中操作',16,1)
    SELECT @OUT_CUST_ID = 0
    IF EXISTS(SELECT * FROM TSYSCONTROL WHERE FLAG_TYPE = N'A_CARDID' AND VALUE = 1)
    BEGIN
        IF @IN_CARD_TYPE = N'110801' AND LEN(@IN_CARD_ID)=15   --15位身份证号码转18位
            SELECT @IN_CARD_ID = dbo.IDCARD15TO18(@IN_CARD_ID)
    END

    IF @IN_CUST_TYPE = 1
        BEGIN
            SELECT @V_CUST_TYPE_NAME = N'个人'

            IF @IN_CARD_TYPE IS NULL OR @IN_CARD_TYPE = N''
                SELECT @IN_CARD_TYPE = N'110899', @V_CARD_TYPE_NAME = N'其它'
            ELSE IF NOT EXISTS(SELECT 1 FROM TDICTPARAM WHERE TYPE_ID = 1108 AND TYPE_VALUE = @IN_CARD_TYPE)
                SELECT @IN_CARD_TYPE = N'110899', @V_CARD_TYPE_NAME = N'其它'
            ELSE
                SELECT @V_CARD_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_ID = 1108 AND TYPE_VALUE = @IN_CARD_TYPE
        END
    ELSE
        BEGIN
            SELECT @V_CUST_TYPE_NAME = N'机构'

            IF @IN_CARD_TYPE IS NULL OR @IN_CARD_TYPE = N''
                SELECT @IN_CARD_TYPE = N'210809', @V_CARD_TYPE_NAME = N'其他类机构代码'
            ELSE IF NOT EXISTS(SELECT 1 FROM TDICTPARAM WHERE TYPE_ID = 2108 AND TYPE_VALUE = @IN_CARD_TYPE)
                SELECT @IN_CARD_TYPE = N'210809', @V_CARD_TYPE_NAME = N'其他类机构代码'
            ELSE
                SELECT @V_CARD_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_ID = 2108 AND TYPE_VALUE = @IN_CARD_TYPE
        END

    IF (@IN_CARD_TYPE IS NOT NULL AND @IN_CARD_TYPE  <> '') AND (@IN_CARD_ID IS NOT NULL AND @IN_CARD_ID  <> '')
    BEGIN
        IF EXISTS(SELECT * FROM TCustomers WHERE CUST_NAME = @IN_CUST_NAME AND CARD_TYPE = @IN_CARD_TYPE AND CARD_ID = @IN_CARD_ID AND STATUS <>'112805')
           RETURN @V_RET_CODE-2   -- 客户已存在

        IF EXISTS (SELECT * FROM TCustomers WHERE CARD_TYPE = @IN_CARD_TYPE AND CARD_ID = @IN_CARD_ID AND STATUS <>'112805' )
           RETURN @V_RET_CODE-3  --该身份证号已存在
    END
    ELSE
    BEGIN
        SELECT @IN_CARD_ID = '999999'
    END

    SELECT @IN_COUNTRY = ISNULL(@IN_COUNTRY,'9997CHN')   --默认中国 add by luohh 200908

    SELECT @V_CARD_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_CARD_TYPE
    IF @IN_SEX = 1
       SELECT @V_SEX_NAME  =N'男'
    ELSE IF @IN_SEX = 2
       SELECT @V_SEX_NAME  =N'女'
    ELSE
        SELECT @V_SEX_NAME  =N''


    SELECT @V_CUST_LEVEL = N'111101'
    SELECT @V_CUST_LEVEL_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @V_CUST_LEVEL
    IF ISNULL(@IN_TOUCH_TYPE,'') = N'' SELECT @IN_TOUCH_TYPE = N'110901'
    SELECT @V_TOUCH_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_TOUCH_TYPE
    IF ISNULL(@IN_CUST_SOURCE,'') = N'' SELECT @IN_CUST_SOURCE = N'111004'
    SELECT @V_CUST_SOURCE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_CUST_SOURCE

    SELECT @V_CUST_AUM_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_CUST_AUM
    SELECT @V_CUST_AGE_GROUP_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_CUST_AGE_GROUP
    SELECT @V_OTHER_INVE_STATUS_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_OTHER_INVESTMENT_STATUS
    SELECT @V_NATION_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_NATION
    SELECT @V_HEALTH_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_HEALTH
    SELECT @V_EDUCATION_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_EDUCATION
    SELECT @V_COMPANY_CHARACTER_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_COMPANY_CHARACTER
    SELECT @V_CLIENT_QUALE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_CLIENT_QUALE

    --获得客户偏好信息
    SELECT @V_TYPE_PREF_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_TYPE_PREF
    SELECT @V_TIME_LIMIT_PREF_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_TIME_LIMIT_PREF
    SELECT @V_INVEST_PREF_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_INVEST_PREF
    --SELECT @V_HOBBY_PREF_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_HOBBY_PREF
    SELECT @V_SERVICE_PREF_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_SERVICE_PREF
    SELECT @V_CONTACT_PREF_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_CONTACT_PREF
    SELECT @V_RISK_PREF_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_RISK_PREF

    --获得行政区域
    SELECT @V_GOV_PROV_REGIONAL_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_GOV_PROV_REGIONAL
    SELECT @V_GOV_REGIONAL_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_GOV_REGIONAL

    --生成CUST_ID
   BEGIN
        SELECT @V_CUST_ID2 = ISNULL(MAX(CUST_ID),0)+ 1 FROM INTRUST..TCUSTOMERINFO
        SELECT @V_MAX_ID2 = ISNULL(MAX(LIST_ID),0)+ 1 FROM INTRUST..TCUSTOMERINFO
    END
    SELECT @V_CUST_ID = ISNULL(MAX(CUST_ID),0)+ 1 FROM TCustomers
    SELECT @V_MAX_ID = ISNULL(MAX(LIST_ID),0)+ 1 FROM TCustomers
    IF @V_CUST_ID2 > @V_CUST_ID
        SET @V_CUST_ID = @V_CUST_ID2
    IF @V_MAX_ID2 > @V_MAX_ID
        SET @V_MAX_ID = @V_MAX_ID2
    --客户编号
    IF @IN_CUST_NO IS NULL OR @IN_CUST_NO = N''
    BEGIN
        SELECT @V_CUST_NO = CONVERT(CHAR(8),@IN_CUST_TYPE * 10000000+@V_MAX_ID)
        --20050112 dongyg 个人客户编号以0开头，机构以J开头
        IF @IN_CUST_TYPE = 1
            SELECT @V_CUST_NO = N'0' + RIGHT(@V_CUST_NO,7)
        ELSE
            SELECT @V_CUST_NO = N'J' + RIGHT(@V_CUST_NO,7)
    END
    ELSE
    BEGIN
        IF EXISTS(SELECT * FROM TCustomers WHERE CUST_NO = @IN_CUST_NO)
            RETURN @V_RET_CODE - 1  --客户编号已存在
        SELECT @V_CUST_NO = @IN_CUST_NO
    END
    --如果是个人客户，证件类型是身份证，且未输入生日时，根据15位和18位的情况生成生日变量
    IF @IN_CUST_TYPE = 1 AND (@IN_BIRTHDAY IS NULL OR @IN_BIRTHDAY = 0) AND @IN_CARD_TYPE = N'110801'
    BEGIN
        IF LEN(@IN_CARD_ID)=15
        BEGIN
            SELECT @IN_BIRTHDAY = CONVERT(INT,'19'+SUBSTRING(@IN_CARD_ID,7,6))
        END
        ELSE IF LEN(@IN_CARD_ID) = 18
        BEGIN
            SELECT @IN_BIRTHDAY = CONVERT(INT,SUBSTRING(@IN_CARD_ID,7,8))
        END
    END
    IF @IN_SERVICE_MAN IS NULL OR @IN_SERVICE_MAN = 0 SELECT @IN_SERVICE_MAN = @IN_INPUT_MAN
    SELECT @V_VOC_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_VOC_TYPE
    --查找机构类型名称
    select @V_EAST_JG_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_EAST_JG_TYPE

    SET @V_PinYinSimple = dbo.fGetPy(@IN_CUST_NAME)
    --

    BEGIN TRANSACTION
    IF @V_INTRUST_OPERATOR IS NULL --从sso映射表中未取到Intrust操作员时，使用CRM操作员
        SELECT @V_INTRUST_OPERATOR = @IN_INPUT_MAN
    IF @V_BOOK_CODE IS NULL --最后取不到使用默认值1
        SELECT @V_BOOK_CODE = 1

    INSERT INTO TCustomers(ReceiveServices, --添加客户时，默认接收所有服务
          CUST_ID,CUST_NO,CUST_NAME,CUST_TEL,POST_ADDRESS,POST_ADDRESS2,
          CARD_TYPE,CARD_TYPE_NAME,CARD_ID,AGE,SEX,SEX_NAME,POST_CODE2,
          O_TEL,H_TEL,MOBILE,BP,FAX,E_MAIL,CUST_TYPE,CUST_TYPE_NAME,
          WT_FLAG,WT_FLAG_NAME,PASSWORD,
          TOUCH_TYPE,TOUCH_TYPE_NAME,CUST_SOURCE,CUST_SOURCE_NAME,
          SUMMARY,INPUT_MAN,POST_CODE,CHECK_FLAG,LIST_ID, LEGAL_MAN, LEGAL_ADDRESS, BIRTHDAY,
          PRINT_DEPLOY_BILL, PRINT_POST_INFO, IS_LINK, SERVICE_MAN,VIP_CARD_ID,VIP_DATE,HGTZR_BH,
          VOC_TYPE,VOC_TYPE_NAME,CARD_VALID_DATE,COUNTRY,JG_CUST_TYPE, CONTACT_MAN,MONEY_SOURCE,MONEY_SOURCE_NAME,FACT_CONTROLLER,
          ImageIdentification,PinYinSimple,POTENTIAL_MONEY,IS_DEAL,GOV_PROV_REGIONAL,GOV_PROV_REGIONAL_NAME,GOV_REGIONAL,
          GOV_REGIONAL_NAME,RECOMMENDED,COMPLETE_FLAG,EAST_JG_TYPE,EAST_JG_TYPE_NAME,JG_WTRLX2,TRUE_FLAG,
          STATUS,STATUS_NAME)
    VALUES(@V_ReceiveServices,
          @V_CUST_ID,@V_CUST_NO,@IN_CUST_NAME,@IN_CUST_TEL,@IN_POST_ADDRESS,@IN_POST_ADDRESS2,
          @IN_CARD_TYPE,@V_CARD_TYPE_NAME,@IN_CARD_ID,@IN_AGE,@IN_SEX,@V_SEX_NAME,@IN_POST_CODE2,
          @IN_O_TEL,@IN_H_TEL,@IN_MOBILE,@IN_BP,@IN_FAX,@IN_E_MAIL,@IN_CUST_TYPE,@V_CUST_TYPE_NAME,
          2,'否','000000',
          @IN_TOUCH_TYPE,@V_TOUCH_TYPE_NAME,@IN_CUST_SOURCE,@V_CUST_SOURCE_NAME,
          @IN_SUMMARY,@IN_INPUT_MAN,@IN_POST_CODE,2,@V_MAX_ID, @IN_LEGAL_MAN, @IN_LEGAL_ADDRESS, @IN_BIRTHDAY,
          @IN_PRINT_DEPLOY_BILL, @IN_PRINT_POST_INFO, @IN_IS_LINK, @IN_SERVICE_MAN,@IN_VIP_CARD_ID,@IN_VIP_DATE,@IN_HGTZR_BH,
          @IN_VOC_TYPE,@V_VOC_TYPE_NAME,@IN_CARD_VALID_DATE,@IN_COUNTRY,@IN_JG_CUST_TYPE, @IN_CONTACT_MAN,@IN_MONEY_SOURCE,@IN_MONEY_SOURCE_NAME,@IN_FACT_CONTROLLER,
          @IN_ImageIdentification,@V_PinYinSimple,@IN_POTENTIAL_MONEY,@V_IS_DEAL,@IN_GOV_PROV_REGIONAL,@V_GOV_PROV_REGIONAL_NAME,
          @IN_GOV_REGIONAL,@V_GOV_REGIONAL_NAME,@IN_RECOMMENDED,@IN_COMPLETE_FLAG,@IN_EAST_JG_TYPE,@V_VOC_TYPE_NAME,@IN_JG_WTRLX2,@V_TRUE_FLAG,
          @V_STATUS,@V_STATUS_NAME)

    UPDATE TCUSTOMERS
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
            COMPANY_DEPART    = @IN_COMPANY_DEPART,
            COMPANY_POSITION = @IN_COMPANY_POSITION,
            CUST_AUM = @IN_CUST_AUM,
            CUST_AUM_NAME = @V_CUST_AUM_NAME,
            CUST_AGE_GROUP = @IN_CUST_AGE_GROUP,
            CUST_AGE_GROUP_NAME = @V_CUST_AGE_GROUP_NAME,
            INVES_EXPERINCE = @IN_INVES_EXPERINCE,
            CUST_SOURCE_EXPLAIN = @IN_CUST_SOURCE_EXPLAIN
        WHERE CUST_ID = @V_CUST_ID

    INSERT INTO TCUSTSOURCEINFO(CUST_ID) SELECT CUST_ID FROM TCUSTOMERS WHERE CUST_ID = @V_CUST_ID
    
    --新增的客户是非交易客户
    INSERT INTO TCustomerClass(CUST_ID,CLASSDEFINE_ID,CLASSDEFINE_NAME,CLASSDETAIL_ID,CLASSDETAIL_NAME,INSERTMAN,CHECK_FLAG)
        VALUES(@V_CUST_ID,10,'是否认购',1002,'非交易客户',@IN_INPUT_MAN,2)
    
    --初始化客户积分卡
    IF EXISTS(SELECT 1 FROM sysobjects WHERE NAME = 'IM_CUST_INTEGRAL_CARD') AND NOT EXISTS(SELECT 1 FROM IM_CUST_INTEGRAL_CARD WHERE CUST_ID = @V_CUST_ID)
    BEGIN
        INSERT INTO IM_CUST_INTEGRAL_CARD (CUST_ID,CUST_NAME,CUST_LEVEL_ID,BASE_INTEGRAL,RULE_INTEGAL,TOTAL_INTEGAL,EXCHANGED_INTEGAL,BALANCE)
        SELECT CUST_ID,CUST_NAME,1,0,0,0,0,0
            FROM TCustomers WHERE CUST_ID = @V_CUST_ID
    END
    --客户证件信息表
    IF EXISTS(SELECT 1 FROM TCUSTCARDINFO WHERE CUST_ID = @V_CUST_ID AND CARD_TYPE = @IN_CARD_TYPE AND CARD_ID = @IN_CARD_ID)
    BEGIN
        UPDATE TCUSTCARDINFO
            SET CARD_CUST_NAME = @IN_CUST_NAME,
                CARD_TYPE      = @IN_CARD_TYPE,
                CARD_TYPE_NAME = @V_CARD_TYPE_NAME,
                CARD_ID        = @IN_CARD_ID,
                VALID_DATE     = @IN_CARD_VALID_DATE,
                INPUT_TIME     = GETDATE(),
                IMAGE1         = @IN_ImageIdentification
            WHERE CUST_ID = @V_CUST_ID AND CARD_TYPE = @IN_CARD_TYPE AND CARD_ID = @IN_CARD_ID
    END
    ELSE
    BEGIN
        INSERT INTO TCUSTCARDINFO(CUST_ID,CARD_CUST_NAME,CUST_TYPE,CARD_TYPE,CARD_TYPE_NAME,CARD_ID,VALID_DATE,INPUT_TIME,IMAGE1)
            VALUES(@V_CUST_ID,@IN_CUST_NAME,@IN_CUST_TYPE,@IN_CARD_TYPE,@V_CARD_TYPE_NAME,@IN_CARD_ID,@IN_CARD_VALID_DATE,GETDATE(),@IN_ImageIdentification)
    END
    
    SELECT @SSUMMARY = N'增加客户基本信息，客户名称：' + @IN_CUST_NAME
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
    SELECT @OUT_CUST_ID = @V_CUST_ID

    COMMIT TRANSACTION
    SET XACT_ABORT OFF
        RETURN 100
    END TRY
    
    --异常处理
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
