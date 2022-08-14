USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TSCHEDULES    @IN_SERIAL_NO           INT = 0 ,               --ID
                                        @IN_INPUT_MAN           INT = 0 ,               --操作员
                                        @IN_BEGIN_DATE          INT = 0,                --起始时间
                                        @IN_END_DATE            INT = 0,                --结束时间
                                        @IN_SCHEDULE_TYPE       INT,                    --日程类型 --约会/活动/计划
                                        @IN_CHECK_FLAG          INT = 0                 --日程状态 新建/已读/完成
WITH ENCRYPTION
AS
    IF ISNULL(@IN_SERIAL_NO,0) <> 0
        SELECT SERIAL_NO ,SCHEDULE_TYPE,SCHEDULE_NAME,START_DATE,END_DATE,OP_CODE,OP_NAME,CONTENT,CHECK_FLAG,DF_SERIAL_NO
        FROM TSCHEDULES
        WHERE SERIAL_NO = @IN_SERIAL_NO
    ELSE
        SELECT SERIAL_NO ,SCHEDULE_TYPE,SCHEDULE_NAME,START_DATE,END_DATE,OP_CODE,OP_NAME,CONTENT,CHECK_FLAG,DF_SERIAL_NO
        FROM TSCHEDULES
        WHERE OP_CODE = @IN_INPUT_MAN
            AND (dbo.GETDATEINT(START_DATE) >= @IN_BEGIN_DATE OR ISNULL(@IN_BEGIN_DATE,0) = 0 )
            AND (dbo.GETDATEINT(START_DATE) <= @IN_END_DATE OR ISNULL(@IN_END_DATE,0) = 0 )
            AND (SCHEDULE_TYPE = @IN_SCHEDULE_TYPE OR ISNULL(@IN_SCHEDULE_TYPE,0) = 0 )
            AND (CHECK_FLAG = @IN_CHECK_FLAG OR ISNULL(@IN_CHECK_FLAG,0) = 0 )

    RETURN 100
GO
