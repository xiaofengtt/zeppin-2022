<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder, com.whaty.platform.config.*" %>
<%@ page import ="com.whaty.platform.config.ForumConfig" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>国培计划</title>
<link href="/web/bzz_index/style/index.css" rel="stylesheet" type="text/css">
<link href="../css.css" rel="stylesheet" type="text/css" />
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

<body onLoad="MM_preloadImages('/web/bzz_index/images/topover_18.jpg','/web/bzz_index/images/topover_06.jpg','/web/bzz_index/images/topover_08.jpg','/web/bzz_index/images/topover_10.jpg','/web/bzz_index/images/topover_12.jpg','/web/bzz_index/images/topover_14.jpg','/web/bzz_index/images/topover_16.jpg','/web/bzz_index/images/topover_20.jpg')">
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td><img src="/web/bzz_index/images/top_02.jpg" width="1002" height="153"></td><!--学生工作室顶部图片需要更换-->
  </tr>
  <tr>
    <td height="56" align="center" valign="middle" class="twocentop">
    	<marquee id="affiche" align="left" behavior="scroll" direction="left" hspace="5" vspace="5" loop="-1" scrollamount="20" scrolldelay="300" onMouseOut="this.start()" onMouseOver="this.stop()">
								<font color=red>尊敬的<s:property value="#session['user_session'].loginId"/>,欢迎您登陆平台,为了检验我们的培训效果请您填写下下面的调查问卷，以方便我们更新改进!</font></marquee>
     <!-- table width="936" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td align="center"><a href="/entity/first/firstInfoNews_toIndex.action" target="_blank"><img src="/web/bzz_index/images/btn_01.jpg" name="Image2" width="77" height="29" border="0" id="Image2" onMouseOver="MM_swapImage('Image2','','/web/bzz_index/images/btn_011.jpg',1)" onMouseOut="MM_swapImgRestore()"></a></td>
        <td align="center">&nbsp;</td>
        <td align="center"><a href="/web/bzz_index/xmjs/xmjs.jsp" target="_blank"><img src="/web/bzz_index/images/btn_02.jpg" name="Image3" width="84" height="29" border="0" id="Image2" onMouseOver="MM_swapImage('Image3','','/web/bzz_index/images/btn_021.jpg',1)" onMouseOut="MM_swapImgRestore()"></a></td>
        <td align="center">&nbsp;</td>
        <td align="center"><a href="/web/bzz_index/kctx/kctx.jsp" target="_blank"><img src="/web/bzz_index/images/btn_03.jpg" name="Image4" width="84" height="29" border="0" id="Image2" onMouseOver="MM_swapImage('Image4','','/web/bzz_index/images/btn_031.jpg',1)" onMouseOut="MM_swapImgRestore()"></a></td>
        <td align="center">&nbsp;</td>
        <td align="center"><a href="/web/bzz_index/khrz/khrz.jsp" target="_blank"><img src="/web/bzz_index/images/btn_04.jpg" name="Image5" width="84" height="29" border="0" id="Image2" onMouseOver="MM_swapImage('Image5','','/web/bzz_index/images/btn_041.jpg',1)" onMouseOut="MM_swapImgRestore()"></a></td>
        <td align="center">&nbsp;</td>
        <td align="center"><a href="/web/bzz_index/msfc/msfc_new.jsp" target="_blank"><img src="/web/bzz_index/images/btn_05.jpg" name="Image6" width="84" height="29" border="0" id="Image2" onMouseOver="MM_swapImage('Image6','','/web/bzz_index/images/btn_051.jpg',1)" onMouseOut="MM_swapImgRestore()"></a></td>
        <td align="center">&nbsp;</td>
        <td align="center"><a href="/web/bzz_index/yxbzz/yxbzz.jsp" target="_blank"><img src="/web/bzz_index/images/btn_06.jpg" name="Image7" width="99" height="29" border="0" id="Image2" onMouseOver="MM_swapImage('Image7','','/web/bzz_index/images/btn_061.jpg',1)" onMouseOut="MM_swapImgRestore()"></a></td>
        <td align="center">&nbsp;</td>
        <td align="center"><a href="/web/bzz_index/xyfw/xyfw.jsp" target="_blank"><img src="/web/bzz_index/images/btn_07.jpg" name="Image8" width="84" height="29" border="0" id="Image2" onMouseOver="MM_swapImage('Image8','','/web/bzz_index/images/btn_071.jpg',1)" onMouseOut="MM_swapImgRestore()"></a></td>
        <td align="center">&nbsp;</td>
        <td align="center"><a href="/web/bzz_index/cjwt_new/cjwt.jsp" target="_blank"><img src="/web/bzz_index/images/btn_08.jpg" name="Image9" width="84" height="29" border="0" id="Image2" onMouseOver="MM_swapImage('Image9','','/web/bzz_index/images/btn_081.jpg',1)" onMouseOut="MM_swapImgRestore()"></a></td>
        <td align="center">&nbsp;</td>
        <td align="center"><a href="/web/bzz_index/lxwm_new/lxwm_new.jsp" target="_blank"><img src="/web/bzz_index/images/btn_09.jpg" name="Image10" width="84" height="29" border="0" id="Image2" onMouseOver="MM_swapImage('Image10','','/web/bzz_index/images/btn_091.jpg',1)" onMouseOut="MM_swapImgRestore()"></a></td>
      </tr>
    </table --></td>
  </tr>
</table>
</body>
</html>
