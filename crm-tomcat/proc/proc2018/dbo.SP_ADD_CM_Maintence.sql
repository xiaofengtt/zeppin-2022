USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE SP_ADD_CM_Maintence              @IN_CONTRACT_ID                INTEGER,                 --对应合同ID
												  @IN_START_DATE                 INTEGER,                 ---开始日期
					                              @IN_END_DATE			         INTEGER,                 ---结束日期
						                          @PLAN_DATE			         INTEGER,		  ---预计收取日期
					                              @IN_CYCLE			             INTEGER,		  ---维护周期  
                                                  @IN_CYCLE_UNIT		         NVARCHAR(10),            ---维护周期单位 日，月，年
						                          @CYCLE_UNIT_NAME               NVARCHAR(10),           ---维护周期单位名称 日，月，年
						                          @MAINTENCE_MONEY               DEC(16,3),               ---维护费用
                                                  @IN_INPUT_MAN                  INTEGER
                                           
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_MAINTENANCE_ID INT
    SELECT @SBUSI_NAME = N'合同管理-增加维护计划'
    SELECT @SSUMMARY = N'合同管理-增加维护计划'
    SELECT @IBUSI_FLAG = 70101
    
        
    SELECT @V_MAINTENANCE_ID = ISNULL(MAX(MAINTENANCE_ID),0)+ 1 FROM CM_TMAINTENCE
    
    BEGIN TRANSACTION
    
    
    INSERT INTO CM_TMAINTENCE(CONTRACT_ID,START_DATE,END_DATE,PLAN_DATE,CYCLE,CYCLE_UNIT,CYCLE_UNIT_NAME,MAINTENCE_MONEY,INPUT_MAN)
        VALUES(@IN_CONTRACT_ID,@IN_START_DATE,@IN_END_DATE,@PLAN_DATE,@IN_CYCLE,@IN_CYCLE_UNIT,@CYCLE_UNIT_NAME,@MAINTENCE_MONEY,@IN_INPUT_MAN)
            
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END    
    SELECT @SSUMMARY = N'合同管理-增加维护计划,维护计划ID: '+ RTRIM(CONVERT(NVARCHAR(16),@V_MAINTENANCE_ID)) 
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
