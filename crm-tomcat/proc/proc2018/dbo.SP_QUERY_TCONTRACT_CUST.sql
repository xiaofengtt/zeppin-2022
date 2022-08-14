﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCONTRACT_CUST 
                                  @IN_CUST_ID   INT,       --客户ID
                                  @IN_FLAG      INT = 1,   --1正常合同 2全部合同 3已结束合同
                                  @IN_PRODUCT_ID INT = 0,
                                  @IN_SERVICE_MAN INT = 0
WITH ENCRYPTION
AS
    
    SELECT A.PRODUCT_ID,A.PRODUCT_NAME,A.CUST_ID,B.CUST_NAME,A.QS_DATE,A.START_DATE,A.END_DATE,A.RG_MONEY AS MONEY,
           A.HT_STATUS,A.HT_STATUS_NAME,A.SUB_PRODUCT_ID,A.CONTRACT_SUB_BH AS CONTRACT_BH,
           A.HT_CUST_NAME,A.HT_CUST_ADDRESS,A.HT_CUST_TEL,
           B.SERVICE_MAN,C.OP_NAME
        FROM INTRUST..TCONTRACT A 
            LEFT JOIN TCustomers B ON A.CUST_ID = B.CUST_ID
            LEFT JOIN INTRUST..TOPERATOR C ON C.OP_CODE=
                CASE WHEN (SELECT USER_ID FROM TSYSTEMINFO)=25/*25重庆信托*/ AND ISNULL(A.SERVICE_MAN,0)<>0 THEN A.SERVICE_MAN
                  ELSE B.SERVICE_MAN 
                END
        WHERE (ISNULL(@IN_CUST_ID,0)=0 OR A.CUST_ID = @IN_CUST_ID)
            AND ((@IN_FLAG = 1 AND A.HT_STATUS IN ('120101','120106')) OR
                (@IN_FLAG = 2) OR (@IN_FLAG = 3 AND A.HT_STATUS IN ('120104','120105')))
            AND (ISNULL(@IN_PRODUCT_ID,0)=0 OR A.PRODUCT_ID=@IN_PRODUCT_ID)
            AND (ISNULL(@IN_SERVICE_MAN,0)=0 
                    OR CASE WHEN (SELECT USER_ID FROM TSYSTEMINFO)=25/*25重庆信托*/ AND ISNULL(A.SERVICE_MAN,0)<>0 THEN A.SERVICE_MAN
                        ELSE B.SERVICE_MAN END=@IN_SERVICE_MAN)
    UNION --开方式合同部分
    SELECT A.PRODUCT_ID,A.PRODUCT_NAME,A.CUST_ID,B.CUST_NAME,A.QS_DATE,A.START_DATE,A.END_DATE,A.SG_MONEY AS MONEY,
           A.HT_STATUS,A.HT_STATUS_NAME,A.SUB_PRODUCT_ID,A.CONTRACT_SUB_BH AS CONTRACT_BH,
           A.HT_CUST_NAME,A.HT_CUST_ADDRESS,A.HT_CUST_TEL,
           B.SERVICE_MAN,C.OP_NAME
        FROM INTRUST..TCONTRACTSG A 
            LEFT JOIN TCustomers B ON A.CUST_ID = B.CUST_ID
            LEFT JOIN INTRUST..TOPERATOR C ON C.OP_CODE=
                CASE WHEN (SELECT USER_ID FROM TSYSTEMINFO)=25/*25重庆信托*/ AND ISNULL(A.SERVICE_MAN,0)<>0 THEN A.SERVICE_MAN
                  ELSE B.SERVICE_MAN 
                END
        WHERE (ISNULL(@IN_CUST_ID,0)=0 OR A.CUST_ID = @IN_CUST_ID)
            AND((@IN_FLAG = 1 AND A.HT_STATUS IN ('120101','120106')) OR
                (@IN_FLAG = 2) OR (@IN_FLAG = 3 AND A.HT_STATUS IN ('120104','120105')))
            AND (ISNULL(@IN_PRODUCT_ID,0)=0 OR A.PRODUCT_ID=@IN_PRODUCT_ID)
            AND (ISNULL(@IN_SERVICE_MAN,0)=0 
                    OR CASE WHEN (SELECT USER_ID FROM TSYSTEMINFO)=25/*25重庆信托*/ AND ISNULL(A.SERVICE_MAN,0)<>0 THEN A.SERVICE_MAN
                        ELSE B.SERVICE_MAN END=@IN_SERVICE_MAN)
GO