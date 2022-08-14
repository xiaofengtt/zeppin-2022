USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCONTRACT_ALL @IN_BOOK_CODE           INT,                        --账套
                                        @IN_PRODUCT_ID          INT,                        --产品ID
                                        @IN_CONTRACT_BH         NVARCHAR(16),               --合同编号
                                        @IN_CUST_NAME           NVARCHAR(100),              --客户名称
                                        @IN_CARD_ID             NVARCHAR(40),               --证件号码
                                        @IN_INPUT_MAN           INT,                        --操作员
                                        @IN_ONLY_THISPRODUCT    INT = NULL,                 --
                                        @IN_CUST_TYPE           INT = NULL,                 --客户类别
                                        @IN_PROV_LEVEL          NVARCHAR(10) = NULL,        --收益级别
                                        @IN_CUST_NO             NVARCHAR(8) = '',           --客户编号
                                        @IN_MIN_RG_MONEY        DECIMAL(16,2) = NULL,       --认购金额下限
                                        @IN_MAX_RG_MONEY        DECIMAL(16,2) = NULL,       --认购金额上限
                                        @IN_PRODUCT_NAME        NVARCHAR(100) = NULL,       --产品名称
                                        @IN_CITY_NAME           NVARCHAR(100) = NULL,       --推介地
                                        @IN_SERVICE_MAN         INT = NULL,                 --合同维护人员
                                        @IN_CONTRACT_SUB_BH     NVARCHAR(50) = NULL,        --实际合同编号
                                        @IN_CELL_FLAG           INT = 1,                    --模式： 1 原来模式 2 产品单元模式
                                        @IN_CELL_ID             INT = 0,                    --单元ID
                                        @IN_DEPART_ID           INT = 0,                    --设计部门
                                        @IN_QS_DATE_START       INT = 0,                    --签署日期区间下限
                                        @IN_QS_DATE_END         INT = 0,                    --签署日期区间上限
                                        @IN_SUB_PRODUCT_ID      INT = 0,                    --子产品ID   20111112  LUOHH
                                        @IN_ADMIN_MANAGER       NVARCHAR(120) = ''          --执行经理       
WITH ENCRYPTION
AS
    --20409: The Products can be accessed. START
    DECLARE @V_SW20409 INT
    DECLARE @V_ZXJLCKHT01  INT
    --执行经理是否有权限查看所属产品下非其录入的信托合同：1 是 2否  默认 2否
    SELECT @V_ZXJLCKHT01 = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE ='ZXJLCKHT01'
    IF ISNULL(@V_ZXJLCKHT01,0) = 0
        SET @V_ZXJLCKHT01 = 2   
        
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
                    OR ADMIN_MANAGER IN(SELECT OP_CODE FROM INTRUST..TOPCUSTRIGHT WHERE ENABLE_OP_CODE = @IN_INPUT_MAN))
                AND (ISNULL(@IN_PRODUCT_NAME,'') ='' OR PRODUCT_NAME LIKE '%'+ISNULL(@IN_PRODUCT_NAME,'')+'%')
    --20409: The Products can be accessed. END
    DECLARE @V_IS_FLAG INT
    
    --能够访问所有认购信息
    SELECT @V_IS_FLAG = 0
    IF EXISTS(SELECT 1 FROM TOPRIGHT WHERE OP_CODE = @IN_INPUT_MAN AND MENU_ID ='20409' AND FUNC_ID = 2049906)
        SET @V_IS_FLAG = 1
    IF ISNULL(@IN_INPUT_MAN,0) = 0
        SET @V_IS_FLAG = 1
    
    CREATE TABLE #TEMPCP(
        PRODUCT_ID   INT,
        PRODUCT_CODE NVARCHAR(6)
    )
    
    --选择要查询的产品
    IF @IN_CELL_FLAG = 1
    BEGIN
        INSERT INTO #TEMPCP(PRODUCT_ID,PRODUCT_CODE)
            SELECT PRODUCT_ID,PRODUCT_CODE 
                FROM INTRUST..TPRODUCT
                WHERE BOOK_CODE = @IN_BOOK_CODE AND (PRODUCT_ID = @IN_PRODUCT_ID OR (ISNULL(@IN_PRODUCT_ID,0) = 0)) 
                      AND (DEPART_ID = @IN_DEPART_ID OR ISNULL(@IN_DEPART_ID,0) = 0)
                      AND (ISNULL(@IN_PRODUCT_NAME,'') ='' OR PRODUCT_NAME LIKE '%'+ISNULL(@IN_PRODUCT_NAME,'')+'%')
    END
    ELSE
    BEGIN
        CREATE TABLE #CELL_ALL(   
            CELL_ID      INT,
            CELL_CODE    NVARCHAR(10),
            CELL_NAME    NVARCHAR(60),
            CELL_TYPE    INT,
            PRODUCT_ID   INT,
            PRODUCT_CODE NVARCHAR(6),
            PRODUCT_NAME NVARCHAR(60)
        )

        INSERT INTO #CELL_ALL(CELL_ID,CELL_CODE,CELL_NAME,PRODUCT_ID,PRODUCT_CODE,PRODUCT_NAME,CELL_TYPE)
            SELECT SERIAL_NO,CELL_CODE,CELL_NAME,PRODUCT_ID,PRODUCT_CODE,PRODUCT_NAME,CELL_TYPE 
             FROM INTRUST..TPRODUCT_CELL 
             WHERE SERIAL_NO = @IN_CELL_ID

        IF @@ROWCOUNT > 0
            INSERT INTO #CELL_ALL(CELL_ID,CELL_CODE,CELL_NAME,CELL_TYPE,PRODUCT_ID,PRODUCT_CODE,PRODUCT_NAME)
                SELECT A.SUB_CELL_ID,C.CELL_CODE,C.CELL_NAME,C.CELL_TYPE,C.PRODUCT_ID,C.PRODUCT_CODE,C.PRODUCT_NAME
                    FROM  INTRUST..TPRODUCT_CELL_DETAIL A,#CELL_ALL B,INTRUST..TPRODUCT_CELL C
                    WHERE A.DF_SERIAL_NO = B.CELL_ID AND A.SUB_CELL_ID = C.SERIAL_NO AND A.SUB_CELL_ID NOT IN(SELECT CELL_ID FROM #CELL_ALL) 
                    
        INSERT INTO #TEMPCP 
            SELECT A.PRODUCT_ID,A.PRODUCT_CODE 
                FROM #CELL_ALL A,INTRUST..TPRODUCT B
                WHERE A.CELL_TYPE = 1 AND A.PRODUCT_ID = B.PRODUCT_ID AND (B.DEPART_ID = @IN_DEPART_ID OR ISNULL(@IN_DEPART_ID,0) = 0)
                GROUP BY A.PRODUCT_ID,A.PRODUCT_CODE
    END

    SELECT A.SERIAL_NO,A.CONTRACT_SUB_BH,A.CHECK_FLAG,A.HT_STATUS,A.CONTRACT_BH,A.PRODUCT_ID,A.PRODUCT_NAME,
           A.CUST_ID,A.RG_MONEY,A.QS_DATE,A.HT_STATUS_NAME,A.TRANS_FLAG_NAME,
           B.CUST_NO,B.CUST_NAME,B.CARD_ID,C.LIST_NAME,C.LIST_NAME AS SUB_PRODUCT_NAME,A.RG_FEE_MONEY,
		   A.INPUT_MAN,CONVERT(INT,CONVERT(NVARCHAR,A.INPUT_TIME,112)) INPUT_TIME,A.CHECK_MAN,CONVERT(INT,CONVERT(NVARCHAR,A.CHECK_TIME,112)) CHECK_TIME
        FROM INTRUST..TCONTRACT A LEFT JOIN INTRUST..TSUBPRODUCT C ON (A.SUB_PRODUCT_ID =C.SUB_PRODUCT_ID),INTRUST..TCUSTOMERINFO B,INTRUST..TPRODUCT D,TOPERATOR E
            WHERE A.CUST_ID = B.CUST_ID AND A.BOOK_CODE = @IN_BOOK_CODE
              AND (A.CONTRACT_BH LIKE '%'+@IN_CONTRACT_BH+'%' OR ISNULL(@IN_CONTRACT_BH,'') = '')
              AND EXISTS(SELECT 1 FROM #TEMPCP C WHERE A.PRODUCT_ID = C.PRODUCT_ID)
              AND (A.LINK_MAN = @IN_INPUT_MAN OR A.INPUT_MAN = @IN_INPUT_MAN OR A.SERVICE_MAN = @IN_INPUT_MAN OR @V_IS_FLAG = 1 OR
                       A.INPUT_MAN IN(SELECT OP_CODE FROM INTRUST..TOPCUSTRIGHT WHERE ENABLE_OP_CODE = @IN_INPUT_MAN))
              AND (B.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%' OR ISNULL(@IN_CUST_NAME,'') = '')
              AND (A.CONTRACT_SUB_BH LIKE '%'+@IN_CONTRACT_SUB_BH+'%' OR ISNULL(@IN_CONTRACT_SUB_BH,'') = '')
              AND (B.CARD_ID LIKE '%'+@IN_CARD_ID+'%' OR ISNULL(@IN_CARD_ID,'') = '')
              AND (B.CUST_TYPE = @IN_CUST_TYPE OR ISNULL(@IN_CUST_TYPE,0) = 0)
              AND (B.CUST_ID IN (SELECT CUST_ID FROM INTRUST..TBENIFITOR WHERE PROV_LEVEL = @IN_PROV_LEVEL) OR ISNULL(@IN_PROV_LEVEL,'') = '')
              AND (B.CUST_NO LIKE '%'+@IN_CUST_NO + '%' OR ISNULL(@IN_CUST_NO,'') = '')
              AND  A.PRODUCT_ID = D.PRODUCT_ID
              AND (A.LINK_MAN = @IN_INPUT_MAN OR A.INPUT_MAN = @IN_INPUT_MAN OR A.SERVICE_MAN = @IN_INPUT_MAN OR @V_IS_FLAG = 1
                        OR B.SERVICE_MAN = @IN_INPUT_MAN OR (@V_ZXJLCKHT01 = 1 AND (D.ADMIN_MANAGER = @IN_INPUT_MAN OR D.ADMIN_MANAGER2 =@IN_INPUT_MAN)))
              AND ((A.RG_MONEY BETWEEN @IN_MIN_RG_MONEY AND @IN_MAX_RG_MONEY) OR
                      (ISNULL(@IN_MIN_RG_MONEY,0) = 0 AND RG_MONEY <= @IN_MAX_RG_MONEY) OR
                      (ISNULL(@IN_MAX_RG_MONEY,0) = 0 AND RG_MONEY >= @IN_MIN_RG_MONEY) OR
                      (ISNULL(@IN_MIN_RG_MONEY,0) = 0 AND ISNULL(@IN_MAX_RG_MONEY,0) = 0))
              AND (A.CITY_NAME LIKE '%'+@IN_CITY_NAME+'%' OR ISNULL(@IN_CITY_NAME,'') = '')
              AND (A.SERVICE_MAN=@IN_SERVICE_MAN OR ISNULL(@IN_SERVICE_MAN,0) = 0)
              AND (ISNULL(@IN_SUB_PRODUCT_ID,0) =0 OR A.SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID)
              AND ((A.QS_DATE BETWEEN ISNULL(@IN_QS_DATE_START,0) AND ISNULL(@IN_QS_DATE_END,20991231)) OR
                      (ISNULL(@IN_QS_DATE_START,0) = 0 AND ISNULL(@IN_QS_DATE_END,0) = 0))
              AND D.ADMIN_MANAGER = E.OP_CODE AND (E.OP_NAME LIKE '%' + @IN_ADMIN_MANAGER + '%' OR ISNULL(@IN_ADMIN_MANAGER,'') = '')
        ORDER BY A.PRODUCT_ID,A.CONTRACT_BH
GO
