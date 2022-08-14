﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
--DROP PROCEDURE SP2_QUERY_TVideoRecording_CONTRACTID
CREATE PROCEDURE SP2_QUERY_TVideoRecording_CONTRACTID
					@IN_CONTRACTID        INT,        --
					@IN_INPUT_MAN         INT         --操作员
					
WITH ENCRYPTION
AS
    SELECT A.VID,A.ProductID,A.SubProductID,A.SaveName,A.OriginName,A.ContractID,A.InputTime,A.InputMan,A.InputManName
			,A.CheckFlag,A.CheckMan,A.CheckManName,A.CheckTime,A.CheckComment
			,B.CONTRACT_SUB_BH,B.RG_MONEY,B.LINK_MAN
			,C.CUST_NAME,C.CARD_TYPE,C.CARD_TYPE_NAME,C.CARD_ID,C.CUST_NO
			,D.PRODUCT_NAME
			,E.OP_NAME AS SERVICE_MAN_NAME
			,F.OP_NAME AS LINK_MAN_NAME
		FROM TVideoRecording A LEFT JOIN INTRUST..TCONTRACT B ON A.ContractID=B.SERIAL_NO AND A.CheckFlag<>4
			LEFT JOIN TCustomers C ON C.CUST_ID=B.CUST_ID
			LEFT JOIN INTRUST..TPRODUCT D ON D.PRODUCT_ID=B.PRODUCT_ID
			LEFT JOIN TOPERATOR E ON E.OP_CODE=C.SERVICE_MAN
			LEFT JOIN TOPERATOR F ON F.OP_CODE=B.LINK_MAN
		WHERE A.ContractID=ISNULL(@IN_CONTRACTID,0)
			AND A.CheckFlag<>4
GO
