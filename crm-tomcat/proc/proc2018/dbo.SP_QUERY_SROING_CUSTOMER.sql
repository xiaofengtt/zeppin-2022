﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_SROING_CUSTOMER 	@IN_OPERAND_ID  	INT,  --TSystemValue表ID
											@IN_CUST_ID			VARCHAR(30)
											--@IN_SCORING_VALUE	DEC(16,3)
WITH ENCRYPTION
AS

	DECLARE @V_TOP_FLAG INT
	DECLARE @V_TOP_VALUE NVARCHAR(16),@V_SQL NVARCHAR(600),@V_SOURCE_FLAG DEC(16,3)
	DECLARE @V_INCLUDE_TOP INT
	DECLARE @V_INCLUDE_END INT
	DECLARE @V_TABLE_NAME NVARCHAR(60),@V_FEITER_NAME NVARCHAR(60)
	DECLARE @ParmDefinition nvarchar(500);	
	DECLARE @V_TRUE_VALUE INT,@V_CUST_TYPE INT
	
	-------------
	SELECT @V_TOP_FLAG   	= INCLUDE_TOP,
		   @V_TABLE_NAME 	= SOURCE_TABLE,
		   @V_FEITER_NAME	= SOURCE_FIELD	FROM TSYSTEMVALUE WHERE OPERAND_ID = @IN_OPERAND_ID 		   
	
	--获得客户类别 机构还是个人
	SELECT @V_CUST_TYPE = CUST_TYPE FROM TCUSTOMERS WHERE CUST_ID = @IN_CUST_ID
	
	IF @V_TABLE_NAME = 'TCUSTOMERS'
	BEGIN
		IF @V_FEITER_NAME = 'TOTAL_MONEY'
			SET @V_SQL = 'SELECT @FEITER_NAME=(ISNULL(SUM(ADD_MONEY),0)-ISNULL(SUM(DESC_MONEY),0)) FROM INTRUSTHistory..HCUSTZJBD WHERE BD_BUSI_ID IN(1,2,11) AND CUST_ID = '+ @IN_CUST_ID+' GROUP BY CUST_ID'			
		ELSE
		BEGIN
			SET @V_SQL = 'SELECT  @FEITER_NAME='+@V_FEITER_NAME+' FROM '+@V_TABLE_NAME +' WHERE CUST_ID ='+ @IN_CUST_ID+''
		END
	END	
	ELSE
	BEGIN
		SET @V_SQL = 'SELECT @FEITER_NAME = COUNT(A.'+@V_FEITER_NAME+')'+ 
		' FROM (SELECT CUST_ID,'+@V_FEITER_NAME+' FROM '+@V_TABLE_NAME+
		' WHERE CUST_ID = '+@IN_CUST_ID+'GROUP BY CUST_ID,'+@V_FEITER_NAME+') A'+
		' GROUP BY A.CUST_ID'
		
	END

	
	SET @ParmDefinition = N'@FEITER_NAME INT OUTPUT';	

	EXECUTE sp_executesql @V_SQL,@ParmDefinition,@FEITER_NAME=@V_SOURCE_FLAG OUTPUT
	 

	--查询出客户的分值	
	DECLARE C1_T1 CURSOR FOR
		SELECT INCLUDE_TOP,INCLUDE_END FROM TSYSTEMVALUE
			WHERE OPERAND_ID = @IN_OPERAND_ID
			AND (CUST_TYPE = @V_CUST_TYPE)
			AND (@V_SOURCE_FLAG BETWEEN TOP_THRESHOLD AND END_THRESHOLD)
	OPEN C1_T1
	FETCH NEXT FROM C1_T1 INTO @V_INCLUDE_TOP,@V_INCLUDE_END
	WHILE @@FETCH_STATUS = 0
	BEGIN

		IF @V_INCLUDE_TOP = 1 AND @V_INCLUDE_END = 1 --算头算尾
		BEGIN
			 SELECT @V_TRUE_VALUE=TRUE_VALUE FROM TSYSTEMVALUE
				WHERE OPERAND_ID = @IN_OPERAND_ID
				AND (CUST_TYPE = @V_CUST_TYPE)
				AND (TOP_THRESHOLD <= @V_SOURCE_FLAG)
				AND (@V_SOURCE_FLAG <= END_THRESHOLD)  
		END
		ELSE IF @V_INCLUDE_TOP = 1 AND @V_INCLUDE_END = 2 --算头不算尾
		BEGIN
			 SELECT @V_TRUE_VALUE=TRUE_VALUE FROM TSYSTEMVALUE
				WHERE OPERAND_ID = @IN_OPERAND_ID
				AND (CUST_TYPE = @V_CUST_TYPE)
				AND (TOP_THRESHOLD <= @V_SOURCE_FLAG)
				AND (@V_SOURCE_FLAG < END_THRESHOLD) 
		END	
		ELSE IF @V_INCLUDE_TOP = 2 AND @V_INCLUDE_END = 1 --不算头算尾
		BEGIN
			  SELECT @V_TRUE_VALUE=TRUE_VALUE FROM TSYSTEMVALUE
				WHERE OPERAND_ID = @IN_OPERAND_ID
				AND (CUST_TYPE = @V_CUST_TYPE)
				AND (TOP_THRESHOLD < @V_SOURCE_FLAG)
				AND (@V_SOURCE_FLAG <= END_THRESHOLD) 
		END
		ELSE IF @V_INCLUDE_TOP = 2 AND @V_INCLUDE_END = 2 --不算头不算尾
		BEGIN
			  SELECT @V_TRUE_VALUE=TRUE_VALUE FROM TSYSTEMVALUE
				WHERE OPERAND_ID = @IN_OPERAND_ID
				AND (CUST_TYPE = @V_CUST_TYPE)
				AND (TOP_THRESHOLD < @V_SOURCE_FLAG)
				AND (@V_SOURCE_FLAG < END_THRESHOLD) 
		END
		ELSE
		BEGIN
			SELECT 0 AS TRUE_VALUE
		END	
		FETCH NEXT FROM C1_T1 INTO @V_INCLUDE_TOP,@V_INCLUDE_END
	END
	CLOSE C1_T1
	DEALLOCATE C1_T1
	
	SELECT ISNULL(@V_TRUE_VALUE,0) AS TRUE_VALUE,@V_SOURCE_FLAG AS SOURCE_NUM
GO