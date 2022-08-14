﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_OLD_BENIFITOR @IN_PRODUCT_NAME NVARCHAR(100)=NULL,
                              @IN_CONTRACT_BH NVARCHAR(100)=NULL,
                              @IN_CUST_NAME NVARCHAR(100)=NULL
WITH ENCRYPTION
AS

   SELECT * 
     FROM OLD_BENIFITOR
     WHERE ([产品名称] LIKE '%'+@IN_PRODUCT_NAME+'%' OR ISNULL(@IN_PRODUCT_NAME,'')='')
         AND ([合同编号] LIKE '%'+@IN_CONTRACT_BH+'%' OR ISNULL(@IN_CONTRACT_BH,'')='')
         AND ([受益人] LIKE '%'+@IN_CUST_NAME+'%' OR ISNULL(@IN_CUST_NAME,'')='')
         AND [产品名称] IS NOT NULL
     ORDER BY [合同编号] ASC
GO