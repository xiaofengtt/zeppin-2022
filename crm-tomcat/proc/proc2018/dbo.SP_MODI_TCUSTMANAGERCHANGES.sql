﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TCUSTMANAGERCHANGES    @IN_SERIAL_NO           INT,            --序列号
                                                @IN_MANAGERID_BEFORE    INT,            --前客户经理ID关联对应于TCUSTMANAGERS.ManagerID
                                                @IN_MANAGERID_NOW       INT,            --现客户经理ID关联对应于TCUSTMANAGERS.ManagerID
                                                @IN_INPUT_MAN           INT = 0,        --操作员
                                                @IN_CUST_ID             INT = 0         --操作员
WITH ENCRYPTION
AS
    DECLARE @V_MANAGERNAME_BEFORE   NVARCHAR(64), @V_MANAGERNAME_NOW NVARCHAR(64)
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -30401000, @IBUSI_FLAG = 30401
    SELECT @SBUSI_NAME = N'修改客户移交，客户经理变更信息', @SSUMMARY = N'修改客户移交，客户经理变更信息'

    BEGIN TRY
		IF @IN_MANAGERID_BEFORE = @IN_MANAGERID_NOW
			RAISERROR('移交前后客户经理相同，不做移交',16,1)
		SELECT @V_MANAGERNAME_BEFORE = ManagerName FROM  TCustManagers  WHERE ManagerID = @IN_MANAGERID_BEFORE
		IF @@ROWCOUNT=0
			RAISERROR('移交前客户经理信息不存在',16,1)
		SELECT @V_MANAGERNAME_NOW = ManagerName FROM  TCustManagers  WHERE ManagerID = @IN_MANAGERID_NOW
		IF @@ROWCOUNT=0
			RAISERROR('移交后客户经理信息不存在',16,1)
		IF EXISTS(SELECT 1 FROM TCUSTMANAGERCHANGES WHERE SERIAL_NO = @IN_SERIAL_NO AND CHECK_FLAG = 2 )
			RAISERROR('该客户经理移交记录已经审批通过，不允许修改',16,1)
    BEGIN TRANSACTION
    UPDATE TCUSTMANAGERCHANGES
		SET MANAGERID_BEFORE = @IN_MANAGERID_BEFORE,
			MANAGERNAME_BEFORE = @V_MANAGERNAME_BEFORE,
			MANAGERID_NOW = @IN_MANAGERID_NOW,
			MANAGERNAME_NOW = @V_MANAGERNAME_NOW,
			CUST_ID = @IN_CUST_ID,
			CHECK_FLAG = 1
		WHERE SERIAL_NO = @IN_SERIAL_NO
    
    SET @SSUMMARY = N'修改客户移交，客户经理变更信息，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
    
    COMMIT TRANSACTION
        RETURN 100
    END TRY
    
    --异常处理
    BEGIN CATCH
        IF @@TRANCOUNT > 0 BEGIN
            ROLLBACK TRANSACTION
        END
        DECLARE @V_ERROR_STR NVARCHAR(1000),@V_ERROR_NUMBER INT,@V_ERROR_SEVERITY INT,@V_ERROR_STATE INT,
                @V_ERROR_PROCEDURE sysname,@V_ERROR_LINE INT,@V_ERROR_MESSAGE NVARCHAR(4000)

        SELECT @V_ERROR_STR = N'Message:%s,Error:%d,Level:%d,State:%d,Procedure:%s,Line:%d',
               @V_ERROR_NUMBER = ERROR_NUMBER(),
               @V_ERROR_SEVERITY = ERROR_SEVERITY(),
               @V_ERROR_STATE = ERROR_STATE(),
               @V_ERROR_PROCEDURE = ERROR_PROCEDURE(),
               @V_ERROR_LINE = ERROR_LINE(),
               @V_ERROR_MESSAGE = ERROR_MESSAGE()

        RAISERROR(@V_ERROR_STR,@V_ERROR_SEVERITY,1,@V_ERROR_MESSAGE,@V_ERROR_NUMBER,@V_ERROR_SEVERITY,@V_ERROR_STATE,
                  @V_ERROR_PROCEDURE,@V_ERROR_LINE)

        RETURN -100
    END CATCH
GO
