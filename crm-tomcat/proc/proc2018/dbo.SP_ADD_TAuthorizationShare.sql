USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TAuthorizationShare @IN_ShareType       INT,                   --1共享；2授权；3单客户有时限快捷授权
                                            @IN_SharDescription NVARCHAR(200) = N'',   --描述
                                            @IN_Status          INT = 2,
                                            @IN_SourceManagerID INT = 0 ,              --源客户经理，1共享时需要指定源客户经理
                                            @IN_CA_ID           INT = 0 ,              --2授权时，需要指定授权集ID
                                            @IN_ManagerID       INT = 0 ,              --目标客户经理，被共享或授权的
                                            @IN_INPUT_MAN       INT = 0 ,              --录入操作员
                                            @IN_CUST_ID         INT = NULL,            --客户ID，ShareType=3时有效
                                            @OUT_SERIAL_NO      INT = NULL OUTPUT,     --主键
                                            @IN_INVALID_TIME    DATETIME = NULL,       --失效时间，ShareType=3时有效
                                            @IN_START_TIME      DATETIME = NULL       --生效时间，ShareType=3时有效
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_ERROR NVARCHAR(200)
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200),@V_INPUT_TIME DATETIME
    SELECT @V_RET_CODE = -40502000, @IBUSI_FLAG = 40502
    SELECT @SBUSI_NAME = N'添加授权集分配记录', @SSUMMARY = N'添加授权集分配记录'

    ------------------------------------------------------------------------
    BEGIN TRY
    --1.业务逻辑与操作
    --1.1校验
    IF NOT EXISTS(SELECT 1 FROM TCustManagers  WHERE ManagerID = @IN_SourceManagerID)
    BEGIN
        SET @V_ERROR = N'源客户经理信息不存在'
        RAISERROR(@V_ERROR,16,3)
    END
    IF NOT EXISTS(SELECT 1 FROM TCustManagers  WHERE ManagerID = @IN_ManagerID)
    BEGIN
        SET @V_ERROR = N'目标客户经理信息不存在'
        RAISERROR(@V_ERROR,16,3)
    END
    IF (@IN_ShareType=2) AND (NOT EXISTS(SELECT 1 FROM TAuthorization   WHERE CA_ID = @IN_CA_ID) ) --授权时，授权集必须存在，共享时不必需
    BEGIN
        SET @V_ERROR = N'授权集信息不存在'
        RAISERROR(@V_ERROR,16,3)
    END
    IF @IN_ShareType=2 AND EXISTS(SELECT 1 FROM TAuthorizationShare WHERE ShareType=2 AND CA_ID = @IN_CA_ID AND ManagerID=@IN_ManagerID)
    BEGIN
        SET @V_ERROR = N'该授权集已经授权给了目标客户经理，不能重复授权'
        RAISERROR(@V_ERROR,16,3)
    END
    IF @IN_ShareType=1 AND EXISTS(SELECT 1 FROM TAuthorizationShare WHERE ShareType=1 AND SourceManagerID = @IN_SourceManagerID AND ManagerID=@IN_ManagerID)
    BEGIN
        SET @V_ERROR = N'该源客户经理已经共享给了目标客户经理，不能重复共享'
        RAISERROR(@V_ERROR,16,3)
    END
    IF (@IN_ShareType=3 AND @IN_CUST_ID<>0) AND (NOT EXISTS(SELECT 1 FROM TCustomers WHERE CUST_ID = @IN_CUST_ID) ) --快捷授权时，客户必须存在
    BEGIN
        SET @V_ERROR = N'未指定待授权的客户'
        RAISERROR(@V_ERROR,16,3)
    END
    IF (@IN_ShareType=3 AND @IN_CUST_ID=0) AND EXISTS(SELECT 1 FROM TAuthorizationShare WHERE ShareType=3 AND SourceManagerID=@IN_SourceManagerID AND ManagerID=@IN_ManagerID AND CUST_ID=0)
    BEGIN
        SET @V_ERROR = N'同样的授权已经存在，不需要重复授权！'
        RAISERROR(@V_ERROR,16,3)
    END  
    IF (@IN_ShareType=3 AND @IN_CUST_ID=0) AND EXISTS(SELECT 1 FROM TAuthorizationShare WHERE ShareType=3 AND SourceManagerID=@IN_SourceManagerID AND ManagerID=@IN_ManagerID)
    BEGIN
        SET @V_ERROR = N'已存在把部分客户授权给目标客户经理的记录，不需要重复授权！'
        RAISERROR(@V_ERROR,16,3)
    END   
    IF @IN_ShareType=3 AND @IN_CUST_ID<>0 AND
        EXISTS(SELECT 1 FROM TAuthorizationShare WHERE ShareType=3 AND SourceManagerID=@IN_SourceManagerID AND (CUST_ID=@IN_CUST_ID OR CUST_ID=0) AND ManagerID=@IN_ManagerID)
    BEGIN
        SET @V_ERROR = N'同样的授权已经存在，不需要重复授权！'
        RAISERROR(@V_ERROR,16,3)
    END
    IF dbo.GETDATEINT(@IN_INVALID_TIME) < 19000101
        SET @IN_INVALID_TIME = NULL
    IF @IN_ShareType = 3 --3单客户有时限快捷授权，直接有效
        SET @IN_Status = 1
    --1.2操作
    BEGIN TRANSACTION
    INSERT INTO TAuthorizationShare(ShareType,SharDescription,Status,SourceManagerID,CA_ID,ManagerID,CUST_ID,INVALID_TIME,START_TIME)
        VALUES(@IN_ShareType,@IN_SharDescription,@IN_Status,@IN_SourceManagerID,@IN_CA_ID,@IN_ManagerID,@IN_CUST_ID,@IN_INVALID_TIME,@IN_START_TIME)
    SELECT @OUT_SERIAL_NO = @@IDENTITY

    --2.日志
    SET @SSUMMARY = N'添加授权集分配记录，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
