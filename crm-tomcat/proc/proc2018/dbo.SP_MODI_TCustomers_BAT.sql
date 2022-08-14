﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TCustomers_BAT @IN_CUST_ID INT,
                         @IN_CARD_TYPE              NVARCHAR(10),    --证件类型
                         @IN_CARD_ID                NVARCHAR(40),    --证件号码
                         @IN_E_MAIL                 NVARCHAR(30),    --EMAIL
                         @IN_FAX                    NVARCHAR(20),    --传真
                         @IN_MOBILE                 NVARCHAR(100),   --手机
                         @IN_CUST_TEL               NVARCHAR(20),    --固定电话
                         @IN_POST_ADDRESS           NVARCHAR(60),    --联系地址
                         @IN_POST_CODE              NVARCHAR(6),     --邮政编码
                         @IN_SERVICE_MAN            INT ,            --客户经理
                         @IN_INPUT_MAN              INT              --操作员
                                    
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    SET XACT_ABORT ON

    DECLARE @V_BOOK_CODE INT, @V_INTRUST_OPERATOR INT
    DECLARE @RET INT
    
    DECLARE @V_STATUS NVARCHAR(10),@V_STATUS_NAME NVARCHAR(30),@V_CHECK_FLAG INT, @V_TEMP1 INT
    DECLARE @V_CARD_TYPE_NAME NVARCHAR(30)
    DECLARE @V_CUST_NAME NVARCHAR(100),@V_CUST_TYPE INT
    DECLARE @OLD_CUST_TEL NVARCHAR(20),@OLD_MOBILE NVARCHAR(20),@OLD_FAX NVARCHAR(20)
    DECLARE @OLD_POST_ADDRESS NVARCHAR(60)
    DECLARE @OLD_POST_CODE NVARCHAR(6), @OLD_SERVICE_MAN INT
    DECLARE @OLD_CARD_TYPE NVARCHAR(10),@OLD_CUST_TYPE_NAME NVARCHAR(10)
    DECLARE @OLD_CARD_TYPE_NAME NVARCHAR(30),@OLD_EMAIL NVARCHAR(30)
    DECLARE @OLD_CARD_ID NVARCHAR(40)
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@V_MODI_CHECK_FLAG INT
    DECLARE @SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    
    SELECT @V_RET_CODE = -20602000
    SELECT @IBUSI_FLAG = 20602
    SELECT @SBUSI_NAME = N'修改客户基本信息'
    SELECT @SSUMMARY = N'修改客户基本信息'

    IF NOT EXISTS(SELECT 1 FROM TCustomers WHERE CUST_ID = @IN_CUST_ID)
       RETURN @V_RET_CODE-1   -- 客户编号不存在

    SELECT @OLD_CARD_TYPE = CARD_TYPE,@OLD_CARD_TYPE_NAME = CARD_TYPE_NAME,@OLD_CARD_ID = CARD_ID,
           @OLD_POST_ADDRESS = POST_ADDRESS,@OLD_POST_CODE = POST_CODE,
           @OLD_EMAIL = E_MAIL,@OLD_FAX = FAX,@OLD_MOBILE = MOBILE,@OLD_CUST_TEL=CUST_TEL,
           @OLD_SERVICE_MAN=SERVICE_MAN,
           @V_CUST_NAME = CUST_NAME,@V_STATUS = STATUS,@V_CUST_TYPE=CUST_TYPE
      FROM TCustomers WHERE CUST_ID = @IN_CUST_ID
	--如果操作员的角色中存在保密限制的角色，则根据保密优先的原则，保密限制后，就没有修改权限了
    IF EXISTS(SELECT 1 FROM TOPROLE WHERE OP_CODE = @IN_INPUT_MAN AND ROLE_ID IN(SELECT ROLE_ID FROM TROLE WHERE FLAG_ENCRYPTION = 1))
	BEGIN
		IF @OLD_SERVICE_MAN<>@IN_INPUT_MAN
			RAISERROR('客户处于加密状态，没有修改权限',16,1)
	END
    IF @IN_SERVICE_MAN IS NULL OR @IN_SERVICE_MAN = 0 SELECT @IN_SERVICE_MAN = @OLD_SERVICE_MAN
  
    BEGIN TRY
    --本地信托数据库
    BEGIN TRANSACTION
    IF @V_STATUS = N'112805'
       RAISERROR('已销户不能修改',16,2)  -- 已销户不能修改
	--客户信息变更审核 1是 2否
	IF EXISTS (SELECT 1 FROM TSYSCONTROL WHERE FLAG_TYPE='CUSTINFO_CHANGE' AND VALUE=1)
		SET @V_MODI_CHECK_FLAG =1
	ELSE SET @V_MODI_CHECK_FLAG=0
    --------------------修改TCustomerChanges表---------------------
    IF LTRIM(RTRIM(ISNULL(@OLD_CARD_TYPE,''))) <> LTRIM(RTRIM(ISNULL(@IN_CARD_TYPE,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            SELECT @IN_CUST_ID,N'CARD_TYPE_NAME',N'证件类型',@OLD_CARD_TYPE_NAME,
            (SELECT TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_ID=(CASE @V_CUST_TYPE WHEN 1 THEN 1108 ELSE 2108 END) AND TYPE_VALUE=@IN_CARD_TYPE),
            @IN_INPUT_MAN,@V_MODI_CHECK_FLAG
    END

    IF LTRIM(RTRIM(ISNULL(@OLD_CARD_ID,''))) <> LTRIM(RTRIM(ISNULL(@IN_CARD_ID,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'CARD_ID',N'证件号码',@OLD_CARD_ID,@IN_CARD_ID,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
    END
    IF LTRIM(RTRIM(ISNULL(@OLD_EMAIL,''))) <> LTRIM(RTRIM(ISNULL(@IN_E_MAIL,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'E_MAIL',N'EMAIL',@OLD_EMAIL,@IN_E_MAIL,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
    END
    IF LTRIM(RTRIM(ISNULL(@OLD_FAX,''))) <> LTRIM(RTRIM(ISNULL(@IN_FAX,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'FAX',N'传真',@OLD_FAX,@IN_FAX,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
    END
    IF LTRIM(RTRIM(ISNULL(@OLD_MOBILE,''))) <> LTRIM(RTRIM(ISNULL(@IN_MOBILE,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'MOBILE',N'手机',@OLD_MOBILE,@IN_MOBILE,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
    END
    IF LTRIM(RTRIM(ISNULL(@OLD_CUST_TEL,''))) <> LTRIM(RTRIM(ISNULL(@IN_CUST_TEL,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'CUST_TEL',N'固定电话',@OLD_CUST_TEL,@IN_CUST_TEL,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
    END
    IF LTRIM(RTRIM(ISNULL(@OLD_POST_ADDRESS,''))) <> LTRIM(RTRIM(ISNULL(@IN_POST_ADDRESS,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'POST_ADDRESS',N'邮寄地址',@OLD_POST_ADDRESS,@IN_POST_ADDRESS,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
    END
    IF LTRIM(RTRIM(ISNULL(@OLD_POST_CODE,''))) <> LTRIM(RTRIM(ISNULL(@IN_POST_CODE,'')))
    BEGIN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'POST_CODE',N'邮政编码',@OLD_POST_CODE,@IN_POST_CODE,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
    END
	IF ISNULL(@OLD_SERVICE_MAN,0) <> ISNULL(@IN_SERVICE_MAN,0)
    BEGIN
        DECLARE @OLD_SERVICE_NAME NVARCHAR(20),@V_SERVICE_NAME NVARCHAR(20)
        SELECT @OLD_SERVICE_NAME = OP_NAME FROM TOPERATOR WHERE OP_CODE = @OLD_SERVICE_MAN
        SELECT @V_SERVICE_NAME = OP_NAME FROM TOPERATOR WHERE OP_CODE = @IN_SERVICE_MAN
        INSERT INTO TCustomerChanges(CUST_ID,FIELD_NAME,FIELD_CN_NAME,OLD_FIELD_INFO,NEW_FIELD_INFO,INPUT_MAN,CHECK_FLAG)
            VALUES(@IN_CUST_ID,N'SERVICE_MAN',N'客户经理',@OLD_SERVICE_NAME,@V_SERVICE_NAME,@IN_INPUT_MAN,@V_MODI_CHECK_FLAG)
        IF @V_MODI_CHECK_FLAG=0 --不审核
        BEGIN
            UPDATE INTRUST..TCONTRACT SET SERVICE_MAN = @IN_SERVICE_MAN WHERE CUST_ID = @IN_CUST_ID AND CHECK_FLAG = 2
        END
    END
    
    
	--------------------------------------------------------------------
    IF @V_MODI_CHECK_FLAG=0
	BEGIN--不审核时运行，要审核时，下面的动作放到审核的过程中去
    UPDATE TCustomers
        SET CUST_TEL = @IN_CUST_TEL,
            POST_ADDRESS = @IN_POST_ADDRESS,
            POST_CODE = @IN_POST_CODE,
            CARD_TYPE = @IN_CARD_TYPE,
            CARD_TYPE_NAME = @V_CARD_TYPE_NAME,
            CARD_ID = @IN_CARD_ID,
            MOBILE = @IN_MOBILE,
            FAX = @IN_FAX,
            E_MAIL = @IN_E_MAIL,
            MODI_MAN = @IN_INPUT_MAN,
            MODI_TIME = dbo.GETDATEINT(getdate()),
            SERVICE_MAN = @IN_SERVICE_MAN
        WHERE CUST_ID = @IN_CUST_ID
    --
    --同步到信托客户表中
    UPDATE INTRUST..TCUSTOMERINFO
        SET CUST_TEL = @IN_CUST_TEL,
            POST_ADDRESS = @IN_POST_ADDRESS,
            POST_CODE = @IN_POST_CODE,
            CARD_TYPE = @IN_CARD_TYPE,
            CARD_TYPE_NAME = @V_CARD_TYPE_NAME,
            CARD_ID = @IN_CARD_ID,
            MOBILE = @IN_MOBILE,
            FAX = @IN_FAX,
            E_MAIL = @IN_E_MAIL,
            MODI_MAN = @IN_INPUT_MAN,
            MODI_TIME = dbo.GETDATEINT(getdate()),
            SERVICE_MAN = @IN_SERVICE_MAN
        WHERE CUST_ID = @IN_CUST_ID
      
    --客户证件信息表
    IF EXISTS(SELECT 1 FROM TCUSTCARDINFO WHERE CUST_ID = @IN_CUST_ID AND CARD_TYPE = @OLD_CARD_TYPE AND CARD_ID = @OLD_CARD_ID)
    BEGIN
        UPDATE TCUSTCARDINFO
            SET CARD_TYPE      = @IN_CARD_TYPE,
                CARD_TYPE_NAME = @V_CARD_TYPE_NAME,
                CARD_ID        = @IN_CARD_ID,
                INPUT_TIME     = GETDATE()
            WHERE CUST_ID = @IN_CUST_ID AND CARD_TYPE = @OLD_CARD_TYPE AND CARD_ID = @OLD_CARD_ID
    END
    ELSE
    BEGIN
        INSERT INTO TCUSTCARDINFO(CUST_ID,CARD_CUST_NAME,CUST_TYPE,CARD_TYPE,CARD_TYPE_NAME,CARD_ID,VALID_DATE,INPUT_TIME,IMAGE1)
            VALUES(@IN_CUST_ID,@V_CUST_NAME,@V_CUST_TYPE,@IN_CARD_TYPE,@V_CARD_TYPE_NAME,@IN_CARD_ID,0,GETDATE(),null)
    END
    END --是否审核判断结束
	--不是客户经理自己修改，则通知客户经理
	IF @OLD_SERVICE_MAN<>@IN_INPUT_MAN
		INSERT INTO TSYSMESSAGE(TITLE,MSG,FROM_OPCODE,TO_OPCODE,INPUT_TIME,IS_READ)
			SELECT '修改客户基本信息','修改客户基本信息，客户名称：' + @V_CUST_NAME,@IN_INPUT_MAN,@OLD_SERVICE_MAN,GETDATE(),1
    SELECT @SSUMMARY = N'修改客户基本信息，客户名称：' + @V_CUST_NAME
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
    
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