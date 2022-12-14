USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_STAT_SERVICEMANSELLS
                                @IN_PRODUCT_ID    INT,             --产品ID
                                @IN_DATE_START    INT,             --统计时间(起)
                                @IN_DATE_END      INT,             --统计时间(止)
                                @IN_SERVICEMAN_ID INT,             --客户经理ID
                                @IN_INPUT_MAN     INT              --操作员
                                  
WITH ENCRYPTION
AS
    SET @IN_DATE_START = ISNULL(@IN_DATE_START,0)
    IF ISNULL(@IN_DATE_END,0)=0 SET @IN_DATE_END=21991231
    
    CREATE TABLE #TEMP_QUERY
    (
        SERIAL_NO       INT NOT NULL IDENTITY,
        PRODUCT_ID      INT,             --产品ID
        PRODUCT_NAME    NVARCHAR(80),    --产品名称
        DEPART_ID       INT,             --部门ID
        SERVICEMAN      INT,             --客户经理ID(OP_CODE)
        SERVICEMAN_NAME NVARCHAR(60),    --客户经理名称
        RG_MONEY        DEC(20,3),       --到帐金额(销售金额)
        COMMISSION_MONEY DEC(20,3)       --销售提成金额
    )
    IF ISNULL(@IN_SERVICEMAN_ID,0)<>0 --具体的客户经理统计
    BEGIN
        INSERT INTO #TEMP_QUERY(PRODUCT_ID,PRODUCT_NAME,RG_MONEY,COMMISSION_MONEY)
		    SELECT A.PRODUCT_ID,A.PRODUCT_NAME,SUM(RG_MONEY),SUM(ISNULL(B.COMMISSION_RATE,0)*RG_MONEY)
		    FROM INTRUST..TCONTRACT A LEFT JOIN TPRODUCT B ON A.PRODUCT_ID=B.PRODUCT_ID
		        LEFT JOIN TCustomers C ON C.CUST_ID=A.CUST_ID
		    WHERE (A.PRODUCT_ID=@IN_PRODUCT_ID OR ISNULL(@IN_PRODUCT_ID,0)=0)
		        --AND (CASE WHEN (SELECT USER_ID FROM TSYSTEMINFO)<>25/*25重庆信托*/ THEN C.SERVICE_MAN
		        --        WHEN ISNULL(A.SERVICE_MAN,0)=0 THEN C.SERVICE_MAN ELSE A.SERVICE_MAN END)
		        AND (CASE ISNULL(A.SERVICE_MAN_SALES,0) WHEN 0 THEN C.SERVICE_MAN ELSE A.SERVICE_MAN_SALES END)=@IN_SERVICEMAN_ID
		    GROUP BY A.PRODUCT_ID,A.PRODUCT_NAME
		--加入小计
		INSERT INTO #TEMP_QUERY(PRODUCT_ID,PRODUCT_NAME,RG_MONEY,COMMISSION_MONEY)
		    SELECT -1,'小计',SUM(RG_MONEY),SUM(COMMISSION_MONEY) FROM #TEMP_QUERY
		SELECT PRODUCT_ID,PRODUCT_NAME,RG_MONEY,COMMISSION_MONEY FROM #TEMP_QUERY ORDER BY SERIAL_NO
        RETURN
    END
    
    --根据各部门来统计
    /*IF (SELECT USER_ID FROM TSYSTEMINFO)<>25/*25重庆信托*/        
        INSERT INTO #TEMP_QUERY(DEPART_ID,SERVICEMAN,SERVICEMAN_NAME,RG_MONEY)
            SELECT C.DEPART_ID,A.SERVICE_MAN_SALES,C.OP_NAME,SUM(A.RG_MONEY)
            FROM INTRUST..TCONTRACT A
                LEFT JOIN TCustomers D ON D.CUST_ID=A.CUST_ID
                LEFT JOIN TOPERATOR C ON A.SERVICE_MAN_SALES=C.OP_CODE
            WHERE (A.PRODUCT_ID=@IN_PRODUCT_ID OR ISNULL(@IN_PRODUCT_ID,0)=0)
			    AND A.QS_DATE>=@IN_DATE_START
			    AND A.QS_DATE<=@IN_DATE_END
            GROUP BY C.DEPART_ID,A.SERVICE_MAN_SALES,C.OP_NAME
    ELSE *//*25重庆信托*/
        INSERT INTO #TEMP_QUERY(DEPART_ID,SERVICEMAN,SERVICEMAN_NAME,RG_MONEY)
            SELECT O.DEPART_ID, 
                CASE ISNULL(CON.SERVICE_MAN_SALES,0) WHEN 0 THEN CUS.SERVICE_MAN ELSE CON.SERVICE_MAN_SALES END AS SERVICE_MAN, 
                O.OP_NAME, SUM(CON.RG_MONEY)
            FROM INTRUST..TCONTRACT CON
                LEFT JOIN TCustomers CUS ON CUS.CUST_ID=CON.CUST_ID
                LEFT JOIN TOPERATOR O ON O.OP_CODE=CASE ISNULL(CON.SERVICE_MAN_SALES,0) WHEN 0 THEN CUS.SERVICE_MAN ELSE CON.SERVICE_MAN_SALES END
            WHERE (CON.PRODUCT_ID=@IN_PRODUCT_ID OR ISNULL(@IN_PRODUCT_ID,0)=0)
			    AND CON.QS_DATE>=@IN_DATE_START
			    AND CON.QS_DATE<=@IN_DATE_END
            GROUP BY O.DEPART_ID,CASE ISNULL(CON.SERVICE_MAN_SALES,0) WHEN 0 THEN CUS.SERVICE_MAN ELSE CON.SERVICE_MAN_SALES END,O.OP_NAME
            
    --加入各部门的小计
    INSERT INTO #TEMP_QUERY(DEPART_ID,SERVICEMAN,SERVICEMAN_NAME,RG_MONEY)
		SELECT DEPART_ID,-1,'小计',SUM(RG_MONEY) FROM #TEMP_QUERY
		GROUP BY DEPART_ID
	--加入全部的合计
	INSERT INTO #TEMP_QUERY(DEPART_ID,SERVICEMAN,SERVICEMAN_NAME,RG_MONEY)
		SELECT 99999999,-1,'合计',SUM(RG_MONEY) FROM #TEMP_QUERY
		WHERE SERVICEMAN<>-1
    SELECT M.DEPART_ID,N.DEPART_NAME,SERVICEMAN,SERVICEMAN_NAME,RG_MONEY 
        FROM #TEMP_QUERY M LEFT JOIN TDEPARTMENT N ON M.DEPART_ID=N.DEPART_ID
        ORDER BY DEPART_ID,SERIAL_NO
    
GO
