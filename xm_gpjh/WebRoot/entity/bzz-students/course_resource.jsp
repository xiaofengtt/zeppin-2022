<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>生殖健康咨询师培训课堂</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
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

<body bgcolor="#E0E0E0" onLoad="MM_preloadImages('/entity/bzz-students/images/zlxz.jpg','/entity/bzz-students/images/zlxz_over.jpg','/entity/bzz-students/images/cybgxz.jpg','/entity/bzz-students/images/cybgxz_over.jpg')">
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
    <td width="237" valign="top"><IFRAME NAME="leftA" width=237 height=520 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/left.jsp" scrolling=no allowTransparency="true"></IFRAME></td>
   <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17" align="left" class="twocentop"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" /> 当前位置：资料下载</td>
      </tr>
      <tr>
          <td><br><!-- <img name="two2_r5_c5" src="images/two/two2_r5_c5.jpg" width="750" height="72" border="0" id="two2_r5_c5" alt="" /> --></td>
      </tr>
      <tr>
            <td align="left"><img border="0" src="/entity/bzz-students/images/zlxz.jpg" width="124" height="25" name="Image1" onMouseOver="MM_swapImage('Image1','','/entity/bzz-students/images/zlxz_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <!-- <a href="/entity/bzz-students/resource.jsp"><img border="0" src="/entity/bzz-students/images/cybgxz.jpg" width="124" height="25" name="Image2" onMouseOver="MM_swapImage('Image2','','/entity/bzz-students/images/cybgxz_over.jpg',1)" onMouseOut="MM_swapImgRestore()" /></a> -->
            </td>
          </tr>
      <tr align="center">
        <td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr>
          	<td width="5%" align="center">&nbsp;</td>
            <td width="20%" align="left">课程名称</td>
            <td width="30%" align="center">资料名称</td>
            <td width="30%" align="center">发布时间</td>
            <td width="15%" align="center">操作</td>
          </tr>
        </table></td>
      </tr>
      <tr valign="top">
        <td ><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocen">
          <s:set name="tc" value="trainCourses"/>
          <s:iterator value="ziliaoList" id="list" status="sta">
          <tr>
            <td width="5%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="20%" align="left"><s:property value="#tc[#sta.index].name"/></td>
            <td width="30%" align="center"><s:property value="#list.title"/></td>
            <td width="30%" align="center"><s:date name="#list.publishDate" format="yyyy-MM-dd hh:mm:ss"/></td>
            <td width="15%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="详细信息" onclick='window.open("/entity/workspaceStudent/bzzstudent_showDetail.action?ids=<s:property value="#list.id"/>","resource");'></td>
          </tr>
          </s:iterator>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          </table></td>
      </tr>
      <tr height="25">
       <td align="center" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"><table width="96%" cellspacing="0" cellpadding="0">
		  <tr>
            <td align="right" class="cen">共 <s:property value="totalPage"/> 页 <s:property value="totalSize"/> 条记录 | 
            	<s:if test="curPage == 1">
            		<font color="gray">首页 上一页</font>
            	</s:if>
            	<s:else>
            		<a href="/entity/workspaceStudent/bzzstudent_toDownload.action&curPage=1">首页</a>
            		<a href="/entity/workspaceStudent/bzzstudent_toDownload.action&curPage=<s:property value="%{curPage-1}"/>">上一页</a>
            	</s:else>
            	<s:if test="curPage == totalPage">
            		<font color="gray">下一页 尾页</font>
            	</s:if>
            	<s:else>
            		<a href="/entity/workspaceStudent/bzzstudent_toDownload.action&curPage=<s:property value="%{curPage+1}"/>">下一页</a>
            		<a href="/entity/workspaceStudent/bzzstudent_toDownload.action&curPage=<s:property value="totalPage"/>">尾页</a>
            	</s:else>
            </td>
          </tr>
        </table>
	   </td>
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