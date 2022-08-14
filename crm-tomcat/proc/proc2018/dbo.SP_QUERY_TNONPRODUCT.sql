USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TNONPRODUCT @IN_NONPRODUCT_ID     INT,            --主键
                                      @IN_NONPRODUCT_NAME   NVARCHAR(60),   --非信托产品名称
                                      @IN_INVESTMENT_MANAGER NVARCHAR(60),  --投资管理人
                                      @IN_PARTNER_CUST_NAME NVARCHAR(60),           --合伙人企业
                                      @IN_STATUS            NVARCHAR(10),   --状态，NULL或’’查全部
                                      @IN_CHECK_FLAG        INT,            --审核
                                      @IN_INPUT_MAN         INT             --录入操作员
WITH ENCRYPTION
AS
    IF ISNULL(@IN_NONPRODUCT_ID,0) <> 0
        BEGIN
            SELECT * FROM TNONPRODUCT
            WHERE NONPRODUCT_ID = @IN_NONPRODUCT_ID
        END
    ELSE
        BEGIN
            SELECT * FROM TNONPRODUCT A LEFT JOIN TCUSTOMERS B ON A.PARTNER_CUST_ID = B.CUST_ID
            WHERE  (A.NONPRODUCT_ID = @IN_NONPRODUCT_ID OR ISNULL(@IN_NONPRODUCT_ID,'') = N'')
                AND (A.NONPRODUCT_NAME LIKE '%' + @IN_NONPRODUCT_NAME + '%' OR ISNULL(@IN_NONPRODUCT_NAME,'') = N'')
                AND (A.INVESTMENT_MANAGER LIKE '%' + @IN_INVESTMENT_MANAGER + '%' OR ISNULL(@IN_INVESTMENT_MANAGER,'') = N'')
                AND (B.CUST_NAME LIKE '%' + @IN_PARTNER_CUST_NAME + '%' OR ISNULL(@IN_PARTNER_CUST_NAME,'') = N'')
                AND (A.STATUS LIKE '%' + @IN_STATUS + '%' OR ISNULL(@IN_STATUS,'') = N'')
                AND (A.CHECK_FLAG = @IN_CHECK_FLAG OR  ISNULL(@IN_CHECK_FLAG,'') = N'')
        END

    RETURN 100
GO
