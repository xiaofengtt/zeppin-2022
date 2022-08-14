﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TLOGLIST  @IN_OP_CODE INT,
                                    @IN_BEGIN_DATE INT,
                                    @IN_END_DATE INT,
                                    @IN_SUMMARY NVARCHAR(200) = NULL,
                                    @IN_BUSI_FLAG INT = NULL
WITH ENCRYPTION
AS
    SELECT A.*,B.OP_NAME FROM TLOGLIST A,TOPERATOR B
        WHERE (A.OP_CODE = @IN_OP_CODE OR ISNULL(@IN_OP_CODE,0) = 0 )
            AND (CONVERT(INT,CONVERT(CHAR(8),A.TRADE_TIME,112)) >= @IN_BEGIN_DATE OR ISNULL(@IN_BEGIN_DATE,0) = 0 )
            AND (CONVERT(INT,CONVERT(CHAR(8),A.TRADE_TIME,112)) <= @IN_END_DATE OR ISNULL(@IN_END_DATE,0) = 0)
            AND(A.SUMMARY LIKE '%'+@IN_SUMMARY+'%' OR @IN_SUMMARY IS NULL OR @IN_SUMMARY = N'')
            AND(A.BUSI_FLAG = @IN_BUSI_FLAG OR ISNULL(@IN_BUSI_FLAG,0) = 0)
			AND (B.OP_CODE = @IN_OP_CODE OR ISNULL(@IN_OP_CODE,0) = 0 )
			AND (A.OP_CODE = B.OP_CODE)
        ORDER BY A.SERIAL_NO DESC
GO
