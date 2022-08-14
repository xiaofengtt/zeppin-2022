<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="./css/base.css" />
	<link rel="stylesheet" href="./css/header.css" />
	<link rel="stylesheet" href="./css/paging.css" />
	<link rel="stylesheet" href="./css/main.css" />
	<link rel="stylesheet" href="./css/page.css" />
	<link rel="stylesheet" href="./css/table.css" />
	<link rel="stylesheet" href="./css/superAdminList.css" />

	<!--[if lte IE 8]>
		<script src="js/html5shiv.js"></script>
		<script src="js/respond.js"></script>
	<![endif]-->
</head>
<body>
	<input id="pageId" type="hidden" value="c6c28908-ff27-11e7-ac6d-fcaa14314cbe"/>
	<jsp:include page="header.jsp"/>
	<main>
		<jsp:include page="contentLeft.jsp"/>
		<div class="main-right">
			<div class="main-right-body box loadingOver">
				<div class="search-bar">
					<div class="search-box">
						<h3>管理员设置</h3>
					</div>
					<div class="btn-box">
						<a class="btn" id="add">添加管理员</a>
					</div>
				</div>

				<table cellspacing="0" cellpadding="0" class="table">
					

				</table>
				<div id="pageTool">

				</div>
			</div>
			
			<!-- loading -->
			<div class="loadingDiv"></div>
			
		</div>
	</main>
	<div class="bg bg-add">
			<div class="modal">
				<div class="modal-header">
					<h3 class="box-title iframe-title">添加管理员</h3>
					<a id="modal-close"></a>
				</div>
				<div class="modal-body">
					<form:form id="addForm" action="../rest/qcb/companyAdmin/add" method="post">
						<div class="item">
							<span class="item-title">
								<i class="color-red">*</i>
								姓名：
							</span>
							<input type="text" autocomplete="off" name="name" placeholder="请输入姓名" />
							<p class="tips color-red">请填写姓名</p>
						</div>
						<div class="item">
							<span class="item-title">
								<i class="color-red">*</i>
								手机：
							</span>
							<input type="text" autocomplete="off" name="mobile" placeholder="请输入手机号" />
							<p class="tips color-red">请填写正确的手机号</p>
						</div>
						<div class="item">
							<span class="item-title">
								<i class="color-red">*</i>
								角色：
							</span>
							<input type="text" autocomplete="off" name="role" placeholder="请输入角色" />
							<p class="tips color-red">请填写角色</p>
						</div>
						<div class="item">
							<span class="item-title">
								<i class="color-red">*</i>
								权限：
							</span>
							<div class="check-box" id="add-check-box">
								
							</div>
							<p class="tips color-red">请选择权限</p>
						</div>
					</form:form>
				</div>
				<input type="button" value="保存" class="sub" id="modal-add" />
			</div>
		</div>

		<div class="bg bg-edit">
			<div class="modal">
				<div class="modal-header">
					<h3 class="box-title iframe-title">修改管理员信息</h3>
					<a id="modal-close-edit"></a>
				</div>
				<div class="modal-body">
					<form:form id="editForm" action="../rest/qcb/companyAdmin/edit" method="post">
						<input type="hidden" name="uuid" value="" />
						<div class="item">
							<span class="item-title">
								<i class="color-red">*</i>
								姓名：
							</span>
							<input type="text" autocomplete="off" class="nameEdit" readonly="readonly" style="background:#eee;"/>
						</div>
						<div class="item">
							<span class="item-title">
								<i class="color-red">*</i>
								手机：
							</span>
							<input type="text" autocomplete="off" class="mobileEdit" readonly="readonly" style="background:#eee;" />
						</div>
						<div class="item">
							<span class="item-title">
								<i class="color-red">*</i>
								角色：
							</span>
							<input type="text" autocomplete="off" class="roleEdit" name="role" placeholder="请输入角色" />
							<p class="tips color-red">请填写角色</p>
						</div>
						<div class="item">
							<span class="item-title">
								<i class="color-red">*</i>
								权限：
							</span>
							<div class="check-box" id="edit-check-box">
								
							</div>
							<p class="tips color-red">请填写权限</p>
						</div>
					</form:form>
				</div>
				<input type="button" value="保存" class="sub" id="modal-edit" />
			</div>
		</div>
	<jsp:include page="footer.jsp"/>
	<script src="./js/query.js"></script>
	<script src="./js/paging.js"></script>
	<script src="./js/superAdminList.js?v=<%=Math.random()%>"></script>
</body>
</html>