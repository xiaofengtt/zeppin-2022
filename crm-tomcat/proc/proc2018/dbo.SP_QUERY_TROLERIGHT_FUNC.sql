USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TROLERIGHT_FUNC @IN_ROLE_ID INT,@IN_MENU_ID NVARCHAR(10)
WITH ENCRYPTION
AS
    DECLARE @V_USER_ID INT
    SELECT @V_USER_ID = USER_ID FROM TSYSTEMINFO
    SELECT A.FUNC_ID,B.FUNC_NAME FROM TROLERIGHT A,TFUNCTYPE B
        WHERE (A.ROLE_ID = @IN_ROLE_ID AND A.MENU_ID = @IN_MENU_ID)
            AND (A.MENU_ID = B.MENU_ID AND A.FUNC_ID = B.FUNC_ID)
            AND (A.MENU_ID IN (SELECT MENU_ID FROM TMENUINFO WHERE USER_ID = @V_USER_ID OR USER_ID = 0))
            AND (A.FUNC_ID IN (SELECT FUNC_ID FROM TFUNCTYPE WHERE USER_ID = @V_USER_ID OR USER_ID = 0))
        ORDER BY A.ROLE_ID,A.MENU_ID,A.FUNC_ID
GO
