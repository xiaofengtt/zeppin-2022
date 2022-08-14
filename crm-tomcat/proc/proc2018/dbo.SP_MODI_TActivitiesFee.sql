USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE SP_MODI_TActivitiesFee     @IN_SERIAL_NO           INT,            --活动费用号
                                            @IN_FeeItems            NVARCHAR(128),  -- 费用名目
                                            @IN_FeeAmount           DECIMAL(16,3),  -- 费用金额
                                            @IN_Remark              NVARCHAR(512),
                                            @IN_INPUT_MAN           INT = 0         -- 操作员
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200),@V_ActiveFee DECIMAL(16,3),@V_Active_Serial_no INT
    DECLARE @V_ORIGINAL_FEEAMOUNT DECIMAL(16,3),@V_NEW_FEEAMOUNT DECIMAL(16,3),@V_AVERAGE_AMOUNT DECIMAL(16,3)
    DECLARE @V_COUNT INT
    SELECT @V_RET_CODE = -30403000, @IBUSI_FLAG = 30403

    SET @V_NEW_FEEAMOUNT = ISNULL(@V_NEW_FEEAMOUNT,0)
    SET @V_AVERAGE_AMOUNT = ISNULL(@V_AVERAGE_AMOUNT,0)

    SELECT @SBUSI_NAME = N'修改营销活动费用信息', @SSUMMARY = N'修改营销活动费用信息'
    SELECT @V_Active_Serial_no = Active_Serial_no FROM TActivitiesFee WHERE Serial_no = @IN_SERIAL_NO

    SELECT @V_ORIGINAL_FEEAMOUNT = FEEAMOUNT FROM TActivitiesFee WHERE  Serial_no = @IN_SERIAL_NO
    SELECT @V_COUNT = COUNT(*) FROM TACTIVITYCUSTS WHERE ACTIVITY_ID = @V_Active_Serial_no

    BEGIN TRANSACTION
        UPDATE TActivitiesFee
        SET FeeItems = @IN_FeeItems ,
            FeeAmount = @IN_FeeAmount,
            Remark = @IN_Remark
        WHERE Serial_no = @IN_SERIAL_NO
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END

        --费用分摊--
        IF @V_COUNT <> 0
        BEGIN
            SELECT @V_AVERAGE_AMOUNT = ACTIVITY_FEE FROM TACTIVITYCUSTS WHERE ACTIVITY_ID = @V_Active_Serial_no
            SELECT @V_NEW_FEEAMOUNT = (@IN_FeeAmount-@V_ORIGINAL_FEEAMOUNT)/@V_COUNT
            UPDATE TACTIVITYCUSTS SET ACTIVITY_FEE = @V_AVERAGE_AMOUNT+@V_NEW_FEEAMOUNT WHERE ACTIVITY_ID = @V_Active_Serial_no
            IF @@ERROR <> 0
            BEGIN
                ROLLBACK TRANSACTION
                RETURN -100
            END
        END
        ----分摊汇总后更新到TCUSTSERVICEINFO表---
        UPDATE TCUSTSERVICEINFO
            SET ACTIVITY_FEE =B.ACTIVITY_FEE
            FROM TCUSTSERVICEINFO A,(SELECT CUST_ID,SUM(ACTIVITY_FEE)AS ACTIVITY_FEE FROM TACTIVITYCUSTS GROUP BY CUST_ID ) B
                WHERE A.CUST_ID =B.CUST_ID
        ----汇总后插入到c表------
        --INSERT INTO TCUSTSERVICEINFO(CUST_ID,ACTIVITY_FEE)
        --  SELECT A.CUST_ID,A.ACTIVITY_FEE
        --      FROM (SELECT CUST_ID,SUM(ACTIVITY_FEE)AS ACTIVITY_FEE FROM TACTIVITYCUSTS GROUP BY CUST_ID) A
        --          WHERE A.CUST_ID NOT IN(SELECT CUST_ID FROM TCUSTSERVICEINFO GROUP BY CUST_ID)


        SELECT @V_ActiveFee = ISNULL(SUM(FeeAmount),0) FROM TActivitiesFee WHERE Active_Serial_no = @V_Active_Serial_no --获得活动费用总数
        UPDATE TActivities
        SET ACTIVITY_FEE = @V_ActiveFee
        WHERE SERIAL_NO = @V_Active_Serial_no
        IF @@ERROR <> 0
        BEGIN
            ROLLBACK TRANSACTION
            RETURN -100
        END

        SET @SSUMMARY = N'修改营销活动费用信息，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
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
