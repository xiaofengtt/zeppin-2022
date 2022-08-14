USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TCUSTMANAGERTREE   @IN_SERIAL_NO       INT,            --操作员序列ID
                                            @IN_MANAGERID       INT,            --子节点操作员ID
                                            @IN_INPUT_MAN       INT = 0,        --输入员
                                            @IN_LEVEL_NO        NVARCHAR(30) = '',  --客户经理级别编号
                                            @IN_LEVEL_NAME      NVARCHAR(100) = ''  --客户经理级别名称
WITH ENCRYPTION
AS
    DECLARE @V_OP_NAME          NVARCHAR(16) --操作员姓名
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -30401000, @IBUSI_FLAG = 30401
    SELECT @SBUSI_NAME = N'修改节点客户经理信息', @SSUMMARY = N'修改节点客户经理信息'

    SELECT @V_OP_NAME = ManagerName FROM TCustManagers WHERE ManagerID = @IN_MANAGERID
    IF @IN_LEVEL_NO <> ''
    BEGIN
        IF EXISTS(SELECT 1 FROM TCustManagerTree WHERE LEVEL_NO = @IN_LEVEL_NO AND SERIAL_NO <> @IN_SERIAL_NO)
            RETURN @V_RET_CODE - 9 --客户经理级别编号已存在
    END

    IF EXISTS (SELECT 1 FROM TCustManagerTree WHERE SERIAL_NO = @IN_SERIAL_NO)
    BEGIN
        BEGIN TRANSACTION
        UPDATE TCustManagerTree
            SET MANAGERID = @IN_MANAGERID,
                MANAGERNAME = @V_OP_NAME,
                LEVEL_NO    = @IN_LEVEL_NO,
                LEVEL_NAME  = @IN_LEVEL_NAME
            WHERE SERIAL_NO = @IN_SERIAL_NO
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END

        SET @SSUMMARY = N'修改节点客户经理信息，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
