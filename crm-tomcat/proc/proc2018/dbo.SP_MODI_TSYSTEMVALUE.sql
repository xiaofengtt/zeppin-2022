USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE SP_MODI_TSYSTEMVALUE @IN_OPERAND_V_ID     INT,                      --主键
                                      @IN_OPERAND_ID       INT,                      --操作数ID(打分项)
                                      @IN_OPERAND_XH       NVARCHAR(20),             --序号
                                      @IN_SOURCE_TABLE     NVARCHAR(60),             --数据来源表
                                      @IN_SOURCE_FIELD     NVARCHAR(60),             --数据来源字段
                                      @IN_INCLUDE_TOP      INT,                      --是否算头1是，2否
                                      @IN_TOP_THRESHOLD    DECIMAL(16,3),            --头临界值
                                      @IN_INCLUDE_END      INT,                      --是否算尾1是，2否
                                      @IN_END_THRESHOLD    DECIMAL(16,3),            --尾临界值
                                      @IN_TRUE_VALUE       NVARCHAR(20),             --为真取值
                                      @IN_FALSE_VALUE      NVARCHAR(20),             --为假取值
                                      @IN_ADD_SUB_ITEMS    INT,                      --加减项
                                      @IN_WEIGHT           DECIMAL(9,6),             --权重
                                      @IN_MULTIPLE         DECIMAL(9,6),             --倍数
                                      @IN_SUMMARY          NVARCHAR(200),            --描述
                                      @IN_INPUT_MAN        INT,                       --操作员
									  @IN_CUST_TYPE		   INT
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_SOURCE_TABLE_CN NVARCHAR(60),@V_SOURCE_FIELD_CN NVARCHAR(60)
    SELECT @SBUSI_NAME = N'修改系统打分取值信息'
    SELECT @SSUMMARY = N'修改系统打分取值信息'
    SELECT @IBUSI_FLAG = 21005
    SELECT @V_RET_CODE = -21005000
    SELECT @V_SOURCE_TABLE_CN = TABLE_NAME_CN FROM TDataTABLE WHERE TABLE_NAME = @IN_SOURCE_TABLE
	SELECT @V_SOURCE_FIELD_CN = FIELD_NAME_CN FROM TDataFIELD WHERE FIELD_NAME = @IN_SOURCE_FIELD
    
	IF NOT EXISTS(SELECT * FROM TSYSTEMVALUE WHERE OPERAND_V_ID = @IN_OPERAND_V_ID)
        RETURN @V_RET_CODE - 1   --系统打分取值信息不存在
	
	IF ISNULL(@IN_END_THRESHOLD,0) =0 
		SET @IN_END_THRESHOLD = 999999999999	

    BEGIN TRANSACTION
    UPDATE TSYSTEMVALUE
        SET OPERAND_ID 		= @IN_OPERAND_ID,
			OPERAND_XH 		= @IN_OPERAND_XH,
			SOURCE_TABLE 	= @IN_SOURCE_TABLE,
            SOURCE_TABLE_CN = @V_SOURCE_TABLE_CN,
			SOURCE_FIELD 	= @IN_SOURCE_FIELD,
			SOURCE_FIELD_CN = @V_SOURCE_FIELD_CN,
			INCLUDE_TOP 	= @IN_INCLUDE_TOP,
			TOP_THRESHOLD	= @IN_TOP_THRESHOLD,
			INCLUDE_END 	= @IN_INCLUDE_END,
			END_THRESHOLD 	= @IN_END_THRESHOLD,
			TRUE_VALUE 		= @IN_TRUE_VALUE,
			FALSE_VALUE 	= @IN_FALSE_VALUE,
			ADD_SUB_ITEMS 	= @IN_ADD_SUB_ITEMS,
			WEIGHT 			= @IN_WEIGHT,
			MULTIPLE 		= @IN_MULTIPLE,
			SUMMARY 		= @IN_SUMMARY,
			INPUT_MAN 		= @IN_INPUT_MAN,
			CUST_TYPE       = @IN_CUST_TYPE
        WHERE OPERAND_V_ID = @IN_OPERAND_V_ID
		
    UPDATE TSYSTEMCONDITION
        SET OPERAND_XH = @IN_OPERAND_XH WHERE OPERAND_V_ID = @IN_OPERAND_V_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'修改系统打分取值信息:'+@IN_OPERAND_XH
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
