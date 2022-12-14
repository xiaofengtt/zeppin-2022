USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_SYNCHRO_TCustomers   @IN_CUST_ID   INT
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    SET XACT_ABORT ON

    DECLARE @RET INT, @V_DT_INTRUST INT
    DECLARE @V_RG_TIMES INT,@V_TOTAL_MONEY DECIMAL,@V_CURRENT_MONEY DECIMAL,@V_LAST_RG_DATE INT

    SELECT @V_DT_INTRUST = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'DT_INTRUST'

    IF @V_DT_INTRUST = 1
    BEGIN
        SELECT @V_RG_TIMES = RG_TIMES,
               @V_TOTAL_MONEY = TOTAL_MONEY,
               @V_CURRENT_MONEY = CURRENT_MONEY,
               @V_LAST_RG_DATE = LAST_RG_DATE
        FROM SRV_Intrust.INTRUST.dbo.TCUSTOMERINFO
        WHERE CUST_ID = @IN_CUST_ID
    END
    ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
    BEGIN
        SELECT @V_RG_TIMES = RG_TIMES,
               @V_TOTAL_MONEY = TOTAL_MONEY,
               @V_CURRENT_MONEY = CURRENT_MONEY,
               @V_LAST_RG_DATE = LAST_RG_DATE
        FROM INTRUST.dbo.TCUSTOMERINFO
        WHERE CUST_ID = @IN_CUST_ID
    END
    BEGIN TRANSACTION
        UPDATE TCustomers
        SET RG_TIMES = @V_RG_TIMES,
            TOTAL_MONEY = @V_TOTAL_MONEY,
            CURRENT_MONEY = @V_CURRENT_MONEY,
            LAST_RG_DATE = @V_LAST_RG_DATE
        WHERE CUST_ID = @IN_CUST_ID
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    COMMIT TRANSACTION
    SET XACT_ABORT OFF
    RETURN 100
GO
