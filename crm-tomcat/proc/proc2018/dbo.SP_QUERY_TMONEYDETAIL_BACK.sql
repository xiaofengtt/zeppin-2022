USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TMONEYDETAIL_BACK @IN_BOOK_CODE           INTEGER,
                                            @IN_PRODUCT_ID          INTEGER,
                                            @IN_CONTRACT_BH         VARCHAR(16),
                                            @IN_INPUT_MAN           INTEGER         = NULL,
                                            @IN_PRODUCT_NAME        VARCHAR(60)     = NULL,
                                            @IN_MIN_TO_MONEY        DECIMAL(16,3)   = NULL,
                                            @IN_MAX_TO_MONEY        DECIMAL(16,3)   = NULL,
                                            @IN_CONTRACT_SUB_BH     VARCHAR(50)     = NULL
WITH ENCRYPTION
AS

    DECLARE @V_SW20409 INT,@V_ISMONYFH INT,@V_CHECK_FLAG INT
    SELECT @V_SW20409 = VALUE FROM INTRUST..TSYSCONTROL WHERE FLAG_TYPE = 'SW20409'
    SELECT @V_ISMONYFH = VALUE FROM INTRUST..TSYSCONTROL WHERE FLAG_TYPE = 'ISMONYFH'
    IF ISNULL(@V_ISMONYFH,0)=0 SET @V_CHECK_FLAG=2
    ELSE SET @V_CHECK_FLAG=9
    DECLARE @V_TMP_PRODUCTS TABLE(PRODUCT_ID INT)
    IF @V_SW20409 = 1 --是否有产品的查询权限
        INSERT INTO @V_TMP_PRODUCTS
            SELECT PRODUCT_ID FROM INTRUST..TPRODUCT WHERE BOOK_CODE = @IN_BOOK_CODE
                AND(INTRUST_FLAG1 IN ( SELECT FUNC_ID-2040900 FROM TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID > 2040900 AND FUNC_ID <= 2040902 AND OP_CODE = @IN_INPUT_MAN )
                    OR DEPART_ID IN (SELECT B.DEPART_ID FROM TOPRIGHT A,TOPERATOR B WHERE A.OP_CODE = B.OP_CODE AND A.MENU_ID = '20409' AND A.FUNC_ID = 2040903 AND A.OP_CODE = @IN_INPUT_MAN)
                    OR PRODUCT_ID IN ( SELECT FUNC_ID FROM TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID < 2040900 AND OP_CODE = @IN_INPUT_MAN)
                    OR ADMIN_MANAGER = @IN_INPUT_MAN OR INPUT_MAN = @IN_INPUT_MAN OR ISNULL(@IN_INPUT_MAN,0)=0 
                    OR SUB_MAN = @IN_INPUT_MAN OR SUB_MAN2 = @IN_INPUT_MAN
                    OR CASH_MAN = @IN_INPUT_MAN OR CASH_MAN2 = @IN_INPUT_MAN
                    OR ADMIN_MANAGER IN(SELECT OP_CODE FROM INTRUST..TOPCUSTRIGHT WHERE ENABLE_OP_CODE = @IN_INPUT_MAN))

	SELECT A.SERIAL_NO,A.PRODUCT_ID,B.PRODUCT_NAME,A.CONTRACT_BH,A.CUST_ID,'' AS CUST_NO,A.CUST_NAME,F.CARD_ID, A.DZ_DATE,A.TO_MONEY,A.TO_AMOUNT,A.TO_BANK_DATE,A.JK_TYPE_NAME,A.CHECK_FLAG,
           A.PZ_FLAG,A.JK_TYPE,A.BOOK_CODE,A.INPUT_MAN,A.FEE_TYPE,A.FEE_MONEY,A.SUMMARY,C.CONTRACT_SUB_BH,D.LIST_NAME,D.LIST_NAME AS SUB_PRODUCT_NAME,C.START_DATE,
           (ISNULL(E.BANK_NAME,C.BANK_NAME) + ISNULL('-' + ISNULL(E.BANK_SUB_NAME,C.BANK_SUB_NAME),'')) AS BANK_NAME,
           ISNULL(E.BANK_ACCT,C.BANK_ACCT) AS BANK_ACCT,
           ISNULL(E.CUST_ACCT_NAME,C.GAIN_ACCT) AS GAIN_ACCT,
           B.TG_BANK_NAME,B.TG_BANK_SUB_NAME,B.TG_BANK_ACCT,C.SERVICE_MAN,B.MATAIN_MANAGER,C.PROV_LEVEL,C.PROV_LEVEL_NAME,C.END_DATE
        FROM INTRUST..TMONEYDETAIL A LEFT JOIN INTRUST..TSUBPRODUCT D ON A.SUB_PRODUCT_ID = D.SUB_PRODUCT_ID
                            LEFT JOIN INTRUST..TBENIFITOR E ON A.PRODUCT_ID = E.PRODUCT_ID AND A.CONTRACT_BH = E.CONTRACT_BH AND A.LIST_ID = E.LIST_ID,
             INTRUST..TPRODUCT B,INTRUST..TCONTRACT C,INTRUST..TCUSTOMERINFO F
         WHERE A.IS_JK_DATA IN(0,1) 
            AND  A.CUST_ID =F.CUST_ID
            AND A.PRODUCT_ID = B.PRODUCT_ID
            AND A.PRODUCT_ID = C.PRODUCT_ID AND A.CONTRACT_BH = C.CONTRACT_BH
            AND A.PZ_FLAG=1  --1未出凭证
            AND A.CHECK_FLAG = @V_CHECK_FLAG
            AND A.BOOK_CODE = @IN_BOOK_CODE
            AND (A.PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID IS NULL OR @IN_PRODUCT_ID = 0)
            AND (A.CONTRACT_BH LIKE '%'+@IN_CONTRACT_BH+'%' OR @IN_CONTRACT_BH IS NULL OR @IN_CONTRACT_BH = '')
            AND (C.CONTRACT_SUB_BH LIKE '%'+@IN_CONTRACT_SUB_BH+'%' OR @IN_CONTRACT_SUB_BH IS NULL OR @IN_CONTRACT_SUB_BH = '')
            AND(@V_SW20409 = 0 OR @V_SW20409 IS NULL OR A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_TMP_PRODUCTS))
            AND(B.PRODUCT_NAME LIKE '%'+@IN_PRODUCT_NAME+'%' OR ISNULL(@IN_PRODUCT_NAME,'') = '')
            AND( (A.TO_MONEY BETWEEN @IN_MIN_TO_MONEY AND @IN_MAX_TO_MONEY)
                         OR (ISNULL(@IN_MIN_TO_MONEY,0) = 0 AND A.TO_MONEY <= @IN_MAX_TO_MONEY)
                         OR (ISNULL(@IN_MAX_TO_MONEY,0) = 0 AND A.TO_MONEY >= @IN_MIN_TO_MONEY)
                         OR (ISNULL(@IN_MIN_TO_MONEY,0) = 0 AND ISNULL(@IN_MAX_TO_MONEY,0) = 0)
                     )
        ORDER BY A.DZ_DATE DESC, A.JK_TYPE DESC
GO
