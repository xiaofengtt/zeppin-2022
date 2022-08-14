USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCallRecords @IN_Serial_no   BIGINT,         --序号（主键）
                                       @IN_Direction   INTEGER,        --方向1被叫接听2主叫拨打
                                       @IN_CallTime    INTEGER,        --通话开始日期（YYYYMMDD）
                                       @IN_ManagerID   INTEGER,        --客户经理ID（TCustManagers.ManagerID）
                                       @IN_Extension   INTEGER,        --分机号码
                                       @IN_CUST_ID     INTEGER,        --客户ID（TCustomers.CUST_ID）（索引）
                                       @IN_PhoneNumber NVARCHAR(30),   --对方电话号码
                                       @IN_Content     NVARCHAR(MAX),  --通话记事
                                       @IN_Status      INTEGER,        --状态1正常完成2本次会话待处理
                                       @IN_INPUT_MAN   INTEGER         --操作员
WITH ENCRYPTION
AS
    SELECT A.*, CONVERT(VARCHAR, A.CallTime, 120) AS CallTimeStr
        FROM TCallRecords A
        WHERE (A.Serial_no    = @IN_Serial_no   OR @IN_Serial_no   IS NULL OR @IN_Serial_no   = 0)
           AND(A.Direction    = @IN_Direction   OR @IN_Direction   IS NULL OR @IN_Direction   = 0)
           AND(A.ManagerID    = @IN_ManagerID   OR @IN_ManagerID   IS NULL OR @IN_ManagerID   = 0)
           AND(A.Extension    = @IN_Extension   OR @IN_Extension   IS NULL OR @IN_Extension   = 0)
           AND(A.CUST_ID      = @IN_CUST_ID     OR @IN_CUST_ID     IS NULL OR @IN_CUST_ID     = 0)
           AND(A.Status       = @IN_Status      OR @IN_Status      IS NULL OR @IN_Status      = 0)
           AND(A.PhoneNumber  = @IN_PhoneNumber OR @IN_PhoneNumber IS NULL OR @IN_PhoneNumber = N'')
           AND(dbo.GETDATEINT(A.CallTime) = @IN_CallTime OR @IN_CallTime IS NULL OR @IN_CallTime    = 0)
           AND(A.Content LIKE '%'+@IN_Content+'%' OR @IN_Content   IS NULL OR @IN_Content     = N'')
        ORDER BY CallTime DESC
GO
