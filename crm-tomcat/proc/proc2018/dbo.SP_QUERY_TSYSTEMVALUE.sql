USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TSYSTEMVALUE @IN_OPERAND_V_ID  INT,                  --主键
                                       @IN_INPUT_MAN     INT,                  --操作员
                                       @IN_OPERAND_ID    INT = 0,              --操作数ID(打分项)
                                       @IN_OPERAND_XH    NVARCHAR(20)=NULL,    --序号
                                       @IN_SOURCE_TABLE  NVARCHAR(60)=NULL,    --数据来源表
                                       @IN_SOURCE_FIELD  NVARCHAR(60)=NULL,    --数据来源字段
									   @IN_CUST_TYPE	 INT = 0			   --类别
WITH ENCRYPTION
AS
    BEGIN
	SELECT A.*,B.OPERAND_NAME FROM TSYSTEMVALUE A
		LEFT JOIN TScoreOperand B ON A.OPERAND_ID = B.OPERAND_ID
		WHERE (OPERAND_V_ID = @IN_OPERAND_V_ID OR ISNULL(@IN_OPERAND_V_ID,0)=0)
		AND (A.CUST_TYPE = @IN_CUST_TYPE OR ISNULL(@IN_CUST_TYPE,0)=0)
		AND (A.OPERAND_ID = @IN_OPERAND_ID OR ISNULL(@IN_OPERAND_ID,0)=0)
		AND (A.OPERAND_XH LIKE '%' + @IN_OPERAND_XH + '%' OR ISNULL(@IN_OPERAND_XH,'') = N'')
		AND (A.SOURCE_TABLE = @IN_SOURCE_TABLE OR ISNULL(@IN_SOURCE_TABLE,'') = N'')
        AND (A.SOURCE_FIELD_CN = @IN_SOURCE_FIELD OR ISNULL(@IN_SOURCE_FIELD,'') = N'')
    END
    RETURN 100
GO
