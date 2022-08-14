USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE SP_MODI_TSYSTEMCONDITION @IN_OPERAND_V_ID     INT,                      --主键
                                          @IN_SOURCE_TABLE     NVARCHAR(60),             --数据来源表
                                          @IN_SOURCE_FIELD     NVARCHAR(60),             --数据来源字段
                                          @IN_INCLUDE_TOP      INT,                      --是否算头1是，2否
                                          @IN_TOP_THRESHOLD    DECIMAL(16,3),            --头临界值
                                          @IN_INCLUDE_END      INT,                      --是否算尾1是，2否
                                          @IN_END_THRESHOLD    DECIMAL(16,3),            --尾临界值
                                          @IN_TRUE_FALSE_VALUE INT,                      --真假值
                                          @IN_SUMMARY          NVARCHAR(200),            --描述
                                          @IN_INPUT_MAN        INT                       --操作员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_SOURCE_TABLE NVARCHAR(60),@V_SOURCE_FIELD NVARCHAR(60)
    SELECT @SBUSI_NAME = N'修改系统打分条件信息'
    SELECT @SSUMMARY = N'修改系统打分条件信息'
    SELECT @IBUSI_FLAG = 21005
    SELECT @V_RET_CODE = -21005000
    SELECT @V_SOURCE_TABLE = TABLE_NAME_CN FROM TDATASOURCE WHERE TABLE_NAME = @IN_SOURCE_TABLE
		SELECT @V_SOURCE_FIELD = FIELD_NAME_CN FROM TDATASOURCE WHERE FIELD_NAME = @IN_SOURCE_FIELD
    IF NOT EXISTS(SELECT * FROM TSYSTEMCONDITION WHERE OPERAND_V_ID = @IN_OPERAND_V_ID)
        RETURN @V_RET_CODE - 2   --系统打分条件信息不存在

    BEGIN TRANSACTION
    UPDATE TSYSTEMCONDITION
        SET SOURCE_TABLE = @V_SOURCE_TABLE,SOURCE_FIELD = @V_SOURCE_FIELD,INCLUDE_TOP = @IN_INCLUDE_TOP
            ,TOP_THRESHOLD = @IN_TOP_THRESHOLD,INCLUDE_END = @IN_INCLUDE_END,END_THRESHOLD = @IN_END_THRESHOLD
            ,TRUE_FALSE_VALUE = @IN_TRUE_FALSE_VALUE,SUMMARY = @IN_SUMMARY,INPUT_MAN = @IN_INPUT_MAN
        WHERE OPERAND_V_ID = @IN_OPERAND_V_ID
        
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'修改系统打分条件信息:'+RTRIM(CONVERT(NVARCHAR(16),@IN_OPERAND_V_ID))
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
