USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE dbo.SP_AML_MODI_CUST @IN_CUST_ID           INT,             --对应客户信息表中的ID
                                  @IN_POST_ADDRESS      NVARCHAR(60),    --客户地址POST_ADDRESS
                                  @IN_CUST_TEL          NVARCHAR(20),    --客户联系电话CUST_TEL
                                  @IN_POST_ADDRESS2     NVARCHAR(60),    --客户其他联系方式POST_ADDRESS2
                                  @IN_CARD_TYPE         NVARCHAR(10),    --证件类型
                                  @IN_CARD_ID           NVARCHAR(40),    --证件号码
                                  @IN_VOC_TYPE          NVARCHAR(10),    --个人职业/机构行业类别(1142/2142)
                                  @IN_CARD_VALID_DATE   INT,             --客户身份证件有效期限8位日期表示
                                  @IN_COUNTRY           NVARCHAR(10),    --客户国籍(9901)
                                  @IN_JG_CUST_TYPE      NVARCHAR(10),    --机构客户类别(9921)，仅在CUST_TYPE=2机构时有效
                                  @IN_INPUT_MAN         INT,             --操作员
                                  @IN_FACT_CONTROLLER   NVARCHAR(60)=''  --实际控制人
WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT, @SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -2020106, @IBUSI_FLAG = 20201
    SELECT @SBUSI_NAME = '修改客户附加信息', @SSUMMARY = '修改客户附加信息'

    DECLARE @V_VOC_TYPE_NAME NVARCHAR(30), @V_CARD_TYPE_NAME NVARCHAR(30)

    IF NOT EXISTS(SELECT * FROM TCUSTOMERS WHERE CUST_ID = @IN_CUST_ID)
        RETURN @V_RET_CODE - 1  -- 客户不存在
    IF NOT EXISTS(SELECT * FROM TDICTPARAM WHERE TYPE_VALUE = @IN_COUNTRY)
        RETURN @V_RET_CODE - 2  -- 输入国籍无效
    IF @IN_JG_CUST_TYPE IS NOT NULL AND @IN_JG_CUST_TYPE <> ''
        IF NOT EXISTS(SELECT * FROM TDICTPARAM WHERE TYPE_VALUE = @IN_JG_CUST_TYPE)
            RETURN @V_RET_CODE - 3  -- 输入机构客户类别无效

    SELECT @V_VOC_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_VOC_TYPE
    SELECT @V_CARD_TYPE_NAME = TYPE_CONTENT FROM TDICTPARAM  WHERE TYPE_VALUE = @IN_CARD_TYPE

    BEGIN TRANSACTION

UPDATE INTRUST..TCUSTOMERINFO
        SET CARD_VALID_DATE = @IN_CARD_VALID_DATE,
            COUNTRY = @IN_COUNTRY,
            JG_CUST_TYPE = @IN_JG_CUST_TYPE,
            POST_ADDRESS = @IN_POST_ADDRESS,
            CUST_TEL = @IN_CUST_TEL,
            POST_ADDRESS2 = @IN_POST_ADDRESS2,
            CARD_TYPE = @IN_CARD_TYPE,
            CARD_TYPE_NAME = @V_CARD_TYPE_NAME,
            CARD_ID = @IN_CARD_ID,
            VOC_TYPE = @IN_VOC_TYPE,
            VOC_TYPE_NAME = @V_VOC_TYPE_NAME,
            FACT_CONTROLLER = @IN_FACT_CONTROLLER
        WHERE CUST_ID = @IN_CUST_ID
        
    UPDATE TCUSTOMERS
        SET CARD_VALID_DATE = @IN_CARD_VALID_DATE,
            COUNTRY = @IN_COUNTRY,
            JG_CUST_TYPE = @IN_JG_CUST_TYPE,
            POST_ADDRESS = @IN_POST_ADDRESS,
            CUST_TEL = @IN_CUST_TEL,
            POST_ADDRESS2 = @IN_POST_ADDRESS2,
            CARD_TYPE = @IN_CARD_TYPE,
            CARD_TYPE_NAME = @V_CARD_TYPE_NAME,
            CARD_ID = @IN_CARD_ID,
            VOC_TYPE = @IN_VOC_TYPE,
            VOC_TYPE_NAME = @V_VOC_TYPE_NAME,
            FACT_CONTROLLER = @IN_FACT_CONTROLLER
        WHERE CUST_ID = @IN_CUST_ID
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = '修改客户附加信息'
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
