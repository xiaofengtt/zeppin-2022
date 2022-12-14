USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TGAINLEVELRATE @IN_SERIAL_NO      INTEGER,        --记录号
                                         @IN_DF_SERIAL_NO   INTEGER         --TGAINLEVEL.SERIAL_NO
WITH ENCRYPTION 
AS
    SELECT * FROM TGAINLEVELRATE
        WHERE (SERIAL_NO = @IN_SERIAL_NO OR ISNULL(@IN_SERIAL_NO,0) = 0)
            AND (DF_SERIAL_NO = @IN_DF_SERIAL_NO OR ISNULL(@IN_DF_SERIAL_NO,0) = 0)
        ORDER BY START_DATE ASC
GO
