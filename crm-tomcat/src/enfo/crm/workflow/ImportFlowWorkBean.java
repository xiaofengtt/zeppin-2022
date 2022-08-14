package enfo.crm.workflow;

//import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmBusiExBean;
//import enfo.crm.dao.CrmJdbcTemplate;
//import enfo.crm.project.LevelAppRecordLocal;
import enfo.crm.tools.Argument;
import enfo.crm.tools.Utility;

@Component(value="importFlowWork")
public class ImportFlowWorkBean extends CrmBusiExBean implements ImportFlowWorkLocal {
	
	/* (non-Javadoc)
	 * @see enfo.crm.workflow.ImportFlowWorkLocal#del_nodes_lines(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public void del_nodes_lines(String ProjectId, String NodesStr, String LinesStr, Integer input_operatorCode) throws BusiException {
		String delNodeStrIn = "", delLineStrIn = "";
		String[] nodeArr = Utility.splitString(NodesStr, "β");
		String[] lineArr = Utility.splitString(LinesStr, "β");
		for (int i = 0; i < nodeArr.length; i++)// 取单行数据
		{
			if(i==0){
				delNodeStrIn = " and ISNULL(NODE_CODE,'') not in ('" + Utility.trimNull(nodeArr[i]) + "' ";
			}else{
				delNodeStrIn = delNodeStrIn + ", '"  +Utility.trimNull(nodeArr[i]) + "'";
			}
			if(i==nodeArr.length-1){
				delNodeStrIn = delNodeStrIn + ") ";
			}
		}
		
		for (int i = 0; i < lineArr.length; i++)// 取单行数据
		{
			if(i==0){
				delLineStrIn = "  and ISNULL(DRIVE_CODE,'') not in ('" + Utility.trimNull(lineArr[i]) + "' ";
			}else{
				delLineStrIn = delLineStrIn + ", '"  +Utility.trimNull(lineArr[i]) + "'";
			}
			if(i==lineArr.length-1){
				delLineStrIn = delLineStrIn + ") ";
			}
		}
		String[] deleteSql = new String[6];
		deleteSql[0] = "delete from FLOW_NODEDRAW where FLOW_NO = '" + ProjectId + "' " + delNodeStrIn;
		deleteSql[1] = "delete from FLOW_DRIVEDRAW where FLOW_NO = '" + ProjectId + "' " + delLineStrIn;
		deleteSql[2] = "delete from FLOW_DRIVEDRAW where FLOW_NO = '" + ProjectId + "' "
					+ " AND ( NOT EXISTS (SELECT 1 FROM FLOW_NODEDRAW B WHERE B.FLOW_NO = FLOW_DRIVEDRAW.FLOW_NO" 
					+ " AND B.NODE_CODE = FLOW_DRIVEDRAW.FROM_NODE)"
					+ " OR NOT EXISTS (SELECT 1 FROM FLOW_NODEDRAW B WHERE B.FLOW_NO = FLOW_DRIVEDRAW.FLOW_NO" 
					+ " AND B.NODE_CODE = FLOW_DRIVEDRAW.TO_NODE))";
		deleteSql[3] = "delete from FLOW_NODE where FLOW_NO = '" + ProjectId + "' " + delNodeStrIn;
		deleteSql[4] = "delete from FLOW_OBJECT where FLOW_NO = '" + ProjectId + "' " + delNodeStrIn;
		deleteSql[5] = "delete from FLOW_DRIVE where FLOW_NO = '" + ProjectId + "' " 
					  + " and NOT EXISTS (SELECT 1 FROM FLOW_DRIVEDRAW B WHERE B.FLOW_NO = FLOW_DRIVE.FLOW_NO" 
					  + " AND B.DRIVE_CODE = FLOW_DRIVE.DRIVE_CODE)";
		batchUpdateEx(deleteSql);
	}

	/* (non-Javadoc)
	 * @see enfo.crm.workflow.ImportFlowWorkLocal#del_workflow(java.lang.String, java.lang.Integer, boolean)
	 */
	@Override
	public void del_workflow(String projectId, Integer input_operatorCode, boolean keyFlag) throws BusiException {
		String flow_no = Utility.trimNull(projectId);
		if ("".equals(Utility.trimNull(flow_no))) {
			throw new BusiException("流程类型不正确，请核对！");
		}
		String[] deleteSql = new String[2];
		deleteSql[0] = "delete from FLOW_NODEDRAW where FLOW_NO = '" + Utility.trimNull(flow_no) + "'";
		deleteSql[1] = "delete from FLOW_DRIVEDRAW where FLOW_NO = '" + Utility.trimNull(flow_no) + "'";
		if(keyFlag){//传进来的是流程主键
			deleteSql[0] = "delete from FLOW_NODEDRAW where FLOW_NO = (select FLOW_NO FROM FLOW_CATALOG WHERE CATALOG_ID = " + Utility.parseInt(flow_no, 0) + ")";
			deleteSql[1] = "delete from FLOW_DRIVEDRAW where FLOW_NO = (select FLOW_NO FROM FLOW_CATALOG WHERE CATALOG_ID = " + Utility.parseInt(flow_no, 0) + ")";
		}
		batchUpdateEx(deleteSql);			
	}

	/* (non-Javadoc)
	 * @see enfo.crm.workflow.ImportFlowWorkLocal#import_workflow_lines(java.lang.String, java.lang.String[], java.lang.Integer)
	 */
	@Override
	public void import_workflow_lines(String projectId, String[] content,Integer input_operatorCode) throws BusiException {
		if (content == null || content.length <= 0) {
			throw new BusiException("取出的文件内容为空！请检查程序！");
		}

		String[] execSqlArr = new String[content.length];
		for (int i = 0; i < content.length; i++)// 取单行数据
		{
			String tempContent = content[i];
			String[] rowValue = Utility.splitString(tempContent, "β");// 对单行数据进行分解，分离出每一字段的数据，赋值到数组中
			String existSql = "select 1 from FLOW_DRIVEDRAW where FLOW_NO ='" + projectId+ "' and DRIVE_CODE = '" + Utility.trimNull(rowValue[0]) + "'";
			String execSql = "";
			Integer _number = Utility.parseInt(rowValue[4], null);
			String property = Utility.trimNull(rowValue[rowValue.length-1]);
			if("".equals(property) || "null".equalsIgnoreCase(property)){
				property = null;
			}
			if(Argument.isExistsBySql(existSql, null, null, true)){
				execSql = "UPDATE FLOW_DRIVEDRAW SET DRIVE_NAME ='" + Utility.trimNull(rowValue[1]) 
					+ "', TYPE= '" + Utility.trimNull(rowValue[2]) + "', SHAPE= '" + Utility.trimNull(rowValue[3]) 
					+ "', NUMBER= " + (_number==null ? "NULL" : "'" + _number + "'")
					+ ", FROM_NODE= '" + Utility.trimNull(rowValue[5]) + "', TO_NODE= '" + Utility.trimNull(rowValue[6]) 
					+ "', FROMX= '" + Utility.trimNull(rowValue[7]) + "', FROMY= '" + Utility.trimNull(rowValue[8])
					+ "', TOX= '" + Utility.trimNull(rowValue[9]) + "', TOY= '" + Utility.trimNull(rowValue[10])
					+ "', PROPERTY= " + (property==null ? "NULL" : "'" + property + "'");
				execSql = execSql + " where FLOW_NO ='" + projectId + "' and DRIVE_CODE = '" + Utility.trimNull(rowValue[0]) + "'";
			}else{
				execSql = "INSERT INTO FLOW_DRIVEDRAW(FLOW_NO, DRIVE_CODE, DRIVE_NAME, TYPE, SHAPE, NUMBER, "
					+ "FROM_NODE, TO_NODE, FROMX, FROMY, TOX, TOY, PROPERTY)"
					+ "VALUES('" + projectId 
					+ "', '" + Utility.trimNull(rowValue[0]) + "', '" + Utility.trimNull(rowValue[1]) 
					+ "', '" + Utility.trimNull(rowValue[2])+ "', '" + Utility.trimNull(rowValue[3]) 
					+ "', " + (_number==null ? "NULL" : "'" + _number + "'")
					+ ", '" + Utility.trimNull(rowValue[5])+ "', '" + Utility.trimNull(rowValue[6]) 
					+ "', '" + Utility.trimNull(rowValue[7]) + "', '" + Utility.trimNull(rowValue[8]) 
					+ "', '" + Utility.trimNull(rowValue[9]) + "', '" + Utility.trimNull(rowValue[10])
					+ "', " + (property==null ? "NULL" : "'" + property + "'") 
					+ ")";
			}
			execSqlArr[i] = execSql;
		}
		batchUpdateEx(execSqlArr);	
	}

	/* (non-Javadoc)
	 * @see enfo.crm.workflow.ImportFlowWorkLocal#import_workflow_nodes(java.lang.String, java.lang.String[], java.lang.Integer)
	 */
	@Override
	public void import_workflow_nodes(String projectId, String[] content, Integer input_operatorCode) throws BusiException {
		if (content == null || content.length <= 0) {
			throw new BusiException("取出的文件内容为空！请检查程序！");
		}
		String[] execSqlArr = new String[content.length];
		for (int i = 0; i < content.length; i++)// 取单行数据
		{
			String tempContent = content[i];
			String[] rowValue = tempContent.split("β");// 用jdk1.4提供的方法进行数据分解
			String existSql = "select 1 from FLOW_NODEDRAW where FLOW_NO ='"+projectId+ "' and NODE_CODE = '" + Utility.trimNull(rowValue[0]) + "'";
			String execSql = "";
			Integer _number = Utility.parseInt(rowValue[4], null);
			String property = Utility.trimNull(rowValue[rowValue.length-1]);
			if("".equals(property) || "null".equalsIgnoreCase(property)){
				property = null;
			}
			
			if(Argument.isExistsBySql(existSql, null, null, true)){
				execSql = "UPDATE FLOW_NODEDRAW SET NODE_NAME ='" + Utility.trimNull(rowValue[1]) 
					+ "', TYPE= '" + Utility.trimNull(rowValue[2]) + "', SHAPE= '" + Utility.trimNull(rowValue[3]) 
					+ "', NUMBER= " + (_number==null ? "NULL" : "'" + _number + "'")
					+ ", LEFT1= '" + Utility.trimNull(rowValue[5]) + "', TOP1= '" + Utility.trimNull(rowValue[6]) 
					+ "', WIDTH= '" + Utility.trimNull(rowValue[7]) + "', HEIGHT= '" + Utility.trimNull(rowValue[8])
					+ "', PROPERTY= " + (property==null ? "NULL" : "'" + property + "'");
				execSql = execSql + " where FLOW_NO ='" + projectId + "' and NODE_CODE = '" + Utility.trimNull(rowValue[0]) + "'";
			}else{
				execSql = "INSERT INTO FLOW_NODEDRAW(FLOW_NO, NODE_CODE, NODE_NAME, TYPE, SHAPE, NUMBER"
					+ ", LEFT1, TOP1, WIDTH, HEIGHT, PROPERTY)"
					+ "VALUES('" + projectId + "', '" + Utility.trimNull(rowValue[0]) 
					+ "', '" + Utility.trimNull(rowValue[1]) + "', '" + Utility.trimNull(rowValue[2])
					+ "', '" + Utility.trimNull(rowValue[3]) 
					+ "', " + (_number==null ? "NULL" : "'" + _number + "'") 
					+ ", '" + Utility.trimNull(rowValue[5]) + "', '" + Utility.trimNull(rowValue[6]) 
					+ "', '" + Utility.trimNull(rowValue[7]) + "', '" + Utility.trimNull(rowValue[8]) 
					+ "', " + (property==null ? "NULL" : "'" + property + "'") 
					+ ")";
			}
			execSqlArr[i] = execSql;
		}
        batchUpdateEx(execSqlArr);
		//super.batchUpdate(execSqlArr);	
	}
	
	/* (non-Javadoc)
	 * @see enfo.crm.workflow.ImportFlowWorkLocal#update_flow_nodelineobject(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void update_flow_nodelineobject(String projectId, Integer input_operatorCode) throws BusiException {
		String flow_no = Utility.trimNull(projectId);
		if ("".equals(Utility.trimNull(flow_no))) {
			throw new BusiException("流程类型不正确，请核对！");
		}
		String[] deleteSql = new String[2];
		deleteSql[0] = "update FLOW_NODE set FLOW_NODE.NODE_NAME  = B.NODE_NAME from FLOW_NODEDRAW B"
					+ " where FLOW_NODE.FLOW_NO = B.FLOW_NO and FLOW_NODE.NODE_CODE = B.NODE_CODE "
					+ " and FLOW_NODE.FLOW_NO = '" + projectId + "'";
		deleteSql[1] = "update FLOW_OBJECT set FLOW_OBJECT.NODE_NAME  = B.NODE_NAME from FLOW_NODEDRAW B"
					+ " where FLOW_OBJECT.FLOW_NO = B.FLOW_NO and FLOW_OBJECT.NODE_CODE = B.NODE_CODE "
					+ " and FLOW_OBJECT.FLOW_NO = '" + projectId + "'";
		batchUpdateEx(deleteSql);	
	}

	/* (non-Javadoc)
	 * @see enfo.crm.workflow.ImportFlowWorkLocal#list_WorkFlow(java.lang.String)
	 */
	@Override
	public String list_WorkFlow(String projectId) throws BusiException {
		List nodeList = null;
		List lineList = null;
		List countList = null;

		Object[] nodeParams = new Object[3];
		nodeParams[0] = Utility.trimNull(projectId);
		nodeParams[1] = new Integer(1);
		nodeParams[2] = null;

		Object[] lineParams = new Object[3];
		lineParams[0] = Utility.trimNull(projectId);
		lineParams[1] = new Integer(2);
		lineParams[2] = null;

		Object[] countParams = new Object[1];
		countParams[0] = Utility.trimNull(projectId);

		String nodeSql = "select B.* FROM FLOW_CATALOG A, FLOW_NODEDRAW B" + " WHERE A.FLOW_NO = B.FLOW_NO and A.FLOW_NO ='" + projectId + "'";
		String lineSql = "select B.* FROM FLOW_CATALOG A, FLOW_DRIVEDRAW B" + " WHERE A.FLOW_NO = B.FLOW_NO and A.FLOW_NO ='" + projectId + "'";
		String countSql = "SELECT MAX(NUMBER) COUNT FROM ( SELECT NUMBER FROM FLOW_NODEDRAW WHERE FLOW_NO ='" + projectId + "' "
						+ " UNION ALL SELECT  NUMBER FROM FLOW_DRIVEDRAW WHERE FLOW_NO ='" + projectId + "') A ";
		
		nodeList = super.listBySql(nodeSql);
		lineList = super.listBySql(lineSql);
		countList = super.listBySql(countSql);
		
		Integer count = new Integer(0);// 计数总共有多少条记录
		if(countList!=null && countList.size()>0){
			Map countMap = (Map) countList.get(0);
			count = (Integer) countMap.get("COUNT");
		}
		

		StringBuffer workflowStr = new StringBuffer("");
		StringBuffer workflowNodeStr = new StringBuffer("");
		StringBuffer workflowLineStr = new StringBuffer("");
		if (nodeList != null && nodeList.size() > 0) {

			workflowNodeStr.append("\"nodes\":[");
			String[] jsonStrNodeSig = new String[nodeList.size()];

			Map rowMap = null;
			String propertyStr = "";
			String propertyValue = "";
			
			for (int i = 0; i < nodeList.size(); i++) {

				rowMap = (Map) nodeList.get(i);
				propertyStr = Utility.trimNull(rowMap.get("PROPERTY"));

				if (propertyStr != "" && propertyStr.toLowerCase() != "null") {
					propertyValue = "[" + propertyStr + "]";
				} else {
					propertyValue = "null";
				}
				jsonStrNodeSig[i] = "{\"id\":\""
						+ Utility.trimNull(rowMap.get("NODE_CODE"))
						+ "\",\"name\":\""
						+ Utility.trimNull(rowMap.get("NODE_NAME"))
						+ "\",\"type\":\""
						+ Utility.trimNull(rowMap.get("TYPE"))
						+ "\",\"shape\":\""
						+ Utility.trimNull(rowMap.get("SHAPE"))
						+ "\",\"number\":"
						+ Utility.trimNull(rowMap.get("NUMBER")) + ",\"left\":"
						+ Utility.trimNull(rowMap.get("LEFT1")) + ",\"top\":"
						+ Utility.trimNull(rowMap.get("TOP1")) + ",\"width\":"
						+ Utility.trimNull(rowMap.get("WIDTH"))
						+ ",\"height\":"
						+ Utility.trimNull(rowMap.get("HEIGHT"))
						+ ",\"property\":" + propertyValue + "}";
			}
			
			for (int i = 0; jsonStrNodeSig.length != 1 && i < jsonStrNodeSig.length - 1; i++) {
				jsonStrNodeSig[i] += ',';
				workflowNodeStr.append(jsonStrNodeSig[i]);
			}
			workflowNodeStr.append(jsonStrNodeSig[jsonStrNodeSig.length - 1]
					+ "]");
		} else {
			return "";
		}

		if (lineList != null && lineList.size() > 0) {

			workflowLineStr.append("\"lines\":[");
			String[] jsonStrLineSig = new String[lineList.size()];

			Map rowMapLine = null;
			String linePropertyStr = "";
			String linePropertyValue = "";
			for (int i = 0; i < lineList.size(); i++) {

				rowMapLine = (Map) lineList.get(i);
				linePropertyStr = Utility.trimNull(rowMapLine.get("PROPERTY"));

				if (linePropertyStr != ""
						&& linePropertyStr.toLowerCase() != "null") {
					linePropertyValue = "[" + linePropertyStr + "]";
				} else {
					linePropertyValue = "null";
				}
				jsonStrLineSig[i] = "{\"id\":\""
						+ Utility.trimNull(rowMapLine.get("DRIVE_CODE"))
						+ "\",\"name\":\""
						+ Utility.trimNull(rowMapLine.get("DRIVE_NAME"))
						+ "\",\"type\":\""
						+ Utility.trimNull(rowMapLine.get("TYPE"))
						+ "\",\"shape\":\""
						+ Utility.trimNull(rowMapLine.get("SHAPE"))
						+ "\",\"number\":"
						+ Utility.trimNull(rowMapLine.get("NUMBER"))
						+ ",\"from\":\""
						+ Utility.trimNull(rowMapLine.get("FROM_NODE"))
						+ "\",\"to\":\""
						+ Utility.trimNull(rowMapLine.get("TO_NODE"))
						+ "\",\"fromx\":"
						+ Utility.trimNull(rowMapLine.get("FROMX"))
						+ ",\"fromy\":"
						+ Utility.trimNull(rowMapLine.get("FROMY"))
						+ ",\"tox\":" + Utility.trimNull(rowMapLine.get("TOX"))
						+ ",\"toy\":" + Utility.trimNull(rowMapLine.get("TOY"))
						+ ",\"polydot\":[],\"property\":" + linePropertyValue
						+ "}";
			}
			for (int i = 0; jsonStrLineSig.length != 1
					&& i < jsonStrLineSig.length - 1; i++) {
				jsonStrLineSig[i] += ',';
				workflowLineStr.append(jsonStrLineSig[i]);
			}
			workflowLineStr.append(jsonStrLineSig[jsonStrLineSig.length - 1]
					+ "]");
		} else {
			return "";
		}

		workflowStr.append("{\"id\":\"" + projectId
				+ "\",\"name\":\"\",\"count\":" + count.intValue() + ",");
		workflowStr.append(workflowNodeStr.toString() + ",");
		workflowStr.append(workflowLineStr.toString() + "}");
		return workflowStr.toString();
	}

	/* (non-Javadoc)
	 * @see enfo.crm.workflow.ImportFlowWorkLocal#list_nodes_disabled(java.lang.String)
	 */
	@Override
	public List list_nodes_disabled(String ProjectId) throws BusiException {
		// TODO Auto-generated method stub
		return new ArrayList();
	}
	
	//@Override
	/* (non-Javadoc)
	 * @see enfo.crm.workflow.ImportFlowWorkLocal#remove()
	 */
	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

    private void batchUpdateEx(String[] sqls) throws BusiException {
        //UserTransaction transaction = null;
        
        try{
            //transaction = getTransaction();
            //transaction.begin();
            
            super.batchUpdate(sqls);
            
            //transaction.commit();
            
        }catch(Exception e){
            //if (transaction!=null) {
            //    try{                
            //        transaction.rollback();
            //    }catch (Exception ex) {
            //        throw new BusiException("数据库操作异常！回滚失败！请手动清除脏数据！", e);
            //    }
            //} 
            
            throw new BusiException("数据库操作异常！", e);
        }
    }
//  查询流程处理轨迹图
    /* (non-Javadoc)
	 * @see enfo.crm.workflow.ImportFlowWorkLocal#list_WorkFlowTrack(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String list_WorkFlowTrack(String projectId, String objectId, String objectType) throws BusiException {
		List nodeList = null;
		List lineList = null;
		List countList = null;
		
		//任务与节点
		List taskNodeList = null;
		Map taskNodeMap = new HashMap();
		String taskNodeSql = "select ft.END_TIME, fn.NODE_CODE from flow_task ft, flow_node fn "; 
		taskNodeSql += "where ft.flow_no=fn.flow_no AND ft.node_no=fn.node_no ";
		taskNodeSql = taskNodeSql + " AND ft.object_no ='" + Utility.trimNull(objectId) 
			+ "' AND ft.object_type='" + Utility.trimNull(objectType) + "'";
		taskNodeList = super.listBySql(taskNodeSql);
		for(int i=0;i<taskNodeList.size();i++){
			Map map = (Map)taskNodeList.get(i);
			String nodeCode = Utility.trimNull(map.get("NODE_CODE"));
			taskNodeMap.put(nodeCode, map.get("END_TIME"));
		}
		
		//无指定阶段节点888
		String taskNode888Sql = "select ft.END_TIME, fn.NODE_CODE from flow_task ft, flow_node fn "; 
		taskNode888Sql += "where ft.flow_no=fn.flow_no AND ft.next_node=fn.node_no AND ft.next_node='888' ";
		taskNode888Sql = taskNode888Sql + " AND ft.object_no ='" + Utility.trimNull(objectId) 
		+ "' AND ft.object_type='" + Utility.trimNull(objectType) + "'";
		List taskNode888List = super.listBySql(taskNode888Sql);
		if(taskNode888List.size()>0){
			Map map888 = (Map)taskNode888List.get(0);
			String nodeCode = Utility.trimNull(map888.get("NODE_CODE"));
			taskNodeMap.put(nodeCode, map888.get("END_TIME"));
		}
		
		//任务与驱动线
		List taskDriveList = null;
		Map taskDriveMap = new HashMap();
		String taskDriveSql = "select ft.END_TIME, fd.DRIVE_CODE from flow_task ft, flow_drive fd "; 
		taskDriveSql += "where ft.flow_no=fd.flow_no AND ft.node_no=fd.node_no AND ft.next_node=fd.next_node AND ft.end_time is not null ";
		taskDriveSql = taskDriveSql + " AND ft.object_no ='" + Utility.trimNull(objectId) 
			+ "' AND ft.object_type='" + Utility.trimNull(objectType) + "'";
		taskDriveList = super.listBySql(taskDriveSql);
		for(int i=0;i<taskDriveList.size();i++){
			Map map = (Map)taskDriveList.get(i);
			String driveCode = Utility.trimNull(map.get("DRIVE_CODE"));
			taskDriveMap.put(driveCode, map.get("END_TIME"));
		}
		
		Object[] nodeParams = new Object[3];
		nodeParams[0] = Utility.trimNull(projectId);
		nodeParams[1] = new Integer(1);
		nodeParams[2] = null;

		Object[] lineParams = new Object[3];
		lineParams[0] = Utility.trimNull(projectId);
		lineParams[1] = new Integer(2);
		lineParams[2] = null;

		Object[] countParams = new Object[1];
		countParams[0] = Utility.trimNull(projectId);

		String nodeSql = "select B.* FROM FLOW_CATALOG A, FLOW_NODEDRAW B" + " WHERE A.FLOW_NO = B.FLOW_NO and A.FLOW_NO ='" + projectId + "'";
		String lineSql = "select B.* FROM FLOW_CATALOG A, FLOW_DRIVEDRAW B" + " WHERE A.FLOW_NO = B.FLOW_NO and A.FLOW_NO ='" + projectId + "'";
		String countSql = "SELECT MAX(NUMBER) COUNT FROM ( SELECT NUMBER FROM FLOW_NODEDRAW WHERE FLOW_NO ='" + projectId + "' "
						+ " UNION ALL SELECT  NUMBER FROM FLOW_DRIVEDRAW WHERE FLOW_NO ='" + projectId + "') A ";
		
		nodeList = super.listBySql(nodeSql);
		lineList = super.listBySql(lineSql);
		countList = super.listBySql(countSql);
		
		Integer count = new Integer(0);// 计数总共有多少条记录
		if(countList!=null && countList.size()>0){
			Map countMap = (Map) countList.get(0);
			count = (Integer) countMap.get("COUNT");
		}
		

		StringBuffer workflowStr = new StringBuffer("");
		StringBuffer workflowNodeStr = new StringBuffer("");
		StringBuffer workflowLineStr = new StringBuffer("");
		if (nodeList != null && nodeList.size() > 0) {

			workflowNodeStr.append("\"nodes\":[");
			String[] jsonStrNodeSig = new String[nodeList.size()];

			Map rowMap = null;
			String propertyStr = "";
			String propertyValue = "";
			
			for (int i = 0; i < nodeList.size(); i++) {

				rowMap = (Map) nodeList.get(i);
				propertyStr = Utility.trimNull(rowMap.get("PROPERTY"));

				if (propertyStr != "" && propertyStr.toLowerCase() != "null") {
					propertyValue = "[" + propertyStr + "]";
				} else {
					propertyValue = "null";
				}
				String dealFlag = "0";//处理标志
				if(taskNodeMap.containsKey(Utility.trimNull(rowMap.get("NODE_CODE")))){
					if(taskNodeMap.get(Utility.trimNull(rowMap.get("NODE_CODE"))) ==null){
						dealFlag = "toDeal";
					}else{
						dealFlag = "isDealed";
					}
				}
				jsonStrNodeSig[i] = "{\"id\":\""
						+ Utility.trimNull(rowMap.get("NODE_CODE"))
						+ "\",\"name\":\""
						+ Utility.trimNull(rowMap.get("NODE_NAME"))
						+ "\",\"dealFlag\":\""
						+ dealFlag
						+ "\",\"type\":\""
						+ Utility.trimNull(rowMap.get("TYPE"))
						+ "\",\"shape\":\""
						+ Utility.trimNull(rowMap.get("SHAPE"))
						+ "\",\"number\":"
						+ Utility.trimNull(rowMap.get("NUMBER")) + ",\"left\":"
						+ Utility.trimNull(rowMap.get("LEFT1")) + ",\"top\":"
						+ Utility.trimNull(rowMap.get("TOP1")) + ",\"width\":"
						+ Utility.trimNull(rowMap.get("WIDTH"))
						+ ",\"height\":"
						+ Utility.trimNull(rowMap.get("HEIGHT"))
						+ ",\"property\":" + propertyValue + "}";
			}
			
			for (int i = 0; jsonStrNodeSig.length != 1 && i < jsonStrNodeSig.length - 1; i++) {
				jsonStrNodeSig[i] += ',';
				workflowNodeStr.append(jsonStrNodeSig[i]);
			}
			workflowNodeStr.append(jsonStrNodeSig[jsonStrNodeSig.length - 1]
					+ "]");
		} else {
			return "";
		}

		if (lineList != null && lineList.size() > 0) {

			workflowLineStr.append("\"lines\":[");
			String[] jsonStrLineSig = new String[lineList.size()];

			Map rowMapLine = null;
			String linePropertyStr = "";
			String linePropertyValue = "";
			for (int i = 0; i < lineList.size(); i++) {

				rowMapLine = (Map) lineList.get(i);
				linePropertyStr = Utility.trimNull(rowMapLine.get("PROPERTY"));

				if (linePropertyStr != ""
						&& linePropertyStr.toLowerCase() != "null") {
					linePropertyValue = "[" + linePropertyStr + "]";
				} else {
					linePropertyValue = "null";
				}
				
				String dealFlag = "0";//处理标志
				if(taskDriveMap.containsKey(Utility.trimNull(rowMapLine.get("DRIVE_CODE")))){
					dealFlag = "isDealed";
				}
				
				jsonStrLineSig[i] = "{\"id\":\""
						+ Utility.trimNull(rowMapLine.get("DRIVE_CODE"))
						+ "\",\"name\":\""
						+ Utility.trimNull(rowMapLine.get("DRIVE_NAME"))
						+ "\",\"dealFlag\":\""
						+ dealFlag
						+ "\",\"type\":\""
						+ Utility.trimNull(rowMapLine.get("TYPE"))
						+ "\",\"shape\":\""
						+ Utility.trimNull(rowMapLine.get("SHAPE"))
						+ "\",\"number\":"
						+ Utility.trimNull(rowMapLine.get("NUMBER"))
						+ ",\"from\":\""
						+ Utility.trimNull(rowMapLine.get("FROM_NODE"))
						+ "\",\"to\":\""
						+ Utility.trimNull(rowMapLine.get("TO_NODE"))
						+ "\",\"fromx\":"
						+ Utility.trimNull(rowMapLine.get("FROMX"))
						+ ",\"fromy\":"
						+ Utility.trimNull(rowMapLine.get("FROMY"))
						+ ",\"tox\":" + Utility.trimNull(rowMapLine.get("TOX"))
						+ ",\"toy\":" + Utility.trimNull(rowMapLine.get("TOY"))
						+ ",\"polydot\":[],\"property\":" + linePropertyValue
						+ "}";
			}
			for (int i = 0; jsonStrLineSig.length != 1
					&& i < jsonStrLineSig.length - 1; i++) {
				jsonStrLineSig[i] += ',';
				workflowLineStr.append(jsonStrLineSig[i]);
			}
			workflowLineStr.append(jsonStrLineSig[jsonStrLineSig.length - 1]
					+ "]");
		} else {
			return "";
		}

		workflowStr.append("{\"id\":\"" + projectId
				+ "\",\"name\":\"\",\"count\":" + count.intValue() + ",");
		workflowStr.append(workflowNodeStr.toString() + ",");
		workflowStr.append(workflowLineStr.toString() + "}");
		return workflowStr.toString();
	}
	
}
