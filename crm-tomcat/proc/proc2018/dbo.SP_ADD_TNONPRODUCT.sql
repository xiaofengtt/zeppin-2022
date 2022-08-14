USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TNONPRODUCT @IN_NONPRODUCT_NAME      NVARCHAR(60),    --非信托产品名称
                                    @IN_INVESTMENT_DIRECTION NVARCHAR(10),    --投向（字典1155）
                                    @IN_VALID_PRIOD_UNIT     INT,             --期限单位：1天2月3季4年9无
                                    @IN_VALID_PRIOD          INT,             --期限
                                    @IN_START_DATE           INT,             --起始日期
                                    @IN_END_DATE             INT,             --结束日期
                                    @IN_EXPECT_MONEY         DEC(16,3),       --预期产品金额
                                    @IN_EXPECT_RATE1         DEC(9,6),        --预期收益1
                                    @IN_EXPECT_RATE2         DEC(9,6),        --预期收益2
                                    @IN_INVESTMENT_MANAGER   NVARCHAR(60),    --投资管理人
                                    @IN_PARTNER_CUST_ID      INT,             --合伙人企业ID（TCustomers.CUST_ID）
                                    @IN_INPUT_MAN            INT,             --录入操作员
                                    @OUT_NONPRODUCT_ID       INT,             --主键
                                    @IN_DESCRIPTION          NVARCHAR(MAX) = '' --描述(简介)
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_INVESTMENT_DIRECTION_NAME NVARCHAR(30)
    SELECT @SBUSI_NAME = N'增加非信托产品信息'
    SELECT @SSUMMARY = N'增加非信托产品信息'
    SELECT @IBUSI_FLAG = 39001
    SELECT @V_RET_CODE = -39001000

    IF EXISTS(SELECT * FROM TNONPRODUCT WHERE NONPRODUCT_ID <> @OUT_NONPRODUCT_ID AND NONPRODUCT_NAME = @IN_NONPRODUCT_NAME)
        RETURN @V_RET_CODE - 21   -- 非信托产品信息已存在

    BEGIN TRANSACTION

    SELECT @V_INVESTMENT_DIRECTION_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_INVESTMENT_DIRECTION

    INSERT INTO TNONPRODUCT(NONPRODUCT_NAME, INVESTMENT_DIRECTION, INVESTMENT_DIRECTION_NAME, VALID_PRIOD_UNIT, VALID_PRIOD, START_DATE,
          END_DATE, EXPECT_MONEY,EXPECT_RATE1, EXPECT_RATE2, INVESTMENT_MANAGER, PARTNER_CUST_ID,STATUS, INPUT_MAN,INPUT_TIME, CHECK_FLAG,
          DESCRIPTION)
                    VALUES(@IN_NONPRODUCT_NAME, @IN_INVESTMENT_DIRECTION,@V_INVESTMENT_DIRECTION_NAME, @IN_VALID_PRIOD_UNIT,@IN_VALID_PRIOD,
                            @IN_START_DATE,@IN_END_DATE,@IN_EXPECT_MONEY,@IN_EXPECT_RATE1/100,@IN_EXPECT_RATE2/100,
                            @IN_INVESTMENT_MANAGER,@IN_PARTNER_CUST_ID,'110203', @IN_INPUT_MAN,GETDATE(),1,
                            @IN_DESCRIPTION)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    SELECT @SSUMMARY = N'增加产品，产品编号：'+RTRIM(CONVERT(NVARCHAR(16),@OUT_NONPRODUCT_ID))+N',产品名称：'+RTRIM(@IN_NONPRODUCT_NAME)
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
