USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE SP_QUERY_TCustomersExt_C @IN_CUST_ID             INT,
                                        @IN_CUST_NO             NVARCHAR(8),
                                        @IN_CUST_NAME           NVARCHAR(100),
                                        @IN_CUST_SOURCE         NVARCHAR(10),
                                        @IN_CARD_TYPE           NVARCHAR(10),
                                        @IN_CARD_ID             NVARCHAR(20),
                                        @IN_TOUCH_TYPE          NVARCHAR(10),          --联系方式(1109)
                                        @IN_MIN_TIMES           INT,                   --认购次数查询下限
                                        @IN_MAX_TIMES           INT,                   --认购次数查询上限
                                        @IN_INPUT_MAN           INT,                   --操作员
                                        @IN_TEL                 NVARCHAR(20)   = NULL, --客户电话，包括手机、办公电话或者家庭电话
                                        @IN_ADDRESS             NVARCHAR(60)   = NULL, --客户邮寄地址
                                        @IN_CUST_TYPE           INT            = NULL, --客户类型:1个人2机构
                                        @IN_PRODUCT_ID          INT            = NULL, --产品ID
                                        @IN_FAMILY_NAME         NVARCHAR(40)   = NULL, --家庭名称
                                        @IN_ONLYEMAIL           INT = NULL,             --是否只返回有EMAIL的记录 1-是 0-否
                                        @IN_CUST_LEVEL          NVARCHAR(10)   = NULL,  --客户级别(1111)
                                        @IN_MIN_TOTAL_MONEY     MONEY          = NULL,  --认购金额下限
                                        @IN_MAX_TOTAL_MONEY     MONEY          = NULL,  --认购金额上限
                                        @IN_ONLY_THISPRODUCT    INT            = NULL,  --(本参数未使用)
                                        @IN_ORDERBY             NVARCHAR(100)  = NULL,  --结果集的排序字段名
                                        @IN_BIRTHDAY            INT            = NULL,  --生日下限（输入格式yyyymmdd，过程内部处理掉yyyy，仅mmdd起作用）
                                        @IN_PRINT_DEPLOY_BILL   INT            = NULL,  --是否打印客户对账单(收益情况)
                                        @IN_IS_LINK             INT            = NULL,  --是否为关联方:1是2否
                                        @IN_PROV_LEVEL          NVARCHAR(10)   = NULL,  --受益优先级别(1204)
                                        @IN_BEN_AMOUNT_MIN      DECIMAL(16,3)  = NULL,  --当前受益份额下限
                                        @IN_BEN_AMOUNT_MAX      DECIMAL(16,3)  = NULL,  --当前受益份额上限
                                        @IN_VIP_CARD_ID         NVARCHAR(20)   = NULL,  --VIP卡编号
                                        @IN_HGTZR_BH            NVARCHAR(20)   = NULL,  --合格投资人编号
                                        @IN_WTR_FLAG            INTEGER        = 0,     --委托人标志：0 -- 委托人和受益人 1 受益人 2 委托人    针对只查询单一产品情况
                                        @IN_CLASSES             NVARCHAR(500)  = NULL,  --各客户级别的值拼成的字符串，半角逗号分隔。例如：1001,1102,2001
                                        @IN_IS_DEAL             INT            = 0,
                                        @IN_GroupID             INT            = 0,     --客户群组
                                        @IN_MONEY_SOURCE_NAME   NVARCHAR(1000) = NULL,  --资金来源名称
                                        @IN_COUNTRY             NVARCHAR(10)   = NULL,  --客户国籍(9997)
                                        @IN_ACCOUNTMANAGER      INTEGER        = 0,     --客户经理
                                        @IN_RECOMMENDED         NVARCHAR(60)   = NULL,  --推荐人
                                        @IN_PRODUCT_TO          NVARCHAR(30)   = NULL,  --投向
                                        @IN_START_AGE           INT            = NULL,  --年龄下限
                                        @IN_END_AGE             INT            = NULL,  --年龄上限
                                        @IN_CELL_ID             INT            = 0,     --产品单元ID
                                        @IN_BIRTHDAY_END        INT            = NULL,  --生日上限（输入格式yyyymmdd，过程内部处理掉yyyy，仅mmdd起作用）
                                        @IN_RG_BEGIN_DATE       INT            = NULL,  --认购日期下限
                                        @IN_RG_END_DATE         INT            = NULL,  --认购日期上限
                                        @IN_START_CURRENT_SOURCE INT           = NULL,  ---从 客户得分
                                        @IN_END_CURRENT_SOURCE  INT            = NULL,  ---到 客户得分
                                        @IN_REGIONAL_NAME       NVARCHAR(60)   = NULL,  --地区
                                        @IN_CHANNEL_NAME        NVARCHAR(60)   = NULL,  --渠道名称
										@IN_CITY_NAME			NVARCHAR(60)   = NULL,	--销售区域
										@IN_SUB_PRODUCT_ID		INT			   = 0,		--子产品ID
										@IN_INVEST_FIELD        VARCHAR(60)    = NULL,  --CRM投资领域
										@IN_TRUE_FLAG           INT            = 0,     --客户的真实性核查：1未核查，2真实，3待核查(已经核实过但不确定结果)
										@IN_EXPORT_FLAG         INT            = 0,     --导出标记：0非导出查询 1导出客户信息 2导出手机 3导出通讯录 101客户信息更新查询 102客户移交查询
										@IN_EXPORT_SUMMARY      NVARCHAR(900)  = '',    --导出备注
										@IN_IP                  NVARCHAR(20)   = '',    --查询人IP地址
										@IN_MAC                 NVARCHAR(20)   = '',    --查询人MAC地址
										@IN_SEX                 INT            = 0,      --客户性别:1男2女
										@IN_CC_STATUS           NVARCHAR(20)   = ''      --联系方式修改状态
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    IF @IN_BIRTHDAY_END = 0 SET @IN_BIRTHDAY_END = NULL
    IF @IN_RG_END_DATE = 0 SET @IN_RG_END_DATE = NULL
    IF @IN_END_AGE = 0 SET @IN_END_AGE = NULL
    DECLARE @RET INT, @IN_NODE_SHARE INT,@V_QUERYCUSTOMERS INT, @V_CUSTQZ0001 INT, @V_CCUSTMODI INT,@V_FACT_POTE_FLAG INT,@V_IS_DEAL INT,@V_ENCRYPTION_SIZE INT
    DECLARE @V_FLAG_ACCESS_ALL INT,@V_FLAG_ENCRYPTION INT, @V_SQL NVARCHAR(2000), @IN_CARD_ID18 NVARCHAR(18),@V_CONDITION_SQL NVARCHAR(600),@V_USER_ID INT
    DECLARE @V_FACT_POTENTIAL_FLAG_FZ INT,@V_INTRUST_INPUT_MAN INT
	SELECT @V_FACT_POTENTIAL_FLAG_FZ = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = 'FACT_POTENTIAL_FLAG_FZ'--方正客户信息加密条件
	SELECT @V_QUERYCUSTOMERS = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'QUERYCUSTOMERS'
	SELECT @V_USER_ID = USER_ID FROM TSYSTEMINFO
    IF @V_QUERYCUSTOMERS IS NULL SET @V_QUERYCUSTOMERS = 1
    SELECT @V_CUSTQZ0001 = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'CUSTQZ0001' --默认1
    SELECT @V_CUSTQZ0001 = ISNULL(@V_CUSTQZ0001,1)
    SELECT @V_CCUSTMODI = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'CCUSTMODI'
    SELECT @V_FACT_POTE_FLAG = VALUE FROM TSYSCONTROL WHERE  FLAG_TYPE = 'FACT_POTENTIAL_FLAG'--事实潜在客户加密条件
    SELECT @V_CCUSTMODI = ISNULL(@V_CCUSTMODI,2)

    IF @V_FACT_POTE_FLAG = 1  SET @V_IS_DEAL = 2 -- 潜在客户信息加密
    IF @V_FACT_POTE_FLAG = 2  SET @V_IS_DEAL = 1 -- 事实客户信息加密
    IF @V_FACT_POTE_FLAG = 3  SET @V_IS_DEAL = 0 -- 事实、潜在客户信息加密
    IF @V_FACT_POTE_FLAG = 4  SET @V_IS_DEAL = 3 -- 事实、潜在客户信息都不加密
	
	--加密显示位数	
	IF EXISTS(SELECT 1 FROM TDICTPARAM WHERE TYPE_VALUE = '800701')
		SELECT @V_ENCRYPTION_SIZE = CAST(TYPE_CONTENT AS INT) FROM TDICTPARAM WHERE TYPE_VALUE = '800701'
	ELSE
		SET  @V_ENCRYPTION_SIZE = 4

    IF EXISTS(SELECT * FROM TSYSCONTROL WHERE FLAG_TYPE = N'A_CARDID' AND VALUE = 1)
    BEGIN
        IF LEN(@IN_CARD_ID)=15   --15位身份证号码转18位
            SELECT @IN_CARD_ID18 = dbo.IDCARD15TO18(@IN_CARD_ID)
        ELSE IF LEN(@IN_CARD_ID)=18 --18位转成15位
            SELECT @IN_CARD_ID18 = dbo.IDCARD18TO15(@IN_CARD_ID)
    END
    DECLARE @V_TEMPCUST1 TABLE(CUST_ID INT) --根据输入条件能够访问的客户，根据查询条件 AND
    DECLARE @V_TEMPCUST2 TABLE(CUST_ID INT,AUTH_FLAG INT) --根据访问权限能够访问的客户，根据访问权限 OR
    DECLARE @V_TEMPCUST_SCORE TABLE(CUST_ID INT)--获得客户得分数据表
    DECLARE @V_PRODUCT_IDS TABLE(PRODUCT_ID INT)
    DECLARE @V_CELLALL TABLE(CELL_ID      INT,
                             CELL_CODE    NVARCHAR(10),
                             CELL_NAME    NVARCHAR(60),
                             CELL_TYPE    INT,
                             PRODUCT_ID   INT,
                             PRODUCT_CODE NVARCHAR(6),
                             PRODUCT_NAME NVARCHAR(60))
    IF @IN_CELL_ID IS NOT NULL AND @IN_CELL_ID <> 0
    BEGIN
        INSERT INTO @V_CELLALL(CELL_ID,CELL_CODE,CELL_NAME,PRODUCT_ID,PRODUCT_CODE,PRODUCT_NAME)
            SELECT SERIAL_NO,CELL_CODE,CELL_NAME,PRODUCT_ID,PRODUCT_CODE,PRODUCT_NAME FROM INTRUST..TPRODUCT_CELL
            WHERE SERIAL_NO = @IN_CELL_ID
        WHILE @@ROWCOUNT > 0
        BEGIN
            INSERT INTO @V_CELLALL(CELL_ID)
                SELECT A.SUB_CELL_ID
                FROM  INTRUST..TPRODUCT_CELL_DETAIL A, @V_CELLALL B
                WHERE A.DF_SERIAL_NO = B.CELL_ID AND A.SUB_CELL_ID NOT IN(SELECT CELL_ID FROM @V_CELLALL)
        END
        UPDATE @V_CELLALL
            SET CELL_CODE = B.CELL_CODE,
                CELL_NAME = B.CELL_NAME,
                CELL_TYPE = B.CELL_TYPE,
                PRODUCT_ID = B.PRODUCT_ID,
                PRODUCT_CODE = B.PRODUCT_CODE,
                PRODUCT_NAME = B.PRODUCT_NAME
            FROM @V_CELLALL A , INTRUST..TPRODUCT_CELL B
            WHERE A.CELL_ID = B.SERIAL_NO
        INSERT INTO @V_PRODUCT_IDS(PRODUCT_ID) SELECT PRODUCT_ID FROM @V_CELLALL WHERE CELL_TYPE = 1 GROUP BY PRODUCT_ID
    END

    SELECT @IN_CARD_ID18 = ISNULL(@IN_CARD_ID18, @IN_CARD_ID)
    SELECT @V_FLAG_ACCESS_ALL = 0 --访问全部标志
    SELECT @V_FLAG_ENCRYPTION = 0 --加密标志
    --客户经理级别树中，同节点客户经理是否共享客户
    IF EXISTS(SELECT 1 FROM TSYSCONTROL WHERE FLAG_TYPE = 'MANAGER002' AND VALUE = 1)
        SET @IN_NODE_SHARE = 0  --共享
    ELSE
        SET @IN_NODE_SHARE = 1  --不共享
    --能够访问所有客户信息
    IF EXISTS(SELECT 1 FROM TOPRIGHT WHERE OP_CODE = @IN_INPUT_MAN AND MENU_ID = N'999' AND FUNC_ID = 99903)
        SELECT @V_FLAG_ACCESS_ALL = 1
    --如果操作员的角色中存在访问所有客户信息权限的标志 则赋予能够访问所有客户信息权限
    IF EXISTS(SELECT 1 FROM TOPROLE WHERE OP_CODE = @IN_INPUT_MAN AND ROLE_ID IN(SELECT ROLE_ID FROM TROLE WHERE FLAG_ACCESS_ALL = 1))
        SELECT @V_FLAG_ACCESS_ALL = 1

    --20120822 打印角色不加密可能是为交银加的，由于ROLE_ID写死了会影响其他客户，故添加USER_ID判断
    --如果用户存在打印角色的时候 不需要加密
    IF (@V_USER_ID = 8) AND EXISTS(SELECT 1 FROM TOPROLE WHERE OP_CODE = @IN_INPUT_MAN AND ROLE_ID = 5)
    BEGIN
        SET @V_FLAG_ENCRYPTION = 2
    END
    ELSE
    BEGIN
        --如果操作员的角色中存在不保密限制的角色，则不进行保密限制，否则加密
        IF EXISTS(SELECT 1 FROM TOPROLE WHERE OP_CODE = @IN_INPUT_MAN AND ROLE_ID IN(SELECT ROLE_ID FROM TROLE WHERE FLAG_ENCRYPTION = 0))
            SELECT @V_FLAG_ENCRYPTION = 2--不加密
        ELSE
			SELECT @V_FLAG_ENCRYPTION = 1--加密
    END


    CREATE TABLE #TEMPCUST
    (
       CUST_ID                 INT,                 --客户ID
       CUST_NO                 NVARCHAR (8),        --客户编号
       CUST_NAME               NVARCHAR (100),      --客户名称
       SEX                     NVARCHAR(10),        --性别
       BIRTHDAY                INT,                 --生日
       POST_ADDRESS            NVARCHAR(90),        --联系地址
       POST_CODE               NVARCHAR(6),         --邮政编码
       CARD_ID                 NVARCHAR (40),       --证件ID
       CUST_TEL                NVARCHAR (40),       --联系电话
       E_MAIL                  NVARCHAR (60),       --E-MAIL
       O_TEL                   NVARCHAR (40),       --公司电话
       MOBILE                  NVARCHAR (100) ,     --手机
       BP                      NVARCHAR (60) ,      --BP
       STATUS_NAME             NVARCHAR (30),       --客户状态说明
       LAST_RG_DATE            INT,                 --最近一次购买时间
       CURR_TO_AMOUNT          DECIMAL(16,3),       --
       TOTAL_MONEY             DECIMAL(16, 3),     --已购买金额
       CURRENT_MONEY           DECIMAL(16, 3),     --存量金额
       BEN_AMOUNT              DECIMAL(16, 3),     --受益份额
       EXCHANGE_AMOUNT         DECIMAL(16, 3),     --转让份额
       EXCHANGE_AMOUNT_IN      DECIMAL(16, 3),     --受让份额
       BACK_AMOUNT             DECIMAL(16, 3),     --到期结算份额
       SERVICE_MAN             INT,                 --客户经理
       CUST_TYPE               INT,
       MODI_FLAG               INT,                 --1允许修改 2不允许修改
       GRADE_LEVEL             NVARCHAR(10),        --客户风险等级
       GRADE_LEVEL_NAME        NVARCHAR(30),        --客户风险等级说明
       CARD_TYPE_NAME          NVARCHAR(30),        --证件类型
       IS_DEAL                 INT DEFAULT 2,
       IS_DEAL_NAME            NVARCHAR(30),
       CUST_SOURCE             NVARCHAR(20),
       CUST_SOURCE_NAME        NVARCHAR(60),
       COUNTRY                 NVARCHAR(20),
       COUNTRY_NAME            NVARCHAR(60),
       MONEY_SOURCE            NVARCHAR(300),
       MONEY_SOURCE_NAME       NVARCHAR(1000),
       FIRST_RG_DATE           INT,
       END_AMOUNT              DECIMAL(16,3),------当前份额（受益份额-到期结算份额）
       PRINT_DEPLOY_BILL       INT DEFAULT 0,
       PRINT_POST_INFO         INT DEFAULT 0,
       CUST_LEVEL              NVARCHAR(10),
	   CUST_SOURCE_EXPLAIN	   NVARCHAR(60),
	   INPUT_TIME			   INTEGER,
	   TOTAL_MONEY_ALL    	   DECIMAL(16, 3),
	   TOTAL_COUNT             INT
    )
    --------------------------------------------------------------------
    DECLARE @V_MANAGER_IDS TABLE(MANAGERID INT, MANAGERNAME NVARCHAR(60))
    --处理客户经理同级共享(共享给当前操作员的源客户经理)，共享时，同节点及下级节点的客户经理所辖客户，由于也具有访问权限，故一起共享了。故对经理树的处理放在下面
    INSERT INTO @V_MANAGER_IDS(MANAGERID)
        SELECT SourceManagerID FROM TAuthorizationShare WHERE ShareType = 1 AND Status = 1 AND ManagerID = @IN_INPUT_MAN
    --从客户经理树取当前操作员所辖客户经理,再取这些客户经理的客户
    IF NOT EXISTS (SELECT * FROM TSYSCONTROL WHERE FLAG_TYPE='TEAMLEADER_ROLERIGHT' AND VALUE=1)
    BEGIN
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
    END
    --处理授权给下级的(当前操作员的)客户授权集，注：被授权的客户没有级联共享给同级别经理
    INSERT INTO @V_TEMPCUST2(CUST_ID,AUTH_FLAG)
        SELECT DISTINCT A.CUST_ID,A.AUTH_FLAG FROM TAuthorizationCusts A, TAuthorizationShare B
        WHERE A.CA_ID = B.CA_ID AND B.ShareType = 2 AND B.Status = 1 AND B.ManagerID = @IN_INPUT_MAN
            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)
    --处理快捷授权的客户ShareType=3，仅查询
    INSERT INTO @V_TEMPCUST2(CUST_ID,AUTH_FLAG)
        SELECT CUST_ID,0 FROM TAuthorizationShare A WHERE ShareType = 3 AND Status = 1 AND CUST_ID <> 0 AND (GETDATE() BETWEEN ISNULL(A.START_TIME,GETDATE()) AND ISNULL(A.INVALID_TIME,GETDATE()+36000)) AND ManagerID = @IN_INPUT_MAN
            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)
        UNION ALL
        SELECT B.CUST_ID,0 FROM TAuthorizationShare A, TCustomers B
        WHERE A.SourceManagerID = B.SERVICE_MAN AND A.ShareType = 3 AND A.Status = 1 AND A.CUST_ID = 0 AND (GETDATE() BETWEEN ISNULL(A.START_TIME,GETDATE()) AND ISNULL(A.INVALID_TIME,GETDATE()+36000)) AND A.ManagerID = @IN_INPUT_MAN
            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)
    --以上客户经理的客户
    INSERT INTO @V_TEMPCUST2(CUST_ID)
        SELECT DISTINCT CUST_ID FROM TCustomers A
        WHERE (EXISTS( SELECT 1 FROM @V_MANAGER_IDS B WHERE A.SERVICE_MAN = B.MANAGERID)
                OR EXISTS( SELECT 1 FROM @V_MANAGER_IDS C WHERE (@V_QUERYCUSTOMERS = 1 AND A.INPUT_MAN = C.MANAGERID) ) )
            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)
            AND(A.RECOMMENDED = @IN_RECOMMENDED OR @IN_RECOMMENDED = '' OR @IN_RECOMMENDED IS NULL)
            AND(A.SERVICE_MAN = @IN_ACCOUNTMANAGER OR @IN_ACCOUNTMANAGER = 0 OR @IN_ACCOUNTMANAGER IS NULL) --增加客户经理和推荐人的查询
            AND(ISNULL(AGE,0) BETWEEN ISNULL(@IN_START_AGE,0) AND ISNULL(@IN_END_AGE,99999999) )
    --团队领导的团队成员
    IF NOT EXISTS (SELECT * FROM TSYSCONTROL WHERE FLAG_TYPE='TEAMLEADER_ROLERIGHT' AND VALUE=1)
    BEGIN
    INSERT INTO @V_TEMPCUST2(CUST_ID)
		SELECT CUST_ID FROM TCustomers T WHERE EXISTS (
			SELECT MANAGERID FROM TManagerTeamMembers A WHERE EXISTS (SELECT TEAM_ID FROM TManagerTeams B WHERE A.TEAM_ID=B.TEAM_ID AND LEADER=@IN_INPUT_MAN)
				AND T.SERVICE_MAN=A.MANAGERID)
			AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE T.CUST_ID = C.CUST_ID)
    END
    --20110610 dongyg 按"产品客户经理"控制访问权限
    IF EXISTS(SELECT 1 FROM TSYSCONTROL WHERE FLAG_TYPE = 'PRODUCT001' AND VALUE = 1)
    BEGIN
        SELECT @V_INTRUST_INPUT_MAN=INTRUST_Operator FROM TOPERATOR_MAP WHERE CRM_Operator=@IN_INPUT_MAN AND INTRUST_BOOKCODE=1
        SET @V_INTRUST_INPUT_MAN=ISNULL(@V_INTRUST_INPUT_MAN,@IN_INPUT_MAN)
        INSERT INTO @V_TEMPCUST2(CUST_ID)
            SELECT DISTINCT A.CUST_ID FROM INTRUST..TBENIFITOR A, INTRUST..TPRODUCT B
				WHERE A.PRODUCT_ID = B.PRODUCT_ID
				AND B.SERVICE_MAN = @V_INTRUST_INPUT_MAN
    END
    --------------------------------------------------------------------

    --已做过客户优质度评级的客户
    BEGIN
        INSERT INTO @V_TEMPCUST_SCORE
            SELECT CUST_ID FROM TCUSTSCORE WHERE (ISNULL(CURRENT_SOURCE,0) BETWEEN ISNULL(@IN_START_CURRENT_SOURCE,0) AND ISNULL(@IN_END_CURRENT_SOURCE,100000000)) AND (END_DATE = 21001231)
    END
    --------

    --根据 @IN_CLASSES 取满足条件的客户ID到临时表，若 @IN_CLASSES 为空，临时表也为空无记录
    IF @IN_CLASSES IS NULL SET @IN_CLASSES = N''
    IF dbo.chkCustClassDetailIDs(@IN_CLASSES) = 0
        RETURN -21111001 --输入参数无效
    CREATE TABLE #TMP_QUERY_CustByClass( CUST_ID INT )
    IF @IN_CLASSES <> ''
    BEGIN
        SET @V_SQL = N'INSERT INTO #TMP_QUERY_CustByClass(CUST_ID) SELECT CUST_ID FROM TCUSTOMERCLASS WHERE CLASSDETAIL_ID IN ('+@IN_CLASSES+') GROUP BY CUST_ID'
        EXEC sp_executesql @V_SQL
    END
    --------------------------------------------------------------------
    --20100121 dongyg start 增加对[认购份额、受益份额分级访问权限]的处理
    --认购份额、受益份额 按产品+客户计算汇总
    DECLARE @V_CUSTLEVELAUTH TABLE(
        PRODUCT_ID      INT,
        LEVEL_ID        INT,
        LEVEL_VALUE_ID  INT,
        MIN_VALUE       DEC(16,3),
        MAX_VALUE       DEC(16,3),
        LEVEL_TYPE      INT,        --条件类型：1任意一笔记录在范围内；2所有此类记录在范围内
        SUM_TYPE        INT         --金额汇总类型：1按客户+产品+合同；2按客户+产品；3按客户；为3时PRODUCT_ID值无效
    )
   
    --20100121 dongyg end 增加对[认购份额、受益份额分级访问权限]的处理
    ----------------------------------------
    --20111025 DONGYG 如果不控制潜在客户的查询，将所有潜在客户添加到 @V_TEMPCUST2 中
    IF @V_CUSTQZ0001 <> 1
        INSERT INTO @V_TEMPCUST2(CUST_ID) SELECT CUST_ID FROM TCustomers WHERE IS_DEAL = 2

    --------------------------------------------------------------------
    IF @IN_PRODUCT_ID IS NULL SELECT @IN_PRODUCT_ID = 0
    IF @IN_CELL_ID IS NULL SELECT @IN_CELL_ID = 0
    IF ISNULL(@IN_CUST_ID,0)<>0
        INSERT INTO #TEMPCUST(CUST_ID,CUST_NO,CUST_NAME,SEX,BIRTHDAY,POST_ADDRESS,POST_CODE,CARD_ID,CUST_TEL,E_MAIL,O_TEL,MOBILE,BP,
                STATUS_NAME,LAST_RG_DATE,CURR_TO_AMOUNT,TOTAL_MONEY,CURRENT_MONEY,BEN_AMOUNT,EXCHANGE_AMOUNT,BACK_AMOUNT,SERVICE_MAN,
                CUST_TYPE,GRADE_LEVEL,GRADE_LEVEL_NAME,CARD_TYPE_NAME,IS_DEAL,IS_DEAL_NAME,CUST_SOURCE,CUST_SOURCE_NAME,
                COUNTRY,COUNTRY_NAME,MONEY_SOURCE,MONEY_SOURCE_NAME,FIRST_RG_DATE,END_AMOUNT,PRINT_DEPLOY_BILL,PRINT_POST_INFO,
				CUST_SOURCE_EXPLAIN,INPUT_TIME,EXCHANGE_AMOUNT_IN)
            SELECT CUST_ID,CUST_NO,CUST_NAME,CASE SEX WHEN 1 THEN N'男' WHEN 2 THEN '女' ELSE N'' END AS SEX,BIRTHDAY,POST_ADDRESS,POST_CODE,
                   CARD_ID,CUST_TEL,E_MAIL,O_TEL,MOBILE,BP,STATUS_NAME,LAST_RG_DATE, 0,TOTAL_MONEY,CURRENT_MONEY,0,0,0,
                   SERVICE_MAN,CUST_TYPE,GRADE_LEVEL,GRADE_LEVEL_NAME,CARD_TYPE_NAME,IS_DEAL,
                   CASE IS_DEAL WHEN 1 THEN N'事实客户' WHEN 2 THEN N'潜在客户' END AS IS_DEAL_NAME,
                   CUST_SOURCE,CUST_SOURCE_NAME,COUNTRY,N'',MONEY_SOURCE,MONEY_SOURCE_NAME,NULL,0,PRINT_DEPLOY_BILL,PRINT_POST_INFO,
				   CUST_SOURCE_EXPLAIN,DBO.GETDATEINT(INPUT_TIME),0
                FROM TCustomers WHERE CUST_ID = @IN_CUST_ID
					
    ELSE
    BEGIN
        IF @IN_PRODUCT_ID = 0 AND @IN_CELL_ID = 0 AND ISNULL(@IN_PRODUCT_TO,'')=''
        BEGIN
            INSERT INTO #TEMPCUST(CUST_ID,CUST_NO,CUST_NAME,SEX,BIRTHDAY,POST_ADDRESS,POST_CODE,CARD_ID,CUST_TEL,E_MAIL,O_TEL,MOBILE,BP,
                    STATUS_NAME,LAST_RG_DATE,CURR_TO_AMOUNT,TOTAL_MONEY,CURRENT_MONEY,BEN_AMOUNT,EXCHANGE_AMOUNT,BACK_AMOUNT,SERVICE_MAN,
                    CUST_TYPE,GRADE_LEVEL,GRADE_LEVEL_NAME,CARD_TYPE_NAME,IS_DEAL,IS_DEAL_NAME,CUST_SOURCE,CUST_SOURCE_NAME,
                    COUNTRY,COUNTRY_NAME,MONEY_SOURCE,MONEY_SOURCE_NAME,FIRST_RG_DATE,END_AMOUNT,PRINT_DEPLOY_BILL,PRINT_POST_INFO,
					CUST_SOURCE_EXPLAIN,INPUT_TIME,EXCHANGE_AMOUNT_IN)
                SELECT A.CUST_ID,CUST_NO,CUST_NAME,CASE SEX WHEN 1 THEN N'男' WHEN 2 THEN '女' ELSE N'' END AS SEX,BIRTHDAY,POST_ADDRESS,POST_CODE,
                       CARD_ID,CUST_TEL,E_MAIL,O_TEL,MOBILE,BP,STATUS_NAME,LAST_RG_DATE, 0,TOTAL_MONEY,CURRENT_MONEY,0,0,0,
                       SERVICE_MAN,CUST_TYPE,GRADE_LEVEL,GRADE_LEVEL_NAME,CARD_TYPE_NAME,IS_DEAL,
                       CASE IS_DEAL WHEN 1 THEN N'事实客户' WHEN 2 THEN N'潜在客户' END AS IS_DEAL_NAME,
                       A.CUST_SOURCE,A.CUST_SOURCE_NAME,A.COUNTRY,N'',A.MONEY_SOURCE,A.MONEY_SOURCE_NAME,NULL,0,A.PRINT_DEPLOY_BILL,A.PRINT_POST_INFO,
					   CUST_SOURCE_EXPLAIN,DBO.GETDATEINT(INPUT_TIME),0
                    FROM TCustomers A LEFT JOIN @V_TEMPCUST_SCORE H ON (A.CUST_ID = H.CUST_ID)
                    WHERE (ISNULL(@IN_CUST_ID,0) = 0 OR A.CUST_ID = @IN_CUST_ID)
                        AND (ISNULL(@IN_CUST_NO,'') = N'' OR CUST_NO LIKE '%'+@IN_CUST_NO OR CUST_NO LIKE @IN_CUST_NO+'_______')
                        AND (ISNULL(@IN_CUST_NAME,'') = N'' OR CUST_NAME LIKE '%'+@IN_CUST_NAME+'%')
                        AND (ISNULL(@IN_CUST_SOURCE,'') = N'' OR CUST_SOURCE = @IN_CUST_SOURCE)
                        AND (ISNULL(@IN_CARD_TYPE,'') = N'' OR CARD_TYPE = @IN_CARD_TYPE)
                        AND (ISNULL(@IN_TOUCH_TYPE,'') = N'' OR ISNULL(@IN_TOUCH_TYPE,'') = N'0' OR TOUCH_TYPE = @IN_TOUCH_TYPE)
                        AND (ISNULL(@IN_CARD_ID,'') = N'' OR CARD_ID LIKE @IN_CARD_ID+'%' OR CARD_ID LIKE '%'+@IN_CARD_ID18+'%')
                        AND (ISNULL(@IN_MAX_TIMES,0) = 0 OR (RG_TIMES BETWEEN @IN_MIN_TIMES AND @IN_MAX_TIMES) )
                        --操作员=客户资料录入人 或 有访问客户资料权限
                        AND ( ( @V_QUERYCUSTOMERS = 1 AND A.INPUT_MAN = @IN_INPUT_MAN) OR SERVICE_MAN = @IN_INPUT_MAN OR @V_FLAG_ACCESS_ALL = 1
                             OR EXISTS(SELECT 1 FROM @V_TEMPCUST2 B WHERE A.CUST_ID = B.CUST_ID) )
                        AND (ISNULL(CUST_TEL,'') + ',' + ISNULL(O_TEL,'') + ',' + ISNULL(H_TEL,'') + ',' + ISNULL(MOBILE,'') + ',' + ISNULL(BP,'') + ',' + ISNULL(FAX,'') LIKE '%'+@IN_TEL+'%' OR @IN_TEL = N'' OR @IN_TEL IS NULL )
                        AND (POST_ADDRESS LIKE '%'+@IN_ADDRESS+'%' OR @IN_ADDRESS = N'' OR @IN_ADDRESS IS NULL )
                        AND (ISNULL(@IN_CUST_TYPE,0) = 0 OR CUST_TYPE = @IN_CUST_TYPE)
                        AND (A.IS_DEAL = @IN_IS_DEAL OR ISNULL(@IN_IS_DEAL,0) = 0 )
                        AND (A.CUST_ID IN (SELECT DISTINCT CUST_ID FROM TCustFamilyInfo WHERE FAMILY_NAME LIKE '%'+@IN_FAMILY_NAME+'%') OR ISNULL(@IN_FAMILY_NAME,'') = N'')
                        AND (@IN_ONLYEMAIL IS NULL OR @IN_ONLYEMAIL <> 1 OR (@IN_ONLYEMAIL = 1 AND E_MAIL LIKE '%@%'))
                        --AND ((ISNULL(@IN_MIN_TOTAL_MONEY,0) =0 OR TOTAL_MONEY >= @IN_MIN_TOTAL_MONEY) AND (ISNULL(@IN_MAX_TOTAL_MONEY,0) =0 OR TOTAL_MONEY <= @IN_MAX_TOTAL_MONEY))--目前CRM的客户表TOTAL_MONEY字段数据不准确，注释了
                        --AND (ISNULL(BIRTHDAY,0)%10000 BETWEEN ISNULL(@IN_BIRTHDAY,0)%10000 AND ISNULL(@IN_BIRTHDAY_END,20101231)%10000)
                        AND (ISNULL(BIRTHDAY,0)%10000>=ISNULL(@IN_BIRTHDAY,0)%10000 OR ISNULL(@IN_BIRTHDAY,0)%10000=0)
                        AND (ISNULL(BIRTHDAY,0)%10000<=ISNULL(@IN_BIRTHDAY_END,0)%10000 OR ISNULL(@IN_BIRTHDAY_END,0)%10000=0)
                        AND (ISNULL(@IN_PRINT_DEPLOY_BILL,0) = 0 OR PRINT_DEPLOY_BILL = @IN_PRINT_DEPLOY_BILL)
                        AND (ISNULL(@IN_IS_LINK,0) = 0 OR IS_LINK = @IN_IS_LINK)
                        AND (ISNULL(@IN_VIP_CARD_ID,'') = N'' OR VIP_CARD_ID LIKE '%'+@IN_VIP_CARD_ID+'%')
                        AND (ISNULL(@IN_HGTZR_BH,'') = N'' OR HGTZR_BH LIKE '%'+@IN_HGTZR_BH+'%')
                        AND (ISNULL(@IN_MONEY_SOURCE_NAME,'') = N'' OR MONEY_SOURCE_NAME LIKE '%'+@IN_MONEY_SOURCE_NAME+'%')
                        AND (ISNULL(@IN_COUNTRY,'') = N'' OR COUNTRY = @IN_COUNTRY)
                        AND STATUS <> '112805'
                        AND (A.CUST_ID IN (SELECT C.CUST_ID FROM TCustGroupMembers C WHERE C.GroupID = @IN_GroupID) OR ISNULL(@IN_GroupID,0) = 0)
                        AND(A.RECOMMENDED = @IN_RECOMMENDED OR @IN_RECOMMENDED = '' OR @IN_RECOMMENDED IS NULL)
                        AND(A.SERVICE_MAN = @IN_ACCOUNTMANAGER OR @IN_ACCOUNTMANAGER = 0 OR @IN_ACCOUNTMANAGER IS NULL) --增加客户经理和推荐人的查询
                        AND(ISNULL(AGE,0) BETWEEN ISNULL(@IN_START_AGE,0) AND ISNULL(@IN_END_AGE,99999999) )
                        AND(ISNULL(A.GOV_PROV_REGIONAL_NAME,'') LIKE '%'+ISNULL(@IN_REGIONAL_NAME,'')+'%' OR ISNULL(A.GOV_REGIONAL_NAME,'') LIKE '%'+ISNULL(@IN_REGIONAL_NAME,'')+'%')
                        AND (EXISTS (SELECT 1 FROM INTRUST..TCONTRACT M LEFT JOIN TPRODUCT N ON M.PRODUCT_ID=N.PRODUCT_ID WHERE M.CUST_ID =A.CUST_ID AND N.INVEST_FIELD=@IN_INVEST_FIELD)
							OR EXISTS(SELECT 1 FROM INTRUST..TCONTRACTSG M LEFT JOIN TPRODUCT N ON M.PRODUCT_ID=N.PRODUCT_ID WHERE M.CUST_ID =A.CUST_ID AND N.INVEST_FIELD=@IN_INVEST_FIELD) OR ISNULL(@IN_INVEST_FIELD,'')='')
						--AND (ISNULL(@IN_MOBILE,'') = N'' OR A.MOBILE LIKE '%'+@IN_MOBILE+'%' OR A.O_TEL LIKE '%'+@IN_MOBILE+'%' OR A.H_TEL LIKE '%'+@IN_MOBILE+'%') 
						--AND (ISNULL(@IN_POST_ADDRESS,'') = N'' OR A.POST_ADDRESS LIKE '%'+@IN_POST_ADDRESS+'%')
						AND (TRUE_FLAG=@IN_TRUE_FLAG OR ISNULL(@IN_TRUE_FLAG,0)=0)
						AND (ISNULL(@IN_SEX,0)=0 OR (A.CUST_TYPE=1 AND A.SEX=@IN_SEX)) --个人性别
        END
        ELSE
        BEGIN
            --MODI BY JINXR 2007/12/4
            IF @IN_WTR_FLAG = 0
            BEGIN
                -- 委托人和受益人
                INSERT INTO @V_TEMPCUST1
                    SELECT CUST_ID FROM INTRUST.dbo.TCONTRACT A LEFT JOIN TPRODUCT B ON A.PRODUCT_ID = B.PRODUCT_ID
                        WHERE (A.PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)  AND HT_STATUS <> '120104'
                            AND (A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
                            AND EXISTS(SELECT 1 FROM INTRUST.dbo.TPRODUCT Y WHERE A.PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
                            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST1 Z WHERE A.CUST_ID = Z.CUST_ID)
							AND (A.SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID OR ISNULL(@IN_SUB_PRODUCT_ID,0)=0)
							AND (B.INVEST_FIELD = @IN_INVEST_FIELD OR ISNULL(@IN_INVEST_FIELD,'')='')
                        GROUP BY CUST_ID
                INSERT INTO @V_TEMPCUST1
                    SELECT CUST_ID FROM INTRUST.dbo.TBENIFITOR A LEFT JOIN TPRODUCT B ON A.PRODUCT_ID = B.PRODUCT_ID
                        WHERE (A.PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)
							AND BEN_STATUS <>'121104'
							AND (A.SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID OR ISNULL(@IN_SUB_PRODUCT_ID,0)=0)
							AND (PROV_LEVEL=@IN_PROV_LEVEL OR @IN_PROV_LEVEL='' OR @IN_PROV_LEVEL IS NULL)
                            AND (A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
                            AND EXISTS(SELECT 1 FROM INTRUST.dbo.TPRODUCT Y WHERE A.PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
                            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST1 Z WHERE A.CUST_ID = Z.CUST_ID)
							AND (B.INVEST_FIELD = @IN_INVEST_FIELD OR ISNULL(@IN_INVEST_FIELD,'')='')
                        GROUP BY CUST_ID
            END
            ELSE IF @IN_WTR_FLAG = 1
            BEGIN
                --受益人
                INSERT INTO @V_TEMPCUST1
                SELECT CUST_ID FROM INTRUST.dbo.TBENIFITOR A LEFT JOIN TPRODUCT B ON A.PRODUCT_ID = B.PRODUCT_ID
                    WHERE (A.PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)
						AND BEN_STATUS <>'121104'
						AND (A.SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID OR ISNULL(@IN_SUB_PRODUCT_ID,0)=0)
						AND (PROV_LEVEL=@IN_PROV_LEVEL OR @IN_PROV_LEVEL='' OR @IN_PROV_LEVEL IS NULL)
                        AND (A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
                        AND EXISTS(SELECT 1 FROM INTRUST.dbo.TPRODUCT Y WHERE A.PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
                        AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST1 Z WHERE A.CUST_ID = Z.CUST_ID)
						AND (B.INVEST_FIELD = @IN_INVEST_FIELD OR ISNULL(@IN_INVEST_FIELD,'')='')
                    GROUP BY CUST_ID
            END
            ELSE
            BEGIN
                --委托人
                INSERT INTO @V_TEMPCUST1
                SELECT CUST_ID FROM INTRUST.dbo.TCONTRACT A LEFT JOIN TPRODUCT B ON A.PRODUCT_ID = B.PRODUCT_ID
                    WHERE (A.PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)  AND HT_STATUS <> '120104'
						AND (A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
						AND EXISTS(SELECT 1 FROM INTRUST.dbo.TPRODUCT Y WHERE A.PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
						AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST1 Z WHERE A.CUST_ID = Z.CUST_ID)
						AND (A.SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID OR ISNULL(@IN_SUB_PRODUCT_ID,0)=0)
						AND (B.INVEST_FIELD = @IN_INVEST_FIELD OR ISNULL(@IN_INVEST_FIELD,'')='')
                    GROUP BY CUST_ID
            END
            INSERT INTO @V_TEMPCUST1 SELECT CUST_ID FROM INTRUST.dbo.TPRECONTRACT A 
                WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)  AND PRE_STATUS='111301'
                    AND (A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
                    AND EXISTS(SELECT 1 FROM INTRUST.dbo.TPRODUCT Y WHERE A.PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
                    AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST1 Z WHERE A.CUST_ID = Z.CUST_ID)
                GROUP BY CUST_ID
       --
            INSERT INTO #TEMPCUST(CUST_ID,CUST_NO,CUST_NAME,SEX,BIRTHDAY,POST_ADDRESS,POST_CODE,CARD_ID,CUST_TEL,E_MAIL,O_TEL,MOBILE,BP,
                    STATUS_NAME,LAST_RG_DATE,CURR_TO_AMOUNT,TOTAL_MONEY,CURRENT_MONEY,BEN_AMOUNT,EXCHANGE_AMOUNT,BACK_AMOUNT,SERVICE_MAN,
                    CUST_TYPE,GRADE_LEVEL,GRADE_LEVEL_NAME,CARD_TYPE_NAME,IS_DEAL,IS_DEAL_NAME,CUST_SOURCE,CUST_SOURCE_NAME,
                    COUNTRY,COUNTRY_NAME,MONEY_SOURCE,MONEY_SOURCE_NAME,FIRST_RG_DATE,END_AMOUNT,PRINT_DEPLOY_BILL,PRINT_POST_INFO,
					CUST_SOURCE_EXPLAIN,INPUT_TIME,EXCHANGE_AMOUNT_IN)
                SELECT A.CUST_ID,A.CUST_NO,A.CUST_NAME,CASE SEX WHEN 1 THEN '男' WHEN 2 THEN '女' ELSE N'' END AS SEX,BIRTHDAY,POST_ADDRESS,POST_CODE,
                       A.CARD_ID,A.CUST_TEL,A.E_MAIL,A.O_TEL,A.MOBILE,A.BP,A.STATUS_NAME,A.LAST_RG_DATE, 0,A.TOTAL_MONEY,A.CURRENT_MONEY,0,0,0,
                       A.SERVICE_MAN,A.CUST_TYPE,GRADE_LEVEL,GRADE_LEVEL_NAME,CARD_TYPE_NAME,IS_DEAL,
                       CASE IS_DEAL WHEN 1 THEN '事实客户' WHEN 2 THEN '潜在客户' END AS IS_DEAL_NAME,
                       A.CUST_SOURCE,A.CUST_SOURCE_NAME,A.COUNTRY,N'',A.MONEY_SOURCE,A.MONEY_SOURCE_NAME,NULL,0,A.PRINT_DEPLOY_BILL,A.PRINT_POST_INFO,
					   CUST_SOURCE_EXPLAIN,DBO.GETDATEINT(INPUT_TIME),0
                    FROM TCustomers A LEFT JOIN @V_TEMPCUST_SCORE H ON (A.CUST_ID = H.CUST_ID),@V_TEMPCUST1 B
                    WHERE (A.CUST_ID = @IN_CUST_ID OR @IN_CUST_ID = 0 OR @IN_CUST_ID IS NULL)
                        AND (A.CUST_NO LIKE '%'+@IN_CUST_NO OR CUST_NO LIKE @IN_CUST_NO+'_______' OR @IN_CUST_NO = N'' OR @IN_CUST_NO IS NULL)
                        AND (A.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%' OR @IN_CUST_NAME = N'' OR @IN_CUST_NAME IS NULL)
                        AND (A.CUST_SOURCE = @IN_CUST_SOURCE OR @IN_CUST_SOURCE = N'' OR @IN_CUST_SOURCE IS NULL)
                        AND (A.CARD_TYPE = @IN_CARD_TYPE OR @IN_CARD_TYPE = N'' OR @IN_CARD_TYPE IS NULL)
                        AND (A.TOUCH_TYPE = @IN_TOUCH_TYPE OR @IN_TOUCH_TYPE = N'' OR @IN_TOUCH_TYPE IS NULL)
                        AND (A.CARD_ID LIKE @IN_CARD_ID+'%' OR CARD_ID LIKE '%'+@IN_CARD_ID18+'%' OR @IN_CARD_ID = N'' OR @IN_CARD_ID IS NULL)
                        AND ((A.RG_TIMES BETWEEN @IN_MIN_TIMES AND @IN_MAX_TIMES) OR @IN_MAX_TIMES IS NULL OR @IN_MAX_TIMES = 0 )
                        --操作员=客户资料录入人 或 有访问客户资料权限
                        AND (( @V_QUERYCUSTOMERS = 1 AND A.INPUT_MAN = @IN_INPUT_MAN) OR A.SERVICE_MAN = @IN_INPUT_MAN OR @V_FLAG_ACCESS_ALL = 1
                             OR EXISTS(SELECT 1 FROM @V_TEMPCUST2 Y WHERE A.CUST_ID = Y.CUST_ID))
                        --当输入了产品时，只返回该产品签约客户资料
                        AND A.CUST_ID=B.CUST_ID
                        AND (ISNULL(CUST_TEL,'') + ',' + ISNULL(O_TEL,'') + ',' + ISNULL(H_TEL,'') + ',' + ISNULL(MOBILE,'') + ',' + ISNULL(BP,'') + ',' + ISNULL(FAX,'') LIKE '%'+@IN_TEL+'%' OR @IN_TEL = N'' OR @IN_TEL IS NULL )
                        AND (A.POST_ADDRESS LIKE '%'+@IN_ADDRESS+'%' OR @IN_ADDRESS = N'' OR @IN_ADDRESS IS NULL )
                        AND (A.CUST_TYPE = @IN_CUST_TYPE OR @IN_CUST_TYPE = 0 OR @IN_CUST_TYPE IS NULL)
                        AND (A.IS_DEAL = @IN_IS_DEAL OR ISNULL(@IN_IS_DEAL,0) = 0 )
                        AND (A.CUST_ID IN (SELECT DISTINCT CUST_ID FROM TCustFamilyInfo WHERE FAMILY_NAME LIKE '%'+@IN_FAMILY_NAME+'%') OR @IN_FAMILY_NAME IS NULL OR @IN_FAMILY_NAME = N'')
                        AND (@IN_ONLYEMAIL IS NULL OR @IN_ONLYEMAIL <> 1 OR (@IN_ONLYEMAIL = 1 AND E_MAIL LIKE '%@%'))
                        --AND ((ISNULL(@IN_MIN_TOTAL_MONEY,0) =0 OR TOTAL_MONEY >= @IN_MIN_TOTAL_MONEY) AND (ISNULL(@IN_MAX_TOTAL_MONEY,0) =0 OR TOTAL_MONEY <= @IN_MAX_TOTAL_MONEY))--目前CRM的客户表TOTAL_MONEY字段数据不准确，注释了
                        --AND (ISNULL(BIRTHDAY,0)%10000 BETWEEN ISNULL(@IN_BIRTHDAY,0)%10000 AND ISNULL(@IN_BIRTHDAY_END,20101231)%10000)
                        AND (ISNULL(BIRTHDAY,0)%10000>=ISNULL(@IN_BIRTHDAY,0)%10000 OR ISNULL(@IN_BIRTHDAY,0)%10000=0)
                        AND (ISNULL(BIRTHDAY,0)%10000<=ISNULL(@IN_BIRTHDAY_END,0)%10000 OR ISNULL(@IN_BIRTHDAY_END,0)%10000=0)
                        AND (A.PRINT_DEPLOY_BILL = @IN_PRINT_DEPLOY_BILL OR ISNULL(@IN_PRINT_DEPLOY_BILL,0) = 0)
                        AND (A.IS_LINK = @IN_IS_LINK OR ISNULL(@IN_IS_LINK,0) = 0)
                        AND (VIP_CARD_ID LIKE '%'+@IN_VIP_CARD_ID+'%' OR ISNULL(@IN_VIP_CARD_ID,'') = N'')
                        AND (HGTZR_BH LIKE '%'+@IN_HGTZR_BH+'%' OR ISNULL(@IN_HGTZR_BH,'') = N'')
                        AND (ISNULL(@IN_MONEY_SOURCE_NAME,'') = N'' OR MONEY_SOURCE_NAME LIKE '%'+@IN_MONEY_SOURCE_NAME+'%')
                        AND (ISNULL(@IN_COUNTRY,'') = N'' OR COUNTRY = @IN_COUNTRY)
                        AND A.STATUS <> '112805'
                        AND (A.CUST_ID IN (SELECT C.CUST_ID FROM TCustGroupMembers C WHERE C.GroupID = @IN_GroupID) OR ISNULL(@IN_GroupID,0) = 0)
                        AND(A.RECOMMENDED = @IN_RECOMMENDED OR @IN_RECOMMENDED = '' OR @IN_RECOMMENDED IS NULL)
                        AND(A.SERVICE_MAN = @IN_ACCOUNTMANAGER OR @IN_ACCOUNTMANAGER = 0 OR @IN_ACCOUNTMANAGER IS NULL) --增加客户经理和推荐人的查询
                        AND(ISNULL(AGE,0) BETWEEN ISNULL(@IN_START_AGE,0) AND ISNULL(@IN_END_AGE,1000) )
                        AND(ISNULL(A.GOV_PROV_REGIONAL_NAME,'') LIKE '%'+ISNULL(@IN_REGIONAL_NAME,'')+'%' OR ISNULL(A.GOV_REGIONAL_NAME,'') LIKE '%'+ISNULL(@IN_REGIONAL_NAME,'')+'%')
						--AND (ISNULL(@IN_MOBILE,'') = N'' OR A.MOBILE LIKE '%'+@IN_MOBILE+'%' OR A.O_TEL LIKE '%'+@IN_MOBILE+'%' OR A.H_TEL LIKE '%'+@IN_MOBILE+'%')
						--AND (ISNULL(@IN_POST_ADDRESS,'') = N'' OR A.POST_ADDRESS LIKE '%'+@IN_POST_ADDRESS+'%')
						AND (TRUE_FLAG=@IN_TRUE_FLAG OR ISNULL(@IN_TRUE_FLAG,0)=0)
						AND (ISNULL(@IN_SEX,0)=0 OR (A.CUST_TYPE=1 AND A.SEX=@IN_SEX)) --个人性别
        END
    END

    --受让金额
    --ADD BY JINXR 2006/08/17
    CREATE TABLE #TEMPCUST3(CUST_ID INT,TO_AMOUNT DECIMAL(16,3))
    INSERT INTO #TEMPCUST3
        SELECT TO_CUST_ID,SUM(TO_AMOUNT) FROM INTRUST.dbo.TBENCHANGES
            WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)
                AND (PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
                AND EXISTS(SELECT 1 FROM INTRUST.dbo.TPRODUCT Y WHERE PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
                AND CHECK_FLAG = 2 AND TRANS_TYPE NOT IN('181511','181512','181513','181514')
            GROUP BY TO_CUST_ID

    UPDATE #TEMPCUST
        SET EXCHANGE_AMOUNT_IN = B.TO_AMOUNT
        FROM #TEMPCUST A, #TEMPCUST3 B
        WHERE A.CUST_ID = B.CUST_ID
    --转让金额
    DELETE FROM #TEMPCUST3
    INSERT INTO #TEMPCUST3
    SELECT FROM_CUST_ID,SUM(TO_AMOUNT) FROM INTRUST.dbo.TBENCHANGES
        WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)
            AND (PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
            AND EXISTS(SELECT 1 FROM INTRUST.dbo.TPRODUCT Y WHERE PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
            AND CHECK_FLAG = 2 AND TRANS_TYPE NOT IN('181511','181512','181513','181514')
        GROUP BY FROM_CUST_ID

    UPDATE #TEMPCUST
        SET EXCHANGE_AMOUNT = B.TO_AMOUNT
        FROM #TEMPCUST A, #TEMPCUST3 B
        WHERE A.CUST_ID = B.CUST_ID
    --到期本金
    DELETE FROM #TEMPCUST3
    INSERT INTO #TEMPCUST3
    SELECT CUST_ID,SUM(SY_AMOUNT)
        FROM INTRUST.dbo.TDEPLOY
        WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0) AND SY_TYPE = N'111605' AND PZ_FLAG = 2
        AND (PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
        AND EXISTS(SELECT 1 FROM INTRUST.dbo.TPRODUCT Y WHERE PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
        GROUP BY CUST_ID

    UPDATE #TEMPCUST
        SET BACK_AMOUNT = B.TO_AMOUNT
        FROM #TEMPCUST A, #TEMPCUST3 B
        WHERE A.CUST_ID = B.CUST_ID
    CREATE TABLE #TEMPCUST4(CUST_ID    INT,
                            BEN_AMOUNT DECIMAL(16,3),
                            TO_AMOUNT  DECIMAL(16,3),
                            MAX_DATE   INT,
                            PRODUCT_ID INT
                            )
    --从受益人表中统计客户的累计认购金额
    INSERT INTO #TEMPCUST4
    SELECT CUST_ID,SUM(ISNULL(BEN_AMOUNT,0)),SUM(ISNULL(TO_AMOUNT,0)),MAX(BEN_DATE),MAX(PRODUCT_ID)
        FROM INTRUST.dbo.TBENIFITOR
        WHERE (PRODUCT_ID = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)
		AND (SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID OR ISNULL(@IN_SUB_PRODUCT_ID,0)=0)
        AND (PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCT_IDS) OR NOT EXISTS(SELECT 1 FROM @V_PRODUCT_IDS) )
        AND EXISTS(SELECT 1 FROM INTRUST.dbo.TPRODUCT Y WHERE PRODUCT_ID = Y.PRODUCT_ID AND (INTRUST_TYPE2 = @IN_PRODUCT_TO OR ISNULL(@IN_PRODUCT_TO,'')=''))
        GROUP BY CUST_ID

    UPDATE #TEMPCUST
        SET BEN_AMOUNT = B.BEN_AMOUNT, --当前受益金额合计
            TOTAL_MONEY = B.TO_AMOUNT, --累计认购金额
            LAST_RG_DATE = B.MAX_DATE  --最近认购日期
        FROM #TEMPCUST A, #TEMPCUST4 B
        WHERE A.CUST_ID = B.CUST_ID
    --删除记录：不合条件的：累计认购金额不在输入区间的
    DELETE FROM #TEMPCUST WHERE (ISNULL(@IN_MIN_TOTAL_MONEY,0)>0 AND TOTAL_MONEY<@IN_MIN_TOTAL_MONEY) OR (ISNULL(@IN_MAX_TOTAL_MONEY,0)>0 AND TOTAL_MONEY>@IN_MAX_TOTAL_MONEY)
    IF @IN_PRODUCT_ID <> 0
    BEGIN
        DELETE FROM #TEMPCUST3
        INSERT INTO #TEMPCUST3
            SELECT CUST_ID,SUM(ISNULL(TO_AMOUNT,0))
                FROM INTRUST.dbo.TBENIFITOR WHERE CHECK_FLAG = 2
					AND PRODUCT_ID = @IN_PRODUCT_ID
					AND (SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID OR @IN_SUB_PRODUCT_ID = 0)
					GROUP BY CUST_ID
        UPDATE #TEMPCUST
            SET CURR_TO_AMOUNT = B.TO_AMOUNT
            FROM #TEMPCUST A,#TEMPCUST3 B
            WHERE A.CUST_ID = B.CUST_ID
    END
    --20100302 dongyg EFCRM.TCustomers.TOTAL_MONEY/CURRENT_MONEY 的值未根据业务发生而变化，需要根据 INTRUST.TCUSTOMERINFO.TOTAL_MONEY/CURRENT_MONEY 修正
    UPDATE #TEMPCUST
        SET TOTAL_MONEY = B.TOTAL_MONEY, CURRENT_MONEY = B.CURRENT_MONEY, CUST_LEVEL = B.CUST_LEVEL
        FROM #TEMPCUST A, INTRUST..TCUSTOMERINFO B
        WHERE A.CUST_ID = B.CUST_ID
    --20110531 dongyg 统计的已购买金额、受益份额、存量金额，将非信托产品计算在内（非信托产品的受益份额不在份额权限控制机制内）
--20111107 dongyg 北国投需求："综合查询"中统计的已购买金额、受益份额、存量金额，将非信托产品不计算在内.
--    UPDATE #TEMPCUST SET BEN_AMOUNT = A.BEN_AMOUNT + B.QUOTIENT_AMOUNT
--        FROM #TEMPCUST A, (SELECT CUST_ID,SUM(QUOTIENT_AMOUNT) AS QUOTIENT_AMOUNT FROM TQUOTIENT GROUP BY CUST_ID) B
--        WHERE A.CUST_ID = B.CUST_ID
--    UPDATE #TEMPCUST SET TOTAL_MONEY = A.TOTAL_MONEY + B.SUBSCRIBE_MONEY
--        FROM #TEMPCUST A, (SELECT CUST_ID,SUM(SUBSCRIBE_MONEY) AS SUBSCRIBE_MONEY FROM TSUBSCRIBE GROUP BY CUST_ID) B
--        WHERE A.CUST_ID = B.CUST_ID
--    UPDATE #TEMPCUST SET CURRENT_MONEY = A.CURRENT_MONEY + B.QUOTIENT_MONEY
--        FROM #TEMPCUST A, (SELECT CUST_ID,SUM(QUOTIENT_MONEY) AS QUOTIENT_MONEY FROM TQUOTIENT GROUP BY CUST_ID) B
--        WHERE A.CUST_ID = B.CUST_ID
    --增加当前份额（不含到期的 2011-10-09）
    UPDATE #TEMPCUST SET END_AMOUNT = TOTAL_MONEY-BEN_AMOUNT

    -- 修改资金来源名称 国别名称
    UPDATE #TEMPCUST SET COUNTRY_NAME = (SELECT B.TYPE_CONTENT FROM TDICTPARAM B WHERE B.TYPE_VALUE = COUNTRY)

    -------------------------------------------------------------------------------------
    --对客户只读or编辑权限的判断
    --根据客户授权集的授权标志取只读or编辑
    UPDATE #TEMPCUST SET MODI_FLAG = B.AUTH_FLAG
        FROM #TEMPCUST A, @V_TEMPCUST2 B
        WHERE A.CUST_ID = B.CUST_ID
    --20120201 dongyg 仅客户经理和录入人允许修改
    UPDATE #TEMPCUST SET MODI_FLAG = 1
        FROM #TEMPCUST A, TCustomers B
        WHERE A.CUST_ID = B.CUST_ID AND (@V_CCUSTMODI <> 1 OR ((B.SERVICE_MAN = @IN_INPUT_MAN OR B.INPUT_MAN = @IN_INPUT_MAN) AND @V_CCUSTMODI = 1))
            AND A.MODI_FLAG IS NULL
    --除以上条件外，默认为只读
    UPDATE #TEMPCUST SET MODI_FLAG = 2 WHERE MODI_FLAG IS NULL
    -------------------------------------------------------------------------------------
	--统计客户累计购买金额及购买次数:从资金到账表中统计，同一产品，只算一次认购
	SELECT A.CUST_ID,SUM(A.TO_MONEY) TOTAL_MONEY_ALL,COUNT(A.CUST_ID) TOTAL_COUNT 
		INTO #TEMPCUST_TOTAL_TMP
		FROM INTRUST..TMONEYDETAIL A
		WHERE A.IS_JK_DATA=1 --1为缴款记录，其他为利息等非缴款记录
			AND CHECK_FLAG=2 --财务已审核
		GROUP BY A.CUST_ID,A.PRODUCT_ID,A.SUB_PRODUCT_ID
	SELECT CUST_ID,SUM(TOTAL_MONEY_ALL) TOTAL_MONEY_ALL,COUNT(CUST_ID) TOTAL_COUNT
		INTO #TEMPCUST_TOTAL
		FROM #TEMPCUST_TOTAL_TMP
		GROUP BY CUST_ID
							
	UPDATE #TEMPCUST SET 
		TOTAL_MONEY_ALL = ISNULL((SELECT ISNULL(A.TOTAL_MONEY_ALL,0) FROM #TEMPCUST_TOTAL A WHERE A.CUST_ID = #TEMPCUST.CUST_ID),0) + ISNULL(TOTAL_MONEY_ALL,0),
		TOTAL_COUNT = ISNULL((SELECT ISNULL(A.TOTAL_COUNT,0) FROM #TEMPCUST_TOTAL A WHERE A.CUST_ID = #TEMPCUST.CUST_ID),0) + ISNULL(TOTAL_COUNT,0)
	--加入合同未录入系统的历史数据(只有在没有合同的时候，才加此历史数据)
	UPDATE #TEMPCUST SET 
		TOTAL_MONEY_ALL = ISNULL(TOTAL_MONEY_ALL,0) + ISNULL((SELECT TRADE_MONEY FROM TCUSTINFOADD WHERE CUST_ID=#TEMPCUST.CUST_ID),0),
		TOTAL_COUNT = ISNULL(TOTAL_COUNT,0) + ISNULL((SELECT TRADE_TIMES FROM TCUSTINFOADD WHERE CUST_ID = #TEMPCUST.CUST_ID),0)
		WHERE ISNULL(TOTAL_MONEY_ALL,0)=0
	
    --开关控制 加密重要联系信息
	--(方正的要求是除高级客户经理、部门经理意外的角色对于客户信息的查询都只能看到
	-- 客户名称和认购项目的金额) 因此以开关来单独进行加密控制
    IF(@V_FLAG_ENCRYPTION = 1)
    BEGIN
		IF(@V_FACT_POTENTIAL_FLAG_FZ = 2) --方正的加密方式
		BEGIN
			IF @IN_EXPORT_FLAG=101 --要加密的客户直接删除，不能查看(客户信息修改时的查询)
				DELETE FROM #TEMPCUST WHERE ( (SERVICE_MAN <> @IN_INPUT_MAN ) OR MODI_FLAG <> 1 )  --仅只读的记录才加密数据
					AND (IS_DEAL = @V_IS_DEAL OR ISNULL(@V_IS_DEAL,0)=0)
					AND (NOT EXISTS(SELECT CUST_ID FROM @V_TEMPCUST2 B WHERE CUST_ID = B.CUST_ID))--不存在客户授权的
			ELSE
			UPDATE #TEMPCUST
			SET CARD_ID = ISNULL(stuff(A.CARD_ID,len(A.CARD_ID)-@V_ENCRYPTION_SIZE+1,len(A.CARD_ID),'******'),'****'),
				CUST_TEL = ISNULL(stuff(A.CUST_TEL,len(A.CUST_TEL)-@V_ENCRYPTION_SIZE+1,len(A.CUST_TEL),'******'),'****'),
				--H_TEL = ISNULL(stuff(H_TEL,1,len(H_TEL)-@V_ENCRYPTION_SIZE,'******'),'****'),
				O_TEL = ISNULL(stuff(A.O_TEL,len(A.O_TEL)-@V_ENCRYPTION_SIZE+1,len(A.O_TEL),'******'),'****'),
				E_MAIL = ISNULL(stuff(A.E_MAIL,1,charindex('@',A.E_MAIL)-1,'******'),''),
				MOBILE = ISNULL(stuff(A.MOBILE,len(A.MOBILE)-@V_ENCRYPTION_SIZE+1,len(A.MOBILE),'******'),'****'),
				BP = ISNULL(stuff(A.BP,len(A.BP)-@V_ENCRYPTION_SIZE+1,len(A.BP),'******'),'****'),
				POST_ADDRESS = ISNULL(stuff(A.POST_ADDRESS,len(A.BP)-@V_ENCRYPTION_SIZE+1,len(A.POST_ADDRESS),'******'),'****')
			FROM #TEMPCUST A
			WHERE ( (A.SERVICE_MAN <> @IN_INPUT_MAN ) OR MODI_FLAG <> 1 )  --仅只读的记录才加密数据
				AND (A.IS_DEAL = @V_IS_DEAL OR ISNULL(@V_IS_DEAL,0)=0)
				AND (NOT EXISTS(SELECT CUST_ID FROM @V_TEMPCUST2 B WHERE A.CUST_ID = B.CUST_ID))--不存在客户授权的
			
		    --  WHERE ( (SERVICE_MAN <> @IN_INPUT_MAN ) OR MODI_FLAG <> 1 )  --仅只读的记录才加密数据
		END
		ELSE --客户加密
		BEGIN
			IF @IN_EXPORT_FLAG=101 --要加密的客户直接删除，不能查看(客户信息修改时的查询)
				DELETE FROM #TEMPCUST
					WHERE ((#TEMPCUST.SERVICE_MAN <> @IN_INPUT_MAN ) OR MODI_FLAG <> 1 )  --仅只读的记录才加密数据
					--AND (A.IS_DEAL = @V_IS_DEAL OR ISNULL(@V_IS_DEAL,0)=0)
					AND (NOT EXISTS(SELECT CUST_ID FROM @V_TEMPCUST2 B WHERE #TEMPCUST.CUST_ID = B.CUST_ID))--不存在客户授权的
			ELSE
			UPDATE #TEMPCUST
			SET CARD_ID = ISNULL(stuff(A.CARD_ID,len(A.CARD_ID)-@V_ENCRYPTION_SIZE+1,len(A.CARD_ID),'******'),'****'),
				CUST_TEL = ISNULL(stuff(A.CUST_TEL,len(A.CUST_TEL)-@V_ENCRYPTION_SIZE+1,len(A.CUST_TEL),'******'),'****'),
				--H_TEL = ISNULL(stuff(H_TEL,len(H_TEL)-@V_ENCRYPTION_SIZE+1,len(H_TEL),'******'),'****'),
				O_TEL = ISNULL(stuff(A.O_TEL,len(A.O_TEL)-@V_ENCRYPTION_SIZE+1,len(A.O_TEL),'******'),'****'),
				E_MAIL = ISNULL(stuff(A.E_MAIL,1,charindex('@',A.E_MAIL)-1,'******'),''),
				MOBILE = ISNULL(stuff(A.MOBILE,len(A.MOBILE)-@V_ENCRYPTION_SIZE+1,len(A.MOBILE),'******'),'****'),
				BP = ISNULL(stuff(A.BP,len(A.BP)-@V_ENCRYPTION_SIZE+1,len(A.BP),'******'),'****'),
				POST_ADDRESS = ISNULL(stuff(A.POST_ADDRESS,len(A.POST_ADDRESS)-@V_ENCRYPTION_SIZE+1,len(A.POST_ADDRESS),'******'),'****')
			FROM #TEMPCUST A
			WHERE ((A.SERVICE_MAN <> @IN_INPUT_MAN ) OR MODI_FLAG <> 1 )  --仅只读的记录才加密数据
				--AND (A.IS_DEAL = @V_IS_DEAL OR ISNULL(@V_IS_DEAL,0)=0)
				AND (NOT EXISTS(SELECT CUST_ID FROM @V_TEMPCUST2 B WHERE A.CUST_ID = B.CUST_ID))--不存在客户授权的
		END
    END
    --20110613 DONGYG 添加客户首次认购、末次认购日期及过滤(改为以到账日期为统计依据)
    UPDATE #TEMPCUST
        SET FIRST_RG_DATE = B.FIRST_RG_DATE,LAST_RG_DATE = B.LAST_RG_DATE
        FROM #TEMPCUST A, (SELECT CUST_ID, MIN(DZ_DATE) AS FIRST_RG_DATE, MAX(DZ_DATE) AS LAST_RG_DATE FROM INTRUST..TMONEYDETAIL GROUP BY CUST_ID) B
        WHERE A.CUST_ID = B.CUST_ID
    SELECT A.CUST_ID               
		   ,A.CUST_NO               
		   ,A.CUST_NAME             
		   ,A.SEX                   
		   ,A.BIRTHDAY              
		   ,A.POST_ADDRESS          
		   ,A.POST_CODE             
		   ,A.CARD_ID               
		   ,A.CUST_TEL              
		   ,A.E_MAIL                
		   ,A.O_TEL                 
		   ,A.MOBILE                
		   ,A.BP                    
		   ,A.STATUS_NAME           
		   ,A.LAST_RG_DATE          
		   ,A.CURR_TO_AMOUNT        
		   ,A.TOTAL_MONEY           
		   ,A.CURRENT_MONEY         
		   ,A.BEN_AMOUNT            
		   ,A.EXCHANGE_AMOUNT       
		   ,A.EXCHANGE_AMOUNT_IN    
		   ,A.BACK_AMOUNT           
		   ,A.SERVICE_MAN           
		   ,A.CUST_TYPE             
		   ,A.MODI_FLAG             
		   ,A.GRADE_LEVEL           
		   ,A.GRADE_LEVEL_NAME      
		   ,A.CARD_TYPE_NAME        
		   ,A.IS_DEAL               
		   ,A.IS_DEAL_NAME          
		   ,A.CUST_SOURCE           
		   ,A.CUST_SOURCE_NAME      
		   ,A.COUNTRY               
		   ,A.COUNTRY_NAME          
		   ,A.MONEY_SOURCE          
		   ,A.MONEY_SOURCE_NAME     
		   ,A.FIRST_RG_DATE         
		   ,A.END_AMOUNT            
		   ,A.PRINT_DEPLOY_BILL     
		   ,A.PRINT_POST_INFO       
		   ,A.CUST_LEVEL            
		   ,A.CUST_SOURCE_EXPLAIN	   
		   ,A.INPUT_TIME			  
		   ,A.TOTAL_MONEY_ALL    	   
		   ,A.TOTAL_COUNT, CASE A.CUST_TYPE WHEN 1 THEN N'个人' WHEN 2 THEN N'机构' END AS CUST_TYPE_NAME, dbo.GETAGE(B.BIRTHDAY) AGE, B.CARD_TYPE, B.FAX
       ,B.TRUE_FLAG, CASE B.TRUE_FLAG WHEN 1 THEN '未核查' WHEN 2 THEN '真实' ELSE '待核查' END AS TRUE_FLAG_NAME
        INTO #TEMP_QUERY_TCustomersExt
        FROM #TEMPCUST A, TCustomers B
        WHERE A.CUST_ID = B.CUST_ID AND (ISNULL(@IN_BEN_AMOUNT_MIN,0) = 0 OR A.BEN_AMOUNT >= ISNULL(@IN_BEN_AMOUNT_MIN,0))
          AND (ISNULL(@IN_BEN_AMOUNT_MAX,0) = 0 OR A.BEN_AMOUNT <= ISNULL(@IN_BEN_AMOUNT_MAX,0))
          AND (ISNULL(A.FIRST_RG_DATE,0) BETWEEN ISNULL(@IN_RG_BEGIN_DATE,0) AND ISNULL(@IN_RG_END_DATE,30001231)
            OR ISNULL(A.LAST_RG_DATE,0) BETWEEN ISNULL(@IN_RG_BEGIN_DATE,0) AND ISNULL(@IN_RG_END_DATE,30001231) )
	
	--统计导出的记录数
	DECLARE @V_EXPORT_NUM INT,@V_QUERY_TYPE INT,@V_QUERY_TYPE_NAME NVARCHAR(60)
	IF @IN_EXPORT_FLAG>0
	BEGIN
		SELECT @V_EXPORT_NUM=COUNT(0) FROM #TEMP_QUERY_TCustomersExt A LEFT JOIN (SELECT CUST_ID,MAX([10]) AS CLASS10, MAX([11]) AS CLASS11, MAX([12]) AS CLASS12
                FROM TCustomerClass PIVOT(MAX(CLASSDETAIL_NAME) FOR CLASSDEFINE_ID IN ([10],[11],[12])) AS Z
                GROUP BY CUST_ID) AS B ON A.CUST_ID = B.CUST_ID
            LEFT JOIN TOPERATOR H ON A.SERVICE_MAN = H.OP_CODE
            WHERE (@IN_CLASSES='' OR EXISTS(SELECT 1 FROM #TMP_QUERY_CustByClass Z WHERE A.CUST_ID = Z.CUST_ID))
				AND (ISNULL(@IN_CITY_NAME,'') = '' OR EXISTS(SELECT 1 FROM INTRUST..TCONTRACT O WHERE A.CUST_ID = O.CUST_ID AND O.CITY_NAME LIKE '%'+@IN_CITY_NAME+'%'))
				AND (ISNULL(@IN_CHANNEL_NAME,'')='' OR EXISTS(SELECT 1 FROM INTRUST..TCONTRACT O, INTRUST..TCHANNEL P
                    WHERE O.CHANNEL_ID=P.CHANNEL_ID AND A.CUST_ID=O.CUST_ID AND P.CHANNEL_NAME LIKE '%'+@IN_CHANNEL_NAME+'%'))
    END
	--导出时，做日志记录	
	IF @IN_EXPORT_FLAG=1 --客户信息导出
	BEGIN
		INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
			SELECT '20301','客户信息导出',@IN_INPUT_MAN,'客户信息导出数：'+CAST(@V_EXPORT_NUM AS VARCHAR)+'；备注：'+ISNULL(@IN_EXPORT_SUMMARY,'')
		SELECT @V_QUERY_TYPE=3,@V_QUERY_TYPE_NAME='客户信息导出'
	END
	ELSE IF @IN_EXPORT_FLAG=2 --客户手机
	BEGIN
	    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
			SELECT '20301','客户信息导出',@IN_INPUT_MAN,'客户手机导出数：'+CAST(@V_EXPORT_NUM AS VARCHAR)+'；备注：'+ISNULL(@IN_EXPORT_SUMMARY,'')
		SELECT @V_QUERY_TYPE=4,@V_QUERY_TYPE_NAME='客户手机导出'
	END
	ELSE IF @IN_EXPORT_FLAG=3 --客户通讯录
	BEGIN
	    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
			SELECT '20301','客户信息导出',@IN_INPUT_MAN,'客户通讯录导出数：'+CAST(@V_EXPORT_NUM AS VARCHAR)+'；备注：'+ISNULL(@IN_EXPORT_SUMMARY,'')
		SELECT @V_QUERY_TYPE=5,@V_QUERY_TYPE_NAME='客户通讯录导出'
	END
	ELSE IF @IN_EXPORT_FLAG=102
	BEGIN --客户移交查询时，要去掉已经保存，且未审核的移交客户
		DELETE FROM #TEMP_QUERY_TCustomersExt WHERE EXISTS (SELECT * FROM TCUSTMANAGERCHANGES WHERE MANAGERID_BEFORE=#TEMP_QUERY_TCustomersExt.SERVICE_MAN AND CUST_ID=#TEMP_QUERY_TCustomersExt.CUST_ID AND CHECK_FLAG=1)
	END
	ELSE IF @IN_CUST_ID>0
		SELECT @V_QUERY_TYPE=1,@V_QUERY_TYPE_NAME='单客户查询'
	ELSE
		SELECT @V_QUERY_TYPE=2,@V_QUERY_TYPE_NAME='客户条件查询'
	
	IF @IN_CC_STATUS = '-1'
	SET @IN_CC_STATUS = '1,2,3,4,5' --查询已有申请记录的客户
	
	IF EXISTS(SELECT 1 FROM TSYSTEM_CTRL WHERE CTRL_TYPE='IS_SHOW_REALPHONE' AND CTRL_VALUE = '1')  
		BEGIN
			IF @IN_CC_STATUS = '0' --待录入
			BEGIN
				SET @V_SQL = N'
					SELECT TR.TC_TEL AS V_TEL_NUM,A.*, B.CLASS10, B.CLASS11, B.CLASS12 AS CUST_LEVEL_NAME,H.OP_NAME AS SERVICE_MAN_NAME
					FROM #TEMP_QUERY_TCustomersExt A LEFT JOIN (SELECT CUST_ID,MAX([10]) AS CLASS10, MAX([11]) AS CLASS11, MAX([12]) AS CLASS12
										FROM TCustomerClass PIVOT(MAX(CLASSDETAIL_NAME) FOR CLASSDEFINE_ID IN ([10],[11],[12])) AS Z
										GROUP BY CUST_ID) AS B ON A.CUST_ID = B.CUST_ID
									 LEFT JOIN TOPERATOR H ON A.SERVICE_MAN = H.OP_CODE
									 LEFT JOIN TNUMBER_RELATION TR ON TR.FK_TCUSTOMERS=A.CUST_ID
									 LEFT JOIN 
									 (SELECT * FROM (select * , ROW_NUMBER() over(partition by CUST_ID order by INPUT_TIME DESC) as rownum 
										from TCUSTOMOERS_CONNECTION_MODI) em WHERE em.rownum=1) PR ON A.CUST_ID=PR.CUST_ID
									 WHERE 1=1 AND PR.SERIAL_NO IS NULL'
			END
			ELSE
			BEGIN
				SET @V_SQL = N'
					SELECT TR.TC_TEL AS V_TEL_NUM,A.*, B.CLASS10, B.CLASS11, B.CLASS12 AS CUST_LEVEL_NAME,H.OP_NAME AS SERVICE_MAN_NAME
					FROM #TEMP_QUERY_TCustomersExt A LEFT JOIN (SELECT CUST_ID,MAX([10]) AS CLASS10, MAX([11]) AS CLASS11, MAX([12]) AS CLASS12
										FROM TCustomerClass PIVOT(MAX(CLASSDETAIL_NAME) FOR CLASSDEFINE_ID IN ([10],[11],[12])) AS Z
										GROUP BY CUST_ID) AS B ON A.CUST_ID = B.CUST_ID
									 LEFT JOIN TOPERATOR H ON A.SERVICE_MAN = H.OP_CODE
									 LEFT JOIN TNUMBER_RELATION TR ON TR.FK_TCUSTOMERS=A.CUST_ID
									 LEFT JOIN 
									 (SELECT * FROM (select * , ROW_NUMBER() over(partition by CUST_ID order by INPUT_TIME DESC) as rownum 
										from TCUSTOMOERS_CONNECTION_MODI WHERE [STATUS] IN (' + @IN_CC_STATUS
										+ N')) em WHERE em.rownum=1) PR ON A.CUST_ID=PR.CUST_ID
									 WHERE 1=1 AND PR.SERIAL_NO IS NOT NULL'
			END

		END
	ELSE
		BEGIN
		IF @IN_CC_STATUS = '0' --待录入
		BEGIN
			SET @V_SQL = N'
				SELECT TR.TC_TEL AS V_TEL_NUM,
				A.CUST_ID,A.CUST_NO,A.CUST_NAME,A.SEX,A.BIRTHDAY,A.POST_ADDRESS,A.POST_CODE,A.CARD_ID,
				NULL AS CUST_TEL,
				A.E_MAIL,
				NULL AS O_TEL,
				TR.TC_TEL AS MOBILE
			   ,NULL AS BP
			   ,A.STATUS_NAME
			   ,A.LAST_RG_DATE
			   ,A.CURR_TO_AMOUNT
			   ,A.TOTAL_MONEY
			   ,A.CURRENT_MONEY
			   ,A.BEN_AMOUNT
			   ,A.EXCHANGE_AMOUNT
			   ,A.EXCHANGE_AMOUNT_IN
			   ,A.BACK_AMOUNT
			   ,A.SERVICE_MAN
			   ,A.CUST_TYPE
			   ,A.MODI_FLAG
			   ,A.GRADE_LEVEL
			   ,A.GRADE_LEVEL_NAME
			   ,A.CARD_TYPE_NAME
			   ,A.IS_DEAL
			   ,A.IS_DEAL_NAME
			   ,A.CUST_SOURCE
			   ,A.CUST_SOURCE_NAME
			   ,A.COUNTRY
			   ,A.COUNTRY_NAME
			   ,A.MONEY_SOURCE
			   ,A.MONEY_SOURCE_NAME
			   ,A.FIRST_RG_DATE
			   ,A.END_AMOUNT
			   ,A.PRINT_DEPLOY_BILL
			   ,A.PRINT_POST_INFO
			   ,A.CUST_LEVEL
			   ,A.CUST_SOURCE_EXPLAIN
			   ,A.INPUT_TIME
			   ,A.TOTAL_MONEY_ALL
			   ,A.TOTAL_COUNT, A.CUST_TYPE_NAME, A.AGE, A.CARD_TYPE, A.FAX
		   ,A.TRUE_FLAG, A.TRUE_FLAG_NAME
			   , B.CLASS10
			   , B.CLASS11
			   ,B.CLASS12 AS CUST_LEVEL_NAME 
			   ,H.OP_NAME AS SERVICE_MAN_NAME
				FROM #TEMP_QUERY_TCustomersExt A 
				LEFT JOIN (SELECT CUST_ID,MAX([10]) AS CLASS10, MAX([11]) AS CLASS11, MAX([12]) AS CLASS12
									FROM TCustomerClass PIVOT(MAX(CLASSDETAIL_NAME) FOR CLASSDEFINE_ID IN ([10],[11],[12])) AS Z
									GROUP BY CUST_ID) AS B ON A.CUST_ID = B.CUST_ID
								 LEFT JOIN TOPERATOR H ON A.SERVICE_MAN = H.OP_CODE
								 LEFT JOIN TNUMBER_RELATION TR ON TR.FK_TCUSTOMERS = A.CUST_ID
								 LEFT JOIN 
									 (SELECT * FROM (select * , ROW_NUMBER() over(partition by CUST_ID order by INPUT_TIME DESC) as rownum 
										from TCUSTOMOERS_CONNECTION_MODI) em WHERE em.rownum=1) PR ON A.CUST_ID=PR.CUST_ID
									 WHERE 1=1 AND PR.SERIAL_NO IS NULL'
		END
		ELSE
		BEGIN
			SET @V_SQL = N'
				SELECT TR.TC_TEL AS V_TEL_NUM,
				A.CUST_ID,A.CUST_NO,A.CUST_NAME,A.SEX,A.BIRTHDAY,A.POST_ADDRESS,A.POST_CODE,A.CARD_ID,
				NULL AS CUST_TEL,
				A.E_MAIL,
				NULL AS O_TEL,
				TR.TC_TEL AS MOBILE
			   ,NULL AS BP
			   ,A.STATUS_NAME
			   ,A.LAST_RG_DATE
			   ,A.CURR_TO_AMOUNT
			   ,A.TOTAL_MONEY
			   ,A.CURRENT_MONEY
			   ,A.BEN_AMOUNT
			   ,A.EXCHANGE_AMOUNT
			   ,A.EXCHANGE_AMOUNT_IN
			   ,A.BACK_AMOUNT
			   ,A.SERVICE_MAN
			   ,A.CUST_TYPE
			   ,A.MODI_FLAG
			   ,A.GRADE_LEVEL
			   ,A.GRADE_LEVEL_NAME
			   ,A.CARD_TYPE_NAME
			   ,A.IS_DEAL
			   ,A.IS_DEAL_NAME
			   ,A.CUST_SOURCE
			   ,A.CUST_SOURCE_NAME
			   ,A.COUNTRY
			   ,A.COUNTRY_NAME
			   ,A.MONEY_SOURCE
			   ,A.MONEY_SOURCE_NAME
			   ,A.FIRST_RG_DATE
			   ,A.END_AMOUNT
			   ,A.PRINT_DEPLOY_BILL
			   ,A.PRINT_POST_INFO
			   ,A.CUST_LEVEL
			   ,A.CUST_SOURCE_EXPLAIN
			   ,A.INPUT_TIME
			   ,A.TOTAL_MONEY_ALL
			   ,A.TOTAL_COUNT, A.CUST_TYPE_NAME, A.AGE, A.CARD_TYPE, A.FAX
		   ,A.TRUE_FLAG, A.TRUE_FLAG_NAME
			   , B.CLASS10
			   , B.CLASS11
			   ,B.CLASS12 AS CUST_LEVEL_NAME 
			   ,H.OP_NAME AS SERVICE_MAN_NAME
				FROM #TEMP_QUERY_TCustomersExt A 
				LEFT JOIN (SELECT CUST_ID,MAX([10]) AS CLASS10, MAX([11]) AS CLASS11, MAX([12]) AS CLASS12
									FROM TCustomerClass PIVOT(MAX(CLASSDETAIL_NAME) FOR CLASSDEFINE_ID IN ([10],[11],[12])) AS Z
									GROUP BY CUST_ID) AS B ON A.CUST_ID = B.CUST_ID
								 LEFT JOIN TOPERATOR H ON A.SERVICE_MAN = H.OP_CODE
								 LEFT JOIN TNUMBER_RELATION TR ON TR.FK_TCUSTOMERS = A.CUST_ID
								 LEFT JOIN 
									 (SELECT * FROM (select * , ROW_NUMBER() over(partition by CUST_ID order by INPUT_TIME DESC) as rownum 
										from TCUSTOMOERS_CONNECTION_MODI WHERE [STATUS] IN (' + @IN_CC_STATUS
										+ N')) em WHERE em.rownum=1) PR ON A.CUST_ID=PR.CUST_ID
									 WHERE 1=1 AND PR.SERIAL_NO IS NOT NULL'
		END

	   END
    --todo 添加 绑定号码 
    IF @IN_CLASSES <> ''
        SET @V_SQL = @V_SQL + ' AND EXISTS(SELECT 1 FROM #TMP_QUERY_CustByClass Z WHERE A.CUST_ID = Z.CUST_ID)'
    DECLARE @V_CUST_LEVEL_NAME NVARCHAR(30)
    SELECT @V_CUST_LEVEL_NAME = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = @IN_CUST_LEVEL
    IF ISNULL(@V_CUST_LEVEL_NAME,'') <> '' AND ISNULL(@V_CUST_LEVEL_NAME,'') = N'其它'
        SET @V_SQL = @V_SQL + ' AND (B.CLASS12 IS NULL) '
    ELSE IF ISNULL(@V_CUST_LEVEL_NAME,'') <> ''
        SET @V_SQL = @V_SQL + ' AND (B.CLASS12 = N''' + @V_CUST_LEVEL_NAME + ''') '
    IF ISNULL(@IN_CHANNEL_NAME,'') <> '' --如果输入参数[渠道名称]不为空，只返回与指定渠道有关的客户
        SET @V_SQL = @V_SQL + '
            AND EXISTS(SELECT 1 FROM INTRUST..TCONTRACT O, INTRUST..TCHANNEL P
                   WHERE O.CHANNEL_ID=P.CHANNEL_ID AND A.CUST_ID=O.CUST_ID AND P.CHANNEL_NAME LIKE ''%'+@IN_CHANNEL_NAME+'%'')'
	IF ISNULL(@IN_CITY_NAME,'') <> ''
		SET @V_SQL = @V_SQL + 
			' AND EXISTS(SELECT 1 FROM INTRUST..TCONTRACT O WHERE A.CUST_ID = O.CUST_ID AND O.CITY_NAME LIKE ''%'+@IN_CITY_NAME+'%'')'

    IF ISNULL(@IN_ORDERBY,'') <> ''
    BEGIN
        SET @V_SQL = @V_SQL + ' ORDER BY ' + @IN_ORDERBY
    END
    ELSE
    BEGIN
        SET @V_SQL = @V_SQL + ' ORDER BY A.CURRENT_MONEY DESC'
    END
    
    EXECUTE( @V_SQL )
    
    SELECT @IN_EXPORT_FLAG=@@ROWCOUNT
    --保存查询记录
    INSERT INTO TLOGQUERYCUST(QUERY_TIME,OP_CODE,IP,MAC,QUERY_TYPE,QUERY_TYPE_NAME,CUST_NUMBER)
		SELECT GETDATE(),@IN_INPUT_MAN,@IN_IP,@IN_MAC,@V_QUERY_TYPE,@V_QUERY_TYPE_NAME,@IN_EXPORT_FLAG
GO
