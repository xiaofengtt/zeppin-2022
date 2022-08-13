<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.whaty.platform.sso.web.servlet.UserSession"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.MyResultSet"/>
<jsp:directive.page import="com.whaty.platform.resource.ResourceFactory"/>
<jsp:directive.page import="com.whaty.platform.resource.BasicResourceManage"/>
<jsp:directive.page import="com.whaty.platform.resource.basic.Resource"/>
<jsp:directive.page import="com.whaty.platform.resource.basic.ResourceContent"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id = request.getParameter("id");
String course_id = "";
String open_course_id = "";
if(id!=null&&id.indexOf("|")>0)
{
	course_id = id.substring(0,id.indexOf("|"));
	open_course_id = id.substring(id.indexOf("|")+1);
}
request.getSession().setAttribute("firstPage", "/entity/function/answer/question_add.jsp");
%>
<script language="javascript">
	window.top.location.href="<%=basePath %>/sso/bzzinteraction_InteractionStuManage.action?course_id=<%=course_id%>&opencourseId=<%=open_course_id%>";
</script>