<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>教学计划</title>
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
<script>
function check(){
var num = document.getElementById("note");
if (num.lenght>500){
  alert('备注必须在500字以内');
  return false;
  }else 
  return true;
}
</script>
</head>

<body>
<form action="/entity/workspaceStudent/uniteExamAvoid_apply.action" onsubmit="return check();">
<input type="hidden" name="bean.id" value="<s:property value="bean.id" />" />
<div id="main_content">
	<div class="content_title">统考计算机免试申请</div>
	<input type="hidden" name="type" value="<s:property value="type"/>" />
<div class="student_cntent_k">
    	<div class="k_cc">
<table width="100%" border="0" align="center" cellpadding="5" cellspacing="0" >
	<tr> 
      <td height="17" class="td"><font color="#000000">---免试申请</font>
	  </td>
    </tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="table_bg1"> 
		<td height=25px  align=right width="30%" colspan="2">
			<div align="center">
			<table width="80%" border="0" cellpadding="0" cellspacing="0">
			<tr>
			<td width="30%">
			请填写申请说明:</br>
			(500字以内)</td>
			<td>
			<textarea name="note" id="note" cols="50" rows="5"  ></textarea>
			</td>
			</tr>
			</table>
			<table width="80%" border="0" cellpadding="0" cellspacing="0">
			<tr> 
			<td>
			<input TYPE='submit' value="提交申请" />&nbsp; <input TYPE="button" value="返回" onclick="javascript:history.back();" /> 
			</td>
			</tr>
			</table>
			</div>
		</td>
	</tr>
</table>

    </div>
    </div>
</div>
</form>
</body>
</html>
        