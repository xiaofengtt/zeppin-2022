﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TCustomers_WEBCALL  @IN_CUST_ID 			INTEGER,                 	--操作员ID													
											@IN_WebCallID        	NVARCHAR(100), 			    --访客ID										
											@IN_INPUT_MAN			INTEGER          			--录入员
WITH ENCRYPTION										
AS
DECLARE @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
SELECT  @IBUSI_FLAG = 10106    SELECT @SBUSI_NAME = N'记录访客ID与Customers的对应关系', @SSUMMARY = N'记录访客ID与Customers的对应关系'	
BEGIN TRANSACTION
	INSERT INTO TCustomers_WEBCALL (CUST_ID,WebCallID,INSERT_TIME)
		VALUES (@IN_CUST_ID,@IN_WebCallID,CONVERT(DATETIME,CONVERT(NVARCHAR(64),GETDATE(),120)))
	IF @@ERROR <> 0
	BEGIN        
		ROLLBACK TRANSACTION        
		RETURN -100    
	END	

	SET @SSUMMARY = N'记录访客ID与Customers的对应关系，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))    
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