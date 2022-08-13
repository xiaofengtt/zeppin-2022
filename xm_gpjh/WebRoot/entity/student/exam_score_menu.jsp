<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>    
<% response.setHeader("expires", "0"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<link href="/entity/manager/pub/images/layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>
		<div id="main_content">
			<div id="tab_bg">
				<div id="0110" class="tab_menu2" onClick="parent.openPage('/entity/workspaceStudent/peLearningCourseScore_toOldCourseScore.action',this.id)" title="已修在修课程">已修在修课程</div>
				<s:if test="edutype=='edutype'">
				<div id="0111" class="tab_menu2" onClick="parent.openPage('/entity/workspaceStudent/peLearningCourseScore_toOtherCourseScore.action',this.id)" title="其他课程查询">其他课程查询</div>
				</s:if>
				<script>parent.openPage('/entity/workspaceStudent/peLearningCourseScore_toOldCourseScore.action','0110');</script>
			</div>
		</div>
	</td>
  </tr>
</table>
</body>
</html>