USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCustomers_WEBCALL  @IN_CUST_ID 		 INTEGER,                 	--客户ID										
											  @IN_WebCallID      NVARCHAR(100) 			    --访客ID										
WITH ENCRYPTION										
AS	
	SELECT * FROM TCustomers_WEBCALL 
			WHERE (CUST_ID = @IN_CUST_ID OR ISNULL(@IN_CUST_ID,'') = N'')
			 AND (WebCallID = @IN_WebCallID OR ISNULL(@IN_WebCallID,'') = N'')
GO
