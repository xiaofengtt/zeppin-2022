﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TPRECONTRACT_CUSTID @IN_SERIAL_NO  INT,
                                             @IN_CUST_ID    INT,    --
                                             @IN_INPUT_MAN  INT
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_ERROR NVARCHAR(200)
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME VARCHAR(40),@SSUMMARY VARCHAR(200)
    SELECT @V_RET_CODE = -20702000
    SELECT @IBUSI_FLAG = 20702
    SELECT @SBUSI_NAME = '修改预约的客户'
    SELECT @SSUMMARY = '修改预约的客户'

    DECLARE @V_CUST_ID INT

    BEGIN TRY
    --1.业务逻辑与操作
    IF NOT EXISTS(SELECT * FROM TPRECONTRACT WHERE SERIAL_NO = @IN_SERIAL_NO)
    BEGIN
        SET @V_ERROR = N'记录不存在！'
        RAISERROR(@V_ERROR,16,3)
    END
    SELECT @V_CUST_ID = CUST_ID FROM TPRECONTRACT WHERE SERIAL_NO = @IN_SERIAL_NO

    BEGIN TRANSACTION

    UPDATE TPRECONTRACT SET CUST_ID = @IN_CUST_ID WHERE SERIAL_NO = @IN_SERIAL_NO

    --2.日志
    SELECT @SSUMMARY = '修改预约的客户，原CUST_ID：' + CONVERT(NVARCHAR(30),@V_CUST_ID) + ',新CUST_ID：' + CONVERT(NVARCHAR(30),@IN_CUST_ID)
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