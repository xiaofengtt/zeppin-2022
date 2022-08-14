USE EFCRM
GO
SET ANSI_NULLS, QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE SP_QUERY_TOPERATOR_UUID  @IN_OP_CODE 		INTEGER,                 	--操作员ID													
										  @IN_UUID        	NVARCHAR(60) 			    --UUID										
WITH ENCRYPTION										
AS	
	SELECT A.*,B.LOGIN_USER FROM TOPERATOR_UUID A LEFT JOIN TOPERATOR B ON A.OP_CODE = B.OP_CODE
			WHERE (A.OP_CODE = @IN_OP_CODE OR ISNULL(@IN_OP_CODE,'') = N'')
			 AND (A.UUID = @IN_UUID OR ISNULL(@IN_UUID,'') = N'')		
--******************************************************************************
-- = Filename: workspace\SP_QUERY_InsideServiceTasks.SQL
--******************************************************************************
/*
 Auth   : 董毅光
 Time   : 20091120
 Purpose: 查询TServiceTasks表中所有等待“我”处理的服务任务
 UsedBy : 首页调用，我的工作台中，“待处理的内部事务”中的列表
 Output :
20111129 DONGYG 动态判断客户预登记数据，返回显示到“待处理客户事务”中
*/
PRINT ' ==================== creating Procedure SP_QUERY_InsideServiceTasks  =================== '
GO
