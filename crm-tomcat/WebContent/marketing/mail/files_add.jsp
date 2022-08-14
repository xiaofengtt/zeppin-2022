<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.io.*,enfo.crm.web.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//声明辅助变量
boolean sizeFlag = false;
SendMail2 mail = new SendMail2();
Vector fileList = SendMail2.file;
DocumentFile file = null;
String strFolder = "C:\\Temp\\" + input_operatorCode;

boolean bSuccess = false;
String title = Utility.trimNull(request.getParameter("title"));
if(title.equals("")){
	title = enfo.crm.tools.LocalUtilis.language("message.noThemeo",clientLocale);//无主题
}	

if(request.getMethod().equals("POST")){  	 
	 file = new DocumentFile(pageContext);
     file.parseRequest();
     
     if(file.getParameter("flag").equals("2")){		//删除所选附件
		String file_del = Utility.trimNull(file.getParameter("file_del"));  
		File file2 = new File(strFolder + java.io.File.separator + file_del);
		file2.delete();			
		mail.remobvefile(file_del);	
		 }	
		else{			
		if(file.smartUpload.getFiles().getFile(0).getSize()<5242880){
			mail.attachfile(file.smartUpload.getFiles().getFile(0).getFileName());		
			file.uploadFile(strFolder);		
		}
		else{
			sizeFlag = true;
		}
		
		if(!(file.getParameter("flag").equals("1"))){  //确定
			mail.remobvefile("");	
			bSuccess = true;		
		}					
	 }	 
}
%>
<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<title><%=LocalUtilis.language("menu.filesAdd",clientLocale)%> </title>
<!--添加附件-->

</HEAD>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language="javascript">
<%if (bSuccess)
{		
%>	
	window.returnValue = true;	
	window.close();	
<%}%>
function addFiles()
{	
	if(document.theform.file.value != ""){
		document.theform.flag.value = 1;	
		document.theform.submit();
	}else{
		sl_alert("<%=LocalUtilis.language("message.selectFileToUpload",clientLocale)%> ？");//请选择您要上传的文件
	}
}
function removeFile(file_del)
{   	
	document.theform.flag.value = 2;
	document.theform.file_del.value = file_del;	
	document.theform.submit();
}

window.onload = function(){
	var sizeFlag = document.getElementById("sizeFlag").value;
		
	if(sizeFlag=="true"){		
			sl_alert("<%=LocalUtilis.language("message.attachmentsAddError",clientLocale)%> 5M！");//添加的附件不能超过5M
	}
}
</script>
<BODY  class="body body-nox"leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" MARGINWIDTH="0" MARGINHEIGHT="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="files_add.jsp" ENCTYPE="multipart/form-data">
<!--修改成功标志-->
<input type="hidden" id="sizeFlag" value="<%=sizeFlag%>"/>
<input type="hidden" name="title" value="">
<input type="hidden" name="flag" value="">
<input type="hidden" name="file_del" value="">
<TABLE height="100%" cellSpacing=0 cellPadding=0 border=0 width="100%" >
	<TBODY>
		<TR>
			<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=4 align=center border=0 width="100%">
				<TBODY>
					<TR>
						<TD align=middle>
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td class="page-title"><font color="#215dc6"><b><%=LocalUtilis.language("menu.filesAdd",clientLocale)%> </b></font></td>
								<!--添加附件-->
							</tr>
							
						</table>
						<br/>
						<table border="0" width="100%" cellspacing="3">
							<tr>
								<td align="left"><%=LocalUtilis.language("menu.filesFind",clientLocale)%> :<input type="file" style="font-size: 9pt" name="file" size="40" onkeydown="javascript:return false;"></td>								
							</tr><!--查找文件-->
							<tr>
								<td align="left" width="100%" >（<%=LocalUtilis.language("message.attachmentsAddError",clientLocale)%> 5M）</td><!--添加的附件不能超过5M-->								
							</tr>
							<tr>
								<td align="left" width="100%" ><%=LocalUtilis.language("message.filesInThisMail",clientLocale)%> :</td><!--邮件当前的附件-->								
							</tr>
							<tr>
								<td width="100%">
								<table id="table3" border="0" cellspacing="1" cellpadding="2" bgcolor="#000000" width="100%">
									<tr class="trh">
										<td align="center" height="25" width="70%"><%=LocalUtilis.language("class.fileName",clientLocale)%> </td><!--附件名称-->
										<td align="center" height="25" width="30%"><%=LocalUtilis.language("message.delete",clientLocale)%> </td><!--删除-->						
									</tr>
									<%
									for (int i = 0; i < fileList.size(); i++)
									{
										String file_name = (String)fileList.get(i);	
    									if (file_name != null)
    									{
    	
									%>																	
									<tr class="tr1">
										<td class="tdh" align="center" height="25" width="70%"><%=file_name%></td>
										<td align="center" height="25" width="30%">
										<a href="#" onclick="javascript:removeFile('<%=file_name%>');">
											<img src="<%=request.getContextPath()%>/images/recycle.gif" height="16" width="16" alt="<%=LocalUtilis.language("message.delete",clientLocale)%> "/>
											<!--删除-->
										</a>								
										</td>
									</tr>
									<%
										}
									}
									%>																										
								</table>		
								</td>								
							</tr>							
						</table>
						<br>
						<table border="0" width="100%">
							<tr>
								<td align="right">	
								<button type="button"  class="xpbutton6" accessKey=v onclick="javascript:addFiles();"><%=LocalUtilis.language("message.confirm&addNext",clientLocale)%> (<u>V</u>)</button>
								&nbsp;&nbsp;<!--确定并添加下一个-->							
								<button type="button"  class="xpbutton3" accessKey=s onclick="javascript:document.theform.submit();"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>S</u>)</button>
								&nbsp;&nbsp;<!--确定-->
								<button type="button"  class="xpbutton3" accessKey=c onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>
								&nbsp;&nbsp;<!--关闭-->
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

