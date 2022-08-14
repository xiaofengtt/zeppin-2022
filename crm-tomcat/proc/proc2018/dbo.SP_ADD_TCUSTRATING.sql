﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TCUSTRATING @IN_SUBJECT_ID   INT,      --科目
                                    @IN_OPERAND_ID   INT,      --操作数
                                    @IN_CUST_ID      INT,      --客户
                                    @IN_RATING_DATE  INT,      --打分日期
                                    @IN_SCORE        INT,      --分值
                                    @IN_INPUT_MAN    INT       --打分人
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_END_DATE INT,@V_SCORE INT,@V_SUM_SCORE INT,@V_CURRENT_SOURCE INT
    DECLARE @V_CUST_SCORE INT,@V_RATING_NO NVARCHAR(16),@V_RATING_NAME NVARCHAR(100)
    SELECT @SBUSI_NAME = N'增加客户评级打分信息'
    SELECT @SSUMMARY = N'增加客户评级打分信息'
    SELECT @IBUSI_FLAG = 21012
    SELECT @V_RET_CODE = -21012000

--客户得分明细表
    IF EXISTS(SELECT * FROM TCUSTSCOREDETAIL
        WHERE OPERAND_ID = @IN_OPERAND_ID AND SUBJECT_ID = @IN_SUBJECT_ID AND CUST_ID = @IN_CUST_ID)
    BEGIN
        SELECT @V_END_DATE = MAX(END_DATE),@V_SCORE = MAX(CUST_SOURCE)
            FROM TCUSTSCOREDETAIL
            WHERE OPERAND_ID = @IN_OPERAND_ID AND SUBJECT_ID = @IN_SUBJECT_ID AND CUST_ID = @IN_CUST_ID
        
        UPDATE TCUSTSCOREDETAIL SET END_DATE = @IN_RATING_DATE-1
            WHERE OPERAND_ID = @IN_OPERAND_ID AND SUBJECT_ID = @IN_SUBJECT_ID AND CUST_ID = @IN_CUST_ID
              AND END_DATE = @V_END_DATE
        
        INSERT INTO TCUSTSCOREDETAIL(CUST_ID,SUBJECT_ID,OPERAND_ID,SCORING_DATE,CUST_SOURCE,REGULATION,END_DATE,SUMMARY,INPUT_MAN)
             VALUES(@IN_CUST_ID,@IN_SUBJECT_ID,@IN_OPERAND_ID,@IN_RATING_DATE,
                 @IN_SCORE+@V_SCORE,@IN_SCORE,@V_END_DATE,'',@IN_INPUT_MAN)
    END
    ELSE
    BEGIN
    	  INSERT INTO TCUSTSCOREDETAIL(CUST_ID,SUBJECT_ID,OPERAND_ID,SCORING_DATE,CUST_SOURCE,REGULATION,END_DATE,SUMMARY,INPUT_MAN)
            VALUES(@IN_CUST_ID,@IN_SUBJECT_ID,@IN_OPERAND_ID,@IN_RATING_DATE,
                @IN_SCORE,@IN_SCORE,21001231,'',@IN_INPUT_MAN)
    END
--客户得分表
    SELECT @V_SUM_SCORE = SUM(CUST_SOURCE) FROM TCUSTSCOREDETAIL
        WHERE OPERAND_ID = @IN_OPERAND_ID AND SUBJECT_ID = @IN_SUBJECT_ID
            AND CUST_ID = @IN_CUST_ID AND SCORING_DATE = @IN_RATING_DATE

		IF EXISTS(SELECT * FROM TCUSTSCORE WHERE SUBJECT_ID = @IN_SUBJECT_ID AND CUST_ID = @IN_CUST_ID)
		BEGIN
        SELECT @V_CURRENT_SOURCE = CURRENT_SOURCE FROM TCUSTSCORE
            WHERE SUBJECT_ID = @IN_SUBJECT_ID AND CUST_ID = @IN_CUST_ID AND END_DATE = @V_END_DATE

        UPDATE TCUSTSCORE SET END_DATE = @IN_RATING_DATE-1
            WHERE SUBJECT_ID = @IN_SUBJECT_ID AND CUST_ID = @IN_CUST_ID AND END_DATE = @V_END_DATE
            
        INSERT INTO TCUSTSCORE(CUST_ID,SUBJECT_ID,CURRENT_SOURCE,CURRENTDATE,REGULATION,END_DATE,INPUT_MAN)
            VALUES(@IN_CUST_ID,@IN_SUBJECT_ID,@V_SUM_SCORE+@V_CURRENT_SOURCE,@IN_RATING_DATE,@V_SUM_SCORE,@V_END_DATE,@IN_INPUT_MAN)
    END
    ELSE
    BEGIN
        INSERT INTO TCUSTSCORE(CUST_ID,SUBJECT_ID,CURRENT_SOURCE,CURRENTDATE,REGULATION,END_DATE,INPUT_MAN)
            VALUES(@IN_CUST_ID,@IN_SUBJECT_ID,@V_SUM_SCORE,@IN_RATING_DATE,@V_SUM_SCORE,21001231,@IN_INPUT_MAN)
    END
--客户评级表
    SELECT @V_CUST_SCORE = CURRENT_SOURCE FROM TCUSTSCORE 
        WHERE SUBJECT_ID = @IN_SUBJECT_ID AND CUST_ID = @IN_CUST_ID AND CURRENTDATE = @IN_RATING_DATE
    SELECT @V_RATING_NO = RATING_NO,@V_RATING_NAME = RATING_NAME FROM TSUBJECTSCORERATING
        WHERE SUBJECT_ID = @IN_SUBJECT_ID AND (@V_CUST_SCORE BETWEEN SCORE_LOWER AND SCORE_UPPER)
        
    IF EXISTS(SELECT * FROM TCUSTRATING WHERE SUBJECT_ID = @IN_SUBJECT_ID AND CUST_ID = @IN_CUST_ID)
        IF EXISTS(SELECT * FROM TCUSTRATING WHERE SUBJECT_ID = @IN_SUBJECT_ID AND CUST_ID = @IN_CUST_ID AND RATING_NO = @V_RATING_NO)
            UPDATE TCUSTRATING SET RATING_DATE = @IN_RATING_DATE,INPUT_MAN = @IN_INPUT_MAN
                WHERE SUBJECT_ID = @IN_SUBJECT_ID AND CUST_ID = @IN_CUST_ID AND RATING_NO = @V_RATING_NO
        ELSE
        BEGIN
        	  UPDATE TCUSTRATING SET END_DATE = @IN_RATING_DATE-1
                WHERE SUBJECT_ID = @IN_SUBJECT_ID AND CUST_ID = @IN_CUST_ID AND END_DATE = @V_END_DATE
            INSERT INTO TCUSTRATING(CUST_ID,SUBJECT_ID,RATING_NO,RATING_NAME,RATING_DATE,END_DATE,INPUT_MAN)
                VALUES(@IN_CUST_ID,@IN_SUBJECT_ID,@V_RATING_NO,@V_RATING_NAME,@IN_RATING_DATE,21001231,@IN_INPUT_MAN)
        END
    ELSE
        INSERT INTO TCUSTRATING(CUST_ID,SUBJECT_ID,RATING_NO,RATING_NAME,RATING_DATE,END_DATE,INPUT_MAN)
            VALUES(@IN_CUST_ID,@IN_SUBJECT_ID,@V_RATING_NO,@V_RATING_NAME,@IN_RATING_DATE,21001231,@IN_INPUT_MAN)

    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    SELECT @SSUMMARY = N'增加客户评级打分信息，评分科目ID：'+RTRIM(@IN_SUBJECT_ID)+N'，客户ID：'+RTRIM(@IN_CUST_ID)+'，操作数ID：'+RTRIM(@IN_OPERAND_ID)
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
