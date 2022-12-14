USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TCUSTLEVELAUTH @IN_OP_CODE        INTEGER,   --操作员
                                       @IN_PRODUCT_ID     INTEGER,   --产品ID
                                       @IN_LEVEL_VALUE_ID INTEGER,   --当前类别的分类值ID
                                       @IN_INPUT_MAN      INTEGER    --执行操作员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'添加认购份额、受益份额分级权限分配'
    SELECT @SSUMMARY = N'添加认购份额、受益份额分级权限分配'
    SELECT @IBUSI_FLAG = 10111
    SELECT @V_RET_CODE = -10111000

    DECLARE @V_BOOK_CODE INT, @V_INTRUST_OPERATOR INT
    DECLARE @RET INT, @V_DT_INTRUST INT
    SELECT @V_DT_INTRUST = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'DT_INTRUST'

    DECLARE @V_LEVEL_VALUE_NAME NVARCHAR(60)

    IF @IN_PRODUCT_ID IS NULL SET @IN_PRODUCT_ID = 0
    IF @IN_PRODUCT_ID <> 0
    BEGIN
        IF @V_DT_INTRUST = 1 --启用分布式
        BEGIN
            IF NOT EXISTS(SELECT 1 FROM SRV_Intrust.INTRUST.dbo.TPRODUCT WHERE PRODUCT_ID = @IN_PRODUCT_ID)
                RETURN -10110001 --产品不存在
        END
        ELSE IF (@V_DT_INTRUST = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'INTRUST') --本地信托数据库
        BEGIN
            IF NOT EXISTS(SELECT 1 FROM INTRUST..TPRODUCT WHERE PRODUCT_ID = @IN_PRODUCT_ID)
                RETURN -10110001 --产品不存在
        END
    END
    IF NOT EXISTS(SELECT 1 FROM TCustManagers WHERE ManagerID = @IN_OP_CODE)
        RETURN -30401001 --客户经理不存在
    IF NOT EXISTS(SELECT 1 FROM TCUSTLEVEL WHERE PRODUCT_ID = @IN_PRODUCT_ID AND LEVEL_VALUE_ID = @IN_LEVEL_VALUE_ID)
        RETURN -20702001 --记录不存在
    SELECT @V_LEVEL_VALUE_NAME = LEVEL_VALUE_NAME FROM TCUSTLEVEL WHERE PRODUCT_ID = @IN_PRODUCT_ID AND LEVEL_VALUE_ID = @IN_LEVEL_VALUE_ID

    BEGIN TRANSACTION

    IF NOT EXISTS(SELECT 1 FROM TCUSTLEVELAUTH WHERE OP_CODE = @IN_OP_CODE AND PRODUCT_ID = @IN_PRODUCT_ID AND LEVEL_VALUE_ID = @IN_LEVEL_VALUE_ID)
    BEGIN
        INSERT INTO TCUSTLEVELAUTH(OP_CODE,PRODUCT_ID,LEVEL_VALUE_ID,LEVEL_VALUE_NAME)
            VALUES(@IN_OP_CODE,@IN_PRODUCT_ID,@IN_LEVEL_VALUE_ID,@V_LEVEL_VALUE_NAME)
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    SELECT @SSUMMARY = N'添加认购份额、受益份额分级权限分配'
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
