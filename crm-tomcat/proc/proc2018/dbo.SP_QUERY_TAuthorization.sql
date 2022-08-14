USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TAuthorization @IN_CA_ID             INT = 0 ,            --客户授权集合ID
                                         @IN_CA_NAME           NVARCHAR(60) = N'',  --客户授权集合名称
                                         @IN_CA_DESCRIPTION    NVARCHAR(200) = N'',  --客户授权集合描述
                                         @IN_ManagerID         INT =0,               --客户经理ID   新增的参数
                                         @IN_INPUT_MAN         INT                  --操作员       新增的参数，记得去修改代码，以后所有的过程都写上这个参数
WITH ENCRYPTION
AS
    IF ISNULL(@IN_CA_ID,0) <> 0
        SELECT *
        FROM TAuthorization
        WHERE CA_ID = @IN_CA_ID
    ELSE
    --判断该操作员是否有授权权限 如果有，查询所有
    IF EXISTS(SELECT 1 FROM TOPROLE A,TROLERIGHT B
     WHERE A.OP_CODE =@IN_INPUT_MAN AND A.ROLE_ID =B.ROLE_ID AND B.MENU_ID =N'40502' AND B.FUNC_ID =103)
            SELECT *
            FROM TAuthorization
            WHERE (CA_NAME LIKE '%' + @IN_CA_NAME + '%' OR ISNULL(@IN_CA_NAME,'') = N'')
                AND (CA_DESCRIPTION LIKE '%' + @IN_CA_DESCRIPTION + '%' OR ISNULL(@IN_CA_DESCRIPTION,'') = N'')

        --判断该操作员是否有授权权限 如果没有，查询该操作员自己作为经理的授权集
    ELSE
       SELECT *
            FROM TAuthorization
        WHERE (ManagerID=@IN_ManagerID OR ISNULL(@IN_ManagerID,0) = 0)     --如果不需要判断授权权限，而需要把所有授权集查出来,就将@IN_ManagerID设为0
        AND (CA_NAME LIKE '%' + @IN_CA_NAME + '%' OR ISNULL(@IN_CA_NAME,'') = N'')
                AND (CA_DESCRIPTION LIKE '%' + @IN_CA_DESCRIPTION + '%' OR ISNULL(@IN_CA_DESCRIPTION,'') = N'')

    RETURN 100
GO
