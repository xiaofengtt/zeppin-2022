USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_CAL_INTEGRAL @IN_RULE_ID        INT,                        --规则ID
                                 @IN_ORDER_NO       INT,                        --认明细规则
                                 @IN_AMOUNT         DECIMAL(16,2),              --额度
                                 @OUT_RDTL_ID       INT           OUTPUT,       --明细规则ID,
                                 @OUT_RAMOUNT_ID    INT           OUTPUT,       --额度规则ID
                                 @OUT_INTEGRAL      DECIMAL(16,2) OUTPUT        --输出积分
WITH ENCRYPTION
AS

    DECLARE @V_INTEGRAL_VALUE INT,@V_AMOUNT_RADIX INT
    DECLARE @V_NO_AMOUNT BIT
    DECLARE @V_MULTIPLE INT
    --1. 取出规则
    SELECT @V_INTEGRAL_VALUE = INTEGRAL_VALUE ,@V_AMOUNT_RADIX = AMOUNT_RADIX FROM IM_RULE WHERE @IN_RULE_ID = RULE_ID

    --2. 取出明细规则表(次数)
    SELECT @V_NO_AMOUNT = NO_AMOUNT,@OUT_RDTL_ID = RDTL_ID FROM IM_RULE_DTL
        WHERE @IN_RULE_ID = RULE_ID AND (@IN_ORDER_NO >= LOWER_VALUE AND (@IN_ORDER_NO <= UPPER_VALUE OR ISNULL(UPPER_VALUE,0) = 0))
    --3. 额度规则表
    IF ISNULL(@V_NO_AMOUNT,1) = 0
        SELECT @V_MULTIPLE = MULTIPLE,@OUT_RAMOUNT_ID = RAMOUNT_ID FROM IM_RULE_AMOUNT
            WHERE RDTL_ID = @OUT_RDTL_ID AND (@IN_AMOUNT >= LOWER_VALUE AND (@IN_AMOUNT < UPPER_VALUE OR ISNULL(UPPER_VALUE,0) = 0 ))
    ELSE
        SET @V_MULTIPLE = 1

    --SELECT @IN_AMOUNT,@V_Integral_Value,@V_Amount_Radix,@V_Multiple
    SET @OUT_INTEGRAL = ROUND(@IN_AMOUNT * @V_INTEGRAL_VALUE / @V_AMOUNT_RADIX * @V_MULTIPLE,2,1)
GO
