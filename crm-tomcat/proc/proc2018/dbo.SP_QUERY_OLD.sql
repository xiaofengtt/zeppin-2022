USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_OLD @IN_PRODUCT_NAME NVARCHAR(100)=NULL,
                              @IN_CONTRACT_BH NVARCHAR(100)=NULL,
                              @IN_CUST_NAME NVARCHAR(100)=NULL
WITH ENCRYPTION
AS

   SELECT * FROM INTRUST.dbo.OLD WHERE ([项目名称] = @IN_PRODUCT_NAME OR @IN_PRODUCT_NAME IS NULL OR @IN_PRODUCT_NAME='')
                            AND ([合同编号] LIKE +'%'+ @IN_CONTRACT_BH+'%' OR @IN_CONTRACT_BH IS NULL OR @IN_CONTRACT_BH='')
                            AND ([委托人] LIKE +'%'+@IN_CUST_NAME+'%' OR @IN_CUST_NAME IS NULL OR @IN_CUST_NAME='')
                            AND [项目名称] IS NOT NULL
                            ORDER BY [合同编号] ASC
GO
