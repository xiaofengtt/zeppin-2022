USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TSmsRecord      @IN_SmsUser             NVARCHAR(50),     --发送者用户代码
                                        @IN_PhoneNumber         NVARCHAR(20),    --接受短信号码
                                        @IN_SmsContent          NVARCHAR(250),   --发送短信内容
                                        @IN_SendLevel           INT = 0,         --数值越低，优先级越高
                                        @IN_PutType             NVARCHAR(50),    --提交方式（待发、定时、循环）
                                        @IN_SerialNo_details    INTEGER,         --明细任务ID
                                        @IN_INPUT_MAN           INT = 0,         --操作员
                                        @OUT_SmsIndex           INT OUTPUT
WITH ENCRYPTION
AS
    DECLARE @V_SmsIndex INT,@V_SerialNo INT,@V_CUST_NAME NVARCHAR(50),@V_ServiceTitle NVARCHAR(50)
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT, @SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -40307000, @IBUSI_FLAG = 40307
    SELECT @SBUSI_NAME = N'添加短信发送记录信息', @SSUMMARY = N'添加短信发送记录信息'

    SELECT @V_SerialNo = TaskSerialNO FROM TServiceTaskDetail WHERE Serial_no = @IN_SerialNo_details
    SELECT @V_ServiceTitle = ServiceTitle FROM TServiceTasks WHERE Serial_no = @V_SerialNo

    SELECT @V_CUST_NAME = CUST_NAME
    FROM TCustomers c ,TServiceTaskDetail s
    WHERE c.CUST_ID = s.TargetCustID AND s.Serial_no = @IN_SerialNo_details

    BEGIN TRANSACTION
    INSERT INTO TSmsRecord(
            SmsUser,
            PhoneNumber,
            SmsContent,
            SendLevel,
            SmsTime,
            PutType,
            SerialNo,
            SerialNo_details,
            Cust_Name,
            ServiceTitle)
    VALUES(
            @IN_SmsUser,
            @IN_PhoneNumber,
            @IN_SmsContent,
            @IN_SendLevel,
            CONVERT(DATETIME,CONVERT(NVARCHAR(64),GETDATE(),120)),
            @IN_PutType,
            @V_SerialNo,
            @IN_SerialNo_details,
            @V_CUST_NAME,
            @V_ServiceTitle)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    --取得主键返回
    SELECT @V_SmsIndex = @@IDENTITY

    SET @SSUMMARY = N'添加短信发送记录信息，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @OUT_SmsIndex = @V_SmsIndex
    COMMIT TRANSACTION
    RETURN 100
GO
