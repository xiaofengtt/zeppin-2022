USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE SP_MODI_CM_Maintence             @IN_MAINTENANCE_ID             INTEGER,		  --维护计划ID
                                                  @IN_CONTRACT_ID                INTEGER,         --对应合同ID
                                                  @IN_START_DATE                 INTEGER,         ---开始日期
                                                  @IN_END_DATE			         INTEGER,         ---结束日期
                                                  @PLAN_DATE			         INTEGER,		  ---预计收取日期
                                                  @IN_CYCLE			             INTEGER,		  ---维护周期  
                                                  @IN_CYCLE_UNIT		         NVARCHAR(10),    ---维护周期单位 日，月，年
                                                  @CYCLE_UNIT_NAME               NVARCHAR(10),    ---维护周期单位名称 日，月，年
                                                  @MAINTENCE_MONEY               DEC(16,3),       ---维护费用
                                                  @IN_INPUT_MAN                  INTEGER
                                           
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'合同管理-修改维护计划'
    SELECT @SSUMMARY = N'合同管理-修改维护计划'
    SELECT @IBUSI_FLAG = 70101

    
    BEGIN TRANSACTION
    
    
    UPDATE  CM_TMAINTENCE 
    SET     CONTRACT_ID=@IN_CONTRACT_ID,START_DATE=@IN_START_DATE,END_DATE=@IN_END_DATE,
	    PLAN_DATE=@PLAN_DATE,CYCLE=@IN_CYCLE,CYCLE_UNIT=@IN_CYCLE_UNIT,CYCLE_UNIT_NAME=@CYCLE_UNIT_NAME,
	    MAINTENCE_MONEY=@MAINTENCE_MONEY,INPUT_MAN=@IN_INPUT_MAN
    WHERE MAINTENANCE_ID=@IN_MAINTENANCE_ID
            
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END    
    SELECT @SSUMMARY = N'合同管理-修改维护计划,维护计划ID: '+ RTRIM(CONVERT(NVARCHAR(16),@IN_MAINTENANCE_ID))
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
