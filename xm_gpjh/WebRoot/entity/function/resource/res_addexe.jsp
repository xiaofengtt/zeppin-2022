<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*,com.whaty.platform.entity.recruit.*"%>
<%@ page import="com.whaty.platform.resource.basic.*,com.whaty.platform.resource.*"%>
<%@ page import="com.whaty.util.string.*"%>
<%@ include file="../pub/priv.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="../css/admincss.css" rel="stylesheet" type="text/css">
</head>
<%
	String res_name = request.getParameter("res_name");
	String res_language = request.getParameter("res_language");
	String res_description = request.getParameter("res_description");
	String res_keywords = request.getParameter("res_keywords");
	String res_creatuser = request.getParameter("res_creatuser");
	String res_detail = request.getParameter("body");
	StrManage strManage=StrManageFactory.creat(res_detail); 
	res_detail = strManage.htmlEncode(); 
	
	String type_id = request.getParameter("type_id");
	String dir_id = request.getParameter("dir_id");
	
	String[] fieldNameList = request.getParameterValues("field_name");
	String[] fieldValueList = request.getParameterValues("field_value");
	
	String xml = "<resource><item><name>内容</name><content>" + res_detail + "</content></item>";
	if(fieldValueList!=null)
	{
		for(int i=0; i<fieldNameList.length; i++) {
			xml = xml + "<item><name>" + fieldNameList[i] + "</name><content>" + fieldValueList[i] + "</content></item>";
		}
	}
	xml += "</resource>";
	
	ResourceFactory fac = ResourceFactory.getInstance();
	BasicResourceManage manage = fac.creatBasicResourceManage();
	
	 int i = manage.addResource(res_name, res_language,
			res_description, res_keywords, res_creatuser,
			type_id, dir_id, xml);
	if(i >0) {
%>
<script>
	alert("添加成功");
	opener.history.go(0);
	window.close();
</script>
<%
	} else {
%>
<script>
	alert("添加失败");
	window.history.back();
</script>
<%
	}
%>
<body leftmargin="0" topmargin="0" class="scllbar">
</body>
</html>
