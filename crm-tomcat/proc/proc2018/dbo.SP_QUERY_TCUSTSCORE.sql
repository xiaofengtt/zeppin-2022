USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCUSTSCORE @IN_CUST_NO NVARCHAR(16),
                                     @IN_CUST_NAME NVARCHAR(200),
                                     @IN_SUBJECT_ID INT,
                                     @IN_CURRENTDATE INT,
                                     @IN_INPUT_MAN INT
WITH ENCRYPTION
AS
    BEGIN
        SELECT A.*,B.CUST_NO,B.CUST_NAME,(SELECT C.SUBJECT_NAME FROM TSCORESUBJECT C WHERE C.SUBJECT_ID = A.SUBJECT_ID) AS SUBJECT_NAME
          FROM TCUSTSCORE A ,TCUSTOMERS B
            WHERE  (A.SUBJECT_ID = @IN_SUBJECT_ID OR ISNULL(@IN_SUBJECT_ID,'') = N'')
                AND (B.CUST_NO LIKE '%' + @IN_CUST_NO + '%' OR ISNULL(@IN_CUST_NO,'') = N'')
                AND (B.CUST_NAME LIKE '%' + @IN_CUST_NAME + '%' OR ISNULL(@IN_CUST_NAME,'') = N'')
                AND ((@IN_CURRENTDATE BETWEEN A.CURRENTDATE AND A.END_DATE) OR ISNULL(@IN_CURRENTDATE,'') = N'')
                AND A.CUST_ID = B.CUST_ID
    END
    RETURN 100
GO
