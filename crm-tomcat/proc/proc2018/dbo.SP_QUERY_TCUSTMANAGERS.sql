USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCUSTMANAGERS @IN_MANAGERID           INT = 0 ,           --关联对应于TOperator.OP_CODE（主键）
                                        @IN_MANAGERNAME         NVARCHAR(64)= N'',   --客户经理姓名
                                        @IN_EXTENSION           NVARCHAR(32) = N'',   --分机号码
                                        @IN_RECORDEXTENSION     NVARCHAR(64)= N'',   --录音分机号码
                                        @IN_DUTYNAME            NVARCHAR(60) = N'',  --岗位（职位）名称
                                        @IN_PROVIDESERVICES     INT = 0             --当前客户经理提供的服务类别
WITH ENCRYPTION
AS
    IF ISNULL(@IN_MANAGERID,0) <> 0
        SELECT ManagerID,ManagerName,Extension,DutyName,ProvideServices,RecordExtension
        FROM TCustManagers
        WHERE ManagerID = @IN_MANAGERID
        ORDER BY ManagerName
    ELSE
        SELECT ManagerID,ManagerName,Extension,DutyName,ProvideServices,RecordExtension
        FROM TCustManagers
        WHERE (ManagerName LIKE '%' + @IN_MANAGERNAME + '%' OR ISNULL(@IN_MANAGERNAME,'') = N'')
            AND (Extension LIKE '%' + @IN_EXTENSION + '%' OR ISNULL(@IN_EXTENSION,'') = N'')
            AND (RecordExtension LIKE '%' + @IN_RECORDEXTENSION + '%' OR ISNULL(@IN_RECORDEXTENSION,'') = N'')
            AND (DutyName LIKE '%' + @IN_DUTYNAME + '%' OR ISNULL(@IN_DUTYNAME,'') = N'')
            AND ((ProvideServices & @IN_PROVIDESERVICES) > 0  OR ISNULL(@IN_PROVIDESERVICES,0) = 0 )
        ORDER BY ManagerName
    RETURN 100
GO
