USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TFAQS_RELATED @IN_SERIAL_NO   INT,
                                        @IN_INPUT_MAN   INT = NULL
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_KEYWORDS NVARCHAR(200)

    SELECT @V_KEYWORDS = @V_KEYWORDS FROM TFAQS WHERE SERIAL_NO = @IN_SERIAL_NO
    SELECT TOP 4 SERIAL_NO,FAQ_TITLE FROM V_FAQS A
        WHERE SERIAL_NO <> @IN_SERIAL_NO
            AND EXISTS(SELECT 1 FROM dbo.F_SPLIT(@V_KEYWORDS,' ') Z WHERE A.FAQ_KEYWORDS LIKE '%'+Z.TEXTSTR+'%' )
        ORDER BY POPULARITY_RATING DESC
GO
