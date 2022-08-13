<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*,com.whaty.platform.entity.recruit.*"%>
<%@ page import="com.whaty.platform.resource.basic.*,com.whaty.platform.resource.*"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="css/admincss.css" rel="stylesheet" type="text/css">
	</head>
	<%
		ResourceFactory resFactory = ResourceFactory.getInstance();
		BasicResourceManage resManage = resFactory.creatBasicResourceManage();

		String id = request.getParameter("id");
		Resource res = resManage.getResource(id);
	%>
	<body leftmargin="0" topmargin="0" class="scllbar">
			<table width="90%" border="0" cellpadding="0" cellspacing="0">

				<tr valign="bottom" class="postFormBox">
					<td width="82" valign="bottom">名称</td>
					<td></td>
					<td><%= res.getTitle() %></td>
				</tr>
				
				<tr valign="bottom" class="postFormBox">
					<td width="82" valign="bottom">语言</td>
					<td></td>
					<td><%= res.getLanguage() %></td>
				</tr>
				
				<tr valign="bottom" class="postFormBox">
					<td width="82" valign="bottom">描述</td>
					<td></td>
					<td><%= res.getDiscription() %></td>
				</tr>
				
				<tr valign="bottom" class="postFormBox">
					<td width="82" valign="bottom">关键字</td>
					<td></td>
					<td><%= res.getKeyWords() %></td>
				</tr>
				
				<tr valign="bottom" class="postFormBox">
					<td width="82" valign="bottom">创建者</td>
					<td></td>
					<td><%= res.getCreatUser() %></td>
				</tr>
				
				<tr valign="bottom" class="postFormBox">
					<td width="82" valign="bottom">资源类型</td>
					<td></td>
					<td><%= res.getResourceType().getName() %></td>
				</tr>
				
				<tr valign="bottom" class="postFormBox">
					<td width="82" valign="bottom">所属资源目录</td>
					<td></td>
					<td><%= res.getResourceDir().getName() %></td>
				</tr>
				<%
					List contentList = res.getResourceContentList();
					for(int i=0; i<contentList.size(); i++) {
						ResourceContent content = (ResourceContent)contentList.get(i);
				%>
				<tr valign="bottom" class="postFormBox">
					<td width="82" valign="bottom"><%= content.getName() %></td>
					<td></td>
					<td><%= content.getContent() %></td>
				</tr>
				<%
					}
				%>
			</table>
	</body>
</html>
