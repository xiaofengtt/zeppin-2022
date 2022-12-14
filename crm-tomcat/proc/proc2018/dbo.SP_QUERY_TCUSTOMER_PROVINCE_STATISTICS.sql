USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCUSTOMER_PROVINCE_STATISTICS   @IN_BEGIN_DATE    INT=0,
                                                          @IN_END_DATE      INT=0
WITH ENCRYPTION
AS
    IF ISNULL(@IN_END_DATE,0)=0
        SET @IN_END_DATE = dbo.GETDATEINT(GETDATE())

    BEGIN
    SELECT ISNULL(REPLACE(REPLACE(C.TYPE_CONTENT,'省',''),'市',''),'其他')AS TYPE_CONTENT,COUNT(A.CUST_ID) AS ACOUNT,ROUND(SUM(F.RG_MONEY)/10000,2) AS RG_MONEY
        FROM TCUSTOMERS A LEFT JOIN TDICTPARAM C ON A.GOV_PROV_REGIONAL = C.TYPE_VALUE
            LEFT JOIN (SELECT CUST_ID,SUM(RG_MONEY)AS RG_MONEY FROM INTRUST..TCONTRACT GROUP BY CUST_ID) F ON F.CUST_ID = A.CUST_ID
            LEFT JOIN TCUSTSOURCEINFO E ON A.CUST_ID = E.CUST_ID
        WHERE EXISTS(SELECT B.CUST_ID FROM INTRUST..TBENIFITOR B WHERE A.CUST_ID = B.CUST_ID GROUP BY B.CUST_ID)
            AND A.CUST_TYPE = 1
            AND (E.CHANNEL_TYPE LIKE '550001%' OR E.CHANNEL_TYPE IS NULL)
            AND dbo.GETDATEINT(A.INPUT_TIME) <= ISNULL(@IN_END_DATE,30111230)
        GROUP BY ISNULL(A.GOV_PROV_REGIONAL,''),C.TYPE_CONTENT
    END

GO
