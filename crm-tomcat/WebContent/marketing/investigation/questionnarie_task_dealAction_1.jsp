<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//页面传递变量
Integer ques_taskId = Utility.parseInt(request.getParameter("ques_taskId"), new Integer(0));
Integer ques_taskId_detail = Utility.parseInt(request.getParameter("ques_taskId_detail"), new Integer(0));
Integer targetCustId = Utility.parseInt(request.getParameter("targetCustId"), new Integer(0));
Integer ques_Id = Utility.parseInt(request.getParameter("ques_Id"), new Integer(0));
String[] temp_topic_id = request.getParameterValues("topic_id");
String[] temp_topic_value = request.getParameterValues("topic_value");
Integer topic_id = new Integer(0);
String topic_value ="";

//获得对象
QuestionnaireLocal quesLocal = EJBFactory.getQuestionnaire();
QuestionnaireVO vo_ques = new QuestionnaireVO();	 
ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();
ServiceTaskVO vo_treat = new ServiceTaskVO();

//保存问卷调查记录
if(temp_topic_id!=null&&temp_topic_value!=null){
	for(int i=0;i<temp_topic_id.length;i++){
		topic_id = Utility.parseInt(temp_topic_id[i], new Integer(0));
		topic_value = Utility.trimNull(temp_topic_value[i]);
		
		vo_ques.setQues_taskDetailId(ques_taskId_detail);
		vo_ques.setTarget_custId(targetCustId);
		vo_ques.setQues_taskId(ques_taskId);
		vo_ques.setQues_id(ques_Id);
		vo_ques.setTopic_id(topic_id);
		vo_ques.setTopic_value(topic_value);
		vo_ques.setInputMan(input_operatorCode);		
		
		quesLocal.appendQuestRecord(vo_ques);
	}
}

//任务处理
Integer	service_way = Utility.parseInt(Utility.trimNull(request.getParameter("service_way")), new Integer(0));	
Integer	service_status = Utility.parseInt(Utility.trimNull(request.getParameter("service_status")), new Integer(0));	
Integer r_needFeedback = Utility.parseInt(Utility.trimNull(request.getParameter("feedback")), new Integer(0));	
String	deal_result = Utility.trimNull(request.getParameter("deal_result"));
Integer	satifaction = Utility.parseInt(Utility.trimNull(request.getParameter("Satifaction")), new Integer(0));
String r_executeTime = Utility.trimNull(request.getParameter("execute_date"));

vo_treat.setSerial_no(ques_taskId_detail);
vo_treat.setServiceWay(Utility.trimNull(service_way));
vo_treat.setExecutorTime(r_executeTime);
vo_treat.setStatus(service_status);
vo_treat.setResult(deal_result);
vo_treat.setNeedFeedBack(r_needFeedback);
vo_treat.setSatisfaction(satifaction);
vo_treat.setInputMan(input_operatorCode);

serviceTask.treat_details(vo_treat);
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_update_ok();		
	window.parent.document.getElementById("closeImg").click();		
	window.parent.location.reload();
	window.close();
</SCRIPT>