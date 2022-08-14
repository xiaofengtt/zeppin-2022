﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TMONEYDATA_CRM @IN_SERIAL_NO       INTEGER,            --主键
                                    @IN_SUB_PRODUCT_ID  INTEGER,            --子产品ID
                                    @IN_DZ_DATE         INTEGER,            --到账日期
                                    @IN_JK_TYPE         NVARCHAR(10),       --缴款方式 1114
                                    @IN_TO_MONEY        DECIMAL(16,2),      --认购金额
                                    @IN_TO_AMOUNT       DECIMAL(16,2),      --份额
                                    @IN_SUMMARY         NVARCHAR(200),      --说明
                                    @IN_CITY_SERIAL_NO  INTEGER,            --资金来源地编号
                                    @IN_INPUT_MAN       INTEGER             --操作员
WITH ENCRYPTION
AS
    DECLARE @V_ERROR NVARCHAR(200)
    DECLARE @V_CHECK_FLAG INT,@V_CUST_ID INT,@V_PRODUCT_ID INT,@V_TO_MONEY DECIMAL(16,2),@V_DZ_DATE INT
    DECLARE @V_PRODUCT_CODE NVARCHAR(6),@V_PRODUCT_NAME NVARCHAR(60),@V_CITY_NAME NVARCHAR(20),@V_CITY_NAME0 NVARCHAR(20)
    DECLARE @V_SUB_FLAG INT,@V_SUB_PRODUCT_ID INT,@V_SUB_PRODUCT_NAME NVARCHAR(60),@V_SUB_PRODUCT_NAME0 NVARCHAR(60)
    DECLARE @V_JK_TYPE_NAME NVARCHAR(30),@V_JK_TYPE_NAME0 NVARCHAR(30)
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME VARCHAR(40),@SSUMMARY VARCHAR(200)
    
    BEGIN TRY

    SELECT @V_RET_CODE = -31206000 ,@IBUSI_FLAG = 31206
    SELECT @SBUSI_NAME = N'修改认购资金' ,@SSUMMARY = N'修改认购资金，'



    IF ISNULL(@IN_TO_MONEY,0) = 0 BEGIN
        SET @V_ERROR = '资金无效！'
        RAISERROR(@V_ERROR,16,1)
    END

    SELECT @V_PRODUCT_ID = PRODUCT_ID,@V_CUST_ID = CUST_ID,@V_CHECK_FLAG = CHECK_FLAG,
           @V_TO_MONEY = TO_MONEY,@V_DZ_DATE = DZ_DATE,@V_JK_TYPE_NAME0 = JK_TYPE_NAME,
           @V_SUB_PRODUCT_ID = SUB_PRODUCT_ID,@V_CITY_NAME0 = CITY_NAME
        FROM INTRUST..TMONEYDATA WHERE SERIAL_NO = @IN_SERIAL_NO
    IF @@ROWCOUNT=0
    BEGIN
		SET @V_ERROR = '记录不存在，请检查！'
        RAISERROR(@V_ERROR,16,2)
    END
    IF @V_CHECK_FLAG <> 1 BEGIN
        SET @V_ERROR = '该资金数据已确认！'
        RAISERROR(@V_ERROR,16,3)
    END
    
    SELECT @V_SUB_PRODUCT_NAME0 = LIST_NAME FROM TSUBPRODUCT WHERE SUB_PRODUCT_ID = @V_SUB_PRODUCT_ID
    SELECT @V_SUB_PRODUCT_NAME = LIST_NAME FROM TSUBPRODUCT WHERE SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID

    SELECT @V_PRODUCT_CODE = PRODUCT_CODE,@V_PRODUCT_NAME = PRODUCT_NAME,@V_SUB_FLAG = ISNULL(SUB_FLAG,0)
        FROM INTRUST..TPRODUCT WHERE PRODUCT_ID = @V_PRODUCT_ID
    IF @V_SUB_FLAG = 1 AND NOT EXISTS(SELECT 1 FROM TSUBPRODUCT WHERE PRODUCT_ID = @V_PRODUCT_ID AND SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID) BEGIN
        SET @V_ERROR  = '无效的子产品!'
        RAISERROR(@V_ERROR,16,3)
    END

    SELECT @V_JK_TYPE_NAME = TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_VALUE = @IN_JK_TYPE
    SELECT @V_CITY_NAME = CITY_NAME FROM INTRUST..TPRODUCTCITY WHERE SERIAL_NO = @IN_CITY_SERIAL_NO
    IF EXISTS (SELECT * FROM TSYSCONTROL WHERE FLAG_TYPE='TMONEYDATA_INPUT_CHECK' AND VALUE=1) --录入要审核
		SET @V_CHECK_FLAG=11
	ELSE
		SET @V_CHECK_FLAG=1
    
    BEGIN TRANSACTION

    UPDATE INTRUST..TMONEYDATA
        SET SUB_PRODUCT_ID  = @IN_SUB_PRODUCT_ID,
            TO_MONEY        = @IN_TO_MONEY,
            TO_AMOUNT       = @IN_TO_AMOUNT,
            DZ_DATE         = @IN_DZ_DATE,
            JK_TYPE         = @IN_JK_TYPE,
            JK_TYPE_NAME    = @V_JK_TYPE_NAME,
            SUMMARY         = @IN_SUMMARY,
            INPUT_MAN       = @IN_INPUT_MAN,
            CITY_SERIAL_NO  = @IN_CITY_SERIAL_NO,
            CITY_NAME       = @V_CITY_NAME,
            CHECK_FLAG      = @V_CHECK_FLAG,
            INPUT_CHECK_FLAG= 1
        WHERE SERIAL_NO = @IN_SERIAL_NO

    SET @SSUMMARY = @SSUMMARY + '，记录号：' + RTRIM(CONVERT(CHAR,@IN_SERIAL_NO)) 
    IF @V_SUB_PRODUCT_NAME0 <> @V_SUB_PRODUCT_NAME
        SET @SSUMMARY = @SSUMMARY + '，缴款方式：' + @V_SUB_PRODUCT_NAME0  +  '-->' + @V_SUB_PRODUCT_NAME
    IF @V_TO_MONEY <> @IN_TO_MONEY 
        SET @SSUMMARY = @SSUMMARY + '，到账金额：' + RTRIM(CONVERT(CHAR,@V_TO_MONEY))  +  '-->' + RTRIM(CONVERT(CHAR,@IN_TO_MONEY))
    IF @V_DZ_DATE <> @IN_DZ_DATE
        SET @SSUMMARY = @SSUMMARY + '，到账日期：' + RTRIM(CONVERT(CHAR,@V_DZ_DATE))  +  '-->' + RTRIM(CONVERT(CHAR,@IN_DZ_DATE))
    IF @V_JK_TYPE_NAME0 <> @V_JK_TYPE_NAME
        SET @SSUMMARY = @SSUMMARY + '，缴款方式：' + @V_JK_TYPE_NAME0  +  '-->' + @V_JK_TYPE_NAME
    IF @V_CITY_NAME0 <> @V_CITY_NAME
        SET @SSUMMARY = @SSUMMARY + '，资金来源地：' + @V_CITY_NAME0  +  '-->' + @V_CITY_NAME
    
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)


    COMMIT TRANSACTION
    END TRY
    BEGIN CATCH

        IF @@TRANCOUNT > 0 ROLLBACK TRANSACTION
        DECLARE @V_ERROR_STR NVARCHAR(1000),@V_ERROR_NUMBER INT,@V_ERROR_SEVERITY INT,@V_ERROR_STATE INT,
                @V_ERROR_PROCEDURE sysname,@V_ERROR_LINE INT,@V_ERROR_MESSAGE NVARCHAR(4000)

        SELECT @V_ERROR_STR = N'%s<BR><font color = "white">Error:%d,Level:%d,State:%d,Procedure:%s,Line:%d</font>',
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
