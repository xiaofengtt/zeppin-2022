﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_IMPORT_DATA @IN_SERIAL_NO INT = NULL,
                                      @IN_MODULE_ID NVARCHAR(30) = '',  --模块ID
                                      @IN_STATUS    INT = 1,    --1未处理 2已预处理 3已导入 9有异常的数据
                                      @IN_INPUT_MAN INT = NULL
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    IF @IN_SERIAL_NO IS NULL SET @IN_SERIAL_NO = 0
    IF @IN_STATUS IS NULL SET @IN_STATUS = 1
    SELECT * FROM IMPORT_DATA
        WHERE (SERIAL_NO = @IN_SERIAL_NO OR @IN_SERIAL_NO = 0)
            AND((@IN_STATUS IN (1,2,3) AND ABS(STATUS) = @IN_STATUS) OR (@IN_STATUS = 9 AND STATUS IN (-2,9)))
            AND(INSERT_MAN = @IN_INPUT_MAN)
        ORDER BY SERIAL_NO DESC
GO