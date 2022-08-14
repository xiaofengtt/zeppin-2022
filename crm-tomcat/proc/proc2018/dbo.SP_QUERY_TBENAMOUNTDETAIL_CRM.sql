﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TBENAMOUNTDETAIL_CRM @IN_SERIAL_NO        INT,                --序号
											   @IN_PRODUCT_ID       INT,                --产品ID
											   @IN_CONTRACT_BH      NVARCHAR(16),       --合同编号
											   @IN_CUST_NAME        NVARCHAR(100),      --客户名称
											   @IN_CHG_TYPE         INT,                --份额变动业务类别1认购2申购3赎回4份额转增
											   @IN_START_DATE       INT,                --起始日期
											   @IN_END_DATE         INT,                --终止日期
											   @IN_INPUT_MAN        INT,                --操作员
											   @IN_JK_TYPE          NVARCHAR(10) = '',  --@IN_CHG_TYPE = 4时有效（111450、111451）
											   @IN_SUB_PRODUCT_ID   INT = 0,            --子产品ID   20111112  LUOHH
											   @IN_PRINT_FLAG       INT = 0,            --打印标志 1已打印 2未打印 0全部
											   @IN_PRODUCT_NAME     NVARCHAR(100)=''    --产品名称
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_USER_ID INT,@V_SX_DATE INT

    SET @IN_START_DATE =ISNULL(@IN_START_DATE,0)
    IF ISNULL(@IN_END_DATE,0) =0
        SET @IN_END_DATE =20991231

    SELECT A.[SERIAL_NO]
			  ,A.[PRODUCT_ID]
			  ,A.[CONTRACT_BH]
			  ,A.[CUST_ID]
			  ,A.[CHG_TYPE]
			  ,A.[CHG_TYPE_NAME]
			  ,A.[APPL_AMOUNT]
			  ,A.[FEE_RATE]
			  ,A.[FEE_MONEY]
			  ,A.[PRICE]
			  ,A.[CHG_MONEY]
			  ,A.[CHG_AMOUNT]
			  ,A.[AFTER_MONEY]
			  ,A.[AFTER_AMOUNT]
			  ,A.[SQ_DATE]
			  ,A.[DZ_DATE]
			  ,A.[HK_DATE]
			  ,A.[CONFIRM_DATE]
			  ,A.[JK_TYPE]
			  ,A.[JK_TYPE_NAME]
			  ,A.[INPUT_MAN]
			  ,A.[INPUT_TIME]
			  ,A.[LIST_ID]
			  ,A.[DF_SERIAL_NO]
			  ,A.[PRINT_FLAG]
			  ,A.[SHARE_MONEY]
			  ,A.[FDYJ_SHARE_MONEY]
			  ,A.[PRINT_TIMES]
			  ,B.CUST_NAME, C.PRODUCT_NAME, D.CONTRACT_SUB_BH, Z.BEN_START_DATE, C.START_DATE,B.CARD_ID AS CARD_ID,
            CASE CHG_TYPE WHEN 1 THEN ISNULL(CHG_MONEY,0)
                          WHEN 2 THEN ISNULL(AFTER_MONEY,0) - ISNULL(CHG_MONEY,0)
                          WHEN 3 THEN ISNULL(AFTER_MONEY,0) + ISNULL(CHG_MONEY,0)
                          WHEN 4 THEN ISNULL(AFTER_MONEY,0) - ISNULL(CHG_MONEY,0)
                            ELSE 0 END AS BEGIN_MONEY,
            CASE CHG_TYPE WHEN 1 THEN ISNULL(CHG_AMOUNT,0)
                          WHEN 2 THEN ISNULL(AFTER_AMOUNT,0) - ISNULL(CHG_AMOUNT,0)
                          WHEN 3 THEN ISNULL(AFTER_AMOUNT,0) + ISNULL(CHG_AMOUNT,0)
                          WHEN 4 THEN ISNULL(AFTER_AMOUNT,0) - ISNULL(CHG_AMOUNT,0)
                            ELSE 0 END AS BEGIN_AMOUNT,
            CONVERT(DEC(16,3),0.0) AS RG_BOND,CONVERT(INT,0) AS OPEN_DATE,CONVERT(INT,0) AS LAST_OPEN_DATE,C.INTRUST_FLAG1,E.LIST_NAME,E.LIST_NAME AS SUB_PRODUCT_NAME
        INTO #TMP_QUERY_TBENAMOUNTDETAIL
        FROM INTRUST.dbo.TBENAMOUNTDETAIL A, INTRUST.dbo.TCUSTOMERINFO B, INTRUST.dbo.TPRODUCT C,
            (SELECT O.PRODUCT_ID, O.CONTRACT_BH, O.CUST_ID, MIN(BEN_DATE) AS BEN_START_DATE
                FROM INTRUST.dbo.TBENIFITOR O GROUP BY O.PRODUCT_ID, O.CONTRACT_BH, O.CUST_ID
            ) Z,INTRUST.dbo.TCONTRACT D LEFT JOIN INTRUST.dbo.TSUBPRODUCT E ON (D.SUB_PRODUCT_ID = E.SUB_PRODUCT_ID)
        WHERE A.CUST_ID = B.CUST_ID AND A.PRODUCT_ID = C.PRODUCT_ID
           AND A.PRODUCT_ID = D.PRODUCT_ID AND A.CONTRACT_BH = D.CONTRACT_BH
           AND A.PRODUCT_ID = Z.PRODUCT_ID AND A.CONTRACT_BH = Z.CONTRACT_BH AND A.CUST_ID = Z.CUST_ID
           AND(ISNULL(@IN_SERIAL_NO,0) =0 OR A.SERIAL_NO = @IN_SERIAL_NO)
           AND(ISNULL(@IN_PRODUCT_ID,0) =0 OR A.PRODUCT_ID = @IN_PRODUCT_ID)
           AND(ISNULL(@IN_CONTRACT_BH,'') ='' OR A.CONTRACT_BH = @IN_CONTRACT_BH)
           AND(ISNULL(@IN_CUST_NAME,'') ='' OR B.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%')
           AND(ISNULL(@IN_CHG_TYPE,0) =0 OR A.CHG_TYPE = @IN_CHG_TYPE)
           AND(A.SQ_DATE BETWEEN @IN_START_DATE AND @IN_END_DATE)
           AND(A.JK_TYPE = @IN_JK_TYPE OR ISNULL(@IN_JK_TYPE,'') = '')
           AND (ISNULL(@IN_SUB_PRODUCT_ID,0) =0 OR D.SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID)
           AND(ISNULL(@IN_PRINT_FLAG,0)=0 OR A.PRINT_FLAG = @IN_PRINT_FLAG)
           AND (C.PRODUCT_NAME LIKE '%'+@IN_PRODUCT_NAME+'%' OR ISNULL(@IN_PRODUCT_NAME,'')='')
        ORDER BY A.SERIAL_NO

    --取发行期利息
    UPDATE #TMP_QUERY_TBENAMOUNTDETAIL
        SET RG_BOND = B.RG_BOND
        FROM #TMP_QUERY_TBENAMOUNTDETAIL A,
            ( SELECT C.SERIAL_NO, ISNULL(SUM(SY_MONEY),0) AS RG_BOND FROM INTRUST.dbo.TDEPLOY A, INTRUST.dbo.TBENIFITOR B, INTRUST.dbo.TMONEYDETAIL C
                WHERE A.PRODUCT_ID = B.PRODUCT_ID AND A.CONTRACT_BH = B.CONTRACT_BH AND A.CUST_ID = B.CUST_ID AND A.LIST_ID = B.LIST_ID
                    AND A.PRODUCT_ID = C.PRODUCT_ID AND A.CONTRACT_BH = C.CONTRACT_BH AND A.CUST_ID = C.CUST_ID AND A.LIST_ID = C.LIST_ID
                    AND A.SY_TYPE = '111601'
                GROUP BY C.SERIAL_NO) B
        WHERE A.DF_SERIAL_NO = B.SERIAL_NO AND A.CHG_TYPE = 1

    --取集合产品，申购、赎回的本次开放日
    IF ISNULL(@IN_SERIAL_NO,0) <> 0
    BEGIN
        UPDATE #TMP_QUERY_TBENAMOUNTDETAIL
            SET OPEN_DATE = C.OPEN_DATE
            FROM (SELECT MIN(B.OPEN_DATE) AS OPEN_DATE FROM #TMP_QUERY_TBENAMOUNTDETAIL A,INTRUST..TOPENDATE B
                WHERE A.PRODUCT_ID = B.PRODUCT_ID
                AND A.SERIAL_NO = @IN_SERIAL_NO
                AND A.CHG_TYPE IN (2,3)
                AND B.OPEN_DATE >= A.HK_DATE) C
    END
    --输出
    SELECT * FROM #TMP_QUERY_TBENAMOUNTDETAIL
GO