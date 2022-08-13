<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String savepath = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>提交任务资料</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
</head>  
  <script type="text/javascript">
  
  function checkall(){
		document.getElementById('jobResource').submit();return false;
	if(document.getElementById('upload').value=='' ){
		alert("对不起，任务资料不能为空！");
		flag=false;
		
	}
	if(document.getElementById('reply').value=='' ){
		alert("对不起，提交任务回复不能为空！");
		flag=false;
	}
	if(document.getElementById('reply').value.length>2000){
		alert("对不起，回复内容过多！");
		flag=false;
	}
		document.getElementById('jobResource').submit();
	}
</script>
<body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">
<div id="main_content">
    <div class="content_title">提交任务资料</div>
    <div class="cntent_k">
   	  <div class="k_cc">
<form id='jobResource' action="/entity/information/prJobUnitInit_uploadResource.action" method="post"  enctype="multipart/form-data">
<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
			     <tr>
			       <td colspan="3" height="100" align="center" valign="middle" >&nbsp;<input type="hidden" name="bean.id" value="<s:property value='bean.id'/>"/></td>
			     </tr>
			     <tr valign="middle"> 
			       <td width="200" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">提交任务资料*：</span></td>
			       <td class="postFormBox" style="padding-left:10px"><input type="file" id='upload' name="upload1" /></td>
			     </tr>
				<tr valign="middle"> 
				  <td width="200" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">提交任务回复*：</span></td>
				  <td class="postFormBox" style="padding-left:10px"><textarea name="reply" id="reply" cols="40" rows="5"></textarea></td>
				</tr>
				<tr>
				  <td height="50" align="center" colspan="2">
				  <input type="button" value="提交" onClick="checkall()"/></td>
				</tr>
</table>
</form>
	  </div>
    </div>
</div>
<div class="clear"></div>
<s:if test="msg!=null">
<script>
alert('<s:property value="msg"/>');
</script>
</s:if>
</body>
</html>