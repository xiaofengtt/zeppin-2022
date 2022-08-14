USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE SP_MODI_CM_CollectionPlan        @IN_COLLECTION_ID             INTEGER,                 --收款计划ID
                                                  @IN_CONTRACT_ID                INTEGER,                 --对应合同ID
                                                  @IN_COLLECTION_CONDITION       NVARCHAR(200),           --收款条件
                                                  @IN_PLAN_DATE                  INTEGER,                 --计划收款日期
                                                  @IN_COLLECTION_MONEY           DEC(16,3),               --收款金额
                                                  @IN_SUMMARY                    NVARCHAR(200),	          --备注
                                                  @IN_INPUT_MAN                  INTEGER
                                           
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_COLLECTION_ID INT
    SELECT @SBUSI_NAME = N'合同管理-修改收款计划'
    SELECT @SSUMMARY = N'合同管理-修改收款计划'
    SELECT @IBUSI_FLAG = 70101

    BEGIN TRANSACTION
    
    UPDATE CM_TCOLLECTION_PLAN
        SET CONTRACT_ID=@IN_CONTRACT_ID,COLLECTION_CONDITION=@IN_COLLECTION_CONDITION,
            PLAN_DATE=@IN_PLAN_DATE,COLLECTION_MONEY=@IN_COLLECTION_MONEY,SUMMARY=@IN_SUMMARY,INPUT_MAN=@IN_INPUT_MAN
        WHERE  COLLECTION_ID=@IN_COLLECTION_ID     
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END    
    SELECT @SSUMMARY = N'合同管理-修改收款计划,收款计划ID：'+ RTRIM(CONVERT(NVARCHAR(16),@IN_COLLECTION_ID)) 
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
