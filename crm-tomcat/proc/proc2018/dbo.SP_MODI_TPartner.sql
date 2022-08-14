USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TPartner    @IN_PARTN_ID           INT,                        --伙伴主键
                                     @IN_PARTN_NO             NVARCHAR(8),
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
                                     @IN_JG_PARTN_TYPE        NVARCHAR(10) = NULL,    --机构客户类别(9921)，仅在CUST_TYPE=2机构时有效
                                     @IN_TOUCH_TYPE           NVARCHAR(10),
                                     @IN_SUMMARY              NVARCHAR(200),
                                     @IN_LEGAL_MAN            NVARCHAR(20),
                                     @IN_LEGAL_ADDRESS        NVARCHAR(60),
                                     @IN_CONTACT_MAN          NVARCHAR(30) = NULL,    --机构客户联系人
                                     @IN_SERVICE_MAN          INT = NULL,
                                     @IN_VOC_TYPE             NVARCHAR(10) = N'',      -- 个人职业/机构行业类别(1142/2142)
                                     @IN_REPORT_TYPE          NVARCHAR(10),
                                     @IN_PARTN_TYPE2_FLAG     INT,
                                     @IN_CUST_ID              INT =NULL,
                                     @IN_INPUT_MAN            INT

WITH ENCRYPTION
AS
    DECLARE @V_CARD_TYPE_NAME NVARCHAR(30),@V_SEX_NAME NVARCHAR(10)
    DECLARE @V_PARTN_TYPE_NAME NVARCHAR(10),@V_TOUCH_TYPE_NAME NVARCHAR(30),@V_VOC_TYPE_NAME NVARCHAR(30),@V_REPORT_TYPE_NAME NVARCHAR(30)

    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT, @SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -20702000, @IBUSI_FLAG = 20702
    SELECT @SBUSI_NAME = N'修改合作伙伴基本信息', @SSUMMARY = N'修改合作伙伴基本信息'

    IF NOT EXISTS(SELECT 1 FROM TPartner WHERE PARTN_ID = @IN_PARTN_ID)
        RETURN @V_RET_CODE - 1 --合作伙伴信息不存在

    IF EXISTS(SELECT * FROM TSYSCONTROL WHERE FLAG_TYPE = N'A_CARDID' AND VALUE = 1)
    BEGIN
        IF @IN_CARD_TYPE = N'110801' AND LEN(@IN_CARD_ID)=15   --15位身份证号码转18位
            SELECT @IN_CARD_ID = dbo.IDCARD15TO18(@IN_CARD_ID)
    END
    IF (@IN_CARD_TYPE IS NOT NULL AND @IN_CARD_TYPE  <> '') AND (@IN_CARD_ID IS NOT NULL AND @IN_CARD_ID  <> '')
    BEGIN
        IF EXISTS (SELECT * FROM TPartner WHERE CARD_TYPE = @IN_CARD_TYPE AND CARD_ID = @IN_CARD_ID AND PARTN_ID <> @IN_PARTN_ID)
            RETURN @V_RET_CODE - 3  --该身份证号已存在
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

    -- 取证件类别名称
    SELECT @V_CARD_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_CARD_TYPE
    --国家
    SELECT @IN_COUNTRY = ISNULL(@IN_COUNTRY,'9997CHN')   --默认中国 add by luohh 200908

    -- 性别名称
    SELECT @V_CARD_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_CARD_TYPE
    IF @IN_SEX = 1
    SELECT @V_SEX_NAME  ='男'
    ELSE
    SELECT @V_SEX_NAME  ='女'

    --伙伴类型
    IF @IN_PARTN_TYPE = 1
        SELECT @V_PARTN_TYPE_NAME = N'个人'
    ELSE
        SELECT @V_PARTN_TYPE_NAME = N'机构'

    --联系类型名称
    SELECT @V_TOUCH_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_TOUCH_TYPE
    --  个人职业/机构行业类别说明
    SELECT @V_VOC_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_VOC_TYPE
    --  报道类型名称
    SELECT @V_REPORT_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_REPORT_TYPE
select @In_Contact_Man
BEGIN TRANSACTION
    UPDATE TPartner
        SET PARTN_NAME =  @IN_PARTN_NAME,
            PARTN_TEL  =  @IN_PARTN_TEL,
            POST_ADDRESS = @IN_POST_ADDRESS,
            POST_ADDRESS2 = @IN_POST_ADDRESS2,
            POST_CODE = @IN_POST_CODE,
            POST_CODE2 = @IN_POST_CODE2,
            CARD_TYPE = @IN_CARD_TYPE,
            CARD_TYPE_NAME = @V_CARD_TYPE_NAME,
            CARD_ID = @IN_CARD_ID,
            CARD_VALID_DATE = @IN_CARD_VALID_DATE,
            COUNTRY = @IN_COUNTRY,
            BIRTHDAY = @IN_BIRTHDAY,
            AGE = @IN_AGE,
            SEX = @IN_SEX,
            SEX_NAME = @V_SEX_NAME,
            O_TEL = @IN_O_TEL,
            H_TEL = @IN_H_TEL,
            MOBILE = @IN_MOBILE,
            BP = @IN_BP,
            FAX = @IN_FAX,
            E_MAIL = @IN_E_MAIL,
            PARTN_TYPE = @IN_PARTN_TYPE,
            PARTN_TYPE_NAME = @V_PARTN_TYPE_NAME,
            JG_PARTN_TYPE = @IN_JG_PARTN_TYPE,
            TOUCH_TYPE = @IN_TOUCH_TYPE,
            TOUCH_TYPE_NAME = @V_TOUCH_TYPE_NAME,
            SUMMARY = @IN_SUMMARY,
            LEGAL_MAN = @IN_LEGAL_MAN,
            LEGAL_ADDRESS = @IN_LEGAL_ADDRESS,
            CONTACT_MAN = @In_Contact_Man,
            SERVICE_MAN = @IN_SERVICE_MAN,
            VOC_TYPE = @IN_VOC_TYPE,
            VOC_TYPE_NAME = @V_VOC_TYPE_NAME,
            REPORT_TYPE = @IN_REPORT_TYPE,
            REPORT_TYPE_NAME = @V_REPORT_TYPE_NAME,
            PARTN_TYPE2_FLAG = @IN_PARTN_TYPE2_FLAG,
            CUST_ID = @IN_CUST_ID
    WHERE PARTN_ID = @IN_PARTN_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'修改合作伙伴基本信息，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
