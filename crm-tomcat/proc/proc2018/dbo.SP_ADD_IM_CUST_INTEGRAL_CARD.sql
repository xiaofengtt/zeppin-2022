USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_IM_CUST_INTEGRAL_CARD @IN_CUST_ID        INT,
                                              @IN_BUSI_TYPE      INT,            --1购买 2兑换
                                              @IN_BUSI_ID        INT,            --对应业务的记录号
                                              @IN_AMOUNT         DECIMAL(16,2),  --发生额
                                              @IN_AD_INTEGRAL    INT,            --积分
                                              @IN_RULE_ID        INT,            --规则ID
                                              @IN_RDTL_ID        INT,            --明细
                                              @IN_RAMOUNT_ID     INT,            --额度ID
                                              @IN_PRODUCT_ID     INT,            --对应产品
                                              @IN_INPUT_MAN      INT = 0         --操作员
WITH ENCRYPTION
AS
    DECLARE @V_CUST_LEVEL_ID INT, @V_CUST_NAME NVARCHAR(60)
    DECLARE @V_RULE_INTEGAL INT,@V_RULE_INTEGAL1 INT,@V_TOTAL_INTEGRAL INT,@V_TOTAL_INTEGRAL1 INT
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)

    SELECT @V_RET_CODE = -30602000, @IBUSI_FLAG = 30601
    SELECT @SBUSI_NAME = N'添加客户积分卡信息'
    SELECT @V_CUST_NAME = CUST_NAME FROM TCustomers WHERE CUST_ID = @IN_CUST_ID

    BEGIN TRANSACTION
        --1. 处理【积分日志表】
        INSERT INTO IMRULELOG(LOG_TYPE,CUST_ID,BUSI_TYPE,BUSI_ID,AMOUNT,AD_INTEGAL,AD_BALANCE,RULE_ID,RDTL_ID,RAMOUNT_ID,PRODUCT_ID,TRACT_TIME,REMARK)
                VALUES(1,@IN_CUST_ID,@IN_BUSI_TYPE,@IN_BUSI_ID,@IN_AMOUNT,@IN_AD_INTEGRAL,0,@IN_RULE_ID,@IN_RDTL_ID,@IN_RAMOUNT_ID,@IN_PRODUCT_ID, GETDATE(),N'系统自动计算生成')
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END

        --2. 处理【客户积分卡表】
        IF NOT EXISTS(SELECT 1 FROM IM_CUST_INTEGRAL_CARD WHERE CUST_ID = @IN_CUST_ID)
        BEGIN
        --2.1. 第一次购买
            SELECT @V_CUST_LEVEL_ID = CUST_LEVEL_ID FROM IM_CUST_LEVEL WHERE @IN_AD_INTEGRAL >LOWER_VALUE AND (@IN_AD_INTEGRAL <= UPPER_VALUE OR ISNULL(UPPER_VALUE,0) = 0)

            INSERT INTO IM_CUST_INTEGRAL_CARD(CUST_ID,CUST_NAME,CUST_LEVEL_ID,RULE_INTEGAL,TOTAL_INTEGAL)
                VALUES(@IN_CUST_ID,@V_CUST_NAME,@V_CUST_LEVEL_ID,@IN_AD_INTEGRAL,@IN_AD_INTEGRAL)
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END
        END
        ELSE
        BEGIN
        --2.2. 多次购买  或 兑换
            SELECT @V_TOTAL_INTEGRAL = TOTAL_INTEGAL,@V_RULE_INTEGAL = RULE_INTEGAL FROM IM_CUST_INTEGRAL_CARD WHERE CUST_ID = @IN_CUST_ID

            SET @V_TOTAL_INTEGRAL1 = @V_TOTAL_INTEGRAL + @IN_AD_INTEGRAL
            SET @V_RULE_INTEGAL1 = @V_RULE_INTEGAL + @IN_AD_INTEGRAL

            SELECT @V_CUST_LEVEL_ID = CUST_LEVEL_ID FROM IM_CUST_LEVEL WHERE @V_TOTAL_INTEGRAL1 >LOWER_VALUE AND (@V_TOTAL_INTEGRAL1 <= UPPER_VALUE OR ISNULL(UPPER_VALUE,0) = 0)

            UPDATE IM_CUST_INTEGRAL_CARD
            SET CUST_LEVEL_ID = @V_CUST_LEVEL_ID,
                RULE_INTEGAL = @V_RULE_INTEGAL1,
                TOTAL_INTEGAL = @V_TOTAL_INTEGRAL1,
                CUST_NAME = @V_CUST_NAME
            WHERE CUST_ID = @IN_CUST_ID
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END
        END

        SET @SSUMMARY = N'添加客户积分卡信息，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
