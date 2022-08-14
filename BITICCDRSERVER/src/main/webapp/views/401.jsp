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
		<meta charset="utf-8"/>
		<title>401 未授权错误</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
		<link href="views/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="views/css/error.css" rel="stylesheet" type="text/css" />
	</head>
	<body class="page-500-full-page">
		<div class="row">
			<div class="col-md-12 page-500">
				<div class="number">401</div>
				<div class="details">
					<h3>未授权</h3>
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