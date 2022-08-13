 <%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<% response.setHeader("expires", "0"); %>
<%
String path = request.getContextPath();
%>
<html>
	<head>
		
			<link rel="stylesheet" type="text/css" href="<%=path %>/js/extjs/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="<%=path %>/js/extjs/css/ext-ext.css" />
		<link rel="stylesheet" type="text/css" href="<%=path %>/js/extjs/whatyExtjs.css" />
		<link rel="stylesheet" type="text/css" href="<%=path %>/js/extjs/examples/Datetime/datetime.css" />
		<script type="text/javascript" src="<%=path %>/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="<%=path %>/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="<%=path %>/js/extjs/examples/Datetime/Datetime.js"></script>
		<script type="text/javascript" src="<%=path %>/js/extjs/pub/ext-lang-zh_CN.js"></script> 
		<script type="text/javascript" src="<%=path %>/FCKeditor/fckeditor.js"></script>
		<script type="text/javascript" src="<%=path %>/js/ajax.js"></script>
		<script type="text/javascript" src="<%=path %>/js/extjs/whatyExtjs.js"></script>
		<script type="text/javascript" src="<s:property value="getMyJavascript()" escape="false"/>"> </script>
		<script type="text/javascript" src="<s:property value="getJsAction()" escape="false"/>"> </script>
		<title><s:property value="getGridConfig().getTitle()" escape="false"/></title>
	</head>
	<body id="main_content" leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0">
		<table width="100%" height="100%" bgcolor="#dfecf0">
			<tr>
				<td colspan="3" height="8"></td>
			</tr>
			<tr>
				<td width="3%"></td>
				<td valign="top">
					<div id="user-defined-content"></div>
					<div id="searchtool"></div>
					<div id="model-grid"></div>
					<div id="note"><s:property value="getGridConfig().getNote()" escape="false"/></div>
					<div id="exportexcel"></div>
				</td>
				<td width="3%"></td>
			</tr>
			<tr>
				<td colspan="3" height="8"></td>
			</tr>
		</table>
	</body>
</html>
