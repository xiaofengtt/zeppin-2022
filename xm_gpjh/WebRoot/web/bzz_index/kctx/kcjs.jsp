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
<%
	String title="";
	String note="";
	dbpool db = new dbpool();
	MyResultSet rs = null;
	String sql="";
	
	if(title.equals(""))
		title="课程体系简介";
 %>
<body bgcolor="#FFFFFF">
<table width="699" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="34" background="/web/bzz_index/xytd/images/title.jpg"><table width="100%" height="34" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td align="center" class="tit"><%=title %></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td background="/web/bzz_index/xytd/images/bg.jpg"><table width="99%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td class="con" style="font-size:11.5pt"><div class="mianer">课程体系根据班组长在管理能力方面应具备知识的要求，按照知识结构层次分为两大类：<br>
<b><a href="<%=basePath %>/web/bzz_index/kctx/kctx.jsp?src=kctx01.jsp&menu=menu1" target="_parent">基础类课程</a></b>：班组长应掌握的基础性课程，共计76学时，是考试课程。是班组长应具备的应知、应会的知识。课程包括：企业战略认知、班组现场管理、和谐劳动关系构建及劳动合同法解读等。<br>
<b><a href="<%=basePath %>/web/bzz_index/kctx/kctx.jsp?src=kctx02.jsp&menu=menu2" target="_parent">提升类课程</a></b>：根据不同岗位的需求，提升类课程是供优秀的班组长、企业基层管理者在工作中进一步提升而选择学习的课程（含名家讲座），共计86学时，包括在教学服务站举办的面授课程。课程包括：技术革新与班组创新、时间管理、职业道德塑造等。
<br><br>在课程体系的两大类中，每个大类都可以分为三个系列：<br>
<b>管理基础系列</b>：是企业管理的基础课程，旨在让班组长树立现代管理意识，掌握企业管理的基本知识，提升执行力、构建和谐团队以及沟通协调的能力。<br>
<b>管理技能与技巧系列</b>：是培训课程中的重要内容，旨在使班组长掌握班组生产与管理的技能与技巧，精通岗位职能和业务流程，帮助其改善工作质量，提高解决问题的能力。<br>
<b>综合素养系列</b>：是培养班组长个人职业素质和文化修养的课程，旨在培养班组长良好的职业道德，高尚的情操，增强工作责任心，塑造积极工作心态，全面提升班组长综合素养。</div>
</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><img src="/web/bzz_index/xytd/images/bottom.jpg" width="699" height="15"></td>
  </tr>
</table>
</body>
</html>
