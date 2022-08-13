<jsp:directive.page import="com.whaty.platform.sso.web.servlet.UserSession"/>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<html>
<head>
<script language="javascript" type="text/javascript"  src="/entity/student/js/js-menu.js" ></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>无标题文档</title>
<script type="text/JavaScript">
<!--

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}
//-->
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #E0F2FF;
}
-->
</style>
<link href="/entity/student/css/css-menu.css" rel="stylesheet" type="text/css">
<link href="/entity/student/css/admincss.css" rel="stylesheet" type="text/css">
</head>
<%
	UserSession userSession = (UserSession)session.getAttribute("user_session");
	
	if (userSession == null) {
%>
		<script>
			window.top.alert("登录超时，为了您的帐户安全，请重新登录。");
			window.top.location = "/";
		</script>
<%
		return;
	}
%>
<body onLoad="tempObj = document.getElementById('firBt');MM_preloadImages('images/images/t_over.jpg') " scroll="no">
<table width="179" height="100%" border="0" align="center" cellpadding="0" cellspacing="0" onselectstart="return false">
  <tr>
    <td align="center" valign="top" >
	<div id="firBt"  class="menuAOn" onMouseOver="over(this)" onMouseOut="out(this)" 
	 onclick="top.openPage('/entity/workspaceStudent/stuPeBulletinView_toBulletinView.action')"  >
	    <table width="179" height="29" border="0" cellspacing="0" cellpadding="0"   >
          <tr>
            <td  class="zjmenu031" >通知公告</td>
          </tr>
        </table>
	  </div>
	   <div class="menuA" onMouseOver="over(this)" onMouseOut="out(this)"   onclick="top.openPage('/entity/student/student_info_manage.jsp')">
	    <table width="179" height="29" border="0" cellspacing="0" cellpadding="0"  >
          <tr>
            <td  class="zjmenu031" >个人信息</td>
          </tr>
        </table>
	  </div>
	  
	  <div class="menuA" onMouseOver="over(this)" onMouseOut="out(this)"  onclick="top.openPage('/entity/workspaceStudent/studentElective_toProgram.action')">
	    <table width="179" height="29" border="0" cellspacing="0" cellpadding="0"  >
          <tr>
            <td  class="zjmenu031" >教学计划</td>
          </tr>
        </table>
	  </div>
	  
	  <div class="menuA" onMouseOver="over(this)" onMouseOut="out(this)"  onclick="top.openPage('/entity/workspaceStudent/studentElective_main.action')">
	    <table width="179" height="29" border="0" cellspacing="0" cellpadding="0"  >
          <tr>
            <td  class="zjmenu031" >选课</td>
          </tr>
        </table>
	  </div>
	  
	   <div class="menuA" onMouseOver="over(this)" onMouseOut="out(this)"  onclick="top.openPage('/entity/workspaceStudent/student_toLearningCourses.action')">
	    <table width="179" height="29" border="0" cellspacing="0" cellpadding="0"  >
          <tr>
            <td  class="zjmenu031" >在学课程</td>
          </tr>
        </table>
	  </div>
	  <div class="menuA" onMouseOver="over(this)" onMouseOut="out(this)"  onclick="top.openPage('/entity/student/exam_reserver.jsp')">
	    <table width="179" height="29" border="0" cellspacing="0" cellpadding="0"  >
          <tr>
            <td  class="zjmenu031" >考试预约</td>
          </tr>
        </table>
	  </div>
	  <div class="menuA" onMouseOver="over(this)" onMouseOut="out(this)"  onclick="top.openPage('/entity/workspaceStudent/prExamDetail_toExamDetail.action')">
	    <table width="179" height="29" border="0" cellspacing="0" cellpadding="0"  >
          <tr>
            <td  class="zjmenu031" >考试信息</td>
          </tr>
        </table>
	  </div>
	  <div class="menuA" onMouseOver="over(this)" onMouseOut="out(this)"  onclick="top.openPage('/entity/student/score_manage.jsp')">
	    <table width="179" height="29" border="0" cellspacing="0" cellpadding="0"  >
          <tr>
            <td  class="zjmenu031" >成绩查询</td>
          </tr>
        </table>
	  </div>
	  
	  <s:if test="edutype.equals('zsb')">
	  
	  <div class="menuA" onMouseOver="over(this)" onMouseOut="out(this)"  onclick="top.openPage('/entity/workspaceStudent/student_toPaperOperate.action')">
	    <table width="179" height="29" border="0" cellspacing="0" cellpadding="0"  >
          <tr>
            <td  class="zjmenu031" >毕业论文</td>
          </tr>
        </table>
	  </div>
	  
	  </s:if>
	  
	  <div class="menuA" onMouseOver="over(this)" onMouseOut="out(this)"  onclick="top.openPage('/entity/workspaceStudent/studentFee_toDetail.action')">
	    <table width="179" height="29" border="0" cellspacing="0" cellpadding="0"  >
          <tr>
            <td  class="zjmenu031" >费用查询</td>
          </tr>
        </table>
	  </div>
	  
	  <div class="menuA" onMouseOver="over(this)" onMouseOut="out(this)"  onclick="top.openPage('/entity/student/apply_manage.jsp')">
	    <table width="179" border="0" cellspacing="0" cellpadding="0"  >
          <tr>
            <td  class="zjmenu031" >申请管理</td>
          </tr>
        </table>
	  </div>
	  <div class="menuA" onMouseOver="over(this)" onMouseOut="out(this)"  onclick="top.openPage('/entity/function/intoBBS.jsp')">
	    <table width="179" height="29" border="0" cellspacing="0" cellpadding="0"  >
          <tr>
            <td  class="zjmenu031" >公共论坛</td>
          </tr>
        </table>
	  </div>
	   <div class="menuA" onMouseOver="over(this)" onMouseOut="out(this)"  onclick="top.openPage('/entity/teacher/problems.jsp')">
	    <table width="179"  height="29" border="0" cellspacing="0" cellpadding="0"  >
          <tr>
            <td  class="zjmenu031" >使用帮助</td>
          </tr>
        </table>
	  </div>
	  
	<!--------------- -----------------------></td>
  </tr>
</table>
</body>
</html>
