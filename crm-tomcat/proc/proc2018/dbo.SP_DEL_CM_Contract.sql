USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_DEL_CM_Contract     @IN_CONTRACT_ID INT,
                                        @IN_INPUT_MAN   INT
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'删除合同'
    SELECT @SSUMMARY = N'删除合同'
    SELECT @IBUSI_FLAG = 20801
    SELECT @V_RET_CODE = -20801006

    IF NOT EXISTS(SELECT * FROM CM_TCONTRACT WHERE CONTRACT_ID = @IN_CONTRACT_ID)
        RETURN @V_RET_CODE - 11   -- 合同编号不存在

    BEGIN TRANSACTION

    DELETE FROM CM_TCONTRACT WHERE CONTRACT_ID = @IN_CONTRACT_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END


    SELECT @SSUMMARY = N'删除合同，合同编号：'+RTRIM(CONVERT(NVARCHAR(16),@IN_CONTRACT_ID))
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
