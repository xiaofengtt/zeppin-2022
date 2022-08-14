USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE SP_RE_CHECK_TCUSTOMERSCONNECTION @IN_SERIAL_NO  INT, --序号 
											  @IN_STATUS     INT, --审核状态
											  @IN_CUST_ID                         INT,
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
												@IN_VIP_CARD_ID                     NVARCHAR(20) = NULL,   -- VIP卡编号
												@IN_VIP_DATE                        INT = NULL,            -- VIP发卡日期
												@IN_HGTZR_BH                        NVARCHAR(20) = NULL,   -- 合格投资人编号
												@IN_VOC_TYPE                        NVARCHAR(10) = N'',    -- 个人职业/机构行业类别(1142/2142)
												@IN_CARD_VALID_DATE                 INT = NULL,            --客户身份证件有效期限8位日期表示
												@IN_COUNTRY                         NVARCHAR(10) = NULL,   --客户国籍(9997)
												@IN_JG_CUST_TYPE                    NVARCHAR(10) = NULL,   --机构客户类别(9921)，仅在CUST_TYPE=2机构时有效
												@IN_CONTACT_MAN                     NVARCHAR(30) = NULL,   --机构客户联系人
												@IN_EAST_JG_TYPE                    NVARCHAR(12)='',       --机构类型2109（依据EAST委托人类型WTRLX）
												@IN_JG_WTRLX2                       NVARCHAR(2)='',        --机构有效：1金融性公司 2政府 3非金融性公司 4境外金融性公司
												@IN_RE_CHECK_REASON                 NVARCHAR(200)          --复审意见
												
					
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_ERROR NVARCHAR(200)
    DECLARE @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    
    SELECT @IBUSI_FLAG = 655351
    SELECT @SBUSI_NAME = N'审核客户联系方式修改申请'
    SELECT @SSUMMARY = N'审核客户联系方式修改申请'

    --DECLARE @V_PRE_CODE NVARCHAR(16), @V_PREPRODUCT_NAME NVARCHAR(100), @V_PREPRODUCT_ID INT, @V_PRE_SERIAL_NO INT
    
    DECLARE @V_STATUS INT -- -1未审核 0审核未通过 1审核已通过
    DECLARE @V_STATUS_NAME NVARCHAR(50)
    --DECLARE @V_SL_TIME NVARCHAR(50), @V_SL_TYPE_NAME NVARCHAR(50)
    
    DECLARE @V_CUST_ID INT
    DECLARE @V_CUST_NAME NVARCHAR(100)
    DECLARE @V_CUST_TEL NVARCHAR(40)
    DECLARE @V_MOBILE NVARCHAR(40)
    DECLARE @V_O_TEL NVARCHAR(40)
    DECLARE @V_H_TEL NVARCHAR(40)
    DECLARE @V_BP NVARCHAR(40)
    
    DECLARE @V_USER_ID INT
	SELECT @V_USER_ID=USER_ID FROM TSYSTEMINFO 
	DECLARE @RET INT,@V_MODI_CHECK_FLAG INT
	
	SET @V_MODI_CHECK_FLAG=0
	
	DECLARE @OLD_CUST_TEL NVARCHAR(20),@OLD_O_TEL NVARCHAR(20),@OLD_H_TEL NVARCHAR(20),@OLD_MOBILE NVARCHAR(20),@OLD_BP NVARCHAR(60)
	
    ------------------------------------------------------------------------
    BEGIN TRY
    --1.业务逻辑与操作
    --校验
    IF @IN_STATUS < 3
    BEGIN
        SET @V_ERROR = N'审核状态错误！'
        RAISERROR(@V_ERROR,16,1)
    END
    
    IF NOT EXISTS(SELECT 1 FROM TCUSTOMOERS_CONNECTION_MODI WHERE SERIAL_NO = @IN_SERIAL_NO)
    BEGIN
        SET @V_ERROR = N'记录不存在！'
        RAISERROR(@V_ERROR,16,2)
    END
    
	SELECT @V_CUST_ID = CUST_ID,@V_STATUS = [STATUS], @V_STATUS_NAME = STATUS_NAME
	, @V_CUST_TEL = N_CUST_TEL, @V_MOBILE = N_MOBILE, @V_O_TEL = N_O_TEL
	, @V_H_TEL = N_H_TEL, @V_BP = N_BP
	FROM TCUSTOMOERS_CONNECTION_MODI WHERE SERIAL_NO = @IN_SERIAL_NO

	SELECT @V_CUST_NAME=CUST_NAME,@OLD_CUST_TEL = CUST_TEL, @OLD_O_TEL = O_TEL,@OLD_H_TEL = H_TEL,@OLD_MOBILE = MOBILE,@OLD_BP = BP 
	FROM TCustomers WHERE CUST_ID = @V_CUST_ID
	
	IF @V_STATUS <> 3
	BEGIN
        SET @V_ERROR = N'记录已审核，不能审核！'
        RAISERROR(@V_ERROR,16,3)
    END
    
	/*SELECT @V_PREPRODUCT_ID = PREPRODUCT_ID, @V_PRE_CODE = PRE_CODE
        FROM TPRECONTRACT WHERE SERIAL_NO = @V_PRE_SERIAL_NO
    SELECT @V_PREPRODUCT_NAME = PREPRODUCT_NAME FROM TPREPRODUCT WHERE PREPRODUCT_ID = @V_PREPRODUCT_ID*/
    
    IF @IN_STATUS = 4 
    BEGIN
		SET @V_STATUS_NAME = N'审核未通过'
    END
    ELSE IF @IN_STATUS = 5 
    BEGIN
		 SET @V_STATUS_NAME = N'已通过'
    END
    ELSE
    BEGIN
        SET @V_ERROR = N'审核状态错误！'
        RAISERROR(@V_ERROR,16,1)
    END
    ------------------------------------------------------------------------
    BEGIN TRANSACTION

    UPDATE TCUSTOMOERS_CONNECTION_MODI SET [STATUS] = @IN_STATUS, STATUS_NAME = @V_STATUS_NAME 
    ,RE_CHECKER = @IN_INPUT_MAN, RE_CHECK_TIME = GETDATE(), RE_CHECK_REASON = @IN_RE_CHECK_REASON 
        WHERE SERIAL_NO = @IN_SERIAL_NO
	
	IF @IN_STATUS = 5 --审核通过更新客户表数据
	BEGIN
		--添加修改记录
	    IF @V_CUST_TEL IS NULL OR @V_CUST_TEL = ''
		SET @V_CUST_TEL = @OLD_CUST_TEL
	    
		IF LTRIM(RTRIM(ISNULL(@OLD_CUST_TEL,''))) <> LTRIM(RTRIM(ISNULL(@V_CUST_TEL,'')))
		BEGIN
			INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
				VALUES(@V_CUST_ID,N'CUST_TEL',N'联系电话',@OLD_CUST_TEL,@V_CUST_TEL,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
			IF @@ERROR <> 0
			BEGIN
				ROLLBACK TRANSACTION
				RETURN -100
			END
		END
		IF @V_O_TEL IS NULL OR @V_O_TEL = ''
		SET @V_O_TEL = @OLD_O_TEL

		IF LTRIM(RTRIM(ISNULL(@OLD_O_TEL,''))) <> LTRIM(RTRIM(ISNULL(@V_O_TEL,'')))
		BEGIN
			INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
				VALUES(@V_CUST_ID,N'O_TEL',N'单位电话',@OLD_O_TEL,@V_O_TEL,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
			IF @@ERROR <> 0
			BEGIN
				ROLLBACK TRANSACTION
				RETURN -100
			END
		END

		IF @V_H_TEL IS NULL OR @V_H_TEL = ''
		SET @V_H_TEL = @OLD_H_TEL

		IF LTRIM(RTRIM(ISNULL(@OLD_H_TEL,''))) <> LTRIM(RTRIM(ISNULL(@V_H_TEL,'')))
		BEGIN
			INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
				VALUES(@V_CUST_ID,N'H_TEL',N'家庭电话',@OLD_H_TEL,@V_H_TEL,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
			IF @@ERROR <> 0
			BEGIN
				ROLLBACK TRANSACTION
				RETURN -100
			END
		END

		IF @V_MOBILE IS NULL OR @V_MOBILE = ''
		SET @V_MOBILE = @OLD_MOBILE

		IF LTRIM(RTRIM(ISNULL(@OLD_MOBILE,''))) <> LTRIM(RTRIM(ISNULL(@V_MOBILE,'')))
		BEGIN
			INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
				VALUES(@V_CUST_ID,N'MOBILE',N'手机',@OLD_MOBILE,@V_MOBILE,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
			IF @@ERROR <> 0
			BEGIN
				ROLLBACK TRANSACTION
				RETURN -100
			END
		END

		IF @V_BP IS NULL OR @V_BP = ''
		SET @V_BP = @OLD_BP

		IF LTRIM(RTRIM(ISNULL(@OLD_BP,''))) <> LTRIM(RTRIM(ISNULL(@V_BP,'')))
		BEGIN
			INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
				VALUES(@V_CUST_ID,N'BP',N'BP机(手机2)',@OLD_BP,@V_BP,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
			IF @@ERROR <> 0
			BEGIN
				ROLLBACK TRANSACTION
				RETURN -100
			END
		END
		UPDATE TCustomers
		SET O_TEL = @V_O_TEL,
            H_TEL = @V_H_TEL,
            MOBILE = @V_MOBILE,
            BP = @V_BP,
            CUST_TEL = @V_CUST_TEL
        WHERE CUST_ID = @V_CUST_ID
        
        --20180703修改调用单独同步联系方式的Intrust库存储过程（当前只能修改mobile 后期需要拓展修改）
        /*IF @V_USER_ID<>15/*建信不用同步到信托客户表*/
		BEGIN
			EXEC @RET = INTRUST..SP_MODI_TCUSTOMERINFO
                        @IN_CUST_ID              = @V_CUST_ID,
                        @IN_CUST_NAME            = @V_CUST_NAME,
                        @IN_CUST_TEL             = @V_CUST_TEL,
                        @IN_POST_ADDRESS         = @IN_POST_ADDRESS,
                        @IN_POST_CODE            = @IN_POST_CODE,
                        @IN_CARD_TYPE            = @IN_CARD_TYPE,
                        @IN_CARD_ID              = @IN_CARD_ID,
                        @IN_AGE                  = @IN_AGE,
                        @IN_SEX                  = @IN_SEX,
                        @IN_O_TEL                = @V_O_TEL,
                        @IN_H_TEL                = @V_H_TEL,
                        @IN_MOBILE               = @V_MOBILE,
                        @IN_BP                   = @V_BP,
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
						@IN_EAST_JG_TYPE		 = @IN_EAST_JG_TYPE,       --机构类型2109（依据EAST委托人类型WTRLX）
						@IN_JG_WTRLX2            = @IN_JG_WTRLX2           --机构有效：1金融性公司 2政府 3非金融性公司 4境外金融性公司
		END*/
		IF @V_USER_ID<>15/*建信不用同步到信托客户表*/
		BEGIN
			EXEC @RET = INTRUST..SP_CRM_CopyCustomerToIntust_m
						@IN_CUST_ID      = @V_CUST_ID,           --客户ID
                         @IN_CUST_NO      = @IN_CUST_NO,   --客户编号
                         @IN_CUST_NAME    = @V_CUST_NAME, --客户名称
                         @IN_CARD_TYPE    = @IN_CARD_TYPE,  --证件类型
                         @IN_CARD_ID      = @IN_CARD_ID,  --证件号码
                         @IN_CUST_TYPE    = @IN_CUST_TYPE,       --1个人2机构
                         @IN_LEGAL_MAN    = @IN_LEGAL_MAN,  --法定代表人
                         @IN_CONTACT_MAN  = @IN_CONTACT_MAN,  --联系人
                         @IN_POST_ADDRESS = @IN_POST_ADDRESS, --通讯地址
                         @IN_POST_CODE    = @IN_POST_CODE,   --邮政编码
                         @IN_E_MAIL       = @IN_E_MAIL,  --E_MAIL
                         @IN_MOBILE       = @V_MOBILE,  --手机
                         @IN_SERVICE_MAN  = @IN_SERVICE_MAN,           --客户经理CRM.TOperator.OP_CODE
                         @IN_INPUT_MAN    = @IN_INPUT_MAN
		END
	END
	
	
    --2.日志
    SELECT @SSUMMARY = N'审核客户联系方式修改申请，客户名称：' + @V_CUST_NAME
						 + N'审核状态：' + @V_STATUS_NAME
    
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY) VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
    COMMIT TRANSACTION
    END TRY
    --3.异常处理
    BEGIN CATCH
        IF @@TRANCOUNT > 0 ROLLBACK TRANSACTION
        DECLARE @V_ERROR_STR NVARCHAR(1000),@V_ERROR_NUMBER INT,@V_ERROR_SEVERITY INT,@V_ERROR_STATE INT,
                @V_ERROR_PROCEDURE sysname,@V_ERROR_LINE INT,@V_ERROR_MESSAGE NVARCHAR(4000)
        SELECT @V_ERROR_STR = N'Message:%s,Procedure:%s,Line:%d',
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
