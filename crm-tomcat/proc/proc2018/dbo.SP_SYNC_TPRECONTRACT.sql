USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_SYNC_TPRECONTRACT   @IN_SERIAL_NO       INTEGER,         --序号
                                        @IN_INPUT_MAN       INTEGER,         --操作员
                                        @OUT_SERIAL_NO      INTEGER OUTPUT
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_RET INT,@V_RET_CODE INT,@V_ERROR NVARCHAR(200)
    DECLARE @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -20701000
    SELECT @IBUSI_FLAG = 20701
    SELECT @SBUSI_NAME = N'客户预约销售'
    SELECT @SSUMMARY = N'客户预约销售'

    DECLARE @V_MSG1 NVARCHAR(100), @V_MSG2 NVARCHAR(100), @V_MSG3 NVARCHAR(100), @V_MSG4 NVARCHAR(100), @V_MSG5 NVARCHAR(100)
    DECLARE @V_PRODUCT_ID INT,@V_CUST_ID INT,@V_PRE_MONEY DECIMAL (16, 3),@V_LINK_MAN INT,@V_VALID_DAYS INT,@V_PREPRODUCT_NAME NVARCHAR(100)
    DECLARE @V_PRE_TYPE NVARCHAR(10),@V_SUMMARY NVARCHAR(200),@V_PRE_NUM INT,@V_INPUT_MAN INT,@V_PRE_DATE INT
    DECLARE @V_EXP_REG_DATE INT,@V_CUST_SOURCE  NVARCHAR(10)
    DECLARE @V_CHANNEL_TYPE NVARCHAR(20)
    DECLARE @OUT_PRE_CODE NVARCHAR(16),@BZ_FLAG INTEGER,@V_BIND_SERIAL_NO INT,@V_PRE_CODE NVARCHAR(16)
    --读取数据
    SELECT @V_PRODUCT_ID = B.BIND_PRODUCT_ID,@V_CUST_ID = A.CUST_ID,@V_PRE_MONEY = A.PRE_MONEY,@V_LINK_MAN = A.LINK_MAN,@V_VALID_DAYS = A.VALID_DAYS,
           @V_PRE_TYPE = A.PRE_TYPE,@V_SUMMARY = A.SUMMARY,@V_PRE_NUM = PRE_NUM,@V_INPUT_MAN = A.INPUT_MAN,@V_PRE_DATE = A.PRE_DATE,
           @V_EXP_REG_DATE = A.EXP_REG_DATE,@V_CUST_SOURCE = A.CUST_SOURCE,@V_PREPRODUCT_NAME = PREPRODUCT_NAME,@V_CHANNEL_TYPE = CHANNEL_TYPE,
           @V_BIND_SERIAL_NO = 	BIND_SERIAL_NO
    FROM TPRECONTRACT A LEFT JOIN TPREPRODUCT B ON (A.PREPRODUCT_ID = B.PREPRODUCT_ID OR A.PRODUCT_ID=B.BIND_PRODUCT_ID)
    WHERE A.SERIAL_NO = @IN_SERIAL_NO
    ------------------------------------------------------------------------
    BEGIN TRY
        --校验
        IF ISNULL(@V_PRODUCT_ID,0) = 0
        BEGIN
            SET @V_ERROR = N'未绑定项目！'
            RAISERROR(@V_ERROR,16,3)
        END
        --调用业务系统过程保存预约数据
        BEGIN TRANSACTION
			IF EXISTS(SELECT 1 FROM INTRUST..TPRECONTRACT WHERE SERIAL_NO = @V_BIND_SERIAL_NO)
			BEGIN
				SET @OUT_SERIAL_NO = @V_BIND_SERIAL_NO
				SELECT @V_PRE_CODE = PRE_CODE FROM INTRUST..TPRECONTRACT WHERE SERIAL_NO = @V_BIND_SERIAL_NO
				EXEC @V_RET = INTRUST..SP_MODI_TPRECONTRACT_CRM	@V_BIND_SERIAL_NO,
                                                    @V_PRE_MONEY,
                                                    @V_LINK_MAN,
                                                    @V_VALID_DAYS,
                                                    @V_PRE_TYPE,
                                                    @V_SUMMARY,
                                                    @V_PRE_NUM,
                                                    @V_INPUT_MAN,
                                                    @V_PRE_DATE,
                                                    @V_EXP_REG_DATE,
                                                    @V_CUST_SOURCE,
													@V_PRE_CODE,
													2
											
			END										
			ELSE
				EXEC @V_RET = INTRUST..SP_ADD_TPRECONTRACT_CRM @V_PRODUCT_ID,
                                                    @V_CUST_ID,
                                                    @V_PRE_MONEY,
                                                    @V_LINK_MAN,
                                                    @V_VALID_DAYS,
                                                    @V_PRE_TYPE,
                                                    @V_SUMMARY,
                                                    @V_PRE_NUM,
                                                    @V_INPUT_MAN,
                                                    @V_PRE_DATE,
                                                    @V_EXP_REG_DATE,
                                                    @V_CUST_SOURCE,
                                                    @OUT_PRE_CODE OUTPUT,
                                                    @BZ_FLAG  OUTPUT,
                                                    @OUT_SERIAL_NO OUTPUT,
                                                    @V_CHANNEL_TYPE
            IF @V_RET < 0
            BEGIN
                SELECT @V_ERROR = ERROR_NAME, @V_MSG1 = MSG1, @V_MSG2 = MSG2, @V_MSG3 = MSG3, @V_MSG4 = MSG4, @V_MSG5 = MSG5
                    FROM INTRUST..TERRORINFO WHERE ERROR_ID = @V_RET
                SELECT @V_ERROR = REPLACE(@V_ERROR, '%1', ISNULL(@V_MSG1,''))
                SELECT @V_ERROR = REPLACE(@V_ERROR, '%2', ISNULL(@V_MSG2,''))
                SELECT @V_ERROR = REPLACE(@V_ERROR, '%3', ISNULL(@V_MSG3,''))
                SELECT @V_ERROR = REPLACE(@V_ERROR, '%4', ISNULL(@V_MSG4,''))
                SELECT @V_ERROR = REPLACE(@V_ERROR, '%5', ISNULL(@V_MSG5,''))
                RAISERROR(@V_ERROR,16,3)
            END
            --不为空
            IF ISNULL(@OUT_SERIAL_NO,0) <> 0
            BEGIN
                UPDATE TPRECONTRACT
                SET BIND_SERIAL_NO = @OUT_SERIAL_NO
                WHERE SERIAL_NO = @IN_SERIAL_NO
            END

             --2.日志
            SELECT @SSUMMARY = N'客户预约销售同步，产品名称：' + @V_PREPRODUCT_NAME + N'预约号：' + @OUT_PRE_CODE
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
