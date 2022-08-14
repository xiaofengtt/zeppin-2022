USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCustomer_MOBILE  @IN_PhoneNumber     NVARCHAR(60), --电话号码
                                            @IN_CARD_ID         NVARCHAR(20), --证件号码
                                            @IN_PRODUCT_NAME    NVARCHAR(256),--产品名称
                                            @IN_CUST_NAME       NVARCHAR(100),
                                            @IN_CUST_NO       NVARCHAR(20), --客户编号
                                            @IN_POST_ADDRESS    NVARCHAR(256),--联系地址
                                            @IN_INPUT_MAN       INT
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    DECLARE @V_COUNT INT
    DECLARE @V_TEMPCUST TABLE(CUST_ID INT) --返回的CUST_ID

    IF LEN(@IN_PhoneNumber)>0
    BEGIN
        --去掉来电号码中前面的"0"
        IF LEFT(@IN_PhoneNumber,1) = N'0'
            SET @IN_PhoneNumber = RIGHT(@IN_PhoneNumber,LEN(@IN_PhoneNumber)-1)
        SET @IN_PhoneNumber = REPLACE(@IN_PhoneNumber, N'-',N'')

        --去掉客户信息、联系人信息中各电话号码中的非数字字符
        --1.完全匹配
        INSERT INTO @V_TEMPCUST
        SELECT CUST_ID FROM TCustomers
            WHERE (REPLACE(CUST_TEL,N'-',N'') = @IN_PhoneNumber OR REPLACE(O_TEL, N'-',N'') = @IN_PhoneNumber
                    OR REPLACE(H_TEL, N'-',N'') = @IN_PhoneNumber OR REPLACE(MOBILE, N'-',N'') = @IN_PhoneNumber OR REPLACE(FAX, N'-',N'') = @IN_PhoneNumber
                    OR EXISTS(SELECT CUST_ID FROM TCustomerContacts WHERE TCustomers.CUST_ID = TCustomerContacts.CUST_ID
                                AND(REPLACE(PhoneHome,N'-',N'') = @IN_PhoneNumber OR REPLACE(PhoneOffice,N'-',N'') = @IN_PhoneNumber
                                    OR REPLACE(Moblie,N'-',N'') = @IN_PhoneNumber OR REPLACE(Fax,N'-',N'') = @IN_PhoneNumber
                                    OR REPLACE(AssistantTelphone,N'-',N'') = @IN_PhoneNumber OR REPLACE(ManagerTelphone,N'-',N'') = @IN_PhoneNumber)
                    ) )

        SELECT @V_COUNT = COUNT(*) FROM @V_TEMPCUST
        --2.右匹配
        IF @V_COUNT<=0
        BEGIN
            SET @IN_PhoneNumber = N'%'+@IN_PhoneNumber
            INSERT INTO @V_TEMPCUST
            SELECT CUST_ID FROM TCustomers
            WHERE (REPLACE(CUST_TEL,N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(O_TEL, N'-',N'') LIKE @IN_PhoneNumber
                    OR REPLACE(H_TEL, N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(MOBILE, N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(FAX, N'-',N'') LIKE @IN_PhoneNumber
                    OR EXISTS(SELECT CUST_ID FROM TCustomerContacts WHERE TCustomers.CUST_ID = TCustomerContacts.CUST_ID
                                AND(REPLACE(PhoneHome,N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(PhoneOffice,N'-',N'') LIKE @IN_PhoneNumber
                                    OR REPLACE(Moblie,N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(Fax,N'-',N'') LIKE @IN_PhoneNumber
                                    OR REPLACE(AssistantTelphone,N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(ManagerTelphone,N'-',N'') LIKE @IN_PhoneNumber)
                    ) )

            SELECT @V_COUNT = COUNT(*) FROM @V_TEMPCUST
            --3.左匹配
            IF @V_COUNT<=0
            BEGIN
                SET @IN_PhoneNumber = @IN_PhoneNumber+'%'
                INSERT INTO @V_TEMPCUST
                SELECT CUST_ID FROM TCustomers
                WHERE (REPLACE(CUST_TEL,N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(O_TEL, N'-',N'') LIKE @IN_PhoneNumber
                        OR REPLACE(H_TEL, N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(MOBILE, N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(FAX, N'-',N'') LIKE @IN_PhoneNumber
                        OR EXISTS(SELECT CUST_ID FROM TCustomerContacts WHERE TCustomers.CUST_ID = TCustomerContacts.CUST_ID
                                    AND(REPLACE(PhoneHome,N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(PhoneOffice,N'-',N'') LIKE @IN_PhoneNumber
                                        OR REPLACE(Moblie,N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(Fax,N'-',N'') LIKE @IN_PhoneNumber
                                        OR REPLACE(AssistantTelphone,N'-',N'') LIKE @IN_PhoneNumber OR REPLACE(ManagerTelphone,N'-',N'') LIKE @IN_PhoneNumber)
                        ) )

--                SELECT @V_COUNT = COUNT(*) FROM @V_TEMPCUST
--                IF @V_COUNT<=0
--                BEGIN
--                    --4.取来电号码右7位模糊匹配
--                    SET @IN_PhoneNumber = N'%'+RIGHT(@IN_PhoneNumber,7)+'%'
--
--                    INSERT INTO @V_TEMPCUST
--                    SELECT CUST_ID FROM TCustomers
--                    WHERE (REPLACE(CUST_TEL,N'-',N'') = @IN_PhoneNumber OR REPLACE(O_TEL, N'-',N'') = @IN_PhoneNumber
--                            OR REPLACE(H_TEL, N'-',N'') = @IN_PhoneNumber OR REPLACE(MOBILE, N'-',N'') = @IN_PhoneNumber OR REPLACE(FAX, N'-',N'') = @IN_PhoneNumber
--                            OR EXISTS(SELECT CUST_ID FROM TCustomerContacts WHERE TCustomers.CUST_ID = TCustomerContacts.CUST_ID
--                                        AND(REPLACE(PhoneHome,N'-',N'') = @IN_PhoneNumber OR REPLACE(PhoneOffice,N'-',N'') = @IN_PhoneNumber
--                                            OR REPLACE(Moblie,N'-',N'') = @IN_PhoneNumber OR REPLACE(Fax,N'-',N'') = @IN_PhoneNumber
--                                            OR REPLACE(AssistantTelphone,N'-',N'') = @IN_PhoneNumber OR REPLACE(ManagerTelphone,N'-',N'') = @IN_PhoneNumber)
--                            ) )
--                END
            END
        END
    END
    ELSE
    BEGIN
        INSERT INTO @V_TEMPCUST
        SELECT CUST_ID FROM TCustomers
    END

    SELECT A.*
    FROM TCustomers A,@V_TEMPCUST B
    WHERE A.CUST_ID = B.CUST_ID
        AND (ISNULL(@IN_CARD_ID,'') = N'' OR CARD_ID LIKE '%'+@IN_CARD_ID+'%')
        AND (ISNULL(@IN_CUST_NAME,'') = N'' OR CUST_NAME LIKE '%'+@IN_CUST_NAME+'%')
        AND (ISNULL(@IN_CUST_NO,'') = N'' OR CUST_NO = @IN_CUST_NO )
        AND (ISNULL(@IN_POST_ADDRESS,'') = N'' OR POST_ADDRESS LIKE '%'+@IN_POST_ADDRESS+'%')
        AND (ISNULL(@IN_PRODUCT_NAME,'') = N'' OR A.CUST_ID IN (SELECT CUST_ID FROM INTRUST..TCONTRACT WHERE PRODUCT_NAME LIKE '%'+@IN_PRODUCT_NAME+'%'))
GO
