<%		/*---------------------------------------------
		 Function Description:	
		 Editing Time:	
		 Editor: chenjian
		 Target File:	
		 	 
		 Revise Log
		 Revise Time:  Revise Content:   Reviser:
		 -----------------------------------------------*/
%>
<%@ page language="java"  pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=GBK"%>
<%

	String courseId=(String)session.getAttribute("courseId");
	TrainingFactory factory=TrainingFactory.getInstance();
	TrainingManage trainingManage=factory.creatTrainingManage(includePriv);
	TrainingCourse course=trainingManage.getTrainingCourse(courseId);
	if(course.getCoursewareType()!=null  && course.getCoursewareType().equals(TrainingCoursewareType.AICC))
	{
	%>
	<script>
	alert("�ÿγ��Ѿ��пμ��������鿴......");
	window.location="courseware/aicc/coursewareInfo.jsp?courseId=<%=courseId%>";
	</script>
	<%	
	}
	else if(course.getCoursewareType()!=null  && course.getCoursewareType().equals(TrainingCoursewareType.SCORM12))
	{
	%>
	<script>
	alert("�ÿγ��Ѿ��пμ��������鿴......");
	window.location="courseware/scorm12/coursewareInfo.jsp?courseId=<%=courseId%>";
	</script>
	<%	
	}
	else if(course.getCoursewareType()!=null  && course.getCoursewareType().equals(TrainingCoursewareType.WHATYPRO))
	{
	%>
	<script>
	alert("�ÿγ��Ѿ��пμ��������鿴......");
	window.location="courseware/whatypro/coursewareInfo.jsp?courseId=<%=courseId%>";
	</script>
	<%	
	}
	else if(course.getCoursewareType()!=null  && course.getCoursewareType().equals(TrainingCoursewareType.WHATYBASIC))
	{
	%>
	<script>
	alert("�ÿγ��Ѿ��пμ��������鿴......");
	window.location="courseware/whatybasic/coursewareInfo.jsp?courseId=<%=courseId%>";
	</script>
	<%	
	}
	else if(course.getCoursewareType()!=null  && course.getCoursewareType().equals(TrainingCoursewareType.TIME))
	{
	%>
	<script>
	alert("�ÿγ��Ѿ��пμ��������鿴......");
	window.location="courseware/time/coursewareInfo.jsp?courseId=<%=courseId%>"; 
	</script>
	<%	
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>��ѵƽ̨����̨����</title>
<link href="../css/adminMain.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="../js/frame.js"></script>
</head>

<body leftmargin="0" rightmargin="0" style="background-color:transparent;" class="scllbar">
	<!-- start:ͼ������ -->
	<%@include file="pub/courseMainIcons.jsp"%>
	<!-- end:ͼ������ -->
	<form name='user-form' action='courseware/selectCoursewareType.jsp' method='post' class='nomargin' onsubmit="">
	<input type="hidden" name="courseId" value="<%=courseId%>">
	<!-- start:��������1 -->
	<div unselectable="on" class="border">   
		<div unselectable="on" style="position:relative"><span unselectable="on" class="header">����μ�</span></div>
		<table width='100%' border="0" cellpadding="0" cellspacing='0'  style="padding-left:8px;padding-right:8px">
		
		<tr>
			<td align='left' class="xmlLabel"><span>Ҫ����Ŀμ����ͣ�</span></td>
			<td width="18"><img style="cursor:hand" src="../images/buttons/help.png" alt=""  width="16" height="16" border="0"  help="���û��ļ�������" onMouseOver="parent.SHI(this.help)" onMouseOut="parent.HHI()"></td>
			<td class="xmlTd"><select name="coursewareType" class="selectM">
<%--			<option value="<%=TrainingCoursewareType.AICC %>"><%=TrainingCoursewareType.typeShow(TrainingCoursewareType.AICC)%>--%>
	        <option value="<%=TrainingCoursewareType.SCORM12 %>"><%=TrainingCoursewareType.typeShow(TrainingCoursewareType.SCORM12)%>
	        <!-- <option value="<%=TrainingCoursewareType.WHATYBASIC %>"><%=TrainingCoursewareType.typeShow(TrainingCoursewareType.WHATYBASIC)%>
	        <option value="<%=TrainingCoursewareType.WHATYPRO %>"><%=TrainingCoursewareType.typeShow(TrainingCoursewareType.WHATYPRO)%>
	         -->
	         <option value="<%=TrainingCoursewareType.TIME %>"><%=TrainingCoursewareType.typeShow(TrainingCoursewareType.TIME)%>
	        </select></td>
			<td class="xmlLabelT">*����</td>
		</tr>
		</table>
	</div>
	<!-- end:��������1 -->
	
	<!-- button row start -->
		<div class="dialogbuttons" unselectable="on">
		
    <input name="ok" value="�� ��" type="submit" class="dialogbutton" onmouseover="this.className='dialogbuttonOver'" onmouseout="this.className='dialogbutton'">
		
    </div>
	<!-- button row end -->
</form>

	<!-- ����������� -->
</body>
</html>

