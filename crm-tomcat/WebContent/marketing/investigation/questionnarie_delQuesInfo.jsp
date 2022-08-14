<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
String[] temp_ques_ids = request.getParameterValues("ques_id");
Integer t_ques_id;

//获得对象
QuestionnaireLocal local = EJBFactory.getQuestionnaire();
QuestionnaireVO vo = new QuestionnaireVO();	 

//逐个删除
if(temp_ques_ids!=null){
	for(int i=0;i<temp_ques_ids.length;i++){
		t_ques_id = Utility.parseInt(temp_ques_ids[i], new Integer(0));
		
		if(t_ques_id.intValue()!=0){		
			vo.setQues_id(t_ques_id);
			vo.setInputMan(input_operatorCode);
		
			local.deleteQuestInfo(vo);
		}	
	}
}

local.remove();
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ！");//删除成功
	location =  "questionnarie_list.jsp";
</SCRIPT>