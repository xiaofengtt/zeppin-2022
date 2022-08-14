<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.contractManage.*,enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%
TcoTeamMemberVO vo = new TcoTeamMemberVO();
TcoTeamMemberLocal tcoTeamMemberLocal = EJBFactory.getTcoTeamMember();

Integer team_id =  Utility.parseInt(request.getParameter("team_id"),null);
Integer serial_no =  Utility.parseInt(request.getParameter("serial_no"),null);

vo.setSerial_no(serial_no);

vo.setInput_man(input_operatorCode);

try{
	tcoTeamMemberLocal.delete(vo);	
}catch(Exception e){
	String message = enfo.crm.tools.LocalUtilis.language("message.deleteFail", clientLocale);//É¾³ıÊ§°Ü
	out.println("<script language=\"javascript\">alert(\""+message+","+e.getMessage()+"\");</script>");
}

tcoTeamMemberLocal.remove();
%>
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<form name="theform" action="tcoTeamMember.jsp" method="post">
	<input type="hidden" name="team_id" value="<%=Utility.trimNull(team_id)%>"/>
</form>
<SCRIPT LANGUAGE="javascript">	
	theform.submit();
</SCRIPT>