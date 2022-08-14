<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获取主键ID 
String[] temp_checks = request.getParameterValues("check_serial_no");
Integer tranFlag = Utility.parseInt(request.getParameter("tranFlag"), new Integer(0));
Integer executorID = Utility.parseInt(request.getParameter("executorID"), new Integer(0));
Integer t_serial_no;
boolean bSuccess = false;
//获得对象
ServiceTaskLocal local = EJBFactory.getServiceTask();
ServiceTaskVO vo_allot = new ServiceTaskVO();

vo_allot.setInputMan(input_operatorCode);
vo_allot.setExecutor(executorID);

//逐个删除
if(temp_checks!=null){
	for(int i=0;i<temp_checks.length;i++){
		t_serial_no = Utility.parseInt(temp_checks[i], new Integer(0));
		
		if(t_serial_no.intValue()!=0){				
			vo_allot.setSerial_no(t_serial_no);		
			local.allocate(vo_allot);			
			bSuccess = true;
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
<%if(bSuccess){%>
sl_alert("<%=LocalUtilis.language("message.assignSucceed",clientLocale)%> ！");//分配成功
<%}%>	
<%if(tranFlag.intValue()>0){%>
	location =  "<%=request.getContextPath()%>/marketing/investigation/questionnarie_task_allot.jsp";
<%
}else{%>
	location =  "<%=request.getContextPath()%>/affair/service/service_allot.jsp";
<%}%>
</SCRIPT>
<%local.remove();%>