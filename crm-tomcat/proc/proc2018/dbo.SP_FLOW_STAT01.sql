USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  SP_FLOW_STAT01   @IN_FLOW_NODE_FLAG INT=1 --1表示未结束流程，2结束流程
								  
WITH ENCRYPTION
AS

	DECLARE @V_CURRENT_DATE NVARCHAR(8)
	DECLARE @V_QUERY_MONTH INT

	CREATE TABLE #TEMPRESULT
    (
		 USER_ID   		INT,
		 USER_NAME  	NVARCHAR(40),
		 QUERY_MONTH    INT,
		 FLOW_COUNT     int
     )
     
	CREATE TABLE #TEMPMONTH
    (
		 QUERY_MONTH 	INT
     )

    SELECT @V_CURRENT_DATE= CONVERT(nvarchar(8),getDate(),112)  
	
	--插入月份
	INSERT INTO #TEMPMONTH(QUERY_MONTH) VALUES( dbo.GETMONTH(@V_CURRENT_DATE,-0) /100)
	INSERT INTO #TEMPMONTH(QUERY_MONTH) VALUES( dbo.GETMONTH(@V_CURRENT_DATE,-1) /100)
	INSERT INTO #TEMPMONTH(QUERY_MONTH) VALUES( dbo.GETMONTH(@V_CURRENT_DATE,-2) /100)
	INSERT INTO #TEMPMONTH(QUERY_MONTH) VALUES( dbo.GETMONTH(@V_CURRENT_DATE,-3) /100)
	INSERT INTO #TEMPMONTH(QUERY_MONTH) VALUES( dbo.GETMONTH(@V_CURRENT_DATE,-4) /100)
	INSERT INTO #TEMPMONTH(QUERY_MONTH) VALUES( dbo.GETMONTH(@V_CURRENT_DATE,-5) /100)
	INSERT INTO #TEMPMONTH(QUERY_MONTH) VALUES( dbo.GETMONTH(@V_CURRENT_DATE,-6) /100)
	INSERT INTO #TEMPMONTH(QUERY_MONTH) VALUES( dbo.GETMONTH(@V_CURRENT_DATE,-7) /100)
	INSERT INTO #TEMPMONTH(QUERY_MONTH) VALUES( dbo.GETMONTH(@V_CURRENT_DATE,-8) /100)
	INSERT INTO #TEMPMONTH(QUERY_MONTH) VALUES( dbo.GETMONTH(@V_CURRENT_DATE,-9) /100)
	INSERT INTO #TEMPMONTH(QUERY_MONTH) VALUES( dbo.GETMONTH(@V_CURRENT_DATE,-10) /100)
	INSERT INTO #TEMPMONTH(QUERY_MONTH) VALUES( dbo.GETMONTH(@V_CURRENT_DATE,-11) /100)
	
	--利用游标循环
	DECLARE C1 CURSOR FOR 
		SELECT QUERY_MONTH FROM #TEMPMONTH
	OPEN C1				
	    FETCH NEXT FROM C1 INTO @V_QUERY_MONTH
    WHILE @@FETCH_STATUS =0
    BEGIN
        IF @IN_FLOW_NODE_FLAG=1  --未结束流程
        BEGIN
        	INSERT INTO #TEMPRESULT(USER_ID, USER_NAME, QUERY_MONTH, FLOW_COUNT)
				select op.op_code, op.op_name, @V_QUERY_MONTH, fst.cou from  toperator op left join	
					(select  ft.user_id user_id , count(fs.state_id) cou
						from flow_state fs left join flow_task ft 
						on fs.flow_no=ft.flow_no and fs.object_type=ft.object_type and fs.object_no=ft.object_no and fs.node_no = ft.node_no
						where fs.node_flag <>'end'
							and  substring(fs.input_date,1,4)+ substring(fs.input_date,6,2) = @V_QUERY_MONTH
						group by ft.user_id ) 
					fst
				on op.op_code  = fst.user_id 
        END
        ELSE  --已结束流程统计
        BEGIN
        	INSERT INTO #TEMPRESULT(USER_ID, USER_NAME, QUERY_MONTH, FLOW_COUNT)
				select op.op_code, op.op_name, @V_QUERY_MONTH, fst.cou from  toperator op left join	
					(select  ft.user_id user_id , count(fs.state_id) cou
						from flow_state fs left join flow_task ft 
						on fs.flow_no=ft.flow_no and fs.object_type=ft.object_type and fs.object_no=ft.object_no and fs.node_no = ft.node_no
						where fs.node_flag ='end'
							and  substring(fs.input_date,1,4)+ substring(fs.input_date,6,2) = @V_QUERY_MONTH
						group by ft.user_id ) 
					fst
				on op.op_code  = fst.user_id 
        END
			
	    FETCH NEXT FROM C1 INTO @V_QUERY_MONTH
    END
    CLOSE C1
	DEALLOCATE C1
	
	
	select * from #TEMPRESULT  order by USER_ID,QUERY_MONTH desc
GO
