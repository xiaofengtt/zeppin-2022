
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
	//�ж��ַ���Ϊ�յĻ�����ֵΪ""
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
<title>��ѵƽ̨����̨����</title>
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
	<!-- start:ͼ������ -->
	<!-- end:ͼ������ -->
	<form name='courseInfo' action='courseImport.jsp' method='post' class='nomargin' onSubmit="return checkValues()"  enctype="multipart/form-data">
	<!-- start:��������1 -->
	<div unselectable="on" class="border">   
		<table width=60%  border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="3F6C61">
		<tr bgcolor="#FFFFFF">
			<td align='center' class="t12_14_bgE3EAE9" colspan="3">����SCORM1.2��μ�</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' class="t12_14_bgE3EAE9"><span>�μ���ţ�</span></td>
			<td class="t12_14_bgE3EAE9"><input class="xmlInput textInput" name="course_id" id="course_id" value="<%=courseware_id %>" readonly="readonly"></td>
			<td class="t12_14_bgE3EAE9">*����</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' class="t12_14_bgE3EAE9"><span>�μ����ƣ�</span></td>
			<td class="t12_14_bgE3EAE9"><input class="xmlInput textInput" name="course_title" id="course_id" value="<%=courseware_name %>" readonly="readonly"></td>
			<td class="t12_14_bgE3EAE9">*����</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' class="t12_14_bgE3EAE9"><span>Ҫ�����SCORMѹ������</span></td>
			<td class="t12_14_bgE3EAE9"><input type="File" name="coursezipfile" size="20" value="100"></td>
			<td class="t12_14_bgE3EAE9">*����</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' class="t12_14_bgE3EAE9"><span>�γ̵Ŀ��Ʒ�ʽ��</span></td>
			<td class="t12_14_bgE3EAE9">
			<!-- ˳�����&nbsp;&nbsp;&nbsp;&nbsp;
		            <input type="radio" name="controltype" value="flow" checked><br> -->
		            <input type="radio" name="controltype" value="choice" checked>&nbsp;&nbsp;���ɷ���
		            </td>
			<td class="t12_14_bgE3EAE9">*����</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td align='left' class="t12_14_bgE3EAE9"><span>�μ�������ʽ��</span></td>
			<td class="t12_14_bgE3EAE9">
			<input type="radio" name="navigate" value="<%=ScormConstant.SCORM_NAVIGATE_PLATFORM %>" checked>&nbsp;&nbsp;ƽ̨����&nbsp;&nbsp;&nbsp;&nbsp;
		           <input type="radio" name="navigate" value="<%=ScormConstant.SCORM_NAVIGATE_COURSEWARE %>"> �μ�����&nbsp;&nbsp;</td>
		            
			<td class="t12_14_bgE3EAE9">*����</td>
		</tr>
		
		<%
		if( error != null) 
		{
		 %>
		<tr bgcolor="#FFFFFF">
			<td align='left' colspan='4' class="t12_14_bgE3EAE9"><span style="color:red">����γ̳���<%=error %></span></td>
			 
		</tr>
		<%} %>
		
		<tr bgcolor="#FFFFFF">
		<td colspan="3" align="center" class="t12_14_bgE3EAE9">
		 <input name="ok" value="�� ��" type="submit" class="dialogbutton" onmouseover="this.className='dialogbuttonOver'" onmouseout="this.className='dialogbutton'">
		
    <input name="cancel" type="button" value="ȡ ��" onclick="history.back()" class="dialogbutton" onmouseover="this.className='dialogbuttonOver'" onmouseout="this.className='dialogbutton'">
		</td>
		</tr>
		</table>
	</div>
	<!-- end:��������1 -->
	<input type=hidden name="theManifest">
   	<input type=hidden name="theZipFile">
	<!-- button row start -->
		<div class="dialogbuttons" unselectable="on">
		
   
		</div>
	<!-- button row end -->
	
</form>

	<!-- ����������� -->
</body>
</html>

