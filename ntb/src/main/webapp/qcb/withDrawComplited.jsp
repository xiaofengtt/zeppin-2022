<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="./css/base.css" />
	<link rel="stylesheet" href="./css/paging.css" />
	<link rel="stylesheet" href="./css/withDrawComplited.css" />

	<!--[if lte IE 8]>
		<script src="js/html5shiv.js"></script>
		<script src="js/respond.js"></script>
	<![endif]-->
</head>
<body>
	<!-- <input id="pageId" type="hidden" value="ffffffff-ffff-ffff-ffff-ffffffffffff"/> -->
	<%-- <jsp:include page="header.jsp"/>
	<main>
		<jsp:include page="contentLeft.jsp"/>
		<div class="main-right">
			
		</div>
	</main>
	 --%>
	<%-- <jsp:include page="footer.jsp"/> --%>
	<script src="js/jquery-1.11.1.js"></script>		
	<script src="./layer-v3.0.1/layer/layer.js"></script>
	<script>
		layer.msg("您没有该页面权限！", {
	        time: 2000
	    },function(){
	    	window.history.go(-1);
	    }); 
	</script>
</body>
</html>