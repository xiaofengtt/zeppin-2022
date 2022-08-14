﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TOPERATOR_PRODUCT_ID_CRM  	@IN_OP_CODE    INT,
													@IN_PRODUCT_ID INT
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME VARCHAR(40),@SSUMMARY VARCHAR(200)
    SELECT @V_RET_CODE = -10601000 ,@IBUSI_FLAG = 10601
    SELECT @SBUSI_NAME = '修改员工操作产品' ,@SSUMMARY = '修改员工操作产品'

    IF NOT EXISTS(SELECT 1 FROM TOPERATOR WHERE OP_CODE = @IN_OP_CODE)
        RETURN @V_RET_CODE - 4  --操作员不存在

    IF NOT EXISTS(SELECT 1 FROM INTRUST..TPRODUCT WHERE PRODUCT_ID = @IN_PRODUCT_ID)
        SET @IN_PRODUCT_ID = 0
		
    BEGIN TRANSACTION
		UPDATE TOPERATOR SET PRODUCT_ID = @IN_PRODUCT_ID WHERE OP_CODE = @IN_OP_CODE
		IF @@ERROR <> 0
		BEGIN
			ROLLBACK TRANSACTION
			RETURN -100
		END

		SELECT @SSUMMARY = '修改员工操作产品，员工编号：' + RTRIM(CONVERT(CHAR(12),@IN_OP_CODE)) + '，产品ID：' + RTRIM(CONVERT(CHAR,@IN_PRODUCT_ID))
		INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
			VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_OP_CODE,@SSUMMARY)
		IF @@ERROR <> 0
		BEGIN
			ROLLBACK TRANSACTION
			RETURN -100
		END
    COMMIT TRANSACTION
    RETURN 100
GO
