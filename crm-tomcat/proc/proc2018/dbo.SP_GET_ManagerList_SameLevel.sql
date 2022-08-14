USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_GET_ManagerList_SameLevel @IN_INPUT_MAN INT --当前操作员
WITH ENCRYPTION
AS
        --1.根据节点主经理来判断当前操作员
        --同级节点的主客户经理
        SELECT A.MANAGERID,A.MANAGERNAME FROM TCustManagerTree A, TCustManagerTree B
        WHERE A.LEVEL_ID = B.LEVEL_ID AND A.MANAGERID <> 0 AND A.MANAGERID IS NOT NULL
            AND (B.MANAGERID = @IN_INPUT_MAN) AND A.MANAGERID <> @IN_INPUT_MAN
        --同级节点的成员客户经理
        UNION ALL
        SELECT C.MANAGERID,C.MANAGERNAME FROM TCustManagerTree A, TCustManagerTree B, TCustManagerTreeMembers C
        WHERE A.LEVEL_ID = B.LEVEL_ID AND A.SERIAL_NO = C.TREE_ID
            AND (B.MANAGERID = @IN_INPUT_MAN) AND C.MANAGERID <> @IN_INPUT_MAN
        --2.根据节点成员经理来判断当前操作员
        --同级节点的主客户经理
        UNION ALL
        SELECT D.MANAGERID,D.MANAGERNAME FROM TCustManagerTreeMembers B, TCustManagerTree C, TCustManagerTree D
        WHERE B.TREE_ID = C.SERIAL_NO AND C.LEVEL_ID = D.LEVEL_ID AND D.MANAGERID <> 0 AND D.MANAGERID IS NOT NULL
            AND (B.MANAGERID = @IN_INPUT_MAN) AND D.MANAGERID <> @IN_INPUT_MAN
        --同级节点的成员客户经理
        UNION ALL
        SELECT A.MANAGERID,A.MANAGERNAME FROM TCustManagerTreeMembers B, TCustManagerTree C, TCustManagerTree D, TCustManagerTreeMembers A
        WHERE B.TREE_ID = C.SERIAL_NO AND C.LEVEL_ID = D.LEVEL_ID AND A.TREE_ID = D.SERIAL_NO
            AND (B.MANAGERID = @IN_INPUT_MAN) AND A.MANAGERID <> @IN_INPUT_MAN


--******************************************************************************
-- = Filename: query\SP_GET_MANAGERREE_CHILDREN.sql
--******************************************************************************
PRINT ' ==================== creating Procedure SP_GET_MANAGERREE_CHILDREN =================== '
/*
 Auth   : 关进恒
 Time   : 20091122 hesl 20110516 添加字段LEVEL_NO,LEVEL_NAME
 Purpose: 查询出该节点的次级所有子节点
 UsedBy :
 Output :
exec SP_GET_MANAGERREE_CHILDREN 1
*/
GO
