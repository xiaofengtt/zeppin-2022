USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_STAT_TPRODUCT_TOTAL @IN_BEGIN_DATE  INTEGER = 0,
                                        @IN_CREATE_FLAG NVARCHAR , --  是  否，
                                        @IN_INPUT_MAN   INTEGER = 0
WITH ENCRYPTION
AS

IF @IN_CREATE_FLAG = '是'
BEGIN
	DELETE TPRODUCTCOLLECT WHERE (LEFT([成立时间],6) = @IN_BEGIN_DATE OR ISNULL(@IN_BEGIN_DATE,0)=0)--删除数据

	DECLARE @V_TEMP_TOTAL_TANLE TABLE(PRODUCT_ID                    INTEGER ,
	                                  PRODUCT_CODE                  NVARCHAR(10),
	                                  PRODUCT_NAME                  NVARCHAR(100),
									  [固有]                        DEC(16,3),
									  [劣后]                        DEC(16,3),
									  [认购总规模]                  DEC(16,3),
									  [个人客户数]                  INTEGER,
									  [机构客户数]                  INTEGER,
									  [签约客户数]                  INTEGER,
									  [新增个人客户]                INTEGER,
									  [新增机构客户]                INTEGER,
									  [新增交行客户数]              INTEGER,
									  [新增非交行客户]              INTEGER,
									  [新增直销客户]                INTEGER,
									  [新增客户数]                  INTEGER,
									  [300万以下客户数]             INTEGER,
									  [新增1000万以上客户数]        INTEGER,
									  [新增1000万以上交行客户数]    INTEGER,
									  [新增1000万以上非交行客户数]  INTEGER,
									  [新增1000万以上直销客户数]    INTEGER,
									  [新增1000万以上其他客户]      INTEGER,
									  [新增5000万以上客户数]        INTEGER,
									  [新增5000万以上交行客户数]    INTEGER,
									  [新增5000万以上非交行客户数]  INTEGER,
									  [新增5000万以上直销客户数]    INTEGER,
									  [新增5000万以上其他客户]      INTEGER
	)

	--客户首次认购或者受益时间
	SELECT C.CUST_ID,MIN(C.BEN_DATE) AS 'FIRST_BUY_DATE' INTO #V_FIRT_BY_DATE
		FROM INTRUST..TBENIFITOR C GROUP BY CUST_ID

	--员工+部门信息
	SELECT A.OP_CODE,A.OP_NAME,B.DEPART_NAME INTO #V_OPERATOR_DEPARTMENT_TABLE
		FROM INTRUST..TOPERATOR A,INTRUST..TDEPARTMENT B
		WHERE A.DEPART_ID = B.DEPART_ID


	INSERT INTO	@V_TEMP_TOTAL_TANLE
    --获得每个产品的认购总规模，客户数.新增客户数、固有、劣后、个人客户数、机构客户数、签约客户数、新增个人客户。。。
    SELECT A.PRODUCT_ID,A.PRODUCT_CODE,A.PRODUCT_NAME,SUM(CASE WHEN A.CUST_ID = 119 THEN A.RG_MONEY END) AS [固有] ,--通过交银自身购买的算固有的
    		SUM(CASE WHEN A.PRE_FLAG=3 THEN A.RG_MONEY END) AS [劣后],
    		SUM(RG_MONEY) AS [认购总规模],
    		COUNT(CASE WHEN B.CUST_TYPE=1 THEN A.CUST_ID END) AS [个人客户数],
    		COUNT(CASE WHEN B.CUST_TYPE=2 THEN A.CUST_ID END) AS [机构客户数],
    		COUNT(A.CUST_ID) AS [签约客户数],
    		COUNT(CASE WHEN B.CUST_TYPE=1 AND A.START_DATE = D.FIRST_BUY_DATE THEN A.CUST_ID END) AS [新增个人客户],
    		COUNT(CASE WHEN B.CUST_TYPE=2 AND A.START_DATE = D.FIRST_BUY_DATE THEN A.CUST_ID END) AS [新增机构客户],
    		COUNT(CASE WHEN A.CHANNEL_TYPE = '550002'
    						AND A.START_DATE = D.FIRST_BUY_DATE
    				   THEN A.CUST_ID END) AS [新增交行客户数],
    		COUNT(CASE WHEN  A.CHANNEL_TYPE LIKE '550003%'
    						AND A.START_DATE = D.FIRST_BUY_DATE
    				   THEN A.CUST_ID END) AS [新增非交行客户],
    		COUNT(CASE WHEN A.CHANNEL_TYPE LIKE '550001%'
    						AND A.START_DATE = D.FIRST_BUY_DATE
    				   THEN A.CUST_ID END) AS [新增直销客户],
    		COUNT(CASE A.START_DATE WHEN D.FIRST_BUY_DATE THEN A.CUST_ID END) AS [新增客户数],
    		COUNT(CASE WHEN RG_MONEY < 3000000 THEN A.CUST_ID END) AS [300万以下客户数],
    		COUNT(CASE WHEN RG_MONEY < 50000000 AND RG_MONEY>=10000000 THEN A.CUST_ID END) AS [新增1000万以上客户数],
    		COUNT(CASE WHEN RG_MONEY < 50000000
    						AND RG_MONEY>=10000000
    						AND A.CHANNEL_TYPE = '550002'
    					THEN A.CUST_ID END) AS [新增1000万以上交行客户数],
    		COUNT(CASE WHEN RG_MONEY < 50000000
    						AND RG_MONEY>=10000000
    						AND A.CHANNEL_TYPE LIKE '550003%'
    					THEN A.CUST_ID END) AS [新增1000万以上非交行客户数],
    		 COUNT(CASE WHEN RG_MONEY < 50000000
    						AND RG_MONEY>=10000000
    						AND A.CHANNEL_TYPE LIKE '550001%'
    					THEN A.CUST_ID END) AS [新增1000万以上直销客户数],
    		COUNT(CASE WHEN RG_MONEY < 50000000
    						AND RG_MONEY>=10000000
    						AND ISNULL(A.CHANNEL_TYPE,'' ) = ''
    					THEN A.CUST_ID END) AS [新增1000万以上其他客户],
    		COUNT(CASE WHEN RG_MONEY >= 50000000 THEN A.CUST_ID END) AS [新增5000万以上客户数],
    		COUNT(CASE WHEN RG_MONEY >= 50000000 AND A.CHANNEL_TYPE = '550002'
    					THEN A.CUST_ID END) AS [新增5000万以上交行客户数],
    		COUNT(CASE WHEN RG_MONEY >= 50000000 AND A.CHANNEL_TYPE LIKE '550003%'
    					THEN A.CUST_ID END) AS [新增5000万以上非交行客户数],
    		 COUNT(CASE WHEN RG_MONEY >= 50000000 AND A.CHANNEL_TYPE LIKE '550001%'
    					THEN A.CUST_ID END) AS [新增5000万以上直销客户数],
    		COUNT(CASE WHEN RG_MONEY < 50000000 AND ISNULL(A.CHANNEL_TYPE,'' ) = ''
    					THEN A.CUST_ID END) AS [新增5000万以上其他客户]
    	FROM INTRUST..TCONTRACT A,TCUSTOMERS B,#V_FIRT_BY_DATE D
    	WHERE A.CUST_ID = B.CUST_ID
    		AND A.CUST_ID = D.CUST_ID
    		AND B.CUST_ID = D.CUST_ID
    	GROUP BY A.PRODUCT_ID,A.PRODUCT_CODE,A.PRODUCT_NAME
    	ORDER BY A.PRODUCT_ID


    --获得每个产品的明细以及 直销、交行、非交行、等渠道的销售规模
    SELECT PRODUCT_ID,[550001] AS [直销],[550002] AS  [交行], [550003] AS [外部渠道] ,--TOAL_CUST_NUM,
			[550004] AS [其它] INTO #V_TEMP_TABLE
		FROM (SELECT A.PRODUCT_ID ,ISNULL(SUBSTRING(A.CHANNEL_TYPE,0,7),'550004') AS CHANNEL_TYPE,
				SUM(RG_MONEY) AS 'RG_MONEY'--,COUNT(A.CUST_ID) AS TOAL_CUST_NUM
    			FROM  INTRUST..TCONTRACT A GROUP BY PRODUCT_ID,ISNULL(SUBSTRING(A.CHANNEL_TYPE,0,7),'550004'))B
    	PIVOT(SUM(RG_MONEY) FOR CHANNEL_TYPE IN ([550001],[550002],[550003],[550004])) AS PRV

    INSERT INTO TPRODUCTCOLLECT([产品ID],[产品编号],[产品名称],[项目类型],--[项目类别],
		[业务团队],[信托经理],[年份],[月份],
		[募集天数],[成立时间],[到期时间],[计划规模上限],[计划规模下线],[预期年收益],[实际年收益],[交行规模],[非交行规模],
		[直销规模],[中心外部员工推荐规模])
    SELECT A.PRODUCT_ID,
			A.PRODUCT_CODE,
			A.PRODUCT_NAME,
			CASE A.INTRUST_FLAG1 WHEN 1 THEN '单一' ELSE '集合' END AS INTRUST_FLAG1_NAME,
--			'' AS [项目类别],
			B.DEPART_NAME AS [业务团队],
			B.OP_NAME,
			LEFT(@IN_BEGIN_DATE,4) AS 'YEAR',
			RIGHT(@IN_BEGIN_DATE,2) AS 'MOTH',
			CASE (A.PRE_END_DATE - A.PRE_START_DATE) WHEN 0 THEN 1 ELSE (A.PRE_END_DATE - A.PRE_START_DATE) END AS [募集天数],
			A.START_DATE,
			A.END_DATE,
			A.PRE_MAX_MONEY,
			A.PRE_MONEY,
			CONVERT(NVARCHAR(10),A.EXP_RATE1*100)+'-'+CONVERT(NVARCHAR(10),A.EXP_RATE2*100) AS RATE,
			C.APY AS [实际年收益],
			CONVERT(DEC(16,3),D.交行),
			CONVERT(DEC(16,3),D.外部渠道),
			CONVERT(DEC(16,3),D.直销),
			CONVERT(DEC(16,3),D.其它)

    FROM INTRUST..TPRODUCT A LEFT JOIN #V_OPERATOR_DEPARTMENT_TABLE B ON A.ADMIN_MANAGER = B.OP_CODE
							 LEFT JOIN INTRUST..TNAVPRICEINFO C ON A.PRODUCT_ID = C.PRODUCT_ID
							 LEFT JOIN #V_TEMP_TABLE D ON A.PRODUCT_ID = D.PRODUCT_ID


	UPDATE TPRODUCTCOLLECT SET  [固有]                        = B.[固有],
                                [劣后]                        = B.[劣后],
                                [签约客户总数]                = B.[签约客户数],
                                [300万以下客户数]             = B.[300万以下客户数],
                                [机构客户数]                  = B.[机构客户数],
                                [个人客户数]                  = B.[个人客户数],
                                [新增客户数]                  = B.[新增客户数],
                                [新增机构客户数]              = B.[新增机构客户],
                                [新增个人客户数]              = B.[新增个人客户],
                                [新增交行客户数]              = B.[新增交行客户数],
                                [新增非交行渠道数]            = B.[新增非交行客户],
                                [新增直销客户数]              = B.[新增直销客户],
                                [募集规模]                    = B.[认购总规模],
                                [新增1000万以上客户数]        = B.[新增1000万以上客户数],
                                [新增1000万以上交行客户数]    = B.[新增1000万以上交行客户数],
                                [新增1000万以上非交行客户数]  = B.[新增1000万以上非交行客户数],
                                [新增1000万以上直销客户数]    = B.[新增1000万以上直销客户数],
                                [新增5000万以上客户数]        = B.[新增5000万以上客户数],
                                [新增5000万以上交行客户数]    = B.[新增5000万以上交行客户数],
                                [新增5000万以上非交行客户数]  = B.[新增5000万以上非交行客户数],
                                [新增5000万以上直销客户数]    = B.[新增5000万以上直销客户数]

	    FROM TPRODUCTCOLLECT A,@V_TEMP_TOTAL_TANLE B
	    WHERE A.[产品ID] = B.PRODUCT_ID

		UPDATE TPRODUCTCOLLECT SET	[直销占比]   = CASE WHEN ISNULL([募集规模],0)>0 THEN [直销规模]/[募集规模] END,
									[交行占比]	 = CASE WHEN ISNULL([募集规模],0)>0 THEN [交行规模]/[募集规模] END,
									[非交行占比] = CASE WHEN ISNULL([募集规模],0)>0 THEN [非交行规模]/[募集规模] END
END

SELECT [产品编号],[产品名称],[项目类型],[项目类别],[业务团队],[信托经理],[年份],[月份],[募集天数],
		[成立时间],[到期时间],[计划规模上限],[计划规模下线],[募集规模],[预期年收益],[实际年收益],
		[交行规模],[非交行规模],[直销规模],[中心外部员工推荐规模],[固有],[劣后],[直销占比],[交行占比],
		[非交行占比],[中心外部员工推荐占比],[平均费率],[交行费率],[非交行费率],[签约客户总数],
		[300万以下客户数],[机构客户数],[个人客户数],[新增客户数],[新增机构客户数],[新增个人客户数],
		[新增交行客户数],[新增非交行渠道数],[新增直销客户数],[中心外部员工推荐新增客户数],[新增1000万以上客户数],
		[新增1000万以上交行客户数],[新增1000万以上非交行客户数],[新增1000万以上直销客户数],[新增5000万以上客户数],
		[新增5000万以上交行客户数],[新增5000万以上非交行客户数],[新增5000万以上直销客户数]
	FROM TPRODUCTCOLLECT WHERE LEFT([成立时间],6) = @IN_BEGIN_DATE
	ORDER BY [产品编号] DESC
GO
