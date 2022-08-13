<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ include file="./pub/priv.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-color: #e5eaee;
}
-->
</style>
<link href="css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {font-size: 14px}
-->
</style>
</head>
<body>
<table width="100%" height="35" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="8" height="26" background="images/top1.jpg"><img src="images/2-2.jpg" width="0" height="35" /></td>
    <td width="1235" height="26" align="right" background="images/top1.jpg"><table width="100%" height="28" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="4%" height="23"><div align="center"><img src="images/wt_07.gif" width="38" height="25" /></div></td>
        <td width="96%" align="left" valign="middle"><span class="biaoti14">当前课程：<%=openCourse.getCourse().getName() %></span></td>
      </tr>
    </table></td>
    <td width="10" height="26" align="right" background="images/top1.jpg"><img src="images/top2.jpg" width="7" height="35" /></td>
  </tr>
</table>
</body>
</html>

      