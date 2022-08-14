USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TCustManagerTreeMembers    @IN_TREE_ID       INT,      --经理级别ID（TCustManagerTree.SERIAL_NO）
                                                    @IN_MANAGERID     INT,      --经理ID
                                                    @IN_MANAGERNAME   NVARCHAR(64),  --经理名称
                                                    @IN_INPUT_MAN     INT =  0  --输入员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -30301000, @IBUSI_FLAG = 30301
    SELECT @SBUSI_NAME = N'添加经理级别成员', @SSUMMARY = N'添加经理级别成员'
    IF EXISTS(SELECT 1 FROM TCustManagerTreeMembers WHERE TREE_ID = @IN_TREE_ID AND MANAGERID = @IN_MANAGERID)
        RETURN @V_RET_CODE - 1 --客户组中该客户已存在，不允许重复添加
    DECLARE @V_RIGHT_ID INT,@V_LEVEL_ID INT
    IF EXISTS (SELECT 1 FROM TCustManagerTree WHERE SERIAL_NO = @IN_TREE_ID)
    BEGIN
        BEGIN TRANSACTION
        INSERT INTO TCustManagerTreeMembers(TREE_ID,MANAGERID,MANAGERNAME)
            VALUES (@IN_TREE_ID,@IN_MANAGERID,@IN_MANAGERNAME)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
        SET @SSUMMARY = N'添加经理级别成员，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
        INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
        COMMIT TRANSACTION
    END
GO
