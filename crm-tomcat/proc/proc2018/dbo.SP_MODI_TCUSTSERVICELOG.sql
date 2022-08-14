﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TCUSTSERVICELOG  @IN_SERIAL_NO            	INT,              --序号
                                          @IN_CUST_ID              	INT,              --客户ID
                                          @IN_CUST_NO              	NVARCHAR(8) ,     --客户编号
                                          @IN_SERVICE_TIME         	DATETIME ,        --服务、维护时间
                                          @IN_SERVICE_INFO         	NVARCHAR(100),    --服务项目
                                          @IN_SERVICE_SUMMARY      	NVARCHAR(500),    --服务项目的详细描述
										  @IN_SERVICE_MAN			INT,			   --客户经理
										  @IN_EXECUTOR			 	INT,			   --执行人
										  @IN_CONTENT				NVARCHAR(MAX),	  --交流内容
										  @IN_SUBJECT				NVARCHAR(60),	  --交流主题
										  @IN_STEP_FLAG			 	NVARCHAR(10),     --工作进展标志
										  @IN_INFO_LEVEL			NVARCHAR(10),	  --信息等级
                                          @IN_INPUT_MAN            	INT               --录入操作员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
	DECLARE @V_STEP_FLAG_NAME NVARCHAR(30),@V_INFO_LEVEL_NAME NVARCHAR(30)
	DECLARE	@V_SERVICE_MAN_NAME NVARCHAR(200),@V_EXECUTOR_NAME NVARCHAR(200)
    SELECT @V_RET_CODE = -40501000, @IBUSI_FLAG = 40501
    SELECT @SBUSI_NAME = N'修改客户维护记录', @SSUMMARY = N'修改客户维护记录'
	SELECT @V_STEP_FLAG_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_STEP_FLAG
	SELECT @V_INFO_LEVEL_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_INFO_LEVEL
	SELECT @V_SERVICE_MAN_NAME = OP_NAME FROM TOPERATOR WHERE OP_CODE = @IN_SERVICE_MAN
	SELECT @V_EXECUTOR_NAME = OP_NAME FROM TOPERATOR WHERE OP_CODE = @IN_EXECUTOR
	
    BEGIN TRANSACTION
    UPDATE TCUSTSERVICELOG
    SET CUST_ID = @IN_CUST_ID,
        CUST_NO = @IN_CUST_NO,
        SERVICE_TIME = @IN_SERVICE_TIME,
	    SERVICE_INFO = @IN_SERVICE_INFO,
	    SERVICE_SUMMARY = @IN_SERVICE_SUMMARY,
		CONTENT = @IN_CONTENT,
		SUBJECT = @IN_SUBJECT,
		STEP_FLAG = @IN_STEP_FLAG,
		STEP_FLAG_NAME = @V_STEP_FLAG_NAME,
		INFO_LEVEL = @IN_INFO_LEVEL,
		INFO_LEVEL_NAME = @V_INFO_LEVEL_NAME,
		SERVICE_MAN = @IN_SERVICE_MAN,
		EXECUTOR = @IN_EXECUTOR,
		SERVICE_MAN_NAME = @V_SERVICE_MAN_NAME,
		EXECUTOR_NAME = @V_EXECUTOR_NAME
    WHERE SERIAL_NO = @IN_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'修改客户维护记录，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
