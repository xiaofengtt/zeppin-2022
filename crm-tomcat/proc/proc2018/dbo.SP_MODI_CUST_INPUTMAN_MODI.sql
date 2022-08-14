﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_CUST_INPUTMAN_MODI 	@IN_CUST_ID    INT,
												@IN_NEW_INPUT_MAN  INT
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'修改客户录入人'
    SELECT @SSUMMARY = N'修改客户录入人'
    SELECT @IBUSI_FLAG = 20402
    SELECT @V_RET_CODE = -204020010

    IF NOT EXISTS(SELECT * FROM TCUSTOMERS WHERE CUST_ID = @IN_CUST_ID)
        RETURN @V_RET_CODE - 01   -- 客户不存在

    BEGIN TRANSACTION

    UPDATE TCUSTOMERS
    SET INPUT_MAN = @IN_NEW_INPUT_MAN
    WHERE CUST_ID = @IN_CUST_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    SELECT @SSUMMARY = N'修改客户录入人，客户ID：'+RTRIM(CONVERT(NVARCHAR(16),@IN_CUST_ID))
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_NEW_INPUT_MAN,@SSUMMARY)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    COMMIT TRANSACTION
    RETURN 100
GO