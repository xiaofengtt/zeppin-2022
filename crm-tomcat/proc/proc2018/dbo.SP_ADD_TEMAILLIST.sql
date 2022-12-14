USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TEMAILLIST  @IN_SUBJECT        NVARCHAR(200),
                                    @IN_BODY           NTEXT,
                                    @IN_INPUT_MAN      INT,
                                    @OUT_SERIAL_NO     INT OUTPUT
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT
    DECLARE @SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -23001000, @IBUSI_FLAG = 23001
    SELECT @SBUSI_NAME = N'增加EMAIL', @SSUMMARY = N'增加EMAIL'

    BEGIN TRANSACTION

    INSERT INTO TEMAILLIST(SUBJECT, BODY, INPUT_MAN)
        VALUES(@IN_SUBJECT, @IN_BODY, @IN_INPUT_MAN)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    SELECT @OUT_SERIAL_NO = @@IDENTITY

    SELECT @SSUMMARY = N'增加EMAIL，主题：' + RTRIM(@IN_SUBJECT)
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
