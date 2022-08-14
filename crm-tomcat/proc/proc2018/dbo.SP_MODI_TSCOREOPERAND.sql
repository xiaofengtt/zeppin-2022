USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE SP_MODI_TSCOREOPERAND @IN_OPERAND_ID    INT,             --操作数ID(打分项)
                                       @IN_SUBJECT_ID    INT,             --科目ID
                                       @IN_OPERAND_NO    NVARCHAR(16),    --操作数编号
                                       @IN_OPERAND_NAME  NVARCHAR(60),    --操作数名称
                                       @IN_SCORING       INT,             --人工（系统）打分 1人工，2系统
                                       @IN_SOURCE        INT,             --来源(仅人工)1手工打分2通过计算打分
                                       @IN_SUMMARY       NVARCHAR(200),   --描述
                                       @IN_INPUT_MAN     INT              --操作员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'修改计分操作数信息'
    SELECT @SSUMMARY = N'修改计分操作数信息'
    SELECT @IBUSI_FLAG = 21003
    SELECT @V_RET_CODE = -21003000

    IF NOT EXISTS(SELECT * FROM TSCOREOPERAND WHERE OPERAND_ID = @IN_OPERAND_ID)
        RETURN @V_RET_CODE - 1   --计分操作数信息不存在

    BEGIN TRANSACTION
    UPDATE TSCOREOPERAND
        SET OPERAND_NO = @IN_OPERAND_NO, OPERAND_NAME = @IN_OPERAND_NAME,
            SUBJECT_ID = @IN_SUBJECT_ID, SCORING = @IN_SCORING,
            SOURCE = @IN_SOURCE,SUMMARY = @IN_SUMMARY,INPUT_MAN = @IN_INPUT_MAN
        WHERE OPERAND_ID = @IN_OPERAND_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'修改计分操作数信息:'+@IN_OPERAND_NAME
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
