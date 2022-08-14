USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCUSTMANAGERS_AUTH @IN_INPUT_MAN  INT,
                                             @IN_FORCEALL   INT = 0  --1强制返回所有客户经理
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_ROLE_ID INT
    SELECT @V_ROLE_ID = ROLE_ID FROM TOPERATOR WHERE OP_CODE = @IN_INPUT_MAN
    IF @IN_FORCEALL = 1 --强制返回所有客户经理
        OR (NOT EXISTS(SELECT 1 FROM TSYSTEMINFO WHERE USER_ID = 2)) --非北京信托返回全部客户经理
        OR (EXISTS(SELECT 1 FROM TSYSTEMINFO WHERE USER_ID = 2) AND EXISTS(SELECT 1 FROM TOPROLE WHERE OP_CODE = @IN_INPUT_MAN AND ROLE_ID = 14)) --北京信托角色(录入人员)14返回所有客户经理
    BEGIN
        SELECT ManagerID,ManagerName,Extension,DutyName,ProvideServices,RecordExtension
        FROM TCustManagers
    END
    ELSE --北京信托：普通角色返回有授权给当前操作员的客户经理列表
    BEGIN
        SELECT B.ManagerID,B.ManagerName,B.Extension,B.DutyName,B.ProvideServices,B.RecordExtension
            FROM TCustManagers B
            WHERE ManagerID = @IN_INPUT_MAN --操作员自己
        UNION ALL
        SELECT DISTINCT B.ManagerID,B.ManagerName,B.Extension,B.DutyName,B.ProvideServices,B.RecordExtension
            FROM TAuthorizationShare A, TCustManagers B
            WHERE A.SourceManagerID = B.ManagerID AND A.ShareType = 3 AND A.ManagerID = @IN_INPUT_MAN AND ISNULL(A.CUST_ID,0) = 0 --CUST_ID=0表示仅返回所有客户授权给当前操作员的
                AND (GETDATE() BETWEEN ISNULL(A.START_TIME,GETDATE()) AND ISNULL(A.INVALID_TIME,GETDATE()+36000))
    END
    RETURN 100
GO
