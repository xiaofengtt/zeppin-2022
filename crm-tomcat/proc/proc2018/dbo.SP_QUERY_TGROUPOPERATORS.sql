﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TGROUPOPERATORS @IN_GROUP_ID int
WITH ENCRYPTION
AS
   SELECT OP_CODE,OP_NAME FROM TOPTOGROUP WHERE GROUP_ID = @IN_GROUP_ID
GO