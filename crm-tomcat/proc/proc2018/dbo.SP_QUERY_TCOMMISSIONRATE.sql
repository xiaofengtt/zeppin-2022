﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCOMMISSIONRATE @IN_PRODUCT_ID     INT,    
                                        @IN_SUB_PRODUCT_ID INT,
                                        @IN_INPUT_MAN      INT     --操作员

WITH ENCRYPTION
AS
    SELECT SERIAL_NO,PRODUCT_ID,SUBPRODUCT_ID,PERIOD,PERIOD_UNIT,TRADE_START_MONEY,TRADE_END_MONEY
		,FEE_RATE,FEE_AMOUNT,INPUT_MAN,INPUT_DATE,CHECK_FLAG,CHECK_MAN,CHECK_DATE,SUMMARY
		 FROM TCOMMISSIONRATE 
      WHERE PRODUCT_ID = @IN_PRODUCT_ID AND ISNULL(SUBPRODUCT_ID,0)=ISNULL(@IN_SUB_PRODUCT_ID,0)
      ORDER BY PERIOD*10+PERIOD_UNIT, TRADE_START_MONEY
GO