﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TCOMPLAINT  @IN_CUST_NAME     NVARCHAR(100),       --客户姓名
									@IN_INPUT_DATE    INTEGER,		       --投诉日期
									@IN_COMP_TYPE	  INTEGER,             --投诉方式:1电话；2短信；3EMAIL；4其他
									@IN_CONTENT       NVARCHAR(MAX),       --投诉内容
									@IN_INPUT_MAN     INTEGER              --操作员
WITH ENCRYPTION
AS
    SET NOCOUNT ON

    DECLARE @V_ERROR NVARCHAR(200), @IBUSI_FLAG INT, @SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    
    BEGIN TRY
    
    SET @IBUSI_FLAG = 30601
    SET @SBUSI_NAME = '新增客户投诉'
    
    --校验输入数据
    IF ISNULL(@IN_INPUT_DATE,0)=0
	BEGIN
		SET @V_ERROR = N'投诉日期不能为空！'
		RAISERROR(@V_ERROR,16,1)
	END
	IF ISNULL(@IN_CUST_NAME,'') = ''
	BEGIN
		SET @V_ERROR = N'客户姓名不能为空'
		RAISERROR(@V_ERROR,16,2)
	END
    IF ISNULL(@IN_CONTENT,'') = ''
	BEGIN
		SET @V_ERROR = N'投诉内容不能为空'
		RAISERROR(@V_ERROR,16,3)
	END
    
    BEGIN TRANSACTION
    
    --
    INSERT INTO TCOMPLAINT(CUST_NAME,INPUT_DATE,COMP_TYPE,CONTENT,INPUT_MAN)
        VALUES(@IN_CUST_NAME,@IN_INPUT_DATE,@IN_COMP_TYPE,@IN_CONTENT,@IN_INPUT_MAN)
    
    --日志记录
    SET @SSUMMARY = @SBUSI_NAME + ',新增,客户投诉:客户姓名:'+@IN_CUST_NAME
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
    COMMIT TRANSACTION
    END TRY

    --3.异常处理
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION
        DECLARE @V_ERROR_STR NVARCHAR(1000),@V_ERROR_NUMBER INT,@V_ERROR_SEVERITY INT,@V_ERROR_STATE INT,
                @V_ERROR_PROCEDURE sysname,@V_ERROR_LINE INT,@V_ERROR_MESSAGE NVARCHAR(4000)
        SELECT @V_ERROR_STR = N'Message:%s<BR><font color = "white">Error:%d,Level:%d,State:%d,Procedure:%s,Line:%d</font>',
               @V_ERROR_NUMBER = ERROR_NUMBER(),
               @V_ERROR_SEVERITY = ERROR_SEVERITY(),
               @V_ERROR_STATE = ERROR_STATE(),
               @V_ERROR_PROCEDURE = ERROR_PROCEDURE(),
               @V_ERROR_LINE = ERROR_LINE(),
               @V_ERROR_MESSAGE = ERROR_MESSAGE()
        RAISERROR(@V_ERROR_STR,@V_ERROR_SEVERITY,1,@V_ERROR_MESSAGE,@V_ERROR_NUMBER,@V_ERROR_SEVERITY,@V_ERROR_STATE,
                  @V_ERROR_PROCEDURE,@V_ERROR_LINE)
        RETURN -100
    END CATCH
    RETURN 100
GO
