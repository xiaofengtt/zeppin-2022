﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TPREPRODUCT_LOAD @IN_PREPRODUCT_ID INT
WITH ENCRYPTION
AS	
	SELECT PREPRODUCT_NAME, PRE_START_DATE, PRE_END_DATE, START_DATE, ISNULL(PRE_FACT_MONEY,0.0) AS PRE_FACT_MONEY
		,(SELECT ISNULL(SUM(PRE_NUM),0) FROM TPRECONTRACT WHERE PREPRODUCT_ID=@IN_PREPRODUCT_ID AND PRE_STATUS<>'111304') AS PRE_FACT_NUM
	  FROM TPREPRODUCT
	  WHERE PREPRODUCT_ID=@IN_PREPRODUCT_ID
GO