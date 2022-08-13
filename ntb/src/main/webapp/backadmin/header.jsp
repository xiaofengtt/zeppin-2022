<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<link rel="stylesheet" href="css/bootstrap.css" />
		<link rel="stylesheet" href="css/bootstrap2.css" />
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/colorbox.css" />
		<link rel="stylesheet" href="css/paging.css">
		<script type="text/javascript" src="http://cdn.webfont.youziku.com/wwwroot/js/wf/youziku.api.min.js"></script>
	</head>
	<body>
		<div class="header">
			<div class="header-inner">
				<div class="header-left">
					<h1 class="text-center"><img src="img/afterlogo.png" alt="logo"/></h1>
					<p>后台管理系统</p>
					<div class="clear"></div>
				</div>
				<div class="header-right">
					<span class="modifyPassword"><a class="btn-reset" href="resetPwd.jsp?uuid=${sessionScope.currentOperator.uuid}">修改密码</a></span>
					<span class="layout"><a href="../rest/backadmin/operator/logout">退出</a></span>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		<script>
			$youziku.load("h1,.locationLeft,.PFSCR,.order,.list-item-ft,.fundName", "c6da775181ca42f7a7c023bb366de971", "PingFangSC_R");
			$youziku.load("#search", "40c55e59f78848f89c2bfe9b105e4fb6", "SiYuan-ExtraLight");//思源黑体ExtraLight 
			$youziku.draw(); 
		</script>
		<script type="text/javascript" src="js/jquery-1.11.1.js" ></script>
		<script type="text/javascript" src="js/bootstrap.js" ></script>
		<script type="text/javascript" src="js/style.js" ></script>
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/query.js"></script>
		<script type="text/javascript" src="js/paging.js"></script>
		<script type="text/javascript" src="js/header.js" ></script>
	</body>
</html>