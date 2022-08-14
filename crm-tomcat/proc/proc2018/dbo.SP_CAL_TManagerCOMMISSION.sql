﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_CAL_TManagerCOMMISSION
                                     @IN_PRODUCT_ID     INT,    --产品ID
                                     @IN_SUBPRODUCT_ID  INT,    --子产品ID
                                     @IN_INPUT_MAN      INT     --操作员
WITH ENCRYPTION
AS
    --SET NOCOUNT ON
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_FACE_MONEY DEC(16,3),@V_NONPRODUCT_ID INT,@V_CUST_NAME NVARCHAR(80)
    DECLARE @V_CUST_ID INT,@V_RG_MONEY DEC(16,3),@V_START_DATE INT,@V_END_DATE INT,@V_LIST_ID INT
    DECLARE @V_COMMIS_FEE DEC(16,3),@V_COMMIS_RATE DECIMAL(5,4),@V_MANAGER_ID INT
    DECLARE @V_PRODUCT_NAME NVARCHAR(90)
    SELECT @SBUSI_NAME = N'计算销售提成'
    SELECT @SSUMMARY = N'计算销售提成'
    SELECT @IBUSI_FLAG = 39007
    SET @IN_SUBPRODUCT_ID = ISNULL(@IN_SUBPRODUCT_ID,0)
    
    BEGIN TRY
    IF ISNULL(@IN_PRODUCT_ID,0)=0
        RAISERROR('请选定产品',16,1)
    
    BEGIN TRANSACTION
    
    --计算之前，先清除原有的计算结果
	DELETE FROM TManagerCOMMISSION WHERE PRODUCT_ID=@IN_PRODUCT_ID AND SUBPRODUCT_ID=@IN_SUBPRODUCT_ID
	--认购
	DECLARE CUR1 CURSOR FOR SELECT CUST_ID,RG_MONEY,START_DATE,END_DATE FROM INTRUST..TCONTRACT WHERE PRODUCT_ID=@IN_PRODUCT_ID AND ISNULL(SUB_PRODUCT_ID,0)=@IN_SUBPRODUCT_ID AND CHECK_FLAG=2
	OPEN CUR1
	FETCH NEXT FROM CUR1 INTO @V_CUST_ID,@V_RG_MONEY,@V_START_DATE,@V_END_DATE
	WHILE @@FETCH_STATUS = 0
	BEGIN
		--计算提成数据
		EXEC SP_INNER_CAL_COMMISSION @IN_PRODUCT_ID,@IN_SUBPRODUCT_ID,@V_RG_MONEY,@V_START_DATE,@V_END_DATE,@V_COMMIS_FEE OUTPUT, @V_COMMIS_RATE OUTPUT
		SELECT @V_MANAGER_ID = SERVICE_MAN FROM INTRUST..TCUSTOMERINFO WHERE CUST_ID=@V_CUST_ID
		SELECT @V_LIST_ID = MAX(LIST_ID)+1 FROM TManagerCOMMISSION WHERE PRODUCT_ID=@IN_PRODUCT_ID AND SUBPRODUCT_ID=@IN_SUBPRODUCT_ID AND OP_CODE=@V_MANAGER_ID AND CUST_ID=@V_CUST_ID
		SET @V_LIST_ID = ISNULL(@V_LIST_ID,1)
		INSERT INTO TManagerCOMMISSION(PRODUCT_ID,SUBPRODUCT_ID,OP_CODE,CUST_ID,LIST_ID,SELLS_AMOUNT,COMMISSION_AMOUNT,COMMISSION_RATE)
			VALUES(@IN_PRODUCT_ID,@IN_SUBPRODUCT_ID,@V_MANAGER_ID,@V_CUST_ID,@V_LIST_ID,@V_RG_MONEY,@V_COMMIS_FEE,@V_COMMIS_RATE)
		FETCH NEXT FROM CUR1 INTO @V_CUST_ID,@V_RG_MONEY,@V_START_DATE,@V_END_DATE
	END
	CLOSE CUR1
	DEALLOCATE CUR1
	--申购
	DECLARE CUR1 CURSOR FOR SELECT CUST_ID,SG_MONEY,START_DATE,END_DATE FROM INTRUST..TCONTRACTSG WHERE PRODUCT_ID=@IN_PRODUCT_ID AND ISNULL(SUB_PRODUCT_ID,0)=@IN_SUBPRODUCT_ID AND CHECK_FLAG=4
	OPEN CUR1
	FETCH NEXT FROM CUR1 INTO @V_CUST_ID,@V_RG_MONEY,@V_START_DATE,@V_END_DATE
	WHILE @@FETCH_STATUS = 0
	BEGIN
		--计算提成数据
		EXEC SP_INNER_CAL_COMMISSION @IN_PRODUCT_ID,@IN_SUBPRODUCT_ID,@V_RG_MONEY,@V_START_DATE,@V_END_DATE,@V_COMMIS_FEE OUTPUT, @V_COMMIS_RATE OUTPUT
		SELECT @V_MANAGER_ID = SERVICE_MAN FROM INTRUST..TCUSTOMERINFO WHERE CUST_ID=@V_CUST_ID
		SELECT @V_LIST_ID = MAX(LIST_ID)+1 FROM TManagerCOMMISSION WHERE PRODUCT_ID=@IN_PRODUCT_ID AND SUBPRODUCT_ID=@IN_SUBPRODUCT_ID AND OP_CODE=@V_MANAGER_ID AND CUST_ID=@V_CUST_ID
		SET @V_LIST_ID = ISNULL(@V_LIST_ID,1)
		INSERT INTO TManagerCOMMISSION(PRODUCT_ID,SUBPRODUCT_ID,OP_CODE,CUST_ID,LIST_ID,SELLS_AMOUNT,COMMISSION_AMOUNT,COMMISSION_RATE)
			VALUES(@IN_PRODUCT_ID,@IN_SUBPRODUCT_ID,@V_MANAGER_ID,@V_CUST_ID,@V_LIST_ID,@V_RG_MONEY,@V_COMMIS_FEE,@V_COMMIS_RATE)
		FETCH NEXT FROM CUR1 INTO @V_CUST_ID,@V_RG_MONEY,@V_START_DATE,@V_END_DATE
	END
	CLOSE CUR1
	DEALLOCATE CUR1
	--日志
	SELECT @V_PRODUCT_NAME = PRODUCT_NAME FROM INTRUST..TPRODUCT WHERE PRODUCT_ID=@IN_PRODUCT_ID
	SET @SSUMMARY = N'计算销售提成，产品：'+ISNULL(@V_PRODUCT_NAME,'')
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
    
    COMMIT TRANSACTION
    END TRY

    BEGIN CATCH
        IF @@TRANCOUNT > 0 ROLLBACK TRANSACTION
        IF EXISTS (SELECT * FROM MASTER.dbo.syscursors where cursor_name='CUR1') --游标存在则关闭游标
        BEGIN
			CLOSE CUR1
			DEALLOCATE CUR1
        END

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
