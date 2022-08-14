USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_HCUSTZJBD @IN_SERIAL_NO        INT,                --序号
                                    @IN_PRODUCT_ID       INT,                --产品ID
                                    @IN_CONTRACT_SUB_BH  NVARCHAR(16),       --合同编号
                                    @IN_CUST_NAME        NVARCHAR(100),      --客户名称
                                    @IN_CHG_TYPE         INT,                --份额变动业务类别1认购2申购3赎回
                                    @IN_START_DATE       INT,                --起始日期
                                    @IN_END_DATE         INT,                --终止日期
                                    @IN_INPUT_MAN        INT,                --操作员
                                    @IN_PRINT_FLAG       INT = 0,            --打印标志 1已打印 2未打印 0全部
                                    @IN_PRODUCT_NAME     NVARCHAR(100) = ''  --产品名称
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    SET @IN_START_DATE = ISNULL(@IN_START_DATE,0)
    IF ISNULL(@IN_END_DATE,0) = 0 SET @IN_END_DATE = 20991231

    SELECT A.*, C.CUST_NAME, D.PRODUCT_NAME, E.CONTRACT_SUB_BH
    FROM INTRUSTHistory..HCUSTZJBD A, INTRUST..TCUSTOMERINFO C, INTRUST..TPRODUCT D, INTRUST..TCONTRACT E
    WHERE A.CUST_ID = C.CUST_ID AND A.PRODUCT_ID = D.PRODUCT_ID AND A.PRODUCT_ID = E.PRODUCT_ID AND A.CONTRACT_BH = E.CONTRACT_BH
        AND A.BD_BUSI_ID IN (1,2,3)
        AND(A.PRODUCT_ID = @IN_PRODUCT_ID OR ISNULL(@IN_PRODUCT_ID,0) = 0)
        AND(E.CONTRACT_SUB_BH = @IN_CONTRACT_SUB_BH OR ISNULL(@IN_CONTRACT_SUB_BH,'') = '')
        AND(C.CUST_NAME LIKE '%'+ISNULL(@IN_CUST_NAME,'')+'%')
        AND(A.BD_BUSI_ID = @IN_CHG_TYPE OR ISNULL(@IN_CHG_TYPE,0) = 0)
        AND(A.TRADE_DATE BETWEEN @IN_START_DATE AND @IN_END_DATE)
        AND(D.PRODUCT_NAME LIKE '%'+ISNULL(@IN_PRODUCT_NAME,'')+'%')
        AND(A.PRINT_FLAG = @IN_PRINT_FLAG OR ISNULL(@IN_PRINT_FLAG,0) = 0)
GO
