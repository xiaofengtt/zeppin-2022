﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE SP_MODI_TSCORESUBJECT @IN_SUBJECT_ID    INT,             --主键
																		@IN_SUBJECT_NO       NVARCHAR(16),    --科目编号
                                    @IN_SUBJECT_NAME     NVARCHAR(60),    --科目名称
                                    @IN_SUMMARY          NVARCHAR(200),   --描述
                                    @IN_INPUT_MAN        INT              --录入操作员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'修改评分科目'
    SELECT @SSUMMARY = N'修改评分科目'
    SELECT @IBUSI_FLAG = 21001
    SELECT @V_RET_CODE = -21001000

    IF NOT EXISTS(SELECT * FROM TSCORESUBJECT WHERE SUBJECT_ID = @IN_SUBJECT_ID)
        RETURN @V_RET_CODE - 1   --评分科目信息不存在

    BEGIN TRANSACTION
    UPDATE TSCORESUBJECT
    SET SUBJECT_NO = @IN_SUBJECT_NO, SUBJECT_NAME = @IN_SUBJECT_NAME,
        SUMMARY = @IN_SUMMARY,INPUT_MAN = @IN_INPUT_MAN
    WHERE SUBJECT_ID = @IN_SUBJECT_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'修改评分科目:'+@IN_SUBJECT_NAME
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