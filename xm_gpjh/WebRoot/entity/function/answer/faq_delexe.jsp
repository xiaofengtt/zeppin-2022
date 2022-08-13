<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.interaction.announce.*"%>
<jsp:directive.page import="com.whaty.platform.resource.ResourceFactory"/>
<jsp:directive.page import="com.whaty.platform.resource.BasicResourceManage"/>
<%@ include file="../pub/priv.jsp"%>

<%
String id = request.getParameter("id");
int count = 0;
try
{
	ResourceFactory fac = ResourceFactory.getInstance();
	BasicResourceManage manage = fac.creatBasicResourceManage();
	count = manage.deleteResource(id);
}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}

if(count == 1) {
%>
		<script language="javascript">
			alert("成功删除！");
			window.parent.location.reload();
		</script>
<%
	} else {
%>
		<script language="javascript">
			alert("删除不成功！");
			window.history.back(-1);
		</script>
<%
	}
%>