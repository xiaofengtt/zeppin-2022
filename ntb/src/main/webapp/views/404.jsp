<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en" class="no-js">
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<title>404 错误</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
		<link href="views/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="views/css/error.css" rel="stylesheet" type="text/css" />
	</head>
	<body class="page-404-full-page">
	    <div class="row">
	        <div class="col-md-12 page-404">
	            <div class="number">404</div>
	            <div class="details">
	                <h3>找不到当前页面</h3>
	            </div>
	        </div>
	    </div>
		<script>
		jQuery(document).ready(function() {
			App.init();
		});
		</script>
	</body>
</html>