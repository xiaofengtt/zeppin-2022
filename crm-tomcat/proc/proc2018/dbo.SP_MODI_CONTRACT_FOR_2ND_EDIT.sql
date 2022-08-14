﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_CONTRACT_FOR_2ND_EDIT @IN_CONTRACT_TYPE INT,       -- 1:认购合同；2:申购合同；3:全部
                                               @IN_SERIAL_NO     INT,         
                                               @IN_PROV_FLAG     INT,                -- 受益优先级
                                               @IN_PROV_LEVEL    NVARCHAR(10),       -- 收益级别 
                                               @IN_CHANNEL_COOPERTYPE  NVARCHAR(10), -- 渠道合作方式(5502)           
                                               @IN_WITH_BANK_FLAG INT = 0,        -- 银信合作
                                               @IN_HT_BANK_ID NVARCHAR(10) = NULL,   -- 合作银行
                                               @IN_HT_BANK_SUB_NAME NVARCHAR(60) = '',
                                               @IN_WITH_SECURITY_FLAG INT = 0,    -- 证信合作
                                               @IN_WITH_PRIVATE_FALG INT = 0,     -- 私募基金合作
                                               @IN_CITY_SERIAL_NO INT = NULL,        -- 推介地编号
                                               @IN_JG_WTRLX2      NVARCHAR(2),       -- 机构类型分类2：1金融性公司 2政府 3非金融性公司 4境外金融性公司
                                               @IN_VALID_PERIOD   INT,               -- 合同期限 
											   @IN_PERIOD_UNIT	  INT,				 -- 合同期限单位（年、月、日）
                                               @IN_INPUT_MAN      INT
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_CUST_ID INT,@V_CHANNEL_COOPERTYPE_NAME NVARCHAR(40)
    DECLARE @V_PROV_LEVEL_NAME NVARCHAR(30),@V_PRODUCT_ID INT,@V_CONTRACT_BH NVARCHAR(60),@V_ZY_FLAG INT
    SELECT @IBUSI_FLAG = 99905
    SELECT @SBUSI_NAME = N'认申购合同二次维护'
    SELECT @SSUMMARY = N'认申购合同二次维护'

	BEGIN TRY
	SELECT @IN_WITH_BANK_FLAG = 0 WHERE @IN_WITH_BANK_FLAG IS NULL
	SELECT @IN_WITH_SECURITY_FLAG = 0 WHERE @IN_WITH_SECURITY_FLAG IS NULL
	SELECT @IN_WITH_PRIVATE_FALG = 0 WHERE @IN_WITH_PRIVATE_FALG IS NULL
	
    SELECT @V_CHANNEL_COOPERTYPE_NAME=TYPE_CONTENT  FROM INTRUST..TDICTPARAM WHERE TYPE_ID=5502 AND TYPE_VALUE=@IN_CHANNEL_COOPERTYPE
    BEGIN TRANSACTION
    --更新认购合同表
    IF @IN_CONTRACT_TYPE=1 --1:认购合同
    BEGIN
        SELECT @V_PROV_LEVEL_NAME=TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_ID='1204' AND TYPE_VALUE=@IN_PROV_LEVEL
        SELECT @V_CUST_ID=CUST_ID,@V_PRODUCT_ID=PRODUCT_ID,@V_CONTRACT_BH=CONTRACT_BH,@V_ZY_FLAG=ZY_FLAG FROM INTRUST..TCONTRACT WHERE SERIAL_NO=@IN_SERIAL_NO
        UPDATE INTRUST..TCONTRACT
            SET PROV_FLAG=@IN_PROV_FLAG, 
                PROV_LEVEL=@IN_PROV_LEVEL,
                PROV_LEVEL_NAME=@V_PROV_LEVEL_NAME,
                VALID_PERIOD=@IN_VALID_PERIOD,
				PERIOD_UNIT=@IN_PERIOD_UNIT,	
                CHANNEL_COOPERTYPE=@IN_CHANNEL_COOPERTYPE,
                CHANNEL_COOPERTYPE_NAME=@V_CHANNEL_COOPERTYPE_NAME, 
                CITY_SERIAL_NO=@IN_CITY_SERIAL_NO,
                WITH_BANK_FLAG=@IN_WITH_BANK_FLAG, 
                HT_BANK_ID=@IN_HT_BANK_ID, 
                HT_BANK_SUB_NAME=@IN_HT_BANK_SUB_NAME,
                WITH_SECURITY_FLAG=@IN_WITH_SECURITY_FLAG, 
                WITH_PRIVATE_FLAG=@IN_WITH_PRIVATE_FALG
            WHERE SERIAL_NO=@IN_SERIAL_NO
        IF @V_ZY_FLAG=1--同时更新受益人表中的收益级别
        BEGIN
	        UPDATE INTRUST..TBENIFITOR SET PROV_LEVEL=@IN_PROV_LEVEL,PROV_LEVEL_NAME=@V_PROV_LEVEL_NAME
				WHERE PRODUCT_ID=@V_PRODUCT_ID AND CONTRACT_BH=@V_CONTRACT_BH AND CUST_ID=@V_CUST_ID
		END
	END
    ELSE-- 1:认购合同；2:申购合同；
    BEGIN--更新申购合同表
        SELECT @V_PROV_LEVEL_NAME=TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_ID='1204' AND TYPE_VALUE=@IN_PROV_LEVEL
        SELECT @V_CUST_ID=CUST_ID,@V_PRODUCT_ID=PRODUCT_ID,@V_CONTRACT_BH=CONTRACT_BH FROM INTRUST..TCONTRACTSG WHERE SERIAL_NO=@IN_SERIAL_NO
        UPDATE INTRUST..TCONTRACTSG
            SET PROV_FLAG=@IN_PROV_FLAG, 
                PROV_LEVEL=@IN_PROV_LEVEL,
                PROV_LEVEL_NAME=@V_PROV_LEVEL_NAME,
                VALID_PERIOD=@IN_VALID_PERIOD,
				PERIOD_UNIT=@IN_PERIOD_UNIT,
                CHANNEL_COOPERTYPE=@IN_CHANNEL_COOPERTYPE,
                CHANNEL_COOPERTYPE_NAME=@V_CHANNEL_COOPERTYPE_NAME,  
                CITY_SERIAL_NO=@IN_CITY_SERIAL_NO,
                WITH_BANK_FLAG=@IN_WITH_BANK_FLAG, 
                HT_BANK_ID=@IN_HT_BANK_ID, 
                HT_BANK_SUB_NAME=@IN_HT_BANK_SUB_NAME,
                WITH_SECURITY_FLAG=@IN_WITH_SECURITY_FLAG, 
                WITH_PRIVATE_FLAG=@IN_WITH_PRIVATE_FALG
            WHERE SERIAL_NO=@IN_SERIAL_NO
   END
    --更新客户信息表
    UPDATE INTRUST..TCUSTOMERINFO
        SET JG_WTRLX2=@IN_JG_WTRLX2
        WHERE CUST_ID=@V_CUST_ID AND CUST_TYPE=2       
    --保存操作日志
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