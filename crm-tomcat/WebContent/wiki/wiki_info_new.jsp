<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*,enfo.crm.web.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
String faq_class_no = Utility.trimNull(request.getParameter("faq_class_no"));
if(faq_class_no.equals("")){
	faq_class_no = "W0501";
}
String faq_class_name = Utility.trimNull(Argument.getFaqClassName(faq_class_no,input_operatorCode));
String faq_title = "";
String faq_keywords = "";
String faq_content = "";

FaqsVO vo = new FaqsVO();
FaqsLocal faqs = EJBFactory.getFaqs();
Integer df_serial_no = new Integer(0); 
boolean bSuccess = false;

/**增加附件上传 request取值改为file*****/ 
DocumentFileToCrmDB file=null;

if(request.getMethod().equals("POST")){
	file = new DocumentFileToCrmDB(pageContext);
   	file.parseRequest();
   	faq_class_no = file.getParameter("faq_class_no");
	vo.setFaq_title(file.getParameter("faq_title"));
	vo.setFaq_keywords(file.getParameter("faq_keywords"));
	vo.setFaq_content(file.getParameter("faq_content"));
	vo.setInput_man(input_operatorCode);
	vo.setFaq_class_no(faq_class_no);
	df_serial_no = faqs.append(vo);
	file.uploadAttchment(new Integer(56),"TFAQS",df_serial_no,"",input_operatorCode);
	bSuccess = true;
		
}
%>
<html>
<head>
<title>新建知识库</title>
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
	location.href = 'wiki_list.jsp?menu_id=<%=faq_class_no%>';
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
function openTree()
{
	v = showModalDialog('<%=request.getContextPath()%>/wiki/wiki_class_select.jsp', '', 'dialogWidth:450px;dialogHeight:500px;status:0;help:0');
	if (v != null)
	{
		document.theform.faq_class_no.value = v[0];
		document.theform.faq_class_name.value = v[0] + "-" + v[1];
	}		
}

var n=1;
function addline() { 
	n++;
	newline=document.all.test.insertRow()
	newline.id="fileRow"+n; 
	newline.insertCell().innerHTML="<input type='file' name=file_info"+n+" size='45'>"+"<input type='button' class='xpbutton3' style='margin-left:5px; width:45px;' onclick='javascript:removeline(this)' value='移除'/>";  
} 

function removeline(obj){	
	var objSourceRow=obj.parentNode.parentNode;
	var objTable=obj.parentNode.parentNode.parentNode.parentNode; 
	objTable.lastChild.removeChild(objSourceRow);	
}
</script>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="wiki_info_new.jsp" onsubmit="javascript:return validateForm(this);" ENCTYPE="multipart/form-data">
	<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td class="page-title"><font color="#215dc6"><b>新建知识库信息</b></font></td>
		</tr>
	</table>
	<br/>
	<table border="0" align="center" width="100%" cellspacing="3" class="product-list">
		<tr>
			<td  align="right">知识库分类: </td>
			<td  align="left">
				<input type="text" name="faq_class_name" size="20" readonly class="edline" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(faq_class_no+"-"+faq_class_name)%>" />
				<input type="hidden" name="faq_class_no" value="<%=Utility.trimNull(faq_class_no)%>" />
				<button type="button"  class="xpbutton3" accessKey=b  name="btnBrowse" onclick="javascript:openTree();">浏览(B)...</button>
			</td>
		</tr>
		<tr>
			<td  align="right">标题: </td><!--标题-->
			<td  align="left"><input type="text" name="faq_title" size="60" value="" onkeydown="javascript:nextKeyPress(this)"></td>
		</tr>
		<tr>
			<td align="right">关键字: </td><!--关键字-->
			<td><textarea rows="3" name="faq_keywords" onkeydown="javascript:nextKeyPress(this)" cols="60"></textarea>(多个关键字用空格隔开)</td>
		</tr>	
		<tr>
			<td align="right">内容:</td><!--内容-->
			<td>
				<textarea rows="30" name="faq_content" cols="120"></textarea>
				<script type="text/javascript" src="<%=request.getContextPath()%>/webEditor/fckeditor.js"></script>
				<script type="text/javascript">
					var oFCKeditor = new FCKeditor( 'faq_content' ) ;
					oFCKeditor.BasePath = '<%=request.getContextPath()%>/webEditor/' ;
					//oFCKeditor.ToolbarSet = 'Basic' ;
					oFCKeditor.Width = '100%' ;
					oFCKeditor.Height = '250' ;	
					oFCKeditor.ReplaceTextarea() ;								
				</script>
			</td>	
		</tr>
		<tr>
			<td align="right" valign="top">
<!--				<input type="button" onclick="addline()" value="添加更多附件" style="line-height:15px;margin-top:5px;"/>-->
				<button type="button"  class="xpbutton6" accessKey=s id="btnSave" name="btnSave" onclick="javascript:addline();">添加更多附件</button>
			</td>	
			<td>
				<div>
	            	<div style="float:left">
		            	<table id="test" style="width:190px;" ><tr ><td><input type="file" name="file_info" size="45">&nbsp;</td></tr></table>
	            	</div>
	            <div>
			</td>
		</tr>
	</table>
	<table border="0" width="100%">
		<tr>
			<td align="right">
			<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
			&nbsp;&nbsp;<!--保存-->
			<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;history.back();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
			&nbsp;&nbsp;<!--取消-->
			</td>
		</tr>
	</table>
</form>
</body>
</html>
