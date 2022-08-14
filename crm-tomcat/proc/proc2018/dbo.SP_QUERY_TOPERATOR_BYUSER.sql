﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  SP_QUERY_TOPERATOR_BYUSER @IN_LOGIN_USER NVARCHAR(20)
WITH ENCRYPTION
AS
    SELECT * FROM TOPERATOR
    WHERE LOGIN_USER = @IN_LOGIN_USER AND (RTRIM(ISNULL(@IN_LOGIN_USER,'')) <> '')
GO