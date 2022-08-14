USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_STAT_CUSTOMER_LEVEL     @IN_FUNC_ID     INT,  --统计功能.100:忠诚客户明细表 200:潜在流失客户明细表 300:已流失客户明细表
                                            @IN_INPUT_MAN   INT,  --CRM系统操作员
                                            @IN_BEGIN_DATE  INT=0,
                                            @IN_SCOPE       INT=0 --统计范围:0全部，1本人；2本部门
WITH ENCRYPTION
AS
    DECLARE @V_FUNC_ID INT
    DECLARE @V_BOOK_CODE INT, @V_INTRUST_OPERATOR INT
    DECLARE @RET INT, @V_DT_INTRUST INT
    SELECT @V_DT_INTRUST = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'DT_INTRUST'

    SELECT @V_FUNC_ID = @IN_FUNC_ID --300:已流失客户明细表

    IF @V_DT_INTRUST = 1 --启用分布式
    BEGIN
        SELECT @V_BOOK_CODE = INTRUST_BOOKCODE, @V_INTRUST_OPERATOR = INTRUST_Operator
            FROM SRV_Intrust.INTRUST.dbo.TOperatorMap WHERE CRM_Operator = @IN_INPUT_MAN
        IF @V_INTRUST_OPERATOR IS NULL --从sso映射表中未取到Intrust操作员时，使用CRM操作员
            SELECT @V_INTRUST_OPERATOR = @IN_INPUT_MAN
        IF @V_BOOK_CODE IS NULL --从sso映射表中未取到Intrust帐套时，使用Intrust操作员表设置的默认帐套
            SELECT @V_BOOK_CODE = BOOK_CODE FROM SRV_Intrust.INTRUST.dbo.TOPERATOR WHERE OP_CODE = @V_INTRUST_OPERATOR
        IF @V_BOOK_CODE IS NULL --最后取不到使用默认值1
            SELECT @V_BOOK_CODE = 1

        --调用Intrust中 SP_STAT_CUSTOMER_LEVEL 过程
        exec SRV_Intrust.INTRUST.dbo.SP_STAT_CUSTOMER_LEVEL @V_FUNC_ID, @V_BOOK_CODE, @V_INTRUST_OPERATOR,@IN_BEGIN_DATE,@IN_SCOPE
    END
    ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
    BEGIN
        SELECT @V_BOOK_CODE = INTRUST_BOOKCODE, @V_INTRUST_OPERATOR = INTRUST_Operator
            FROM INTRUST.dbo.TOperatorMap WHERE CRM_Operator = @IN_INPUT_MAN
        IF @V_INTRUST_OPERATOR IS NULL --从sso映射表中未取到Intrust操作员时，使用CRM操作员
            SELECT @V_INTRUST_OPERATOR = @IN_INPUT_MAN
        IF @V_BOOK_CODE IS NULL --从sso映射表中未取到Intrust帐套时，使用Intrust操作员表设置的默认帐套
            SELECT @V_BOOK_CODE = BOOK_CODE FROM INTRUST.dbo.TOPERATOR WHERE OP_CODE = @V_INTRUST_OPERATOR
        IF @V_BOOK_CODE IS NULL --最后取不到使用默认值1
            SELECT @V_BOOK_CODE = 1

        --调用Intrust中 SP_STAT_CUSTOMER_LEVEL 过程
        exec INTRUST.dbo.SP_STAT_CUSTOMER_LEVEL @V_FUNC_ID, @V_BOOK_CODE, @V_INTRUST_OPERATOR,@IN_BEGIN_DATE,@IN_SCOPE
    END
GO
