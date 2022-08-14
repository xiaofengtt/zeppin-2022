USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TSCORESUBJECT @IN_SUBJECT_ID     INT,            --科目ID,主键
                                        @IN_INPUT_MAN      INT,            --录入操作员
                                        @IN_SUBJECT_NO     NVARCHAR(16)= NULL,   --科目编号
                                        @IN_SUBJECT_NAME   NVARCHAR(60)= NULL   --科目名称
                                        
WITH ENCRYPTION
AS
    BEGIN
        SELECT * FROM TSCORESUBJECT
            WHERE  (SUBJECT_ID = @IN_SUBJECT_ID OR ISNULL(@IN_SUBJECT_ID,'') = N'')
                AND (SUBJECT_NO LIKE '%' + @IN_SUBJECT_NO + '%' OR ISNULL(@IN_SUBJECT_NO,'') = N'')
                AND (SUBJECT_NAME LIKE '%' + @IN_SUBJECT_NAME + '%' OR ISNULL(@IN_SUBJECT_NAME,'') = N'')
    END
    RETURN 100
GO
