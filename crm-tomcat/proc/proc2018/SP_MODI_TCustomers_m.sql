USE [EFCRM]
GO

/****** Object:  StoredProcedure [dbo].[SP_MODI_TCustomers]    Script Date: 2018/5/25 12:00:39 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[SP_MODI_TCustomers_m] @IN_CUST_ID                         INT,
                                    @IN_CUST_NAME                       NVARCHAR(100),
                                    @IN_CUST_TEL                        NVARCHAR(20),
                                    @IN_POST_ADDRESS                    NVARCHAR(60),
                                    @IN_POST_CODE                       NVARCHAR(6),
                                    @IN_CARD_TYPE                       NVARCHAR(10),
                                    @IN_CARD_ID                         NVARCHAR(40),
                                    @IN_AGE                             INT,
                                    @IN_SEX                             INT,
                                    @IN_O_TEL                           NVARCHAR(20),
                                    @IN_H_TEL                           NVARCHAR(20),
                                    @IN_MOBILE                          NVARCHAR(100),
                                    @IN_BP                              NVARCHAR(60),
                                    @IN_FAX                             NVARCHAR(20),
                                    @IN_E_MAIL                          NVARCHAR(30),
                                    @IN_CUST_TYPE                       INT,
                                    @IN_TOUCH_TYPE                      NVARCHAR(10),
                                    @IN_CUST_SOURCE                     NVARCHAR(10),
                                    @IN_SUMMARY                         NVARCHAR(200),
                                    @IN_INPUT_MAN                       INT,
                                    @IN_CUST_NO                         NVARCHAR(8),
                                    @IN_LEGAL_MAN                       NVARCHAR(20),
                                    @IN_LEGAL_ADDRESS                   NVARCHAR(60),
                                    @IN_BIRTHDAY                        INT,
                                    @IN_POST_ADDRESS2                   NVARCHAR(60),
                                    @IN_POST_CODE2                      NVARCHAR(6),
                                    @IN_PRINT_DEPLOY_BILL               INT,
                                    @IN_PRINT_POST_INFO                 INT,
                                    @IN_IS_LINK                         INT,
                                    @IN_SERVICE_MAN                     INT = NULL,
                                    @IN_VIP_CARD_ID                     NVARCHAR(20) = NULL,   -- VIP??????
                                    @IN_VIP_DATE                        INT = NULL,            -- VIP????????
                                    @IN_HGTZR_BH                        NVARCHAR(20) = NULL,   -- ??????????????
                                    @IN_VOC_TYPE                        NVARCHAR(10) = N'',    -- ????????/????????????(1142/2142)
                                    @IN_CARD_VALID_DATE                 INT = NULL,            --????????????????????8??????????
                                    @IN_COUNTRY                         NVARCHAR(10) = NULL,   --????????(9997)
                                    @IN_JG_CUST_TYPE                    NVARCHAR(10) = NULL,   --????????????(9921)??????CUST_TYPE=2??????????
                                    @IN_CONTACT_MAN                     NVARCHAR(30) = NULL,   --??????????????
                                    @IN_MONEY_SOURCE                    NVARCHAR(300) = NULL,  --????????
                                    @IN_MONEY_SOURCE_NAME               NVARCHAR(1000) = NULL, --????????????
                                    @IN_FACT_CONTROLLER                 NVARCHAR(100) = NULL,  --??????????WITH ENCRYPTION
                                    @IN_POTENTIAL_MONEY                 DECIMAL(16,3),         -- ????????
                                    @IN_GOV_PROV_REGIONAL               NVARCHAR(10),          --????????????(9999)
                                    @IN_GOV_REGIONAL                    NVARCHAR(10),          --????????(9999)
                                    @IN_RECOMMENDED                     NVARCHAR(30),          --??????
                                    @IN_PERSONAL_INCOME                 DEC(16,3),             --??????????
                                    @IN_HOUSEHOLD_INCOME                DEC(16,3),             --??????????
                                    @IN_FEEDING_NUM_PEOPLE              INT,                   --????????
                                    @IN_MAIN_SOURCE                     NVARCHAR(160),         --????????????
                                    @IN_OTHER_SOURCE                    NVARCHAR(160),         --????????????
                                    @IN_HOUSE_POSITION                  NVARCHAR(120),         --????????
                                    @IN_HOUSE_PROPERTY                  INT,                   --??????1????2????
                                    @IN_HOUSE_AREA                      INT,                   --????
                                    @IN_PLAT_ENVALUATE                  NVARCHAR(200),         --????????
                                    @IN_MARKET_APPRAISAL                DEC(16,3),             --????????
                                    @IN_VEHICLE_NUM                     INT,                   --????????
                                    @IN_VEHICLE_MAKE                    NVARCHAR(100),         --????
                                    @IN_VEHICLE_TYPE                    NVARCHAR(100),         --????
                                    @IN_CREDIT_TYPE                     NVARCHAR(60),          --????????
                                    @IN_CREDIT_NUM                      INT,                   --????????
                                    @IN_CREDIT_START_DATE               INT,                   --??????
                                    @IN_CREDIT_END_DATE                 INT,                   --??????
                                    @IN_OTHER_INVESTMENT_STATUS         NVARCHAR(20),          --????????????(1165)
                                    @IN_TYPE_PREF                       NVARCHAR(20),          --????????(1166)
                                    @IN_TIME_LIMIT_PREF                 NVARCHAR(20),          --????????(1170)
                                    @IN_INVEST_PREF                     NVARCHAR(20),          --????????(1167)
                                    @IN_HOBBY_PREF                      NVARCHAR(60),          --????????(1168)
                                    @IN_SERVICE_PREF                    NVARCHAR(20),          --????????(1169)
                                    @IN_CONTACT_PREF                    NVARCHAR(20),          --????????????(1109)
                                    @IN_HEAD_OFFICE_CUST_ID             NVARCHAR(60),          --??????????
                                    @IN_STATURE                         INT,                   --????
                                    @IN_WEIGHT                          INT,                   --????
                                    @IN_SPOUSE_NAME                     NVARCHAR(40),          --????????
                                    @IN_SPOUSE_INFO                     NVARCHAR(300),         --????????
                                    @IN_CHILDREN_NAME                   NVARCHAR(100),         --????????
                                    @IN_CHILDREN_INFO                   NVARCHAR(300),         --????????
                                    @IN_NATION                          NVARCHAR(20),          --????(1161)
                                    @IN_MARITAL                         INT,                   --????????(1????0??)
                                    @IN_HEALTH                          NVARCHAR(20),          --????????(1162)
                                    @IN_EDUCATION                       NVARCHAR(20),          --????????(1163)
                                    @IN_POST                            NVARCHAR(120),         --????
                                    @IN_HOLDEROFANOFFICE                NVARCHAR(120),         --????
                                    @IN_COMPANY_CHARACTER               NVARCHAR(20),          --????????(1164)
                                    @IN_COMPANY_STAFF                   INT,                   --????????????????1????0??
                                    @IN_BOCOM_STAFF                     INT,                   --????????????????1????0??
                                    @IN_CLIENT_QUALE                    NVARCHAR(20),          --????????(????)(1164)
                                    @IN_REGISTERED_PLACE                NVARCHAR(120),         --??????(????)
                                    @IN_REGISTERED_CAPITAL              DEC(16,3),             --????????(????)
                                    @IN_EMPLOYEE_NUM                    INT,                   --??????????????
                                    @IN_HOLDING                         NVARCHAR(400),         --??????????????(????)
                                    @IN_RISK_PREF                       NVARCHAR(20),          --??????????1172??
                                    @IN_FREE_CASH_FLOW                  DEC(16,3),             --??????????
                                    @IN_BUREND_OF_DEBT                  DEC(16,3),             --????????
                                    @IN_COMPLETE_FLAG                   INTEGER         = 2,    --???????? 1???? 2?????? ????2
                                    @IN_COMPANY_UNIT                    NVARCHAR(60)='',       --????????
                                    @IN_COMPANY_DEPART                  NVARCHAR(30)='',       --????????
                                    @IN_COMPANY_POSITION                NVARCHAR(60)='',       --????????
                                    @IN_CUST_AUM                        NVARCHAR(10)='',       --????AUM
                                    @IN_CUST_AGE_GROUP                  NVARCHAR(10)='',       --??????
                                    @IN_INVES_EXPERINCE                 NVARCHAR(10)='',       --??????
                                    @IN_CUST_SOURCE_EXPLAIN             NVARCHAR(60)='',       --????????????
                                    @IN_EAST_JG_TYPE                    NVARCHAR(12)='',       --????????2109??????EAST??????????WTRLX??
                                    @IN_JG_WTRLX2                       NVARCHAR(2)='',        --??????????1?????????? 2???? 3???????????? 4??????????????
                                    @IN_CUST_FLAG                       NVARCHAR(20) ='',       --????????????1????????????????????[1??????(????)??2??????3??????(??????????????????????)];??2??????????????????????????[2????????1????(????)];??3????????????????[2????????(????)??1????????]
																			    			    --          ??4??:????????????[1??0??]
                                    @IN_ImageIdentification             IMAGE = NULL           --????????????????????????????????java??????????????????
AS
    SET NOCOUNT ON
    SET XACT_ABORT ON

    DECLARE @V_BOOK_CODE INT, @V_INTRUST_OPERATOR INT
    DECLARE @RET INT, @V_DT_INTRUST INT
    DECLARE @V_IS_DEAL INT,@V_TRUE_FLAG INT
    SELECT @V_DT_INTRUST = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'DT_INTRUST'
    
    DECLARE @V_STATUS NVARCHAR(10),@V_STATUS_NAME NVARCHAR(30),@OLD_STATUS NVARCHAR(10),@V_CHECK_FLAG INT, @V_TEMP1 INT,@OLD_CARD_VALID_DATE INT
    DECLARE @V_VOC_TYPE_NAME NVARCHAR(30),@V_CARD_TYPE_NAME NVARCHAR(30),@V_TOUCH_TYPE_NAME NVARCHAR(30),@V_CUST_SOURCE_NAME NVARCHAR(30)
    DECLARE @V_CUST_TYPE_NAME NVARCHAR(10),@V_SEX_NAME NVARCHAR(10), @V_PinYinSimple NVARCHAR(100)
    DECLARE @V_GOV_PROV_REGIONAL_NAME NVARCHAR(30),@V_GOV_REGIONAL_NAME NVARCHAR(30)--????????
    DECLARE @OLD_CUST_NAME NVARCHAR(100), @OLD_CUST_NO NVARCHAR(8), @OLD_IS_LINK INT
    DECLARE @OLD_CUST_TEL NVARCHAR(20),@OLD_O_TEL NVARCHAR(20),@OLD_H_TEL NVARCHAR(20),@OLD_MOBILE NVARCHAR(20),@OLD_BP NVARCHAR(60),@OLD_FAX NVARCHAR(20)
    DECLARE @OLD_POST_ADDRESS NVARCHAR(60), @OLD_POST_ADDRESS2 NVARCHAR(60)
    DECLARE @OLD_POST_CODE NVARCHAR(6), @OLD_POST_CODE2 NVARCHAR(6), @OLD_SERVICE_MAN INT
    DECLARE @OLD_CARD_TYPE NVARCHAR(10),@OLD_CUST_TYPE_NAME NVARCHAR(10),@OLD_CUST_SOURCE NVARCHAR(10),@OLD_TOUCH_TYPE NVARCHAR(10)
    DECLARE @OLD_VOC_TYPE_NAME NVARCHAR(30),@OLD_CARD_TYPE_NAME NVARCHAR(30),@OLD_EMAIL NVARCHAR(30),@OLD_CUST_SOURCE_NAME NVARCHAR(30),@OLD_TOUCH_TYPE_NAME NVARCHAR(30)
    DECLARE @OLD_CARD_ID NVARCHAR(40), @OLD_LEGAL_MAN NVARCHAR(20), @OLD_LEGAL_ADDRESS NVARCHAR(60), @V_CUST_LEVEL NVARCHAR(10)
    DECLARE @OLD_SEX INT,@OLD_CUST_TYPE INT, @OLD_BIRTHDAY INT, @V_LIST_ID INT
    DECLARE @OLD_SEX_NAME NVARCHAR(2), @OLD_PRINT_DEPLOY_BILL INT, @OLD_PRINT_POST_INFO INT
    DECLARE @OLD_SUMMARY NVARCHAR(200), @OLD_AGE INT, @OLD_VOC_TYPE NVARCHAR(10), @OLD_JG_CUST_TYPE NVARCHAR(10)
    DECLARE @OLD_MONEY_SOURCE NVARCHAR(600),@OLD_MONEY_SOURCE_NAME NVARCHAR(1000)
    DECLARE @OLD_VIP_CARD_ID NVARCHAR(20),@OLD_VIP_DATE INT,@OLD_HGTZR_BH NVARCHAR(20)
    DECLARE @OLD_COMPANY_UNIT NVARCHAR(60),@OLD_COMPANY_DEPART NVARCHAR(30),@OLD_COMPANY_POSITION NVARCHAR(60)
    DECLARE @OLD_EAST_JG_TYPE NVARCHAR(10),@OLD_EAST_JG_TYPE_NAME NVARCHAR(60),@OLD_JG_WTRLX2 NVARCHAR(2),@V_WTRLX NVARCHAR(2)
    DECLARE @OLD_TRUE_FLAG INT,@OLD_CONTACT_MAN NVARCHAR(100)
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@V_MODI_CHECK_FLAG INT
    DECLARE @SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_CUST_AUM_NAME NVARCHAR(30),@V_CUST_AGE_GROUP_NAME NVARCHAR(30)
    DECLARE @V_OTHER_INVE_STATUS_NAME NVARCHAR(60),@V_TYPE_PREF_NAME NVARCHAR(60)
    DECLARE @V_TIME_LIMIT_PREF_NAME NVARCHAR(60),@V_INVEST_PREF_NAME NVARCHAR(60)
    DECLARE @V_HOBBY_PREF_NAME NVARCHAR(60),@V_SERVICE_PREF_NAME NVARCHAR(60)
    DECLARE @V_CONTACT_PREF_NAME NVARCHAR(60),@V_NATION_NAME NVARCHAR(60)
    DECLARE @V_HEALTH_NAME NVARCHAR(60),@V_EDUCATION_NAME NVARCHAR(60)
    DECLARE @V_COMPANY_CHARACTER_NAME NVARCHAR(60),@V_CLIENT_QUALE_NAME NVARCHAR(60)
    DECLARE @V_RISK_PREF_NAME NVARCHAR(60),@V_EAST_JG_TYPE_NAME NVARCHAR(100)
    

    SELECT @V_RET_CODE = -20602000
    SELECT @IBUSI_FLAG = 20602
    SELECT @SBUSI_NAME = N'????????????????'
    SELECT @SSUMMARY = N'????????????????'

    DECLARE @V_USER_ID INT
	SELECT @V_USER_ID=USER_ID FROM TSYSTEMINFO 
    IF @V_USER_ID=15/*????????????????*/
		RETURN
    IF NOT EXISTS(SELECT 1 FROM TCustomers WHERE CUST_ID = @IN_CUST_ID)
       RETURN @V_RET_CODE-1   -- ??????????????

    SELECT @IN_CUST_FLAG=ISNULL(@IN_CUST_FLAG,'')
    SELECT @V_TRUE_FLAG=SUBSTRING(@IN_CUST_FLAG,1,1) --??@IN_CUST_FLAG????????:????????????????
    IF @V_TRUE_FLAG=0 SET @V_TRUE_FLAG=1 --????????????????????????
    --SELECT @V_CARD_ID_CHECK=SUBSTRING(@IN_CUST_FLAG,2,1) --??@IN_CUST_FLAG????????:??????????????????????
    --IF @V_CARD_ID_CHECK=0 SET @V_CARD_ID_CHECK=1 --????????????????????????
    SELECT @V_IS_DEAL=SUBSTRING(@IN_CUST_FLAG,3,1) --??@IN_CUST_FLAG????????:????????????[2????????(????)??1????????]
    IF @V_IS_DEAL=0 SET @V_IS_DEAL=2 --????????????????????????
    IF ISNULL(SUBSTRING(@IN_CUST_FLAG,4,1),'')='1' --??????4??:????????????[1??0??]
    BEGIN
		SET @V_STATUS='112806'
		SET @V_STATUS_NAME='????'
	END
	ELSE
	BEGIN
		SET @V_STATUS='112801'
		SET @V_STATUS_NAME='????'
	END
    IF EXISTS(SELECT 1 FROM TSYSCONTROL WHERE FLAG_TYPE = N'A_CARDID' AND VALUE = 1)
    BEGIN
        IF @IN_CARD_TYPE = N'110801' AND LEN(@IN_CARD_ID)=15   --15??????????????18??
            SELECT @IN_CARD_ID = dbo.IDCARD15TO18(@IN_CARD_ID)
    END
	IF @V_USER_ID<>15/*??????????????????????*/ AND EXISTS ( SELECT 1 FROM TCustomers WHERE CARD_TYPE = @IN_CARD_TYPE AND CARD_ID = @IN_CARD_ID AND CUST_ID <> @IN_CUST_ID AND STATUS <> '112805' )
       RETURN @V_RET_CODE - 3 --????????????????

    SELECT @OLD_CUST_NAME = CUST_NAME,@OLD_CUST_TEL = CUST_TEL,@OLD_POST_ADDRESS = POST_ADDRESS,@OLD_POST_CODE = POST_CODE,
           @OLD_CARD_TYPE = CARD_TYPE,@OLD_CARD_TYPE_NAME = CARD_TYPE_NAME,@OLD_CARD_ID = CARD_ID,@OLD_SEX = SEX,@OLD_SEX_NAME = SEX_NAME,
           @OLD_O_TEL = O_TEL,@OLD_H_TEL = H_TEL,@OLD_MOBILE = MOBILE,@OLD_BP = BP,@OLD_FAX = FAX,@OLD_EMAIL = E_MAIL,
           @OLD_CUST_TYPE = CUST_TYPE,@OLD_CUST_TYPE_NAME = CUST_TYPE_NAME,@OLD_TOUCH_TYPE = TOUCH_TYPE,@OLD_TOUCH_TYPE_NAME = TOUCH_TYPE_NAME,
           @OLD_CUST_SOURCE = CUST_SOURCE,@OLD_CUST_SOURCE_NAME = CUST_SOURCE_NAME,@OLD_SUMMARY = SUMMARY, @OLD_CUST_NO = CUST_NO,
           @OLD_LEGAL_MAN = LEGAL_MAN, @OLD_LEGAL_ADDRESS = LEGAL_ADDRESS, @OLD_POST_ADDRESS2 = POST_ADDRESS2,@OLD_CARD_VALID_DATE=CARD_VALID_DATE,
           @OLD_POST_CODE2 = POST_CODE2, @V_LIST_ID = LIST_ID,@OLD_MONEY_SOURCE = MONEY_SOURCE,@OLD_MONEY_SOURCE_NAME = MONEY_SOURCE_NAME,
           @OLD_PRINT_DEPLOY_BILL = PRINT_DEPLOY_BILL, @OLD_PRINT_POST_INFO = PRINT_POST_INFO, @OLD_IS_LINK = IS_LINK,
           @OLD_SERVICE_MAN = SERVICE_MAN,@OLD_VIP_CARD_ID = VIP_CARD_ID,@OLD_VIP_DATE = VIP_DATE,@OLD_HGTZR_BH = HGTZR_BH,
           @OLD_VOC_TYPE_NAME = VOC_TYPE_NAME,@OLD_COMPANY_UNIT = COMPANY_UNIT,@OLD_COMPANY_DEPART = COMPANY_DEPART,
           @OLD_COMPANY_POSITION = COMPANY_POSITION,@OLD_EAST_JG_TYPE = EAST_JG_TYPE,@OLD_EAST_JG_TYPE_NAME = EAST_JG_TYPE_NAME,@OLD_JG_WTRLX2 = JG_WTRLX2,
           @OLD_BIRTHDAY=BIRTHDAY, @OLD_AGE=AGE, @OLD_VOC_TYPE=VOC_TYPE, @OLD_JG_CUST_TYPE=JG_CUST_TYPE,@OLD_TRUE_FLAG=TRUE_FLAG,
           @OLD_CONTACT_MAN=CONTACT_MAN,@OLD_STATUS=STATUS,@V_CHECK_FLAG = CHECK_FLAG
      FROM TCustomers WHERE CUST_ID = @IN_CUST_ID
	IF @OLD_STATUS = N'112805'
       RAISERROR('????????????????????',16,1)  -- ??????????????
	--????????????????????????????????????????????????????????????????????????????????????????
    IF EXISTS(SELECT 1 FROM TOPROLE WHERE OP_CODE = @IN_INPUT_MAN AND ROLE_ID IN(SELECT ROLE_ID FROM TROLE WHERE FLAG_ENCRYPTION = 1))
	BEGIN
		IF @OLD_SERVICE_MAN<>@IN_INPUT_MAN
			RAISERROR('??????????????????????????????',16,1)
	END
    IF @IN_SERVICE_MAN IS NULL OR @IN_SERVICE_MAN = 0 SELECT @IN_SERVICE_MAN = @OLD_SERVICE_MAN

    IF @IN_CUST_NO IS NULL OR @IN_CUST_NO = N''
        SELECT @IN_CUST_NO = @OLD_CUST_NO
    ELSE
    BEGIN
          IF EXISTS(SELECT 1 FROM TCustomers WHERE CUST_NO = @IN_CUST_NO AND CUST_ID <> @IN_CUST_ID)
            RETURN @V_RET_CODE - 5  --????????????????
    END
    IF @IN_CUST_TYPE = 1
       SELECT @V_CUST_TYPE_NAME = N'????'
    ELSE
       SELECT @V_CUST_TYPE_NAME = N'????'

    SELECT @V_CARD_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_CARD_TYPE
    IF @IN_SEX = 1
       SELECT @V_SEX_NAME = N'??'
    ELSE IF @IN_SEX = 2
       SELECT @V_SEX_NAME  =N'??'
    ELSE
        SELECT @V_SEX_NAME  =N''

    IF ISNULL(@IN_TOUCH_TYPE,'') = N'' SELECT @IN_TOUCH_TYPE = N'110901'
    SELECT @V_TOUCH_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_TOUCH_TYPE
    IF ISNULL(@IN_CUST_SOURCE,'') = N'' SELECT @IN_CUST_SOURCE = N'111004'
    SELECT @V_CUST_SOURCE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_CUST_SOURCE

    SELECT @V_VOC_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_VOC_TYPE
    SET @V_PinYinSimple = dbo.fGetPy(@IN_CUST_NAME)

    --????????????
    SELECT @V_GOV_PROV_REGIONAL_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_GOV_PROV_REGIONAL
    SELECT @V_GOV_REGIONAL_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_GOV_REGIONAL

    SELECT @V_OTHER_INVE_STATUS_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_OTHER_INVESTMENT_STATUS
    SELECT @V_NATION_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_NATION
    SELECT @V_HEALTH_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_HEALTH
    SELECT @V_EDUCATION_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_EDUCATION
    SELECT @V_COMPANY_CHARACTER_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_COMPANY_CHARACTER
    SELECT @V_CLIENT_QUALE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_CLIENT_QUALE

    SELECT @V_CUST_AUM_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_CUST_AUM
    SELECT @V_CUST_AGE_GROUP_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_CUST_AGE_GROUP

    --????????????????
    SELECT @V_TYPE_PREF_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_TYPE_PREF
    SELECT @V_TIME_LIMIT_PREF_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_TIME_LIMIT_PREF
    SELECT @V_INVEST_PREF_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_INVEST_PREF
    --SELECT @V_HOBBY_PREF_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_HOBBY_PREF
    SELECT @V_SERVICE_PREF_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_SERVICE_PREF
    SELECT @V_CONTACT_PREF_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_CONTACT_PREF
    SELECT @V_RISK_PREF_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_RISK_PREF
    --????????????????
    select @V_EAST_JG_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_EAST_JG_TYPE

    BEGIN TRY
    --??????????????
    BEGIN TRANSACTION
    IF @V_INTRUST_OPERATOR IS NULL --??sso??????????????Intrust??????????????CRM??????
        SELECT @V_INTRUST_OPERATOR = @IN_INPUT_MAN
    IF @V_BOOK_CODE IS NULL --????????????????????1
        SELECT @V_BOOK_CODE = 1
	--???????????????? 1?? 2??
	IF EXISTS (SELECT 1 FROM TSYSCONTROL WHERE FLAG_TYPE='CUSTINFO_CHANGE' AND VALUE=1)
		SET @V_MODI_CHECK_FLAG =1
	ELSE SET @V_MODI_CHECK_FLAG=0
    --------------------????TCustomerChanges??---------------------
    IF LTRIM(RTRIM(ISNULL(@OLD_CUST_NAME,''))) <> LTRIM(RTRIM(ISNULL(@IN_CUST_NAME,'')))
    BEGIN
        UPDATE TCustomerChanges SET NEW_FIELD_INFO=@IN_CUST_NAME,INPUT_MAN=@IN_INPUT_MAN,INPUT_TIME=GETDATE() WHERE CUST_ID=@IN_CUST_ID AND FIELD_NAME='CUST_NAME' AND CHECK_FLAG=1
        IF @@ROWCOUNT=0
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'CUST_NAME',N'????????',@OLD_CUST_NAME,@IN_CUST_NAME,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END


    IF @IN_CUST_TEL IS NULL OR @IN_CUST_TEL = ''
    SET @IN_CUST_TEL = @OLD_CUST_TEL
    
    IF LTRIM(RTRIM(ISNULL(@OLD_CUST_TEL,''))) <> LTRIM(RTRIM(ISNULL(@IN_CUST_TEL,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'CUST_TEL',N'????????',@OLD_CUST_TEL,@IN_CUST_TEL,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END
	
	IF LTRIM(RTRIM(ISNULL(@OLD_CARD_VALID_DATE,''))) <> LTRIM(RTRIM(ISNULL(@IN_CARD_VALID_DATE,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'CARD_VALID_DATE',N'??????????',@OLD_CARD_VALID_DATE,@IN_CARD_VALID_DATE,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF LTRIM(RTRIM(ISNULL(@OLD_POST_ADDRESS,''))) <> LTRIM(RTRIM(ISNULL(@IN_POST_ADDRESS,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'POST_ADDRESS',N'????????',@OLD_POST_ADDRESS,@IN_POST_ADDRESS,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF LTRIM(RTRIM(ISNULL(@OLD_POST_ADDRESS2,''))) <> LTRIM(RTRIM(ISNULL(@IN_POST_ADDRESS2,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'POST_ADDRESS2',N'????????2',@OLD_POST_ADDRESS2,@IN_POST_ADDRESS2,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF LTRIM(RTRIM(ISNULL(@OLD_POST_CODE,''))) <> LTRIM(RTRIM(ISNULL(@IN_POST_CODE,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'POST_CODE',N'????????',@OLD_POST_CODE,@IN_POST_CODE,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF LTRIM(RTRIM(ISNULL(@OLD_POST_CODE2,''))) <> LTRIM(RTRIM(ISNULL(@IN_POST_CODE2,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'POST_CODE2',N'????????2',@OLD_POST_CODE2,@IN_POST_CODE2,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF LTRIM(RTRIM(ISNULL(@OLD_CARD_TYPE_NAME,''))) <> LTRIM(RTRIM(ISNULL(@V_CARD_TYPE_NAME,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'CARD_TYPE_NAME',N'????????',@OLD_CARD_TYPE_NAME,@V_CARD_TYPE_NAME,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF LTRIM(RTRIM(ISNULL(@OLD_CARD_ID,''))) <> LTRIM(RTRIM(ISNULL(@IN_CARD_ID,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'CARD_ID',N'????????',@OLD_CARD_ID,@IN_CARD_ID,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF @IN_CUST_TYPE=1 AND @OLD_SEX <> @IN_SEX
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'SEX_NAME',N'????',@OLD_SEX_NAME,@V_SEX_NAME,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END
    
    IF @IN_O_TEL IS NULL OR @IN_O_TEL = ''
    SET @IN_O_TEL = @OLD_O_TEL

    IF LTRIM(RTRIM(ISNULL(@OLD_O_TEL,''))) <> LTRIM(RTRIM(ISNULL(@IN_O_TEL,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'O_TEL',N'????????',@OLD_O_TEL,@IN_O_TEL,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF @IN_H_TEL IS NULL OR @IN_H_TEL = ''
    SET @IN_H_TEL = @OLD_H_TEL

    IF LTRIM(RTRIM(ISNULL(@OLD_H_TEL,''))) <> LTRIM(RTRIM(ISNULL(@IN_H_TEL,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'H_TEL',N'????????',@OLD_H_TEL,@IN_H_TEL,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF @IN_MOBILE IS NULL OR @IN_MOBILE = ''
    SET @IN_MOBILE = @OLD_MOBILE

    IF LTRIM(RTRIM(ISNULL(@OLD_MOBILE,''))) <> LTRIM(RTRIM(ISNULL(@IN_MOBILE,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'MOBILE',N'????',@OLD_MOBILE,@IN_MOBILE,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF @IN_BP IS NULL OR @IN_BP = ''
    SET @IN_BP = @OLD_BP

    IF LTRIM(RTRIM(ISNULL(@OLD_BP,''))) <> LTRIM(RTRIM(ISNULL(@IN_BP,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'BP',N'BP??(????2)',@OLD_BP,@IN_BP,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END


    IF LTRIM(RTRIM(ISNULL(@OLD_FAX,''))) <> LTRIM(RTRIM(ISNULL(@IN_FAX,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'FAX',N'????',@OLD_FAX,@IN_FAX,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END


    IF LTRIM(RTRIM(ISNULL(@OLD_EMAIL,''))) <> LTRIM(RTRIM(ISNULL(@IN_E_MAIL,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'E_MAIL',N'EMAIL',@OLD_EMAIL,@IN_E_MAIL,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF @OLD_CUST_TYPE <> @IN_CUST_TYPE
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'CUST_TYPE_NAME',N'????????',@OLD_CUST_TYPE_NAME,@V_CUST_TYPE_NAME,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF LTRIM(RTRIM(ISNULL(@OLD_TOUCH_TYPE,''))) <> LTRIM(RTRIM(ISNULL(@IN_TOUCH_TYPE,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'TOUCH_TYPE_NAME',N'????????',@OLD_TOUCH_TYPE_NAME,@V_TOUCH_TYPE_NAME,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF LTRIM(RTRIM(ISNULL(@OLD_CUST_SOURCE,''))) <> LTRIM(RTRIM(ISNULL(@IN_CUST_SOURCE,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'CUST_SOURCE_NAME',N'????????',@OLD_CUST_SOURCE_NAME,@V_CUST_SOURCE_NAME,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF LTRIM(RTRIM(ISNULL(@OLD_SUMMARY,''))) <> LTRIM(RTRIM(ISNULL(@IN_SUMMARY,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'SUMMARY',N'????',@OLD_SUMMARY,@IN_SUMMARY,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF LTRIM(RTRIM(ISNULL(@OLD_MONEY_SOURCE,''))) <> LTRIM(RTRIM(ISNULL(@IN_MONEY_SOURCE,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'MONEY_SOURCE',N'????????',@OLD_MONEY_SOURCE,@IN_MONEY_SOURCE,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF LTRIM(RTRIM(ISNULL(@OLD_MONEY_SOURCE_NAME,''))) <> LTRIM(RTRIM(ISNULL(@IN_MONEY_SOURCE_NAME,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'MONEY_SOURCE_NAME',N'????????????',@OLD_MONEY_SOURCE_NAME,@IN_MONEY_SOURCE_NAME,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF LTRIM(RTRIM(ISNULL(@OLD_CUST_NO,''))) <> LTRIM(RTRIM(ISNULL(@IN_CUST_NO,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'CUST_NO',N'????????',@OLD_CUST_NO,@IN_CUST_NO,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF LTRIM(RTRIM(ISNULL(@OLD_LEGAL_MAN,''))) <> LTRIM(RTRIM(ISNULL(@IN_LEGAL_MAN,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'LEGAL_MAN',N'????????',@OLD_LEGAL_MAN,@IN_LEGAL_MAN,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF LTRIM(RTRIM(ISNULL(@OLD_LEGAL_ADDRESS,''))) <> LTRIM(RTRIM(ISNULL(@IN_LEGAL_ADDRESS,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'LEGAL_ADDRESS',N'????????',@OLD_LEGAL_ADDRESS,@IN_LEGAL_ADDRESS,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF @IN_CUST_TYPE=1 AND ISNULL(@OLD_BIRTHDAY,0)<>ISNULL(@IN_BIRTHDAY,0)
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'BIRTHDAY',N'????',@OLD_BIRTHDAY,@IN_BIRTHDAY,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF @IN_CUST_TYPE=1 AND ISNULL(@OLD_AGE,0)<>ISNULL(@IN_AGE,0)
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'AGE',N'????',@OLD_AGE,@IN_AGE,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END
    
    IF ISNULL(@OLD_PRINT_DEPLOY_BILL,0) <> ISNULL(@IN_PRINT_DEPLOY_BILL,0)
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'PRINT_DEPLOY_BILL',N'??????????????',@OLD_PRINT_DEPLOY_BILL,@IN_PRINT_DEPLOY_BILL,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF ISNULL(@OLD_PRINT_POST_INFO,0) <> ISNULL(@IN_PRINT_POST_INFO,0)
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'PRINT_POST_INFO',N'????????????????',@OLD_PRINT_POST_INFO,@IN_PRINT_POST_INFO,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF ISNULL(@OLD_IS_LINK,0) <> ISNULL(@IN_IS_LINK,0)
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'IS_LINK',N'????????????',@OLD_IS_LINK,@IN_IS_LINK,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END
    IF ISNULL(@OLD_SERVICE_MAN,0) <> ISNULL(@IN_SERVICE_MAN,0)
    BEGIN
        DECLARE @OLD_SERVICE_NAME NVARCHAR(20),@V_SERVICE_NAME NVARCHAR(20)
        SELECT @OLD_SERVICE_NAME = OP_NAME FROM TOPERATOR WHERE OP_CODE = @OLD_SERVICE_MAN
        SELECT @V_SERVICE_NAME = OP_NAME FROM TOPERATOR WHERE OP_CODE = @IN_SERVICE_MAN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'SERVICE_MAN',N'????????',@OLD_SERVICE_NAME,@V_SERVICE_NAME,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
		--???????????????????? 1?? 2??
		IF NOT EXISTS (SELECT 1 FROM TSYSCONTROL WHERE FLAG_TYPE='CUSTINFO_CHANGE' AND VALUE=1)
        BEGIN --??????
            UPDATE INTRUST..TCONTRACT SET SERVICE_MAN = @IN_SERVICE_MAN WHERE CUST_ID = @IN_CUST_ID AND CHECK_FLAG = 2
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END
        END
    END
    IF LTRIM(RTRIM(ISNULL(@OLD_VIP_CARD_ID,''))) <> LTRIM(RTRIM(ISNULL(@IN_VIP_CARD_ID,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'VIP_CARD_ID',N'VIP??????',@OLD_VIP_CARD_ID,@IN_VIP_CARD_ID,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END
    IF ISNULL(@OLD_VIP_DATE,0) <> ISNULL(@IN_VIP_DATE,0)
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'VIP_DATE',N'VIP????????',@OLD_VIP_DATE,@IN_VIP_DATE,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END
    IF LTRIM(RTRIM(ISNULL(@OLD_HGTZR_BH,''))) <> LTRIM(RTRIM(ISNULL(@IN_HGTZR_BH,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'HGTZR_BH',N'??????????????',@OLD_HGTZR_BH,@IN_HGTZR_BH,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END
    /*
    IF LTRIM(RTRIM(@OLD_VOC_TYPE_NAME)) <> LTRIM(RTRIM(@V_VOC_TYPE_NAME))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'VOC_TYPE_NAME',N'????????????????????',@OLD_VOC_TYPE_NAME,@V_VOC_TYPE_NAME,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END*/

    IF LTRIM(RTRIM(ISNULL(@OLD_COMPANY_UNIT,''))) <> LTRIM(RTRIM(ISNULL(@IN_COMPANY_UNIT,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'COMPANY_UNIT',N'????????',@OLD_COMPANY_UNIT,@IN_COMPANY_UNIT,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF LTRIM(RTRIM(ISNULL(@OLD_COMPANY_DEPART,''))) <> LTRIM(RTRIM(ISNULL(@IN_COMPANY_DEPART,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'COMPANY_DEPART',N'????????',@OLD_COMPANY_DEPART,@IN_COMPANY_DEPART,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF LTRIM(RTRIM(ISNULL(@OLD_COMPANY_POSITION,''))) <> LTRIM(RTRIM(ISNULL(@IN_COMPANY_POSITION,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'COMPANY_DOSITION',N'????????',@OLD_COMPANY_POSITION,@IN_COMPANY_POSITION,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF LTRIM(RTRIM(ISNULL(@OLD_EAST_JG_TYPE,''))) <> LTRIM(RTRIM(ISNULL(@IN_EAST_JG_TYPE,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'EAST_JG_TYPE',N'????????',@OLD_EAST_JG_TYPE,@IN_EAST_JG_TYPE,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    IF LTRIM(RTRIM(ISNULL(@OLD_JG_WTRLX2,''))) <> LTRIM(RTRIM(ISNULL(@IN_JG_WTRLX2,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'JG_WTRLX2',N'??????????2',@OLD_JG_WTRLX2,@IN_JG_WTRLX2,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END
    
    IF LTRIM(RTRIM(ISNULL(@OLD_VOC_TYPE,''))) <> LTRIM(RTRIM(ISNULL(@IN_VOC_TYPE,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'VOC_TYPE',N'????',@OLD_VOC_TYPE,@IN_VOC_TYPE,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END
    IF LTRIM(RTRIM(ISNULL(@OLD_CONTACT_MAN,''))) <> LTRIM(RTRIM(ISNULL(@IN_CONTACT_MAN,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'CONTACT_MAN',N'??????',@OLD_CONTACT_MAN,@IN_CONTACT_MAN,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END
    IF LTRIM(RTRIM(ISNULL(@OLD_TRUE_FLAG,0))) <> LTRIM(RTRIM(ISNULL(@V_TRUE_FLAG,0)))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'TRUE_FLAG',N'??????????',@OLD_TRUE_FLAG,@V_TRUE_FLAG,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END
    IF LTRIM(RTRIM(ISNULL(@OLD_STATUS,''))) <> LTRIM(RTRIM(ISNULL(@V_STATUS,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'STATUS',N'????????',@OLD_STATUS,@V_STATUS,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
    END
	--------------------------------------------------------------------
    --???????????????????? 1?? 2??
	IF NOT EXISTS (SELECT 1 FROM TSYSCONTROL WHERE FLAG_TYPE='CUSTINFO_CHANGE' AND VALUE=1)
	BEGIN--????????????????????????????????????????????????????
    IF @OLD_STATUS = N'112801' AND @V_CHECK_FLAG = 2 AND @V_STATUS<>'112806'
       SELECT @V_STATUS = N'112803'
    SELECT @V_STATUS_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @V_STATUS
    
    UPDATE TCustomers
        SET CUST_NAME = @IN_CUST_NAME,
            CUST_TEL = @IN_CUST_TEL,
            POST_ADDRESS = @IN_POST_ADDRESS,
            POST_ADDRESS2 = @IN_POST_ADDRESS2,
            POST_CODE = @IN_POST_CODE,
            POST_CODE2 = @IN_POST_CODE2,
            CARD_TYPE = @IN_CARD_TYPE,
            CARD_TYPE_NAME = @V_CARD_TYPE_NAME,
            CARD_ID = @IN_CARD_ID,
            AGE = @IN_AGE,
            SEX = @IN_SEX,
            SEX_NAME = @V_SEX_NAME,
            O_TEL = @IN_O_TEL,
            H_TEL = @IN_H_TEL,
            MOBILE = @IN_MOBILE,
            BP = @IN_BP,
            FAX = @IN_FAX,
            E_MAIL = @IN_E_MAIL,
            CUST_TYPE = @IN_CUST_TYPE,
            CUST_TYPE_NAME = @V_CUST_TYPE_NAME,
            TOUCH_TYPE = @IN_TOUCH_TYPE,
            TOUCH_TYPE_NAME = @V_TOUCH_TYPE_NAME,
            CUST_SOURCE = @IN_CUST_SOURCE,
            CUST_SOURCE_NAME = @V_CUST_SOURCE_NAME,
            SUMMARY = @IN_SUMMARY,
            CUST_NO = @IN_CUST_NO,
            STATUS = @V_STATUS,
            STATUS_NAME = @V_STATUS_NAME,
            MODI_MAN = @IN_INPUT_MAN,
            MODI_TIME = dbo.GETDATEINT(getdate()),
            LEGAL_MAN = @IN_LEGAL_MAN,
            LEGAL_ADDRESS = @IN_LEGAL_ADDRESS,
            BIRTHDAY = @IN_BIRTHDAY,
            PRINT_DEPLOY_BILL = @IN_PRINT_DEPLOY_BILL,
            PRINT_POST_INFO = @IN_PRINT_POST_INFO,
            IS_LINK = @IN_IS_LINK,
            SERVICE_MAN = @IN_SERVICE_MAN,
            VIP_CARD_ID = @IN_VIP_CARD_ID,
            VIP_DATE = @IN_VIP_DATE,
            HGTZR_BH = @IN_HGTZR_BH,
            VOC_TYPE = @IN_VOC_TYPE,
            VOC_TYPE_NAME = @V_VOC_TYPE_NAME,
            CARD_VALID_DATE = @IN_CARD_VALID_DATE,
            COUNTRY         = @IN_COUNTRY,
            JG_CUST_TYPE    = @IN_JG_CUST_TYPE,
            CONTACT_MAN     = @IN_CONTACT_MAN,
            MONEY_SOURCE    = @IN_MONEY_SOURCE,
            MONEY_SOURCE_NAME = @IN_MONEY_SOURCE_NAME, --????????????
            FACT_CONTROLLER = @IN_FACT_CONTROLLER,     --??????????     add by luohh 0090727
            ImageIdentification = ISNULL(@IN_ImageIdentification,ImageIdentification),
            PinYinSimple = @V_PinYinSimple,
            POTENTIAL_MONEY = @IN_POTENTIAL_MONEY,
            --IS_DEAL = @V_IS_DEAL,
            GOV_PROV_REGIONAL = @IN_GOV_PROV_REGIONAL,
            GOV_PROV_REGIONAL_NAME = @V_GOV_PROV_REGIONAL_NAME,
            GOV_REGIONAL = @IN_GOV_REGIONAL,
            GOV_REGIONAL_NAME = @V_GOV_REGIONAL_NAME,
            RECOMMENDED = @IN_RECOMMENDED,
            COMPLETE_FLAG = @IN_COMPLETE_FLAG,
            COMPANY_UNIT     = @IN_COMPANY_UNIT,
            COMPANY_DEPART   = @IN_COMPANY_DEPART,
            COMPANY_POSITION = @IN_COMPANY_POSITION,
            CUST_AUM = @IN_CUST_AUM,
            CUST_AUM_NAME = @V_CUST_AUM_NAME,
            CUST_AGE_GROUP = @IN_CUST_AGE_GROUP,
            CUST_AGE_GROUP_NAME = @V_CUST_AGE_GROUP_NAME,
            INVES_EXPERINCE = @IN_INVES_EXPERINCE,
            CUST_SOURCE_EXPLAIN = @IN_CUST_SOURCE_EXPLAIN,
            EAST_JG_TYPE = @IN_EAST_JG_TYPE,                    --????????2109??????EAST??????????WTRLX??
            EAST_JG_TYPE_NAME = @V_EAST_JG_TYPE_NAME,           --??????????????????EAST??????????WTRLX??
            JG_WTRLX2 = @IN_JG_WTRLX2,
            TRUE_FLAG=@V_TRUE_FLAG
        WHERE CUST_ID = @IN_CUST_ID
    UPDATE TCustomers
        SET PERSONAL_INCOME = @IN_PERSONAL_INCOME,HOUSEHOLD_INCOME = @IN_HOUSEHOLD_INCOME
            ,FEEDING_NUM_PEOPLE = @IN_FEEDING_NUM_PEOPLE,MAIN_SOURCE = @IN_MAIN_SOURCE
            ,OTHER_SOURCE = @IN_OTHER_SOURCE,HOUSE_POSITION = @IN_HOUSE_POSITION,HOUSE_PROPERTY = @IN_HOUSE_PROPERTY,HOUSE_AREA = @IN_HOUSE_AREA
            ,PLAT_ENVALUATE = @IN_PLAT_ENVALUATE,MARKET_APPRAISAL = @IN_MARKET_APPRAISAL
            ,VEHICLE_NUM = @IN_VEHICLE_NUM,VEHICLE_MAKE = @IN_VEHICLE_MAKE,VEHICLE_TYPE = @IN_VEHICLE_TYPE,CREDIT_TYPE = @IN_CREDIT_TYPE
            ,CREDIT_NUM = @IN_CREDIT_NUM,CREDIT_START_DATE = @IN_CREDIT_START_DATE
            ,CREDIT_END_DATE = @IN_CREDIT_END_DATE,OTHER_INVESTMENT_STATUS = @IN_OTHER_INVESTMENT_STATUS
            ,TYPE_PREF = @IN_TYPE_PREF,TIME_LIMIT_PREF = @IN_TIME_LIMIT_PREF,INVEST_PREF = @IN_INVEST_PREF,HOBBY_PREF = @IN_HOBBY_PREF
            ,SERVICE_PREF = @IN_SERVICE_PREF,CONTACT_PREF = @IN_CONTACT_PREF
            ,HEAD_OFFICE_CUST_ID = @IN_HEAD_OFFICE_CUST_ID,STATURE = @IN_STATURE,WEIGHT = @IN_WEIGHT,SPOUSE_NAME = @IN_SPOUSE_NAME
            ,SPOUSE_INFO = @IN_SPOUSE_INFO,CHILDREN_NAME = @IN_CHILDREN_NAME,CHILDREN_INFO = @IN_CHILDREN_INFO,NATION = @IN_NATION
            ,MARITAL = @IN_MARITAL,HEALTH = @IN_HEALTH,EDUCATION = @IN_EDUCATION,POST = @IN_POST,HOLDEROFANOFFICE = @IN_HOLDEROFANOFFICE
            ,COMPANY_CHARACTER = @IN_COMPANY_CHARACTER,COMPANY_STAFF = @IN_COMPANY_STAFF,BOCOM_STAFF = @IN_BOCOM_STAFF,CLIENT_QUALE = @IN_CLIENT_QUALE
            ,REGISTERED_PLACE = @IN_REGISTERED_PLACE,REGISTERED_CAPITAL = @IN_REGISTERED_CAPITAL,EMPLOYEE_NUM = @IN_EMPLOYEE_NUM,HOLDING = @IN_HOLDING
            ,OTHER_INVE_STATUS_NAME = @V_OTHER_INVE_STATUS_NAME,TYPE_PREF_NAME = @V_TYPE_PREF_NAME
            ,TIME_LIMIT_PREF_NAME = @V_TIME_LIMIT_PREF_NAME,INVEST_PREF_NAME = @V_INVEST_PREF_NAME
            ,HOBBY_PREF_NAME = @V_HOBBY_PREF_NAME,SERVICE_PREF_NAME = @V_SERVICE_PREF_NAME
            ,CONTACT_PREF_NAME = @V_CONTACT_PREF_NAME,NATION_NAME = @V_NATION_NAME,HEALTH_NAME = @V_HEALTH_NAME,EDUCATION_NAME = @V_EDUCATION_NAME
            ,COMPANY_CHARACTER_NAME = @V_COMPANY_CHARACTER_NAME,CLIENT_QUALE_NAME = @V_CLIENT_QUALE_NAME
            ,RISK_PREF = @IN_RISK_PREF,RISK_PREF_NAME = @V_RISK_PREF_NAME
            ,FREE_CASH_FLOW = @IN_FREE_CASH_FLOW,BUREND_OF_DEBT = @IN_BUREND_OF_DEBT,COMPLETE_FLAG = @IN_COMPLETE_FLAG
        WHERE CUST_ID = @IN_CUST_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    --
    --??????????????????
    IF @V_USER_ID<>15/*????????????????????????*/
    BEGIN
        EXEC @RET = INTRUST..SP_MODI_TCUSTOMERINFO
                        @IN_CUST_ID              = @IN_CUST_ID,
                        @IN_CUST_NAME            = @IN_CUST_NAME,
                        @IN_CUST_TEL             = @IN_CUST_TEL,
                        @IN_POST_ADDRESS         = @IN_POST_ADDRESS,
                        @IN_POST_CODE            = @IN_POST_CODE,
                        @IN_CARD_TYPE            = @IN_CARD_TYPE,
                        @IN_CARD_ID              = @IN_CARD_ID,
                        @IN_AGE                  = @IN_AGE,
                        @IN_SEX                  = @IN_SEX,
                        @IN_O_TEL                = @IN_O_TEL,
                        @IN_H_TEL                = @IN_H_TEL,
                        @IN_MOBILE               = @IN_MOBILE,
                        @IN_BP                   = @IN_BP,
                        @IN_FAX                  = @IN_FAX,
                        @IN_E_MAIL               = @IN_E_MAIL,
                        @IN_CUST_TYPE            = @IN_CUST_TYPE,
                        @IN_TOUCH_TYPE           = @IN_TOUCH_TYPE,
                        @IN_CUST_SOURCE          = @IN_CUST_SOURCE,
                        @IN_SUMMARY              = @IN_SUMMARY,
                        @IN_INPUT_MAN            = @IN_INPUT_MAN,
                        @IN_CUST_NO              = @IN_CUST_NO,
                        @IN_LEGAL_MAN            = @IN_LEGAL_MAN,
                        @IN_LEGAL_ADDRESS        = @IN_LEGAL_ADDRESS,
                        @IN_BIRTHDAY             = @IN_BIRTHDAY,
                        @IN_POST_ADDRESS2        = @IN_POST_ADDRESS2,
                        @IN_POST_CODE2           = @IN_POST_CODE2,
                        @IN_PRINT_DEPLOY_BILL    = @IN_PRINT_DEPLOY_BILL,
                        @IN_PRINT_POST_INFO      = @IN_PRINT_POST_INFO,
                        @IN_IS_LINK              = @IN_IS_LINK,
                        @IN_SERVICE_MAN          = @IN_SERVICE_MAN,
                        @IN_VIP_CARD_ID          = @IN_VIP_CARD_ID,
                        @IN_VIP_DATE             = @IN_VIP_DATE,
                        @IN_HGTZR_BH             = @IN_HGTZR_BH,
                        @IN_VOC_TYPE             = @IN_VOC_TYPE,
                        @IN_CARD_VALID_DATE      = @IN_CARD_VALID_DATE,
                        @IN_COUNTRY              = @IN_COUNTRY,
                        @IN_JG_CUST_TYPE         = @IN_JG_CUST_TYPE,
                        @IN_CONTACT_MAN          = @IN_CONTACT_MAN,
                        @IN_MONEY_SOURCE		 = NULL,
						@IN_FACT_CONTROLLER		 = NULL,
						@IN_LINK_TYPE			 = NULL,
						@IN_LINK_GD_MONEY		 = NULL,
						@IN_GRADE_LEVEL			 = NULL,
						@IN_COMPLETE_FLAG		 = NULL,
						@IN_GOV_PROV_REGIONAL	 = NULL,
						@IN_GOV_REGIONAL		 = NULL,
						@IN_LEGAL_TEL			 = NULL,
						@IN_LEGAL_MOBILE		 = NULL,
						@IN_ISSUED_ORG			 = NULL,
						@IN_INDUSTRY_POST		 = NULL,
						@IN_CUST_INDUSTRY		 = NULL,
						@IN_CUST_CORP_NATURE	 = NULL,
						@IN_CORP_BRANCH			 = NULL,
						@IN_SERVICE_MAN_TEL		 = NULL,
						@IN_GRADE_SCORE			 = NULL,
						@IN_FC_CARD_TYPE		 = NULL,
						@IN_FC_CARD_ID			 = NULL,
						@IN_FC_CARD_VALID_DATE	 = NULL,
						@IN_SUMMARY1			 = NULL,
						@IN_SUMMARY2			 = NULL,
						@IN_LEGAL_POST_CODE		 = NULL,
						@IN_TRANS_NAME			 = NULL,
						@IN_TRANS_TEL			 = NULL,
						@IN_TRANS_MOBILE		 = NULL,
						@IN_TRANS_ADDRESS		 = NULL,
						@IN_TRANS_POST_CODE		 = NULL,
						@IN_REGISTER_ADDRESS	 = NULL,
						@IN_REGISTER_POST_CODE	 = NULL,
						@IN_EAST_JG_TYPE		 = @IN_EAST_JG_TYPE,       --????????2109??????EAST??????????WTRLX??
						@IN_JG_WTRLX2            = @IN_JG_WTRLX2           --??????????1?????????? 2???? 3???????????? 4??????????????
                                    
      /*  BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END*/
    END
    --??????INTRUST..TE_WTRXX??
    IF @IN_CUST_TYPE=2 --2????
		SELECT @V_WTRLX = ADDITIVE_VALUE FROM TDICTPARAM WHERE TYPE_VALUE = @IN_EAST_JG_TYPE
	ELSE --1????
		SET @V_WTRLX='1'
	IF @V_USER_ID<>15/*????????????????????????*/
    UPDATE INTRUST..TE_WTRXX
		SET WTRLX = @V_WTRLX,				--????????2109??????EAST??????????WTRLX??
			JG_WTRLX2 = @IN_JG_WTRLX2		--??????????1?????????? 2???? 3???????????? 4??????????????
		WHERE CUST_ID = @IN_CUST_ID
    --??????????????
    IF EXISTS(SELECT 1 FROM TCUSTCARDINFO WHERE CUST_ID = @IN_CUST_ID AND CARD_TYPE = @OLD_CARD_TYPE AND CARD_ID = @OLD_CARD_ID)
    BEGIN
        UPDATE TCUSTCARDINFO
            SET CARD_CUST_NAME = @IN_CUST_NAME,
                CARD_TYPE      = @IN_CARD_TYPE,
                CARD_TYPE_NAME = @V_CARD_TYPE_NAME,
                CARD_ID        = @IN_CARD_ID,
                VALID_DATE     = @IN_CARD_VALID_DATE,
                INPUT_TIME     = GETDATE(),
                IMAGE1         = ISNULL(@IN_ImageIdentification,IMAGE1)
            WHERE CUST_ID = @IN_CUST_ID AND CARD_TYPE = @OLD_CARD_TYPE AND CARD_ID = @OLD_CARD_ID
    END
    ELSE
    BEGIN
        INSERT INTO TCUSTCARDINFO(CUST_ID,CARD_CUST_NAME,CUST_TYPE,CARD_TYPE,CARD_TYPE_NAME,CARD_ID,VALID_DATE,INPUT_TIME,IMAGE1)
            VALUES(@IN_CUST_ID,@IN_CUST_NAME,@IN_CUST_TYPE,@IN_CARD_TYPE,@V_CARD_TYPE_NAME,@IN_CARD_ID,@IN_CARD_VALID_DATE,GETDATE(),@IN_ImageIdentification)
    END
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
	END --????????????????
	--????????????????????????????????????
	IF @OLD_SERVICE_MAN<>@IN_INPUT_MAN
		INSERT INTO TSYSMESSAGE(TITLE,MSG,FROM_OPCODE,TO_OPCODE,INPUT_TIME,IS_READ)
			SELECT '????????????????','????????????????',@IN_INPUT_MAN,@OLD_SERVICE_MAN,GETDATE(),1
    SELECT @SSUMMARY = N'????????????????????????????' + @IN_CUST_NAME
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    COMMIT TRANSACTION
    END TRY
    BEGIN CATCH
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

    SET XACT_ABORT OFF
    RETURN 100

GO


