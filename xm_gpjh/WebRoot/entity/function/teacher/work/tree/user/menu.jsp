<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@ page import="com.whaty.platform.entity.basic.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.interaction.forum.*,com.whaty.platform.courseware.basic.*,com.whaty.platform.courseware.*" %>
<%@ include file="../../../../pub/priv.jsp"%>
<html>
<head>
<title></title>
<link href="../js/tree/menu.css" rel="stylesheet" type="text/css">
<script LANGUAGE="JavaScript">

<!--
  document.onmouseover = doDocumentOnMouseOver ;
  document.onmouseout = doDocumentOnMouseOut ;

  function doDocumentOnMouseOver() {
    var eSrc = window.event.srcElement ;
    if (eSrc.className == "item") {
      window.event.srcElement.className = "highlight"; // change class
    }
  }

  function doDocumentOnMouseOut() {
    var eSrc = window.event.srcElement ;
    if (eSrc.className == "highlight") {
      window.event.srcElement.className = "item"; // change class
    }
  }

var bV=parseInt(navigator.appVersion);

NS4=(document.layers) ? true : false;

IE4=((document.all)&&(bV>=4))?true:false;

ver4 = (NS4 || IE4) ? true : false;

function expandIt(){return}
function expandAll(){return}
//-->
function expandIt(el) {

	if (!ver4) return;

	if (IE4) {expandIE(el)} else {expandNS(el)}

}



function expandIE(el) { 

	whichIm = event.srcElement;

	whichEl = eval(el + "Child");

	if (whichEl.style.display == "none") {

		whichEl.style.display = "block";

		//whichE2.style.display = "none";

		whichIm.src = "images/minus.gif";		

	}

	else {

		whichEl.style.display = "none";

		whichIm.src = "images/icon-t.gif";

	}

    window.event.cancelBubble = true ;

}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8"><style type="text/css">
<!--
body {
	margin-left: 0px;
}
-->
</style></head>
<%!
	String fixnull(String str)
	{
	    if(str == null || str.equals("") || str.equals("null"))
			str = "";
			return str;
	
	}
%>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" class="scllbar" style="background-color:transparent">
<table width="185" border="0" cellspacing="2" cellpadding="0">
  
   <%
    InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
				
	List itemList=interactionManage.getTheachItem(courseId);
  
	String item="";
	String dayi="";
	String gonggao="";
	String taolun="";
	String kaoshi="";
	String zuoye="";
	String ziyuan="";
	String zxdayi="";
  	String smzuoye="";
  	String zice="";
  	String pingjia="";
  	String daohang="";
  	String daoxue="";
  	String shiyan="";
 	String zfx="";
 	String boke="";
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
	          zxdayi=fixnull(techItem.getZxdayi());
	          smzuoye=fixnull(techItem.getSmzuoye());
	          zice=fixnull(techItem.getZice());
	          pingjia=fixnull(techItem.getPingjia());
	          daoxue=fixnull(techItem.getDaoxue());
	          daohang = fixnull(techItem.getDaohang());
	          shiyan=fixnull(techItem.getShiyan());
	          zfx=fixnull(techItem.getZfx());
	          boke=fixnull(techItem.getBoke());
	  	}
	}
	zice="1";
	zxdayi="0";
	
//	List cwareList=interactionManage.getCoursewares(teachclass_id);
	%>
 	<tr>
    <td height="26" background="../../../images/help_bt12.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="28" align="right"><img src="../../../images/ico01.gif" width="11" height="10"></td>
        <td class="meau_hu">　<a href="/entity/function/kcjj/coursenote_list.jsp?type =KCJJ&open_course_id=<%=teachclass_id%>" target=contentBox>课 程 简 介</a></td>
      </tr>
    </table></td>
  </tr>
   <tr>
    <td height="26" background="../../../images/help_bt12.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="28" align="right"><img src="../../../images/ico01.gif" width="11" height="10"></td>
        <td class="meau_hu">　<a href="/entity/function/jsjj/teachernote_list.jsp?open_course_id=<%=teachclass_id%>" target=contentBox>教 师 简 介</a></td>
      </tr>
    </table></td>
  </tr>
  
    <tr>
    <td height="26" background="../../../images/help_bt12.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="28" align="right"><img src="../../../images/ico01.gif" width="11" height="10"></td>
        <td class="meau_hu">　<a href="../../../../kczl/homeworkpaper_list.jsp" target=contentBox>资 料 下 载</a></td>
      </tr>
    </table></td>
  </tr>
<!-- 
   <%
  	if(daohang.equals("1")||zfx.equals("1")||ziyuan.equals("1"))
  	{
   %>
  <tr>
    <td height="26" valign="bottom"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="26" background="../../../images/help_bt12.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="28" align="right"><img src="../../../images/ico02.gif" width="11" height="10"></td>
                <td class="meau_hu">　<a href="#" onClick="expandIt('elzy');return false">资 料 共 享</a></td>
              </tr>
          </table></td>
        </tr>
      </table>
        <div ID="elzyChild" CLASS="child" style="width:100%; height: 26;display:none" >
          <p style="word-spacing: 0; line-height: 150%; margin-top: 0"><%
          	if(daohang.equals("1"))
          	{
           %>　　 <span class="meau_hu"><a href="/entity/function/teachclass_info.jsp?type=XXDH" target=contentBox>■ 教 学 大 纲
</a><br>
            </span><%
            	}
            	if(ziyuan.equals("1"))
            	{
             %> 　　 <span class="meau_hu"><a href="../../../../kczl/homeworkpaper_list.jsp" target=contentBox>■ 参 考 资 料</a><br>
            </span><%
            	}
            	if(zfx.equals("1"))
            	{
             %> 　　 <span class="meau_hu"><a href="../../../../zfx/homeworkpaper_list.jsp" target=contentBox>■ 复 习 资 料</a><br>
          </span>
          <%
          		}
           %>
          </p>
        </div></td>
  </tr>
  <%
  }
  %>  -->
  
  <%
  	if(zxdayi.equals("1"))
  	{
   %>
  <tr>
    <td height="26" background="../../../images/help_bt12.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="28" align="right"><img src="../../../images/ico01.gif" width="11" height="10"></td>
        <td class="meau_hu">　<a href="/sso/other/interaction_getcoursewareList.action?open_course_id=<%=teachclass_id%>" target=contentBox>课 件 浏 览</a></td>
      </tr>
    </table></td>
  </tr>
  <%
  	}
   %>
  
  <%
  	if(daoxue.equals("1"))
  	{
   %>
  <tr>
    <td height="26" background="../../../images/help_bt12.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="28" align="right"><img src="../../../images/ico01.gif" width="11" height="10"></td>
        <td class="meau_hu">　<a href="/entity/function/teachclass_info.jsp?type=SPFD" target=contentBox>视 频 辅 导</a></td>
      </tr>
    </table></td>
  </tr>
  <%
  	}
   %>
   
   <%
  	if(dayi.equals("1"))
  	{
   %>
  <tr>
    <td height="26" background="../../../images/help_bt12.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="28" align="right"><img src="../../../images/ico01.gif" width="11" height="10"></td>
        <td class="meau_hu">　<a href="../../../../answer/index.jsp" target=contentBox>课 程 答 疑</a></td>
      </tr>
    </table></td>
  </tr>
  <%
  	}
   %>
  
  <%
  	if(zuoye.equals("1"))
  	{
   %>
  <tr>
    <td height="26" background="../../../images/help_bt12.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="28" align="right"><img src="../../../images/ico01.gif" width="11" height="10"></td>
        <td class="meau_hu">　<a href="../../../../onlinehomeworkpaper/homeworkpaper_list.jsp" target=contentBox>在 线 作 业</a></td>
      </tr>
    </table></td>
  </tr>
  <%
  	}
   %>
  
  <%
  	if(smzuoye.equals("1"))
  	{
   %>
  <tr>
    <td height="26" background="../../../images/help_bt12.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="28" align="right"><img src="../../../images/ico01.gif" width="11" height="10"></td>
        <td class="meau_hu">　<a href="/entity/workspaceTeacher/interactionHomework_toHomeworkList.action?teachclassId=<%=teachclass_id %>" target=contentBox>上 传 作 业</a></td>
      </tr>
    </table></td>
  </tr>
  <%
  	}
   %>
  
  <%
  	if(zice.equals("1"))
  	{
   %>
  <tr>
    <td height="26" background="../../../images/help_bt12.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="28" align="right"><img src="../../../images/ico01.gif" width="11" height="10"></td>
        <td class="meau_hu">　<a href="../../../../testpaper/onlinetestcourse_list.jsp" target=contentBox>在 线 自 测</a></td>
      </tr>
    </table></td>
  </tr>
  <%
  	}
   %>
   
     <tr>
    <td height="26" background="../../../images/help_bt12.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="28" align="right"><img src="../../../images/ico01.gif" width="11" height="10"></td>
        <td class="meau_hu">　<a href="../../../../exampaper/alert.jsp" target=contentBox>工 具 与 案 例</a></td>
      </tr>
    </table></td>
  </tr>
  

<!-- 
  <tr>
    <td height="26" background="../../../images/help_bt12.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="28" align="right"><img src="../../../images/ico01.gif" width="11" height="10"></td>
        <td class="meau_hu">　<a href="../../../../vote_list.jsp" target=contentBox>使 用 帮 助</a></td>
      </tr>
    </table></td>
  </tr>
 -->
</table>
</body>
</html>