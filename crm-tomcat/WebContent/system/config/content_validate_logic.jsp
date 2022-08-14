<%@ page contentType="text/html; charset=GBK" pageEncoding="GBK" import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*" %>
<%@ include file="/includes/operator.inc" %>

<%
	String className=Utility.trimNull(request.getParameter("className"));
	String methodName=Utility.trimNull(request.getParameter("methodName"));
	String paramName = Utility.trimNull(request.getParameter("paramName"));
	ConfigLocal configLocal=EJBFactory.getConfig();
	boolean returnFlag = true;
	String[] returnValue=paramName.split("@@");
	
	//字段自定义界面的字段校验
	if(className.equals("ConfigUtil") && methodName.equals("isFieldExistContent")){
		returnFlag = ConfigUtil.isFieldExistContent(configLocal,returnValue[0],returnValue[1]);
	//要素自定义（辅助）要素校验	
	}else if(className.equals("ConfigUtil") && methodName.equals("isFieldExistElement")){
		returnFlag = ConfigUtil.isFieldExistElement(configLocal,returnValue[0],returnValue[1]);
	}
%>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
<%if(returnFlag){%>
	window.returnValue='1';
<%}else{//已存在%>
	window.returnValue='0';
<%}%>
window.close();
</SCRIPT>