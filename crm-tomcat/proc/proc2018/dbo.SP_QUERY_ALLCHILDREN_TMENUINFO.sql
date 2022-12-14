USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_ALLCHILDREN_TMENUINFO
    @IN_MENU_ID NVARCHAR(10) = NULL,
    @IN_MENU_NAME NVARCHAR(60) = NULL
WITH ENCRYPTION
AS
    DECLARE @V_USER_ID INT
    SELECT @V_USER_ID = USER_ID FROM TSYSTEMINFO
    SELECT *
        FROM TMENUINFO
        WHERE ((PARENT_ID <> '') AND (PARENT_ID IS NOT NULL))
            AND (MENU_ID LIKE @IN_MENU_ID+'%' OR @IN_MENU_ID IS NULL OR @IN_MENU_ID = N'')
            AND (MENU_NAME LIKE '%'+@IN_MENU_NAME+'%' OR @IN_MENU_NAME IS NULL OR @IN_MENU_NAME = N'')
            AND (USER_ID = @V_USER_ID OR USER_ID = 0)
            AND MENU_ID NOT IN('20409')
        ORDER BY MENU_ID
GO
