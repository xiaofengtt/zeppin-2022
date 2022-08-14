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
	 *  ��ʼ������״̬  
	 * @param paramName
	 * @throws Exception 
	 */
	public static String initFlow(String paramName) throws Exception{
		//LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		OperatorLocal local = EJBFactory.getOperator();
		String list = local.listMenuView("101",new Integer(888));
		System.out.println(list);
		String[] initValue=paramName.split("@@");
		//objectNo-������,objectType-��������,flowNo-��������,currentUser-��ǰ�û�
		String objectNo=initValue[0],objectType=initValue[1],flowNo=initValue[2],currentUser=initValue[3];	
		String[] sql = new String[2];
		sql[0]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE,FLOW_DESC, FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,  USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,Input_date)"+
			   " select '0','"+objectNo+"','"+objectType+"',NULL,fn.flow_no,fn.flow_name,fn.Node_no,fn.node_name,fn.node_flag,'"+currentUser+"',dbo.GETUSERNAME('"+currentUser+"'),dbo.GETORGNAME('"+currentUser+"','OPCODEID'),dbo.GETORGNAME('"+currentUser+"','OPCODENAME'),getdate(),convert(nvarchar(10),getdate(),120)"+
			   " from FLOW_CATALOG FC,FLOW_NODE FN where fc.flow_no=fn.flow_no and fc.init_Node=fn.Node_no and fc.flow_no='"+flowNo+"'";
		sql[1]="insert into FLOW_STATE(OBJECT_NO, OBJECT_TYPE, FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG, USER_ID,USER_NAME,DEPT_ID, DEPT_NAME, INPUT_TIME,INPUT_DATE)"+
			   " select '"+objectNo+"','"+objectType+"',fn.flow_no,fn.flow_name,fn.Node_no,fn.node_name,fn.node_flag,'"+currentUser+"',dbo.GETUSERNAME('"+currentUser+"'),dbo.GETORGNAME('"+currentUser+"','OPCODEID'),dbo.GETORGNAME('"+currentUser+"','OPCODENAME'),getdate(),convert(nvarchar(10),getdate(),120)"+
			   " from FLOW_CATALOG FC,FLOW_NODE FN where fc.flow_no=fn.flow_no and fc.init_Node=fn.Node_no and fc.flow_no='"+flowNo+"'";
		//��ʼ��ҵ���߼�
		dealBusinesslogic(objectNo,objectType,flowNo,"010");
		initStartFlow(sql);	
		//CrmDBManager.executeSql(sql);
		
		return "success";
	}

	/**
	 *  �������ժҪ  
	 * @param paramName
	 * @throws Exception 
	 */
	public static String getFlowDesc(String paramName) throws Exception{
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] initValue=paramName.split("@@");
		String objectNo=initValue[0],objectType=initValue[1],flowNo=initValue[2];
		String interfaceCode="";
		//��ö�Ӧģ��ժҪ
		interfaceCode =Utility.trimNull(ConfigUtil.getDataBaseValue("Flow_Desc","FLOW_CATALOG","Flow_No='"+flowNo+"'"));
		if(interfaceCode==null || "".equals(interfaceCode) || "0".equals(interfaceCode)) interfaceCode=BusinessCheck.getShowInterface(objectNo,objectType);
		String fieldName="",fieldCode="",valueType="",valueContent="",requiredFlag="",visibleFlag="",readonlyFlag="",showUnit="",showType="",editType="";
		String tableCode="",keyFlag="",keyField="",selectField="";
		List showfieldList = levelAppRecord.getFieldShowList(interfaceCode);//��ȡҳ�����е���ЧԪ�ؼ���
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
			//��������ֶ�
			if("1".equals(keyFlag)) keyField=fieldCode;
			//�����ʾ�ֶ�
			if(("1".equals(showType)||"2".equals(showType)||"3".equals(showType)||"4".equals(showType)||"5".equals(showType)) && ("1".equals(visibleFlag)||"2".equals(visibleFlag)||"1,2".equals(visibleFlag)))
			{
				if("1".equals(valueType)){
					selectField= selectField +"'"+fieldName+"��'+isnull(CAST(dbo.getCodeName('"+valueContent+"',"+fieldCode+") AS NVARCHAR),'')+'��;'+";
				}else if(fieldCode.indexOf("_USER")>=0){
					selectField= selectField +"'"+fieldName+"��'+isnull(CAST(dbo.getUserName("+fieldCode+") AS NVARCHAR),'')+'��;'+";
				}else if(fieldCode.indexOf("_DEPT")>=0){
					selectField= selectField +"'"+fieldName+"��'+isnull(CAST(dbo.GETORGNAME("+fieldCode+",'DEPTID') AS NVARCHAR),'')+'��;'+";
				}else if(fieldCode.indexOf("_TIME")>=0){
					selectField= selectField +"'"+fieldName+"��'+isnull(convert(nvarchar(40),"+fieldCode+",21),'')+'��;'+";
				}else if("2".equals(valueType)){
					//selectField= selectField +"'"+fieldName+"��'+isnull("+fieldCode+"_NAME,'')+'��;'+";
					
				}
				else{
					//��ȡ�ֶ���ʾ��λ
					if(!"".equals(showUnit)){
						if(showUnit.indexOf("<")>=0)
							showUnit=showUnit.substring(0, showUnit.indexOf("<"));
					}
					if("1".equals(showType) && "2".equals(keyFlag))
						selectField= selectField +"'"+fieldName+"��'+isnull("+fieldCode+",'')+'��"+showUnit+";'+";
					else
						selectField= selectField +"'"+fieldName+"��'+isnull(CAST("+fieldCode+" AS NVARCHAR),'')+'��"+showUnit+";'+";
						
				}
			}
		}
		String sql="select "+selectField+""+"''"+" from "+tableCode+" where "+keyField+"="+objectNo;
		String flowDesc =Utility.trimNull(ConfigUtil.getSqlResult(sql));
		return flowDesc;
	}
	
	/**
	 *  ��������״̬  
	 * @param paramName
	 * @throws Exception 
	 */
	public static String actionFlow(String paramName) throws Exception{
		String[] pagePassValue=paramName.split("@@");
		//nodeOpinion-�ڵ������	
		String nodeOpinion=pagePassValue[5];	
		//�������������
		if("1310".equals(nodeOpinion)){//�������˻���һ��
			takeBackFlow(paramName);
		}else if("1300".equals(nodeOpinion)){//�������˻ز�������
			backFirstFlow(paramName);
		}else if("1400".equals(nodeOpinion)){//�������ύԭ�˻���
			commitOriginFlow(paramName);
		}else if("1410".equals(nodeOpinion)){//�����������ύ��ԭ������
			commitFirstFlow(paramName);
		}else if("1500".equals(nodeOpinion) || "1510".equals(nodeOpinion) || "1520".equals(nodeOpinion)){//�����л�ǩ�ڵ������
			commitSignFlow(paramName);
		}else{							//�����������ڵ���ת
			dealwithFlow(paramName);
		}
		return "success";		
	}

	/**
	 *  �����������˻�����  
	 * @param paramName
	 * @throws Exception 
	 */
	public static void takeBackFlow(String paramName) throws Exception{
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] pagePassValue=paramName.split("@@");
		//objectNo-������,objectType-��������,flowNo-��������,nodeNo-��ǰ�ڵ�	
		String taskID=pagePassValue[0],objectNo=pagePassValue[1],objectType=pagePassValue[2],flowNo=pagePassValue[3],nodeNo=pagePassValue[4];	
		String[] sql = new String[4];
		//���Ʋ������½ڵ�����
		sql[0]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE, FLOW_DESC,FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,  USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,INPUT_DATE)"+
			   " select '"+taskID+"',OBJECT_NO,OBJECT_TYPE,FLOW_DESC,FLOW_NO,FLOW_NAME,NODE_NO,NODE_NAME,NODE_FLAG,"+
			   " user_id,user_name,dept_id,dept_name,getdate(),convert(nvarchar(10),getdate(),120)"+
			   " from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')";	
		//������һ�ڵ�����״̬
		sql[1]="update FLOW_STATE set node_no=(select top 1 node_no from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')),"+
			   " node_name=(select top 1 node_name from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')),"+
			   " node_flag=(select top 1 node_flag from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')),"+
			   " input_date=convert(nvarchar(10),getdate(),120)"+
			   " where object_no='"+objectNo+"' and object_type='"+objectType+"'";			
		//������ǰ�ڵ�����
		sql[2]="update FLOW_TASK set end_time=getdate() where task_id='"+taskID+"'";
		//ɾ����ǰ�ڵ�����δ�������
		sql[3]="delete from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and node_no='"+nodeNo+"' and (end_time is null or end_time='') and task_id!='"+taskID+"'";
		levelAppRecord.initStartFlow(sql);		
	}

	/**
	 *  �����������ύ��ԭ�˻���
	 * @param paramName
	 * @throws Exception 
	 */
	public static void commitOriginFlow(String paramName) throws Exception{
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] pagePassValue=paramName.split("@@");
		//objectNo-������,objectType-��������,flowNo-��������,nodeNo-��ǰ�ڵ�	
		String taskID=pagePassValue[0],objectNo=pagePassValue[1],objectType=pagePassValue[2],flowNo=pagePassValue[3],nodeNo=pagePassValue[4];	
		String[] sql = new String[4];
		//���Ʋ������½ڵ�����
		sql[0]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE, FLOW_DESC,FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,  USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,INPUT_DATE)"+
			   " select '"+taskID+"',OBJECT_NO,OBJECT_TYPE,(select top 1 FLOW_DESC from FLOW_TASK where task_id='"+taskID+"'),FLOW_NO,FLOW_NAME,NODE_NO,NODE_NAME,NODE_FLAG,"+
			   " user_id,user_name,dept_id,dept_name,getdate(),convert(nvarchar(10),getdate(),120)"+
			   " from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')";
		//������һ�ڵ�����״̬
		sql[1]="update FLOW_STATE set node_no=(select top 1 node_no from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')),"+
			   " node_name=(select top 1 node_name from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')),"+
			   " node_flag=(select top 1 node_flag from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')),"+
			   " input_date=convert(nvarchar(10),getdate(),120)"+
			   " where object_no='"+objectNo+"' and object_type='"+objectType+"'";			
		//������ǰ�ڵ�����
		sql[2]="update FLOW_TASK set end_time=getdate() where task_id='"+taskID+"'";
		//ɾ����ǰ�ڵ�����δ�������
		sql[3]="delete from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and node_no='"+nodeNo+"' and (end_time is null or end_time='') and task_id!='"+taskID+"'";
		levelAppRecord.initStartFlow(sql);			
	}

	/**
	 *  �����������˻ز������ϵ�ԭʼ�ڵ�  
	 * @param paramName
	 * @throws Exception 
	 */
	public static void backFirstFlow(String paramName) throws Exception{
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] pagePassValue=paramName.split("@@");
		//taskID-������,objectNo-������,objectType-��������,flowNo-��������,nodeNo-��ǰ�ڵ�
		String taskID=pagePassValue[0],objectNo=pagePassValue[1],objectType=pagePassValue[2],flowNo=pagePassValue[3],nodeNo=pagePassValue[4];	
		String[] sql = new String[4];
		//���Ʋ������½ڵ�����
		sql[0]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE, FLOW_DESC,FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,  USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,INPUT_DATE)"+
		   " select '"+taskID+"',OBJECT_NO,OBJECT_TYPE,NULL,FLOW_NO,FLOW_NAME,'300','�˻ز�������','middle',"+
		   " user_id,user_name,dept_id,dept_name,getdate(),convert(nvarchar(10),getdate(),120)"+
		   " from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and RELATIVE_NO='0'";		
		//������һ�ڵ�����״̬
		sql[1]="update FLOW_STATE set node_no='300',node_name='�˻ز�������',node_flag='middle',input_date=convert(nvarchar(10),getdate(),120) where object_no='"+objectNo+"' and object_type='"+objectType+"'";
		//������ǰ�ڵ�����
		sql[2]="update FLOW_TASK set end_time=getdate() where task_id='"+taskID+"'";
		//ɾ����ǰ�ڵ�����δ�������
		sql[3]="delete from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and node_no='"+nodeNo+"' and (end_time is null or end_time='') and task_id!='"+taskID+"'";
		levelAppRecord.initStartFlow(sql);		
	}	
	
	/**
	 *  �����������ύԭ������  
	 * @param paramName
	 * @throws Exception 
	 */
	public static void commitFirstFlow(String paramName) throws Exception{
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] pagePassValue=paramName.split("@@");
		//taskID-������,objectNo-������,objectType-��������,flowNo-��������,nodeNo-��ǰ�ڵ�
		String taskID=pagePassValue[0],objectNo=pagePassValue[1],objectType=pagePassValue[2],flowNo=pagePassValue[3],nodeNo=pagePassValue[4];	
		String[] sql = new String[4];
		//���Ʋ������½ڵ�����
		sql[0]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE, FLOW_DESC,FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,  USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,INPUT_DATE)"+
		   " select '"+taskID+"',OBJECT_NO,OBJECT_TYPE,(select flow_desc from FLOW_TASK ft where task_id='"+taskID+"'),FLOW_NO,FLOW_NAME,'500','ԭ������ȷ��','middle',"+
		   " user_id,user_name,dept_id,dept_name,getdate(),convert(nvarchar(10),getdate(),120)"+
		   " from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and RELATIVE_NO='0'";		
		//������һ�ڵ�����״̬
		sql[1]="update FLOW_STATE set node_no='500',node_name='ԭ������ȷ��',node_flag='middle',input_date=convert(nvarchar(10),getdate(),120) where object_no='"+objectNo+"' and object_type='"+objectType+"'";
		//������ǰ�ڵ�����
		sql[2]="update FLOW_TASK set end_time=getdate() where task_id='"+taskID+"'";
		//ɾ����ǰ�ڵ�����δ�������
		sql[3]="delete from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and node_no='"+nodeNo+"' and (end_time is null or end_time='') and task_id!='"+taskID+"'";
		levelAppRecord.initStartFlow(sql);		
	}	
	
	
	/**
	 *  ��ʼ��������ÿ���ڵ�����  
	 * @param paramName
	 * @throws Exception 
	 */
	public static void dealwithFlow(String paramName) throws Exception{
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] pagePassValue=paramName.split("@@");
		String objectWhere="";
		//taskID-������,objectNo-������,objectType-��������,flowNo-��������,nodeNo-��ǰ�ڵ�,nextUser-��һ�ڵ㴦����
		String taskID=pagePassValue[0],objectNo=pagePassValue[1],objectType=pagePassValue[2],flowNo=pagePassValue[3],nodeNo=pagePassValue[4],nextUser=pagePassValue[6];	
		//ȡ�¸��ڵ�ĳ�ʼ����Ϣ
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
		if("hand".equals(driveType)){//�ֹ�����ڵ�
			objectWhere=" charindex('"+nextUser+"',op_code)!=0";
		}else if("auto".equals(driveType)){//�Զ�����ڵ�
			//ȡ�¸��׶εĲ�������
			objectWhere=getOperateUser(flowNo,nextNodeNo,userID,deptID);
		}
		//������û�����ò�������
		String[] sql = new String[4];
		if("".equals(objectWhere)) objectWhere="1=2";
		//���ɵ�ǰ�ڵ�����
		if("100".equals(nextNodeNo) || "200".equals(nextNodeNo)){//��������ͨ�����������
			sql[0]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE, flow_desc,FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,  NODE_OPINION,NODE_OPINION_NAME,DRIVE_TYPE,NEXT_NODE,USER_OPINION,USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,END_TIME,INPUT_DATE)"+
			   " select '"+taskID+"','"+objectNo+"','"+objectType+"',(select flow_desc from FLOW_TASK ft where task_id='"+taskID+"'),'"+flowNo+"','"+flowName+"','"+nextNodeNo+"','"+nextNodeName+"','"+nextNodeFlag+"','1900','�������ս���','auto','999','"+nextNodeName+"',"+
			   " op_code,op_name,depart_id,dbo.GETORGNAME(depart_id,'DEPTID'),getdate(),getdate(),convert(nvarchar(10),getdate(),120)"+
			   " from toperator where "+objectWhere;
			//��ʼ��ҵ���߼�
			dealBusinesslogic(objectNo,objectType,flowNo,nextNodeNo);			
		}else{
			sql[0]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE, flow_desc,FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,  USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,INPUT_DATE)"+
			   " select '"+taskID+"','"+objectNo+"','"+objectType+"',(select flow_desc from FLOW_TASK ft where task_id='"+taskID+"'),'"+flowNo+"','"+flowName+"','"+nextNodeNo+"','"+nextNodeName+"','"+nextNodeFlag+"',"+
			   " op_code,op_name,depart_id,dbo.GETORGNAME(depart_id,'DEPTID'),getdate(),convert(nvarchar(10),getdate(),120)"+
			   " from toperator where "+objectWhere;
		}		
		//������һ�ڵ�����״̬
		sql[1]="update FLOW_STATE set node_no='"+nextNodeNo+"',node_name='"+nextNodeName+"',node_flag='"+nextNodeFlag+"',input_date=convert(nvarchar(10),getdate(),120) where object_no='"+objectNo+"' and object_type='"+objectType+"'";
		//������ǰ�ڵ�����
		sql[2]="update FLOW_TASK set end_time=getdate() where task_id='"+taskID+"'";
		//ɾ����ǰ�ڵ�����δ�������
		sql[3]="delete from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and node_no='"+nodeNo+"' and (end_time is null or end_time='') and task_id!='"+taskID+"'";
		levelAppRecord.initStartFlow(sql);		
	}

	/**
	 *  ��������е�ǰ�ڵ������Ա  
	 * @param paramName
	 * @throws Exception 
	 */
	public static String getOperateUser(String flowNo,String nodeNo,String userID,String deptID) throws Exception{
		String objectWhere="",operateObjectType="";	
		String flowObjectValue=ConfigUtil.getQueryPrintSql("select relation_type,relation_no from FLOW_OBJECT where flow_no='"+flowNo+"' and  node_no='"+nodeNo+"'",2);
		String[] flowRelationValue=flowObjectValue.split("@@");
		//��ʼ�����������¼��Ϣ
		for(int i=0;i<flowRelationValue.length/2;i++){
			operateObjectType=flowRelationValue[2*i];
			String[] operateUserValue=flowRelationValue[2*i+1].split(",");
			//������һ���׶�����
			if("User".equals(operateObjectType)){//���û�����
				for(int j=0;j<operateUserValue.length;j++){
					if("".equals(objectWhere)) objectWhere=" op_code='"+operateUserValue[j]+"'";
					else objectWhere=objectWhere+" or op_code='"+operateUserValue[j]+"'";					   
				}
			}else if("Role".equals(operateObjectType)){//����ɫ����
				for(int j=0;j<operateUserValue.length;j++){
					if("".equals(objectWhere)) objectWhere="exists (select 1 from TOPROLE where role_id='"+operateUserValue[j]+"' and toperator.op_code=op_code)";
					else objectWhere=objectWhere+" or exists (select 1 from TOPROLE where role_id='"+operateUserValue[j]+"' and toperator.op_code=op_code)";					
				}			
			}else if("WorkGroup".equals(operateObjectType)){//������������
				for(int j=0;j<operateUserValue.length;j++){
					if("".equals(objectWhere)) objectWhere=" exists (select 1 from TWORKGROUPUSERS where workGroupID='"+operateUserValue[j]+"' and toperator.op_code=userID)";
					else objectWhere=objectWhere+" or exists (select 1 from TWORKGROUPUSERS where workGroupID='"+operateUserValue[j]+"' and toperator.op_code=userID)";					
				}				
			}else if("Org".equals(operateObjectType)){//����������
				for(int j=0;j<operateUserValue.length;j++){
					if("".equals(objectWhere)) objectWhere=" depart_id='"+operateUserValue[j]+"'";
					else objectWhere=objectWhere+" or depart_id='"+operateUserValue[j]+"'";					
				}				
			}else if("OrgRole".equals(operateObjectType)){//�������½�ɫ����
				for(int j=0;j<operateUserValue.length;j++){
					if("".equals(objectWhere)) objectWhere="exists (select 1 from TOPROLE where role_id='"+operateUserValue[j]+"' and toperator.op_code=op_code) and Depart_ID='"+deptID+"'";
					else objectWhere=objectWhere+" or (exists (select 1 from TOPROLE where role_id='"+operateUserValue[j]+"' and toperator.op_code=op_code) and Depart_ID='"+deptID+"')";					
				}			
			}
		}
		return objectWhere;
	}
	
	/**
	 *  ���������ǩ�ڵ�  
	 * @param paramName
	 * @throws Exception 
	 */
	public static String commitSignFlow(String paramName) throws Exception{	
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] pagePassValue=paramName.split("@@");
		String objectWhere="";
		//taskID-������,objectNo-������,objectType-��������,flowNo-��������,nodeNo-��ǰ�ڵ�
		String taskID=pagePassValue[0],objectNo=pagePassValue[1],objectType=pagePassValue[2],flowNo=pagePassValue[3],nodeNo=pagePassValue[4];	
		//ȡ�¸��ڵ�ĳ�ʼ����Ϣ
		String sqlValue="select isnull(sign_flag,'')+'@@'+isnull(sign_type,'')+'@@'+isnull(sign_result,'')"
						+ " from FLOW_node ft where flow_no='"+flowNo+"' and node_no='"+nodeNo+"'";
		String flowValue =Utility.trimNull(ConfigUtil.getSqlResult(sqlValue));	
		String[] result=flowValue.split("@@");
		String signFlag=result[0],signType=result[1],signResult=result[2];
		//�жϵ�ǰ�ڵ��Ƿ��ǩ�ڵ�
		if("1".equals(signFlag)){
			sqlValue="select count(1) from FLOW_TASK where task_id!= '"+taskID+"' and object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and node_No='"+nodeNo+"' and (end_time is null or end_time='')";
			String returnValue =Utility.trimNull(ConfigUtil.getSqlResult(sqlValue));		
			if("0".equals(returnValue)){
				String[] sql = new String[2];
				//���㵱ǰ��ǩ�ڵ���������
				sqlValue="select convert(nvarchar(10),sum(case when node_opinion='1500' then 1 else 0 end))+'@@'" +
								"+convert(nvarchar(10),sum(case when node_opinion='1510' then 1 else 0 end))+'@@'"+
								"+convert(nvarchar(10),sum(case when node_opinion='1520' then 1 else 0 end))"+
								" from FLOW_TASK  where object_no='"+objectNo+"' and object_type='"+objectType+"'"+
								" and flow_No='"+flowNo+"' and node_No='"+nodeNo+"'"+
								" and relative_no=(select top 1 relative_no from FLOW_TASK where task_id='"+taskID+"')";
				String opinionValue =Utility.trimNull(ConfigUtil.getSqlResult(sqlValue));
				String[] resultValue=opinionValue.split("@@");
				String passCount=resultValue[0],failCount=resultValue[1],quitCount=resultValue[2];
				//������ǰ�ڵ�����
				sql[0]="update FLOW_TASK set end_time=getdate() where task_id='"+taskID+"'";					
				//�����»�ǩ����ڵ�����
				if(Integer.parseInt(passCount)>=Integer.parseInt(failCount) && "010".equals(signResult)){
					sql[1]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE, flow_desc,FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,NODE_OPINION,NODE_OPINION_NAME,DRIVE_TYPE,NEXT_NODE,USER_OPINION,USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,END_TIME,INPUT_DATE)"+
					   " select '"+taskID+"',OBJECT_NO, OBJECT_TYPE, flow_desc,FLOW_NO, FLOW_NAME, NODE_NO,NODE_NAME, NODE_FLAG,'1530','��ǩ������',DRIVE_TYPE,'100','"+passCount+"����ͬ�⣻"+failCount+"���˲�ͬ�⣻"+quitCount+"���˷�������������ǩ�����ս���ǡ�ͬ�⡿',"+
					   " '888',dbo.GETUSERNAME('888'),dbo.GETORGNAME('888','OPCODEID'),dbo.GETORGNAME('888','OPCODENAME'),getdate(),getdate(),convert(nvarchar(10),getdate(),120)"+
					   " from FLOW_TASK where task_ID='"+taskID+"'";
				}else if(Integer.parseInt(passCount)>=1 && "020".equals(signResult)){
					sql[1]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE, flow_desc,FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,NODE_OPINION,NODE_OPINION_NAME,DRIVE_TYPE,NEXT_NODE,USER_OPINION,USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,END_TIME,INPUT_DATE)"+
					   " select '"+taskID+"',OBJECT_NO, OBJECT_TYPE, flow_desc,FLOW_NO, FLOW_NAME, NODE_NO,NODE_NAME, NODE_FLAG,'1530','��ǩ������',DRIVE_TYPE,'100','"+passCount+"����ͬ�⣻"+failCount+"���˲�ͬ�⣻"+quitCount+"���˷�������������ǩ�����ս���ǡ�ͬ�⡿',"+
					   " '888',dbo.GETUSERNAME('888'),dbo.GETORGNAME('888','OPCODEID'),dbo.GETORGNAME('888','OPCODENAME'),getdate(),getdate(),convert(nvarchar(10),getdate(),120)"+
					   " from FLOW_TASK where task_ID='"+taskID+"'";
				}else{
					sql[1]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE, flow_desc,FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,NODE_OPINION,NODE_OPINION_NAME,DRIVE_TYPE,NEXT_NODE,USER_OPINION,USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,END_TIME,INPUT_DATE)"+
					   " select '"+taskID+"',OBJECT_NO, OBJECT_TYPE, flow_desc,FLOW_NO, FLOW_NAME, NODE_NO,NODE_NAME, NODE_FLAG,'1530','��ǩ������',DRIVE_TYPE,'200','"+passCount+"����ͬ�⣻"+failCount+"���˲�ͬ�⣻"+quitCount+"���˷�������������ǩ�����ս���ǡ���ͬ�⡿',"+
					   " '888',dbo.GETUSERNAME('888'),dbo.GETORGNAME('888','OPCODEID'),dbo.GETORGNAME('888','OPCODENAME'),getdate(),getdate(),convert(nvarchar(10),getdate(),120)"+
					   " from FLOW_TASK where task_ID='"+taskID+"'";					
				}				
				levelAppRecord.initStartFlow(sql);
				//������ǰ�ڵ�����
				sqlValue="select max(task_id) from FLOW_TASK  where object_no='"+objectNo+"' and object_type='"+objectType+"'"+
						" and flow_No='"+flowNo+"' and node_No='"+nodeNo+"'";
				String newTaskID =Utility.trimNull(ConfigUtil.getSqlResult(sqlValue));				
				//��������һ���ڵ������
				dealwithFlow(newTaskID+"@@"+objectNo+"@@"+objectType+"@@"+flowNo+"@@"+nodeNo+"@@"+"0"+"@@"+"0");
			}else{
				String[] sql = new String[1];
				//������ǰ�ڵ�����
				sql[0]="update FLOW_TASK set end_time=getdate() where task_id='"+taskID+"'";	
				levelAppRecord.initStartFlow(sql);
			}
		}else{
			dealwithFlow(paramName);
		}
		return "success";		
	}
		
	/**
	 *  �������������ջ�  
	 * @param paramName
	 * @throws Exception 
	 */
	public static String backFlow(String paramName) throws Exception{
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] initValue=paramName.split("@@");
		//objectNo-������,objectType-��������,flowNo-��������,nodeNo-��ʼ�ڵ�,currentUser-��ǰ�ջ���
		String objectNo=initValue[0],objectType=initValue[1],flowNo=initValue[2],nodeNo=initValue[3],currentUser=initValue[4];	
		String[] sql = new String[3];
		String sqlValue="select count(1) from FLOW_TASK where Node_Opinion >0 and object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"'"
					   +" and relative_no=(select max(task_id) from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and user_id ='"+currentUser+"' and (end_time is not null or end_time!=''))";
		String returnValue =Utility.trimNull(ConfigUtil.getSqlResult(sqlValue));	
		if("".equals(returnValue) || "0".equals(returnValue)){
			//������һ�ڵ�����״̬
			sql[0]="update FLOW_STATE set node_no=(select top 1 node_no from FLOW_TASK where task_id=(select max(task_id) from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and user_id ='"+currentUser+"' and (end_time is not null or end_time!=''))),"+
				   " node_name=(select top 1 node_name from FLOW_TASK where task_id=(select max(task_id) from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and user_id ='"+currentUser+"' and (end_time is not null or end_time!=''))),"+
				   " node_flag=(select top 1 node_flag from FLOW_TASK where task_id=(select max(task_id) from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and user_id ='"+currentUser+"' and (end_time is not null or end_time!=''))),"+
				   " input_date=convert(nvarchar(10),getdate(),120)"+
				   " where object_no='"+objectNo+"' and object_type='"+objectType+"'";	
			//ɾ����ǰ�ڵ�����δ�������
			sql[1]="delete from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and node_no='"+nodeNo+"'"
				  +" and relative_no=(select max(task_id) from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and user_id ='"+currentUser+"' and (end_time is not null or end_time!=''))";
			//���ɵ�ǰ�ڵ�����
			sql[2]="update FLOW_TASK set end_time=NULL where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and user_id ='"+currentUser+"'"
				  +" and task_id=(select max(task_id) from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and user_id ='"+currentUser+"' and (end_time is not null or end_time!=''))";
			levelAppRecord.initStartFlow(sql);
			returnValue="success";
		}
		return returnValue;
	}	
	
	/**
	 *  �������������˻���һ��  
	 * @param paramName
	 * @throws Exception 
	 */
	public static String untreadFlow(String paramName) throws Exception{
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] initValue=paramName.split("@@");
		//taskID-������,objectNo-������,objectType-��������,flowNo-��������,nodeNo-��ʼ�ڵ�
		String taskID=initValue[0],objectNo=initValue[1],objectType=initValue[2],flowNo=initValue[3],nodeNo=initValue[4];	
		String[] sql = new String[3];
		String sqlValue="select count(1) from FLOW_TASK where task_id='"+taskID+"'";
		String returnValue =Utility.trimNull(ConfigUtil.getSqlResult(sqlValue));	
		if("1".equals(returnValue)){
			//���ɵ�ǰ�ڵ�����
			sql[0]="update FLOW_TASK set end_time=NULL where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"'"
			  	  +" and task_id=(select relative_no from FLOW_TASK where task_id='"+taskID+"')";		
			//������һ�ڵ�����״̬
			sql[1]="update FLOW_STATE set node_no=(select top 1 node_no from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')),"+
				   " node_name=(select top 1 node_name from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')),"+
				   " node_flag=(select top 1 node_flag from FLOW_TASK where task_id=(select RELATIVE_NO from FLOW_TASK where task_id='"+taskID+"')),"+
				   " input_date=convert(nvarchar(10),getdate(),120)"+
				   " where object_no='"+objectNo+"' and object_type='"+objectType+"'";	
			//ɾ����ǰ�ڵ�����δ�������
			sql[2]="delete from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"'"
				  +" and relative_no=(select relative_no from FLOW_TASK where task_id='"+taskID+"')";
			levelAppRecord.initStartFlow(sql);
			returnValue="success";
		}
		return returnValue;
	}	
	
	/**
	 *  ������������ί�� 
	 * @param paramName
	 * @throws Exception 
	 */
	public static String consignFlowTask(String paramName) throws Exception{	
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] initValue=paramName.split("@@");
		//taskID-������,objectNo-������,objectType-��������,flowNo-��������,nodeNo-��ʼ�ڵ�,nodeNo-ί���û�,
		String taskID=initValue[0],objectNo=initValue[1],objectType=initValue[2],flowNo=initValue[3],nodeNo=initValue[4],userID=initValue[5];	
		String[] sql = new String[3];
		//ɾ����ǰ�ڵ�����δ�������
		sql[0]="delete from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"' and flow_No='"+flowNo+"' and node_no='"+nodeNo+"' and (end_time is null or end_time='') and task_id!='"+taskID+"'";		
		//������ǰ�ڵ�����
		sql[1]="update FLOW_TASK set end_time=getdate(),NODE_OPINION='1610',NODE_OPINION_NAME='����ί��',DRIVE_TYPE='hand',NEXT_NODE=NODE_NO,USER_OPINION='ԭ�������ǡ�'+USER_NAME+'��ί�и���'+dbo.GETUSERNAME('"+userID+"')+'������;'+isnull(USER_OPINION,'')"
		  	  +" where task_id='"+taskID+"'";		
		//�����½ڵ�����
		sql[2]="insert into FLOW_TASK(RELATIVE_NO,OBJECT_NO, OBJECT_TYPE, flow_desc,FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,INPUT_DATE)"+
		   " select '"+taskID+"',OBJECT_NO, OBJECT_TYPE, flow_desc,FLOW_NO, FLOW_NAME, NODE_NO, '��ί�С�'+NODE_NAME, NODE_FLAG,"+
		   " '"+userID+"',dbo.GETUSERNAME('"+userID+"'),dbo.GETORGNAME('"+userID+"','OPCODEID'),dbo.GETORGNAME('"+userID+"','OPCODENAME'),getdate(),convert(nvarchar(10),getdate(),120)"+
		   " from FLOW_TASK where task_ID='"+taskID+"'";	
		levelAppRecord.initStartFlow(sql);		
		return "success";
	}
	
	/**
	 *  ɾ���������̼�¼  
	 * @param objectNo  ������
	 * @param objectType ��������
	 * @param flowNo ���̱��
	 * @param nodeNo �ڵ���
	 * @throws Exception 
	 */
	public static String deleteFlow(String objectNo,String objectType,String flowNo,String nodeNo) throws Exception{
		LevelAppRecordLocal levelAppRecord = EJBFactory.getLevelAppRecord();
		String[] sql = new String[2];
		//ɾ��������Ӧ��������
		sql[0]="delete from FLOW_TASK where object_no='"+objectNo+"' and object_type='"+objectType+"'";
		sql[1]="delete from FLOW_STATE where object_no='"+objectNo+"' and object_type='"+objectType+"'";
		levelAppRecord.initStartFlow(sql);
		//��ʼ��ҵ���߼�
		dealBusinesslogic(objectNo,objectType,flowNo,"888");		
		
		return "success";		
	}	
	
	/**
	 *  ��������еĶ����� 
	 * @param paramName ��������
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
	 *  ��ʼ����������ҵ���߼�  
	 * @param objectNo  ������
	 * @param objectType ��������
	 * @param flowNo ���̱��
	 * @param nodeNo �ڵ���
	 * @throws Exception 
	 */
	public static void dealBusinesslogic(String objectNo,String objectType,String flowNo,String nodeNo) throws Exception{
		//���̹���ҵ��״̬��ʼ������
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
	 * ��ʼ��������Ϣ
	 * @param sql
	 * @throws BusiException
	 */
	public static void initStartFlow(String[] sql) throws BusiException{
		CrmDBManager.batchUpdateAuto(sql);
	}
}


