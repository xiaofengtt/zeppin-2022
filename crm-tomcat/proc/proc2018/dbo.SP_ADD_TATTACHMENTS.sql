USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TATTACHMENTS    @IN_DF_TABLE_ID         INT,            --相关表ID
                                        @IN_DF_TABLE_NAME       NVARCHAR(60),   --相关表名称
                                        @IN_DF_SERIAL_NO        INT,            --相关记录序号
                                        @IN_SAVE_NAME           NVARCHAR(500),  --保存时的名称
                                        @IN_ORIGIN_NAME         NVARCHAR(500),  --原始文件名
                                        @IN_FILE_SIZE           INT,            --文件大小
                                        @IN_DESCRIPTION         NVARCHAR(500),  --文件描述
                                        @IN_INPUT_MAN           INT = 0         --操作员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT, @SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    DECLARE @V_SERIAL_NO INT
    SELECT @V_RET_CODE = -10801000, @IBUSI_FLAG = 10801
    SELECT @SBUSI_NAME = '增加附件', @SSUMMARY = '增加附件'

    BEGIN TRANSACTION

    INSERT INTO TATTACHMENTS(DF_TABLE_ID,DF_TABLE_NAME,DF_SERIAL_NO,SAVE_NAME,ORIGIN_NAME,FILE_SIZE,DESCRIPTION)
        VALUES(@IN_DF_TABLE_ID,@IN_DF_TABLE_NAME,@IN_DF_SERIAL_NO,@IN_SAVE_NAME,@IN_ORIGIN_NAME,@IN_FILE_SIZE,@IN_DESCRIPTION)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        COMMIT TRANSACTION
        RETURN -100
    END

    SET @V_SERIAL_NO = @@IDENTITY

    SET @SSUMMARY = N'增加附件,对应表：' + @IN_DF_TABLE_NAME + N',附件记录号' + RTRIM(CONVERT(CHAR,@V_SERIAL_NO)) + N'文件名：' + @IN_ORIGIN_NAME
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        COMMIT TRANSACTION
        RETURN -100
    END

    COMMIT TRANSACTION
    RETURN 100
GO
