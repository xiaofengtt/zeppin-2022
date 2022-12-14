USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
 CREATE PROCEDURE SP_QUERY_TCOMAINTAINMONEYMX @IN_MAINTAIN_ID   INT,              --维护费(维护合同)ID，对应合同TCOCONTRACTMAINTAIN. MAINTAIN_ID
                                              @IN_MONEYMX_ID    INT,     --到账明细ID，自增长
                                              @IN_INPUT_MAN     INT               --录入人员，对应表TOPERATOR.OP_CODE  
WITH ENCRYPTION
AS
    SELECT * FROM TCOMAINTAINMONEYMX
        WHERE (ISNULL(@IN_MAINTAIN_ID,0) =0 OR MAINTAIN_ID = @IN_MAINTAIN_ID)
            AND (ISNULL(@IN_MONEYMX_ID,0) =0 OR MONEYMX_ID =@IN_MONEYMX_ID)
GO
