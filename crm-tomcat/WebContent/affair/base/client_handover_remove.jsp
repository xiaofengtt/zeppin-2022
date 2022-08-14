<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*" %>
<%@ include file="/includes/operator.inc" %>
<%
CustManagerChangesLocal local = EJBFactory.getCustManagerChanges();

TcustmanagerchangesVO vo = new TcustmanagerchangesVO();
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
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">	
	sl_remove_ok("<%=request.getContextPath()%>/affair/base/client_handover.jsp");
</SCRIPT>