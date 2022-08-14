﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_SALE_COST @IN_CUST_NAME           NVARCHAR(200),
                                    @IN_GROUP_ID            INT,
                                    @IN_INPUT_MAN           INT = NULL
WITH ENCRYPTION
AS
    DECLARE @V_DT_INTRUST INT
    SELECT @V_DT_INTRUST = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'DT_INTRUST'

    CREATE TABLE #SP_QUERY_SALE_COST
        (CUST_ID INT,
         CUST_NO NVARCHAR(16),
         CUST_NAME   NVARCHAR(200),
         TOTAL_MONEY    DEC(16,3),
         ACTIVITY_FEE   DEC(9,3)
        )

    INSERT INTO #SP_QUERY_SALE_COST
    SELECT A.CUST_ID,A.CUST_NO,A.CUST_NAME,A.TOTAL_MONEY,B.ACTIVITY_FEE
        FROM TCUSTOMERS A LEFT JOIN TCUSTSERVICEINFO B ON(A.CUST_ID = B.CUST_ID)
        WHERE (ISNULL(@IN_GROUP_ID,0)=0 OR A.CUST_ID IN (SELECT CUST_ID FROM TCustGroupMembers WHERE GroupID = @IN_GROUP_ID))
            AND (A.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%' OR @IN_CUST_NAME IS NULL OR @IN_CUST_NAME = '')

    IF @V_DT_INTRUST = 1 --启用分布式
    BEGIN
        UPDATE #SP_QUERY_SALE_COST
            SET TOTAL_MONEY = B.TOTAL_MONEY
            FROM #SP_QUERY_SALE_COST A, SRV_Intrust.INTRUST.dbo.TCUSTOMERINFO B
            WHERE A.CUST_ID = B.CUST_ID
    END
    ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
    BEGIN
        UPDATE #SP_QUERY_SALE_COST
            SET TOTAL_MONEY = B.TOTAL_MONEY
            FROM #SP_QUERY_SALE_COST A, INTRUST..TCUSTOMERINFO B
            WHERE A.CUST_ID = B.CUST_ID
    END

    SELECT * FROM #SP_QUERY_SALE_COST
GO
