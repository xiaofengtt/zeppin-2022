USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_GET_ManagerList_NextLevel @IN_INPUT_MAN INT --当前操作员
WITH ENCRYPTION
AS
        --1.根据节点主经理来判断当前操作员
        --所辖节点的主客户经理
        SELECT A.MANAGERID,A.MANAGERNAME FROM TCustManagerTree A, TCustManagerTree B
        WHERE (A.LEFT_ID BETWEEN B.LEFT_ID+1 AND B.RIGHT_ID) AND A.MANAGERID <> 0 AND A.MANAGERID IS NOT NULL
            AND (B.MANAGERID = @IN_INPUT_MAN)
        --所辖节点的成员客户经理
        UNION ALL
        SELECT C.MANAGERID,C.MANAGERNAME FROM TCustManagerTree A, TCustManagerTree B, TCustManagerTreeMembers C
        WHERE (A.LEFT_ID BETWEEN B.LEFT_ID+1 AND B.RIGHT_ID) AND A.SERIAL_NO = C.TREE_ID
            AND (B.MANAGERID = @IN_INPUT_MAN)
        --2.根据节点成员经理来判断当前操作员
        --当前操作员所在节点及所辖节点的主客户经理
        UNION ALL
        SELECT D.MANAGERID,D.MANAGERNAME FROM TCustManagerTreeMembers B, TCustManagerTree C, TCustManagerTree D
        WHERE B.TREE_ID = C.SERIAL_NO AND (D.LEFT_ID BETWEEN C.LEFT_ID+1 AND C.RIGHT_ID) AND D.MANAGERID <> 0 AND D.MANAGERID IS NOT NULL
            AND (B.MANAGERID = @IN_INPUT_MAN)
        --当前操作员所在节点及所辖节点的成员客户经理
        UNION ALL
        SELECT A.MANAGERID,A.MANAGERNAME FROM TCustManagerTreeMembers B, TCustManagerTree C, TCustManagerTree D, TCustManagerTreeMembers A
        WHERE B.TREE_ID = C.SERIAL_NO AND (D.LEFT_ID BETWEEN C.LEFT_ID+1 AND C.RIGHT_ID) AND A.TREE_ID = D.SERIAL_NO
            AND (B.MANAGERID = @IN_INPUT_MAN)
--******************************************************************************
-- = Filename: query\SP_GET_ManagerList_SameLevel.sql
--******************************************************************************
PRINT ' ==================== creating Procedure SP_GET_ManagerList_SameLevel =================== '
/*
 Auth   : 董毅光
 creater: 何士龙
 Time   : 20110523
 Purpose: 查询出该客户经理(当前操作员)的所有同级经理，包括所有同一级别的主经理，不包括自身
 UsedBy :
 Output :
exec SP_GET_ManagerList_SameLevel 888
*/
GO
