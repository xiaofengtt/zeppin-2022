<?xml version="1.0"?>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/xml;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*,com.whaty.platform.entity.recruit.*"%>
<%@ page import="com.whaty.platform.resource.basic.*,com.whaty.platform.resource.*"%>
<%
	String id = request.getParameter("id");
	ResourceFactory resFactory = ResourceFactory.getInstance();
	BasicResourceManage resManage = resFactory.creatBasicResourceManage();

	ResourceDir dir = resManage.getResourceDir(id);	
	List subDirList = dir.getSubDir();
	List resList = dir.getResourceList();
%>
<tree>
	<%
		for(int i=0; i<subDirList.size(); i++) {
			ResourceDir subDir = (ResourceDir)subDirList.get(i);
	%>
   <tree text="<%=subDir.getName()%>" src="xml/tree.jsp?id=<%=subDir.getId()%>" />
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