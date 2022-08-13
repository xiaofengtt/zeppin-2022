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
        <td align="center" class="tit">项目主要内容</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td background="/web/bzz_index/xytd/images/bg.jpg"><table width="99%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td class="con" style="font-size:11.5pt"><div class="mianer"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;项目主要内容包括以下几个方面：课程设计思路、课程目标 、课程内容 、课程师资 、课件表现形式 、学习方式 、教学质量保障、考试认证 。<br><br>
<b><font color="#800000">课程设计思路</font>：</b>项目课程是在全面分析班组长岗位管理能力要求和工作特点...  &nbsp;&nbsp;&nbsp;&nbsp;
<div align=right class="msfc_name1"><a href="/web/bzz_index/xmjs/subpage.jsp?type=_sjsl&title=课程设计思路" style="text-decoration:none;">[更多]</a> </div>
<br>
<b><font color="#800000">课程目标</font>：</b>通过对班组长岗位管理能力的培训，使班组长树立现代管理意识...  &nbsp;&nbsp;&nbsp;&nbsp;
<div align=right class="msfc_name1"><a href="/web/bzz_index/xmjs/subpage.jsp?type=_kcmb&title=课程目标" style="text-decoration:none;">[更多]</a></div>
<br>
<b><font color="#800000">课程内容</font>：</b>课程体系根据班组长在管理能力方面应具备知识的要求，按照知识...&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<div align=right class="msfc_name1"><a href="/web/bzz_index/xmjs/subpage.jsp?type=_kcnr&title=课程内容" style="text-decoration:none;">[更多]</a></div>
<br>
<b><font color="#800000">课程师资</font>：</b>充分利用清华大学教育品牌优势，根椐班组长岗位要求和工作...  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<div align=right class="msfc_name1"><a href="/web/bzz_index/xmjs/subpage.jsp?type=_kcsz&title=课程师资" style="text-decoration:none;">[更多]</a> </div>
<br>
<b><font color="#800000">课件表现形式</font>：</b>根据课程内容和特点，采用不同的技术，制作不同类型的...  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<div align=right class="msfc_name1"><a href="/web/bzz_index/xmjs/subpage.jsp?type=_bxxx&title=课件表现形式" style="text-decoration:none;">[更多]</a></div>
<br>
<b><font color="#800000">学习方式</font>：</b>学员以网络在线学习为主，辅之以辅导答疑和书面参考教材...  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<div align=right class="msfc_name1"><a href="/web/bzz_index/xmjs/subpage.jsp?type=_xxfs&title=学习方式" style="text-decoration:none;">[更多]</a></div>
<br>
<b><font color="#800000">教学质量保障</font>：</b>清华大学现代远程教育经过多年的探索与实践，积累了...  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<div align=right class="msfc_name1"><a href="/web/bzz_index/xmjs/subpage.jsp?type=_jxzlbz&title=教学质量保障" style="text-decoration:none;">[更多]</a> </div>
<br>
<b><font color="#800000">考试认证</font>：</b>学员学习时间满12个月，基础类课程修满76学时，可参加...  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<div align=right class="msfc_name1"><a href="/web/bzz_index/xmjs/subpage.jsp?type=_khrz&title=考试认证" style="text-decoration:none;">[更多]</a></div>
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
