USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE dbo.SP_QUERY_CustomerClass @IN_CUST_ID INT,                        --客户ID
                                        @IN_CUST_NO NVARCHAR(8),                --客户编号
                                        @IN_CUST_NAME   NVARCHAR(100),          --客户名称
                                        @IN_CLASSDEFINE_NAME NVARCHAR(60),      --评级定义名称
                                        @IN_CLASSDETAIL_NAME NVARCHAR(60),      --评级明细名称
                                        @IN_INPUT_MAN  INT                      --操作员
WITH ENCRYPTION
AS
    SELECT A.*, B.CUST_NAME, B.CUST_TYPE_NAME, B.CUST_NO
    FROM TCustomerClass A, TCustomers B
    WHERE A.CUST_ID = B.CUST_ID
        AND(A.CUST_ID = @IN_CUST_ID OR @IN_CUST_ID IS NULL OR @IN_CUST_ID = 0)
        AND(B.CUST_NO = @IN_CUST_NO OR @IN_CUST_NO = N'' OR @IN_CUST_NO IS NULL)
        AND(B.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%' OR @IN_CUST_NAME = N'' OR @IN_CUST_NAME IS NULL)
        AND(A.CLASSDEFINE_NAME LIKE '%'+@IN_CLASSDEFINE_NAME+'%' OR @IN_CLASSDEFINE_NAME = N'' OR @IN_CLASSDEFINE_NAME IS NULL)
        AND(A.CLASSDETAIL_NAME LIKE '%'+@IN_CLASSDETAIL_NAME+'%' OR @IN_CLASSDETAIL_NAME = N'' OR @IN_CLASSDETAIL_NAME IS NULL)
     -- AND(A.INSERTMAN = @IN_INPUT_MAN OR @IN_INPUT_MAN IS NULL OR @IN_INPUT_MAN = 0)
        AND A.CHECK_FLAG = 1  --未审核
GO
