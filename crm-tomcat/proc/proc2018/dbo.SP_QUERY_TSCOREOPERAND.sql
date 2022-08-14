USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TSCOREOPERAND @IN_OPERAND_ID    INT,                    --操作数ID(打分项)
                                        @IN_INPUT_MAN     INT,                    --操作员
                                        @IN_SUBJECT_ID    INT = 0,                --科目ID
                                        @IN_OPERAND_NO    NVARCHAR(16) = NULL,    --操作数编号
                                        @IN_OPERAND_NAME  NVARCHAR(60) = NULL,    --操作数名称
                                        @IN_SCORING       INT = 0,                --人工（系统）打分 1人工，2系统
                                        @IN_SOURCE        INT = 0                 --来源(仅人工)1手工打分2通过计算打分

                                        
WITH ENCRYPTION
AS
    BEGIN
    SELECT A.*,(SELECT B.SUBJECT_NAME FROM TSCORESUBJECT B WHERE B.SUBJECT_ID = A.SUBJECT_ID) AS SUBJECT_NAME 
        FROM TSCOREOPERAND A 
            WHERE  (A.OPERAND_ID = @IN_OPERAND_ID OR ISNULL(@IN_OPERAND_ID,'') = N'')
                AND (A.SUBJECT_ID = @IN_SUBJECT_ID OR ISNULL(@IN_SUBJECT_ID,'') = N'')
                AND (A.OPERAND_NO LIKE '%' + @IN_OPERAND_NO + '%' OR ISNULL(@IN_OPERAND_NO,'') = N'')
                AND (A.OPERAND_NAME LIKE '%' + @IN_OPERAND_NAME + '%' OR ISNULL(@IN_OPERAND_NAME,'') = N'')
                AND (A.SCORING = @IN_SCORING OR ISNULL(@IN_SCORING,'') = N'')
                AND (A.SOURCE = @IN_SOURCE OR ISNULL(@IN_SOURCE,'') = N'')
    END
    RETURN 100
GO
