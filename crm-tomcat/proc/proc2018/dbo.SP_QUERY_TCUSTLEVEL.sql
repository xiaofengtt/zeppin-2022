USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCUSTLEVEL @IN_SERIAL_NO      INTEGER,    --序号
                                     @IN_PRODUCT_ID     INTEGER,    --产品ID
                                     @IN_LEVEL_ID       INTEGER,    --类别编号10认购份额20受益份额
                                     @IN_LEVEL_VALUE_ID INTEGER,    --当前类别的分类值ID
                                     @IN_INPUT_MAN      INTEGER    --操作员
WITH ENCRYPTION
AS
    SELECT * FROM TCUSTLEVEL
        WHERE(SERIAL_NO      = @IN_SERIAL_NO      OR @IN_SERIAL_NO      = 0 OR @IN_SERIAL_NO      IS NULL)
          AND(PRODUCT_ID     = @IN_PRODUCT_ID     OR @IN_PRODUCT_ID     = 0 OR @IN_PRODUCT_ID     IS NULL)
          AND(LEVEL_ID       = @IN_LEVEL_ID       OR @IN_LEVEL_ID       = 0 OR @IN_LEVEL_ID       IS NULL)
          AND(LEVEL_VALUE_ID = @IN_LEVEL_VALUE_ID OR @IN_LEVEL_VALUE_ID = 0 OR @IN_LEVEL_VALUE_ID IS NULL)
GO
