﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_FAQ_CLASS @IN_FAQ_CLASS_NAME NVARCHAR(30), --知识库分类名称
                                  @IN_INPUT_MAN      INT,
								  @OUT_FAQ_CLASS_NO  NVARCHAR(10) OUTPUT
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT, @SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -19004000, @IBUSI_FLAG = 19004
    SELECT @SBUSI_NAME = N'添加知识库分类', @SSUMMARY = N'添加知识库分类'
    IF EXISTS(SELECT * FROM TMENUINFO WHERE MENU_ID LIKE 'W05%' AND MENU_ID <> 'W05' AND MENU_NAME = @IN_FAQ_CLASS_NAME)
        RETURN @V_RET_CODE - 1 --分类名称已经存在

    DECLARE @V_FAQ_CLASS_NO NVARCHAR(10)
    SELECT @V_FAQ_CLASS_NO = MAX(MENU_ID) FROM TMENUINFO WHERE MENU_ID LIKE 'W05%' AND MENU_ID <> 'W05'
    SELECT @V_FAQ_CLASS_NO = dbo.THIRTYSIXADD(@V_FAQ_CLASS_NO,2)

    BEGIN TRANSACTION

    INSERT INTO TMENUINFO(MENU_ID,MENU_NAME,MENU_INFO,PARENT_ID,BOTTOM_FLAG,URL)
        SELECT @V_FAQ_CLASS_NO,@IN_FAQ_CLASS_NAME,N'知识库>>'+@IN_FAQ_CLASS_NAME,N'W05',1,N'/wiki/wiki_list.jsp'
	SELECT @OUT_FAQ_CLASS_NO = @V_FAQ_CLASS_NO	
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    INSERT INTO TFUNCTYPE(MENU_ID,FUNC_ID,FUNC_NAME)
        SELECT           @V_FAQ_CLASS_NO,108,N'查询'
        UNION ALL SELECT @V_FAQ_CLASS_NO,100,N'编辑'
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'添加知识库分类'
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    COMMIT TRANSACTION
    RETURN 100
GO
