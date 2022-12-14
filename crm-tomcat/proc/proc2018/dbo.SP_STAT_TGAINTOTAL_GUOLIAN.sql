USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_STAT_TGAINTOTAL_GUOLIAN
							@IN_INPUT_MAN           INTEGER                 --操作员
										
WITH ENCRYPTION
AS
    DECLARE @V_FIELD NVARCHAR(40),@V_SHOW_FIELDS NVARCHAR(MAX),@V_SQL NVARCHAR(MAX)
    SET @V_SHOW_FIELDS=''
    
    --建立临时表
	CREATE TABLE #TEMP0908(P_ID INT IDENTITY,
			PRODUCT_CODE NVARCHAR(60),
			PRODUCT_NAME NVARCHAR(60),
			INTRUST_TYPE2_NAME NVARCHAR(60),
			NATRUST_TYPE_NAME NVARCHAR(60),
			TG_BANK_NAME NVARCHAR(60),
			SERVICE_MAN NVARCHAR(60),
			START_DATE NVARCHAR(60),
			END_DATE NVARCHAR(60),
			FACT_END_DATE NVARCHAR(60),
			SY_DATE NVARCHAR(60),
			SY_TYPE_NAME NVARCHAR(60),
			FACT_MONEY NVARCHAR(60),
			VALID_PERIOD NVARCHAR(60),
			EXP_RATE NVARCHAR(60),
			FACT_DATES NVARCHAR(60),
			BEN_PERIOD NVARCHAR(60))
	--表头
	--产品编号	产品名称	项目投向	运用方式	托管行	项目经理	成立时间	到期日
    --	实际到期日（当日不含）	清算分配日	分配情况	分配规模	分配期限	年化收益率		本期实际信托期限	分配周期
	INSERT INTO #TEMP0908(PRODUCT_CODE,PRODUCT_NAME,INTRUST_TYPE2_NAME,NATRUST_TYPE_NAME,TG_BANK_NAME,SERVICE_MAN,START_DATE,END_DATE,
			FACT_END_DATE,SY_DATE,SY_TYPE_NAME,FACT_MONEY,VALID_PERIOD,EXP_RATE,FACT_DATES,BEN_PERIOD)
	VALUES('产品编号','产品名称','项目投向','运用方式','托管行','项目经理','成立时间','到期日',
			'实际到期日（当日不含）','清算分配日','分配情况','分配规模','分配期限','年化收益率','本期实际信托期限','分配周期')
	--表数据
	INSERT INTO #TEMP0908(PRODUCT_CODE,PRODUCT_NAME,INTRUST_TYPE2_NAME,NATRUST_TYPE_NAME,TG_BANK_NAME,SERVICE_MAN,START_DATE,END_DATE,
			FACT_END_DATE,SY_DATE,SY_TYPE_NAME,FACT_MONEY,VALID_PERIOD,EXP_RATE,FACT_DATES,BEN_PERIOD)
	SELECT PRODUCT_CODE,PRODUCT_NAME,INTRUST_TYPE2_NAME,NATRUST_TYPE_NAME,TG_BANK_NAME,C.OP_NAME,START_DATE,END_DATE,
			FACT_END_DATE,SY_DATE,SY_TYPE_NAME,FACT_MONEY,
			CASE VALID_PERIOD WHEN 0 THEN '' ELSE CAST(VALID_PERIOD AS VARCHAR)+ CASE PERIOD_UNIT WHEN 1 THEN '天' WHEN 2 THEN '月' WHEN 3 THEN '年' END END VALID_PERIOD1,
			CAST(CAST(ISNULL(EXP_RATE1,0)*100 AS NUMERIC(6,2)) AS VARCHAR)+'%-'+CAST(CAST(ISNULL(EXP_RATE2,0)*100 AS NUMERIC(6,2)) AS VARCHAR)+'%' EXP_RATE,--CAST(BEN_PERIOD AS VARCHAR)+'月' BEN_PERIOD,
			CAST(dbo.GETDATEDIFF(B.START_DATE,B.FACT_END_DATE) AS VARCHAR)+'天' FACT_DATES,CAST(B.BEN_PERIOD AS VARCHAR)+'月' BEN_PERIOD
		FROM INTRUST..TGAINTOTAL A LEFT JOIN INTRUST..TPRODUCT B ON A.PRODUCT_ID=B.PRODUCT_ID
		LEFT JOIN TOPERATOR C ON C.OP_CODE=B.SERVICE_MAN
		WHERE SY_TYPE<>'111605'
		ORDER BY A.PRODUCT_ID
	--没有设置自定义显示列，则显示全部
    IF NOT EXISTS (SELECT 1 FROM TMENUVIEWDIM WHERE MENU_ID='3804404' AND OP_CODE=@IN_INPUT_MAN) OR
        ISNULL((SELECT VIEWSTR FROM TMENUVIEWDIM WHERE MENU_ID='3804404' AND OP_CODE=@IN_INPUT_MAN), '') = ''
    BEGIN
		SELECT PRODUCT_CODE,PRODUCT_NAME,INTRUST_TYPE2_NAME,NATRUST_TYPE_NAME,TG_BANK_NAME,SERVICE_MAN,START_DATE,END_DATE,
			FACT_END_DATE,SY_DATE,SY_TYPE_NAME,FACT_MONEY,VALID_PERIOD,EXP_RATE,FACT_DATES,BEN_PERIOD
			FROM #TEMP0908 ORDER BY P_ID
		RETURN
    END
    --待显示列:从操作员自定义的表中取出
    SELECT @V_SHOW_FIELDS=VIEWSTR FROM TMENUVIEWDIM WHERE MENU_ID='3804404' AND OP_CODE=@IN_INPUT_MAN
    SET @V_SHOW_FIELDS=REPLACE(@V_SHOW_FIELDS,'$',',')
	IF SUBSTRING(@V_SHOW_FIELDS,LEN(@V_SHOW_FIELDS),LEN(@V_SHOW_FIELDS))=','--去掉最后面的','
		SET @V_SHOW_FIELDS=SUBSTRING(@V_SHOW_FIELDS,1,LEN(@V_SHOW_FIELDS)-1)
    --把待显示列组装出来
    /*DECLARE CURR CURSOR FOR SELECT FIELD FROM TFIELDSDIM WHERE MENU_ID='3804404' ORDER BY PARAM_TYPE_ID 
    OPEN CURR
    FETCH NEXT FROM CURR INTO @V_FIELD
    WHILE @@FETCH_STATUS = 0
    BEGIN     
        IF EXISTS 
            (SELECT 1 
               FROM TMENUVIEWDIM 
               WHERE MENU_ID='3804404' AND OP_CODE=@IN_INPUT_MAN 
                AND (VIEWSTR=@V_FIELD OR VIEWSTR LIKE '%$'+@V_FIELD+'$%' 
                    OR VIEWSTR LIKE @V_FIELD+'$%' OR VIEWSTR LIKE '%$'+@V_FIELD))
            SET @V_SHOW_FIELDS=@V_SHOW_FIELDS+','+@V_FIELD
		FETCH NEXT FROM CURR INTO @V_FIELD
    END
    CLOSE CURR
    DEALLOCATE CURR
    --去掉最前面的','
    SET @V_SHOW_FIELDS=SUBSTRING(@V_SHOW_FIELDS,2,LEN(@V_SHOW_FIELDS))
    */
    SET @V_SQL = 'SELECT '+@V_SHOW_FIELDS+' FROM #TEMP0908 ORDER BY P_ID'
    EXEC (@V_SQL)

GO
