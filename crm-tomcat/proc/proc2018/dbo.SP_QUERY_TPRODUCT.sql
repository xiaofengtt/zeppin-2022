USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TPRODUCT @IN_BOOK_CODE       INT,
                                   @IN_PRODUCT_ID      INT,
                                   @IN_PRODUCT_CODE    NVARCHAR(6),
                                   @IN_PRODUCT_NAME    NVARCHAR(60),
                                   @IN_PRODUCT_STATUS  NVARCHAR(10),      --产品状态
                                   @IN_OPEN_FLAG       INT           = 0, --发行方式 1开放式2封闭式
                                   @IN_START_DATE      INT = NULL,
                                   @IN_END_DATE        INT = NULL,
                                   @IN_CHECK_FLAG      INT           = 0, --是否审核
                                   @IN_INTRUST_FLAG1   INT,               --集合标志
                                   @IN_SALE_STATUS     INT,               --CRM产品销售参数审核状态 -9为设置，1为审核，2已审核
                                   @IN_INPUT_MAN       INT
WITH ENCRYPTION
AS
    DECLARE @V_SINGLE INT
    SELECT @V_SINGLE = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = 'SINGLE'
    SELECT @V_SINGLE = ISNULL(@V_SINGLE,0)

    BEGIN
        SELECT A.*,B.RISK_LEVEL,CASE A.INTRUST_FLAG1 WHEN 1 THEN ISNULL(B.SALE_STATUS,2) WHEN 2 THEN ISNULL(B.SALE_STATUS,-9) END AS SALE_STATUS,
               B.CHANNEL_ID, B.CHANNEL_FARE_RATE,B.COMMISSION_RATE,B.PRE_START_TIME,B.PRE_END_TIME,B.CHECK_MAN,C.CHANNEL_NAME--, D.LIST_ID,D.LIST_NAME
               ,B.PRE_MIN_AMOUNT
            FROM INTRUST..TPRODUCT A
                    LEFT JOIN TPRODUCT B ON(A.PRODUCT_ID = B.PRODUCT_ID)
                    LEFT JOIN INTRUST..TCHANNEL C ON(B.CHANNEL_ID = C.CHANNEL_ID)
                    --LEFT JOIN INTRUST..TSUBPRODUCT D ON A.PRODUCT_ID = D.PRODUCT_ID
            WHERE A.BOOK_CODE  = @IN_BOOK_CODE --AND A.PRODUCT_STATUS IN ('110202')
                AND (A.PRODUCT_ID = @IN_PRODUCT_ID OR ISNULL(@IN_PRODUCT_ID,0) = 0)
                AND (A.PRODUCT_CODE LIKE '%'+@IN_PRODUCT_CODE+'%' OR @IN_PRODUCT_CODE IS NULL OR @IN_PRODUCT_CODE = '')
                AND (A.PRODUCT_NAME LIKE '%'+@IN_PRODUCT_NAME+'%' OR @IN_PRODUCT_NAME IS NULL OR @IN_PRODUCT_NAME = '')
                AND (A.PRODUCT_STATUS = @IN_PRODUCT_STATUS OR @IN_PRODUCT_STATUS IS NULL OR @IN_PRODUCT_STATUS = '')
                AND (A.OPEN_FLAG = @IN_OPEN_FLAG OR ISNULL(@IN_OPEN_FLAG,0) = 0)
                AND (A.CHECK_FLAG = @IN_CHECK_FLAG OR ISNULL(@IN_CHECK_FLAG,0) = 0)
                AND (A.INTRUST_FLAG1 = @IN_INTRUST_FLAG1 OR ISNULL(@IN_INTRUST_FLAG1,0) = 0)
                AND ((A.PRE_START_DATE BETWEEN @IN_START_DATE AND @IN_END_DATE)
                    OR(A.PRE_END_DATE BETWEEN @IN_START_DATE AND @IN_END_DATE)
                    OR(@IN_START_DATE IS NULL OR @IN_START_DATE = 0)
                    OR(@IN_END_DATE IS NULL OR @IN_END_DATE = 0))
                AND ISNULL(A.FINANCIAL_CONSULTANT,0) <> 1
                AND ((A.INTRUST_FLAG1 = 2 AND ISNULL(SALE_STATUS,-9) = @IN_SALE_STATUS) OR (A.INTRUST_FLAG1 = 1 AND ISNULL(SALE_STATUS,2) = @IN_SALE_STATUS) OR ISNULL(@IN_SALE_STATUS,0) = 0)
                AND ((A.INTRUST_FLAG1 = 2 AND @V_SINGLE = 0) OR (@V_SINGLE = 1))
            ORDER BY A.PRODUCT_CODE DESC,A.INTRUST_FLAG1
    END
GO
