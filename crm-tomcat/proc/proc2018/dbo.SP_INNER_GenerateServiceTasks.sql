USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_INNER_GenerateServiceTasks @IN_DATE      INTEGER, --服务日期，生成该日期的任务
                                               @IN_ManagerID INTEGER  --客户经理ID，可为空，不为空时仅生成该客户经理的任务
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    SET XACT_ABORT ON

    DECLARE @V_ServiceType INT, @V_ServiceTypeName NVARCHAR(60), @V_OffsetDays INT, @V_ServiceWay NVARCHAR(10),
            @V_NoticeCaption NVARCHAR(200), @V_Executor INT, @V_ActualDate INT,@V_AutoFlag INT,@V_TempID INT,@V_TempTitle NVARCHAR(40),
            @V_Originate INT,@V_ExecRoleID INT,@V_ExecFlag INT
    DECLARE @RET INT, @V_DT_INTRUST INT

    SELECT @V_DT_INTRUST = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'DT_INTRUST'
    IF @IN_DATE IS NULL SELECT @IN_DATE = dbo.GETDATEINT(getdate())
    SELECT @V_Originate = dbo.GETDATEINT(getdate()) --ISNULL(MAX(Originate),0)+1 FROM TServiceTasks

    --不需要更新其他数据库，故不使用分布式数据
    BEGIN TRANSACTION

    DECLARE CUR_GenerateServiceTasks CURSOR FOR
        SELECT ServiceType, ServiceTypeName, OffsetDays, ServiceWay, NoticeCaption, Executor,AutoFlag,TempID,TempTitle--,ExecRoleID
        FROM TServiceDefine
        WHERE IsValid = 1
    OPEN CUR_GenerateServiceTasks
    FETCH NEXT FROM CUR_GenerateServiceTasks INTO @V_ServiceType, @V_ServiceTypeName, @V_OffsetDays, @V_ServiceWay, @V_NoticeCaption, @V_Executor,@V_AutoFlag,@V_TempID,@V_TempTitle
    WHILE @@FETCH_STATUS = 0
    BEGIN
        SET @V_ActualDate = dbo.GETDATE(@IN_DATE,-@V_OffsetDays)
        --生日提醒: 服务日期=出生日期+OffsetDays
        IF @V_ServiceType = 1
        BEGIN
            SELECT @V_NoticeCaption = REPLACE(@V_NoticeCaption,N'%1',dbo.GETDATESTRYMD(@V_ActualDate))
           -- IF ISNULL(@V_ExecFlag,0)=1 AND ISNULL(@V_ExecRoleID,0)>0--执行人与执行角色同时执行,多加一条任务记录:给角色的任务
            --插入服务任务
            INSERT INTO TServiceTasks(ManagerID, CustomerCount, Executor, ServiceType, ServiceTypeName,Originate,
                    ServiceTitle, ServiceWay, StartDateTime, EndDateTime,AutoFlag,TempID,TempTitle)
                SELECT A.ManagerID, COUNT(A.ManagerID), ISNULL(@V_Executor,A.ManagerID), @V_ServiceType, @V_ServiceTypeName,@V_Originate,
                       @V_NoticeCaption, @V_ServiceWay, dbo.GetDateTime(@IN_DATE), dbo.GetDateTime(@V_ActualDate),@V_AutoFlag,@V_TempID,@V_TempTitle
                    FROM TCustManagers A, TCustomers B
                    WHERE  (A.ManagerID = B.SERVICE_MAN)
                        AND(A.ProvideServices & B.ReceiveServices & @V_ServiceType > 0)
                        AND(NOT EXISTS(SELECT 1 FROM TServiceTasks Z WHERE A.ManagerID = Z.ManagerID AND Z.Originate = @V_Originate
                                         AND Z.ServiceType = @V_ServiceType AND Z.StartDateTime = dbo.GetDateTime(@IN_DATE)))
                        AND(B.SERVICE_MAN = @IN_ManagerID OR @IN_ManagerID = 0 OR @IN_ManagerID IS NULL)
                        --以上条件公用(针对Implement之2,3,4, 及输入参数@IN_ManagerID)
                        AND(@V_ActualDate % 10000 = B.BIRTHDAY % 10000)
                        --以上条件过滤客户生日
                    GROUP BY A.ManagerID HAVING COUNT(A.ManagerID) > 0
            IF @@ERROR <> 0
            BEGIN
                CLOSE CUR_GenerateServiceTasks
                DEALLOCATE CUR_GenerateServiceTasks
                ROLLBACK TRANSACTION
                RETURN -100
            END
            --插入任务明细
            INSERT INTO TServiceTaskDetail(TaskSerialNO, ServiceType, ServiceTypeName, ServiceWay, TargetCustID,AutoFlag,Mobile,SmsContent)
                SELECT O.Serial_no, O.ServiceType, O.ServiceTypeName, O.ServiceWay, B.CUST_ID,@V_AutoFlag,B.MOBILE,REPLACE(ISNULL(T.Content,''),'%1',B.CUST_NAME)
                    FROM TCustManagers A, TCustomers B,
                         TServiceTasks O LEFT JOIN TSmsTemplates T ON (O.ServiceWay = '110905' AND O.TempID = T.TempID)
                    WHERE A.ManagerID = B.SERVICE_MAN AND O.ServiceType = @V_ServiceType AND O.Originate = @V_Originate
                        AND(A.ProvideServices & B.ReceiveServices & @V_ServiceType > 0)
                        AND(NOT EXISTS(SELECT 1 FROM TServiceTaskDetail Z WHERE O.Serial_no = Z.TaskSerialNO))
                        --以上条件公用(与插入任务部分条件相同)
                        AND O.ManagerID = A.ManagerID
                        AND(@V_ActualDate % 10000 = B.BIRTHDAY % 10000)
                        --以上条件为表关联，及数据过滤
                    ORDER BY O.Serial_no
            IF @@ERROR <> 0
            BEGIN
                CLOSE CUR_GenerateServiceTasks
                DEALLOCATE CUR_GenerateServiceTasks
                ROLLBACK TRANSACTION
                RETURN -100
            END
        END
        --纪念日提醒: 服务日期=纪念日+OffsetDays
        ELSE IF @V_ServiceType = 2
        BEGIN
            --待续
            SELECT @V_ActualDate = 20091125
        END
        --收益分配通知: 服务日期=收益分配日期+OffsetDays
        ELSE IF @V_ServiceType = 8
        BEGIN
            IF @V_DT_INTRUST = 1 --启用分布式
            BEGIN
                --插入服务任务
                INSERT INTO TServiceTasks(ManagerID, CustomerCount, Executor, ServiceType, ServiceTypeName,Originate,
                        ServiceTitle, ServiceWay, StartDateTime, EndDateTime,AutoFlag,TempID,TempTitle,InsertMan)
                    SELECT A.ManagerID, COUNT(A.ManagerID), ISNULL(@V_Executor,A.ManagerID), @V_ServiceType, @V_ServiceTypeName,@V_Originate,
                           REPLACE(@V_NoticeCaption,'%1',D.PRODUCT_NAME), @V_ServiceWay, dbo.GetDateTime(@IN_DATE), dbo.GetDateTime(@V_ActualDate),@V_AutoFlag,@V_TempID,@V_TempTitle,A.ManagerID
                        FROM TCustManagers A, TCustomers B, INTRUSTRISKDB.dbo.TTASKJHB C,
                             INTRUST.dbo.TPRODUCT D, INTRUST.dbo.TBENIFITOR E
                        WHERE  (A.ManagerID = B.SERVICE_MAN) AND D.INTRUST_FLAG1 = 2 --集合信托
                            AND(A.ProvideServices & B.ReceiveServices & @V_ServiceType > 0)
                            AND(NOT EXISTS(SELECT 1 FROM TServiceTasks Z WHERE A.ManagerID = Z.ManagerID AND Z.Originate = @V_Originate
                                             AND Z.ServiceType = @V_ServiceType AND Z.StartDateTime = dbo.GetDateTime(@IN_DATE)))
                            AND(B.SERVICE_MAN = @IN_ManagerID OR @IN_ManagerID = 0 OR @IN_ManagerID IS NULL)
                            --以上条件公用(针对Implement之2,3,4, 及输入参数@IN_ManagerID)
                            AND B.CUST_ID = E.CUST_ID AND E.PRODUCT_ID = C.PRODUCT_ID AND C.PRODUCT_ID = D.PRODUCT_ID
                            --以上条件为业务数据表间关联
                            AND(C.TASK_TYPE = 102) AND (@V_ActualDate = C.TASK_DATE)
                            --以上条件过滤102收益分配通知及日期
                        GROUP BY A.ManagerID, D.PRODUCT_NAME HAVING COUNT(A.ManagerID) > 0
                IF @@ERROR <> 0
                BEGIN
                    CLOSE CUR_GenerateServiceTasks
                    DEALLOCATE CUR_GenerateServiceTasks
                    ROLLBACK TRANSACTION
                    RETURN -100
                END
                --插入任务明细
                INSERT INTO TServiceTaskDetail(TaskSerialNO, ServiceType, ServiceTypeName, ServiceWay, TargetCustID,AutoFlag,Mobile,SmsContent)
                    SELECT O.Serial_no, O.ServiceType, O.ServiceTypeName, O.ServiceWay, B.CUST_ID,@V_AutoFlag,B.MOBILE,REPLACE(ISNULL(Content,''),'%1',B.CUST_NAME)
                        FROM TCustManagers A, TCustomers B, INTRUSTRISKDB.dbo.TTASKJHB C,
                             INTRUST.dbo.TPRODUCT D, INTRUST.dbo.TBENIFITOR E,
                             TServiceTasks O LEFT JOIN TSmsTemplates T ON (O.ServiceWay = '110905' AND O.TempID = T.TempID)
                        WHERE A.ManagerID = B.SERVICE_MAN AND O.ServiceType = @V_ServiceType AND O.Originate = @V_Originate
                            AND(A.ProvideServices & B.ReceiveServices & @V_ServiceType > 0)
                            AND(NOT EXISTS(SELECT 1 FROM TServiceTaskDetail Z WHERE O.Serial_no = Z.TaskSerialNO))
                            --以上条件公用(与插入任务部分条件相同)
                            AND O.ManagerID = A.ManagerID
                            AND B.CUST_ID = E.CUST_ID AND E.PRODUCT_ID = C.PRODUCT_ID AND C.PRODUCT_ID = D.PRODUCT_ID
                            AND(C.TASK_TYPE = 102) AND (@V_ActualDate = C.TASK_DATE)
                            --以上条件为表关联，及数据过滤
                        ORDER BY O.Serial_no
                IF @@ERROR <> 0
                BEGIN
                    CLOSE CUR_GenerateServiceTasks
                    DEALLOCATE CUR_GenerateServiceTasks
                    ROLLBACK TRANSACTION
                    RETURN -100
                END
            END
            ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
            BEGIN
                --插入服务任务
                INSERT INTO TServiceTasks(ManagerID, CustomerCount, Executor, ServiceType, ServiceTypeName,Originate,
                        ServiceTitle, ServiceWay, StartDateTime, EndDateTime,AutoFlag,TempID,TempTitle)
                    SELECT A.ManagerID, COUNT(A.ManagerID), ISNULL(@V_Executor,A.ManagerID), @V_ServiceType, @V_ServiceTypeName,@V_Originate,
                           REPLACE(@V_NoticeCaption,'%1',D.PRODUCT_NAME), @V_ServiceWay, dbo.GetDateTime(@IN_DATE), dbo.GetDateTime(@V_ActualDate),@V_AutoFlag,@V_TempID,@V_TempTitle
                        FROM TCustManagers A, TCustomers B, INTRUSTRISKDB.dbo.TTASKJHB C,
                             INTRUST.dbo.TPRODUCT D, INTRUST.dbo.TBENIFITOR E
                        WHERE  (A.ManagerID = B.SERVICE_MAN)
                            AND(A.ProvideServices & B.ReceiveServices & @V_ServiceType > 0)
                            AND(NOT EXISTS(SELECT 1 FROM TServiceTasks Z WHERE A.ManagerID = Z.ManagerID AND Z.Originate = @V_Originate
                                             AND Z.ServiceType = @V_ServiceType AND Z.StartDateTime = dbo.GetDateTime(@IN_DATE)))
                            AND(B.SERVICE_MAN = @IN_ManagerID OR @IN_ManagerID = 0 OR @IN_ManagerID IS NULL)
                            --以上条件公用(针对Implement之2,3,4, 及输入参数@IN_ManagerID)
                            AND B.CUST_ID = E.CUST_ID AND E.PRODUCT_ID = C.PRODUCT_ID AND C.PRODUCT_ID = D.PRODUCT_ID
                            --以上条件为业务数据表间关联
                            AND(C.TASK_TYPE = 102) AND (@V_ActualDate = C.TASK_DATE)
                            --以上条件过滤102收益分配通知及日期
                        GROUP BY A.ManagerID, D.PRODUCT_NAME HAVING COUNT(A.ManagerID) > 0
                IF @@ERROR <> 0
                BEGIN
                    CLOSE CUR_GenerateServiceTasks
                    DEALLOCATE CUR_GenerateServiceTasks
                    ROLLBACK TRANSACTION
                    RETURN -100
                END
                --插入任务明细
                INSERT INTO TServiceTaskDetail(TaskSerialNO, ServiceType, ServiceTypeName, ServiceWay, TargetCustID,AutoFlag,Mobile,SmsContent)
                    SELECT O.Serial_no, O.ServiceType, O.ServiceTypeName, O.ServiceWay, B.CUST_ID,@V_AutoFlag,B.MOBILE,REPLACE(Content,'%1',B.CUST_NAME)
                        FROM TCustManagers A, TCustomers B, INTRUSTRISKDB.dbo.TTASKJHB C,
                             INTRUST.dbo.TPRODUCT D, INTRUST.dbo.TBENIFITOR E,
                             TServiceTasks O LEFT JOIN TSmsTemplates T ON (O.ServiceWay = '110905' AND O.TempID = T.TempID)
                        WHERE A.ManagerID = B.SERVICE_MAN AND O.ServiceType = @V_ServiceType AND O.Originate = @V_Originate
                            AND(A.ProvideServices & B.ReceiveServices & @V_ServiceType > 0)
                            AND(NOT EXISTS(SELECT 1 FROM TServiceTaskDetail Z WHERE O.Serial_no = Z.TaskSerialNO))
                            --以上条件公用(与插入任务部分条件相同)
                            AND O.ManagerID = A.ManagerID
                            AND B.CUST_ID = E.CUST_ID AND E.PRODUCT_ID = C.PRODUCT_ID AND C.PRODUCT_ID = D.PRODUCT_ID
                            AND(C.TASK_TYPE = 102) AND (@V_ActualDate = C.TASK_DATE)
                            --以上条件为表关联，及数据过滤
                        ORDER BY O.Serial_no
                IF @@ERROR <> 0
                BEGIN
                    CLOSE CUR_GenerateServiceTasks
                    DEALLOCATE CUR_GenerateServiceTasks
                    ROLLBACK TRANSACTION
                    RETURN -100
                END
            END
        END --收益分配通知END
        --产品到期通知: 服务日期=产品到期日期+OffsetDays
        ELSE IF @V_ServiceType = 16
        BEGIN
            IF @V_DT_INTRUST = 1 --启用分布式
            BEGIN
                --插入服务任务
                INSERT INTO TServiceTasks(ManagerID, CustomerCount, Executor, ServiceType, ServiceTypeName,Originate,
                        ServiceTitle, ServiceWay, StartDateTime, EndDateTime,AutoFlag,TempID,TempTitle)
                    SELECT A.ManagerID, COUNT(A.ManagerID), ISNULL(@V_Executor,A.ManagerID), @V_ServiceType, @V_ServiceTypeName,@V_Originate,
                           REPLACE(@V_NoticeCaption,'%1',D.PRODUCT_NAME), @V_ServiceWay, dbo.GetDateTime(@IN_DATE), dbo.GetDateTime(@V_ActualDate),@V_AutoFlag,@V_TempID,@V_TempTitle
                        FROM TCustManagers A, TCustomers B, INTRUSTRISKDB.dbo.TTASKJHB C,
                             INTRUST.dbo.TPRODUCT D, INTRUST.dbo.TBENIFITOR E
                        WHERE  (A.ManagerID = B.SERVICE_MAN)
                            AND(A.ProvideServices & B.ReceiveServices & @V_ServiceType > 0)
                            AND(NOT EXISTS(SELECT 1 FROM TServiceTasks Z WHERE A.ManagerID = Z.ManagerID AND Z.Originate = @V_Originate
                                             AND Z.ServiceType = @V_ServiceType AND Z.StartDateTime = dbo.GetDateTime(@IN_DATE)))
                            AND(B.SERVICE_MAN = @IN_ManagerID OR @IN_ManagerID = 0 OR @IN_ManagerID IS NULL)
                            --以上条件公用(针对Implement之2,3,4, 及输入参数@IN_ManagerID)
                            AND B.CUST_ID = E.CUST_ID AND E.PRODUCT_ID = C.PRODUCT_ID AND C.PRODUCT_ID = D.PRODUCT_ID
                            --以上条件为业务数据表间关联
                            AND(C.TASK_TYPE = 101) AND (@V_ActualDate = C.TASK_DATE)
                            --以上条件过滤102收益分配通知及日期
                        GROUP BY A.ManagerID, D.PRODUCT_NAME HAVING COUNT(A.ManagerID) > 0
                IF @@ERROR <> 0
                BEGIN
                    CLOSE CUR_GenerateServiceTasks
                    DEALLOCATE CUR_GenerateServiceTasks
                    ROLLBACK TRANSACTION
                    RETURN -100
                END
                --插入任务明细
                INSERT INTO TServiceTaskDetail(TaskSerialNO, ServiceType, ServiceTypeName, ServiceWay, TargetCustID,AutoFlag,Mobile,SmsContent)
                    SELECT O.Serial_no, O.ServiceType, O.ServiceTypeName, O.ServiceWay, B.CUST_ID,@V_AutoFlag,B.MOBILE,REPLACE(Content,'%1',B.CUST_NAME)
                        FROM TCustManagers A, TCustomers B, INTRUSTRISKDB.dbo.TTASKJHB C,
                             INTRUST.dbo.TPRODUCT D, INTRUST.dbo.TBENIFITOR E,
                             TServiceTasks O LEFT JOIN TSmsTemplates T ON (O.ServiceWay = '110905' AND O.TempID = T.TempID)
                        WHERE A.ManagerID = B.SERVICE_MAN AND O.ServiceType = @V_ServiceType AND O.Originate = @V_Originate
                            AND(A.ProvideServices & B.ReceiveServices & @V_ServiceType > 0)
                            AND(NOT EXISTS(SELECT 1 FROM TServiceTaskDetail Z WHERE O.Serial_no = Z.TaskSerialNO))
                            --以上条件公用(与插入任务部分条件相同)
                            AND O.ManagerID = A.ManagerID
                            AND B.CUST_ID = E.CUST_ID AND E.PRODUCT_ID = C.PRODUCT_ID AND C.PRODUCT_ID = D.PRODUCT_ID
                            AND(C.TASK_TYPE = 101) AND (@V_ActualDate = C.TASK_DATE)
                            --以上条件为表关联，及数据过滤
                        ORDER BY O.Serial_no
                IF @@ERROR <> 0
                BEGIN
                    CLOSE CUR_GenerateServiceTasks
                    DEALLOCATE CUR_GenerateServiceTasks
                    ROLLBACK TRANSACTION
                    RETURN -100
                END
            END
            ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
            BEGIN
                --插入服务任务
                INSERT INTO TServiceTasks(ManagerID, CustomerCount, Executor, ServiceType, ServiceTypeName,Originate,
                        ServiceTitle, ServiceWay, StartDateTime, EndDateTime,AutoFlag,TempID,TempTitle)
                    SELECT A.ManagerID, COUNT(A.ManagerID), ISNULL(@V_Executor,A.ManagerID), @V_ServiceType, @V_ServiceTypeName,@V_Originate,
                           REPLACE(@V_NoticeCaption,'%1',D.PRODUCT_NAME), @V_ServiceWay, dbo.GetDateTime(@IN_DATE), dbo.GetDateTime(@V_ActualDate),@V_AutoFlag,@V_TempID,@V_TempTitle
                        FROM TCustManagers A, TCustomers B, INTRUSTRISKDB.dbo.TTASKJHB C,
                             INTRUST.dbo.TPRODUCT D, INTRUST.dbo.TBENIFITOR E
                        WHERE  (A.ManagerID = B.SERVICE_MAN)
                            AND(A.ProvideServices & B.ReceiveServices & @V_ServiceType > 0)
                            AND(NOT EXISTS(SELECT 1 FROM TServiceTasks Z WHERE A.ManagerID = Z.ManagerID AND Z.Originate = @V_Originate
                                             AND Z.ServiceType = @V_ServiceType AND Z.StartDateTime = dbo.GetDateTime(@IN_DATE)))
                            AND(B.SERVICE_MAN = @IN_ManagerID OR @IN_ManagerID = 0 OR @IN_ManagerID IS NULL)
                            --以上条件公用(针对Implement之2,3,4, 及输入参数@IN_ManagerID)
                            AND B.CUST_ID = E.CUST_ID AND E.PRODUCT_ID = C.PRODUCT_ID AND C.PRODUCT_ID = D.PRODUCT_ID
                            --以上条件为业务数据表间关联
                            AND(C.TASK_TYPE = 101) AND (@V_ActualDate = C.TASK_DATE)
                            --以上条件过滤102收益分配通知及日期
                        GROUP BY A.ManagerID, D.PRODUCT_NAME HAVING COUNT(A.ManagerID) > 0
                IF @@ERROR <> 0
                BEGIN
                    CLOSE CUR_GenerateServiceTasks
                    DEALLOCATE CUR_GenerateServiceTasks
                    ROLLBACK TRANSACTION
                    RETURN -100
                END
                --插入任务明细
                INSERT INTO TServiceTaskDetail(TaskSerialNO, ServiceType, ServiceTypeName, ServiceWay, TargetCustID,AutoFlag,Mobile,SmsContent)
                    SELECT O.Serial_no, O.ServiceType, O.ServiceTypeName, O.ServiceWay, B.CUST_ID,@V_AutoFlag,B.MOBILE,REPLACE(Content,'%1',B.CUST_NAME)
                        FROM TCustManagers A, TCustomers B, INTRUSTRISKDB.dbo.TTASKJHB C,
                             INTRUST.dbo.TPRODUCT D, INTRUST.dbo.TBENIFITOR E,
                             TServiceTasks O LEFT JOIN TSmsTemplates T ON (O.ServiceWay = '110905' AND O.TempID = T.TempID)
                        WHERE A.ManagerID = B.SERVICE_MAN AND O.ServiceType = @V_ServiceType AND O.Originate = @V_Originate
                            AND(A.ProvideServices & B.ReceiveServices & @V_ServiceType > 0)
                            AND(NOT EXISTS(SELECT 1 FROM TServiceTaskDetail Z WHERE O.Serial_no = Z.TaskSerialNO))
                            --以上条件公用(与插入任务部分条件相同)
                            AND O.ManagerID = A.ManagerID
                            AND B.CUST_ID = E.CUST_ID AND E.PRODUCT_ID = C.PRODUCT_ID AND C.PRODUCT_ID = D.PRODUCT_ID
                            AND(C.TASK_TYPE = 101) AND (@V_ActualDate = C.TASK_DATE)
                            --以上条件为表关联，及数据过滤
                        ORDER BY O.Serial_no
                IF @@ERROR <> 0
                BEGIN
                    CLOSE CUR_GenerateServiceTasks
                    DEALLOCATE CUR_GenerateServiceTasks
                    ROLLBACK TRANSACTION
                    RETURN -100
                END
            END
        END --产品到期通知END
        --净值发布通知: 服务日期=净值发布日期+OffsetDays
        ELSE IF @V_ServiceType = 32
        BEGIN
            IF @V_DT_INTRUST = 1 --启用分布式
            BEGIN
                --插入服务任务
                INSERT INTO TServiceTasks(ManagerID, CustomerCount, Executor, ServiceType, ServiceTypeName,Originate,
                        ServiceTitle, ServiceWay, StartDateTime, EndDateTime,AutoFlag,TempID,TempTitle)
                    SELECT A.ManagerID, COUNT(A.ManagerID), ISNULL(@V_Executor,A.ManagerID), @V_ServiceType, @V_ServiceTypeName,@V_Originate,
                           REPLACE(@V_NoticeCaption,'%1',C.PRODUCT_NAME), @V_ServiceWay, dbo.GetDateTime(@IN_DATE), dbo.GetDateTime(@V_ActualDate),@V_AutoFlag,@V_TempID,@V_TempTitle
                        FROM TCustManagers A, TCustomers B, INTRUSTHistory.dbo.HNAVPRICEINFO C,
                             INTRUST.dbo.TBENIFITOR E
                        WHERE  (A.ManagerID = B.SERVICE_MAN)
                            AND(A.ProvideServices & B.ReceiveServices & @V_ServiceType > 0)
                            AND(NOT EXISTS(SELECT 1 FROM TServiceTasks Z WHERE A.ManagerID = Z.ManagerID AND Z.Originate = @V_Originate
                                             AND Z.ServiceType = @V_ServiceType AND Z.StartDateTime = dbo.GetDateTime(@IN_DATE)))
                            AND(B.SERVICE_MAN = @IN_ManagerID OR @IN_ManagerID = 0 OR @IN_ManagerID IS NULL)
                            --以上条件公用(针对Implement之2,3,4, 及输入参数@IN_ManagerID)
                            AND B.CUST_ID = E.CUST_ID AND E.PRODUCT_ID = C.PRODUCT_ID
                            --以上条件为业务数据表间关联
                            AND(C.PUB_FLAG = 2) AND (@V_ActualDate = C.HQ_DATE)
                            --以上条件过滤102收益分配通知及日期
                        GROUP BY A.ManagerID, C.PRODUCT_NAME HAVING COUNT(A.ManagerID) > 0
                IF @@ERROR <> 0
                BEGIN
                    CLOSE CUR_GenerateServiceTasks
                    DEALLOCATE CUR_GenerateServiceTasks
                    ROLLBACK TRANSACTION
                    RETURN -100
                END
                --插入任务明细
                INSERT INTO TServiceTaskDetail(TaskSerialNO, ServiceType, ServiceTypeName, ServiceWay, TargetCustID,AutoFlag,Mobile,SmsContent)
                    SELECT O.Serial_no, O.ServiceType, O.ServiceTypeName, O.ServiceWay, B.CUST_ID,@V_AutoFlag,B.MOBILE,REPLACE(Content,'%1',B.CUST_NAME)
                        FROM TCustManagers A, TCustomers B, INTRUSTHistory.dbo.HNAVPRICEINFO C,
                             INTRUST.dbo.TBENIFITOR E,
                             TServiceTasks O LEFT JOIN TSmsTemplates T ON (O.ServiceWay = '110905' AND O.TempID = T.TempID)
                        WHERE A.ManagerID = B.SERVICE_MAN AND O.ServiceType = @V_ServiceType AND O.Originate = @V_Originate
                            AND(A.ProvideServices & B.ReceiveServices & @V_ServiceType > 0)
                            AND(NOT EXISTS(SELECT 1 FROM TServiceTaskDetail Z WHERE O.Serial_no = Z.TaskSerialNO))
                            --以上条件公用(与插入任务部分条件相同)
                            AND O.ManagerID = A.ManagerID
                            AND B.CUST_ID = E.CUST_ID AND E.PRODUCT_ID = C.PRODUCT_ID
                            AND(C.PUB_FLAG = 2) AND (@V_ActualDate = C.HQ_DATE)
                            --以上条件为表关联，及数据过滤
                        ORDER BY O.Serial_no
                IF @@ERROR <> 0
                BEGIN
                    CLOSE CUR_GenerateServiceTasks
                    DEALLOCATE CUR_GenerateServiceTasks
                    ROLLBACK TRANSACTION
                    RETURN -100
                END
            END
            ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
            BEGIN
                --插入服务任务
                INSERT INTO TServiceTasks(ManagerID, CustomerCount, Executor, ServiceType, ServiceTypeName,Originate,
                        ServiceTitle, ServiceWay, StartDateTime, EndDateTime,AutoFlag,TempID,TempTitle)
                    SELECT A.ManagerID, COUNT(A.ManagerID), ISNULL(@V_Executor,A.ManagerID), @V_ServiceType, @V_ServiceTypeName,@V_Originate,
                           REPLACE(@V_NoticeCaption,'%1',C.PRODUCT_NAME), @V_ServiceWay, dbo.GetDateTime(@IN_DATE), dbo.GetDateTime(@V_ActualDate),@V_AutoFlag,@V_TempID,@V_TempTitle
                        FROM TCustManagers A, TCustomers B, INTRUSTHistory.dbo.HNAVPRICEINFO C,
                             INTRUST.dbo.TBENIFITOR E
                        WHERE  (A.ManagerID = B.SERVICE_MAN)
                            AND(A.ProvideServices & B.ReceiveServices & @V_ServiceType > 0)
                            AND(NOT EXISTS(SELECT 1 FROM TServiceTasks Z WHERE A.ManagerID = Z.ManagerID AND Z.Originate = @V_Originate
                                             AND Z.ServiceType = @V_ServiceType AND Z.StartDateTime = dbo.GetDateTime(@IN_DATE)))
                            AND(B.SERVICE_MAN = @IN_ManagerID OR @IN_ManagerID = 0 OR @IN_ManagerID IS NULL)
                            --以上条件公用(针对Implement之2,3,4, 及输入参数@IN_ManagerID)
                            AND B.CUST_ID = E.CUST_ID AND E.PRODUCT_ID = C.PRODUCT_ID
                            --以上条件为业务数据表间关联
                            AND(C.PUB_FLAG = 2) AND (@V_ActualDate = C.HQ_DATE)
                            --以上条件过滤102收益分配通知及日期
                        GROUP BY A.ManagerID, C.PRODUCT_NAME HAVING COUNT(A.ManagerID) > 0
                IF @@ERROR <> 0
                BEGIN
                    CLOSE CUR_GenerateServiceTasks
                    DEALLOCATE CUR_GenerateServiceTasks
                    ROLLBACK TRANSACTION
                    RETURN -100
                END
                --插入任务明细
                INSERT INTO TServiceTaskDetail(TaskSerialNO, ServiceType, ServiceTypeName, ServiceWay, TargetCustID,AutoFlag,Mobile,SmsContent)
                    SELECT O.Serial_no, O.ServiceType, O.ServiceTypeName, O.ServiceWay, B.CUST_ID,@V_AutoFlag,B.MOBILE,REPLACE(Content,'%1',B.CUST_NAME)
                        FROM TCustManagers A, TCustomers B, INTRUSTHistory.dbo.HNAVPRICEINFO C,
                             INTRUST.dbo.TBENIFITOR E,
                             TServiceTasks O LEFT JOIN TSmsTemplates T ON (O.ServiceWay = '110905' AND O.TempID = T.TempID)
                        WHERE A.ManagerID = B.SERVICE_MAN AND O.ServiceType = @V_ServiceType AND O.Originate = @V_Originate
                            AND(A.ProvideServices & B.ReceiveServices & @V_ServiceType > 0)
                            AND(NOT EXISTS(SELECT 1 FROM TServiceTaskDetail Z WHERE O.Serial_no = Z.TaskSerialNO))
                            --以上条件公用(与插入任务部分条件相同)
                            AND O.ManagerID = A.ManagerID
                            AND B.CUST_ID = E.CUST_ID AND E.PRODUCT_ID = C.PRODUCT_ID
                            AND(C.PUB_FLAG = 2) AND (@V_ActualDate = C.HQ_DATE)
                            --以上条件为表关联，及数据过滤
                        ORDER BY O.Serial_no
                IF @@ERROR <> 0
                BEGIN
                    CLOSE CUR_GenerateServiceTasks
                    DEALLOCATE CUR_GenerateServiceTasks
                    ROLLBACK TRANSACTION
                    RETURN -100
                END
            END
        END
        --20110426 dongyg 添加对证件有效期的判断，将证件即将过期的客户作为任务处理，过期后若任务未处理自动处理
        --  作为一种任务定义在“服务定义表”中，但这个不需要判断客户经理是否提供服务、客户是否接受服务
        ELSE IF @V_ServiceType = 256
        BEGIN
            SELECT @V_NoticeCaption = REPLACE(@V_NoticeCaption,'%1',dbo.GETDATESTRYMD(@V_ActualDate))
            --插入服务任务
            INSERT INTO TServiceTasks(ManagerID, CustomerCount, Executor, ServiceType, ServiceTypeName,Originate,
                    ServiceTitle, ServiceWay, StartDateTime, EndDateTime,AutoFlag,TempID,TempTitle)
                SELECT A.ManagerID, COUNT(A.ManagerID), ISNULL(@V_Executor,A.ManagerID), @V_ServiceType, @V_ServiceTypeName,@V_Originate,
                       @V_NoticeCaption, @V_ServiceWay, dbo.GetDateTime(@IN_DATE), dbo.GetDateTime(@V_ActualDate),@V_AutoFlag,@V_TempID,@V_TempTitle
                    FROM TCustManagers A, TCustomers B
                    WHERE  (A.ManagerID = B.SERVICE_MAN)
                        AND(NOT EXISTS(SELECT 1 FROM TServiceTasks Z WHERE A.ManagerID = Z.ManagerID AND Z.Originate = @V_Originate
                                         AND Z.ServiceType = @V_ServiceType AND Z.StartDateTime = dbo.GetDateTime(@IN_DATE)))
                        AND(B.SERVICE_MAN = @IN_ManagerID OR @IN_ManagerID = 0 OR @IN_ManagerID IS NULL)
                        --以上条件公用(针对Implement之2,3,4, 及输入参数@IN_ManagerID)
                        AND(@V_ActualDate <= B.CARD_VALID_DATE OR B.CARD_VALID_DATE IS NULL)
                        --受益人表中 余额大于零的提示
                       AND(EXISTS(SELECT E.CUST_ID FROM INTRUST.dbo.TBENIFITOR E WHERE B.CUST_ID = E.CUST_ID GROUP BY CUST_ID HAVING SUM(E.BEN_AMOUNT)>0))
                        --以上条件过滤证件有效期
                    GROUP BY A.ManagerID HAVING COUNT(A.ManagerID) > 0
            IF @@ERROR <> 0
            BEGIN
                CLOSE CUR_GenerateServiceTasks
                DEALLOCATE CUR_GenerateServiceTasks
                ROLLBACK TRANSACTION
                RETURN -100
            END
            --插入任务明细
            INSERT INTO TServiceTaskDetail(TaskSerialNO, ServiceType, ServiceTypeName, ServiceWay, TargetCustID, Result)
                SELECT O.Serial_no, O.ServiceType, O.ServiceTypeName, O.ServiceWay, B.CUST_ID, B.CUST_NAME+'('+CONVERT(NVARCHAR(8),B.CARD_VALID_DATE)+')'
                    FROM TCustManagers A, TCustomers B, TServiceTasks O
                    WHERE A.ManagerID = B.SERVICE_MAN AND O.ServiceType = @V_ServiceType AND O.Originate = @V_Originate
                        AND(NOT EXISTS(SELECT 1 FROM TServiceTaskDetail Z WHERE O.Serial_no = Z.TaskSerialNO))
                        --以上条件公用(与插入任务部分条件相同)
                        AND O.ManagerID = A.ManagerID
                        AND(@V_ActualDate <= B.CARD_VALID_DATE OR B.CARD_VALID_DATE IS NULL)
                        --受益人表中 余额大于零的提示
                        AND(EXISTS(SELECT E.CUST_ID FROM INTRUST.dbo.TBENIFITOR E WHERE B.CUST_ID = E.CUST_ID GROUP BY CUST_ID HAVING SUM(E.BEN_AMOUNT)>0))
                        --以上条件为表关联，及数据过滤
                    ORDER BY O.Serial_no
            IF @@ERROR <> 0
            BEGIN
                CLOSE CUR_GenerateServiceTasks
                DEALLOCATE CUR_GenerateServiceTasks
                ROLLBACK TRANSACTION
                RETURN -100
            END
        END
        --20120110 DONGYG 添加节日问候服务任务处理
        ELSE IF @V_ServiceType = 512
        BEGIN
            DECLARE @V_HOLIDAY_NAME NVARCHAR(60), @V_SMS_GREETING NVARCHAR(500)
            --如果匹配到需要生成任务的节日
            IF EXISTS(SELECT 1 FROM V_HOLIDAYS WHERE DC_DATEINT = @V_ActualDate AND CREATE_TASK = 1)
            BEGIN
                SELECT @V_HOLIDAY_NAME = HOLIDAY_NAME, @V_SMS_GREETING = SMS_GREETING
                    FROM V_HOLIDAYS WHERE DC_DATEINT = @V_ActualDate AND CREATE_TASK = 1
                SELECT @V_NoticeCaption = REPLACE(@V_NoticeCaption,'%1',@V_HOLIDAY_NAME)
                --插入服务任务
                INSERT INTO TServiceTasks(ManagerID, CustomerCount, Executor, ServiceType, ServiceTypeName,Originate,
                        ServiceTitle, ServiceWay, StartDateTime, EndDateTime,AutoFlag)
                    SELECT A.ManagerID, COUNT(A.ManagerID), ISNULL(@V_Executor,A.ManagerID), @V_ServiceType, @V_ServiceTypeName,@V_Originate,
                           @V_NoticeCaption, @V_ServiceWay, dbo.GetDateTime(@IN_DATE), dbo.GetDateTime(@V_ActualDate),@V_AutoFlag
                        FROM TCustManagers A, TCustomers B
                        WHERE  (A.ManagerID = B.SERVICE_MAN)
                            AND(A.ProvideServices & B.ReceiveServices & @V_ServiceType > 0)
                            AND(NOT EXISTS(SELECT 1 FROM TServiceTasks Z WHERE A.ManagerID = Z.ManagerID AND Z.Originate = @V_Originate
                                             AND Z.ServiceType = @V_ServiceType AND Z.StartDateTime = dbo.GetDateTime(@IN_DATE)))
                            AND(B.SERVICE_MAN = @IN_ManagerID OR @IN_ManagerID = 0 OR @IN_ManagerID IS NULL)
                            --以上条件公用(针对Implement之2,3,4, 及输入参数@IN_ManagerID)
                        GROUP BY A.ManagerID HAVING COUNT(A.ManagerID) > 0
                IF @@ERROR <> 0
                BEGIN
                    CLOSE CUR_GenerateServiceTasks
                    DEALLOCATE CUR_GenerateServiceTasks
                    ROLLBACK TRANSACTION
                    RETURN -100
                END
                --插入任务明细
                INSERT INTO TServiceTaskDetail(TaskSerialNO, ServiceType, ServiceTypeName, ServiceWay, TargetCustID,AutoFlag,Mobile,SmsContent)
                    SELECT O.Serial_no, O.ServiceType, O.ServiceTypeName, O.ServiceWay, B.CUST_ID,@V_AutoFlag,B.MOBILE,REPLACE(ISNULL(@V_SMS_GREETING,''),'%1',B.CUST_NAME)
                        FROM TCustManagers A, TCustomers B, TServiceTasks O
                        WHERE A.ManagerID = B.SERVICE_MAN AND O.ServiceType = @V_ServiceType AND O.Originate = @V_Originate
                            AND(A.ProvideServices & B.ReceiveServices & @V_ServiceType > 0)
                            AND(NOT EXISTS(SELECT 1 FROM TServiceTaskDetail Z WHERE O.Serial_no = Z.TaskSerialNO))
                            --以上条件公用(与插入任务部分条件相同)
                            AND O.ManagerID = A.ManagerID
                            --以上条件为表关联，及数据过滤
                        ORDER BY O.Serial_no
                IF @@ERROR <> 0
                BEGIN
                    CLOSE CUR_GenerateServiceTasks
                    DEALLOCATE CUR_GenerateServiceTasks
                    ROLLBACK TRANSACTION
                    RETURN -100
                END
            END
        END
        --产品到期提醒: 当作任务产生，提前天数，任务定义里设置
        ELSE IF @V_ServiceType = 1024
        BEGIN
			IF @V_DT_INTRUST = 1 --启用分布式
            BEGIN
                --插入服务任务
                INSERT INTO TServiceTasks(ManagerID, CustomerCount, Executor, ServiceType, ServiceTypeName,Originate,
                        ServiceTitle, ServiceWay, StartDateTime, EndDateTime,AutoFlag,TempID,TempTitle,PRODUCT_ID)
                    SELECT B.SERVICE_MAN, COUNT(B.SERVICE_MAN), ISNULL(@V_Executor,B.SERVICE_MAN), @V_ServiceType, @V_ServiceTypeName,@V_Originate,
                           REPLACE(@V_NoticeCaption,'%1',D.PRODUCT_NAME), @V_ServiceWay, dbo.GetDateTime(@IN_DATE), dbo.GetDateTime(@V_ActualDate),@V_AutoFlag,@V_TempID,@V_TempTitle,A.PRODUCT_ID
                        FROM INTRUST.dbo.TBENIFITOR A LEFT JOIN TCustomers B ON A.CUST_ID=B.CUST_ID
                             LEFT JOIN INTRUST.dbo.TPRODUCT D ON A.PRODUCT_ID=D.PRODUCT_ID
                        WHERE (B.SERVICE_MAN = @IN_ManagerID OR @IN_ManagerID = 0 OR @IN_ManagerID IS NULL)
                            AND (A.BEN_END_DATE<=@V_ActualDate) --结束日期在提醒日期内
                            AND A.BEN_END_DATE>=@IN_DATE --结束日期大于当天
                            AND A.PRODUCT_ID NOT IN (SELECT PRODUCT_ID FROM TServiceTasks WHERE ServiceType=1024 AND PRODUCT_ID IS NOT NULL)--已提醒过的客户就不再提醒
                        GROUP BY B.SERVICE_MAN, D.PRODUCT_NAME,A.PRODUCT_ID HAVING COUNT(B.SERVICE_MAN) > 0
                IF @@ERROR <> 0
                BEGIN
                    CLOSE CUR_GenerateServiceTasks
                    DEALLOCATE CUR_GenerateServiceTasks
                    ROLLBACK TRANSACTION
                    RETURN -100
                END
                --插入任务明细
                INSERT INTO TServiceTaskDetail(TaskSerialNO, ServiceType, ServiceTypeName, ServiceWay, TargetCustID,AutoFlag)
                    SELECT O.Serial_no, O.ServiceType, O.ServiceTypeName, O.ServiceWay, B.CUST_ID,@V_AutoFlag
                        FROM INTRUST.dbo.TBENIFITOR A LEFT JOIN TCustomers B ON A.CUST_ID=B.CUST_ID--, TCustomers B, INTRUSTRISKDB.dbo.TTASKJHB C,
							LEFT JOIN TServiceTasks O ON (O.ManagerID=B.SERVICE_MAN AND O.ServiceType=1024)
                        WHERE O.Serial_no IS NOT NULL
                            AND (B.SERVICE_MAN = @IN_ManagerID OR @IN_ManagerID = 0 OR @IN_ManagerID IS NULL)
                            AND (A.BEN_END_DATE<=@V_ActualDate) --结束日期在提醒日期内
                            AND A.BEN_END_DATE>=@IN_DATE --结束日期大于当天
                            AND NOT EXISTS(SELECT * FROM TServiceTaskDetail WHERE ServiceType=1024 AND TaskSerialNO=O.Serial_no)--已提醒过的客户就不再提醒
                        ORDER BY O.Serial_no
                IF @@ERROR <> 0
                BEGIN
                    CLOSE CUR_GenerateServiceTasks
                    DEALLOCATE CUR_GenerateServiceTasks
                    ROLLBACK TRANSACTION
                    RETURN -100
                END
            END
            ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
            BEGIN
                --插入服务任务
                INSERT INTO TServiceTasks(ManagerID, CustomerCount, Executor, ServiceType, ServiceTypeName,Originate,
                        ServiceTitle, ServiceWay, StartDateTime, EndDateTime,AutoFlag,TempID,TempTitle,PRODUCT_ID)
                    SELECT B.SERVICE_MAN, COUNT(B.SERVICE_MAN), ISNULL(@V_Executor,B.SERVICE_MAN), @V_ServiceType, @V_ServiceTypeName,@V_Originate,
                           REPLACE(@V_NoticeCaption,'%1',D.PRODUCT_NAME), @V_ServiceWay, dbo.GetDateTime(@IN_DATE), dbo.GetDateTime(@V_ActualDate),@V_AutoFlag,@V_TempID,@V_TempTitle,A.PRODUCT_ID
                        FROM INTRUST.dbo.TBENIFITOR A LEFT JOIN TCustomers B ON A.CUST_ID=B.CUST_ID
                             LEFT JOIN INTRUST.dbo.TPRODUCT D ON A.PRODUCT_ID=D.PRODUCT_ID
                        WHERE (B.SERVICE_MAN = @IN_ManagerID OR @IN_ManagerID = 0 OR @IN_ManagerID IS NULL)
                            AND (A.BEN_END_DATE<=@V_ActualDate) --结束日期在提醒日期内
                            AND A.BEN_END_DATE>=@IN_DATE --结束日期大于当天
                            AND A.PRODUCT_ID NOT IN (SELECT PRODUCT_ID FROM TServiceTasks WHERE ServiceType=1024 AND PRODUCT_ID IS NOT NULL)--已提醒过的客户就不再提醒
                        GROUP BY B.SERVICE_MAN, D.PRODUCT_NAME,A.PRODUCT_ID HAVING COUNT(B.SERVICE_MAN) > 0
                IF @@ERROR <> 0
                BEGIN
                    CLOSE CUR_GenerateServiceTasks
                    DEALLOCATE CUR_GenerateServiceTasks
                    ROLLBACK TRANSACTION
                    RETURN -100
                END
                --插入任务明细
                INSERT INTO TServiceTaskDetail(TaskSerialNO, ServiceType, ServiceTypeName, ServiceWay, TargetCustID,AutoFlag)
                    SELECT O.Serial_no, O.ServiceType, O.ServiceTypeName, O.ServiceWay, B.CUST_ID,@V_AutoFlag
                        FROM INTRUST.dbo.TBENIFITOR A LEFT JOIN TCustomers B ON A.CUST_ID=B.CUST_ID--, TCustomers B, INTRUSTRISKDB.dbo.TTASKJHB C,
							LEFT JOIN TServiceTasks O ON (O.ManagerID=B.SERVICE_MAN AND O.ServiceType=1024)
                        WHERE O.Serial_no IS NOT NULL
                            AND (B.SERVICE_MAN = @IN_ManagerID OR @IN_ManagerID = 0 OR @IN_ManagerID IS NULL)
                            AND (A.BEN_END_DATE<=@V_ActualDate) --结束日期在提醒日期内
                            AND A.BEN_END_DATE>=@IN_DATE --结束日期大于当天
                            AND NOT EXISTS(SELECT * FROM TServiceTaskDetail WHERE ServiceType=1024 AND TaskSerialNO=O.Serial_no)--已提醒过的客户就不再提醒
                        ORDER BY O.Serial_no
                IF @@ERROR <> 0
                BEGIN
                    CLOSE CUR_GenerateServiceTasks
                    DEALLOCATE CUR_GenerateServiceTasks
                    ROLLBACK TRANSACTION
                    RETURN -100
                END
            END
        END --产品到期提醒END
        FETCH NEXT FROM CUR_GenerateServiceTasks INTO @V_ServiceType, @V_ServiceTypeName, @V_OffsetDays, @V_ServiceWay, @V_NoticeCaption, @V_Executor,@V_AutoFlag,@V_TempID,@V_TempTitle
    END
    CLOSE CUR_GenerateServiceTasks
    DEALLOCATE CUR_GenerateServiceTasks
    --20110426 dongyg 证件到期提醒任务过期自动结束
    UPDATE TServiceTasks
        SET CompleteTime = EndDateTime, Status = 4, CompleteCount = CustomerCount
        WHERE Status < 4 AND dbo.GETDATEINT(EndDateTime) = @IN_DATE AND ServiceType = 256
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    UPDATE TServiceTaskDetail
        SET ExecuteTime = B.EndDateTime, Status = 4
        FROM TServiceTaskDetail A, TServiceTasks B
        WHERE A.TaskSerialNO = B.Serial_no AND A.Status < 4 AND dbo.GETDATEINT(B.EndDateTime) = @IN_DATE AND A.ServiceType = 256
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    COMMIT TRANSACTION
    SET XACT_ABORT OFF
GO
