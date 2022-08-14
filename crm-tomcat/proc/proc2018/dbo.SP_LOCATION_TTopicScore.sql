USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_LOCATION_TTopicScore  @IN_SERIAL_NO         INTEGER,
                                          @IN_FLAG              INTEGER,        --1.向上 2.向下
                                          @IN_INPUT_MAN         INT = 0         --操作员
WITH ENCRYPTION
AS
    DECLARE @V_TOPIC_ID INTEGER,@V_TOPIC_VALUE_NO INTEGER,@V_MAX_TOPIC_VALUE_NO INTEGER
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -30503000, @IBUSI_FLAG = 30503
    SELECT @SBUSI_NAME = N'调整问卷分值表序号信息', @SSUMMARY = N'调整问卷分值表序号信息'

    IF NOT EXISTS(SELECT 1 FROM TTopicScore WHERE SERIAL_NO = @IN_SERIAL_NO)
        RETURN @V_RET_CODE - 1 --问卷题目分值表不存在

    SELECT @V_TOPIC_ID = TOPIC_ID, @V_TOPIC_VALUE_NO = TOPIC_VALUE_NO
    FROM TTopicScore WHERE SERIAL_NO = @IN_SERIAL_NO

    SELECT @V_MAX_TOPIC_VALUE_NO = ISNULL(MAX(TOPIC_VALUE_NO),0)
    FROM TTopicScore WHERE TOPIC_ID = @V_TOPIC_ID
BEGIN TRANSACTION
    IF @IN_FLAG = 1
    BEGIN
        IF @V_TOPIC_VALUE_NO > 1
        BEGIN
            UPDATE TTopicScore
            SET TOPIC_VALUE_NO = @V_TOPIC_VALUE_NO
            WHERE TOPIC_ID = @V_TOPIC_ID
                AND TOPIC_VALUE_NO = @V_TOPIC_VALUE_NO -1
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END

            UPDATE TTopicScore
            SET TOPIC_VALUE_NO = @V_TOPIC_VALUE_NO-1
            WHERE SERIAL_NO = @IN_SERIAL_NO
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END
        END
    END
    ELSE IF @IN_FLAG = 2
    BEGIN
        IF @V_TOPIC_VALUE_NO < @V_MAX_TOPIC_VALUE_NO
        BEGIN
            UPDATE TTopicScore
            SET TOPIC_VALUE_NO = @V_TOPIC_VALUE_NO
            WHERE TOPIC_ID = @V_TOPIC_ID
                AND TOPIC_VALUE_NO = @V_TOPIC_VALUE_NO +1
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END

            UPDATE TTopicScore
            SET TOPIC_VALUE_NO = @V_TOPIC_VALUE_NO+1
            WHERE SERIAL_NO = @IN_SERIAL_NO
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
