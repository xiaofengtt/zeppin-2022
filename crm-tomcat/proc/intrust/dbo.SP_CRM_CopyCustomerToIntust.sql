USE INTRUST
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_CRM_CopyCustomerToIntust @IN_CUST_ID      INT,           --客户ID
                                             @IN_CUST_NO      NVARCHAR(8),   --客户编号
                                             @IN_CUST_NAME    NVARCHAR(100), --客户名称
                                             @IN_CARD_TYPE    NVARCHAR(10),  --证件类型
                                             @IN_CARD_ID      NVARCHAR(40),  --证件号码
                                             @IN_CUST_TYPE    INTEGER,       --1个人2机构
                                             @IN_LEGAL_MAN    NVARCHAR(10),  --法定代表人
                                             @IN_CONTACT_MAN  NVARCHAR(10),  --联系人
                                             @IN_POST_ADDRESS NVARCHAR(100), --通讯地址
                                             @IN_POST_CODE    NVARCHAR(6),   --邮政编码
                                             @IN_E_MAIL       NVARCHAR(30),  --E_MAIL
                                             @IN_MOBILE       NVARCHAR(30),  --手机
                                             @IN_SERVICE_MAN  INT,           --客户经理CRM.TOperator.OP_CODE
                                             @IN_INPUT_MAN    INT            --操作员CRM.TOperator.OP_CODE

AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT, @SBUSI_NAME VARCHAR(40),@SSUMMARY VARCHAR(200)
    SELECT @V_RET_CODE = -20602000, @IBUSI_FLAG = 20602
    SELECT @SBUSI_NAME = '从CRM获取客户数据', @SSUMMARY = '从CRM获取客户数据'

    DECLARE @V_CARD_TYPE_NAME VARCHAR(30), @V_CUST_TYPE_NAME VARCHAR(30), @V_SERVICE_MAN INT, @V_INPUT_MAN INT,
            @V_BOOK_CODE INT
    IF NOT EXISTS(SELECT * FROM TDICTPARAM WHERE TYPE_VALUE = @IN_CARD_TYPE)
        RETURN @V_RET_CODE - 6  --证件类型无效
    SELECT @V_CARD_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_CARD_TYPE
    IF @IN_CUST_TYPE NOT IN (1,2)
        RETURN @V_RET_CODE - 7  --客户类别无效
    SELECT @V_CUST_TYPE_NAME = CASE @IN_CUST_TYPE WHEN 1 THEN '个人'
                                                  WHEN 2 THEN '机构'
                                                ELSE ''
                               END
    --转换CRM操作员为Intrust操作员
    SELECT @V_SERVICE_MAN = INTRUST_Operator FROM TOperatorMap WHERE CRM_Operator = @IN_SERVICE_MAN
    SELECT @V_INPUT_MAN = INTRUST_Operator FROM TOperatorMap WHERE CRM_Operator = @IN_INPUT_MAN
    IF @V_SERVICE_MAN IS NULL
        SELECT @V_SERVICE_MAN = @IN_SERVICE_MAN
    IF @V_INPUT_MAN IS NULL
        SELECT @V_INPUT_MAN = @IN_INPUT_MAN
    SELECT @V_BOOK_CODE = BOOK_CODE FROM TOPERATOR WHERE OP_CODE = @V_INPUT_MAN
    IF @V_BOOK_CODE IS NULL SET @V_BOOK_CODE = 1

    BEGIN TRANSACTION

    IF NOT EXISTS(SELECT * FROM TCUSTOMERINFO WHERE CUST_ID = @IN_CUST_ID)
    BEGIN
        INSERT INTO TCUSTOMERINFO(BOOK_CODE,CUST_NO,CUST_NAME,CARD_TYPE,CARD_ID,CUST_TYPE,LEGAL_MAN,CONTACT_MAN,
                POST_ADDRESS,POST_CODE,E_MAIL,MOBILE,INPUT_MAN, CUST_ID, CARD_TYPE_NAME, CUST_TYPE_NAME,
                WT_FLAG, WT_FLAG_NAME, CUST_LEVEL,CUST_LEVEL_NAME,TOUCH_TYPE,TOUCH_TYPE_NAME,CUST_SOURCE,CUST_SOURCE_NAME,
                CHECK_FLAG, SERVICE_MAN)
            VALUES(@V_BOOK_CODE,@IN_CUST_NO,@IN_CUST_NAME,@IN_CARD_TYPE,@IN_CARD_ID,@IN_CUST_TYPE,@IN_LEGAL_MAN,@IN_CONTACT_MAN,
                @IN_POST_ADDRESS,@IN_POST_CODE,@IN_E_MAIL,@IN_MOBILE,@V_INPUT_MAN, @IN_CUST_ID, @V_CARD_TYPE_NAME, @V_CUST_TYPE_NAME,
                2, '否', '111101', '未签约客户', '110901', '电话', '111006', '潜在客户',
                2, @V_SERVICE_MAN)
    END
    ELSE
        UPDATE TCUSTOMERINFO
            SET CUST_NO      = @IN_CUST_NO,
                CUST_NAME    = @IN_CUST_NAME,
                CARD_TYPE    = @IN_CARD_TYPE,
                CARD_TYPE_NAME = @V_CARD_TYPE_NAME,
                CARD_ID      = @IN_CARD_ID,
                CUST_TYPE    = @IN_CUST_TYPE,
                CUST_TYPE_NAME = @V_CUST_TYPE_NAME,
                LEGAL_MAN    = @IN_LEGAL_MAN,
                CONTACT_MAN  = @IN_CONTACT_MAN,
                POST_ADDRESS = @IN_POST_ADDRESS,
                POST_CODE    = @IN_POST_CODE,
                E_MAIL       = @IN_E_MAIL,
--                MOBILE       = @IN_MOBILE, --不能同步，号码为小号
                INPUT_MAN    = @V_INPUT_MAN,
                STATUS       = '112803',
                STATUS_NAME  = '修改',
                SERVICE_MAN  = @V_SERVICE_MAN
            WHERE CUST_ID = @IN_CUST_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    COMMIT TRANSACTION
    RETURN 100
GO
