﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_IMPORT_CUSTOMERS @IN_CUST_NAME       		NVARCHAR(100), 		--客户名称
                                     @IN_CUST_TYPE       		NVARCHAR(10),  		--客户类型
                                     @IN_CARD_TYPE       		NVARCHAR(30),  		--证件类型/有效证件名称
                                     @IN_CARD_ID         		NVARCHAR(40),  		--证件号码/有效证件号码
                                     @IN_ADDRESS         		NVARCHAR(60),  		--通讯地址/联系地址
                                     @IN_POST_CODE       		NVARCHAR(6),   		--邮政编码
                                     @IN_MOBILE          		NVARCHAR(100), 		--手机
                                     @IN_TEL             		NVARCHAR(40),  		--电话号码/固话
                                     @IN_EMAIL           		NVARCHAR(60),  		--EMAIL
                                     @IN_CONTACT_MAN     		NVARCHAR(60),  		--联系人
                                     @IN_SERVICE_MAN     		NVARCHAR(64),  		--客户经理
                                     @IN_WT_FLAG         		NVARCHAR(10),  		--是否委托人
                                     @IN_TOUCH_TYPE      		NVARCHAR(30),  		--首选联系方式
                                     @IN_CUST_SOURCE     		NVARCHAR(30),  		--客户来源
                                     @IN_AGE             		INT,           		--年龄
                                     @IN_POTENTIAL_MONEY 		DEC(16,3),     		--潜在购买力
                                     @IN_INPUT_MAN       		INT,           		--操作员
                                     @IN_CUST_ID         		INT = NULL,         --客户ID（用于导出客户关键信息后，补充详细信息再做导入更新，根据CUST_ID+CUST_NAME更新其他字段）
                                     @IN_BIRTHDAY        		INT = NULL,         --出生日期
                                     @IN_SEX_NAME        		NVARCHAR(10) = NULL,--性别（男、女）
                                     @IN_VOC_TYPE_NAME   		NVARCHAR(30) = NULL,--职业/行业
                                     @IN_H_TEL           		NVARCHAR(60) = NULL,--家庭电话
                                     @IN_O_TEL           		NVARCHAR(60) = NULL,--公司电话
                                     @IN_MOBILE2         		NVARCHAR(60) = NULL,--手机2
                                     @IN_FAX             		NVARCHAR(60) = NULL,--传真
                                     @IN_ADDRESS2        		NVARCHAR(60) = NULL,--地址2
                                     @IN_POST_CODE2      		NVARCHAR(6)  = NULL,--邮编2
                                     @IN_LEGAL_MAN       		NVARCHAR(30) = NULL,--法人姓名
									 @IN_COMPANY_UNIT	 		NVARCHAR(60)=NULL,	--所在单位
								     @IN_COMPANY_DEPART	 		NVARCHAR(30)=NULL,	--所在部门
								     @IN_COMPANY_POSITION		NVARCHAR(60)=NULL, 	--所在职位
									 @IN_CUST_AUM_NAME			NVARCHAR(30)=NULL,  --客户AMU(字典表 2144)
									 @IN_INVES_EXPERINCE    	NVARCHAR(10)=NULL,	--有，无
									 @IN_GOV_PROV_REGIONAL_NAME	NVARCHAR(40)=NULL,	--省(字典表 9999_0000)
									 @IN_GOV_REGIONAL_NAME		NVARCHAR(40)=NULL,	--市(字典表 9999)
									 @IN_RISK_PREF_NAME			NVARCHAR(30)=NULL,	--风险偏好（1172）
									 @IN_HOBBY_PREF			    NVARCHAR(20)=NULL,  --兴趣偏好(1168)
									 @IN_CUST_SOURCE_EXPLAIN	NVARCHAR(60)=NULL,	--客户来源说明
									 @IN_CUST_AGE_GROUP_NAME	NVARCHAR(30)=NULL	--年龄段
									 
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    SET XACT_ABORT ON

    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT, @SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SET @V_RET_CODE = -20601100
    SET @IBUSI_FLAG = 20601

	DECLARE @V_CUST_AUM NVARCHAR(30),@V_CUST_AGE_GROUP NVARCHAR(30),@V_RISK_PREF  NVARCHAR(30)
	DECLARE @V_GOV_PROV_REGIONAL NVARCHAR(60),@V_GOV_REGIONAL NVARCHAR(60)
    DECLARE @V_CUST_TYPE INT, @V_CARD_TYPE NVARCHAR(10), @V_SERVICE_MAN INT, @V_TOUCH_TYPE NVARCHAR(10), @V_CUST_SOURCE NVARCHAR(10),
            @V_WT_FLAG INT, @V_CUST_NO NVARCHAR(8),@V_BIRTHDAY INT,@V_AGE INT,@V_SEX INT,@V_VOC_TYPE NVARCHAR(10),
            @V_CUST_LEVEL NVARCHAR(10), @V_CUST_LEVEL_NAME NVARCHAR(30)

    SELECT @V_CUST_LEVEL = N'111101'
    SELECT @V_BIRTHDAY = 0
    SELECT @V_CUST_LEVEL_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @V_CUST_LEVEL

    --检验输入
    SET @V_SEX = CASE @IN_SEX_NAME WHEN N'男' THEN 1 WHEN N'女' THEN 2 END
    SET @V_WT_FLAG = CASE @IN_WT_FLAG WHEN N'委托人' THEN 1 WHEN N'非委托人' THEN 2 END
    IF @V_WT_FLAG IS NULL
    BEGIN
        SET @V_WT_FLAG = 2
        SET @IN_WT_FLAG = N'非委托人'
    END

    SELECT @V_TOUCH_TYPE = TYPE_VALUE FROM TDICTPARAM WHERE TYPE_ID = 1109 AND TYPE_CONTENT = @IN_TOUCH_TYPE
    IF @V_TOUCH_TYPE IS NULL
    BEGIN
        SET @V_TOUCH_TYPE = 110901
        SET @IN_TOUCH_TYPE = N'电话'
    END

    SELECT @V_CUST_SOURCE = TYPE_VALUE FROM TDICTPARAM WHERE TYPE_ID = 1110 AND TYPE_CONTENT = @IN_CUST_SOURCE
    IF @V_CUST_SOURCE IS NULL
    BEGIN
        SET @V_CUST_SOURCE = 111001
        SET @IN_CUST_SOURCE = N'网络客户'
    END
    SET @V_CUST_TYPE = CASE @IN_CUST_TYPE WHEN N'个人' THEN 1 WHEN N'机构' THEN 2 END
    IF @V_CUST_TYPE IS NULL RETURN @V_RET_CODE - 1 --客户类型无效，必须是个人或机构
    IF @V_CUST_TYPE = 1 --个人
    BEGIN
        IF @IN_CARD_TYPE IS NULL OR @IN_CARD_TYPE = N''
            SELECT @IN_CARD_TYPE = N'其它', @V_CARD_TYPE = N'110899'
        ELSE IF NOT EXISTS(SELECT 1 FROM TDICTPARAM WHERE TYPE_ID = 1108 AND TYPE_CONTENT = @IN_CARD_TYPE)
            SELECT @IN_CARD_TYPE = N'其它', @V_CARD_TYPE = N'110899'
        ELSE
            SELECT @V_CARD_TYPE = TYPE_VALUE FROM TDICTPARAM WHERE TYPE_ID = 1108 AND TYPE_CONTENT = @IN_CARD_TYPE
        IF @IN_VOC_TYPE_NAME IS NOT NULL AND @IN_VOC_TYPE_NAME <> ''
        BEGIN
            IF NOT EXISTS(SELECT 1 FROM TDICTPARAM WHERE TYPE_ID = 1142 AND TYPE_CONTENT = @IN_VOC_TYPE_NAME)
                RETURN @V_RET_CODE - 6 --个人客户职业不存在
            ELSE
                SELECT @V_VOC_TYPE = TYPE_VALUE FROM TDICTPARAM WHERE TYPE_ID = 1142 AND TYPE_CONTENT = @IN_VOC_TYPE_NAME
        END
    END
    ELSE --机构
    BEGIN
        IF @IN_CARD_TYPE IS NULL OR @IN_CARD_TYPE = N''
            SELECT @IN_CARD_TYPE = N'其他', @V_CARD_TYPE = N'210899'
        ELSE IF NOT EXISTS(SELECT 1 FROM TDICTPARAM WHERE TYPE_ID = 2108 AND TYPE_CONTENT = @IN_CARD_TYPE)
            SELECT @IN_CARD_TYPE = N'其他', @V_CARD_TYPE = N'210899'
        ELSE
            SELECT @V_CARD_TYPE = TYPE_VALUE FROM TDICTPARAM WHERE TYPE_ID = 2108 AND TYPE_CONTENT = @IN_CARD_TYPE
        IF @IN_VOC_TYPE_NAME IS NOT NULL AND @IN_VOC_TYPE_NAME <> ''
        BEGIN
            IF NOT EXISTS(SELECT 1 FROM TDICTPARAM WHERE TYPE_ID = 2142 AND TYPE_CONTENT = @IN_VOC_TYPE_NAME)
                RETURN @V_RET_CODE - 7 --机构客户行业不存在
            ELSE
                SELECT @V_VOC_TYPE = TYPE_VALUE FROM TDICTPARAM WHERE TYPE_ID = 2142 AND TYPE_CONTENT = @IN_VOC_TYPE_NAME
        END
    END
    IF @IN_CARD_ID IS NULL SET @IN_CARD_ID = N'未知'

    --如果是个人客户，证件类型是身份证，且未输入生日时，根据15位和18位的情况生成生日变量
    IF @V_CUST_TYPE = 1 AND @V_CARD_TYPE = N'110801'
    BEGIN
        IF LEN(@IN_CARD_ID)=15
        BEGIN
            SELECT @V_BIRTHDAY = CONVERT(INT,N'19'+SUBSTRING(@IN_CARD_ID,7,6))
        END
        ELSE IF LEN(@IN_CARD_ID) = 18
        BEGIN
            SELECT @V_BIRTHDAY = CONVERT(INT,SUBSTRING(@IN_CARD_ID,7,8))
        END
        SELECT @V_AGE = YEAR(GETDATE()) - (@V_BIRTHDAY/10000)
    END
    IF ISNULL(@IN_AGE,0) = 0 AND @V_AGE IS NOT NULL
        SET @IN_AGE = @V_AGE
    IF ISNULL(@IN_BIRTHDAY,0) = 0 AND @V_BIRTHDAY IS NOT NULL
        SET @IN_BIRTHDAY = @V_BIRTHDAY

	SELECT @V_RISK_PREF = TYPE_VALUE FROM TDICTPARAM WHERE TYPE_CONTENT = @IN_RISK_PREF_NAME	
    SELECT @V_SERVICE_MAN = OP_CODE FROM TOPERATOR WHERE OP_NAME = @IN_SERVICE_MAN
    IF @V_SERVICE_MAN IS NULL SET @V_SERVICE_MAN = @IN_INPUT_MAN  --RETURN @V_RET_CODE - 2 --客户经理不存在
    --如果IN_CUST_ID不为空，判断是否存在
    IF ISNULL(@IN_CUST_ID,0) > 0
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM TCustomers WHERE CUST_ID = @IN_CUST_ID)
            RETURN @V_RET_CODE - 4 --提供了客户ID但不存在该记录
        ELSE --提供了正确的CUST_ID,验证CUST_NAME是否相同，不同则抛错，不允许修改客户名称
        BEGIN
            IF NOT EXISTS(SELECT 1 FROM TCustomers WHERE CUST_ID = @IN_CUST_ID AND CUST_NAME = @IN_CUST_NAME)
                RETURN @V_RET_CODE - 5 --提供了客户ID但提供的客户名称不一致
        END
    END

    --分布式标志
    DECLARE @V_BOOK_CODE INT, @V_INTRUST_OPERATOR INT, @RET INT, @V_DT_INTRUST INT, @V_ReceiveServices INT
    SELECT @V_DT_INTRUST = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'DT_INTRUST'
    SELECT @V_ReceiveServices = SUM(ServiceType) FROM TServiceDefine
    --生成CUST_ID
    DECLARE @V_MAX_ID INT, @V_CUST_ID INT, @V_CUST_ID2 INT, @V_MAX_ID2 INT
   
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
    SELECT @V_CUST_NO = CONVERT(CHAR(8),@V_CUST_TYPE * 10000000+@V_MAX_ID)
    IF @V_CUST_TYPE = 1
        SELECT @V_CUST_NO = N'0' + RIGHT(@V_CUST_NO,7)
    ELSE
        SELECT @V_CUST_NO = N'J' + RIGHT(@V_CUST_NO,7)

	SELECT @V_CUST_AUM = TYPE_VALUE FROM TDICTPARAM WHERE TYPE_CONTENT = @IN_CUST_AUM_NAME
	SELECT @V_CUST_AGE_GROUP = TYPE_VALUE FROM TDICTPARAM WHERE TYPE_CONTENT = @IN_CUST_AGE_GROUP_NAME
	
	--客户省市
	IF ISNULL(@IN_GOV_PROV_REGIONAL_NAME,'') <> '' 
	BEGIN
		IF NOT EXISTS(SELECT * FROM INTRUST..TDICTPARAM WHERE TYPE_ID = 9999 AND TYPE_VALUE LIKE '9999__0000' AND TYPE_CONTENT = @IN_GOV_PROV_REGIONAL_NAME) 
		BEGIN
			RETURN  N'导入省：'+@IN_GOV_PROV_REGIONAL_NAME+ N'不存在!'
		END
		SELECT @V_GOV_PROV_REGIONAL = TYPE_VALUE FROM INTRUST..TDICTPARAM WHERE TYPE_ID = 9999 AND TYPE_VALUE LIKE '9999__0000' AND TYPE_CONTENT = @IN_GOV_PROV_REGIONAL_NAME
		SELECT TOP 1 @V_GOV_REGIONAL = TYPE_VALUE FROM INTRUST..TDICTPARAM WHERE TYPE_ID = 9999 AND TYPE_CONTENT LIKE @IN_GOV_REGIONAL_NAME+'%'
	END
		
    --开始事务
	--无分布式或本地信托数据库
    BEGIN TRANSACTION
    IF @V_INTRUST_OPERATOR IS NULL --从sso映射表中未取到Intrust操作员时，使用CRM操作员
        SELECT @V_INTRUST_OPERATOR = @IN_INPUT_MAN
    IF @V_BOOK_CODE IS NULL --最后取不到使用默认值1
        SELECT @V_BOOK_CODE = 1

    IF EXISTS(SELECT 1 FROM TCustomers WHERE CUST_ID = @IN_CUST_ID)
    BEGIN --已存在客户更新(不修改客户名称、客户编号)
        UPDATE TCustomers SET
            CUST_TYPE        = @V_CUST_TYPE,
            CUST_TYPE_NAME   = @IN_CUST_TYPE,
            CARD_TYPE        = @V_CARD_TYPE,
            CARD_TYPE_NAME   = @IN_CARD_TYPE,
            CARD_ID          = @IN_CARD_ID,
            POST_ADDRESS     = @IN_ADDRESS,
            POST_CODE        = @IN_POST_CODE,
            MOBILE           = @IN_MOBILE,
            SERVICE_MAN      = @V_SERVICE_MAN,
            BIRTHDAY         = @IN_BIRTHDAY,
            AGE              = @IN_AGE,
            POTENTIAL_MONEY  = @IN_POTENTIAL_MONEY,
            E_MAIL           = @IN_EMAIL,
            CONTACT_MAN      = @IN_CONTACT_MAN,
            CUST_TEL         = @IN_TEL,
            WT_FLAG          = @V_WT_FLAG,
            WT_FLAG_NAME     = @IN_WT_FLAG,
            TOUCH_TYPE       = @V_TOUCH_TYPE,
            TOUCH_TYPE_NAME  = @IN_TOUCH_TYPE,
            CUST_SOURCE      = @V_CUST_SOURCE,
            CUST_SOURCE_NAME = @IN_CUST_SOURCE,
            INPUT_MAN        = @IN_INPUT_MAN,
			COMPANY_UNIT	 = @IN_COMPANY_UNIT,
			COMPANY_DEPART	 = @IN_COMPANY_DEPART,
			COMPANY_POSITION = @IN_COMPANY_POSITION,
            --SUMMARY          = '数据导入',
            SEX              = ISNULL(@V_SEX,SEX),  --更新的时候，以下字段如果是空值则不更新，避免覆盖已存在的值
            SEX_NAME         = ISNULL(@IN_SEX_NAME,SEX_NAME),
            VOC_TYPE         = ISNULL(@V_VOC_TYPE,VOC_TYPE),
            VOC_TYPE_NAME    = ISNULL(@IN_VOC_TYPE_NAME,VOC_TYPE_NAME),
            H_TEL            = ISNULL(@IN_H_TEL,H_TEL),
            O_TEL            = ISNULL(@IN_O_TEL,O_TEL),
            BP               = ISNULL(@IN_MOBILE2,BP),
            FAX              = ISNULL(@IN_FAX,FAX),
            POST_ADDRESS2    = ISNULL(@IN_ADDRESS2,POST_ADDRESS2),
            POST_CODE2       = ISNULL(@IN_POST_CODE2,POST_CODE2),
            LEGAL_MAN        = ISNULL(@IN_LEGAL_MAN,LEGAL_MAN),
			CUST_AUM 		 = @V_CUST_AUM,
			CUST_AUM_NAME    = @IN_CUST_AUM_NAME,
			CUST_AGE_GROUP   = @V_CUST_AGE_GROUP,
			CUST_AGE_GROUP_NAME = @IN_CUST_AGE_GROUP_NAME,
			INVES_EXPERINCE     = @IN_INVES_EXPERINCE,
			CUST_SOURCE_EXPLAIN = @IN_CUST_SOURCE_EXPLAIN,
			GOV_PROV_REGIONAL = @V_GOV_PROV_REGIONAL,
			GOV_PROV_REGIONAL_NAME = @IN_GOV_PROV_REGIONAL_NAME,
			GOV_REGIONAL      = @V_GOV_REGIONAL,
			GOV_REGIONAL_NAME = @IN_GOV_REGIONAL_NAME,
			RISK_PREF		  = @V_RISK_PREF,
			RISK_PREF_NAME	  = @IN_RISK_PREF_NAME,
			HOBBY_PREF		  = @IN_HOBBY_PREF
        WHERE CUST_ID = @IN_CUST_ID
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
		IF EXISTS(SELECT 1 FROM INTRUST..TCUSTOMERINFO WHERE CUST_ID = @IN_CUST_ID)
		BEGIN
			UPDATE INTRUST..TCUSTOMERINFO SET
				CUST_TYPE        = @V_CUST_TYPE,
				CUST_TYPE_NAME   = @IN_CUST_TYPE,
				CARD_TYPE        = @V_CARD_TYPE,
				CARD_TYPE_NAME   = @IN_CARD_TYPE,
				CARD_ID          = @IN_CARD_ID,
				POST_ADDRESS     = @IN_ADDRESS,
				POST_CODE        = @IN_POST_CODE,
				MOBILE           = @IN_MOBILE,
				SERVICE_MAN      = @V_SERVICE_MAN,
				BIRTHDAY         = @IN_BIRTHDAY,
				AGE              = @IN_AGE,
				E_MAIL           = @IN_EMAIL,
				CONTACT_MAN      = @IN_CONTACT_MAN,
				CUST_TEL         = @IN_TEL,
				WT_FLAG          = @V_WT_FLAG,
				WT_FLAG_NAME     = @IN_WT_FLAG,
				TOUCH_TYPE       = @V_TOUCH_TYPE,
				TOUCH_TYPE_NAME  = @IN_TOUCH_TYPE,
				CUST_SOURCE      = @V_CUST_SOURCE,
				CUST_SOURCE_NAME = @IN_CUST_SOURCE,
				INPUT_MAN        = @IN_INPUT_MAN,
				/* COMPANY_UNIT	 = @IN_COMPANY_UNIT,
				COMPANY_DEPART	 = @IN_COMPANY_DEPART,
				COMPANY_POSITION = @IN_COMPANY_POSITION, */
				--SUMMARY          = '数据导入',
				SEX              = ISNULL(@V_SEX,SEX),  --更新的时候，以下字段如果是空值则不更新，避免覆盖已存在的值
				SEX_NAME         = ISNULL(@IN_SEX_NAME,SEX_NAME),
				VOC_TYPE         = ISNULL(@V_VOC_TYPE,VOC_TYPE),
				VOC_TYPE_NAME    = ISNULL(@IN_VOC_TYPE_NAME,VOC_TYPE_NAME),
				H_TEL            = ISNULL(@IN_H_TEL,H_TEL),
				O_TEL            = ISNULL(@IN_O_TEL,O_TEL),
				BP               = ISNULL(@IN_MOBILE2,BP),
				FAX              = ISNULL(@IN_FAX,FAX),
				POST_ADDRESS2    = ISNULL(@IN_ADDRESS2,POST_ADDRESS2),
				POST_CODE2       = ISNULL(@IN_POST_CODE2,POST_CODE2),
				LEGAL_MAN        = ISNULL(@IN_LEGAL_MAN,LEGAL_MAN)
			WHERE CUST_ID = @IN_CUST_ID
			IF @@ERROR <> 0
			BEGIN
				ROLLBACK TRANSACTION
				RETURN -100
			END
		END
    END
    ELSE  --未传入CUST_ID
    BEGIN
        IF EXISTS(SELECT 1 FROM TCustomers WHERE CUST_NAME = @IN_CUST_NAME AND CARD_TYPE_NAME = @IN_CARD_TYPE AND CARD_ID = @IN_CARD_ID AND STATUS <> '112805')
        BEGIN --根据证件号码更新
            UPDATE TCustomers SET
                CUST_TYPE        = @V_CUST_TYPE,
                CUST_TYPE_NAME   = @IN_CUST_TYPE,
                POST_ADDRESS     = @IN_ADDRESS,
                POST_CODE        = @IN_POST_CODE,
                MOBILE           = @IN_MOBILE,
                SERVICE_MAN      = @V_SERVICE_MAN,
                BIRTHDAY         = @IN_BIRTHDAY,
                AGE              = @IN_AGE,
                POTENTIAL_MONEY  = @IN_POTENTIAL_MONEY,
                E_MAIL           = @IN_EMAIL,
                CONTACT_MAN      = @IN_CONTACT_MAN,
                CUST_TEL         = @IN_TEL,
                WT_FLAG          = @V_WT_FLAG,
                WT_FLAG_NAME     = @IN_WT_FLAG,
                TOUCH_TYPE       = @V_TOUCH_TYPE,
                TOUCH_TYPE_NAME  = @IN_TOUCH_TYPE,
                CUST_SOURCE      = @V_CUST_SOURCE,
                CUST_SOURCE_NAME = @IN_CUST_SOURCE,
                INPUT_MAN        = @IN_INPUT_MAN,
				COMPANY_UNIT	 = @IN_COMPANY_UNIT,
				COMPANY_DEPART	 = @IN_COMPANY_DEPART,
				COMPANY_POSITION = @IN_COMPANY_POSITION,
                --SUMMARY          = '数据导入',
                SEX              = ISNULL(@V_SEX,SEX),  --更新的时候，以下字段如果是空值则不更新，避免覆盖已存在的值
                SEX_NAME         = ISNULL(@IN_SEX_NAME,SEX_NAME),
                VOC_TYPE         = ISNULL(@V_VOC_TYPE,VOC_TYPE),
                VOC_TYPE_NAME    = ISNULL(@IN_VOC_TYPE_NAME,VOC_TYPE_NAME),
                H_TEL            = ISNULL(@IN_H_TEL,H_TEL),
                O_TEL            = ISNULL(@IN_O_TEL,O_TEL),
                BP               = ISNULL(@IN_MOBILE2,BP),
                FAX              = ISNULL(@IN_FAX,FAX),
                POST_ADDRESS2    = ISNULL(@IN_ADDRESS2,POST_ADDRESS2),
                POST_CODE2       = ISNULL(@IN_POST_CODE2,POST_CODE2),
                LEGAL_MAN        = ISNULL(@IN_LEGAL_MAN,LEGAL_MAN),

				CUST_AUM 		 = @V_CUST_AUM,
				CUST_AUM_NAME    = @IN_CUST_AUM_NAME,
				CUST_AGE_GROUP   = @V_CUST_AGE_GROUP,
				CUST_AGE_GROUP_NAME = @IN_CUST_AGE_GROUP_NAME,
				INVES_EXPERINCE     = @IN_INVES_EXPERINCE,
				CUST_SOURCE_EXPLAIN = @IN_CUST_SOURCE_EXPLAIN,
				GOV_PROV_REGIONAL = @V_GOV_PROV_REGIONAL,
				GOV_PROV_REGIONAL_NAME = @IN_GOV_PROV_REGIONAL_NAME,
				GOV_REGIONAL      = @V_GOV_REGIONAL,
				GOV_REGIONAL_NAME = @IN_GOV_REGIONAL_NAME,
				RISK_PREF		  = @V_RISK_PREF,
				RISK_PREF_NAME	  = @IN_RISK_PREF_NAME,
				HOBBY_PREF		  = @IN_HOBBY_PREF
				
            WHERE CUST_NAME = @IN_CUST_NAME AND CARD_TYPE_NAME = @IN_CARD_TYPE AND CARD_ID = @IN_CARD_ID
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END
		END
        ELSE --潜在客户，不需要插入到INTRUST库
		BEGIN
			INSERT INTO TCustomers (CUST_ID,LIST_ID,CUST_NAME,CUST_NO,CUST_TYPE,CUST_TYPE_NAME,CARD_TYPE,CARD_TYPE_NAME,CARD_ID,
					POST_ADDRESS,POST_CODE,MOBILE,SERVICE_MAN,BIRTHDAY,AGE,POTENTIAL_MONEY,IS_DEAL,E_MAIL,CONTACT_MAN,CUST_TEL,
					WT_FLAG,WT_FLAG_NAME,TOUCH_TYPE,TOUCH_TYPE_NAME,CUST_SOURCE,CUST_SOURCE_NAME,INPUT_MAN,CHECK_FLAG,SUMMARY,
					SEX,SEX_NAME,VOC_TYPE,VOC_TYPE_NAME,H_TEL,O_TEL,BP,FAX,POST_ADDRESS2,POST_CODE2,LEGAL_MAN,
					PinYinSimple, ReceiveServices,COMPANY_UNIT,COMPANY_DEPART,COMPANY_POSITION,
					CUST_AUM,CUST_AUM_NAME,CUST_AGE_GROUP,CUST_AGE_GROUP_NAME,   
					INVES_EXPERINCE,CUST_SOURCE_EXPLAIN,GOV_PROV_REGIONAL,
					GOV_PROV_REGIONAL_NAME,GOV_REGIONAL,GOV_REGIONAL_NAME,RISK_PREF,RISK_PREF_NAME,HOBBY_PREF)
				SELECT @V_CUST_ID, @V_MAX_ID, @IN_CUST_NAME,@V_CUST_NO,@V_CUST_TYPE,@IN_CUST_TYPE,@V_CARD_TYPE,@IN_CARD_TYPE,@IN_CARD_ID,
				    @IN_ADDRESS,@IN_POST_CODE,@IN_MOBILE,@V_SERVICE_MAN,@IN_BIRTHDAY,@IN_AGE,@IN_POTENTIAL_MONEY,2,@IN_EMAIL,@IN_CONTACT_MAN,@IN_TEL,
				    @V_WT_FLAG,@IN_WT_FLAG,@V_TOUCH_TYPE,@IN_TOUCH_TYPE,@V_CUST_SOURCE,@IN_CUST_SOURCE,@IN_INPUT_MAN,2,'数据导入',
				    @V_SEX,@IN_SEX_NAME,@V_VOC_TYPE,@IN_VOC_TYPE_NAME,@IN_H_TEL,@IN_O_TEL,@IN_MOBILE2,@IN_FAX,@IN_ADDRESS2,@IN_POST_CODE2,@IN_LEGAL_MAN,
				    EFCRM.dbo.fGetPy(@IN_CUST_NAME), @V_ReceiveServices,@IN_COMPANY_UNIT,@IN_COMPANY_DEPART,@IN_COMPANY_POSITION,
				    @V_CUST_AUM,@IN_CUST_AUM_NAME,@V_CUST_AGE_GROUP,@IN_CUST_AGE_GROUP_NAME,
					@IN_INVES_EXPERINCE,@IN_CUST_SOURCE_EXPLAIN,@V_GOV_PROV_REGIONAL,
					@IN_GOV_PROV_REGIONAL_NAME,@V_GOV_REGIONAL,@IN_GOV_REGIONAL_NAME,
					@V_RISK_PREF,@IN_RISK_PREF_NAME,@IN_HOBBY_PREF
		END
		IF EXISTS(SELECT 1 FROM INTRUST..TCUSTOMERINFO WHERE CUST_NAME = @IN_CUST_NAME AND CARD_TYPE_NAME = @IN_CARD_TYPE AND CARD_ID = @IN_CARD_ID)
		BEGIN
			UPDATE INTRUST..TCUSTOMERINFO SET
				CUST_TYPE        = @V_CUST_TYPE,
				CUST_TYPE_NAME   = @IN_CUST_TYPE,
				POST_ADDRESS     = @IN_ADDRESS,
				POST_CODE        = @IN_POST_CODE,
				MOBILE           = @IN_MOBILE,
				SERVICE_MAN      = @V_SERVICE_MAN,
				BIRTHDAY         = @IN_BIRTHDAY,
				AGE              = @IN_AGE,
				E_MAIL           = @IN_EMAIL,
				CONTACT_MAN      = @IN_CONTACT_MAN,
				CUST_TEL         = @IN_TEL,
				WT_FLAG          = @V_WT_FLAG,
				WT_FLAG_NAME     = @IN_WT_FLAG,
				TOUCH_TYPE       = @V_TOUCH_TYPE,
				TOUCH_TYPE_NAME  = @IN_TOUCH_TYPE,
				CUST_SOURCE      = @V_CUST_SOURCE,
				CUST_SOURCE_NAME = @IN_CUST_SOURCE,
				INPUT_MAN        = @IN_INPUT_MAN,
				
				--COMPANY_UNIT	 = @IN_COMPANY_UNIT,
				--COMPANY_DEPART	 = @IN_COMPANY_DEPART,
				--COMPANY_POSITION = @IN_COMPANY_POSITION,
				--SUMMARY          = '数据导入',
				SEX              = ISNULL(@V_SEX,SEX),  --更新的时候，以下字段如果是空值则不更新，避免覆盖已存在的值
				SEX_NAME         = ISNULL(@IN_SEX_NAME,SEX_NAME),
				VOC_TYPE         = ISNULL(@V_VOC_TYPE,VOC_TYPE),
				VOC_TYPE_NAME    = ISNULL(@IN_VOC_TYPE_NAME,VOC_TYPE_NAME),
				H_TEL            = ISNULL(@IN_H_TEL,H_TEL),
				O_TEL            = ISNULL(@IN_O_TEL,O_TEL),
				BP               = ISNULL(@IN_MOBILE2,BP),
				FAX              = ISNULL(@IN_FAX,FAX),
				POST_ADDRESS2    = ISNULL(@IN_ADDRESS2,POST_ADDRESS2),
				POST_CODE2       = ISNULL(@IN_POST_CODE2,POST_CODE2),
				LEGAL_MAN        = ISNULL(@IN_LEGAL_MAN,LEGAL_MAN)
			WHERE CUST_NAME = @IN_CUST_NAME AND CARD_TYPE_NAME = @IN_CARD_TYPE AND CARD_ID = @IN_CARD_ID
			IF @@ERROR <> 0
			BEGIN
				ROLLBACK TRANSACTION
				RETURN -100
			END
		END
	END
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SBUSI_NAME = N'导入客户信息'
    SET @SSUMMARY = N'导入客户信息'
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
