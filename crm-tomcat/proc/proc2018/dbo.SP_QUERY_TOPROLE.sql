﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TOPROLE  @IN_SERIAL_NO INT,
                                   @IN_OP_CODE   INT,
                                   @IN_ROLE_ID   INT
WITH ENCRYPTION
AS
    IF ISNULL(@IN_SERIAL_NO,0) <> 0
        SELECT A.*,B.ROLE_NAME FROM TOPROLE A,TROLE B where A.ROLE_ID = B.ROLE_ID
    ELSE
        SELECT A.*,B.ROLE_NAME FROM TOPROLE A,TROLE B
        WHERE (ISNULL(@IN_ROLE_ID,0) = 0 OR A.ROLE_ID = @IN_ROLE_ID)
          AND  A.ROLE_ID = B.ROLE_ID
              AND (ISNULL(@IN_OP_CODE,0) = 0 OR A.OP_CODE = @IN_OP_CODE)
GO
