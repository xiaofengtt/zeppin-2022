<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<HTML>
<HEAD>
<%int pos_flag = Utility.parseInt(request.getParameter("pos_flag"), 0);
%>
<TITLE>手持设备证件识别导入</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
/*取消*/
	function CancelAction(){
			window.parent.document.getElementById("closeImg").click();	
	}
/*确认*/
	function SaveAction(){
        var imagepath="";
        var txtpath="";

		var file_upl = document.getElementById('resultTxt');
		file_upl.select();
		txtpath = document.selection.createRange().text;
		var image_upl = document.getElementById('imageIdentification');
		image_upl.select();
        imagepath = document.selection.createRange().text;

         if(txtpath==""){
            sl_alert("请选择返回结果文件");
            return ;
         }
         if(imagepath==""){
             sl_alert("请选择上传照片");
             return ;
          }	
      var url="wireCard_discern.jsp?resultTxt="+txtpath+"&image="+imagepath+"&pos_flag=<%=pos_flag%>";
      document.theform.action=url;
      document.theform.submit();
      //location=url;	 
	}
//检验图片上传的格式
function checkFileType()  
{  
	var custFile = document.getElementById("imageIdentification");  
	var filePath = custFile.value;
	var dotNdx = filePath.lastIndexOf('.');          
	var exetendName = filePath.slice(dotNdx + 1).toLowerCase();    

	if((exetendName == "jpg")) {
 		return true;    
	}else{
		custFile.select()
  		document.execCommand("Delete");
   		custFile.focus(); 
		sl_alert("<%=LocalUtilis.language("message.invalidImgFormat",clientLocale)%> ！");//图片的格式无效，请选择您要上传的JPG文件
		return false; 
	}
} 
//检验文本文件上传的格式
function checkFileTxtType()  
{  
	var custFile = document.getElementById("resultTxt");  
	var filePath = custFile.value;
	var dotNdx = filePath.lastIndexOf('.');          
	var exetendName = filePath.slice(dotNdx + 1).toLowerCase();    

	if((exetendName == "txt")) {
 		return true;    
	}else{
		custFile.select()
  		document.execCommand("Delete");
   		custFile.focus(); 
		sl_alert("格式无效，请上传TXT文件 ！");//图片的格式无效，请选择您要上传的JPG文件
		return false; 
	}
} 
</script>
</HEAD> <body class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" enctype="multipart/form-data">
<INPUT type='hidden' name='pos_flag' value=<%=pos_flag %>>
<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b>手持设备读卡</b></font>
	</div>
</div>
<div style="margin-left:10px;margin-top:5px;" align="center">
	<div class="direct-panel">
		<fieldset style="height:362px;">
			<legend><b style="color: blue;">文件选取</b></legend>
			<table>
<tr><td><TD align="left"></TD><font color="red"><strong>证件识别返回结果文件选择 Result.txt</font></td></tr>
				<TR>
			      <TD><INPUT name="resultTxt" type="file" size="70"  value="" id="resultTxt" onchange="javascript:checkFileTxtType();"></TD>
	           </TR>
			</table>
<table>
<tr><td><TD align="left"></TD><font color="red"><strong>证件识别图片路径 HeadImage.jpg</font></td></tr>

	<TR>
			<TD><INPUT name="imageIdentification" type="file" size="70"  value="" id="imageIdentification" onchange="javascript:checkFileType();"></TD>
	</TR>
</table>
		</fieldset>

<button type="button"  class="xpbutton3" accessKey=n id="btnNext" name="btnNext" title="确认" onclick="javascript: SaveAction();">确认</button>
	&nbsp;&nbsp;&nbsp;	
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();">关闭 (<u>C</u>)</button>
	</div>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>