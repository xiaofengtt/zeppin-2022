USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TMEETINGS      @IN_SERIAL_NO           INT,            --会议ID
                                        @IN_MEETING_DATE        INT,            --会议时间
                                        @IN_MEETING_TYPE        NVARCHAR(16),    --会议类型
                                        @IN_START_DATE          DATETIME,       --开始时间
                                        @IN_END_DATE            DATETIME,       --结束时间
                                        @IN_MEETING_ADDRESS     NVARCHAR(64),   --会议地点
                                        @IN_ATTEND_MAN          NVARCHAR(256),  --会议参与人员
                                        @IN_ATTEND_MAN_CODE     NVARCHAR(128),  --会议参与人员编号
                                        @IN_MEETING_THEME       NVARCHAR(256),  --会议主题
                                        @IN_REMARK              NVARCHAR(256),  --备注说明
                                        @IN_INPUT_MAN           INT = 0         --操作员

WITH ENCRYPTION
AS
    DECLARE @V_MEETING_TYPE_NAME NVARCHAR(64)
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -30102000, @IBUSI_FLAG = 30102
    SELECT @SBUSI_NAME = N'修改营销会议记录信息', @SSUMMARY = N'修改营销会议记录信息'
    IF NOT EXISTS(SELECT 1 FROM TMEETINGS WHERE SERIAL_NO = @IN_SERIAL_NO)
        RETURN @V_RET_CODE - 1 --营销会议记录信息不存在

    SELECT @V_MEETING_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_MEETING_TYPE
    SELECT @V_MEETING_TYPE_NAME = ISNULL(@V_MEETING_TYPE_NAME,'')
    BEGIN TRANSACTION
    UPDATE TMEETINGS
    SET MEETING_DATE = @IN_MEETING_DATE,
        MEETING_TYPE = @IN_MEETING_TYPE,
        MEETING_TYPE_NAME = @V_MEETING_TYPE_NAME,
        START_DATE = @IN_START_DATE,
        END_DATE = @IN_END_DATE,
        MEETING_ADDRESS = @IN_MEETING_ADDRESS,
        ATTEND_MAN = @IN_ATTEND_MAN,
        ATTEND_MAN_CODE = @IN_ATTEND_MAN_CODE ,
        MEETING_THEME = @IN_MEETING_THEME,
        REMARK = @IN_REMARK
    WHERE SERIAL_NO = @IN_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'修改营销会议记录信息，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
