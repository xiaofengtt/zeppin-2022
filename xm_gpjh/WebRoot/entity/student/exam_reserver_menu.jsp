<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="../manager/pub/images/layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>
		<div id="main_content">
			<div id="tab_bg">
				<div id="0110" class="tab_menu2" onClick="parent.openPage('/entity/workspaceStudent/prExamReserver_courseList.action',this.id)" title="考试预约">考试预约</div>
				<div id="0111" class="tab_menu2" onClick="parent.openPage('/entity/workspaceStudent/prExamReserverView_courseList.action',this.id)" title="考试预约查询">考试预约查询</div>
				<s:if test="edutype=='edutype'">
				<div id="0112" class="tab_menu2" onClick="parent.openPage('/entity/workspaceStudent/mainCourseReserver_toReserver.action',this.id)" title="主干课预约">主干课预约</div>
				<div id="0113" class="tab_menu2" onClick="parent.openPage('/entity/workspaceStudent/mainCourseReserverView_viewMainCourse.action',this.id)" title="主干课查询">主干课查询</div>
				</s:if>
				<script>parent.openPage('/entity/workspaceStudent/prExamReserver_courseList.action','0110');</script>
			</div>
		</div>
	</td>
  </tr>
</table>
</body>
</html>