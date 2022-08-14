USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ISORNOT_AGENTCUST_BJITIC 
                               @IN_CUSTNAME     NVARCHAR(80),   -- 客户名称
                               @IN_CustType     INT,            --客户类型:0机构;1个人
                               @IN_CardType     NVARCHAR(2),    --证件类型（传入编号）个人证件类型：0身份证1中国护照2军官证3士兵证4回乡证5户口本6外籍护照7其他8文职9警官A台胞证
                                                                                    --机构证件类型：0技术监督局代码1营业执照2行政机关3社会团体4军队5武警6下属机构7基金会8其他机构
                               @IN_cardNo       NVARCHAR(80)    --证件号码

WITH ENCRYPTION
AS
    DECLARE @V_CardType NVARCHAR(20),@V_CustType INT,@V_CUST_ID INT
    SET @V_CardType = ''
    --SELECT * FROM TDICTPARAM WHERE TYPE_NAME LIKE '%证件类型%'
    --把证件类型转换成CRM里的证件类型（TDICTPARAM）
    IF @IN_CustType=1 --1个人
    BEGIN
		IF @IN_CardType='0' SET @V_CardType='110801'       --0身份证
		ELSE IF @IN_CardType='1' SET @V_CardType='110802'  --1中国护照
		ELSE IF @IN_CardType='2' SET @V_CardType='110804'  --2军官证
		ELSE IF @IN_CardType='3' SET @V_CardType='110811'  --3士兵证
		ELSE IF @IN_CardType='4' SET @V_CardType='110812'  --4回乡证
		ELSE IF @IN_CardType='5' SET @V_CardType='110813'  --5户口本
		ELSE IF @IN_CardType='6' SET @V_CardType='110814'  --6外籍护照
		ELSE IF @IN_CardType='7' SET @V_CardType='110899'  --7其他
		ELSE IF @IN_CardType='8' SET @V_CardType='110815'  --8文职
		ELSE IF @IN_CardType='9' SET @V_CardType='110816'  --9警官
		ELSE IF @IN_CardType='A' SET @V_CardType='110817'  --A台胞证
    END
    ELSE IF @IN_CustType=0 --0机构
    BEGIN
		IF @IN_CardType='0' SET @V_CardType='210811'       --0技术监督局代码
		ELSE IF @IN_CardType='1' SET @V_CardType='210801'  --1营业执照
		ELSE IF @IN_CardType='2' SET @V_CardType='210812'  --2行政机关
		ELSE IF @IN_CardType='3' SET @V_CardType='210813'  --3社会团体
		ELSE IF @IN_CardType='4' SET @V_CardType='210814'  --4军队
		ELSE IF @IN_CardType='5' SET @V_CardType='210815'  --5武警
		ELSE IF @IN_CardType='6' SET @V_CardType='210816'  --6下属机构
		ELSE IF @IN_CardType='7' SET @V_CardType='210817'  --7基金会
		ELSE IF @IN_CardType='8' SET @V_CardType='210818'  --8其他机构
	END
	--把客户类型转成信托业务系统里的客户类型
	IF @IN_CustType=1 SET @V_CustType=1 --个人
	ELSE SET @V_CustType=2 --机构
	
	SELECT @V_CUST_ID = CUST_ID FROM INTRUST..TCUSTOMERINFO WHERE CUST_TYPE=@V_CustType AND CARD_TYPE=@V_CardType AND CARD_ID=@IN_cardNo AND CUST_NAME=@IN_CUSTNAME
	IF @@ROWCOUNT=0
	BEGIN
		SELECT '' RESULT,'不存在此客户' REASON
		RETURN
	END
	--[北京信托直销客户]认定条件：2008.01.01之后认购的客户；
	--[非北京信托直销客户]认定条件：①2008.01.01之前认购的客户（休眠客户）并且②无有效预约记录并且无认购记录的客户。
    IF EXISTS(SELECT * FROM INTRUST..TCONTRACT --北京信托直销客户
		WHERE (QS_DATE>=20080101 AND CUST_ID=@V_CUST_ID)
			OR EXISTS (SELECT * FROM INTRUST..TPRECONTRACT WHERE CUST_ID=@V_CUST_ID)
	)
	BEGIN
		SELECT '1' RESULT,'' REASON
		RETURN
	END
	SELECT '0' RESULT,'' REASON
GO
