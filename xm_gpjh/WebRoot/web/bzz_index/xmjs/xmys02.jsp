<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>生殖健康咨询师培训网</title>
<link href="/web/bzz_index/style/xytd.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/web/bzz_index/js/wsMenu.js"></script>
<script language="javascript">
function resize()
{
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
}
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>
<body bgcolor="#FFFFFF">
<table width="699" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="34" background="/web/bzz_index/xytd/images/title.jpg"><table width="100%" height="34" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td align="center" class="tit">项目优势及鼓励政策</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td background="/web/bzz_index/xytd/images/bg.jpg"><table width="99%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td class="con" style="font-size:11.5pt"><div class="mianer">
<b><font color="#800000">5、&nbsp;&nbsp;灵活的学习方式</font></b><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为学员提供界面友好、人性化的网络学习环境，操作简便。学员可根据自己的实际情况和需求，随时随地、方便灵活地在线点播课程进行自主学习。
网络平台具有课程点播、辅导答疑、课堂作业、在线自测、资料下载等强大的学习功能。系统对学员端设备和网络条件要求简单，可方便上网学习。
<br>
<b><font color="#800000">6、&nbsp;&nbsp; 稳健的平台系统</font></b><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在清华大学已有采用“三网合一”先进远程技术的“清华远程学堂”平台系统的基础上，根据班组长学习特点和需求，开发了项目网络平台。系统采用了模块化的结构设计和先进的流媒体技术，具有较强的可靠性、稳定性和可扩展性。系统管理功能强大、运行稳定流畅。企业可利用平台管理功能，对本企业班组长培训进行数据统计、分析和研究，为企业班组建设和班组长培训提供科学决策依据，推进企业人力资源信息化管理和建设。
<br>
<b><font color="#800000">7、&nbsp;&nbsp; 严格的质量控制</font></b><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在整个学习过程中，包括课程学习、辅导答疑、课程作业、在线自测、参考教材、考核认证、课程评估、信息反馈等各个环节，建立了一整套严格、规范的质量控制和保障体系，从而确保了教学质量和效果。
<br>
<b><font color="#800000">8、&nbsp;&nbsp;权威的考核认证</font></b><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;国资委和清华大学组织全国统一考试。考试合格者，由国资委和清华大学联合颁发结业证书，具有权威性。证书可在清华大学网站上查询。
国资委将积极探索建立与培训项目相配套的中央企业班组长岗位资格认证体系，这将为班组长职业化道路发展奠定基础。
<div align=center class="font02"><a href="/web/bzz_index/xmjs/xmys01.jsp">[首页]</a>&nbsp;&nbsp;&nbsp;<a href="/web/bzz_index/xmjs/xmys01.jsp">[上一页]</a>&nbsp;&nbsp;&nbsp;<a href="/web/bzz_index/xmjs/xmys03.jsp">[下一页]</a>&nbsp;&nbsp;&nbsp;<a href="/web/bzz_index/xmjs/xmys03.jsp">[末页]</a></div>
</div></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><img src="/web/bzz_index/xytd/images/bottom.jpg" width="699" height="15"></td>
  </tr>
</table>
</body>
</html>
