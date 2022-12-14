USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TPRODUCT_MARKETING_TOTAL @IN_PRODUCT_ID INT,
                                                   @IN_LINK_MAN   INT, --销售人员
                                                   @IN_INPUT_MAN  INT
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_DT_INTRUST INT,@V_SINGLE INT
    SELECT @V_DT_INTRUST = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'DT_INTRUST'
    SELECT @V_SINGLE = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'SINGLE'
    SELECT @V_SINGLE = ISNULL(@V_SINGLE,0)

    CREATE TABLE #TEMP_TPRODUCT_MARKETING_TOTAL
    (
        SERIAL_NO      INT NOT NULL IDENTITY,
        PRODUCT_ID     INT,
        PRODUCT_CODE   NVARCHAR(12),
        PRODUCT_NAME   NVARCHAR(120),
        PRODUCT_JC     NVARCHAR(60),
        PRE_MONEY      DEC(16,3),       --预计发行金额
        RG_MONEY       DEC(16,3),       --总已销售金额(认购金额)
        TO_MONEY       DEC(16,3),       --总缴款金额
        LINK_MAN       INT,             --销售人员
        LINK_MAN_NAME  NVARCHAR(40),    --销售人员名称
        MY_RG_MONEY    DEC(16,3),       --销售人员销售金额(认购金额)
        MY_TO_MONEY    DEC(16,3)        --销售人员缴款金额
    )
    IF @V_DT_INTRUST = 1 --启用分布式
    BEGIN
        INSERT INTO #TEMP_TPRODUCT_MARKETING_TOTAL(PRODUCT_ID,PRODUCT_CODE,PRODUCT_NAME,PRODUCT_JC,PRE_MONEY,LINK_MAN,MY_RG_MONEY,MY_TO_MONEY)
            SELECT B.PRODUCT_ID,B.PRODUCT_CODE,B.PRODUCT_NAME,B.PRODUCT_JC,B.PRE_MONEY,A.LINK_MAN,SUM(A.RG_MONEY),SUM(A.TO_MONEY)
                FROM SRV_Intrust.INTRUST.dbo.TCONTRACT A, SRV_Intrust.INTRUST.dbo.TPRODUCT B
                WHERE B.PRODUCT_STATUS = '110202' AND A.PRODUCT_ID = B.PRODUCT_ID
                      AND ((@V_SINGLE = 0 AND B.INTRUST_FLAG1 <> 1) OR (@V_SINGLE <> 0))
                GROUP BY B.PRODUCT_ID,B.PRODUCT_CODE,B.PRODUCT_NAME,B.PRODUCT_JC,B.PRE_MONEY,A.LINK_MAN
    END
    ELSE
    BEGIN
        INSERT INTO #TEMP_TPRODUCT_MARKETING_TOTAL(PRODUCT_ID,PRODUCT_CODE,PRODUCT_NAME,PRODUCT_JC,PRE_MONEY,LINK_MAN,MY_RG_MONEY,MY_TO_MONEY)
            SELECT B.PRODUCT_ID,B.PRODUCT_CODE,B.PRODUCT_NAME,B.PRODUCT_JC,B.PRE_MONEY,A.LINK_MAN,SUM(A.RG_MONEY),SUM(A.TO_MONEY)
                FROM INTRUST.dbo.TCONTRACT A, INTRUST.dbo.TPRODUCT B
                WHERE B.PRODUCT_STATUS = '110202' AND A.PRODUCT_ID = B.PRODUCT_ID AND ((@V_SINGLE = 0 AND B.INTRUST_FLAG1 <> 1) OR (@V_SINGLE <> 0))
                GROUP BY B.PRODUCT_ID,B.PRODUCT_CODE,B.PRODUCT_NAME,B.PRODUCT_JC,B.PRE_MONEY,A.LINK_MAN
    END
    UPDATE #TEMP_TPRODUCT_MARKETING_TOTAL
        SET LINK_MAN_NAME = B.OP_NAME
        FROM #TEMP_TPRODUCT_MARKETING_TOTAL A, TOPERATOR B
        WHERE A.LINK_MAN = B.OP_CODE
    UPDATE #TEMP_TPRODUCT_MARKETING_TOTAL
        SET RG_MONEY = B.RG_MONEY, TO_MONEY = B.TO_MONEY
        FROM #TEMP_TPRODUCT_MARKETING_TOTAL A, (SELECT PRODUCT_ID, SUM(MY_RG_MONEY) AS RG_MONEY, SUM(MY_TO_MONEY) AS TO_MONEY FROM #TEMP_TPRODUCT_MARKETING_TOTAL GROUP BY PRODUCT_ID) B
        WHERE A.PRODUCT_ID = B.PRODUCT_ID

    SELECT PRODUCT_ID,PRODUCT_CODE,PRODUCT_NAME,PRODUCT_JC,PRE_MONEY,RG_MONEY,TO_MONEY,LINK_MAN,LINK_MAN_NAME,MY_RG_MONEY,MY_TO_MONEY
    FROM #TEMP_TPRODUCT_MARKETING_TOTAL
    WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID IS NULL OR @IN_PRODUCT_ID = 0)
        AND(LINK_MAN = @IN_LINK_MAN OR @IN_LINK_MAN IS NULL OR @IN_LINK_MAN = 0)
GO
