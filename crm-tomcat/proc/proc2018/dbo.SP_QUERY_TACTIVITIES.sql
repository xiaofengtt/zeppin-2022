USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TACTIVITIES   @IN_SERIAL_NO           INT,                        --活动序列号
                                        @IN_ACTIVE_TYPE         NVARCHAR(16) = N'',         --活动类别
                                        @IN_ACTIVE_THEME        NVARCHAR(128) = N'',        --活动主题
                                        @IN_START_DATE          INT = 0 ,                   --开始日期
                                        @IN_END_DATE            INT = 0 ,                   --结束日期
                                        @IN_MANAGE_CODE         INT = 0 ,                   --活动负责人编号
                                        @IN_ACTIVE_FLAG         INT = 0 ,                   --活动标识
                                        @IN_COMPLETE_TIME_UP    INT = 0 ,                   --活动完成时间上限
                                        @IN_COMPLETE_TIME_DOWN  INT = 0 ,                   --活动完成时间下限
                                        @IN_ACTIVITY_FEE_UP     DECIMAL(16,3),              --活动费用上限
                                        @IN_ACTIVITY_FEE_DOWN   DECIMAL(16,3),              --活动费用下限
                                        @IN_CREATOR             INT = 0 ,                   --活动发起人编号
                                        @IN_ACTIVE_CODE         NVARCHAR(16)=''             --活动编号
WITH ENCRYPTION
AS
    IF ISNULL(@IN_SERIAL_NO,0) <> 0
        BEGIN
            SELECT  SERIAL_NO,ACTIVE_TYPE,ACTIVE_TYPE_NAME,ACTIVE_THEME,START_DATE,END_DATE,MANAGE_CODE,MANAGE_MAN,CUSTOMER_TYPE,ACTIVE_PLAN,
                    ACTIVE_TRACE,ACTIVE_RESULT,ACTIVE_FLAG,COMPLETE_TIME,ACTIVITY_FEE,CREATOR_NAME,CREATOR,ACTIVE_CODE
            FROM TACTIVITIES
            WHERE SERIAL_NO = @IN_SERIAL_NO
        END
    ELSE
        BEGIN
            SELECT  SERIAL_NO,ACTIVE_TYPE,ACTIVE_TYPE_NAME,ACTIVE_THEME,START_DATE,END_DATE,MANAGE_CODE,MANAGE_MAN,CUSTOMER_TYPE,ACTIVE_PLAN,
                    ACTIVE_TRACE,ACTIVE_RESULT,ACTIVE_FLAG,COMPLETE_TIME,ACTIVITY_FEE,CREATOR_NAME,CREATOR,ACTIVE_CODE
            FROM TACTIVITIES
            WHERE  (ACTIVE_TYPE = @IN_ACTIVE_TYPE  OR ISNULL(@IN_ACTIVE_TYPE,'') = N'')
                AND (ACTIVE_THEME LIKE '%' + @IN_ACTIVE_THEME + '%' OR ISNULL(@IN_ACTIVE_THEME,'') = N'')
                AND (dbo.GETDATEINT(START_DATE) > =@IN_START_DATE OR ISNULL(@IN_START_DATE,0) = 0 )
                AND (dbo.GETDATEINT(START_DATE) < = @IN_END_DATE OR ISNULL(@IN_END_DATE,0) = 0 )
                AND ((MANAGE_CODE = @IN_MANAGE_CODE OR ISNULL(@IN_MANAGE_CODE,0) = 0 ) OR (CREATOR = @IN_CREATOR OR ISNULL(@IN_CREATOR,0) = 0 ))
                AND (ACTIVE_FLAG = @IN_ACTIVE_FLAG OR ISNULL(@IN_ACTIVE_FLAG,0) = 0 )
                AND (dbo.GETDATEINT(COMPLETE_TIME) > =@IN_COMPLETE_TIME_UP OR ISNULL(@IN_COMPLETE_TIME_UP,0) = 0 )
                AND (dbo.GETDATEINT(COMPLETE_TIME) < = @IN_COMPLETE_TIME_DOWN OR ISNULL(@IN_COMPLETE_TIME_DOWN,0) = 0 )
                AND (ACTIVITY_FEE < = @IN_ACTIVITY_FEE_UP OR ISNULL(@IN_ACTIVITY_FEE_UP,0) = 0)
                AND (ACTIVITY_FEE > = @IN_ACTIVITY_FEE_DOWN OR ISNULL(@IN_ACTIVITY_FEE_DOWN,0) = 0)

                AND (ACTIVE_CODE LIKE '%'+@IN_ACTIVE_CODE+'%' OR ISNULL(@IN_ACTIVE_CODE,'') = N'')
        END

    RETURN 100
GO
