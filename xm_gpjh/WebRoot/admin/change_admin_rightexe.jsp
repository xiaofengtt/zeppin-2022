<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.entity.BasicRightManage"%>
<%@ page import="java.io.File,com.whaty.platform.entity.*,com.whaty.platform.entity.right.*" %>
<%@ page import="com.whaty.platform.config.PlatformConfig" %>
<%@ include file="./pub/priv.jsp"%>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals("null"))
			str = "";
			return str;
	}
%>

<%
	PlatformFactory platformfactory = PlatformFactory.getInstance();
	BasicRightManage rightManage = platformfactory.creatBasicRightManage();
	String id=fixnull(request.getParameter("id"));
	String name=fixnull(request.getParameter("name"));
	String model_id[] = request.getParameterValues("model_ids");
	List right = new ArrayList();
	if(model_id==null)
	{
	%>
	<Script>
		alert("没有选择任何权限！");
		window.history.back();
	</script>	
	<%
	}
	for(int i=0;model_id!=null && i<model_id.length;i++)
	{
		String right_id[] = request.getParameterValues("right_id_"+model_id[i]);
		for(int j=0;right_id!=null && j<right_id.length;j++)
		{
			right.add(right_id[j]);
		}
	}
	int count=0;
	count=rightManage.updateRight(id,right);

	if (count < 1)
	{
	%>
	<script language="javascript">
	alert("修改失败！");
	window.history.back();
	</script>
	<%
	}
	else
	{
	%>
	<script language="javascript">
	alert("修改成功!");
	window.navigate("right_admin.jsp");
</script>

<%}%>