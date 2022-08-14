USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TCallRecords @IN_Direction    INTEGER,      --方向1被叫接听2主叫拨打
                                     @IN_CallTime     DATETIME,     --通话开始时间（索引）
                                     @IN_CallLength   INTEGER,      --通话时长（秒）
                                     @IN_ManagerID    INTEGER,      --客户经理ID（TCustManagers.ManagerID）（索引）
                                     @IN_Extension    INTEGER,      --分机号码
                                     @IN_CUST_ID      INTEGER,      --客户ID（TCustomers.CUST_ID）（索引）通话对方是客户时，必须有CUST_ID。不是客户且未保存为客户时，为空
                                     @IN_ContactID    INTEGER,      --联系人ID(TCustomerContacts.ContactID)
                                     @IN_PhoneNumber  NVARCHAR(50), --对方电话号码
                                     @IN_BusinessID   INTEGER,      --本次通话所涉及业务ID
                                     @IN_Content      NVARCHAR(MAX),--通话记事
                                     @IN_Status       INTEGER,      --状态1正常完成2本次会话待处理9放弃接听
                                     @IN_CallCenterID BIGINT,       --CallCenter系统中标识（HelpCenter.calllog.id）
                                     @IN_INPUT_MAN    INTEGER,
                                     @OUT_CC_ID       INT OUTPUT

WITH ENCRYPTION
AS
    DECLARE @RET INT, @V_DT_CALL INT
--  SELECT @V_DT_CALL = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'DT_CALL'
--  暂时使用分布式事务，向CallCenter的数据库表HelpCenter中插入记录，便于测试
--  SET XACT_ABORT ON

    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'通话记录'
    SELECT @SSUMMARY = N'通话记录'
    SELECT @IBUSI_FLAG = 50001
    SELECT @V_RET_CODE = -50001000

    IF @IN_CallTime IS NULL SET @IN_CallTime = GETDATE()
---------向CallCenter的数据库表HelpCenter中插入记录，便于测试
    --BEGIN DISTRIBUTED TRANSACTION
/*
    IF @IN_CallTime IS NULL SET @IN_CallTime = GETDATE()
    IF @V_DT_CALL = 1 --启用分布式
    BEGIN
        INSERT INTO SRV_CallCenter.HelpCenter.dbo.calllog(callerid,calleeid,IO,InboundCallTime,InQueueTime,OutQueueTime,
            QueueDuration,StartRingTime,AnsweredTime,HangUpTime,TalkDuration,WaitTime,AnswerFlag,IsFlashed,Extno,AgentId)
        VALUES(@IN_PhoneNumber, '57187703600', @IN_Direction-1, @IN_CallTime, @IN_CallTime, @IN_CallTime,
            0,@IN_CallTime,@IN_CallTime,dateadd(second,@IN_CallLength,@IN_CallTime),@IN_CallLength,0,
            case @IN_Status when 9 then 0 else 1 end, 0, @IN_Extension, @IN_ManagerID )
        SELECT @IN_CallCenterID = MAX(id) FROM SRV_CallCenter.HelpCenter.dbo.calllog
    END
    ELSE IF (@V_DT_CALL = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'HelpCenter') --本地数据库
    BEGIN
        INSERT INTO HelpCenter..calllog(callerid,calleeid,IO,InboundCallTime,InQueueTime,OutQueueTime,
            QueueDuration,StartRingTime,AnsweredTime,HangUpTime,TalkDuration,WaitTime,AnswerFlag,IsFlashed,Extno,AgentId)
        VALUES(@IN_PhoneNumber, '57187703600', @IN_Direction-1, @IN_CallTime, @IN_CallTime, @IN_CallTime,
            0,@IN_CallTime,@IN_CallTime,dateadd(second,@IN_CallLength,@IN_CallTime),@IN_CallLength,0,
            case @IN_Status when 9 then 0 else 1 end, 0, @IN_Extension, @IN_ManagerID )
        SELECT @IN_CallCenterID = MAX(id) FROM HelpCenter..calllog
    END
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    IF @V_DT_CALL = 1 --启用分布式
        INSERT INTO SRV_CallCenter.tvc_voice.dbo.files([datetime],lasttime,telphone,callerid,[out],ring,times,length,[size],
            username,path,path2,callid)
        VALUES(@IN_CallTime,dateadd(second,@IN_CallLength,@IN_CallTime),@IN_Extension,@IN_PhoneNumber,@IN_Direction-1,0,0,@IN_CallLength,0,
            @IN_ManagerID,'test.wav','http://128.8.28.19:9084/wav/test.wav', @IN_CallCenterID)
    ELSE IF (@V_DT_CALL = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'tvc_voice') --本地数据库
        INSERT INTO tvc_voice..files([datetime],lasttime,telphone,callerid,[out],ring,times,length,[size],
            username,path,path2,callid)
        VALUES(@IN_CallTime,dateadd(second,@IN_CallLength,@IN_CallTime),@IN_Extension,@IN_PhoneNumber,@IN_Direction-1,0,0,@IN_CallLength,0,
            @IN_ManagerID,'test.wav','http://128.8.28.19:9084/wav/test.wav', @IN_CallCenterID)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    declare @v_temp int
    select @v_temp = round(rand()*1000,0)
    IF @V_DT_CALL = 1 --启用分布式
    BEGIN
        INSERT INTO SRV_CallCenter.HelpCenter.dbo.statrecord(channel,status,starttime,endtime,extno,uid)
            VALUES(@IN_Extension,7,@IN_CallTime,dateadd(s,@IN_CallLength,@IN_CallTime),@IN_Extension,@IN_ManagerID)
        INSERT INTO SRV_CallCenter.HelpCenter.dbo.statrecord(channel,status,starttime,endtime,extno,uid)
            VALUES(@IN_Extension,0,dateadd(s,@IN_CallLength,@IN_CallTime),dateadd(s,@IN_CallLength+@v_temp,@IN_CallTime),@IN_Extension,@IN_ManagerID)
        INSERT INTO SRV_CallCenter.HelpCenter.dbo.statrecord(channel,status,starttime,endtime,extno,uid)
            VALUES(@IN_Extension,20,dateadd(s,@IN_CallLength+@v_temp,@IN_CallTime),null,@IN_Extension,@IN_ManagerID)
    END
    ELSE IF (@V_DT_CALL = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'tvc_voice') --本地数据库
    BEGIN
        INSERT INTO HelpCenter..statrecord(channel,status,starttime,endtime,extno,uid)
            VALUES(@IN_Extension,7,@IN_CallTime,dateadd(s,@IN_CallLength,@IN_CallTime),@IN_Extension,@IN_ManagerID)
        INSERT INTO HelpCenter..statrecord(channel,status,starttime,endtime,extno,uid)
            VALUES(@IN_Extension,0,dateadd(s,@IN_CallLength,@IN_CallTime),dateadd(s,@IN_CallLength+@v_temp,@IN_CallTime),@IN_Extension,@IN_ManagerID)
        INSERT INTO HelpCenter..statrecord(channel,status,starttime,endtime,extno,uid)
            VALUES(@IN_Extension,20,dateadd(s,@IN_CallLength+@v_temp,@IN_CallTime),null,@IN_Extension,@IN_ManagerID)
    END*/
    ---------向CallCenter的数据库表HelpCenter中插入记录，便于测试
    BEGIN TRANSACTION
    INSERT INTO TCallRecords(Direction,CallTime,CallLength,ManagerID,Extension,CUST_ID,ContactID,PhoneNumber,BusinessID,Content,Status,CallCenterID)
        VALUES(@IN_Direction,@IN_CallTime,@IN_CallLength,@IN_ManagerID,@IN_Extension,@IN_CUST_ID,@IN_ContactID,@IN_PhoneNumber,@IN_BusinessID,@IN_Content,@IN_Status,@IN_CallCenterID)
    SELECT @OUT_CC_ID = @@IDENTITY
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'增加通话记录，客户经理：'+RTRIM(CONVERT(NVARCHAR(16),@IN_ManagerID))+N'|时间：'+RTRIM(CONVERT(NVARCHAR(30),@IN_CallTime))
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
