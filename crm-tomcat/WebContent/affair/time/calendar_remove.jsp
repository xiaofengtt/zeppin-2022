<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%
ScheDulesVO vo = new ScheDulesVO();
ScheDulesLocal local = EJBFactory.getScheDules();

vo.setSerial_no(Utility.parseInt(request.getParameter("number"),new Integer(0)));
vo.setInput_man(input_operatorCode);

try{
	local.delete(vo);
}catch(Exception e){
	String message = enfo.crm.tools.LocalUtilis.language("message.deleteFail", clientLocale);  //É¾³ýÊ§°Ü
    out.println("<script language=\"javascript\">alert(\""+message+","+e.getMessage()+"\"); window.returnValue = null;window.close();</script>");
}

local.remove();
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">	
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ");//É¾³ý³É¹¦
	window.returnValue = true;
	window.close();
</SCRIPT>
