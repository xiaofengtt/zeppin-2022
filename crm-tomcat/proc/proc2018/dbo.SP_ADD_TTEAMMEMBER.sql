﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TTEAMMEMBER         @IN_TEAM_ID             NVARCHAR(64),   --项目组ID，对应表TTEAMINFO.TEAM_ID
                                            @IN_TEAM_MEMBER         INT,            --项目组成员ID，对应表TOPERATOR.OP_CODE
                                            @IN_TEAM_POSITION       NVARCHAR(100),
                                            @IN_INPUT_MAN           INT = 0         --操作员


WITH ENCRYPTION
AS
    DECLARE @V_MANAGER_NAME NVARCHAR(64),@V_TEAM_NO NVARCHAR(30),@V_TEAM_NAME NVARCHAR(64)
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -30402000, @IBUSI_FLAG = 30402
    SELECT @SBUSI_NAME = N'添加项目组成员记录', @SSUMMARY = N'添加项目组成员记录'
    
    IF EXISTS(SELECT 1 FROM TTEAMMEMBER WHERE TEAM_ID = @IN_TEAM_ID AND TEAM_MEMBER = @IN_TEAM_MEMBER)
        RETURN @V_RET_CODE - 3  --项目组成员不允许重复添加

    BEGIN TRANSACTION
    INSERT INTO TTEAMMEMBER(TEAM_ID,TEAM_MEMBER,TEAM_POSITION,INPUT_MAN)
    VALUES(@IN_TEAM_ID,@IN_TEAM_MEMBER,@IN_TEAM_POSITION,@IN_INPUT_MAN)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'添加项目组成员记录，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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