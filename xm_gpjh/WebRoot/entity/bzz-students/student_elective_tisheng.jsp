<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
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
    <td width="237" valign="top"><IFRAME NAME="leftA" width=237 height=500 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/left.jsp" scrolling=no allowTransparency="true"></IFRAME></td>
   <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17" align="left" class="twocentop"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" /> 当前位置：课程答疑</td>
      </tr>
      <tr>
          <td><br><!-- <img name="two2_r5_c5" src="images/two/two2_r5_c5.jpg" width="750" height="72" border="0" id="two2_r5_c5" alt="" /> --></td>
      </tr>
       <tr>
            <td align="left"><a href="/entity/workspaceStudent/bzzstudent_toLearntCourses.action"><img border="0" src="/entity/bzz-students/images/two/basic_course2.jpg" width="124" height="25" /></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="/entity/workspaceStudent/bzzstudent_toLearntTishengCourses.action"><img border="0" src="/entity/bzz-students/images/two/tisheng_course.jpg" width="124" height="25" /></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" name="question" value="常见问题" onclick="window.top.location.href='/entity/bzz-students/question.jsp'" />
            </td>
          </tr>
      <tr align="center">
        <td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr>
          <td width="5%" align="center"></td>
            <td width="40%" align="left">课程名称</td>
            <td width="10%" align="center">学时</td>
            <td width="30%" align="center">实时答疑室</td>
            <td width="15%" align="center">课程论坛</td>
          </tr>
        </table></td>
      </tr>
      <tr valign="top">
        <td ><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocen">
           <s:if test="learntTishengCourses.size > 0">
     	 <s:iterator value="learntTishengCourses" status="stuts" >
          <tr>
            <td width="5%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="40%" align="left"><s:property value="getPrBzzTchOpencourse().getPeBzzTchCourse().getName()"/></td>
            <td width="10%" align="center"><s:property value="getPrBzzTchOpencourse().getPeBzzTchCourse().getTime()"/></td>
             <script>
				var tmp_url_<s:property value="getPrBzzTchOpencourse().getId()"/> = "http://210.76.98.124:8080/mcs2/vlog.asp?roomid=<s:property value="getPrBzzTchOpencourse().getRoomId()"/>&op=0&user=" + escape("<s:property value="getPeStudent().getTrueName()"/>") + "&nick=" + escape("<s:property value="getPeStudent().getTrueName()"/>") + "&roomname=" + escape("<s:property value="getPrBzzTchOpencourse().getPeBzzTchCourse().getName()"/>") + "&roomgroup=" + escape("实时多媒体答疑教室");
			</script>
            <td width="30%" align="center">
            <input type="button" value="实时答疑室" onclick='window.open(tmp_url_<s:property value="getPrBzzTchOpencourse().getId()"/>,"msc2");'>
			</td>
            <td width="15%" align="center">
             <input type="button" value="课程论坛" onclick='window.open("<%=basePath%>/entity/workspaceStudent/bzzstudent_toCourseforum.action?opencourseid=<s:property value="getPrBzzTchOpencourse().getId()"/>","forum");'>
            </td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
           </s:iterator>
         </s:if>
          </table></td>
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