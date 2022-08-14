USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_EDIT_TSERVICEDEFINE     @IN_SERVICETYPE     BIGINT,         --必要  服务类别
                                            @IN_OFFSETDAYS      INTEGER,        --天数偏移量，负数为提前N天提醒，正数为推后N天提醒。为空表示0
                                            @IN_SERVICEWAY      NVARCHAR(10),   --服务途径TDICTPARAM(1109)
                                            @IN_DESCRIPTION     NVARCHAR(200),  --该服务的详细描述
                                            @IN_NOTICECAPTION   NVARCHAR(200),  --服务提示的说明文字
                                            @IN_ISVALID         INTEGER,        --是否有效（启用）1有效2无效
                                            @IN_EXECUTOR        INTEGER,        --执行者（TCUSTMANAGERS.MANAGERID）
                                            @IN_AutoFlag        INTEGER,         --是否自动发送短信
                                            @IN_TempID          INTEGER,         --短信模板ID
                                            @IN_INPUT_MAN       INTEGER         --操作员
WITH ENCRYPTION
AS
    DECLARE @V_TempTitle NVARCHAR(40)
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -50301000, @IBUSI_FLAG = 50301
    SELECT @SBUSI_NAME = N'修改服务类别', @SSUMMARY = N'修改服务类别'

    IF @IN_TempID>0
    BEGIN
        SELECT @V_TempTitle = Title FROM TSmsTemplates WHERE TempID = @IN_TempID
    END

    BEGIN TRANSACTION
    UPDATE TServiceDefine
    SET OffsetDays = @IN_OFFSETDAYS,
        ServiceWay = @IN_SERVICEWAY,
        Description = @IN_DESCRIPTION,
        NoticeCaption = @IN_NOTICECAPTION,
        IsValid = @IN_ISVALID,
        Executor = @IN_EXECUTOR,
        TempID = @IN_TempID ,
        TempTitle = @V_TempTitle,
        AutoFlag = @IN_AutoFlag
    WHERE ServiceType = @IN_SERVICETYPE
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'修改服务类别，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
