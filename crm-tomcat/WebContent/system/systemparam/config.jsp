<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> 
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
String user_name = Utility.trimNull(request.getParameter("user_name"));
String address = Utility.trimNull(request.getParameter("address"));
String post = Utility.trimNull(request.getParameter("post"));
String tel = Utility.trimNull(request.getParameter("tel")); 
String fax =Utility.trimNull(request.getParameter("fax"));
int flag = Utility.parseInt(request.getParameter("queryflag"), 0);
boolean bSuccess = false;
 
SystemInfoLocal systeminfo = EJBFactory.getSystemInfo();
SystemInfoVO vo = new SystemInfoVO();

if(request.getMethod().equals("POST")){	
	vo.setUser_id(user_id);
	vo.setUser_name(user_name);	
	vo.setAddress(address);
	vo.setPost(post);
	vo.setTel(tel);
	vo.setFax(fax);
	
	systeminfo.modi(vo,input_operatorCode);
	bSuccess=true;
}

if(flag==0){
	List rsListSig = systeminfo.listBySig(user_id);	
	Map rowMapSig = (Map)rsListSig.get(0);
	
	if(rowMapSig!= null){
		user_name = Utility.trimNull(rowMapSig.get("USER_NAME"));		
		address = Utility.trimNull(rowMapSig.get("ADDRESS"));
		post = Utility.trimNull(rowMapSig.get("POST_CODE"));
		tel = Utility.trimNull(rowMapSig.get("TELEPHONE"));	
		fax = Utility.trimNull(rowMapSig.get("FAX"));		
	}
}
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.config",clientLocale)%> </TITLE>
<!--首页参数维护-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>
<%
if (bSuccess){
%>
	sl_update_ok();
<%}
bSuccess=false;
%>
function validateForm(form){
	if(!sl_check(theform.post, "<%=LocalUtilis.language("class.post2",clientLocale)%> ", 6, 6))	return false;//邮编
	if(!sl_check(theform.tel, "<%=LocalUtilis.language("class.tel",clientLocale)%> ", 15, 1))	return false;//电话号码
	if(!sl_check(theform.fax, "<%=LocalUtilis.language("class.fax",clientLocale)%> ", 15, 1))	return false;//传真		
	return sl_check_update();
	
}
</script>
</HEAD>
<BODY leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" MARGINWIDTH="0" MARGINHEIGHT="0" class="body body-nox">
<%@ include file="/includes/waiting.inc"%>    
<form name="theform" method="post" action="config.jsp" onsubmit="javascript:return validateForm(this);">
	<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
		<TR>
		<TD>
			<table border="0" width="100%" cellspacing="0" cellpadding="0" align="center">
				<tr>
					<td class="page-title">	
						<font color="#215dc6"><b><%=menu_info%></b></font>
					</td>
				</tr>
				<tr>
					<td align="right">
					<div class="btn-wrapper">
					<%if(input_operator.hasFunc(menu_id, 102)){%>
						<button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" title="<%=LocalUtilis.language("message.saveParameter",clientLocale)%> "  type="submit"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)
						</button><!--保存参数--><!--保存-->
					<%}%>
					</div><br/>
					</td>
				<tr>
			</table>
			
			<table id="table3" border="0" cellspacing="1" cellpadding="2" bgcolor="#000000" width="100%" height="25" class="table-popup">
				<tr class="trtagsort trh">
					<td align="right" height="25" width="15%"><%=LocalUtilis.language("message.paraName",clientLocale)%> </td><!--参数名称-->
					<td align="center" height="25" width="85%"><%=LocalUtilis.language("message.paraValue",clientLocale)%> </td><!--参数值-->
				</tr>			
				<TR class="tr1">
					<TD class="configtd" align="right" height="25" width="15%"><%=LocalUtilis.language("class.userName",clientLocale)%> </TD><!--用户名称-->
					<TD align="left" height="25" width="85%"><INPUT align="center" type="text" name="user_name" size="150" value="<%=user_name%>"></TD>
				</TR>
				<TR class="tr1">
					<TD class="configtd" align="right" height="25" width="15%"><%=LocalUtilis.language("class.address",clientLocale)%> </TD><!--地址-->
					<TD align="left" height="25" width="85%"><INPUT type="text" name="address" size="150" value="<%=address%>"></TD>
				</TR>
				<TR class="tr1">
					<TD class="configtd" align="right" height="25" width="15%"><%=LocalUtilis.language("class.post2",clientLocale)%> </TD><!--邮编-->
					<TD align="left" height="25" width="85%"><INPUT type="text" name="post" size="150" value="<%=post%>"></TD>
				</TR>
				<TR class="tr1">
					<TD class="configtd" align="right" height="25" width="15%"><%=LocalUtilis.language("class.tele",clientLocale)%> </TD><!--电话-->
					<TD align="left" height="25" width="85%"><INPUT type="text" name="tel" size="150" value="<%=tel%>"></TD>
				</TR>
				<TR class="tr1">
					<TD class="configtd" align="right" height="25" width="15%"><%=LocalUtilis.language("class.fax",clientLocale)%> </TD><!--传真-->
					<TD align="left" height="25" width="85%"><INPUT type="text" name="fax" size="150" value="<%=fax%>"></TD>
				</TR>	
			</table>			
		</td>
		</tr>
	</table>					
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%
systeminfo.remove();
%>

