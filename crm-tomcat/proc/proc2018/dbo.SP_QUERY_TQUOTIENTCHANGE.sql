USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TQUOTIENTCHANGE @IN_SERIAL_NO        INT,            --主键序号
                                          @IN_NONPRODUCT_ID    INT,            --非信托产品ID
                                          @IN_NONPRODUCT_NAME  NVARCHAR(60),   --非信托产品名称
                                          @IN_CUST_NAME        NVARCHAR(100),  --客户名称
                                          @IN_SUBSCRIBE_BH     NVARCHAR(60),   --购买合同编号
                                          @IN_CHECK_FLAG       INT,            --1未确认2已确认0或null查全部
                                          @IN_INPUT_MAN        INT             --操作员
WITH ENCRYPTION
AS
    IF ISNULL(@IN_SERIAL_NO,0) <> 0
        BEGIN
            SELECT * FROM TQUOTIENTCHANGE A, TSUBSCRIBE B
                WHERE (A.SERIAL_NO = @IN_SERIAL_NO) AND (A.SUBSCRIBE_ID = B.SUBSCRIBE_ID)
        END
    ELSE
        BEGIN
            SELECT A.SERIAL_NO,B.SUBSCRIBE_ID,B.NONPRODUCT_ID,B.NONPRODUCT_NAME,B.CUST_ID,B.SUBSCRIBE_BH,A.CHECK_FLAG,
                    A.CHANGE_TYPE, A.CHANGE_AMOUNT, A.CHANGE_MONEY,A.CHANGE_DATE,B.BANK_ID,B.BANK_NAME,B.BANK_SUB_NAME,
                    B.BANK_ACCT,B.ACCT_NAME,B.SUBSCRIBE_MONEY,B.SIGN_DATE,B.PAY_DATE, C.CUST_NAME
               FROM TQUOTIENTCHANGE A, TSUBSCRIBE B, TCUSTOMERS C
                   WHERE (A.SERIAL_NO = @IN_SERIAL_NO OR ISNULL(@IN_SERIAL_NO,'') = N'')
                    AND (A.SUBSCRIBE_ID = B.SUBSCRIBE_ID)
                    AND (B.NONPRODUCT_ID = @IN_NONPRODUCT_ID OR ISNULL(@IN_NONPRODUCT_ID,'') = N'')
                    AND (B.NONPRODUCT_NAME LIKE '%' + @IN_NONPRODUCT_NAME + '%' OR ISNULL(@IN_NONPRODUCT_NAME,'') = N'')
                    AND (B.CUST_ID = C.CUST_ID)
                    AND (B.SUBSCRIBE_BH LIKE '%' + @IN_SUBSCRIBE_BH + '%' OR ISNULL(@IN_SUBSCRIBE_BH,'') = N'')
                    AND (A.CHECK_FLAG = @IN_CHECK_FLAG OR ISNULL(@IN_CHECK_FLAG,'') = N'' AND B.CHECK_FLAG = 2)
                    AND (C.CUST_NAME LIKE '%' + @IN_CUST_NAME + '%' OR ISNULL(@IN_CUST_NAME,'') = N'')
                    AND (A.CHANGE_TYPE = 1)
        END

    RETURN 100
GO
