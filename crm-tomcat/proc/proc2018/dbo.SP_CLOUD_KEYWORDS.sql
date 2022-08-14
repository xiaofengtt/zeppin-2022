﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_CLOUD_KEYWORDS
WITH ENCRYPTION
AS
    --SELECT TOP 20 * FROM TINFOREPOS_KEYWORDS ORDER BY SEARCH_TIME

    CREATE TABLE #TMP_CLOUD_KEYWORDS(KEY_WORD NVARCHAR(200),SEARCH_TIMES INT,KEY_TIMES INT,SEARCH_TIME DATETIME)
    DECLARE @VT_CLOUD_KEYWORDS TABLE(KEY_WORD NVARCHAR(200),TIMES INT)

    --统计知识库中关键词出现的次数
    DECLARE @V_FAQ_KEYWORDS NVARCHAR(200)
    DECLARE CUR1 CURSOR FOR SELECT FAQ_KEYWORDS FROM TFAQS WHERE ISNULL(FAQ_KEYWORDS,'') <> ''
    OPEN CUR1
    FETCH NEXT FROM CUR1 INTO @V_FAQ_KEYWORDS
    WHILE @@FETCH_STATUS = 0
    BEGIN
        UPDATE #TMP_CLOUD_KEYWORDS SET KEY_TIMES = KEY_TIMES + 1
            FROM #TMP_CLOUD_KEYWORDS A, dbo.F_SPLIT(@V_FAQ_KEYWORDS,' ') B
            WHERE A.KEY_WORD = B.TEXTSTR
        INSERT INTO #TMP_CLOUD_KEYWORDS(KEY_WORD,KEY_TIMES)
            SELECT TEXTSTR,1 FROM dbo.F_SPLIT(@V_FAQ_KEYWORDS,' ') A
                WHERE NOT EXISTS(SELECT 1 FROM #TMP_CLOUD_KEYWORDS Z WHERE Z.KEY_WORD = A.TEXTSTR)
        FETCH NEXT FROM CUR1 INTO @V_FAQ_KEYWORDS
    END
    CLOSE CUR1
    DEALLOCATE CUR1
    INSERT INTO @VT_CLOUD_KEYWORDS(KEY_WORD,TIMES)
        SELECT TOP 10 KEY_WORD,KEY_TIMES FROM #TMP_CLOUD_KEYWORDS ORDER BY KEY_TIMES DESC
    --搜索词条的次数
    UPDATE #TMP_CLOUD_KEYWORDS SET SEARCH_TIMES = B.SEARCH_TIMES, SEARCH_TIME = B.SEARCH_TIME
        FROM #TMP_CLOUD_KEYWORDS A, TINFOREPOS_KEYWORDS B
        WHERE A.KEY_WORD = B.KEY_WORD AND B.KEY_WORD <> ''
    INSERT INTO #TMP_CLOUD_KEYWORDS(KEY_WORD,SEARCH_TIMES,SEARCH_TIME)
        SELECT KEY_WORD,SEARCH_TIMES,SEARCH_TIME FROM TINFOREPOS_KEYWORDS A
            WHERE A.KEY_WORD <> '' AND NOT EXISTS(SELECT 1 FROM #TMP_CLOUD_KEYWORDS Z WHERE Z.KEY_WORD = A.KEY_WORD)
    INSERT INTO @VT_CLOUD_KEYWORDS(KEY_WORD,TIMES)
        SELECT TOP 10 KEY_WORD,SEARCH_TIMES FROM #TMP_CLOUD_KEYWORDS ORDER BY SEARCH_TIMES DESC, SEARCH_TIME DESC
    --返回综合结果
    SELECT KEY_WORD,SUM(TIMES) AS TIMES FROM @VT_CLOUD_KEYWORDS GROUP BY KEY_WORD
GO
