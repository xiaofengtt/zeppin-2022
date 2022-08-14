﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_CHECK_TCOPRODUCT @IN_COPRODUCT_ID                INT,             --产品ID
                                     @IN_INPUT_MAN                     INT              --录入人员，对应表TOPERATOR.OP_CODE
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_COPRODUCT_NAME NVARCHAR(200)
    SELECT @SBUSI_NAME = N'合同管理之复核产品'
    SELECT @SSUMMARY = N'合同管理之复核产品'
    SELECT @IBUSI_FLAG = 50001
    SELECT @V_RET_CODE = -50001000

    SELECT @V_COPRODUCT_NAME =COPRODUCT_NAME FROM TCOPRODUCT WHERE COPRODUCT_ID =@IN_COPRODUCT_ID

    BEGIN TRANSACTION

    UPDATE TCOPRODUCT
        SET CHECK_FLAG =2
        WHERE COPRODUCT_ID =@IN_COPRODUCT_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    --
    SELECT @SSUMMARY = N'合同管理之删除产品,产品名称：'+@V_COPRODUCT_NAME
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
