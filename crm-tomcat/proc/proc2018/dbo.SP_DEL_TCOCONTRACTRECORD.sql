USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
 CREATE PROCEDURE SP_DEL_TCOCONTRACTRECORD @IN_MAINRECORD_ID                INTEGER,          --客户维护ID，自增长
                                           @IN_INPUT_MAN                   INT               --录入操作员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_CUST_NAME NVARCHAR(60)
    DECLARE @V_CUST_ID INT
    DECLARE @V_MAIN_PRO_NAME  NVARCHAR(200)    --维护项目名称
    DECLARE @V_RECORD_DATE    INT              --受理日期
    
    SELECT @SBUSI_NAME = N'合同管理之删除客户维护记录'
    SELECT @SSUMMARY = N'合同管理之删除客户维护记录'
    SELECT @IBUSI_FLAG = 50001
    SELECT @V_RET_CODE = -50001000 
    
    SELECT @V_CUST_ID = CUST_ID,@V_MAIN_PRO_NAME = MAIN_PRO_NAME,@V_RECORD_DATE =RECORD_DATE FROM TCOCONTRACTRECORD WHERE MAINRECORD_ID = @IN_MAINRECORD_ID
    SELECT @V_CUST_NAME =CUST_NAME FROM TCustomers WHERE CUST_ID =@V_CUST_ID
    
    BEGIN TRANSACTION
    
    DELETE FROM TCOCONTRACTRECORD WHERE MAINRECORD_ID = @IN_MAINRECORD_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    --
    SELECT @SSUMMARY = N'合同管理之删除客户维护记录，客户名称：'+@V_CUST_NAME + '，维护项目名称：'+@V_MAIN_PRO_NAME+'，受理日期：'+CONVERT(NVARCHAR(8),@V_RECORD_DATE)
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
