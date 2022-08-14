<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*,java.io.*" %>
<%@ include file="/includes/operator.inc"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>

<%
language="cn";  //operator.inc 取不到值，硬编码
IntrustCapitalTypeLocal opertype = EJBFactory.getIntrustCapitalTypeLocal();
IntrustCapitalTypeLocal operParenttype = EJBFactory.getIntrustCapitalTypeLocal();
String node_info = "";
boolean bSuccess = false;
Integer depart_id = new Integer(Utility.parseInt(request.getParameter("depart_id"), 0));
Integer parent_id = new Integer(Utility.parseInt(request.getParameter("parent_id"), 0));

Integer zc_flag=new Integer(Utility.parseInt(request.getParameter("zc_flag"), 1));
if(parent_id.intValue()==-1)
{
	zc_flag=new Integer(1);		
	operParenttype.setCaption("信托资产");
}		
if(parent_id.intValue()==-2)
{
	zc_flag=new Integer(2);	
	operParenttype.setCaption("非信托资产");
}		
			
String caption=Utility.trimNull(request.getParameter("caption"));
String zclb_bh=Utility.trimNull(request.getParameter("zclb_bh"));
boolean isNew = (request.getParameter("is_new") != null);
if (depart_id.intValue() == 0)
{
	isNew = true;
}
opertype.setZc_flag(zc_flag);
if (request.getMethod().equals("POST"))
{
	opertype.setCaption(caption); 	
	if(parent_id.intValue()<0)
		parent_id=new Integer(0);		
	if (isNew)
	{		
		opertype.setBookcode(input_bookCode);
		opertype.setParent_id(parent_id);
		opertype.setZclb_bh(zclb_bh);
		opertype.append(input_operatorCode);
		depart_id = opertype.getSerial_no();
		node_info = depart_id+"|"+zclb_bh+"-"+caption+"|"+caption+"|"+zclb_bh;
	} 
	else 
	{		
		opertype.setSerial_no(depart_id);
		opertype.save(input_operatorCode);
		node_info = caption;
	}
	bSuccess = true;
}
if (depart_id.intValue()!=0)
{
	opertype.setBookcode(input_bookCode);
	opertype.setSerial_no(depart_id);
	opertype.load(input_operatorCode);
	opertype.getNext();
	parent_id=opertype.getParent_id();	
}
if (parent_id!=null)
{
	if(parent_id.intValue()>0)
	{
		operParenttype.setBookcode(input_bookCode);
		operParenttype.setSerial_no(parent_id);
		operParenttype.load(input_operatorCode);
		operParenttype.getNext();
	}	
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
<%}else{ %>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<%} %>

<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<title><%=LocalUtilis.language("menu.busiTypeInfo",clientLocale)%></title><!-- 业务类别信息 -->
</HEAD>
<script language=javascript>
<%if (bSuccess){
%>
	window.returnValue = "<%=node_info%>";
	window.close();
<%}%>

function validateForm(form){	
	if(form.parent_id.value>0)
	{
		if(!sl_check(form.zclb_bh, "<%=LocalUtilis.language("message.noAssetClass",clientLocale)%>", 6, 1))	return false;//资产类别编号
	}	
	if(!sl_check(form.caption, "<%=LocalUtilis.language("message.nameofassetclasses",clientLocale)%>", 24, 1)) return false; //资产类别名称
	return sl_check_update();
}
</script>
<BODY leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="capitaltype_update.jsp" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="depart_id" value="<%=Utility.trimNull(request.getParameter("depart_id"))%>">
<input type=hidden name="parent_id" value="<%=Utility.trimNull(parent_id)%>">
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
								<td>
									<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absbottom" width="32" height="28">
									<b><font color="#215dc6"><%=LocalUtilis.language("menu.busiTypeInfo",clientLocale)%></font></b>
								</td>
							</tr>
							<tr>
								<td><hr noshade color="#808080" size="1"></td>
							</tr>
						</table>
						<br><br>
						<table border="0" width="100%" cellspacing="3"> 
							<tr>
								<td align="right" >*<%=LocalUtilis.language("message.sourcesofassets",clientLocale)%> :</td><!-- 资产来源 -->
								<td>
									<input onkeydown="javascript:nextKeyPress(this)" readonly class="edline" size="20" name="zc_flag" value="<%=Argument.getZclbFlagName(opertype.getZc_flag())%>">
								</td>
							</tr>
							<tr>
								<td width="31%" align="right"><%=LocalUtilis.language("message.parentName",clientLocale)%> :</td><!-- 上级名称 -->
								<td width="64%"><input onkeydown="javascript:nextKeyPress(this)" readonly class="edline" size="20" name="parent_caption" value="<%=Utility.trimNull(operParenttype.getCaption())%>"></td>
							</tr>
							<tr>
								<td width="31%" align="right">*<%=LocalUtilis.language("message.noassetclass",clientLocale)%> :</td><!-- 资产类别编号 -->
								<td width="64%"><input <%if (!isNew){%> readonly class="edline" <%}%> onkeydown="javascript:nextKeyPress(this)" size="20" maxlength=6 name="zclb_bh" value="<%=Utility.trimNull(opertype.getZclb_bh())%>"></td>
							</tr>
							<tr>
								<td width="31%" align="right">*<%=LocalUtilis.language("class.capitalTypeName",clientLocale)%> :</td><!-- 资产类别名称 -->
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