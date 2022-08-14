USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TSYSMESSAGE
                                 @IN_SERIAL_NO           INT,              --序号
                                 @IN_TITLE               NVARCHAR(200),    --消息主题
                                 @IN_FROM_OPCODE         INT,              --消息发起人
                                 @IN_TO_OPCODE           INT,              --消息接收人
                                 @IN_DATE_START          INT,              --消息日期（起）
                                 @IN_DATE_END            INT,              --消息日期（止）
                                 @IN_IS_READ             INT,              --是否已阅读 1否2是3已反馈
                                 @IN_INPUT_MAN           INT               --操作员
                                        
WITH ENCRYPTION
AS
    DECLARE @V_DATE INT, @V_OP_NAME NVARCHAR(20)
    
    SET @V_DATE=CAST(CONVERT(VARCHAR,GETDATE(),112) AS INT)
    --IF ISNULL(@IN_DATE_START,0)=0 SET @IN_DATE_START=0
    IF ISNULL(@IN_DATE_END,0)=0 SET @IN_DATE_END=@V_DATE
    
    SELECT A.SERIAL_NO,A.TITLE,OP_NAME,A.MSG,A.FROM_OPCODE,A.TO_OPCODE,A.INPUT_TIME,A.IS_READ, B.OP_NAME FROM_OPNAME
        FROM TSYSMESSAGE A LEFT JOIN TOPERATOR B ON A.FROM_OPCODE=B.OP_CODE
        WHERE (A.SERIAL_NO = @IN_SERIAL_NO OR ISNULL(@IN_SERIAL_NO,0)=0)
            AND (dbo.GETDATEINT(INPUT_TIME) >= @IN_DATE_START OR ISNULL(@IN_DATE_START,0)=0)
            AND (dbo.GETDATEINT(INPUT_TIME) <= @IN_DATE_END)
            AND (A.TITLE LIKE '%'+@IN_TITLE+'%' OR ISNULL(@IN_TITLE,'') = '' )
            AND (A.FROM_OPCODE=@IN_FROM_OPCODE OR ISNULL(@IN_FROM_OPCODE,0)=0)
            AND (A.TO_OPCODE=@IN_TO_OPCODE OR ISNULL(@IN_TO_OPCODE,0)=0)
            AND (A.IS_READ=@IN_IS_READ OR ISNULL(@IN_IS_READ,0)=0)
    RETURN 100
GO
