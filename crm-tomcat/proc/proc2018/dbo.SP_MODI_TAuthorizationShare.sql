USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TAuthorizationShare @IN_SERIAL_NO       INT,            --ID
                                             @IN_SharDescription NVARCHAR(60) ,  --描述
                                             @IN_SourceManagerID INT,            --源客户经理，1共享时需要指定源客户经理
                                             @IN_CA_ID           INT = 0,        --2授权时，需要指定授权集ID，共享时允许ca_id为空
                                             @IN_ManagerID       INT,            --目标客户经理，被共享或授权的
                                             @IN_INPUT_MAN       INT,            --录入操作员
                                             @IN_CUST_ID         INT = NULL,     --客户ID，ShareType=3时有效
                                             @IN_INVALID_TIME    DATETIME = NULL,--失效时间，ShareType=3时有效
                                             @IN_START_TIME      DATETIME = NULL --生效时间，ShareType=3时有效
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_ERROR NVARCHAR(200)
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -40502000, @IBUSI_FLAG = 40502
    SELECT @SBUSI_NAME = N'修改授权集授权记录', @SSUMMARY = N'修改授权集授权记录'

    ------------------------------------------------------------------------
    BEGIN TRY
    --1.业务逻辑与操作
    --1.1校验
    IF dbo.GETDATEINT(@IN_INVALID_TIME) < 19800101
        SET @IN_INVALID_TIME = NULL
    IF NOT EXISTS(SELECT 1 FROM TAuthorizationShare WHERE SERIAL_NO = @IN_SERIAL_NO)
    BEGIN
        SET @V_ERROR = N'记录不存在！'
        RAISERROR(@V_ERROR,16,3)
    END
    IF EXISTS(SELECT 1 FROM TAuthorizationShare WHERE SERIAL_NO = @IN_SERIAL_NO AND ShareType<>3 AND Status=1)
    BEGIN
        SET @V_ERROR = N'此授权集授权已经启用，要禁用后才能修改'
        RAISERROR(@V_ERROR,16,3)
    END

    --1.2操作
    BEGIN TRANSACTION
    UPDATE TAuthorizationShare
        SET SharDescription = @IN_SharDescription,
            SourceManagerID = @IN_SourceManagerID,
            CA_ID = @IN_CA_ID,
            ManagerID = @IN_ManagerID,
            CUST_ID = @IN_CUST_ID, INVALID_TIME = @IN_INVALID_TIME, START_TIME = @IN_START_TIME
        WHERE SERIAL_NO = @IN_SERIAL_NO

    --2.日志
    SET @SSUMMARY = N'修改授权集授权记录，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
