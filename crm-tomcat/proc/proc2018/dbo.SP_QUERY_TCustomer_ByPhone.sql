USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCustomer_ByPhone @IN_PhoneNumber NVARCHAR(60), --电话号码
                                            @IN_INPUT_MAN   INT

WITH ENCRYPTION
AS
    IF LEN(@IN_PhoneNumber)>0
    BEGIN
        --去掉来电号码中前面的"0"
        IF LEFT(@IN_PhoneNumber,1) = N'0'
            SET @IN_PhoneNumber = RIGHT(@IN_PhoneNumber,LEN(@IN_PhoneNumber)-1)
        --去掉客户信息、联系人信息中各电话号码中的非数字字符
        --1.完全匹配
        SELECT *
            FROM TCustomers
            WHERE (CUST_TEL = @IN_PhoneNumber OR O_TEL = @IN_PhoneNumber OR H_TEL = @IN_PhoneNumber
                OR MOBILE = @IN_PhoneNumber OR FAX = @IN_PhoneNumber
                OR EXISTS(SELECT CUST_ID FROM TCustomerContacts WHERE TCustomers.CUST_ID = TCustomerContacts.CUST_ID
                            AND(PhoneHome = @IN_PhoneNumber OR PhoneOffice = @IN_PhoneNumber OR Moblie = @IN_PhoneNumber OR Fax = @IN_PhoneNumber
                                OR AssistantTelphone = @IN_PhoneNumber OR ManagerTelphone = @IN_PhoneNumber)
                         ) )
        --2.右匹配
        IF @@ROWCOUNT > 0
            RETURN @@ROWCOUNT
        ELSE
        BEGIN
            SET @IN_PhoneNumber = N'%'+@IN_PhoneNumber
            SELECT *
                FROM TCustomers
                WHERE (CUST_TEL LIKE @IN_PhoneNumber OR O_TEL LIKE @IN_PhoneNumber OR H_TEL LIKE @IN_PhoneNumber
                    OR MOBILE LIKE @IN_PhoneNumber OR FAX LIKE @IN_PhoneNumber
                    OR EXISTS(SELECT CUST_ID FROM TCustomerContacts WHERE TCustomers.CUST_ID = TCustomerContacts.CUST_ID
                                AND(PhoneHome LIKE @IN_PhoneNumber OR PhoneOffice LIKE @IN_PhoneNumber OR Moblie LIKE @IN_PhoneNumber OR Fax LIKE @IN_PhoneNumber
                                    OR AssistantTelphone LIKE @IN_PhoneNumber OR ManagerTelphone LIKE @IN_PhoneNumber)
                             ) )
        END
        --3.左匹配
        IF @@ROWCOUNT > 0
            RETURN @@ROWCOUNT
        ELSE
        BEGIN
            SET @IN_PhoneNumber = @IN_PhoneNumber+'%'
            SELECT *
                FROM TCustomers
                WHERE (CUST_TEL LIKE @IN_PhoneNumber OR O_TEL LIKE @IN_PhoneNumber OR H_TEL LIKE @IN_PhoneNumber
                    OR MOBILE LIKE @IN_PhoneNumber OR FAX LIKE @IN_PhoneNumber
                    OR EXISTS(SELECT CUST_ID FROM TCustomerContacts WHERE TCustomers.CUST_ID = TCustomerContacts.CUST_ID
                                AND(PhoneHome LIKE @IN_PhoneNumber OR PhoneOffice LIKE @IN_PhoneNumber OR Moblie LIKE @IN_PhoneNumber OR Fax LIKE @IN_PhoneNumber
                                    OR AssistantTelphone LIKE @IN_PhoneNumber OR ManagerTelphone LIKE @IN_PhoneNumber)
                             ) )
        END
        --4.取来电号码右7位模糊匹配
        IF @@ROWCOUNT > 0
            RETURN @@ROWCOUNT
        ELSE
        BEGIN
            SET @IN_PhoneNumber = N'%'+RIGHT(@IN_PhoneNumber,7)+'%'
            SELECT *
                FROM TCustomers
                WHERE (CUST_TEL LIKE @IN_PhoneNumber OR O_TEL LIKE @IN_PhoneNumber OR H_TEL LIKE @IN_PhoneNumber
                    OR MOBILE LIKE @IN_PhoneNumber OR FAX LIKE @IN_PhoneNumber
                    OR EXISTS(SELECT CUST_ID FROM TCustomerContacts WHERE TCustomers.CUST_ID = TCustomerContacts.CUST_ID
                                AND(PhoneHome LIKE @IN_PhoneNumber OR PhoneOffice LIKE @IN_PhoneNumber OR Moblie LIKE @IN_PhoneNumber OR Fax LIKE @IN_PhoneNumber
                                    OR AssistantTelphone LIKE @IN_PhoneNumber OR ManagerTelphone LIKE @IN_PhoneNumber)
                             ) )
            RETURN @@ROWCOUNT
        END
    END
GO
