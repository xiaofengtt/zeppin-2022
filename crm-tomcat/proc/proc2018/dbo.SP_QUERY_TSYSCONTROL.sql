USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  SP_QUERY_TSYSCONTROL @IN_FLAG_TYPE NVARCHAR(30),
                                       @IN_TYPE_NAME NVARCHAR(30) = N'',
                                       @IN_IS_MODI   INT = 0,
                                       @IN_SUMMARY   NVARCHAR(200) = N''
WITH ENCRYPTION
AS
    SELECT * FROM TSYSCONTROL
    WHERE (FLAG_TYPE = @IN_FLAG_TYPE OR ISNULL(@IN_FLAG_TYPE,'') = N'')
           AND (TYPE_NAME LIKE '%' + @IN_TYPE_NAME + '%' OR ISNULL(@IN_TYPE_NAME,'') = N'')
           AND (SUMMARY LIKE '%' + @IN_SUMMARY + '%' OR ISNULL(@IN_SUMMARY,'') = N'')
           AND (IS_MODI = @IN_IS_MODI OR ISNULL(@IN_IS_MODI,0) = 0)
GO
