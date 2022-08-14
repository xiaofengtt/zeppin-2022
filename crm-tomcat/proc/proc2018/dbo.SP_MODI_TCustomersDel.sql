﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TCustomersDel	@IN_CUST_ID    INT,
										@IN_INPUT_MAN  INT
WITH ENCRYPTION
AS
    DECLARE @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'注销客户恢复'
    SELECT @SSUMMARY = N'注销客户恢复'
    SELECT @IBUSI_FLAG = 20112

    BEGIN TRANSACTION

    UPDATE TCustomers
    SET STATUS = '112803'
    WHERE CUST_ID = @IN_CUST_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    COMMIT TRANSACTION
    RETURN 100
GO