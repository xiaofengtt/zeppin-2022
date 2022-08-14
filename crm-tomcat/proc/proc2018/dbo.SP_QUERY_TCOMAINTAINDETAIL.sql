USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCOMAINTAINDETAIL @IN_MAINTAINDETAIL_ID             INT, --维护费明细ID
                                            @IN_MAINTAIN_ID                   INT,            --维护费ID，对应TCOCONTRACTMAINTAIN. MAINTAIN_ID
                                            @IN_INPUT_MAN                     INT,            --录入操作员 
                                            @IN_COCONTRACT_ID                 INT=0,          --主合同ID
                                            @IN_COPRODUCT_ID                  INT=0           --产品ID         


WITH ENCRYPTION
AS
    SELECT * FROM TCOMAINTAINDETAIL
        WHERE (ISNULL(@IN_MAINTAINDETAIL_ID,0) =0 OR MAINTAINDETAIL_ID =@IN_MAINTAINDETAIL_ID)
            AND (ISNULL(@IN_MAINTAIN_ID,0) =0 OR MAINTAIN_ID =@IN_MAINTAIN_ID)
            AND (ISNULL(@IN_COCONTRACT_ID,0) =0 OR COCONTRACT_ID =@IN_COCONTRACT_ID)
            AND (ISNULL(@IN_COPRODUCT_ID,0) =0 OR COPRODUCT_ID =@IN_COPRODUCT_ID)

GO
