USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE SP_MODI_TMANAGERTEAMS  @IN_TEAM_ID             INT,            --团队ID
                                        @IN_TEAM_NO             NVARCHAR(64),   --团队编号
                                        @IN_TEAM_NAME           NVARCHAR(64),   --团队名称
                                        @IN_CREATE_DATE         INT,            --成立日期
                                        @IN_LEADER              INT ,           --负责人ID
                                        @IN_DESCRIPTION         NVARCHAR(256),  --备注说明
                                        @IN_INPUT_MAN           INT = 0,        --操作员
                                        @IN_MARK_FLAG           INT = 2,        --是否为营销团队 1是 2否 默认2
                                        @IN_PARENT_ID           INT = 0         --父团队ID,0为没有父团队
WITH ENCRYPTION
AS
    DECLARE @V_LEADER_NAME NVARCHAR(64)
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -50104000, @IBUSI_FLAG = 30412
    SELECT @SBUSI_NAME = N'修改营销团队记录', @SSUMMARY = N'修改营销团队记录'

    SELECT @V_LEADER_NAME = ManagerName FROM TCustManagers WHERE ManagerID = @IN_LEADER
    BEGIN TRANSACTION
    UPDATE TManagerTeams
    SET TEAM_NO     = @IN_TEAM_NO,
        TEAM_NAME   = @IN_TEAM_NAME,
        CREATE_DATE = @IN_CREATE_DATE,
        LEADER      = @IN_LEADER,
        LEADER_NAME = @V_LEADER_NAME,
        DESCRIPTION = @IN_DESCRIPTION,
        MARK_FLAG   =@IN_MARK_FLAG
        --PARENT_ID   =@IN_PARENT_ID --父团队不允许修改，即不能改变团队间关系
    WHERE TEAM_ID = @IN_TEAM_ID
	IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
	
	UPDATE TManagerTeamMembers
    SET TEAM_NO     = @IN_TEAM_NO,
        TEAM_NAME   = @IN_TEAM_NAME
    WHERE TEAM_ID = @IN_TEAM_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'修改营销团队记录，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
