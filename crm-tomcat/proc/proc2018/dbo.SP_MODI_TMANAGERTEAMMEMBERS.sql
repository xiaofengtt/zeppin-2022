﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TMANAGERTEAMMEMBERS    @IN_SERIAL_NO           INT,            --团队成员ID
                                                @IN_TEAM_ID             NVARCHAR(64),   --团队编号
                                                @IN_MANAGERID           NVARCHAR(64),   --团队名称
                                                @IN_DESCRIPTION         NVARCHAR(256),  --备注说明
                                                @IN_INPUT_MAN           INT = 0         --操作员


WITH ENCRYPTION
AS
    DECLARE @V_MANAGER_NAME NVARCHAR(64),@V_TEAM_NO NVARCHAR(30),@V_TEAM_NAME NVARCHAR(64)
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -30402000, @IBUSI_FLAG = 30402
    SELECT @SBUSI_NAME = N'修改营销团队成员记录', @SSUMMARY = N'修改营销团队成员记录'
    IF NOT EXISTS(SELECT 1 FROM TCustManagers WHERE ManagerID = @IN_MANAGERID)
        RETURN -30401001    --对应经理信息不存在
    IF NOT EXISTS(SELECT 1 FROM TManagerTeams WHERE TEAM_ID = @IN_TEAM_ID)
        RETURN @V_RET_CODE-1    --对应团队信息不存在

    SELECT @V_MANAGER_NAME = ManagerName FROM TCustManagers WHERE ManagerID = @IN_MANAGERID
    SELECT @V_TEAM_NO = TEAM_NO,@V_TEAM_NAME = TEAM_NAME FROM TManagerTeams WHERE TEAM_ID = @IN_TEAM_ID
    BEGIN TRANSACTION
    UPDATE TManagerTeamMembers
    SET TEAM_ID = @IN_TEAM_ID,
        TEAM_NO = @V_TEAM_NO,
        TEAM_NAME = @V_TEAM_NAME,
        MANAGERID = @IN_MANAGERID,
        MANAGERNAME = @V_MANAGER_NAME,
        DESCRIPTION = @IN_DESCRIPTION
    WHERE SERIAL_NO = @IN_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'修改营销团队成员记录，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
