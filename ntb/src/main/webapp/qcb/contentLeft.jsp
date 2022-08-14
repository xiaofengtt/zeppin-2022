<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
	</head>
	<body>
		<div class="main-left">
			<div class="main-left-item">
				<a class="title" href="index.jsp">
					<img src="./img/g-1.png" alt="" /> 首页
				</a>
			</div>
			<div class="main-left-item">
				<a class="title">
					<img src="./img/g-2.png" alt="" /> 企业账户
				</a>
				<ul>
					<li>
						<a href="accountInformation.jsp">账户信息</a>
					</li>
					<li>
						<a href="recharge.jsp">企业充值</a>
					</li>
					<li>
						<a href="companyWithdrawals.jsp">企业提现</a>
					</li>
					<li>
						<a href="financeList.jsp">账务明细</a>
					</li>
				</ul>
			</div>
			<div class="main-left-item">
				<a class="title">
					<img src="./img/g-3.png" alt="" /> 员工福利
				</a>
				<ul>
					<li>
						<a href="extendWages.jsp">福利发放</a>
					</li>
					<li>
						<a href="wagesList.jsp">发放记录</a>
					</li>
					<li>
						<a href="fiscalService.jsp">财税服务</a>
					</li>
				</ul>
			</div>
			<div class="main-left-item">
				<a class="title">
					<img src="./img/g-4.png" alt="" /> 企业管理
				</a>
				<ul>
					<li>
						<a href="companyMsg.jsp">企业信息</a>
					</li>
					<li>
						<a href="staffControl.jsp">员工管理</a>
					</li>
				</ul>
			</div>
			<div class="main-left-item nb-b">
				<a class="title">
					<img src="./img/g-5.png" alt="" /> 设置
				</a>
				<ul>
					<li>
						<a href="safetyCenter.jsp">安全中心</a>
					</li>
					<li>
						<a href="superAdminList.jsp">管理员</a>
					</li>
				</ul>
			</div>
		</div>
	</body>
</html>
