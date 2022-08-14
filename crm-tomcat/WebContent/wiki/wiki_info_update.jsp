<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*,enfo.crm.web.*,enfo.crm.intrust.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
String faq_class_no = Utility.trimNull(request.getParameter("faq_class_no"));
String q_faq_keywords = Utility.trimNull(request.getParameter("q_faq_keywords"));
String faq_class_name = Utility.trimNull(Argument.getFaqClassName(faq_class_no,input_operatorCode));
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
	vo.setInput_man(input_operatorCode);
	listAll = faqs.listBySql(vo);
	map = (Map)listAll.get(0); 
}

if(map!= null){
	faq_title = Utility.trimNull(map.get("FAQ_TITLE"));
	faq_keywords = Utility.trimNull(map.get("FAQ_KEYWORDS"));
	faq_content = Utility.trimNull(map.get("FAQ_CONTENT"));
}

//附件相关变量
AttachmentToCrmLocal attachmentLocal=EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO=new AttachmentVO();
attachmentVO.setDf_serial_no(serial_no);
attachmentVO.setDf_talbe_id(new Integer(56));
attachmentVO.setInput_man(input_operatorCode);
List attachmentList=null;
Map attachmentMap=null;
String attachmentId="";
String origin_name="";
String save_name="";
attachmentList=attachmentLocal.load(attachmentVO);

/**增加附件上传 request取值改为file*****/ 
DocumentFileToCrmDB file=null;
if(request.getMethod().equals("POST")){
	file = new DocumentFileToCrmDB(pageContext);
   	file.parseRequest();
		
	vo.setSerial_no(Utility.parseInt(file.getParameter("serial_no"),new Integer(0)));
	vo.setFaq_title(file.getParameter("faq_title"));
	vo.setFaq_keywords(file.getParameter("faq_keywords"));
	vo.setFaq_content(file.getParameter("faq_content"));
	vo.setInput_man(input_operatorCode);
	vo.setFaq_class_no(file.getParameter("faq_class_no"));
	faqs.modi(vo);
	file.uploadAttchment(new Integer(56),"TFAQS",vo.getSerial_no(),"",input_operatorCode);
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
	location = 'wiki_info.jsp?serial_no=<%=file.getParameter("serial_no")%>&faq_class_no=<%=file.getParameter("faq_class_no")%>';
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


var n=1;
function addline() { 
	n++;
	newline=document.all.test.insertRow()
	newline.id="fileRow"+n; 
	newline.insertCell().innerHTML="<input type='file' name=file_info"+n+" size='45'>"+"<input type='button' style='margin-left:5px; width:45px;' onclick='javascript:removeline(this)' value='移除'/>";  
} 

function removeline(obj){	
	var objSourceRow=obj.parentNode.parentNode;
	var objTable=obj.parentNode.parentNode.parentNode.parentNode; 
	objTable.lastChild.removeChild(objSourceRow);	
}

/*查看附件方法*/
function DownloadAttachment(attachmentId){
	var url = "<%=request.getContextPath()%>/tool/download1.jsp?attachmentId="+attachmentId;		
	window.location.href = url;	
}

//删除数据库中已有附件的方法
function deleteDbAttachment(attachmentId,save_name){
    if(window.confirm("<%=LocalUtilis.language("message.confirmDeleteAttachment",clientLocale)%>")){ //确认删除附件吗
		window.location.href="faqsRemoveAttachMent.jsp?action=del&attachmentId="+attachmentId+"&save_name="+save_name+"&serial_no=<%=serial_no%>&faq_class_no=<%=faq_class_no%>&q_faq_keywords=<%=q_faq_keywords%>";	
	}
}
</script>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="wiki_info_update.jsp" onsubmit="javascript:return validateForm(this);" ENCTYPE="multipart/form-data">
<input type=hidden name="serial_no" value="<%=serial_no%>">
	<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td class="page-title"><font color="#215dc6"><b>编辑知识库信息</b></font></td>
		</tr>
	</table>
	<table border="0" width="100%" cellspacing="3" class="product-list">
		<tr>
			<td  align="right">知识库分类：</td>
			<td  align="left">
				<input type="text" name="faq_class_name" size="20" readonly class="edline" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(faq_class_no+"-"+faq_class_name)%>">
				<input type="hidden" name="faq_class_no" value="<%=faq_class_no%>">
			</td>
		</tr>
		<tr>
			<td  align="right">标题: </td><!--标题-->
			<td  align="left"><input type="text" name="faq_title" size="60" value="<%=faq_title%>" onkeydown="javascript:nextKeyPress(this)"></td>
		</tr>
		<tr>
			<td align="right">关键字: </td><!--关键字-->
			<td><textarea rows="3" name="faq_keywords" onkeydown="javascript:nextKeyPress(this)" cols="60"><%=faq_keywords%></textarea>(多个关键字用空格隔开)</td>
		</tr>	
		<tr>
			<td align="right">内容:</td><!--内容-->
			<td>
				<textarea rows="30" name="faq_content" cols="120"><%=Utility.trimNull(faq_content)%></textarea>
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
<%
	for(int i=0;i<attachmentList.size();i++){
		attachmentMap=(Map)attachmentList.get(i);
		attachmentId=Utility.trimNull(attachmentMap.get("ATTACHMENTID"));
		origin_name = Utility.trimNull(attachmentMap.get("ORIGIN_NAME"));
	   	save_name=Utility.trimNull(attachmentMap.get("SAVE_NAME"));
%>
		<tr>
			<td align="right">
				附件<%=i+1%>：
			</td>
					<td>
						<table border="0" width="100%">
							<tr>
								<td width="400px"><a href="#"  onclick="javascript:DownloadAttachment(<%=attachmentId%>)"><%= origin_name%></a></td>
								<td align="left"><button type="button"  class="xpbutton3"  id="btn_DelDbAttachment" name="btn_DelDbAttachment" onclick="javascript:deleteDbAttachment('<%=attachmentId%>','<%=save_name%>');">删除附件</button></td>
							</tr>
						</table>
					</td>
		</tr>
				
<%	}
 %>	
		
		<tr>
			<td align="right" valign="top">
<!--				<input type="button" onclick="addline()" value="添加更多附件" style="line-height:15px;margin-top:5px;"/>-->
				<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:addline();">添加更多附件</button>
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
			<button type="button"  class="xpbutton3" accessKey="b" onclick="javascript:location='wiki_info.jsp?serial_no=<%=serial_no%>&q_faq_keywords=<%=q_faq_keywords%>&faq_class_no=<%=faq_class_no%>'">返回(<u>B</u>)</button>
			&nbsp;&nbsp;<!--取消-->
			</td>
		</tr>
	</table>
</form>
</body>
</html>
