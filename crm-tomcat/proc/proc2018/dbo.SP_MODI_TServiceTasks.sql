USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TServiceTasks   @IN_SERIAL_NO      INT,             --任务ID
                                         @IN_MANAGERID      INTEGER,         --客户经理TCUSTMANAGERS.MANAGERID
                                         @IN_SERVICETYPE    INTEGER,         --服务类别TSERVICEDEFINE.SERVICETYPE
                                         @IN_SERVICETITLE   NVARCHAR(200),   --服务项标题
                                         @IN_SERVICEWAY     NVARCHAR(10),    --服务途径TDICTPARAM(1109)
                                         @IN_STARTDATETIME  DATETIME,        --起始时间，当前服务任务应当在本时间后进行
                                         @IN_ENDDATETIME    DATETIME,        --结束时间，当前服务任务应当在本时间前进行
                                         @IN_ServiceRemark  NVARCHAR(800),   --服务详述
                                         @IN_QUES_ID        INTEGER,         --问卷ID
                                         @IN_PRODUCT_ID     INTEGER,         --产品ID
                                         @IN_AutoFlag       INTEGER,         --是否自动发送短信
                                         @IN_TempID         INTEGER,         --短信模板ID
                                         @IN_INPUT_MAN      INTEGER,         --操作员
                                         @IN_EXECUTOR       INT = NULL      --任务执行人
WITH ENCRYPTION
AS
    DECLARE @V_SERVICETYPENAME NVARCHAR(64),@V_TempTitle NVARCHAR(40)
    DECLARE @V_SerialNO_Detail INT,@V_CustID INT,@V_Mobile NVARCHAR(100) ,@V_SmsContent NVARCHAR(250),@V_CustName NVARCHAR(30)
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -50301000, @IBUSI_FLAG = 50301
    SELECT @SBUSI_NAME = N'修改创建服务任务', @SSUMMARY = N'修改创建服务任务'

    IF @IN_EXECUTOR IS NULL SET @IN_EXECUTOR = @IN_MANAGERID

    SELECT @V_SERVICETYPENAME = ServiceTypeName FROM TServiceDefine WHERE ServiceType = @IN_SERVICETYPE
    IF @IN_TempID>0
    BEGIN
        SELECT @V_TempTitle = Title,@V_SmsContent = Content FROM TSmsTemplates WHERE TempID = @IN_TempID
    END

    BEGIN TRANSACTION
    --主任务信息修改
    UPDATE TServiceTasks
        SET ManagerID = @IN_MANAGERID,
            Executor = @IN_EXECUTOR,
            ServiceType = @IN_SERVICETYPE,
            ServiceTypeName = @V_SERVICETYPENAME,
            ServiceTitle = @IN_SERVICETITLE,
            ServiceWay = @IN_SERVICEWAY,
            StartDateTime = @IN_STARTDATETIME,
            EndDateTime = @IN_ENDDATETIME,
            ServiceRemark = @IN_ServiceRemark,
            QUES_ID = @IN_QUES_ID,
            PRODUCT_ID = @IN_PRODUCT_ID,
            TempID = @IN_TempID ,
            TempTitle = @V_TempTitle,
            AutoFlag = @IN_AutoFlag
        WHERE Serial_no = @IN_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    --定义游标
    DECLARE CUR_TServiceTaskDetail CURSOR FOR
        SELECT Serial_no,TargetCustID
        FROM TServiceTaskDetail
        WHERE TaskSerialNO = @IN_SERIAL_NO

    OPEN CUR_TServiceTaskDetail
    FETCH NEXT FROM CUR_TServiceTaskDetail INTO @V_SerialNO_Detail,@V_CustID
    WHILE @@FETCH_STATUS = 0
    BEGIN
        -- 获取相关客户信息
        SELECT @V_Mobile = MOBILE,@V_CustName = CUST_NAME
        FROM TCustomers
        WHERE CUST_ID = @V_CustID
        -- 短信内容获取
        IF @IN_TempID>0
        BEGIN
            SELECT @V_SmsContent = REPLACE(@V_SmsContent,'%1',@V_CustName)
        END
        ELSE
        BEGIN
            SELECT @V_SmsContent = N''
        END

        UPDATE TServiceTaskDetail
        SET ServiceType = @IN_SERVICETYPE,
            ServiceTypeName = @V_SERVICETYPENAME,
            ServiceWay = @IN_SERVICEWAY,
            AutoFlag = @IN_AutoFlag,
            Mobile = @V_Mobile,
            SmsContent = @V_SmsContent
        WHERE Serial_no = @V_SerialNO_Detail
        IF @@ERROR <> 0
        BEGIN
            CLOSE CUR_TServiceTaskDetail
            DEALLOCATE CUR_TServiceTaskDetail
            ROLLBACK TRANSACTION
            RETURN -100
        END

        FETCH NEXT FROM CUR_TServiceTaskDetail INTO @V_SerialNO_Detail,@V_CustID
    END
    CLOSE CUR_TServiceTaskDetail
    DEALLOCATE CUR_TServiceTaskDetail

    SET @SSUMMARY = N'修改创建服务任务，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
