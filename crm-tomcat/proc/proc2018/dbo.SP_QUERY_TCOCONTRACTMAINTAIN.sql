USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCOCONTRACTMAINTAIN @IN_MAINTAIN_ID                   INT,   --维护合同(维护费)ID
                                              @IN_COCONTRACTMAINTAIN_SUB_BH     NVARCHAR(200),  --维护合同编号
                                              @IN_MAIN_PRO_NAME                 NVARCHAR(60),   --维护项目名称
                                              @IN_COLLECT_TIME_BEGIN            INT,            --预计收款时间从
                                              @IN_COLLECT_TIME_END              INT,            --预计收款时间到
                                              @IN_START_DATE_BEGIN              INT,            --起始日期从
                                              @IN_START_DATE_END                INT,            --起始日期到
                                              @IN_END_DATE_BEGIN                INT,            --到期日期从
                                              @IN_END_DATE_END                  INT,            --到期日期到
                                              @IN_INPUT_MAN                     INT,            --录入人员，对应表TOPERATOR.OP_CODE
                                              @IN_CUST_NAME                     NVARCHAR(100) = NULL --客户名称
WITH ENCRYPTION
AS
    DECLARE @V_FLAG_ACCESS_ALL INT, @V_FLAG_ENCRYPTION INT, @IN_NODE_SHARE INT
    SELECT @V_FLAG_ACCESS_ALL = 0 --访问全部标志
    SELECT @V_FLAG_ENCRYPTION = 0 --加密标志
    --能够访问所有客户信息
    IF EXISTS(SELECT 1 FROM TOPRIGHT WHERE OP_CODE = @IN_INPUT_MAN AND MENU_ID = N'999' AND FUNC_ID = 99903)
        SELECT @V_FLAG_ACCESS_ALL = 1
    --如果操作员的角色中存在访问所有客户信息权限的标志 则赋予能够访问所有客户信息权限
    IF EXISTS(SELECT 1 FROM TOPROLE WHERE OP_CODE = @IN_INPUT_MAN AND ROLE_ID IN(SELECT ROLE_ID FROM TROLE WHERE FLAG_ACCESS_ALL = 1))
        SELECT @V_FLAG_ACCESS_ALL = 1
    --如果操作员的角色中存在保密限制的角色，则根据保密优先的原则，则进行保密限制
    IF EXISTS(SELECT 1 FROM TOPROLE WHERE OP_CODE = @IN_INPUT_MAN AND ROLE_ID IN(SELECT ROLE_ID FROM TROLE WHERE FLAG_ENCRYPTION = 1))
        SELECT @V_FLAG_ENCRYPTION = 1
    --客户经理级别树中，同节点客户经理是否共享客户
    IF EXISTS(SELECT 1 FROM TSYSCONTROL WHERE FLAG_TYPE = 'MANAGER002' AND VALUE = 1)
        SET @IN_NODE_SHARE = 0  --共享
    ELSE
        SET @IN_NODE_SHARE = 1  --不共享
    DECLARE @V_TEMPCUST2 TABLE(CUST_ID INT) --根据访问权限能够访问的客户，根据访问权限 OR
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
                OR EXISTS( SELECT 1 FROM @V_MANAGER_IDS C WHERE A.INPUT_MAN = C.MANAGERID) )
            AND NOT EXISTS(SELECT 1 FROM @V_TEMPCUST2 C WHERE A.CUST_ID = C.CUST_ID)

    SELECT A.*,C.CUST_NAME, B.COCONTRACT_ID
        FROM TCOCONTRACTMAINTAIN A LEFT JOIN TCOCONTRACT B ON A.COCONTRACT_SUB_BH = B.COCONTRACT_SUB_BH, TCustomers C
        WHERE (ISNULL(@IN_MAINTAIN_ID,0) =0 OR MAINTAIN_ID = @IN_MAINTAIN_ID)
            AND (ISNULL(@IN_COCONTRACTMAINTAIN_SUB_BH,'') ='' OR COCONTRACTMAINTAIN_SUB_BH LIKE '%'+@IN_COCONTRACTMAINTAIN_SUB_BH+'%')
            AND (ISNULL(@IN_MAIN_PRO_NAME,'') ='' OR MAIN_PRO_NAME LIKE '%'+@IN_MAIN_PRO_NAME+'%')
            AND (ISNULL(@IN_COLLECT_TIME_BEGIN,0) =0 OR COLLECT_TIME >=@IN_COLLECT_TIME_BEGIN)
            AND (ISNULL(@IN_COLLECT_TIME_END,0) =0 OR COLLECT_TIME <=@IN_COLLECT_TIME_END)
            AND (ISNULL(@IN_START_DATE_BEGIN,0) =0 OR A.START_DATE >=@IN_START_DATE_BEGIN)
            AND (ISNULL(@IN_START_DATE_END,0) =0 OR A.START_DATE <=@IN_START_DATE_END)
            AND (ISNULL(@IN_END_DATE_BEGIN,0) =0 OR A.END_DATE >=@IN_END_DATE_BEGIN)
            AND (ISNULL(@IN_END_DATE_END,0) =0 OR A.END_DATE <=@IN_END_DATE_END)
            AND A.CUST_ID = C.CUST_ID
            AND (C.CUST_NAME LIKE '%'+ISNULL(@IN_CUST_NAME,'')+'%')
            AND( A.INPUT_MAN = @IN_INPUT_MAN OR A.INPUT_MAN IN (SELECT TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_ID=9111 AND TYPE_VALUE=CONVERT(NVARCHAR(30),@IN_INPUT_MAN)) OR A.CHECK_MAN = @IN_INPUT_MAN OR C.SERVICE_MAN = @IN_INPUT_MAN OR @V_FLAG_ACCESS_ALL = 1
                OR EXISTS(SELECT 1 FROM @V_TEMPCUST2 Z WHERE A.CUST_ID = Z.CUST_ID))
GO
