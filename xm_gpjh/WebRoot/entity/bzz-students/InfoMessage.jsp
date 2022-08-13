<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script type="text/javascript">
  		 var operateresult = '<s:property value='operateresult'/>';
  		if(operateresult=='1'){
  				alert("修改成功！");
  				window.location="/entity/workspaceStudent/bzzstudent_StudentInfo.action";
  		}else{
  			alert("修改失败!");
  			window.history.back();
  		}
  	  </script>
	</head>
	<body>
	</body>
</html>
