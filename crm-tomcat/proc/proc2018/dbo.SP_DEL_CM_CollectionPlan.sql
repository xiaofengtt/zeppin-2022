USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_DEL_CM_CollectionPlan     @IN_COLLECTION_ID INT,
                                              @IN_CONTRACT_ID INT,
                                              @IN_INPUT_MAN   INT
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'删除收款计划'
    SELECT @SSUMMARY = N'删除收款计划'
    SELECT @IBUSI_FLAG = 20801
    SELECT @V_RET_CODE = -20801006

    BEGIN TRANSACTION
	IF ISNULL(@IN_COLLECTION_ID,0) <> 0
	BEGIN	
		DELETE FROM CM_TCOLLECTION_PLAN WHERE COLLECTION_ID = @IN_COLLECTION_ID
        SELECT @SSUMMARY = N'删除收款计划，收款计划编号：'+RTRIM(CONVERT(NVARCHAR(16),@IN_COLLECTION_ID))
	END
	ELSE
	BEGIN
        DELETE FROM CM_TCOLLECTION_PLAN WHERE CONTRACT_ID = @IN_CONTRACT_ID
        SELECT @SSUMMARY = N'删除收款计划，收款计划所属合同ID：'+RTRIM(CONVERT(NVARCHAR(16),@IN_CONTRACT_ID))
    END
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
