USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCustClassDefine @IN_CLASSDEFINE_ID   INTEGER,        --分级定义ID
                                           @IN_CLASSDEFINE_NAME NVARCHAR(60),   --分级定义名称
                                           @IN_LEVEL_ID         INTEGER,        --层次编号
                                           @IN_PARENT_ID        INTEGER,        --父层次编号
                                           @IN_PARENT_VALUE     INTEGER,        --父分级明细值为何值时，分级定义有效
                                           @IN_CANMODI          INTEGER,        --是否可以修改，1是2否
                                           @IN_INPUT_MAN        INTEGER,        --操作员
                                           @IN_CD_NO            INT = 1         --1客户评级；2客户分类
WITH ENCRYPTION
AS
    SELECT * FROM TCustClassDefine
        WHERE (CD_NO = @IN_CD_NO OR @IN_CD_NO IS NULL OR @IN_CD_NO = 0)
           AND(CLASSDEFINE_ID   = @IN_CLASSDEFINE_ID   OR @IN_CLASSDEFINE_ID   IS NULL OR @IN_CLASSDEFINE_ID   = 0 )
           AND(CLASSDEFINE_NAME = @IN_CLASSDEFINE_NAME OR @IN_CLASSDEFINE_NAME IS NULL OR @IN_CLASSDEFINE_NAME = N'')
           AND(LEVEL_ID         = @IN_LEVEL_ID         OR @IN_LEVEL_ID         IS NULL OR @IN_LEVEL_ID         = 0 )
           AND(PARENT_ID        = @IN_PARENT_ID        OR @IN_PARENT_ID        IS NULL OR @IN_PARENT_ID        = 0 )
           AND(PARENT_VALUE     = @IN_PARENT_VALUE     OR @IN_PARENT_VALUE     IS NULL OR @IN_PARENT_VALUE     = 0 )
           AND(CANMODI          = @IN_CANMODI          OR @IN_CANMODI          IS NULL OR @IN_CANMODI          = 0 )
GO
