USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TCUSTTELINFO    @IN_CUST_ID         INTEGER,        --客户ID
                                        @IN_CUST_TEL        NVARCHAR(60),   --联系电话
                                        @IN_OPERAT_FLAG     INTEGER,        --联系方式操作标志(1更新 2新增)
                                        @IN_INPUT_MAN       INTEGER,        --操作员
                                        @IN_OPER_FLAG       INTEGER = 1     --更新字段标志 1H_TEL 2O_TEL 3MOBILE 4BP
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200), @V_CUST_NAME NVARCHAR(60)
    SELECT @SBUSI_NAME = N'来电之后对于老客户如果与联系电话不一致时提供两种操作1更新联系电话2添加联系电话'
    SELECT @SSUMMARY = N'来电之后对于老客户如果与联系电话不一致时提供两种操作1更新联系电话2添加联系电话'
    SELECT @IBUSI_FLAG = 20205
    SELECT @V_RET_CODE = -20205000

    IF ISNULL(@IN_OPERAT_FLAG,0) = 0
        SET @IN_OPERAT_FLAG = 1 --默认更新
    IF ISNULL(@IN_CUST_ID,0) <> 0
        SELECT @V_CUST_NAME = CUST_NAME FROM TCUSTOMERS WHERE CUST_ID = @IN_CUST_ID
    
    BEGIN TRANSACTION

    IF @IN_OPERAT_FLAG = 1 BEGIN
        IF @IN_OPER_FLAG = 1 BEGIN
            UPDATE TCUSTOMERS SET H_TEL = @IN_CUST_TEL WHERE CUST_ID = @IN_CUST_ID
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END
        END
        IF @IN_OPER_FLAG = 2 BEGIN
            UPDATE TCUSTOMERS SET O_TEL = @IN_CUST_TEL WHERE CUST_ID = @IN_CUST_ID
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END
        END
        IF @IN_OPER_FLAG = 3 BEGIN
            UPDATE TCUSTOMERS SET MOBILE = @IN_CUST_TEL WHERE CUST_ID = @IN_CUST_ID
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END
        END
        IF @IN_OPER_FLAG = 4 BEGIN
            UPDATE TCUSTOMERS SET BP = @IN_CUST_TEL WHERE CUST_ID = @IN_CUST_ID
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END
        END
    END
    ELSE BEGIN
        IF @IN_OPER_FLAG = 1 BEGIN
            INSERT INTO TCustomerContacts (CUST_ID,Contactor,PhoneHome,InsertMan)
                VALUES(@IN_CUST_ID,@V_CUST_NAME,@IN_CUST_TEL,@IN_INPUT_MAN)
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END
        END
        IF @IN_OPER_FLAG = 2 BEGIN
            INSERT INTO TCustomerContacts (CUST_ID,Contactor,PhoneOffice,InsertMan)
                VALUES(@IN_CUST_ID,@V_CUST_NAME,@IN_CUST_TEL,@IN_INPUT_MAN)
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END
        END
        IF (@IN_OPER_FLAG = 3) OR (@IN_OPER_FLAG = 4) BEGIN
            INSERT INTO TCustomerContacts (CUST_ID,Contactor,Moblie,InsertMan)
                VALUES(@IN_CUST_ID,@V_CUST_NAME,@IN_CUST_TEL,@IN_INPUT_MAN)
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END
        END
    END
    
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
