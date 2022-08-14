<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page import="cn.zeppin.product.ntb.qcb.vo.AdminVO" %>
<%@ page import="cn.zeppin.product.ntb.qcb.vo.QcbMenuLessVO" %>
<%
	List<String> views = new ArrayList<String>();

	if(session.getAttribute("currentQcbOperator") != null){
		AdminVO admin = (AdminVO)session.getAttribute("currentQcbOperator");
		Set<String> menus = admin.getListMenu().keySet();
		views.add("ffffffff-ffff-ffff-ffff-ffffffffffff");
		for(String menuUuid : menus){
			QcbMenuLessVO qm = admin.getListMenu().get(menuUuid);
			if(qm.getChildMenu() != null && qm.getChildMenu().size() > 0){
				for(String childUuid : qm.getChildMenu().keySet()){
					QcbMenuLessVO childqQm = qm.getChildMenu().get(childUuid);
					views.add(childqQm.getUuid());
				}
			}
		}
	}
%>  

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<meta name="keywords" content="企财宝,人事福利,北京知鹏汇仁科技有限公司,saas服务云平台,福利,财务福利,投资理财,企财宝（北京）区块链科技有限公司,智能福利" />
		<meta name="description" content="企财宝是一个专业服务于企业人事福利、费用管理与成本控制的Saas服务云平台，由北京知鹏汇仁科技有限公司运营。企财宝平台核心团队由多位企业人力资源领域、财税审计领域、互联网领域、金融服务领域的资深专家组成，公司总部坐落在北京中关村核心园区，是一家国家认定的高新技术企业、双软企业，注册实缴资本1000万元。" />
		<title>企财宝 － 智能福利管理平台</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="./css/header.css" />
		<link rel="stylesheet" href="./css/main.css" />
	</head>
	<body>
		<nav>
			<div class="nav-header">
				<div id="click">
					<span></span>
					<span></span>
					<span></span>
				</div>
				<div class="nav-header-left">
					<a href="./index.jsp"><img src="./img/logo-bg.png" alt="" style="border:none;" /></a>

				</div>
				<div class="nav-header-right">
					<ul>
						<li>
							<img src="./img/icon.png" class="icon" />
							<a id="name" uuid="${sessionScope.currentQcbOperator.qcbCompany}">${sessionScope.currentQcbOperator.qcbCompanyName}</a>
							<img src="./img/bottom.png" class="bottom" />
							<input id="currentUserId" type="hidden" value="${sessionScope.currentQcbOperator.uuid}">
							<div class="company-list">
								<c:forEach items="${sessionScope.currentQcbOperator.listCompanyAccount}" var="user">
									<c:choose>
										<c:when test="${user.uuid == sessionScope.currentQcbOperator.qcbCompany}">
										    <p uuid="${user.uuid}"><span class="company-name">${user.name}</span>
										    <span class="color-gray">当前选择</span>
										    </p> 
									    </c:when>
									    <c:otherwise> 
										    <p uuid="${user.uuid}">
										    	<span class="company-name">${user.name}</span>
										    	<span class="color-gray" style="display: none;">当前选择</span>
										    	<a class="change">切换</a>
										    </p>  
									    </c:otherwise>
									</c:choose>
								</c:forEach> 
							</div>
						</li>
						<!-- <li>
							<a class="mail-logo">
								<img src="./img/new.png" alt="" />
							</a>
						</li> -->
						<li>
							<a class="back" href="../rest/qcb/admin/logout"></a>
						</li>
					</ul>
				</div>
			</div>
		</nav>
		<script src="js/jquery-1.11.1.js"></script>		
		<script src="./layer-v3.0.1/layer/layer.js"></script>
		<script type="text/javascript" src="js/base64.js" ></script>
		<script type="text/javascript" src="js/jquery.cookie.js" ></script>
		<script type="text/javascript" src="js/url.min.js" ></script>
		<script type="text/javascript" src="js/base.js" ></script>
		<script type="text/javascript" src="js/header.js" ></script>
		<script>
			var viewString = "<%=views%>";
			var pageId = $("#pageId").val();
			if(typeof(pageId) == "undefined" || pageId == "" || viewString.indexOf(pageId) == -1){
				//该页面没权限
				window.location.href="withDrawComplited.jsp";
			}
		</script>
	</body>
</html>
