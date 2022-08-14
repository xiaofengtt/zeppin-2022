<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="./css/base.css" />
	<link rel="stylesheet" href="css/fiscalService.css" />
	<!--[if lte IE 8]>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.js"></script>
<![endif]-->
</head>
<body>
	<input id="pageId" type="hidden" value="7e11389e-ff27-11e7-ac6d-fcaa14314cbe"/>
	<jsp:include page="header.jsp"/>
	<main>
		<jsp:include page="contentLeft.jsp"/>
		<div class="main-right">
			<div class="box loadingOver">
				<h1 class="box-title">财税服务</h1>
				<p class="tel">咨询电话：010-62226659</p>
				<div class="clear"></div>
				<div class="fiscalBottom">
					<div class="know">
						<h2>你知道吗？</h2>
						<p>根据国家税务局总局发布的相关法律条文规定，员工福利费、培训费等项目支出可以进行税前列支。研发费用可以税前加计扣除。</p>
						<p>进行合法合规的财税优化可以有效降低企业成本，并能增加员工收入。</p>
					</div>
					<div class="fiscalBox">
						<img src="img/fiscal-bg1.png" alt="背景图" />
						<h1 class="color-orange">企业节省  12%</h1>
						<p class="color-orange">约8.3万/月（100万/年）</p>
						<p class="btnP">按50名员工，平均福利1万元计算</p>
					</div>
					<div class="fiscalBox">
						<img src="img/fiscal-bg2.png" alt="背景图" />
						<h1 class="color-green">员工增收  15%</h1>
						<p class="color-green">全年增收约1.7万元</p>
						<p class="btnP">按某员工工资1万，年终奖2万元计算</p>
					</div>
					<div class="clear"></div>
					<a class="btn sureBtn openIframe">申请开通</a>
				</div>
			</div>
			
			<!-- loading -->
			<div class="loadingDiv"></div>
			
		</div>
	</main>
	<jsp:include page="footer.jsp"/>
	<!--申请开通-->
	<div class="iframe">
		<div class="iframeInner">
			<h1>申请开通</h1>
			<a class="closeIframe" style="top:15px;"><img src="img/closeIframe.png" alt="关闭"/></a>
			<div class="groupBox">
				<div class="group">
					<label>联系人：</label>
					<input type="text" class="contactName" placeholder="请输入联系人" />
					<p class="tip color-red">联系人不能为空</p>
				</div>
				<div class="group">
					<label>联系电话：</label>
					<input type="text" class="contactTel" placeholder="请输入联系电话" />
					<p class="tip color-red">请填写正确的联系电话</p>
				</div>
				<p class="tips">提示：尊敬的用户，您的专属服务人员会在1-2个工作日内与您联系，请保持电话畅通。</p>
			</div>
			<a class="sureBtn modifyBtn" style="margin-left:180px !important;">申请</a>
		</div>
		<!-- 添加 -->
		<form:form id="addForm" action="../rest/qcb/finance/add" method="post">
		</form:form>
		<!-- 编辑 -->
		<form:form id="editForm" action="../rest/qcb/finance/edit" method="post">
		</form:form>
	</div>
	<script type="text/javascript" src="js/fiscalService.js?v=<%=Math.random()%>" ></script>
</body>
</html>