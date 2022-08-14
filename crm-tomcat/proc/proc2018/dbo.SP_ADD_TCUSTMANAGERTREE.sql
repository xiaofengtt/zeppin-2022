USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TCUSTMANAGERTREE    @IN_SERIAL_NO_UP    INT,            --操作员ID
                                            @IN_MANAGERID_NEXT  INT,            --子节点操作员ID
                                            @IN_INPUT_MAN       INT =  0,       --输入员
                                            @IN_LEVEL_NO        NVARCHAR(30) = '',  --客户经理级别编号
                                            @IN_LEVEL_NAME      NVARCHAR(100) = ''  --客户经理级别名称
WITH ENCRYPTION
AS
    DECLARE @V_OP_NAME  NVARCHAR(64) ,@V_MANANGERID_UP INT
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -30401000, @IBUSI_FLAG = 30401
    SELECT @SBUSI_NAME = N'添加下级子节点，，下级客户经理', @SSUMMARY = N'添加下级子节点，，下级客户经理'
    --IF NOT EXISTS(SELECT 1 FROM  TCustManagers  WHERE ManagerID = @IN_MANAGERID_NEXT)
    --    RETURN -30401001    --对应经理信息不存在
    IF @IN_LEVEL_NO <> ''
    BEGIN
        IF EXISTS(SELECT 1 FROM TCustManagerTree WHERE LEVEL_NO = @IN_LEVEL_NO)
            RETURN @V_RET_CODE - 9 --客户经理级别编号已存在
    END

    DECLARE @V_RIGHT_ID INT,@V_LEVEL_ID INT ,@V_LEFT_ID INT
    SELECT @V_OP_NAME = ManagerName FROM  TCustManagers   WHERE ManagerID = @IN_MANAGERID_NEXT
    IF EXISTS (SELECT 1 FROM TCustManagerTree WHERE SERIAL_NO = @IN_SERIAL_NO_UP)
    BEGIN
        SELECT @V_MANANGERID_UP = MANAGERID,@V_LEFT_ID = LEFT_ID,@V_RIGHT_ID=RIGHT_ID,@V_LEVEL_ID = LEVEL_ID
        FROM TCustManagerTree WHERE SERIAL_NO = @IN_SERIAL_NO_UP
        IF EXISTS(SELECT 1 FROM  TCustManagers  WHERE ManagerID = @IN_MANAGERID_NEXT)
        BEGIN
            IF EXISTS(SELECT 1 FROM TCustManagerTree WHERE MANAGERID = @IN_MANAGERID_NEXT AND LEFT_ID >= @V_LEFT_ID AND RIGHT_ID <= @V_RIGHT_ID)
            BEGIN
                UPDATE TERRORINFO SET MSG1 = @V_OP_NAME WHERE ERROR_ID = @V_RET_CODE - 7
                RETURN @V_RET_CODE -  7 --客户经理级别不能重复设置，@V_OP_NAME已是其它节点经理
            END
            IF ISNULL(@V_MANANGERID_UP,0) <> 0 AND @V_MANANGERID_UP IN (SELECT OP_CODE FROM dbo.F_COUNTLAYER(@IN_MANAGERID_NEXT))
                RETURN @V_RET_CODE -  8 --客户经理级别发生嵌套设置，请查看原因！
            IF EXISTS(SELECT 1 FROM TSYSCONTROL WHERE FLAG_TYPE = 'MANAGER001' AND VALUE = 1)
            BEGIN
                IF EXISTS(SELECT 1 FROM TCustManagerTreeMembers B WHERE B.MANAGERID = @IN_MANAGERID_NEXT)
                BEGIN
                    UPDATE TERRORINFO SET MSG1 = @V_OP_NAME WHERE ERROR_ID = @V_RET_CODE - 10
                    RETURN @V_RET_CODE - 10 --客户经理级别不能重复设置，@V_OP_NAME已是其它节点的成员
                END
            END
        END
        BEGIN TRANSACTION
        UPDATE TCustManagerTree SET RIGHT_ID=RIGHT_ID+2 WHERE RIGHT_ID>=@V_RIGHT_ID
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
        UPDATE TCustManagerTree SET LEFT_ID=LEFT_ID+2 WHERE LEFT_ID>=@V_RIGHT_ID
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END

        INSERT INTO TCustManagerTree (MANAGERID,MANAGERNAME,LEFT_ID,RIGHT_ID,LEVEL_ID,LEVEL_NO,LEVEL_NAME)
            VALUES (@IN_MANAGERID_NEXT,@V_OP_NAME,@V_RIGHT_ID,@V_RIGHT_ID+1,@V_LEVEL_ID + 1,@IN_LEVEL_NO,@IN_LEVEL_NAME)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
        SET @SSUMMARY = N'添加下级子节点，，下级客户经理，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
        INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
        COMMIT TRANSACTION
        RETURN 100
    END
GO
