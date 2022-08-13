<?xml version="1.0"?>
<%@ page language="java"  pageEncoding="UTF-8"%>
<% response.setHeader("expires", "0"); %>
<%@ page contentType="text/xml;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*,com.whaty.platform.entity.recruit.*"%>
<%@ page import="com.whaty.platform.resource.basic.*,com.whaty.platform.resource.*"%>
<%
	String parentid = request.getParameter("parentid");
	ResourceFactory resFactory = ResourceFactory.getInstance();
	BasicResourceManage resManage = resFactory.creatBasicResourceManage();
	List rootList = resManage.getResourceDirs(null, null, parentid, null, null, null);
	List resList = resManage.getResources(null, null, null, null, null, null, null, parentid);
%>
<tree>
	<%
		for(int i=0; i<rootList.size(); i++) {
			ResourceDir dir = (ResourceDir)rootList.get(i);
	%>
   <tree text="<%=dir.getName()%>" target="content_frame" src="xml/tree.jsp?id=<%=dir.getId()%>" />
   <%
   		}
		for(int i=0; i<resList.size(); i++) {
			Resource res = (Resource)resList.get(i);
	%>
   <tree text="<%=res.getTitle()%>" action="res_info.jsp?id=<%= res.getId() %>" target="content_frame" />
   <%
   		}
   	%>
</tree>