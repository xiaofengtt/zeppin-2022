USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_DEL_TActivitiesFee      @IN_SERIAL_NO           INT,            --活动费用序列号
                                            @IN_INPUT_MAN           INT = 0         --操作员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200),@V_ActiveFee DECIMAL(16,3),@V_Active_Serial_no INT
    SELECT @V_RET_CODE = -30403000, @IBUSI_FLAG = 30403
    SELECT @SBUSI_NAME = N'删除营销活动费用信息', @SSUMMARY = N'删除费用活动记录信息'

    IF NOT EXISTS(SELECT 1 FROM TActivitiesFee WHERE Serial_no = @IN_SERIAL_NO)
        RETURN @V_RET_CODE - 1 --营销活动费用信息不存在

    SELECT @V_Active_Serial_no = Active_Serial_no FROM TActivitiesFee WHERE Serial_no = @IN_SERIAL_NO
    BEGIN TRANSACTION
    DELETE FROM  TActivitiesFee
    WHERE Serial_no = @IN_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @V_ActiveFee = ISNULL(SUM(FeeAmount),0) FROM TActivitiesFee WHERE Active_Serial_no = @V_Active_Serial_no --获得活动费用总数
    UPDATE TActivities
    SET ACTIVITY_FEE = @V_ActiveFee
    WHERE Serial_no = @V_Active_Serial_no
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'删除营销活动费用信息，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
