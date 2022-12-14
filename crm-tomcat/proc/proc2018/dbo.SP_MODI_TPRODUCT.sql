USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TPRODUCT @IN_PRODUCT_ID     INT,
                                  @IN_PRODUCT_CODE   NVARCHAR(12),
                                  @IN_PRODUCT_NAME   NVARCHAR(120),
                                  @IN_RISK_LEVEL     NVARCHAR(10),
                                  @IN_INPUT_MAN      INT,
                                  @IN_CHANNEL_ID     INT = NULL,
                                  @IN_CHANNEL_FARE_RATE DEC(16,8) = NULL,
                                  @IN_PRE_START_DATE    INTEGER,       -- 信托产品表 推介起始日期
                                  @IN_PRE_END_DATE      INTEGER,       -- 信托产品表 推介终止日期
                                  @IN_PRE_CODE          NVARCHAR(60),  -- 合同前缀
                                  @IN_PRE_MAX_RATE      DECIMAL(5, 4), -- 预约比例
                                  @IN_COMMISSION_RATE   DECIMAL(9, 8)=NULL,--销售提成比例(%),目前只保存在CRM库中
                                  @IN_PRE_START_TIME    VARCHAR(5)='00:00',--预约起始时间
                                  @IN_PRE_END_TIME      VARCHAR(5)='23:59', --预约结束时间
								  @IN_SUB_PRODUCT_ID    INT = 0 ,       --子产品ID
								  @IN_SUB_PRODUCT_NAME  VARCHAR(80) = NULL,
								  @IN_TEMPLATE_ID       INT = 0 ,       --打印模板ID
								  @IN_PRE_MIN_AMOUNT    INT = 0 ,       --最小预约金额
								  @IN_UP_TO_SHOW        INT = 0         --认购提醒阀值：目前厦门信托用到:当认购额达到此值时，页面中提醒
WITH ENCRYPTION
AS
    DECLARE @V_RISK_LEVEL_NAME NVARCHAR(30),@DT_INTRUST INT,@V_ERROR NVARCHAR(80),@V_NEED_CHECK INT,@V_SALE_STATUS INT
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT, @SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -30105000, @IBUSI_FLAG = 30105
    SELECT @SBUSI_NAME = N'修改CRM产品表状态', @SSUMMARY = N'修改CRM产品表状态'
    SELECT @V_RISK_LEVEL_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_RISK_LEVEL
    SELECT @DT_INTRUST=VALUE FROM TSYSCONTROL WHERE FLAG_TYPE='DT_INTRUST'--0无信托1分布式查询和更新信托数据库2本地信托数据库
	SELECT @V_NEED_CHECK=VALUE FROM TSYSCONTROL WHERE FLAG_TYPE='PRODUCTLIMIT_CHECK'
	IF ISNULL(@V_NEED_CHECK,0)=2 SET @V_SALE_STATUS=2/*销售参数设置不需要审核*/
	ELSE SET @V_SALE_STATUS=1

    BEGIN TRY
    BEGIN TRANSACTION
    IF ISNULL(@IN_PRE_START_TIME,'')='' SET @IN_PRE_START_TIME='00:00'
    IF ISNULL(@IN_PRE_END_TIME,'')='' SET @IN_PRE_END_TIME='23:59'
    IF ISDATE(@IN_PRE_START_TIME)=0
    BEGIN
		SET @V_ERROR='非法的预约起始时间：'+@IN_PRE_START_TIME
		RAISERROR(@V_ERROR,16,1)
    END
    IF ISDATE(@IN_PRE_END_TIME)=0
    BEGIN
		SET @V_ERROR='非法的预约结束时间：'+@IN_PRE_END_TIME
		RAISERROR(@V_ERROR,16,1)
    END
	
	IF @IN_SUB_PRODUCT_ID = 0
	BEGIN
    IF EXISTS (SELECT * FROM TPRODUCT WHERE PRODUCT_ID = @IN_PRODUCT_ID)
          UPDATE TPRODUCT SET PRODUCT_CODE = @IN_PRODUCT_CODE, PRODUCT_NAME = @IN_PRODUCT_NAME,
              RISK_LEVEL = @IN_RISK_LEVEL, RISK_LEVEL_NAME = @V_RISK_LEVEL_NAME, SALE_STATUS = @V_SALE_STATUS,
              INPUT_TIME = GETDATE(),
              INPUT_MAN  = @IN_INPUT_MAN,
              CHANNEL_ID = @IN_CHANNEL_ID, CHANNEL_FARE_RATE = @IN_CHANNEL_FARE_RATE,
              COMMISSION_RATE = @IN_COMMISSION_RATE,--销售提成比例
              PRE_START_TIME = @IN_PRE_START_TIME,
              PRE_END_TIME   = @IN_PRE_END_TIME,
              PRE_MIN_AMOUNT = @IN_PRE_MIN_AMOUNT
              WHERE PRODUCT_ID = @IN_PRODUCT_ID
    ELSE
          INSERT INTO TPRODUCT(PRODUCT_ID,PRODUCT_CODE,PRODUCT_NAME, RISK_LEVEL, RISK_LEVEL_NAME,SALE_STATUS,INPUT_TIME,INPUT_MAN,
              CHANNEL_ID,CHANNEL_FARE_RATE,COMMISSION_RATE,PRE_START_TIME,PRE_END_TIME,PRE_MIN_AMOUNT)
              VALUES(@IN_PRODUCT_ID,@IN_PRODUCT_CODE,@IN_PRODUCT_NAME, @IN_RISK_LEVEL, @V_RISK_LEVEL_NAME,@V_SALE_STATUS,GETDATE(),@IN_INPUT_MAN,
              @IN_CHANNEL_ID,@IN_CHANNEL_FARE_RATE,@IN_COMMISSION_RATE,@IN_PRE_START_TIME,@IN_PRE_END_TIME,@IN_PRE_MIN_AMOUNT)
			  
		--修改信托库中 推介期产品数据
    IF @DT_INTRUST=1 --分布式查询
		UPDATE SRV_Intrust.INTRUST.dbo.TPRODUCT
			SET PRE_START_DATE = @IN_PRE_START_DATE,
				PRE_END_DATE = @IN_PRE_END_DATE,
				PRE_CODE  = @IN_PRE_CODE ,
				PRE_MAX_RATE = @IN_PRE_MAX_RATE
		WHERE PRODUCT_ID = @IN_PRODUCT_ID
    ELSE IF @DT_INTRUST=2 --本地信托数据库
	BEGIN
		UPDATE INTRUST.dbo.TPRODUCT
			SET PRE_START_DATE = @IN_PRE_START_DATE,
				PRE_END_DATE = @IN_PRE_END_DATE,
				PRE_CODE  = @IN_PRE_CODE ,
				PRE_MAX_RATE = @IN_PRE_MAX_RATE
		WHERE PRODUCT_ID = @IN_PRODUCT_ID
		UPDATE INTRUST..TPRODUCTLIMIT
			SET CONTRACT_PRT_TEMPLATE=@IN_TEMPLATE_ID,
				UP_TO_SHOW=@IN_UP_TO_SHOW
		WHERE PRODUCT_ID = @IN_PRODUCT_ID AND ISNULL(SUB_PRODUCT_ID,0)=@IN_SUB_PRODUCT_ID
	END
			  
	
	END
	
	ELSE 
	BEGIN --对子产品进行设置
		IF NOT EXISTS (SELECT * FROM TPRODUCT WHERE PRODUCT_ID = @IN_PRODUCT_ID)
			INSERT INTO TPRODUCT(PRODUCT_ID,PRODUCT_CODE,PRODUCT_NAME, RISK_LEVEL, RISK_LEVEL_NAME,SALE_STATUS,INPUT_TIME,INPUT_MAN,
              CHANNEL_ID,CHANNEL_FARE_RATE,COMMISSION_RATE,PRE_START_TIME,PRE_END_TIME,PRE_MIN_AMOUNT)
              VALUES(@IN_PRODUCT_ID,@IN_PRODUCT_CODE,@IN_PRODUCT_NAME, @IN_RISK_LEVEL, @V_RISK_LEVEL_NAME,@V_SALE_STATUS,GETDATE(),@IN_INPUT_MAN,
              @IN_CHANNEL_ID,@IN_CHANNEL_FARE_RATE,@IN_COMMISSION_RATE,@IN_PRE_START_TIME,@IN_PRE_END_TIME,@IN_PRE_MIN_AMOUNT)
		IF EXISTS (SELECT * FROM TSUBPRODUCT WHERE SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID)		
          UPDATE TSUBPRODUCT SET 
              RISK_LEVEL = @IN_RISK_LEVEL, RISK_LEVEL_NAME = @V_RISK_LEVEL_NAME, SALE_STATUS = @V_SALE_STATUS,
              INPUT_TIME = GETDATE(),
              CHANNEL_ID = @IN_CHANNEL_ID, CHANNEL_FARE_RATE = @IN_CHANNEL_FARE_RATE,
              COMMISSION_RATE = @IN_COMMISSION_RATE,--销售提成比例
              PRE_START_TIME = @IN_PRE_START_TIME,
              PRE_END_TIME   = @IN_PRE_END_TIME,
			  LIST_NAME = @IN_SUB_PRODUCT_NAME,
			  PRE_MIN_AMOUNT=@IN_PRE_MIN_AMOUNT    --最小预约金额
              WHERE SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID			  
		ELSE
          INSERT INTO TSUBPRODUCT(SUB_PRODUCT_ID,PRODUCT_ID,LIST_ID,LIST_NAME, RISK_LEVEL, RISK_LEVEL_NAME,SALE_STATUS,INPUT_TIME,
              CHANNEL_ID,CHANNEL_FARE_RATE,COMMISSION_RATE,PRE_START_TIME,PRE_END_TIME,PRE_MIN_AMOUNT)
              SELECT @IN_SUB_PRODUCT_ID,@IN_PRODUCT_ID,(SELECT LIST_ID FROM INTRUST..TSUBPRODUCT WHERE SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID),@IN_SUB_PRODUCT_NAME, @IN_RISK_LEVEL, @V_RISK_LEVEL_NAME,@V_SALE_STATUS,GETDATE(),
                  @IN_CHANNEL_ID,@IN_CHANNEL_FARE_RATE,@IN_COMMISSION_RATE,@IN_PRE_START_TIME,@IN_PRE_END_TIME,@IN_PRE_MIN_AMOUNT
			  
		--修改信托库中 推介期产品数据
    IF @DT_INTRUST=1 --分布式查询
		BEGIN
		UPDATE SRV_Intrust.INTRUST.dbo.TSUBPRODUCT
			SET PRE_START_DATE = @IN_PRE_START_DATE,
				PRE_END_DATE = @IN_PRE_END_DATE
		WHERE SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID
		
		UPDATE SRV_Intrust.INTRUST.dbo.TPRODUCT
			SET	PRE_CODE  = @IN_PRE_CODE ,
				PRE_MAX_RATE = @IN_PRE_MAX_RATE
		WHERE PRODUCT_ID = @IN_PRODUCT_ID		
		END
    ELSE IF @DT_INTRUST=2 --本地信托数据库
	BEGIN
		UPDATE INTRUST.dbo.TSUBPRODUCT
			SET PRE_START_DATE = @IN_PRE_START_DATE,
				PRE_END_DATE = @IN_PRE_END_DATE
			WHERE SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID
		UPDATE INTRUST.dbo.TPRODUCT	
			SET PRE_CODE  = @IN_PRE_CODE ,
				PRE_MAX_RATE = @IN_PRE_MAX_RATE
		WHERE PRODUCT_ID = @IN_PRODUCT_ID
		UPDATE INTRUST..TPRODUCTLIMIT
			SET CONTRACT_PRT_TEMPLATE=@IN_TEMPLATE_ID,
				UP_TO_SHOW=@IN_UP_TO_SHOW
		WHERE PRODUCT_ID = @IN_PRODUCT_ID AND ISNULL(SUB_PRODUCT_ID,0)=@IN_SUB_PRODUCT_ID
	END
		
		END
    

    SELECT @SSUMMARY = N'修改CRM产品表状态'
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)

    COMMIT TRANSACTION
    END TRY

    BEGIN CATCH
        IF @@TRANCOUNT > 0 ROLLBACK TRANSACTION

        DECLARE @V_ERROR_STR NVARCHAR(1000),@V_ERROR_NUMBER INT,@V_ERROR_SEVERITY INT,@V_ERROR_STATE INT,
                @V_ERROR_PROCEDURE sysname,@V_ERROR_LINE INT,@V_ERROR_MESSAGE NVARCHAR(4000)

        SELECT @V_ERROR_STR = N'Message:%s,Procedure:%s,Line:%d',
               @V_ERROR_NUMBER = ERROR_NUMBER(),
               @V_ERROR_SEVERITY = ERROR_SEVERITY(),
               @V_ERROR_STATE = ERROR_STATE(),
               @V_ERROR_PROCEDURE = ERROR_PROCEDURE(),
               @V_ERROR_LINE = ERROR_LINE(),
               @V_ERROR_MESSAGE = ERROR_MESSAGE()

        RAISERROR(@V_ERROR_STR,@V_ERROR_SEVERITY,1,@V_ERROR_MESSAGE,@V_ERROR_PROCEDURE,@V_ERROR_LINE)

        RETURN -100
    END CATCH
    RETURN 100
GO
