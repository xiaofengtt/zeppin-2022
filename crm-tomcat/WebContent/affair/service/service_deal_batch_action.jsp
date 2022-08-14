<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递参数
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), null);//主任务ID
String[] temp_checks = request.getParameterValues("check_serial_no");//主键ID
Integer t_serial_no;
String r_executeTime = Utility.trimNull(request.getParameter("execute_date"));
Integer service_way = Utility.parseInt(Utility.trimNull(request.getParameter("service_way")), new Integer(0));	
Integer r_needFeedback = Utility.parseInt(Utility.trimNull(request.getParameter("feedback")), new Integer(0));	
Integer service_status = Utility.parseInt(Utility.trimNull(request.getParameter("service_status")), new Integer(0));	
String deal_result = Utility.trimNull(request.getParameter("deal_result"));
Integer satifaction = Utility.parseInt(Utility.trimNull(request.getParameter("Satifaction")), new Integer(0));

//获得对象
ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();
ServiceTaskVO vo_treat = null;

if(request.getMethod().equals("POST")){
	//逐个保存
	if(temp_checks!=null){
		for(int i=0;i<temp_checks.length;i++){
			t_serial_no = Utility.parseInt(temp_checks[i], new Integer(0));
			
			if(t_serial_no.intValue()!=0){		
				vo_treat = new ServiceTaskVO();		
				
				vo_treat.setSerial_no(t_serial_no);
				vo_treat.setServiceWay(service_way.toString());
				vo_treat.setExecutorTime(r_executeTime);
				vo_treat.setStatus(service_status);
				vo_treat.setResult(deal_result);
				vo_treat.setNeedFeedBack(r_needFeedback);
				vo_treat.setSatisfaction(satifaction);
				vo_treat.setInputMan(input_operatorCode);
				
				serviceTask.treat_details(vo_treat);
			}
		}
	}
}

serviceTask.remove();
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_update_ok();		
	window.location.href="<%=request.getContextPath()%>/affair/service/service_deal_add.jsp?serial_no=<%=serial_no%>";
</SCRIPT>