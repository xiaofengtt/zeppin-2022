<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.announce.*"%>
<%@ page import="com.whaty.platform.courseware.basic.*"%>
<%@ include file="./pub/priv.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>生殖健康咨询师培训网</title>
<link href="title.css" rel="stylesheet" type="text/css" />
<script type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.osRC;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
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

<body leftmargin="0" topmargin="0" onload="MM_preloadImages('images/menu11.gif','images/menu22.gif','images/menu33.gif','images/menu44.gif','images/menu55.gif','images/66.gif')">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="134" valign="top">
    <table width="100%" height="134" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="68" valign="top"><table width="100%" height="68" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="13"><img src="images/top2.gif" width="13" height="68" /></td>
              <td width="108"><img src="images/top3.gif" width="108" height="68" /></td>
              <td width="104"><img src="images/top4.gif" width="104" height="68" /></td>
              <td width="83"><img src="images/top5.gif" width="83" height="68" /></td>
              <td background="images/top1.gif">&nbsp;</td>
              <td width="13"><img src="images/top.gif" width="13" height="68" /></td>
            </tr>
        </table>
       </td>
      </tr>
      <tr>
        <td valign="top">
	<form name="courseware" method="post" action="../../courseware/courseware_view.jsp" target="main">        
        <table width="100%" height="42" border="0" cellpadding="0" cellspacing="0">
            <tr valign="baseline">
              <td width="13" height="42" valign="baseline"><img src="images/left-m.gif" width="13" height="42" /></td>

<%!
	//判断字符串为空的话，赋值为"不详"
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
	
	
    List itemList=interactionManage.getTheachItem(courseId);
    
   
  String item="";
  String dayi="";
  String gonggao="";
  String taolun="";
  String kaoshi="";
  String zuoye="";
  String ziyuan="";
 if(itemList!=null){
 
    for(int i=0;i<itemList.size();i++){
           CourseItem techItem=(CourseItem) itemList.get(i);
           dayi=fixnull(techItem.getDayi());  
           gonggao=fixnull(techItem.getGonggao());
           taolun=fixnull(techItem.getTaolun());
           kaoshi=fixnull(techItem.getKaoshi());
           zuoye=fixnull(techItem.getZuoye());
           ziyuan=fixnull(techItem.getZiyuan());
    
   }
   }


	
%>              
              <td height="42" align="left" valign="middle" background="images/mid.gif" style="font-size:12px;white-space:nowrap">选择课件
				<select name="courseware_id">
<%
	if(coursewares == null || coursewares.size() < 1)
	{
%>									
				<option>目前没有课件</option>
<%
	}
	else
	{
		for(int i = 0; i < coursewares.size(); i++)
		{
			Courseware courseware = (Courseware)coursewares.get(i);
			String selected = "";
			if(courseware_id != null && courseware_id.equals(courseware.getId()))
				selected = "selected";
%>				
				<option value=<%=courseware.getId()%> <%=selected%>><%=courseware.getName()%></option>
<%
		}
	}				
%>	
				</select>
                <strong><input type="submit" value="浏览"></strong></td>                
              <td width="492" height="42" valign="baseline">
            
            
            <table width="100%" height="42" border="0" cellpadding="0" cellspacing="0">
                
                <%if(itemList==null){%>
                <tr>
				  <td width="53"><a href="./announce/announce_list.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image7','','images/menu11.gif',1)" target="main"><img src="images/menu.gif" name="Image7" width="53" height="42" border="0" id="Image7" /></a></td>                                  
                  <td width="53"><a href="./answer/index.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image8','','images/menu22.gif',1)" target="main"><img src="images/menu1.gif" name="Image8" width="53" height="42" border="0" id="Image8" /></a></td>
                  <td width="79"><a href="./forum/forumlist_list.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image9','','images/menu33.gif',1)" target="main"><img src="images/menu4.gif" name="Image9" width="79" height="42" border="0" id="Image9" /></a></td>
                  <td width="86"><a href="./testpaper/onlinetestcourse_list.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image13','','images/menu44.gif',1)" target="main"><img src="images/menu3.gif" name="Image13" width="86" height="42" border="0" id="Image13" /></a><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image10','','images/menu44.gif',1)"></a></td>
                  <td width="86"><a href="./homeworkpaper/homeworkpaper_list.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image11','','images/menu55.gif',1)" target="main"><img src="images/menu5.gif" name="Image11" width="86" height="42" border="0" id="Image11" /></a></td>
                  <td width="64"><a href="resource/course_resource.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image12','','images/66.gif',1)" target="main"><img src="images/menu6.gif" name="Image12" width="64" height="42" border="0" id="Image12" /></a></td>
                  <td background="images/mid.gif">&nbsp;</td>
                </tr>
                <%
                }else{%>
                
                 <tr>
                 <%if(gonggao.equals("1")) {%>
				  <td width="53"><a href="./announce/announce_list.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image7','','images/menu11.gif',1)" target="main"><img src="images/menu.gif" name="Image7" width="53" height="42" border="0" id="Image7" /></a></td> 
				   <%}%>
				  <%if(dayi.equals("1")) {%>                                
                  <td width="53"><a href="./answer/index.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image8','','images/menu22.gif',1)" target="main"><img src="images/menu1.gif" name="Image8" width="53" height="42" border="0" id="Image8" /></a></td>
                  <%}%>
                   <%if(taolun.equals("1")) {%>
                  <td width="79"><a href="./forum/forumlist_list.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image9','','images/menu33.gif',1)" target="main"><img src="images/menu4.gif" name="Image9" width="79" height="42" border="0" id="Image9" /></a></td>
                  <%}%>
                   <%if(kaoshi.equals("1")) {%>
                  <td width="86"><a href="./testpaper/onlinetestcourse_list.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image13','','images/menu44.gif',1)" target="main"><img src="images/menu3.gif" name="Image13" width="86" height="42" border="0" id="Image13" /></a><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image10','','images/menu44.gif',1)"></a></td>
                 <%}%>
                  <%if(zuoye.equals("1")) {%>
                  <td width="86"><a href="./homeworkpaper/homeworkpaper_list.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image11','','images/menu55.gif',1)" target="main"><img src="images/menu5.gif" name="Image11" width="86" height="42" border="0" id="Image11" /></a></td>
                   <%}%>
                   <%if(ziyuan.equals("1")) {%>
                  <td width="64"><a href="resource/course_resource.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image12','','images/66.gif',1)" target="main"><img src="images/menu6.gif" name="Image12" width="64" height="42" border="0" id="Image12" /></a></td>
                 <%}%>
                  <td background="images/mid.gif">&nbsp;</td>
                </tr>
                
               <%}%>
              </table>
              </td>
            </tr>
        </table>
</form>        
        </td>
      </tr>
      <tr>
        <td height="24" background="images/121.gif">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td align="left" valign="top"><table width="100%" height="430" border="0" cellpadding="0" cellspacing="0">
      <tr>
<%
	String link = "./announce/announce_list.jsp";
	
	if(courseware_id != null && !courseware_id.equals(""))
	{
		link = "../../courseware/courseware_view.jsp?courseware_id=" + courseware_id;
	}
%>        
        <td height="100%"><iframe name="main" width="100%" height="100%" scrolling="auto" frameborder="0" src="<%=link%>"></iframe></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
