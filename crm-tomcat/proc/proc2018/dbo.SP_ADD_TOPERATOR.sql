USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TOPERATOR  @IN_OP_CODE     INT,
                                   @IN_OP_NAME     NVARCHAR(20),
                                   @IN_DEPART_ID   INT,
                                   @IN_ROLE_ID     INT,
                                   @IN_SUMMARY     NVARCHAR(200),
                                   @IN_INPUT_MAN   INT,
                                   @IN_ADDRESS     NVARCHAR (60) ,
                                   @IN_EMAIL       NVARCHAR (40) ,
                                   @IN_O_TEL       NVARCHAR (40) ,
                                   @IN_MOBILE      NVARCHAR (100),
                                   @IN_LOGIN_USER  NVARCHAR(20), -- 登录用户名
                                   @IN_CARD_TYPE   NVARCHAR(10), --身份证件/证明文件类型(1108)
                                   @IN_CARD_ID     NVARCHAR(30), --身份证件/证明文件号码
                                   @IN_CARD_VALID_DATE  INT     --身份证件/证明文件有效期限
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT, @SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -10601000, @IBUSI_FLAG = 10601
    SELECT @SBUSI_NAME = N'增加员工信息', @SSUMMARY = N'增加员工信息'

    IF EXISTS(SELECT * FROM TOPERATOR WHERE OP_CODE = @IN_OP_CODE)
       RETURN @V_RET_CODE-1   -- 员工编号已存在
    IF NOT EXISTS(SELECT * FROM TDEPARTMENT WHERE DEPART_ID = @IN_DEPART_ID)
       RETURN @V_RET_CODE-2   -- 部门编号不存在
    SELECT @IN_LOGIN_USER = RTRIM(ISNULL(@IN_LOGIN_USER,''))
    IF @IN_LOGIN_USER = N''
        SELECT @IN_LOGIN_USER = RTRIM(CONVERT(CHAR(8),@IN_OP_CODE))
    IF EXISTS(SELECT * FROM TOPERATOR WHERE LOGIN_USER = @IN_LOGIN_USER)
        RETURN @V_RET_CODE-1   -- 员工编号已存在

    BEGIN TRANSACTION

    -----------将初始密码加密  modi by luohh 20080505
    INSERT INTO TOPERATOR(OP_CODE,OP_NAME,DEPART_ID,ROLE_ID,SUMMARY,PASSWORD,ENCRYPT_PASSWORD,REG_DATE,
              ADDRESS,EMAIL,O_TEL,MOBILE,LOGIN_USER,CARD_TYPE,CARD_ID,CARD_VALID_DATE)
        VALUES(@IN_OP_CODE,@IN_OP_NAME,@IN_DEPART_ID,0,@IN_SUMMARY,'000000',pwdencrypt('000000'),CONVERT(INT,CONVERT(CHAR(8),GETDATE(),112))
              ,@IN_ADDRESS,@IN_EMAIL,@IN_O_TEL,@IN_MOBILE,@IN_LOGIN_USER,@IN_CARD_TYPE,@IN_CARD_ID,@IN_CARD_VALID_DATE)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'增加员工信息，员工编号：' + RTRIM(CONVERT(NVARCHAR(12),@IN_OP_CODE)) + N'，员工姓名：' + @IN_OP_NAME
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
