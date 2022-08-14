USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCUSTOMERCONTACTS  @IN_CONTACTID          INTEGER,        --联系人ID
                                             @IN_CUST_ID            INTEGER,        --客户ID（TCUSTOMERS.CUST_ID）
                                             @IN_Contactor          NVARCHAR(30),   --联系人名称
                                             @IN_Phone              NVARCHAR(30),   --联系人电话，需要与多个电话字段匹配
                                             @IN_Address            NVARCHAR(60),   --联系人地址（模糊）
                                             @IN_ContactWay         NVARCHAR(30),   --首选联系方式
                                             @IN_ReceiveContactWay  INTEGER,        --接受的联系方式
                                             @IN_ReceiveServices    INTEGER,        --接受的服务
                                             @IN_CONTACT_TYPE       INTEGER,            --联系人类型：1。客户联系人 2。合作伙伴联系人
                                             @IN_INPUT_MAN          INTEGER         --操作员

WITH ENCRYPTION
AS
    DECLARE @V_LEFT_ID INT, @V_RIGHT_ID INT
    SELECT @V_LEFT_ID = LEFT_ID, @V_RIGHT_ID = RIGHT_ID FROM TCustManagerTree WHERE MANAGERID = @IN_INPUT_MAN

    SELECT *
    FROM TCustomerContacts
    WHERE (ContactID = @IN_CONTACTID OR ISNULL(@IN_CONTACTID,0) = 0 )
        AND(CONTACT_TYPE = @IN_CONTACT_TYPE OR @IN_CONTACT_TYPE IS NULL OR @IN_CONTACT_TYPE = 0)
        AND(CUST_ID = @IN_CUST_ID OR @IN_CUST_ID IS NULL OR @IN_CUST_ID = 0)
        AND(Contactor LIKE '%'+@IN_Contactor+'%' OR @IN_Contactor IS NULL)
        AND(PhoneHome = @IN_Phone OR PhoneOffice = @IN_Phone OR Moblie = @IN_Phone OR Fax = @IN_Phone
            OR AssistantTelphone = @IN_Phone OR ManagerTelphone = @IN_Phone OR @IN_Phone IS NULL OR @IN_Phone = N'')
        AND(Address LIKE '%'+@IN_Address+'%' OR @IN_Address IS NULL)
        AND(ContactWay = @IN_ContactWay OR @IN_ContactWay IS NULL OR @IN_ContactWay = N'')
        AND(ReceiveContactWay & @IN_ReceiveContactWay > 0 OR @IN_ReceiveContactWay IS NULL)
        AND(ReceiveServices & @IN_ReceiveServices > 0 OR @IN_ReceiveServices IS NULL)
        AND(InsertMan = @IN_INPUT_MAN
            OR EXISTS(SELECT 1 FROM TCustomers WHERE TCustomerContacts.CUST_ID = TCustomers.CUST_ID
                        AND (INPUT_MAN = @IN_INPUT_MAN OR SERVICE_MAN = @IN_INPUT_MAN
                               OR SERVICE_MAN IN (SELECT MANAGERID FROM TCustManagerTree
                                                  WHERE LEFT_ID BETWEEN @V_LEFT_ID AND @V_RIGHT_ID )
                            )
                     )
           )
GO
