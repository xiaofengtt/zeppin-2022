USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TQuestInfo    @IN_QUES_ID         INTEGER = 0,
                                        @IN_QUES_NO         NVARCHAR(8) ='',    --问卷编号
                                        @IN_QUES_TITLE      NVARCHAR(60) ='',   --问卷题目
                                        @IN_QUES_TYPE       NVARCHAR(16) = N'',
                                        @IN_CREATOR         INTEGER = 0,
                                        @IN_STATUS          INTEGER,
                                        @IN_INPUT_MAN       INT = 0         --操作员
WITH ENCRYPTION
AS
IF ISNULL(@IN_QUES_ID,0) <> 0
    BEGIN
        SELECT QUES_ID,QUES_NO,QUES_TITLE,QUES_TYPE,QUES_TYPE_NAME,CREATOR,CREATOR_NAME,INPUT_TIME,REMARK,STATUS
        FROM TQuestInfo
        WHERE QUES_ID = @IN_QUES_ID
    END
ELSE
    BEGIN
        SELECT QUES_ID,QUES_NO,QUES_TITLE,QUES_TYPE,QUES_TYPE_NAME,CREATOR,CREATOR_NAME,INPUT_TIME,REMARK,STATUS
        FROM TQuestInfo
        WHERE (QUES_NO LIKE '%' + @IN_QUES_NO + '%' OR ISNULL(@IN_QUES_NO,'') = N'')
            AND (QUES_TITLE LIKE '%' + @IN_QUES_TITLE + '%' OR ISNULL(@IN_QUES_TITLE,'') = N'')
            AND (QUES_TYPE = @IN_QUES_TYPE  OR ISNULL(@IN_QUES_TYPE,'') = N'')
            AND (CREATOR = @IN_CREATOR OR ISNULL(@IN_CREATOR,0) = 0 )
            AND (STATUS = @IN_STATUS OR ISNULL(@IN_STATUS,0) = 0 )
    END

    RETURN 100
GO
