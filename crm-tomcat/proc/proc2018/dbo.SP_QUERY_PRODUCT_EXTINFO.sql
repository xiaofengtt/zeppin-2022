﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_PRODUCT_EXTINFO @IN_PRODUCT_ID     INT,            --产品ID
                                          @IN_PRODUCT_CODE   NVARCHAR(6),    --产品编号
                                          @IN_PRODUCT_NAME   NVARCHAR(60),   --产品名称（模糊查询）
                                          @IN_CLASS_TYPE1    NVARCHAR(10),   --产品分类(1113)
                                          @IN_START_DATE1    INT,            --产品成立日期下限
                                          @IN_START_DATE2    INT,            --产品成立日期上限
                                          @IN_FACT_MONEY1    DEC(16,3),      --产品规模下限
                                          @IN_FACT_MONEY2    DEC(16,3),      --产品规模上限
                                          @IN_SUMMARY        NVARCHAR(60),   --到产品简介和备注中进行全文搜索
                                          @IN_INPUT_MAN      INT
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_DT_INTRUST INT
    DECLARE @V_SW20409 INT, @IN_BOOK_CODE INT
    SET @IN_BOOK_CODE = 1
    SELECT @V_DT_INTRUST = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'DT_INTRUST'
    IF @V_DT_INTRUST = 1 --启用分布式
        SELECT @V_SW20409 = VALUE FROM SRV_Intrust.INTRUST.dbo.TSYSCONTROL WHERE FLAG_TYPE = 'SW20409'
    ELSE
        SELECT @V_SW20409 = VALUE FROM INTRUST..TSYSCONTROL WHERE FLAG_TYPE = 'SW20409'
    DECLARE @V_PRODUCTS TABLE(PRODUCT_ID INT)
    IF @V_DT_INTRUST = 1 --启用分布式
    BEGIN
        IF @V_SW20409 = 1
            INSERT INTO @V_PRODUCTS
                SELECT PRODUCT_ID FROM SRV_Intrust.INTRUST.dbo.TPRODUCT WHERE BOOK_CODE = @IN_BOOK_CODE
                    AND(INTRUST_FLAG1 IN ( SELECT -(FUNC_ID-2040900)%2+2 FROM SRV_Intrust.INTRUST.dbo.TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID >= 2040900 AND FUNC_ID <= 2040999 AND OP_CODE = @IN_INPUT_MAN )
                        OR PRODUCT_ID IN ( SELECT FUNC_ID FROM SRV_Intrust.INTRUST.dbo.TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID < 2040900 AND OP_CODE = @IN_INPUT_MAN)
                        OR ADMIN_MANAGER = @IN_INPUT_MAN OR INPUT_MAN = @IN_INPUT_MAN OR ISNULL(@IN_INPUT_MAN,0)=0
                        OR ADMIN_MANAGER IN(SELECT OP_CODE FROM SRV_Intrust.INTRUST.dbo.TOPCUSTRIGHT WHERE ENABLE_OP_CODE = @IN_INPUT_MAN)
                        )
    END
    ELSE
    BEGIN
        IF @V_SW20409 = 1
            INSERT INTO @V_PRODUCTS
                SELECT PRODUCT_ID FROM INTRUST..TPRODUCT WHERE BOOK_CODE = @IN_BOOK_CODE
                    AND(INTRUST_FLAG1 IN ( SELECT -(FUNC_ID-2040900)%2+2 FROM INTRUST..TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID >= 2040900 AND FUNC_ID <= 2040999 AND OP_CODE = @IN_INPUT_MAN )
                        OR PRODUCT_ID IN ( SELECT FUNC_ID FROM INTRUST..TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID < 2040900 AND OP_CODE = @IN_INPUT_MAN)
                        OR ADMIN_MANAGER = @IN_INPUT_MAN OR INPUT_MAN = @IN_INPUT_MAN OR ISNULL(@IN_INPUT_MAN,0)=0
                        OR ADMIN_MANAGER IN(SELECT OP_CODE FROM INTRUST..TOPCUSTRIGHT WHERE ENABLE_OP_CODE = @IN_INPUT_MAN)
                        )
    END
    CREATE TABLE #TMP_PRODUCT_INFOREPOS(
        PRODUCT_ID          INT,            --产品ID
        PRODUCT_CODE        NVARCHAR(6),    --产品编号
        PRODUCT_NAME        NVARCHAR(60)    --产品名称
    )

    IF @V_DT_INTRUST = 1 --启用分布式
    BEGIN
        INSERT INTO #TMP_PRODUCT_INFOREPOS(PRODUCT_ID,PRODUCT_CODE,PRODUCT_NAME)
            SELECT A.PRODUCT_ID,B.PRODUCT_CODE,B.PRODUCT_NAME
            FROM EFCRM..TPRODUCT A, SRV_Intrust.INTRUST.dbo.TPRODUCT B
            WHERE A.PRODUCT_ID = B.PRODUCT_ID
                AND(A.PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID IS NULL OR @IN_PRODUCT_ID = 0)
                AND(B.PRODUCT_CODE = @IN_PRODUCT_CODE OR @IN_PRODUCT_CODE IS NULL OR @IN_PRODUCT_CODE = '')
                AND(B.PRODUCT_NAME LIKE '%'+ISNULL(@IN_PRODUCT_NAME,'')+'%')
                --AND(A.CLASS_TYPE1 = @IN_CLASS_TYPE1 OR @IN_CLASS_TYPE1 IS NULL OR @IN_CLASS_TYPE1 = '')
                AND(B.START_DATE BETWEEN ISNULL(@IN_START_DATE1,0) AND ISNULL(@IN_START_DATE2,99999999))
                AND(B.FACT_MONEY BETWEEN ISNULL(@IN_FACT_MONEY1,0) AND ISNULL(@IN_FACT_MONEY2,9999999999999.0))
                AND(B.SUMMARY LIKE '%'+@IN_SUMMARY+'%' OR B.PRODUCT_INFO LIKE '%'+@IN_SUMMARY+'%')
                AND(@V_SW20409 = 0 OR @V_SW20409 IS NULL OR A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCTS))
    END
    ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
    BEGIN
        INSERT INTO #TMP_PRODUCT_INFOREPOS(PRODUCT_ID,PRODUCT_CODE,PRODUCT_NAME)
            SELECT A.PRODUCT_ID,B.PRODUCT_CODE,B.PRODUCT_NAME
            FROM EFCRM..TPRODUCT A, INTRUST..TPRODUCT B
            WHERE A.PRODUCT_ID = B.PRODUCT_ID
                AND(A.PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID IS NULL OR @IN_PRODUCT_ID = 0)
                AND(B.PRODUCT_CODE = @IN_PRODUCT_CODE OR @IN_PRODUCT_CODE IS NULL OR @IN_PRODUCT_CODE = '')
                AND(B.PRODUCT_NAME LIKE '%'+ISNULL(@IN_PRODUCT_NAME,'')+'%')
                --AND(A.CLASS_TYPE1 = @IN_CLASS_TYPE1 OR @IN_CLASS_TYPE1 IS NULL OR @IN_CLASS_TYPE1 = '')
                AND(B.START_DATE BETWEEN ISNULL(@IN_START_DATE1,0) AND ISNULL(@IN_START_DATE2,99999999))
                AND(B.FACT_MONEY BETWEEN ISNULL(@IN_FACT_MONEY1,0) AND ISNULL(@IN_FACT_MONEY2,9999999999999.0))
                AND(B.SUMMARY LIKE '%'+@IN_SUMMARY+'%' OR B.PRODUCT_INFO LIKE '%'+@IN_SUMMARY+'%')
                AND(@V_SW20409 = 0 OR @V_SW20409 IS NULL OR A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCTS))
    END
    SELECT A.*, B.FIELD_NAME, C.EXTEND_VALUE
    FROM #TMP_PRODUCT_INFOREPOS A, TEXTENDDEFINE B, TEXTENDINFO C
    WHERE A.PRODUCT_ID = C.KEY_VALUE AND B.EXTEND_NO = C.EXTEND_NO AND B.TABLE_NAME = 'TPRODUCT'
    ORDER BY A.PRODUCT_ID, B.EXTEND_NO
GO
