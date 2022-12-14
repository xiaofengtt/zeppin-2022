USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TBENCHANGES_LOAD @IN_SERIAL_NO INT
WITH ENCRYPTION
AS
    SELECT A.*,D.CONTRACT_SUB_BH,B.CUST_NAME AS FROM_CUST_NAME,B.CUST_NO AS FROM_CUST_NO,C.CUST_NAME AS TO_CUST_NAME,C.CUST_NO AS TO_CUST_NO
    FROM INTRUST..TBENCHANGES A,INTRUST..TCUSTOMERINFO B,INTRUST..TCUSTOMERINFO C,INTRUST..TCONTRACT D
        WHERE A.SERIAL_NO = @IN_SERIAL_NO
            AND A.PRODUCT_ID = D.PRODUCT_ID AND A.CONTRACT_BH = D.CONTRACT_BH -- 合同编号查询  20080104 by wangc
            AND A.FROM_CUST_ID=B.CUST_ID
            AND A.TO_CUST_ID=C.CUST_ID
GO
