<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>期末成绩复查申请</title>
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
<form id="changeform" name="changeform" action="/entity/workspaceStudent/examScoreChangeApply_examScoreChangeApply.action" method="post" onsubmit="return check();">
<input type="hidden" id="changeValue" name="changeValue"/>
<input type="hidden" id="changeType" name="changeType"/>
<div id="main_content">
	<div class="content_title">期末考试成绩复查申请</div>
<div class="student_cntent_k">
    	<div class="k_cc">
<table width="100%" border="0" align="center" cellpadding="5" cellspacing="0" >
	<tr> 
      <td height="17" class="td"><font color="#000000">期末考试成绩复查申请</font>		
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
			<option value="<s:property value="id"/>"><s:property value="prTchStuElective.prTchOpencourse.peTchCourse.name"/></option>
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
		<td height=25px  align=right width="30%" >请选择类型:</td>
		<td height=25px  align="right" width="70%" >
			<div align="left">
			<select name="type">
			<option value="normal">平时成绩</option>
			<option value="work">作业成绩</option>
			<option value="exam">考试成绩</option>
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
			<s:if test="apply==true">
			<input type="submit" value="提交申请" />
			</s:if>
			<s:else>
			不在成绩复查申请操作时间内。申请操作时间是在成绩发布一周之内。
			</s:else>
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
		<td width="20%">申请类型</td>
		<td width="15%">申请时间</td>
		<td width="15%">申请的课程</td>
		<td width="35%">申请的原因</td>
		<td width="15%">审核状态</td>
	</tr>
	<s:iterator value="normalapplys">
	<tr>
	<s:set name="normal" value="applyNote.substring(applyNote.indexOf('|')+1,applyNote.length())"/>
		<td><s:property value="enumConstByApplyType.name"/>&nbsp;</td>
		<td><s:date name="applyDate" format="yyyy-MM-dd HH：mm"/>&nbsp;</td>
		<td><s:property value="#normal.substring(0,#normal.indexOf('|'))"/>&nbsp;</td>
		<td><s:property value="#normal.substring(#normal.indexOf('|')+1,#normal.length())"/>&nbsp;</td>		
		<td><s:property value="enumConstByFlagApplyStatus.name"/>&nbsp;</td>
	</tr>	
	</s:iterator>
	<s:iterator value="workapplys">
	<tr>
	<s:set name="work" value="applyNote.substring(applyNote.indexOf('|')+1,applyNote.length())"/>
		<td><s:property value="enumConstByApplyType.name"/>&nbsp;</td>
		<td><s:date name="applyDate" format="yyyy-MM-dd HH：mm"/>&nbsp;</td>
		<td><s:property value="#work.substring(0,#work.indexOf('|'))"/>&nbsp;</td>
		<td><s:property value="#work.substring(#work.indexOf('|')+1,#work.length())"/>&nbsp;</td>		
		<td><s:property value="enumConstByFlagApplyStatus.name"/>&nbsp;</td>
	</tr>	
	</s:iterator>
	<s:iterator value="examapplys">
	<tr>
	<s:set name="exam" value="applyNote.substring(applyNote.indexOf('|')+1,applyNote.length())"/>
		<td><s:property value="enumConstByApplyType.name"/>&nbsp;</td>
		<td><s:date name="applyDate" format="yyyy-MM-dd HH：mm"/>&nbsp;</td>
		<td><s:property value="#exam.substring(0,#exam.indexOf('|'))"/>&nbsp;</td>
		<td><s:property value="#exam.substring(#exam.indexOf('|')+1,#exam.length())"/>&nbsp;</td>		
		<td><s:property value="enumConstByFlagApplyStatus.name"/>&nbsp;</td>
	</tr>	
	</s:iterator>		
</table>

    </div>
    </div>
</div>
</form>
</body>
</html>
    