﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TCOCONTRACTMAINTAIN @IN_COCONTRACTMAINTAIN_SUB_BH     NVARCHAR(200),        --维护合同编号
                                            @IN_CUST_ID                       INT,                  --客户ID，对应表TCustomers.CUST_ID
                                            @IN_MAIN_PERIOD                   INT,                  --维护周期
                                            @IN_MAIN_PERIOD_UNIT              INT,                  --维护周期单位（1日，2月，3年）
                                            @IN_MAIN_PRO_NAME                 NVARCHAR(60),         --维护项目名称
                                            @IN_COLLECT_TIME                  INT,                  --预计收款时间
                                            @IN_START_DATE                    INT,                  --起始日期
                                            @IN_END_DATE                      INT,                  --到期日期
                                            @IN_HT_MONEY                      DECIMAL(16,3),        --项目合同总金额
                                            @IN_WH_MONEY                      DECIMAL(16,3),        --维护费金额
                                            @IN_MAIN_SUMMARY                  NVARCHAR(2000),       --合同说明
                                            @IN_INPUT_MAN                     INT,                  --录入人员，对应表TOPERATOR.OP_CODE
                                            @OUT_MAINTAIN_ID                  INT     OUTPUT,       --维护合同(维护费)ID
                                            @IN_COCONTRACT_SUB_BH             NVARCHAR(500) = NULL  --对应销售合同编号

WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'合同管理之新增维护合同'
    SELECT @SSUMMARY = N'合同管理之新增维护合同'
    SELECT @IBUSI_FLAG = 50001
    SELECT @V_RET_CODE = -50001000

    BEGIN TRANSACTION

    INSERT INTO TCOCONTRACTMAINTAIN(COCONTRACTMAINTAIN_SUB_BH,CUST_ID,MAIN_PERIOD,MAIN_PERIOD_UNIT,MAIN_PRO_NAME,COLLECT_TIME,START_DATE,END_DATE,HT_MONEY,WH_MONEY,MAIN_SUMMARY,INPUT_MAN,COCONTRACT_SUB_BH)
        VALUES(@IN_COCONTRACTMAINTAIN_SUB_BH,@IN_CUST_ID,@IN_MAIN_PERIOD,@IN_MAIN_PERIOD_UNIT,@IN_MAIN_PRO_NAME,@IN_COLLECT_TIME,@IN_START_DATE,@IN_END_DATE,@IN_HT_MONEY,@IN_WH_MONEY,@IN_MAIN_SUMMARY,@IN_INPUT_MAN,@IN_COCONTRACT_SUB_BH)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    --
    SET @OUT_MAINTAIN_ID = @@IDENTITY
    --
    SELECT @SSUMMARY = N'合同管理之新增维护合同，合同编号'+@IN_COCONTRACTMAINTAIN_SUB_BH
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
