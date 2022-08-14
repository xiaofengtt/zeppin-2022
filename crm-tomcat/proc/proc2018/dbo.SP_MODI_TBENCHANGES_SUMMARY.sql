﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
/**
功能：在任何时候修改备注

*/
CREATE PROCEDURE SP_MODI_TBENCHANGES_SUMMARY @IN_SERIAL_NO       INTEGER,
                                     @IN_SUMMARY         NVARCHAR(200),
                                     @IN_INPUT_MAN       INTEGER
                                     
WITH ENCRYPTION
AS
    DECLARE @V_ERROR NVARCHAR(200)
    DECLARE @V_SUMMARY VARCHAR(MAX),@V_CONTRACT_BH varchar(16)
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME VARCHAR(40),@SSUMMARY VARCHAR(200)
    
    BEGIN TRY
    SELECT @V_RET_CODE = -21702000,@IBUSI_FLAG = 21702
    SELECT @SBUSI_NAME = '修改受益权转让备注',@SSUMMARY = '修改受益权转让备注'

    IF NOT EXISTS(SELECT * FROM INTRUST..TBENCHANGES WHERE SERIAL_NO = @IN_SERIAL_NO) BEGIN
        --RETURN @V_RET_CODE-1   -- 受益人转让信息不存在
        SET @V_ERROR = '受益人转让信息不存在'
        RAISERROR(@V_ERROR,16,1)
    END
    SELECT @V_SUMMARY = SUMMARY,@V_CONTRACT_BH=CONTRACT_BH FROM INTRUST..TBENCHANGES WHERE SERIAL_NO = @IN_SERIAL_NO
    
    BEGIN TRANSACTION

    UPDATE INTRUST..TBENCHANGES
        SET SUMMARY         = @IN_SUMMARY
       WHERE SERIAL_NO = @IN_SERIAL_NO

    SELECT @SSUMMARY = '修改受益权转让，合同编号：' + @V_CONTRACT_BH
    INSERT INTO INTRUST..TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)

    COMMIT TRANSACTION
    END TRY

    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION

        DECLARE @V_ERROR_STR NVARCHAR(1000),@V_ERROR_NUMBER INT,@V_ERROR_SEVERITY INT,@V_ERROR_STATE INT,
                @V_ERROR_PROCEDURE sysname,@V_ERROR_LINE INT,@V_ERROR_MESSAGE NVARCHAR(4000)
        SELECT @V_ERROR_STR = N',Message %s,Error %d,Level %d,State %d,Procedure %s,Line %d',
               @V_ERROR_NUMBER = ERROR_NUMBER(),
               @V_ERROR_SEVERITY = ERROR_SEVERITY(),
               @V_ERROR_STATE = ERROR_STATE(),
               @V_ERROR_PROCEDURE = ERROR_PROCEDURE(),
               @V_ERROR_LINE = ERROR_LINE(),
               @V_ERROR_MESSAGE = ERROR_MESSAGE()

        RAISERROR(@V_ERROR_STR,@V_ERROR_MESSAGE,@V_ERROR_SEVERITY,1,@V_ERROR_NUMBER,@V_ERROR_SEVERITY,
                  @V_ERROR_STATE,@V_ERROR_PROCEDURE,@V_ERROR_LINE)
        RETURN -100

    END CATCH
    RETURN 100
GO
