USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE SP_CHECK_TCUSTOMERSCONNECTION @IN_SERIAL_NO  INT, --序号 
											  @IN_STATUS     INT, --审核状态
											  @IN_INPUT_MAN  INT,  --操作员
											  @IN_CHECK_REASON  NVARCHAR(200)  --确认意见
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_ERROR NVARCHAR(200)
    DECLARE @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    
    SELECT @IBUSI_FLAG = 655351
    SELECT @SBUSI_NAME = N'确认客户联系方式修改申请'
    SELECT @SSUMMARY = N'确认客户联系方式修改申请'

    --DECLARE @V_PRE_CODE NVARCHAR(16), @V_PREPRODUCT_NAME NVARCHAR(100), @V_PREPRODUCT_ID INT, @V_PRE_SERIAL_NO INT
    
    DECLARE @V_STATUS INT -- -1未审核 0审核未通过 1审核已通过
    DECLARE @V_STATUS_NAME NVARCHAR(50)
    --DECLARE @V_SL_TIME NVARCHAR(50), @V_SL_TYPE_NAME NVARCHAR(50)
    
    DECLARE @V_CUST_ID INT
    DECLARE @V_CUST_NAME NVARCHAR(100)
    ------------------------------------------------------------------------
    BEGIN TRY
    --1.业务逻辑与操作
    --校验
    IF @IN_STATUS < 0
    BEGIN
        SET @V_ERROR = N'状态错误！'
        RAISERROR(@V_ERROR,16,1)
    END
    
    IF NOT EXISTS(SELECT 1 FROM TCUSTOMOERS_CONNECTION_MODI WHERE SERIAL_NO = @IN_SERIAL_NO)
    BEGIN
        SET @V_ERROR = N'记录不存在！'
        RAISERROR(@V_ERROR,16,2)
    END
    
	SELECT @V_CUST_ID = CUST_ID,@V_STATUS = [STATUS], @V_STATUS_NAME = STATUS_NAME 
	FROM TCUSTOMOERS_CONNECTION_MODI WHERE SERIAL_NO = @IN_SERIAL_NO
	
	SELECT @V_CUST_NAME=CUST_NAME FROM TCustomers WHERE CUST_ID = @V_CUST_ID
	
	IF @V_STATUS <> 1
	BEGIN
        SET @V_ERROR = N'记录已审核，不能审核！'
        RAISERROR(@V_ERROR,16,3)
    END
    
	/*SELECT @V_PREPRODUCT_ID = PREPRODUCT_ID, @V_PRE_CODE = PRE_CODE
        FROM TPRECONTRACT WHERE SERIAL_NO = @V_PRE_SERIAL_NO
    SELECT @V_PREPRODUCT_NAME = PREPRODUCT_NAME FROM TPREPRODUCT WHERE PREPRODUCT_ID = @V_PREPRODUCT_ID*/
    
    IF @IN_STATUS = 2 
    BEGIN
		SET @V_STATUS_NAME = N'确认未通过'
    END
    ELSE IF @IN_STATUS = 3 
    BEGIN
		 SET @V_STATUS_NAME = N'待审核'
    END
    ELSE
    BEGIN
        SET @V_ERROR = N'状态错误！'
        RAISERROR(@V_ERROR,16,1)
    END
    ------------------------------------------------------------------------
    BEGIN TRANSACTION

    UPDATE TCUSTOMOERS_CONNECTION_MODI SET [STATUS] = @IN_STATUS, STATUS_NAME = @V_STATUS_NAME 
    ,CHECKER = @IN_INPUT_MAN, CHECK_TIME = GETDATE(), CHECK_REASON = @IN_CHECK_REASON 
        WHERE SERIAL_NO = @IN_SERIAL_NO

    --2.日志
    SELECT @SSUMMARY = N'确认客户联系方式修改申请，客户名称：' + @V_CUST_NAME
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
