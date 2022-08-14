﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TDEPLOY_OUTPUT @IN_BOOK_CODE      INTEGER,
                                         @IN_PRODUCT_ID     INTEGER,
                                         @IN_BANK_ID        NVARCHAR(10),
                                         @IN_SY_TYPE        NVARCHAR(10),
                                         @IN_JK_TYPE        NVARCHAR(10)    = '',
                                         @IN_SY_DATE        INTEGER         = NULL,
                                         @IN_PROV_LEVEL     NVARCHAR(10)    = NULL,
                                         @IN_INPUT_MAN      INTEGER         = NULL,
                                         @IN_SUB_PRODUCT_ID INT   =0,          --子产品ID   20111112  LUOHH
                                         @IN_LINK_MAN     INT   =0
WITH ENCRYPTION
AS
--20409: The Products can be accessed. START
    DECLARE @V_SW20409 INT,@V_USER_ID INT
    SELECT @V_USER_ID=USER_ID FROM TSYSTEMINFO
    SELECT @V_SW20409 = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = 'SW20409'
    DECLARE @V_PRODUCTS TABLE(PRODUCT_ID INT)
    IF @V_SW20409 = 1
        INSERT INTO @V_PRODUCTS
            SELECT PRODUCT_ID FROM INTRUST..TPRODUCT WHERE BOOK_CODE = @IN_BOOK_CODE
                AND(INTRUST_FLAG1 IN ( SELECT FUNC_ID-2040900 FROM TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID > 2040900 AND FUNC_ID <= 2040902 AND OP_CODE = @IN_INPUT_MAN )
                    OR DEPART_ID IN (SELECT B.DEPART_ID FROM TOPRIGHT A,TOPERATOR B WHERE A.OP_CODE = B.OP_CODE AND A.MENU_ID = '20409' AND A.FUNC_ID = 2040903 AND A.OP_CODE = @IN_INPUT_MAN)
                    OR PRODUCT_ID IN ( SELECT FUNC_ID FROM TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID < 2040900 AND OP_CODE = @IN_INPUT_MAN)
                    OR ADMIN_MANAGER = @IN_INPUT_MAN OR INPUT_MAN = @IN_INPUT_MAN OR ISNULL(@IN_INPUT_MAN,0)=0 
                    OR SUB_MAN = @IN_INPUT_MAN OR SUB_MAN2 = @IN_INPUT_MAN
                    OR CASH_MAN = @IN_INPUT_MAN OR CASH_MAN2 = @IN_INPUT_MAN
                    OR ADMIN_MANAGER IN(SELECT OP_CODE FROM INTRUST..TOPCUSTRIGHT WHERE ENABLE_OP_CODE = @IN_INPUT_MAN)
                    )
--20409: The Products can be accessed. END
    --如果有客户访问权限控制
    IF EXISTS(SELECT * FROM TSYSCONTROL WHERE FLAG_TYPE = 'CUSTRIGHT' AND VALUE = 1)
    BEGIN
        SELECT A.*, B.BANK_ID,D.CONTRACT_SUB_BH, BANK_NAME = B.BANK_NAME + ISNULL(B.BANK_SUB_NAME,''), B.BANK_ACCT, 
              dbo.ENCRYPTCUSTINFO(C.SERVICE_MAN,@IN_INPUT_MAN,C.CARD_ID) AS CARD_ID, C.CUST_NAME,C.CARD_TYPE_NAME,B.CUST_ACCT_NAME,E.LIST_NAME,E.LIST_NAME AS SUB_PRODUCT_NAME
            FROM INTRUST..TDEPLOY A, INTRUST..TBENIFITOR B, INTRUST..TCUSTOMERINFO C, INTRUST..TCONTRACT D LEFT JOIN INTRUST..TSUBPRODUCT E ON (D.SUB_PRODUCT_ID =E.SUB_PRODUCT_ID)
            WHERE A.BOOK_CODE = B.BOOK_CODE AND A.PRODUCT_ID = B.PRODUCT_ID 
                  AND A.CONTRACT_BH = B.CONTRACT_BH AND A.LIST_ID = B.LIST_ID 
                  AND B.PRODUCT_ID = D.PRODUCT_ID AND B.CONTRACT_BH = D.CONTRACT_BH 
                  AND A.CUST_ID = C.CUST_ID 
                  AND A.BOOK_CODE = @IN_BOOK_CODE AND A.PRODUCT_ID = @IN_PRODUCT_ID 
                  AND (@V_SW20409 = 0 OR @V_SW20409 IS NULL OR A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCTS))
                  AND (A.JK_TYPE = @IN_JK_TYPE OR ISNULL(@IN_JK_TYPE,'') = '')
                  AND (A.SY_TYPE = @IN_SY_TYPE OR ISNULL(@IN_SY_TYPE,'') = '' OR (@IN_SY_TYPE='111699' AND A.SY_TYPE IN ('111605','111603'))) 
                  AND (B.BANK_ID = @IN_BANK_ID OR ISNULL(@IN_BANK_ID,'') = '') 
                  AND (A.SY_DATE = @IN_SY_DATE OR ISNULL(@IN_SY_DATE,0) = 0 )
                  AND (A.PROV_LEVEL = @IN_PROV_LEVEL OR ISNULL(@IN_PROV_LEVEL,'') = '')
                  AND (ISNULL(@IN_SUB_PRODUCT_ID,0) = 0 OR D.SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID)
                  AND (ISNULL(@IN_LINK_MAN,0) = 0 OR D.LINK_MAN = @IN_LINK_MAN)
            ORDER BY A.CONTRACT_BH
    END
    ELSE
    BEGIN
        SELECT A.*, B.BANK_ID,D.CONTRACT_SUB_BH, BANK_NAME = B.BANK_NAME + ISNULL(B.BANK_SUB_NAME,''), B.BANK_ACCT, C.CARD_ID,
               C.CUST_NAME,C.CARD_TYPE_NAME,B.CUST_ACCT_NAME,E.LIST_NAME,E.LIST_NAME AS SUB_PRODUCT_NAME
            FROM INTRUST..TDEPLOY A, INTRUST..TBENIFITOR B, INTRUST..TCUSTOMERINFO C, INTRUST..TCONTRACT D LEFT JOIN INTRUST..TSUBPRODUCT E ON (D.SUB_PRODUCT_ID =E.SUB_PRODUCT_ID)
            WHERE A.BOOK_CODE = B.BOOK_CODE AND A.PRODUCT_ID = B.PRODUCT_ID 
                  AND A.CONTRACT_BH = B.CONTRACT_BH AND A.LIST_ID = B.LIST_ID 
                  AND B.PRODUCT_ID = D.PRODUCT_ID AND B.CONTRACT_BH = D.CONTRACT_BH 
                  AND A.CUST_ID = C.CUST_ID 
                  AND A.BOOK_CODE = @IN_BOOK_CODE AND A.PRODUCT_ID = @IN_PRODUCT_ID 
                  AND (@V_SW20409 = 0 OR @V_SW20409 IS NULL OR A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCTS)) 
                  AND (A.JK_TYPE = @IN_JK_TYPE OR ISNULL(@IN_JK_TYPE,'') = '') 
                  AND (A.SY_TYPE = @IN_SY_TYPE OR ISNULL(@IN_SY_TYPE,'') = '' OR (@IN_SY_TYPE='111699' AND A.SY_TYPE IN ('111605','111603')))
                  AND (B.BANK_ID = @IN_BANK_ID OR ISNULL(@IN_BANK_ID,'') = '')
                  AND (A.SY_DATE = @IN_SY_DATE OR ISNULL(@IN_SY_DATE,0) = 0 )
                  AND (A.PROV_LEVEL = @IN_PROV_LEVEL OR ISNULL(@IN_PROV_LEVEL,'') = '')
                  AND (ISNULL(@IN_SUB_PRODUCT_ID,0) = 0 OR D.SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID)
                  AND (ISNULL(@IN_LINK_MAN,0) = 0 OR D.LINK_MAN = @IN_LINK_MAN)
            ORDER BY A.CONTRACT_BH
    END
GO
