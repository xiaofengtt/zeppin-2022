USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_ADD_TCUSTOMERCONTACTS    @IN_CUST_ID           INTEGER,         --客户ID（TCUSTOMERS.CUST_ID）
                                             @IN_CONTACTOR         NVARCHAR(30),    --联系人名称
                                             @IN_PHONEHOME         NVARCHAR(30),    --家庭电话
                                             @IN_PHONEOFFICE       NVARCHAR(30),    --办公电话
                                             @IN_MOBLIE            NVARCHAR(30),    --手机
                                             @IN_FAX               NVARCHAR(30),    --传真
                                             @IN_EMAIL             NVARCHAR(60),    --电子邮件
                                             @IN_ADDRESS           NVARCHAR(60),    --通讯地址
                                             @IN_ZIPCODE           NVARCHAR(6),     --邮政编码
                                             @IN_SEX               INTEGER,         --性别1男2女
                                             @IN_BIRTHDAY          INTEGER,         --生日YYYYMMDD
                                             @IN_ANNIVERSARY       INTEGER,         --纪念日YYYYMMDD
                                             @IN_ISMARRIED         INTEGER,         --婚姻状况0否1是
                                             @IN_SPOUSE            NVARCHAR(30),    --配偶姓名
                                             @IN_BOSS              BIGINT,          --上司(其他联系人ID)(TCUSTOMERCONTACTS.CONTACTID)
                                             @IN_DEPARTMENT        NVARCHAR(30),    --部门
                                             @IN_DUTY              NVARCHAR(30),    --职务
                                             @IN_COUNTRY           NVARCHAR(30),    --国家
                                             @IN_PROVINCE          NVARCHAR(30),    --省、市、自治区
                                             @IN_CITY              NVARCHAR(30),    --市、县
                                             @IN_ROLE              INTEGER,         --角色1决策者2员工3影响者
                                             @IN_ASSISTANT         NVARCHAR(30),    --助理
                                             @IN_ASSISTANTTELPHONE NVARCHAR(30),    --助理电话
                                             @IN_MANAGER           NVARCHAR(30),    --经理
                                             @IN_MANAGERTELPHONE   NVARCHAR(30),    --经理电话
                                             @IN_CONTACTWAY        NVARCHAR(30),    --首选联系方式（1109）
                                             @IN_PREFERREDDATE     INTEGER,         --接受服务首选日（星期）1-7
                                             @IN_PREFERREDTIME     INTEGER,         --接受服务首选时间（1上午2下午3晚上）
                                             @IN_RECEIVECONTACTWAY INTEGER,         --接受的联系方式(1109之ADDITIVE_VALUE的或值)
                                             @IN_RECEIVESERVICES   INTEGER,         --当前客户接收的服务类别
                                             @IN_CONTACT_TYPE      INTEGER,         --联系人类型：1。客户联系人 2。合作伙伴联系人
                                             @IN_INPUT_MAN         INTEGER          --必要  操作员（CRM.TOPERATOR.OP_CODE）
WITH ENCRYPTION
AS
    DECLARE @V_SERVICETYPE INT,@V_SERVICEWAY NVARCHAR(10),@V_SERVICETYPENAME NVARCHAR(64)
    DECLARE @V_RET_CODE INT, @IBUSI_FLAG INT,@SBUSI_NAME NVARCHAR(40), @SSUMMARY NVARCHAR(200)
    SELECT @V_RET_CODE = -10401000, @IBUSI_FLAG = 10401
    SELECT @SBUSI_NAME = N'增加客户联系人信息', @SSUMMARY = N'增加客户联系人信息'
    IF NOT EXISTS(SELECT 1 FROM TCustomers WHERE CUST_ID = @IN_CUST_ID )
        RETURN @V_RET_CODE - 1  --客户信息不存在

    BEGIN TRANSACTION
    INSERT INTO TCustomerContacts(CUST_ID,Contactor,PhoneHome,PhoneOffice,Moblie,Fax,Email,Address,ZipCode,Sex,Birthday,Anniversary,
             IsMarried,Spouse,Boss,Department,Duty,Country,Province,City,Role,Assistant,AssistantTelphone,ContactWay,
             Manager,ManagerTelphone,PreferredDate,PreferredTime,ReceiveContactWay,ReceiveServices,CONTACT_TYPE,InsertMan)
       VALUES(@IN_CUST_ID,@IN_CONTACTOR,@IN_PHONEHOME,@IN_PHONEOFFICE,@IN_MOBLIE,@IN_FAX,@IN_EMAIL,@IN_ADDRESS,@IN_ZIPCODE,@IN_SEX,@IN_BIRTHDAY,@IN_ANNIVERSARY,
        @IN_ISMARRIED,@IN_SPOUSE,@IN_BOSS,@IN_DEPARTMENT,@IN_DUTY,@IN_COUNTRY,@IN_PROVINCE,@IN_CITY,@IN_ROLE,@IN_ASSISTANT,@IN_ASSISTANTTELPHONE,@IN_CONTACTWAY,
        @IN_MANAGER,@IN_MANAGERTELPHONE,@IN_PREFERREDDATE,@IN_PREFERREDTIME,@IN_RECEIVECONTACTWAY,@IN_RECEIVESERVICES,@IN_CONTACT_TYPE,@IN_INPUT_MAN)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END
    SET @SSUMMARY = N'增加客户联系人信息，操作员：' + CAST(@IN_INPUT_MAN AS NVARCHAR(10))
    INSERT INTO TLOGLIST(BUSI_FLAG,BUSI_NAME,OP_CODE,SUMMARY)
        VALUES(@IBUSI_FLAG,@SBUSI_NAME,@IN_INPUT_MAN,@SSUMMARY)
    IF @@ERROR <> 0
    BEGIN
        ROLLBACK TRANSACTION
        RETURN -100
    END

    COMMIT TRANSACTION

    RETURN 100

GO
