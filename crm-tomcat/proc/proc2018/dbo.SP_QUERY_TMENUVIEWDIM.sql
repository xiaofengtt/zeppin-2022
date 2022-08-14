﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TMENUVIEWDIM  @IN_MENU_ID     NVARCHAR(30),
                                        @IN_OP_CODE     INT
WITH ENCRYPTION
AS
    SELECT *
        FROM TMENUVIEWDIM
        WHERE (MENU_ID = @IN_MENU_ID OR ISNULL(@IN_MENU_ID,'') = N'') AND
              (OP_CODE = @IN_OP_CODE OR ISNULL(@IN_OP_CODE,0) = 0)
GO