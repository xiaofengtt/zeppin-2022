USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TMANAGERTEAMS   @IN_TEAM_NO             NVARCHAR(64),   --团队编号
                                        @IN_TEAM_NAME           NVARCHAR(64),   --团队名称
                                        @IN_CREATE_DATE         INT,            --成立日期
                                        @IN_LEADER              INT ,           --负责人ID
                                        @IN_DESCRIPTION         NVARCHAR(256),  --备注说明
                                        @IN_INPUT_MAN           INT = 0 ,       --操作员
                                        @IN_MARK_FLAG           INT = 2,        --是否为营销团队 1是 2否 默认2
										@IN_PARENT_ID           INT = 0,         --父团队ID
                                        @OUT_TEAM_ID            INT OUTPUT      --团队ID
                                        
WITH ENCRYPTION
AS
    DECLARE @V_LEADER_NAME NVARCHAR(64)
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -30402000, @IBUSI_FLAG = 30402
    SELECT @SBUSI_NAME = N'添加营销团队记录', @SSUMMARY = N'添加营销团队记录'
    IF NOT EXISTS(SELECT 1 FROM TCustManagers  WHERE ManagerID = @IN_LEADER)
        RETURN -30401001   --客户经理信息不存在
    IF EXISTS(SELECT 1 FROM TManagerTeams WHERE TEAM_NO = @IN_TEAM_NO)
        RETURN @V_RET_CODE - 4 --团队编号不允许重复
    SELECT @V_LEADER_NAME = ManagerName FROM TCustManagers  WHERE ManagerID = @IN_LEADER
    BEGIN TRANSACTION
    INSERT INTO TManagerTeams(TEAM_NO,TEAM_NAME,CREATE_DATE,LEADER,LEADER_NAME,DESCRIPTION,MARK_FLAG,PARENT_ID)
    VALUES(@IN_TEAM_NO,@IN_TEAM_NAME,@IN_CREATE_DATE,@IN_LEADER,@V_LEADER_NAME,@IN_DESCRIPTION,@IN_MARK_FLAG,@IN_PARENT_ID)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    SELECT @OUT_TEAM_ID = @@IDENTITY

    SET @SSUMMARY = N'添加营销团队记录，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
