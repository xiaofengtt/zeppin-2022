﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP2_QUERY_TGAINLEVEL_4PRODUCT
                      @IN_PRODUCT_ID          INT,        --产品ID
                      @IN_INPUT_MAN           INT         --操作员
                      
WITH ENCRYPTION
AS
    SELECT A.PROV_FLAG,B.CONTENT PROV_FLAG_NAME,A.PROV_LEVEL,A.PROV_LEVEL_NAME,A.LOWER_LIMIT,A.UPPER_LIMIT,A.GAIN_RATE 
		FROM INTRUST..TGAINLEVEL A LEFT JOIN INTRUST..TINTEGERPARAM B ON TYPE_ID=3003 AND A.PROV_FLAG=B.TYPE_VALUE
		WHERE PRODUCT_ID=@IN_PRODUCT_ID
GO
