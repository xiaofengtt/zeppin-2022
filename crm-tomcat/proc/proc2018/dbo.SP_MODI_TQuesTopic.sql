﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TQuesTopic     @IN_TOPIC_ID            INTEGER,
                                        @IN_QUES_ID             INTEGER,
                                        --@IN_TOPIC_SERIAL_NO     INTEGER   ,
                                        @IN_TOPIC_CONTENT       NVARCHAR(512),
                                        @IN_TOPIC_TYPE          NVARCHAR(8),
                                        @IN_REMARK              NVARCHAR(512),
                                        @IN_INPUT_MAN           INT = 0         --操作员
WITH ENCRYPTION
AS
DECLARE @V_QUES_NO NVARCHAR(8),@V_QUES_TITLE NVARCHAR(60)
DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)

SELECT @V_RET_CODE = -30502000, @IBUSI_FLAG = 30502
SELECT @SBUSI_NAME = N'修改问卷题目信息', @SSUMMARY = N'修改问卷题目信息'

IF NOT EXISTS(SELECT 1 FROM TQuestInfo WHERE QUES_ID = @IN_QUES_ID)
    RETURN -30502001   --问卷信息不存在

SELECT @V_QUES_NO = ISNULL(QUES_NO,''),@V_QUES_TITLE = ISNULL(QUES_TITLE,'') FROM TQuestInfo WHERE QUES_ID = @IN_QUES_ID

BEGIN TRANSACTION
    UPDATE TQuesTopic
    SET QUES_ID = @IN_QUES_ID,
        QUES_NO = @V_QUES_NO,
        QUES_TITLE = @V_QUES_TITLE,
        --TOPIC_SERIAL_NO = @IN_TOPIC_SERIAL_NO,
        TOPIC_CONTENT = @IN_TOPIC_CONTENT,
        TOPIC_TYPE = @IN_TOPIC_TYPE,
        REMARK = @IN_REMARK
    WHERE TOPIC_ID = @IN_TOPIC_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'修改问卷题目信息，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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