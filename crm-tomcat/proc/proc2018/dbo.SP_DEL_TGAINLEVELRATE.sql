USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_DEL_TGAINLEVELRATE @IN_SERIAL_NO     INTEGER,        --记录号
                                       @IN_INPUT_MAN     INTEGER         --操作员
WITH ENCRYPTION 
AS
    DECLARE @V_PRODUCT_NAME NVARCHAR(60)
    DECLARE @V_DF_SERIAL_NO INT,@V_PROV_LEVEL_NAME NVARCHAR(20)
    DECLARE @V_GAIN_RATE DECIMAL(5,4),@V_START_DATE INT,@V_END_DATE INT,@V_START_DATE1 INT,@V_END_DATE1 INT
    DECLARE @V_COUNT INTEGER,@V_PRODUCT_ID INT
    DECLARE @IBUSI_FLAG INT
    DECLARE @SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    
    SET @IBUSI_FLAG = 3300109
    SET @SBUSI_NAME = N'删除收益率'
    
    BEGIN TRY
        BEGIN TRANSACTION
        
        SELECT @V_PRODUCT_ID = B.PREPRODUCT_ID,@V_PRODUCT_NAME = B.PREPRODUCT_NAME,@V_PROV_LEVEL_NAME = A.PROV_LEVEL_NAME,
               @V_DF_SERIAL_NO = D.DF_SERIAL_NO,@V_GAIN_RATE = D.GAIN_RATE,@V_START_DATE = D.START_DATE,@V_END_DATE = D.END_DATE
            FROM TGAINLEVELRATE D LEFT JOIN TGAINLEVEL A ON A.SERIAL_NO=D.DF_SERIAL_NO
                LEFT JOIN TPREPRODUCT B ON B.PREPRODUCT_ID = A.PREPRODUCT_ID
            WHERE D.SERIAL_NO = @IN_SERIAL_NO
            
        SELECT @V_COUNT = COUNT(*) FROM TGAINLEVELRATE WHERE DF_SERIAL_NO = @V_DF_SERIAL_NO 
            
        IF @V_COUNT = 1
            RAISERROR('至少剩余一条收益率，不能删除',16,1)
        
        SET @V_END_DATE1  = dbo.GETDATE(@V_END_DATE,1)
        SET @V_START_DATE1 = dbo.GETDATE(@V_START_DATE,1)
        
        --删除的是最后一段时间 或 删除的是中间的一段时间
        IF NOT EXISTS(SELECT 1 FROM TGAINLEVELRATE WHERE START_DATE = @V_END_DATE1 AND DF_SERIAL_NO = @V_DF_SERIAL_NO)
        BEGIN   
            UPDATE TGAINLEVELRATE SET END_DATE = @V_END_DATE WHERE END_DATE = @V_START_DATE1 AND DF_SERIAL_NO = @V_DF_SERIAL_NO    
        END
        ELSE --删除的是中间的一段时间 或  删除的是第一段时间
        BEGIN
            UPDATE TGAINLEVELRATE SET START_DATE = @V_START_DATE WHERE START_DATE = @V_END_DATE1 AND DF_SERIAL_NO = @V_DF_SERIAL_NO
        END
        
        DELETE FROM TGAINLEVELRATE WHERE SERIAL_NO = @IN_SERIAL_NO
    
        --日志记录
        SET @SSUMMARY = @SBUSI_NAME + N'，预发行产品，产品名称：' + @V_PRODUCT_NAME
                                    + N'，收益级别：' + @V_PROV_LEVEL_NAME
                                    + N'，时间区间：' + CONVERT(VARCHAR,@V_START_DATE) + N'-->' + CONVERT(VARCHAR,@V_END_DATE)
                                    + N'，收益率：' + CONVERT(VARCHAR,@V_GAIN_RATE)

        INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
            VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
        
        COMMIT TRANSACTION
        RETURN 100
    END TRY
    
    BEGIN CATCH
        IF @@TRANCOUNT > 0 BEGIN
            ROLLBACK TRANSACTION
        END
        
        DECLARE @V_ERROR_STR NVARCHAR(1000),@V_ERROR_NUMBER INT,@V_ERROR_SEVERITY INT,@V_ERROR_STATE INT,
                @V_ERROR_PROCEDURE sysname,@V_ERROR_LINE INT,@V_ERROR_MESSAGE NVARCHAR(4000)

        SELECT @V_ERROR_STR = N'Error %d,Level %d,State %d,Procedure %s,Line %d,Message %s',
               @V_ERROR_NUMBER = ERROR_NUMBER(),
               @V_ERROR_SEVERITY = ERROR_SEVERITY(),
               @V_ERROR_STATE = ERROR_STATE(),
               @V_ERROR_PROCEDURE = ERROR_PROCEDURE(),
               @V_ERROR_LINE = ERROR_LINE(),
               @V_ERROR_MESSAGE = ERROR_MESSAGE()

        RAISERROR(@V_ERROR_STR,@V_ERROR_SEVERITY,1,@V_ERROR_NUMBER,@V_ERROR_SEVERITY,@V_ERROR_STATE,
                  @V_ERROR_PROCEDURE,@V_ERROR_LINE,@V_ERROR_MESSAGE)

        RETURN -100    
    END CATCH
GO
