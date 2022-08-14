﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_PRODUCT_RELATED @IN_PREPRODUCT_ID INT,
                                          @IN_PRODUCT_ID    INT,
                                          @IN_INPUT_MAN     INT
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    SET @IN_PREPRODUCT_ID = ISNULL(@IN_PREPRODUCT_ID,0)
    SET @IN_PRODUCT_ID = ISNULL(@IN_PRODUCT_ID,0)

    DECLARE @V_SW20409 INT, @IN_BOOK_CODE INT
    SET @IN_BOOK_CODE = 1
    SELECT @V_SW20409 = VALUE FROM INTRUST..TSYSCONTROL WHERE FLAG_TYPE = 'SW20409'
    DECLARE @V_PRODUCTS TABLE(PRODUCT_ID INT)
    IF @V_SW20409 = 1
        INSERT INTO @V_PRODUCTS
            SELECT PRODUCT_ID FROM INTRUST..TPRODUCT WHERE BOOK_CODE = @IN_BOOK_CODE
                AND(INTRUST_FLAG1 IN ( SELECT -(FUNC_ID-2040900)%2+2 FROM INTRUST..TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID >= 2040900 AND FUNC_ID <= 2040999 AND OP_CODE = @IN_INPUT_MAN )
                    OR PRODUCT_ID IN ( SELECT FUNC_ID FROM INTRUST..TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID < 2040900 AND OP_CODE = @IN_INPUT_MAN)
                    OR ADMIN_MANAGER = @IN_INPUT_MAN OR INPUT_MAN = @IN_INPUT_MAN OR ISNULL(@IN_INPUT_MAN,0)=0
                    OR ADMIN_MANAGER IN(SELECT OP_CODE FROM INTRUST..TOPCUSTRIGHT WHERE ENABLE_OP_CODE = @IN_INPUT_MAN)
                    )
    DECLARE @V_PRODUCT_NAME NVARCHAR(200), @V_ADMIN NVARCHAR(30)
    DECLARE @VT_PRODUCTS TABLE(
        PRODUCT_ID      INT,            --产品ID
        PREPRODUCT_ID   INT,            --预发行产品ID
        PRODUCT_NAME    NVARCHAR(200),  --产品名称
        PRODUCT_NAME_A  NVARCHAR(200),
        ADMIN_MANAGER   NVARCHAR(30),   --项目负责人
        INPUT_TIME      DATETIME,       --录入时间
        STATUS_NAME     NVARCHAR(30),   --产品状态
        DATA_TYPE       INT,            --标识数据类别用于将相似度大的排在前面
        CAN_ACCESS      INT             --权限 1对当前产品有权限页面可提供查看链接 2无权限
    )
    IF EXISTS(SELECT 1 FROM INTRUST..TPRODUCT WHERE PRODUCT_ID = @IN_PRODUCT_ID)
        SELECT @V_PRODUCT_NAME = PRODUCT_NAME, @V_ADMIN = B.OP_NAME
            FROM INTRUST..TPRODUCT A, INTRUST..TOPERATOR B WHERE A.ADMIN_MANAGER = B.OP_CODE AND A.PRODUCT_ID = @IN_PRODUCT_ID
    ELSE IF EXISTS(SELECT 1 FROM TPREPRODUCT WHERE PREPRODUCT_ID = @IN_PREPRODUCT_ID)
        SELECT @V_PRODUCT_NAME = PREPRODUCT_NAME, @V_ADMIN = ADMIN_MANAGER FROM TPREPRODUCT WHERE PREPRODUCT_ID = @IN_PREPRODUCT_ID
    ELSE
    BEGIN
        SELECT TOP 4 PRODUCT_ID,PREPRODUCT_ID,PRODUCT_NAME,ADMIN_MANAGER,INPUT_TIME,STATUS_NAME FROM @VT_PRODUCTS
        RETURN 100
    END
    SET @V_PRODUCT_NAME = dbo.DELSPLITCHAR(@V_PRODUCT_NAME)

    INSERT INTO @VT_PRODUCTS(PRODUCT_ID,PREPRODUCT_ID,PRODUCT_NAME,ADMIN_MANAGER,INPUT_TIME,STATUS_NAME)
        SELECT BIND_PRODUCT_ID,PREPRODUCT_ID,PREPRODUCT_NAME,ADMIN_MANAGER,INPUT_TIME,N'预发行产品'
            FROM TPREPRODUCT
    INSERT INTO @VT_PRODUCTS(PRODUCT_ID,PREPRODUCT_ID,PRODUCT_NAME,ADMIN_MANAGER,INPUT_TIME,STATUS_NAME)
        SELECT A.PRODUCT_ID,NULL,A.PRODUCT_NAME,B.OP_NAME,A.INPUT_TIME,A.PRODUCT_STATUS_NAME
            FROM INTRUST..TPRODUCT A, INTRUST..TOPERATOR B
            WHERE A.ADMIN_MANAGER = B.OP_CODE
                AND(A.PRODUCT_STATUS IN ('110202','110203')) AND A.INTRUST_FLAG1 = 2
                AND NOT EXISTS(SELECT 1 FROM @VT_PRODUCTS Z WHERE A.PRODUCT_ID = Z.PRODUCT_ID)
    UPDATE @VT_PRODUCTS SET PRODUCT_NAME_A = PRODUCT_NAME
    UPDATE @VT_PRODUCTS SET PRODUCT_NAME_A = dbo.DELSPLITCHAR(PRODUCT_NAME_A)

    UPDATE @VT_PRODUCTS SET DATA_TYPE = 2 WHERE PRODUCT_NAME_A = PRODUCT_NAME
    UPDATE @VT_PRODUCTS SET DATA_TYPE = 1 WHERE PRODUCT_NAME_A <> PRODUCT_NAME
    UPDATE @VT_PRODUCTS SET DATA_TYPE = 0 WHERE PRODUCT_NAME_A = @V_PRODUCT_NAME

    UPDATE @VT_PRODUCTS SET CAN_ACCESS = 1 WHERE PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCTS) OR PRODUCT_ID IS NULL
    UPDATE @VT_PRODUCTS SET CAN_ACCESS = 2 WHERE CAN_ACCESS IS NULL
    IF @IN_PREPRODUCT_ID <> 0
        DELETE FROM @VT_PRODUCTS WHERE PREPRODUCT_ID = @IN_PREPRODUCT_ID
    IF @IN_PRODUCT_ID <> 0
        DELETE FROM @VT_PRODUCTS WHERE PRODUCT_ID = @IN_PRODUCT_ID
    SELECT TOP 4 PRODUCT_ID,PREPRODUCT_ID,PRODUCT_NAME,ADMIN_MANAGER,INPUT_TIME,STATUS_NAME,CAN_ACCESS
        FROM @VT_PRODUCTS
        WHERE CAN_ACCESS = 1
            AND(PRODUCT_NAME_A = @V_PRODUCT_NAME OR ADMIN_MANAGER = @V_ADMIN)
        ORDER BY DATA_TYPE,INPUT_TIME DESC
GO
