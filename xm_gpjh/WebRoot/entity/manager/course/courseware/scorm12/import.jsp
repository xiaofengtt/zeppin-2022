<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-tags.tld" prefix="s" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课件导入</title>
<link href="/entity/function/style.css" rel="stylesheet" type="text/css">
<script>
function checkValues()
   {
      if ( courseInfo.upload.value == "") 
      {
         alert( "请浏览输入要上传的课件压缩包！" );
         return false;
      }
      return true;
   }
</script>
</head>
<body leftmargin="0" rightmargin="0" style="background-color:transparent;" class="scllbar">
	<!-- start:图标区域 -->
	<!-- end:图标区域 -->
	<form name='courseInfo' action="/entity/teaching/peTchCoursewareAction_processupload.action" method='post' class='nomargin' onSubmit="return checkValues()"  enctype="multipart/form-data">
	<!-- start:内容区域1 -->
	<div unselectable="on" class="border">   
		<table width=60%  border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="3F6C61">
		<tr bgcolor="#FFFFFF">
			<td align='center' class="t12_14_bgE3EAE9" colspan="3">导入SCORM1.2版课件</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' class="t12_14_bgE3EAE9"><span>课件编号：</span></td>
			<td class="t12_14_bgE3EAE9"><input class="xmlInput textInput" name="course_code" id="course_code" value="<s:property value='allobjectlist.get(0).code'/>" readonly="readonly"></td>
			<td class="t12_14_bgE3EAE9">*必填</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' class="t12_14_bgE3EAE9"><span>课件名称：</span><input type="hidden" value="<s:property value='allobjectlist.get(0).id'/>"  name="course_id"/></td>
			<td class="t12_14_bgE3EAE9"><input class="xmlInput textInput" name="course_name" id="course_name" value="<s:property value='allobjectlist.get(0).name'/>" readonly="readonly"></td>
			<td class="t12_14_bgE3EAE9">*必填</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' class="t12_14_bgE3EAE9"><span>要导入的SCORM压缩包：</span></td>
			<td class="t12_14_bgE3EAE9"><input type="file" name="upload" size="20" value="100"></td>
			<td class="t12_14_bgE3EAE9">*必填</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' class="t12_14_bgE3EAE9"><span>课程的控制方式：</span></td>
			<td class="t12_14_bgE3EAE9">
			<!-- 顺序访问&nbsp;&nbsp;&nbsp;&nbsp;
		            <input type="radio" name="controltype" value="flow" checked><br> -->
		            <input type="radio" name="controltype" value="choice" checked>&nbsp;&nbsp;自由访问
		            </td>
			<td class="t12_14_bgE3EAE9">*必填</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' class="t12_14_bgE3EAE9"><span>课件导航方式：</span></td>
			<td class="t12_14_bgE3EAE9">
			<input type="radio" name="navigate" value="platform_nav" checked>&nbsp;&nbsp;平台导航&nbsp;&nbsp;&nbsp;&nbsp;
		           <input type="radio" name="navigate" value="courseware_nav"> 课件导航&nbsp;&nbsp;</td>
		            
			<td class="t12_14_bgE3EAE9">*必填</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' class="t12_14_bgE3EAE9"><span>课件制造商：</span></td>
			<td class="t12_14_bgE3EAE9">
			<input type="radio" name="manufacturer" value="whaty" checked>&nbsp;&nbsp;网梯课件&nbsp;&nbsp;&nbsp;&nbsp;
		           <input type="radio" name="manufacturer" value="guodian"> 国电课件&nbsp;&nbsp;</td>
		            
			<td class="t12_14_bgE3EAE9">*必填</td>
		</tr>
		<tr bgcolor="#FFFFFF">
		<td colspan="3" align="center" class="t12_14_bgE3EAE9">
		 <input name="ok" value="提 交" type="submit" class="dialogbutton" onmouseover="this.className='dialogbuttonOver'" onmouseout="this.className='dialogbutton'">
		
    <input name="cancel" type="button" value="关 闭" onclick="javascript:window.close();" class="dialogbutton" onmouseover="this.className='dialogbuttonOver'" onmouseout="this.className='dialogbutton'">
		</td>
		</tr>
		</table>
	</div>
	<!-- end:内容区域1 -->
	<!-- button row start -->
		<div class="dialogbuttons" unselectable="on">
		</div>
	<!-- button row end -->
</form>
</body>
</html>

