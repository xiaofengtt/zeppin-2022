USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TMENUVIEWDIM @IN_MENU_ID     NVARCHAR(30),
                                     @IN_OP_CODE     INT,
                                     @IN_VIEWSTR     NVARCHAR(800)
WITH ENCRYPTION
AS

    BEGIN TRANSACTION
    IF NOT EXISTS(SELECT 1 FROM TMENUVIEWDIM WHERE MENU_ID = @IN_MENU_ID AND OP_CODE = @IN_OP_CODE)
        INSERT INTO TMENUVIEWDIM(MENU_ID,OP_CODE,VIEWSTR) VALUES(@IN_MENU_ID,@IN_OP_CODE,@IN_VIEWSTR)
    ELSE
        UPDATE TMENUVIEWDIM
            SET VIEWSTR = @IN_VIEWSTR
            WHERE MENU_ID = @IN_MENU_ID AND OP_CODE = @IN_OP_CODE

    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    COMMIT TRANSACTION
    RETURN 100
GO
