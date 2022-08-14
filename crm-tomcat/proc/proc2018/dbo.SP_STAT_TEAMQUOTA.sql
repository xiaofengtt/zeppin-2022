﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_STAT_TEAMQUOTA
                                  @IN_PRODUCT_ID   INT,             --产品ID
                                  @IN_INPUT_MAN    INT              --操作员
                                  
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    --产品，销售团队，销售人员，销售金额，提成比例，提成金额
    --TManagerTeamMembers
    --SELECT * FROM TTEAMQUOTA
    DECLARE @V_PRODUCT_NAME NVARCHAR(60),@V_COMMISSION_RATE NUMERIC(5,3)
    
    SELECT @V_PRODUCT_NAME=PRODUCT_NAME,@V_COMMISSION_RATE=COMMISSION_RATE FROM TPRODUCT WHERE PRODUCT_ID=@IN_PRODUCT_ID
    SELECT @V_PRODUCT_NAME PRODUCT_NAME,A.TEAM_NAME,A.MANAGERNAME,B.ALREADYSALE,@V_COMMISSION_RATE COMMISSION_RATE,B.ALREADYSALE*@V_COMMISSION_RATE COMMISSION_MONEY FROM 
		TManagerTeamMembers A LEFT JOIN TTEAMQUOTA B ON A.TEAM_ID = B.TEAM_ID AND B.PRODUCT_ID =@IN_PRODUCT_ID
		WHERE B.ALREADYSALE IS NOT NULL
GO