USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_LOGOUT   @IN_OP_CODE INT
WITH ENCRYPTION
AS
    DECLARE @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @IBUSI_FLAG = 1010402
    SELECT @SBUSI_NAME = N'操作员登出'
    SELECT @SSUMMARY = N'操作员登出'

    BEGIN TRANSACTION
    UPDATE TOPERATOR SET LOGOUT_TIME = CURRENT_TIMESTAMP,ONLINE = 2
        WHERE OP_CODE = @IN_OP_CODE
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'操作员登出，操作员编号：' + CONVERT(NVARCHAR(16),@IN_OP_CODE)
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_OP_CODE,@SSUMMARY)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    COMMIT TRANSACTION
    RETURN 100
GO
