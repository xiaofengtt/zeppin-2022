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
        <td class="con" style="font-size:11.5pt"><div class="mianer"><b><font color="#800000">9、&nbsp;&nbsp;深度的服务支持</font></b><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为学员提供答疑辅导、学习交流、经验分享等服务支持；为企业提供内训定制、企业咨询、科技服务等全方位的服务支持。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(1)&nbsp;&nbsp; 在学员较为集中的地区、企业建立教学服务中心，为学员提供课程答疑和考前辅导等教学服务；<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(2)&nbsp;&nbsp; 成立清华中央企业班组长同学会，为班组长搭建一个“学习、交流、资源共享”的服务平台，定期组织开展有关班组建设的论坛、讲座、沙龙、经验交流会、联谊等活动；<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(3)&nbsp;&nbsp; 设置服务热线，为学员和企业提供及时、周到的服务；<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(4)&nbsp;&nbsp; 为企业搭建E-learning学习平台，提供精品课程资源，帮助企业建立自主培训体系，助力企业学习型组织建设；<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(5)&nbsp;&nbsp; 为企业提供内训定制和咨询服务。根据企业需求定制课程，推荐师资，组织专家为企业提供系列咨询服务；<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(6)&nbsp;&nbsp; 为企业提供清华校企合作科技服务。在科研项目、技术开发、企业管理、人才培养等方面为企业提供服务支持。
<br>
<b><font color="#800000">10、积极的鼓励政策</font></b><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;国资委和清华大学将出台系列鼓励政策，奖励在项目实施过程中有良好表现的企业和个人。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>(1)&nbsp;&nbsp;<font color="#800000">优秀学员评选</font>：</b>
参加项目学习并考试成绩优良的学员可参加此项评选。优秀学员的评选分为两级：企业级和国资委级（其中国资委级的优秀学员将从企业级中评选产生）。国资委和清华大学为其共同颁发荣誉证书，从中选拔优秀学员代表到清华大学参加学习参观活动，并有机会参加由国资委组织的出国学习考察活动。
<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>(2)&nbsp;&nbsp;<font color="#800000">优秀组织管理者评选</font>：</b>
参训企业项目负责人和管理员可参加此项评选。国资委和清华大学为其共同颁发荣誉证书，从中选拔部分获奖者代表到清华大学参加交流研讨活动，并有机会参加由国资委组织的出国学习考察活动。
<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>(3)&nbsp;&nbsp;<font color="#800000">优秀企业评选</font>：</b>
参训企业可参加此项评选。国资委和清华大学为其共同颁发荣誉证书和奖状，并为优秀企业提供清华培训基金，企业可选拔人员参加清华大学系列培训课程的学习。
<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>(4)&nbsp;&nbsp;<font color="#800000">优秀论文和案例征集评选</font>：</b>
对学员根据所学知识、结合自身实际工作而撰写的论文或案例进行征集评选。评出的优秀论文或案例将汇编成册，统一出版。
<div align=center class="font02"><a href="/web/bzz_index/xmjs/xmys01.jsp">[首页]</a>&nbsp;&nbsp;&nbsp;<a href="/web/bzz_index/xmjs/xmys02.jsp">[上一页]</a>&nbsp;&nbsp;&nbsp;<a href="/web/bzz_index/xmjs/xmys03.jsp">[末页]</a></div>
       </div> </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><img src="/web/bzz_index/xytd/images/bottom.jpg" width="699" height="15"></td>
  </tr>
</table>
</body>
</html>
