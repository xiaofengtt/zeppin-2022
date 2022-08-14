<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.affair.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%
boolean isSuccess = false;
TcustmanagersVO vo = new TcustmanagersVO();
TcustmanagersLocal tcustmanagers_Bean = EJBFactory.getTcustmanagers();

vo.setManagerid(Utility.parseInt(request.getParameter("number"),new Integer(0)));
vo.setInput_man(input_operatorCode);

try{
	tcustmanagers_Bean.delete(vo);	
	isSuccess = true;
}catch(Exception e){
	isSuccess = false;
	String message = enfo.crm.tools.LocalUtilis.language("message.deleteFail", clientLocale);  //É¾³ýÊ§°Ü
    out.println("<script language=\"javascript\">alert(\""+message+","+e.getMessage()+"\"); window.returnValue = null;window.close();</script>");
}
tcustmanagers_Bean.remove();
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
<%if(isSuccess){%>
   //É¾³ý³É¹¦
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> £¡");
<%}%>	
	location =  "<%=request.getContextPath()%>/affair/base/initmanager.jsp";
</SCRIPT>