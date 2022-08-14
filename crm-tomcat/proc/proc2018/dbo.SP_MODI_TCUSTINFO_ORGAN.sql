USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE SP_MODI_TCUSTINFO_ORGAN
		    @IN_CUST_ID             INTEGER ,            --TCustomers.CUST_ID
		    @IN_BUSINESS_CODE       NVARCHAR(40),        --企业营业执照编号
		    @IN_ORGAN_CODE          NVARCHAR(40),        --组织机构代码
		    @IN_TAX_REG_CODE        NVARCHAR(40) ,       --税务登记证
		    @IN_LEGAL_NAME          NVARCHAR(40),        --法定代表人姓名
		    @IN_LEGAL_CARD_ID       NVARCHAR(40) ,       --法定代表人身份证号码
		    @IN_AGENT_NAME          NVARCHAR(40) ,       --代理人姓名
		    @IN_AGENT_CARD_ID       NVARCHAR(40) ,       --代理人身份证号码
		    @IN_PARTNER_CARD_ID     NVARCHAR(40) ,       --合伙人有效身份证明文件编码
		    @IN_INPUT_MAN           INTEGER,             --操作人
		    @IN_Managing_Scope      NVARCHAR(500)='',    --经营范围
		    @IN_LEGAL_CARD_TYPE     NVARCHAR(10)='',     --法人证件类型1108/2108
		    @IN_LEGAL_CARD_VALID_DATE INT=0,             --法人证件有效期限8位日期表示
		    @IN_LEGAL_TEL           NVARCHAR(40)='',     --法人联系电话
		    @IN_AGENT_CARD_TYPE     NVARCHAR(10)='',     --代理人证件类型1108/2108
		    @IN_AGENT_TEL           NVARCHAR(40)='',     --代理人电话
		    @IN_AGENT_CARD_VALID_DATE INT=0              --代理人证件有效期限8位日期表示
		    
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @IBUSI_FLAG = 21701
    SELECT @SBUSI_NAME = N'修改机构信息',@SSUMMARY=''

    BEGIN TRY

    BEGIN TRANSACTION
    
    --todo 屏蔽修改 法人联系电话 和  代理人电话 新建存储过程单独处理

    UPDATE TCUSTINFO_ORGAN SET
        BUSINESS_CODE   = @IN_BUSINESS_CODE       ,       --企业营业执照编号
        ORGAN_CODE      = @IN_ORGAN_CODE          ,       --组织机构代码
        TAX_REG_CODE    = @IN_TAX_REG_CODE        ,       --税务登记证
        LEGAL_NAME      = @IN_LEGAL_NAME          ,       --法定代表人姓名
        LEGAL_CARD_ID   = @IN_LEGAL_CARD_ID       ,       --法定代表人身份证号码
        AGENT_NAME      = @IN_AGENT_NAME          ,       --代理人姓名
        AGENT_CARD_ID   = @IN_AGENT_CARD_ID       ,       --代理人身份证号码
        PARTNER_CARD_ID = @IN_PARTNER_CARD_ID     ,       --合伙人有效身份证明文件编码
        Managing_Scope  = @IN_Managing_Scope      ,       --经营范围
		LEGAL_CARD_TYPE = @IN_LEGAL_CARD_TYPE     ,       --法人证件类型1108/2108
		LEGAL_CARD_VALID_DATE=@IN_LEGAL_CARD_VALID_DATE,  --法人证件有效期限8位日期表示
		--LEGAL_TEL       = @IN_LEGAL_TEL           ,       --法人联系电话
		AGENT_CARD_TYPE = @IN_AGENT_CARD_TYPE     ,       --代理人证件类型1108/2108
		--AGENT_TEL       = @IN_AGENT_TEL           ,       --代理人电话
		AGENT_CARD_VALID_DATE=@IN_AGENT_CARD_VALID_DATE   --代理人证件有效期限8位日期表示
        WHERE CUST_ID = @IN_CUST_ID
    IF @@ROWCOUNT=0
    BEGIN
        INSERT INTO TCUSTINFO_ORGAN(CUST_ID,BUSINESS_CODE,ORGAN_CODE,TAX_REG_CODE,LEGAL_NAME,LEGAL_CARD_ID,
            AGENT_NAME,AGENT_CARD_ID,PARTNER_CARD_ID,Managing_Scope,LEGAL_CARD_TYPE,LEGAL_CARD_VALID_DATE,
            LEGAL_TEL,AGENT_CARD_TYPE,AGENT_TEL,AGENT_CARD_VALID_DATE)
        VALUES(@IN_CUST_ID,@IN_BUSINESS_CODE,@IN_ORGAN_CODE,@IN_TAX_REG_CODE,@IN_LEGAL_NAME,@IN_LEGAL_CARD_ID,
            @IN_AGENT_NAME,@IN_AGENT_CARD_ID,@IN_PARTNER_CARD_ID,@IN_Managing_Scope,@IN_LEGAL_CARD_TYPE,@IN_LEGAL_CARD_VALID_DATE,
            @IN_LEGAL_TEL,@IN_AGENT_CARD_TYPE,@IN_AGENT_TEL,@IN_AGENT_CARD_VALID_DATE)
        SET @SBUSI_NAME = N'新增机构信息'
    END
    
    SELECT @SSUMMARY=@SSUMMARY+N'客户名称:'+ISNULL(CUST_NAME,'') FROM TCustomers WHERE CUST_ID=@IN_CUST_ID
    
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
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
