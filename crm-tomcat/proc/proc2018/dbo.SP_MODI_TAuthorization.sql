USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TAuthorization  @IN_CA_ID             INT,            --客户授权集合ID
                                         @IN_CA_NAME           NVARCHAR(60) ,  --客户授权集合名称
                                         @IN_CA_DESCRIPTION    NVARCHAR(200) ,  --客户授权集合描述
                                         @IN_ManagerID         INT ,           --所属客户经理
                                         @IN_INPUT_MAN         INT               --录入操作员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -40501000, @IBUSI_FLAG = 40501
    SELECT @SBUSI_NAME = N'修改客户授权集合', @SSUMMARY = N'修改客户授权集合'
    BEGIN TRANSACTION
    UPDATE TAuthorization
    SET CA_NAME = @IN_CA_NAME,
        CA_DESCRIPTION = @IN_CA_DESCRIPTION,
        ManagerID = @IN_ManagerID
    WHERE CA_ID = @IN_CA_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'修改客户授权集合，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
