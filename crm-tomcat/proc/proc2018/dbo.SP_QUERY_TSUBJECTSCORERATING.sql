USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TSUBJECTSCORERATING @IN_RATING_ID     INT,                  --科目ID
                                              @IN_INPUT_MAN     INT,                  --操作员
                                              @IN_SUBJECT_ID    INT = 0,              --科目ID
                                              @IN_RATING_NO     NVARCHAR(16)=NULL,    --评级编号
                                              @IN_RATING_NAME   NVARCHAR(60)=NULL     --评级名称
WITH ENCRYPTION
AS
    BEGIN
          SELECT A.*,(SELECT B.SUBJECT_NAME FROM TSCORESUBJECT B WHERE B.SUBJECT_ID = A.SUBJECT_ID) AS SUBJECT_NAME 
          FROM TSUBJECTSCORERATING A 
            WHERE  (A.RATING_ID = @IN_RATING_ID OR ISNULL(@IN_RATING_ID,'') = N'')
                AND (A.SUBJECT_ID = @IN_SUBJECT_ID OR ISNULL(@IN_SUBJECT_ID,'') = N'')
                AND (A.RATING_NO LIKE '%' + @IN_RATING_NO + '%' OR ISNULL(@IN_RATING_NO,'') = N'')
                AND (A.RATING_NAME LIKE '%' + @IN_RATING_NAME + '%' OR ISNULL(@IN_RATING_NAME,'') = N'')
    END
    RETURN 100
GO
