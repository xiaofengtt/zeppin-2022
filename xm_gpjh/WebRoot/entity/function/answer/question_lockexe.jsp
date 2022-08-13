<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../pub/noproxy.jsp"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>

<%@ include file="../pub/priv.jsp"%>
<%!
	String fixNull(String s)
	{
		if(s == null)
			return "";
		else
			return s;
	}
%>
<%

	String []id = request.getParameterValues("id");
	String islock = request.getParameter("islock");
	int ret = 0;
		    
	try
{
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);		
	if(id != null)
	{
		for(int i=0;i<id.length;i++)
		{
			if("0".equals(islock))
				ret += interactionManage.setUnLock(id[i]);
			if("1".equals(islock))
				ret += interactionManage.setLock(id[i]);
		}
	}
	else
	{
		out.println("<script>alert('请至少选定一个问题!');window.history.back();</script>");
	}
			
}
catch(Exception e)
{
	out.print(e.getMessage());
	return;
}
	if (ret > 0) {
%>
		<script language="javascript">
			alert("操作成功！");
			window.navigate("index.jsp");
		</script>
<%
	} else {
%>
		<script language="javascript">
			alert("操作失败！");
			window.history.back(-1);
		</script>
<%
	}
%>
