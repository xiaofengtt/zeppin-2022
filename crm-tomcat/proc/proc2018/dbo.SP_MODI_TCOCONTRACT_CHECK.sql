USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TCOCONTRACT_CHECK  @IN_COCONTRACT_ID                 INT,             --合同ID
                                            @IN_CHECK_FLAG                    INT,             --审核标志
                                            @IN_CHECK_MAN                     INT,             --审核人
                                            @IN_CHECK_DATE                    INT              --审核日期

WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_PAYMENT_TYPE_NAME NVARCHAR(30),@V_COCONTRACT_TYPE_NAME NVARCHAR(30)
    SELECT @SBUSI_NAME = N'合同管理之修改主合同-审核'
    SELECT @SSUMMARY = N'合同管理之修改主合同-审核'
    SELECT @IBUSI_FLAG = 50001
    SELECT @V_RET_CODE = -50001000


    BEGIN TRANSACTION

    UPDATE TCOCONTRACT
        SET CHECK_FLAG    = @IN_CHECK_FLAG,
            CHECK_MAN     = @IN_CHECK_MAN,
            CHECK_DATE    = @IN_CHECK_DATE
        WHERE COCONTRACT_ID =@IN_COCONTRACT_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    --
    SELECT @SSUMMARY = N'合同管理之修改主合同-审核，合同编号'+RTRIM(CONVERT(NVARCHAR(16),@IN_COCONTRACT_ID))
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_CHECK_MAN,@SSUMMARY)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    COMMIT TRANSACTION
    RETURN 100
GO
