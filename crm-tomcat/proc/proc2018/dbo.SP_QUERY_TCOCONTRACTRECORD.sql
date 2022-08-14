USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCOCONTRACTRECORD @IN_MAINRECORD_ID               INTEGER,          --客户维护ID，自增长
                                             @IN_CUST_NAME                   NVARCHAR(60),     --客户名称
                                             @IN_MAIN_PRO_NAME               NVARCHAR(200),    --维护项目名称
                                             @IN_RECORD_DATE_BEGIN           INT,              --受理日期从
                                             @IN_RECORD_DATE_END             INT,              --受理日期到
                                             @IN_INPUT_MAN                   INT               --录入操作员 
WITH ENCRYPTION
AS
    IF ISNULL(@IN_RECORD_DATE_BEGIN,0) =0
        SET @IN_RECORD_DATE_BEGIN =20000101
    IF ISNULL(@IN_RECORD_DATE_END,0) =0
        SET @IN_RECORD_DATE_END =20991231
        
    SELECT A.*,B.CUST_NAME FROM TCOCONTRACTRECORD A,TCustomers B
        WHERE (ISNULL(@IN_MAINRECORD_ID,0) =0 OR A.MAINRECORD_ID=@IN_MAINRECORD_ID)
            AND (ISNULL(@IN_MAIN_PRO_NAME,'') ='' OR A.MAIN_PRO_NAME LIKE '%'+@IN_MAIN_PRO_NAME+'%')
            AND (A.RECORD_DATE BETWEEN @IN_RECORD_DATE_BEGIN AND @IN_RECORD_DATE_END)
            AND A.CUST_ID =B.CUST_ID
            AND (ISNULL(@IN_CUST_NAME,'') ='' OR B.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%')
GO
