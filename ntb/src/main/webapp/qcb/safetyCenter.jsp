<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="cn.zeppin.product.ntb.qcb.vo.AdminVO"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="./css/base.css" />
	<link rel="stylesheet" href="css/safetyCenter.css" />
	<!--[if lte IE 8]>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.js"></script>
<![endif]-->
</head>
<body>
	<input id="pageId" type="hidden" value="ffffffff-ffff-ffff-ffff-ffffffffffff"/>
	<jsp:include page="header.jsp"/>
	<main>
		<jsp:include page="contentLeft.jsp"/>
		<div class="main-right">
			<div class="safeItem box loadingOver">
				<div class="safeTop">
					<img src="img/safetyCenter-1-icon.png" alt="安全性" />
					<h3>安全性</h3>						
					<div class="starDiv">
						<div class="starIndex"></div>
					</div>
					<p class="level"></p>
				</div>
				<div class="safeBottom color-orange modifyPassword">
					修改密码
				</div>
			</div>
			<div class="safeItem box loadingOver">
				<div class="safeTop">
					<img src="img/safetyCenter-2-icon.png" alt="安全性" />
					<h3>手机号</h3>
					<p>${sessionScope.currentQcbOperator.mobile}</p>
				</div>
				<div class="safeBottom color-orange bindPhone">
					重新绑定
				</div>
			</div>
			<div class="safeItem box loadingOver">
				<div class="safeTop">
					<img src="img/safetyCenter-3-icon.png" alt="安全性" />
					<div style="position:relative;">
						<h3 style="margin-left:22px;">微信认证
						<img class="tipsIcon" src="./img/tip.png" alt="疑问" style="margin-left:5px;cursor:pointer;" /></h3>
						<div class="tipsDiv">
							<p>绑定管理员微信账号，可用于身份验证，提高帐号安全性。</p>
						</div>
					</div>
					
					<c:choose>
						<c:when test="${sessionScope.currentQcbOperator.wechatFlag}">
						    <p class="color-green">已绑定</p>
					    </c:when>
					    <c:otherwise> 
						    <p class="color-red">未绑定</p>
					    </c:otherwise>
					</c:choose>
					
				</div>
				
				
					<c:choose>
						<c:when test="${sessionScope.currentQcbOperator.wechatFlag}">
						<div class="safeBottom color-orange bindWeChatAgain">
						   重新绑定
						 </div> 
					    </c:when>
					    <c:otherwise> 
					    <div class="safeBottom color-orange bindWeChat">
						   去绑定
						 </div> 
						    
					    </c:otherwise>
					</c:choose>
					
				
				<a id="wechatBtn" href="safetyCenterIframe.jsp?type=bind" target="_blank">
				</a>
				<a id="wechatAgainBtn" href="safetyCenterIframe.jsp?type=remove" target="_blank">
				</a>
			</div>
			<div class="clear"></div>
			
			<!-- loading -->
			<div class="loadingDiv"></div>
			
		</div>
	</main>
	<jsp:include page="footer.jsp"/>
	<!--修改密码-->
	<div class="iframe modifyPasswordIframe">
		<div class="iframeInner">
			<h1>修改密码</h1>
			<a class="closeIframe"></a>
			<%-- <form:form id="password" action="../rest/qcb/admin/password" method="post"> --%>
			<div class="groupBox">
				<div class="group">
					<label>原密码：</label>
					<input type="password" class="oldPassword" name="password" placeholder="请输入原登录密码" />
					<p class="tip color-red">原始密码不能为空</p>
				</div>
				<div class="group">
					<label>新密码：</label>
					<input type="password" class="newPassword" name="passwordNew" placeholder="8-20位数字、字母或符号组合" />
					<p class="tip color-red">请输入正确的密码格式</p>
				</div>
				<div class="group">
					<label>确认密码：</label>
					<input type="password" class="confirmPassword" name="passwordNewCheck" placeholder="请再次输入您的新密码" />
					<p class="tip color-red">两次密码不一致</p>
				</div>
			</div>
			<%-- </form:form> --%>
			<a class="sureBtn modifyPasswordBtn">保存</a>
		</div>
	</div>
	<!--重新绑定-->
	<div class="iframe bindPhoneIframe">
		<div class="iframeInner">
			<h1>绑定手机</h1>
			<a class="closeIframe"></a>
			<%-- <form:form id="changeMobile" action="../rest/qcb/admin/changeMobile" method="post"> --%>
			<div class="groupBox">
				<div class="group">
					<label>原手机号：</label>
					<span style="line-height:42px;font-size:16px;">${sessionScope.currentQcbOperator.mobile}</span>
					<%-- <input type="text" class="oldphone" value="${sessionScope.currentQcbOperator.mobile}"
					 readonly="readonly" style="background-color:#eee;" /> --%>
					<p class="tip color-red">原手机号不能为空</p>
				</div>
				<div class="group">
					<label>验证码：</label>
					<input type="text" class="smsCode" name="code" placeholder="请输入验证码" />
					<a class="getCode color-orange">获取验证码</a>
					<div class="clear"></div>
					<p class="tip color-red">验证码不能为空</p>
				</div>
				<div class="group">
					<label>新手机号：</label>
					<input type="text" class="newPhone" name="mobile" placeholder="请输入新手机号" />
					<p class="tip color-red">请输入正确的手机号格式</p>
				</div>
			</div>
			<%-- </form:form> --%>
			<a class="sureBtn bindPhoneBtn">修改</a>
			<form:form id="sendCodeToCheck" action="../rest/qcb/sms/sendCodeToCheck" method="post">
			</form:form>
		</div>
	</div>
	<!--绑定微信-->
	<div class="iframe bindWeChatIframe">
		<div class="iframeInner">
			<a class="closeIframe"></a>
			<p>微信绑定中。。。</p>
		</div>			
	</div>
	<script src="https://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
	<script type="text/javascript" src="js/safetyCenter.js?v=<%=Math.random()%>" ></script>
	<script>
		
		<%
			Integer level = 3;
			if(session.getAttribute("currentQcbOperator") != null){
				AdminVO admin = (AdminVO)session.getAttribute("currentQcbOperator");
				if(admin.getPasswordLevel() != null){
					level = admin.getPasswordLevel();
				}
			}
		%>
		var level = "<%=level%>";
		if(Number(level)==3){
			$(".level").html("中");
			$(".starIndex").css({"width":"60%"});
		}else if(1<=Number(level)&&Number(level)<3){
			$(".level").html("低");
			$(".starIndex").css({"width":"20%"});
		}else if(3<Number(level)&&Number(level)<=5){
			$(".level").html("高");
			$(".starIndex").css({"width":"100%"});
		}
				
	</script>
</body>
</html>