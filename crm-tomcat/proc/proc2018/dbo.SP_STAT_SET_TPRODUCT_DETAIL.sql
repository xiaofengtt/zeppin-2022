﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_STAT_SET_TPRODUCT_DETAIL @IN_BEGIN_DATE  INTEGER = 0,
                                             @IN_END_DATE    INTEGER = 0,
                                             @IN_INPUT_MAN   INTEGER = 0
WITH ENCRYPTION
AS
    --获取每个合同的渠道来源
    SELECT A.PRODUCT_ID,A.SERIAL_NO,A.CONTRACT_BH,A.CUST_ID,A.CHANNEL_ID,A.CHANNEL_TYPE,CASE SUBSTRING(A.CHANNEL_TYPE,0,7)
			WHEN '550001' THEN '直营'
			WHEN '550002' THEN '内部渠道'
			ELSE '外部渠道'	END AS 'CHANNEL_TYPE_NAME',B.CHANNEL_NAME INTO #V_TEMP_CHANNEL_TABLE
	    FROM INTRUST..TCONTRACT A LEFT JOIN INTRUST..TCHANNEL B ON A.CHANNEL_ID = B.CHANNEL_ID AND A.CHANNEL_TYPE = B.CHANNEL_TYPE

    --获得每个客户的一次购买时间
    SELECT CUST_ID,MIN(START_DATE) AS FIRST_BY_DATE,'Y' AS 'NEW' INTO #V_TEMP_CUSTFIRST_TABLE
        FROM INTRUSTHistory..HBENIFITOR GROUP BY CUST_ID

    --查询集合产品的购买明细
    SELECT A.PRODUCT_CODE, A.PRODUCT_NAME,B.CUST_NAME,A.CONTRACT_SUB_BH, A.RG_MONEY,C.CONTENT+A.PROV_LEVEL_NAME AS '信托单位',
		CASE ISNULL(P.VALID_PERIOD,0)
				WHEN 0 THEN O.CONTENT
		ELSE CONVERT(VARCHAR,P.VALID_PERIOD)+O.CONTENT END AS RG_DEADLINE,B.CUST_TYPE_NAME,N.CHANNEL_TYPE_NAME,N.CHANNEL_NAME,'',
		A.CHANNEL_COOPERTYPE_NAME,A.MARKET_MONEY,H.TEAM_NAME,H.MANAGERNAME,ISNULL(M.NEW,'N') AS 'Y/N'
	    FROM INTRUST..TPRODUCT P LEFT JOIN INTRUST..TINIPARAM O ON P.PERIOD_UNIT = O.TYPE_VALUE AND O.TYPE_ID = 114,
			INTRUST..TCONTRACT A LEFT JOIN EFCRM..TCustomers B ON A.CUST_ID = B.CUST_ID
							  LEFT JOIN INTRUST..TINTEGERPARAM C ON A.PROV_FLAG = C.TYPE_VALUE AND C.TYPE_ID = 3003
							  LEFT JOIN TMANAGERTEAMMEMBERS H ON A.SERVICE_MAN = H.MANAGERID
							  LEFT JOIN #V_TEMP_CHANNEL_TABLE N ON A.CONTRACT_BH = N.CONTRACT_BH AND A.PRODUCT_ID = N.PRODUCT_ID AND A.CUST_ID = N.CUST_ID
							  LEFT JOIN #V_TEMP_CUSTFIRST_TABLE M ON A.CUST_ID = M.CUST_ID AND A.START_DATE = M.FIRST_BY_DATE
		WHERE A.PRODUCT_ID = P.PRODUCT_ID
		    AND P.INTRUST_FLAG1 = 2
		    AND (P.START_DATE BETWEEN ISNULL(@IN_BEGIN_DATE,0) AND ISNULL(@IN_END_DATE,20991230))
	    ORDER BY PRODUCT_CODE DESC


GO