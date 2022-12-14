USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_PASSWORD @IN_OP_CODE INT,
                                       @IN_OLD_PASSWORD NVARCHAR(40),
                                       @IN_NEW_PASSWORD NVARCHAR(40),
                                       @OUT_RET_CODE INT OUTPUT
WITH ENCRYPTION
AS
    DECLARE @V_LOGIN_USER NVARCHAR(20),@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_ENCRYPT_PASSWORD VARBINARY(256)   --将登陆密码从明文转成密文 add by luohh 20080505
    SELECT @SBUSI_NAME = N'修改员工密码'
    SELECT @SSUMMARY = N'修改员工密码'
    SELECT @OUT_RET_CODE = 100
    SELECT @IBUSI_FLAG = 10605

    IF NOT EXISTS(SELECT * FROM TOPERATOR WHERE OP_CODE = @IN_OP_CODE)
    BEGIN
        SELECT @OUT_RET_CODE = -1   -- 员工编号不存在
        return -1
    END
    SELECT @V_ENCRYPT_PASSWORD = ENCRYPT_PASSWORD,@V_LOGIN_USER = RTRIM(ISNULL(LOGIN_USER,'')) FROM TOPERATOR WHERE OP_CODE = @IN_OP_CODE
    IF pwdcompare(@IN_OLD_PASSWORD,@V_ENCRYPT_PASSWORD)<>1   ---密文密码对比  modi by luohh 20080505
    BEGIN
         SELECT @OUT_RET_CODE = -2  -- 原密码不对
         return -2
    END

    BEGIN TRANSACTION

    UPDATE TOPERATOR
        SET ENCRYPT_PASSWORD = pwdencrypt(@IN_NEW_PASSWORD),-------密码加密 modi by luohh 20080505
            PASSWORD = @IN_NEW_PASSWORD,
            LOGOUT_TIME = getdate()
        WHERE OP_CODE = @IN_OP_CODE
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    --同时修改了其他OP_CODE的密码
    IF @V_LOGIN_USER <> ''
    BEGIN
        UPDATE TOPERATOR
        SET ENCRYPT_PASSWORD = pwdencrypt(@IN_NEW_PASSWORD),-------密码加密 modi by luohh 20080505
            PASSWORD = @IN_NEW_PASSWORD
        WHERE OP_CODE <> @IN_OP_CODE AND LOGIN_USER = @V_LOGIN_USER
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    SELECT @SSUMMARY = N'修改员工密码，员工编号：'+CONVERT(NVARCHAR(8),@IN_OP_CODE)
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_OP_CODE,@SSUMMARY)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    COMMIT TRANSACTION
    return 100
GO
