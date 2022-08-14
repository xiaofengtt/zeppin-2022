USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TManagerCOMMISSION
                                     @IN_PRODUCT_ID        INT,        --产品ID
                                     @IN_SUBPRODUCT_ID     INT,        --子产品ID
                                     @IN_OP_CODE           INT ,       --销售人员(TOPERATOR.OP_CODE)
                                     @IN_CUST_ID           INT ,       --客户ID
                                     @IN_CUST_NAME         VARCHAR(100), 
                                     @IN_INPUT_MAN         INT         --操作员
                                     
WITH ENCRYPTION
AS
    --SET NOCOUNT ON
    DECLARE @V_TEAM_ID INT, @V_USER_ID INT
    
    SET @IN_PRODUCT_ID = ISNULL(@IN_PRODUCT_ID,0)
    SET @IN_SUBPRODUCT_ID = ISNULL(@IN_SUBPRODUCT_ID,0)
    SET @IN_OP_CODE = ISNULL(@IN_OP_CODE,0)
    SET @IN_CUST_ID = ISNULL(@IN_CUST_ID,0)
    
	SELECT A.*,B.PRODUCT_NAME,C.OP_NAME,D.CUST_NAME
		FROM TManagerCOMMISSION A LEFT JOIN TPRODUCT B ON B.PRODUCT_ID = A.PRODUCT_ID
			LEFT JOIN TOPERATOR C ON C.OP_CODE=A.OP_CODE
			LEFT JOIN TCustomers D ON D.CUST_ID=A.CUST_ID
		WHERE (A.PRODUCT_ID=@IN_PRODUCT_ID OR @IN_PRODUCT_ID=0)
			AND (A.SUBPRODUCT_ID=@IN_SUBPRODUCT_ID OR @IN_SUBPRODUCT_ID=0)
			AND (A.OP_CODE=@IN_OP_CODE OR @IN_OP_CODE=0)
			AND (A.CUST_ID=@IN_CUST_ID OR @IN_CUST_ID=0)
            AND (ISNULL(@IN_CUST_NAME,'')='' OR D.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%')
GO
