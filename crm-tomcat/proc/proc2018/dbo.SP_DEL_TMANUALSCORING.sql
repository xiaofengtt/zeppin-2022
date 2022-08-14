﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_DEL_TMANUALSCORING @IN_MANUAL_ID    INT,    --主键
                                       @IN_INPUT_MAN    INT     --录入操作员

WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'删除人工打分信息'
    SELECT @SSUMMARY = N'删除人工打分信息'
    SELECT @IBUSI_FLAG = 21004
    SELECT @V_RET_CODE = -21004000

    IF NOT EXISTS(SELECT * FROM TMANUALSCORING WHERE MANUAL_ID = @IN_MANUAL_ID)
        RETURN @V_RET_CODE - 1   --计分操作数信息不存在

    BEGIN TRANSACTION

    DELETE FROM TMANUALSCORING WHERE MANUAL_ID = @IN_MANUAL_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'删除人工打分信息'+RTRIM(CONVERT(NVARCHAR(16),@IN_MANUAL_ID))
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