﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCONTRACT_SERIALNO @IN_SERIAL_NO INT
WITH ENCRYPTION
AS
    SELECT A.*,C.CUST_NO,C.CUST_NAME,C.CARD_ID,ISNULL(D.BEN_AMOUNT,0) AS BEN_AMOUNT
			,ISNULL(E.MIN_BUY_LIMIT,0) MIN_BUY_LIMIT,ISNULL(E.MAX_BUY_LIMIT,0) MAX_BUY_LIMIT,D.BEN_AMOUNT TO_AMOUNT
			,D.MONEY_ORIGIN BEN_MONEY_ORIGIN,D.SUB_MONEY_ORIGIN BEN_SUB_MONEY_ORIGIN
        FROM INTRUST..TCONTRACT A LEFT JOIN INTRUST..TBENIFITOR D ON A.CONTRACT_BH = D.CONTRACT_BH AND A.PRODUCT_ID = D.PRODUCT_ID
			--LEFT JOIN TPRODUCT B ON A.PRODUCT_ID = B.PRODUCT_ID
			LEFT JOIN TCustomers C ON A.CUST_ID = C.CUST_ID
			LEFT JOIN INTRUST..TSUBPRODUCT E ON D.SUB_PRODUCT_ID = E.SUB_PRODUCT_ID
        WHERE A.SERIAL_NO = @IN_SERIAL_NO

GO
