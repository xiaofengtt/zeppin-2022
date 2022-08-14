USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TAuthorizationCusts @IN_CA_ID       INT,            --客户授权集合编号 TAuthorization.CA_ID
                                            @IN_CUST_ID     INT,            --客户编号 TCustomers.CUST_ID
                                            @IN_INPUT_MAN   INT = 0,        --操作员
                                            @IN_AUTH_FLAG   INT = 1         --授权访问方式 1可编辑 2仅查询
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200),@V_INPUT_TIME DATETIME
    SELECT @V_RET_CODE = -40501000, @IBUSI_FLAG = 40501
    SELECT @SBUSI_NAME = N'添加客户授权集合内客户', @SSUMMARY = N'添加客户授权集合内客户'
    SELECT @V_INPUT_TIME=getdate()
    IF NOT EXISTS(SELECT 1 FROM TCustomers  WHERE CUST_ID = @IN_CUST_ID)
        RETURN @V_RET_CODE - 3    --对应客户信息不存在
    IF NOT EXISTS(SELECT 1 FROM TAuthorization WHERE CA_ID = @IN_CA_ID)
        RETURN @V_RET_CODE - 4  --对应客户授权集信息不存在
    IF EXISTS(SELECT 1 FROM TAuthorizationCusts WHERE CA_ID = @IN_CA_ID AND CUST_ID = @IN_CUST_ID)
        RETURN @V_RET_CODE - 5  --客户授权集合内客户不允许重复添加

    BEGIN TRANSACTION
    INSERT INTO TAuthorizationCusts(CA_ID,CUST_ID,INPUT_MAN,INPUT_TIME,AUTH_FLAG)
    VALUES(@IN_CA_ID,@IN_CUST_ID,@IN_INPUT_MAN,@V_INPUT_TIME,@IN_AUTH_FLAG)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'添加客户授权集合内客户，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
