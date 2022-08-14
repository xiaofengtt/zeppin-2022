USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP2_CHECK_LOGIN
                      @IN_LOGIN_USER          NVARCHAR(80),        --
                      @IN_PASSWORD            NVARCHAR(60),        --
                      @IN_IP_ADDRESS          NVARCHAR(60)
                      
WITH ENCRYPTION
AS
    DECLARE @V_OP_CODE INT
    SELECT @V_OP_CODE=OP_CODE FROM TOPERATOR WHERE LOGIN_USER=@IN_LOGIN_USER
    declare @v_ret int
	exec SP_LOGIN @V_OP_CODE,@IN_PASSWORD,@IN_IP_ADDRESS,@v_ret output
	select @v_ret LOGIN_STATUS
    
GO
