USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TAuthorization  @IN_CA_NAME           NVARCHAR(60) = N'',  --客户授权集合名称
                                        @IN_CA_DESCRIPTION    NVARCHAR(200) = N'',  --客户授权集合描述
                                        @IN_ManagerID         INT = 0 ,           --所属客户经理
                                        @IN_INPUT_MAN         INT = 0 ,              --录入操作员
                                        @OUT_CA_ID            INT OUTPUT      --客户授权集ID

WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200),@V_INPUT_TIME DATETIME
    SELECT @V_RET_CODE = -40501000, @IBUSI_FLAG = 40501
    SELECT @SBUSI_NAME = N'添加授权集记录', @SSUMMARY = N'添加授权集记录'
    SELECT @V_INPUT_TIME=getdate()
    IF NOT EXISTS(SELECT 1 FROM TCustManagers  WHERE ManagerID = @IN_ManagerID)
        RETURN @V_RET_CODE - 1   --客户经理信息不存在
    IF EXISTS(SELECT 1 FROM TAuthorization WHERE CA_NAME = @IN_CA_NAME)
        RETURN @V_RET_CODE - 2  --授权集名称已经存在
    BEGIN TRANSACTION
    INSERT INTO TAuthorization(CA_NAME,CA_DESCRIPTION,ManagerID,INPUT_MAN,INPUT_TIME)
    VALUES(@IN_CA_NAME,@IN_CA_DESCRIPTION,@IN_ManagerID,@IN_INPUT_MAN,@V_INPUT_TIME)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    SELECT @OUT_CA_ID = @@IDENTITY

    SET @SSUMMARY = N'添加授权集记录，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
