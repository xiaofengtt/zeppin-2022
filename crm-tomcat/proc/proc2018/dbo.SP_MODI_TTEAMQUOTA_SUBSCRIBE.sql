﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE SP_MODI_TTEAMQUOTA_SUBSCRIBE @IN_SERIAL_NO             INT,
                                              @IN_PRODUCT_ID            INT,
                                              @IN_TEAM_ID               INT,
                                              @IN_ALREADYSALE           DECIMAL(16,3),
                                              @IN_SERIAL_NO_CONTRACT    INT,
                                              @IN_ALREADY_QUALIFIED_NUM INT,
                                              @IN_INPUT_MAN             INT,
                                              @IN_PREPRODUCT_ID         INT = 0,
											  @IN_SUB_PRODUCT_ID		INT = 0

WITH ENCRYPTION
AS
    DECLARE @V_RET_CODE INT,@IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)
    DECLARE @V_PRODUCT_ID INT,@V_RG_MONEY DEC(16,3),@V_LINK_MAN INT
    DECLARE @V_TEAM_ID INT,@V_SERIAL_NO INT,@V_SUB_PRODUCT_ID INT
    DECLARE @V_QUOTAMONEY DEC(16,3)
    SELECT @SBUSI_NAME = N'修改销售配额'
    SELECT @SSUMMARY = N'修改销售配额'
    SELECT @IBUSI_FLAG = 30105
    SELECT @V_RET_CODE = -30105000

    IF @IN_PRODUCT_ID IS NULL OR @IN_PRODUCT_ID = 0
        RETURN -10110001 --产品不存在

    BEGIN TRANSACTION

    IF @IN_SERIAL_NO_CONTRACT <> 0
    BEGIN

        BEGIN
            SELECT @V_PRODUCT_ID = PRODUCT_ID, @V_RG_MONEY = RG_MONEY, @V_LINK_MAN = LINK_MAN
                FROM INTRUST..TCONTRACT WHERE SERIAL_NO = @IN_SERIAL_NO_CONTRACT
        END

        IF EXISTS(SELECT * FROM TMANAGERTEAMMEMBERS WHERE (MANAGERID = @V_LINK_MAN))
        BEGIN
            SELECT @V_TEAM_ID = TEAM_ID FROM TMANAGERTEAMMEMBERS WHERE (MANAGERID = @V_LINK_MAN)
        END
        ELSE
        BEGIN
            SELECT @V_TEAM_ID = -1
        END

        SELECT @V_SERIAL_NO = SERIAL_NO,@V_QUOTAMONEY = QUOTAMONEY
                FROM TTEAMQUOTA 
				WHERE (TEAM_ID = @V_TEAM_ID )
					AND (PRODUCT_ID = @V_PRODUCT_ID) 
					AND (SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID OR ISNULL(@IN_SUB_PRODUCT_ID,0)=0)

/*         IF @V_QUOTAMONEY < @IN_ALREADYSALE
        BEGIN
           RETURN -30105002 --用户所在团队在该产品上累计已销售额超过配额值
        END
 */

        UPDATE TTEAMQUOTA
            SET ALREADYSALE = ALREADYSALE - @V_RG_MONEY
            WHERE SERIAL_NO = @V_SERIAL_NO
    END

    IF EXISTS(SELECT * FROM TTEAMQUOTA WHERE (PRODUCT_ID = @IN_PRODUCT_ID) AND (TEAM_ID = @IN_TEAM_ID) AND (SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID))
    BEGIN
        UPDATE TTEAMQUOTA
           SET ALREADYSALE = ISNULL(@IN_ALREADYSALE,0),
               ALREADY_QUALIFIED_NUM = @IN_ALREADY_QUALIFIED_NUM
           WHERE SERIAL_NO = @IN_SERIAL_NO
    END
    ELSE
    BEGIN
       INSERT INTO TTEAMQUOTA(PRODUCT_ID,TEAM_ID,QUOTAMONEY,ALREADYSALE,QUOTA_QUALIFIED_NUM,ALREADY_QUALIFIED_NUM,SUB_PRODUCT_ID)
           VALUES (@IN_PRODUCT_ID,@IN_TEAM_ID,0,@IN_ALREADYSALE,0,@IN_ALREADY_QUALIFIED_NUM,@IN_SUB_PRODUCT_ID)
    END
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    SELECT @SSUMMARY = N'修改销售配额'
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