USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TPROFIT @IN_SERIAL_NO        INT,            --主键序号
                                  @IN_NONPRODUCT_ID    INT,            --非信托产品ID
                                  @IN_NONPRODUCT_NAME  NVARCHAR(60),   --非信托产品名称
                                  @IN_CUST_NAME        NVARCHAR(100),  --客户名称
                                  @IN_SUBSCRIBE_BH     NVARCHAR(60),   --购买合同编号
                                  @IN_SY_DATE          INT,            --收益分配日期
                                  @IN_INPUT_MAN        INT,            --操作员
                                  @IN_CUST_ID          INT = NULL,     --客户ID
                                  @IN_CHECK_FLAG       INT = NULL,         --审核
                                  @IN_SY_TYPE          NVARCHAR(10) = NULL --状态
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
    IF ISNULL(@IN_SERIAL_NO,0) <> 0
        BEGIN
            SELECT * FROM TPROFIT A, TSUBSCRIBE B,TCUSTOMERS C 
			WHERE (A.SERIAL_NO = @IN_SERIAL_NO) AND (A.SUBSCRIBE_ID = B.SUBSCRIBE_ID)AND (B.CUST_ID = C.CUST_ID)
				AND (C.INPUT_MAN = @IN_INPUT_MAN OR C.SERVICE_MAN = @IN_INPUT_MAN OR @V_FLAG_ACCESS_ALL = 1)
        END
    ELSE
        BEGIN
            SELECT A.SERIAL_NO,B.NONPRODUCT_ID, B.NONPRODUCT_NAME, A.SUBSCRIBE_ID
                , A.CUST_ID, B.SUBSCRIBE_BH, A.PROFIT_MONEY, A.SY_DATE, A.SY_TYPE, A.SY_TYPE_NAME,
                 A.BANK_ID, A.BANK_NAME, A.BANK_SUB_NAME, A.BANK_ACCT,A.CHECK_FLAG, C.CUST_NAME
                 FROM TPROFIT A, TSUBSCRIBE B, TCUSTOMERS C
                   WHERE (A.SERIAL_NO = @IN_SERIAL_NO OR ISNULL(@IN_SERIAL_NO,'') = N'')
                    AND (B.NONPRODUCT_ID = @IN_NONPRODUCT_ID OR ISNULL(@IN_NONPRODUCT_ID,'') = N'')
                    AND (A.SUBSCRIBE_ID = B.SUBSCRIBE_ID)
                    AND (B.NONPRODUCT_NAME LIKE '%' + @IN_NONPRODUCT_NAME + '%' OR ISNULL(@IN_NONPRODUCT_NAME,'') = N'')
                    AND (A.CUST_ID = C.CUST_ID)
                    AND (B.SUBSCRIBE_BH LIKE '%' + @IN_SUBSCRIBE_BH + '%' OR ISNULL(@IN_SUBSCRIBE_BH,'') = N'')
                    AND (C.CUST_NAME LIKE '%' + @IN_CUST_NAME + '%' OR ISNULL(@IN_CUST_NAME,'') = N'')
                    AND (A.SY_DATE = @IN_SY_DATE OR ISNULL(@IN_SY_DATE,'') = N'')
                    AND (A.SY_TYPE LIKE '%' + @IN_SY_TYPE + '%' OR ISNULL(@IN_SY_TYPE,'') = N'')
                    AND (A.CHECK_FLAG = @IN_CHECK_FLAG OR ISNULL(@IN_CHECK_FLAG,'') = N'')
                    AND (A.CUST_ID = @IN_CUST_ID OR ISNULL(@IN_CUST_ID,0) = 0)
					AND (C.INPUT_MAN = @IN_INPUT_MAN OR C.SERVICE_MAN = @IN_INPUT_MAN OR @V_FLAG_ACCESS_ALL = 1)
        END

    RETURN 100
GO
