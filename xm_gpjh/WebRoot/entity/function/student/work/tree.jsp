<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.net.*" pageEncoding="UTF-8"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.user.*,com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tree</title>
<link href="../css/admincss.css" rel="stylesheet" type="text/css">
</head>
<%
	OpenCourse openCourse = (OpenCourse)session.getAttribute("openCourse");
	String courseName = openCourse.getBzzCourse().getName();
%>
<body leftmargin="0" topmargin="0">
<table width="204" height="320" border="0" cellpadding="0" cellspacing="0" style="border-top:1px solid #CCD9E0">
  <tr> 
    <td valign="top"><table width="212" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="2" bgcolor="7F8F9F"><img src="../images/k.gif" width="2" height="1"></td>
        <td height="300" valign="top" background="../images/help_bt08.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <!--     <tr>
              <td align="center"><img src="../images/help_bt09_1.gif" width="97" height="35"></td>
            </tr>  -->
            <tr>
              <td align="center"><table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="A1A9FA">
                  <tr>
                    <td height="27" align="center" bgcolor="B4FF00" class="kc">当前课程：<%=courseName %></td>
                  </tr>
              </table></td>
            </tr>
            <tr>
              <td height="4" align="center"><img src="../images/help_bt11.gif" width="208" height="4"></td>
            </tr>
            <tr>
              <td align="left"><div id="helpInfo" style="background-color:#E2EEF3;margin-left:4px;width:204px; height:220px; overflow:hidden">
                <iframe id="treeBox" name="treeBox" src="tree/preload/preload.htm" width="204px" height="100%" frameborder="0" align="top" scrolling="yes" allowtransparency="true"></iframe>
              </div></td>
            </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
