USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCOCONTRACTPAYPLAN @IN_PAYPLAN_ID             INT,                  --付款计划ID
                                            @IN_COCONTRACT_ID           INT,                  --主合同ID，对应TCOCONTRACT.COCONTRACT_ID
                                            @IN_PAY_SUMMARY             NVARCHAR(2000),       --付款条件说明
                                            @IN_INPUT_MAN               INT                 --录入人员，对应表TOPERATOR.OP_CODE
WITH ENCRYPTION
AS
    SELECT * FROM TCOCONTRACTPAYPLAN
        WHERE (ISNULL(@IN_PAYPLAN_ID,0) = 0 OR PAYPLAN_ID =@IN_PAYPLAN_ID)
            AND (ISNULL(@IN_COCONTRACT_ID,0) =0 OR COCONTRACT_ID = @IN_COCONTRACT_ID)
            AND (ISNULL(@IN_PAY_SUMMARY,'') ='' OR PAY_SUMMARY LIKE '%'+@IN_PAY_SUMMARY+'%')
GO
