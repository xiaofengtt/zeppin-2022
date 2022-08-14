USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TCUSTMANAGERS  @IN_MANAGERID           INT,            --关联对应于TOperator.OP_CODE（主键）
                                        @IN_EXTENSION           NVARCHAR(32),    --分机号码
                                        @IN_RECORDEXTENSION     NVARCHAR(50),    --录音分机号码
                                        @IN_DUTYNAME            NVARCHAR(60),   --岗位（职位）名称
                                        @IN_PROVIDESERVICES     INT,            --当前客户经理提供的服务类别
                                        @IN_INPUT_MAN           INT = 0         --操作员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -30401000, @IBUSI_FLAG = 30401
    SELECT @SBUSI_NAME = N'修改客户经理信息', @SSUMMARY = N'修改客户经理信息'

    IF NOT EXISTS(SELECT 1 FROM TCustManagers WHERE ManagerID = @IN_MANAGERID)
        RETURN @V_RET_CODE - 1 --客户经理记录不存在

    BEGIN TRANSACTION
    UPDATE TCustManagers
    SET Extension = @IN_EXTENSION,
        DutyName = @IN_DUTYNAME,
        ProvideServices = @IN_PROVIDESERVICES,
        RecordExtension = @IN_RECORDEXTENSION
    WHERE ManagerID = @IN_MANAGERID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'修改客户经理信息，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
