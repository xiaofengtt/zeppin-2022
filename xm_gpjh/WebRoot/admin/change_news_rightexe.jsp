<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.info.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.database.oracle.standard.info.user.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.*,
				com.whaty.platform.*"%>
<%@ page import="com.whaty.platform.Exception.*"%>
<%@ include file="./pub/priv.jsp"%>

<%
	int count = 0;

	String id = request.getParameter("id");
	String name = request.getParameter("name");
	String type = request.getParameter("type");
	if(type == null || type.equals("null"))
		type = "";

	String[] page_news_type_ids = request.getParameterValues("page_news_type_ids");
	String[] news_type_ids = request.getParameterValues("listMultiAction");
	try{
	try {
	PlatformFactory platformfactory = PlatformFactory.getInstance();
	InfoManage infoManage = platformfactory.creatInfoManage();
		count = infoManage.updateInfoRight(id,page_news_type_ids, news_type_ids );
	}
	catch(PlatformException e)
	{
		%>
		<script>
			alert("<%=e.toString().trim()%>");
			window.history.back(-1);
		</script>
		<%
		return;
	}
 	} catch(Exception e) {
		out.print(e.getMessage());
		return;
	}
	
	if (count > 0) {
%>
		<script language="javascript">
			alert("指定成功！");
			window.history.back(-1);
		</script>
<%
	} else {
%>
		<script language="javascript">
			alert("指定不成功！");
			window.history.back(-1);
		</script>
<%
	}
%>