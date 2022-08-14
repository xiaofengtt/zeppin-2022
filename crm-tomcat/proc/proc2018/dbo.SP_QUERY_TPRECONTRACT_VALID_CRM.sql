﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TPRECONTRACT_VALID_CRM @IN_BOOK_CODE       INT,
                                                 @IN_PRODUCT_ID      INT,
                                                 @IN_PRE_CODE        NVARCHAR(16),
                                                 @IN_LINK_MAN        INT = NULL,
                                                 @IN_INPUT_MAN       INT,
                                                 @IN_CUST_NAME       NVARCHAR(100) = '',
                                                 @IN_CUST_TYPE       INT=0,                  --客户类型
                                                 @IN_MAX_PRE_MONEY   DECIMAL(16,3)=NULL,     --最大预约金额
                                                 @IN_MIN_PRE_MONEY   DECIMAL(16,3)=NULL,     --最小预约金额
                                                 @IN_CUST_SOURCE     NVARCHAR(10)=NULL,      --客户来源
                                                 @IN_GroupID         INTEGER=NULL,           --客户分组序号
                                                 @IN_CLASSDETAIL_ID  INTEGER=NULL,           --客户分级ID
                                                 @IN_MONEY_STATUS    INT = NULL             --缴款状态 0全部 1已缴款 2未缴款
WITH ENCRYPTION
AS
    SET NOCOUNT ON

    DECLARE @V_DT_INTRUST INT,@V_GroupName NVARCHAR(120),@V_CLASSDETAIL_NAME NVARCHAR(120)
    SELECT @V_DT_INTRUST = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'DT_INTRUST'

    DECLARE @V_SW20409 INT
    DECLARE @V_PRODUCTS TABLE(PRODUCT_ID INT)
    DECLARE @V_IS_FLAG INT,@V_PRE_CODE NVARCHAR(16),@V_PRE_MONEY DECIMAL(16,3),@V_PRE_NUM INT,
                @V_PRODUCT_ID INT,@V_RG_NUM INT,@V_RG_MONEY DECIMAL(16,3)
    SET @V_GroupName = '';

    IF ISNULL(@IN_GroupID,0)<>0
    BEGIN
        SELECT @V_GroupName = GroupName FROM TCustGroups WHERE  GroupID = @IN_GroupID
    END

    IF ISNULL(@IN_CLASSDETAIL_ID,0)<>0
    BEGIN
        SELECT @V_CLASSDETAIL_NAME = CLASSDETAIL_NAME FROM TCustClassDetail WHERE  CLASSDETAIL_ID = @IN_CLASSDETAIL_ID
    END

    CREATE TABLE #TPRECONTRACT
    (
        PRODUCT_ID INT,
        PRE_CODE NVARCHAR(16)
     )

    BEGIN
        SELECT @V_SW20409 = VALUE FROM INTRUST..TSYSCONTROL WHERE FLAG_TYPE = 'SW20409'
        IF @V_SW20409 IS NULL SELECT @V_SW20409 = 0
        IF @V_SW20409 = 1
            INSERT INTO @V_PRODUCTS
                SELECT PRODUCT_ID FROM INTRUST..TPRODUCT WHERE BOOK_CODE = @IN_BOOK_CODE
                    AND(INTRUST_FLAG1 IN ( SELECT FUNC_ID-2040900 FROM INTRUST..TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID > 2040900 AND FUNC_ID <= 2040999 AND OP_CODE = @IN_INPUT_MAN )
                        OR PRODUCT_ID IN ( SELECT FUNC_ID FROM INTRUST..TOPRIGHT WHERE MENU_ID = '20409' AND FUNC_ID < 2040900 AND OP_CODE = @IN_INPUT_MAN)
                        OR ADMIN_MANAGER = @IN_INPUT_MAN OR INPUT_MAN = @IN_INPUT_MAN OR ISNULL(@IN_INPUT_MAN,0)=0 )
        --20409: The Products can be accessed. END
        SELECT @V_IS_FLAG = 0
        --能够访问所有预约信息
        IF EXISTS(SELECT 1 FROM INTRUST..TOPRIGHT WHERE OP_CODE = @IN_INPUT_MAN AND MENU_ID ='20409' AND FUNC_ID = 2049905)
           SELECT @V_IS_FLAG = 1
        IF @IN_PRODUCT_ID IS NULL SELECT @IN_PRODUCT_ID = 0
        IF @IN_LINK_MAN IS NULL SELECT @IN_LINK_MAN = 0
        IF @IN_PRE_CODE IS NULL SELECT @IN_PRE_CODE = ''

        DECLARE C1_VALID CURSOR FOR
            SELECT A.PRODUCT_ID,A.PRE_CODE,A.PRE_MONEY,A.PRE_NUM
                FROM INTRUST..TPRECONTRACT A,INTRUST..TPRODUCT B
                WHERE A.PRODUCT_ID = B.PRODUCT_ID
                    AND A.BOOK_CODE  = @IN_BOOK_CODE AND A.PRE_STATUS IN ('111301','111302')
                    AND (A.PRODUCT_ID  = @IN_PRODUCT_ID OR @IN_PRODUCT_ID = 0)
                    AND (B.PRODUCT_STATUS = '110202' OR (B.PRODUCT_STATUS = '110203' AND B.OPEN_FLAG = 1))
                    AND (A.LINK_MAN = @IN_LINK_MAN OR A.INPUT_MAN = @IN_INPUT_MAN OR @V_IS_FLAG = 1)
                    AND (A.PRE_CODE LIKE '%'+@IN_PRE_CODE+'%' OR @IN_PRE_CODE = '')
        OPEN C1_VALID
        FETCH NEXT FROM C1_VALID INTO @V_PRODUCT_ID,@V_PRE_CODE,@V_PRE_MONEY,@V_PRE_NUM
        WHILE @@FETCH_STATUS = 0
        BEGIN
            SELECT @V_RG_NUM = 0
            SELECT @V_RG_MONEY = 0
            SELECT @V_RG_NUM = COUNT(*),@V_RG_MONEY = SUM(RG_MONEY)
            FROM INTRUST..TCONTRACT
            WHERE PRODUCT_ID = @V_PRODUCT_ID AND PRE_CODE = @V_PRE_CODE AND PRE_FLAG = 1 AND HT_STATUS = '120101'
            IF (ISNULL(@V_RG_NUM,0) < @V_PRE_NUM) AND (ISNULL(@V_RG_MONEY,0) < @V_PRE_MONEY)
                INSERT INTO #TPRECONTRACT VALUES(@V_PRODUCT_ID,@V_PRE_CODE)
            FETCH NEXT FROM C1_VALID INTO @V_PRODUCT_ID,@V_PRE_CODE,@V_PRE_MONEY,@V_PRE_NUM
        END
        CLOSE C1_VALID
        DEALLOCATE C1_VALID
        IF ISNULL(@IN_CUST_NAME,'') = ''
            SELECT A.*,B.CUST_NAME, B.CUST_TEL,B.H_TEL,B.O_TEL,B.MOBILE,B.BP,B.CUST_NO,
                /*如果不存在按子产品设置的收益级别设置，则按父产品的收益级别设置来取*/ 
                CASE WHEN NOT EXISTS 
                    (SELECT 1
                      FROM INTRUST..TGAINLEVEL GL JOIN INTRUST..TGAINLEVELRATE GLR ON GL.SERIAL_NO=GLR.DF_SERIAL_NO
                      WHERE GL.PRODUCT_ID=A.PRODUCT_ID                        
                        AND (GL.CUST_TYPE=0 OR GL.CUST_TYPE=B.CUST_TYPE)
                        AND A.PRE_MONEY>=GL.LOWER_LIMIT AND A.PRE_MONEY<=GL.UPPER_LIMIT
                        AND A.PRE_DATE<=GLR.END_DATE) THEN
                    CASE WHEN ISNULL(A.SUB_PRODUCT_ID,0)=0 THEN                    
                        (SELECT ISNULL(EXP_RATE1,0.0) FROM INTRUST..TPRODUCT WHERE PRODUCT_ID=A.PRODUCT_ID)
                    ELSE
                        (SELECT ISNULL(EXP_RATE1,0.0) FROM INTRUST..TSUBPRODUCT WHERE SUB_PRODUCT_ID=A.SUB_PRODUCT_ID)
                    END
                ELSE 
                    (SELECT TOP 1 GLR.GAIN_RATE 
                      FROM INTRUST..TGAINLEVEL GL JOIN INTRUST..TGAINLEVELRATE GLR ON GL.SERIAL_NO=GLR.DF_SERIAL_NO
                      WHERE GL.PRODUCT_ID=A.PRODUCT_ID       
                        AND (ISNULL(GL.SUB_PRODUCT_ID,0)=0 OR GL.SUB_PRODUCT_ID=A.SUB_PRODUCT_ID)                  
                        AND (GL.CUST_TYPE=0 OR GL.CUST_TYPE=B.CUST_TYPE)
                        AND A.PRE_MONEY>=GL.LOWER_LIMIT AND A.PRE_MONEY<=GL.UPPER_LIMIT
                        AND A.PRE_DATE<=GLR.END_DATE
                      ORDER BY ISNULL(GL.SUB_PRODUCT_ID,0) DESC, GL.PROV_FLAG, GL.PROV_LEVEL)
                END AS EXP_RATE1,
                CASE WHEN NOT EXISTS 
                    (SELECT 1
                      FROM INTRUST..TGAINLEVEL GL JOIN INTRUST..TGAINLEVELRATE GLR ON GL.SERIAL_NO=GLR.DF_SERIAL_NO
                      WHERE GL.PRODUCT_ID=A.PRODUCT_ID                        
                        AND (GL.CUST_TYPE=0 OR GL.CUST_TYPE=B.CUST_TYPE)
                        AND A.PRE_MONEY>=GL.LOWER_LIMIT AND A.PRE_MONEY<=GL.UPPER_LIMIT
                        AND A.PRE_DATE<=GLR.END_DATE) THEN
                    CASE WHEN ISNULL(A.SUB_PRODUCT_ID,0)=0 THEN                    
                        (SELECT ISNULL(EXP_RATE2,0.0) FROM INTRUST..TPRODUCT WHERE PRODUCT_ID=A.PRODUCT_ID)
                    ELSE
                        (SELECT ISNULL(EXP_RATE2,0.0) FROM INTRUST..TSUBPRODUCT WHERE SUB_PRODUCT_ID=A.SUB_PRODUCT_ID)
                    END
                ELSE                     
                    (SELECT TOP 1 GLR.GAIN_RATE 
                      FROM INTRUST..TGAINLEVEL GL JOIN INTRUST..TGAINLEVELRATE GLR ON GL.SERIAL_NO=GLR.DF_SERIAL_NO
                      WHERE GL.PRODUCT_ID=A.PRODUCT_ID       
                        AND (ISNULL(GL.SUB_PRODUCT_ID,0)=0 OR GL.SUB_PRODUCT_ID=A.SUB_PRODUCT_ID)                  
                        AND (GL.CUST_TYPE=0 OR GL.CUST_TYPE=B.CUST_TYPE)
                        AND A.PRE_MONEY>=GL.LOWER_LIMIT AND A.PRE_MONEY<=GL.UPPER_LIMIT
                        AND A.PRE_DATE<=GLR.END_DATE
                      ORDER BY ISNULL(GL.SUB_PRODUCT_ID,0) DESC, GL.PROV_FLAG, GL.PROV_LEVEL)
                END AS EXP_RATE2,
                CASE WHEN NOT EXISTS 
                    (SELECT 1
                       FROM INTRUST..TMONEYDETAIL M, INTRUST..TCONTRACT CON
                       WHERE CON.PRODUCT_ID=A.PRODUCT_ID AND CON.PRE_CODE=A.PRE_CODE 
                         AND M.PRODUCT_ID=CON.PRODUCT_ID AND M.CONTRACT_BH=CON.CONTRACT_BH
                         AND M.CHECK_FLAG=2) THEN
                    0.0
                ELSE (SELECT SUM(M.TO_MONEY)
                       FROM INTRUST..TMONEYDETAIL M, INTRUST..TCONTRACT CON
                       WHERE CON.PRODUCT_ID=A.PRODUCT_ID AND CON.PRE_CODE=A.PRE_CODE 
                         AND M.PRODUCT_ID=CON.PRODUCT_ID AND M.CONTRACT_BH=CON.CONTRACT_BH
                         AND M.CHECK_FLAG=2)
                END AS PAY_MONEY,                   
                B.CUST_TYPE_NAME,@V_GroupName AS GroupName,
                @V_CLASSDETAIL_NAME  AS CLASSDETAIL_NAME,D.PRODUCT_NAME
            FROM INTRUST..TPRECONTRACT A,TCustomers B,#TPRECONTRACT C,INTRUST..TPRODUCT D
            WHERE (A.CUST_ID = B.CUST_ID) AND (A.PRODUCT_ID = C.PRODUCT_ID AND A.PRE_CODE = C.PRE_CODE) AND (A.PRODUCT_ID=D.PRODUCT_ID)
                AND(@V_SW20409 = 0 OR A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCTS))
                AND (ISNULL(@IN_CUST_TYPE,0)=0 OR B.CUST_TYPE=@IN_CUST_TYPE)
                AND (ISNULL(@IN_MAX_PRE_MONEY,0)=0 OR A.PRE_MONEY <= @IN_MAX_PRE_MONEY)
                AND (ISNULL(@IN_MIN_PRE_MONEY,0)=0 OR A.PRE_MONEY >= @IN_MIN_PRE_MONEY)
                AND (ISNULL(@IN_CUST_SOURCE,'')='' OR A.CUST_SOURCE=@IN_CUST_SOURCE)
                AND (ISNULL(@IN_GroupID,0)=0 OR A.CUST_ID IN (SELECT CUST_ID FROM TCustGroupMembers WHERE GroupID = @IN_GroupID))
                AND (ISNULL(@IN_CLASSDETAIL_ID,0)=0 OR A.CUST_ID IN (SELECT CUST_ID FROM TCustomerClass WHERE CLASSDETAIL_ID = @IN_CLASSDETAIL_ID))
                AND (A.MONEY_STATUS = @IN_MONEY_STATUS OR ISNULL(@IN_MONEY_STATUS,0) = 0)
            ORDER BY A.PRODUCT_ID DESC,A.PRE_MONEY DESC,A.PRE_CODE
        ELSE
            SELECT A.*,B.CUST_NAME, B.CUST_TEL,B.H_TEL,B.O_TEL,B.MOBILE,B.BP,B.CUST_NO,
                CASE WHEN NOT EXISTS 
                    (SELECT 1
                      FROM INTRUST..TGAINLEVEL GL JOIN INTRUST..TGAINLEVELRATE GLR ON GL.SERIAL_NO=GLR.DF_SERIAL_NO
                      WHERE GL.PRODUCT_ID=A.PRODUCT_ID                        
                        AND (GL.CUST_TYPE=0 OR GL.CUST_TYPE=B.CUST_TYPE)
                        AND A.PRE_MONEY>=GL.LOWER_LIMIT AND A.PRE_MONEY<=GL.UPPER_LIMIT
                        AND A.PRE_DATE<=GLR.END_DATE) THEN
                    CASE WHEN ISNULL(A.SUB_PRODUCT_ID,0)=0 THEN                    
                        (SELECT ISNULL(EXP_RATE1,0.0) FROM INTRUST..TPRODUCT WHERE PRODUCT_ID=A.PRODUCT_ID)
                    ELSE
                        (SELECT ISNULL(EXP_RATE1,0.0) FROM INTRUST..TSUBPRODUCT WHERE SUB_PRODUCT_ID=A.SUB_PRODUCT_ID)
                    END
                ELSE 
                   (SELECT TOP 1 GLR.GAIN_RATE 
                      FROM INTRUST..TGAINLEVEL GL JOIN INTRUST..TGAINLEVELRATE GLR ON GL.SERIAL_NO=GLR.DF_SERIAL_NO
                      WHERE GL.PRODUCT_ID=A.PRODUCT_ID       
                        AND (ISNULL(GL.SUB_PRODUCT_ID,0)=0 OR GL.SUB_PRODUCT_ID=A.SUB_PRODUCT_ID)                  
                        AND (GL.CUST_TYPE=0 OR GL.CUST_TYPE=B.CUST_TYPE)
                        AND A.PRE_MONEY>=GL.LOWER_LIMIT AND A.PRE_MONEY<=GL.UPPER_LIMIT
                        AND A.PRE_DATE<=GLR.END_DATE
                      ORDER BY ISNULL(GL.SUB_PRODUCT_ID,0) DESC, GL.PROV_FLAG, GL.PROV_LEVEL)
                END AS EXP_RATE1,
                CASE WHEN NOT EXISTS 
                    (SELECT 1
                      FROM INTRUST..TGAINLEVEL GL JOIN INTRUST..TGAINLEVELRATE GLR ON GL.SERIAL_NO=GLR.DF_SERIAL_NO
                      WHERE GL.PRODUCT_ID=A.PRODUCT_ID                        
                        AND (GL.CUST_TYPE=0 OR GL.CUST_TYPE=B.CUST_TYPE)
                        AND A.PRE_MONEY>=GL.LOWER_LIMIT AND A.PRE_MONEY<=GL.UPPER_LIMIT
                        AND A.PRE_DATE<=GLR.END_DATE) THEN
                    CASE WHEN ISNULL(A.SUB_PRODUCT_ID,0)=0 THEN                    
                        (SELECT ISNULL(EXP_RATE2,0.0) FROM INTRUST..TPRODUCT WHERE PRODUCT_ID=A.PRODUCT_ID)
                    ELSE
                        (SELECT ISNULL(EXP_RATE2,0.0) FROM INTRUST..TSUBPRODUCT WHERE SUB_PRODUCT_ID=A.SUB_PRODUCT_ID)
                    END
                ELSE 
                    (SELECT TOP 1 GLR.GAIN_RATE 
                      FROM INTRUST..TGAINLEVEL GL JOIN INTRUST..TGAINLEVELRATE GLR ON GL.SERIAL_NO=GLR.DF_SERIAL_NO
                      WHERE GL.PRODUCT_ID=A.PRODUCT_ID       
                        AND (ISNULL(GL.SUB_PRODUCT_ID,0)=0 OR GL.SUB_PRODUCT_ID=A.SUB_PRODUCT_ID)                  
                        AND (GL.CUST_TYPE=0 OR GL.CUST_TYPE=B.CUST_TYPE)
                        AND A.PRE_MONEY>=GL.LOWER_LIMIT AND A.PRE_MONEY<=GL.UPPER_LIMIT
                        AND A.PRE_DATE<=GLR.END_DATE
                      ORDER BY ISNULL(GL.SUB_PRODUCT_ID,0) DESC, GL.PROV_FLAG, GL.PROV_LEVEL)
                END AS EXP_RATE2,
                CASE WHEN NOT EXISTS 
                    (SELECT 1
                       FROM INTRUST..TMONEYDETAIL M, INTRUST..TCONTRACT CON
                       WHERE CON.PRODUCT_ID=A.PRODUCT_ID AND CON.PRE_CODE=A.PRE_CODE 
                         AND M.PRODUCT_ID=CON.PRODUCT_ID AND M.CONTRACT_BH=CON.CONTRACT_BH
                         AND M.CHECK_FLAG=2) THEN
                    0.0
                ELSE (SELECT SUM(M.TO_MONEY)
                       FROM INTRUST..TMONEYDETAIL M, INTRUST..TCONTRACT CON
                       WHERE CON.PRODUCT_ID=A.PRODUCT_ID AND CON.PRE_CODE=A.PRE_CODE 
                         AND M.PRODUCT_ID=CON.PRODUCT_ID AND M.CONTRACT_BH=CON.CONTRACT_BH
                         AND M.CHECK_FLAG=2)
                END AS PAY_MONEY, 
                B.CUST_TYPE_NAME,@V_GroupName AS GroupName,
			    @V_CLASSDETAIL_NAME  AS CLASSDETAIL_NAME,D.PRODUCT_NAME
            FROM INTRUST..TPRECONTRACT A,TCustomers B,#TPRECONTRACT C,INTRUST..TPRODUCT D
            WHERE (A.CUST_ID = B.CUST_ID) AND (A.PRODUCT_ID = C.PRODUCT_ID AND A.PRE_CODE = C.PRE_CODE) AND (A.PRODUCT_ID=D.PRODUCT_ID)
                AND(B.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%')
                AND(@V_SW20409 = 0 OR A.PRODUCT_ID IN (SELECT PRODUCT_ID FROM @V_PRODUCTS))
                AND (ISNULL(@IN_CUST_TYPE,0)=0 OR B.CUST_TYPE=@IN_CUST_TYPE)
                AND (ISNULL(@IN_MAX_PRE_MONEY,0)=0 OR A.PRE_MONEY <= @IN_MAX_PRE_MONEY)
                AND (ISNULL(@IN_MIN_PRE_MONEY,0)=0 OR A.PRE_MONEY >= @IN_MIN_PRE_MONEY)
                AND (ISNULL(@IN_CUST_SOURCE,'')='' OR A.CUST_SOURCE=@IN_CUST_SOURCE)
                AND (ISNULL(@IN_GroupID,0)=0 OR A.CUST_ID IN (SELECT CUST_ID FROM TCustGroupMembers WHERE GroupID = @IN_GroupID))
                AND (ISNULL(@IN_CLASSDETAIL_ID,0)=0 OR A.CUST_ID IN (SELECT CUST_ID FROM TCustomerClass WHERE CLASSDETAIL_ID = @IN_CLASSDETAIL_ID))
                AND (A.MONEY_STATUS = @IN_MONEY_STATUS OR ISNULL(@IN_MONEY_STATUS,0) = 0)
            ORDER BY A.PRODUCT_ID DESC,A.PRE_MONEY DESC,A.PRE_CODE
    END
GO
