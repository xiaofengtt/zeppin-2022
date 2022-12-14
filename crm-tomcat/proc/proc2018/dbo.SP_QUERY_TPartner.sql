USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TPartner  @IN_PARTN_ID             INT,
                                    @IN_PARTN_NO             NVARCHAR(8),
                                    @IN_PARTN_NAME           NVARCHAR(100),
                                    @IN_CARD_ID              NVARCHAR(40),
                                    @IN_PARTN_TYPE           INT,
                                    @IN_REPORT_TYPE          NVARCHAR(10),
                                    @IN_PARTN_TYPE2_FLAG     INT,
                                    @IN_CUST_ID           INT
WITH ENCRYPTION
AS
    IF ISNULL(@IN_PARTN_ID,0) <> 0
        BEGIN
            SELECT  PARTN_ID,PARTN_NO,PARTN_NAME,PARTN_TEL,POST_ADDRESS,POST_ADDRESS2,POST_CODE,POST_CODE2,
                    CARD_TYPE,CARD_TYPE_NAME,CARD_ID,CARD_VALID_DATE,COUNTRY,BIRTHDAY,AGE,SEX,SEX_NAME,O_TEL,
                    H_TEL,MOBILE,BP,FAX,E_MAIL,PARTN_TYPE,PARTN_TYPE_NAME,JG_PARTN_TYPE,TOUCH_TYPE,TOUCH_TYPE_NAME,
                    INPUT_MAN,SUMMARY,LEGAL_MAN,LEGAL_ADDRESS,CONTACT_MAN,LIST_ID,SERVICE_MAN,VOC_TYPE,VOC_TYPE_NAME,
                    REPORT_TYPE,REPORT_TYPE_NAME,PARTN_TYPE2_FLAG
            FROM TPartner
            WHERE PARTN_ID = @IN_PARTN_ID
        END
    ELSE
        BEGIN
            SELECT  PARTN_ID,PARTN_NO,PARTN_NAME,PARTN_TEL,POST_ADDRESS,POST_ADDRESS2,POST_CODE,POST_CODE2,
                    CARD_TYPE,CARD_TYPE_NAME,CARD_ID,CARD_VALID_DATE,COUNTRY,BIRTHDAY,AGE,SEX,SEX_NAME,O_TEL,
                    H_TEL,MOBILE,BP,FAX,E_MAIL,PARTN_TYPE,PARTN_TYPE_NAME,JG_PARTN_TYPE,TOUCH_TYPE,TOUCH_TYPE_NAME,
                    INPUT_MAN,SUMMARY,LEGAL_MAN,LEGAL_ADDRESS,CONTACT_MAN,LIST_ID,SERVICE_MAN,VOC_TYPE,VOC_TYPE_NAME,
                    REPORT_TYPE,REPORT_TYPE_NAME,PARTN_TYPE2_FLAG
            FROM TPartner
            WHERE (PARTN_TYPE = @IN_PARTN_TYPE  OR ISNULL(@IN_PARTN_TYPE,'') = N'')
                AND (PARTN_NO LIKE '%' + @IN_PARTN_NO + '%' OR ISNULL(@IN_PARTN_NO,'') = N'')
                AND (PARTN_NAME LIKE '%' + @IN_PARTN_NAME + '%' OR ISNULL(@IN_PARTN_NAME,'') = N'')
                AND (CARD_ID LIKE '%' + @IN_CARD_ID + '%' OR ISNULL(@IN_CARD_ID,'') = N'')
                AND (PARTN_TYPE2_FLAG = @IN_PARTN_TYPE2_FLAG  OR ISNULL(@IN_PARTN_TYPE2_FLAG,'') = N'')
                AND (CUST_ID = @IN_CUST_ID  OR ISNULL(@IN_CUST_ID,'') = N'')
        END
    RETURN 100
GO
