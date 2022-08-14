USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TCONTRACTUNREAL  @IN_SERIAL_NO    INT,                    --序号
                                        @IN_PREPRODUCT_ID   INT,                    --预发行产品ID
                                        @IN_PREPRODUCT_NAME NVARCHAR(200),          --预发行产品名称
                                        @IN_CUST_ID         INT ,                   --客户ID
                                        @IN_CUST_NAME       NVARCHAR(100),          --客户名称
                                        @IN_CONTRACT_SUB_BH NVARCHAR (30) ,         --合同实际编号
                                        @IN_QS_DATE1        INTEGER ,               --签署日期(起)
                                        @IN_QS_DATE2        INTEGER ,               --签署日期(止)
                                        @IN_STATUS          INTEGER ,               --状态：1未转正式认购2已转认购
                                        @IN_INPUT_MAN       INT                     --操作员
                                        
WITH ENCRYPTION
AS
    SET NOCOUNT ON
    SET @IN_SERIAL_NO = ISNULL(@IN_SERIAL_NO,0)
    SET @IN_PREPRODUCT_ID = ISNULL(@IN_PREPRODUCT_ID,0)
    SET @IN_PREPRODUCT_NAME = ISNULL(@IN_PREPRODUCT_NAME,'')
    SET @IN_CUST_NAME = ISNULL(@IN_CUST_NAME,'')
    SET @IN_QS_DATE1 = ISNULL(@IN_QS_DATE1,0)
    SET @IN_QS_DATE2 = ISNULL(@IN_QS_DATE2,20991231)
    SET @IN_STATUS = ISNULL(@IN_STATUS,0)
    
    DECLARE @V_IS_FLAG INT,@V_TEAM_ID INT
	
    SELECT @V_IS_FLAG = 0
    --能够访问所有预约信息
    IF EXISTS(SELECT 1 FROM INTRUST..TOPRIGHT WHERE OP_CODE = @IN_INPUT_MAN AND MENU_ID ='20409' AND FUNC_ID = 2049905)
        SELECT @V_IS_FLAG = 1

    --访问权限，这里先不做控制，如果需求有，再在此加
    --------------------------------------------------------------------
    
    SELECT A.*,B.PREPRODUCT_NAME,B.BIND_PRODUCT_ID,C.CUST_NAME,C.CUST_TYPE_NAME,C.CARD_ID
        FROM TCONTRACTUNREAL A 
            LEFT JOIN TPREPRODUCT B ON A.PREPRODUCT_ID=B.PREPRODUCT_ID
			LEFT JOIN TCustomers C ON A.CUST_ID=C.CUST_ID
        WHERE (A.SERIAL_NO=@IN_SERIAL_NO OR ISNULL(@IN_SERIAL_NO,0)=0)
            AND (A.PREPRODUCT_ID=@IN_PREPRODUCT_ID OR ISNULL(@IN_PREPRODUCT_ID,0)=0)
            AND (B.PREPRODUCT_NAME LIKE '%'+@IN_PREPRODUCT_NAME+'%' OR ISNULL(@IN_PREPRODUCT_NAME,'')='')
            AND (A.CUST_ID=@IN_CUST_ID OR ISNULL(@IN_CUST_ID,0)=0)
            AND (C.CUST_NAME LIKE '%'+@IN_CUST_NAME+'%' OR ISNULL(@IN_CUST_NAME,'')='')
            AND (A.CONTRACT_SUB_BH LIKE '%'+@IN_CONTRACT_SUB_BH+'%' OR ISNULL(@IN_CONTRACT_SUB_BH,'')='')
            AND ( (ISNULL(@IN_QS_DATE1,0)=0 OR A.QS_DATE>=@IN_QS_DATE1) AND (ISNULL(@IN_QS_DATE2,0)=0 OR A.QS_DATE<=@IN_QS_DATE2) )
            AND (A.STATUS=@IN_STATUS OR ISNULL(@IN_STATUS,0)=0)
    
GO
