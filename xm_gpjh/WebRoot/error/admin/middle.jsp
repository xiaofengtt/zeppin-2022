<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.util.*"%>
<%@ include file="./pub/priv.jsp"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Java环境信息</title>
<link href="css.css" rel="stylesheet" type="text/css" />
</head>

<body topmargin="0" leftmargin="0" bottommargin="0" rightmargin="0" style="background-color:transparent" scroll="no">
<table width="100%" height="500" cellpadding="0" cellspacing="1" bgcolor="#000000">
  <tr>
    <td width="20%" bgcolor="#D4E4EB" class="zhengwen">java运行环境名称</td>
    <td width="80%" bgcolor="#D4E4EB" class="zhengwen"><%= SystemInfoUtil.getRuntimeName() %></td>
  </tr>
  <tr>
    <td bgcolor="#D4E4EB" class="zhengwen">java虚拟机版本</td>
    <td bgcolor="#D4E4EB" class="zhengwen"><%= SystemInfoUtil.getVmVersion() %></td>
  </tr>
  <tr>
    <td bgcolor="#D4E4EB" class="zhengwen">java运行环境版本</td>
    <td bgcolor="#D4E4EB" class="zhengwen"><%= SystemInfoUtil.getRuntimeVersion() %></td>
  </tr>
  <tr>
    <td bgcolor="#D4E4EB" class="zhengwen">java虚拟机提供商</td>
    <td bgcolor="#D4E4EB" class="zhengwen"><%= SystemInfoUtil.getVmVendor() %></td>
  </tr>
  <tr>
    <td bgcolor="#D4E4EB" class="zhengwen">java虚拟机信息</td>
    <td bgcolor="#D4E4EB" class="zhengwen"><%= SystemInfoUtil.getVmInfo() %></td>
  </tr>
  <!-- tr>
    <td bgcolor="#D4E4EB" class="zhengwen">java编译器</td>
    <td bgcolor="#D4E4EB"><%//SystemInfoUtil.getJavaCompiler() %></td>
  </tr-->
  <tr>
    <td bgcolor="#D4E4EB" class="zhengwen">应用环境根目录</td>
    <td bgcolor="#D4E4EB" class="zhengwen"><%= SystemInfoUtil.getAppEnviromentRoot() %></td>
  </tr>
  <tr>
    <td bgcolor="#D4E4EB" class="zhengwen">操作系统架构</td>
    <td bgcolor="#D4E4EB" class="zhengwen"><%= SystemInfoUtil.getOsArch() %></td>
  </tr>
  <tr>
    <td bgcolor="#D4E4EB" class="zhengwen">操作系统名称</td>
    <td bgcolor="#D4E4EB" class="zhengwen"><%= SystemInfoUtil.getOsName() %></td>
  </tr>
  <tr>
    <td height="40" bgcolor="#D4E4EB" class="zhengwen">java库路径</td>
    <td bgcolor="#D4E4EB" class="zhengwen"><%= SystemInfoUtil.getLibraryPath() %></td>
  </tr>
  <tr>
    <td bgcolor="#D4E4EB" class="zhengwen">java类路径</td>
    <td bgcolor="#D4E4EB" class="zhengwen"><%= SystemInfoUtil.getClassPth() %></td>
  </tr>
  <tr>
    <td bgcolor="#D4E4EB" class="zhengwen">用户时区</td>
    <td bgcolor="#D4E4EB" class="zhengwen"><%= SystemInfoUtil.getTimeZone() %></td>
  </tr>
  <tr>
    <td bgcolor="#D4E4EB" class="zhengwen">文件编码</td>
    <td bgcolor="#D4E4EB" class="zhengwen"><%= SystemInfoUtil.getFileEncoding() %></td>
  </tr>
  <tr>
    <td bgcolor="#D4E4EB" class="zhengwen">用户语言</td>
    <td bgcolor="#D4E4EB" class="zhengwen"><%= SystemInfoUtil.getLang() %></td>
  </tr>
</table>
</body>
</html>
