<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.interaction.*" %>
<%@ page import="com.whaty.platform.entity.basic.*" %>
<%@ page import="com.whaty.platform.interaction.forum.*,com.whaty.platform.courseware.basic.*,com.whaty.platform.courseware.*" %>
<%@ include file="./pub/priv.jsp"%>
<HTML 
xmlns="http://www.w3.org/1999/xhtml"><HEAD><TITLE>Test page</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
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
<body onLoad="MM_preloadImages('images/11-01.jpg','images/12-01.jpg','images/13-01.jpg','images/14-01.jpg','images/15-01.jpg','images/16-01.jpg','images/17-01.jpg','images/18-01.jpg')">
<table width="180" border="0" cellpadding="0" cellspacing="2">
  <tr>
    <td><table width="180" border="0" cellpadding="0" cellspacing="0" class="xian">
      <tr>
        <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="5" height="14"><img src="images/menu03.jpg" width="5" height="14" /></td>
            <td width="155" valign="middle" background="images/menu04.jpg"><div align="center"></div></td>
            <td width="5"><img src="images/menu05.jpg" width="5" height="14" /></td>
          </tr>
        </table></td>
      </tr>
      <tr>
          <td height="305" align="left" valign="middle" class="rightlayer"><table height="297" border="0" align="center" cellpadding="0" cellspacing="1">
              <tr> 
                <td width="153" height="36"><a href="teachclass_info.jsp?type=XXDH" target=mainFrame><img src="images/1-1.jpg" name="Image15" width="153" height="36" border="0" id="Image15" /></td>
              </tr>
              <%
              		InteractionFactory interactionFactory = InteractionFactory.getInstance();
					InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
					List cwareList=interactionManage.getCoursewares(teachclass_id);
					if(cwareList.size()>0)
					{
               %>
              <tr> 
                <td height="36"><a href="./enter_cware.jsp?courseware_id=<%=((Courseware)cwareList.get(0)).getId()%>&enter_type=view" target=_blank><img src="images/2-1.jpg" name="Image14" width="153" height="36" border="0" id="Image14" /></td>
              </tr>
              <%
              		}
              		else
              		{
               %>
               <tr> 
                <td height="36"><img src="images/2-1.jpg" name="Image14" width="153" height="36" border="0" id="Image14" /></td>
              </tr>
               <%
               		}
                %>
              <tr> 
                <td height="36"><A href="teachclass_info.jsp?type=FDDY" target=mainFrame urn=zoomx#y1#400><img src="images/4-1.jpg" width="153" height="36" border="0" /></a></td>
              </tr>
              <tr> 
                <td height="36" ><A href="teachclass_info.jsp?type=ZYZC" target=mainFrame urn=zoomx#y2#400><img src="images/3-1.jpg" border="0"/></a></td>
              </tr>
              <tr> 
                <td height="36"><img src="images/5-1.jpg" name="Image18" width="153" height="36" border="0" id="Image18" /></td>
              </tr>
              <tr> 
                <td height="36"><a href="./experimentpaper/homeworkpaper_list.jsp" target=mainFrame><img src="images/6-1.jpg" name="Image19" width="153" height="36" border="0" id="Image19" /></a></td>
              </tr>
              <tr> 
                <td height="36"><a href="./exampaper/onlinetestcourse_list.jsp" target=mainFrame><img src="images/7-1.jpg" name="Image20" width="153" height="36" border="0" id="Image20" /></a></td>
              </tr>
              <tr> 
                <td height="36"><a href="./vote_list.jsp" target=mainFrame><img src="images/8-1.jpg" name="Image21" width="153" height="36" border="0" id="Image21" /></td>
              </tr>
            </table></td>
      </tr>
    </table></td>
  </tr>
   <%
			if("teacher".equals(userType))
			{
		%>
  <tr>
    <td><table width="180" border="0" cellpadding="0" cellspacing="0" class="xian">
      <tr>
        <td height="14" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="5" height="14"><img src="images/menu003.jpg" width="5" height="14" /></td>
            <td width="155" height="14" valign="middle" background="images/menu004.jpg"><img src="images/menu004.jpg" width="1" height="14" /></td>
            <td width="5"><img src="images/menu005.jpg" width="5" height="14" /></td>
          </tr>
        </table></td>
      </tr>
    
      <tr>
          <td height="84" align="left" valign="middle" class="rightlayer"><table height="77" border="0" align="center" cellpadding="0" cellspacing="1">
              <tr> 
                <td width="153" height="36"><a href="student_manage.jsp" target=mainFrame><img src="images/9-1.jpg" name="Image15" width="153" height="36" border="0" id="Image15" /></a></td>
              </tr>
              <tr> 
                <td height="36"><a href="student_score.jsp" target=mainFrame><img src="images/10-1.jpg" name="Image14" width="153" height="36" border="0" id="Image14" /></a></td>
              </tr>
            </table></td>
      </tr>
      
    </table></td>
  </tr>
  <%
      		}
       %>
  <tr>
    <td height="60"><table width="180" border="0" cellpadding="0" cellspacing="0" class="xian">
      <tr>
        <td height="14" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="5" height="14"><img src="images/menu0003.jpg" width="5" height="14" /></td>
            <td width="155" height="14" valign="middle" background="images/menu0004.jpg"><img src="images/menu0004.jpg" width="1" height="14" /></td>
            <td width="5"><img src="images/menu0005.jpg" width="5" height="14" /></td>
          </tr>
        </table></td>
      </tr>
      <tr>
          <td align="left" valign="middle" class="rightlayer"><table height="38" border="0" align="center" cellpadding="0" cellspacing="1">
              <tr> 
                <td width="153" height="36"><a href="./resource/course_resource.jsp" target=mainFrame><img src="images/11-1.jpg" name="Image15" width="153" height="36" border="0" id="Image15" /></a></td>
              </tr>
            </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><table width="180" border="0" cellpadding="0" cellspacing="0" class="xian">
      <tr>
        <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="5" height="14"><img src="images/menu00003.jpg" width="5" height="14" /></td>
            <td width="155" height="14" valign="middle" background="images/menu00004.jpg"><img src="images/menu00004.jpg" width="1" height="14" /></td>
            <td width="5"><img src="images/menu00005.jpg" width="5" height="14" /></td>
          </tr>
        </table></td>
      </tr>
      <tr>
          <td height="83" align="left" valign="middle" class="rightlayer"><table height="77" border="0" align="center" cellpadding="0" cellspacing="1">
              <tr> 
                <td width="153" height="36"><img src="images/12-1.jpg" name="Image15" width="153" height="36" border="0" id="Image15" /></td>
              </tr>
              <tr> 
                <td height="36"><img src="images/13-1.jpg" name="Image14" width="153" height="36" border="0" id="Image14" /></td>
              </tr>
            </table></td>
      </tr>
    </table></td>
  </tr>
</table>
<div class="hello" id="y1" > 
  <table width="120" border="0" cellpadding="0" cellspacing="0" class="xian">
    <tr> 
      <td width="120" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="5" height="14" align="right" background="images/menu04.jpg">&nbsp;</td>
            <td width="132" valign="middle" background="images/menu04.jpg"><div align="center"></div></td>
            <td width="5" background="images/menu04.jpg">&nbsp;</td>
          </tr>
        </table></td>
    </tr>
    <tr > 
      <td height="80" align="center" valign="middle"   class="rightlayer" > <table height="80" border="0" align="center" cellpadding="0" cellspacing="1">
           <tr> 
            <td height="5" align="center" background="images/hxdiv.gif"><a href="#">辅导课件</a></td>
          </tr>
          <tr> 
            <td height="5" align="center" background="images/hxdiv.gif"> <a href="./answer/index.jsp" target=mainFrame>在线答疑</a></td>
          </tr>
         <%
         	if("teacher".equalsIgnoreCase(userType))
         	{
          %>
          <tr> 
            <td width="102" height="5" align="center" background="images/hxdiv.gif"><a href="../teacher/enter_mcsclass.jsp?course_id=<%=teachclass_id %>&course_name=<%=openCourse.getCourse().getName() %>" target=_blank>视频答疑</a></td>
          </tr> 
          <%
          	}
          	else
          	{
           %>
            <tr> 
            <td width="102" height="5" align="center" background="images/hxdiv.gif"><a href="../student/enter_mcsclass.jsp?course_id=<%=teachclass_id %>&course_name=<%=openCourse.getCourse().getName() %>" target=_blank>视频答疑</a></td>
          </tr>
           <%
           	}
            %>
        </table></td>
    </tr>
  </table>
</div>
<div class="hello" id="y2"> 
  <table width="120" border="0" cellpadding="0" cellspacing="0" class="xian">
    <tr> 
      <td width="120" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="5" height="14" align="right" background="images/menu04.jpg">&nbsp;</td>
            <td width="132" valign="middle" background="images/menu04.jpg"><div align="center"></div></td>
            <td width="5" background="images/menu04.jpg">&nbsp;</td>
          </tr>
        </table></td>
    </tr>
    <tr > 
      <td height="80" align="center" valign="middle"   class="rightlayer" > <table height="80" border="0" align="center" cellpadding="0" cellspacing="1">
          <tr> 
            <td width="102" height="5" align="center" background="images/hxdiv.gif"><a href="./smzy/homeworkpaper_list.jsp" target=mainFrame>书面作业</a></td>
          </tr>
          <tr> 
            <td height="5" align="center" background="images/hxdiv.gif"><a href="./homeworkpaper/homeworkpaper_list.jsp" target=mainFrame>在线作业 
              </a></td>
          </tr>
          <tr> 
            <td height="5" align="center" background="images/hxdiv.gif"> <a href="./testpaper/onlinetestcourse_list.jsp" target=mainFrame>在线自测</a></td>
          </tr>
        </table></td>
    </tr>
  </table>
</div>

</body>

</html>