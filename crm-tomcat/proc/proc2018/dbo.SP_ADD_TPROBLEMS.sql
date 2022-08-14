﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TPROBLEMS  @IN_PROJECTID            INTEGER,
                                   @IN_ITEM_ID              INTEGER,
                                   @IN_PROBLEMTYPEID        INTEGER,
                                   @IN_PROBLEMSTATEID       INTEGER,
                                   @IN_PROBLEMSEVERITYID    INTEGER,
                                   @IN_PROBLEMPRIORITYID    INTEGER,
                                   @IN_DEADLINE             INTEGER,
                                   @IN_ASSIGNEDTO           INTEGER,
                                   @IN_TITLE                NVARCHAR(50),
                                   @IN_DESCRIPTION          NTEXT,
                                   @IN_STARTLINE            INTEGER,
                                   @IN_INPUT_MAN            INTEGER,
                                   @OUT_PROBLEMID           INTEGER         OUTPUT,
                                   @IN_PROBLEM_NO           NVARCHAR(50)    = '',       --事务编号，由创建人自己填写
                                   @IN_LAUNCH_FLAG          INTEGER         = 1,        --发起标志 1 自动发起 2手工发起
                                   @IN_BUSINESS_ID          NVARCHAR(200)   = '',       --为支持对多条业务记录进行审核的情况,存放业务ID,用,隔开
                                   @IN_PRODUCT_ID           INT = 0,                    -- 如果传入参数,则直接按传入的为PRODUCT_ID
                                   @IN_DISCLOSURE_TYPE      NVARCHAR(30) = ''
WITH ENCRYPTION
AS
    SET NOCOUNT ON

    DECLARE @IN_PROBLEMCATALOGID INT,@V_STATETYPE INT
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT, @SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    DECLARE @V_PRODUCT_OF INTEGER, @V_PRODUCT_ID INTEGER
    SELECT @IBUSI_FLAG = 8001
    SELECT @SBUSI_NAME = N'增加事务信息', @SSUMMARY = N'增加事务信息'

    SELECT @IN_PROBLEMCATALOGID = PROJECTCATALOGID, @V_PRODUCT_OF = PRODUCT_OF
        FROM INTRUST..TPROJECTS WHERE PROJECTID = @IN_PROJECTID
    --是否要选择产品信息 "是"
    IF ISNULL(@IN_PRODUCT_ID,0) = 0
    BEGIN
        IF ISNULL(@V_PRODUCT_OF,0) = 1
            SET @V_PRODUCT_ID = @IN_ITEM_ID
    END
    ELSE
        SELECT @V_PRODUCT_ID = @IN_PRODUCT_ID
    --            
    IF ISNULL(@IN_PROBLEMSTATEID,0) = 0
        SELECT @IN_PROBLEMSTATEID = MIN(PROBLEMSTATEID) FROM INTRUST..TPROBLEMSTATE WHERE PROJECTID = @IN_PROJECTID AND LISTORDER = 1

    SELECT @V_STATETYPE = STATETYPE FROM INTRUST..TPROBLEMSTATE WHERE PROBLEMSTATEID = @IN_PROBLEMSTATEID

    IF NOT EXISTS(SELECT 1 FROM INTRUST..TPROBLEMSTATE WHERE PROBLEMSTATEID = @IN_PROBLEMSTATEID)
        RETURN -8002101    -- 事务状态未选择

    BEGIN TRANSACTION

    INSERT INTO INTRUST..TPROBLEMS(PROJECTID,ITEM_ID,PROBLEMCATALOGID,PROBLEMTYPEID,PROBLEMSTATEID,PROBLEMSEVERITYID,
                          PROBLEMPRIORITYID,CREATEUSERID,STARTLINE,DEADLINE,ASSIGNEDTO,TITLE,DESCRIPTION,
                          PROBLEM_NO,LAUNCH_FLAG,BUSINESS_ID,PRODUCT_ID,DISCLOSURE_TYPE)
    VALUES(@IN_PROJECTID,@IN_ITEM_ID,@IN_PROBLEMCATALOGID,@IN_PROBLEMTYPEID,@IN_PROBLEMSTATEID,@IN_PROBLEMSEVERITYID,
           @IN_PROBLEMPRIORITYID,@IN_INPUT_MAN,@IN_STARTLINE,@IN_DEADLINE,@IN_ASSIGNEDTO,@IN_TITLE,@IN_DESCRIPTION,
           @IN_PROBLEM_NO,@IN_LAUNCH_FLAG,@IN_BUSINESS_ID,@V_PRODUCT_ID,@IN_DISCLOSURE_TYPE)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    SELECT @OUT_PROBLEMID = @@IDENTITY

    /*
    --更新状态变动记录表
    EXEC SP_INNER_RECORDSTATESTART @OUT_PROBLEMID,@IN_PROBLEMSTATEID,@IN_ASSIGNEDTO
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END
    */
    INSERT INTO INTRUST..TPMTASKRECORD(PROBLEMID,CREATEUSER,TOSTATEID,STATETYPE,USERID)
        VALUES(@OUT_PROBLEMID,@IN_INPUT_MAN,@IN_PROBLEMSTATEID,@V_STATETYPE,@IN_ASSIGNEDTO)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    --更新项目统计信息
    EXEC SP_INNER_PROJECTSTAT @IN_PROJECTID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'增加事务信息，标题：' + @IN_TITLE
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