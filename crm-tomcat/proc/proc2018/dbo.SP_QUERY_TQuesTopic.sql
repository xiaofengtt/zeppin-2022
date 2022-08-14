USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TQuesTopic    @IN_TOPIC_ID            INTEGER = 0,
                                        @IN_QUES_ID             INTEGER = 0,
                                        @IN_QUES_NO             NVARCHAR(8) ='',
                                        @IN_QUES_TITLE          NVARCHAR(60) = N'',
                                        @IN_TOPIC_SERIAL_NO     INTEGER = 0,
                                        @IN_TOPIC_CONTENT       NVARCHAR(512) = N'',
                                        @IN_TOPIC_TYPE          NVARCHAR(8),
                                        @IN_CREATOR             INTEGER = 0,
                                        @IN_REMARK              NVARCHAR(512) = N'',
                                        @IN_INPUT_MAN           INT = 0         --操作员
WITH ENCRYPTION
AS
IF ISNULL(@IN_TOPIC_ID,0) <> 0
    BEGIN
        SELECT TOPIC_ID,QUES_ID,QUES_NO,QUES_TITLE,TOPIC_SERIAL_NO,TOPIC_CONTENT,TOPIC_TYPE,CREATOR,CREATOR_NAME,INPUT_TIME,REMARK
        FROM TQuesTopic
        WHERE TOPIC_ID = @IN_TOPIC_ID
    END
ELSE
    BEGIN
        SELECT TOPIC_ID,QUES_ID,QUES_NO,QUES_TITLE,TOPIC_SERIAL_NO,TOPIC_CONTENT,TOPIC_TYPE,CREATOR,CREATOR_NAME,INPUT_TIME,REMARK
        FROM TQuesTopic
        WHERE (QUES_ID = @IN_QUES_ID OR ISNULL(@IN_QUES_ID,0) = 0 )
            AND (QUES_NO LIKE '%' + @IN_QUES_NO + '%' OR ISNULL(@IN_QUES_NO,'') = N'')
            AND (TOPIC_TYPE = @IN_TOPIC_TYPE OR ISNULL(@IN_TOPIC_TYPE,'') = N'')
            AND (QUES_TITLE LIKE '%' + @IN_QUES_TITLE + '%' OR ISNULL(@IN_QUES_TITLE,'') = N'')
            AND (TOPIC_SERIAL_NO = @IN_TOPIC_SERIAL_NO OR ISNULL(@IN_TOPIC_SERIAL_NO,0) = 0 )
            AND (TOPIC_CONTENT LIKE '%' + @IN_TOPIC_CONTENT + '%' OR ISNULL(@IN_TOPIC_CONTENT,'') = N'')
            AND (CREATOR = @IN_CREATOR OR ISNULL(@IN_CREATOR,0) = 0 )
            AND (REMARK LIKE '%' + @IN_REMARK + '%' OR ISNULL(@IN_REMARK,'') = N'')
        ORDER BY TOPIC_SERIAL_NO ASC
    END

    RETURN 100
GO
