USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TCustClassDefine @IN_CLASSDEFINE_ID     INTEGER,        --分级定义ID
                                         @IN_CLASSDEFINE_NAME   NVARCHAR(60),   --分级定义名称
                                         @IN_DEFAULT_VALUE      INTEGER,        --该分级的默认值，必须是TCustClassDetail中的一个明细ID值
                                         @IN_SUMMARY            NVARCHAR(200),  --分级定义的描述
                                         @IN_PARENT_ID          INTEGER,        --当前分级定义的父结点
                                         @IN_PARENT_VALUE       INTEGER,        --当前分级定义有父结点时，指示父分级明细值为何值时，当前分级定义有效
                                         @IN_INPUT_MAN          INTEGER,        --操作员
                                         @IN_CD_NO              INT = 1         --1客户评级；2客户分类
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'增加客户分级定义'
    SELECT @SSUMMARY = N'增加客户分级定义'
    SELECT @IBUSI_FLAG = 20205
    SELECT @V_RET_CODE = -20205000

    DECLARE @V_LEVEL_ID INTEGER --层级编号

    IF @IN_PARENT_ID IS NOT NULL AND @IN_PARENT_ID <> 0
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM TCustClassDefine WHERE CLASSDEFINE_ID = @IN_PARENT_ID)
            RETURN @V_RET_CODE - 1 --父结点不存在
        IF @IN_PARENT_VALUE IS NOT NULL AND @IN_PARENT_VALUE <> 0
            IF NOT EXISTS(SELECT 1 FROM TCustClassDetail WHERE CLASSDEFINE_ID = @IN_PARENT_ID AND CLASSDETAIL_ID = @IN_PARENT_VALUE)
                RETURN @V_RET_CODE - 2 --父结点默认值不存在
        SELECT @V_LEVEL_ID = LEVEL_ID + 1 FROM TCustClassDefine WHERE CLASSDEFINE_ID = @IN_PARENT_ID
    END
    ELSE
    BEGIN
        SELECT @V_LEVEL_ID = 1
    END
    IF EXISTS(SELECT * FROM TCustClassDefine WHERE CLASSDEFINE_ID = @IN_CLASSDEFINE_ID)
        RETURN @V_RET_CODE - 3 --定义ID已经存在

    BEGIN TRANSACTION

    INSERT INTO TCustClassDefine(CD_NO,CLASSDEFINE_ID,CLASSDEFINE_NAME,DEFAULT_VALUE,PARENT_ID,PARENT_VALUE,LEVEL_ID,CANMODI,SUMMARY,CANDEL,CANADD)
        VALUES(@IN_CD_NO,@IN_CLASSDEFINE_ID,@IN_CLASSDEFINE_NAME,@IN_DEFAULT_VALUE,@IN_PARENT_ID,@IN_PARENT_VALUE,@V_LEVEL_ID,1,@IN_SUMMARY,1,1)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'增加客户分级定义：ID:'+RTRIM(CONVERT(NVARCHAR(16),@IN_CLASSDEFINE_ID))+N'|名称:'+@IN_CLASSDEFINE_NAME
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
