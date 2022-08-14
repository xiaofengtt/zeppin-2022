USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TTEAMINFO   @IN_TEAM_ID                  INT,                --项目组ID
                                     @IN_TEAM_NAME                NVARCHAR(200),      --项目组名称
                                     @IN_TEAM_SUMMARY             NVARCHAR(1000),     --项目组说明
                                     @IN_TEAM_ADMIN               INT,                --项目组长，对应表TOPERATOR.OP_CODE
                                     @IN_INPUT_MAN                INT                --录入人员,对应表TOPERATOR.OP_CODE


WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'合同管理之修改项目组'
    SELECT @SSUMMARY = N'合同管理之修改项目组'
    SELECT @IBUSI_FLAG = 50001
    SELECT @V_RET_CODE = -50001000

    BEGIN TRANSACTION

    UPDATE TTEAMINFO SET
            TEAM_NAME=@IN_TEAM_NAME,TEAM_SUMMARY=@IN_TEAM_SUMMARY,TEAM_ADMIN=@IN_TEAM_ADMIN,INPUT_MAN=@IN_INPUT_MAN
            WHERE TEAM_ID=@IN_TEAM_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'合同管理之修改项目组，项目组名称'+@IN_TEAM_NAME
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
