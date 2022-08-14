USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TCallRecords @IN_Serial_no  BIGINT,         --序号（主键）
                                      @IN_Content    NVARCHAR(MAX),  --通话记事
                                      @IN_Status     INTEGER,        --状态1正常完成2本次会话待处理
                                      @IN_INPUT_MAN INTEGER         --操作员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'通话记录处理'
    SELECT @SSUMMARY = N'通话记录处理'
    SELECT @IBUSI_FLAG = 50002
    SELECT @V_RET_CODE = -50002000

    IF NOT EXISTS(SELECT * FROM TCallRecords WHERE Serial_no = @IN_Serial_no)
        RETURN @V_RET_CODE - 1  -- 记录不存在

    BEGIN TRANSACTION

    UPDATE TCallRecords SET Content = @IN_Content, Status = @IN_Status WHERE Serial_no = @IN_Serial_no
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'通话记录处理，记录序号：'+CONVERT(NVARCHAR(16),@IN_Serial_no)
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
