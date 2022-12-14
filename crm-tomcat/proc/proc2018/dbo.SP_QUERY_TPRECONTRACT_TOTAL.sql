USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TPRECONTRACT_TOTAL    @IN_PREPRODUCT_ID     INT          --预发行产品ID
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    SET @IN_PREPRODUCT_ID = ISNULL(@IN_PREPRODUCT_ID,0)
	DECLARE @PRE_END_DATE INT 	--预约截止日期
	DECLARE @V_MICRO_NUM INT 	--300W以下预约总份数
	DECLARE @V_PRODUCT_TOTAL_MONEY DECIMAL(16,3) --产品总规模
    DECLARE @V_HUGE_MONEY DEC(16,3) --大额预约金额
    DECLARE @V_EXPRE_END_TIME DATETIME --预约截止时间
    DECLARE @V_PRE_VALID_DAYS INT   --预约有效天数
	SELECT @PRE_END_DATE = PRE_END_DATE, @V_PRODUCT_TOTAL_MONEY = ISNULL(PRE_MONEY,0), @V_HUGE_MONEY = HUGE_MONEY
	    , @V_EXPRE_END_TIME=EXPRE_END_TIME, @V_PRE_VALID_DAYS=PRE_VALID_DAYS
		FROM TPREPRODUCT 
            WHERE PREPRODUCT_ID = @IN_PREPRODUCT_ID
	
	SELECT @V_MICRO_NUM = ISNULL(SUM(PRE_NUM),0)  
		FROM TPRECONTRACT 
		WHERE PRE_STATUS IN ('111301','111302') AND PRE_MONEY < 3000000  AND PREPRODUCT_ID = @IN_PREPRODUCT_ID
		
	--统计结果	
    SELECT @V_MICRO_NUM AS PRE_MICRO_NUM,ISNULL(SUM(PRE_NUM),0) AS PRE_TOTAL_NUM,ISNULL(SUM(PRE_MONEY),0) AS PRE_TOTAL_MONEY,
		@PRE_END_DATE AS PRE_END_DATE,@V_PRODUCT_TOTAL_MONEY AS PRODUCT_TOTAL_MONEY,(@V_PRODUCT_TOTAL_MONEY - ISNULL(SUM(PRE_MONEY),0)) AS PRODUCT_SY_MONEY,
        @V_HUGE_MONEY AS HUGE_MONEY, @V_EXPRE_END_TIME AS EXPRE_END_TIME, @V_PRE_VALID_DAYS AS PRE_VALID_DAYS
		FROM TPRECONTRACT 
		WHERE PRE_STATUS IN (111301,111302) AND PREPRODUCT_ID = @IN_PREPRODUCT_ID

GO
