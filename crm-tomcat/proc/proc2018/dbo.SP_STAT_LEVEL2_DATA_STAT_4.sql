USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_STAT_LEVEL2_DATA_STAT_4   @IN_BEGIN_DATE  INT=0,
                                              @IN_END_DATE    INT=0
                                              --@IN_INPUT_MAN   INT

WITH ENCRYPTION
AS

DECLARE @V_TEMP_TABLE TABLE(MOTH INT,
                            ONE_MONEY    DECIMAL(16,3),
                            TWO_MONEY    DECIMAL(16,3),
                            THREE_MONEY  DECIMAL(16,3),
                            FOUR_MONEY   DECIMAL(16,3),
                            FIVE_MOENY   DECIMAL(16,3),
                            SIX_MONEY    DECIMAL(16,3),
                            SEVEN_MONEY  DECIMAL(16,3),
                            EIGHT_MONEY  DECIMAL(16,3),
                            NINE_MONEY   DECIMAL(16,3),
                            TEN_MONEY    DECIMAL(16,3),
                            ELEVEN_MONEY DECIMAL(16,3),
                            TWELVE_MONEY DECIMAL(16,3))
DECLARE @V_FLAG INT
SET @V_FLAG = 1


WHILE @V_FLAG <13
BEGIN
	INSERT INTO @V_TEMP_TABLE(MOTH)VALUES(201100+@V_FLAG)
	SET @V_FLAG = @V_FLAG+1
END

--100 至 300 万
UPDATE @V_TEMP_TABLE SET ONE_MONEY = B.TOTAL_MONEY
FROM @V_TEMP_TABLE A,(SELECT dbo.GETDATEINT(INPUT_TIME)/100 AS MOTH,CUST_TYPE,TOTAL_MONEY
	FROM TCUSTOMERS
		WHERE dbo.GETDATEINT(INPUT_TIME)/100 BETWEEN 201101 AND 201110)B
	WHERE (B.TOTAL_MONEY < 3000000)
	AND A.MOTH = B.MOTH

--300 至 1000 万
UPDATE @V_TEMP_TABLE SET TWO_MONEY = B.TOTAL_MONEY
FROM @V_TEMP_TABLE A,(SELECT dbo.GETDATEINT(INPUT_TIME)/100 AS MOTH,CUST_TYPE,TOTAL_MONEY
	FROM TCUSTOMERS
		WHERE dbo.GETDATEINT(INPUT_TIME)/100 BETWEEN 201101 AND 201110)B
	WHERE (B.TOTAL_MONEY BETWEEN 3000000 AND 10000000)
	AND A.MOTH = B.MOTH

--1000 至 5000 万
UPDATE @V_TEMP_TABLE SET TWO_MONEY = B.TOTAL_MONEY
FROM @V_TEMP_TABLE A,(SELECT dbo.GETDATEINT(INPUT_TIME)/100 AS MOTH,CUST_TYPE,TOTAL_MONEY
	FROM TCUSTOMERS
		WHERE dbo.GETDATEINT(INPUT_TIME)/100 BETWEEN 201101 AND 201110)B
	WHERE (B.TOTAL_MONEY BETWEEN 10000000 AND 50000000)
	AND A.MOTH = B.MOTH

--大于 5000 万
UPDATE @V_TEMP_TABLE SET TWO_MONEY = B.TOTAL_MONEY
FROM @V_TEMP_TABLE A,(SELECT dbo.GETDATEINT(INPUT_TIME)/100 AS MOTH,CUST_TYPE,TOTAL_MONEY
	FROM TCUSTOMERS
		WHERE dbo.GETDATEINT(INPUT_TIME)/100 BETWEEN 201101 AND 201110)B
	WHERE (B.TOTAL_MONEY > 50000000)
	AND A.MOTH = B.MOTH

SELECT * FROM @V_TEMP_TABLE



GO
