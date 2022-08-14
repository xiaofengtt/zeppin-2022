USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TQuestRecord    @IN_ServiceTaskID       INTEGER,            --任务ID
                                        @IN_ServiceTaskDetailID INTEGER,            --任务明细ID
                                        @IN_TargetCustID        INTEGER,            --客户ID
                                        @IN_QUES_ID             INTEGER,            --问卷ID
                                        @IN_TOPIC_ID            INTEGER,            --题目ID
                                        @IN_TOPIC_VALE          NVARCHAR(30),       --题目值
                                        @IN_TOPIC_Score         INTEGER,            --题目分
                                        @IN_INPUT_MAN           INT = 0             --操作员
WITH ENCRYPTION
AS
DECLARE @V_TargetCustName NVARCHAR(30),@V_QUES_TITLE NVARCHAR(30)
DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)

SELECT @V_RET_CODE = -30504000, @IBUSI_FLAG = 30504
SELECT @SBUSI_NAME = N'添加问卷调查记录信息', @SSUMMARY = N'添加问卷调查记录信息'

SELECT @V_TargetCustName = CUST_NAME FROM TCustomers WHERE CUST_ID = @IN_TargetCustID
SELECT @V_QUES_TITLE = QUES_TITLE FROM TQUESTINFO WHERE QUES_ID = @IN_QUES_ID

BEGIN TRANSACTION
    INSERT INTO TQuestRecord(ServiceTaskID,ServiceTaskDetailID,TargetCustID,TargetCustName,Ques_ID,QUES_TITLE,TOPIC_ID,TOPIC_VALE,TOPIC_Score,InsertTime,InsertMan)
    VALUES (@IN_ServiceTaskID,@IN_ServiceTaskDetailID,@IN_TargetCustID,@V_TargetCustName,@IN_QUES_ID,@V_QUES_TITLE,@IN_TOPIC_ID,@IN_TOPIC_VALE,@IN_TOPIC_Score,CONVERT(DATETIME,CONVERT(NVARCHAR(64),GETDATE(),120)),@IN_INPUT_MAN)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SET @SSUMMARY = N'添加问卷调查记录信息，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
