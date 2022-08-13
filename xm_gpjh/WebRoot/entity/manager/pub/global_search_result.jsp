<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<title>无标题文档</title>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
</head>
<!-- JavaScript functions -->
<body>
<!--内容区-->
<div id="main_content">
    <div class="content_title">站内搜索</div>
    <div class="cntent_k">
   	  <div class="k_cc">
	  	<p>共找到与"<s:property value="key"/>"有关的信息<s:property value="resultTotal"/>条，其中：</p>
	  	<hr />
	  	<s:if test="studentList.size() > 0">
	  		<p><strong>学生<s:property value="studentList.size()"/>条：</strong></p>
		  	<p>
		  		<s:iterator value="studentList">
		  			<s:property value="name"/>&nbsp;&nbsp;&nbsp;&nbsp;
		  		</s:iterator>
		  	</p>
		  	<p>&nbsp;</p>
	  	</s:if>
	  	<s:if test="teacherList.size() > 0">
	  		<p><strong>教师<s:property value="teacherList.size()"/>条：</strong></p>
		  	<p>
		  		<s:iterator value="teacherList">
		  			<s:property value="name"/>&nbsp;&nbsp;&nbsp;&nbsp;
		  		</s:iterator>
		  	</p>
		  	<p>&nbsp;</p>
	  	</s:if>
	  	<s:if test="managerList.size() > 0">
	  		<p><strong>管理员<s:property value="managerList.size()"/>条：</strong></p>
		  	<p>
		  		<s:iterator value="managerList">
		  			<s:property value="name"/>&nbsp;&nbsp;&nbsp;&nbsp;
		  		</s:iterator>
		  	</p>
		  	<p>&nbsp;</p>
	  	</s:if>
	  	<s:if test="sitemanagerList.size() > 0">
	  		<p><strong>信息采集人<s:property value="sitemanagerList.size()"/>条：</strong></p>
		  	<p>
		  		<s:iterator value="sitemanagerList">
		  			<s:property value="name"/>&nbsp;&nbsp;&nbsp;&nbsp;
		  		</s:iterator>
		  	</p>
		  	<p>&nbsp;</p>
	  	</s:if>
	  	<s:if test="siteList.size() > 0">
	  		<p><strong>学习中心<s:property value="siteList.size()"/>条：</strong></p>
		  	<p>
		  		<s:iterator value="siteList">
		  			<s:property value="name"/>&nbsp;&nbsp;&nbsp;&nbsp;
		  		</s:iterator>
		  	</p>
		  	<p>&nbsp;</p>
	  	</s:if>
	  	<s:if test="courseList.size() > 0">
	  		<p><strong>课程<s:property value="courseList.size()"/>条：</strong></p>
		  	<p>
		  		<s:iterator value="courseList">
		  			<s:property value="name"/>&nbsp;&nbsp;&nbsp;&nbsp;
		  		</s:iterator>
		  	</p>
		  	<p>&nbsp;</p>
	  	</s:if>
	  	<s:if test="bookList.size() > 0">
	  		<p><strong>教材<s:property value="bookList.size()"/>条：</strong></p>
		  	<p>
		  		<s:iterator value="bookList">
		  			<s:property value="name"/>&nbsp;&nbsp;&nbsp;&nbsp;
		  		</s:iterator>
		  	</p>
		  	<p>&nbsp;</p>
	  	</s:if>
	  	<s:if test="diskList.size() > 0">
	  		<p><strong>光盘<s:property value="diskList.size()"/>条：</strong></p>
		  	<p>
		  		<s:iterator value="diskList">
		  			<s:property value="name"/>&nbsp;&nbsp;&nbsp;&nbsp;
		  		</s:iterator>
		  	</p>
		  	<p>&nbsp;</p>
	  	</s:if>
	  	<s:if test="coursewareList.size() > 0">
	  		<p><strong>课件<s:property value="coursewareList.size()"/>条：</strong></p>
		  	<p>
		  		<s:iterator value="coursewareList">
		  			<s:property value="name"/>&nbsp;&nbsp;&nbsp;&nbsp;
		  		</s:iterator>
		  	</p>
		  	<p>&nbsp;</p>
	  	</s:if>
	  	<s:if test="majorList.size() > 0">
	  		<p><strong>专业<s:property value="majorList.size()"/>条：</strong></p>
		  	<p>
		  		<s:iterator value="majorList">
		  			<s:property value="name"/>&nbsp;&nbsp;&nbsp;&nbsp;
		  		</s:iterator>
		  	</p>
		  	<p>&nbsp;</p>
	  	</s:if>
	  	<s:if test="edutypeList.size() > 0">
	  		<p><strong>层次<s:property value="edutypeList.size()"/>条：</strong></p>
		  	<p>
		  		<s:iterator value="edutypeList">
		  			<s:property value="name"/>&nbsp;&nbsp;&nbsp;&nbsp;
		  		</s:iterator>
		  	</p>
		  	<p>&nbsp;</p>
	  	</s:if>
	  	<s:if test="gradeList.size() > 0">
	  		<p><strong>年级<s:property value="gradeList.size()"/>条：</strong></p>
		  	<p>
		  		<s:iterator value="gradeList">
		  			<s:property value="name"/>&nbsp;&nbsp;&nbsp;&nbsp;
		  		</s:iterator>
		  	</p>
		  	<p>&nbsp;</p>
	  	</s:if>
	  	<s:if test="newsList.size() > 0">
	  		<p><strong>新闻<s:property value="newsList.size()"/>条：</strong></p>
		  	<p>
		  		<s:iterator value="newsList">
		  			<s:property value="title" escape="false"/><br/>
		  		</s:iterator>
		  	</p>
		  	<p>&nbsp;</p>
	  	</s:if>
	  	<s:if test="votePaperList.size() > 0">
	  		<p><strong>调查问卷<s:property value="votePaperList.size()"/>条：</strong></p>
		  	<p>
		  		<s:iterator value="votePaperList">
		  			<s:property value="title" escape="false"/><br/>
		  		</s:iterator>
		  	</p>
		  	<p>&nbsp;</p>
	  	</s:if>
   	  </div>
    </div>
</div>
<div class="clear"></div>

<!-- JavaScript onload -->

</body>
</html>
