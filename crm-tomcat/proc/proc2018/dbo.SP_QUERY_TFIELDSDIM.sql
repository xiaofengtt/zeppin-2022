USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TFIELDSDIM  @IN_MENU_ID     NVARCHAR(30)
WITH ENCRYPTION
AS
    SELECT *
        FROM TFIELDSDIM
        WHERE (MENU_ID = @IN_MENU_ID OR ISNULL(@IN_MENU_ID,'') = N'')
GO
