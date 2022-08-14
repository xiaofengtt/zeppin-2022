USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TPROPERTY_SURVER   @IN_CUST_ID             INTEGER,                --客户ID
                                            @IN_SURVEY_DATE         INTEGER,                --调查日期
                                            @IN_LINK_ADDRESS        NVARCHAR(100),          --联系地址
                                            @IN_LINK_PHONE          NVARCHAR(100),          --联系电话
                                            @IN_GR_NATION           NVARCHAR(100),          --国籍(个人有效)
                                            @IN_PROFESSION          NVARCHAR(200),          --职业、行业
                                            @IN_BUSINESS            NVARCHAR(800),          --经营范围(机构有效)
                                            @IN_CAPITAL             DECIMAL(16,3),          --注册资金(机构有效)
                                            @IN_SWDJZHM             NVARCHAR(100),          --税务登记证号码(机构有效)
                                            @IN_FR_NAME             NVARCHAR(60),           --法定代表人（负责人）姓名(机构有效)
                                            @IN_FZ_CARD_TYPE        NVARCHAR(30),           --机构:法定代表人（负责人）身份证件类型 个人:身份证件类型
                                            @IN_FZ_CARD_ID          NVARCHAR(60),           --机构:法定代表人（负责人）身份证件号码 个人:身份证件号码
                                            @IN_FZ_CARD_YXQ         INTEGER,                --机构:法定代表人（负责人）身份证件有效期 个人:身份证件有效期限
                                            @IN_GD_NAME             NVARCHAR(60),           --控股股东或实际控制人姓名/名称(机构有效)
                                            @IN_GD_CARD_TYPE        NVARCHAR(30),           --控股股东或实际控制人身份证件类型(机构有效)
                                            @IN_GD_CARD_ID          NVARCHAR(60),           --控股股东或实际控制人身份证件号码(机构有效)
                                            @IN_GD_CARD_YXQ         INTEGER,                --控股股东或实际控制人身份证件有效期(机构有效)
                                            @IN_BL_NAME             NVARCHAR(60),           --授权业务办理人姓名
                                            @IN_BL_CARD_TYPE        NVARCHAR(30),           --授权业务办理人身份证件类型
                                            @IN_BL_CARD_ID          NVARCHAR(60),           --授权业务办理人身份证件号码
                                            @IN_BL_CARD_YXQ         INTEGER,                --授权业务办理人身份证件有效期
                                            @IN_PROPERTY_SOURCE     NVARCHAR(600),          --信托财产来源 2013(INTRUST:1167);多个则用“|”间隔
                                            @IN_KHFXDJ              INTEGER,                --客户风险等级 1高 2中 3低
                                            @IN_INPUT_MAN           INTEGER                 --操作员
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_ERROR NVARCHAR(200), @IBUSI_FLAG INT, @SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    DECLARE @V_FZ_CARD_TYPE_NAME   NVARCHAR(120)          --机构:法定代表人（负责人）身份证件类型名称 个人:身份证件类型名称
    DECLARE @V_GD_CARD_TYPE_NAME   NVARCHAR(120)          --控股股东或实际控制人身份证件类型名称(机构有效)
    DECLARE @V_BL_CARD_TYPE_NAME   NVARCHAR(120)          --授权业务办理人身份证件类型名称
    DECLARE @V_MODI_NO INT,@V_CUST_NAME NVARCHAR(200)
    
    BEGIN TRY
    
    SET @IBUSI_FLAG = 30601
    SET @SBUSI_NAME = '修改客户信托财产调查表'
    
    IF ISNULL(@IN_FZ_CARD_TYPE,'') <> ''
        SELECT @V_FZ_CARD_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_FZ_CARD_TYPE
    IF ISNULL(@IN_GD_CARD_TYPE,'') <> ''
        SELECT @V_GD_CARD_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_GD_CARD_TYPE
    IF ISNULL(@IN_BL_CARD_TYPE,'') <> ''
        SELECT @V_BL_CARD_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_BL_CARD_TYPE
    SELECT @V_CUST_NAME=CUST_NAME FROM TCustomers WHERE CUST_ID=@IN_CUST_ID
    SELECT @V_MODI_NO = MODI_NO FROM TPROPERTY_SURVER WHERE CUST_ID = @IN_CUST_ID
    IF ISNULL(@V_MODI_NO,0) = 0
        SET @V_MODI_NO = 1
    ELSE
        SET @V_MODI_NO = @V_MODI_NO + 1
    
    BEGIN TRANSACTION
    
    UPDATE TPROPERTY_SURVER
        SET SURVEY_DATE         = @IN_SURVEY_DATE,
            CUST_NAME           = @V_CUST_NAME,
            LINK_ADDRESS        = @IN_LINK_ADDRESS,
            LINK_PHONE          = @IN_LINK_PHONE,
            GR_NATION           = @IN_GR_NATION,
            PROFESSION          = @IN_PROFESSION,
            BUSINESS            = @IN_BUSINESS,
            CAPITAL             = @IN_CAPITAL,
            SWDJZHM             = @IN_SWDJZHM,
            FR_NAME             = @IN_FR_NAME,
            FZ_CARD_TYPE        = @IN_FZ_CARD_TYPE,
            FZ_CARD_TYPE_NAME   = @V_FZ_CARD_TYPE_NAME,
            FZ_CARD_ID          = @IN_FZ_CARD_ID,
            FZ_CARD_YXQ         = @IN_FZ_CARD_YXQ,
            GD_NAME             = @IN_GD_NAME,
            GD_CARD_TYPE        = @IN_GD_CARD_TYPE,
            GD_CARD_TYPE_NAME   = @V_GD_CARD_TYPE_NAME,
            GD_CARD_ID          = @IN_GD_CARD_ID,
            GD_CARD_YXQ         = @IN_GD_CARD_YXQ,
            BL_NAME             = @IN_BL_NAME,
            BL_CARD_TYPE        = @IN_BL_CARD_TYPE,
            BL_CARD_TYPE_NAME   = @V_BL_CARD_TYPE_NAME,
            BL_CARD_ID          = @IN_BL_CARD_ID,
            BL_CARD_YXQ         = @IN_BL_CARD_YXQ,
            PROPERTY_SOURCE     = @IN_PROPERTY_SOURCE,
            KHFXDJ              = @IN_KHFXDJ,
            MODI_NO             = @V_MODI_NO,
            MODI_MAN            = @IN_INPUT_MAN,
            MODI_TIME           = GETDATE()
        WHERE CUST_ID=@IN_CUST_ID
    IF @@ROWCOUNT=0
    BEGIN
		INSERT INTO TPROPERTY_SURVER(CUST_ID,SURVEY_DATE,SURVEY_MAN,CUST_NAME,LINK_ADDRESS,LINK_PHONE,GR_NATION,
            PROFESSION,BUSINESS,CAPITAL,SWDJZHM,FR_NAME,FZ_CARD_TYPE,FZ_CARD_TYPE_NAME,FZ_CARD_ID,FZ_CARD_YXQ,
            GD_NAME,GD_CARD_TYPE,GD_CARD_TYPE_NAME,GD_CARD_ID,GD_CARD_YXQ,BL_NAME,BL_CARD_TYPE,BL_CARD_TYPE_NAME,
            BL_CARD_ID,BL_CARD_YXQ,KHFXDJ,INPUT_MAN,PROPERTY_SOURCE)
        VALUES(@IN_CUST_ID,@IN_SURVEY_DATE,@IN_INPUT_MAN,@V_CUST_NAME,@IN_LINK_ADDRESS,@IN_LINK_PHONE,@IN_GR_NATION,
            @IN_PROFESSION,@IN_BUSINESS,@IN_CAPITAL,@IN_SWDJZHM,@IN_FR_NAME,@IN_FZ_CARD_TYPE,@V_FZ_CARD_TYPE_NAME,@IN_FZ_CARD_ID,@IN_FZ_CARD_YXQ,
            @IN_GD_NAME,@IN_GD_CARD_TYPE,@V_GD_CARD_TYPE_NAME,@IN_GD_CARD_ID,@IN_GD_CARD_YXQ,@IN_BL_NAME,@IN_BL_CARD_TYPE,@V_BL_CARD_TYPE_NAME,
            @IN_BL_CARD_ID,@IN_BL_CARD_YXQ,@IN_KHFXDJ,@IN_INPUT_MAN,@IN_PROPERTY_SOURCE)
        SET @SBUSI_NAME = '新建客户信托财产调查表'
    END
        --同步给INTRUST
    UPDATE INTRUST..TPROPERTY_SURVER
        SET SURVEY_DATE         = @IN_SURVEY_DATE,
            CUST_NAME           = @V_CUST_NAME,
            LINK_ADDRESS        = @IN_LINK_ADDRESS,
            LINK_PHONE          = @IN_LINK_PHONE,
            GR_NATION           = @IN_GR_NATION,
            PROFESSION          = @IN_PROFESSION,
            BUSINESS            = @IN_BUSINESS,
            CAPITAL             = @IN_CAPITAL,
            SWDJZHM             = @IN_SWDJZHM,
            FR_NAME             = @IN_FR_NAME,
            FZ_CARD_TYPE        = @IN_FZ_CARD_TYPE,
            FZ_CARD_TYPE_NAME   = @V_FZ_CARD_TYPE_NAME,
            FZ_CARD_ID          = @IN_FZ_CARD_ID,
            FZ_CARD_YXQ         = @IN_FZ_CARD_YXQ,
            GD_NAME             = @IN_GD_NAME,
            GD_CARD_TYPE        = @IN_GD_CARD_TYPE,
            GD_CARD_TYPE_NAME   = @V_GD_CARD_TYPE_NAME,
            GD_CARD_ID          = @IN_GD_CARD_ID,
            GD_CARD_YXQ         = @IN_GD_CARD_YXQ,
            BL_NAME             = @IN_BL_NAME,
            BL_CARD_TYPE        = @IN_BL_CARD_TYPE,
            BL_CARD_TYPE_NAME   = @V_BL_CARD_TYPE_NAME,
            BL_CARD_ID          = @IN_BL_CARD_ID,
            BL_CARD_YXQ         = @IN_BL_CARD_YXQ,
            KHFXDJ              = @IN_KHFXDJ,
            MODI_NO             = @V_MODI_NO,
            MODI_MAN            = @IN_INPUT_MAN,
            MODI_TIME           = GETDATE()
        WHERE CUST_ID=@IN_CUST_ID
    IF @@ROWCOUNT=0
        INSERT INTO INTRUST..TPROPERTY_SURVER(CUST_ID,SURVEY_DATE,SURVEY_MAN,CUST_NAME,LINK_ADDRESS,LINK_PHONE,GR_NATION,
            PROFESSION,BUSINESS,CAPITAL,SWDJZHM,FR_NAME,FZ_CARD_TYPE,FZ_CARD_TYPE_NAME,FZ_CARD_ID,FZ_CARD_YXQ,
            GD_NAME,GD_CARD_TYPE,GD_CARD_TYPE_NAME,GD_CARD_ID,GD_CARD_YXQ,BL_NAME,BL_CARD_TYPE,BL_CARD_TYPE_NAME,
            BL_CARD_ID,BL_CARD_YXQ,KHFXDJ,INPUT_MAN)
        VALUES(@IN_CUST_ID,@IN_SURVEY_DATE,@IN_INPUT_MAN,@V_CUST_NAME,@IN_LINK_ADDRESS,@IN_LINK_PHONE,@IN_GR_NATION,
            @IN_PROFESSION,@IN_BUSINESS,@IN_CAPITAL,@IN_SWDJZHM,@IN_FR_NAME,@IN_FZ_CARD_TYPE,@V_FZ_CARD_TYPE_NAME,@IN_FZ_CARD_ID,@IN_FZ_CARD_YXQ,
            @IN_GD_NAME,@IN_GD_CARD_TYPE,@V_GD_CARD_TYPE_NAME,@IN_GD_CARD_ID,@IN_GD_CARD_YXQ,@IN_BL_NAME,@IN_BL_CARD_TYPE,@V_BL_CARD_TYPE_NAME,
            @IN_BL_CARD_ID,@IN_BL_CARD_YXQ,@IN_KHFXDJ,@IN_INPUT_MAN)

    --2 日志记录
    SET @SSUMMARY = @SBUSI_NAME + N',客户ID:'  + CAST(ISNULL(@IN_CUST_ID,0) AS NVARCHAR(10)) + N',客户名称:'  + ISNULL(@V_CUST_NAME,'')
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)

    COMMIT TRANSACTION
    END TRY

    --3.异常处理
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION
        DECLARE @V_ERROR_STR NVARCHAR(1000),@V_ERROR_NUMBER INT,@V_ERROR_SEVERITY INT,@V_ERROR_STATE INT,
                @V_ERROR_PROCEDURE sysname,@V_ERROR_LINE INT,@V_ERROR_MESSAGE NVARCHAR(4000)
        SELECT @V_ERROR_STR = N'Message:%s<BR><font color = "white">Error:%d,Level:%d,State:%d,Procedure:%s,Line:%d</font>',
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
    RETURN 100
GO
