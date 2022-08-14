﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE SP_ADD_TPROFIT @IN_SUBSCRIBE_ID     INT,          --非信托产品合同序号
                                @IN_PROFIT_MONEY     DEC(16,3),    --收益金额
                                @IN_SY_DATE          INT,          --收益分配日期
                                @IN_INPUT_MAN        INT           --操作员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200),@TABLE_ID NVARCHAR(60)
    DECLARE @V_CUST_ID INT, @V_BANK_ID NVARCHAR(10), @V_BANK_NAME NVARCHAR(30), @V_BANK_SUB_NAME NVARCHAR(60), @V_BANK_ACCT NVARCHAR(60)
    SELECT @SBUSI_NAME = N'添加非信托产品收益信息'
    SELECT @SSUMMARY = N'添加非信托产品收益信息'
    SELECT @IBUSI_FLAG = 39016
    SELECT @V_RET_CODE = -39016000
    SELECT @V_CUST_ID = CUST_ID, @V_BANK_ID = BANK_ID, @V_BANK_NAME = BANK_NAME, @V_BANK_SUB_NAME = BANK_SUB_NAME
                , @V_BANK_ACCT = BANK_ACCT FROM TSUBSCRIBE WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID

    IF NOT EXISTS(SELECT * FROM TQUOTIENT WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID)
        RETURN @V_RET_CODE - 11   --非信托产品份额信息不存在

    IF NOT EXISTS(SELECT * FROM TSUBSCRIBE WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID AND STATUS = '120101' AND CHECK_FLAG = 2)
        RETURN @V_RET_CODE - 22   --非信托产品合同非正常状态

    BEGIN TRANSACTION

    IF EXISTS(SELECT 1 FROM TPROFIT WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID AND SY_DATE = @IN_SY_DATE)
        UPDATE TPROFIT SET PROFIT_MONEY = PROFIT_MONEY + @IN_PROFIT_MONEY
            WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID AND SY_DATE = @IN_SY_DATE
    ELSE
    BEGIN
    INSERT INTO TPROFIT(SUBSCRIBE_ID, CUST_ID, PROFIT_MONEY,SY_DATE, SY_TYPE, SY_TYPE_NAME,
                BANK_ID, BANK_NAME, BANK_SUB_NAME, BANK_ACCT, INPUT_MAN, INPUT_TIME, CHECK_FLAG)
            VALUES(@IN_SUBSCRIBE_ID, @V_CUST_ID, @IN_PROFIT_MONEY ,@IN_SY_DATE ,111602,'中间收益',
            @V_BANK_ID, @V_BANK_NAME, @V_BANK_SUB_NAME, @V_BANK_ACCT,@IN_INPUT_MAN, GETDATE(), 1)
    END
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    SELECT @SSUMMARY = N'增加产品收益，合同编号：'+RTRIM(CONVERT(NVARCHAR(16),@IN_SUBSCRIBE_ID))+N'客户编号：'+RTRIM(CONVERT(CHAR(16),@V_CUST_ID))
                        +N'日期：'+RTRIM(CONVERT(NVARCHAR(16),@IN_SY_DATE))
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