<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,java.math.*,java.io.*"" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
language="cn";  //operator.inc 取不到值，硬编码
FaqsVO vo = new FaqsVO();
FaqsLocal faqs = EJBFactory.getFaqs();
String node_info = "";
boolean bSuccess = false;

String faq_class_no = Utility.trimNull(request.getParameter("faq_class_no"));
String faq_class_name = Utility.trimNull(request.getParameter("faq_class_name"));

int is_new = Utility.parseInt(request.getParameter("is_new"),0);
boolean isNew = false;
if(is_new == 1)
	isNew = true;
if (request.getMethod().equals("POST"))
{
	vo.setWiki_class(faq_class_no);
	if (isNew)
	{		
		vo.setFaq_class_name(faq_class_name);
		vo.setInput_man(input_operatorCode);
		String faq_class_no2 = faqs.addClass(vo);
		Utility.debug(faq_class_no2);
		node_info = faq_class_no2+"|"+faq_class_name;
	} 
	else 
	{		
		vo.setFaq_class_no(faq_class_no);
		vo.setFaq_class_name(faq_class_name);
		vo.setInput_man(input_operatorCode);
		faqs.modiClass(vo);	
		node_info = faq_class_name;
	}
	bSuccess = true;
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

	if(!sl_check(form.faq_class_name, "知识库名称", 24, 1)) return false; //知识库名称
	return sl_check_update();
}
</script>
<BODY leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="wiki_class_update.jsp" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="is_new" value="<%=is_new%>"> 
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
								<td class="page-title">
									<b><font color="#215dc6">知识库分类编辑</font></b>
								</td>
							</tr>
						</table>
						<br><br>
						<table border="0" width="100%" cellspacing="3"> 
							<tr>
								<%if (!isNew){%>
								<td width="31%" align="right">*知识库分类编号 :</td>
								<td width="64%">
									<input  readonly class="edline" onkeydown="javascript:nextKeyPress(this)" size="20" maxlength=6 name="faq_class_no" value="<%=Utility.trimNull(faq_class_no)%>">
								</td>
								<%}%> 
							</tr>
							<tr>
								<td width="31%" align="right">*知识库分类名称 :</td>
								<td width="64%"><input onkeydown="javascript:nextKeyPress(this)" size="20" name="faq_class_name" value="<%=Utility.trimNull(faq_class_name)%>"></td>
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
<%faqs.remove();%>