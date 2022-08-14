<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%
TmanagerteammembersVO vo = new TmanagerteammembersVO();
TmanagerteamsLocal tmanagerteams_Bean = EJBFactory.getTmanagerteams();

Integer team_id =  Utility.parseInt(request.getParameter("team_id"),null);
Integer serial_no =  Utility.parseInt(request.getParameter("serial_no"),null);

vo.setSerial_no(Utility.parseInt(request.getParameter("number"),new Integer(0)));

vo.setInput_man(input_operatorCode);

try{
	tmanagerteams_Bean.delete(vo);	
}catch(Exception e){
	String message = enfo.crm.tools.LocalUtilis.language("message.deleteFail", clientLocale);//É¾³ıÊ§°Ü
	out.println("<script language=\"javascript\">alert(\""+message+","+e.getMessage()+"\");</script>");
}

tmanagerteams_Bean.remove();
%>
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<form name="theform" action="<%=request.getContextPath()%>/affair/base/team_number.jsp" method="post">
	<input type="hidden" name="team_id" value="<%=Utility.trimNull(team_id)%>"/>
	<input type="hidden" name="serial_no" value="<%=Utility.trimNull(serial_no)%>"/>
</form>
<SCRIPT LANGUAGE="javascript">	
	theform.submit();
</SCRIPT>