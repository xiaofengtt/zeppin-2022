USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TMANUALSCORING @IN_MANUAL_ID     INT,                   --操作数ID(打分项)
                                         @IN_INPUT_MAN     INT,                   --操作员
                                         @IN_OPERAND_NO    NVARCHAR(16) = NULL,   --操作数编号
                                         @IN_OPERAND_NAME  NVARCHAR(60) = NULL    --操作数名称

                                        
WITH ENCRYPTION
AS
    BEGIN
    SELECT B.*,A.OPERAND_NO,A.OPERAND_NAME
        FROM TSCOREOPERAND A ,TMANUALSCORING B
            WHERE  (B.MANUAL_ID = @IN_MANUAL_ID OR ISNULL(@IN_MANUAL_ID,'') = N'')
                AND (A.OPERAND_NO LIKE '%' + @IN_OPERAND_NO + '%' OR ISNULL(@IN_OPERAND_NO,'') = N'')
                AND (A.OPERAND_NAME LIKE '%' + @IN_OPERAND_NAME + '%' OR ISNULL(@IN_OPERAND_NAME,'') = N'')
                AND A.SCORING = 1 AND A.SOURCE = 1 AND A.OPERAND_ID = B.OPERAND_ID
    END
    RETURN 100
GO
