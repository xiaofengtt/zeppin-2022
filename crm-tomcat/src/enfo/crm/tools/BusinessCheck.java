package enfo.crm.tools;

import java.util.List;
import java.util.Map;

import enfo.crm.dao.BusiException;
import enfo.crm.dao.CrmDBManager;
import enfo.crm.project.BusinessLogicLocal;


public class BusinessCheck {
	/**
	 * 通过申请编号判断申请信息是否录入完整
	 * @param paramName
	 * @return
	 * @throws Exception
	 */
	public static String checkApplyInfo(String object_id, String object_type)
	throws Exception {
		BusinessLogicLocal businessLogicLocal = EJBFactory.getBusinessLogic();
		String returnValue ="",replaceParam="",getInitValue="";
		//获取校验配置信息
		String checkType="",checkLogic="",controlCondition="",checkCondition="",checkResult="",selectSql="",fromSql="",whereSql="",resultSql="";
		List checkList = businessLogicLocal.listWorkCount("select * from CHECK_INTEGRITY where Object_Type='"+object_type+"' and use_state='1' order by Order_no");
		if(checkList.isEmpty() || checkList.size()==0){
			return "";
		}
		String[] checkCode=new String[checkList.size()-1];
		String[] promptName=new String[checkList.size()-1];
		String[] controlValue=new String[checkList.size()-1];
		String[] rinkType=new String[checkList.size()-1];
		for(int i=0;i<checkList.size();i++){ 
		    Map checkMap = (Map)checkList.get(i);
		    checkType=Utility.trimNull(checkMap.get("CHECK_TYPE"));
		    if("get".equals(checkType)){
		    	replaceParam=Utility.trimNull(checkMap.get("PROMPT_NAME"));
		    	selectSql=Utility.trimNull(checkMap.get("CHECK_LOGIC"));
		    	fromSql=Utility.trimNull(checkMap.get("CONTROL_VALUE"));
		    	whereSql=Utility.trimNull(checkMap.get("CONTROL_CONDITION"));
		    	whereSql=whereSql.replaceAll("#OBJECT_ID#","'"+object_id+"'");
		    }else if("check".equals(checkType)){
		    	checkLogic=Utility.trimNull(checkMap.get("CHECK_LOGIC"));
		    	controlCondition=Utility.trimNull(checkMap.get("CONTROL_CONDITION"));
			    checkCode[i-1]=Utility.trimNull(checkMap.get("CHECK_CODE"));
			    promptName[i-1]=Utility.trimNull(checkMap.get("PROMPT_NAME"));
			    controlValue[i-1]=Utility.trimNull(checkMap.get("CONTROL_VALUE"));
			    rinkType[i-1]=Utility.trimNull(checkMap.get("RINK_TYPE"));	
			    //组合成校验sql语句
			    if(i==1){
			    	resultSql="select (select top 1 1 from CHECK_INTEGRITY where "+controlCondition+") as C"+checkCode[i-1]+",("+checkLogic+") as L"+checkCode[i-1];
			    }else{
			    	resultSql=resultSql+","+"(select top 1 1 from CHECK_INTEGRITY where "+controlCondition+") as C"+checkCode[i-1]+",("+checkLogic+") as L"+checkCode[i-1];
			    }			    
		    }
		}
		System.out.println("resultSql替换前---------------:"+resultSql);
		//初始化校验准备信息
    	getInitValue =ConfigUtil.getDataBaseValue(selectSql,fromSql,whereSql);
		String[] initValueArray=getInitValue.split("@@");
		String[] paramName=replaceParam.split(",");
		for(int i=0;i<initValueArray.length;i++){
			String[] paramReplaceName=paramName[i].split(" ");
			//替换相关参数
			if("String".equals(paramReplaceName[0])){
				resultSql=resultSql.replaceAll("#"+paramReplaceName[1]+"#", "'"+initValueArray[i]+"'");		
			}else if("Number".equals(paramReplaceName[0])){
				resultSql=resultSql.replaceAll("#"+paramReplaceName[1]+"#", ""+initValueArray[i]+"");	
			}
		}
		System.out.println("resultSql替换后---------------:"+resultSql);
		//获得校验结果
		List checkResultList = businessLogicLocal.listWorkCount(resultSql);
		for(int i=0;i<checkResultList.size();i++){ 
		    Map checkMap = (Map)checkResultList.get(i);
		    for(int j=0;j<checkCode.length;j++)
		    {
		    	checkCondition=Utility.trimNull(checkMap.get("C"+checkCode[j]));
		    	checkResult=Utility.trimNull(checkMap.get("L"+checkCode[j]));
		    	//是否需要检验判断
		    	if(!"".equals(checkCondition) && "1".equals(checkCondition)){
		    	//是否满足检验逻辑	
		    		if(!"".equals(checkResult) && "1".equals(rinkType[j]) && Double.parseDouble(checkResult)==Double.parseDouble(controlValue[j])){
		    			if("".equals(returnValue)){
		    				returnValue=promptName[j]+"@@";
		    			}else{
		    				returnValue=returnValue+promptName[j]+"@@";
		    			}
		    		}else if(!"".equals(checkResult) && "3".equals(rinkType[j]) && Double.parseDouble(checkResult)>=Double.parseDouble(controlValue[j])){
		    			if("".equals(returnValue)){
		    				returnValue=promptName[j]+"@@";
		    			}else{
		    				returnValue=returnValue+promptName[j]+"@@";
		    			}		    			
		    		}else if(!"".equals(checkResult) && "4".equals(rinkType[j]) && Double.parseDouble(checkResult)<=Double.parseDouble(controlValue[j])){
		    			if("".equals(returnValue)){
		    				returnValue=promptName[j]+"@@";
		    			}else{
		    				returnValue=returnValue+promptName[j]+"@@";
		    			}		    			
		    		}else if(!"".equals(checkResult) && "5".equals(rinkType[j]) && Double.parseDouble(checkResult)>Double.parseDouble(controlValue[j])){
		    			if("".equals(returnValue)){
		    				returnValue=promptName[j]+"@@";
		    			}else{
		    				returnValue=returnValue+promptName[j]+"@@";
		    			}		    			
		    		}else if(!"".equals(checkResult) && "6".equals(rinkType[j]) && Double.parseDouble(checkResult)<Double.parseDouble(controlValue[j])){
		    			if("".equals(returnValue)){
		    				returnValue=promptName[j]+"@@";
		    			}else{
		    				returnValue=returnValue+promptName[j]+"@@";
		    			}		    			
		    		}
		    	}
		    }
		    
		}
		return returnValue;
	}	
	
	/**
	 * 通过流水号获得显示界面信息
	 * @param object_id
	 * @param object_type
	 * @return
	 * @throws Exception
	 */
	public static String getShowInterface(String object_id, String object_type)
	throws Exception {
		String interface_Type="",sql="";
		if(object_type.equals("BusinessApply")){ 		
			sql="select PT.Apply_Temp from ITEM_INFO II,PRODUCT_TYPE PT where II.Business_Type=PT.Type_Code and II.APPLY_ID='"+object_id+"'";
		}else if(object_type.equals("BusinessContract")){
			sql="select PT.Contract_Temp from ITEM_CONTRACT IC,PRODUCT_TYPE PT where IC.Business_Type=PT.Type_Code and IC.SERIAL_ID='"+object_id+"'";
		}else if(object_type.equals("ContractPutout")){
			sql="select PT.Putout_Temp from CONTRACT_PUTOUT CP,PRODUCT_TYPE PT where CP.Business_Type=PT.Type_Code and CP.PUTOUT_ID='"+object_id+"'";		
		}else if(object_type.equals("AfterLoan")){
			sql="select PT.AfterLoan_temp from ITEM_CONTRACT IC,PRODUCT_TYPE PT where IC.Business_Type=PT.Type_Code and IC.SERIAL_ID='"+object_id+"'";
		}
		interface_Type =Utility.trimNull(ConfigUtil.getSqlResult(sql));
		return interface_Type;
	}
	
	/**
	 * 通过流水号获得对象表信息
	 * @param object_id
	 * @param object_type
	 * @return
	 * @throws Exception
	 */
	public static String getTableValue(String object_id, String object_type)
	throws Exception {
		String returnValue="";
		if(object_type.equals("BusinessApply"))
			returnValue =Utility.trimNull(ConfigUtil.getDataBaseValue("credit_type,apply_type,business_type","ITEM_INFO","Apply_ID='"+object_id+"'"));
		else if(object_type.equals("BusinessContract"))
			returnValue =Utility.trimNull(ConfigUtil.getDataBaseValue("credit_type,apply_type,business_type","ITEM_CONTRACT","SERIAL_ID='"+object_id+"'"));
		return returnValue;
	}

	
	/**
	 * 通过对象编号，合同对象编号，获得对应的编号
	 * @param paramName
	 * @return
	 * @throws Exception
	 */
	public static String getObjectID(String paramName)
	throws Exception {
		String[] s=paramName.split("@@");
		String returnValue="";
		if(s[2].equals("Customer")){//获取客户编号
			if(s[1].equals("BusinessApply")){
				returnValue =Utility.trimNull(ConfigUtil.getDataBaseValue("customer_id","ITEM_INFO","Apply_ID='"+s[0]+"'"));
			}else if(s[1].equals("BusinessContract")){
				returnValue =Utility.trimNull(ConfigUtil.getDataBaseValue("customer_id","ITEM_CONTRACT","Serial_ID='"+s[0]+"'"));
			}else if(s[1].equals("ContractPutout")){
				returnValue =Utility.trimNull(ConfigUtil.getDataBaseValue("customer_id","CONTRACT_PUTOUT","Putout_ID='"+s[0]+"'"));
			}else if(s[1].equals("AfterLoan")){
				returnValue =Utility.trimNull(ConfigUtil.getDataBaseValue("customer_id","ITEM_CONTRACT","Serial_ID='"+s[0]+"'"));
			}
		}else if(s[2].equals("BusinessApply")){//获取申请编号
			if(s[1].equals("BusinessContract")){
				returnValue =Utility.trimNull(ConfigUtil.getDataBaseValue("Apply_ID","ITEM_CONTRACT","Serial_ID='"+s[0]+"'"));
			}else if(s[1].equals("ContractPutout")){
				returnValue =Utility.trimNull(ConfigUtil.getDataBaseValue("Apply_ID","ITEM_CONTRACT","Serial_ID=(select Serial_ID from CONTRACT_PUTOUT where Putout_ID='"+s[0]+"')"));
			}else if(s[1].equals("AfterLoan")){
				returnValue =Utility.trimNull(ConfigUtil.getDataBaseValue("Apply_ID","ITEM_CONTRACT","Serial_ID='"+s[0]+"'"));
			}			
		}else if(s[2].equals("BusinessContract")){//获取合同编号
			 if(s[1].equals("ContractPutout")){
				returnValue =Utility.trimNull(ConfigUtil.getDataBaseValue("Serial_ID","CONTRACT_PUTOUT","Putout_ID='"+s[0]+"'"));
			 }else if(s[1].equals("AfterLoan")){
				returnValue =Utility.trimNull(ConfigUtil.getDataBaseValue("Serial_ID","ITEM_CONTRACT","Serial_ID='"+s[0]+"'"));
			 }			
		}else if(s[2].equals("ContractPutout")){//获取出账编号
			if(s[1].equals("AfterLoan")){
				returnValue =Utility.trimNull(ConfigUtil.getDataBaseValue("Serial_ID","CONTRACT_PUTOUT","Putout_ID='"+s[0]+"'"));
			}	
		}
		
		return returnValue;
	}	

	/**
	 * 通过对象编号，合同对象编号，获得对应的编号
	 * @param paramName 25@@WorkLog@@WorkLog@@WorkLogFlow@@100@@010,300
	 * @return
	 * @throws Exception
	 */
	public static String getFlowID(String paramName)
	throws Exception {
		String[] s=paramName.split("@@");
		System.out.println(s[2]+"----"+s[0]);
		String returnValue="";
		if(s[2].equals("ViewOpinion")||s[2].equals("FlowTrack")){//获取审批编号
			returnValue =s[0];
		}else if(s[2].equals("WorkLog")){
			returnValue=s[0];
		}else if(s[2].equals("tent")){
			returnValue=s[0];
		}else if(s[2].equals("SaleProduct")){
			returnValue=s[0];
		}else if(s[2].equals("ContractStamp")){
			System.out.println(s[2]+"--1--"+s[0]);
			returnValue=s[0];
		}else if(s[2].equals("Contract")){
			returnValue=s[0];
			//returnValue =Utility.trimNull(ConfigUtil.getDataBaseValue("PRECONTRACT_ID","TPRECONTRACTFLOW","SERIAL_NO='"+s[0]+"'"));
		}


		//对象权限判断
		if(s[5].indexOf(s[4])>=0 && s[1].equals(s[2])){
			if(s[2].equals("BusinessContract")){
				returnValue=returnValue+"@@"+"spec";
			}else{
				returnValue=returnValue+"@@"+"yes";
			}
		}else{
			returnValue=returnValue+"@@"+"no";
		}		
		return returnValue;
	}	
	
	/**
	 * 通过流程编号获得初始化流程信息
	 * @param paramName
	 * @return
	 * @throws Exception
	 */
	public static String getInitFlow(String paramName)
	throws Exception {
		String[] s=paramName.split("@@");
		String flowNo=s[0];	
		String whereCondition=" FC.Flow_no=FN.Flow_no and FC.Init_Node=FN.Node_no and FC.Flow_no='"+flowNo+"'";
		String returnValue =Utility.trimNull(ConfigUtil.getDataBaseValue("FC.Init_Node,FC.Init_Node_Name,FN.NODE_FLAG","FLOW_CATALOG FC,FLOW_NODE FN",whereCondition));		
		return returnValue;
	}
	
	/**
	 * 流程中数据状态变化
	 * @param object_id
	 * @param object_type
	 * @param action_flag
	 * @throws BusiException
	 */
	public static void actionFlow(String object_id,String object_type,String action_flag) throws BusiException{
		String updateSql="";
		if(action_flag.equals("ActionFlow1010")){//申请生效
			updateSql="update TSTAFFREIMBURSESUM set STATE='1010' where SERAIL_ID='"+object_id+"'";
		}else if(action_flag.equals("ActionFlow1020")){
			updateSql="update TSTAFFREIMBURSESUM set STATE='1020' where SERAIL_ID='"+object_id+"'";
		}else if(action_flag.equals("ActionFlow1030")){
			updateSql="update TSTAFFREIMBURSESUM set STATE='1030' where SERAIL_ID='"+object_id+"'";
		}else if(action_flag.equals("WorkLog1010")){//日志流程
			updateSql="update TSTAFFWORKLOG set LOG_STATE='1010' where SERAIL_ID='"+object_id+"'";
		}else if(action_flag.equals("WorkLog1020")){
			updateSql="update TSTAFFWORKLOG set LOG_STATE='1020' where SERAIL_ID='"+object_id+"'";
		}else if(action_flag.equals("WorkLog1030")){
			updateSql="update TSTAFFWORKLOG set LOG_STATE='1030' where SERAIL_ID='"+object_id+"'";
			addTschedules(object_id,object_type);
		}else if(action_flag.equals("FlowTemp1010")){//新增流程
			updateSql="update FLOW_TEMP set FLOW_STATE='1010' where TASK_ID='"+object_id+"'";
		}else if(action_flag.equals("FlowTemp1020")){
			updateSql="update FLOW_TEMP set FLOW_STATE='1020' where TASK_ID='"+object_id+"'";
		}else if(action_flag.equals("FlowTemp1030")){
			updateSql="update FLOW_TEMP set FLOW_STATE='1030' where TASK_ID='"+object_id+"'";
		}else if(action_flag.equals("SaleFlow1010")){//产品销售确认
			updateSql="update product_sale set ISSUE_STATE='1010' where ISSUE_ID='"+object_id+"'";
		}else if(action_flag.equals("SaleFlow1020")){
			updateSql="update product_sale set ISSUE_STATE='1020' where ISSUE_ID='"+object_id+"'";
		}else if(action_flag.equals("SaleFlow1030")){
			updateSql="update product_sale set ISSUE_STATE='1030' where ISSUE_ID='"+object_id+"'";
		}else if(action_flag.equals("ContractStamp1010")){//合同用印流程
			updateSql="update intrust..tcontract set STAMP_STATE= NULL where SERIAL_NO='"+object_id+"'";
		}else if(action_flag.equals("ContractStamp1020")){
			updateSql="update intrust..tcontract set STAMP_STATE='1020' where SERIAL_NO='"+object_id+"'";
		}else if(action_flag.equals("ContractStamp1030")){
			updateSql="update intrust..tcontract set STAMP_STATE='1030' where SERIAL_NO='"+object_id+"'";
		}else if(action_flag.equals("ContractFlow1010")){//合同领取流程
			updateSql="update TPRECONTRACTFLOW set CONTRACT_STATE='1010' where SERIAL_NO='"+object_id+"'";
		}else if(action_flag.equals("ContractFlow1020")){
			updateSql="update TPRECONTRACTFLOW set CONTRACT_STATE='1020' where SERIAL_NO='"+object_id+"'";
		}else if(action_flag.equals("ContractFlow1030")){
			updateSql="update TPRECONTRACTFLOW set CONTRACT_STATE='1030' where SERIAL_NO='"+object_id+"'";
			updateTprecontract(object_id,object_type);
		}
		CrmDBManager.executeSql(updateSql);
	}

	/**
	 * @param object_id
	 * @param object_type
	 * @throws BusiException
	 * @throws Exception
	 */
	private static void addTschedules(String object_id, String object_type) throws BusiException {
		String opCode="",opName="";
		String querySql =" SELECT TP.OP_CODE FROM TOPERATOR TP,TOPROLE TL  WHERE TP.OP_CODE=TL.OP_CODE AND TP.DEPART_ID=(SELECT DEPART_ID FROM TOPERATOR WHERE OP_CODE=(select input_man from TSTAFFWORKLOG where SERAIL_ID='"+object_id+"')) AND TL.ROLE_ID=102";
		try {
			opCode = Utility.trimNull(ConfigUtil.getSqlResult(querySql));
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		String querySql2 =" SELECT TP.OP_NAME FROM TOPERATOR TP,TOPROLE TL  WHERE TP.OP_CODE=TL.OP_CODE AND TP.DEPART_ID=(SELECT DEPART_ID FROM TOPERATOR WHERE OP_CODE=(select input_man from TSTAFFWORKLOG where SERAIL_ID='"+object_id+"')) AND TL.ROLE_ID=102";
		try {
			opName = Utility.trimNull(ConfigUtil.getSqlResult(querySql2));
		} catch (Exception e1) {
			// TODO 自动生成 catch 块
			e1.printStackTrace();
		}
		String addSql =" INSERT INTO TSCHEDULES(SCHEDULE_TYPE,SCHEDULE_NAME,START_DATE,END_DATE,OP_CODE,OP_NAME,CONTENT,CHECK_FLAG,DF_SERIAL_NO)"+
			" SELECT '304290','流程通知',GETDATE(),GETDATE()+1,"+opCode+",'"+opName+"',DBO.GETUSERNAME(INPUT_MAN)+'已提交【'+DATE+''+LOG_TYPE_NAME+'】',1,SERAIL_ID"+
			" FROM TSTAFFWORKLOG WHERE SERAIL_ID='"+object_id+"'";
		System.out.println(addSql);
		CrmDBManager.executeSql(addSql);
	}
	private static void updateTprecontract(String object_id,String object_type){
		try{
			String sql = "select precontract_id+'&&'+contract_no from tprecontractflow where serial_no= '"+object_id+"'";
			String pre_id = Utility.trimNull(ConfigUtil.getSqlResult(sql));
			String s[] = pre_id.split("&&");
			
			String contract_no_= Utility.trimNull(ConfigUtil.getSqlResult("select contract_no from tprecontract where serial_no = '"+s[0]+"'"));
			contract_no_ = contract_no_+s[1];
			CrmDBManager.executeSql("update tprecontract set contract_no = '"+contract_no_+"' where serial_no = '"+s[0]+"'");
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}


