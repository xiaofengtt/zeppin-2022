USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TPRECONTRACT2 @IN_SERIAL_NO INT,
                      @IN_PRE_MONEY                 DECIMAL (16, 3),
                      @IN_LINK_MAN                  INT,
                      @IN_VALID_DAYS                INT,
                      @IN_PRE_TYPE                  VARCHAR(10),
                      @IN_SUMMARY                   VARCHAR(200),
                      @IN_PRE_NUM                   INT,
                      @IN_INPUT_MAN                 INT,
                      @IN_PRE_DATE                  INT = NULL,
                      @IN_EXP_REG_DATE              INT=NULL,           --预计认购日期
                      @IN_CUST_SOURCE               NVARCHAR(10)=NULL,  --客户来源
                      @IN_PRE_CODE                  NVARCHAR(16)=NULL,  --预约编号
                      @IN_MONEY_STATUS              INT = 2            --缴款状态 1已缴款 2未缴款
WITH ENCRYPTION
AS
    DECLARE @V_PRE_CODE VARCHAR(16),@V_PRE_MAX_NUM INT,@V_PRE_NUM INT,@V_FACT_PRE_NUM INT
    DECLARE @V_PRODUCT_ID INT, @V_SUB_PRODUCT_ID INT, @V_BIND_SERIAL_NO INT, @V_PREPRODUCT_ID INT
    DECLARE @V_END_DATE INT,@V_NUM INT
    DECLARE @V_STATUS VARCHAR(10),@V_PRE_TYPE_NAME VARCHAR(30), @V_PREPRODUCT_NAME NVARCHAR(60)
    DECLARE @V_PRE_MONEY DECIMAL(16,3),@V_FACT_PRE_MONEY DECIMAL(16,3),@V_PRE_MAX_MONEY DECIMAL(16,3)
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME VARCHAR(40),@SSUMMARY VARCHAR(200),@V_CUST_SOURCE_NAME NVARCHAR(30)
    DECLARE @V_NEED_CHECK INT,@V_USER_ID INT
    
    SELECT @V_RET_CODE = -20702000
    SELECT @IBUSI_FLAG = 20702
    SELECT @SBUSI_NAME = '修改客户预约销售'
    SELECT @SSUMMARY = '修改客户预约销售'
    SELECT @V_USER_ID=USER_ID FROM TSYSTEMINFO
	
    IF NOT EXISTS (SELECT * FROM TPRECONTRACT WHERE SERIAL_NO = @IN_SERIAL_NO)
       RETURN @V_RET_CODE -1    -- 记录不存在          
    IF EXISTS (SELECT * FROM TPRECONTRACT WHERE SERIAL_NO = @IN_SERIAL_NO AND CHECK_FLAG=1)
       RETURN @V_RET_CODE -5    -- 记录已审核，不能修改
           
    IF @IN_PRE_DATE IS NULL OR @IN_PRE_DATE = 0
        SELECT @IN_PRE_DATE = CONVERT(INT,CONVERT(CHAR(8),GETDATE(),112))
    --IF @IN_VALID_DAYS <= 0
    --   SELECT @IN_VALID_DAYS = 1
    --SELECT @V_END_DATE  = dbo.GETDAY(@IN_PRE_DATE,@IN_VALID_DAYS - 1)

    SELECT @V_PRODUCT_ID = PRODUCT_ID, @V_SUB_PRODUCT_ID=SUB_PRODUCT_ID
        , @V_BIND_SERIAL_NO=BIND_SERIAL_NO, @V_PREPRODUCT_ID=PREPRODUCT_ID
        ,@V_STATUS = PRE_STATUS,@V_PRE_MONEY = PRE_MONEY,@V_PRE_CODE = PRE_CODE,@V_PRE_NUM = PRE_NUM
      FROM TPRECONTRACT 
      WHERE SERIAL_NO = @IN_SERIAL_NO
      
    -- 判断有没有输入预约编号，如果没有则还原为原来的预约编号，如果有输入则预约编号为用户输入的
    IF ISNULL(@IN_PRE_CODE,'') = ''
        SELECT @IN_PRE_CODE = @V_PRE_CODE
    ELSE
      BEGIN
        SELECT @V_NUM = CONVERT(INT,@IN_PRE_CODE)
          
        IF @V_NUM < 10
            SELECT @IN_PRE_CODE = '00'+CONVERT(VARCHAR(8),@V_NUM)
        ELSE IF @V_NUM < 100
            SELECT @IN_PRE_CODE = '0'+CONVERT(VARCHAR(8),@V_NUM)
        ELSE
            SELECT @IN_PRE_CODE = CONVERT(VARCHAR(8),@V_NUM)
     END
     
    -- 如果用户输入的预约编号存在则提示已存在
    IF @V_PRODUCT_ID>0
      BEGIN
        IF EXISTS 
            (SELECT *
              FROM TPRECONTRACT 
              WHERE PRE_CODE=@IN_PRE_CODE AND PRODUCT_ID = @V_PRODUCT_ID
                AND SERIAL_NO != @IN_SERIAL_NO)
           OR EXISTS 
            (SELECT * 
              FROM INTRUST..TPRECONTRACT 
              WHERE PRE_CODE=@IN_PRE_CODE AND PRODUCT_ID = @V_PRODUCT_ID
                AND (ISNULL(@V_BIND_SERIAL_NO,0)=0 -- 尚未同步到信托数据库
                   OR SERIAL_NO != @V_BIND_SERIAL_NO))                   
            RETURN -20801012 --预约编号已存在
      END
    ELSE -- @V_PREPRODUCT_ID>0
      BEGIN
        IF EXISTS 
            (SELECT * 
              FROM TPRECONTRACT 
              WHERE PRE_CODE=@IN_PRE_CODE AND PREPRODUCT_ID = @V_PREPRODUCT_ID
                AND SERIAL_NO != @IN_SERIAL_NO)
            RETURN -20801012 --预约编号已存在
      END

    --判断如果有效期不填为0的话,end_date也为0,预约将一直有效. modi by wangc 20080321
    IF @IN_VALID_DAYS <= 0
        SELECT @V_END_DATE = 0
    ELSE
        SELECT @V_END_DATE = dbo.GETDAY(@IN_PRE_DATE,@IN_VALID_DAYS - 1)
    SELECT @V_PRE_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_PRE_TYPE    
    IF @V_STATUS = '111304'
        RETURN @V_RET_CODE - 2 --预约已作废
        
    IF @V_PRODUCT_ID>0 AND ISNULL(@V_SUB_PRODUCT_ID,0)=0
      BEGIN
        SELECT @V_PRE_MAX_NUM = PRE_MAX_NUM,@V_FACT_PRE_NUM = ISNULL(FACT_PRE_NUM,0),
            @V_FACT_PRE_MONEY = FACT_PRE_MONEY,@V_PRE_MAX_MONEY = PRE_MAX_MONEY
          FROM  INTRUST..TPRODUCT WHERE PRODUCT_ID = @V_PRODUCT_ID
        IF @V_PRE_MAX_NUM>0 AND @V_FACT_PRE_NUM -@V_PRE_NUM +@IN_PRE_NUM > @V_PRE_MAX_NUM
           RETURN @V_RET_CODE - 3   -- 预约份数已超过总份数限制
        IF @V_PRE_MAX_MONEY>0.0 AND @V_FACT_PRE_MONEY -@V_PRE_MONEY +@IN_PRE_MONEY > @V_PRE_MAX_MONEY
           RETURN @V_RET_CODE - 4  -- 预约金额已满不能修改预约金额
     END
			              
    SELECT @V_CUST_SOURCE_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE=@IN_CUST_SOURCE
    
    BEGIN TRANSACTION

    UPDATE TPRECONTRACT
        SET PRE_MONEY = @IN_PRE_MONEY,
            LINK_MAN = @IN_LINK_MAN,
            VALID_DAYS = @IN_VALID_DAYS,
            PRE_DATE = @IN_PRE_DATE,
            END_DATE = @V_END_DATE,
            PRE_TYPE = @IN_PRE_TYPE,
            PRE_TYPE_NAME = @V_PRE_TYPE_NAME,
            SUMMARY = @IN_SUMMARY,
            PRE_NUM = @IN_PRE_NUM,
            --INPUT_TIME = GETDATE(),
            EXP_REG_DATE=@IN_EXP_REG_DATE,
            CUST_SOURCE=@IN_CUST_SOURCE,
            CUST_SOURCE_NAME=@V_CUST_SOURCE_NAME,
            PRE_CODE = @IN_PRE_CODE--,
            --MONEY_STATUS = @IN_MONEY_STATUS
        WHERE SERIAL_NO = @IN_SERIAL_NO
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    --预约是否要求审核 1是
    SELECT @V_NEED_CHECK=ISNULL(MAX(VALUE),0) FROM TSYSCONTROL WHERE FLAG_TYPE='PRECONTRACT_CHECK'
    
    IF @V_NEED_CHECK<>1 AND @V_PRODUCT_ID>0 AND @V_USER_ID<>15/*建信要求不同步给业务系统*/ --不要求审核，同步给信托库
    BEGIN
        UPDATE INTRUST..TPRECONTRACT
            SET PRE_MONEY = @IN_PRE_MONEY,
                LINK_MAN = @IN_LINK_MAN,
                VALID_DAYS = @IN_VALID_DAYS,
                PRE_DATE = @IN_PRE_DATE,
                END_DATE = @V_END_DATE,
                PRE_TYPE = @IN_PRE_TYPE,
                PRE_TYPE_NAME = @V_PRE_TYPE_NAME,
                SUMMARY = @IN_SUMMARY,
                PRE_NUM = @IN_PRE_NUM,
                --INPUT_TIME = GETDATE(),
                EXP_REG_DATE=@IN_EXP_REG_DATE,
                CUST_SOURCE=@IN_CUST_SOURCE,
                CUST_SOURCE_NAME=@V_CUST_SOURCE_NAME,
                PRE_CODE = @IN_PRE_CODE--,
                --MONEY_STATUS = @IN_MONEY_STATUS
            WHERE SERIAL_NO = @V_BIND_SERIAL_NO
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END      
        
       IF @V_SUB_PRODUCT_ID>0
        BEGIN
            UPDATE INTRUST..TSUBPRODUCT 
              SET PRE_NUM = PRE_NUM-@V_PRE_NUM+@IN_PRE_NUM
                , PRE_MONEY = PRE_MONEY -@V_PRE_MONEY +@IN_PRE_MONEY
              WHERE SUB_PRODUCT_ID = @V_SUB_PRODUCT_ID	 
            IF @@ERROR <> 0
              BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
              END  
        END   
       ELSE
        BEGIN
           UPDATE INTRUST..TPRODUCT
              SET FACT_PRE_NUM = FACT_PRE_NUM-@V_PRE_NUM+@IN_PRE_NUM
                , FACT_PRE_MONEY = FACT_PRE_MONEY -@V_PRE_MONEY +@IN_PRE_MONEY
              WHERE PRODUCT_ID = @V_PRODUCT_ID
            IF @@ERROR <> 0
              BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
              END   
        END 
    END           
            
   IF @V_PREPRODUCT_ID>0 
   BEGIN
        UPDATE TPREPRODUCT 
          SET PRE_FACT_MONEY = PRE_FACT_MONEY - @V_PRE_MONEY + @IN_PRE_MONEY
          WHERE PREPRODUCT_ID = @V_PREPRODUCT_ID
        IF @@ERROR <> 0
          BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
          END   
    END
      
	IF @V_PRODUCT_ID>0
      BEGIN
        SELECT @V_PREPRODUCT_NAME=PRODUCT_NAME FROM INTRUST..TPRODUCT WHERE PRODUCT_ID = @V_PRODUCT_ID
		SELECT @V_PREPRODUCT_NAME = @V_PREPRODUCT_NAME+'['+LIST_NAME+']'
		  FROM INTRUST..TSUBPRODUCT 
		  WHERE @V_SUB_PRODUCT_ID>0 AND SUB_PRODUCT_ID=@V_SUB_PRODUCT_ID
      END
	ELSE
        SELECT @V_PREPRODUCT_NAME = PREPRODUCT_NAME FROM TPREPRODUCT WHERE PREPRODUCT_ID = @V_PREPRODUCT_ID	   
             
    SELECT @SSUMMARY = '修改客户预约销售，产品名称：' + @V_PREPRODUCT_NAME+',预约号：'+@V_PRE_CODE
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
