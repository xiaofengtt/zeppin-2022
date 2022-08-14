﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_DUE_PRODUCT 
                                @IN_SERVICE_MAN INT,             --客户经理ID
                                @IN_DAYS_BEFORE_DUE INT = 30,    --到期前通知天数  
                                @IN_INPUT_MAN     INT            --操作员
                                  
WITH ENCRYPTION
AS       
    SELECT @IN_DAYS_BEFORE_DUE=30 WHERE ISNULL(@IN_DAYS_BEFORE_DUE,-1)<0
    
    SELECT 1 AS CONTRACT_TYPE, CUS.CUST_ID, CUS.CUST_NAME, P.PRODUCT_ID, P.PRE_CODE, P.PRODUCT_NAME, 
        CON.END_DATE, CON.START_DATE, CON.RG_MONEY AS MONEY, CON.CONTRACT_BH, CON.CONTRACT_SUB_BH
      FROM INTRUST..TPRODUCT P 
        JOIN INTRUST..TCONTRACT CON ON CON.PRODUCT_ID=P.PRODUCT_ID
        LEFT JOIN TCustomers CUS ON CUS.CUST_ID=CON.CUST_ID
      WHERE CUS.SERVICE_MAN=@IN_SERVICE_MAN 
        AND CON.END_DATE>=dbo.GETDATEINT(GETDATE()) AND CON.END_DATE-@IN_DAYS_BEFORE_DUE<=dbo.GETDATEINT(GETDATE())
    UNION 
    SELECT 2 AS CONTRACT_TYPE, CUS.CUST_ID, CUS.CUST_NAME, P.PRODUCT_ID, P.PRE_CODE, P.PRODUCT_NAME, 
        CON.END_DATE, CON.START_DATE, CON.SG_MONEY AS MONEY, CON.CONTRACT_BH, CON.CONTRACT_SUB_BH
      FROM INTRUST..TPRODUCT P 
        JOIN INTRUST..TCONTRACTSG CON ON CON.PRODUCT_ID=P.PRODUCT_ID
        LEFT JOIN TCustomers CUS ON CUS.CUST_ID=CON.CUST_ID
      WHERE CUS.SERVICE_MAN=@IN_SERVICE_MAN 
        AND CON.END_DATE>=dbo.GETDATEINT(GETDATE()) AND CON.END_DATE-@IN_DAYS_BEFORE_DUE<=dbo.GETDATEINT(GETDATE())
    ORDER BY CUST_ID, PRODUCT_ID
GO
