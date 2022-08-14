USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TAuthorizationCusts   @IN_CA_ID           INT,      --客户授权集合ID
                                                @IN_INPUT_MAN       INT =  0  --输入员
WITH ENCRYPTION
AS
    IF ISNULL(@IN_CA_ID,0) <> 0
        SELECT * FROM TAuthorizationCusts WHERE CA_ID = @IN_CA_ID
    ELSE
        SELECT * FROM TAuthorizationCusts
    RETURN 100
GO
