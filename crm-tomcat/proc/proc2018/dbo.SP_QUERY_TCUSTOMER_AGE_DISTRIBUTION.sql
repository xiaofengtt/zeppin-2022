USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCUSTOMER_AGE_DISTRIBUTION    @IN_BEGIN_DATE  INT =0,
                                                        @IN_END_DATE    INT =0,   --暂未使用的参数
                                                        @IN_SCOPE       INT =0,   --统计范围:0全部，1本人；2本部门
                                                        @IN_INPUT_MAN   INT = NULL 

WITH ENCRYPTION
AS
    DECLARE @V_OPCODES TABLE(OP_CODE INT)
    IF @IN_SCOPE=2 --保存下本部门的客户经理列表
		INSERT INTO @V_OPCODES
			SELECT OP_CODE FROM TOPERATOR WHERE DEPART_ID=(SELECT DEPART_ID FROM TOPERATOR WHERE OP_CODE=@IN_INPUT_MAN)
    CREATE TABLE #TEMP_TABLE5
    (
        BEGIN_AGE   INT,
        END_AGE     INT,
        TOTAL       INT,
        RG_MONEY    DECIMAL(16,3),
        NUMBER_RATE DECIMAL(16,4),
        RG_RATE     DECIMAL(16,4)
    )
    
    CREATE TABLE #TEMP_TCONTRACT
    (
        CUST_ID     INT,
        RG_MONEY    DECIMAL(16,3)
    )
    
    
    IF ISNULL(@IN_BEGIN_DATE,0)=0
        SET @IN_BEGIN_DATE = dbo.GETDATEINT(GETDATE())
    --IF ISNULL(@IN_END_DATE,0)=0
    --    SET @IN_END_DATE = dbo.GETDATEINT(GETDATE())
    
    --直销客户的认购统计
    BEGIN
        INSERT INTO #TEMP_TCONTRACT(CUST_ID,RG_MONEY)
            SELECT B.CUST_ID,SUM(B.RG_MONEY) AS RG_MONEY 
                FROM INTRUST..TCONTRACT B 
                WHERE (B.CHANNEL_TYPE LIKE '550001%' OR B.CHANNEL_TYPE IS NULL) 
                GROUP BY B.CUST_ID
    END

    BEGIN
    INSERT INTO #TEMP_TABLE5(BEGIN_AGE,END_AGE,TOTAL,RG_MONEY)
        SELECT C_TABLE.BEGIN_AGE,C_TABLE.END_AGE,COUNT(*) ,SUM(RG_MONEY) FROM
            (SELECT
                CASE WHEN (A.AGE/10<2) THEN 0
                     WHEN (A.AGE/10<3) THEN 20
                     WHEN (A.AGE/10<4) THEN 30
                     WHEN (A.AGE/10<5) THEN 40
                     WHEN (A.AGE/10<6) THEN 50
                     WHEN (A.AGE/10>=6) THEN 60
                     ELSE 0 END AS BEGIN_AGE ,
                CASE WHEN (A.AGE/10<2) THEN 19
                     WHEN (A.AGE/10<3) THEN 29
                     WHEN (A.AGE/10<4) THEN 39
                     WHEN (A.AGE/10<5) THEN 49
                     WHEN (A.AGE/10<6) THEN 59
                     WHEN (A.AGE/10>=6) THEN 100
                     ELSE 0 END AS END_AGE,
                B.RG_MONEY AS RG_MONEY
            FROM TCUSTOMERS A,#TEMP_TCONTRACT B
            WHERE A.CUST_TYPE = 1 
                AND EXISTS(SELECT C.CUST_ID FROM INTRUST..TBENIFITOR C WHERE A.CUST_ID = C.CUST_ID)
                AND A.CUST_ID = B.CUST_ID
                AND (@IN_SCOPE=0 --全部
                    OR (@IN_SCOPE=1 AND A.SERVICE_MAN=@IN_INPUT_MAN) --本人
                    OR (@IN_SCOPE=2 AND EXISTS(SELECT * FROM @V_OPCODES WHERE OP_CODE=A.SERVICE_MAN))--本部门
                )
                AND dbo.GETDATEINT(A.INPUT_TIME) <= @IN_BEGIN_DATE) AS C_TABLE
        GROUP BY C_TABLE.BEGIN_AGE,C_TABLE.END_AGE ORDER BY  C_TABLE.BEGIN_AGE
    END
    
    DECLARE @V_NMBER_ACOUNT DECIMAL(16,4),@V_RG_ACOUNT DECIMAL(16,4)
    SET @V_NMBER_ACOUNT = (SELECT SUM(TOTAL) FROM #TEMP_TABLE5)
    SET @V_RG_ACOUNT = (SELECT SUM(RG_MONEY) FROM #TEMP_TABLE5)
    
    BEGIN
        UPDATE #TEMP_TABLE5 SET NUMBER_RATE = TOTAL/@V_NMBER_ACOUNT,
                                RG_RATE     = RG_MONEY/@V_RG_ACOUNT
    END
    SELECT * FROM #TEMP_TABLE5
GO
