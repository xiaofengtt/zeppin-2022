<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//��ȡҳ�洫�ݱ���
Integer flag = Utility.parseInt(Utility.trimNull(request.getParameter("flag")),new Integer(0));
Integer topic_id =  Utility.parseInt(Utility.trimNull(request.getParameter("topic_id")),new Integer(0));

//��ȡ����
QuestionnaireLocal local = EJBFactory.getQuestionnaire();
QuestionnaireVO vo = new QuestionnaireVO();	 

vo.setFlag(flag);
vo.setTopic_id(topic_id);
vo.setInputMan(input_operatorCode);

local.locationTQuesTopic(vo);

local.remove();
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	window.close();
</SCRIPT>
