USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_PORTALMENU3 @IN_INPUT_MAN     NVARCHAR(60),
                                      @IN_LANGUAGE_FLAG NVARCHAR(10) = ''
WITH ENCRYPTION
AS
    SET NOCOUNT ON

    DECLARE @V_USER_ID INT
    SELECT @V_USER_ID = USER_ID FROM TSYSTEMINFO
    DECLARE @V_TMP_MENU_RIGHT TABLE
    (
        OP_CODE     INT,
        MENU_ID     NVARCHAR (20) NOT NULL ,
        MENU_NAME   NVARCHAR (400) NOT NULL ,
        MENU_INFO   NVARCHAR (2000),
        PARENT_ID   NVARCHAR (20),
        BOTTOM_FLAG INTEGER NOT NULL,
        URL         NVARCHAR(1000),
        TREE_ORDER  NVARCHAR(10),  --排序依据字段
        ICON_CLS    NVARCHAR(200),
        MENU_CODE   NVARCHAR(60)
    )
    DECLARE @V_TEMPRIGHT TABLE(MENU_ID NVARCHAR(20))
    INSERT INTO @V_TEMPRIGHT SELECT MENU_ID FROM TROLERIGHT
    WHERE ROLE_ID IN(SELECT ROLE_ID FROM TOPROLE WHERE OP_CODE = @IN_INPUT_MAN)
    GROUP BY MENU_ID

    --TMENUINFO中MENU_ID以'M'开头的项为快捷功能项分类，对应FUNC_ID为快捷功能项
    --插入操作员有权限的快捷功能项
    IF EXISTS(SELECT * FROM TSYSTEMINFO WHERE INIT_FLAG = 1)
        INSERT INTO @V_TMP_MENU_RIGHT
            SELECT @IN_INPUT_MAN, B.MENU_ID,B.MENU_NAME,B.MENU_INFO,B.PARENT_ID, B.BOTTOM_FLAG, B.URL, B.TREE_ORDER, B.ICON_CLS, B.MENU_ID
                FROM @V_TEMPRIGHT A, TMENUINFO B
                WHERE A.MENU_ID = B.MENU_ID AND B.MENU_ID LIKE 'M%'
                    AND (B.USER_ID = @V_USER_ID OR B.USER_ID = 0)
    ELSE
        INSERT INTO @V_TMP_MENU_RIGHT
            SELECT @IN_INPUT_MAN, B.MENU_ID,B.MENU_NAME,B.MENU_INFO,B.PARENT_ID, B.BOTTOM_FLAG, B.URL, B.TREE_ORDER, B.ICON_CLS, B.MENU_ID
                FROM @V_TEMPRIGHT A, TMENUINFO B
                WHERE A.MENU_ID = B.MENU_ID AND B.MENU_ID LIKE 'M%'
                    AND B.MENU_ID NOT IN ('10104', '20409') AND (B.USER_ID = @V_USER_ID OR B.USER_ID = 0)
    UPDATE @V_TMP_MENU_RIGHT SET PARENT_ID = '' WHERE PARENT_ID = 'M00'
    IF ISNULL(@IN_LANGUAGE_FLAG,'')='en'
    BEGIN
        UPDATE @V_TMP_MENU_RIGHT SET MENU_NAME = ISNULL(B.MENU_NAME,A.MENU_NAME), MENU_INFO = ISNULL(B.MENU_INFO,A.MENU_INFO)
            FROM @V_TMP_MENU_RIGHT A, TMENUINFO B
            WHERE A.MENU_ID = B.MENU_ID
    END
    --快捷功能项
    INSERT INTO @V_TMP_MENU_RIGHT
        SELECT @IN_INPUT_MAN, A.MENU_ID+CONVERT(NVARCHAR(10),B.FUNC_ID), B.FUNC_NAME,'',A.MENU_ID,1,B.URL,A.TREE_ORDER,B.ICON_CLS, B.MENU_ID
            FROM @V_TMP_MENU_RIGHT A, TFUNCTYPE B
            WHERE A.MENU_ID = B.MENU_ID
    --附加其他菜单
    INSERT INTO @V_TMP_MENU_RIGHT(OP_CODE,MENU_ID,MENU_CODE,MENU_NAME,PARENT_ID,BOTTOM_FLAG,URL)
        SELECT @IN_INPUT_MAN,'M0099','M0099','附加','',2,''
    --添加“我的菜单”
    INSERT INTO @V_TMP_MENU_RIGHT
        SELECT @IN_INPUT_MAN, A.MENU_ID, B.ALIAS_NAME,'','M0099',1,'/addlog.jsp?menu_id='+A.MENU_ID,A.TREE_ORDER,A.ICON_CLS,'M'+A.MENU_ID
            FROM TMENUINFO A, TMYMENUINFO B
            WHERE B.OP_CODE = @IN_INPUT_MAN AND B.MENU_ID = A.MENU_ID
    --添加某些常用菜单，由菜单编号直接指定
    INSERT INTO @V_TMP_MENU_RIGHT
        SELECT @IN_INPUT_MAN, A.MENU_ID, A.MENU_NAME,'','M0099',1,'/addlog.jsp?menu_id='+A.MENU_ID,A.TREE_ORDER,A.ICON_CLS,'M'+A.MENU_ID
            FROM TMENUINFO A
            WHERE A.BOTTOM_FLAG = 1 AND A.MENU_ID IN ('')

    SELECT MENU_CODE,MENU_ID,PARENT_ID,MENU_NAME,'' AS APP_CODE,URL AS PATH,ICON_CLS FROM @V_TMP_MENU_RIGHT ORDER BY MENU_CODE,MENU_ID
    RETURN @@ROWCOUNT
GO
