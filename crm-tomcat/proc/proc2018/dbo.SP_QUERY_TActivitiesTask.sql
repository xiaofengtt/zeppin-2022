USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TActivitiesTask   @IN_SERIAL_NO           INT,                        -- 活动任务序列号
                                            @IN_ActiveTaskTitle     NVARCHAR(64),               -- 活动标题
                                            @IN_Active_Serial_no    INT = 0,                    -- 活动编号
                                            @IN_ActiveTaskType      NVARCHAR(8),                    -- 活动任务类别
                                            @IN_Begin_DATE          INT = 0 ,                   -- 开始日期
                                            @IN_End_DATE            INT = 0 ,                   -- 结束日期
                                            @IN_Executor            INT = 0 ,                   -- 执行者
                                            @IN_MANAGE_CODE         INT = 0 ,                   -- 管理者
                                            @IN_COMPLETE_TIME_UP    INT = 0 ,                   -- 活动完成时间上限
                                            @IN_COMPLETE_TIME_DOWN  INT = 0 ,                   -- 活动完成时间下限
                                            @IN_TaskFlag            INT = 0                     -- 任务标识
WITH ENCRYPTION
AS
    IF  ISNULL(@IN_SERIAL_NO,0)<>0
    BEGIN
        SELECT  atask.Serial_no,atask.Active_Serial_no,atask.ActiveTaskTitle,atask.ActiveTaskType,atask.ActiveTaskTypeName,atask.Content,atask.BeginDate,atask.EndDate,atask.Executor,atask.CompleteTime,atask.Remark,atask.TaskFlag,atask.CheckMan,atask.Check_Options,
                a.ACTIVE_THEME,a.MANAGE_CODE,a.MANAGE_MAN,a.ACTIVE_FLAG,a.ACTIVE_CODE
        FROM TActivitiesTask atask,TACTIVITIES a
        WHERE a.SERIAL_NO = atask.Active_Serial_no AND atask.Serial_no = @IN_SERIAL_NO
    END
    ELSE
    BEGIN
        SELECT  atask.Serial_no,atask.Active_Serial_no,atask.ActiveTaskTitle,atask.ActiveTaskType,atask.ActiveTaskTypeName,atask.Content,atask.BeginDate,atask.EndDate,atask.Executor,atask.CompleteTime,atask.Remark,atask.TaskFlag,atask.CheckMan,atask.Check_Options,
                a.ACTIVE_THEME,a.MANAGE_CODE,a.MANAGE_MAN,a.ACTIVE_FLAG,a.ACTIVE_CODE
        FROM    TActivitiesTask atask,TACTIVITIES a
        WHERE   a.SERIAL_NO = atask.Active_Serial_no
            AND (atask.Active_Serial_no = @IN_Active_Serial_no OR ISNULL(@IN_Active_Serial_no,0) = 0)
            AND (atask.ActiveTaskTitle LIKE '%' + @IN_ActiveTaskTitle + '%' OR ISNULL(@IN_ActiveTaskTitle,'') = N'')
            AND (atask.ActiveTaskType = @IN_ActiveTaskType  OR ISNULL(@IN_ActiveTaskType,'') = N'')
            AND (dbo.GETDATEINT(atask.BeginDate) > =@IN_Begin_DATE OR ISNULL(@IN_Begin_DATE,0) = 0 )
            AND (dbo.GETDATEINT(atask.BeginDate) < = @IN_End_DATE OR ISNULL(@IN_End_DATE,0) = 0 )
            AND (atask.Executor = @IN_Executor OR ISNULL(@IN_Executor,0) = 0 )
            AND (a.MANAGE_CODE = @IN_MANAGE_CODE OR ISNULL(@IN_MANAGE_CODE,0) = 0 )
            AND (dbo.GETDATEINT(atask.CompleteTime) > = @IN_COMPLETE_TIME_UP OR ISNULL(@IN_COMPLETE_TIME_UP,0) = 0 )
            AND (dbo.GETDATEINT(atask.CompleteTime) < = @IN_COMPLETE_TIME_DOWN OR ISNULL(@IN_COMPLETE_TIME_DOWN,0) = 0 )
            AND (atask.TaskFlag = @IN_TaskFlag OR ISNULL(@IN_TaskFlag,0) = 0 )
    END

    RETURN 100;
GO
