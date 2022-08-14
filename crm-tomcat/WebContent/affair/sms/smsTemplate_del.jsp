<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.callcenter.*,enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得对象
SmsTemplateLocal smsTemplocal = EJBFactory.getSmsTemplate();
SmsTemplateVO vo = new SmsTemplateVO();
String[] s = request.getParameterValues("check_smsTempID"); 
Integer tempID = new Integer(0);

if (s != null){
	for(int i = 0;i < s.length; i++){
		tempID = new Integer(Utility.parseInt(s[i], 0));
		if(tempID.intValue()>0){
			vo.setTempId(tempID);
			vo.setInput_man(input_operatorCode);	
			smsTemplocal.delete(vo);
		}
	}
}
%>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ！");//删除成功
	location = "smsTemplate_list.jsp";
</SCRIPT>
<%smsTemplocal.remove();%>