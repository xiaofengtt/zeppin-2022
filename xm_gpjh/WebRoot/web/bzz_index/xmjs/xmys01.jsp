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
        <td class="con" style="font-size:11.5pt"><div class="mianer"><b><font color="#800000">1、&nbsp;&nbsp;创新的培训模式</font></b>  <br> 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;项目充分整合了高校优质教育资源、办学经验以及现代远程教育技术优势，具有覆盖范围广、课程水平高、学习方式灵活、成本相对低等特点，是一种全新的班组长培训模式。它将有效解决目前班组长培训过程中存在的难点，并使班组长岗位管理能力的培训真正实现“大规模、可持续、有实效、低成本、高水平、能推广”。对中央企业班组建设和班组长培训作用重大、意义深远。在此基础上，将积极探索建立与培训项目相配套的中央企业班组长岗位资格认证体系，这将为班组长职业化道路发展奠定基础，具有一定的创新意义。
<br>
<b><font color="#800000">2、&nbsp;&nbsp;系统的课程体系</font></b><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;课程设计是以国资委《关于加强中央企业班组建设的指导意见》中对班组长的任职条件和总体目标要求为基础，以培养班组长通用管理能力和素质为目标，与企业自有的以岗位专业技能为主的个性化培训形成有效互补。
课程体系具有较强的科学性、系统性、针对性、实用性和前瞻性。内容标准、规范，对企业班组长培训具有一定的指导和示范作用。<br>
<b><font color="#800000">3、&nbsp;&nbsp;优质的师资资源</font></b><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;充分利用清华大学教育品牌优势，根椐班组长岗位要求和工作特点，按照“实战为主、兼顾理论”的原则，整合国内外一流的师资资源。所选师资在清华培训体系中都具有良好的教学评价（综合评价是优秀等级的）。建立师资评价体系，实行教师准入与退出机制，以确保课程的质量和水平。
<br>
<b><font color="#800000">4、&nbsp;&nbsp;丰富的课件形式</font></b><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;课件的制作运用到大量的网页设计、 Flash技术、多媒体技术等，课件表现形式丰富多彩、内容生动形象、页面设计精美，具有较强的视听觉效果，符合学员学习习惯，可达到较好的教学效果。
<br>
<div align=center class="font02"><a href="/web/bzz_index/xmjs/xmys02.jsp">[下一页]</a>&nbsp;&nbsp;&nbsp;<a href="/web/bzz_index/xmjs/xmys03.jsp">[末页]</a></div>
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
