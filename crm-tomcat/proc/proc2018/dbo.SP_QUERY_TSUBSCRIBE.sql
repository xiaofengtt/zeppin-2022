USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TSUBSCRIBE @IN_SUBSCRIBE_ID       INT,            --主键序号
                                      @IN_NONPRODUCT_ID     INT,            --非信托产品ID
                                      @IN_NONPRODUCT_NAME   NVARCHAR(60),   --非信托产品名称
                                      @IN_CUST_NAME         NVARCHAR(100),  --投资管理人
                                      @IN_CHECK_FLAG        INT,            --审核
                                      @IN_STATUS            NVARCHAR(10),   --合同状态(1201)
                                      @IN_INPUT_MAN         INT,            --录入操作员
                                      @IN_CUST_ID           INT = NULL      --客户ID
WITH ENCRYPTION
AS
	DECLARE @V_FLAG_ACCESS_ALL INT
	SELECT @V_FLAG_ACCESS_ALL = 0 --访问全部标志
	--能够访问所有客户信息
    IF EXISTS(SELECT 1 FROM TOPRIGHT WHERE OP_CODE = @IN_INPUT_MAN AND MENU_ID = N'999' AND FUNC_ID = 99903)
        SELECT @V_FLAG_ACCESS_ALL = 1
    --如果操作员的角色中存在访问所有客户信息权限的标志 则赋予能够访问所有客户信息权限
    IF EXISTS(SELECT 1 FROM TOPROLE WHERE OP_CODE = @IN_INPUT_MAN AND ROLE_ID IN(SELECT ROLE_ID FROM TROLE WHERE FLAG_ACCESS_ALL = 1))
        SELECT @V_FLAG_ACCESS_ALL = 1
    IF ISNULL(@IN_SUBSCRIBE_ID,0) <> 0
        BEGIN
            SELECT * FROM TSUBSCRIBE A,TCUSTOMERS B
			WHERE SUBSCRIBE_ID = @IN_SUBSCRIBE_ID AND (A.CUST_ID = B.CUST_ID)
				AND (B.INPUT_MAN = @IN_INPUT_MAN OR B.SERVICE_MAN = @IN_INPUT_MAN OR @V_FLAG_ACCESS_ALL = 1)
        END
    ELSE
        BEGIN
            SELECT A.*,B.CUST_NAME FROM TSUBSCRIBE A,TCUSTOMERS B
            WHERE  (A.SUBSCRIBE_ID = @IN_SUBSCRIBE_ID OR ISNULL(@IN_SUBSCRIBE_ID,'') = N'')
                AND (A.NONPRODUCT_ID = @IN_NONPRODUCT_ID OR ISNULL(@IN_NONPRODUCT_ID,'') = N'')
                AND (A.NONPRODUCT_NAME LIKE '%' + @IN_NONPRODUCT_NAME + '%' OR ISNULL(@IN_NONPRODUCT_NAME,'') = N'')
                AND (B.CUST_NAME LIKE '%' + @IN_CUST_NAME + '%' OR ISNULL(@IN_CUST_NAME,'') = N'')
                AND (A.STATUS LIKE '%' + @IN_STATUS + '%' OR ISNULL(@IN_STATUS,'') = N'')
                AND (A.CHECK_FLAG = @IN_CHECK_FLAG OR  ISNULL(@IN_CHECK_FLAG,'') = N'')
                AND (A.CUST_ID = B.CUST_ID)
                AND (B.CUST_ID = @IN_CUST_ID OR ISNULL(@IN_CUST_ID,0) = 0)
				AND (B.INPUT_MAN = @IN_INPUT_MAN OR B.SERVICE_MAN = @IN_INPUT_MAN OR @V_FLAG_ACCESS_ALL = 1)
        END

    RETURN 100
GO
