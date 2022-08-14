USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TAuthorization_Status  @IN_SERIAL_NO               INT,              --客户授权记录ID
                                                @IN_Status                  INT,              --启用状态
                                                @IN_INPUT_MAN               INT               --录入操作员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -40502000, @IBUSI_FLAG = 40502
    SELECT @SBUSI_NAME = N'修改客户授权集合授权状态（启用、禁用）授权状态（启用、禁用）', @SSUMMARY = N'修改客户授权集合授权状态（启用、禁用）'
    BEGIN TRANSACTION
    UPDATE TAuthorizationShare
    SET Status = @IN_Status
    WHERE SERIAL_NO = @IN_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'修改客户授权集合授权状态（启用、禁用），操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
