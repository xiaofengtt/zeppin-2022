USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TSmsRecord @IN_SmsIndex           INT,
                                     @IN_SmsUser            NVARCHAR(50),   --发送者用户代码
                                     @IN_PhoneNumber        NVARCHAR(20),   --接受短信号码
                                     @IN_SmsContent         NVARCHAR(250),  --发送短信内容
                                     @IN_SendLevel          INT = 0,        --数值越低，优先级越高
                                     @IN_PutType            NVARCHAR(50),   --提交方式（待发、定时、循环）
                                     @IN_CUST_NAME          NVARCHAR(50),   --客户名称
                                     @IN_SerialNo           INTEGER,        --主任务ID
                                     @IN_SerialNo_details   INTEGER,        --明细任务ID
                                     @IN_SERVICETITLE       NVARCHAR(60) = NULL,    --任务标题
                                     @IN_DATE1              INTEGER = NULL, --时间下限
                                     @IN_DATE2              INTEGER = NULL --时间上限
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    IF @IN_DATE1 IS NULL SET @IN_DATE1 = 0
    IF @IN_DATE2 IS NULL OR @IN_DATE2 = 0 SET @IN_DATE2 = 99999999
    IF @IN_SERVICETITLE IS NULL SET @IN_SERVICETITLE = ''
    IF ISNULL(@IN_SmsIndex,0) <> 0
    BEGIN
        SELECT A.*, B.OP_NAME
        FROM (TSmsRecord A LEFT JOIN TOPERATOR B ON A.SmsUser = B.OP_CODE)
        WHERE A.SmsIndex = @IN_SmsIndex
    END
    ELSE
    BEGIN
        SELECT A.*, B.OP_NAME
        FROM (TSmsRecord A LEFT JOIN TOPERATOR B ON A.SmsUser = B.OP_CODE) LEFT JOIN TServiceTasks C ON A.SerialNo = C.Serial_no
        WHERE (A.SmsUser = @IN_SmsUser OR ISNULL(@IN_SmsUser,'') = N'')
            AND (A.PhoneNumber LIKE '%' + @IN_PhoneNumber + '%' OR ISNULL(@IN_PhoneNumber,'') = N'')
            AND (A.SmsContent LIKE '%' + @IN_SmsContent + '%' OR ISNULL(@IN_SmsContent,'') = N'')
            AND (A.SendLevel = @IN_SendLevel OR ISNULL(@IN_SendLevel,0) = 0 )
            AND (A.PutType LIKE '%' + @IN_PutType + '%' OR ISNULL(@IN_PutType,'') = N'')
            AND (A.Cust_Name LIKE '%' + @IN_CUST_NAME + '%' OR ISNULL(@IN_CUST_NAME,'') = N'')
            AND (A.SerialNo = @IN_SerialNo OR ISNULL(@IN_SerialNo,0) = 0 )
            AND (A.SerialNo_details = @IN_SerialNo_details OR ISNULL(@IN_SerialNo_details,0) = 0 )
            AND (dbo.GETDATEINT(A.SmsTime) BETWEEN @IN_DATE1 AND @IN_DATE2)
            AND (C.ServiceTitle IS NULL OR C.ServiceTitle LIKE '%'+@IN_SERVICETITLE+'%')
        ORDER BY SmsIndex DESC
    END
GO
