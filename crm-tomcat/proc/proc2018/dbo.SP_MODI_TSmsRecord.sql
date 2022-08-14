USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TSmsRecord     @IN_SmsIndex        INTEGER,
                                        @IN_Status          INTEGER,
                                        @IN_StatusName      NVARCHAR(50),
                                        @IN_INPUT_MAN       INT = 0          --操作员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -40307000, @IBUSI_FLAG = 40307
    SELECT @SBUSI_NAME = N'修改短信发送记录信息', @SSUMMARY = N'修改短信发送记录信息'

    BEGIN TRANSACTION
        UPDATE TSmsRecord
        SET Status = @IN_Status,
            StatusName = @IN_StatusName
        WHERE SmsIndex = @IN_SmsIndex
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END

        SET @SSUMMARY = N'修改短信发送记录信息，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
