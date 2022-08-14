﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCUSTSERVICELOG_BYCUST
								@IN_CUST_ID   		INT,         --客户ID
                                @IN_INPUT_MAN 		INTEGER      --操作员
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    --------------------------------------------------------------------
    
    CREATE TABLE #TMP_QUERY_TCustomerMaintenance(
        SERIAL_NO        INT DEFAULT 0,     
        CUST_ID          INT,
        CUST_NO          NVARCHAR(30),
        CUST_NAME        NVARCHAR(100),
        SERVICE_TIME     DATETIME,       --服务、维护时间
        SERVICE_INFO     NVARCHAR(100),  --服务项目，根据实际项目以文本形式返回
        SERVICE_SUMMARY  NVARCHAR(500),  --服务项目的详细描述，如发短信可以包含短信内容、电话可以包含电话记录及语音回放、活动可以包含活动内容、费用等
		SERVICE_MAN		 INT,			 --客户经理编号
        SERVICE_MAN_NAME NVARCHAR(30),   --客户经理
		EXECUTOR		 INT,			 --执行者
        EXECUTOR_NAME    NVARCHAR(30),   --服务实际执行人
		CONTENT 		 NVARCHAR(MAX),	 --交流内容
		SUBJECT			 NVARCHAR(60),	 --交流主题
		INFO_LEVEL		 NVARCHAR(10),	 --信息级别
		STEP_FLAG		 NVARCHAR(10),	 --工作进展标志
        DATA_FLAG        NVARCHAR(10)    --系统 或 人工
    )
    --查询各类服务记录插入到临时表
    --服务任务明细表
    /*INSERT INTO #TMP_QUERY_TCustomerMaintenance(CUST_ID,CUST_NO,CUST_NAME,SERVICE_TIME,SERVICE_INFO,SERVICE_SUMMARY,SERVICE_MAN_NAME,EXECUTOR_NAME,DATA_FLAG,SERVICE_MAN,EXECUTOR)
        SELECT A.CUST_ID,A.CUST_NO,A.CUST_NAME,B.ExecuteTime,B.ServiceTypeName,B.Result,D.ManagerName,E.ManagerName,'系统',C.ManagerID,C.Executor
            FROM TCustomers A,TServiceTaskDetail B, TServiceTasks C, TCustManagers D, TCustManagers E
            WHERE B.TARGETCUSTID = A.CUST_ID AND B.TaskSerialNO = C.Serial_no AND C.ManagerID = D.ManagerID AND C.Executor = E.ManagerID
                AND(A.CUST_ID=@IN_CUST_ID)
    */
    --活动对象客户表
    INSERT INTO #TMP_QUERY_TCustomerMaintenance(CUST_ID,CUST_NO,CUST_NAME,SERVICE_TIME,SERVICE_INFO,SERVICE_SUMMARY,SERVICE_MAN_NAME,EXECUTOR_NAME,DATA_FLAG,SERVICE_MAN,EXECUTOR)
        SELECT A.CUST_ID,B.CUST_NO,B.CUST_NAME,C.COMPLETE_TIME ,C.ACTIVE_THEME,C.ACTIVE_PLAN+ ' <br>分摊费用:'+CONVERT(NVARCHAR(20),A.ACTIVITY_FEE),C.MANAGE_MAN,C.CREATOR_NAME,'系统',C.MANAGE_CODE,C.CREATOR
            FROM TACTIVITYCUSTS A,TCustomers B,TACTIVITIES C
            WHERE (A.CUST_ID = B.CUST_ID) AND (A.ACTIVITY_ID = C.SERIAL_NO)
                AND(A.CUST_ID=@IN_CUST_ID)
    --呼叫中心通话
    INSERT INTO #TMP_QUERY_TCustomerMaintenance(CUST_ID,CUST_NO,CUST_NAME,SERVICE_TIME,SERVICE_INFO,SERVICE_SUMMARY,SERVICE_MAN_NAME,EXECUTOR_NAME,DATA_FLAG,SERVICE_MAN,EXECUTOR)
        SELECT A.CUST_ID,B.CUST_NO,B.CUST_NAME,A.CallTime,'语音通话','',D.ManagerName,D.ManagerName,'系统',A.ManagerID,A.ManagerID
        FROM TCallRecords A, TCustomers B, TCustManagers D
        WHERE A.CUST_ID = B.CUST_ID AND A.ManagerID = D.ManagerID
			AND(A.CUST_ID=@IN_CUST_ID)
	--客户维护记录
	INSERT INTO #TMP_QUERY_TCustomerMaintenance(SERIAL_NO,CUST_ID,CUST_NO,CUST_NAME,SERVICE_TIME,SERVICE_INFO,SERVICE_SUMMARY,SERVICE_MAN,SERVICE_MAN_NAME,EXECUTOR,EXECUTOR_NAME,CONTENT,SUBJECT,INFO_LEVEL,STEP_FLAG,DATA_FLAG)	
		SELECT A.SERIAL_NO,A.CUST_ID,A.CUST_NO,B.CUST_NAME,A.SERVICE_TIME,A.SERVICE_INFO,A.SERVICE_SUMMARY,A.SERVICE_MAN,A.SERVICE_MAN_NAME,A.EXECUTOR,A.EXECUTOR_NAME,A.CONTENT,A.SUBJECT,A.INFO_LEVEL,A.STEP_FLAG,'人工' AS DATA_FLAG
		FROM TCUSTSERVICELOG A, TCustomers B
		WHERE A.CUST_ID = B.CUST_ID
            AND(A.CUST_ID=@IN_CUST_ID)
	--输出
    SELECT A.SERIAL_NO,A.CUST_ID,A.CUST_NO,A.CUST_NAME,A.SERVICE_TIME,A.SERVICE_INFO,A.SERVICE_SUMMARY,A.SERVICE_MAN,A.SERVICE_MAN_NAME,A.EXECUTOR,A.EXECUTOR_NAME,A.DATA_FLAG,A.CONTENT,A.SUBJECT,A.INFO_LEVEL,A.STEP_FLAG
        FROM #TMP_QUERY_TCustomerMaintenance A
        ORDER BY A.SERVICE_TIME DESC
GO