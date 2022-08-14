<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.system.*,enfo.crm.dao.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
	String id = Utility.trimNull(request.getParameter("id"));
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
</HEAD>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>

<script language=javascript>

function viewConfirm(){
	var preview_code = document.theform.preview_code.value;
	var id = document.theform.id.value;
	if(preview_code == ''){
		alert('请选择预览类别！');
		return false;
	}
	if(preview_code == '1'){
		if(showModalDialog('/system/config/commonlist_preview.jsp?catalog_id='+id, '', 'dialogWidth:1200px;dialogHeight:600px;status:0;help:0') == 1)
		{
			window.close();
			window.returnValue = 1;
		}
	}else{
		if(showModalDialog('/system/config/commondetail_preview.jsp?catalog_id='+id+'&preview_code='+preview_code, '', 'dialogWidth:960px;dialogHeight:600px;status:0;help:0') == 1)
		{
			window.close();
			window.returnValue = 1;
		}
	}
	
}

</script>
<BODY class="BODY" >
<form name="theform" method="POST" action="">
<input type="hidden" name="id" value="<%=id%>">
<table border="0" width="100%" align="center" cellspacing="0" cellpadding="0">
<tr>
	<td>
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="0">  				
		<tr>
			<td>请选择预览类别：</td>
		</tr>
	</table> 
	</td>
</tr>
	<!-- 要加入的查询内容 -->
<tr>
	<td>
	<table border="0" width="100%" cellspacing="0" cellpadding="0">
	
		<tr>
			<td height="10"></td>
		</tr>
		<tr>
			<td align="left">预览类别:</td>
			<td>
				<SELECT size="1" name="preview_code" style="width: 120px" onkeydown="javascript:nextKeyPress(this)">
					<option value="" >请选择</option>
					<option value="1" >表格列表预览</option>
					<option value="2" >新增详情预览</option>
					<option value="3" >修改详情预览</option>
				</SELECT>
			</td>
		</tr>
		<tr>
			<td height="25"></td>
		</tr>
		<tr>
			<td align="right" colspan="2">
				<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:viewConfirm();">确定(<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
				&nbsp;&nbsp;
				<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">返回(<u>C</u>)</button>
			</td>
		</tr>			
	</table>
	</td>
</tr>
</table>
</form>
<%@ include file="/includes/foot.inc"%>	
</BODY>
</HTML>

