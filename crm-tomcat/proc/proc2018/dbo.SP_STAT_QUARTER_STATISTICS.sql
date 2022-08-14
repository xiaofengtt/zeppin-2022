﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_STAT_QUARTER_STATISTICS   @IN_BEGIN_DATE  INT=0,
                                              @IN_END_DATE    INT=0
                                              --@IN_INPUT_MAN   INT

WITH ENCRYPTION
AS
    IF ISNULL(@IN_BEGIN_DATE,0)=0
        SET @IN_BEGIN_DATE = YEAR(GETDATE())*100+1
    IF ISNULL(@IN_END_DATE,0)=0
        SET @IN_END_DATE = YEAR(GETDATE())*100+12

    --新增投资客户数
    DECLARE @V_TEMP_ADDCUST TABLE(PRODUCT_ID INT,CUST_COUNT INT)
    BEGIN
        INSERT INTO @V_TEMP_ADDCUST
            SELECT B.PRODUCT_ID,COUNT(B.CUST_ID) FROM(
                SELECT CUST_ID,MIN(BEN_DATE) AS BEN_DATE,min(product_id) AS PRODUCT_ID
                FROM INTRUST..TBENIFITOR GROUP BY CUST_ID) B
            GROUP BY B.PRODUCT_ID ORDER BY B.PRODUCT_ID
    END

	DECLARE @V_TEMP_DIRECT TABLE(PRODUCT_ID INT,DIRECT_MONEY DECIMAL(16,3))
	BEGIN	--产品直销类的认购总金额
		INSERT INTO @V_TEMP_DIRECT
			SELECT PRODUCT_ID,SUM(RG_MONEY) FROM INTRUST..TCONTRACT
			WHERE CHANNEL_TYPE LIKE '550001%'
			GROUP BY PRODUCT_ID
	END

    DECLARE @V_TEMP_TABLE TABLE(VIEW_DATE INT,
                                PRODUCT_ID INT,
                                PRODUCT_CODE VARCHAR(10),
                                PRODUCT_NAME NVARCHAR(200),
                                FACT_MONEY	 DECIMAL(16,3),
                                VALID_DAY INT,
                                CUST_COUNT INT,
                                DIRECT_MONEY DECIMAL(5,4),
                                DEFAULT_NO INT )

    --产品的规模，预期天数
    BEGIN
        INSERT INTO @V_TEMP_TABLE
            SELECT A.START_DATE/100 AS VIEW_DATE,A.PRODUCT_ID,A.PRODUCT_CODE,A.PRODUCT_NAME,
                A.FACT_MONEY,(A.PRE_END_DATE-A.PRE_START_DATE) AS VALID_DAY,0,0.00,0
            FROM INTRUST..TPRODUCT A
				LEFT JOIN @V_TEMP_DIRECT B ON A.PRODUCT_ID = B.PRODUCT_ID
			WHERE A.START_DATE/100 BETWEEN @IN_BEGIN_DATE AND @IN_END_DATE
            GROUP BY A.START_DATE/100,A.PRODUCT_CODE,A.PRODUCT_NAME,A.FACT_MONEY,(A.PRE_END_DATE-A.PRE_START_DATE),A.PRODUCT_ID
    END

    BEGIN
        UPDATE @V_TEMP_TABLE SET CUST_COUNT = A.CUST_COUNT
            FROM @V_TEMP_ADDCUST A,@V_TEMP_TABLE B
            WHERE A.PRODUCT_ID = B.PRODUCT_ID
    END

	BEGIN
		UPDATE @V_TEMP_TABLE SET DIRECT_MONEY =
		CASE ISNULL(B.FACT_MONEY,0)
			WHEN 0 THEN 0.0000
			ELSE ISNULL(A.DIRECT_MONEY,0)/ISNULL(B.FACT_MONEY,0) END
		FROM @V_TEMP_DIRECT A,@V_TEMP_TABLE B
		WHERE B.PRODUCT_ID = A.PRODUCT_ID
	END
    --合计
    DECLARE @V_ALL_COUNT INT
    SELECT @V_ALL_COUNT = COUNT(*) FROM @V_TEMP_TABLE
    BEGIN
        INSERT INTO @V_TEMP_TABLE(PRODUCT_NAME,FACT_MONEY,VALID_DAY,CUST_COUNT,DEFAULT_NO)
            SELECT '合计',SUM(FACT_MONEY),SUM(VALID_DAY)/@V_ALL_COUNT,SUM(CUST_COUNT),1
            FROM @V_TEMP_TABLE
    END
   SELECT VIEW_DATE,PRODUCT_CODE,PRODUCT_NAME,FACT_MONEY,CUST_COUNT,''AS OTHER1,DIRECT_MONEY,VALID_DAY,'' AS OTHER2
   FROM @V_TEMP_TABLE
   order by	DEFAULT_NO,VIEW_DATE
GO