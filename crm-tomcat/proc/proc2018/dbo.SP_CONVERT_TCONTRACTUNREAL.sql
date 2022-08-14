USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_CONVERT_TCONTRACTUNREAL
                             @IN_PREPRODUCT_ID       INTEGER=0 ,       --预发行产品ID
                             @IN_INPUT_MAN           INT = 0           --操作员
WITH ENCRYPTION
AS
    DECLARE @V_ERROR NVARCHAR(200),@V_PRODUCT_NAME NVARCHAR(60)
    DECLARE @V_PREPRODUCT_ID INT,@V_CUST_ID INT,@V_CONTRACT_SUB_BH NVARCHAR (30),@V_PROV_FLAG INT,@V_PROV_LEVEL NVARCHAR(10),@V_QS_DATE INT,@V_RG_MONEY DECIMAL(16,3),@V_JK_TYPE NVARCHAR (10)
    DECLARE @V_JK_DATE INT,@V_BANK_ID NVARCHAR (10),@V_BANK_SUB_NAME NVARCHAR(60),@V_BANK_ACCT NVARCHAR(30),@V_BANK_ACCT_TYPE NVARCHAR(10),@V_GAIN_ACCT NVARCHAR(60),@V_SERVICE_MAN INT,@V_EXPECT_ROR_LOWER DECIMAL(5,4),@V_EXPECT_ROR_UPPER DECIMAL(5,4)
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    DECLARE @V_SERIAL_NO INT,@V_PRODUCT_ID INT,@OUT_SERIAL_NO INT,@OUT_CONTRACT_BH NVARCHAR(16)
    SELECT @V_RET_CODE = -50101000, @IBUSI_FLAG = 50101
    SELECT @SBUSI_NAME = N'预先录入的非正式合同，转成认购合同', @SSUMMARY = N'预先录入的非正式合同，转成认购合同'
    
    BEGIN TRY
    IF @IN_PREPRODUCT_ID<>0 AND EXISTS (SELECT * FROM TPREPRODUCT WHERE PREPRODUCT_ID=@IN_PREPRODUCT_ID AND ISNULL(BIND_PRODUCT_ID,0)=0)
    BEGIN
        SET @V_ERROR = N'预发行产品未绑定业务产品，请先绑定产品'
		RAISERROR(@V_ERROR,16,1)
	END
	IF @IN_PREPRODUCT_ID=0--批量转
	BEGIN
	    SELECT TOP 1 @V_PRODUCT_NAME=PREPRODUCT_NAME FROM TPREPRODUCT WHERE ISNULL(BIND_PRODUCT_ID,0)=0 AND PREPRODUCT_ID IN (SELECT DISTINCT PREPRODUCT_ID FROM TCONTRACTUNREAL WHERE STATUS=1)
	    IF @@ROWCOUNT>0
	    BEGIN
			SET @V_ERROR = N'预发行产品['+ISNULL(@V_PRODUCT_NAME,'')+']未绑定业务产品，请先绑定产品'
			RAISERROR(@V_ERROR,16,1)
		END
	END
	
	BEGIN TRANSACTION
	DECLARE CUR_2 CURSOR LOCAL FOR
        SELECT SERIAL_NO,PREPRODUCT_ID,CUST_ID,CONTRACT_SUB_BH,PROV_FLAG,PROV_LEVEL,QS_DATE,RG_MONEY,JK_TYPE,
            JK_DATE,BANK_ID,BANK_SUB_NAME,BANK_ACCT,BANK_ACCT_TYPE,GAIN_ACCT,SERVICE_MAN,EXPECT_ROR_LOWER,EXPECT_ROR_UPPER 
        FROM TCONTRACTUNREAL WHERE STATUS=1
    OPEN CUR_2
    FETCH NEXT FROM CUR_2 INTO @V_SERIAL_NO,@V_PREPRODUCT_ID,@V_CUST_ID,@V_CONTRACT_SUB_BH,@V_PROV_FLAG,@V_PROV_LEVEL,@V_QS_DATE,@V_RG_MONEY,@V_JK_TYPE,
            @V_JK_DATE,@V_BANK_ID,@V_BANK_SUB_NAME,@V_BANK_ACCT,@V_BANK_ACCT_TYPE,@V_GAIN_ACCT,@V_SERVICE_MAN,@V_EXPECT_ROR_LOWER,@V_EXPECT_ROR_UPPER
    WHILE @@FETCH_STATUS=0
    BEGIN
		SELECT @V_PRODUCT_ID=BIND_PRODUCT_ID FROM TPREPRODUCT WHERE PREPRODUCT_ID=@V_PREPRODUCT_ID
		--调用信托的新增认购过程
		EXEC INTRUST..SP_ADD_TCONTRACT_NOPRE 1,
                                   @V_CUST_ID ,
                                   0,
                                   @V_PRODUCT_ID,
                                   @V_RG_MONEY,
                                   @V_JK_TYPE,
                                   @V_BANK_ID,
                                   @V_BANK_ACCT,
                                   0,                        -- 这个参数没有用
                                   @V_SERVICE_MAN,
                                   '',
                                   @IN_INPUT_MAN,
                                   '',
                                   @V_QS_DATE,
                                   @V_JK_DATE,
                                   @V_BANK_SUB_NAME,
                                   0,
                                   '',
                                   '',
                                   @V_CONTRACT_SUB_BH,
                                   0,
                                   @OUT_SERIAL_NO OUTPUT,
                                   @OUT_CONTRACT_BH OUTPUT,
                                   1,
                                   NULL,
                                   NULL,
                                   @V_GAIN_ACCT,         --银行帐号户名
                                   0,            --费用缴款方式：1从本金扣，2另外交,0不交
                                   @V_BANK_ACCT_TYPE,         --银行账户类型(9920)
                                   NULL,         --允许从界面输入起始日期、结束日期
                                   NULL,
                                   1,            --1、兑付　2、转份额
                                   0,            --子产品ID，暂未考虑
                                   0,            --是否银信合作 1是 0 否
                                   NULL,         --合同银行
                                   NULL,         --合同银行下级支行名称
                                   0,            --销售渠道ID
                                   0,            --是否证信合作
                                   0,            --是否私募基金合作
                                   0,            --转份额比例,bonus_flag = 3有效
                                   @V_PROV_FLAG,            --1.优先，2一般，3劣后
                                   @V_PROV_LEVEL,     --收益级别（1204）
                                   NULL,         --交银:渠道类别(5500)
                                   NULL,         --渠道附加信息
                                   NULL,         --渠道合作方式(5502)
                                   2,            --是否保证金  1 是 2 否  20120215  LUOHH
                                   0,            --渠道费用
                                   0,            --合同推荐人
                                   '',           --受益账户开户行所在省
                                   '',           --受益账户开户行所在市
                                   @V_EXPECT_ROR_LOWER,         --预期收益率区间
                                   @V_EXPECT_ROR_UPPER,         --预期收益率区间
                                   '',           --合同推荐人(外部)
                                   '0',			--用款方关联标志：1是
								   '',			--信托合同预计收益率（文本对象）
								   NULL			--本合同的联系人ID
		--更新本记录为已转认购状态
		UPDATE TCONTRACTUNREAL SET STATUS=2 WHERE SERIAL_NO=@V_SERIAL_NO
		FETCH NEXT FROM CUR_2 INTO @V_SERIAL_NO,@V_PREPRODUCT_ID,@V_CUST_ID,@V_CONTRACT_SUB_BH,@V_PROV_FLAG,@V_PROV_LEVEL,@V_QS_DATE,@V_RG_MONEY,@V_JK_TYPE,
            @V_JK_DATE,@V_BANK_ID,@V_BANK_SUB_NAME,@V_BANK_ACCT,@V_BANK_ACCT_TYPE,@V_GAIN_ACCT,@V_SERVICE_MAN,@V_EXPECT_ROR_LOWER,@V_EXPECT_ROR_UPPER
    END
    CLOSE CUR_2
    DEALLOCATE CUR_2
    
    SET @SSUMMARY = N'预先录入的非正式合同，转成认购合同，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR)
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
    
    COMMIT TRANSACTION
    END TRY

    --3.异常处理
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION
        DECLARE @V_ERROR_STR NVARCHAR(1000),@V_ERROR_NUMBER INT,@V_ERROR_SEVERITY INT,@V_ERROR_STATE INT,
                @V_ERROR_PROCEDURE sysname,@V_ERROR_LINE INT,@V_ERROR_MESSAGE NVARCHAR(4000)
        SELECT @V_ERROR_STR = N'Message:%s<BR><font color = "white">Error:%d,Level:%d,State:%d,Procedure:%s,Line:%d</font>',
               @V_ERROR_NUMBER = ERROR_NUMBER(),
               @V_ERROR_SEVERITY = ERROR_SEVERITY(),
               @V_ERROR_STATE = ERROR_STATE(),
               @V_ERROR_PROCEDURE = ERROR_PROCEDURE(),
               @V_ERROR_LINE = ERROR_LINE(),
               @V_ERROR_MESSAGE = ERROR_MESSAGE()
        RAISERROR(@V_ERROR_STR,@V_ERROR_SEVERITY,1,@V_ERROR_MESSAGE,@V_ERROR_NUMBER,@V_ERROR_SEVERITY,@V_ERROR_STATE,
                  @V_ERROR_PROCEDURE,@V_ERROR_LINE)
        RETURN -100
    END CATCH
    RETURN 100
GO
