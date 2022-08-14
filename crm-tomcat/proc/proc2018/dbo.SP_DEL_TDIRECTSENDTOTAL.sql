﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_DEL_TDIRECTSENDTOTAL @IN_SMS_SERIAL_NO    	INT,                   --序号
										 @IN_INPUT_MAN         INT

WITH ENCRYPTION
AS

    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT
    DECLARE @SBUSI_NAME VARCHAR(40), @SSUMMARY VARCHAR(200)
    DECLARE @V_CONTENT_TEMPLET   NVARCHAR(MAX)      --发送内容，如果通过客户取手机号/邮箱，可以使用%1代表客户名称；自填则不能使用%1
    DECLARE @V_WAY_TYPE          INTEGER              --1:sms; 2:mail;

    SELECT @V_RET_CODE = -23001000, @IBUSI_FLAG = 23001
    SET @SBUSI_NAME = '删除短信息'
    SET @SSUMMARY = '删除短信息'
    
    SELECT @V_CONTENT_TEMPLET = CONTENT_TEMPLET,@V_WAY_TYPE = WAY_TYPE FROM TDIRECTSENDTOTAL WHERE SERIAL_NO = @IN_SMS_SERIAL_NO

    BEGIN TRANSACTION

    DELETE FROM TDIRECTSENDTOTAL WHERE SERIAL_NO = @IN_SMS_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    --
    DELETE FROM TDIRECTSENDDETAIL WHERE SEND_SERIAL_NO =@IN_SMS_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END    
    --
    IF @V_WAY_TYPE = 1   --短信
    BEGIN
        SET @SBUSI_NAME = '删除短信息'
        SET @SSUMMARY = '删除短信息，主题：' + RTRIM(@V_CONTENT_TEMPLET)
    END
    ELSE    --邮件
    BEGIN
        SET @SBUSI_NAME = '删除邮件信息'
        SET @SSUMMARY = '删除邮件信息，主题：' + RTRIM(@V_CONTENT_TEMPLET)
    END
    --    
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    COMMIT TRANSACTION
    RETURN 100
GO
