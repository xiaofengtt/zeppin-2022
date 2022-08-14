﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TMENUINFO_LOAD @IN_MENU_ID NVARCHAR(10),
                                         @IN_LANGUAGE_TYPE INT = 1  --1.zh_CN;2.en_US
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_TMP_MENU_RIGHT TABLE
    (
        MENU_ID     NVARCHAR(10) NOT NULL ,
        MENU_NAME   NVARCHAR(60) NOT NULL ,
        MENU_INFO   NVARCHAR(200),
        PARENT_ID   NVARCHAR(10),
        BOTTOM_FLAG INTEGER NOT NULL,
        URL         NVARCHAR(300),
        LINKTO      NVARCHAR(30),
        TREE_ORDER  NVARCHAR(10)  --排序依据字段
    )
    INSERT INTO @V_TMP_MENU_RIGHT(MENU_ID,MENU_NAME,MENU_INFO,PARENT_ID,BOTTOM_FLAG,URL,LINKTO,TREE_ORDER)
        SELECT A.MENU_ID,A.MENU_NAME,A.MENU_INFO,A.PARENT_ID,A.BOTTOM_FLAG,A.URL,A.LINKTO,A.TREE_ORDER
            FROM TMENUINFO A WHERE MENU_ID = @IN_MENU_ID
    UPDATE @V_TMP_MENU_RIGHT SET URL = ISNULL(B.TYPE_CONTENT,'')+URL
        FROM @V_TMP_MENU_RIGHT A, TDICTPARAM B
        WHERE A.LINKTO = B.TYPE_VALUE AND B.TYPE_VALUE IN ('800001','800002')
    --  如果语言环境为英语
    IF @IN_LANGUAGE_TYPE = 2
    BEGIN
        UPDATE @V_TMP_MENU_RIGHT
        SET MENU_NAME = ISNULL(B.MENU_NAME_EN,B.MENU_NAME),
            MENU_INFO = ISNULL(B.MENU_INFO_EN,B.MENU_INFO)
        FROM @V_TMP_MENU_RIGHT A,TMENUINFO B
        WHERE A.MENU_ID = B.MENU_ID
    END
    SELECT * FROM @V_TMP_MENU_RIGHT
GO
