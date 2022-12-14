USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_DEL_TMANAGERTEAMMEMBERS     @IN_SERIAL_NO           INT,            --团队成员ID
                                                @IN_INPUT_MAN           INT = 0         --操作员


WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -30402000, @IBUSI_FLAG = 30402
    SELECT @SBUSI_NAME = N'删除营销团队成员记录', @SSUMMARY = N'删除营销团队成员记录'
    IF NOT EXISTS(SELECT 1 FROM TManagerTeamMembers WHERE SERIAL_NO = @IN_SERIAL_NO)
        RETURN @V_RET_CODE - 2  --对应销团队成员记录信息不存在

    BEGIN TRANSACTION
    DELETE FROM TMANAGERTEAMMEMBERS
    WHERE SERIAL_NO = @IN_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'删除营销团队成员记录，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
