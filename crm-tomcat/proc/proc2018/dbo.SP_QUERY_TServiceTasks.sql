USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TServiceTasks @IN_SERIAL_NO       INTEGER = 0,        --序号
                                        @IN_MANAGERID       INTEGER = NULL,        --客户经理TCUSTMANAGERS.MANAGERID
                                        @IN_EXECUTOR        INTEGER = NULL,        --执行者（TSERVICETASKS.EXECUTOR）
                                        @IN_INSERTMAN       INTEGER = NULL,        --创建者
                                        @IN_SERVICETYPE     INTEGER = 0,        --服务类别TSERVICEDEFINE.SERVICETYPE
                                        @IN_SERVICEWAY      NVARCHAR(10),       --服务途径TDICTPARAM(1109)
                                        @IN_STATUS          INTEGER = 0,        --处理标志1未处理2待处理3处理中4已处理9作废
                                        @IN_QUES_ID         INTEGER = 0,        --问卷ID
                                        @IN_PRODUCT_ID      INTEGER = 0,        --产品ID
                                        @IN_INPUT_MAN       INTEGER = 0,        --操作员
                                        @IN_START_DATE      INT = 0 ,           --开始日期
                                        @IN_END_DATE        INT = 0             --结束日期

WITH ENCRYPTION
AS
    DECLARE @V_ROLE_ID INT
    SELECT @V_ROLE_ID=ROLE_ID FROM TOPERATOR WHERE OP_CODE=@IN_INPUT_MAN
    IF ISNULL(@IN_SERIAL_NO,0) <> 0
    BEGIN
        IF @IN_SERVICETYPE = 64
            BEGIN
                SELECT a.*,b.*
                FROM TServiceTasks a,TQuestInfo b
                WHERE Serial_no = @IN_SERIAL_NO
                    AND a.QUES_ID = b.QUES_ID
            END
        ELSE
            BEGIN
                SELECT *
                FROM TServiceTasks
                WHERE Serial_no = @IN_SERIAL_NO
            END
    END
    ELSE IF @IN_SERVICETYPE = 64
    BEGIN
        SELECT a.*,b.*
        FROM TServiceTasks a,TQuestInfo b
        WHERE (a.InsertMan = @IN_INSERTMAN OR ISNULL(@IN_MANAGERID,0)=0 OR a.ManagerID = @IN_MANAGERID)
            AND (a.Executor = @IN_EXECUTOR OR ISNULL(@IN_EXECUTOR,0) = 0)
            AND (a.ServiceType = @IN_SERVICETYPE OR ISNULL(@IN_SERVICETYPE,0) = 0 )
            AND (a.ServiceWay = @IN_SERVICEWAY OR ISNULL(@IN_SERVICEWAY,'') = N'' )
            AND (a.Status = @IN_STATUS OR  ISNULL(@IN_STATUS,0) = 0 )
            AND (a.QUES_ID = @IN_QUES_ID OR  ISNULL(@IN_QUES_ID,0) = 0 )
            AND (a.PRODUCT_ID = @IN_PRODUCT_ID OR  ISNULL(@IN_PRODUCT_ID,0) = 0 )
            AND (dbo.GETDATEINT(StartDateTime) > =@IN_START_DATE OR ISNULL(@IN_START_DATE,0) = 0 )
            AND (dbo.GETDATEINT(StartDateTime) < = @IN_END_DATE OR ISNULL(@IN_END_DATE,0) = 0 )
            AND a.QUES_ID = b.QUES_ID
    END
    ELSE
    BEGIN
        SELECT *
        FROM TServiceTasks
        WHERE  (ManagerID = @IN_MANAGERID OR ISNULL(@IN_MANAGERID,0)=0 OR InsertMan = @IN_INSERTMAN)
            AND (Executor = @IN_EXECUTOR OR ISNULL(@IN_EXECUTOR,0) = 0 OR (ExecRoleID=@V_ROLE_ID AND IsRole=1))
            AND (ServiceType = @IN_SERVICETYPE OR ISNULL(@IN_SERVICETYPE,0) = 0 )
            AND (ServiceWay = @IN_SERVICEWAY OR ISNULL(@IN_SERVICEWAY,'') = N'' )
            AND (Status = @IN_STATUS OR  ISNULL(@IN_STATUS,0) = 0 )
            AND (QUES_ID = @IN_QUES_ID OR  ISNULL(@IN_QUES_ID,0) = 0 )
            AND (PRODUCT_ID = @IN_PRODUCT_ID OR  ISNULL(@IN_PRODUCT_ID,0) = 0 )
            AND (dbo.GETDATEINT(StartDateTime) > =@IN_START_DATE OR ISNULL(@IN_START_DATE,0) = 0 )
            AND (dbo.GETDATEINT(StartDateTime) < = @IN_END_DATE OR ISNULL(@IN_END_DATE,0) = 0 )
    END
GO
