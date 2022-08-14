USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TActivitiesFee    @IN_SERIAL_NO           INT,                        -- 活动费用序列号
                                            @IN_Active_Serial_no    INT = 0,                    -- 活动编号
                                            @IN_FeeItems            NVARCHAR(128),              -- 费用名目
                                            @IN_FeeAmountUp         DECIMAL(16,3),              -- 费用金额上限
                                            @IN_FeeAmountDown       DECIMAL(16,3)               -- 费用金额下限
WITH ENCRYPTION
AS
    IF  ISNULL(@IN_SERIAL_NO,0)<>0
    BEGIN
        SELECT af.Serial_no,af.Active_Serial_no,af.FeeItems,af.FeeAmount,a.ACTIVE_CODE,a.ACTIVE_THEME,a.ACTIVITY_FEE,af.Remark
        FROM TActivitiesFee af,TACTIVITIES a
        WHERE a.SERIAL_NO = af.Active_Serial_no AND af.Serial_no = @IN_SERIAL_NO
    END
    ELSE
    BEGIN
        SELECT af.Serial_no,af.Active_Serial_no,af.FeeItems,af.FeeAmount,a.ACTIVE_CODE,a.ACTIVE_THEME,a.ACTIVITY_FEE,af.Remark
        FROM TActivitiesFee af,TACTIVITIES a
        WHERE a.SERIAL_NO = af.Active_Serial_no
            AND (af.Active_Serial_no = @IN_Active_Serial_no OR ISNULL(@IN_Active_Serial_no,0) = 0)
            AND (af.FeeItems LIKE '%' + @IN_FeeItems + '%' OR ISNULL(@IN_FeeItems,'') = N'')
            AND (af.FeeAmount < = @IN_FeeAmountUp OR ISNULL(@IN_FeeAmountUp,0) = 0)
            AND (af.FeeAmount > = @IN_FeeAmountDown OR ISNULL(@IN_FeeAmountDown,0) = 0)
    END

    RETURN 100
GO
