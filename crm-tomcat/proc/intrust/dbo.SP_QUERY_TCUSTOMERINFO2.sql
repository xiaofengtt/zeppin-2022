USE INTRUST
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCUSTOMERINFO2 @IN_BOOK_CODE              INTEGER,                        --1.账套
                                         @IN_CUST_ID                INTEGER,                        --2.客户ID
                                         @IN_CUST_NO                NVARCHAR(8),                    --3.客户编号
                                         @IN_CUST_NAME              NVARCHAR(100),                  --4.客户名称
                                         @IN_CUST_SOURCE            NVARCHAR(10),                   --5.客户来源
                                         @IN_CARD_TYPE              NVARCHAR(10),                   --6.证件类型
                                         @IN_CARD_ID                NVARCHAR(20),                   --7.证件号
                                         @IN_TOUCH_TYPE             NVARCHAR(10),                   --8.联系方式
                                         @IN_MIN_TIMES              INTEGER,                        --9.认购次数下限
                                         @IN_MAX_TIMES              INTEGER,                        --10.认购次数上限
                                         @IN_INPUT_MAN              INTEGER,                        --11.操作员
                                         @IN_TEL                    NVARCHAR(20)    = NULL,         --12.电话号码
                                         @IN_ADDRESS                NVARCHAR(60)    = NULL,         --13.地址
                                         @IN_CUST_TYPE              INTEGER         = NULL,         --14.客户类别
                                         @IN_PRODUCT_ID             INTEGER         = NULL,         --15.产品ID
                                         @IN_FAMILY_NAME            NVARCHAR(40)    = NULL,         --16.家庭名称
                                         @IN_ONLYEMAIL              INTEGER         = NULL,         --17.是否只返回有EMAIL的记录 1-是 0-否
                                         @IN_CUST_LEVEL             NVARCHAR(10)    = NULL,         --18.客户级别
                                         @IN_MIN_TOTAL_MONEY        MONEY           = NULL,         --19.认购金额下限
                                         @IN_MAX_TOTAL_MONEY        MONEY           = NULL,         --20认购金额上限
                                         @IN_ONLY_THISPRODUCT       INTEGER         = NULL,         --21.
                                         @IN_ORDERBY                NVARCHAR(100)   = NULL,         --22.排序字段
                                         @IN_BIRTHDAY               INTEGER         = NULL,         --23.查询出生日期的开始日期
                                         @IN_PRINT_DEPLOY_BILL      INTEGER         = NULL,         --24.是否打印客户对帐单（收益情况
                                         @IN_IS_LINK                INTEGER         = NULL,         --25.是否为关联方1是/2否
                                         @IN_PROV_LEVEL             NVARCHAR(10)    = NULL,         --26.收益级别
                                         @IN_BEN_AMOUNT_MIN         DECIMAL(16,3)   = NULL,         --27.受益份额下限
                                         @IN_BEN_AMOUNT_MAX         DECIMAL(16,3)   = NULL,         --28.受益份额上限
                                         @IN_VIP_CARD_ID            NVARCHAR(20)    = NULL,         --29.VIP卡编号
                                         @IN_HGTZR_BH               NVARCHAR(20)    = NULL,         --30.合格投资人编号
                                         @IN_WTR_FLAG               INTEGER         = 0,            --31.委托人标志：0 -- 委托人和受益人 1 受益人 2 委托人    针对只查询单一产品情况
                                         @IN_BIRTHDAY_END           INTEGER         = NULL,         --32.查询出生日期的结束日期
                                         @IN_BIRTHDAY_MONTH_BEGIN   INTEGER         = 0,            --33.生日从     月日
                                         @IN_BIRTHDAY_MONTH_END     INTEGER         = 0,            --34.生日到     月日
                                         @IN_COMPLETE_FLAG          INTEGER         = 0,            --35.资料完整
                                         @IN_CELL_FLAG              INTEGER         = 1,            --36.模式： 1 原来模式 2 产品单元模式
                                         @IN_CELL_ID                INTEGER         = 0,            --37.单元ID
                                         @IN_DEPART_ID              INTEGER         = 0,            --38.设计部门
                                         @IN_VAILD_START_DATE       INTEGER         = 0,            --39.证件到期查询起始日期
                                         @IN_VALID_END_DATE         INTEGER         = 0,            --40.证件到期查询结束日期
                                         @IN_AGE_BEGIN              INTEGER         = 0,            --41.年龄开始
                                         @IN_AGE_END                INTEGER         = 0,            --42.年龄结束
                                         @IN_SEX                    INTEGER         = 0,            --43.性别
                                         @IN_POST_ADDRESS_ID        NVARCHAR(10)    = NULL,         --44.
                                         @IN_RG_DATE_START          INTEGER         = NULL,         --45.最近购买日期查询起始日期
                                         @IN_RG_DATE_END            INTEGER         = NULL,         --46.最近购买日期查询结束日期
                                         @IN_RISK_LEVEL             INTEGER         = 0,            --47.风险等级
                                         @IN_SUB_PRODUCT_ID         INTEGER         = 0,            --48.子产品ID
                                         @IN_PROV_FLAG              INTEGER         = 0,            --49.受益优先级
                                         @IN_SERVICE_NAME           NVARCHAR(20)    = '',           --50.客户经理
                                         @IN_INPUT_START_DATE       INTEGER         = 0,            --51.录入时间开始日期
                                         @IN_INPUT_END_DATE         INTEGER         = 0             --52.录入时间结束日期
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_IS_FLAG INT, @V_SQL NVARCHAR(2000),@V_CONDITION_SQL NVARCHAR(600), @V_USER_ID INT,
            @V_CUST_ID INT,@V_STEMP NVARCHAR(2000),@V_PRODUCT_NAME NVARCHAR(60),@V_RG_MONEY DEC(16,3),
            @V_QUERYJG INT
    SELECT @V_USER_ID = USER_ID FROM TSYSTEMINFO WHERE SYSTEM_ID = 1
    SET @IN_BIRTHDAY_MONTH_BEGIN = ISNULL(@IN_BIRTHDAY_MONTH_BEGIN,0)
    IF ISNULL(@IN_BIRTHDAY_MONTH_END,0) =0
        SET @IN_BIRTHDAY_MONTH_END =1231
    IF ISNULL(@IN_BIRTHDAY_END,0) = 0
        SET @IN_BIRTHDAY_END = 30000101
    DECLARE @V_TEMPCUST1 TABLE(CUST_ID INT)
    DECLARE @V_TEMPCUST2 TABLE(CUST_ID INT)

    SELECT @V_QUERYJG = 0
    SELECT @V_QUERYJG = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = 'QUERYJG'
    IF ISNULL(@V_QUERYJG,0) = 0 SELECT @V_QUERYJG = 2
    SET @V_IS_FLAG = 0
    --能够访问所有客户信息
    IF EXISTS(SELECT 1 FROM TOPRIGHT WHERE OP_CODE = @IN_INPUT_MAN AND MENU_ID ='20409' AND FUNC_ID = 2049903)
        SET @V_IS_FLAG = 1
    --能够访问机构客户信息
    ELSE IF EXISTS(SELECT 1 FROM TOPRIGHT WHERE OP_CODE = @IN_INPUT_MAN AND MENU_ID ='20409' AND FUNC_ID = 2049913)
        SET @V_IS_FLAG = 2

    CREATE TABLE #TEMPCUST(
       CUST_ID          INT,                --客户ID
       CUST_NO          NVARCHAR (20),      --客户编号
       CUST_NAME        NVARCHAR (100),     --客户名称
       SEX              NVARCHAR(1),        --性别
       BIRTHDAY         INT,                --生日
       AGE              INT,                --年龄
       CUST_LEVEL_NAME  NVARCHAR(30),       --客户级别
       POST_ADDRESS     NVARCHAR(120),       --字段
       POST_CODE        NVARCHAR(10),       --委托人邮编
       CARD_ID          NVARCHAR (40),      --证件ID
       CUST_TEL         NVARCHAR (40),      --联系电话
       E_MAIL           NVARCHAR (40),      --E-MAIL
       MOBILE           NVARCHAR (100) ,    --手机
       BP               NVARCHAR (60) ,     --BP
       STATUS_NAME      NVARCHAR (30),      --客户状态说明
       LAST_RG_DATE     INT,                --最近一次购买时间
       CURR_TO_AMOUNT   DECIMAL(16,3),      --
       TOTAL_MONEY      DECIMAL (16, 3),    --已购买金额
       CURRENT_MONEY    DECIMAL (16, 3),    --存量金额
       BEN_AMOUNT       DECIMAL (16, 3),    --收益份额
       EXCHANGE_AMOUNT  DECIMAL (16, 3),    --受益权转让份额
       BACK_AMOUNT      DECIMAL (16, 3),    --到期份额
       SERVICE_MAN      INT,                --客户经理
       CUST_TYPE        INT,                --客户类别
       MODI_FLAG        INT DEFAULT 0,
       GRADE_LEVEL      NVARCHAR(10),       --客户风险等级             add by luoh 20090703
       GRADE_LEVEL_NAME NVARCHAR(30),       --客户风险等级说明       add by luoh 20090703
       CARD_TYPE_NAME   NVARCHAR(30),       --证件类型   add by luohh 20090728
       POST_ADDRESS2    NVARCHAR(120),       --委托人备用地址
       POST_CODE2       NVARCHAR(10),       --委托人备用邮编
       COMPLETE_FLAG    INT,                --资料完整
       PRODUCT_NAMES    NVARCHAR(2000),     --产品名称
       CARD_VALID_DATE  INT,                --客户证件到期日期     
       DEALNUM          INT                 --购买数量
    )

    DECLARE @V_CP TABLE(
        PRODUCT_ID   INT,
        PRODUCT_CODE NVARCHAR(20)
    )

    --选择要查询的产品
    IF @IN_CELL_FLAG = 1
    BEGIN
        INSERT INTO @V_CP(PRODUCT_ID,PRODUCT_CODE)
            SELECT PRODUCT_ID,PRODUCT_CODE
                FROM TPRODUCT
                WHERE BOOK_CODE = @IN_BOOK_CODE AND (PRODUCT_ID = @IN_PRODUCT_ID OR (ISNULL(@IN_PRODUCT_ID,0) = 0)) AND
                      (DEPART_ID = @IN_DEPART_ID OR ISNULL(@IN_DEPART_ID,0) = 0)
    END
    ELSE
    BEGIN
        CREATE TABLE #CELL_ALL(
            CELL_ID      INT,
            CELL_CODE    NVARCHAR(10),
            CELL_NAME    NVARCHAR(60),
            CELL_TYPE    INT,
            PRODUCT_ID   INT,
            PRODUCT_CODE NVARCHAR(20),
            PRODUCT_NAME NVARCHAR(60),
            BOOK_CODE    INTEGER
        )

        INSERT INTO #CELL_ALL(CELL_ID,CELL_CODE,CELL_NAME,PRODUCT_ID,PRODUCT_CODE,PRODUCT_NAME,CELL_TYPE)
            SELECT SERIAL_NO,CELL_CODE,CELL_NAME,PRODUCT_ID,PRODUCT_CODE,PRODUCT_NAME,CELL_TYPE
             FROM TPRODUCT_CELL
             WHERE SERIAL_NO = @IN_CELL_ID

        WHILE @@ROWCOUNT > 0
            INSERT INTO #CELL_ALL(CELL_ID)
                SELECT A.SUB_CELL_ID 
                    FROM TPRODUCT_CELL_DETAIL A, #CELL_ALL B
                    WHERE A.DF_SERIAL_NO = B.CELL_ID AND A.SUB_CELL_ID NOT IN(SELECT CELL_ID FROM #CELL_ALL)
        UPDATE #CELL_ALL 
            SET CELL_CODE    = B.CELL_CODE,
                CELL_NAME    = B.CELL_NAME,
                CELL_TYPE    = B.CELL_TYPE,
                PRODUCT_ID   = B.PRODUCT_ID,
                PRODUCT_CODE = B.PRODUCT_CODE,
                PRODUCT_NAME = B.PRODUCT_NAME,
                BOOK_CODE    = B.BOOK_CODE
            FROM #CELL_ALL A,TPRODUCT_CELL B
            WHERE A.CELL_ID = B.SERIAL_NO

        INSERT INTO @V_CP
            SELECT A.PRODUCT_ID,A.PRODUCT_CODE
                FROM #CELL_ALL A,TPRODUCT B
                WHERE A.CELL_TYPE = 1 AND A.PRODUCT_ID = B.PRODUCT_ID AND A.BOOK_CODE = @IN_BOOK_CODE AND
                     (B.DEPART_ID = @IN_DEPART_ID OR ISNULL(@IN_DEPART_ID,0) = 0)
                GROUP BY A.PRODUCT_ID,A.PRODUCT_CODE
    END
    --END

    IF ISNULL(@IN_CUST_ID,0) <> 0 BEGIN
        INSERT INTO #TEMPCUST
            SELECT A.CUST_ID,CUST_NO,CUST_NAME,CASE SEX WHEN 1 THEN N'男' ELSE N'女' END AS SEX,ISNULL(BIRTHDAY,0) AS BIRTHDAY,AGE,CUST_LEVEL_NAME,POST_ADDRESS,POST_CODE,
                   CARD_ID,CUST_TEL,E_MAIL,MOBILE,BP,STATUS_NAME,LAST_RG_DATE, 0,TOTAL_MONEY,CURRENT_MONEY,0,0,0,A.SERVICE_MAN,
                   CUST_TYPE,0,GRADE_LEVEL,GRADE_LEVEL_NAME,CARD_TYPE_NAME,POST_ADDRESS2,POST_CODE2,COMPLETE_FLAG,NULL AS PRODUCT_NAMES,CARD_VALID_DATE,DEALNUM
                FROM TCUSTOMERINFO A LEFT JOIN (SELECT CUST_ID,COUNT(CONTRACT_BH) AS DEALNUM FROM TCONTRACT GROUP BY CUST_ID) B ON B.CUST_ID = A.CUST_ID
                WHERE A.CUST_ID = @IN_CUST_ID
    END
    ELSE BEGIN
        IF ISNULL(@IN_PRODUCT_ID,0) = 0 AND @IN_CELL_FLAG = 1 BEGIN
            INSERT INTO #TEMPCUST
                SELECT A.CUST_ID,A.CUST_NO,A.CUST_NAME,CASE A.SEX WHEN 1 THEN N'男' ELSE N'女' END AS SEX,ISNULL(A.BIRTHDAY,0) AS BIRTHDAY,A.AGE,A.CUST_LEVEL_NAME,A.POST_ADDRESS,A.POST_CODE,
                       A.CARD_ID,A.CUST_TEL,A.E_MAIL,A.MOBILE,A.BP,A.STATUS_NAME,A.LAST_RG_DATE, 0,A.TOTAL_MONEY,A.CURRENT_MONEY,0,0,0,A.SERVICE_MAN,
                       A.CUST_TYPE,0,A.GRADE_LEVEL,A.GRADE_LEVEL_NAME,A.CARD_TYPE_NAME,A.POST_ADDRESS2,A.POST_CODE2,A.COMPLETE_FLAG,NULL AS PRODUCT_NAMES,A.CARD_VALID_DATE,B.DEALNUM
                    FROM TCUSTOMERINFO A LEFT JOIN (SELECT CUST_ID,COUNT(CONTRACT_BH) AS DEALNUM FROM TCONTRACT GROUP BY CUST_ID) B ON B.CUST_ID = A.CUST_ID
                                         LEFT JOIN TOPERATOR D ON A.SERVICE_MAN = D.OP_CODE
                    WHERE A.BOOK_CODE = @IN_BOOK_CODE
                        AND (ISNULL(@IN_CUST_ID,0) = 0 OR A.CUST_ID = @IN_CUST_ID)
                        AND (ISNULL(@IN_CUST_NO,'') = '' OR A.CUST_NO LIKE '%'+@IN_CUST_NO OR CUST_NO LIKE @IN_CUST_NO+N'_______')
                        AND (ISNULL(@IN_CUST_NAME,'') = '' OR A.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%')
                        AND (ISNULL(@IN_CUST_SOURCE,'') = '' OR A.CUST_SOURCE = @IN_CUST_SOURCE)
                        AND (ISNULL(@IN_CARD_TYPE,'') = '' OR A.CARD_TYPE = @IN_CARD_TYPE)
                        AND (ISNULL(@IN_TOUCH_TYPE,'') = '' OR A.TOUCH_TYPE = @IN_TOUCH_TYPE)
                        AND (A.CARD_ID LIKE '%'+@IN_CARD_ID+'%' OR @IN_CARD_ID = '' OR @IN_CARD_ID IS NULL)
                        AND (ISNULL(@IN_MAX_TIMES,0) = 0 OR (A.RG_TIMES BETWEEN @IN_MIN_TIMES AND @IN_MAX_TIMES) )
                        --操作员=客户资料录入人 或 有访问客户资料权限
                        AND (A.INPUT_MAN = @IN_INPUT_MAN OR A.SERVICE_MAN = @IN_INPUT_MAN OR @V_IS_FLAG = 1 OR (@V_IS_FLAG = 2 AND A.CUST_TYPE = 2) 
                            OR ( @V_QUERYJG = 1 AND A.CUST_TYPE = 2)
                            OR  A.CUST_ID IN (SELECT FUNC_ID FROM TOPRIGHT WHERE OP_CODE = @IN_INPUT_MAN AND MENU_ID = '21201') )
                        AND (ISNULL(A.CUST_TEL,'') + ',' + ISNULL(A.O_TEL,'') + ',' + ISNULL(A.H_TEL,'') + ',' + ISNULL(A.MOBILE,'') + ',' + ISNULL(A.BP,'') + ',' + ISNULL(FAX,'') LIKE '%'+@IN_TEL+'%' OR @IN_TEL = '' OR @IN_TEL IS NULL )
                        AND (A.POST_ADDRESS LIKE '%'+@IN_ADDRESS+'%' OR @IN_ADDRESS = '' OR @IN_ADDRESS IS NULL )
                        AND (ISNULL(@IN_CUST_TYPE,0) = 0 OR A.CUST_TYPE = @IN_CUST_TYPE)
                        AND (A.CUST_ID IN (SELECT DISTINCT CUST_ID FROM TCUSTFAMILYINFO WHERE FAMILY_NAME LIKE '%'+@IN_FAMILY_NAME+'%') OR ISNULL(@IN_FAMILY_NAME,'') = '')
                        AND (@IN_ONLYEMAIL IS NULL OR @IN_ONLYEMAIL <> 1 OR (@IN_ONLYEMAIL = 1 AND A.E_MAIL LIKE '%@%'))
                        AND (ISNULL(@IN_CUST_LEVEL,'') = '' OR A.CUST_LEVEL = @IN_CUST_LEVEL)
                        AND ((ISNULL(@IN_MIN_TOTAL_MONEY,0) =0 OR A.TOTAL_MONEY >= @IN_MIN_TOTAL_MONEY) AND (ISNULL(@IN_MAX_TOTAL_MONEY,0) =0 OR A.TOTAL_MONEY <= @IN_MAX_TOTAL_MONEY))
                        AND (ISNULL(@IN_BIRTHDAY,0) = 0 OR (ISNULL(A.BIRTHDAY,0) BETWEEN ISNULL(@IN_BIRTHDAY,0) AND ISNULL(@IN_BIRTHDAY_END,30000101)))  --modi by luohh
                        AND (ISNULL(@IN_BIRTHDAY,0) = 0 OR (ISNULL(A.BIRTHDAY,0)%10000 BETWEEN @IN_BIRTHDAY_MONTH_BEGIN AND @IN_BIRTHDAY_MONTH_END))  --add by luohh 20100427
                        AND (ISNULL(@IN_PRINT_DEPLOY_BILL,0) = 0 OR A.PRINT_DEPLOY_BILL = @IN_PRINT_DEPLOY_BILL)
                        AND (ISNULL(@IN_IS_LINK,0) = 0 OR A.IS_LINK = @IN_IS_LINK)
                        AND (ISNULL(@IN_VIP_CARD_ID,'') = '' OR A.VIP_CARD_ID LIKE '%'+@IN_VIP_CARD_ID+'%')
                        AND (ISNULL(@IN_HGTZR_BH,'') = '' OR A.HGTZR_BH LIKE '%'+@IN_HGTZR_BH+'%')
                        AND A.STATUS <> '112805'
                        AND (ISNULL(@IN_COMPLETE_FLAG,0) = 0 OR (@IN_COMPLETE_FLAG = 2 AND (ISNULL(A.COMPLETE_FLAG,0) = 0 OR A.COMPLETE_FLAG = 2)) OR (@IN_COMPLETE_FLAG = 1 AND A.COMPLETE_FLAG = 1))
                        AND (ISNULL(A.CARD_VALID_DATE,0) BETWEEN ISNULL(@IN_VAILD_START_DATE,0) AND CASE WHEN ISNULL(@IN_VALID_END_DATE,30000101) = 30000101 THEN 99999999 ELSE @IN_VALID_END_DATE END )
                        AND ((ISNULL(@IN_AGE_BEGIN,0) = 0 AND ISNULL(@IN_AGE_END,0) = 0) OR (ISNULL(A.AGE,0) BETWEEN ISNULL(@IN_AGE_BEGIN,0) AND ISNULL(@IN_AGE_END,1000)))
                        AND (ISNULL(@IN_POST_ADDRESS_ID,'') = '' OR A.GOV_PROV_REGIONAL = @IN_POST_ADDRESS_ID OR A.GOV_REGIONAL = @IN_POST_ADDRESS_ID)
                        AND (ISNULL(@IN_SEX,0) = 0 OR A.SEX = @IN_SEX)
                        AND (D.OP_NAME LIKE N'%' + @IN_SERVICE_NAME + N'%' OR ISNULL(@IN_SERVICE_NAME,'') = '')
                        AND ((CONVERT(INTEGER,CONVERT(NVARCHAR,A.INPUT_TIME,112)) >= @IN_INPUT_START_DATE OR ISNULL(@IN_INPUT_START_DATE,0) = 0) AND
                             (CONVERT(INTEGER,CONVERT(NVARCHAR,A.INPUT_TIME,112)) <= @IN_INPUT_END_DATE OR ISNULL(@IN_INPUT_END_DATE,0) = 0))   
        END
        ELSE BEGIN
            IF @IN_WTR_FLAG = 0 BEGIN
                --委托人
                INSERT INTO @V_TEMPCUST1
                    SELECT A.CUST_ID 
                        FROM TCONTRACT A,@V_CP B
                        WHERE  A.PRODUCT_ID= B.PRODUCT_ID AND A.HT_STATUS <> '120104' AND
                            (A.PROV_LEVEL = @IN_PROV_LEVEL OR ISNULL(@IN_PROV_LEVEL,'') = '') AND
                            (ISNULL(@IN_SUB_PRODUCT_ID,0) = 0 OR SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID) AND
                            (ISNULL(@IN_PROV_FLAG,0) = 0 OR PROV_FLAG = @IN_PROV_FLAG)
                        GROUP BY A.CUST_ID
                --受益人
                UNION
                    SELECT A.CUST_ID FROM TBENIFITOR A,@V_CP B
                        WHERE A.PRODUCT_ID = B.PRODUCT_ID AND A.BEN_STATUS <> '121104' AND
                              (A.PROV_LEVEL = @IN_PROV_LEVEL OR ISNULL(@IN_PROV_LEVEL,'') = '') AND
                              (ISNULL(@IN_SUB_PRODUCT_ID,0) = 0 OR SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID) AND
                              (ISNULL(@IN_PROV_FLAG,0) = 0 OR PROV_FLAG = @IN_PROV_FLAG)
                        GROUP BY A.CUST_ID
                UNION
                SELECT A.CUST_ID FROM TPRECONTRACT A,@V_CP B
                    WHERE A.PRODUCT_ID = B.PRODUCT_ID AND A.PRE_STATUS='111301' AND
                            (ISNULL(@IN_SUB_PRODUCT_ID,0) = 0 OR SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID)
                    GROUP BY A.CUST_ID

                --INSERT INTO @V_TEMPCUST1 SELECT CUST_ID FROM @V_TEMPCUST2 WHERE CUST_ID NOT IN(SELECT CUST_ID FROM @V_TEMPCUST1)
            END
            ELSE IF @IN_WTR_FLAG = 1 BEGIN
                --受益人
                INSERT INTO @V_TEMPCUST1
                    SELECT A.CUST_ID FROM TBENIFITOR A,@V_CP B
                        WHERE A.PRODUCT_ID = B.PRODUCT_ID AND A.BEN_STATUS <> '121104' AND
                              (A.PROV_LEVEL = @IN_PROV_LEVEL OR ISNULL(@IN_PROV_LEVEL,'') = '') AND
                              (ISNULL(@IN_SUB_PRODUCT_ID,0) = 0 OR SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID) AND
                              (ISNULL(@IN_PROV_FLAG,0) = 0 OR PROV_FLAG = @IN_PROV_FLAG)
                        GROUP BY A.CUST_ID
                UNION
                SELECT A.CUST_ID FROM TPRECONTRACT A,@V_CP B
                    WHERE A.PRODUCT_ID = B.PRODUCT_ID AND A.PRE_STATUS='111301' AND
                            (ISNULL(@IN_SUB_PRODUCT_ID,0) = 0 OR SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID)
                    GROUP BY A.CUST_ID
            END
            ELSE BEGIN
                --委托人
                INSERT INTO @V_TEMPCUST1
                    SELECT CUST_ID FROM TCONTRACT A,@V_CP B
                        WHERE A.PRODUCT_ID = B.PRODUCT_ID AND A.HT_STATUS <> '120104' AND
                            (A.PROV_LEVEL = @IN_PROV_LEVEL OR ISNULL(@IN_PROV_LEVEL,'') = '') AND
                            (ISNULL(@IN_SUB_PRODUCT_ID,0) = 0 OR SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID) AND
                            (ISNULL(@IN_PROV_FLAG,0) = 0 OR PROV_FLAG = @IN_PROV_FLAG)
                        GROUP BY A.CUST_ID
                UNION
                SELECT A.CUST_ID FROM TPRECONTRACT A,@V_CP B
                    WHERE A.PRODUCT_ID = B.PRODUCT_ID AND A.PRE_STATUS='111301' AND
                            (ISNULL(@IN_SUB_PRODUCT_ID,0) = 0 OR SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID)
                    GROUP BY A.CUST_ID
            END
            --预约客户
            --DELETE FROM @V_TEMPCUST2
            /*INSERT INTO @V_TEMPCUST2
                SELECT A.CUST_ID FROM TPRECONTRACT A
                    WHERE EXISTS(SELECT 1 FROM @V_CP B WHERE A.PRODUCT_ID = B.PRODUCT_ID) AND A.PRE_STATUS='111301' AND
                            (ISNULL(@IN_SUB_PRODUCT_ID,0) = 0 OR SUB_PRODUCT_ID = @IN_SUB_PRODUCT_ID)
                    GROUP BY A.CUST_ID
            INSERT INTO @V_TEMPCUST1
                SELECT CUST_ID FROM @V_TEMPCUST2
                    WHERE CUST_ID NOT IN(SELECT CUST_ID FROM @V_TEMPCUST1) */

            INSERT INTO #TEMPCUST
                SELECT A.CUST_ID,A.CUST_NO,A.CUST_NAME,CASE A.SEX WHEN 1 THEN N'男' ELSE N'女' END AS SEX,ISNULL(A.BIRTHDAY,0) AS BIRTHDAY,A.AGE,A.CUST_LEVEL_NAME,A.POST_ADDRESS,A.POST_CODE,
                       A.CARD_ID,A.CUST_TEL,A.E_MAIL,A.MOBILE,A.BP,A.STATUS_NAME,A.LAST_RG_DATE, 0,A.TOTAL_MONEY,A.CURRENT_MONEY,0,0,0,A.SERVICE_MAN,
                       A.CUST_TYPE,0,A.GRADE_LEVEL,A.GRADE_LEVEL_NAME,A.CARD_TYPE_NAME,A.POST_ADDRESS2,A.POST_CODE2,A.COMPLETE_FLAG,NULL AS PRODUCT_NAMES,A.CARD_VALID_DATE,C.DEALNUM
                    FROM TCUSTOMERINFO A INNER JOIN @V_TEMPCUST1 B ON A.CUST_ID = B.CUST_ID
                                         LEFT JOIN (SELECT C1.CUST_ID,COUNT(CONTRACT_BH) AS DEALNUM FROM TCONTRACT C1,@V_TEMPCUST1 C2 WHERE C1.CUST_ID = C2.CUST_ID  GROUP BY C1.CUST_ID) C ON C.CUST_ID = A.CUST_ID
                                         LEFT JOIN TOPERATOR D ON A.SERVICE_MAN = D.OP_CODE
                    WHERE A.BOOK_CODE = @IN_BOOK_CODE
                        AND (A.CUST_ID = @IN_CUST_ID OR @IN_CUST_ID = 0 OR @IN_CUST_ID IS NULL)
                        AND (A.CUST_NO LIKE '%'+@IN_CUST_NO OR A.CUST_NO LIKE @IN_CUST_NO+N'_______' OR @IN_CUST_NO = '' OR @IN_CUST_NO IS NULL)
                        AND (A.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%' OR @IN_CUST_NAME = '' OR @IN_CUST_NAME IS NULL)
                        AND (A.CUST_SOURCE = @IN_CUST_SOURCE OR @IN_CUST_SOURCE = '' OR @IN_CUST_SOURCE IS NULL)
                        AND (A.CARD_TYPE = @IN_CARD_TYPE OR @IN_CARD_TYPE = '' OR @IN_CARD_TYPE IS NULL)
                        AND (A.TOUCH_TYPE = @IN_TOUCH_TYPE OR @IN_TOUCH_TYPE = '' OR @IN_TOUCH_TYPE IS NULL)
                        AND (A.CARD_ID LIKE '%'+@IN_CARD_ID+'%' OR @IN_CARD_ID = '' OR @IN_CARD_ID IS NULL)
                        AND ((A.RG_TIMES BETWEEN @IN_MIN_TIMES AND @IN_MAX_TIMES) OR @IN_MAX_TIMES IS NULL OR @IN_MAX_TIMES = 0 )
                        --操作员=客户资料录入人 或 有访问客户资料权限
                        AND (A.INPUT_MAN = @IN_INPUT_MAN OR A.SERVICE_MAN = @IN_INPUT_MAN OR @V_IS_FLAG = 1 OR (@V_IS_FLAG = 2 AND A.CUST_TYPE = 2)
                                OR ( @V_QUERYJG = 1 AND A.CUST_TYPE = 2) )
                        --当输入了产品时，只返回该产品签约客户资料
                        --AND A.CUST_ID=B.CUST_ID
                        AND (ISNULL(A.CUST_TEL,'') + ',' + ISNULL(A.O_TEL,'') + ',' + ISNULL(A.H_TEL,'') + ',' + ISNULL(A.MOBILE,'') + ',' + ISNULL(A.BP,'') + ',' + ISNULL(A.FAX,'') LIKE '%'+@IN_TEL+'%' OR @IN_TEL = '' OR @IN_TEL IS NULL )
                        AND (A.POST_ADDRESS LIKE '%'+@IN_ADDRESS+'%' OR @IN_ADDRESS = '' OR @IN_ADDRESS IS NULL )
                        AND (A.CUST_TYPE = @IN_CUST_TYPE OR @IN_CUST_TYPE = 0 OR @IN_CUST_TYPE IS NULL)
                        AND (A.CUST_ID IN (SELECT DISTINCT CUST_ID FROM TCUSTFAMILYINFO WHERE FAMILY_NAME LIKE '%'+@IN_FAMILY_NAME+'%') OR @IN_FAMILY_NAME IS NULL OR @IN_FAMILY_NAME = '')
                        AND (@IN_ONLYEMAIL IS NULL OR @IN_ONLYEMAIL <> 1 OR (@IN_ONLYEMAIL = 1 AND A.E_MAIL LIKE '%@%'))
                        AND (@IN_CUST_LEVEL IS NULL OR @IN_CUST_LEVEL = '' OR A.CUST_LEVEL = @IN_CUST_LEVEL)
                        AND ((ISNULL(@IN_MIN_TOTAL_MONEY,0) =0 OR A.TOTAL_MONEY >= @IN_MIN_TOTAL_MONEY) AND (ISNULL(@IN_MAX_TOTAL_MONEY,0) =0 OR A.TOTAL_MONEY <= @IN_MAX_TOTAL_MONEY))
                        AND (ISNULL(@IN_BIRTHDAY,0) = 0 OR (ISNULL(A.BIRTHDAY,0) BETWEEN ISNULL(@IN_BIRTHDAY,0) AND ISNULL(@IN_BIRTHDAY_END,30000101)))
                        AND (ISNULL(@IN_BIRTHDAY,0) = 0 OR (ISNULL(A.BIRTHDAY,0)%10000 BETWEEN @IN_BIRTHDAY_MONTH_BEGIN AND @IN_BIRTHDAY_MONTH_END))
                        AND (A.PRINT_DEPLOY_BILL = @IN_PRINT_DEPLOY_BILL OR ISNULL(@IN_PRINT_DEPLOY_BILL,0) = 0)
                        AND (A.IS_LINK = @IN_IS_LINK OR ISNULL(@IN_IS_LINK,0) = 0)
                        AND (A.VIP_CARD_ID LIKE '%'+@IN_VIP_CARD_ID+'%' OR ISNULL(@IN_VIP_CARD_ID,'') = '')
                        AND (A.HGTZR_BH LIKE '%'+@IN_HGTZR_BH+'%' OR ISNULL(@IN_HGTZR_BH,'') = '')
                        AND A.STATUS <> '112805'
                        AND (ISNULL(@IN_COMPLETE_FLAG,0) = 0 OR (@IN_COMPLETE_FLAG = 2 AND (ISNULL(A.COMPLETE_FLAG,0) = 0 OR A.COMPLETE_FLAG = 2)) OR (@IN_COMPLETE_FLAG = 1 AND A.COMPLETE_FLAG = 1))
                        AND (ISNULL(A.CARD_VALID_DATE,0) BETWEEN ISNULL(@IN_VAILD_START_DATE,0) AND CASE WHEN ISNULL(@IN_VALID_END_DATE,30000101) = 30000101 THEN 99999999 ELSE @IN_VALID_END_DATE END )
                        AND ((ISNULL(@IN_AGE_BEGIN,0) = 0 AND ISNULL(@IN_AGE_END,0) = 0) OR (ISNULL(A.AGE,0) BETWEEN ISNULL(@IN_AGE_BEGIN,0) AND ISNULL(@IN_AGE_END,1000)))
                        AND (ISNULL(@IN_POST_ADDRESS_ID,'') = '' OR A.GOV_PROV_REGIONAL = @IN_POST_ADDRESS_ID OR A.GOV_REGIONAL = @IN_POST_ADDRESS_ID)
                        AND (ISNULL(@IN_SEX,0) = 0 OR A.SEX = @IN_SEX)
                        AND (D.OP_NAME LIKE N'%' + @IN_SERVICE_NAME + N'%' OR ISNULL(@IN_SERVICE_NAME,'') = '')
                        AND ((CONVERT(INTEGER,CONVERT(NVARCHAR,A.INPUT_TIME,112)) >= @IN_INPUT_START_DATE OR ISNULL(@IN_INPUT_START_DATE,0) = 0) AND
                             (CONVERT(INTEGER,CONVERT(NVARCHAR,A.INPUT_TIME,112)) <= @IN_INPUT_END_DATE OR ISNULL(@IN_INPUT_END_DATE,0) = 0))
        END
    END
    /*
    --这段内容不对, 先屏蔽 modi by jinxr 2011/6/14
    --认购产品明细
    DECLARE CUR_CUST_CONTRACT CURSOR FOR SELECT CUST_ID FROM #TEMPCUST
    OPEN CUR_CUST_CONTRACT
    FETCH CUR_CUST_CONTRACT INTO @V_CUST_ID
    WHILE @@FETCH_STATUS = 0 BEGIN
        SELECT @V_STEMP = ''
        SELECT @V_STEMP = @V_STEMP +  B.PRODUCT_NAME + ','
            FROM TBENIFITOR A, TPRODUCT B
            WHERE A.PRODUCT_ID = B.PRODUCT_ID AND A.CUST_ID = @V_CUST_ID
            GROUP BY B.PRODUCT_NAME, A.CUST_ID

        UPDATE #TEMPCUST SET PRODUCT_NAMES = LTRIM(RTRIM(@V_STEMP)) WHERE CUST_ID = @V_CUST_ID
        FETCH CUR_CUST_CONTRACT INTO @V_CUST_ID
    END
    CLOSE CUR_CUST_CONTRACT
    DEALLOCATE CUR_CUST_CONTRACT
    */

    --受让金额
    CREATE TABLE #TEMPCUST3(
        CUST_ID INT,
        TO_AMOUNT DECIMAL(16,2)
    )
    INSERT INTO #TEMPCUST3
        SELECT A.TO_CUST_ID,SUM(A.TO_AMOUNT) FROM TBENCHANGES A
            WHERE A.BOOK_CODE = @IN_BOOK_CODE AND A.CHECK_FLAG = 2 AND A.TRANS_TYPE NOT IN('181511','181512','181513','181514') AND
                 EXISTS(SELECT 1 FROM @V_CP B WHERE A.PRODUCT_ID = B.PRODUCT_ID) AND
                 EXISTS(SELECT 1 FROM #TEMPCUST C WHERE A.TO_CUST_ID = C.CUST_ID)
            GROUP BY A.TO_CUST_ID

    UPDATE #TEMPCUST
        SET EXCHANGE_AMOUNT = B.TO_AMOUNT
        FROM #TEMPCUST A, #TEMPCUST3 B
        WHERE A.CUST_ID = B.CUST_ID

    --转让金额
    DELETE FROM #TEMPCUST3
    INSERT INTO #TEMPCUST3
        SELECT A.FROM_CUST_ID,SUM(A.TO_AMOUNT) FROM TBENCHANGES A
            WHERE A.BOOK_CODE = @IN_BOOK_CODE AND A.CHECK_FLAG = 2 AND A.TRANS_TYPE NOT IN('181511','181512','181513','181514') AND
                EXISTS(SELECT 1 FROM @V_CP B WHERE A.PRODUCT_ID = B.PRODUCT_ID) AND
                EXISTS(SELECT 1 FROM #TEMPCUST C WHERE A.FROM_CUST_ID = C.CUST_ID)
            GROUP BY A.FROM_CUST_ID

    UPDATE #TEMPCUST
        SET EXCHANGE_AMOUNT = ISNULL(EXCHANGE_AMOUNT,0) - B.TO_AMOUNT
        FROM #TEMPCUST A, #TEMPCUST3 B
        WHERE A.CUST_ID = B.CUST_ID

    --到期本金
    DELETE FROM #TEMPCUST3
    INSERT INTO #TEMPCUST3
        SELECT A.CUST_ID,SUM(A.SY_AMOUNT)
            FROM TDEPLOY A
            WHERE A.BOOK_CODE = @IN_BOOK_CODE AND A.SY_TYPE = '111605' AND A.PZ_FLAG = 2 AND
                  EXISTS(SELECT 1 FROM @V_CP B WHERE A.PRODUCT_ID = B.PRODUCT_ID) AND
                  EXISTS(SELECT 1 FROM #TEMPCUST C WHERE A.CUST_ID = C.CUST_ID)
            GROUP BY A.CUST_ID
    UPDATE #TEMPCUST
        SET BACK_AMOUNT = B.TO_AMOUNT
        FROM #TEMPCUST A, #TEMPCUST3 B
        WHERE A.CUST_ID = B.CUST_ID

    --总受益份额
    CREATE TABLE #TEMPCUST4(
        CUST_ID    INT,
        BEN_MONEY  DEC(16,2),
        BEN_AMOUNT DECIMAL(16,2),
        TO_AMOUNT  DECIMAL(16,2),
        MAX_DATE   INT
    )
    --存量金额 按照 受益金额来统计
    INSERT INTO #TEMPCUST4
        SELECT A.CUST_ID,SUM(ISNULL(A.BEN_MONEY,0)),SUM(ISNULL(A.BEN_AMOUNT,0)),SUM(ISNULL(A.TO_AMOUNT,0)),MAX(A.BEN_DATE)
            FROM TBENIFITOR A
            WHERE A.BOOK_CODE = @IN_BOOK_CODE AND A.CHECK_FLAG = 2 AND 
                  EXISTS(SELECT 1 FROM @V_CP B WHERE A.PRODUCT_ID = B.PRODUCT_ID) AND
                  EXISTS(SELECT 1 FROM #TEMPCUST C WHERE A.CUST_ID = C.CUST_ID)
            GROUP BY A.CUST_ID
    UPDATE #TEMPCUST
        SET CURRENT_MONEY = B.BEN_MONEY,
            BEN_AMOUNT = B.BEN_AMOUNT,
            TOTAL_MONEY = B.TO_AMOUNT,
            LAST_RG_DATE = B.MAX_DATE
        FROM #TEMPCUST A, #TEMPCUST4 B
        WHERE A.CUST_ID = B.CUST_ID

    IF @IN_PRODUCT_ID <> 0
    BEGIN
        DELETE FROM #TEMPCUST3
            INSERT INTO #TEMPCUST3
                SELECT CUST_ID,SUM(ISNULL(TO_AMOUNT,0))
                    FROM TBENIFITOR  A
                    WHERE CHECK_FLAG = 2 AND PRODUCT_ID = @IN_PRODUCT_ID  AND
                          EXISTS(SELECT 1 FROM #TEMPCUST C WHERE A.CUST_ID = C.CUST_ID)
                    GROUP BY CUST_ID
        UPDATE #TEMPCUST
            SET CURR_TO_AMOUNT = B.TO_AMOUNT
            FROM #TEMPCUST A,#TEMPCUST3 B
            WHERE A.CUST_ID = B.CUST_ID
    END

    --如果有客户访问权限控制，则执行客户信息加密
    IF EXISTS(SELECT 1 FROM TSYSCONTROL WHERE FLAG_TYPE = 'CUSTRIGHT' AND VALUE = 1) AND (@V_IS_FLAG = 1)
    BEGIN
       --修改有有权限的显示
        UPDATE #TEMPCUST
            SET MODI_FLAG = ISNULL(dbo.GETCUSTRIGHT(CUST_ID,@IN_INPUT_MAN),0)
        
        IF  EXISTS(SELECT 1 FROM TSYSTEMINFO WHERE USER_ID IN (7,24))
            --国联 只加密 电话 且允许访问所有客户
            UPDATE #TEMPCUST
                SET CUST_TEL = replace(N'******',CUST_TEL,CUST_TEL),
                    E_MAIL   = replace(N'******',E_MAIL,E_MAIL),
                    MOBILE   = replace(N'******',MOBILE,MOBILE),
                    BP       = replace(N'******',BP,BP)
                WHERE MODI_FLAG <> 1
        ELSE
        BEGIN
            UPDATE #TEMPCUST
                SET CARD_ID  = replace(N'******',CARD_ID,CARD_ID),
                    CUST_TEL = replace(N'******',CUST_TEL,CUST_TEL),
                    E_MAIL   = replace(N'******',E_MAIL,E_MAIL),
                    MOBILE   = replace(N'******',MOBILE,MOBILE),
                    BP       = replace(N'******',BP,BP)
                WHERE MODI_FLAG <> 1
            --删除没有权限的客户
            DELETE FROM #TEMPCUST WHERE MODI_FLAG = 0
        END
    END

    SELECT A.*,B.RISK_LEVEL INTO #TEMP_QUERY_TCUSTOMERINFO3 FROM #TEMPCUST A LEFT JOIN TAMCUSTINFO B ON B.CUST_ID = A.CUST_ID
        WHERE (ISNULL(@IN_BEN_AMOUNT_MIN,0) = 0 OR A.BEN_AMOUNT >= ISNULL(@IN_BEN_AMOUNT_MIN,0))
          AND (ISNULL(@IN_BEN_AMOUNT_MAX,0) = 0 OR A.BEN_AMOUNT <= ISNULL(@IN_BEN_AMOUNT_MAX,0))
          AND (ISNULL(A.LAST_RG_DATE,0)>= ISNULL(@IN_RG_DATE_START,0) AND ISNULL(A.LAST_RG_DATE,0) <=ISNULL(@IN_RG_DATE_END,30111231))
          AND (ISNULL(@IN_RISK_LEVEL,0) = 0 OR B.RISK_LEVEL = @IN_RISK_LEVEL)
    IF @V_USER_ID = 2 BEGIN --20100422 dongyg cust_no去掉V开头后，北国投还是要求能明确看出VIP客户，所以在结果集中cust_no前加上个VIP
        UPDATE #TEMP_QUERY_TCUSTOMERINFO3 SET CUST_NO = 'VIP' + CUST_NO WHERE CUST_LEVEL_NAME LIKE '%VIP%'
    END
    
    
    IF EXISTS(SELECT 1 FROM TSYSTEMINFO WHERE USER_ID = 24) BEGIN
        DECLARE @V_XML XML,@V_CONDITION XML
        DECLARE @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40),@SSUMMARY NVARCHAR(200)

        SELECT @V_CONDITION = (SELECT @IN_BOOK_CODE AS IN_BOOK_CODE, @IN_CUST_ID AS IN_CUST_ID, @IN_CUST_NO AS IN_CUST_NO, @IN_CUST_NAME AS IN_CUST_NAME, 
                                      @IN_CUST_SOURCE AS IN_CUST_SOURCE, @IN_CARD_TYPE AS IN_CARD_TYPE, @IN_CARD_ID AS IN_CARD_ID, @IN_TOUCH_TYPE AS IN_TOUCH_TYPE, 
                                      @IN_MIN_TIMES AS IN_MIN_TIMES, @IN_MAX_TIMES AS IN_MAX_TIMES, @IN_INPUT_MAN AS IN_INPUT_MAN, @IN_TEL AS IN_TEL, 
                                      @IN_ADDRESS AS IN_ADDRESS, @IN_CUST_TYPE AS IN_CUST_TYPE, @IN_PRODUCT_ID AS IN_PRODUCT_ID, @IN_FAMILY_NAME AS IN_FAMILY_NAME, 
                                      @IN_ONLYEMAIL AS IN_ONLYEMAIL, @IN_CUST_LEVEL AS IN_CUST_LEVEL, @IN_MIN_TOTAL_MONEY AS IN_MIN_TOTAL_MONEY, 
                                      @IN_MAX_TOTAL_MONEY AS IN_MAX_TOTAL_MONEY, @IN_ONLY_THISPRODUCT AS IN_ONLY_THISPRODUCT, @IN_ORDERBY AS IN_ORDERBY, 
                                      @IN_BIRTHDAY AS IN_BIRTHDAY, @IN_PRINT_DEPLOY_BILL AS IN_PRINT_DEPLOY_BILL, @IN_IS_LINK AS IN_IS_LINK, 
                                      @IN_PROV_LEVEL AS IN_PROV_LEVEL, @IN_BEN_AMOUNT_MIN AS IN_BEN_AMOUNT_MIN, @IN_BEN_AMOUNT_MAX AS IN_BEN_AMOUNT_MAX, 
                                      @IN_VIP_CARD_ID AS IN_VIP_CARD_ID, @IN_HGTZR_BH AS IN_HGTZR_BH, @IN_WTR_FLAG AS IN_WTR_FLAG, @IN_BIRTHDAY_END AS IN_BIRTHDAY_END, 
                                      @IN_BIRTHDAY_MONTH_BEGIN AS IN_BIRTHDAY_MONTH_BEGIN, @IN_BIRTHDAY_MONTH_END AS IN_BIRTHDAY_MONTH_END, @IN_COMPLETE_FLAG AS IN_COMPLETE_FLAG, 
                                      @IN_CELL_FLAG AS IN_CELL_FLAG, @IN_CELL_ID AS IN_CELL_ID, @IN_DEPART_ID AS IN_DEPART_ID, @IN_VAILD_START_DATE AS IN_VAILD_START_DATE, 
                                      @IN_VALID_END_DATE AS IN_VALID_END_DATE, @IN_AGE_BEGIN AS IN_AGE_BEGIN, @IN_AGE_END AS IN_AGE_END, @IN_SEX AS IN_SEX, 
                                      @IN_POST_ADDRESS_ID AS IN_POST_ADDRESS_ID, @IN_RG_DATE_START AS IN_RG_DATE_START, @IN_RG_DATE_END AS IN_RG_DATE_END, 
                                      @IN_RISK_LEVEL AS IN_RISK_LEVEL, @IN_SUB_PRODUCT_ID AS IN_SUB_PRODUCT_ID, @IN_PROV_FLAG AS IN_PROV_FLAG, 
                                      @IN_SERVICE_NAME AS IN_SERVICE_NAME, @IN_INPUT_START_DATE AS IN_INPUT_START_DATE, @IN_INPUT_END_DATE AS IN_INPUT_END_DATE   FOR XML RAW,ROOT)
        
        --SELECT @V_XML = (SELECT * FROM #TEMP_QUERY_TCUSTOMERINFO3 FOR XML RAW,ROOT) 

        SELECT @IBUSI_FLAG = 29900,@SBUSI_NAME = '客户资料查询'
        SELECT @SSUMMARY = '客户资料查询'
        INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY,CONDITION_XML)
            VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY,@V_CONDITION)
    END
    
    IF ISNULL(@IN_ORDERBY,'') <> '' BEGIN
        SELECT @V_SQL = 'SELECT * FROM #TEMP_QUERY_TCUSTOMERINFO3 ORDER BY ' + @IN_ORDERBY
        EXECUTE( @V_SQL )
    END
    ELSE BEGIN
        SELECT * FROM #TEMP_QUERY_TCUSTOMERINFO3 ORDER BY CURRENT_MONEY DESC
    END
GO
