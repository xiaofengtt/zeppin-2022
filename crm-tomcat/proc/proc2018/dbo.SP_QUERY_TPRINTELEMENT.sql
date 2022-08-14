﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TPRINTELEMENT @IN_ELEMENT_ID      INT,            --记录号
                                        @IN_CATALOG_CODE    NVARCHAR(24),   --分类代码
                                        @IN_ELEMENT_CODE    NVARCHAR(24),   --要素代码
                                        @IN_ELEMENT_NAME    NVARCHAR(60)    --要素名称
WITH ENCRYPTION 
AS
    SELECT B.*,A.CATALOG_NAME
        FROM TPRINTCATALOG A,TPRINTELEMENT B
        WHERE A.CATALOG_CODE = B.CATALOG_CODE AND 
              (B.ELEMENT_ID = @IN_ELEMENT_ID OR ISNULL(@IN_ELEMENT_ID,0) = 0) AND
              (B.CATALOG_CODE = @IN_CATALOG_CODE OR ISNULL(@IN_CATALOG_CODE,'') = '') AND 
              (B.ELEMENT_CODE = @IN_ELEMENT_CODE OR ISNULL(@IN_ELEMENT_CODE,'') = '') AND 
              (B.ELEMENT_NAME LIKE '%' + @IN_ELEMENT_NAME + '%' OR ISNULL(@IN_ELEMENT_NAME,'') = '')
GO
