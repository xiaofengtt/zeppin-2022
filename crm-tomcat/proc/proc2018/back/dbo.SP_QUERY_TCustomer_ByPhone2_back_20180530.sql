USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCustomer_ByPhone2 @IN_PhoneNumber NVARCHAR(60), --电话号码
                                             @IN_INPUT_MAN   INT
WITH ENCRYPTION
AS
    DECLARE @V_COUNT INT
    DECLARE @V_TEMPCUST TABLE(CUST_ID INT) --返回的CUST_ID
	--加密标志	encrypion_size
	DECLARE @V_FLAG_ENCRYPTION INT,@V_USER_ID INT,@V_ENCRYPTION_SIZE INT
	DECLARE @V_FACT_POTE_FLAG INT ,@V_IS_DEAL INT --事实潜在客户加密条件
	
	--能够访问所有客户信息
	--说明：这里是电话匹配过程 所以可以查看所有客户 只需对敏感信息过滤
    SELECT @V_FLAG_ENCRYPTION = 0 --加密标志		
	--事实潜在客户加密条件
	SELECT @V_FACT_POTE_FLAG = VALUE FROM TSYSCONTROL WHERE  FLAG_TYPE = 'FACT_POTENTIAL_FLAG'

    IF @V_FACT_POTE_FLAG = 1  SET @V_IS_DEAL = 2 -- 潜在客户信息加密
    IF @V_FACT_POTE_FLAG = 2  SET @V_IS_DEAL = 1 -- 事实客户信息加密
    IF @V_FACT_POTE_FLAG = 3  SET @V_IS_DEAL = 0 -- 事实、潜在客户信息加密
    IF @V_FACT_POTE_FLAG = 4  SET @V_IS_DEAL = 3 -- 事实、潜在客户信息都不加密
	
	IF (@V_USER_ID = 8) AND EXISTS(SELECT 1 FROM TOPROLE WHERE OP_CODE = @IN_INPUT_MAN AND ROLE_ID = 5)--如果用户存在打印角色的时候 不需要加密
    BEGIN
        SET @V_FLAG_ENCRYPTION = 2
    END
    ELSE
    BEGIN
        --如果操作员的角色中存在保密限制的角色，则根据保密优先的原则，则进行保密限制
        IF EXISTS(SELECT 1 FROM TOPROLE WHERE OP_CODE = @IN_INPUT_MAN AND ROLE_ID IN(SELECT ROLE_ID FROM TROLE WHERE FLAG_ENCRYPTION = 1))
            SELECT @V_FLAG_ENCRYPTION = 1
    END
	--加密显示位数	
	IF EXISTS(SELECT 1 FROM TDICTPARAM WHERE TYPE_VALUE = '800701')
		SELECT @V_ENCRYPTION_SIZE = CAST(TYPE_CONTENT AS INT) FROM TDICTPARAM WHERE TYPE_VALUE = '800701'
	ELSE
		SET  @V_ENCRYPTION_SIZE = 4
	
	

    IF LEN(@IN_PhoneNumber)>0
    BEGIN
        --去掉来电号码中前面的"0"
        IF LEFT(@IN_PhoneNumber,1) = N'0'
            SET @IN_PhoneNumber = RIGHT(@IN_PhoneNumber,LEN(@IN_PhoneNumber)-1)
        SET @IN_PhoneNumber = REPLACE(@IN_PhoneNumber, N'-',N'')

        --去掉客户信息、联系人信息中各电话号码中的非数字字符
        --1.完全匹配
        INSERT INTO @V_TEMPCUST
        SELECT CUST_ID FROM TCustomers
            WHERE (REPLACE(CUST_TEL,N'-',N'') = @IN_PhoneNumber OR REPLACE(O_TEL, N'-',N'') = @IN_PhoneNumber
                    OR REPLACE(H_TEL, N'-',N'') = @IN_PhoneNumber OR REPLACE(MOBILE, N'-',N'') = @IN_PhoneNumber OR REPLACE(FAX, N'-',N'') = @IN_PhoneNumber
                    OR EXISTS(SELECT CUST_ID FROM TCustomerContacts WHERE TCustomers.CUST_ID = TCustomerContacts.CUST_ID
                                AND(REPLACE(PhoneHome,N'-',N'') = @IN_PhoneNumber OR REPLACE(PhoneOffice,N'-',N'') = @IN_PhoneNumber
                                    OR REPLACE(Moblie,N'-',N'') = @IN_PhoneNumber OR REPLACE(Fax,N'-',N'') = @IN_PhoneNumber
                                    OR REPLACE(AssistantTelphone,N'-',N'') = @IN_PhoneNumber OR REPLACE(ManagerTelphone,N'-',N'') = @IN_PhoneNumber)
                    ) )

        SELECT @V_COUNT = COUNT(*) FROM @V_TEMPCUST
        --2.右匹配
        IF @V_COUNT<=0
        BEGIN
            SET @IN_PhoneNumber = N'%'+@IN_PhoneNumber
            INSERT INTO @V_TEMPCUST
            SELECT CUST_ID FROM TCustomers
            WHERE (REPLACE(CUST_TEL,N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(O_TEL, N'-',N'') LIKE @IN_PhoneNumber
                    OR REPLACE(H_TEL, N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(MOBILE, N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(FAX, N'-',N'') LIKE @IN_PhoneNumber
                    OR EXISTS(SELECT CUST_ID FROM TCustomerContacts WHERE TCustomers.CUST_ID = TCustomerContacts.CUST_ID
                                AND(REPLACE(PhoneHome,N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(PhoneOffice,N'-',N'') LIKE @IN_PhoneNumber
                                    OR REPLACE(Moblie,N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(Fax,N'-',N'') LIKE @IN_PhoneNumber
                                    OR REPLACE(AssistantTelphone,N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(ManagerTelphone,N'-',N'') LIKE @IN_PhoneNumber)
                    ) )

            SELECT @V_COUNT = COUNT(*) FROM @V_TEMPCUST
            --3.左匹配
            IF @V_COUNT<=0
            BEGIN
                SET @IN_PhoneNumber = @IN_PhoneNumber+'%'
                INSERT INTO @V_TEMPCUST
                SELECT CUST_ID FROM TCustomers
                WHERE (REPLACE(CUST_TEL,N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(O_TEL, N'-',N'') LIKE @IN_PhoneNumber
                        OR REPLACE(H_TEL, N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(MOBILE, N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(FAX, N'-',N'') LIKE @IN_PhoneNumber
                        OR EXISTS(SELECT CUST_ID FROM TCustomerContacts WHERE TCustomers.CUST_ID = TCustomerContacts.CUST_ID
                                    AND(REPLACE(PhoneHome,N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(PhoneOffice,N'-',N'') LIKE @IN_PhoneNumber
                                        OR REPLACE(Moblie,N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(Fax,N'-',N'') LIKE @IN_PhoneNumber
                                        OR REPLACE(AssistantTelphone,N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(ManagerTelphone,N'-',N'') LIKE @IN_PhoneNumber)
                        ) )

--                SELECT @V_COUNT = COUNT(*) FROM @V_TEMPCUST
--                IF @V_COUNT<=0
--                BEGIN
--                    --4.取来电号码右7位模糊匹配
--                    SET @IN_PhoneNumber = N'%'+RIGHT(@IN_PhoneNumber,7)+'%'
--
--                    INSERT INTO @V_TEMPCUST
--                    SELECT CUST_ID FROM TCustomers
--                    WHERE (REPLACE(CUST_TEL,N'-',N'') = @IN_PhoneNumber OR REPLACE(O_TEL, N'-',N'') = @IN_PhoneNumber
--                            OR REPLACE(H_TEL, N'-',N'') = @IN_PhoneNumber OR REPLACE(MOBILE, N'-',N'') = @IN_PhoneNumber OR REPLACE(FAX, N'-',N'') = @IN_PhoneNumber
--                            OR EXISTS(SELECT CUST_ID FROM TCustomerContacts WHERE TCustomers.CUST_ID = TCustomerContacts.CUST_ID
--                                        AND(REPLACE(PhoneHome,N'-',N'') = @IN_PhoneNumber OR REPLACE(PhoneOffice,N'-',N'') = @IN_PhoneNumber
--                                            OR REPLACE(Moblie,N'-',N'') = @IN_PhoneNumber OR REPLACE(Fax,N'-',N'') = @IN_PhoneNumber
--                                            OR REPLACE(AssistantTelphone,N'-',N'') = @IN_PhoneNumber OR REPLACE(ManagerTelphone,N'-',N'') = @IN_PhoneNumber)
--                            ) )
--                END
            END
        END
    END
	--临时表保存客户信息
    SELECT A.[CUST_ID]
		  ,A.[CUST_NO]
		  ,A.[CUST_NAME]
		  ,A.[CUST_TEL]
		  ,A.[POST_ADDRESS]
		  ,A.[POST_ADDRESS2]
		  ,A.[POST_CODE]
		  ,A.[POST_CODE2]
		  ,A.[CARD_TYPE]
		  ,A.[CARD_TYPE_NAME]
		  ,A.[CARD_ID]
		  ,A.[CARD_VALID_DATE]
		  ,A.[CARD_ADDRESS]
		  ,A.[COUNTRY]
		  ,A.[BIRTHDAY]
		  ,A.[AGE]
		  ,A.[SEX]
		  ,A.[SEX_NAME]
		  ,A.[O_TEL]
		  ,A.[H_TEL]
		  ,A.[MOBILE]
		  ,A.[BP]
		  ,A.[FAX]
		  ,A.[E_MAIL]
		  ,A.[CUST_TYPE]
		  ,A.[CUST_TYPE_NAME]
		  ,A.[JG_CUST_TYPE]
		  ,A.[WT_FLAG]
		  ,A.[WT_FLAG_NAME]
		  ,A.[PASSWORD]
		  ,A.[TOUCH_TYPE]
		  ,A.[TOUCH_TYPE_NAME]
		  ,A.[CUST_SOURCE]
		  ,A.[CUST_SOURCE_NAME]
		  ,A.[RG_TIMES]
		  ,A.[TOTAL_MONEY]
		  ,A.[CURRENT_MONEY]
		  ,A.[LAST_RG_DATE]
		  ,A.[INPUT_MAN]
		  ,A.[INPUT_TIME]
		  ,A.[CHECK_FLAG]
		  ,A.[CHECK_MAN]
		  ,A.[MODI_MAN]
		  ,A.[MODI_TIME]
		  ,A.[CANCEL_MAN]
		  ,A.[CANCEL_TIME]
		  ,A.[STATUS]
		  ,A.[STATUS_NAME]
		  ,A.[SUMMARY]
		  ,A.[LEGAL_MAN]
		  ,A.[LEGAL_ADDRESS]
		  ,A.[REGISTRATION_ID]
		  ,A.[CONTACT_MAN]
		  ,A.[LIST_ID]
		  ,A.[PRINT_DEPLOY_BILL]
		  ,A.[PRINT_POST_INFO]
		  ,A.[IS_LINK]
		  ,A.[SERVICE_MAN]
		  ,A.[ENT_CUST_ID]
		  ,A.[VIP_CARD_ID]
		  ,A.[VIP_DATE]
		  ,A.[HGTZR_BH]
		  ,A.[VOC_TYPE]
		  ,A.[VOC_TYPE_NAME]
		  ,A.[GRADE_LEVEL]
		  ,A.[GRADE_LEVEL_NAME]
		  ,A.[MONEY_SOURCE]
		  ,A.[MONEY_SOURCE_NAME]
		  ,A.[FACT_CONTROLLER]
		  ,A.[FC_CARD_TYPE]
		  ,A.[FC_CARD_ID]
		  ,A.[FC_CARD_VALID_DATE]
		  ,A.[TRANS_NAME]
		  ,A.[TRANS_CARD_TYPE]
		  ,A.[TRANS_CARD_NAME]
		  ,A.[TRANS_CARD_NO]
		  ,A.[TRANS_TEL]
		  ,A.[TRANS_MOBILE]
		  ,A.[TRANS_ADDRESS]
		  ,A.[TRANS_POST_CODE]
		  ,A.[PinYinSimple]
		  ,A.[PinYinWhole]
		  ,A.[ImageIdentification]
		  ,A.[ReceiveServices]
		  ,A.[POTENTIAL_MONEY]
		  ,A.[IS_DEAL]
		  ,A.[TRUE_FLAG]
		  ,A.[GOV_PROV_REGIONAL]
		  ,A.[GOV_PROV_REGIONAL_NAME]
		  ,A.[GOV_REGIONAL]
		  ,A.[GOV_REGIONAL_NAME]
		  ,A.[RECOMMENDED]
		  ,A.[HEAD_OFFICE_CUST_ID]
		  ,A.[STATURE]
		  ,A.[WEIGHT]
		  ,A.[SPOUSE_NAME]
		  ,A.[SPOUSE_INFO]
		  ,A.[CHILDREN_NAME]
		  ,A.[CHILDREN_INFO]
		  ,A.[EMPLOYEE_NUM]
		  ,A.[HOLDING]
		  ,A.[PERSONAL_INCOME]
		  ,A.[HOUSEHOLD_INCOME]
		  ,A.[FEEDING_NUM_PEOPLE]
		  ,A.[MAIN_SOURCE]
		  ,A.[OTHER_SOURCE]
		  ,A.[HOUSE_POSITION]
		  ,A.[HOUSE_PROPERTY]
		  ,A.[HOUSE_AREA]
		  ,A.[PLAT_ENVALUATE]
		  ,A.[MARKET_APPRAISAL]
		  ,A.[VEHICLE_NUM]
		  ,A.[VEHICLE_MAKE]
		  ,A.[VEHICLE_TYPE]
		  ,A.[CREDIT_TYPE]
		  ,A.[CREDIT_NUM]
		  ,A.[CREDIT_START_DATE]
		  ,A.[CREDIT_END_DATE]
		  ,A.[OTHER_INVESTMENT_STATUS]
		  ,A.[OTHER_INVE_STATUS_NAME]
		  ,A.[TYPE_PREF]
		  ,A.[TYPE_PREF_NAME]
		  ,A.[TIME_LIMIT_PREF]
		  ,A.[TIME_LIMIT_PREF_NAME]
		  ,A.[INVEST_PREF]
		  ,A.[INVEST_PREF_NAME]
		  ,A.[HOBBY_PREF]
		  ,A.[HOBBY_PREF_NAME]
		  ,A.[SERVICE_PREF]
		  ,A.[SERVICE_PREF_NAME]
		  ,A.[CONTACT_PREF]
		  ,A.[CONTACT_PREF_NAME]
		  ,A.[RISK_PREF]
		  ,A.[RISK_PREF_NAME]
		  ,A.[NATION]
		  ,A.[NATION_NAME]
		  ,A.[MARITAL]
		  ,A.[HEALTH]
		  ,A.[HEALTH_NAME]
		  ,A.[EDUCATION]
		  ,A.[EDUCATION_NAME]
		  ,A.[POST]
		  ,A.[HOLDEROFANOFFICE]
		  ,A.[COMPANY_CHARACTER]
		  ,A.[COMPANY_CHARACTER_NAME]
		  ,A.[COMPANY_STAFF]
		  ,A.[BOCOM_STAFF]
		  ,A.[CLIENT_QUALE]
		  ,A.[CLIENT_QUALE_NAME]
		  ,A.[REGISTERED_PLACE]
		  ,A.[REGISTERED_CAPITAL]
		  ,A.[COMPANY_FAMILY]
		  ,A.[FREE_CASH_FLOW]
		  ,A.[BUREND_OF_DEBT]
		  ,A.[COMPLETE_FLAG]
		  ,A.[MODI_FLAG]
		  ,A.[COMPANY_UNIT]
		  ,A.[COMPANY_DEPART]
		  ,A.[COMPANY_POSITION]
		  ,A.[CUST_AUM]
		  ,A.[CUST_AUM_NAME]
		  ,A.[CUST_AGE_GROUP]
		  ,A.[CUST_AGE_GROUP_NAME]
		  ,A.[INVES_EXPERINCE]
		  ,A.[CUST_SOURCE_EXPLAIN]
		  ,A.[EAST_JG_TYPE]
		  ,A.[EAST_JG_TYPE_NAME]
		  ,A.[JG_WTRLX2], C.Extension, C.RecordExtension,D.MOBILE AS SERVICE_MAN_MOBILE,
			A.CARD_ID AS H_CARD_ID,
			A.CUST_TEL AS H_CUST_TEL,
			A.MOBILE AS H_MOBILE,
			A.O_TEL AS H_O_TEL,
			A.H_TEL AS H_H_TEL,
			A.BP AS H_BP,
			A.FAX AS H_FAX,
			A.E_MAIL AS H_E_MAIL
	INTO #TEMP_QUERY_TCustomers
    FROM TCustomers A LEFT JOIN TCustManagers C ON A.SERVICE_MAN = C.ManagerID, @V_TEMPCUST B,TOPERATOR D
    WHERE A.CUST_ID = B.CUST_ID AND A.SERVICE_MAN = D.OP_CODE
	
	--开关控制 加密重要联系信息
    IF(@V_FLAG_ENCRYPTION = 1)
    BEGIN
        UPDATE #TEMP_QUERY_TCustomers
        SET --CARD_ID = ISNULL(stuff(CARD_ID,1,len(CARD_ID)-@V_ENCRYPTION_SIZE,'******'),'****'),
            CUST_TEL = ISNULL(stuff(CUST_TEL,len(CUST_TEL)-@V_ENCRYPTION_SIZE+1,len(CUST_TEL),'******'),'****'),
            O_TEL = ISNULL(stuff(O_TEL,len(O_TEL)-@V_ENCRYPTION_SIZE+1,len(O_TEL),'******'),'****'),
            H_TEL = ISNULL(stuff(H_TEL,len(H_TEL)-@V_ENCRYPTION_SIZE+1,len(H_TEL),'******'),'****'),
            E_MAIL = ISNULL(stuff(E_MAIL,1,charindex('@',E_MAIL)-1,'******'),'****'),
            FAX = ISNULL(stuff(FAX,len(FAX)-@V_ENCRYPTION_SIZE+1,len(FAX),'******'),'****'),
            MOBILE = ISNULL(stuff(MOBILE,len(MOBILE)-@V_ENCRYPTION_SIZE+1,len(MOBILE),'******'),'****'),
            BP = ISNULL(stuff(BP,len(BP)-@V_ENCRYPTION_SIZE+1,len(BP),'******'),'****'),
            POST_ADDRESS = ISNULL(stuff(POST_ADDRESS,len(POST_ADDRESS)-@V_ENCRYPTION_SIZE+1,len(POST_ADDRESS),'******'),'****'),
            H_CUST_TEL = ISNULL(stuff(H_CUST_TEL,len(H_CUST_TEL)-@V_ENCRYPTION_SIZE+1,len(H_CUST_TEL),'******'),'****'),
            H_MOBILE   = ISNULL(stuff(H_MOBILE,len(H_MOBILE)-@V_ENCRYPTION_SIZE+1,len(H_MOBILE),'******'),'****'),
            H_O_TEL    = ISNULL(stuff(H_O_TEL,len(H_O_TEL)-@V_ENCRYPTION_SIZE+1,len(H_O_TEL),'******'),'****'),
            H_H_TEL    = ISNULL(stuff(H_H_TEL,len(H_H_TEL)-@V_ENCRYPTION_SIZE+1,len(H_H_TEL),'******'),'****'),
            H_BP       = ISNULL(stuff(H_BP,len(H_BP)-@V_ENCRYPTION_SIZE+1,len(H_BP),'******'),'****'),
            H_FAX      = ISNULL(stuff(H_FAX,len(H_FAX)-@V_ENCRYPTION_SIZE+1,len(H_FAX),'******'),'****'),
            H_E_MAIL   = ISNULL(stuff(H_E_MAIL,len(H_E_MAIL)-@V_ENCRYPTION_SIZE+1,len(H_E_MAIL),'******'),'****')
        FROM #TEMP_QUERY_TCustomers A
        WHERE ISNULL(SERVICE_MAN,0) <> @IN_INPUT_MAN --不是客户经理本人的客户
          --  AND (IS_DEAL = @V_IS_DEAL OR ISNULL(@V_IS_DEAL,0)=0)潜在or事实 客户加密
    END
	
	SELECT * FROM #TEMP_QUERY_TCustomers 
GO
