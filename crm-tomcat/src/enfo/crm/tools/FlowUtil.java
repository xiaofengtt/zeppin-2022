package enfo.crm.tools;

import java.util.List;
import java.util.Map;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.project.BusinessLogicLocal;
import enfo.crm.project.LevelAppRecordLocal;
import enfo.crm.system.OperatorLocal;
import enfo.crm.tools.EJBFactory;
import enfo.crm.vo.OperatorVO;
import enfo.crm.workflow.CRMCheckFlowLocal;

public class FlowUtil {

	
	/**
	 *  初始化流程状态  
	 * @param paramName
	 * @throws Exception 
	 */
	public static String initFlow(String paramName) throws Exception{
		//LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		OperatorLocal local = EJBFactory.getOperator();
		String list = local.listMenuView("101",new Integer(888));
		System.out.println(list);
		String[] initValue=paramName.split("@@");
		//objectNo-对象编号,objectType-对象类型,flowNo-发起流程,currentUser-当前用户
		String objectNo=initValue[0],objectType=initValue[1],flowNo=initValue[2],currentUser=initValue[3];	
		String[] sql = new String[2];
		sql[0]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE,FLOW_DESC, FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,  USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,Input_date)"+
			   " select '0','"+objectNo+"','"+objectType+"',NULL,fn.flow_no,fn.flow_name,fn.Node_no,fn.node_name,fn.node_flag,'"+currentUser+"',dbo.GETUSERNAME('"+currentUser+"'),dbo.GETORGNAME('"+currentUser+"','OPCODEID'),dbo.GETORGNAME('"+currentUser+"','OPCODENAME'),getdate(),convert(nvarchar(10),getdate(),120)"+
			   " from FLOW_CATALOG FC,FLOW_NODE FN where fc.flow_no=fn.flow_no and fc.init_Node=fn.Node_no and fc.flow_no='"+flowNo+"'";
		sql[1]="insert into FLOW_STATE(OBJECT_NO, OBJECT_TYPE, FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG, USER_ID,USER_NAME,DEPT_ID, DEPT_NAME, INPUT_TIME,INPUT_DATE)"+
			   " select '"+objectNo+"','"+objectType+"',fn.flow_no,fn.flow_name,fn.Node_no,fn.node_name,fn.node_flag,'"+currentUser+"',dbo.GETUSERNAME('"+currentUser+"'),dbo.GETORGNAME('"+currentUser+"','OPCODEID'),dbo.GETORGNAME('"+currentUser+"','OPCODENAME'),getdate(),convert(nvarchar(10),getdate(),120)"+
			   " from FLOW_CATALOG FC,FLOW_NODE FN where fc.flow_no=fn.flow_no and fc.init_Node=fn.Node_no and fc.flow_no='"+flowNo+"'";
		//初始化业务逻辑
		dealBusinesslogic(objectNo,objectType,flowNo,"010");
		initStartFlow(sql);	
		//CrmDBManager.executeSql(sql);
		
		return "success";
	}

	/**
	 *  获得流程摘要  
	 * @param paramName
	 * @throws Exception 
	 */
	public static String getFlowDesc(String paramName) throws Exception{
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] initValue=paramName.split("@@");
		String objectNo=initValue[0],objectType=initValue[1],flowNo=initValue[2];
		String interfaceCode="";
		//获得对应模板摘要
		interfaceCode =Utility.trimNull(ConfigUtil.getDataBaseValue("Flow_Desc","FLOW_CATALOG","Flow_No='"+flowNo+"'"));
		if(interfaceCode==null || "".equals(interfaceCode) || "0".equals(interfaceCode)) interfaceCode=BusinessCheck.getShowInterface(objectNo,objectType);
		String fieldName="",fieldCode="",valueType="",valueContent="",requiredFlag="",visibleFlag="",readonlyFlag="",showUnit="",showType="",editType="";
		String tableCode="",keyFlag="",keyField="",selectField="";
		List showfieldList = levelAppRecord.getFieldShowList(interfaceCode);//获取页面所有的有效元素集合
		for(int i=0;i<showfieldList.size();i++){ 
			Map showMap = (Map)showfieldList.get(i);
			tableCode = Utility.trimNull(showMap.get("TABLE_CODE"));
			keyFlag = Utility.trimNull(showMap.get("KEY_FLAG"));
			showUnit = Utility.trimNull(showMap.get("SHOW_UNIT"));
			showType = Utility.trimNull(showMap.get("SHOW_TYPE"));
			editType = Utility.trimNull(showMap.get("EDIT_TYPE"));
			fieldName = Utility.trimNull(showMap.get("INTERFACEFIELD_NAME"));
			fieldCode = Utility.trimNull(showMap.get("INTERFACEFIELD_CODE"));
			valueType = Utility.trimNull(showMap.get("VALUE_TYPE"));
			valueContent = Utility.trimNull(showMap.get("VALUE_CONTENT"));
			requiredFlag = Utility.trimNull(showMap.get("REQUIRED_FLAG"));
			visibleFlag = Utility.trimNull(showMap.get("VISIBLED_FLAG"));
			readonlyFlag = Utility.trimNull(showMap.get("READONLY_FLAG"));
			//获得主键字段
			if("1".equals(keyFlag)) keyField=fieldCode;
			//获得显示字段
			if(("1".equals(showType)||"2".equals(showType)||"3".equals(showType)||"4".equals(showType)||"5".equals(showType)) && ("1".equals(visibleFlag)||"2".equals(visibleFlag)||"1,2".equals(visibleFlag)))
			{
				if("1".equals(valueType)){
					selectField= selectField +"'"+fieldName+"【'+isnull(CAST(dbo.getCodeName('"+valueContent+"',"+fieldCode+") AS NVARCHAR),'')+'】;'+";
				}else if(fieldCode.indexOf("_USER")>=0){
					selectField= selectField +"'"+fieldName+"【'+isnull(CAST(dbo.getUserName("+fieldCode+") AS NVARCHAR),'')+'】;'+";
				}else if(fieldCode.indexOf("_DEPT")>=0){
					selectField= selectField +"'"+fieldName+"【'+isnull(CAST(dbo.GETORGNAME("+fieldCode+",'DEPTID') AS NVARCHAR),'')+'】;'+";
				}else if(fieldCode.indexOf("_TIME")>=0){
					selectField= selectField +"'"+fieldName+"【'+isnull(convert(nvarchar(40),"+fieldCode+",21),'')+'】;'+";
				}else if("2".equals(valueType)){
					//selectField= selectField +"'"+fieldName+"【'+isnull("+fieldCode+"_NAME,'')+'】;'+";
					
				}
				else{
					//获取字段显示单位
					if(!"".equals(showUnit)){
						if(showUnit.indexOf("<")>=0)
							showUnit=showUnit.substring(0, showUnit.indexOf("<"));
					}
					if("1".equals(showType) && "2".equals(keyFlag))
						selectField= selectField +"'"+fieldName+"【'+isnull("+fieldCode+",'')+'】"+showUnit+";'+";
					else
						selectField= selectField +"'"+fieldName+"【'+isnull(CAST("+fieldCode+" AS NVARCHAR),'')+'】"+showUnit+";'+";
						
				}
			}
		}
		String sql="select "+selectField+""+"''"+" from "+tableCode+" where "+keyField+"="+objectNo;
		String flowDesc =Utility.trimNull(ConfigUtil.getSqlResult(sql));
		return flowDesc;
	}
	
	/**
	 *  更新流程状态  
	 * @param paramName
	 * @throws Exception 
	 */
	public static String actionFlow(String paramName) throws Exception{
		String[] pagePassValue=paramName.split("@@");
		//nodeOpinion-节点表决意见	
		String nodeOpinion=pagePassValue[5];	
		//流程任务处理调度
		if("1310".equals(nodeOpinion)){//流程中退回上一步
			takeBackFlow(paramName);
		}else if("1300".equals(nodeOpinion)){//流程中退回补充资料
			backFirstFlow(paramName);
		}else if("1400".equals(nodeOpinion)){//流程中提交原退回人
			commitOriginFlow(paramName);
		}else if("1410".equals(nodeOpinion)){//流程中主动提交给原发起人
			commitFirstFlow(paramName);
		}else if("1500".equals(nodeOpinion) || "1510".equals(nodeOpinion) || "1520".equals(nodeOpinion)){//流程中会签节点表决意见
			commitSignFlow(paramName);
		}else{							//流程中正常节点流转
			dealwithFlow(paramName);
		}
		return "success";		
	}

	/**
	 *  流程中主动退回任务  
	 * @param paramName
	 * @throws Exception 
	 */
	public static void takeBackFlow(String paramName) throws Exception{
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] pagePassValue=paramName.split("@@");
		//objectNo-对象编号,objectType-对象类型,flowNo-发起流程,nodeNo-当前节点	
		String taskID=pagePassValue[0],objectNo=pagePassValue[1],objectType=pagePassValue[2],flowNo=pagePassValue[3],nodeNo=pagePassValue[4];	
		String[] sql = new String[4];
		//复制并生成新节点任务
		sql[0]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE, FLOW_DESC,FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,  USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,INPUT_DATE)"+
			   " select '"+taskID+"',OBJECT_NO,OBJECT_TYPE,FLOW_DESC,FLOW_NO,FLOW_NAME,NODE_NO,NODE_NAME,NODE_FLAG,"+
			   " user_id,user_name,dept_id,dept_name,getdate(),convert(nvarchar(10),getdate(),120)"+
			   " from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')";	
		//更新下一节点任务状态
		sql[1]="update FLOW_STATE set node_no=(select top 1 node_no from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')),"+
			   " node_name=(select top 1 node_name from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')),"+
			   " node_flag=(select top 1 node_flag from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')),"+
			   " input_date=convert(nvarchar(10),getdate(),120)"+
			   " where object_no='"+objectNo+"' and object_type='"+objectType+"'";			
		//结束当前节点任务
		sql[2]="update FLOW_TASK set end_time=getdate() where task_id='"+taskID+"'";
		//删除当前节点其他未完成任务
		sql[3]="delete from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and node_no='"+nodeNo+"' and (end_time is null or end_time='') and task_id!='"+taskID+"'";
		levelAppRecord.initStartFlow(sql);		
	}

	/**
	 *  流程中主动提交到原退回人
	 * @param paramName
	 * @throws Exception 
	 */
	public static void commitOriginFlow(String paramName) throws Exception{
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] pagePassValue=paramName.split("@@");
		//objectNo-对象编号,objectType-对象类型,flowNo-发起流程,nodeNo-当前节点	
		String taskID=pagePassValue[0],objectNo=pagePassValue[1],objectType=pagePassValue[2],flowNo=pagePassValue[3],nodeNo=pagePassValue[4];	
		String[] sql = new String[4];
		//复制并生成新节点任务
		sql[0]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE, FLOW_DESC,FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,  USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,INPUT_DATE)"+
			   " select '"+taskID+"',OBJECT_NO,OBJECT_TYPE,(select top 1 FLOW_DESC from FLOW_TASK where task_id='"+taskID+"'),FLOW_NO,FLOW_NAME,NODE_NO,NODE_NAME,NODE_FLAG,"+
			   " user_id,user_name,dept_id,dept_name,getdate(),convert(nvarchar(10),getdate(),120)"+
			   " from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')";
		//更新下一节点任务状态
		sql[1]="update FLOW_STATE set node_no=(select top 1 node_no from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')),"+
			   " node_name=(select top 1 node_name from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')),"+
			   " node_flag=(select top 1 node_flag from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')),"+
			   " input_date=convert(nvarchar(10),getdate(),120)"+
			   " where object_no='"+objectNo+"' and object_type='"+objectType+"'";			
		//结束当前节点任务
		sql[2]="update FLOW_TASK set end_time=getdate() where task_id='"+taskID+"'";
		//删除当前节点其他未完成任务
		sql[3]="delete from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and node_no='"+nodeNo+"' and (end_time is null or end_time='') and task_id!='"+taskID+"'";
		levelAppRecord.initStartFlow(sql);			
	}

	/**
	 *  流程中主动退回补充资料到原始节点  
	 * @param paramName
	 * @throws Exception 
	 */
	public static void backFirstFlow(String paramName) throws Exception{
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] pagePassValue=paramName.split("@@");
		//taskID-任务编号,objectNo-对象编号,objectType-对象类型,flowNo-发起流程,nodeNo-当前节点
		String taskID=pagePassValue[0],objectNo=pagePassValue[1],objectType=pagePassValue[2],flowNo=pagePassValue[3],nodeNo=pagePassValue[4];	
		String[] sql = new String[4];
		//复制并生成新节点任务
		sql[0]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE, FLOW_DESC,FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,  USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,INPUT_DATE)"+
		   " select '"+taskID+"',OBJECT_NO,OBJECT_TYPE,NULL,FLOW_NO,FLOW_NAME,'300','退回补充资料','middle',"+
		   " user_id,user_name,dept_id,dept_name,getdate(),convert(nvarchar(10),getdate(),120)"+
		   " from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and RELATIVE_NO='0'";		
		//更新下一节点任务状态
		sql[1]="update FLOW_STATE set node_no='300',node_name='退回补充资料',node_flag='middle',input_date=convert(nvarchar(10),getdate(),120) where object_no='"+objectNo+"' and object_type='"+objectType+"'";
		//结束当前节点任务
		sql[2]="update FLOW_TASK set end_time=getdate() where task_id='"+taskID+"'";
		//删除当前节点其他未完成任务
		sql[3]="delete from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and node_no='"+nodeNo+"' and (end_time is null or end_time='') and task_id!='"+taskID+"'";
		levelAppRecord.initStartFlow(sql);		
	}	
	
	/**
	 *  流程中主动提交原发起人  
	 * @param paramName
	 * @throws Exception 
	 */
	public static void commitFirstFlow(String paramName) throws Exception{
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] pagePassValue=paramName.split("@@");
		//taskID-任务编号,objectNo-对象编号,objectType-对象类型,flowNo-发起流程,nodeNo-当前节点
		String taskID=pagePassValue[0],objectNo=pagePassValue[1],objectType=pagePassValue[2],flowNo=pagePassValue[3],nodeNo=pagePassValue[4];	
		String[] sql = new String[4];
		//复制并生成新节点任务
		sql[0]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE, FLOW_DESC,FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,  USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,INPUT_DATE)"+
		   " select '"+taskID+"',OBJECT_NO,OBJECT_TYPE,(select flow_desc from FLOW_TASK ft where task_id='"+taskID+"'),FLOW_NO,FLOW_NAME,'500','原发起人确认','middle',"+
		   " user_id,user_name,dept_id,dept_name,getdate(),convert(nvarchar(10),getdate(),120)"+
		   " from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and RELATIVE_NO='0'";		
		//更新下一节点任务状态
		sql[1]="update FLOW_STATE set node_no='500',node_name='原发起人确认',node_flag='middle',input_date=convert(nvarchar(10),getdate(),120) where object_no='"+objectNo+"' and object_type='"+objectType+"'";
		//结束当前节点任务
		sql[2]="update FLOW_TASK set end_time=getdate() where task_id='"+taskID+"'";
		//删除当前节点其他未完成任务
		sql[3]="delete from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and node_no='"+nodeNo+"' and (end_time is null or end_time='') and task_id!='"+taskID+"'";
		levelAppRecord.initStartFlow(sql);		
	}	
	
	
	/**
	 *  初始化流程中每个节点任务  
	 * @param paramName
	 * @throws Exception 
	 */
	public static void dealwithFlow(String paramName) throws Exception{
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] pagePassValue=paramName.split("@@");
		String objectWhere="";
		//taskID-任务编号,objectNo-对象编号,objectType-对象类型,flowNo-发起流程,nodeNo-当前节点,nextUser-下一节点处理人
		String taskID=pagePassValue[0],objectNo=pagePassValue[1],objectType=pagePassValue[2],flowNo=pagePassValue[3],nodeNo=pagePassValue[4],nextUser=pagePassValue[6];	
		//取下个节点的初始化信息
		String sqlValue="select Flow_name+'@@'+next_node"
						+ "+'@@'+"
						+ " (select top 1 node_name from FLOW_NODE where ft.next_node=node_no and ft.flow_no=flow_no)"
						+ "+'@@'+"			
						+ " (select top 1 node_flag from FLOW_NODE where ft.next_node=node_no and ft.flow_no=flow_no)"
						+ "+'@@'+drive_type+'@@'+user_ID+'@@'+Dept_ID"						
						+ " from FLOW_TASK ft where task_id='"+taskID+"'";
		String flowValue =Utility.trimNull(ConfigUtil.getSqlResult(sqlValue));	
		String[] result=flowValue.split("@@");
		String flowName=result[0],nextNodeNo=result[1],nextNodeName=result[2],nextNodeFlag=result[3],driveType=result[4],userID=result[5],deptID=result[6];
		if("hand".equals(driveType)){//手工处理节点
			objectWhere=" charindex('"+nextUser+"',op_code)!=0";
		}else if("auto".equals(driveType)){//自动处理节点
			//取下个阶段的操作对象
			objectWhere=getOperateUser(flowNo,nextNodeNo,userID,deptID);
		}
		//流程中没有设置操作对象
		String[] sql = new String[4];
		if("".equals(objectWhere)) objectWhere="1=2";
		//生成当前节点任务
		if("100".equals(nextNodeNo) || "200".equals(nextNodeNo)){//流程审批通过或审批否决
			sql[0]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE, flow_desc,FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,  NODE_OPINION,NODE_OPINION_NAME,DRIVE_TYPE,NEXT_NODE,USER_OPINION,USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,END_TIME,INPUT_DATE)"+
			   " select '"+taskID+"','"+objectNo+"','"+objectType+"',(select flow_desc from FLOW_TASK ft where task_id='"+taskID+"'),'"+flowNo+"','"+flowName+"','"+nextNodeNo+"','"+nextNodeName+"','"+nextNodeFlag+"','1900','流程最终结束','auto','999','"+nextNodeName+"',"+
			   " op_code,op_name,depart_id,dbo.GETORGNAME(depart_id,'DEPTID'),getdate(),getdate(),convert(nvarchar(10),getdate(),120)"+
			   " from toperator where "+objectWhere;
			//初始化业务逻辑
			dealBusinesslogic(objectNo,objectType,flowNo,nextNodeNo);			
		}else{
			sql[0]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE, flow_desc,FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,  USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,INPUT_DATE)"+
			   " select '"+taskID+"','"+objectNo+"','"+objectType+"',(select flow_desc from FLOW_TASK ft where task_id='"+taskID+"'),'"+flowNo+"','"+flowName+"','"+nextNodeNo+"','"+nextNodeName+"','"+nextNodeFlag+"',"+
			   " op_code,op_name,depart_id,dbo.GETORGNAME(depart_id,'DEPTID'),getdate(),convert(nvarchar(10),getdate(),120)"+
			   " from toperator where "+objectWhere;
		}		
		//更新下一节点任务状态
		sql[1]="update FLOW_STATE set node_no='"+nextNodeNo+"',node_name='"+nextNodeName+"',node_flag='"+nextNodeFlag+"',input_date=convert(nvarchar(10),getdate(),120) where object_no='"+objectNo+"' and object_type='"+objectType+"'";
		//结束当前节点任务
		sql[2]="update FLOW_TASK set end_time=getdate() where task_id='"+taskID+"'";
		//删除当前节点其他未完成任务
		sql[3]="delete from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and node_no='"+nodeNo+"' and (end_time is null or end_time='') and task_id!='"+taskID+"'";
		levelAppRecord.initStartFlow(sql);		
	}

	/**
	 *  获得流程中当前节点操作人员  
	 * @param paramName
	 * @throws Exception 
	 */
	public static String getOperateUser(String flowNo,String nodeNo,String userID,String deptID) throws Exception{
		String objectWhere="",operateObjectType="";	
		String flowObjectValue=ConfigUtil.getQueryPrintSql("select relation_type,relation_no from FLOW_OBJECT where flow_no='"+flowNo+"' and  node_no='"+nodeNo+"'",2);
		String[] flowRelationValue=flowObjectValue.split("@@");
		//初始化流程任务记录信息
		for(int i=0;i<flowRelationValue.length/2;i++){
			operateObjectType=flowRelationValue[2*i];
			String[] operateUserValue=flowRelationValue[2*i+1].split(",");
			//生成下一个阶段任务
			if("User".equals(operateObjectType)){//按用户生成
				for(int j=0;j<operateUserValue.length;j++){
					if("".equals(objectWhere)) objectWhere=" op_code='"+operateUserValue[j]+"'";
					else objectWhere=objectWhere+" or op_code='"+operateUserValue[j]+"'";					   
				}
			}else if("Role".equals(operateObjectType)){//按角色生成
				for(int j=0;j<operateUserValue.length;j++){
					if("".equals(objectWhere)) objectWhere="exists (select 1 from TOPROLE where role_id='"+operateUserValue[j]+"' and toperator.op_code=op_code)";
					else objectWhere=objectWhere+" or exists (select 1 from TOPROLE where role_id='"+operateUserValue[j]+"' and toperator.op_code=op_code)";					
				}			
			}else if("WorkGroup".equals(operateObjectType)){//按工作组生成
				for(int j=0;j<operateUserValue.length;j++){
					if("".equals(objectWhere)) objectWhere=" exists (select 1 from TWORKGROUPUSERS where workGroupID='"+operateUserValue[j]+"' and toperator.op_code=userID)";
					else objectWhere=objectWhere+" or exists (select 1 from TWORKGROUPUSERS where workGroupID='"+operateUserValue[j]+"' and toperator.op_code=userID)";					
				}				
			}else if("Org".equals(operateObjectType)){//按部门生成
				for(int j=0;j<operateUserValue.length;j++){
					if("".equals(objectWhere)) objectWhere=" depart_id='"+operateUserValue[j]+"'";
					else objectWhere=objectWhere+" or depart_id='"+operateUserValue[j]+"'";					
				}				
			}else if("OrgRole".equals(operateObjectType)){//按部门下角色生成
				for(int j=0;j<operateUserValue.length;j++){
					if("".equals(objectWhere)) objectWhere="exists (select 1 from TOPROLE where role_id='"+operateUserValue[j]+"' and toperator.op_code=op_code) and Depart_ID='"+deptID+"'";
					else objectWhere=objectWhere+" or (exists (select 1 from TOPROLE where role_id='"+operateUserValue[j]+"' and toperator.op_code=op_code) and Depart_ID='"+deptID+"')";					
				}			
			}
		}
		return objectWhere;
	}
	
	/**
	 *  流程任务会签节点  
	 * @param paramName
	 * @throws Exception 
	 */
	public static String commitSignFlow(String paramName) throws Exception{	
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] pagePassValue=paramName.split("@@");
		String objectWhere="";
		//taskID-任务编号,objectNo-对象编号,objectType-对象类型,flowNo-发起流程,nodeNo-当前节点
		String taskID=pagePassValue[0],objectNo=pagePassValue[1],objectType=pagePassValue[2],flowNo=pagePassValue[3],nodeNo=pagePassValue[4];	
		//取下个节点的初始化信息
		String sqlValue="select isnull(sign_flag,'')+'@@'+isnull(sign_type,'')+'@@'+isnull(sign_result,'')"
						+ " from FLOW_node ft where flow_no='"+flowNo+"' and node_no='"+nodeNo+"'";
		String flowValue =Utility.trimNull(ConfigUtil.getSqlResult(sqlValue));	
		String[] result=flowValue.split("@@");
		String signFlag=result[0],signType=result[1],signResult=result[2];
		//判断当前节点是否会签节点
		if("1".equals(signFlag)){
			sqlValue="select count(1) from FLOW_TASK where task_id!= '"+taskID+"' and object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and node_No='"+nodeNo+"' and (end_time is null or end_time='')";
			String returnValue =Utility.trimNull(ConfigUtil.getSqlResult(sqlValue));		
			if("0".equals(returnValue)){
				String[] sql = new String[2];
				//计算当前会签节点表决意见情况
				sqlValue="select convert(nvarchar(10),sum(case when node_opinion='1500' then 1 else 0 end))+'@@'" +
								"+convert(nvarchar(10),sum(case when node_opinion='1510' then 1 else 0 end))+'@@'"+
								"+convert(nvarchar(10),sum(case when node_opinion='1520' then 1 else 0 end))"+
								" from FLOW_TASK  where object_no='"+objectNo+"' and object_type='"+objectType+"'"+
								" and flow_No='"+flowNo+"' and node_No='"+nodeNo+"'"+
								" and relative_no=(select top 1 relative_no from FLOW_TASK where task_id='"+taskID+"')";
				String opinionValue =Utility.trimNull(ConfigUtil.getSqlResult(sqlValue));
				String[] resultValue=opinionValue.split("@@");
				String passCount=resultValue[0],failCount=resultValue[1],quitCount=resultValue[2];
				//结束当前节点任务
				sql[0]="update FLOW_TASK set end_time=getdate() where task_id='"+taskID+"'";					
				//生成新会签结果节点任务
				if(Integer.parseInt(passCount)>=Integer.parseInt(failCount) && "010".equals(signResult)){
					sql[1]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE, flow_desc,FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,NODE_OPINION,NODE_OPINION_NAME,DRIVE_TYPE,NEXT_NODE,USER_OPINION,USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,END_TIME,INPUT_DATE)"+
					   " select '"+taskID+"',OBJECT_NO, OBJECT_TYPE, flow_desc,FLOW_NO, FLOW_NAME, NODE_NO,NODE_NAME, NODE_FLAG,'1530','会签结果意见',DRIVE_TYPE,'100','"+passCount+"个人同意；"+failCount+"个人不同意；"+quitCount+"个人放弃表决意见；会签的最终结果是【同意】',"+
					   " '888',dbo.GETUSERNAME('888'),dbo.GETORGNAME('888','OPCODEID'),dbo.GETORGNAME('888','OPCODENAME'),getdate(),getdate(),convert(nvarchar(10),getdate(),120)"+
					   " from FLOW_TASK where task_ID='"+taskID+"'";
				}else if(Integer.parseInt(passCount)>=1 && "020".equals(signResult)){
					sql[1]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE, flow_desc,FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,NODE_OPINION,NODE_OPINION_NAME,DRIVE_TYPE,NEXT_NODE,USER_OPINION,USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,END_TIME,INPUT_DATE)"+
					   " select '"+taskID+"',OBJECT_NO, OBJECT_TYPE, flow_desc,FLOW_NO, FLOW_NAME, NODE_NO,NODE_NAME, NODE_FLAG,'1530','会签结果意见',DRIVE_TYPE,'100','"+passCount+"个人同意；"+failCount+"个人不同意；"+quitCount+"个人放弃表决意见；会签的最终结果是【同意】',"+
					   " '888',dbo.GETUSERNAME('888'),dbo.GETORGNAME('888','OPCODEID'),dbo.GETORGNAME('888','OPCODENAME'),getdate(),getdate(),convert(nvarchar(10),getdate(),120)"+
					   " from FLOW_TASK where task_ID='"+taskID+"'";
				}else{
					sql[1]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE, flow_desc,FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,NODE_OPINION,NODE_OPINION_NAME,DRIVE_TYPE,NEXT_NODE,USER_OPINION,USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,END_TIME,INPUT_DATE)"+
					   " select '"+taskID+"',OBJECT_NO, OBJECT_TYPE, flow_desc,FLOW_NO, FLOW_NAME, NODE_NO,NODE_NAME, NODE_FLAG,'1530','会签结果意见',DRIVE_TYPE,'200','"+passCount+"个人同意；"+failCount+"个人不同意；"+quitCount+"个人放弃表决意见；会签的最终结果是【不同意】',"+
					   " '888',dbo.GETUSERNAME('888'),dbo.GETORGNAME('888','OPCODEID'),dbo.GETORGNAME('888','OPCODENAME'),getdate(),getdate(),convert(nvarchar(10),getdate(),120)"+
					   " from FLOW_TASK where task_ID='"+taskID+"'";					
				}				
				levelAppRecord.initStartFlow(sql);
				//结束当前节点任务
				sqlValue="select max(task_id) from FLOW_TASK  where object_no='"+objectNo+"' and object_type='"+objectType+"'"+
						" and flow_No='"+flowNo+"' and node_No='"+nodeNo+"'";
				String newTaskID =Utility.trimNull(ConfigUtil.getSqlResult(sqlValue));				
				//并触发下一个节点的任务
				dealwithFlow(newTaskID+"@@"+objectNo+"@@"+objectType+"@@"+flowNo+"@@"+nodeNo+"@@"+"0"+"@@"+"0");
			}else{
				String[] sql = new String[1];
				//结束当前节点任务
				sql[0]="update FLOW_TASK set end_time=getdate() where task_id='"+taskID+"'";	
				levelAppRecord.initStartFlow(sql);
			}
		}else{
			dealwithFlow(paramName);
		}
		return "success";		
	}
		
	/**
	 *  流程任务主动收回  
	 * @param paramName
	 * @throws Exception 
	 */
	public static String backFlow(String paramName) throws Exception{
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] initValue=paramName.split("@@");
		//objectNo-对象编号,objectType-对象类型,flowNo-发起流程,nodeNo-起始节点,currentUser-当前收回人
		String objectNo=initValue[0],objectType=initValue[1],flowNo=initValue[2],nodeNo=initValue[3],currentUser=initValue[4];	
		String[] sql = new String[3];
		String sqlValue="select count(1) from FLOW_TASK where Node_Opinion >0 and object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"'"
					   +" and relative_no=(select max(task_id) from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and user_id ='"+currentUser+"' and (end_time is not null or end_time!=''))";
		String returnValue =Utility.trimNull(ConfigUtil.getSqlResult(sqlValue));	
		if("".equals(returnValue) || "0".equals(returnValue)){
			//更新下一节点任务状态
			sql[0]="update FLOW_STATE set node_no=(select top 1 node_no from FLOW_TASK where task_id=(select max(task_id) from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and user_id ='"+currentUser+"' and (end_time is not null or end_time!=''))),"+
				   " node_name=(select top 1 node_name from FLOW_TASK where task_id=(select max(task_id) from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and user_id ='"+currentUser+"' and (end_time is not null or end_time!=''))),"+
				   " node_flag=(select top 1 node_flag from FLOW_TASK where task_id=(select max(task_id) from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and user_id ='"+currentUser+"' and (end_time is not null or end_time!=''))),"+
				   " input_date=convert(nvarchar(10),getdate(),120)"+
				   " where object_no='"+objectNo+"' and object_type='"+objectType+"'";	
			//删除当前节点其他未完成任务
			sql[1]="delete from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and node_no='"+nodeNo+"'"
				  +" and relative_no=(select max(task_id) from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and user_id ='"+currentUser+"' and (end_time is not null or end_time!=''))";
			//生成当前节点任务
			sql[2]="update FLOW_TASK set end_time=NULL where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and user_id ='"+currentUser+"'"
				  +" and task_id=(select max(task_id) from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and user_id ='"+currentUser+"' and (end_time is not null or end_time!=''))";
			levelAppRecord.initStartFlow(sql);
			returnValue="success";
		}
		return returnValue;
	}	
	
	/**
	 *  流程任务主动退回上一步  
	 * @param paramName
	 * @throws Exception 
	 */
	public static String untreadFlow(String paramName) throws Exception{
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] initValue=paramName.split("@@");
		//taskID-任务编号,objectNo-对象编号,objectType-对象类型,flowNo-发起流程,nodeNo-起始节点
		String taskID=initValue[0],objectNo=initValue[1],objectType=initValue[2],flowNo=initValue[3],nodeNo=initValue[4];	
		String[] sql = new String[3];
		String sqlValue="select count(1) from FLOW_TASK where task_id='"+taskID+"'";
		String returnValue =Utility.trimNull(ConfigUtil.getSqlResult(sqlValue));	
		if("1".equals(returnValue)){
			//生成当前节点任务
			sql[0]="update FLOW_TASK set end_time=NULL where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"'"
			  	  +" and task_id=(select relative_no from FLOW_TASK where task_id='"+taskID+"')";		
			//更新下一节点任务状态
			sql[1]="update FLOW_STATE set node_no=(select top 1 node_no from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')),"+
				   " node_name=(select top 1 node_name from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')),"+
				   " node_flag=(select top 1 node_flag from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')),"+
				   " input_date=convert(nvarchar(10),getdate(),120)"+
				   " where object_no='"+objectNo+"' and object_type='"+objectType+"'";	
			//删除当前节点其他未完成任务
			sql[2]="delete from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"'"
				  +" and relative_no=(select relative_no from FLOW_TASK where task_id='"+taskID+"')";
			levelAppRecord.initStartFlow(sql);
			returnValue="success";
		}
		return returnValue;
	}	
	
	/**
	 *  流程任务主动委托 
	 * @param paramName
	 * @throws Exception 
	 */
	public static String consignFlowTask(String paramName) throws Exception{	
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] initValue=paramName.split("@@");
		//taskID-任务编号,objectNo-对象编号,objectType-对象类型,flowNo-发起流程,nodeNo-起始节点,nodeNo-委托用户,
		String taskID=initValue[0],objectNo=initValue[1],objectType=initValue[2],flowNo=initValue[3],nodeNo=initValue[4],userID=initValue[5];	
		String[] sql = new String[3];
		//删除当前节点其他未完成任务
		sql[0]="delete from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and node_no='"+nodeNo+"' and (end_time is null or end_time='') and task_id!='"+taskID+"'";		
		//结束当前节点任务
		sql[1]="update FLOW_TASK set end_time=getdate(),NODE_OPINION='1610',NODE_OPINION_NAME='任务委托',DRIVE_TYPE='hand',NEXT_NODE=NODE_NO,USER_OPINION='原来任务是【'+USER_NAME+'】委托给【'+dbo.GETUSERNAME('"+userID+"')+'】处理;'+isnull(USER_OPINION,'')"
		  	  +" where task_id='"+taskID+"'";		
		//生成新节点任务
		sql[2]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE, flow_desc,FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,INPUT_DATE)"+
		   " select '"+taskID+"',OBJECT_NO, OBJECT_TYPE, flow_desc,FLOW_NO, FLOW_NAME, NODE_NO, '【委托】'+NODE_NAME, NODE_FLAG,"+
		   " '"+userID+"',dbo.GETUSERNAME('"+userID+"'),dbo.GETORGNAME('"+userID+"','OPCODEID'),dbo.GETORGNAME('"+userID+"','OPCODENAME'),getdate(),convert(nvarchar(10),getdate(),120)"+
		   " from FLOW_TASK where task_ID='"+taskID+"'";	
		levelAppRecord.initStartFlow(sql);		
		return "success";
	}
	
	/**
	 *  删除整个流程记录  
	 * @param objectNo  对象编号
	 * @param objectType 对象类型
	 * @param flowNo 流程编号
	 * @param nodeNo 节点编号
	 * @throws Exception 
	 */
	public static String deleteFlow(String objectNo,String objectType,String flowNo,String nodeNo) throws Exception{
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] sql = new String[2];
		//删除整个对应流程任务
		sql[0]="delete from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"'";
		sql[1]="delete from FLOW_STATE where object_no='"+objectNo+"' and object_type='"+objectType+"'";
		levelAppRecord.initStartFlow(sql);
		//初始化业务逻辑
		dealBusinesslogic(objectNo,objectType,flowNo,"888");		
		
		return "success";		
	}	
	
	/**
	 *  获得流程中的对象编号 
	 * @param paramName 参数名称
	 * @throws Exception 
	 */
	public static String initFlowRelationID(String paramName) throws Exception{
		String returnValue="";
		String sqlValue="select TOP 1 'CRM' from  TUSERINFO where 1=1";
		String productType =Utility.trimNull(ConfigUtil.getSqlResult(sqlValue));
		if("CRM".equals(productType)){
			returnValue=BusinessCheck.getFlowID(paramName);
		}
		return returnValue;		
	}	
	
	/**
	 *  初始化关联流程业务逻辑  
	 * @param objectNo  对象编号
	 * @param objectType 对象类型
	 * @param flowNo 流程编号
	 * @param nodeNo 节点编号
	 * @throws Exception 
	 */
	public static void dealBusinesslogic(String objectNo,String objectType,String flowNo,String nodeNo) throws Exception{
		//流程关联业务状态初始化处理
		String sql="select 'CRM'"
				   + "+'@@'+"
				   + " Action_Logic from FLOW_NODE where Flow_No='"+flowNo+"' and node_no='"+nodeNo+"'";
		String returnValue =Utility.trimNull(ConfigUtil.getSqlResult(sql));
		if(!"".equals(returnValue)) {
			String[] s=returnValue.split("@@");
			if(s[1]!=null && !"".equals(s[1])){
				if("CRM".equals(s[0])){
					//CRMCheckFlowLocal crmCheckFlowLocal = EJBFactory.getCRMCheckFlowWork();
					BusinessCheck.actionFlow(objectNo, objectType, s[1]);
				}
			}
		}
	}	
	
	
	//------------------------------------
	/**
	 * 初始化流程信息
	 * @param sql
	 * @throws BusiException
	 */
	public static void initStartFlow(String[] sql) throws BusiException{
		CrmDBManager.batchUpdateAuto(sql);
	}
}


