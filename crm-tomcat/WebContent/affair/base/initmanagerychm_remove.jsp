<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.affair.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%
boolean isSuccess = false;
TcustmanagersVO vo = new TcustmanagersVO();
TcustmanagersLocal local = EJBFactory.getTcustmanagers();

vo.setSerial_no(Utility.parseInt(request.getParameter("serial_no"),new Integer(0)));
vo.setInput_man(input_operatorCode);

try{
	local.deleteychm(vo);
	isSuccess = true;
}catch(Exception e){
	isSuccess = false;
	String message = enfo.crm.tools.LocalUtilis.language("message.deleteFail", clientLocale);  //ɾ��ʧ��
    out.println("<script language=\"javascript\">alert(\""+message+","+e.getMessage()+"\"); window.returnValue = null;window.close();</script>");
}
local.remove();
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
<%if(isSuccess){%>
   //ɾ���ɹ�
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ��");
<%}%>	
	location =  "<%=request.getContextPath()%>/affair/base/initmanagerychm.jsp";
</SCRIPT>