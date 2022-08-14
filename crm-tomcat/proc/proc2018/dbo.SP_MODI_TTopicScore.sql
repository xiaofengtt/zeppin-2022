USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TTopicScore  @IN_SERIAL_NO          INTEGER,
                                      @IN_QUES_ID            INTEGER,
                                      @IN_TOPIC_ID           INTEGER,
                                      @IN_QUES_TITLE            NVARCHAR(30),
                                      @IN_TOPIC_VALUE       NVARCHAR(30),
                                      @IN_Score             INTEGER,
                                      @IN_REMARK                NVARCHAR(512),
                                      @IN_INPUT_MAN         INT = 0         --操作员
WITH ENCRYPTION
AS
DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
SELECT @V_RET_CODE = -30503000, @IBUSI_FLAG = 30503
SELECT @SBUSI_NAME = N'添加问卷分值表信息', @SSUMMARY = N'添加问卷分值表信息'

BEGIN TRANSACTION
    UPDATE TTopicScore
    SET QUES_ID = @IN_QUES_ID,
        TOPIC_ID = @IN_TOPIC_ID,
        QUES_TITLE = @IN_QUES_TITLE,
        TOPIC_VALUE = @IN_TOPIC_VALUE,
        Score = @IN_Score,
        REMARK = @IN_REMARK
    WHERE SERIAL_NO = @IN_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'修改问卷分值表信息，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
