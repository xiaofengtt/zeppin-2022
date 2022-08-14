USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_LOCATION_QuesTopic  @IN_TOPIC_ID            INTEGER,
                                        @IN_FLAG                INTEGER,        --1.向上 2.向下
                                        @IN_INPUT_MAN           INT = 0         --操作员
WITH ENCRYPTION
AS
    DECLARE @V_TOPIC_SERIAL_NO INTEGER, @V_QUES_ID INTEGER,@V_MAX_TOPIC_SERIAL_NO INTEGER
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -30502000, @IBUSI_FLAG = 30502
    SELECT @SBUSI_NAME = N'调整问卷题目的序号', @SSUMMARY = N'调整问卷题目的序号'

     IF NOT EXISTS(SELECT 1 FROM TQuesTopic WHERE TOPIC_ID = @IN_TOPIC_ID)
        RETURN @V_RET_CODE - 1 --问卷题目信息不存在

    SELECT @V_TOPIC_SERIAL_NO = ISNULL(TOPIC_SERIAL_NO,0),@V_QUES_ID = ISNULL(QUES_ID,0)
    FROM TQuesTopic WHERE TOPIC_ID = @IN_TOPIC_ID

    SELECT @V_MAX_TOPIC_SERIAL_NO = ISNULL(MAX(TOPIC_SERIAL_NO),0)
    FROM TQuesTopic WHERE QUES_ID = @V_QUES_ID
BEGIN TRANSACTION
    IF @IN_FLAG = 1
    BEGIN
        IF @V_TOPIC_SERIAL_NO > 1
        BEGIN
            UPDATE TQuesTopic
            SET TOPIC_SERIAL_NO = @V_TOPIC_SERIAL_NO
            WHERE QUES_ID = @V_QUES_ID
                AND TOPIC_SERIAL_NO = @V_TOPIC_SERIAL_NO -1
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END

            UPDATE TQuesTopic
            SET TOPIC_SERIAL_NO = @V_TOPIC_SERIAL_NO-1
            WHERE TOPIC_ID = @IN_TOPIC_ID
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END
        END
    END
    ELSE IF @IN_FLAG = 2
    BEGIN
        IF @V_TOPIC_SERIAL_NO < @V_MAX_TOPIC_SERIAL_NO
        BEGIN
            UPDATE TQuesTopic
            SET TOPIC_SERIAL_NO = @V_TOPIC_SERIAL_NO
            WHERE QUES_ID = @V_QUES_ID
                AND TOPIC_SERIAL_NO = @V_TOPIC_SERIAL_NO + 1
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END

            UPDATE TQuesTopic
            SET TOPIC_SERIAL_NO = @V_TOPIC_SERIAL_NO + 1
            WHERE TOPIC_ID = @IN_TOPIC_ID
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END
        END
    END
COMMIT TRANSACTION
    RETURN 100
GO
