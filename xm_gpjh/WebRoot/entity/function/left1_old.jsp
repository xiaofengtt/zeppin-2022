<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="./pub/priv.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<?xml version="1.0" encoding="gb2312"?><HTML 
xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>Test page</TITLE>
<META http-equiv=Content-Type content="text/html; charset=UTF-8">
<META content=mozart0 name=Author>
<link href="css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
body {
	margin-left: 2px;
	margin-top: 2px;
	margin-bottom: 2px;
	background-color: #e5eaee;
	
}
.hello {
	PADDING-RIGHT: 25px; DISPLAY: none; PADDING-LEFT: 16px; PADDING-BOTTOM: 10px; FONT: bold 16px verdana; WIDTH: 100px; COLOR: black; PADDING-TOP: 10px; HEIGHT: 80px; BACKGROUND-COLOR: #F4F4F6 ; opacity:50%;}
-->
</style>
<script type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}
//-->
</script>
<script language="JavaScript1.1~JavaScript1.1" type="text/JavaScript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);
//-->
</script>
<SCRIPT language="javascript" src="zoomx.js" type="text/javascript"></SCRIPT>
<META content="MSHTML 6.00.2900.3132" name=GENERATOR>
</head>

<body onload="MM_preloadImages('images/11-01.jpg','images/12-01.jpg','images/13-01.jpg','images/14-01.jpg','images/15-01.jpg','images/16-01.jpg','images/17-01.jpg','images/18-01.jpg','images/19-01.jpg','images/20-01.jpg')">
<table width="180" border="0" cellpadding="0" cellspacing="0" class="xian">
  <tr>
    <td height="25" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="25"><img src="images/menu.jpg" width="25" height="26" /></td>
        <td background="images/menu1.jpg" class="zi_1"><div align="left"> 　当前课程 </div></td>
        <td width="5"><img src="images/menu2.jpg" width="5" height="26" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="5"><img src="images/menu3.jpg" width="5" height="34" /></td>
        <td valign="middle" background="images/menu4.jpg"><div align="center" style="font-size: 16px;font-family: '黑体';"><%=openCourse.getCourse().getName() %></div></td>
        <td width="5"><img src="images/menu5.jpg" width="5" height="34" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="351" align="left" valign="top" class="rightlayer"><table height="382" border="0" align="center" cellpadding="0" cellspacing="4">
      <tr>
        <td width="153" height="36"><a href="course_info.jsp" target=mainFrame onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image15','','images/11-01.jpg',1)"><img src="images/11-1.jpg" name="Image15" width="153" height="36" border="0" id="Image15" /></a></td>
      </tr>
      <tr>
        <td height="36"><a href="teacher_info.jsp" target=mainFrame onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image14','','images/12-01.jpg',1)"><img src="images/12-1.jpg" name="Image14" width="153" height="36" border="0" id="Image14" /></a></td>
      </tr>
      <tr>
        <td height="36"><a href="teach_rule.jsp" target=mainFrame onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image16','','images/13-01.jpg',1)"><img src="images/13-1.jpg" name="Image16" width="153" height="36" border="0" id="Image16" /></a></td>
      </tr>
      <tr>
        <td height="36"><a href="./resource/course_resource.jsp" target=mainFrame onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image17','','images/14-01.jpg',1)"><img src="images/14-1.jpg" name="Image17" width="153" height="36" border="0" id="Image17" /></a></td>
      </tr>
       <%
			if("student".equals(userType))
			{
		%>
      <tr>
        <td height="36"><a href="courseware_list.jsp" target=mainFrame  onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image24','','images/22-01.jpg',1)"><img src="images/22-1.jpg" name="Image17" width="153" height="36" border="0" id="Image17" /></a></td>
      </tr>
      <%
      		}
			if("teacher".equals(userType))
			{
		%>
      <tr>
        <td height="36"><a href="courseware_list.jsp" target=mainFrame onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image18','','images/15-01.jpg',1)"><img src="images/15-1.jpg" name="Image18" width="153" height="36" border="0" id="Image18" /></a></td>
      </tr>
      <tr>
        <td height="36"><a href="./lore/lore_dir_list.jsp" target=mainFrame onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image18','','images/23-01.jpg',1)"><img src="images/23-1.jpg" name="Image18" width="153" height="36" border="0" id="Image18" /></a></td>
      </tr>
      <tr>
        <td height="36"><a href="student_manage.jsp" target=mainFrame onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image19','','images/16-01.jpg',1)"><img src="images/16-1.jpg" name="Image19" width="153" height="36" border="0" id="Image19" /></a></td>
      </tr>
      <tr>
        <td height="36"><a href="student_score.jsp" target=mainFrame  onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image20','','images/17-01.jpg',1)"><img src="images/17-1.jpg" name="Image20" width="153" height="36" border="0" id="Image20" /></a></td>
      </tr>
      <tr>
        <td height="36"><a href="assi_manage.jsp" target=mainFrame onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image21','','images/18-01.jpg',1)"><img src="images/18-1.jpg" name="Image21" width="153" height="36" border="0" id="Image21" /></a></td>
      </tr>
      <tr>
        <td height="36"><a href="../teacher/teacher_resource_manage.jsp" target=_blank onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image22','','images/19-01.jpg',1)"><img src="images/19-1.jpg" name="Image22" width="153" height="36" border="0" id="Image22" /></a></td>
      </tr>
      <%
      		}
      		else
      		{
       %>
       <tr>
        <td height="36"><a href="../student/student_resource_manage.jsp" target=_blank  onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image22','','images/19-01.jpg',1)"><img src="images/19-1.jpg" name="Image22" width="153" height="36" border="0" id="Image22" /></a></td>
      </tr>
       <%
       		}
        %>
      <tr>
        <td height="36"><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image23','','images/20-01.jpg',1)"><img src="images/20-1.jpg" name="Image23" width="153" height="36" border="0" id="Image23" /></a></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td align="center" valign="top">&nbsp;</td>
  </tr>
</table>
</body>
</html>