USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TOPERATOR @IN_OP_CODE     INT,
                                   @IN_OP_NAME     NVARCHAR(20),
                                   @IN_DEPART_ID   INT,
                                   @IN_ROLE_ID     INT,
                                   @IN_SUMMARY     NVARCHAR(200),
                                   @IN_INPUT_MAN   INT,
                                   @IN_ADDRESS     NVARCHAR (60) ,
                                   @IN_EMAIL       NVARCHAR (40) ,
                                   @IN_O_TEL       NVARCHAR (40) ,
                                   @IN_MOBILE      NVARCHAR (100),
                                   @IN_INIT_FLAG   INT = 0,  -- 1 初始化密码
                                   @IN_LOGIN_USER  NVARCHAR(20),  -- 登录用户名
                                   @IN_CARD_TYPE   NVARCHAR(10), --身份证件/证明文件类型(1108)
                                   @IN_CARD_ID     NVARCHAR(30), --身份证件/证明文件号码
                                   @IN_CARD_VALID_DATE  INT     --身份证件/证明文件有效期限
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT, @SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -10602000, @IBUSI_FLAG = 10602
    SELECT @SBUSI_NAME = N'修改员工信息', @SSUMMARY = N'修改员工信息'

    IF NOT EXISTS(SELECT * FROM TOPERATOR WHERE OP_CODE = @IN_OP_CODE)
       RETURN -10602001   -- 员工不存在
    IF NOT EXISTS(SELECT * FROM TDEPARTMENT WHERE DEPART_ID = @IN_DEPART_ID)
       RETURN -10601002   -- 部门编号不存在
    SELECT @IN_LOGIN_USER = RTRIM(ISNULL(@IN_LOGIN_USER,''))
    IF @IN_LOGIN_USER = N''
        SELECT @IN_LOGIN_USER = RTRIM(CONVERT(CHAR(8),@IN_OP_CODE))
    IF EXISTS(SELECT * FROM TOPERATOR WHERE LOGIN_USER = @IN_LOGIN_USER AND OP_CODE <> @IN_OP_CODE)
        RETURN -10601001   -- 员工编号已存在

    BEGIN TRANSACTION

    UPDATE TOPERATOR
        SET OP_NAME=@IN_OP_NAME,
            DEPART_ID = @IN_DEPART_ID,
            ROLE_ID = @IN_ROLE_ID,
            SUMMARY = @IN_SUMMARY,
            ADDRESS = @IN_ADDRESS,
            EMAIL = @IN_EMAIL,
            O_TEL  = @IN_O_TEL,
            MOBILE = @IN_MOBILE,
            LOGIN_USER = @IN_LOGIN_USER,
            CARD_TYPE       = @IN_CARD_TYPE,
            CARD_ID         = @IN_CARD_ID,
            CARD_VALID_DATE = @IN_CARD_VALID_DATE
        WHERE OP_CODE = @IN_OP_CODE

    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    IF @IN_INIT_FLAG = 1
    BEGIN
    ----------------------初始密码加密   modi by luohh  20080505
    UPDATE TOPERATOR SET PASSWORD = N'000000' ,ENCRYPT_PASSWORD =pwdencrypt('000000') WHERE OP_CODE = @IN_OP_CODE
    IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END
    END

    SELECT @SSUMMARY = N'修改员工信息，员工编号：' + RTRIM(CONVERT(NVARCHAR(12),@IN_OP_CODE)) + N'，员工姓名：' + @IN_OP_NAME
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
