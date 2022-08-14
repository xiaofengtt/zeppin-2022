USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TTopicScore   @IN_SERIAL_NO           INTEGER = 0,
                                        @IN_QUES_ID             INTEGER = 0,
                                        @IN_TOPIC_ID            INTEGER = 0,
                                        @IN_QUES_TITLE          NVARCHAR(30) ='',
                                        @IN_TOPIC_VALUE_NO      INTEGER = 0,
                                        @IN_TOPIC_VALUE         NVARCHAR(30) = N'',
                                        @IN_Score               INTEGER = 0,
                                        @IN_REMARK              NVARCHAR(512) = N'',
                                        @IN_INPUT_MAN           INT = 0         --操作员
WITH ENCRYPTION
AS
IF ISNULL(@IN_SERIAL_NO,0) <> 0
    BEGIN
        SELECT SERIAL_NO,QUES_ID,TOPIC_ID,QUES_TITLE,TOPIC_VALUE_NO,TOPIC_VALUE,Score,REMARK
        FROM TTopicScore
        WHERE SERIAL_NO = @IN_SERIAL_NO
    END
ELSE
    BEGIN
        SELECT SERIAL_NO,QUES_ID,TOPIC_ID,QUES_TITLE,TOPIC_VALUE_NO,TOPIC_VALUE,Score,REMARK
        FROM TTopicScore
        WHERE (QUES_ID = @IN_QUES_ID OR ISNULL(@IN_QUES_ID,0) = 0 )
            AND (TOPIC_ID = @IN_TOPIC_ID OR ISNULL(@IN_TOPIC_ID,0) = 0 )
            AND (QUES_TITLE LIKE '%' + @IN_QUES_TITLE + '%' OR ISNULL(@IN_QUES_TITLE,'') = N'')
            AND (TOPIC_VALUE_NO = @IN_TOPIC_VALUE_NO OR ISNULL(@IN_TOPIC_VALUE_NO,0) = 0 )
            AND (TOPIC_VALUE LIKE '%' + @IN_TOPIC_VALUE + '%' OR ISNULL(@IN_TOPIC_VALUE,'') = N'')
            AND (Score = @IN_Score OR ISNULL(@IN_Score,0) = 0 )
            AND (REMARK LIKE '%' + @IN_REMARK + '%' OR ISNULL(@IN_REMARK,'') = N'')
        ORDER BY TOPIC_VALUE_NO ASC
    END

    RETURN 100
GO
