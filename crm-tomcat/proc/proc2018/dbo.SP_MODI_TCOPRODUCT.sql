USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TCOPRODUCT     @IN_COPRODUCT_ID           INT,
                                        @IN_COPRODUCT_NAME          NVARCHAR(200),          --产品名称
                                        @IN_TEAM_ID                 INT,                    --隶属项目组，对应表TTEAM.TEAM_ID
                                        @IN_COPRODUCT_MANAGER       INT,                    --项目经理，对应表TOPERATOR.OP_CODE
                                        @IN_PUBLISH_DATE            INT,                    --首次发行日期
                                        @IN_COPRODUCT_TYPE          NVARCHAR(10),           --产品类型(5001)：500101 软件, 500102 硬件
                                        @IN_COPRODUCT_TYPE_NAME     NVARCHAR(30),			--产品类型说明
                                        @IN_SELFMADE_TYPE           NVARCHAR(10),			--研发情况(5002)：500201自主研发 500202 代理
                                        @IN_SELFMADE_TYPE_NAME      NVARCHAR(30),			--研发情况说明
                                        @IN_COPRODUCT_SUMMARY       NVARCHAR(2000),         --产品功能简介
                                        @IN_COPRODUCT_PRICE         DECIMAL(16,3),          --产品建议销售价格
                                        @IN_INPUT_MAN               INT						--录入人员，对应表TOPERATOR.OP_CODE


WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'合同管理之修改产品'
    SELECT @SSUMMARY = N'合同管理之修改产品'
    SELECT @IBUSI_FLAG = 50001
    SELECT @V_RET_CODE = -50001000

    BEGIN TRANSACTION

    UPDATE TCOPRODUCT
            SET COPRODUCT_NAME=@IN_COPRODUCT_NAME,
                TEAM_ID=@IN_TEAM_ID,
                COPRODUCT_MANAGER=@IN_COPRODUCT_MANAGER,
                PUBLISH_DATE=@IN_PUBLISH_DATE,
                COPRODUCT_TYPE=@IN_COPRODUCT_TYPE,
                COPRODUCT_TYPE_NAME=@IN_COPRODUCT_TYPE_NAME,
                SELFMADE_TYPE=@IN_SELFMADE_TYPE,
                SELFMADE_TYPE_NAME=@IN_SELFMADE_TYPE_NAME,
                COPRODUCT_SUMMARY=@IN_COPRODUCT_SUMMARY,
                COPRODUCT_PRICE=@IN_COPRODUCT_PRICE,
                INPUT_MAN=@IN_INPUT_MAN
     WHERE   COPRODUCT_ID= @IN_COPRODUCT_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'合同管理之修改产品，产品名称'+@IN_COPRODUCT_NAME
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
