USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE SP_RE_CHECK_TSALESCHANGES @IN_SERIAL_NO  INT, --序号 
											  @IN_STATUS     INT, --审核状态
											  @IN_INPUT_MAN  INT,  --操作员
											  @IN_RE_CHECK_REASON  NVARCHAR(200)  --确认意见
												
					
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_ERROR NVARCHAR(200)
    DECLARE @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    
    SELECT @IBUSI_FLAG = 655352
    SELECT @SBUSI_NAME = N'审核转销量申请'
    SELECT @SSUMMARY = N'审核转销量申请'

    --DECLARE @V_PRE_CODE NVARCHAR(16), @V_PREPRODUCT_NAME NVARCHAR(100), @V_PREPRODUCT_ID INT, @V_PRE_SERIAL_NO INT
    
    DECLARE @V_STATUS INT -- 状态 1-待确认 2-确认未通过 3-待审核 4-审核未通过 5-已通过
    DECLARE @V_STATUS_NAME NVARCHAR(50)
    --DECLARE @V_SL_TIME NVARCHAR(50), @V_SL_TYPE_NAME NVARCHAR(50)
    
    DECLARE @V_CUST_ID INT
    DECLARE @V_CUST_NAME NVARCHAR(100)
    
    DECLARE @V_PRE_SERIAL_NO INT--预约表ID
	DECLARE @V_PRE_MONEY_SERIAL_NO INT--预约到帐表ID
	
	DECLARE @V_FK_TSALESRESULT INT--预约表ID
    
    DECLARE @V_TO_SERVICE_MAN_NAME NVARCHAR(100)
    DECLARE @V_PREPRODUCT_ID INT,@V_PREPRODUCT_NAME NVARCHAR(200)
    
    DECLARE @V_ZR_MONEY DEC(16,3)	
    DECLARE @V_RECORDS_COUNT INT
    DECLARE @V_FK_TPREMONEYDETAIL INT
    
    --REMARK信息
    DECLARE @V_FROM_SERVICE_MAN NVARCHAR(100),@V_TO_SERVICE_MAN NVARCHAR(100)
    DECLARE @V_REMARK NVARCHAR(500)
    
    DECLARE @V_R_STATUS INT -- 1-正常 0-失效 -1-待生效
    SET @V_R_STATUS = 0
    
    DECLARE @V_L_STATUS INT -- 1-正常 0-失效 -1-待生效
    SET @V_L_STATUS = 1
    ------------------------------------------------------------------------
    BEGIN TRY
    --1.业务逻辑与操作
    --校验
    IF @IN_STATUS < 3
    BEGIN
        SET @V_ERROR = N'审核状态错误！'
        RAISERROR(@V_ERROR,16,1)
    END
    
    IF NOT EXISTS(SELECT 1 FROM TSALES_CHANGES WHERE SERIAL_NO = @IN_SERIAL_NO)
    BEGIN
        SET @V_ERROR = N'记录不存在！'
        RAISERROR(@V_ERROR,16,2)
    END
    
	SELECT @V_PRE_SERIAL_NO = PRE_SERIAL_NO,@V_ZR_MONEY = CHANGE_MONEY,@V_FK_TSALESRESULT = FK_TSALESRESULT
		,@V_FK_TPREMONEYDETAIL = FK_TPREMONEYDETAIL
		,@V_FROM_SERVICE_MAN = FROM_SERVICE_MAN_NAME,@V_TO_SERVICE_MAN = TO_SERVICE_MAN_NAME 
	FROM TSALES_CHANGES WHERE SERIAL_NO = @IN_SERIAL_NO
	
	SET @V_REMARK = N'|'
					+@V_FROM_SERVICE_MAN
					+N'调'
					+@V_TO_SERVICE_MAN
					+CAST(@V_ZR_MONEY AS NVARCHAR)
					
	SELECT @V_RECORDS_COUNT = COUNT(*) FROM TSALESRESULTFORSTATISTIC WHERE FK_TPREMONEYDETAIL = @V_FK_TPREMONEYDETAIL
	
	IF NOT EXISTS(SELECT 1 FROM TPRECONTRACT WHERE SERIAL_NO = @V_PRE_SERIAL_NO)
    BEGIN
        SET @V_ERROR = N'预约记录不存在！'
        RAISERROR(@V_ERROR,16,3)
    END
    SELECT @V_PREPRODUCT_ID = PREPRODUCT_ID, @V_CUST_ID = CUST_ID FROM TPRECONTRACT WHERE SERIAL_NO = @V_PRE_SERIAL_NO  
    SELECT @V_PREPRODUCT_NAME = PREPRODUCT_NAME FROM TPREPRODUCT WHERE PREPRODUCT_ID = @V_PREPRODUCT_ID 
	
	IF NOT EXISTS(SELECT 1 FROM TCustomers WHERE CUST_ID = @V_CUST_ID)
    BEGIN
        SET @V_ERROR = N'客户信息不存在！'
        RAISERROR(@V_ERROR,16,3)
    END
    SELECT @V_CUST_NAME=CUST_NAME FROM TCustomers WHERE CUST_ID = @V_CUST_ID
	
	
	IF @V_STATUS <> 3
	BEGIN
        SET @V_ERROR = N'记录已审核，不能审核！'
        RAISERROR(@V_ERROR,16,3)
    END
    
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

    UPDATE TSALES_CHANGES SET [STATUS] = @IN_STATUS, STATUS_NAME = @V_STATUS_NAME 
    ,RE_CHECKER = @IN_INPUT_MAN, RE_CHECK_TIME = GETDATE(), RE_CHECK_REASON = @IN_RE_CHECK_REASON 
        WHERE SERIAL_NO = @IN_SERIAL_NO
	
	IF @IN_STATUS = 5 --审核通过将转让记录置为有效
	BEGIN
		/*DECLARE @V_PRE_PRODUCT_TYPE INT
		DECLARE @V_PRE_PRODUCT_TYPE_NAME NVARCHAR(50)
		SET @V_PRE_PRODUCT_TYPE = 1
		SET @V_PRE_PRODUCT_TYPE_NAME = N'直销(工作台)'
	
		INSERT INTO TSALESRESULTFORSTATISTIC(PRE_SERIAL_NO,DZ_DATE,DZ_MONEY,JK_TYPE,JK_TYPE_NAME,INPUT_MAN,ONWAY_FLAG,PRE_PRODUCT_TYPE,PRE_PRODUCT_TYPE_NAME,FK_TPREMONEYDETAIL,RECORDS_COUNT,FK_TSALES_CHANGES,REMARK)
		SELECT 
		[PRE_SERIAL_NO]
		,[DZ_DATE]
		,@V_ZR_MONEY
		,[JK_TYPE]
		,[JK_TYPE_NAME]
		,@IN_INPUT_MAN
		,[ONWAY_FLAG]
		,@V_PRE_PRODUCT_TYPE
		,@V_PRE_PRODUCT_TYPE_NAME
		,[FK_TPREMONEYDETAIL]
		,(@V_RECORDS_COUNT+1)
		,[FK_TSALES_CHANGES]
		,@V_REMARK
		FROM TSALESRESULTFORSTATISTIC WHERE SERIAL_NO = @V_FK_TSALESRESULT*/
		UPDATE TSALESRESULTFORSTATISTIC SET [STATUS]=@V_L_STATUS,REMARK=@V_REMARK WHERE FK_TSALES_CHANGES=@IN_SERIAL_NO
		
		--更新REMARK和金额
		UPDATE TSALESRESULTFORSTATISTIC SET DZ_MONEY=(DZ_MONEY-@V_ZR_MONEY), REMARK=(ISNULL(REMARK,N'')+@V_REMARK) WHERE SERIAL_NO=@V_FK_TSALESRESULT
	END
	ELSE IF @IN_STATUS = 4 --审核不通过 将对应记录置为失效状态
	BEGIN
		UPDATE TSALESRESULTFORSTATISTIC SET [STATUS]=@V_R_STATUS WHERE FK_TSALES_CHANGES=@IN_SERIAL_NO
	END
	
	--ELSE IF @IN_STATUS = 4 --审核不通过退回转让金额
	--BEGIN
	--	UPDATE TSALESRESULTFORSTATISTIC SET DZ_MONEY=(DZ_MONEY+@V_ZR_MONEY) WHERE SERIAL_NO=@V_FK_TSALESRESULT
	--END
	
    --2.日志
    SELECT @SSUMMARY = N'审核转销量申请，产品名称：'+@V_PREPRODUCT_NAME
						+N'客户名称：' + @V_CUST_NAME
						+N'审核状态'+@V_STATUS_NAME
    
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
