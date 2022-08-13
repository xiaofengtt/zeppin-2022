<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>主干课成绩复查申请</title>
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
<script>
 function check(){
   var course=document.getElementById('courseId').value;
   if(course==''){
	alert('没有选择课程,无法提交！');
	return false;
 	}
	 return true;
 }

</script>
</head>
<body>
<form id="changeform" name="changeform" action="/entity/workspaceStudent/mainCourseScoreChangeApply_maincourseScoreChangeApply.action" method="post" onsubmit="return check();">
<input type="hidden" id="changeValue" name="changeValue"/>
<input type="hidden" id="changeType" name="changeType"/>
<div id="main_content">
	<div class="content_title">主干课成绩复查申请</div>
<div class="student_cntent_k">
    	<div class="k_cc">
<table width="100%" border="0" align="center" cellpadding="5" cellspacing="0" >
	<tr> 
      <td height="17" class="td"><font color="#000000">主干课成绩复查申请</font>		
	  </td>
    </tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr class="table_bg1">
		<td height=25px  align=right width="30%" >请选择课程:</td>
		<td height=25px  align="right" width="70%" >
			<div align="left">
			<select name="courseId" id="courseId">
			<s:if test="courseList.size > 0">
			<s:iterator value="courseList">
			<option value="<s:property value="prExamOpenMaincourse.peTchCourse.id"/>"><s:property value="prExamOpenMaincourse.peTchCourse.name"/></option>
			</s:iterator>
			</s:if>
			<s:else>
			<option value="">无课程</option>
			</s:else>
			</select>
			</div>
		</td>
	</tr>
	<tr class="table_bg1">
		<td height=25px  align=right width="30%" >申请复查原因:</td>
		<td height=25px  align="right" width="70%" >
			<div align="left">
			<textarea cols="50" rows="5" name="reason" ></textarea>
			</div>
		</td>
	</tr>
	<tr class="table_bg1"> 
		<td height=25px  align=right width="30%" colspan="2">
			<div align="center">
			<s:if test="courseList.size > 0">
			<input type="submit" value="提交申请" />
			</s:if>
			</div>
		</td>
	</tr>
 
</table>
</div>
</div>
    <div class="student_cntent_k">
    	<div class="k_cc">

<table width="100%" border="0" align="center" cellpadding="5" cellspacing="0">
	<tr class="table_bg1">
		<td width="15%">申请时间</td>
		<td width="20%">申请的课程</td>
		<td width="35%">申请的原因</td>
		<td width="15%">审核状态</td>
		<td width="15%">审核时间</td>
	</tr>
	<s:iterator value="applys">
	<tr>
	<s:set name="normal" value="applyNote.substring(applyNote.indexOf('|')+1,applyNote.length())"/>

		<td><s:date name="applyDate" format="yyyy-MM-dd HH：mm"/>&nbsp;</td>
		<td><s:property value="#normal.substring(0,#normal.indexOf('|'))"/>&nbsp;</td>
		<td><s:property value="#normal.substring(#normal.indexOf('|')+1,#normal.length())"/>&nbsp;</td>		
		<td><s:property value="enumConstByFlagApplyStatus.name"/>&nbsp;</td>
		<td><s:date name="checkDate" format="yyyy-MM-dd HH：mm"/>&nbsp;</td>
	</tr>	
	</s:iterator>

</table>

    </div>
    </div>
</div>
</form>
</body>
</html>
    