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
	<link rel="stylesheet" href="./css/page.css" />
	<link rel="stylesheet" href="./css/table.css" />
	<link rel="stylesheet" href="./css/wagesListDetail.css" />

	<!--[if lte IE 8]>
		<script src="js/html5shiv.js"></script>
		<script src="js/respond.js"></script>
	<![endif]-->
</head>
<body>
	<input id="pageId" type="hidden" value="6529ef10-ff27-11e7-ac6d-fcaa14314cbe"/>
	<jsp:include page="header.jsp"/>
	<main>
		<jsp:include page="contentLeft.jsp"/>
		<div class="main-right">
			<div class="main-right-body box loadingOver">
				<h3 class="box-title"><a href="wagesList.jsp">发放记录</a><small></small></h3>
				<p class="item-num">

				</p>
				<table class="table" cellspacing="0" cellpadding="0">
					<tr class="first-tr">
						<th width="14%" class="td-padding-item">姓名</th>
						<th width="23%">身份证</th>
		                <th width="16%">手机</th>
						<th width="12%" class="sort">实发工资</th>
						<th width="11%">确认情况</th>
		                <th width="13%">发送情况</th>
						<th width="13%">反馈信息</th>
					</tr>
					<tbody id="tbody">

					</tbody>

				</table>
				<div id="pageTool">

				</div>
			</div>
			<!-- loading -->
			<div class="loadingDiv"></div>
		</div>
	</main>
	<jsp:include page="footer.jsp"/>
	<script src="./js/query.js"></script>
	<script src="./js/paging.js"></script>
	<script type="text/javascript" src="js/url.min.js"></script>
	<script src="./js/wagesListDetail.js?v=<%=Math.random()%>"></script>
</body>
</html>
