USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TSERVICEDEFINE    @IN_SERIAL_NO       INTEGER = 0,        --序号
                                            @IN_SERVICETYPE     BIGINT =  0,        --服务类别
                                            @IN_ISVALID         INTEGER = 99,       --是否有效（启用）1有效2无效
                                            @IN_INPUT_MAN       INTEGER=0           --操作员


WITH ENCRYPTION
AS
    IF ISNULL(@IN_SERIAL_NO,0) <> 0
        SELECT *
        FROM TServiceDefine
        WHERE Serial_no = @IN_SERIAL_NO
    ELSE
        SELECT *
        FROM TServiceDefine
        WHERE (ServiceType = @IN_SERVICETYPE OR ISNULL(@IN_SERVICETYPE,0) = 0)
            AND (IsValid = @IN_ISVALID OR ISNULL(@IN_ISVALID,99) = 99)
            AND (Executor = @IN_INPUT_MAN OR ISNULL(@IN_INPUT_MAN,0) = 0 )
GO
