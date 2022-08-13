<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
   <title>无标题文档</title> 
   
	<script type="text/javascript">
	var pas =<%=(String)session.getAttribute("loginid")%>
	function first(){
	window.location="<%=basePath%>/entity/workspaceStudent/bzzstudent_toPlanCourses.action";
	//window.alert(pas);
	/*	if(window.confirm("您是第一次登陆本学习系统,建议修改密码\n\n您也可以通过个人信息页面去修改你的密码\n\n点击[确定]将转到密码修改页面,点[取消]将直接进入首页!")){
			window.location="<%=basePath%>/entity/workspaceStudent/bzzstudent_toPassword.action";
		}else{
			window.location="<%=basePath%>/entity/workspaceStudent/bzzstudent_toPlanCourses.action";
		}
		*/
	}
	</script>
</head>
<body onload="first();">
</body>
</html>
