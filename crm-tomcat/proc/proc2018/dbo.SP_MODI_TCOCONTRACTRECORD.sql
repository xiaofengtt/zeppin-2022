USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
 CREATE PROCEDURE SP_MODI_TCOCONTRACTRECORD @IN_MAINRECORD_ID               INTEGER,          --客户维护ID，自增长
                                             @IN_CUST_ID                     INT,              --客户ID，对应表TCustomers.CUST_ID
                                             @IN_MAIN_PRO_NAME               NVARCHAR(200),    --维护项目名称
                                             @IN_MAIN_CONTENT                NVARCHAR(2000),   --维护内容
                                             @IN_TEAM_ID                     INT,              --隶属项目组，对应表TTEAM.TEAM_ID
                                             @IN_COPRODUCT_MANAGER           INT,              --项目经理，对应表TOPERATOR.OP_CODE
                                             @IN_RECORD_DATE                 INT,              --受理日期
                                             @IN_INPUT_MAN                   INT               --录入操作员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_CUST_NAME NVARCHAR(60)
    
    SELECT @SBUSI_NAME = N'合同管理之修改客户维护记录'
    SELECT @SSUMMARY = N'合同管理之修改客户维护记录'
    SELECT @IBUSI_FLAG = 50001
    SELECT @V_RET_CODE = -50001000 
    
    SELECT @V_CUST_NAME =CUST_NAME FROM TCustomers WHERE CUST_ID =@IN_CUST_ID
    
    BEGIN TRANSACTION
    
    UPDATE TCOCONTRACTRECORD
        SET CUST_ID          = @IN_CUST_ID,
            MAIN_PRO_NAME    = @IN_MAIN_PRO_NAME,
            MAIN_CONTENT     = @IN_MAIN_CONTENT,
            TEAM_ID          = @IN_TEAM_ID,
            COPRODUCT_MANAGER = @IN_COPRODUCT_MANAGER,
            RECORD_DATE      = @IN_RECORD_DATE
        WHERE MAINRECORD_ID= @IN_MAINRECORD_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    --
    SELECT @SSUMMARY = N'合同管理之修改客户维护记录，客户名称：'+@V_CUST_NAME + '，维护项目名称：'+@IN_MAIN_PRO_NAME+'，受理日期：'+CONVERT(NVARCHAR(8),@IN_RECORD_DATE)
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
