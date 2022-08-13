<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.util.*,com.whaty.platform.logmanage.*" %>
<%@ include file="./pub/priv.jsp"%>

<%
	String userid = request.getParameter("userid");
	String behavior = request.getParameter("behavior");
	String operateStartTime = request.getParameter("operateStartTime");
	String operateEndtime = request.getParameter("operateEndtime");
	String status = request.getParameter("status");
	String logtype = request.getParameter("logtype");
	String priority = request.getParameter("priority");
	
	LogFactory factory = LogFactory.getInstance();
	LogManage logManage = factory.creatLogManage();
	int result = logManage.backUpLogEntities(null, userid, operateStartTime, operateEndtime, behavior, 
		status, null, logtype, priority);
	if(result > 0) {
%>
<script>
	alert("备份成功");
	location.href="log_search.jsp";
</script>
<%
	} else {
%>
<script>
	alert("备份失败");
	window.history.back();
</script>
<%
	}
%>