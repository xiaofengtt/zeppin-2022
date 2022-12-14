USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TDICTPARAM @IN_TYPE_ID INT = NULL,
                                     @IN_TYPE_NAME NVARCHAR(20) = NULL,
                                     @IN_SERIAL_NO INT = 0,
                                     @IN_TYPE_CONTENT NVARCHAR(100) =NULL,
                                     @IN_ADDITIVE_VALUE NVARCHAR(200) =NULL
WITH ENCRYPTION
AS
    IF ISNULL(@IN_SERIAL_NO,0) = 0
        SELECT *
        FROM TDICTPARAM
        WHERE ((TYPE_ID = @IN_TYPE_ID) OR (@IN_TYPE_ID IS NULL) OR (@IN_TYPE_ID = 0))
            AND(TYPE_NAME LIKE '%'+@IN_TYPE_NAME+'%' OR @IN_TYPE_NAME IS NULL OR @IN_TYPE_NAME = N'')
            AND (TYPE_CONTENT LIKE '%'+@IN_TYPE_CONTENT+'%' OR @IN_TYPE_CONTENT IS NULL OR @IN_TYPE_CONTENT = N'')
            AND(ADDITIVE_VALUE LIKE '%'+@IN_ADDITIVE_VALUE+'%' OR @IN_ADDITIVE_VALUE IS NULL OR @IN_ADDITIVE_VALUE = N'')
        ORDER BY TYPE_VALUE
    ELSE
        SELECT * FROM TDICTPARAM WHERE SERIAL_NO = @IN_SERIAL_NO
GO
