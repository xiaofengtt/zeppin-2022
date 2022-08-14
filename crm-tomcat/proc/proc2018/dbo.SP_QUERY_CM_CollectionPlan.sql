USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_CM_CollectionPlan      @IN_CONTRACT_ID         INT = 0            		--合同ID
                                        	
WITH ENCRYPTION
AS
    IF ISNULL(@IN_CONTRACT_ID,0) <> 0
        BEGIN
            SELECT * FROM CM_TCOLLECTION_PLAN
                WHERE CONTRACT_ID = @IN_CONTRACT_ID
        END
    ELSE
        BEGIN
            SELECT * FROM CM_TCOLLECTION_PLAN
                                      
        END

GO
