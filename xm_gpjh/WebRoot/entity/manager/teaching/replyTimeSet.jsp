<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
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
<script type="text/javascript">
function turnToSearch() {
	window.self.location = "/entity/teaching/replyTimeSet.action";
}

</script>
</head>
<!-- JavaScript functions -->
<body>
<form action="/entity/teaching/replyTimeSet_exe.action">
<!--内容区-->
<div id="main_content">
    <div class="content_title">设置答辩时间</div>
    <div class="cntent_k">
   	  <div class="k_cc">
	  	自动以专业为顺序安排学生的答辩时间。
		<br />
		自动分配答辩时间前请确认已经为本学期设置好答辩时间段（共12个）和答辩教室（共6个）。如设置完毕请点击确认进行自动安排答辩时间。
		<center><input type="submit" value="确认"/></center>
	  </div>
	  <div class="k_cc">
	  	查询、人工调整学生答辩时间
		<br />
		<center><input type="button" value="查询" onclick="turnToSearch()"/></center>
	  </div>
    </div>
</div>
<div class="clear"></div>
<div>
  <div> </div>
</div>
<!-- JavaScript onload -->
</form>
</body>
</html>
