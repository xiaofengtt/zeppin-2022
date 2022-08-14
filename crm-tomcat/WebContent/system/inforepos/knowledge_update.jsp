<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),null);
String faq_title = "";
String faq_keywords = "";
String faq_content = "";



FaqsVO vo = new FaqsVO();
FaqsLocal faqs = EJBFactory.getFaqs();

boolean bSuccess = false;
List listAll = null;
Map map = null;

if(serial_no.intValue()!= 0){
	vo.setSerial_no(serial_no);
	listAll = faqs.listBySql(vo);
	map = (Map)listAll.get(0); 
}

if(map!= null){
	faq_title = Utility.trimNull(map.get("FAQ_TITLE"));
	faq_keywords = Utility.trimNull(map.get("FAQ_KEYWORDS"));
	faq_content = Utility.trimNull(map.get("FAQ_CONTENT"));
}

if(request.getMethod().equals("POST")){
	vo.setSerial_no(serial_no);
	vo.setFaq_title(request.getParameter("faq_title"));
	vo.setFaq_keywords(request.getParameter("faq_keywords"));
	vo.setFaq_content(request.getParameter("faq_content"));
	vo.setInput_man(input_operatorCode);
	faqs.modi(vo);
	bSuccess = true;
		
}
%>
<html>
<head>
<title>编辑知识库</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
</head>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>

<%
if (bSuccess){
%>
	window.returnValue = 1;
	window.close();
<%}%>

function $$(_name){
	return document.getElementsByName(_name)[0];
}

function validateForm(form)
{
	if(!sl_check(form.faq_title, '标题', 60, 1))		return false;//标题
	if(!sl_check(form.faq_keywords, '关键字', 200, 1))		return false;//关键字
	return sl_check_update();
}
</script>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="knowledge_update.jsp" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="is_dialog" value="1">
<input type=hidden name="serial_no" value="<%=serial_no%>">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b>编辑知识库信息</b></font></td>
				</tr>
				<tr>
					<td>
					<hr noshade color="#808080" size="1">
					</td>
				</tr>
</table>
<table border="0" width="100%" cellspacing="3">
			<tr>
				<td  align="right">标题: </td><!--标题-->
				<td  align="left"><input type="text" name="faq_title" size="60" value="<%=faq_title%>" onkeydown="javascript:nextKeyPress(this)"></td>
			</tr>
			<tr>
				<td align="right">关键字: </td><!--关键字-->
<!--				<td align="left" ><input type="text" name="faq_keywords" size="60" value="" onkeydown="javascript:nextKeyPress(this)"></td>-->
				<td><textarea rows="3" name="faq_keywords" onkeydown="javascript:nextKeyPress(this)" cols="60"><%=faq_keywords%></textarea></td>
			</tr>	
			<tr>
				<td align="right">内容:</td><!--内容-->
				<td><textarea rows="8" name="faq_content"  onkeydown="javascript:nextKeyPress(this)" cols="60"><%=faq_content%></textarea></td>
			</tr>
</table>
<table border="0" width="100%">
			<tr>
				<td align="center">
				<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
				&nbsp;&nbsp;<!--保存-->
				<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
				&nbsp;&nbsp;<!--取消-->
				</td>
			</tr>
</table>
</form>
</body>
</html>
