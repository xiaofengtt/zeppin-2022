USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_STAT_TPRODUCT_GUOLIAN_END
							@IN_INPUT_MAN           INTEGER                 --操作员
										
WITH ENCRYPTION
AS
    
    --序号	项目名称	项目投向	运用方式	托管行	项目经理	成立时间	到期日
    --	发行规模	发行期限	年化收益率		分配方式
	SELECT A.PRODUCT_ID,PRODUCT_NAME,INTRUST_TYPE2_NAME,NATRUST_TYPE_NAME,TG_BANK_NAME,B.OP_NAME SERVICE_MAN,START_DATE,END_DATE,
		FACT_MONEY, CASE VALID_PERIOD WHEN 0 THEN '' ELSE CAST(VALID_PERIOD AS VARCHAR)+ CASE PERIOD_UNIT WHEN 1 THEN '天' WHEN 2 THEN '月' WHEN 3 THEN '年' END END VALID_PERIOD1,
		CAST(CAST(ISNULL(EXP_RATE1,0)*100 AS NUMERIC(6,2)) AS VARCHAR)+'%-'+CAST(CAST(ISNULL(EXP_RATE2,0)*100 AS NUMERIC(6,2)) AS VARCHAR)+'%' EXP_RATE,CAST(BEN_PERIOD AS VARCHAR)+'月' BEN_PERIOD
		FROM INTRUST..TPRODUCT A LEFT JOIN INTRUST..TOPERATOR B ON A.SERVICE_MAN=B.OP_CODE
		WHERE PRODUCT_STATUS='110204'--产品终止
		ORDER BY A.PRODUCT_ID
	

GO
