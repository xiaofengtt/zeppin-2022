﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TTEAMMEMBER           @IN_SERIAL_NO           INT,        --项目组成员ID
                                                @IN_TEAM_ID             INT         --项目组ID
WITH ENCRYPTION
AS

    SELECT A.*,B.OP_NAME AS TEAM_MEMBER_NAME
        FROM TTEAMMEMBER A,TOPERATOR B
    WHERE (SERIAL_NO = @IN_SERIAL_NO OR ISNULL(@IN_SERIAL_NO,0) = 0)
        AND (TEAM_ID = @IN_TEAM_ID OR ISNULL(@IN_TEAM_ID,0) = 0 )
        AND A.TEAM_MEMBER =B.OP_CODE
    RETURN 100
GO