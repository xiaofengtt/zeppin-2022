USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCONTRACT_EXPORT
								@IN_PRODUCT_ID          INTEGER,                --产品ID
								@IN_CONTRACT_SUB_BH     NVARCHAR(50)  = '',     --合同编号查询  20080104 by wangc
								@IN_INPUT_MAN           INTEGER                 --操作员
								
WITH ENCRYPTION
AS
    --20409: The Products can be accessed. START
    DECLARE @V_SW20409 INT, @V_IS_VIEW INT
    SET @V_IS_VIEW =0
    SELECT @V_SW20409 = VALUE FROM INTRUST..TSYSCONTROL WHERE FLAG_TYPE = 'SW20409'
    --DECLARE @V_PRODUCTS TABLE(PRODUCT_ID INT)
    IF @V_SW20409 = 1
        IF EXISTS( SELECT PRODUCT_ID FROM INTRUST..TPRODUCT WHERE BOOK_CODE = 1 AND PRODUCT_ID=@IN_PRODUCT_ID
                AND(INTRUST_FLAG1 IN ( SELECT FUNC_ID-2040900 FROM INTRUST..TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID > 2040900 AND FUNC_ID <= 2040999 AND OP_CODE = @IN_INPUT_MAN )
                    OR PRODUCT_ID IN ( SELECT FUNC_ID FROM INTRUST..TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID < 2040900 AND OP_CODE = @IN_INPUT_MAN)
                    OR ADMIN_MANAGER = @IN_INPUT_MAN OR INPUT_MAN = @IN_INPUT_MAN OR ISNULL(@IN_INPUT_MAN,0)=0
                    OR ADMIN_MANAGER IN(SELECT OP_CODE FROM INTRUST..TOPCUSTRIGHT WHERE ENABLE_OP_CODE = @IN_INPUT_MAN))
            )
            SET @V_IS_VIEW=1
    --20409: The Products can be accessed. END

    DECLARE @V_IS_FLAG INT
    SELECT @V_IS_FLAG = 0

    --能够访问所有认购信息
    IF EXISTS(SELECT 1 FROM INTRUST..TOPRIGHT WHERE OP_CODE = @IN_INPUT_MAN AND MENU_ID ='20409' AND FUNC_ID = 2049906)
        SELECT @V_IS_FLAG = 1
--缴款方式	受益人	受益人类型	受益优先级别	收益级别	受益人联系方式	受益人证件名称
--受益人证件编号	受益人地址	受益人邮编	受益人法人代表	受益人固定电话	受益人手机	受益人电子邮件	新合同编号	渠道类别	渠道名称	渠道备注	省	市	客户经理	渠道合作方式
    IF @V_IS_FLAG = 1 OR @V_IS_VIEW=1
        SELECT C.PRODUCT_NAME,A.CONTRACT_SUB_BH,A.QS_DATE,A.JK_DATE,A.TO_MONEY,A.FEE_JK_TYPE,B.CUST_NAME,B.CUST_TYPE_NAME,
                B.POST_CODE,B.POST_ADDRESS,B.POST_CODE2,B.POST_ADDRESS2,B.CARD_TYPE_NAME,B.CARD_ID,B.LEGAL_MAN,B.TOUCH_TYPE_NAME,
                B.CUST_TEL,B.MOBILE,B.E_MAIL,A.BANK_NAME,A.BANK_SUB_NAME,A.BANK_ACCT,A.GAIN_ACCT, A.JK_TYPE_NAME,
                (SELECT CONTENT FROM INTRUST..TINTEGERPARAM WHERE TYPE_ID = 3003 AND TYPE_VALUE=A.PROV_FLAG) PROV_FLAG_NAME,--受益优先级别
                E.CUST_NAME BENIFITOR_NAME,E.CUST_TYPE_NAME BENIFITOR_TYPE_NAME,                
                A.PROV_LEVEL_NAME,E.TOUCH_TYPE_NAME BENIFITOR_TOUCH_TYPE_NAME,E.CARD_TYPE_NAME BENIFITOR_CARD_TYPE_NAME,
                E.CARD_ID BENIFITOR_CARD_ID,E.POST_ADDRESS BENIFITOR_POST_ADDRESS,E.POST_CODE BENIFITOR_POST_CODE,
                E.LEGAL_MAN BENIFITOR_LEGAL_MAN,E.CUST_TEL BENIFITOR_CUST_TEL,E.MOBILE BENIFITOR_MOBILE,
                E.E_MAIL BENIFITOR_E_MAIL,'' NEW_SUB_CONTRACT_BH,
                (SELECT TYPE_CONTENT FROM INTRUST..TDICTPARAM WHERE TYPE_ID='5500' AND TYPE_VALUE=A.CHANNEL_TYPE) CHANNEL_TYPE_NAME,--渠道类别
                F.CHANNEL_NAME,A.CHANNEL_MEMO,B.GOV_PROV_REGIONAL_NAME,B.GOV_REGIONAL_NAME,
                (SELECT OP_NAME FROM TOPERATOR WHERE OP_CODE=B.SERVICE_MAN) SERVICE_MAN_NAME,--客户经理
                A.CHANNEL_COOPERTYPE_NAME
                ,A.VALID_PERIOD, A.PERIOD_UNIT, A.ENTITY_NAME, A.ENTITY_TYPE_NAME
            FROM INTRUST..TCONTRACT A 
                 LEFT JOIN INTRUST..TCHANNEL H ON A.CHANNEL_ID = H.CHANNEL_ID AND A.CHANNEL_TYPE = H.CHANNEL_TYPE
                 LEFT JOIN INTRUST..TCUSTOMERINFO B ON A.CUST_ID = B.CUST_ID
                 LEFT JOIN INTRUST..TPRODUCT C ON A.PRODUCT_ID = C.PRODUCT_ID
                 LEFT JOIN INTRUST..TBENIFITOR D ON D.CONTRACT_BH=A.CONTRACT_BH AND D.PRODUCT_ID=A.PRODUCT_ID AND D.LIST_ID=1
                 LEFT JOIN INTRUST..TCUSTOMERINFO E ON E.CUST_ID = D.CUST_ID
                 LEFT JOIN INTRUST..TCHANNEL F ON F.CHANNEL_ID=A.CHANNEL_ID
            WHERE A.HT_STATUS <>'120104' --已中止
                AND A.HT_STATUS <>'120105' --已到期结算
                AND A.CHECK_FLAG = 2 --已审核
                AND A.CHANNEL_ID>0 --渠道合作方的合同
                AND (A.CONTRACT_SUB_BH LIKE '%'+@IN_CONTRACT_SUB_BH+'%' OR ISNULL(@IN_CONTRACT_SUB_BH,'') = '')
                AND A.PRODUCT_ID = @IN_PRODUCT_ID            
            ORDER BY A.PRODUCT_ID,A.SUB_PRODUCT_ID,A.CONTRACT_SUB_BH
    ELSE
        SELECT A.*
            FROM INTRUST..TCONTRACT A
            WHERE 1=0
GO
