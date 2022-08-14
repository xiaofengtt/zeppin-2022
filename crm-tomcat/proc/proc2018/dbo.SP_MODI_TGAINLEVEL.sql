USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TGAINLEVEL @IN_SERIAL_NO               INTEGER,        --记录号
                                    @IN_PROV_FLAG               INTEGER,        --1.优先，2一般，3劣后
                                    @IN_PROV_LEVEL              NVARCHAR(10),   --收益级别编号
                                    @IN_PROV_LEVEL_NAME         NVARCHAR(20),   --收益级别名称
                                    @IN_LOWER_LIMIT             DECIMAL(16,2),  --份额下限
                                    @IN_UPPER_LIMIT             DECIMAL(16,2),  --份额上限
                                    @IN_SUMMARY                 NVARCHAR(200),  --备注
                                    @IN_INPUT_MAN               INTEGER         --操作员
                                    
WITH ENCRYPTION 
AS
    DECLARE @V_PREPRODUCT_ID INT,@V_PRODUCT_NAME NVARCHAR(60)
    DECLARE @V_PROV_LEVEL_NAME NVARCHAR(20),@V_LOWER_LIMIT DECIMAL(16,2),@V_UPPER_LIMIT DECIMAL(16,2),@V_PROV_FLAG INT
    DECLARE @V_SUB_PRODUCT_ID INT,@V_SUB_PRODUCT_NAME NVARCHAR(60)
    DECLARE @IBUSI_FLAG INT
    DECLARE @SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    
    SET @IBUSI_FLAG = 3300109
    SET @SBUSI_NAME = N'收益级别修改'
    
    --异常捕获
    BEGIN TRY
        SELECT @V_PREPRODUCT_ID = A.PREPRODUCT_ID,@V_PRODUCT_NAME = B.PREPRODUCT_NAME,@V_PROV_FLAG = A.PROV_FLAG,
               @V_PROV_LEVEL_NAME = A.PROV_LEVEL_NAME,@V_LOWER_LIMIT = A.LOWER_LIMIT,@V_UPPER_LIMIT = A.UPPER_LIMIT
            FROM TGAINLEVEL A LEFT JOIN TPREPRODUCT B ON A.PREPRODUCT_ID=B.PREPRODUCT_ID
            WHERE A.SERIAL_NO = @IN_SERIAL_NO
        
        IF @IN_LOWER_LIMIT > @IN_UPPER_LIMIT BEGIN
            RAISERROR('收益级别份额下限不能大于份额上限',16,1)
        END
        
        IF EXISTS(SELECT 1 FROM TGAINLEVEL WHERE PREPRODUCT_ID = @V_PREPRODUCT_ID AND SERIAL_NO <> @IN_SERIAL_NO AND PROV_FLAG = @IN_PROV_FLAG
                      AND (@IN_LOWER_LIMIT BETWEEN LOWER_LIMIT AND UPPER_LIMIT OR @IN_UPPER_LIMIT BETWEEN LOWER_LIMIT AND UPPER_LIMIT
                          OR @IN_LOWER_LIMIT<LOWER_LIMIT AND @IN_UPPER_LIMIT>UPPER_LIMIT))
        BEGIN
            RAISERROR('与已存在的收益级别份额区间重叠',16,1)
        END
        
        SET @SSUMMARY = @SBUSI_NAME + N'，预发行产品，产品名称：' + @V_PRODUCT_NAME
        IF @V_PROV_FLAG <> @IN_PROV_FLAG
        BEGIN
            SET @SSUMMARY = @SSUMMARY + N'，优先级：' + CONVERT(VARCHAR,@V_PROV_FLAG) + '-->' + CONVERT(VARCHAR,@IN_PROV_FLAG)
        END
        
        SELECT @IN_PROV_LEVEL_NAME=TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE=@IN_PROV_LEVEL
        IF @V_PROV_LEVEL_NAME <> @IN_PROV_LEVEL_NAME
        BEGIN
            SET @SSUMMARY = @SSUMMARY + N'，级别：' + @V_PROV_LEVEL_NAME + '-->' + @IN_PROV_LEVEL_NAME
        END
        
        IF @V_LOWER_LIMIT <> @IN_LOWER_LIMIT 
        BEGIN
            SET @SSUMMARY = @SSUMMARY + N'，份额上限：' + CONVERT(VARCHAR,@V_LOWER_LIMIT) + '-->' + CONVERT(VARCHAR,@IN_LOWER_LIMIT)
        END
        IF @V_UPPER_LIMIT <> @IN_UPPER_LIMIT 
        BEGIN
            SET @SSUMMARY = @SSUMMARY + N'，份额下限：' + CONVERT(VARCHAR,@V_UPPER_LIMIT) + '-->' + CONVERT(VARCHAR,@IN_UPPER_LIMIT)
        END
        BEGIN TRANSACTION
        --业务处理
        UPDATE TGAINLEVEL
            SET PROV_FLAG               = @IN_PROV_FLAG,
                PROV_LEVEL              = @IN_PROV_LEVEL,
                PROV_LEVEL_NAME         = @IN_PROV_LEVEL_NAME,
                LOWER_LIMIT             = @IN_LOWER_LIMIT,
                UPPER_LIMIT             = @IN_UPPER_LIMIT,
                SUMMARY                 = @IN_SUMMARY
            WHERE SERIAL_NO = @IN_SERIAL_NO

        --日志记录
        INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
            VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
                        
        COMMIT TRANSACTION
        RETURN 100
    END TRY
    
    --异常处理
    BEGIN CATCH
        IF @@TRANCOUNT > 0 BEGIN
            ROLLBACK TRANSACTION
        END
        DECLARE @V_ERROR_STR NVARCHAR(1000),@V_ERROR_NUMBER INT,@V_ERROR_SEVERITY INT,@V_ERROR_STATE INT,
                @V_ERROR_PROCEDURE sysname,@V_ERROR_LINE INT,@V_ERROR_MESSAGE NVARCHAR(4000)

        SELECT @V_ERROR_STR = N'Message:%s,Error:%d,Level:%d,State:%d,Procedure:%s,Line:%d',
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
GO
