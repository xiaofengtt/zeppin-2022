﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TPOSTSEND @IN_INPUT_DATE       INTEGER,	       --邮寄日期
									@IN_PRODUCT_ID       INTEGER,		   --产品ID
									@IN_CONTRACT_SUB_BH  NVARCHAR(200),    --合同编号
									@IN_POST_NO		     NVARCHAR(30),     --邮寄单号
									@IN_POST_CONTENT	 NVARCHAR(30)      --邮寄内容（1成立公告2确认单3管理报告4临时信息披露5终止公告6其他。可能有多个，则用|间隔）
									
WITH ENCRYPTION
AS
    SET NOCOUNT ON

    --DECLARE @V_ERROR NVARCHAR(200), @IBUSI_FLAG INT, @SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    
	SELECT A.*, B.PRODUCT_NAME,D.CUST_NAME FROM TPOSTSEND A LEFT JOIN INTRUST..TPRODUCT B ON A.PRODUCT_ID = B.PRODUCT_ID,

		INTRUST..TCONTRACT C LEFT JOIN INTRUST..TCUSTOMERINFO D ON C.CUST_ID = D.CUST_ID 

		WHERE (A.INPUT_DATE=@IN_INPUT_DATE OR ISNULL(@IN_INPUT_DATE,0)=0)
		AND (A.PRODUCT_ID=@IN_PRODUCT_ID OR ISNULL(@IN_PRODUCT_ID,0)=0)
		AND (A.CONTRACT_SUB_BH LIKE '%'+@IN_CONTRACT_SUB_BH+'%' OR ISNULL(@IN_CONTRACT_SUB_BH,'')='')
		AND (A.POST_NO LIKE '%'+@IN_POST_NO+'%' OR ISNULL(@IN_POST_NO,'')='')
		AND A.PRODUCT_ID = C.PRODUCT_ID
		AND A.CONTRACT_SUB_BH = C.CONTRACT_SUB_BH
	    ORDER BY A.INPUT_DATE DESC	
		
GO
