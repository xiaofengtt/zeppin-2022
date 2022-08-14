<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获取主键ID 
String[] temp_checks = request.getParameterValues("check_serial_no");
String q_check_options = Utility.trimNull(request.getParameter("q_check_options"));
Integer t_serial_no;
//----活动任务信息
Integer activeSerialNo = new Integer(0);
String taskTitle = "";
String beginDate="";
String endDate = "";
Integer executor = new Integer(0);
String content = "";
String completeTime  = "";
String remark = "";
String check_options = "";
//声明辅助变量
List taskList = null;
Map taskMap = null;

//获得对象
ActivityTaskLocal activityTaskLocal = EJBFactory.getActivityTask();
ActivityTaskVO vo = null;

//逐个审核
if(temp_checks!=null){
	for(int i=0;i<temp_checks.length;i++){
		t_serial_no = Utility.parseInt(temp_checks[i], new Integer(0));
			//查找活动任务信息
			if(t_serial_no.intValue()!=0){				
				vo = new  ActivityTaskVO();
				vo.setSerial_no(t_serial_no);	
				taskList = activityTaskLocal.query(vo);
				
				if(taskList.size()>0){
					taskMap = (Map)taskList.get(0);
				}
				
				if(taskMap!=null){
						activeSerialNo = Utility.parseInt(Utility.trimNull(taskMap.get("Active_Serial_no")),new Integer(0));
						beginDate = Utility.trimNull(taskMap.get("BeginDate"));
						endDate = Utility.trimNull(taskMap.get("EndDate"));
						executor = Utility.parseInt(Utility.trimNull(taskMap.get("Executor")),new Integer(0));
						content = Utility.trimNull(taskMap.get("Content"));
						remark =  Utility.trimNull(taskMap.get("Remark"));
						completeTime =  Utility.trimNull(taskMap.get("CompleteTime"));
						taskTitle = Utility.trimNull(taskMap.get("ActiveTaskTitle"));
						
						vo = new  ActivityTaskVO();
						vo.setSerial_no(t_serial_no);
						vo.setTaskFlag(new Integer(3));	
						vo.setTaskTitle(taskTitle);
						vo.setActivitySerial_no(activeSerialNo);
						vo.setExecutor(executor);
						vo.setBeginDate(beginDate);
						vo.setEndDate(endDate);
						vo.setContent(content);
						vo.setRemark(remark);
						vo.setCompleteTime(completeTime);
						vo.setCheckMan(input_operatorCode);
						vo.setCheckOptions(q_check_options);
						vo.setInputMan(input_operatorCode);
				
						activityTaskLocal.modi(vo);
					}	
				}
	}
}

activityTaskLocal.remove();
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("<%=LocalUtilis.language("message.checkOK",clientLocale)%> ！");//审核成功
	location =  "activity_task_check.jsp?q_check_options=<%=q_check_options%>";
</SCRIPT>
