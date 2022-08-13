<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>基本信息管理</title>

<link rel="stylesheet" href="../css/bootstrap.css">
<!--[if lt IE 9]>
  <script src="../js/html5shiv.js"></script>
  <script src="../js/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" href="../css/app.css">	
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" >
<link href="../css/metro/blue/jtable.css" rel="stylesheet" type="text/css" >
<link href="../css/colorbox.css" rel="stylesheet" type="text/css">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery-ui.js"></script>
<script src="../js/jquery.jtable.js"></script>
<script src="../js/jquery.jtable.zh-CN.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/bootstrap-paginator.min.js"></script>
<script src="../js/bootstrap.js"></script>
<script src="../js/url.min.js"></script>
<script src="../js/app.js"></script> 

</head>
<body>
	<jsp:include page="head.jsp" />
	<jsp:include page="left.jsp" />
<div class="main">
	<div class="listwrap">
		<div class="list_bar">基本信息管理</div>


		<div id="list-content" class="list-content clearfix">

				<div id="<s:property value="#teacherEx.teacher.id" />" class="list-item">
					<div class="list-item-hd">
						<table class="table table-bordered">
							<tbody>
								<tr>
									<td width="150px"><span class="text-primary">教师编号：<s:property value="teacherEx.teacher.id" /></span></td>
									<td width="240px"><span class="text-primary">身份证：<s:property value="teacherEx.teacher.idcard" /></span></td>
									<td width="200px"><span class="text-primary">姓名：</span><s:property value="teacherEx.teacher.name" /></td>
									<td width="auto"><span class="text-primary">所属学校：</span><span><s:property value="teacherEx.teacher.organization.name" /></span></td>
									<td width="150px"><span class="text-primary">手机：</span><span><s:property value="teacherEx.teacher.mobile" /></span></td>
									<td width="80px"><a class="ifrmbox" href="../teacher/tinfo_editInit.action?editId=<s:property value="teacherEx.teacher.id" />"data-fancybox-type="iframe">编辑</a></td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="list-item-bd clearfix">
						
						<div class="list-item-col list-5-05" style="width:100px;text-align:center">
							<s:if test='%{teacherEx.sexString=="女"}'><img src="../img/u2.png" width="80" ></s:if>
							<s:else><img src="../img/u1.png" width="80" ></s:else>
						</div>
						
						<div class="list-item-col list-5-05">
							<ul>
								<li><span class="text-primary">性别：</span><s:property value="teacherEx.sexString" /></li>
								<li><span class="text-primary">年龄：</span> <s:property value="teacherEx.ageString" /></li>
								<li><span class="text-primary">民族：</span> <s:property value="teacherEx.teacher.ethnic.name" /></li>
								<li><span class="text-primary">政治面貌：</span> <s:property value="teacherEx.teacher.politics.name" /></li>
								

							</ul>
						</div>
						<div class="list-item-col list-5-05">
							<ul>
								<li><span class="text-primary">教龄：</span> <s:property value="teacherEx.teachingAge" /></li>
								<li><span class="text-primary">职称：</span> <s:property value="teacherEx.teacher. jobTitle.name" /></li>
								<li><span class="text-primary">行政职务：</span> <s:property value="teacherEx.teacher.jobDuty.name" /></li>
								<li><span class="text-primary">是否双语教学：</span> <s:property value="teacherEx.isMultiLanguage" /></li>

							</ul>
						</div>
						<div class="list-item-col list-5-1">
							<ul>
								<li><span class="text-primary">主要教学学段：</span> <s:property value="teacherEx.mainTeachingClass.grade.name" /></li>
								<li><span class="text-primary">主要教学学科：</span> <s:property value="teacherEx.mainTeachingCourse.subject.name" /></li>
								<li><span class="text-primary">主要教学语言：</span> <s:property value="teacherEx.mainTeachingLanguage.language.name" /></li>
								<li><span class="text-primary">所属地区：</span><div onmouseout="this.className='longtxt'" onmouseover="this.className='longtxt longtxt_hover'" class="longtxt"><s:property value="teacherEx.areaString" /></div></li>

							</ul>
						</div>
						<div class="list-item-col list-5-1">
							<ul>
								<li><span class="text-primary">最高学历：</span> <s:property value="teacherEx.teacher.eductionBackground.name" /></li>
								<li><span class="text-primary">汉语言水平：</span> <s:property value="teacherEx.teacher.chineseLanguageLevel.name" /></li>

							</ul>
						</div>
						

					</div>
				</div>
		</div>

		<div id="pagination" style="float:right;" class="pull-right"></div>
	</div>

</div>
<script type="text/javascript">
$(function(){
	$(".ifrmbox").colorbox({
		iframe : true,
		width : "860px",
		height : "680px",
		maxWidth : '1600px',
		opacity : '0.5',
		overlayClose : false,
		escKey : true
	});
	
})
</script>

<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>