<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>生殖健康咨询师培训网</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {
	color: #FF0000;
	font-weight: bold;
}
.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}
-->
</style></head>

<body bgcolor="#E0E0E0">
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <IFRAME NAME="top" width=100% height=200 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/top.jsp" scrolling=no allowTransparency="true"></IFRAME>
  <tr>
    <td height="13"></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
     <td width="110" valign="top"><!-- <IFRAME NAME="leftA" width=237 height=650 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/left.jsp" scrolling=no allowTransparency="true"></IFRAME></td>
   --><td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17" align="left" class="twocentop"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" /> 当前位置：公告列表</td>
      </tr>
      <tr>
          <td><br><!-- <img name="two2_r5_c5" src="images/two/two2_r5_c5.jpg" width="750" height="72" border="0" id="two2_r5_c5" alt="" /> --></td>
      </tr>
       <tr>
            <td align="left"> &nbsp;<!--<a href="/entity/workspaceStudent/bzzstudent_toLearningCourses.action"><img border="0" src="/entity/bzz-students/images/two/basic_course2.jpg" width="124" height="25" /></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="/entity/workspaceStudent/bzzstudent_tishengcourse.action"><img border="0" src="/entity/bzz-students/images/two/tisheng_course.jpg" width="124" height="25" /></a>
           --></td>
          </tr>
      <tr align="center">
        <td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop" align="center">
          <tr>
          <td width="3%" align="center"></td>
            <td width="57%" align="left">标 &nbsp; 题</td>
            <td width="20%" align="left">发布时间</td>
            <td width="20%" align="left">发布人</td>
          </tr>
        </table></td>
      </tr>
      <tr valign="top">
        <td ><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocen">
           <s:if test="bulletinList.size()> 0">
     	 <s:iterator value="bulletinList" status="stuts" >
          <tr valign="bottom">
											<td width="3%" height="25" align="center">
												<img src="/entity/bzz-students/images/two/4.jpg" width="9"
													height="9" />
											</td>
											<td width="57%" align="left">
												<a href='/entity/workspaceStudent/stuPeBulletinView_toInfo.action?bean.id=<s:property value="id"/>' target="_blank"><s:property value="title" /></a>
											</td>
											<td width="20%" align="left">
												<s:date name="publishDate" format="yyyy-MM-dd" />
											</td>
											<td width="20%" align="left">
												<s:if test="peManager!=null">
													<s:property value="peManager.trueName" />
												</s:if>
												<s:else>
													<s:property value="enterpriseManager.name" />
												</s:else>
											</td>
										</tr>
          <tr>
            <td colspan="6"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          </s:iterator>
         </s:if>
          </table>
          </td>
      </tr>
      <tr>
       <td width="13">&nbsp;</td>
      </tr>
      
        </table></td>
    <td width="13">&nbsp;</td>
  </tr>
</table>
<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no allowTransparency="true" align=center></IFRAME>
</td>
</tr>
</table>
</body>
</html>