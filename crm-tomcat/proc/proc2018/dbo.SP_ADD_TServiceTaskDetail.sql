USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TServiceTaskDetail    @IN_TASKSERIALNO      INTEGER,         --序号（TServiceTask.Serial_no）
                                              @IN_TARGETCUSTID      INTEGER,         --目标客户
                                              @IN_NEEDFEEDBACK      TINYINT,         --是否需要反馈2不需要1需要
                                              @IN_INPUT_MAN         INTEGER          --操作员
WITH ENCRYPTION
AS
    DECLARE @V_SERVICETYPE INT,@V_SERVICEWAY NVARCHAR(10),@V_SERVICETYPENAME NVARCHAR(64),@V_AutoFlag INTEGER,@V_TempID INTEGER,@V_TempTitle NVARCHAR(40)
    DECLARE @V_Mobile NVARCHAR(100) ,@V_SmsContent NVARCHAR(250),@V_CustName NVARCHAR(30)
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -50301000, @IBUSI_FLAG = 50301
    SELECT @SBUSI_NAME = N'创建服务任务明细', @SSUMMARY = N'创建服务任务明细'
    IF NOT EXISTS(SELECT 1 FROM TServiceTasks WHERE Serial_no = @IN_TASKSERIALNO )
        RETURN @V_RET_CODE - 1  --对应的任务列表不存在
    -- 复制任务信息
    SELECT @V_SERVICETYPE = ServiceType,
           @V_SERVICETYPENAME = ServiceTypeName,
           @V_SERVICEWAY = ServiceWay,
           @V_AutoFlag = AutoFlag,
           @V_TempID = TempID,
           @V_TempTitle = TempTitle
    FROM TServiceTasks
    WHERE Serial_no = @IN_TASKSERIALNO
    -- 获取相关客户信息
    SELECT @V_Mobile = MOBILE,@V_CustName = CUST_NAME
    FROM TCustomers
    WHERE CUST_ID = @IN_TARGETCUSTID
    -- 短信内容获取
    IF @V_TempID>0
    BEGIN
        SELECT @V_SmsContent = Content
        FROM TSmsTemplates
        WHERE TempID = @V_TempID

        SELECT @V_SmsContent = REPLACE(@V_SmsContent,'%1',@V_CustName)
    END
    ELSE
    BEGIN
        SELECT @V_SmsContent = N''
    END

    BEGIN TRANSACTION
    INSERT INTO TServiceTaskDetail(TaskSerialNO,ServiceType,ServiceTypeName,ServiceWay,Status,InsertTime,
                                    TargetCustID,NeedFeedback,InsertMan,AutoFlag,Mobile,SmsContent)
       VALUES(@IN_TASKSERIALNO,@V_SERVICETYPE,@V_SERVICETYPENAME,@V_SERVICEWAY,2,CONVERT(DATETIME,CONVERT(NVARCHAR(64),GETDATE(),120)),
                                @IN_TARGETCUSTID,@IN_NEEDFEEDBACK,@IN_INPUT_MAN,@V_AutoFlag,@V_Mobile,@V_SmsContent)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    --任务列表客户累计数加1
    UPDATE TServiceTasks
    SET CustomerCount = CustomerCount + 1
    WHERE Serial_no = @IN_TASKSERIALNO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    SET @SSUMMARY = N'创建服务任务明细，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
