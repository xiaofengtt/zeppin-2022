USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TFAQS @IN_SERIAL_NO    INT,
                               @IN_FAQ_TITLE    NVARCHAR(60),   --文章标题
                               @IN_FAQ_KEYWORDS NVARCHAR(200),  --文章关键字
                               @IN_FAQ_CONTENT  NTEXT,          --文章内容
                               @IN_INPUT_MAN    INT,
                               @IN_CLASS_NO     NVARCHAR(30) = NULL --文章分类编号
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT, @SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -19003000, @IBUSI_FLAG = 19003
    SELECT @SBUSI_NAME = N'知识库条目修改', @SSUMMARY = N'知识库条目修改'

    BEGIN TRANSACTION

    UPDATE TFAQS SET FAQ_TITLE = @IN_FAQ_TITLE,FAQ_KEYWORDS = @IN_FAQ_KEYWORDS,FAQ_CONTENT = @IN_FAQ_CONTENT,
                     UPDATE_TIME = GETDATE(),INPUT_MAN = @IN_INPUT_MAN, CLASS_NO = @IN_CLASS_NO
        WHERE SERIAL_NO = @IN_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'知识库条目修改'
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
