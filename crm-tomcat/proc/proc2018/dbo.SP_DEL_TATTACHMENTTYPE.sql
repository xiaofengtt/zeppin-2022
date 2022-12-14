USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_DEL_TATTACHMENTTYPE @IN_ATTACHMENT_TYPE_ID INT,
                                        @IN_INPUT_MAN          INT

WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'删除文件附件类别'
    SELECT @SSUMMARY = N'删除文件附件类别'
    SELECT @IBUSI_FLAG = 10120
    SELECT @V_RET_CODE = -10120000

    IF NOT EXISTS(SELECT * FROM TATTACHMENTTYPE WHERE ATTACHMENT_TYPE_ID = @IN_ATTACHMENT_TYPE_ID)
        RETURN @V_RET_CODE - 11   -- 附件类别不存在

    BEGIN TRANSACTION

    DELETE FROM TATTACHMENTTYPE WHERE ATTACHMENT_TYPE_ID = @IN_ATTACHMENT_TYPE_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'删除文件附件类别：'+RTRIM(CONVERT(NVARCHAR(16),@IN_ATTACHMENT_TYPE_ID))
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
