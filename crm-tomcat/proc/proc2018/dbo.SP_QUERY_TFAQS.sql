USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TFAQS @IN_SERIAL_NO   INT,
                                @IN_INPUT_MAN   INT = NULL,
                                @IN_WIKI_CLASS  NVARCHAR(30) = NULL,   --知识库文章分类
                                @IN_SEARCHKEY   NVARCHAR(200) = NULL,  --搜索关键字
                                @IN_SORTFIELD   NVARCHAR(200) = NULL,  --排序字段
                                @IN_AUTHOR      INT = NULL             --作者
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_SQL NVARCHAR(2000)
    SET @IN_WIKI_CLASS = ISNULL(@IN_WIKI_CLASS,'')
    IF ISNULL(@IN_SERIAL_NO,0) <> 0
    BEGIN
        SELECT * FROM EFCRM..V_FAQS A WHERE SERIAL_NO = @IN_SERIAL_NO
    END
    ELSE
    BEGIN
        SET @V_SQL = 'SELECT * FROM EFCRM..V_FAQS A WHERE 1 = 1'
        IF @IN_WIKI_CLASS <> ''
            SET @V_SQL = @V_SQL + ' AND CLASS_NO = ''' + @IN_WIKI_CLASS + ''''
        IF @IN_AUTHOR <> 0 AND @IN_AUTHOR IS NOT NULL
            SET @V_SQL = @V_SQL + ' AND INPUT_MAN = ' + CONVERT(NVARCHAR(30),@IN_AUTHOR)
        IF @IN_SEARCHKEY <> '' AND @IN_SEARCHKEY IS NOT NULL
            SET @V_SQL = @V_SQL + ' AND (A.FAQ_TITLE LIKE ''%'+@IN_SEARCHKEY+'%'' OR A.FAQ_KEYWORDS LIKE ''%'+@IN_SEARCHKEY+'%'' OR A.FAQ_CONTENT LIKE ''%'+@IN_SEARCHKEY+'%'') '
        IF @IN_SORTFIELD <> '' AND @IN_SORTFIELD IS NOT NULL
            SET @V_SQL = @V_SQL + ' ORDER BY ' + @IN_SORTFIELD
        PRINT @V_SQL
        EXECUTE( @V_SQL )
    END
GO
