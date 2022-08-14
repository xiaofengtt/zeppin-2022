USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TSUBJECTSCORERATING @IN_RATING_NO     NVARCHAR(16),    --评级编号
                                            @IN_RATING_NAME   NVARCHAR(60),    --评级名称
                                            @IN_SUBJECT_ID    INT,             --科目ID
                                            @IN_INCLUDE_TOP   INT,             --是否算头1是，2否
                                            @IN_SCORE_LOWER   INT,             --分值下限
                                            @IN_INCLUDE_END   INT,             --是否算尾1是，2否
                                            @IN_SCORE_UPPER   INT,             --分值上限
                                            @IN_SUMMARY       NVARCHAR(200),   --描述
                                            @IN_INPUT_MAN     INT              --操作员ZZ
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    SELECT @SBUSI_NAME = N'增加科目分值评级信息'
    SELECT @SSUMMARY = N'增加科目分值评级信息'
    SELECT @IBUSI_FLAG = 21002
    SELECT @V_RET_CODE = -21002000
    
    IF EXISTS(SELECT * FROM TSUBJECTSCORERATING WHERE SUBJECT_ID = @IN_SUBJECT_ID AND RATING_NO = @IN_RATING_NO)
        RETURN @V_RET_CODE - 11   --科目分值评级信息已存在

    BEGIN TRANSACTION

    INSERT INTO TSUBJECTSCORERATING(SUBJECT_ID,RATING_NO,RATING_NAME,INCLUDE_TOP,SCORE_LOWER,INCLUDE_END,SCORE_UPPER,SUMMARY,INPUT_MAN)
         VALUES(@IN_SUBJECT_ID,@IN_RATING_NO,@IN_RATING_NAME,@IN_INCLUDE_TOP,
             @IN_SCORE_LOWER,@IN_INCLUDE_END,@IN_SCORE_UPPER,@IN_SUMMARY,@IN_INPUT_MAN)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    SELECT @SSUMMARY = N'增加科目分值评级信息，评级编号：'+RTRIM(@IN_RATING_NO)+N',评级名称：'+RTRIM(@IN_RATING_NAME)
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
