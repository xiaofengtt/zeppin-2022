USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TQuestRecord   @IN_SERIAL_NO           INTEGER,
                                        --@IN_ServiceTaskID     INTEGER,            --任务ID
                                        --@IN_TargetCustID      INTEGER,            --客户ID
                                        --@IN_TOPIC_ID          INTEGER,            --题目ID
                                        @IN_TOPIC_VALE          NVARCHAR(30),       --题目值
                                        @IN_TOPIC_Score         INTEGER,            --题目分
                                        @IN_INPUT_MAN           INT = 0             --操作员
WITH ENCRYPTION
AS
DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)

SELECT @V_RET_CODE = -30504000, @IBUSI_FLAG = 30504
SELECT @SBUSI_NAME = N'修改问卷调查记录信息', @SSUMMARY = N'修改问卷调查记录信息'

BEGIN TRANSACTION
    UPDATE TQuestRecord
    SET TOPIC_VALE = @IN_TOPIC_VALE,
        TOPIC_Score = @IN_TOPIC_Score
    WHERE SERIAL_NO = @IN_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'修改问卷调查记录信息，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
