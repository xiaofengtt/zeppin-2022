USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TServiceTasks    @IN_MANAGERID      INTEGER,         --客户经理TCUSTMANAGERS.MANAGERID
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
                                         @OUT_SERIAL_NO     INT   OUTPUT,    --返回最新的序列号
                                         @IN_EXECUTOR       INT = NULL,     --任务执行人
                                         @IN_ExecRoleID     INT = NULL,     --任务执行角色ID
                                         @IN_ExecFlag       INT = NULL      --执行标识：1执行人与执行角色同时执行，2其一执行
                                         
WITH ENCRYPTION
AS
    DECLARE @V_SERVICETYPENAME NVARCHAR(64),@V_TempTitle NVARCHAR(40)
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
	DECLARE @V_STATUS INT,@V_DT_SMS_FP INT
    SELECT @V_RET_CODE = -50301000, @IBUSI_FLAG = 50301
    SELECT @SBUSI_NAME = N'创建服务任务', @SSUMMARY = N'创建服务任务'
    SELECT @V_TempTitle = N''
	SELECT @V_DT_SMS_FP = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = 'DT_SMS_FP'
	IF @V_DT_SMS_FP = 2
		SET @V_STATUS = 2
	ELSE
		SET @V_STATUS = 1
    IF @IN_EXECUTOR IS NULL SET @IN_EXECUTOR = @IN_MANAGERID

    SELECT @V_SERVICETYPENAME = ServiceTypeName FROM TServiceDefine WHERE ServiceType = @IN_SERVICETYPE
    IF @IN_TempID>0
    BEGIN
        SELECT @V_TempTitle = Title FROM TSmsTemplates WHERE TempID = @IN_TempID
    END

    BEGIN TRANSACTION
	IF @IN_ExecFlag=1 AND ISNULL(@IN_ExecRoleID,0)>0--执行人与执行角色同时执行,多加一条任务记录:给角色的任务
		INSERT INTO TServiceTasks(ManagerID,Executor,ServiceType,ServiceTypeName,ServiceTitle,ServiceWay,StartDateTime,EndDateTime,ServiceRemark,
                Status,CustomerCount,InsertMan,InsertTime,Originate,QUES_ID,PRODUCT_ID,AutoFlag,TempID,TempTitle,
                ExecRoleID,IsRole)
       VALUES(@IN_MANAGERID,@IN_EXECUTOR,@IN_SERVICETYPE,@V_SERVICETYPENAME,@IN_SERVICETITLE,@IN_SERVICEWAY,@IN_STARTDATETIME,@IN_ENDDATETIME,@IN_ServiceRemark,
                @V_STATUS,0,@IN_INPUT_MAN,CONVERT(DATETIME,CONVERT(NVARCHAR(64),GETDATE(),120)),2,@IN_QUES_ID,@IN_PRODUCT_ID,@IN_AutoFlag,@IN_TempID,@V_TempTitle,
                @IN_ExecRoleID,1)
	
    INSERT INTO TServiceTasks(ManagerID,Executor,ServiceType,ServiceTypeName,ServiceTitle,ServiceWay,StartDateTime,EndDateTime,ServiceRemark,
                Status,CustomerCount,InsertMan,InsertTime,Originate,QUES_ID,PRODUCT_ID,AutoFlag,TempID,TempTitle,
                ExecRoleID,IsRole)
       VALUES(@IN_MANAGERID,@IN_EXECUTOR,@IN_SERVICETYPE,@V_SERVICETYPENAME,@IN_SERVICETITLE,@IN_SERVICEWAY,@IN_STARTDATETIME,@IN_ENDDATETIME,@IN_ServiceRemark,
                @V_STATUS,0,@IN_INPUT_MAN,CONVERT(DATETIME,CONVERT(NVARCHAR(64),GETDATE(),120)),2,@IN_QUES_ID,@IN_PRODUCT_ID,@IN_AutoFlag,@IN_TempID,@V_TempTitle,
                @IN_ExecRoleID,0)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    --返回最新的序列号，用作后续添加明细记录
    SELECT @OUT_SERIAL_NO = @@IDENTITY
    SET @SSUMMARY = N'创建服务任务，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
