USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_GET_ManagerList     @IN_INPUT_MAN         INT        --操作员
WITH ENCRYPTION
AS
    --判断该操作员是否有授权权限 如果有，查询所有客户经理
    IF EXISTS(SELECT 1 FROM TOPROLE A,TROLERIGHT B
     WHERE A.OP_CODE =@IN_INPUT_MAN AND A.ROLE_ID =B.ROLE_ID AND B.MENU_ID =N'40502' AND B.FUNC_ID =103)
            SELECT *
            FROM TCustManagers
        --判断该操作员是否有授权权限 如果没有，则只能共享自己
    ELSE
       SELECT *
            FROM TCustManagers
        WHERE ManagerID=@IN_INPUT_MAN
GO
