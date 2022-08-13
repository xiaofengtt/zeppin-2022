<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>生殖健康咨询师网络培训课堂</title>
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

<body bgcolor="#E0E0E0" onLoad="MM_preloadImages('/entity/bzz-students/images/jckc1.jpg','/entity/bzz-students/images/jckc1_over.jpg','/entity/bzz-students/images/tskc2.jpg','/entity/bzz-students/images/tskc2_over.jpg')">
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
        <td height="17" align="left" class="twocentop"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" /> 当前位置：课程列表</td>
      </tr>
      <tr>
          <td><br><!-- <img name="two2_r5_c5" src="images/two/two2_r5_c5.jpg" width="750" height="72" border="0" id="two2_r5_c5" alt="" /> --></td>
      </tr>
       <tr>
            <td align="left"><a href="/entity/workspaceStudent/bzzstudent_toPlanCourses.action"><img border="0" src="/entity/bzz-students/images/jckc1.jpg" width="124" height="25" name="Image1" onMouseOver="MM_swapImage('Image1','','/entity/bzz-students/images/jckc1_over.jpg',1)" onMouseOut="MM_swapImgRestore()"/></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <!-- <a href="/entity/workspaceStudent/bzzstudent_toTiShengCourses.action"><img border="0" src="/entity/bzz-students/images/tskc2.jpg" width="124" height="25" name="Image2" onMouseOver="MM_swapImage('Image2','','/entity/bzz-students/images/tskc2_over.jpg',1)" onMouseOut="MM_swapImgRestore()" /></a> -->
            </td>
          </tr>
      <tr align="center">
        <td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <!-- tr>
          	<td width="2%" align="center"></td>
            <td width="18%" align="left">课程名称</td>
            <td width="10%" align="left">课程学分</td>
            <td width="10%" align="left">答疑教师</td>
            <td width="10%" align="left">学习要求</td>
            <td width="20%" align="left">学习进度</td>
            <td width="14%" align="center">课程学习</td>
            <td width="16%" align="center">详细学习记录</td>
          </tr -->
          <tr>
          	<td width="2%" align="center"></td>
            <td width="50%" align="left">课程内容</td>
            <td width="30%" align="left">授课人</td>
            <td width="9%" align="center">视频学习</td>
            <td width="9%" align="center">课件学习</td>
          </tr>
        </table></td>
      </tr>
      <tr valign="top">
        <td ><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocen">
        <tr>
          	<td width="2%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="50%" align="left" title="强基提质、职业化和能力建设">强基提质、职业化和能力建设</td>
            <td width="30%" align="left" title="金小桃 国家人口计生委人事司司长">金小桃 国家人口计生委人事司司长</td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="视频学习" onclick="window.open('/entity/student_course_jichu_play.jsp?filepath=jinxiaotao','vedioplay');"></td>
            <td width="9%" align="center">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          <tr>
          	<td width="2%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="50%" align="left" title="生殖健康咨询师必须具备的信息能力（上）">生殖健康咨询师必须具备的信息能力（上）</td>
            <td width="30%" align="left" title="萧绍博 国家人口计生委研究员">萧绍博 国家人口计生委研究员</td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="视频学习" onclick="window.open('/entity/student_course_jichu_play.jsp?filepath=xiaoshaobo1','vedioplay');"></td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="课件学习" onclick="window.open('/flash_course/xiaoshaobo1/index.html');"></td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          <tr>
          	<td width="2%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="50%" align="left" title="生殖健康咨询师必须具备的信息能力（下）">生殖健康咨询师必须具备的信息能力（下）</td>
            <td width="30%" align="left" title="萧绍博 国家人口计生委研究员">萧绍博 国家人口计生委研究员</td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="视频学习" onclick="window.open('/entity/student_course_jichu_play.jsp?filepath=xiaoshaobo2','vedioplay');"></td>
            <td width="9%" align="center">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          <tr>
          	<td width="2%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="50%" align="left" title="《人口和计划生育行政管理》《人口和计划生育相关的法律法规和政策知识》（上）">《人口和计划生育行政管理》《人口和计划生育相关的法律法...</td>
            <td width="30%" align="left" title="付 伟 国家人口计生委科学技术研究所所长">付  伟 &nbsp; 国家人口计生委科学技术研...</td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="视频学习" onclick="window.open('/entity/student_course_jichu_play.jsp?filepath=fuwei1','vedioplay');"></td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="课件学习" onclick="window.open('/flash_course/fuwei1/index.html');"></td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          <tr>
          	<td width="2%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="50%" align="left" title="《人口和计划生育行政管理》《人口和计划生育相关的法律法规和政策知识》（下）">《人口和计划生育行政管理》《人口和计划生育相关的法律法...</td>
            <td width="30%" align="left" title="付 伟 国家人口计生委科学技术研究所所长">付  伟 &nbsp; 国家人口计生委科学技术研...</td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="视频学习" onclick="window.open('/entity/student_course_jichu_play.jsp?filepath=fuwei2','vedioplay');"></td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="课件学习" onclick="window.open('/flash_course/fuwei2/index.html');"></td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          <tr>
          	<td width="2%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="50%" align="left" title="《生殖健康咨询师培训教程》《咨询技巧》《避孕节育咨询》《生殖健康咨询案例分析》（一）">（一）《生殖健康咨询师培训教程》《咨询技巧》《避孕节育...</td>
            <td width="30%" align="left" title="吴尚纯 国家人口计生委科学技术研究所研究员">吴尚纯 国家人口计生委科学技术研...</td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="视频学习" onclick="window.open('/entity/student_course_jichu_play.jsp?filepath=wushangcun1','vedioplay');"></td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="课件学习" onclick="window.open('/flash_course/wushangchun1/index.html');"></td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          <tr>
          	<td width="2%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="50%" align="left" title="《生殖健康咨询师培训教程》《咨询技巧》《避孕节育咨询》《生殖健康咨询案例分析》（二）">（二）《生殖健康咨询师培训教程》《咨询技巧》《避孕节育...</td>
            <td width="30%" align="left" title="吴尚纯 国家人口计生委科学技术研究所研究员">吴尚纯 国家人口计生委科学技术研...</td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="视频学习" onclick="window.open('/entity/student_course_jichu_play.jsp?filepath=wushangcun2','vedioplay');"></td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="课件学习" onclick="window.open('/flash_course/wushangchun2/index.html');"></td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          <tr>
          	<td width="2%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="50%" align="left" title="《生殖健康咨询师培训教程》《咨询技巧》《避孕节育咨询》《生殖健康咨询案例分析》（三）">（三）《生殖健康咨询师培训教程》《咨询技巧》《避孕节育...</td>
            <td width="30%" align="left" title="吴尚纯 国家人口计生委科学技术研究所研究员">吴尚纯 国家人口计生委科学技术研...</td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="视频学习" onclick="window.open('/entity/student_course_jichu_play.jsp?filepath=wushangcun3','vedioplay');"></td>
            <td width="9%" align="center">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          <tr>
          	<td width="2%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="50%" align="left" title="《生殖健康咨询师培训教程》《咨询技巧》《避孕节育咨询》《生殖健康咨询案例分析》（四）">（四）《生殖健康咨询师培训教程》《咨询技巧》《避孕节育...</td>
            <td width="30%" align="left" title="吴尚纯 国家人口计生委科学技术研究所研究员">吴尚纯 国家人口计生委科学技术研...</td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="视频学习" onclick="window.open('/entity/student_course_jichu_play.jsp?filepath=wushangcun4','vedioplay');"></td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="课件学习" onclick="window.open('/flash_course/wushangchun4/index.html');"></td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          <tr>
          	<td width="2%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="50%" align="left" title="《生殖健康咨询师培训教程》《咨询技巧》《避孕节育咨询》《生殖健康咨询案例分析》（五）">（五）《生殖健康咨询师培训教程》《咨询技巧》《避孕节育...</td>
            <td width="30%" align="left" title="吴尚纯 国家人口计生委科学技术研究所研究员">吴尚纯 国家人口计生委科学技术研...</td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="视频学习" onclick="window.open('/entity/student_course_jichu_play.jsp?filepath=wushangcun5','vedioplay');"></td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="课件学习" onclick="window.open('/flash_course/wushangchun5/index.html');"></td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          <tr>
          	<td width="2%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="50%" align="left" title="《生殖健康咨询师培训教程》《咨询技巧》《避孕节育咨询》《生殖健康咨询案例分析》（六）">（六）《生殖健康咨询师培训教程》《咨询技巧》《避孕节育...</td>
            <td width="30%" align="left" title="吴尚纯 国家人口计生委科学技术研究所研究员">吴尚纯 国家人口计生委科学技术研...</td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="视频学习" onclick="window.open('/entity/student_course_jichu_play.jsp?filepath=wushangcun6','vedioplay');"></td>
            <td width="9%" align="center">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          <tr>
          	<td width="2%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="50%" align="left" title="生殖健康咨询职业道德（一）">（一）生殖健康咨询职业道德</td>
            <td width="30%" align="left" title="赵  然 中国财经大学教授">赵  然 &nbsp; 中国财经大学教授</td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="视频学习" onclick="window.open('/entity/student_course_jichu_play.jsp?filepath=zhaoran1','vedioplay');"></td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="课件学习" onclick="window.open('/flash_course/zhaoran/index.html');"></td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          <tr>
          	<td width="2%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="50%" align="left" title="生殖健康咨询职业道德（二）">（二）生殖健康咨询职业道德</td>
            <td width="30%" align="left" title="赵  然 中国财经大学教授">赵  然 &nbsp; 中国财经大学教授</td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="视频学习" onclick="window.open('/entity/student_course_jichu_play.jsp?filepath=zhaoran2','vedioplay');"></td>
            <td width="9%" align="center">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          <tr>
          	<td width="2%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="50%" align="left" title="生殖健康的健康教育与健康促进">生殖健康的健康教育与健康促进</td>
            <td width="30%" align="left" title="王培玉 北京大学教授">王培玉 北京大学教授</td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="视频学习" onclick="window.open('/entity/student_course_jichu_play.jsp?filepath=wangpeiyu','vedioplay');"></td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="课件学习" onclick="window.open('/flash_course/peikaiyan4/index.html');"></td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          <tr>
          	<td width="2%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="50%" align="left" title="《优生优育咨询》《孕期保健咨询》《出生缺陷干预》（上）">《优生优育咨询》《孕期保健咨询》《出生缺陷干预》（上）</td>
            <td width="30%" align="left" title="裴开颜 国家人口计生委科学技术研究所副研究员">裴开颜 国家人口计生委科学技术研...</td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="视频学习" onclick="window.open('/entity/student_course_jichu_play.jsp?filepath=peikaiyan1','vedioplay');"></td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="课件学习" onclick="window.open('/flash_course/peikaiyan1/index.html');"><br/><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="课件学习" onclick="window.open('/flash_course/peikaiyan2/index.html');"></td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          <tr>
          	<td width="2%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="50%" align="left" title="《优生优育咨询》《孕期保健咨询》《出生缺陷干预》（下）">《优生优育咨询》《孕期保健咨询》《出生缺陷干预》（下）</td>
            <td width="30%" align="left" title="裴开颜 国家人口计生委科学技术研究所副研究员">裴开颜 国家人口计生委科学技术研...</td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="视频学习" onclick="window.open('/entity/student_course_jichu_play.jsp?filepath=peikaiyan2','vedioplay');"></td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="课件学习" onclick="window.open('/flash_course/peikaiyan3/index.html');"></td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          <tr>
          	<td width="2%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="50%" align="left" title="生殖道感染防治（上）">生殖道感染防治（上）</td>
            <td width="30%" align="left" title="赵更力 北京大学主任医师">赵更力 北京大学主任医师</td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="视频学习" onclick="window.open('/entity/student_course_jichu_play.jsp?filepath=zhaogengli1','vedioplay');"></td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="课件学习" onclick="window.open('/flash_course/zhaogengli1/index.html');"></td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          <tr>
          	<td width="2%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="50%" align="left" title="生殖道感染防治（下）">生殖道感染防治（下）</td>
            <td width="30%" align="left" title="赵更力 北京大学主任医师">赵更力 北京大学主任医师</td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="视频学习" onclick="window.open('/entity/student_course_jichu_play.jsp?filepath=zhaogengli2','vedioplay');"></td>
            <td width="9%" align="center">&nbsp;</td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
          <tr>
          	<td width="2%" height="30" align="center"><img src="/entity/bzz-students/images/two/4.jpg" width="9" height="9" /></td>
            <td width="50%" align="left" title="性与生殖健康讲座">性与生殖健康讲座</td>
            <td width="30%" align="left" title="马晓年 清华大学玉泉医院主任医师">马晓年 清华大学玉泉医院主任医师</td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="视频学习" onclick="window.open('/entity/student_course_jichu_play.jsp?filepath=maxiaonian','vedioplay');"></td>
            <td width="9%" align="center"><input type="button" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" value="课件学习" onclick="window.open('/flash_course/maxiaonian/index.html');"></td>
          </tr>
          <tr>
            <td colspan="5"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
         </table>
            <td><br></td>
            </tr>
         	<tr align="center">
         		<td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg" colspan="8">
         			<table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
         					<tr>
         						
         					</tr>
         			</table>
         		</td>
         	</tr>
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