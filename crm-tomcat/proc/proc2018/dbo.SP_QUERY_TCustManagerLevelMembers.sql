USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCustManagerLevelMembers     @IN_TREE_ID       INT,      --经理级别ID（TCustManagerTree.Serial_no）
                               @IN_INPUT_MAN     INT =  0  --输入员
WITH ENCRYPTION
AS
    IF ISNULL(@IN_TREE_ID,0) <> 0
        SELECT *
        FROM TCustManagerTreeMembers
        WHERE TREE_ID=@IN_TREE_ID
    RETURN 100
GO
