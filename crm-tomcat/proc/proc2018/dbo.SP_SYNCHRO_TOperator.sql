USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_SYNCHRO_TOperator   @IN_INPUT_MAN   INT
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    SET XACT_ABORT ON

    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@V_DT_INTRUST INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    --客户经理角色ID
    DECLARE @V_CUSTMANAGER_ROLEID INT
    SELECT @V_DT_INTRUST = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'DT_INTRUST'
    SELECT @V_RET_CODE = -10601000, @IBUSI_FLAG = 10601
    SELECT @SBUSI_NAME = N'员工信息同步', @SSUMMARY = N'员工信息同步'

    BEGIN TRANSACTION
    IF @V_DT_INTRUST = 1
    BEGIN
        --客户经理角色ID
        SELECT @V_CUSTMANAGER_ROLEID = VALUE FROM SRV_Intrust.INTRUST.dbo.TSYSCONTROL WHERE FLAG_TYPE = 'AROLE2'
        SET @V_CUSTMANAGER_ROLEID = ISNULL(@V_CUSTMANAGER_ROLEID,0)
        --复制部门
        INSERT INTO TDEPARTMENT(DEPART_ID,DEPART_NAME,DEPART_JC,PARENT_ID,BOTTOM_FLAG,MANAGER_MAN,LEADER_MAN)
            SELECT DEPART_ID,DEPART_NAME,DEPART_JC,PARENT_ID,BOTTOM_FLAG,MANAGER_MAN,LEADER_MAN
                FROM SRV_Intrust.INTRUST.dbo.TDEPARTMENT A
                WHERE NOT EXISTS(SELECT 1 FROM TDEPARTMENT Z WHERE A.DEPART_ID = Z.DEPART_ID)
        --复制操作员
        INSERT INTO TOPERATOR(OP_CODE,OP_NAME,DEPART_ID,ROLE_ID,PASSWORD,ENCRYPT_PASSWORD,STATUS,LOGIN_USER)
            SELECT OP_CODE,OP_NAME,DEPART_ID,@V_CUSTMANAGER_ROLEID,PASSWORD,ENCRYPT_PASSWORD,STATUS,LOGIN_USER
                FROM SRV_Intrust.INTRUST.dbo.TOPERATOR A
                WHERE NOT EXISTS(SELECT 1 FROM TOPERATOR Z WHERE A.OP_CODE = Z.OP_CODE)
                    AND (EXISTS(SELECT 1 FROM SRV_Intrust.INTRUST.dbo.TCUSTOMERINFO B WHERE A.OP_CODE = B.SERVICE_MAN OR A.OP_CODE = B.INPUT_MAN)
                            OR EXISTS(SELECT 1 FROM SRV_Intrust.INTRUST.dbo.TOPROLE O WHERE A.OP_CODE = O.OP_CODE AND O.ROLE_ID = @V_CUSTMANAGER_ROLEID))
                    AND A.BOOK_CODE = 1 AND OP_CODE <> 888
        --复制客户经理
        INSERT INTO TCustManagers(ManagerID,ManagerName,Extension,DutyName,ProvideServices)
            SELECT OP_CODE,OP_NAME,OP_CODE,N'客户经理--'+OP_NAME,63
                FROM SRV_Intrust.INTRUST.dbo.TOPERATOR A
                WHERE NOT EXISTS(SELECT 1 FROM TOPERATOR Z WHERE A.OP_CODE = Z.OP_CODE)
                    AND (EXISTS(SELECT 1 FROM SRV_Intrust.INTRUST.dbo.TCUSTOMERINFO B WHERE A.OP_CODE = B.SERVICE_MAN OR A.OP_CODE = B.INPUT_MAN)
                            OR EXISTS(SELECT 1 FROM INTRUST.dbo.TOPROLE O WHERE A.OP_CODE = O.OP_CODE AND O.ROLE_ID = @V_CUSTMANAGER_ROLEID))
                    AND A.BOOK_CODE = 1 AND OP_CODE <> 888
    END
    ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
    BEGIN
        --客户经理角色ID
        SELECT @V_CUSTMANAGER_ROLEID = VALUE FROM INTRUST.dbo.TSYSCONTROL WHERE FLAG_TYPE = 'AROLE2'
        SET @V_CUSTMANAGER_ROLEID = ISNULL(@V_CUSTMANAGER_ROLEID,0)
        --复制部门
        INSERT INTO TDEPARTMENT(DEPART_ID,DEPART_NAME,DEPART_JC,PARENT_ID,BOTTOM_FLAG,MANAGER_MAN,LEADER_MAN)
            SELECT DEPART_ID,DEPART_NAME,DEPART_JC,PARENT_ID,BOTTOM_FLAG,MANAGER_MAN,LEADER_MAN
                FROM INTRUST.dbo.TDEPARTMENT A
                WHERE NOT EXISTS(SELECT 1 FROM TDEPARTMENT Z WHERE A.DEPART_ID = Z.DEPART_ID)
        --复制操作员
        INSERT INTO TOPERATOR(OP_CODE,OP_NAME,DEPART_ID,ROLE_ID,PASSWORD,ENCRYPT_PASSWORD,STATUS,LOGIN_USER)
            SELECT OP_CODE,OP_NAME,DEPART_ID,@V_CUSTMANAGER_ROLEID,PASSWORD,ENCRYPT_PASSWORD,STATUS,LOGIN_USER
                FROM INTRUST.dbo.TOPERATOR A
                WHERE NOT EXISTS(SELECT 1 FROM TOPERATOR Z WHERE A.OP_CODE = Z.OP_CODE)
                    AND (EXISTS(SELECT 1 FROM INTRUST.dbo.TCUSTOMERINFO B WHERE A.OP_CODE = B.SERVICE_MAN OR A.OP_CODE = B.INPUT_MAN)
                            OR EXISTS(SELECT 1 FROM INTRUST.dbo.TOPROLE O WHERE A.OP_CODE = O.OP_CODE AND O.ROLE_ID = @V_CUSTMANAGER_ROLEID))
                    AND A.BOOK_CODE = 1 AND OP_CODE <> 888
        --复制客户经理
        INSERT INTO TCustManagers(ManagerID,ManagerName,Extension,DutyName,ProvideServices)
            SELECT OP_CODE,OP_NAME,OP_CODE,N'客户经理--'+OP_NAME,63
                FROM INTRUST.dbo.TOPERATOR A
                WHERE NOT EXISTS(SELECT 1 FROM TOPERATOR Z WHERE A.OP_CODE = Z.OP_CODE)
                    AND (EXISTS(SELECT 1 FROM INTRUST.dbo.TCUSTOMERINFO B WHERE A.OP_CODE = B.SERVICE_MAN OR A.OP_CODE = B.INPUT_MAN)
                            OR EXISTS(SELECT 1 FROM INTRUST.dbo.TOPROLE O WHERE A.OP_CODE = O.OP_CODE AND O.ROLE_ID = @V_CUSTMANAGER_ROLEID))
                    AND A.BOOK_CODE = 1 AND OP_CODE <> 888
    END
    --日志
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    COMMIT TRANSACTION
    SET XACT_ABORT OFF
    RETURN 100
GO
