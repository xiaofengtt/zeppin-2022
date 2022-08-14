USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_DEL_TAuthorization   @IN_CA_ID               INT,            --客户授权集合ID
                                         @IN_INPUT_MAN           INT = 0         --操作员


WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -40501000, @IBUSI_FLAG = 40501
    SELECT @SBUSI_NAME = N'删除客户授权集合', @SSUMMARY = N'删除客户授权集合'
    IF NOT EXISTS(SELECT 1 FROM  TAuthorization WHERE CA_ID = @IN_CA_ID)
        RETURN @V_RET_CODE - 4 --对应客户授权集信息不存在
    IF EXISTS(SELECT 1 FROM  TAuthorizationCusts WHERE CA_ID = @IN_CA_ID)
        RETURN @V_RET_CODE - 6 --此授权集内有客户，要删除客户后才能删除此集合
    IF EXISTS(SELECT 1 FROM  TAuthorizationShare WHERE CA_ID = @IN_CA_ID)
        RETURN @V_RET_CODE - 7 --此授权集已经共享给其他经理，要取消共享后才能删除此集合
    BEGIN TRANSACTION
    --删除授权集信息
    DELETE FROM  TAuthorization WHERE CA_ID = @IN_CA_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'删除客户授权集合，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
