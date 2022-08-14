﻿USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCUSTREGINFO_CRM  @IN_BOOK_CODE       INT,
                                            @IN_CUST_ID         INT,
                                            @IN_CUST_NAME       NVARCHAR(100),
                                            @IN_INPUT_MAN       INT,
                                            @IN_FLAG            INT = 1,
                                            @IN_CUST_SOURCE     NVARCHAR(10)=NULL,  --客户来源
                                            @IN_CUST_TYPE       INTEGER=0,          --客户类型
                                            @IN_INVEST_TYPE     NVARCHAR(80)=NULL,  --投向
                                            @IN_MAX_REG_MONEY   DECIMAL(16,3)=NULL, --最大有效登记额度
                                            @IN_MIN_REG_MONEY   DECIMAL(16,3)=NULL, --最小有效登记额度
                                            @IN_REG_DATE        INTEGER=NULL,       --登记日期
                                            @IN_STATUS          INTEGER=NULL,       --状态：1有效2无效
                                            @IN_GroupID         INTEGER=NULL,       --客户分组序号
                                            @IN_CLASSDETAIL_ID  INTEGER=NULL,       --客户分级ID
                                            @IN_LINK_MAN        INTEGER=NULL,       --联系人
                                            @IN_CUST_NO         NVARCHAR(10)=''     --客户编号
WITH ENCRYPTION
AS
    SET NOCOUNT ON
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
    --以上客户经理的客户
    INSERT INTO @V_TEMPCUST2
        SELECT DISTINCT CUST_ID FROM TCustomers A
        WHERE (EXISTS( SELECT 1 FROM @V_MANAGER_IDS B WHERE A.SERVICE_MAN = B.MANAGERID)
                OR EXISTS( SELECT 1 FROM @V_MANAGER_IDS C WHERE (@V_QUERYCUSTOMERS = 1 AND A.INPUT_MAN = C.MANAGERID) ) )
            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)

    --输出
    IF ISNULL(@IN_CUST_ID,0) <> 0
    BEGIN
        SELECT A.*, B.SERVICE_MAN,B.CUST_NO,B.CUST_NAME,B.CUST_TEL,B.POST_ADDRESS,B.O_TEL,B.H_TEL,B.MOBILE,B.BP,B.CUST_TYPE_NAME,B.IS_DEAL
        FROM INTRUST..TCUSTREGINFO A,TCustomers B
        WHERE A.CUST_ID = B.CUST_ID AND A.CUST_ID = @IN_CUST_ID AND A.STATUS = 1
        ORDER BY A.REG_MONEY DESC
    END
    ELSE
    BEGIN
        IF @IN_FLAG = 1
        BEGIN
            SELECT A.*, B.SERVICE_MAN,B.CUST_NO,B.CUST_NAME,B.CUST_TEL,B.POST_ADDRESS,B.O_TEL,B.H_TEL,B.MOBILE,B.BP,B.CUST_TYPE_NAME,B.IS_DEAL--,B.CUST_SOURCE
            FROM INTRUST..TCUSTREGINFO A,TCustomers B
            WHERE A.CUST_ID = B.CUST_ID  AND ISNULL(A.REG_MONEY,0) > 0 --AND A.STATUS = 1
                AND (B.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%' OR @IN_CUST_NAME IS NULL OR @IN_CUST_NAME = '')
                AND (ISNULL(@IN_CUST_SOURCE,'')='' OR A.CUST_SOURCE=@IN_CUST_SOURCE)
                AND (ISNULL(@IN_CUST_TYPE,0)=0 OR B.CUST_TYPE=@IN_CUST_TYPE)
                AND (ISNULL(@IN_INVEST_TYPE,'')='' OR A.INVEST_TYPE LIKE '%'+@IN_INVEST_TYPE+'%')
                AND (ISNULL(@IN_MAX_REG_MONEY,0)=0 OR A.REG_MONEY<=@IN_MAX_REG_MONEY)
                AND (ISNULL(@IN_MIN_REG_MONEY,0)=0 OR A.REG_MONEY>=@IN_MIN_REG_MONEY)
                AND (ISNULL(@IN_REG_DATE,0)=0 OR A.REG_DATE=@IN_REG_DATE)
                AND (ISNULL(@IN_STATUS,0)=0 OR A.STATUS=@IN_STATUS)
                AND (ISNULL(@IN_CUST_NO,'')='' OR B.CUST_NO LIKE '%'+@IN_CUST_NO+'%')
                AND (ISNULL(@IN_GroupID,0)=0 OR A.CUST_ID IN (SELECT CUST_ID FROM TCustGroupMembers WHERE GroupID = @IN_GroupID))
                AND (ISNULL(@IN_CLASSDETAIL_ID,0)=0 OR A.CUST_ID IN (SELECT CUST_ID FROM TCustomerClass WHERE CLASSDETAIL_ID = @IN_CLASSDETAIL_ID))
                AND(A.INPUT_MAN = @IN_INPUT_MAN OR A.LINK_MAN = @IN_INPUT_MAN
                    OR EXISTS(SELECT 1 FROM @V_TEMPCUST2 B WHERE A.CUST_ID = B.CUST_ID))
                AND (ISNULL(@IN_LINK_MAN,0) = 0 OR A.LINK_MAN = @IN_LINK_MAN)
            ORDER BY A.REG_MONEY DESC
        END
        ELSE
        BEGIN
            SELECT A.*, B.SERVICE_MAN,B.CUST_NO,B.CUST_NAME,B.CUST_TEL,B.POST_ADDRESS,B.O_TEL,B.H_TEL,B.MOBILE,B.BP,B.CUST_TYPE_NAME,B.IS_DEAL
            FROM INTRUST..TCUSTREGINFO A,TCustomers B
            WHERE A.CUST_ID = B.CUST_ID --AND A.STATUS = 1
                AND (B.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%' OR @IN_CUST_NAME IS NULL OR @IN_CUST_NAME = '')
                AND (ISNULL(@IN_CUST_SOURCE,'')='' OR A.CUST_SOURCE=@IN_CUST_SOURCE)
                AND (ISNULL(@IN_CUST_TYPE,0)=0 OR B.CUST_TYPE=@IN_CUST_TYPE)
                AND (ISNULL(@IN_INVEST_TYPE,'')='' OR A.INVEST_TYPE LIKE '%'+@IN_INVEST_TYPE+'%')
                AND (ISNULL(@IN_MAX_REG_MONEY,0)=0 OR A.REG_MONEY<=@IN_MAX_REG_MONEY)
                AND (ISNULL(@IN_MIN_REG_MONEY,0)=0 OR A.REG_MONEY>=@IN_MIN_REG_MONEY)
                AND (ISNULL(@IN_REG_DATE,0)=0 OR A.REG_DATE=@IN_REG_DATE)
                AND (ISNULL(@IN_STATUS,0)=0 OR A.STATUS=@IN_STATUS)
                AND (ISNULL(@IN_CUST_NO,'')='' OR B.CUST_NO LIKE '%'+@IN_CUST_NO+'%')
                AND (ISNULL(@IN_GroupID,0)=0 OR A.CUST_ID IN (SELECT CUST_ID FROM TCustGroupMembers WHERE GroupID = @IN_GroupID))
                AND (ISNULL(@IN_CLASSDETAIL_ID,0)=0 OR A.CUST_ID IN (SELECT CUST_ID FROM TCustomerClass WHERE CLASSDETAIL_ID = @IN_CLASSDETAIL_ID))
                AND(A.INPUT_MAN = @IN_INPUT_MAN OR A.LINK_MAN = @IN_INPUT_MAN
                    OR EXISTS(SELECT 1 FROM @V_TEMPCUST2 B WHERE A.CUST_ID = B.CUST_ID))
                AND (ISNULL(@IN_LINK_MAN,0) = 0 OR A.LINK_MAN = @IN_LINK_MAN)
            ORDER BY A.REG_MONEY DESC
        END
    END
GO