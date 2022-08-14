USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE SP_ADD_CM_CollectionPlan         @IN_CONTRACT_ID                INTEGER,                 --对应合同ID
                                                  @IN_COLLECTION_CONDITION       NVARCHAR(200),           --收款条件
                                                  @IN_PLAN_DATE                  INTEGER,                 --计划收款日期
                                                  @IN_COLLECTION_MONEY           DEC(16,3),               --收款金额
                                                  @IN_SUMMARY                    NVARCHAR(200),	          --备注
                                                  @IN_INPUT_MAN                  INTEGER
                                           
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_COLLECTION_ID INT
    SELECT @SBUSI_NAME = N'合同管理-增加收款计划'
    SELECT @SSUMMARY = N'合同管理-增加收款计划'
    SELECT @IBUSI_FLAG = 70101
    
        
    SELECT @V_COLLECTION_ID = ISNULL(MAX(COLLECTION_ID),0)+ 1 FROM CM_TCOLLECTION_PLAN
    
    BEGIN TRANSACTION
    
    
    INSERT INTO CM_TCOLLECTION_PLAN(CONTRACT_ID,COLLECTION_CONDITION,PLAN_DATE,COLLECTION_MONEY,SUMMARY,INPUT_MAN)
        VALUES(@IN_CONTRACT_ID,@IN_COLLECTION_CONDITION,@IN_PLAN_DATE,@IN_COLLECTION_MONEY,@IN_SUMMARY,@IN_INPUT_MAN)
            
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END    
    SELECT @SSUMMARY = N'合同管理-增加收款计划,收款计划ID：'+ RTRIM(CONVERT(NVARCHAR(16),@V_COLLECTION_ID))
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
