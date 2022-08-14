<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
RolerightLocal roleRight = EJBFactory.getRolering();
RolerightVO vo = new RolerightVO();
//roleRight.setInput_man(input_operatorCode);

sPage = request.getParameter("page");
sPagesize = request.getParameter("pagesize");
Integer role_id = new Integer(Utility.parseInt(request.getParameter("role_id").trim(),0));
String menu_id2 = request.getParameter("menu_id");
Integer func_id = new Integer(Utility.parseInt(request.getParameter("func_id"),0));
Integer flag = new Integer(Utility.parseInt(request.getParameter("flag"),0));
roleRight.update(role_id,menu_id2,func_id,input_operatorCode,flag); 

%>

<HTML><HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 
<META HTTP-EQUIV="Expires" CONTENT="0"> 
<BASE TARGET="_self">
<title></title>
</HEAD>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">	
	window.returnValue = 1;
	window.close();
</SCRIPT>
<BODY class="BODY" leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
</BODY>
<%roleRight.remove();%>