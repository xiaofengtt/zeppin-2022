USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TAuthorizationShare @IN_SERIAL_NO       INT = 0 ,             --ID
                                              @IN_ShareType       INT,                  --1共享；2授权；3单客户有时限快捷授权
                                              @IN_SharDescription NVARCHAR(200) = N'',  --描述
                                              @IN_Status          INT = 0,              --启用状态
                                              @IN_SourceManagerID INT = 0,              --源客户经理，1共享时需要指定源客户经理
                                              @IN_CA_ID           INT = 0,              --2授权时，需要指定授权集ID
                                              @IN_ManagerID       INT = 0,              --目标客户经理，被共享或授权的
                                              @IN_INPUT_MAN       INT = 0,              --操作员
                                              @IN_INVALID_DATE    INT = NULL            --查询此日期前的
WITH ENCRYPTION
AS
    DECLARE @V_INVALID_DATE DATETIME
    IF @IN_INVALID_DATE IS NULL OR @IN_INVALID_DATE = 0
        SET @IN_INVALID_DATE = 19000101
    SET @V_INVALID_DATE = dbo.GetDateTime(@IN_INVALID_DATE)
    IF ISNULL(@IN_SERIAL_NO,0) <> 0
        SELECT A.*, C.CUST_NAME, S.OP_NAME AS SOURCE_MANAGER_NAME, T.OP_NAME AS MANAGER_NAME
        FROM TAuthorizationShare A LEFT JOIN TCustomers C ON A.CUST_ID = C.CUST_ID, TOPERATOR S, TOPERATOR T
        WHERE A.SourceManagerID = S.OP_CODE AND A.ManagerID = T.OP_CODE AND A.SERIAL_NO = @IN_SERIAL_NO
    ELSE
    --判断该操作员是否有授权权限 如果有，查询所有
    IF EXISTS(SELECT 1 FROM TOPROLE A,TROLERIGHT B WHERE A.OP_CODE =@IN_INPUT_MAN AND A.ROLE_ID =B.ROLE_ID AND B.MENU_ID =N'40502' AND B.FUNC_ID =103)
        SELECT A.*, C.CUST_NAME, S.OP_NAME AS SOURCE_MANAGER_NAME, T.OP_NAME AS MANAGER_NAME
        FROM TAuthorizationShare A LEFT JOIN TCustomers C ON A.CUST_ID = C.CUST_ID, TOPERATOR S, TOPERATOR T
        WHERE A.SourceManagerID = S.OP_CODE AND A.ManagerID = T.OP_CODE AND A.ShareType = @IN_ShareType
            AND (A.SharDescription LIKE '%' + @IN_SharDescription + '%' OR ISNULL(@IN_SharDescription,'') = N'')
            AND (A.Status=@IN_Status  OR ISNULL(@IN_Status,0) = 0)
            AND (A.CA_ID=@IN_CA_ID  OR ISNULL(@IN_CA_ID,0) = 0)
            AND (A.ManagerID=@IN_ManagerID  OR ISNULL(@IN_ManagerID,0) = 0)
            AND (A.INVALID_TIME IS NULL OR A.INVALID_TIME >= @V_INVALID_DATE)
    --判断该操作员是否有授权权限 如果没有，查询该操作员自己作为经理的授权记录
    ELSE
        SELECT A.*, C.CUST_NAME, S.OP_NAME AS SOURCE_MANAGER_NAME, T.OP_NAME AS MANAGER_NAME
        FROM TAuthorizationShare A LEFT JOIN TCustomers C ON A.CUST_ID = C.CUST_ID, TOPERATOR S, TOPERATOR T
        WHERE A.SourceManagerID = S.OP_CODE AND A.ManagerID = T.OP_CODE AND A.ShareType = @IN_ShareType
            AND (A.SourceManagerID=@IN_SourceManagerID OR ISNULL(@IN_SourceManagerID,0) = 0)     --如果不需要判断授权权限，而需要把所有授权集查出来,就将@IN_ManagerID设为0
            AND (A.SharDescription LIKE '%' + @IN_SharDescription + '%' OR ISNULL(@IN_SharDescription,'') = N'')
            AND (A.Status=@IN_Status  OR ISNULL(@IN_Status,0) = 0)
            AND (A.CA_ID=@IN_CA_ID  OR ISNULL(@IN_CA_ID,0) = 0)
            AND (A.ManagerID=@IN_ManagerID  OR ISNULL(@IN_ManagerID,0) = 0)
            AND (A.INVALID_TIME IS NULL OR A.INVALID_TIME >= @V_INVALID_DATE)

    RETURN 100
GO
