﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_STAT_CUSTOMER_AREA1 @IN_DATE         INT = NULL,
                                        @IN_CHANNEL_TYPE NVARCHAR(10) = ''
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    IF @IN_DATE IS NULL OR @IN_DATE = 0
        SET @IN_DATE = dbo.GETDATEINT(GETDATE())

    DECLARE @VT_CUSTS TABLE(CUST_ID INT)
    INSERT INTO @VT_CUSTS(CUST_ID) --只购买了单一产品的客户
        SELECT DISTINCT CUST_ID FROM INTRUST..TBENIFITOR A WHERE EXISTS(SELECT 1 FROM INTRUST..TPRODUCT B WHERE A.PRODUCT_ID = B.PRODUCT_ID AND B.INTRUST_FLAG1 = 1)
            AND NOT EXISTS(SELECT 1 FROM INTRUST..TPRODUCT C WHERE A.PRODUCT_ID = C.PRODUCT_ID AND C.INTRUST_FLAG1 = 2)
    CREATE TABLE #TMP_CUST_FIRSTBUY(CUST_ID INT, FIRST_RG_DATE INT) --客户首次购买时间
    INSERT INTO #TMP_CUST_FIRSTBUY(CUST_ID) SELECT CUST_ID FROM EFCRM..TCustomers
    UPDATE #TMP_CUST_FIRSTBUY SET FIRST_RG_DATE = B.BEN_DATE
        FROM #TMP_CUST_FIRSTBUY A, (SELECT CUST_ID, MIN(BEN_DATE) AS BEN_DATE FROM INTRUST..TBENIFITOR GROUP BY CUST_ID) B
        WHERE A.CUST_ID = B.CUST_ID

    --按省汇总客户数和购买金额（直销、集合、指定日期前）
    SELECT ISNULL(GOV_PROV_REGIONAL,'') AS GOV_PROV_REGIONAL,ISNULL(GOV_PROV_REGIONAL_NAME,'') AS GOV_PROV_REGIONAL_NAME,
           COUNT(*) AS NUMBER, SUM(R.TO_AMOUNT) AS TO_AMOUNT
        FROM EFCRM..TCUSTOMERS A LEFT JOIN
             (SELECT CUST_ID, SUM(TO_AMOUNT)AS TO_AMOUNT FROM INTRUST..TMONEYDETAIL
              WHERE IS_JK_DATA = 1 AND DZ_DATE <= @IN_DATE GROUP BY CUST_ID) R ON A.CUST_ID = R.CUST_ID
        WHERE EXISTS(SELECT 1 FROM EFCRM..TCUSTSOURCEINFO B WHERE CHANNEL_TYPE LIKE '550001%' AND A.CUST_ID = B.CUST_ID)
            AND EXISTS(SELECT 1 FROM INTRUST..TBENIFITOR O WHERE A.CUST_ID = O.CUST_ID)
            AND EXISTS(SELECT 1 FROM #TMP_CUST_FIRSTBUY H WHERE A.CUST_ID = H.CUST_ID AND H.FIRST_RG_DATE <= @IN_DATE)
            AND NOT EXISTS(SELECT 1 FROM @VT_CUSTS Z WHERE A.CUST_ID = Z.CUST_ID)
        GROUP BY ISNULL(GOV_PROV_REGIONAL,''),ISNULL(GOV_PROV_REGIONAL_NAME,'')
        ORDER BY ISNULL(GOV_PROV_REGIONAL,''),ISNULL(GOV_PROV_REGIONAL_NAME,'')
GO