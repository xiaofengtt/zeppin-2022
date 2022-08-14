USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TDEPLOY_OUTPUT_ALL @IN_BOOK_CODE          INTEGER,                    --账套
                                             @IN_PRODUCT_ID         INTEGER,                    --产品ID
                                             @IN_BANK_ID            VARCHAR(10),                --银行
                                             @IN_JK_TYPE            VARCHAR(10)     = '',       --付款方式
                                             @IN_LIST_ID            INTEGER         = NULL,     --收益序号
                                             @IN_CONTRACT_BH        VARCHAR(16)     = NULL,     --合同编号
                                             @IN_PROV_FLAG          INT             = NULL,     --受益优先级
                                             @IN_PROV_LEVEL         VARCHAR(10)     = NULL,     --收益级别
                                             @IN_INPUT_MAN          INTEGER         = NULL,     --操作员
                                             @IN_SY_TYPE1           VARCHAR(10)     = NULL,     --发行期利息
                                             @IN_SY_TYPE2           VARCHAR(10)     = NULL,     --中间收益
                                             @IN_SY_TYPE3           VARCHAR(10)     = NULL,     --到期收益
                                             @IN_SY_TYPE4           VARCHAR(10)     = NULL,     --兑付期利息
                                             @IN_SY_TYPE5           VARCHAR(10)     = NULL,     --到期本金
                                             @IN_SY_TYPE6           VARCHAR(10)     = NULL,     --赎回金额
                                             @IN_SY_TYPE9           VARCHAR(10)     = NULL,     --申购期利息
                                             @IN_SY_DATE1           INT             = NULL,     --发行期利息计算日期
                                             @IN_SY_DATE235         INT             = NULL,     --收益日期（中间收益、到期收益、到期本金）
                                             @IN_SY_DATE4           INT             = NULL,     --兑付期利息计算日期
                                             @IN_SY_DATE6           INT             = NULL,     --赎回日期
                                             @IN_SY_DATE9           INT             = NULL,     --申购期利息计算日期 
                                             @IN_CUST_NAME          VARCHAR(100)    = NULL,     --客户名称
                                             @IN_FP_FLAG            INTEGER         = NULL,     --分配标志
                                             @IN_CONTRACT_SUB_BH    VARCHAR(50)     = NULL,     --实际合同编号
                                             @IN_SUB_PRODUCT_ID     INT             = 0,        --子产品ID   20111112  LUOHH
                                             @IN_BONUS_FLAG         INT             = 0,        --分红方式
                                             @IN_LINK_MAN           INT             = 0         --合同销售人员
WITH ENCRYPTION
AS
    DECLARE @V_SYS_VALUE INT
    
    CREATE TABLE #TMP_TABLE(
        SERIAL_NO         INT,                  --记录号
        CONTRACT_BH       NVARCHAR(16),         --合同编号
        CONTRACT_SUB_BH   NVARCHAR(50),         --合同实际编号
        CUST_ID           INT,                  --客户ID
        CUST_NAME         NVARCHAR(100),        --客户名称
        TO_AMOUNT        DECIMAL(16,2),        --原始受益份额
        CARD_TYPE_NAME    NVARCHAR(30),         --证件类型
        CARD_ID           NVARCHAR(40),         --证件号
        JK_TYPE           NVARCHAR(10),         --缴款类别
        JK_TYPE_NAME      NVARCHAR(30),         --缴款类别名称
        BANK_NAME         NVARCHAR(60),         --受益银行名称
        BANK_ACCT         NVARCHAR(30),         --受益银行账户
        BOND_SUB          DECIMAL(16,2),        --发行期利息
        BOND_PUR          DECIMAL(16,2),        --申购期利息
        GAIN1             DECIMAL(16,2),        --中间收益
        GAIN2             DECIMAL(16,2),        --到期收益
        TO_MONEY2         DECIMAL(16,2),        --到期本金
        BOND2             DECIMAL(16,2),        --兑付期利息
        REDEEM_MONEY      DECIMAL(16,2),        --赎回金额
        TOTAL_MONEY       DECIMAL(16,2),        --收益合计金额
        BOND_TAX          DECIMAL(16,2),        --扣税
        LIST_ID           INT,                  --受益序号
        CUST_ACCT_NAME    NVARCHAR(100),        --客户账户
        PROV_FLAG         INT,                  --受益优先级
        PROV_FLAG_NAME    NVARCHAR(10),         --受益优先级名称
        PROV_LEVEL        NVARCHAR(10),         --收益级别
        PROV_LEVEL_NAME   NVARCHAR(30),         --收益级别名称
        FLAG              INT,                  --0明细，1合计
        SERVICE_MAN       INT,                  --客户经理
        BOND_TAX1         DECIMAL(16,2),        --发行期利息税
        BOND_TAX4         DECIMAL(16,2),        --兑付期利息税
        BOND_TAX9         DECIMAL(16,2)         --申购期利息税
    )
    
    INSERT INTO #TMP_TABLE(SERIAL_NO,CONTRACT_BH,CONTRACT_SUB_BH,CUST_ID,CUST_NAME,TO_AMOUNT,CARD_TYPE_NAME,CARD_ID,JK_TYPE,JK_TYPE_NAME,
                           BANK_NAME,BANK_ACCT,LIST_ID,CUST_ACCT_NAME,PROV_LEVEL,PROV_LEVEL_NAME,FLAG,SERVICE_MAN,PROV_FLAG,PROV_FLAG_NAME)
        SELECT A.SERIAL_NO,A.CONTRACT_BH,C.CONTRACT_SUB_BH,B.CUST_ID,B.CUST_NAME,A.TO_AMOUNT,B.CARD_TYPE_NAME,B.CARD_ID,A.JK_TYPE,A.JK_TYPE_NAME,
               A.BANK_NAME + ISNULL(A.BANK_SUB_NAME,'') AS BANK_NAME,A.BANK_ACCT,A.LIST_ID,A.CUST_ACCT_NAME,A.PROV_LEVEL,A.PROV_LEVEL_NAME,0,B.SERVICE_MAN,A.PROV_FLAG,D.CONTENT
            FROM INTRUST..TBENIFITOR A,INTRUST..TCUSTOMERINFO B,INTRUST..TCONTRACT C,INTRUST..TINTEGERPARAM D
            WHERE A.CUST_ID = B.CUST_ID AND A.PRODUCT_ID = C.PRODUCT_ID AND A.CONTRACT_BH = C.CONTRACT_BH AND
                  A.BOOK_CODE = @IN_BOOK_CODE AND A.PRODUCT_ID = @IN_PRODUCT_ID AND A.PROV_FLAG = D.TYPE_VALUE AND D.TYPE_ID = 3003 AND
                  (A.BANK_ID = @IN_BANK_ID OR ISNULL(@IN_BANK_ID,'') = '') AND
                  (A.JK_TYPE = @IN_JK_TYPE OR ISNULL(@IN_JK_TYPE,'') = '') AND
                  (A.CONTRACT_BH LIKE '%'+@IN_CONTRACT_BH+'%' OR ISNULL(@IN_CONTRACT_BH,'') = '') AND
                  (C.CONTRACT_SUB_BH LIKE '%'+@IN_CONTRACT_SUB_BH+'%' OR ISNULL(@IN_CONTRACT_SUB_BH,'') = '') AND
                  (A.LIST_ID = @IN_LIST_ID OR ISNULL(@IN_LIST_ID,0) = 0) AND
                  (A.PROV_FLAG = @IN_PROV_FLAG OR ISNULL(@IN_PROV_FLAG,0) = 0) AND
                  (A.PROV_LEVEL = @IN_PROV_LEVEL OR ISNULL(@IN_PROV_LEVEL,'') = '') AND
                  (B.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%' OR ISNULL(@IN_CUST_NAME,'') = '') AND
                  (C.SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID OR ISNULL(@IN_SUB_PRODUCT_ID,0) =0) AND
                  (C.BONUS_FLAG = @IN_BONUS_FLAG OR ISNULL(@IN_BONUS_FLAG,0) = 0) AND
                  (C.LINK_MAN = @IN_LINK_MAN OR ISNULL(@IN_LINK_MAN,0) = 0)

    --发行期利息
    IF @IN_SY_TYPE1 = '111601' BEGIN
        UPDATE #TMP_TABLE 
            SET BOND_SUB = B.SY_MONEY,
                BOND_TAX1 = B.BOND_TAX
            FROM #TMP_TABLE A, (SELECT CONTRACT_BH,LIST_ID,ISNULL(SUM(SY_MONEY),0) AS SY_MONEY,ISNULL(SUM(BOND_TAX),0) AS BOND_TAX
                                    FROM INTRUST..TDEPLOY
                                    WHERE PRODUCT_ID = @IN_PRODUCT_ID AND SY_TYPE = '111601' AND 
                                          (SY_DATE = @IN_SY_DATE1 OR ISNULL(@IN_SY_DATE1,0) = 0) AND 
                                          (FP_FLAG = @IN_FP_FLAG OR ISNULL(@IN_FP_FLAG,0) = 0)
                                    GROUP BY CONTRACT_BH,LIST_ID) B
            WHERE A.CONTRACT_BH = B.CONTRACT_BH AND A.LIST_ID = B.LIST_ID
    END
    
    --申购期利息
    IF @IN_SY_TYPE9 = '111609' BEGIN
        UPDATE #TMP_TABLE 
            SET BOND_PUR = B.SY_MONEY,
                BOND_TAX9 = B.BOND_TAX
            FROM #TMP_TABLE A, (SELECT CONTRACT_BH,LIST_ID,ISNULL(SUM(SY_MONEY),0) AS SY_MONEY,ISNULL(SUM(BOND_TAX),0) AS BOND_TAX
                                    FROM INTRUST..TDEPLOY
                                    WHERE PRODUCT_ID = @IN_PRODUCT_ID AND SY_TYPE = '111609' AND 
                                         (SY_DATE = @IN_SY_DATE9 OR ISNULL(@IN_SY_DATE9,0) = 0) AND 
                                         (FP_FLAG = @IN_FP_FLAG OR ISNULL(@IN_FP_FLAG,0) = 0)
                                    GROUP BY CONTRACT_BH,LIST_ID) B
            WHERE A.CONTRACT_BH = B.CONTRACT_BH AND A.LIST_ID = B.LIST_ID
    END
    
    --兑付期利息
    IF @IN_SY_TYPE4 = '111604' BEGIN
        UPDATE #TMP_TABLE 
            SET BOND2 = B.SY_MONEY,
                BOND_TAX4 = B.BOND_TAX
            FROM #TMP_TABLE A, (SELECT CONTRACT_BH,LIST_ID,ISNULL(SUM(SY_MONEY),0) AS SY_MONEY,ISNULL(SUM(BOND_TAX),0) AS BOND_TAX
                                    FROM INTRUST..TDEPLOY
                                    WHERE PRODUCT_ID = @IN_PRODUCT_ID AND SY_TYPE = '111604' AND
                                         (SY_DATE = @IN_SY_DATE4 OR ISNULL(@IN_SY_DATE4,0) = 0) AND
                                         (FP_FLAG = @IN_FP_FLAG OR ISNULL(@IN_FP_FLAG,0) = 0)
                                    GROUP BY CONTRACT_BH,LIST_ID) B
            WHERE A.CONTRACT_BH = B.CONTRACT_BH AND A.LIST_ID = B.LIST_ID
    END
    
    --中间收益
    IF @IN_SY_TYPE2 = '111602' BEGIN
        UPDATE #TMP_TABLE
            SET GAIN1 = B.SY_MONEY
            FROM #TMP_TABLE A, (SELECT CONTRACT_BH,LIST_ID,ISNULL(SUM(SY_MONEY),0) AS SY_MONEY
                                    FROM INTRUST..TDEPLOY
                                    WHERE PRODUCT_ID = @IN_PRODUCT_ID AND SY_TYPE = '111602' AND
                                         (SY_DATE = @IN_SY_DATE235 OR ISNULL(@IN_SY_DATE235,0) = 0) AND
                                         (FP_FLAG = @IN_FP_FLAG OR ISNULL(@IN_FP_FLAG,0) = 0)
                                    GROUP BY CONTRACT_BH,LIST_ID) B
            WHERE A.CONTRACT_BH = B.CONTRACT_BH AND A.LIST_ID = B.LIST_ID
    END
    
    --到期收益
    IF @IN_SY_TYPE3 = '111603' BEGIN
        UPDATE #TMP_TABLE
            SET GAIN2 = B.SY_MONEY
            FROM #TMP_TABLE A, (SELECT CONTRACT_BH,LIST_ID,ISNULL(SUM(SY_MONEY),0) AS SY_MONEY
                                    FROM INTRUST..TDEPLOY
                                    WHERE PRODUCT_ID = @IN_PRODUCT_ID AND SY_TYPE = '111603' AND
                                         (SY_DATE = @IN_SY_DATE235 OR ISNULL(@IN_SY_DATE235,0) = 0 ) AND
                                         (FP_FLAG = @IN_FP_FLAG OR ISNULL(@IN_FP_FLAG,0) = 0)
                                    GROUP BY CONTRACT_BH,LIST_ID) B
            WHERE A.CONTRACT_BH = B.CONTRACT_BH AND A.LIST_ID = B.LIST_ID
    END
    
    --到期本金
    IF @IN_SY_TYPE5 = '111605' BEGIN
        UPDATE #TMP_TABLE
            SET TO_MONEY2 = B.SY_MONEY
            FROM #TMP_TABLE A, (SELECT CONTRACT_BH,LIST_ID,ISNULL(SUM(SY_MONEY),0) AS SY_MONEY
                                    FROM INTRUST..TDEPLOY
                                    WHERE PRODUCT_ID = @IN_PRODUCT_ID AND SY_TYPE = '111605' AND
                                         (SY_DATE = @IN_SY_DATE235 OR ISNULL(@IN_SY_DATE235,0) = 0 ) AND
                                         (FP_FLAG = @IN_FP_FLAG OR ISNULL(@IN_FP_FLAG,0) = 0)
                                    GROUP BY CONTRACT_BH,LIST_ID) B
            WHERE A.CONTRACT_BH = B.CONTRACT_BH AND A.LIST_ID = B.LIST_ID
    END
    
    --赎回金额
    IF @IN_SY_TYPE6 = '111606' BEGIN
        UPDATE #TMP_TABLE
            SET REDEEM_MONEY = B.SY_MONEY
            FROM #TMP_TABLE A, (SELECT CONTRACT_BH,LIST_ID,ISNULL(SUM(SY_MONEY),0) AS SY_MONEY
                                    FROM INTRUST..TDEPLOY
                                    WHERE PRODUCT_ID = @IN_PRODUCT_ID AND SY_TYPE = '111606' AND
                                         (SY_DATE = @IN_SY_DATE6 OR ISNULL(@IN_SY_DATE6,0) = 0 ) AND
                                         (FP_FLAG = @IN_FP_FLAG OR ISNULL(@IN_FP_FLAG,0) = 0)
                                    GROUP BY CONTRACT_BH,LIST_ID) B
            WHERE A.CONTRACT_BH = B.CONTRACT_BH AND A.LIST_ID = B.LIST_ID
    END
    
    UPDATE #TMP_TABLE 
        SET TOTAL_MONEY = ISNULL(BOND_SUB,0) + ISNULL(BOND_PUR,0) + ISNULL(GAIN1,0) + ISNULL(BOND2,0) + ISNULL(GAIN2,0) + ISNULL(TO_MONEY2,0) + ISNULL(REDEEM_MONEY,0),
            BOND_TAX = ISNULL(BOND_TAX1,0) + ISNULL(BOND_TAX4,0) + ISNULL(BOND_TAX9,0)
   
    INSERT INTO #TMP_TABLE (CONTRACT_SUB_BH,TO_AMOUNT,BOND_SUB,BOND_PUR,GAIN1,GAIN2,BOND2,TO_MONEY2,BOND_TAX,TOTAL_MONEY,REDEEM_MONEY,FLAG)
        SELECT '合计',SUM(TO_AMOUNT),SUM(BOND_SUB),SUM(BOND_PUR),SUM(GAIN1),SUM(GAIN2),SUM(BOND2),SUM(TO_MONEY2),SUM(BOND_TAX),SUM(TOTAL_MONEY),SUM(REDEEM_MONEY),1
            FROM #TMP_TABLE
            WHERE TOTAL_MONEY <> 0
            
    UPDATE #TMP_TABLE 
        SET CUST_ACCT_NAME = CUST_NAME 
        WHERE FLAG = 0 AND ISNULL(CUST_ACCT_NAME,'')=''
        
    --如果有客户访问权限控制
    SELECT @V_SYS_VALUE = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = 'CUSTRIGHT'

    SELECT CONTRACT_BH,CONTRACT_SUB_BH,LIST_ID,CUST_NAME,TO_AMOUNT,CARD_TYPE_NAME,
           CASE @V_SYS_VALUE WHEN 1 THEN dbo.ENCRYPTCUSTINFO(SERVICE_MAN,@IN_INPUT_MAN,CARD_ID) ELSE CARD_ID END AS CARD_ID,
           BANK_NAME,BANK_ACCT,CUST_ACCT_NAME,BOND_SUB,BOND_PUR,GAIN1,GAIN2,TO_MONEY2,BOND2,BOND_TAX,REDEEM_MONEY,TOTAL_MONEY,
           CUST_ACCT_NAME,PROV_LEVEL,PROV_LEVEL_NAME,JK_TYPE,JK_TYPE_NAME
        FROM #TMP_TABLE  
        WHERE TOTAL_MONEY <> 0 
        ORDER BY FLAG,CONTRACT_BH,LIST_ID

    RETURN @@ROWCOUNT
GO
