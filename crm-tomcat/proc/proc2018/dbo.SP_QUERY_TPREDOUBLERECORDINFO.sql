USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE dbo.SP_QUERY_TPREDOUBLERECORDINFO @IN_SERIAL_NO      INT,            --序号
						  @IN_PRE_SERIAL_NO  INT,            --预约表主键(EFCRM..TPRECONTRACT.SERIAL_NO)
						  @IN_SL_DATE  INT,            --双录日期
						  @IN_STATUS  INT,            --双录信息审核状态
						  @IN_INPUT_MAN      INT            --操作员
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    IF @IN_SERIAL_NO IS NULL SET @IN_SERIAL_NO = 0
    IF @IN_PRE_SERIAL_NO IS NULL SET @IN_PRE_SERIAL_NO = 0

    IF ISNULL(@IN_SL_DATE,0) = 0 SET @IN_SL_DATE = 299991231
	DECLARE @V_IS_FLAG INT,@V_TEAM_ID INT
	SELECT @V_TEAM_ID = TEAM_ID FROM TManagerTeamMembers WHERE MANAGERID = @IN_INPUT_MAN
    SELECT @V_IS_FLAG = 0
	
	IF ISNULL(@IN_PRE_SERIAL_NO,0) <> 0 and ISNULL(@IN_SERIAL_NO ,0)=0
		BEGIN
        SELECT A.SERIAL_NO, A.PRE_SERIAL_NO, DBO.GETDATEINT(A.RECORD_TIME) AS SL_DATE, A.RECORD_TIME AS SL_TIME 
                , A.RECORD_TYPE, A.RECORD_TYPE_NAME, A.INPUT_MAN, A.INPUT_TIME, A.STATUS, A.STATUS_NAME, A.CHECKER 
                , A.CHECKER_TIME, D.CUST_NAME, D.SERVICE_MAN
                , O.OP_NAME AS SERVICE_MAN_NAME, O.MOBILE AS SERVICE_MAN_MOBILE
                FROM TPREDOUBLERECORDINFO A 
                LEFT JOIN TPRECONTRACT B ON  A.PRE_SERIAL_NO = B.SERIAL_NO 
                LEFT JOIN TCustomers D ON B.CUST_ID = D.CUST_ID  
                LEFT JOIN TOPERATOR O ON D.SERVICE_MAN = O.OP_CODE
                WHERE (A.PRE_SERIAL_NO = @IN_PRE_SERIAL_NO OR @IN_PRE_SERIAL_NO = 0)
				ORDER BY A.INPUT_TIME DESC
		END 
		ELSE 
		BEGIN
		SELECT A.SERIAL_NO, A.PRE_SERIAL_NO, DBO.GETDATEINT(A.RECORD_TIME) AS SL_DATE, A.RECORD_TIME AS SL_TIME 
                , A.RECORD_TYPE, A.RECORD_TYPE_NAME, A.INPUT_MAN, A.INPUT_TIME, A.STATUS, A.STATUS_NAME , A.STATUS_NAME
                , A.CHECKER 
                , A.CHECKER_TIME
                , D.CUST_NAME, D.SERVICE_MAN
                , O.OP_NAME AS SERVICE_MAN_NAME, O.MOBILE AS SERVICE_MAN_MOBILE
                FROM TPREDOUBLERECORDINFO A 
                LEFT JOIN TPRECONTRACT B ON  A.PRE_SERIAL_NO = B.SERIAL_NO 
                LEFT JOIN TCustomers D ON B.CUST_ID = D.CUST_ID  
                LEFT JOIN TOPERATOR O ON D.SERVICE_MAN = O.OP_CODE
                WHERE A.SERIAL_NO=@IN_SERIAL_NO
                AND (DBO.GETDATEINT(A.RECORD_TIME) BETWEEN 0 AND ISNULL(@IN_SL_DATE,0))
                AND (A.STATUS=@IN_STATUS OR ISNULL(@IN_STATUS,0) = 0 OR @IN_STATUS = '')
                ORDER BY A.INPUT_TIME DESC
		END
GO
