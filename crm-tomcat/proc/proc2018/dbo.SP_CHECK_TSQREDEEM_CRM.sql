USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_CHECK_TSQREDEEM_CRM @IN_SERIAL_NO       INTEGER,         --SERIAL_NO
                                    @IN_CHECK_FLAG      INTEGER,         --2数据复核通过
                                    @IN_INPUT_MAN       INTEGER          --录入操作员
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_TEMP_AMOUNT DECIMAL(16,3),@V_TEMP_DEC_BEN_MONEY DECIMAL(16,3),@V_RET INT
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_ERROR_NAME NVARCHAR(200)
    DECLARE @V_SUB_MAN INT
    
    SET @V_RET_CODE = -20404000
    SET @IBUSI_FLAG  = 20404
    SET @SBUSI_NAME = N'赎回申请的复核'
    
    BEGIN TRY
    
    DECLARE @V_PRODUCT_ID INT,@V_PRODUCT_CODE NVARCHAR(6)
    DECLARE @V_CHECK_FLAG INT,@V_REDEEM_AMOUNT DECIMAL(16,3),@V_CONTRACT_BH NVARCHAR(16),
            @V_CONTRACT_SUB_BH NVARCHAR(60)
    DECLARE @V_LOGIN_NAME NVARCHAR(100),@V_TASK_CODE INT,@V_OBJECT_FLAG INT,@V_OP_NAME NVARCHAR(30),
            @V_SERIAL_NO INT,@V_SYS_DATE INT
    DECLARE @V_INTRUST_FLAG1 INT,@V_MENU_ID NVARCHAR(10),@V_PRODUCT_NAME NVARCHAR(60)
    DECLARE @V_TRANSFER_PRODUCT_ID INTEGER,@V_TRANSFER_SUB_PRODUCT_ID INTEGER,@V_TRANSFER_MONEY DECIMAL(16,3),
            @V_SERVICE_MAN INTEGER,@V_CUST_ID INTEGER,@V_PRE_CODE NVARCHAR(16),@V_BZ_FLAG INTEGER,@V_PRE_SERIAL_NO INTEGER,
            @V_BEN_SERIAL_NO INTEGER

    SELECT @V_CHECK_FLAG = CHECK_FLAG, @V_REDEEM_AMOUNT = REDEEM_AMOUNT, @V_PRODUCT_ID = PRODUCT_ID,
           @V_CONTRACT_BH = CONTRACT_BH,@V_TRANSFER_PRODUCT_ID = TRANSFER_PRODUCT_ID,
           @V_TRANSFER_SUB_PRODUCT_ID = TRANSFER_SUB_PRODUCT_ID,@V_TRANSFER_MONEY = TRANSFER_MONEY,
           @V_BEN_SERIAL_NO = BEN_SERIAL_NO
        FROM INTRUST..TSQREDEEM WHERE SERIAL_NO = @IN_SERIAL_NO
    IF @@ROWCOUNT=0
		RAISERROR('记录不存在!',16,1)
    IF @V_CHECK_FLAG <> 1 OR @IN_CHECK_FLAG <> 2
        RAISERROR('审核标志无效!',16,3)
	SET @V_SYS_DATE = CONVERT(INT,CONVERT(CHAR(8),GETDATE(),112))
    SELECT @V_PRODUCT_CODE = PRODUCT_CODE,@V_PRODUCT_NAME = PRODUCT_NAME,@V_INTRUST_FLAG1 = INTRUST_FLAG1,@V_SUB_MAN = SUB_MAN
        FROM INTRUST..TPRODUCT WHERE PRODUCT_ID = @V_PRODUCT_ID
    SELECT @V_CONTRACT_SUB_BH = CONTRACT_SUB_BH FROM INTRUST..TCONTRACT
        WHERE PRODUCT_ID = @V_PRODUCT_ID AND CONTRACT_BH = @V_CONTRACT_BH

    BEGIN TRANSACTION

    --待转入的信托产品处理
    IF ISNULL(@V_TRANSFER_PRODUCT_ID,0) <> 0 AND ISNULL(@V_TRANSFER_MONEY,0) > 0 
    BEGIN
        SELECT @V_SERVICE_MAN = B.SERVICE_MAN,@V_CUST_ID = A.CUST_ID
            FROM INTRUST..TBENIFITOR A LEFT JOIN TCustomers B ON A.CUST_ID=B.CUST_ID
            WHERE A.SERIAL_NO = @V_BEN_SERIAL_NO
		DECLARE @OUT_PRE_CODE NVARCHAR(40)/*预约编号*/,@OUT_SERIAL_NO INT
		--产生预约记录
        EXEC @V_RET = SP_ADD_TPRECONTRACT @V_TRANSFER_PRODUCT_ID,  --预发行产品ID(表中保存的是预发行产品ID，对正式产品的预约暂不支持)
                                     @V_CUST_ID,                    --客户ID
                                     @V_TRANSFER_MONEY,          --预约金额
                                     @V_SERVICE_MAN,                    --销售人员（联系人）
                                     0,                    --预约有效天数
                                     '111204',           --预约方式(1112)
                                     ''  ,
                                     1,                    --预约份数
                                     @IN_INPUT_MAN,
                                     @V_SYS_DATE,                    --预约日期
                                     NULL,             --预计认购日期
                                     NULL,    --客户来源(1110)
                                     @OUT_PRE_CODE OUTPUT , --预约编号
                                     @OUT_SERIAL_NO OUTPUT,
                                     NULL,    --渠道类别(5500)
                                     NULL,       --渠道费用
                                     NULL,    --预约类别(1182)用于区分预约的级别
                                     0,                  --正式产品ID
                                     0,                --子产品ID
                                     0                   --团队ID，为了适应一个操作员加入多个团队的情况，必需指明团队
        SET @V_ERROR_NAME=@@ERROR
        IF @V_RET < 0 --预约过程出错
        BEGIN
            RAISERROR(@V_ERROR_NAME,16,4)
        END
    END
    UPDATE INTRUST..TSQREDEEM
        SET CHECK_FLAG    = 2,
            CHECK_MAN     = @IN_INPUT_MAN,
            CHECK_TIME    = GETDATE()
        WHERE SERIAL_NO = @IN_SERIAL_NO

    --信息提示
    IF @V_INTRUST_FLAG1 = 1 
    BEGIN
        SET @V_MENU_ID = '4020703'
        SELECT @V_TASK_CODE = TASK_CODE,@V_LOGIN_NAME = TASK_INFO FROM INTRUST..TTASKDICT WHERE TASK_CODE = 602
    END
    ELSE BEGIN
        SET @V_MENU_ID = '4020301'
        SELECT @V_TASK_CODE = TASK_CODE,@V_LOGIN_NAME = TASK_INFO FROM INTRUST..TTASKDICT WHERE TASK_CODE = 603
    END
    SET @V_LOGIN_NAME = @V_LOGIN_NAME + N'产品编号：'   + @V_PRODUCT_CODE + N'，产品名称: ' + @V_PRODUCT_NAME
                                      + N'，合同编号：' + @V_CONTRACT_BH  + N'，实际合同编号：' + @V_CONTRACT_SUB_BH
    
    --关闭赎回已录入提示
    EXEC @V_RET = INTRUST..SP_INNER_CLOSETASKINFO @V_TASK_CODE,@V_PRODUCT_ID,@IN_SERIAL_NO,0

    --生成赎回申请已复核提示
    IF ISNULL(@V_SUB_MAN,0) =0
        SET @V_SUB_MAN = @IN_INPUT_MAN
    EXEC @V_RET = INTRUST..SP_INNER_MAKETASKINFO @V_TASK_CODE,@V_LOGIN_NAME,@V_LOGIN_NAME,@V_SYS_DATE,@V_PRODUCT_ID,
                                             @IN_INPUT_MAN,@V_MENU_ID,@V_SUB_MAN,@IN_SERIAL_NO,0

    --日志记录
    SELECT @SSUMMARY = @SBUSI_NAME + N',产品编号: ' + @V_PRODUCT_CODE 
                                   + N',产品名称: ' + @V_PRODUCT_NAME
                                   + N',合同序号: ' + @V_CONTRACT_BH 
                                   + N',合同编号: ' + @V_CONTRACT_SUB_BH
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
    
    COMMIT TRANSACTION
    END TRY

    BEGIN CATCH
        IF @@TRANCOUNT > 0 BEGIN
            ROLLBACK TRANSACTION
        END
        
        DECLARE @V_ERROR_STR NVARCHAR(1000),@V_ERROR_NUMBER INT,@V_ERROR_SEVERITY INT,@V_ERROR_STATE INT,
                @V_ERROR_PROCEDURE sysname,@V_ERROR_LINE INT,@V_ERROR_MESSAGE NVARCHAR(4000)

        SELECT @V_ERROR_STR = N'Message:%s,<br><font color = "white">Error:%d,Level:%d,State:%d,Procedure:%s,Line:%d </font>',
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
