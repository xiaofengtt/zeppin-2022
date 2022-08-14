<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
String[] temp_checks = request.getParameterValues("check_serial_no");
Integer t_serial_no;

//获得对象
QuestionnaireLocal local = EJBFactory.getQuestionnaire();
QuestionnaireVO vo = new QuestionnaireVO();	 

//逐个删除
if(temp_checks!=null){
	for(int i=0;i<temp_checks.length;i++){
		t_serial_no = Utility.parseInt(temp_checks[i], new Integer(0));
		
		if(t_serial_no.intValue()!=0){			
			vo.setSerial_no(t_serial_no);
			vo.setInputMan(input_operatorCode);
			
			local.deleteTTopicScore(vo);
		}	
	}
}

local.remove();
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<script language="javascript">
alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ！");//删除成功
window.close();
</script>
</head>

<body>

</body>
</html>

