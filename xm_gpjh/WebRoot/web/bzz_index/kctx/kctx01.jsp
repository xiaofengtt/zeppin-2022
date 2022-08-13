<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.MyResultSet"/>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
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
String path1 = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";

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
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" style="border:solid 2 #9FC3FF; padding:5px; margin-top:10px;">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D7D7D7">
    <tr bgcolor="#E9F4FF">
        <td colspan=3 height="35" align="center" class="kctx_zi1"><font size=4><strong>基础类课程（76学时、考核课程）</strong></font></td>
      </tr>
      <tr bgcolor="#ECECEC">
        <td height="35" align="center" class="kctx_zi1"><strong>课程类别</strong></td>
        <td align="center" class="kctx_zi1"><strong>课程名称</strong></td>
        <td align="center" class="kctx_zi1"><strong>学时</strong></td>
      </tr>
      <tr>
        <td rowspan="4" align="center" bgcolor="#F5F5F5" class="kctx_zi1">管理基础系列课程</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">企业管理概览</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">2</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">企业战略认知</td>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">4</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">管理沟通实务</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">4</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">企业文化与班组团队管理</td>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">4</td>
      </tr>
      <tr>
        <td rowspan="10" align="center" bgcolor="#F5F5F5" class="kctx_zi1">管理技能与技巧系列
</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">认识班组长</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">4</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">班组基础管理</td>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">8</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">班组现场管理</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">8</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">班组计划与目标管理</td>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">4</td>
      </tr>
        <tr>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">班组质量管理</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">4</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">班组建设与班组长管理实战</td>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">8</td>
      </tr>
        <tr>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">班组经济核算</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">4</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">班组安全管理</td>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">4</td>
      </tr>
        <tr>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">班组绩效评估工具</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">4</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">班组设备与物料管理</td>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">4</td>
      </tr>
       <tr>
        <td rowspan="2" align="center" bgcolor="#F5F5F5" class="kctx_zi1">综合素养系列</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">领导力与执行力打造</td>
        <td align="center" bgcolor="#FFFFFF" class="kctx_zi2">6</td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">责任胜于能力</td>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2">4</td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>

