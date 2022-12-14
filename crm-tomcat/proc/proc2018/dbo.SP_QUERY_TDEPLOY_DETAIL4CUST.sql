USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
/*
2013-08-21 lvcy
查询指定客户的收益分配明细,当不指定时返回空集，而不是所有的客户
SP_QUERY_TDEPLOY_DETAIL4CUST 5198,1222,0,'058-4',888
*/
CREATE PROCEDURE SP_QUERY_TDEPLOY_DETAIL4CUST
                           @IN_CUST_ID INT,                 --客户ID
                           @IN_PRODUCT_ID INT,              --产品ID
                           @IN_SUB_PRODUCT_ID INT ,         --子产品ID
                           @IN_CONTRACT_SUB_BH VARCHAR(80), --合同实际编号
                           @IN_INPUT_MAN INT                --操作员
WITH ENCRYPTION
AS
    DECLARE @V_USER_ID INT,@V_CONTRACT_BH VARCHAR(16)
    SELECT @V_USER_ID = USER_ID FROM TSYSTEMINFO
    --SELECT * FROM INTRUST..TDEPLOY WHERE CUST_ID =5198 and PRODUCT_ID=1222
    --先通过实际合同编号找出内部合同编号
    SELECT @V_CONTRACT_BH=CONTRACT_BH FROM INTRUST..TCONTRACT 
		WHERE CUST_ID =@IN_CUST_ID AND PRODUCT_ID=@IN_PRODUCT_ID 
			 AND CONTRACT_SUB_BH=@IN_CONTRACT_SUB_BH
			 AND (@IN_SUB_PRODUCT_ID=0 OR SUB_PRODUCT_ID=@IN_SUB_PRODUCT_ID)
	SELECT CUST_ID,PRODUCT_ID,CONTRACT_BH,SY_MONEY,SY_DATE,FP_DATE,SY_TYPE_NAME,JK_TYPE_NAME,SY_RATE,SY_AMOUNT FROM INTRUST..TDEPLOY 
		WHERE CUST_ID =@IN_CUST_ID
			AND PRODUCT_ID=@IN_PRODUCT_ID
			AND (@IN_SUB_PRODUCT_ID=0 OR SUB_PRODUCT_ID=@IN_SUB_PRODUCT_ID)
			AND CONTRACT_BH=@V_CONTRACT_BH
			AND CHECK_FLAG=2 --已审核
		ORDER BY SY_DATE
GO
