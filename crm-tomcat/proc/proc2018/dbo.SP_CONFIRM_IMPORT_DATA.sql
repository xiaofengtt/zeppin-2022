USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_CONFIRM_IMPORT_DATA @IN_INPUT_MAN INT
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_BATCH_ID INT

    BEGIN TRY
    SELECT DISTINCT @V_BATCH_ID = BATCH_ID FROM IMPORT_DATA WHERE MODULE_ID = '20199' AND STATUS = 2 AND INSERT_MAN = @IN_INPUT_MAN
    BEGIN TRANSACTION
    --1.修改客户信息
    UPDATE TCUSTOMERS 
        SET CARD_ID = B.[证件号码], MOBILE = B.[手机], O_TEL = B.[固定电话], 
            FAX = B.[传真], POST_ADDRESS= B.[联系地址], POST_CODE = B.[邮编], SERVICE_MAN = C.OP_CODE, E_MAIL = B.EMAIL
        FROM TCUSTOMERS A, IMPORT_DATA B LEFT JOIN TOPERATOR C ON B.[客户经理] = C.OP_NAME
            WHERE A.CUST_ID = B.FIELD1_VALUE AND B.MODULE_ID = '20199' AND B.STATUS = 2 AND B.INSERT_MAN = @IN_INPUT_MAN
    
    --2.修改状态
    UPDATE IMPORT_DATA SET STATUS = 3 WHERE MODULE_ID = '20199' AND STATUS = 2 AND INSERT_MAN = @IN_INPUT_MAN
    
    UPDATE IMPORT_BATCH SET STATUS = 3, SUMMARY = '已成功导入' WHERE BATCH_ID = @V_BATCH_ID
    
    COMMIT TRANSACTION
    END TRY

    BEGIN CATCH

        IF @@TRANCOUNT > 0 ROLLBACK TRANSACTION
        
        DECLARE @V_ERROR_STR NVARCHAR(1000),@V_ERROR_NUMBER INT,@V_ERROR_SEVERITY INT,
                @V_ERROR_PROCEDURE sysname,@V_ERROR_LINE INT,@V_ERROR_MESSAGE NVARCHAR(4000)

        SELECT @V_ERROR_STR = N'Message:%s,Procedure:%s,Line:%d',
               @V_ERROR_SEVERITY = ERROR_SEVERITY(),
               @V_ERROR_PROCEDURE = ERROR_PROCEDURE(),
               @V_ERROR_LINE = ERROR_LINE(),
               @V_ERROR_MESSAGE = ERROR_MESSAGE()

        RAISERROR(@V_ERROR_STR,@V_ERROR_SEVERITY,1,@V_ERROR_MESSAGE,@V_ERROR_PROCEDURE,@V_ERROR_LINE)
        
        RETURN -100
    END CATCH
    RETURN 100
GO
