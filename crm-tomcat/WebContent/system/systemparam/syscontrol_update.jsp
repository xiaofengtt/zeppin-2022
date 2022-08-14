<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
DictparamLocal dictparam = EJBFactory.getDictparam();
DictparamVO vo = new DictparamVO();

boolean bSuccess = false;
String flag_type = Utility.trimNull(request.getParameter("flag_type"));
Integer is_modi  = new Integer(Utility.parseInt(request.getParameter("is_modi"), 1));
String type_name = Utility.trimNull(request.getParameter("type_name"));
Integer value  = new Integer(Utility.parseInt(request.getParameter("value"), 0));
String summary = Utility.trimNull(request.getParameter("summary"));

if (request.getMethod().equals("POST")){
	vo.setFlag_type(flag_type);
	vo.setIs_modi(is_modi);
	vo.setType_name(type_name);
	vo.setValue(value);
	vo.setSummary(summary);

	dictparam.modiSysControl(vo,input_operatorCode);
	bSuccess = true;
}
else if (flag_type!=""){
	vo.setFlag_type(flag_type);
	List rsListSig = dictparam.listSysControl(vo);
	Map rowMapSig = (Map)rsListSig.get(0);
	
	if(rowMapSig!=null)
	{
		flag_type = Utility.trimNull(rowMapSig.get("FLAG_TYPE"));
		type_name = Utility.trimNull(rowMapSig.get("TYPE_NAME"));
    	value = Utility.parseInt(Utility.trimNull(rowMapSig.get("VALUE")),new Integer(0));
		summary = Utility.trimNull(rowMapSig.get("SUMMARY"));
	}
}
%>


<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>

<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<title><%=LocalUtilis.language("menu.sysControlUpdate",clientLocale)%> </title>
<!--系统开关信息-->
</HEAD>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>
<%if (bSuccess)
{
%>
	window.returnValue = 1;
	window.close();
<%}%>

function validateForm(form)
{
	if(!sl_check(form.value, "<%=LocalUtilis.language("class.switchValue",clientLocale)%> ", 10, 1))	return false;//开关值	
	
	if(!sl_check(form.type_name, "<%=LocalUtilis.language("class.typeName3",clientLocale)%> ", 60, 1))	return false;//开关说明	
	if(!sl_check(form.summary, "<%=LocalUtilis.language("class.summary3",clientLocale)%> ", 800, 1))	return false;//开关含义	
	return sl_check_update();
}
</script>
<BODY class="BODY" leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="syscontrol_update.jsp" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="flag_type" value="<%=flag_type%>">
<input type=hidden name="typevalue" value="">
<form name="theform">

<TABLE height="100%" cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TBODY>
		<TR>

			<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=4 align=center border=0 width="100%">
				<TBODY>
					<TR>
						<TD align=middle>
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td><img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b><%=LocalUtilis.language("menu.controlParaSet",clientLocale)%> </b></font></td>
							</tr><!--控制参数设置-->
							<tr>
								<td>
								<hr noshade color="#808080" size="1">
								</td>
							</tr>
						</table>
						<table border="0" width="100%" cellspacing="3">
							<tr>
								<td width="100%" align="left" colspan=2><b><%=LocalUtilis.language("message.controlParaInfo",clientLocale)%> </b></td>
							</tr><!--控制参数信息-->
							<tr>
								<td width="46%" align="right"><%=LocalUtilis.language("class.typeFlag",clientLocale)%> :</td><!--参数-->
								<td width="49%"><input type="text" class="edline" name="type_id" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=flag_type%>"></td>
							</tr>
							
							<tr>
								<td width="46%" align="right"><%=LocalUtilis.language("class.value",clientLocale)%> :&nbsp;</td><!--值-->
								<td width="49%"><input  type="text" size=20 name="value"  onkeydown="javascript:nextKeyPress(this)" value="<%=value%>">
								</td>
							</tr>
							<tr>
								<td width="46%" align="right"><%=LocalUtilis.language("class.name",clientLocale)%> :</td><!--名称-->
								<td width="49%"><input type="text" name="type_name" onkeydown="javascript:nextKeyPress(this)" size="50" value="<%=type_name%>"></td>
							</tr>
							<tr>
								<td width="46%" align="right"><%=LocalUtilis.language("class.summary4",clientLocale)%> :</td><!--含义-->
								<td width="49%">
								<textarea rows="3" name="summary" cols="60"><%=summary%></textarea>
								</td>
							</tr>
						</table>
						<table border="0" width="100%">
							<tr>
								<td align="right">
								<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();">
								    <%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)
								</button><!--保存-->
								&nbsp;&nbsp;
								<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">
								    <%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)
								</button><!--取消-->
								&nbsp;&nbsp;
								</td>
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
<%dictparam.remove();
%>
