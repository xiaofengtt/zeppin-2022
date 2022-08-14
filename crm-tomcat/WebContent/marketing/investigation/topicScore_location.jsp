<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获取页面传递变量
Integer flag = Utility.parseInt(Utility.trimNull(request.getParameter("flag")),new Integer(0));
Integer serialNo =  Utility.parseInt(Utility.trimNull(request.getParameter("serialNo")),new Integer(0));

//获取对象
QuestionnaireLocal local = EJBFactory.getQuestionnaire();
QuestionnaireVO vo = new QuestionnaireVO();	 

vo.setFlag(flag);
vo.setSerial_no(serialNo);
vo.setInputMan(input_operatorCode);
local.locationTTopicScore(vo);

local.remove();
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	window.close();
</SCRIPT>
