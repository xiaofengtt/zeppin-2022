﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_MODI_TROLE @IN_ROLE_ID    INT,
                               @IN_ROLE_NAME  NVARCHAR(16),
                               @IN_SUMMARY    NVARCHAR(200),
                               @IN_FLAG_ACCESS_ALL  INT,
                               @IN_FLAG_ENCRYPTION  INT,
                               @IN_INPUT_MAN  INT
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'修改角色'
    SELECT @SSUMMARY = N'修改角色'
    SELECT @IBUSI_FLAG = 10702
    SELECT @V_RET_CODE = -10702000

    IF NOT EXISTS(SELECT * FROM TROLE WHERE ROLE_ID = @IN_ROLE_ID)
        RETURN @V_RET_CODE - 11   -- 角色不存在

    BEGIN TRANSACTION

    UPDATE TROLE
    SET ROLE_NAME = @IN_ROLE_NAME,
        SUMMARY = @IN_SUMMARY,
        FLAG_ACCESS_ALL = @IN_FLAG_ACCESS_ALL,
        FLAG_ENCRYPTION = @IN_FLAG_ENCRYPTION
    WHERE ROLE_ID = @IN_ROLE_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    SELECT @SSUMMARY = N'修改角色，角色编号：'+RTRIM(CONVERT(NVARCHAR(16),@IN_ROLE_ID))+N',角色名称：'+RTRIM(@IN_SUMMARY)
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
