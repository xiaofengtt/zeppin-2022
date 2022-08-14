USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_CM_Contract    @IN_CONTRACT_BH         NVARCHAR,               --合同编号
                                        	@IN_CUST_NAME           NVARCHAR(60) = N'',   	--客户名称
                                        	@IN_PAYMENT_TYPE        NVARCHAR(10) = N'',  	--付款类别
                                            @IN_CONTRACT_ID         INT = 0,            		--合同ID
                                            @IN_INPUT_MAN           INT =0
                                        	
WITH ENCRYPTION
AS
    IF ISNULL(@IN_CONTRACT_ID,0) <> 0
        BEGIN
            SELECT * FROM CM_TCONTRACT
                WHERE CONTRACT_ID = @IN_CONTRACT_ID
                    AND INPUT_MAN = @IN_INPUT_MAN
        END
    ELSE
        BEGIN
            SELECT A.*,B.CUST_NAME,C.TYPE_CONTENT AS PAYMENT_TYPE_NAME FROM CM_TCONTRACT A LEFT JOIN TCUSTOMERS B ON A.CUST_ID = B.CUST_ID
                                      LEFT JOIN TDICTPARAM C ON A.PAYMENT_TYPE = C.TYPE_VALUE
			WHERE  (A.CONTRACT_BH LIKE '%' + @IN_CONTRACT_BH + '%' OR ISNULL(@IN_CONTRACT_BH,'') = N'')
				AND (A.PAYMENT_TYPE = @IN_PAYMENT_TYPE OR ISNULL(@IN_PAYMENT_TYPE,'') = '' )
				AND (B.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%' OR ISNULL(@IN_CUST_NAME,'') = N'')	
                AND (A.INPUT_MAN = @IN_INPUT_MAN)
        END

GO
