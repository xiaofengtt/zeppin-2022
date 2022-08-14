USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
-- PROCEDURE SP2_QUERY_TVideoRecording
CREATE PROCEDURE SP2_QUERY_TVideoRecording
					@IN_CONTRACTID        INT,         --
					@IN_CONTRACT_BH       NVARCHAR(40),--合同编号
					@IN_PRODUCT_NAME      NVARCHAR(80),--产品名称
					@IN_CUST_NAME         NVARCHAR(80),--客户名称
					@IN_CheckFlag         INT,         --1未审核2审核通过3审核否决
					@IN_INPUT_MAN         INT          --操作员
					
WITH ENCRYPTION
AS
    SELECT A.VID,A.ProductID,A.SubProductID,A.SaveName,A.OriginName,A.ContractID,A.InputTime,A.InputMan,A.InputManName
			,A.CheckFlag,A.CheckMan,A.CheckManName,A.CheckTime,A.CheckComment
			,B.CONTRACT_SUB_BH,B.RG_MONEY
			,C.CUST_NAME,C.CARD_TYPE,C.CARD_TYPE_NAME,C.CARD_ID
			,D.PRODUCT_NAME
		FROM TVideoRecording A LEFT JOIN INTRUST..TCONTRACT B ON A.ContractID=B.SERIAL_NO AND A.CheckFlag<>4
			LEFT JOIN TCustomers C ON C.CUST_ID=B.CUST_ID
			LEFT JOIN INTRUST..TPRODUCT D ON D.PRODUCT_ID=B.PRODUCT_ID
		WHERE (ISNULL(@IN_CONTRACTID,0)=0 OR A.ContractID=@IN_CONTRACTID)
			AND (ISNULL(@IN_CONTRACT_BH,'')='' OR B.CONTRACT_SUB_BH like '%'+@IN_CONTRACT_BH+'%')
			AND (ISNULL(@IN_PRODUCT_NAME,'')='' OR D.PRODUCT_NAME like '%'+@IN_PRODUCT_NAME+'%')
			AND (ISNULL(@IN_CUST_NAME,'')='' OR C.CUST_NAME like '%'+@IN_CUST_NAME+'%')
			AND (ISNULL(@IN_CheckFlag,0)=0 OR A.CheckFlag=@IN_CheckFlag)
			AND A.CheckFlag<>4
GO
