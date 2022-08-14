USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE  SP_FLOW_COUNT01   @IN_OP_CODE  VARCHAR(10),
									@IN_QUERY_MONTH  VARCHAR(10),
									@IN_QUERY_TYPE  VARCHAR(10)
								  
WITH ENCRYPTION
AS	 
	DECLARE @IN_MONTH01 VARCHAR(7),@IN_MONTH02 VARCHAR(7),@IN_MONTH03 VARCHAR(7),@IN_MONTH04 VARCHAR(7),@IN_MONTH05 VARCHAR(7)

	SELECT @IN_MONTH01=CONVERT(NVARCHAR(7),dateadd(month,-1,GETDATE()),120)
	SELECT @IN_MONTH02=CONVERT(NVARCHAR(7),dateadd(month,-2,GETDATE()),120)
	SELECT @IN_MONTH03=CONVERT(NVARCHAR(7),dateadd(month,-3,GETDATE()),120)
	SELECT @IN_MONTH04=CONVERT(NVARCHAR(7),dateadd(month,-4,GETDATE()),120)
	SELECT @IN_MONTH05=CONVERT(NVARCHAR(7),dateadd(month,-5,GETDATE()),120)

	CREATE TABLE #TEMPRESULT
    (
		 FLOW_NO   VARCHAR(20),
		 FLOW_NAME   VARCHAR(40),
		 NODE_NO VARCHAR(20),
		 NODE_NAME VARCHAR(40),
		 USER_NAME VARCHAR(40),
		 COL1      INT,
		 COL2      INT,
		 COL3      INT,
		 COL4      INT,
		 COL5      INT,
		 COL6      INT
     )

	IF @IN_QUERY_TYPE=1
	INSERT INTO #TEMPRESULT(FLOW_NO,FLOW_NAME,NODE_NO,NODE_NAME,USER_NAME,COL1,COL2,COL3,COL4,COL5,COL6)
	select fs.flow_no,fs.flow_name,fs.node_no,fs.node_name,ft.user_name,
		   Sum(Case when fs.input_date like ''+@IN_QUERY_MONTH+'%' then 1 else 0 end),
		   Sum(Case when fs.input_date like ''+@IN_MONTH01+'%' then 1 else 0 end),
		   Sum(Case when fs.input_date like ''+@IN_MONTH02+'%' then 1 else 0 end),
		   Sum(Case when fs.input_date like ''+@IN_MONTH03+'%' then 1 else 0 end),
		   Sum(Case when fs.input_date like ''+@IN_MONTH04+'%' then 1 else 0 end),
		   Sum(Case when fs.input_date like ''+@IN_MONTH05+'%' then 1 else 0 end)
	from flow_state fs,(select object_no,object_type,user_name from flow_task where (end_time is null or end_time='')) ft
	where fs.object_no=ft.object_no and fs.object_type=ft.object_type
		  and fs.node_flag='middle'
	group by fs.flow_no,fs.flow_name,fs.node_no,fs.node_name,ft.user_name
	UNION ALL
	select 'Total','合计','','','',
		   Sum(Case when fs.input_date like ''+@IN_QUERY_MONTH+'%' then 1 else 0 end),
		   Sum(Case when fs.input_date like ''+@IN_MONTH01+'%' then 1 else 0 end),
		   Sum(Case when fs.input_date like ''+@IN_MONTH02+'%' then 1 else 0 end),
		   Sum(Case when fs.input_date like ''+@IN_MONTH03+'%' then 1 else 0 end),
		   Sum(Case when fs.input_date like ''+@IN_MONTH04+'%' then 1 else 0 end),
		   Sum(Case when fs.input_date like ''+@IN_MONTH05+'%' then 1 else 0 end)
	from flow_state fs,(select object_no,object_type,user_name from flow_task where (end_time is null or end_time='')) ft
	where fs.object_no=ft.object_no and fs.object_type=ft.object_type
		  and fs.node_flag='middle'
	ELSE
	INSERT INTO #TEMPRESULT(FLOW_NO,FLOW_NAME,NODE_NO,NODE_NAME,USER_NAME,COL1,COL2,COL3,COL4,COL5,COL6)
	select ft.flow_no,ft.flow_name,ft.node_no,ft.node_name,ft.user_name,
		   Sum(Case when ft.input_date like ''+@IN_QUERY_MONTH+'%' then 1 else 0 end),
		   Sum(Case when ft.input_date like ''+@IN_MONTH01+'%' then 1 else 0 end),
		   Sum(Case when ft.input_date like ''+@IN_MONTH02+'%' then 1 else 0 end),
		   Sum(Case when ft.input_date like ''+@IN_MONTH03+'%' then 1 else 0 end),
		   Sum(Case when ft.input_date like ''+@IN_MONTH04+'%' then 1 else 0 end),
		   Sum(Case when ft.input_date like ''+@IN_MONTH05+'%' then 1 else 0 end)
	from flow_task ft
	where 1=1 
	and exists (select 1 from flow_state where node_flag='end' and object_no=ft.object_no and object_type=ft.object_type)
	group by ft.flow_no,ft.flow_name,ft.node_no,ft.node_name,ft.user_name
	UNION ALL
	select 'Total','合计','','','',
		   Sum(Case when ft.input_date like ''+@IN_QUERY_MONTH+'%' then 1 else 0 end),
		   Sum(Case when ft.input_date like ''+@IN_MONTH01+'%' then 1 else 0 end),
		   Sum(Case when ft.input_date like ''+@IN_MONTH02+'%' then 1 else 0 end),
		   Sum(Case when ft.input_date like ''+@IN_MONTH03+'%' then 1 else 0 end),
		   Sum(Case when ft.input_date like ''+@IN_MONTH04+'%' then 1 else 0 end),
		   Sum(Case when ft.input_date like ''+@IN_MONTH05+'%' then 1 else 0 end)
	from flow_task ft
	where 1=1 
	and exists (select 1 from flow_state where node_flag='end' and object_no=ft.object_no and object_type=ft.object_type)

	select * from #TEMPRESULT  order by 1
GO
