USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCUSTLEVELAUTH @IN_OP_CODE            INTEGER,        --操作员
                                         @IN_PRODUCT_ID         INTEGER,        --产品ID
                                         @IN_LEVEL_ID           INTEGER,        --类别编号10认购份额20受益份额
                                         @IN_LEVEL_VALUE_ID     INTEGER,        --当前类别的分类值ID
                                         @IN_LEVEL_VALUE_NAME   NVARCHAR(60),   --当前类别的分类值名称
                                         @IN_INPUT_MAN          INTEGER         --执行操作员
WITH ENCRYPTION
AS
    SELECT * FROM TCUSTLEVELAUTH
        WHERE(OP_CODE            = @IN_OP_CODE          OR @IN_OP_CODE          = 0  OR @IN_OP_CODE          IS NULL)
          AND(PRODUCT_ID         = @IN_PRODUCT_ID       OR @IN_PRODUCT_ID       = 0  OR @IN_PRODUCT_ID       IS NULL)
          AND(LEVEL_VALUE_ID/100 = @IN_LEVEL_ID         OR @IN_LEVEL_ID         = 0  OR @IN_LEVEL_ID         IS NULL)
          AND(LEVEL_VALUE_ID     = @IN_LEVEL_VALUE_ID   OR @IN_LEVEL_VALUE_ID   = 0  OR @IN_LEVEL_VALUE_ID   IS NULL)
          AND(LEVEL_VALUE_NAME   = @IN_LEVEL_VALUE_NAME OR @IN_LEVEL_VALUE_NAME = N'' OR @IN_LEVEL_VALUE_NAME IS NULL)
GO
