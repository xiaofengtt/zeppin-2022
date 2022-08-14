USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCUSTGROUPMEMBERS      @IN_SERIAL_NO     INT,      --序号（TCustGroupMembers.Serial_no）
                                                 @IN_GROUPID       INT,      --客户分组ID（TCustGroups.GroupID）
                                                 @IN_CUSTID        INT,      --客户ID
                                                 @IN_INPUT_MAN     INT =  0  --输入员
WITH ENCRYPTION
AS
    IF ISNULL(@IN_SERIAL_NO,0) <> 0
        SELECT B.CUST_NAME,C.GroupName,A.*
        FROM TCustGroupMembers A,TCustomers B,TCustGroups C
        WHERE B.STATUS <> '112805'
            AND A.CUST_ID = B.CUST_ID AND A.GroupID = C.GroupID  AND Serial_no = @IN_SERIAL_NO
    ELSE
        SELECT B.CUST_NAME,C.GroupName,A.*
        FROM TCustGroupMembers A,TCustomers B,TCustGroups C
        WHERE B.STATUS <> '112805'
            AND A.CUST_ID = B.CUST_ID AND A.GroupID = C.GroupID
            AND (A.GroupID = @IN_GROUPID OR ISNULL(@IN_GROUPID,0) = 0 )
            AND (A.CUST_ID = @IN_CUSTID OR ISNULL(@IN_CUSTID,0) = 0 )

GO
