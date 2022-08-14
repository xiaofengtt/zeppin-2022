﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_DEL_TOPERATOR @IN_OP_CODE INT,
                                  @IN_INPUT_MAN INT
WITH ENCRYPTION
AS
    DECLARE @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'删除员工'
    SELECT @SSUMMARY = N'删除员工'
    SELECT @IBUSI_FLAG = 10603

    IF NOT EXISTS(SELECT 1 FROM TOPERATOR WHERE OP_CODE = @IN_OP_CODE)
      RETURN -10602001   -- 员工不存在

    BEGIN TRANSACTION

    UPDATE TOPERATOR SET STATUS = 2 WHERE OP_CODE = @IN_OP_CODE
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'删除加员工，员工编号：'+CONVERT(NVARCHAR(8),@IN_OP_CODE)
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
