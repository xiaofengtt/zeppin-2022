USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  SP_FLOW_STAT01_USER   @IN_OP_CODE 		INT,		 --用户ID
										@IN_QUERY_MONTH 	NVARCHAR(6), --查询年月
										@IN_FLOW_NODE_FLAG  INT=1 		 --1表示未结束流程，2结束流程
								  
WITH ENCRYPTION
AS

    IF @IN_FLOW_NODE_FLAG=1  --未结束流程
    BEGIN
    	select  fs.FLOW_NAME, count(fs.state_id) FLOW_COUNT
					from flow_state fs left join flow_task ft 
					on fs.flow_no=ft.flow_no and fs.object_type=ft.object_type and fs.object_no=ft.object_no and fs.node_no = ft.node_no
					where fs.node_flag <>'end'
						and  substring(fs.input_date,1,4)+ substring(fs.input_date,6,2) = @IN_QUERY_MONTH
						and ft.user_id = @IN_OP_CODE
					group by fs.flow_name 
    END
    ELSE  --已结束流程统计
    BEGIN
    	select  fs.FLOW_NAME, count(fs.state_id) FLOW_COUNT
					from flow_state fs left join flow_task ft 
					on fs.flow_no=ft.flow_no and fs.object_type=ft.object_type and fs.object_no=ft.object_no and fs.node_no = ft.node_no
					where fs.node_flag ='end'
						and  substring(fs.input_date,1,4)+ substring(fs.input_date,6,2) = @IN_QUERY_MONTH
						and ft.user_id = @IN_OP_CODE
					group by fs.flow_name 
    END
GO
