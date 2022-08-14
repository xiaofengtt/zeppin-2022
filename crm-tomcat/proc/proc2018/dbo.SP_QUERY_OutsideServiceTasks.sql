USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_OutsideServiceTasks @IN_INPUT_MAN   INTEGER  --操作员（CRM.TOperator.OP_CODE）
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @RET INT, @V_DT_eTrust INT, @V_SQL NVARCHAR(500)
    SELECT @V_DT_eTrust = VALUE FROM TSYSCONTROL WHERE FLAG_TYPE = N'DT_eTrust'
    DECLARE @V_SYS_DATE INT, @V_TipTitle NVARCHAR(100), @V_URL NVARCHAR(200), @V_eTrust_IP NVARCHAR(60)
    CREATE TABLE #VT_OutsideServiceTasks (
        TipTitle    NVARCHAR(100),  --提示标题，用于在“待处理的内部事务”列表中显示
        URL         NVARCHAR(200),  --提供当然工作提示的处理页面的URL
        number      INT             --记录条数，不为0表示有数据需要处理
    )
    SELECT @V_eTrust_IP = TYPE_CONTENT FROM TDICTPARAM WHERE TYPE_VALUE = N'800002'
    SELECT @V_eTrust_IP = ISNULL(@V_eTrust_IP,'')
    SELECT @V_SYS_DATE = dbo.GETDATEINT(GETDATE())
    IF @V_DT_eTrust = 1 --启用分布式
    BEGIN
        --1.客户注册信息确认
        SET @V_SQL = N' INSERT INTO #VT_OutsideServiceTasks(TipTitle,number,URL)
            SELECT ''客户注册信息待确认'', COUNT(*), ''/system/custOrTranCheck.do?method=queryRegCustInfoListPage&MENU_NAME=注册客户确认&MENU_INFO=客户自助管理''
                FROM SRV_eTrust.SELF_ZL_NULL.dbo.TREG_CUST WHERE CHECK_FLAG = 1'
        --2.注册客户修改确认
        SET @V_SQL = N' INSERT INTO #VT_OutsideServiceTasks(TipTitle,number,URL)
            SELECT ''注册客户信息修改待确认'', COUNT(*), ''/system/regCustomer.do?method=queryCheckRegCustListPage&MENU_NAME=注册客户修改确认&MENU_INFO=客户自助管理''
                FROM SRV_eTrust.SELF_ZL_NULL.dbo.TREG_CUST_DETAIL WHERE CHECK_FLAG = 1'
        --3.转让
        SET @V_SQL = N' INSERT INTO #VT_OutsideServiceTasks(TipTitle,number,URL)
            SELECT ''客户受益权转让待确认'', COUNT(*), ''/system/custOrTranCheck.do?method=queryTransferOrAcceptedTransferListPage&flag=1&checkFlag=1&MENU_NAME=转让信息确认&MENU_INFO=客户自助管理''
                FROM SRV_eTrust.SELF_ZL_NULL.dbo.TBENTRAN WHERE FLAG = 1 AND CHECK_FLAG = 1'
        --4.受让
        SET @V_SQL = N' INSERT INTO #VT_OutsideServiceTasks(TipTitle,number,URL)
            SELECT ''客户受益权受让待确认'', COUNT(*), ''/system/custOrTranCheck.do?method=queryTransferOrAcceptedTransferListPage&flag=2&checkFlag=1&MENU_NAME=受让信息确认&MENU_INFO=客户自助管理''
                FROM SRV_eTrust.SELF_ZL_NULL.dbo.TBENTRAN WHERE FLAG = 2 AND CHECK_FLAG = 1'
        --5.预约
        SET @V_SQL = N' INSERT INTO #VT_OutsideServiceTasks(TipTitle,number,URL)
            SELECT ''客户预约待确认'', COUNT(*), ''/system/presell.do?method=queryBookingProductOfNoCheckList&MENU_NAME=预约信息确认&MENU_INFO=客户自助管理''
                FROM SRV_eTrust.SELF_ZL_NULL.dbo.TCUST_PRE WHERE PRE_FLAG = 1 and CHECK_FLAG = 1'
        --6.留言回复
        SET @V_SQL = N' INSERT INTO #VT_OutsideServiceTasks(TipTitle,number,URL)
            SELECT ''客户留言待回复'', COUNT(*), ''/system/custMsgBoard.do?method=queryCustMsgListPage&MENU_NAME=客户留言回复&MENU_INFO=客户自助管理''
                FROM SRV_eTrust.SELF_ZL_NULL.dbo.TMSGBOARD WHERE REPLY_FLAG = 1'
        --输出结果
        execute(@V_SQL)
        UPDATE #VT_OutsideServiceTasks SET URL = @V_eTrust_IP+URL
    END
    ELSE IF (@V_DT_eTrust = 2) AND EXISTS(SELECT * FROM master..sysdatabases WHERE NAME = N'SELF_ZL_NULL') --本地信托数据库
    BEGIN
        --1.客户注册信息确认
        INSERT INTO #VT_OutsideServiceTasks(TipTitle,number,URL)
            SELECT '客户注册信息待确认', COUNT(*), '/system/custOrTranCheck.do?method=queryRegCustInfoListPage&MENU_NAME=注册客户确认&MENU_INFO=客户自助管理'
                FROM SELF_ZL_NULL.dbo.TREG_CUST WHERE CHECK_FLAG = 1
        --2.注册客户修改确认
        INSERT INTO #VT_OutsideServiceTasks(TipTitle,number,URL)
            SELECT '注册客户信息修改待确认', COUNT(*), '/system/regCustomer.do?method=queryCheckRegCustListPage&MENU_NAME=注册客户修改确认&MENU_INFO=客户自助管理'
                FROM SELF_ZL_NULL.dbo.TREG_CUST_DETAIL WHERE CHECK_FLAG = 1
        --3.转让
        INSERT INTO #VT_OutsideServiceTasks(TipTitle,number,URL)
            SELECT '客户受益权转让待确认', COUNT(*), '/system/custOrTranCheck.do?method=queryTransferOrAcceptedTransferListPage&flag=1&checkFlag=1&MENU_NAME=转让信息确认&MENU_INFO=客户自助管理'
                FROM SELF_ZL_NULL.dbo.TBENTRAN WHERE FLAG = 1 AND CHECK_FLAG = 1
        --4.受让
        INSERT INTO #VT_OutsideServiceTasks(TipTitle,number,URL)
            SELECT '客户受益权受让待确认', COUNT(*), '/system/custOrTranCheck.do?method=queryTransferOrAcceptedTransferListPage&flag=2&checkFlag=1&MENU_NAME=受让信息确认&MENU_INFO=客户自助管理'
                FROM SELF_ZL_NULL.dbo.TBENTRAN WHERE FLAG = 2 AND CHECK_FLAG = 1
        --5.预约
        INSERT INTO #VT_OutsideServiceTasks(TipTitle,number,URL)
            SELECT '客户预约待确认', COUNT(*), '/system/presell.do?method=queryBookingProductOfNoCheckList&MENU_NAME=预约信息确认&MENU_INFO=客户自助管理'
                FROM SELF_ZL_NULL.dbo.TCUST_PRE WHERE PRE_FLAG = 1 and CHECK_FLAG = 1
        --6.留言回复
        INSERT INTO #VT_OutsideServiceTasks(TipTitle,number,URL)
            SELECT '客户留言待回复', COUNT(*), '/system/custMsgBoard.do?method=queryCustMsgListPage&MENU_NAME=客户留言回复&MENU_INFO=客户自助管理'
                FROM SELF_ZL_NULL.dbo.TMSGBOARD WHERE REPLY_FLAG = 1
        --输出结果
        UPDATE #VT_OutsideServiceTasks SET URL = @V_eTrust_IP+URL
    END

    SELECT * FROM #VT_OutsideServiceTasks WHERE number > 0
GO
