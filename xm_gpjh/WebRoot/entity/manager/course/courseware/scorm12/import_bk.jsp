
<%@page import="com.whaty.platform.standard.scorm.util.ScormConstant"%><%		/*---------------------------------------------
		 Function Description:	
		 Editing Time:	
		 Editor: chenjian
		 Target File:	
		 	 
		 Revise Log
		 Revise Time:  Revise Content:   Reviser:
		 -----------------------------------------------*/
%>
<%@ page language="java"  pageEncoding="gb2312"%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@page import = "java.util.*,com.whaty.platform.training.*"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%@ include file="./readerror.jsp"%>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>培训平台－后台管理</title>
<link href="<%=request.getContextPath()%>/work/manager/css/style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="../../../js/frame.js"></script>
<script>
function checkValues()
   {
      if ( courseInfo.title.value == "" || 
           courseInfo.coursezipfile.value == "" ) 
      {
         alert( "You must enter a value for all items" );
         return false;
      }
      
      courseInfo.theZipFile.value = courseInfo.coursezipfile.value;
      return true;
   }
</script>
</head>
<%
	String courseware_id=fixnull(request.getParameter("courseware_id"));
	String courseware_name=fixnull(request.getParameter("courseware_name"));
	dbpool db = new dbpool();
	MyResultSet rs = null;
	String sql="select name from pe_bzz_tch_courseware where code='"+courseware_id+"'";
	rs=db.executeQuery(sql);
	if(rs!=null && rs.next())
	{
		courseware_name=fixnull(rs.getString("name"));
	}
	db.close(rs);
 %>
<body leftmargin="0" rightmargin="0" style="background-color:transparent;" class="scllbar">
	<!-- start:图标区域 -->
	<!-- end:图标区域 -->
	<form name='courseInfo' action='courseImport.jsp' method='post' class='nomargin' onSubmit="return checkValues()"  enctype="multipart/form-data">
	<!-- start:内容区域1 -->
	<div unselectable="on" class="border">   
		<table width=60%  border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="3F6C61">
		<tr bgcolor="#FFFFFF">
			<td align='center' class="t12_14_bgE3EAE9" colspan="3">导入SCORM1.2版课件</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' class="t12_14_bgE3EAE9"><span>课件编号：</span></td>
			<td class="t12_14_bgE3EAE9"><input class="xmlInput textInput" name="course_id" id="course_id" value="<%=courseware_id %>" readonly="readonly"></td>
			<td class="t12_14_bgE3EAE9">*必填</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' class="t12_14_bgE3EAE9"><span>课件名称：</span></td>
			<td class="t12_14_bgE3EAE9"><input class="xmlInput textInput" name="course_title" id="course_id" value="<%=courseware_name %>" readonly="readonly"></td>
			<td class="t12_14_bgE3EAE9">*必填</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' class="t12_14_bgE3EAE9"><span>要导入的SCORM压缩包：</span></td>
			<td class="t12_14_bgE3EAE9"><input type="File" name="coursezipfile" size="20" value="100"></td>
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
			<input type="radio" name="navigate" value="<%=ScormConstant.SCORM_NAVIGATE_PLATFORM %>" checked>&nbsp;&nbsp;平台导航&nbsp;&nbsp;&nbsp;&nbsp;
		           <input type="radio" name="navigate" value="<%=ScormConstant.SCORM_NAVIGATE_COURSEWARE %>"> 课件导航&nbsp;&nbsp;</td>
		            
			<td class="t12_14_bgE3EAE9">*必填</td>
		</tr>
		
		<%
		if( error != null) 
		{
		 %>
		<tr bgcolor="#FFFFFF">
			<td align='left' colspan='4' class="t12_14_bgE3EAE9"><span style="color:red">导入课程出错：<%=error %></span></td>
			 
		</tr>
		<%} %>
		
		<tr bgcolor="#FFFFFF">
		<td colspan="3" align="center" class="t12_14_bgE3EAE9">
		 <input name="ok" value="提 交" type="submit" class="dialogbutton" onmouseover="this.className='dialogbuttonOver'" onmouseout="this.className='dialogbutton'">
		
    <input name="cancel" type="button" value="取 消" onclick="history.back()" class="dialogbutton" onmouseover="this.className='dialogbuttonOver'" onmouseout="this.className='dialogbutton'">
		</td>
		</tr>
		</table>
	</div>
	<!-- end:内容区域1 -->
	<input type=hidden name="theManifest">
   	<input type=hidden name="theZipFile">
	<!-- button row start -->
		<div class="dialogbuttons" unselectable="on">
		
   
		</div>
	<!-- button row end -->
	
</form>

	<!-- 内容区域结束 -->
</body>
</html>

