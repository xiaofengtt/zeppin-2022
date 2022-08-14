﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TMENUINFO
    @IN_MENU_ID    NVARCHAR(10),
    @IN_MENU_NAME  NVARCHAR(60),
    @IN_MENU_INFO  NVARCHAR(200),
    @IN_URL        NVARCHAR(100),
    @IN_INPUT_MAN  INT
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT
    DECLARE @SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    DECLARE @V_OLD_MENU_NAME NVARCHAR(60), @V_OLD_MENU_INFO NVARCHAR(200), @V_OLD_URL NVARCHAR(100)
    SELECT @V_RET_CODE = -11404000, @IBUSI_FLAG = 11404
    SELECT @SBUSI_NAME = N'修改菜单信息', @SSUMMARY = N'修改菜单信息，编号：' + @IN_MENU_ID

    IF NOT EXISTS(SELECT * FROM TMENUINFO WHERE MENU_ID = @IN_MENU_ID)
        RETURN @V_RET_CODE - 1 --记录不存在

    SELECT @IN_MENU_NAME = LTRIM(RTRIM(@IN_MENU_NAME))
    SELECT @IN_MENU_INFO = LTRIM(RTRIM(@IN_MENU_INFO))
    SELECT @IN_URL = LTRIM(RTRIM(@IN_URL))
    BEGIN TRANSACTION

    IF @IN_MENU_NAME IS NOT NULL AND @IN_MENU_NAME <> ''
    BEGIN
        SELECT @V_OLD_MENU_NAME = MENU_NAME FROM TMENUINFO WHERE MENU_ID = @IN_MENU_ID
        UPDATE TMENUINFO
            SET MENU_NAME = @IN_MENU_NAME
            WHERE MENU_ID = @IN_MENU_ID
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
        SELECT @SSUMMARY = @SSUMMARY + N' 菜单名称：' + @V_OLD_MENU_NAME + '->' + @IN_MENU_NAME
    END

    IF @IN_MENU_INFO IS NOT NULL AND @IN_MENU_INFO <> ''
    BEGIN
        SELECT @V_OLD_MENU_INFO = MENU_INFO FROM TMENUINFO WHERE MENU_ID = @IN_MENU_ID
        UPDATE TMENUINFO
            SET MENU_INFO = @IN_MENU_INFO
            WHERE MENU_ID = @IN_MENU_ID
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
        SELECT @SSUMMARY = @SSUMMARY + N' 菜单描述：' + @V_OLD_MENU_INFO + '->' + @IN_MENU_INFO
    END

    IF @IN_URL IS NOT NULL AND @IN_URL <> ''
    BEGIN
        SELECT @V_OLD_URL = URL FROM TMENUINFO WHERE MENU_ID = @IN_MENU_ID
        UPDATE TMENUINFO
            SET URL = @IN_URL
            WHERE MENU_ID = @IN_MENU_ID
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
        SELECT @SSUMMARY = @SSUMMARY + N' 菜单URL：' + @V_OLD_URL + '->' + @IN_URL
    END

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
