﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  SP_QUERY_TSYSCONTROL_VALUE @IN_FLAG_TYPE NVARCHAR(30)

WITH ENCRYPTION
AS
    SELECT VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = @IN_FLAG_TYPE

GO