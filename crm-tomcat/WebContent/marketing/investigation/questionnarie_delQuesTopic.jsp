<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
Integer ques_id = Utility.parseInt(Utility.trimNull(request.getParameter("ques_id")),new Integer(0));
String[] temp_topic_ids = request.getParameterValues("topic_id");
Integer t_topic_id;

//获得对象
QuestionnaireLocal local = EJBFactory.getQuestionnaire();
QuestionnaireVO vo = new QuestionnaireVO();	 

//逐个删除
if(temp_topic_ids!=null){
	for(int i=0;i<temp_topic_ids.length;i++){
		t_topic_id = Utility.parseInt(temp_topic_ids[i], new Integer(0));
		
		if(t_topic_id.intValue()!=0){		
			vo.setTopic_id(t_topic_id);
			vo.setInputMan(input_operatorCode);
		
			local.deleteQuesTopic(vo);
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
