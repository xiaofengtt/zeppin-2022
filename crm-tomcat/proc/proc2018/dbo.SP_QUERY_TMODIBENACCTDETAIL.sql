USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TMODIBENACCTDETAIL @IN_BOOK_CODE INT,
                                             @IN_PRODUCT_ID INT = NULL,
                                             @IN_CONTRACT_BH VARCHAR(16) = NULL,
                                             @IN_CUST_NAME VARCHAR(60) = NULL,
                                             @IN_OLD_BANK_ID VARCHAR(10) = NULL,
                                             @IN_OLD_BANK_ACCT VARCHAR(30) = NULL,
                                             @IN_NEW_BANK_ID VARCHAR(10) = NULL,
                                             @IN_NEW_BANK_ACCT VARCHAR(30) = NULL,
                                             @IN_INPUT_MAN INT = NULL,
                                             @IN_CUST_NO VARCHAR(8) = '',
                                             @IN_CONTRACT_SUB_BH VARCHAR(50) = NULL,   --查询条件修改20080107 by wangc
                                             @IN_SUB_PRODUCT_ID        INT   =0    --子产品ID   20111112  LUOHH
WITH ENCRYPTION
AS
--20409: The Products can be accessed. START
    DECLARE @V_SW20409 INT
    SELECT @V_SW20409 = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = 'SW20409'
    DECLARE @V_PRODUCTS TABLE(PRODUCT_ID INT)
    IF @V_SW20409 = 1
        INSERT INTO @V_PRODUCTS
            SELECT PRODUCT_ID FROM INTRUST..TPRODUCT WHERE BOOK_CODE = @IN_BOOK_CODE
                AND(INTRUST_FLAG1 IN ( SELECT FUNC_ID-2040900 FROM TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID > 2040900 AND FUNC_ID <= 2040902 AND OP_CODE = @IN_INPUT_MAN )
                    OR DEPART_ID IN (SELECT B.DEPART_ID FROM TOPRIGHT A,TOPERATOR B WHERE A.OP_CODE = B.OP_CODE AND A.MENU_ID = '20409' AND A.FUNC_ID = 2040903 AND A.OP_CODE = @IN_INPUT_MAN)
                    OR PRODUCT_ID IN ( SELECT FUNC_ID FROM TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID < 2040900 AND OP_CODE = @IN_INPUT_MAN)
                    OR ADMIN_MANAGER = @IN_INPUT_MAN OR INPUT_MAN = @IN_INPUT_MAN 
                    OR SUB_MAN = @IN_INPUT_MAN OR SUB_MAN2 = @IN_INPUT_MAN
                    OR CASH_MAN = @IN_INPUT_MAN OR CASH_MAN2 = @IN_INPUT_MAN
					OR ADMIN_MANAGER IN(SELECT OP_CODE FROM INTRUST..TOPCUSTRIGHT WHERE ENABLE_OP_CODE = @IN_INPUT_MAN) OR ISNULL(@IN_INPUT_MAN,0)=0 )
--20409: The Products can be accessed. END
    SELECT A.*, B.PRODUCT_ID, B.CONTRACT_BH,E.CONTRACT_SUB_BH, B.CUST_ID ,B.BEN_AMOUNT , C.CUST_NO, C.CUST_NAME, B.LIST_ID, D.PRODUCT_NAME,D.PRODUCT_CODE,F.LIST_NAME,F.LIST_NAME AS SUB_PRODUCT_NAME
        FROM INTRUST..TMODIBENACCTDETAIL A, INTRUST..TBENIFITOR B, INTRUST..TCUSTOMERINFO C, INTRUST..TPRODUCT D,INTRUST..TCONTRACT E LEFT JOIN INTRUST..TSUBPRODUCT F ON (E.SUB_PRODUCT_ID =F.SUB_PRODUCT_ID)
        WHERE A.BEN_SERIAL_NO = B.SERIAL_NO AND B.CUST_ID = C.CUST_ID AND B.PRODUCT_ID = D.PRODUCT_ID
            AND(B.PRODUCT_ID = E.PRODUCT_ID AND B.CONTRACT_BH = E.CONTRACT_BH)
            AND(B.PRODUCT_ID = @IN_PRODUCT_ID OR ISNULL(@IN_PRODUCT_ID,0) = 0)
            AND(B.CONTRACT_BH LIKE '%'+@IN_CONTRACT_BH+'%' OR ISNULL(@IN_CONTRACT_BH,'') = '')
            AND(E.CONTRACT_SUB_BH LIKE '%'+@IN_CONTRACT_SUB_BH+'%' OR ISNULL(@IN_CONTRACT_SUB_BH,'') = '')
            AND(C.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%' OR ISNULL(@IN_CUST_NAME,'') = '')
            AND(A.OLD_BANK_ID = @IN_OLD_BANK_ID OR ISNULL(@IN_OLD_BANK_ID,'') = '')
            AND(A.OLD_BANK_ACCT = @IN_OLD_BANK_ACCT OR ISNULL(@IN_OLD_BANK_ACCT,'') = '')
            AND(A.NEW_BANK_ID = @IN_NEW_BANK_ID OR ISNULL(@IN_NEW_BANK_ID,'') = '')
            AND(A.NEW_BANK_ACCT = @IN_NEW_BANK_ACCT OR ISNULL(@IN_NEW_BANK_ACCT,'') = '')
            AND (C.CUST_NO LIKE '%'+@IN_CUST_NO+'%' OR ISNULL(@IN_CUST_NO,'') = '')
            AND (ISNULL(@IN_SUB_PRODUCT_ID,0) =0 OR E.SUB_PRODUCT_ID =@IN_SUB_PRODUCT_ID)
            AND(@V_SW20409 = 0 OR @V_SW20409 IS NULL OR B.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCTS))
     ORDER BY B.PRODUCT_ID ASC,B.CONTRACT_BH ASC,B.LIST_ID ASC,A.MODI_TIME DESC
GO
