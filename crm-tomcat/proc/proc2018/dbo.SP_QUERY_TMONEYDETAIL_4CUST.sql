USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TMONEYDETAIL_4CUST
                                       @IN_SERIAL_NO            INT,                            
                                       @IN_PRODUCT_ID           INT,                            --产品ID
                                       @IN_SUB_PRODUCT_ID       INT ,                           --子产品ID   20111112  LUOHH
                                       @IN_PRODUCT_NAME         NVARCHAR(100) ,                 --产品名称
                                       @IN_CUST_ID              INTEGER ,                       --客户ID
                                       @IN_CUST_NAME            NVARCHAR(100) ,                 --客户名称
                                       @IN_CONTRACT_SUB_BH      NVARCHAR(50) ,                  --实际合同编号
                                       @IN_DZ_DATE              INTEGER         = NULL,         --缴款时间区间上限
                                       @IN_END_DATE             INTEGER         = 0,            --缴款时间区间下限
                                       @IN_CHECK_FLAG           INTEGER,                        --审核标志
                                       @IN_INPUT_MAN            INTEGER                         --操作员
                                       
WITH ENCRYPTION
AS
    --20409: The Products can be accessed. START
    DECLARE @V_SW20409 INT
    SELECT @V_SW20409 = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = 'SW20409'
    DECLARE @V_TMP_PRODUCTS TABLE(PRODUCT_ID INT)
    IF ISNULL(@IN_PRODUCT_ID,0) <> 0 
        INSERT INTO @V_TMP_PRODUCTS
            SELECT PRODUCT_ID FROM TPRODUCT WHERE PRODUCT_ID = @IN_PRODUCT_ID
    ELSE
    BEGIN
        IF @V_SW20409 = 1
        INSERT INTO @V_TMP_PRODUCTS
            SELECT PRODUCT_ID FROM INTRUST..TPRODUCT WHERE BOOK_CODE = 1
                AND(INTRUST_FLAG1 IN ( SELECT FUNC_ID-2040900 FROM INTRUST..TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID > 2040900 AND FUNC_ID <= 2040902 AND OP_CODE = @IN_INPUT_MAN )
                    OR DEPART_ID IN (SELECT B.DEPART_ID FROM INTRUST..TOPRIGHT A,INTRUST..TOPERATOR B WHERE A.OP_CODE = B.OP_CODE AND A.MENU_ID = '20409' AND A.FUNC_ID = 2040903 AND A.OP_CODE = @IN_INPUT_MAN)
                    OR PRODUCT_ID IN ( SELECT FUNC_ID FROM INTRUST..TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID < 2040900 AND OP_CODE = @IN_INPUT_MAN)
                    OR ADMIN_MANAGER = @IN_INPUT_MAN OR INPUT_MAN = @IN_INPUT_MAN OR ISNULL(@IN_INPUT_MAN,0)=0
                    OR SUB_MAN = @IN_INPUT_MAN OR SUB_MAN2 = @IN_INPUT_MAN
                    OR CASH_MAN = @IN_INPUT_MAN OR CASH_MAN2 = @IN_INPUT_MAN
                    OR ADMIN_MANAGER IN(SELECT OP_CODE FROM INTRUST..TOPCUSTRIGHT WHERE ENABLE_OP_CODE = @IN_INPUT_MAN))
    --20409: The Products can be accessed. END
    END
  
    SELECT A.SERIAL_NO,A.PRODUCT_ID,A.CUST_ID,B.CARD_ID,C.PRODUCT_NAME,A.CUST_NAME,A.DZ_DATE,A.TO_MONEY,A.TO_AMOUNT,A.TO_BANK_DATE,A.JK_TYPE_NAME,A.CHECK_FLAG,
           A.JK_TYPE,A.INPUT_MAN,A.FEE_TYPE,A.FEE_MONEY,C.START_DATE,
           A.JS_DATE,D.CONTRACT_SUB_BH,F.LIST_NAME AS SUB_PRODUCT_NAME,
           (ISNULL(E.BANK_NAME,D.BANK_NAME) + ISNULL('-' + ISNULL(E.BANK_SUB_NAME,D.BANK_SUB_NAME),'')) AS BANK_NAME,
           ISNULL(E.BANK_ACCT,D.BANK_ACCT) AS BANK_ACCT,
           ISNULL(E.CUST_ACCT_NAME,D.GAIN_ACCT) AS GAIN_ACCT,
           E.PROV_LEVEL_NAME,C.MATAIN_MANAGER, A.PRINT_TIMES
        FROM INTRUST..TMONEYDETAIL A LEFT JOIN INTRUST..TSUBPRODUCT F ON A.SUB_PRODUCT_ID = F.SUB_PRODUCT_ID
                 LEFT JOIN INTRUST..TBENIFITOR E ON A.PRODUCT_ID = E.PRODUCT_ID AND A.CONTRACT_BH = E.CONTRACT_BH AND A.LIST_ID = E.LIST_ID
                 --LEFT JOIN INTRUST..TINTEGERPARAM G ON E.PROV_FLAG = G.TYPE_VALUE AND G.TYPE_ID = 3003
                 LEFT JOIN INTRUST..TCUSTOMERINFO B ON A.CUST_ID = B.CUST_ID
                 LEFT JOIN INTRUST..TPRODUCT C ON A.PRODUCT_ID = C.PRODUCT_ID
                 LEFT JOIN INTRUST..TCONTRACT D ON D.PRODUCT_ID = A.PRODUCT_ID AND D.CONTRACT_BH = A.CONTRACT_BH --AND A.CUST_ID=D.CUST_ID
        WHERE A.BOOK_CODE = 1 AND A.IS_JK_DATA IN(0,1)
              AND ISNULL(A.CARD_LOST_FLAG,0) <> 11
              --AND A.PZ_FLAG=2  --A.CHECK_FLAG=2则表明可以出凭证，目前不用关心是否已出凭证
              AND (A.SERIAL_NO=@IN_SERIAL_NO OR ISNULL(@IN_SERIAL_NO,0)=0)
              AND (A.PRODUCT_ID = @IN_PRODUCT_ID OR ISNULL(@IN_PRODUCT_ID,0)=0)
              AND (A.SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID OR ISNULL(@IN_SUB_PRODUCT_ID,0)=0)
              AND (C.PRODUCT_NAME LIKE '%' + @IN_PRODUCT_NAME + '%' OR ISNULL(@IN_PRODUCT_NAME,'')='')
              AND (A.CUST_ID = @IN_CUST_ID OR ISNULL(@IN_CUST_ID,0)=0)
              AND (B.CUST_NAME LIKE '%' + @IN_CUST_NAME + '%' OR @IN_CUST_NAME IS NULL OR @IN_CUST_NAME = '')
              AND (D.CONTRACT_SUB_BH LIKE '%'+@IN_CONTRACT_SUB_BH+'%' OR @IN_CONTRACT_SUB_BH IS NULL OR @IN_CONTRACT_SUB_BH = '')
              AND ((A.DZ_DATE BETWEEN @IN_DZ_DATE AND @IN_END_DATE) OR (ISNULL(@IN_DZ_DATE,0) = 0 AND ISNULL(@IN_END_DATE,0) = 0))
              AND (A.CHECK_FLAG = @IN_CHECK_FLAG OR ISNULL(@IN_CHECK_FLAG,0) = 0)
              AND (@V_SW20409 = 0 OR @V_SW20409 IS NULL OR A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_TMP_PRODUCTS))
        ORDER BY A.PRODUCT_ID ASC,DZ_DATE DESC,A.CONTRACT_BH ASC
GO
