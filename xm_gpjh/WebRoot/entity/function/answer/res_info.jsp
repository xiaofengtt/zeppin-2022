<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*,com.whaty.platform.entity.recruit.*"%>
<%@ page import="com.whaty.platform.resource.basic.*,com.whaty.platform.resource.*"%>
<%@ page import="com.whaty.util.string.*"%>
<%@ include file="../pub/priv.jsp"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>

<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals("NULL"))
			str = "";
			return str;
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="css/admincss.css" rel="stylesheet" type="text/css">
	</head>
	<%
		ResourceFactory resFactory = ResourceFactory.getInstance();
		BasicResourceManage resManage = resFactory.creatBasicResourceManage();
		
		//UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		String id = request.getParameter("id");
		Resource res = resManage.getResource(id);
	%>
	<body leftmargin="0" topmargin="0" class="scllbar">
			<table width="90%" border="0" cellpadding="0" cellspacing="0">

				<tr valign="bottom" class="postFormBox">
					<td width="82" valign="bottom">名称</td>
					<td></td>
					<td><%= fixnull(res.getTitle()) %></td>
				</tr>
				
				<tr valign="bottom" class="postFormBox">
					<td width="82" valign="bottom">语言</td>
					<td></td>
					<td><%= fixnull(res.getLanguage()) %></td>
				</tr>
				
				<tr valign="bottom" class="postFormBox">
					<td width="82" valign="bottom">描述</td>
					<td></td>
					<td><%= fixnull(res.getDiscription()) %></td>
				</tr>
				
				<tr valign="bottom" class="postFormBox">
					<td width="82" valign="bottom">关键字</td>
					<td></td>
					<td><%= fixnull(res.getKeyWords()) %></td>
				</tr>
				
				<tr valign="bottom" class="postFormBox">
					<td width="82" valign="bottom">创建者</td>
					<td></td>
					<td><%= fixnull(res.getCreatUser()) %></td>
				</tr>
				<!-- 
				<tr valign="bottom" class="postFormBox">
					<td width="82" valign="bottom">资源类型</td>
					<td></td>
					<td><%= fixnull(res.getResourceType().getName()) %></td>
				</tr>
				 -->
				<tr valign="bottom" class="postFormBox">
					<td width="82" valign="bottom">所属资源目录</td>
					<td></td>
					<td><%= fixnull(res.getResourceDir().getName()) %></td>
				</tr>
				<%
					List contentList = res.getResourceContentList();
					ResourceContent detail = (ResourceContent)contentList.get(0);
					//StrManage strManage=StrManageFactory.creat(detail.getContent());
				%>
				<tr valign="bottom" class="postFormBox">
					<td width="82" valign="bottom"><%= detail.getName() %></td>
					<td></td><%String content1 = detail.getContent();//System.out.println("content1->"+content1); %>
					<td><a href="faq_detail.jsp?resId=<%=id %>">查看详细内容</a></td>
				<% %>
				</tr>
				<%
					if("teacher".equalsIgnoreCase(userType)&&us.getRoleId().equals("3")) {
				 %>
				<tr valign="bottom" class="postFormBox">
					<td width="82" valign="bottom">操作</td>
					<td></td>
					<td><a href="faq_delexe.jsp?id=<%=res.getId()%>" onclick="return window.confirm('确定删除吗？')">删除</a> </td>
				</tr>
				<% } %>
				
				
				<%
					for(int i=1; i<contentList.size(); i++) {
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
