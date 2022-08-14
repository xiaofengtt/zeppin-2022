﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
-- PROCEDURE SP2_QUERY_TPRECONTRACT_PAGE
ALTER PROCEDURE SP2_QUERY_TPRECONTRACT_PAGE
						@IN_SERIAL_NO       INT,                    --序号
                        @IN_PREPRODUCT_ID   INT,                    --预发行产品ID
                        @IN_PREPRODUCT_NAME NVARCHAR(200),          --预发行产品名称
                        @IN_CUST_NAME       NVARCHAR(100),          --客户名称
                        @IN_CUST_ID         INT,                    --客户ID
                        @IN_TEAM_ID         INT,                    --团队ID
                        @IN_DATE1           INT ,                   --时间区间
                        @IN_DATE2           INT,                    --
                        @IN_MONEY1          DEC(16,3),              --金额区间
                        @IN_MONEY2          DEC(16,3),              --
                        @IN_PRE_STATUS      NVARCHAR(10),           --预约状态(1113)
                        @IN_PRE_LEVEL       NVARCHAR(10),           --预约类别(1182)用于区分预约的级别
                        @IN_PRE_TYPE        NVARCHAR(20),           --预约方式
                        @IN_PRE_CODE        NVARCHAR(20),           --预约编号
                        @IN_PAGE_INDEX      INT,                    --页码
                        @IN_PAGE_SIZE       INT,                    --页宽
                        @IN_INPUT_MAN       INT                     --操作员
                            
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_RECORD_TOTAL INT
    SET @IN_SERIAL_NO = ISNULL(@IN_SERIAL_NO,0)
    SET @IN_PREPRODUCT_ID = ISNULL(@IN_PREPRODUCT_ID,0)
    SET @IN_PREPRODUCT_NAME = ISNULL(@IN_PREPRODUCT_NAME,'')
    SET @IN_CUST_NAME = ISNULL(@IN_CUST_NAME,'')
    SET @IN_TEAM_ID = ISNULL(@IN_TEAM_ID,0)
    IF @IN_DATE1 IS NULL SET @IN_DATE1 = 0
    IF @IN_DATE2 IS NULL OR @IN_DATE2 = 0 SET @IN_DATE2 = 20991231
    IF @IN_MONEY1 IS NULL SET @IN_MONEY1 = 0
    IF @IN_MONEY2 IS NULL OR @IN_MONEY2 = 0 SET @IN_MONEY2 = 999999999999.99
    IF @IN_PRE_STATUS IS NULL SET @IN_PRE_STATUS = ''

    DECLARE @V_IS_FLAG INT,@V_TEAM_ID INT
	SELECT @V_TEAM_ID = TEAM_ID FROM TManagerTeamMembers WHERE MANAGERID = @IN_INPUT_MAN
	DECLARE @V_PRODUCT_ID INT
	SELECT @V_PRODUCT_ID=BIND_PRODUCT_ID FROM TPREPRODUCT WHERE PREPRODUCT_ID=@IN_PREPRODUCT_ID
	
	SELECT @V_IS_FLAG = 0
    --能够访问所有预约信息
    IF EXISTS(SELECT 1 FROM INTRUST..TOPRIGHT WHERE OP_CODE = @IN_INPUT_MAN AND MENU_ID ='20409' AND FUNC_ID = 2049905)
        SELECT @V_IS_FLAG = 1

    DECLARE @V_TEMPCUST2 TABLE(CUST_ID INT) --根据访问权限能够访问的客户，根据访问权限 OR
    DECLARE @IN_NODE_SHARE INT, @V_QUERYCUSTOMERS INT
    SELECT @V_QUERYCUSTOMERS = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'QUERYCUSTOMERS'
    IF @V_QUERYCUSTOMERS IS NULL SET @V_QUERYCUSTOMERS = 1
    --客户经理级别树中，同节点客户经理是否共享客户
    IF EXISTS(SELECT 1 FROM TSYSCONTROL WHERE FLAG_TYPE = 'MANAGER002' AND VALUE = 1)
        SET @IN_NODE_SHARE = 0  --共享
    ELSE
        SET @IN_NODE_SHARE = 1  --不共享
    --------------------------------------------------------------------
    DECLARE @V_MANAGER_IDS TABLE(MANAGERID INT, MANAGERNAME NVARCHAR(60))
    --处理客户经理同级共享(共享给当前操作员的源客户经理)，共享时，同节点及下级节点的客户经理所辖客户，由于也具有访问权限，故一起共享了。故对经理树的处理放在下面
    INSERT INTO @V_MANAGER_IDS(MANAGERID)
        SELECT SourceManagerID FROM TAuthorizationShare WHERE ShareType = 1 AND Status = 1 AND ManagerID = @IN_INPUT_MAN
    --从客户经理树取当前操作员所辖客户经理,再取这些客户经理的客户
    INSERT INTO @V_MANAGER_IDS
        --1.根据节点主经理来判断当前操作员
        --所辖节点的主客户经理
        SELECT A.MANAGERID,A.MANAGERNAME FROM TCustManagerTree A, TCustManagerTree B
        WHERE (A.LEFT_ID BETWEEN B.LEFT_ID+@IN_NODE_SHARE AND B.RIGHT_ID) AND A.MANAGERID <> 0 AND A.MANAGERID IS NOT NULL
            AND (B.MANAGERID = @IN_INPUT_MAN OR EXISTS(SELECT 1 FROM @V_MANAGER_IDS Z WHERE B.MANAGERID = Z.MANAGERID) )
        --所辖节点的成员客户经理
        UNION ALL
        SELECT C.MANAGERID,C.MANAGERNAME FROM TCustManagerTree A, TCustManagerTree B, TCustManagerTreeMembers C
        WHERE (A.LEFT_ID BETWEEN B.LEFT_ID+@IN_NODE_SHARE AND B.RIGHT_ID) AND A.SERIAL_NO = C.TREE_ID
            AND (B.MANAGERID = @IN_INPUT_MAN OR EXISTS(SELECT 1 FROM @V_MANAGER_IDS Z WHERE B.MANAGERID = Z.MANAGERID) )
        --2.根据节点成员经理来判断当前操作员
        --所辖节点的主客户经理
        UNION ALL
        SELECT D.MANAGERID,D.MANAGERNAME FROM TCustManagerTreeMembers B, TCustManagerTree C, TCustManagerTree D
        WHERE B.TREE_ID = C.SERIAL_NO AND (D.LEFT_ID BETWEEN C.LEFT_ID+@IN_NODE_SHARE AND C.RIGHT_ID)
            AND (B.MANAGERID = @IN_INPUT_MAN OR EXISTS(SELECT 1 FROM @V_MANAGER_IDS Z WHERE B.MANAGERID = Z.MANAGERID) )
        --所辖节点的成员客户经理
        UNION ALL
        SELECT A.MANAGERID,A.MANAGERNAME FROM TCustManagerTreeMembers B, TCustManagerTree C, TCustManagerTree D, TCustManagerTreeMembers A
        WHERE B.TREE_ID = C.SERIAL_NO AND (D.LEFT_ID BETWEEN C.LEFT_ID+@IN_NODE_SHARE AND C.RIGHT_ID) AND A.TREE_ID = D.SERIAL_NO
            AND (B.MANAGERID = @IN_INPUT_MAN OR EXISTS(SELECT 1 FROM @V_MANAGER_IDS Z WHERE B.MANAGERID = Z.MANAGERID) )
    --处理授权给下级的(当前操作员的)客户授权集，注：被授权的客户没有级联共享给同级别经理
    INSERT INTO @V_TEMPCUST2
        SELECT DISTINCT A.CUST_ID FROM TAuthorizationCusts A, TAuthorizationShare B
        WHERE A.CA_ID = B.CA_ID AND B.ShareType = 2 AND B.Status = 1 AND B.ManagerID = @IN_INPUT_MAN
            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)
    --处理快捷授权的客户ShareType=3
    INSERT INTO @V_TEMPCUST2(CUST_ID)
        SELECT CUST_ID FROM TAuthorizationShare A WHERE ShareType = 3 AND Status = 1 AND (GETDATE() BETWEEN ISNULL(A.START_TIME,GETDATE()) AND ISNULL(A.INVALID_TIME,GETDATE()+36000)) AND ManagerID = @IN_INPUT_MAN
            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)
        UNION ALL
        SELECT B.CUST_ID FROM TAuthorizationShare A, TCustomers B
        WHERE A.SourceManagerID = B.SERVICE_MAN AND A.ShareType = 3 AND A.Status = 1 AND A.CUST_ID = 0 AND (GETDATE() BETWEEN ISNULL(A.START_TIME,GETDATE()) AND ISNULL(A.INVALID_TIME,GETDATE()+36000)) AND A.ManagerID = @IN_INPUT_MAN
            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)
    --以上客户经理的预约客户
    INSERT INTO @V_TEMPCUST2
        SELECT DISTINCT A.CUST_ID FROM TPRECONTRACT A, TCustomers Z
        WHERE A.CUST_ID = Z.CUST_ID AND (EXISTS( SELECT 1 FROM @V_MANAGER_IDS B WHERE Z.SERVICE_MAN = B.MANAGERID)
                OR EXISTS( SELECT 1 FROM @V_MANAGER_IDS C WHERE (@V_QUERYCUSTOMERS = 1 AND A.INPUT_MAN = C.MANAGERID) ) )
            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)
    --团队领导的团队成员
    INSERT INTO @V_TEMPCUST2(CUST_ID)
		SELECT CUST_ID FROM TCustomers T WHERE EXISTS (
			SELECT MANAGERID FROM TManagerTeamMembers A WHERE EXISTS (SELECT TEAM_ID FROM TManagerTeams B WHERE A.TEAM_ID=B.TEAM_ID AND LEADER=@IN_INPUT_MAN)
				AND T.SERVICE_MAN=A.MANAGERID)
			AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE T.CUST_ID = C.CUST_ID)

    --输出
    IF @IN_SERIAL_NO > 0
    BEGIN
	--判断是否需要显示真实号码
	IF EXISTS(SELECT 1 FROM TSYSTEM_CTRL WHERE CTRL_TYPE='IS_SHOW_REALPHONE' AND CTRL_VALUE = '1') 
	BEGIN
		SELECT A.SERIAL_NO,A.PREPRODUCT_ID,A.CUST_ID,A.PRE_CODE,A.PRE_MONEY,A.RG_MONEY,A.RG_DATE,A.LINK_MAN,
					A.PRE_DATE,A.VALID_DAYS,A.END_DATE,A.PRE_TYPE,A.PRE_TYPE_NAME,A.PRE_STATUS,A.PRE_STATUS_NAME,
					A.INPUT_MAN,A.INPUT_TIME,A.SUMMARY,A.PRE_NUM,A.RG_NUM,A.EXP_REG_DATE,A.CUST_SOURCE,
					A.CUST_SOURCE_NAME,A.CHANNEL_TYPE,A.CHANNEL_FARE,A.BIND_SERIAL_NO,A.PRE_LEVEL,A.PRE_LEVEL_NAME,
					A.CHECK_FLAG,A.CHECK_MAN,A.SUB_PRODUCT_ID,A.CONTRACT_NO,A.PRODUCT_ID,A.PREPRODUCT_FLAG,
					A.ADD_QUOTA_FLAG,B.PREPRODUCT_NAME,B.BIND_PRODUCT_ID,C.CUST_NAME,C.CUST_TYPE,C.CUST_TYPE_NAME, C.H_TEL, 
					C.O_TEL, C.MOBILE, C.BP,C.CARD_ID,CONVERT(DEC(16,3),0.0) AS VALID_PRE_MONEY,
		       D.OP_NAME AS LINK_MAN_NAME, D.MOBILE AS LINK_MAN_MOBILE
		       ,A.HT_RG_MONEY /*已签约金额(为了提高响应，直接从预约表中取)*/
		       --,(SELECT RG_MONEY FROM INTRUST..TCONTRACT WHERE SERIAL_NO=(SELECT TOP 1 CONTRACT_SERIAL_NO FROM TPREMONEYDETAIL WHERE PRE_SERIAL_NO=A.SERIAL_NO AND CONTRACT_SERIAL_NO IS NOT NULL)) HT_RG_MONEY/*已签约金额(合同表中的认购金额)*/
		FROM TPRECONTRACT A LEFT JOIN TOPERATOR D ON A.LINK_MAN = D.OP_CODE
				--LEFT JOIN INTRUST..TPRECONTRACT E ON A.BIND_SERIAL_NO=E.SERIAL_NO
				LEFT JOIN TPREPRODUCT B ON A.PREPRODUCT_ID=B.PREPRODUCT_ID
				LEFT JOIN TCustomers C ON A.CUST_ID=C.CUST_ID
				WHERE A.SERIAL_NO = @IN_SERIAL_NO
	END
	ELSE
	BEGIN
		SELECT A.SERIAL_NO,A.PREPRODUCT_ID,A.CUST_ID,A.PRE_CODE,A.PRE_MONEY,A.RG_MONEY,A.RG_DATE,A.LINK_MAN,
					A.PRE_DATE,A.VALID_DAYS,A.END_DATE,A.PRE_TYPE,A.PRE_TYPE_NAME,A.PRE_STATUS,A.PRE_STATUS_NAME,
					A.INPUT_MAN,A.INPUT_TIME,A.SUMMARY,A.PRE_NUM,A.RG_NUM,A.EXP_REG_DATE,A.CUST_SOURCE,
					A.CUST_SOURCE_NAME,A.CHANNEL_TYPE,A.CHANNEL_FARE,A.BIND_SERIAL_NO,A.PRE_LEVEL,A.PRE_LEVEL_NAME,
					A.CHECK_FLAG,A.CHECK_MAN,A.SUB_PRODUCT_ID,A.CONTRACT_NO,A.PRODUCT_ID,A.PREPRODUCT_FLAG,
					A.ADD_QUOTA_FLAG,B.PREPRODUCT_NAME,B.BIND_PRODUCT_ID,C.CUST_NAME,C.CUST_TYPE,C.CUST_TYPE_NAME, '' AS H_TEL, 
					'' AS O_TEL, TR.TC_TEL AS MOBILE,  '' AS BP,C.CARD_ID,CONVERT(DEC(16,3),0.0) AS VALID_PRE_MONEY,
		       D.OP_NAME AS LINK_MAN_NAME, D.MOBILE AS LINK_MAN_MOBILE
		       ,A.HT_RG_MONEY /*已签约金额(为了提高响应，直接从预约表中取)*/
		       --,(SELECT RG_MONEY FROM INTRUST..TCONTRACT WHERE SERIAL_NO=(SELECT TOP 1 CONTRACT_SERIAL_NO FROM TPREMONEYDETAIL WHERE PRE_SERIAL_NO=A.SERIAL_NO AND CONTRACT_SERIAL_NO IS NOT NULL)) HT_RG_MONEY/*已签约金额(合同表中的认购金额)*/
		FROM TPRECONTRACT A LEFT JOIN TOPERATOR D ON A.LINK_MAN = D.OP_CODE
				--LEFT JOIN INTRUST..TPRECONTRACT E ON A.BIND_SERIAL_NO=E.SERIAL_NO
				LEFT JOIN TPREPRODUCT B ON A.PREPRODUCT_ID=B.PREPRODUCT_ID
				LEFT JOIN TCustomers C ON A.CUST_ID=C.CUST_ID
				LEFT JOIN TNUMBER_RELATION TR ON TR.FK_TCUSTOMERS = C.CUST_ID
				WHERE A.SERIAL_NO = @IN_SERIAL_NO
	END

    END
    ELSE
    BEGIN
        IF ISNULL(@IN_PAGE_INDEX,0)<=0
			SET @IN_PAGE_INDEX=1
		IF ISNULL(@IN_PAGE_SIZE,0)<=0
		BEGIN
			SELECT @IN_PAGE_SIZE=PAGESIZE FROM TOPERATOR WHERE OP_CODE=@IN_INPUT_MAN
			IF ISNULL(@IN_PAGE_SIZE,0)<=0
				SET @IN_PAGE_SIZE=20
		END
		SELECT @V_RECORD_TOTAL=COUNT(0) FROM TPRECONTRACT A LEFT JOIN TPREPRODUCT B ON (A.PREPRODUCT_ID = B.PREPRODUCT_ID)
				LEFT JOIN TCustomers C ON A.CUST_ID = C.CUST_ID
			WHERE (A.PREPRODUCT_ID = @IN_PREPRODUCT_ID OR ISNULL(@IN_PREPRODUCT_ID,0)=0 OR (A.PREPRODUCT_ID=0 AND A.PRODUCT_ID=@V_PRODUCT_ID))
                AND(ISNULL(B.PREPRODUCT_NAME,'') LIKE '%'+@IN_PREPRODUCT_NAME+'%')
                AND(C.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%')
				AND(A.LINK_MAN = @IN_INPUT_MAN OR A.INPUT_MAN = @IN_INPUT_MAN OR C.SERVICE_MAN = @IN_INPUT_MAN OR @V_IS_FLAG = 1 OR EXISTS(SELECT 1 FROM @V_TEMPCUST2 Y WHERE C.CUST_ID = Y.CUST_ID))
                AND(ISNULL(@IN_TEAM_ID,0) = 0 OR C.SERVICE_MAN IN (SELECT MANAGERID FROM TManagerTeamMembers WHERE TEAM_ID = @IN_TEAM_ID))
                AND(A.PRE_DATE BETWEEN @IN_DATE1 AND @IN_DATE2)
                AND(ISNULL(A.PRE_MONEY,0) BETWEEN @IN_MONEY1 AND @IN_MONEY2)
                AND(ISNULL(@IN_PRE_STATUS,'') = '' OR A.PRE_STATUS = @IN_PRE_STATUS)
                AND(A.PRE_LEVEL = @IN_PRE_LEVEL OR ISNULL(@IN_PRE_LEVEL,'') = '')
                AND(ISNULL(@IN_CUST_ID,0) = 0 OR A.CUST_ID = @IN_CUST_ID)
                AND (ISNULL(@IN_PRE_TYPE,'')='' OR A.PRE_TYPE=@IN_PRE_TYPE)
                AND (ISNULL(@IN_PRE_CODE,'')='' OR A.PRE_CODE LIKE '%'+@IN_PRE_CODE+'%')
	
	--判断是否需要显示真实号码
	IF EXISTS(SELECT 1 FROM TSYSTEM_CTRL WHERE CTRL_TYPE='IS_SHOW_REALPHONE' AND CTRL_VALUE = '1') 
	BEGIN
	        SELECT *,@V_RECORD_TOTAL RECORD_TOTAL FROM (
		SELECT A.SERIAL_NO,A.PREPRODUCT_ID,A.CUST_ID,A.PRE_CODE,A.PRE_MONEY,A.RG_MONEY,A.RG_DATE,A.LINK_MAN,
					A.PRE_DATE,A.VALID_DAYS,A.END_DATE,A.PRE_TYPE,A.PRE_TYPE_NAME,A.PRE_STATUS,A.PRE_STATUS_NAME,
					A.INPUT_MAN,A.INPUT_TIME,A.SUMMARY,A.PRE_NUM,A.RG_NUM,A.EXP_REG_DATE,A.CUST_SOURCE,
					A.CUST_SOURCE_NAME,A.CHANNEL_TYPE,A.CHANNEL_FARE,A.BIND_SERIAL_NO,A.PRE_LEVEL,A.PRE_LEVEL_NAME,
					A.CHECK_FLAG,A.CHECK_MAN,CASE ISNULL(A.SUB_PRODUCT_ID,0) WHEN 0 THEN B.BIND_SUB_PRODUCT_ID ELSE A.SUB_PRODUCT_ID END SUB_PRODUCT_ID,
					A.CONTRACT_NO,A.PRODUCT_ID,A.PREPRODUCT_FLAG,
					--A.ADD_QUOTA_FLAG,CASE ISNULL(E.CHECK_FLAG,0) WHEN 4 THEN 4 WHEN 5 THEN CASE A.FLOW_STATUS WHEN 6 THEN 6 ELSE 5 END WHEN 7 THEN 7 ELSE A.FLOW_STATUS END FLOW_STATUS,
					B.PREPRODUCT_NAME,B.BIND_PRODUCT_ID, C.CUST_NAME, C.CUST_TYPE, C.CUST_TYPE_NAME, C.H_TEL, C.O_TEL, C.MOBILE, BP,C.CARD_ID,
		       CASE WHEN A.PRE_STATUS IN ('111301','111302') THEN A.PRE_MONEY ELSE 0 END AS VALID_PRE_MONEY,
		       D.OP_NAME AS LINK_MAN_NAME, D.MOBILE AS LINK_MAN_MOBILE
		       ,A.HT_RG_MONEY /*已签约金额(为了提高响应，直接从预约表中取)*/
		       ,E.CheckFlag VIDEO_CHECK_FLAG
		       --,F.PRE_CODE PRE_CODE2
		      -- ,(SELECT SUM(AA.RG_MONEY) FROM INTRUST..TCONTRACT AA LEFT JOIN TPREMONEYDETAIL BB ON BB.CONTRACT_SERIAL_NO=AA.SERIAL_NO WHERE AA.CUST_ID=A.CUST_ID AND BB.CONTRACT_SERIAL_NO IS NOT NULL) HT_RG_MONEY/*已签约金额(合同表中的认购金额)*/
				   ,ROW_NUMBER() OVER(ORDER BY A.SERIAL_NO DESC) RID 
		    FROM TPRECONTRACT A LEFT JOIN TPREPRODUCT B ON (A.PREPRODUCT_ID = B.PREPRODUCT_ID)
					LEFT JOIN TCustomers C ON A.CUST_ID = C.CUST_ID
					LEFT JOIN TOPERATOR D ON C.SERVICE_MAN = D.OP_CODE
					LEFT JOIN TVideoRecording E ON E.VType=1 AND E.PreContractID=A.SERIAL_NO
					--LEFT JOIN INTRUST..TPRECONTRACT F ON A.BIND_SERIAL_NO=F.SERIAL_NO
					--LEFT JOIN #TMP_MONEYDATA E ON E.PRODUCT_ID=A.PRODUCT_ID AND E.SUB_PRODUCT_ID=A.SUB_PRODUCT_ID AND E.CUST_ID=A.CUST_ID
				WHERE (A.PREPRODUCT_ID = @IN_PREPRODUCT_ID OR ISNULL(@IN_PREPRODUCT_ID,0)=0 OR (A.PREPRODUCT_ID=0 AND A.PRODUCT_ID=@V_PRODUCT_ID))
			AND(ISNULL(B.PREPRODUCT_NAME,'') LIKE '%'+@IN_PREPRODUCT_NAME+'%')
			AND(C.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%')
					AND(A.LINK_MAN = @IN_INPUT_MAN OR A.INPUT_MAN = @IN_INPUT_MAN OR C.SERVICE_MAN = @IN_INPUT_MAN OR @V_IS_FLAG = 1 OR EXISTS(SELECT 1 FROM @V_TEMPCUST2 Y WHERE C.CUST_ID = Y.CUST_ID))
			AND(ISNULL(@IN_TEAM_ID,0) = 0 OR C.SERVICE_MAN IN (SELECT MANAGERID FROM TManagerTeamMembers WHERE TEAM_ID = @IN_TEAM_ID))
			AND(A.PRE_DATE BETWEEN @IN_DATE1 AND @IN_DATE2)
			AND(ISNULL(A.PRE_MONEY,0) BETWEEN @IN_MONEY1 AND @IN_MONEY2)
			AND(ISNULL(@IN_PRE_STATUS,'') = '' OR A.PRE_STATUS = @IN_PRE_STATUS)
			AND(A.PRE_LEVEL = @IN_PRE_LEVEL OR ISNULL(@IN_PRE_LEVEL,'') = '')
			AND(ISNULL(@IN_CUST_ID,0) = 0 OR A.CUST_ID = @IN_CUST_ID)
			AND (ISNULL(@IN_PRE_TYPE,'')='' OR A.PRE_TYPE=@IN_PRE_TYPE)
			AND (ISNULL(@IN_PRE_CODE,'')='' OR A.PRE_CODE LIKE '%'+@IN_PRE_CODE+'%')
		) M WHERE M.RID>=(@IN_PAGE_INDEX-1)*@IN_PAGE_SIZE+1
				AND M.RID<=@IN_PAGE_INDEX*@IN_PAGE_SIZE
	END
	ELSE
	BEGIN
	        SELECT *,@V_RECORD_TOTAL RECORD_TOTAL FROM (
		SELECT A.SERIAL_NO,A.PREPRODUCT_ID,A.CUST_ID,A.PRE_CODE,A.PRE_MONEY,A.RG_MONEY,A.RG_DATE,A.LINK_MAN,
					A.PRE_DATE,A.VALID_DAYS,A.END_DATE,A.PRE_TYPE,A.PRE_TYPE_NAME,A.PRE_STATUS,A.PRE_STATUS_NAME,
					A.INPUT_MAN,A.INPUT_TIME,A.SUMMARY,A.PRE_NUM,A.RG_NUM,A.EXP_REG_DATE,A.CUST_SOURCE,
					A.CUST_SOURCE_NAME,A.CHANNEL_TYPE,A.CHANNEL_FARE,A.BIND_SERIAL_NO,A.PRE_LEVEL,A.PRE_LEVEL_NAME,
					A.CHECK_FLAG,A.CHECK_MAN,CASE ISNULL(A.SUB_PRODUCT_ID,0) WHEN 0 THEN B.BIND_SUB_PRODUCT_ID ELSE A.SUB_PRODUCT_ID END SUB_PRODUCT_ID,
					A.CONTRACT_NO,A.PRODUCT_ID,A.PREPRODUCT_FLAG,
					--A.ADD_QUOTA_FLAG,CASE ISNULL(E.CHECK_FLAG,0) WHEN 4 THEN 4 WHEN 5 THEN CASE A.FLOW_STATUS WHEN 6 THEN 6 ELSE 5 END WHEN 7 THEN 7 ELSE A.FLOW_STATUS END FLOW_STATUS,
					B.PREPRODUCT_NAME,B.BIND_PRODUCT_ID, C.CUST_NAME, C.CUST_TYPE, C.CUST_TYPE_NAME, '' AS H_TEL, '' AS O_TEL, TR.TC_TEL AS MOBILE, '' AS BP,C.CARD_ID,
		       CASE WHEN A.PRE_STATUS IN ('111301','111302') THEN A.PRE_MONEY ELSE 0 END AS VALID_PRE_MONEY,
		       D.OP_NAME AS LINK_MAN_NAME, D.MOBILE AS LINK_MAN_MOBILE
		       ,A.HT_RG_MONEY /*已签约金额(为了提高响应，直接从预约表中取)*/
		       ,E.CheckFlag VIDEO_CHECK_FLAG
		       --,F.PRE_CODE PRE_CODE2
		      -- ,(SELECT SUM(AA.RG_MONEY) FROM INTRUST..TCONTRACT AA LEFT JOIN TPREMONEYDETAIL BB ON BB.CONTRACT_SERIAL_NO=AA.SERIAL_NO WHERE AA.CUST_ID=A.CUST_ID AND BB.CONTRACT_SERIAL_NO IS NOT NULL) HT_RG_MONEY/*已签约金额(合同表中的认购金额)*/
				   ,ROW_NUMBER() OVER(ORDER BY A.SERIAL_NO DESC) RID 
		    FROM TPRECONTRACT A LEFT JOIN TPREPRODUCT B ON (A.PREPRODUCT_ID = B.PREPRODUCT_ID)
					LEFT JOIN TCustomers C ON A.CUST_ID = C.CUST_ID
					LEFT JOIN TNUMBER_RELATION TR ON TR.FK_TCUSTOMERS = C.CUST_ID
					LEFT JOIN TOPERATOR D ON C.SERVICE_MAN = D.OP_CODE
					LEFT JOIN TVideoRecording E ON E.VType=1 AND E.PreContractID=A.SERIAL_NO
					--LEFT JOIN INTRUST..TPRECONTRACT F ON A.BIND_SERIAL_NO=F.SERIAL_NO
					--LEFT JOIN #TMP_MONEYDATA E ON E.PRODUCT_ID=A.PRODUCT_ID AND E.SUB_PRODUCT_ID=A.SUB_PRODUCT_ID AND E.CUST_ID=A.CUST_ID
				WHERE (A.PREPRODUCT_ID = @IN_PREPRODUCT_ID OR ISNULL(@IN_PREPRODUCT_ID,0)=0 OR (A.PREPRODUCT_ID=0 AND A.PRODUCT_ID=@V_PRODUCT_ID))
			AND(ISNULL(B.PREPRODUCT_NAME,'') LIKE '%'+@IN_PREPRODUCT_NAME+'%')
			AND(C.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%')
					AND(A.LINK_MAN = @IN_INPUT_MAN OR A.INPUT_MAN = @IN_INPUT_MAN OR C.SERVICE_MAN = @IN_INPUT_MAN OR @V_IS_FLAG = 1 OR EXISTS(SELECT 1 FROM @V_TEMPCUST2 Y WHERE C.CUST_ID = Y.CUST_ID))
			AND(ISNULL(@IN_TEAM_ID,0) = 0 OR C.SERVICE_MAN IN (SELECT MANAGERID FROM TManagerTeamMembers WHERE TEAM_ID = @IN_TEAM_ID))
			AND(A.PRE_DATE BETWEEN @IN_DATE1 AND @IN_DATE2)
			AND(ISNULL(A.PRE_MONEY,0) BETWEEN @IN_MONEY1 AND @IN_MONEY2)
			AND(ISNULL(@IN_PRE_STATUS,'') = '' OR A.PRE_STATUS = @IN_PRE_STATUS)
			AND(A.PRE_LEVEL = @IN_PRE_LEVEL OR ISNULL(@IN_PRE_LEVEL,'') = '')
			AND(ISNULL(@IN_CUST_ID,0) = 0 OR A.CUST_ID = @IN_CUST_ID)
			AND (ISNULL(@IN_PRE_TYPE,'')='' OR A.PRE_TYPE=@IN_PRE_TYPE)
			AND (ISNULL(@IN_PRE_CODE,'')='' OR A.PRE_CODE LIKE '%'+@IN_PRE_CODE+'%')
		) M WHERE M.RID>=(@IN_PAGE_INDEX-1)*@IN_PAGE_SIZE+1
				AND M.RID<=@IN_PAGE_INDEX*@IN_PAGE_SIZE
	END

    END
GO
