USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE SP_ADD_TPartner     @IN_PARTN_NO             NVARCHAR(8),
                                     @IN_PARTN_NAME           NVARCHAR(100),
                                     @IN_PARTN_TEL            NVARCHAR(20),
                                     @IN_POST_ADDRESS         NVARCHAR(60),
                                     @IN_POST_ADDRESS2        NVARCHAR(60),
                                     @IN_POST_CODE            NVARCHAR(6),
                                     @IN_POST_CODE2           NVARCHAR(6),
                                     @IN_CARD_TYPE            NVARCHAR(10),
                                     @IN_CARD_ID              NVARCHAR(40),
                                     @IN_CARD_VALID_DATE      INT = NULL,             --客户身份证件有效期限8位日期表示
                                     @IN_COUNTRY              NVARCHAR(10) = NULL,    --客户国籍(9997)
                                     @IN_BIRTHDAY             INT,
                                     @IN_AGE                  INT,
                                     @IN_SEX                  INT,
                                     @IN_O_TEL                NVARCHAR(20),
                                     @IN_H_TEL                NVARCHAR(20),
                                     @IN_MOBILE               NVARCHAR(100),
                                     @IN_BP                   NVARCHAR(60),
                                     @IN_FAX                  NVARCHAR(20),
                                     @IN_E_MAIL               NVARCHAR(30),
                                     @IN_PARTN_TYPE           INT,                    --客户类型
                                     @IN_JG_PARTN_TYPE         NVARCHAR(10) = NULL,    --机构客户类别(9921)，仅在CUST_TYPE=2机构时有效
                                     @IN_TOUCH_TYPE           NVARCHAR(10) = NULL,
                                     @IN_SUMMARY              NVARCHAR(200),
                                     @IN_LEGAL_MAN            NVARCHAR(20),
                                     @IN_LEGAL_ADDRESS        NVARCHAR(60),
                                     @IN_CONTACT_MAN          NVARCHAR(30) = NULL,    --机构客户联系人
                                     @IN_SERVICE_MAN          INT = NULL,
                                     @IN_VOC_TYPE             NVARCHAR(10) = N'',      -- 个人职业/机构行业类别(1142/2142)
                                     @IN_REPORT_TYPE          NVARCHAR(10),
                                     @IN_PARTN_TYPE2_FLAG     INT,
                                     @IN_CUST_ID              INT,
                                     @IN_INPUT_MAN            INT,
                                     @OUT_PARTN_ID            INT OUTPUT

WITH ENCRYPTION
AS
    DECLARE @V_PARTN_ID INT,@V_PARTN_NO NVARCHAR(8),@V_CARD_TYPE_NAME NVARCHAR(30),@V_MAX_ID INT,@V_SEX_NAME NVARCHAR(10)
    DECLARE @V_PARTN_TYPE_NAME NVARCHAR(10),@V_TOUCH_TYPE_NAME NVARCHAR(30),@V_VOC_TYPE_NAME NVARCHAR(30),@V_REPORT_TYPE_NAME NVARCHAR(30)
    SELECT  @OUT_PARTN_ID = 0

    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT, @SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -99901000, @IBUSI_FLAG = 99901
    SELECT @SBUSI_NAME = N'增加合作伙伴基本信息', @SSUMMARY = N'增加合作伙伴基本信息'

    IF EXISTS(SELECT * FROM TSYSCONTROL WHERE FLAG_TYPE = N'A_CARDID' AND VALUE = 1)
    BEGIN
        IF @IN_CARD_TYPE = N'110801' AND LEN(@IN_CARD_ID)=15   --15位身份证号码转18位
            SELECT @IN_CARD_ID = dbo.IDCARD15TO18(@IN_CARD_ID)
    END
    IF (@IN_CARD_TYPE IS NOT NULL AND @IN_CARD_TYPE  <> '') AND (@IN_CARD_ID IS NOT NULL AND @IN_CARD_ID  <> '')
    BEGIN
        IF EXISTS(SELECT * FROM TPartner WHERE PARTN_NAME = @IN_PARTN_NAME AND CARD_TYPE = @IN_CARD_TYPE AND CARD_ID = @IN_CARD_ID)
           RETURN @V_RET_CODE -2   -- 客户已存在
        IF EXISTS (SELECT * FROM TPartner WHERE CARD_TYPE = @IN_CARD_TYPE AND CARD_ID = @IN_CARD_ID )
           RETURN @V_RET_CODE -3  --该身份证号已存在
    END


    --如果是个人客户，证件类型是身份证，且未输入生日时，根据15位和18位的情况生成生日变量
    IF @IN_PARTN_TYPE = 1 AND (@IN_BIRTHDAY IS NULL OR @IN_BIRTHDAY = 0) AND @IN_CARD_TYPE = N'110801'
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

    -- 生成PARTN_ID
    SELECT @V_PARTN_ID = ISNULL(MAX(PARTN_ID),0)+ 1 FROM TPartner
    SELECT @V_MAX_ID = ISNULL(MAX(LIST_ID),0)+ 1 FROM TPartner

    --合作伙伴编号
    IF @IN_PARTN_NO IS NULL OR @IN_PARTN_NO = N''
    BEGIN
        SELECT @V_PARTN_NO = CONVERT(CHAR(8),@IN_PARTN_TYPE * 10000000+@V_MAX_ID)
        IF @IN_PARTN_TYPE = 1
            SELECT @V_PARTN_NO = N'P0' + RIGHT(@V_PARTN_NO,6)
        ELSE
            SELECT @V_PARTN_NO = N'PJ' + RIGHT(@V_PARTN_NO,6)
    END
    ELSE
    BEGIN
        IF EXISTS(SELECT * FROM TPartner WHERE PARTN_NO = @IN_PARTN_NO)
            RETURN @V_RET_CODE - 1  --客户编号已存在
        SELECT @V_PARTN_NO = @IN_PARTN_NO
    END

    -- 取证件类别名称
    SELECT @V_CARD_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_CARD_TYPE
    --国家
    SELECT @IN_COUNTRY = ISNULL(@IN_COUNTRY,'9997CHN')   --默认中国 add by luohh 200908

    -- 性别名称
    SELECT @V_CARD_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_CARD_TYPE
    IF @IN_SEX = 1
       SELECT @V_SEX_NAME  = N'男'
    ELSE
       SELECT @V_SEX_NAME  = N'女'

    --伙伴类型
    IF @IN_PARTN_TYPE = 1
        SELECT @V_PARTN_TYPE_NAME = N'个人'
    ELSE
        SELECT @V_PARTN_TYPE_NAME = N'机构'

    --  联系类型名称
    IF @IN_TOUCH_TYPE = '' OR @IN_TOUCH_TYPE IS NULL
		BEGIN
    		SELECT @V_TOUCH_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = '110901'
			SELECT @IN_TOUCH_TYPE = '110901'
		END
    ELSE
	SELECT @V_TOUCH_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_TOUCH_TYPE --默认为电话

    --  个人职业/机构行业类别说明
    SELECT @V_VOC_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_VOC_TYPE
    --  报道类型名称
    SELECT @V_REPORT_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_REPORT_TYPE

BEGIN TRANSACTION
    INSERT INTO TPartner(
        PARTN_ID,PARTN_NO,PARTN_NAME,PARTN_TEL,POST_ADDRESS,POST_ADDRESS2,POST_CODE,POST_CODE2,
        CARD_TYPE,CARD_TYPE_NAME,CARD_ID,CARD_VALID_DATE,COUNTRY,BIRTHDAY,AGE,SEX,SEX_NAME,O_TEL,
        H_TEL,MOBILE,BP,FAX,E_MAIL,PARTN_TYPE,PARTN_TYPE_NAME,JG_PARTN_TYPE,TOUCH_TYPE,TOUCH_TYPE_NAME,
        INPUT_MAN,SUMMARY,LEGAL_MAN,LEGAL_ADDRESS,CONTACT_MAN,LIST_ID,SERVICE_MAN,VOC_TYPE,VOC_TYPE_NAME,
        REPORT_TYPE,REPORT_TYPE_NAME,PARTN_TYPE2_FLAG,CUST_ID)
    VALUES(
        @V_PARTN_ID,@V_PARTN_NO,@IN_PARTN_NAME,@IN_PARTN_TEL,@IN_POST_ADDRESS,@IN_POST_ADDRESS2,@IN_POST_CODE,@IN_POST_CODE2,
        @IN_CARD_TYPE,@V_CARD_TYPE_NAME,@IN_CARD_ID,@IN_CARD_VALID_DATE,@IN_COUNTRY,@IN_BIRTHDAY,@IN_AGE,@IN_SEX,@V_SEX_NAME,@IN_O_TEL,
        @IN_H_TEL,@IN_MOBILE,@IN_BP,@IN_FAX,@IN_E_MAIL,@IN_PARTN_TYPE,@V_PARTN_TYPE_NAME,@IN_JG_PARTN_TYPE,ISNULL(@IN_TOUCH_TYPE,'110901'), @V_TOUCH_TYPE_NAME,
        @IN_INPUT_MAN,@IN_SUMMARY,@IN_LEGAL_MAN,@IN_LEGAL_ADDRESS,@IN_CONTACT_MAN,@V_MAX_ID,@IN_SERVICE_MAN,@IN_VOC_TYPE,@V_VOC_TYPE_NAME,
        @IN_REPORT_TYPE,@V_REPORT_TYPE_NAME,@IN_PARTN_TYPE2_FLAG,@IN_CUST_ID)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @OUT_PARTN_ID = @V_PARTN_ID
    SET @SSUMMARY = N'添加合作伙伴基本信息，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
