<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*,java.io.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>

<%
language="cn"; //operator.inc 取不到值，硬编码
OpertypeLocal opertype = EJBFactory.getOpertype();
boolean bSuccess = false;
String node_info = "";
Integer depart_id = new Integer(Utility.parseInt(request.getParameter("depart_id"), 0));
Integer parent_id = new Integer(Utility.parseInt(request.getParameter("parent_id"), 0));
String caption=Utility.trimNull(request.getParameter("caption"));
boolean isNew = (request.getParameter("is_new") != null);
if (depart_id.intValue() == 0){
	isNew = true;
}
if (request.getMethod().equals("POST")){
	opertype.setCaption(caption); 	
	if (isNew){		
		opertype.setBookcode(input_bookCode);
		opertype.setParent_id(parent_id);
		opertype.append(input_operatorCode);
		depart_id = opertype.getSerial_no();
		node_info = depart_id+"|"+caption;
	} else {		
		opertype.setSerial_no(depart_id);
		opertype.save(input_operatorCode);
		node_info = caption;
	}
	bSuccess = true;
}
if (depart_id.intValue()!=0){
	opertype.setBookcode(input_bookCode);
	opertype.setSerial_no(depart_id);
	opertype.load(input_operatorCode);
	opertype.getNext();
}
%>
<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<%if(language.equals("en")){ %>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_en.js"></SCRIPT>
<%}else{%>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<%}%>

<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<title><%=LocalUtilis.language("message.typeofbusinessinformation",clientLocale)%> </title><!-- 业务类别信息 -->
</HEAD>
<script language=javascript>
<%if (bSuccess){
%>
	window.returnValue = '<%=node_info%>';
	window.close();
<%}%>

function validateForm(form){	
	if(!sl_check(form.caption, '<%=LocalUtilis.language("message.businessCategoryName",clientLocale)%> ', 24, 1))		return false;//业务类别名称
	return sl_check_update();
}
</script>
<BODY leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="opertype_update.jsp" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="depart_id" value="<%=Utility.trimNull(request.getParameter("depart_id"))%>">
<input type=hidden name="parent_id" value="<%=Utility.trimNull(request.getParameter("parent_id"))%>">
<%if (depart_id.intValue() == 0){%>
<input type=hidden name="is_new" value="1"> <%}
%>
<TABLE height="100%" cellSpacing=0 cellPadding=0 border=0 width="389">
	<TBODY>
		<TR>
			<TD vAlign=top align=left width="354">
			<TABLE cellSpacing=0 cellPadding=4 align=center border=0 width="353">
				<TBODY>
					<TR>
						<TD align=middle width="343">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td><img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absbottom" width="32" height="28"><b><font color="#215dc6"><%=LocalUtilis.language("message.typeofbusinessinformation",clientLocale)%> </font></b></td>
							</tr>
							<tr>
								<td>
								<hr noshade color="#808080" size="1">
								</td>
							</tr>
						</table>
						<br><br>
						<table border="0" width="100%" cellspacing="3">
						<%if (depart_id.intValue()!=0){
							%>
							<tr>
							<td width="31%" align="right"><%=LocalUtilis.language("message.highercategoryname",clientLocale)%> :</td><!-- 上级类别名称 -->
								<td width="64%"><input onkeydown="javascript:nextKeyPress(this)" readonly class="edline" size="20" name="parent_caption" <%if(opertype.getParent_id().intValue()==0){%>value="业务类别"<%}else{%>value="<%=Utility.trimNull(opertype.getParent_caption())%>"<%}%>></td>
							</tr>
							<%}%>
							<tr>
								<td width="31%" align="right"><%=LocalUtilis.language("message.businessCategoryName",clientLocale)%> :</td><!-- 上级类别名称 -->
								<td width="64%"><input onkeydown="javascript:nextKeyPress(this)" size="20" name="caption" value="<%=Utility.trimNull(opertype.getCaption())%>"></td>
							</tr>
						</table>
						<table border="0" width="100%">
							<tr>
								<td align="right">&nbsp;&nbsp;
								<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()){ document.theform.submit();}"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
								&nbsp;&nbsp;
								<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
								&nbsp;&nbsp;</td>
							</tr>
						</table>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</form>
</BODY>
</HTML>
<%opertype.remove();%>