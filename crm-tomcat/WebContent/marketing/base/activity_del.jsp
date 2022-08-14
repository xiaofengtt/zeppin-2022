<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//获取主键ID 
String[] temp_checks = request.getParameterValues("check_serial_no");
Integer t_serial_no;

//获得对象
ActivityLocal activityLocal = EJBFactory.getActivity();
ActivityVO vo = new ActivityVO();

//逐个删除
if(temp_checks!=null){
	for(int i=0;i<temp_checks.length;i++){
		t_serial_no = Utility.parseInt(temp_checks[i], new Integer(0));
		
		if(t_serial_no.intValue()!=0){				
			vo.setSerial_no(t_serial_no);
			vo.setInput_man(input_operatorCode);
			
			activityLocal.delete(vo);			
		}
	}
}

activityLocal.remove();
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ！");//删除成功
	window.returnValue = 1;
	location =  "activity_list.jsp";
</SCRIPT>
