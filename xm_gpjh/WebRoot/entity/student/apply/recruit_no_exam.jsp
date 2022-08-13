<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>统考免试申请</title>
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
<div id="main_content">
	<div class="content_title">统考免试申请</div>
<div class="student_cntent_k">
    	<div class="k_cc">
<s:if test="uniteCourse.size>0">
<table width="100%" border="0" align="center" cellpadding="5" cellspacing="0" >
	<tr> 
      <td height="17" class="td"><font color="#000000">统考免试申请</font>
	  </td>
    </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="5" cellspacing="0" >
			<div align="center">
					<s:select list="uniteCourse" name="type" label="申请的课程"></s:select>
		</div>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">

	<tr class="table_bg1"> 
		<td height=25px  align=right width="30%" colspan="2">
			<div align="center">
			<table width="80%" border="0" cellpadding="0" cellspacing="0">
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
			<input TYPE='submit' value="提交申请" />&nbsp;  
			</td>
			</tr>
			</table>
			</div>
		</td>
	</tr>
</table>
</s:if>
<s:else>
<table width="100%" border="0" align="center" cellpadding="5" cellspacing="0" >
	<tr> 
      <td height="17" class="td"><font color="#000000">您没有统考科目信息</font>
	  </td>
    </tr>
</table>
</s:else>
    </div>
    </div>
    <s:if test="applys.size() > 0 ">
    <div class="student_cntent_k">
    	<div class="k_cc">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="table_bg1"><td height=25px colspan="7">&nbsp;&nbsp; 已经提交的申请</td>
	</tr>

    <tr class="table_bg1"> 
   	 <td height=25px width="20%"  align=center>申请科目</td>
      <td height=25px width="15%"  align=center>申请时间</td>
      <td height=25px width="10%" align=center>审核状态</td>
      <td height=25px width="15%"  align=center>审核时间</td>
      <td height=25px width="40%" align=center>申请说明</td>
    </tr>
 <s:iterator value="applys" status="n">	
  <tr <s:if test="#n.index%2 == 0 ">class="table_bg2"</s:if> >
  	  <td height=25px width="20%" align=center><s:property value="applyNote"/>&nbsp;</td>
      <td height=25px width="15%"  align=center><s:date name="applyDate" format="yyyy-MM-dd"/>&nbsp;</td>
      <td height=25px width="10%" align=center><s:property value="enumConstByFlagApplyStatus.name"/>&nbsp;</td>
      <td height=25px width="15%"  align=center><s:date name="checkDate" format="yyyy-MM-dd"/>&nbsp;</td>
      <td height=25px width="40%" align=center><s:property value="applyInfo"/>&nbsp;</td>
  </tr>
   </s:iterator>        
</table>    
</div>
</div>  
</s:if>  
</div>
</form>
</body>
</html>
    