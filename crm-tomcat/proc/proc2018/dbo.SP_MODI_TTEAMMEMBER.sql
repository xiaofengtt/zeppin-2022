﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TTEAMMEMBER    @IN_SERIAL_NO           INT,            --项目组成员ID
                                        @IN_TEAM_POSITION       NVARCHAR(100),
                                        @IN_INPUT_MAN           INT = 0         --操作员

WITH ENCRYPTION
AS
    DECLARE @V_MANAGER_NAME NVARCHAR(64),@V_TEAM_NO NVARCHAR(30),@V_TEAM_NAME NVARCHAR(64)
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -30402000, @IBUSI_FLAG = 30402
    SELECT @SBUSI_NAME = N'修改项目组成员记录', @SSUMMARY = N'修改项目组成员记录'
 
    BEGIN TRANSACTION
    UPDATE TTEAMMEMBER
    SET TEAM_POSITION = @IN_TEAM_POSITION
    WHERE SERIAL_NO = @IN_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'修改项目组成员记录，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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