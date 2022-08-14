USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TDIRECTSENDTOTAL @IN_SMS_SERIAL_NO    	INT,                   --序号
                                          @IN_WAY_TYPE     		INTEGER,               --1:sms; 2:mail;
										  @IN_SEND_TYPE         INTEGER,               --1:即时发送; 2:计划发送
										  @IN_PLAN_TIME         DATETIME,              --计划发送时间，SEND_TYPE=2时有效
										  @IN_MSG_TYPE          NVARCHAR(10),		   --发送信息类型(1910)
										  @IN_MOBILES           NVARCHAR(MAX),        --自填手机号/邮箱，逗号分隔
										  @IN_CONTENT_TEMPLET   NVARCHAR(MAX),         --发送内容，如果通过客户取手机号/邮箱，可以使用%1代表客户名称；自填则不能使用%1
										  @IN_CHECK_FLAG        INT,
										  @IN_CHECK_MAN         INT,
										  @IN_INPUT_MAN         INT

WITH ENCRYPTION
AS

    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT
    DECLARE @SBUSI_NAME VARCHAR(40), @SSUMMARY VARCHAR(200)
    DECLARE @V_MSG_TYPE_NAME  NVARCHAR(30)       --发送信息类型名称

    SELECT @V_RET_CODE = -23001000, @IBUSI_FLAG = 23001
    SET @SBUSI_NAME = '修改短信息'
    SET @SSUMMARY = '修改短信息'
    
    SELECT @V_MSG_TYPE_NAME =TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE =@IN_MSG_TYPE

    BEGIN TRANSACTION

    UPDATE TDIRECTSENDTOTAL
        SET WAY_TYPE            = @IN_WAY_TYPE,
            SEND_TYPE           = @IN_SEND_TYPE,
            PLAN_TIME           = @IN_PLAN_TIME,
            MSG_TYPE            = @IN_MSG_TYPE,
            MSG_TYPE_NAME       = @V_MSG_TYPE_NAME,
            MOBILES             = @IN_MOBILES,
            CONTENT_TEMPLET     = @IN_CONTENT_TEMPLET,
            CHECK_FLAG          = @IN_CHECK_FLAG,
            CHECK_MAN           = @IN_CHECK_MAN,
            INPUT_MAN           = @IN_INPUT_MAN
    WHERE SERIAL_NO = @IN_SMS_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    --
    IF @IN_WAY_TYPE =1   -- 短信
    BEGIN
        SET @SBUSI_NAME = '修改短信息'
        SET @SSUMMARY = '修改短信息，主题：' + RTRIM(@IN_CONTENT_TEMPLET)
    END
    ELSE    --邮件
    BEGIN
        SET @SBUSI_NAME = '修改邮件信息'
        SET @SSUMMARY = '修改邮件信息，主题：' + RTRIM(@IN_CONTENT_TEMPLET)
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
