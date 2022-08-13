<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@ page import="com.whaty.platform.entity.basic.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.courseware.basic.*"%>
<%@ include file="./pub/priv.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%!
	String fixnull(String str)
	{
	    if(str == null || str.equals("") || str.equals("null"))
			str = "";
			return str;
	
	}
%>
<%
	String courseware_id = request.getParameter("courseware_id");

	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
	List coursewares = interactionManage.getActiveCoursewares(teachclass_id);
	session.setAttribute("courseware_priv",interactionManage.getCoursewareManagerPriv());
	
    List itemList=interactionManage.getTheachItem(courseId);
    
   
	String item="";
	String dayi="";
	String gonggao="";
	String taolun="";
	String kaoshi="";
	String zuoye="";
	String ziyuan="";
	int width = 0;
	if(itemList!=null)
	{
	   for(int i=0;i<itemList.size();i++){
	          CourseItem techItem=(CourseItem) itemList.get(i);
	          gonggao=fixnull(techItem.getGonggao());
	          dayi=fixnull(techItem.getDayi());  
	          taolun=fixnull(techItem.getTaolun());
	          kaoshi=fixnull(techItem.getKaoshi());
	          zuoye=fixnull(techItem.getZuoye());
	          ziyuan=fixnull(techItem.getZiyuan());
	  	}
	}
 %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>����8</title>
<link href="css.css" rel="stylesheet" type="text/css" />
<link href="admincss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	background-color: #e5eaee;
	margin-left: 0px;
	margin-top: 0px;
	margin-bottom: 0px;
	margin-right: 0px;
	background-image: url(images/middle.jpg);
}
.STYLE1 {color: #000000}
-->
</style>
<script type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
</head>

<bodyonLoad="MM_preloadImages('images/gonggaolan1.jpg')">
<body onLoad="MM_preloadImages('images/gonggaolan1.jpg','images/gonggaolan-4.jpg')">
<table width="100%" height="75" border="0" cellspacing="4">
  <tr>
    <td width="7%" align="center"><a href="./announce/announce_list.jsp" target=mainFrame><img src="images/1-1.gif" width="57" height="45" border="0"></a><br>
    <a href="./announce/announce_list.jsp" target=mainFrame><span class="gonggaoziti">课程公告</span></a></td>
    <%
			if("student".equals(userType))
			{
		%>
    <td width="7%" align="center"><a href="./teacher_info.jsp" target=mainFrame><img src="images/3-1.gif" width="57" height="45" border="0"></a><br>
     <a href="./teacher_info.jsp" target=mainFrame><span class="gonggaoziti">辅导教师</span></a></td>
     <%
     		}
      %>
      <%
			if("teacher".equals(userType))
			{
		%>
    <td width="7%" align="center"><a href="./teacher_info.jsp" target=mainFrame><img src="images/3-1.gif" width="57" height="45" border="0"></a><br>
     <a href="./teacher_info.jsp" target=mainFrame><span class="gonggaoziti">短信管理</span></a></td>
     <%
     		}
      %>
      
    <td width="7%" align="center"><a href="../../leaveword/index.jsp" target=_blank><img src="images/6-1.gif" width="57" height="45" border="0"></a><br>
     <span class="gonggaoziti">留言板</span></td>
    <td width="7%" align="center"><a href="http://xiazai.upol.cn/media/web/campus/xywh/index.htm" target=_blank><img src="images/2-1.gif" width="57" height="45" border="0"></a><br>
      <span class="gonggaoziti">网上校园</span></td>
   <%
			if("teacher".equals(userType))
			{
		%>   
    <td width="7%" align="center"><a href="../teacher/teacher_resource_manage.jsp" target=blank><img src="images/5-1.gif" width="57" height="45" border="0"></a><br>
      <span class="gonggaoziti">学习工具</span></td>
      <%
     		}
      %>
      <%
			if("student".equals(userType))
			{
		%>   
    <td width="7%" align="center"><a href="../student/student_resource_manage.jsp" target=blank><img src="images/5-1.gif" width="57" height="45" border="0"></a><br>
      <span class="gonggaoziti">学习工具</span></td>
      <%
     		}
      %>
    <td width="7%" align="center"><a href="#"><img src="images/4-1.gif" width="57" height="45" border="0"></a><br>
      <span class="gonggaoziti">学习帮助</span></td>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
</html>