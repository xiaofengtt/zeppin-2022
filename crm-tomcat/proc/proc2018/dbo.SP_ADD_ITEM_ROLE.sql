﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_ITEM_ROLE @IN_ITEM_ID    INT,
                                  @IN_OP_CODE    INT,
                                  @IN_FLAG       INT,   --  标志：1 增加、2 删除
                                  @IN_INPUT_MAN  INT
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'增加部门级项目权限'
    SELECT @SSUMMARY = N'增加部门级项目权限'
	SELECT @IBUSI_FLAG = 10702
    BEGIN TRANSACTION

    IF @IN_FLAG = 2
    BEGIN
        DELETE FROM DEPTITEM_ROLE WHERE ITEM_ID = @IN_ITEM_ID
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END
    ELSE
    BEGIN
        IF NOT EXISTS(SELECT * FROM DEPTITEM_ROLE WHERE ITEM_ID = @IN_ITEM_ID AND OP_CODE = @IN_OP_CODE)
        BEGIN
            INSERT INTO DEPTITEM_ROLE(ITEM_ID,OP_CODE) VALUES(@IN_ITEM_ID,@IN_OP_CODE)
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END
        END
    END

    SELECT @SSUMMARY = N'增加部门级项目权限，角色编号：'+RTRIM(CONVERT(NVARCHAR(16),@IN_ITEM_ID))+N',操作员编号：'+RTRIM(CONVERT(CHAR(16),@IN_OP_CODE))
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    COMMIT TRANSACTION
    RETURN 100
GO