USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_IMRULELOG  @IN_START_DATE     INT,
                                     @IN_END_DATE       INT,
                                     @IN_CUST_NAME      NVARCHAR(60),
                                     @IN_LOG_TYPE       INT = 1,
                                     @IN_RULE_ID        INT = 0
WITH ENCRYPTION
AS
    SELECT A.*,B.CUST_NAME,C.RULE_NAME,B.CUST_NO
    FROM IMRULELOG A,TCustomers B,IM_RULE C
    WHERE (dbo.GETDATEINT(TRACT_TIME) > =@IN_START_DATE OR ISNULL(@IN_START_DATE,0) = 0 )
        AND (dbo.GETDATEINT(TRACT_TIME) < = @IN_END_DATE OR ISNULL(@IN_END_DATE,0) = 0 )
        AND (B.CUST_NAME LIKE '%' + @IN_CUST_NAME + '%' OR ISNULL(@IN_CUST_NAME,'') = '')
        AND (A.RULE_ID = C.RULE_ID OR ISNULL(@IN_RULE_ID,0) = 0 )
        AND A.CUST_ID = B.CUST_ID
        AND A.LOG_TYPE = @IN_LOG_TYPE
        AND B.STATUS<>'112805'
GO
