USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  SP_QUERY_TOPERATOR @IN_OP_CODE    INT,
                                     @IN_DEPART_ID  INT = 0,
                                     @IN_ROLE_ID    INT = 0,
                                     @IN_OP_NAME    NVARCHAR(20) = NULL,
                                     @IN_STATUS     INT = 0      --0全部1正常2注销
WITH ENCRYPTION
AS
    IF ISNULL(@IN_OP_CODE,0) <> 0
    BEGIN
        IF EXISTS(SELECT name FROM master..sysdatabases WHERE name = 'INTRUST') BEGIN
            SELECT A.*, 1 AS BOOK_CODE, (CASE WHEN ISNULL(B.SSO_Operator,'') = '' THEN 0 ELSE 1 END) IS_INTRUST 
                FROM TOPERATOR A LEFT JOIN INTRUST..TOperatorMap B ON A.LOGIN_USER = B.SSO_Operator 
                    WHERE A.OP_CODE = @IN_OP_CODE
        END
        ELSE BEGIN
            SELECT A.*, 1 AS BOOK_CODE, 0 IS_INTRUST 
                FROM TOPERATOR A
                    WHERE A.OP_CODE = @IN_OP_CODE
        END
    END
    ELSE
    BEGIN
        IF ISNULL(@IN_ROLE_ID,0) = 0
        SELECT *,1 AS BOOK_CODE FROM TOPERATOR
        WHERE
          ((DEPART_ID = @IN_DEPART_ID) OR (@IN_DEPART_ID = 0) OR (@IN_DEPART_ID IS NULL))
          AND (OP_NAME LIKE '%'+@IN_OP_NAME+'%' OR @IN_OP_NAME = N'' OR @IN_OP_NAME IS NULL)
          AND (STATUS = @IN_STATUS OR @IN_STATUS IS NULL OR @IN_STATUS = 0)
        ORDER BY OP_NAME
        ELSE
        BEGIN
            IF @IN_ROLE_ID = -10
                SELECT A.*,1 AS BOOK_CODE FROM TOPERATOR A,(SELECT OP_CODE FROM TOPROLE WHERE ROLE_ID IN(2,5) GROUP BY OP_CODE) B
                   WHERE A.OP_CODE = B.OP_CODE AND A.STATUS = 1
            ELSE
               SELECT A.*,1 AS BOOK_CODE FROM TOPERATOR A,TOPROLE B
               WHERE A.OP_CODE = B.OP_CODE
                  AND (A.DEPART_ID = @IN_DEPART_ID OR ISNULL(@IN_DEPART_ID,0)= 0)
                  AND (A.OP_NAME LIKE '%'+@IN_OP_NAME+'%' OR ISNULL(@IN_OP_NAME,'') = N'')
                  AND (STATUS = @IN_STATUS OR @IN_STATUS IS NULL OR @IN_STATUS = 0)
                  AND B.ROLE_ID = @IN_ROLE_ID
               ORDER BY OP_NAME
        END
    END
GO
