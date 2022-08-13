<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->
    <head>
        <meta charset="utf-8" />
        <title>首页</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1.0" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <meta name="MobileOptimized" content="320">

        <link href="../assets/css/style.css" rel="stylesheet" type="text/css" />
		<link href="../assets/css/themes/light.css" rel="stylesheet" type="text/css" />
		<link href="../assets/css/style-responsive.css" rel="stylesheet" type="text/css" />
		<link href="../assets/css/colorbox.css" rel="stylesheet" type="text/css">
		<link href="../assets/css/jquery-ui.css" rel="stylesheet" type="text/css" >
		<link href="../assets/css/metro/blue/jtable.css" rel="stylesheet" type="text/css" >
		<link href="../assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="../assets/css/columLists.css" type="text/css"/>
		<link rel="stylesheet" href="../assets/css/main.css" type="text/css"/>
		
		<script src="../assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
        <script src="../assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="../assets/plugins/jquery.cokie.min.js" type="text/javascript"></script>

        <script src="../assets/scripts/app.js" type="text/javascript"></script>
        <script type="text/javascript" src="../app/js/index.js"></script>
		<script type="text/javascript" src="../assets/scripts/main.js"></script>
    </head>
    <!-- END HEAD -->

    <!-- BEGIN BODY -->
    <body class="page-header-fixed">
        <jsp:include page="header.jsp"></jsp:include>
		<!--head end-->
		<div class="clearfix"></div>
		<!--left-->
			<div class="page-container">
            <!-- BEGIN SIDEBAR -->
            <jsp:include page="left_admin.jsp"></jsp:include>
            <!-- END SIDEBAR -->
            </div>
		<!--left end-->
		<jsp:include page="footer.jsp"></jsp:include>
        <!--[if lt IE 9]>
        <script src="assets/plugins/respond.min.js"></script>
        <script src="assets/plugins/excanvas.min.js"></script>
        <![endif]-->
    </body>
</html>