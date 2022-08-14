USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TCustClassDefine @IN_CLASSDEFINE_ID     INTEGER,        --分级定义ID
                                          @IN_CLASSDEFINE_NAME   NVARCHAR(60),   --分级定义名称
                                          @IN_DEFAULT_VALUE      INTEGER,        --该分级的默认值，必须是TCustClassDetail中的一个明细ID值
                                          @IN_PARENT_VALUE       INTEGER,        --当前分级定义有父结点时，指示父分级明细值为何值时，当前分级定义有效
                                          @IN_SUMMARY            NVARCHAR(200),  --分级定义的描述
                                          @IN_INPUT_MAN          INTEGER         --操作员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'修改客户分级定义'
    SELECT @SSUMMARY = N'修改客户分级定义'
    SELECT @IBUSI_FLAG = 20205
    SELECT @V_RET_CODE = -20205000

    DECLARE @V_CANMODI INTEGER, --是否可删除标志
            @V_PARENT_ID INTEGER --父结点编号

    IF NOT EXISTS(SELECT 1 FROM TCustClassDefine WHERE CLASSDEFINE_ID = @IN_CLASSDEFINE_ID)
        RETURN @V_RET_CODE - 4 --记录不存在

    SELECT @V_CANMODI = CANMODI, @V_PARENT_ID = PARENT_ID FROM TCustClassDefine WHERE CLASSDEFINE_ID = @IN_CLASSDEFINE_ID
    IF @V_PARENT_ID IS NOT NULL AND @V_PARENT_ID <> 0
    BEGIN
        IF @IN_PARENT_VALUE IS NOT NULL AND @IN_PARENT_VALUE <> 0
            IF NOT EXISTS(SELECT 1 FROM TCustClassDetail WHERE CLASSDEFINE_ID = @V_PARENT_ID AND CLASSDETAIL_ID = @IN_PARENT_VALUE)
                RETURN @V_RET_CODE - 2 --父结点默认值不存在
    END

    BEGIN TRANSACTION

    IF @V_CANMODI = 1
    BEGIN
        UPDATE TCustClassDefine
            SET CLASSDEFINE_NAME = @IN_CLASSDEFINE_NAME,
                DEFAULT_VALUE    = @IN_DEFAULT_VALUE   ,
                PARENT_VALUE     = @IN_PARENT_VALUE    ,
                SUMMARY          = @IN_SUMMARY
            WHERE CLASSDEFINE_ID = @IN_CLASSDEFINE_ID
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
        UPDATE TCustClassDetail
            SET CLASSDEFINE_NAME = @IN_CLASSDEFINE_NAME
            WHERE CLASSDEFINE_ID = @IN_CLASSDEFINE_ID
    END
    ELSE
        UPDATE TCustClassDefine
            SET SUMMARY          = @IN_SUMMARY
            WHERE CLASSDEFINE_ID = @IN_CLASSDEFINE_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'修改客户分级定义：ID:'+RTRIM(CONVERT(NVARCHAR(16),@IN_CLASSDEFINE_ID))+N'|名称:'+@IN_CLASSDEFINE_NAME
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
