﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_STAT_CUST_CONTRACT @P_CUST_TYPE    INT,
                                       @IN_INPUT_MAN   INT
WITH ENCRYPTION
AS
    DECLARE @V_FUNC_ID INT,
            @P_SELECT_FLAG  INT, -- 0全选 1 单选 2 多选 11 按产品单元
            @P_ORDERBY      NVARCHAR(60), --排序
            @P_CELL_ID      INT
    DECLARE @V_BOOK_CODE INT, @V_INTRUST_OPERATOR INT
    DECLARE @RET INT, @V_DT_INTRUST INT
    SELECT @V_DT_INTRUST = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'DT_INTRUST'

    SELECT @V_FUNC_ID = 100 --100: 152@客户购买明细简表 200: 153@客户购买明细表
    SELECT @P_SELECT_FLAG = 0, @P_ORDERBY = N'', @P_CELL_ID = 0

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
        exec SRV_Intrust.INTRUST.dbo.SP_STAT_CUST_CONTRACT
            @V_FUNC_ID, @V_BOOK_CODE, '', @P_CUST_TYPE, @P_SELECT_FLAG, @P_ORDERBY, @V_INTRUST_OPERATOR, @P_CELL_ID
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
        exec INTRUST.dbo.SP_STAT_CUST_CONTRACT
            @V_FUNC_ID, @V_BOOK_CODE, '', @P_CUST_TYPE, @P_SELECT_FLAG, @P_ORDERBY, @V_INTRUST_OPERATOR, @P_CELL_ID
    END
GO
