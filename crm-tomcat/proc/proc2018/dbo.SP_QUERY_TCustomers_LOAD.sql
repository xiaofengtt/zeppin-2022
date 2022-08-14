﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE SP_QUERY_TCustomers_LOAD @IN_CUST_ID INT,
                                          @IN_INPUT_MAN INT = NULL,
										  @IN_QUERY_FLAG INT = 0,   --数据查询标志 0：按原有权限模式 1：不按客户权限并且联系信息加密显示
										  @IN_EXPORT_FLAG INT = 0 --导出标记：0非导出查询 1导出客户信息 2导出手机 3导出通讯录
WITH ENCRYPTION
AS
    DECLARE @V_FLAG_ACCESS INT, @V_FLAG_ENCRYPTION INT,@V_ENCRYPTION_SIZE INT, @V_SERVICE_NAME INT
    SELECT @V_FLAG_ACCESS       = 1    --访问标志 修改为1 此过程 不需要访问控制
    SELECT @V_FLAG_ENCRYPTION   = 0    --加密标志
    --如果操作员是该客户的客户经理或录入员 可查看
    IF EXISTS(SELECT 1 FROM TCustomers WHERE CUST_ID=@IN_CUST_ID AND (SERVICE_MAN = @IN_INPUT_MAN OR INPUT_MAN = @IN_INPUT_MAN))
        SELECT @V_FLAG_ACCESS = 1
    --能够访问所有客户信息
    IF EXISTS(SELECT 1 FROM TOPRIGHT WHERE OP_CODE = @IN_INPUT_MAN AND MENU_ID ='999' AND FUNC_ID = 99903)
        SELECT @V_FLAG_ACCESS = 1
    --如果操作员的角色中存在访问所有客户信息权限的标志 则赋予能够访问所有客户信息权限
    IF EXISTS(SELECT 1 FROM TOPROLE WHERE OP_CODE = @IN_INPUT_MAN AND ROLE_ID IN(SELECT ROLE_ID FROM TROLE WHERE FLAG_ACCESS_ALL = 1))
        SELECT @V_FLAG_ACCESS = 1
    --如果操作员的角色中存在不保密限制的角色，则不进行保密限制，否则加密
    IF EXISTS(SELECT 1 FROM TOPROLE WHERE OP_CODE = @IN_INPUT_MAN AND ROLE_ID IN(SELECT ROLE_ID FROM TROLE WHERE FLAG_ENCRYPTION = 0))
        SELECT @V_FLAG_ENCRYPTION = 2
    ELSE
		SELECT @V_FLAG_ENCRYPTION = 1
    --加密显示位数
    IF EXISTS(SELECT 1 FROM TDICTPARAM WHERE TYPE_VALUE = '800701')
        SELECT @V_ENCRYPTION_SIZE = CAST(TYPE_CONTENT AS INT) FROM TDICTPARAM WHERE TYPE_VALUE = '800701'
    ELSE
        SET  @V_ENCRYPTION_SIZE = 4
		
	--导出时，做日志记录	
	IF @IN_EXPORT_FLAG=1 --客户信息导出
		INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
			VALUES('20301','客户信息导出',@IN_INPUT_MAN,'客户信息导出')
	ELSE IF @IN_EXPORT_FLAG=2 --客户手机
	    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
			VALUES('20301','客户信息导出',@IN_INPUT_MAN,'客户手机导出')
	ELSE IF @IN_EXPORT_FLAG=3 --客户通讯录
	    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
			VALUES('20301','客户信息导出',@IN_INPUT_MAN,'客户通讯录导出')
			
    SELECT @V_SERVICE_NAME = SERVICE_MAN FROM TCustomers WHERE CUST_ID = @IN_CUST_ID
    	--判断是否显示真实信息
	DECLARE @V_IS_SHOW_REALPHONE NVARCHAR(50)
	SELECT @V_IS_SHOW_REALPHONE=A.CTRL_VALUE FROM TSYSTEM_CTRL A WHERE A.CTRL_TYPE='IS_SHOW_REALPHONE'
	IF ISNULL(@V_IS_SHOW_REALPHONE,'0') = '1' 
	BEGIN
		IF @IN_QUERY_FLAG = 1
		BEGIN
			SELECT  CUST_ID,CUST_NO,CUST_NAME,BIRTHDAY,AGE,
					SEX,SEX_NAME,CUST_TYPE,CUST_TYPE_NAME,WT_FLAG,WT_FLAG_NAME,
					PASSWORD,TOUCH_TYPE,TOUCH_TYPE_NAME,CUST_SOURCE,
					CUST_SOURCE_NAME,RG_TIMES,TOTAL_MONEY,CURRENT_MONEY,LAST_RG_DATE,
					INPUT_MAN,INPUT_TIME,CHECK_FLAG,CHECK_MAN,MODI_MAN,MODI_TIME,
					CANCEL_MAN,CANCEL_TIME,STATUS,STATUS_NAME,SUMMARY,LEGAL_MAN,
					REGISTRATION_ID,LIST_ID,PRINT_DEPLOY_BILL,PRINT_POST_INFO,IS_LINK,
					SERVICE_MAN,ENT_CUST_ID,VIP_CARD_ID,VIP_DATE,HGTZR_BH,
					VOC_TYPE,VOC_TYPE_NAME,CARD_TYPE,CARD_TYPE_NAME,CONTACT_MAN,CARD_VALID_DATE,COUNTRY,JG_CUST_TYPE,
					POST_CODE,POST_CODE2,POST_ADDRESS,POST_ADDRESS2,LEGAL_ADDRESS,CARD_ID,
					--ISNULL(stuff(CARD_ID,1,len(CARD_ID)-@V_ENCRYPTION_SIZE,'******'),'****') AS CARD_ID,
					ISNULL(stuff(CUST_TEL,len(CUST_TEL)-@V_ENCRYPTION_SIZE+1,len(CUST_TEL),'******'),'****') AS CUST_TEL,
					ISNULL(stuff(MOBILE,len(MOBILE)-@V_ENCRYPTION_SIZE+1,len(MOBILE),'******'),'****') AS MOBILE,
					ISNULL(stuff(O_TEL,len(O_TEL)-@V_ENCRYPTION_SIZE+1,len(O_TEL),'******'),'****') AS O_TEL,
					ISNULL(stuff(H_TEL,len(H_TEL)-@V_ENCRYPTION_SIZE+1,len(H_TEL),'******'),'****') AS H_TEL,
					ISNULL(stuff(BP,len(BP)-@V_ENCRYPTION_SIZE+1,len(BP),'******'),'****') AS BP,
					ISNULL(stuff(FAX,len(FAX)-@V_ENCRYPTION_SIZE+1,len(FAX),'******'),'****') AS FAX,
					ISNULL(stuff(E_MAIL,1,charindex('@',E_MAIL)-1,'******'),'****') AS E_MAIL,
					MONEY_SOURCE,FACT_CONTROLLER,
					CARD_ID AS H_CARD_ID,
					CUST_TEL AS H_CUST_TEL,
					MOBILE AS H_MOBILE,
					O_TEL AS H_O_TEL,
					H_TEL AS H_H_TEL,
					BP AS H_BP,
					FAX AS H_FAX,
					E_MAIL AS H_E_MAIL
			FROM TCustomers WHERE CUST_ID = @IN_CUST_ID
		END
		ELSE
		BEGIN
			IF @V_FLAG_ACCESS = 1
			BEGIN
				IF @V_FLAG_ENCRYPTION = 0 OR @V_FLAG_ENCRYPTION = 2 --全部查看
					SELECT * ,
						CARD_ID AS H_CARD_ID,
						CUST_TEL AS H_CUST_TEL,
						MOBILE AS H_MOBILE,
						O_TEL AS H_O_TEL,
						H_TEL AS H_H_TEL,
						BP AS H_BP,
						FAX AS H_FAX,
						E_MAIL AS H_E_MAIL
					FROM TCustomers
					WHERE CUST_ID = @IN_CUST_ID
				ELSE
					--部份查看
					SELECT  CUST_ID,CUST_NO,CUST_NAME,BIRTHDAY,AGE,
							SEX,SEX_NAME,CUST_TYPE,CUST_TYPE_NAME,WT_FLAG,WT_FLAG_NAME,
							PASSWORD,TOUCH_TYPE,TOUCH_TYPE_NAME,CUST_SOURCE,
							CUST_SOURCE_NAME,RG_TIMES,TOTAL_MONEY,CURRENT_MONEY,LAST_RG_DATE,
							INPUT_MAN,INPUT_TIME,CHECK_FLAG,CHECK_MAN,MODI_MAN,MODI_TIME,
							CANCEL_MAN,CANCEL_TIME,STATUS,STATUS_NAME,SUMMARY,LEGAL_MAN,
							REGISTRATION_ID,LIST_ID,PRINT_DEPLOY_BILL,PRINT_POST_INFO,IS_LINK,
							SERVICE_MAN,ENT_CUST_ID,VIP_CARD_ID,VIP_DATE,HGTZR_BH,
							VOC_TYPE,VOC_TYPE_NAME,CARD_TYPE,CARD_TYPE_NAME,CONTACT_MAN,CARD_VALID_DATE,COUNTRY,JG_CUST_TYPE,
							POST_CODE,POST_CODE2,POST_ADDRESS,POST_ADDRESS2,LEGAL_ADDRESS,CARD_ID,
							--ISNULL(stuff(CARD_ID,1,len(CARD_ID)-@V_ENCRYPTION_SIZE,'******'),'****') AS CARD_ID,
							ISNULL(stuff(CUST_TEL,len(CUST_TEL)-@V_ENCRYPTION_SIZE+1,len(CUST_TEL),'******'),'****') AS CUST_TEL,
							ISNULL(stuff(MOBILE,len(MOBILE)-@V_ENCRYPTION_SIZE+1,len(MOBILE),'******'),'****') AS MOBILE,
							ISNULL(stuff(O_TEL,len(O_TEL)-@V_ENCRYPTION_SIZE+1,len(O_TEL),'******'),'****') AS O_TEL,
							ISNULL(stuff(H_TEL,len(H_TEL)-@V_ENCRYPTION_SIZE+1,len(H_TEL),'******'),'****') AS H_TEL,
							ISNULL(stuff(BP,len(BP)-@V_ENCRYPTION_SIZE+1,len(BP),'******'),'****') AS BP,
							ISNULL(stuff(FAX,len(FAX)-@V_ENCRYPTION_SIZE+1,len(FAX),'******'),'****') AS FAX,
							ISNULL(stuff(E_MAIL,1,charindex('@',E_MAIL)-1,'******'),'****') AS E_MAIL,
							MONEY_SOURCE,FACT_CONTROLLER,
							CARD_ID AS H_CARD_ID,
							CUST_TEL AS H_CUST_TEL,
							MOBILE AS H_MOBILE,
							O_TEL AS H_O_TEL,
							H_TEL AS H_H_TEL,
							BP AS H_BP,
							FAX AS H_FAX,
							E_MAIL AS H_E_MAIL
					FROM TCustomers WHERE CUST_ID = @IN_CUST_ID
			END
			ELSE
			BEGIN
				SELECT * ,
					CARD_ID AS H_CARD_ID,
					CUST_TEL AS H_CUST_TEL,
					MOBILE AS H_MOBILE,
					O_TEL AS H_O_TEL,
					H_TEL AS H_H_TEL,
					BP AS H_BP,
					FAX AS H_FAX,
					E_MAIL AS H_E_MAIL
				FROM TCustomers
				WHERE CUST_ID = 0
			END
		END	
	END
	ELSE
	BEGIN
		IF @IN_QUERY_FLAG = 1
		BEGIN
			SELECT  A.CUST_ID,CUST_NO,CUST_NAME,BIRTHDAY,AGE,
					SEX,SEX_NAME,CUST_TYPE,CUST_TYPE_NAME,WT_FLAG,WT_FLAG_NAME,
					PASSWORD,TOUCH_TYPE,TOUCH_TYPE_NAME,CUST_SOURCE,
					CUST_SOURCE_NAME,RG_TIMES,TOTAL_MONEY,CURRENT_MONEY,LAST_RG_DATE,
					INPUT_MAN,INPUT_TIME,CHECK_FLAG,CHECK_MAN,MODI_MAN,MODI_TIME,
					CANCEL_MAN,CANCEL_TIME,A.STATUS,STATUS_NAME,SUMMARY,LEGAL_MAN,
					REGISTRATION_ID,LIST_ID,PRINT_DEPLOY_BILL,PRINT_POST_INFO,IS_LINK,
					SERVICE_MAN,ENT_CUST_ID,VIP_CARD_ID,VIP_DATE,HGTZR_BH,
					VOC_TYPE,VOC_TYPE_NAME,CARD_TYPE,CARD_TYPE_NAME,CONTACT_MAN,CARD_VALID_DATE,COUNTRY,JG_CUST_TYPE,
					POST_CODE,POST_CODE2,POST_ADDRESS,POST_ADDRESS2,LEGAL_ADDRESS,CARD_ID,
					--ISNULL(stuff(CARD_ID,1,len(CARD_ID)-@V_ENCRYPTION_SIZE,'******'),'****') AS CARD_ID,
					'' AS CUST_TEL,
					TR.TC_TEL AS MOBILE,
					'' AS O_TEL,
					'' AS H_TEL,
					'' AS BP,
					ISNULL(stuff(FAX,len(FAX)-@V_ENCRYPTION_SIZE+1,len(FAX),'******'),'****') AS FAX,
					ISNULL(stuff(E_MAIL,1,charindex('@',E_MAIL)-1,'******'),'****') AS E_MAIL,
					MONEY_SOURCE,FACT_CONTROLLER,
					CARD_ID AS H_CARD_ID,
					'' AS H_CUST_TEL,
					TR.TC_TEL AS H_MOBILE,
					'' AS H_O_TEL,
					'' AS H_H_TEL,
					'' AS H_BP,
					FAX AS H_FAX,
					E_MAIL AS H_E_MAIL
			FROM TCustomers A
			--隐藏号码显示
			LEFT JOIN TNUMBER_RELATION TR ON TR.FK_TCUSTOMERS=CUST_ID
			WHERE CUST_ID = @IN_CUST_ID
		END
		ELSE
		BEGIN
			IF @V_FLAG_ACCESS = 1
			BEGIN
				IF @V_FLAG_ENCRYPTION = 0 OR @V_FLAG_ENCRYPTION = 2 --全部查看
					SELECT A.CUST_ID
						  ,A.CUST_NO
						  ,A.CUST_NAME
						  ,'' AS CUST_TEL
						  ,A.POST_ADDRESS
						  ,A.POST_ADDRESS2
						  ,A.POST_CODE
						  ,A.POST_CODE2
						  ,A.CARD_TYPE
						  ,A.CARD_TYPE_NAME
						  ,A.CARD_ID
						  ,A.CARD_VALID_DATE
						  ,A.COUNTRY
						  ,A.BIRTHDAY
						  ,A.AGE
						  ,A.SEX
						  ,A.SEX_NAME
						  ,'' AS O_TEL
						  ,'' AS H_TEL
						  ,TR.TC_TEL AS MOBILE
						  ,'' AS BP
						  ,A.FAX
						  ,A.E_MAIL
						  ,A.CUST_TYPE
						  ,A.CUST_TYPE_NAME
						  ,A.JG_CUST_TYPE
						  ,A.WT_FLAG
						  ,A.WT_FLAG_NAME
						  ,A.PASSWORD
						  ,A.TOUCH_TYPE
						  ,A.TOUCH_TYPE_NAME
						  ,A.CUST_SOURCE
						  ,A.CUST_SOURCE_NAME
						  ,A.RG_TIMES
						  ,A.TOTAL_MONEY
						  ,A.CURRENT_MONEY
						  ,A.LAST_RG_DATE
						  ,A.INPUT_MAN
						  ,A.INPUT_TIME
						  ,A.CHECK_FLAG
						  ,A.CHECK_MAN
						  ,A.MODI_MAN
						  ,A.MODI_TIME
						  ,A.CANCEL_MAN
						  ,A.CANCEL_TIME
						  ,A.STATUS
						  ,A.STATUS_NAME
						  ,A.SUMMARY
						  ,A.LEGAL_MAN
						  ,A.LEGAL_ADDRESS
						  ,A.REGISTRATION_ID
						  ,A.CONTACT_MAN
						  ,A.LIST_ID
						  ,A.PRINT_DEPLOY_BILL
						  ,A.PRINT_POST_INFO
						  ,A.IS_LINK
						  ,A.SERVICE_MAN
						  ,A.ENT_CUST_ID
						  ,A.VIP_CARD_ID
						  ,A.VIP_DATE
						  ,A.HGTZR_BH
						  ,A.VOC_TYPE
						  ,A.VOC_TYPE_NAME
						  ,A.GRADE_LEVEL
						  ,A.GRADE_LEVEL_NAME
						  ,A.MONEY_SOURCE
						  ,A.FACT_CONTROLLER
						  ,A.PinYinSimple
						  ,A.PinYinWhole
						  ,A.ImageIdentification
						  ,A.ReceiveServices
						  ,A.IS_DEAL
						  ,A.POTENTIAL_MONEY
						  ,A.GOV_PROV_REGIONAL
						  ,A.GOV_PROV_REGIONAL_NAME
						  ,A.GOV_REGIONAL
						  ,A.GOV_REGIONAL_NAME
						  ,A.MONEY_SOURCE_NAME
						  ,A.RECOMMENDED
						  ,A.NATION
						  ,A.NATION_NAME
						  ,A.MARITAL
						  ,A.HEALTH
						  ,A.HEALTH_NAME
						  ,A.EDUCATION
						  ,A.EDUCATION_NAME
						  ,A.POST
						  ,A.HOLDEROFANOFFICE
						  ,A.COMPANY_CHARACTER
						  ,A.COMPANY_CHARACTER_NAME
						  ,A.COMPANY_STAFF
						  ,A.BOCOM_STAFF
						  ,A.CLIENT_QUALE
						  ,A.CLIENT_QUALE_NAME
						  ,A.REGISTERED_PLACE
						  ,A.REGISTERED_CAPITAL
						  ,A.COMPANY_FAMILY
						  ,A.STATURE
						  ,A.WEIGHT
						  ,A.EMPLOYEE_NUM
						  ,A.HOLDING
						  ,A.PERSONAL_INCOME
						  ,A.HOUSEHOLD_INCOME
						  ,A.FEEDING_NUM_PEOPLE
						  ,A.MAIN_SOURCE
						  ,A.OTHER_SOURCE
						  ,A.HOUSE_POSITION
						  ,A.HOUSE_PROPERTY
						  ,A.PLAT_ENVALUATE
						  ,A.MARKET_APPRAISAL
						  ,A.VEHICLE_NUM
						  ,A.VEHICLE_MAKE
						  ,A.VEHICLE_TYPE
						  ,A.CREDIT_TYPE
						  ,A.CREDIT_TYPE_NAME
						  ,A.CREDIT_NUM
						  ,A.CREDIT_START_DATE
						  ,A.CREDIT_END_DATE
						  ,A.OTHER_INVESTMENT_STATUS
						  ,A.OTHER_INVE_STATUS_NAME
						  ,A.TYPE_PREF
						  ,A.TYPE_PREF_NAME
						  ,A.TIME_LIMIT_PREF
						  ,A.TIME_LIMIT_PREF_NAME
						  ,A.INVEST_PREF
						  ,A.INVEST_PREF_NAME
						  ,A.HOBBY_PREF
						  ,A.HOBBY_PREF_NAME
						  ,A.SERVICE_PREF
						  ,A.SERVICE_PREF_NAME
						  ,A.CONTACT_PREF
						  ,A.CONTACT_PREF_NAME
						  ,A.HEAD_OFFICE_CUST_ID
						  ,A.SPOUSE_NAME
						  ,A.SPOUSE_INFO
						  ,A.CHILDREN_NAME
						  ,A.CHILDREN_INFO
						  ,A.HOUSE_AREA
						  ,A.RISK_PREF
						  ,A.RISK_PREF_NAME
						  ,A.FREE_CASH_FLOW
						  ,A.BUREND_OF_DEBT
						  ,A.MODI_FLAG
						  ,A.COMPLETE_FLAG
						  ,A.COMPANY_UNIT
						  ,A.COMPANY_DEPART
						  ,A.COMPANY_POSITION
						  ,A.CUST_AGE_GROUP
						  ,A.CUST_AGE_GROUP_NAME
						  ,A.CUST_AUM
						  ,A.CUST_AUM_NAME
						  ,A.INVES_EXPERINCE
						  ,A.CUST_SOURCE_EXPLAIN
						  ,A.CARD_ADDRESS
						  ,A.EAST_JG_TYPE
						  ,A.EAST_JG_TYPE_NAME
						  ,A.JG_WTRLX2
						  ,A.TRUE_FLAG
						  ,A.FC_CARD_TYPE
						  ,A.FC_CARD_ID
						  ,A.FC_CARD_VALID_DATE
						  ,A.TRANS_NAME
						  ,A.TRANS_CARD_TYPE
						  ,A.TRANS_CARD_NAME
						  ,A.TRANS_CARD_NO
						  ,A.TRANS_TEL
						  ,A.TRANS_MOBILE
						  ,A.TRANS_ADDRESS
						  ,A.TRANS_POST_CODE
						  ,A.ETL_timestamp,
						CARD_ID AS H_CARD_ID,
						'' AS H_CUST_TEL,
						TR.TC_TEL AS H_MOBILE,
						'' AS H_O_TEL,
						'' AS H_H_TEL,
						'' AS H_BP,
						FAX AS H_FAX,
						E_MAIL AS H_E_MAIL
					FROM TCustomers A
					--隐藏号码显示
					LEFT JOIN TNUMBER_RELATION TR ON TR.FK_TCUSTOMERS=CUST_ID
					WHERE CUST_ID = @IN_CUST_ID
				ELSE
					--部份查看
					SELECT  A.CUST_ID,CUST_NO,CUST_NAME,BIRTHDAY,AGE,
							SEX,SEX_NAME,CUST_TYPE,CUST_TYPE_NAME,WT_FLAG,WT_FLAG_NAME,
							PASSWORD,TOUCH_TYPE,TOUCH_TYPE_NAME,CUST_SOURCE,
							CUST_SOURCE_NAME,RG_TIMES,TOTAL_MONEY,CURRENT_MONEY,LAST_RG_DATE,
							INPUT_MAN,INPUT_TIME,CHECK_FLAG,CHECK_MAN,MODI_MAN,MODI_TIME,
							CANCEL_MAN,CANCEL_TIME,A.STATUS,STATUS_NAME,SUMMARY,LEGAL_MAN,
							REGISTRATION_ID,LIST_ID,PRINT_DEPLOY_BILL,PRINT_POST_INFO,IS_LINK,
							SERVICE_MAN,ENT_CUST_ID,VIP_CARD_ID,VIP_DATE,HGTZR_BH,
							VOC_TYPE,VOC_TYPE_NAME,CARD_TYPE,CARD_TYPE_NAME,CONTACT_MAN,CARD_VALID_DATE,COUNTRY,JG_CUST_TYPE,
							POST_CODE,POST_CODE2,POST_ADDRESS,POST_ADDRESS2,LEGAL_ADDRESS,CARD_ID,
							--ISNULL(stuff(CARD_ID,1,len(CARD_ID)-@V_ENCRYPTION_SIZE,'******'),'****') AS CARD_ID,
							'' AS CUST_TEL,
							TR.TC_TEL AS MOBILE,
							'' AS O_TEL,
							'' AS H_TEL,
							'' AS BP,
							ISNULL(stuff(FAX,len(FAX)-@V_ENCRYPTION_SIZE+1,len(FAX),'******'),'****') AS FAX,
							ISNULL(stuff(E_MAIL,1,charindex('@',E_MAIL)-1,'******'),'****') AS E_MAIL,
							MONEY_SOURCE,FACT_CONTROLLER,
							CARD_ID AS H_CARD_ID,
							'' AS H_CUST_TEL,
							TR.TC_TEL AS H_MOBILE,
							'' AS H_O_TEL,
							'' AS H_H_TEL,
							'' AS H_BP,
							FAX AS H_FAX,
							E_MAIL AS H_E_MAIL
					FROM TCustomers A
					--隐藏号码显示
					LEFT JOIN TNUMBER_RELATION TR ON TR.FK_TCUSTOMERS=CUST_ID					
					WHERE CUST_ID = @IN_CUST_ID
			END
			ELSE
			BEGIN
				SELECT A.CUST_ID
					  ,A.CUST_NO
					  ,A.CUST_NAME
					  ,'' AS CUST_TEL
					  ,A.POST_ADDRESS
					  ,A.POST_ADDRESS2
					  ,A.POST_CODE
					  ,A.POST_CODE2
					  ,A.CARD_TYPE
					  ,A.CARD_TYPE_NAME
					  ,A.CARD_ID
					  ,A.CARD_VALID_DATE
					  ,A.COUNTRY
					  ,A.BIRTHDAY
					  ,A.AGE
					  ,A.SEX
					  ,A.SEX_NAME
					  ,'' AS O_TEL
					  ,'' AS H_TEL
					  ,TR.TC_TEL AS MOBILE
					  ,'' AS BP
					  ,A.FAX
					  ,A.E_MAIL
					  ,A.CUST_TYPE
					  ,A.CUST_TYPE_NAME
					  ,A.JG_CUST_TYPE
					  ,A.WT_FLAG
					  ,A.WT_FLAG_NAME
					  ,A.PASSWORD
					  ,A.TOUCH_TYPE
					  ,A.TOUCH_TYPE_NAME
					  ,A.CUST_SOURCE
					  ,A.CUST_SOURCE_NAME
					  ,A.RG_TIMES
					  ,A.TOTAL_MONEY
					  ,A.CURRENT_MONEY
					  ,A.LAST_RG_DATE
					  ,A.INPUT_MAN
					  ,A.INPUT_TIME
					  ,A.CHECK_FLAG
					  ,A.CHECK_MAN
					  ,A.MODI_MAN
					  ,A.MODI_TIME
					  ,A.CANCEL_MAN
					  ,A.CANCEL_TIME
					  ,A.STATUS
					  ,A.STATUS_NAME
					  ,A.SUMMARY
					  ,A.LEGAL_MAN
					  ,A.LEGAL_ADDRESS
					  ,A.REGISTRATION_ID
					  ,A.CONTACT_MAN
					  ,A.LIST_ID
					  ,A.PRINT_DEPLOY_BILL
					  ,A.PRINT_POST_INFO
					  ,A.IS_LINK
					  ,A.SERVICE_MAN
					  ,A.ENT_CUST_ID
					  ,A.VIP_CARD_ID
					  ,A.VIP_DATE
					  ,A.HGTZR_BH
					  ,A.VOC_TYPE
					  ,A.VOC_TYPE_NAME
					  ,A.GRADE_LEVEL
					  ,A.GRADE_LEVEL_NAME
					  ,A.MONEY_SOURCE
					  ,A.FACT_CONTROLLER
					  ,A.PinYinSimple
					  ,A.PinYinWhole
					  ,A.ImageIdentification
					  ,A.ReceiveServices
					  ,A.IS_DEAL
					  ,A.POTENTIAL_MONEY
					  ,A.GOV_PROV_REGIONAL
					  ,A.GOV_PROV_REGIONAL_NAME
					  ,A.GOV_REGIONAL
					  ,A.GOV_REGIONAL_NAME
					  ,A.MONEY_SOURCE_NAME
					  ,A.RECOMMENDED
					  ,A.NATION
					  ,A.NATION_NAME
					  ,A.MARITAL
					  ,A.HEALTH
					  ,A.HEALTH_NAME
					  ,A.EDUCATION
					  ,A.EDUCATION_NAME
					  ,A.POST
					  ,A.HOLDEROFANOFFICE
					  ,A.COMPANY_CHARACTER
					  ,A.COMPANY_CHARACTER_NAME
					  ,A.COMPANY_STAFF
					  ,A.BOCOM_STAFF
					  ,A.CLIENT_QUALE
					  ,A.CLIENT_QUALE_NAME
					  ,A.REGISTERED_PLACE
					  ,A.REGISTERED_CAPITAL
					  ,A.COMPANY_FAMILY
					  ,A.STATURE
					  ,A.WEIGHT
					  ,A.EMPLOYEE_NUM
					  ,A.HOLDING
					  ,A.PERSONAL_INCOME
					  ,A.HOUSEHOLD_INCOME
					  ,A.FEEDING_NUM_PEOPLE
					  ,A.MAIN_SOURCE
					  ,A.OTHER_SOURCE
					  ,A.HOUSE_POSITION
					  ,A.HOUSE_PROPERTY
					  ,A.PLAT_ENVALUATE
					  ,A.MARKET_APPRAISAL
					  ,A.VEHICLE_NUM
					  ,A.VEHICLE_MAKE
					  ,A.VEHICLE_TYPE
					  ,A.CREDIT_TYPE
					  ,A.CREDIT_TYPE_NAME
					  ,A.CREDIT_NUM
					  ,A.CREDIT_START_DATE
					  ,A.CREDIT_END_DATE
					  ,A.OTHER_INVESTMENT_STATUS
					  ,A.OTHER_INVE_STATUS_NAME
					  ,A.TYPE_PREF
					  ,A.TYPE_PREF_NAME
					  ,A.TIME_LIMIT_PREF
					  ,A.TIME_LIMIT_PREF_NAME
					  ,A.INVEST_PREF
					  ,A.INVEST_PREF_NAME
					  ,A.HOBBY_PREF
					  ,A.HOBBY_PREF_NAME
					  ,A.SERVICE_PREF
					  ,A.SERVICE_PREF_NAME
					  ,A.CONTACT_PREF
					  ,A.CONTACT_PREF_NAME
					  ,A.HEAD_OFFICE_CUST_ID
					  ,A.SPOUSE_NAME
					  ,A.SPOUSE_INFO
					  ,A.CHILDREN_NAME
					  ,A.CHILDREN_INFO
					  ,A.HOUSE_AREA
					  ,A.RISK_PREF
					  ,A.RISK_PREF_NAME
					  ,A.FREE_CASH_FLOW
					  ,A.BUREND_OF_DEBT
					  ,A.MODI_FLAG
					  ,A.COMPLETE_FLAG
					  ,A.COMPANY_UNIT
					  ,A.COMPANY_DEPART
					  ,A.COMPANY_POSITION
					  ,A.CUST_AGE_GROUP
					  ,A.CUST_AGE_GROUP_NAME
					  ,A.CUST_AUM
					  ,A.CUST_AUM_NAME
					  ,A.INVES_EXPERINCE
					  ,A.CUST_SOURCE_EXPLAIN
					  ,A.CARD_ADDRESS
					  ,A.EAST_JG_TYPE
					  ,A.EAST_JG_TYPE_NAME
					  ,A.JG_WTRLX2
					  ,A.TRUE_FLAG
					  ,A.FC_CARD_TYPE
					  ,A.FC_CARD_ID
					  ,A.FC_CARD_VALID_DATE
					  ,A.TRANS_NAME
					  ,A.TRANS_CARD_TYPE
					  ,A.TRANS_CARD_NAME
					  ,A.TRANS_CARD_NO
					  ,A.TRANS_TEL
					  ,A.TRANS_MOBILE
					  ,A.TRANS_ADDRESS
					  ,A.TRANS_POST_CODE
					  ,A.ETL_timestamp,
					CARD_ID AS H_CARD_ID,
					'' AS H_CUST_TEL,
					TR.TC_TEL AS H_MOBILE,
					'' AS H_O_TEL,
					'' AS H_H_TEL,
					'' AS H_BP,
					FAX AS H_FAX,
					E_MAIL AS H_E_MAIL
				FROM TCustomers A
				--隐藏号码显示
				LEFT JOIN TNUMBER_RELATION TR ON TR.FK_TCUSTOMERS=CUST_ID				
				WHERE CUST_ID = 0
			END
		END	
	END
	
GO