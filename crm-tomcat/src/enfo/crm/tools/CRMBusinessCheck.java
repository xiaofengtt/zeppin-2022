/*
 * 创建日期 2013-5-3
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.tools;

import enfo.crm.dao.CrmDBManager;

/**
 * @author owen
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class CRMBusinessCheck {
	
	public static void updateFlow(String identity_code) throws Exception{
		String genSql="";
		String objectType= ConfigUtil.getSqlResult("select object_Type from flow_temp where task_id='"+identity_code+"'");
		String rtvalue=ConfigUtil.getSqlResult("select FLOW_DESC+'@@'+ FLOW_NO+'@@'+FLOW_NAME+'@@'+NODE_NO+'@@'+NODE_NAME+'@@'+NODE_OPINION+'@@'+NODE_OPINION_NAME+'@@'+" +
				"DRIVE_TYPE+'@@'+NEXT_NODE+'@@'+USER_OPINION from flow_temp where object_type ='"+objectType+"'");
		String [] s=rtvalue.split("@@");
		genSql = "update flow_task set FLOW_DESC='"+s[0]+"', FLOW_NO='"+s[1]+"', FLOW_NAME='"+s[2]+"', NODE_NO='"+s[3]+"', NODE_NAME='"+s[4]+"',NODE_OPINION='"+s[5]+"',NODE_OPINION_NAME='"+s[6]+"'," +
				"DRIVE_TYPE='"+s[7]+"',NEXT_NODE='"+s[8]+"',USER_OPINION='"+s[9]+"', BEGIN_TIME=getdate(),Input_date=convert(nvarchar(10),getdate(),120) " +
				"where object_Type= '"+objectType+"'";
		CrmDBManager.executeSql(genSql);
		
		
	}
	/**
	 * 插入记录，更新流程信息
	 * @throws Exception
	 */
	public static void updateFlowInfo() throws Exception{
		String genSql="";
		genSql = "update flow_temp set object_no =(select top 1 TASK_ID from FLOW_TEMP order by TASK_ID DESC ),object_type=(select top 1 'WE'+CONVERT(NVARCHAR(20),TASK_ID) from FLOW_TEMP order by TASK_ID DESC ) " +
				"where task_id= (select max(task_id) from FLOW_TEMP)";
		CrmDBManager.executeSql(genSql);
		genSql = "insert into flow_task (RELATIVE_NO,OBJECT_NO, OBJECT_TYPE,FLOW_DESC, FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,  USER_ID, USER_NAME, DEPT_ID, DEPT_NAME, BEGIN_TIME,Input_date)" +
				"select top 1 RELATIVE_NO,OBJECT_NO, OBJECT_TYPE,FLOW_DESC, FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,  USER_ID, USER_NAME, DEPT_ID, DEPT_NAME,getdate(),convert(nvarchar(10),getdate(),120) from flow_temp order by task_id desc";
		CrmDBManager.executeSql(genSql);
		genSql =  "insert into flow_state (OBJECT_NO, OBJECT_TYPE, FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG, USER_ID,USER_NAME,DEPT_ID, DEPT_NAME, INPUT_TIME,INPUT_DATE)" +
				"select top 1 OBJECT_NO, OBJECT_TYPE, FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG,  USER_ID, USER_NAME, DEPT_ID, DEPT_NAME,getdate(),convert(nvarchar(10),getdate(),120) from flow_temp order by task_id desc";
		CrmDBManager.executeSql(genSql);
	}
	/**
	 *  初始化流程状态
	 * @throws Exception
	 */
	public static void initFlowState() throws Exception{
		String genSql="";
		
		genSql ="insert into FLOW_STATE(OBJECT_NO, OBJECT_TYPE, FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG, USER_ID,USER_NAME,DEPT_ID, DEPT_NAME, INPUT_TIME,INPUT_DATE)"+
		   " select top 1 OBJECT_NO, OBJECT_TYPE, FLOW_NO, FLOW_NAME, NODE_NO, NODE_NAME, NODE_FLAG, USER_ID,USER_NAME,DEPT_ID, DEPT_NAME, getdate(),convert(nvarchar(10),getdate(),120)"+
		   " from flow_task order by task_id desc";

		CrmDBManager.executeSql(genSql);
	}
	/**
	 * 获得电话号码
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static String getPhoneNum(String object ) throws Exception{
		String genSql="";
		
		genSql = "select mobile from toperator where op_code= '"+object+"'";
		String returnValue =Utility.trimNull(ConfigUtil.getSqlResult(genSql));
		return returnValue;
	}
	/**
	 * 通过对象编号，对象类型，检查分类判断录入完整
	 * @param paramName
	 * @return
	 * @throws Exception
	 */
	public static String checkBusinessInfo(String object_id, String object_type,String check_flag)
	throws Exception {
		//获取校验配置信息
		String returnValue=BusinessCheck.checkApplyInfo(object_id,object_type);
		return returnValue;
	}
	/**
	 * 取新增信息主键值
	 * @param paramName
	 * @return
	 * @throws Exception
	 */
	public static String getObjectKeyID(String paramName)
	throws Exception {
		String[] s=paramName.split("@@");
		String whereCondition="INPUT_MAN='"+s[2]+"'";
		String ObjectID =Utility.trimNull(ConfigUtil.getDataBaseValue("max("+s[1]+")",s[0],whereCondition));
		return ObjectID;
	}
	/**
	 * 权限判断
	 * @param object_id
	 * @return
	 * @throws Exception
	 */
	public static String getLogType(String object_id) throws Exception{
		String genSql=" select LOG_TYPE from TSTAFFWORKLOG where serail_id='"+object_id+"'";
		String returnValue =Utility.trimNull(ConfigUtil.getSqlResult(genSql));
		return returnValue;
	}
	/**
	 * 计算员工报销金额总和
	 * @param object_id
	 * @return
	 * @throws Exception
	 */
	public static String getStaffreimbSum(String param ) throws Exception{
		String genSql="";
		String s[]=param.split("@@");
		genSql = "select convert(nvarchar(20),isnull(sum(MONEY),'0')) from TSTAFFREIMBURSE WHERE  APPLY_ID='"+s[1]+"'";
		String returnValue =Utility.trimNull(ConfigUtil.getSqlResult(genSql));
		return returnValue;
	}
	/**
	 * 生成合同领取的预约汇总信息
	 * @param object_id
	 * @return
	 * @throws Exception
	 */
	public static String getPreContractFlow(String param ) throws Exception{
		String genSql="";
		String s[]=param.split("@@");
		String updteSql = "update TPRECONTRACT SET CONTRACT_NUM ="+s[1]+" where serial_no='"+s[0]+"'";
		CrmDBManager.executeSql(updteSql);
		String addSql ="INSERT INTO TPRECONTRACTFLOW (PRECONTRACT_ID, PREPRODUCT_ID, CUST_ID, PRE_CODE, PRE_MONEY, RG_MONEY, RG_DATE, LINK_MAN, PRE_DATE, "+
			" VALID_DAYS, END_DATE, PRE_TYPE, PRE_TYPE_NAME, PRE_STATUS, PRE_STATUS_NAME, INPUT_MAN, INPUT_TIME, SUMMARY, PRE_NUM, "+
			" RG_NUM, EXP_REG_DATE, CUST_SOURCE, CUST_SOURCE_NAME, CHANNEL_TYPE, CHANNEL_FARE, BIND_SERIAL_NO, PRE_LEVEL, "+
			" PRE_LEVEL_NAME, CHECK_FLAG, CHECK_MAN, SUB_PRODUCT_ID, CONTRACT_STATE, CONTRACT_NO, PRODUCT_ID) "+
			" SELECT SERIAL_NO, PREPRODUCT_ID, CUST_ID, PRE_CODE, PRE_MONEY, RG_MONEY, RG_DATE, LINK_MAN, PRE_DATE, "+
			" VALID_DAYS, END_DATE, PRE_TYPE, PRE_TYPE_NAME, PRE_STATUS, PRE_STATUS_NAME, INPUT_MAN, INPUT_TIME, SUMMARY, PRE_NUM, "+
			" RG_NUM, EXP_REG_DATE, CUST_SOURCE, CUST_SOURCE_NAME, CHANNEL_TYPE, CHANNEL_FARE, BIND_SERIAL_NO, PRE_LEVEL,"+
			" PRE_LEVEL_NAME, CHECK_FLAG, CHECK_MAN, SUB_PRODUCT_ID, CONTRACT_STATE, '', PRODUCT_ID"+
			"  FROM TPRECONTRACT WHERE SERIAL_NO='"+s[0]+"'";
		CrmDBManager.executeSql(addSql);
		genSql = "select max(SERIAL_NO) from TPRECONTRACTFLOW";
		String returnValue =Utility.trimNull(ConfigUtil.getSqlResult(genSql));
		return returnValue;
	
	}
	/**
	 * 自动获取对象关联对应信息。
	 * @param paramName
	 * @return
	 * @throws Exception
	 */
	public static String getRelationsInfo(String paramName)
	throws Exception {
		String[] s=paramName.split("@@");
		String showType=s[0];
		String objectID=s[1];
		String sql="",returnValue="";
		if("StaffReimbSum".equals(showType)){
			sql="select cast(sum(money) as nvarchar) "+
				" from TSTAFFREIMBURSE"+
				" where isNull( APPLY_ID,'')=''";
			returnValue =Utility.trimNull(ConfigUtil.getSqlResult(sql));	
		}else if("ProductSum".equals(showType)){
			sql="select Product_Sum"+
		    " from PRODUCT_INFO"+
		    " where PRODUCT_ID = '"+objectID+"'";
			returnValue =Utility.trimNull(ConfigUtil.getSqlResult(sql));	
	}
		return returnValue;
	}
	/**
	 * 自动获取对象关联对应信息。
	 * @param paramName
	 * @return
	 * @throws Exception
	 */
	public static String getRelationInfo(String paramName)
	throws Exception {
		String[] s=paramName.split("@@");
		String showType=s[0];
		String sqlContent=s[1];
		String sql="",returnValue="";
		if("StaffReimbSum".equals(showType)){
			sql="select cast(sum(money) as nvarchar) "+
				" from TSTAFFREIMBURSE"+
				" where 1=1  "+sqlContent;
			returnValue =Utility.trimNull(ConfigUtil.getSqlResult(sql));	
		}else if("ProductSum".equals(showType)){
			sql="select Product_Sum"+
		    " from PRODUCT_INFO"+
		    " where PRODUCT_ID = '"+sqlContent+"'";
			returnValue =Utility.trimNull(ConfigUtil.getSqlResult(sql));	
	}
		return returnValue;
	}	
	/**
	 * 更新报销总额。
	 * @param paramName
	 * @return
	 * @throws Exception
	 */
	public static void getReimbSum(String objectId)
	throws Exception {
		String updteSql = "update TSTAFFREIMBURSESUM SET MONEY_SUM=(SELECT SUM(MONEY) FROM  TSTAFFREIMBURSE WHERE APPLY_ID='"+objectId+"')"+
				" WHERE SERAIL_ID='"+objectId+"'";
		CrmDBManager.executeSql(updteSql);
	}
	/**
	 * 更改要汇总的标志位
	 * @param objectId
	 * @throws Exception
	 */
	public static void changeAll(String objectId) throws Exception{
		String applyId=Utility.trimNull(ConfigUtil.getSqlResult("select top 1 serail_id from TSTAFFREIMBURSESUM order by  serail_id desc"));
		String updteSql = "update TSTAFFREIMBURSE SET apply_id='"+applyId+"' WHERE STAFFREIMBURSE_ID='"+objectId+"'";
		CrmDBManager.executeSql(updteSql);
	}
	
	public static void referAll(String inputMan)throws Exception{
				String insertSql = "insert into TSTAFFREIMBURSESUM (input_man,input_time,input_dept)"
				+" values ('"+inputMan+"',getdate()," +
						"(select depart_id from TOPERATOR where op_code='"+inputMan+"'))";
		CrmDBManager.executeSql(insertSql);
		
	}
	public static void updateReferAll(String objectId)throws Exception{
		
		String money_sum = getStaffreimbSum("@@"+objectId);
		String start_date= Utility.trimNull(ConfigUtil.getSqlResult("select min(DATE) from TSTAFFREIMBURSE where apply_id='"+objectId+"'"));
		String end_date= Utility.trimNull(ConfigUtil.getSqlResult("select max(DATE) from TSTAFFREIMBURSE where apply_id='"+objectId+"'"));
		String updateSql = "update TSTAFFREIMBURSESUM set start_date= '"+start_date+"' ,end_date= '"+end_date+"' ,money_sum= '"+money_sum+"'"
						+", state = '1010' where serail_id='"+objectId+"'";
		CrmDBManager.executeSql(updateSql);
	}
	public static void referCheck(String objectId,String applyId)throws Exception{
		
		String updteSql = "update TSTAFFREIMBURSE SET apply_id='"+applyId+"' WHERE STAFFREIMBURSE_ID='"+objectId+"'";
		CrmDBManager.executeSql(updteSql);

	}
	public static void cutDel(String objectId)throws Exception{
		
		String updteSql = "update TSTAFFREIMBURSE SET apply_id='' WHERE STAFFREIMBURSE_ID='"+objectId+"'";
		CrmDBManager.executeSql(updteSql);

	}
	public static void delBack(String objectId)throws Exception{
		
		String updteSql = "update TSTAFFREIMBURSE SET apply_id='' WHERE apply_id='"+objectId+"'";
		CrmDBManager.executeSql(updteSql);

	}
	public static String getFlowValues(String object)throws Exception{
		String sql="select flow_No+'@@'+flow_Name+'@@'+node_Name+'@@'+node_No+'@@'+node_Flag from flow_node " +
				"where 1=1  and use_state='1' and node_flag='start' and flow_name='"+object+"'";
		String returnValues=Utility.trimNull(ConfigUtil.getSqlResult(sql));
		return returnValues;

	}
	//
	public static String getWorkSchedule(String objectId)throws Exception{
		String sql = "select dbo.GETWORKSCHEDULE("+objectId+")";
		String returnValues = Utility.trimNull(ConfigUtil.getSqlResult(sql));
		String[] s = returnValues.split("@@");
		String rt = "";
		for(int i = 0;i<s.length-1;i++){
			rt = rt+s[i]+"\\r\\n";
		}
		rt = rt+s[s.length-1];
		return rt;

	}
	//
	public static void saveContractFlow(Integer objectId,String contractNo)throws Exception{
		
		String updteSql = "update tprecontractflow SET contract_no='"+contractNo+"' WHERE serial_no='"+objectId+"'";
		System.out.println(updteSql);
		CrmDBManager.executeSql(updteSql);

	}
}
