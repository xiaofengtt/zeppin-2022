USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE SP_QUERY_TServiceTaskDetail    @IN_SERIAL_NO        INTEGER,       --序号（TSERVICETASKDETAIL.SERIAL_NO）
                                                @IN_TASKSERIALNO     INTEGER,       --序号（TSERVICETASK.SERIAL_NO）
                                                @IN_SERVICETYPE      INTEGER,       --服务类别TSERVICETASKS.SERVICETYPE
                                                @IN_SERVICEWAY       NVARCHAR(10),  --服务途径TDICTPARAM(1109)
                                                @IN_STATUS           INTEGER,       --处理标志1未处理2待处理3处理中4已处理9作废
                                                @IN_NEEDFEEDBACK     BIT,           --是否需要反馈0不需要1需要
                                                @IN_INPUT_MAN        INTEGER        --操作员
WITH ENCRYPTION
AS
    IF ISNULL(@IN_SERIAL_NO,0) <> 0
        SELECT C.ServiceTitle,B.CUST_NO,B.CUST_NAME,B.SERVICE_MAN,B.CUST_TEL,B.H_TEL,B.O_TEL,B.MOBILE,B.BP,B.POST_ADDRESS,B.POST_ADDRESS2,B.POST_CODE,B.POST_CODE2,B.CARD_ID,A.*
        FROM TServiceTaskDetail A,TCustomers B,TServiceTasks C
        WHERE A.TargetCustID = B.CUST_ID AND A.TaskSerialNO = C.Serial_no
            AND A.Serial_no = @IN_SERIAL_NO
    ELSE
        SELECT C.ServiceTitle,B.CUST_NO,B.CUST_NAME,B.SERVICE_MAN,B.CUST_TEL,B.H_TEL,B.O_TEL,B.MOBILE,B.BP,B.POST_ADDRESS,B.POST_ADDRESS2,B.POST_CODE,B.POST_CODE2,B.CARD_ID,A.*
        FROM TServiceTaskDetail A,TCustomers B,TServiceTasks C
        WHERE A.TargetCustID = B.CUST_ID AND A.TaskSerialNO = C.Serial_no
            AND (A.TaskSerialNO = @IN_TASKSERIALNO OR ISNULL(@IN_TASKSERIALNO,0) = 0 )
            AND (A.ServiceType = @IN_SERVICETYPE OR ISNULL(@IN_SERVICETYPE,0) = 0 )
            AND (A.ServiceWay = @IN_SERVICEWAY OR ISNULL(@IN_SERVICEWAY,'') = N'' )
            AND (A.Status = @IN_STATUS OR  ISNULL(@IN_STATUS,0) = 0 )
            AND (A.NeedFeedback = @IN_NEEDFEEDBACK OR  ISNULL(@IN_NEEDFEEDBACK,0) = 0 )
            AND (C.ManagerID = @IN_INPUT_MAN OR C.Executor = @IN_INPUT_MAN)
GO
