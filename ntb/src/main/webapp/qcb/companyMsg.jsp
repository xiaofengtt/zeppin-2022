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
<link rel="stylesheet" href="css/uploadfile.css" />
<link rel="stylesheet" href="./css/companyMsg.css" />
<!--[if lte IE 8]>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.js"></script>
<![endif]-->
</head>
<body>
	<input id="pageId" type="hidden" value="a051ed00-ff27-11e7-ac6d-fcaa14314cbe"/>
	<jsp:include page="header.jsp"/>
	<main>
		<jsp:include page="contentLeft.jsp"/>
		<div class="main-right">
			<div class="main-right-body box loadingOver">
				<div class="main-right-body-header">
					<h3 class="box-title">基本信息</h3>
					<a class="edit editCompany" href="companyOperate.jsp">修改</a>
				</div>
				<div class="main-right-body-top companyBox">
					<div id="imageShow" ></div>
					<p></p>
					<div class="btn-g">
						<a href="companyOperate.jsp" class="btn">企业认证</a>
					</div>
				</div>
				<div class="msg-box companyBox">
					<div class="msg-item">
						<span class="msg-item-key">企业ID：</span>
						<span class="msg-item-value merchantId"></span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">企业名称：</span>
						<span class="msg-item-value name"></span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">地址：</span>
						<span class="msg-item-value bkAreaCN"></span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">电话：</span>
						<span class="msg-item-value phone">0</span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">法定代表人：</span>
						<span class="msg-item-value corporation"></span>
					</div>
					<input type="hidden" class="bkArea" value="" />
					<input type="hidden" class="companyAddress" value="" />
					<!--<div class="msg-item">
						<span class="msg-item-key">关联企业：</span>
						<span class="msg-item-value">无</span>
					</div>-->
				</div>
			</div>
			
			<div class="main-right-body box loadingOver">
				<div class="main-right-body-header">
					<h3 class="box-title">开票信息</h3>
				</div>
				<div class="msg-box billingBox">
					<div class="msg-item">
						<span class="msg-item-key">公司名称：</span>
						<span class="msg-item-value taxCompany"></span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">纳税人识别号：</span>
						<span class="msg-item-value taxIdentificationNum"></span>
					</div>
					
					<div class="msg-item">
						<span class="msg-item-key">注册地址：</span>
						<span class="msg-item-value taxAddress"></span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">注册电话：</span>
						<span class="msg-item-value taxPhone"></span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">银行账号：</span>
						<span class="msg-item-value openBankCardnum"></span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">银行开户行：</span>
						<span class="msg-item-value openBank"></span>
					</div>
				</div>
			</div>
			
			<div class="main-right-body box loadingOver">
				<div class="main-right-body-header">
					<h3 class="box-title">联系人信息</h3>
				</div>
				<div class="msg-box contactBox">
					<div class="msg-item">
						<span class="msg-item-key">联系人：</span>
						<span class="msg-item-value contacts"></span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">联系电话：</span>
						<span class="msg-item-value contactsMobile"></span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">身份证号：</span>
						<span class="msg-item-value contactsIdcard"></span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">电子邮箱：</span>
						<span class="msg-item-value contactsEmail"></span>
					</div>
				</div>
			</div>
			
			<!-- loading -->
			<div class="loadingDiv"></div>
			
		</div>

	</main>

	<jsp:include page="footer.jsp"/>
	<script src="./js/query.js"></script>
	<script src="./js/paging.js"></script>
	<script type="text/javascript" src="js/jquery.uploadfile.min.js" ></script>
	<script src="./js/companyMsg.js?v=<%=Math.random()%>"></script>
</body>
</html>
