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
<link rel="stylesheet" href="css/companyWithdrawals.css" />
<link rel="stylesheet" href="./css/withDrawComplited.css" />
<!--[if lte IE 8]>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.js"></script>
<![endif]-->
</head>
<body>
	<input id="pageId" type="hidden" value="d60d061e-ff26-11e7-ac6d-fcaa14314cbe"/>
	<jsp:include page="header.jsp"/>
	<main>
		<jsp:include page="contentLeft.jsp"/>
		<div class="main-right">
			<!--企业提现-->
			<div class="box topBox loadingOver">
				<h1 class="box-title">企业提现</h1>
				<a class="withdrawalsRecord color-orange" href="./financeList.jsp?type=withdraw">查看提现记录</a>
				<div class="companyInfo">
					<div class="group">
						<label>企业名称：</label>
						<p class="groupValue companyName">${sessionScope.currentQcbOperator.qcbCompanyName}</span></p>
						<div class="clear"></div>
					</div>
					<div class="group">
						<label>可提现余额：</label>
						<p class="groupValue accountBalance color-orange"></span></p>
						<div class="clear"></div>
					</div>
				</div>
			</div>
			<form:form id="companyTransaction" action="../rest/qcb/companyTransaction/withdraw" method="post">
			<!--提现信息第一步-->
			<div class="withdrawalStep withdrawalFir box loadingOver">
				<h1 class="box-title" style="margin-bottom: 10px;">提现信息</h1>
				<div class="group">
					<label>收款账户：</label>
					<div class="group-right">
						<input type="hidden" name="bankcard" />
						<div class="chooseAccount chooseAccountShow">

						</div>
						<div class="accoundDiv">

						</div>
					</div>
					<a class="tips color-orange addAccount" href="addBankAccount.html">转账到其他账户</a>
					<div class="clear"></div>
				</div>
				<div class="group">
					<label>转账金额：</label>
					<div class="group-right">
						<input class="amount" type="text" placeholder="转账限额：单笔200万元，当日200万元" 
						onkeyup="this.value=this.value.replace(/\D/g,'')" />
						<span class="unit">元</span>
					</div>
					<p class="tips color-red">请输入正确的转账金额，只能为正整数</p>
					<div class="clear"></div>
				</div>
				<div class="group">
					<label>备注信息：</label>
					<div class="group-right">
						<input type="text" class="noteInformation" name="remark" placeholder="请输入备注"  />
					</div>
					<div class="clear"></div>
				</div>
				<!--手续费-->
				<div class="serviceBox">
					<p class="serviceCharge">手续费：<span class="serviceCharges">10.00</span>元</p>
					<div class="smsNotice">
						<div class="piaochecked on_check">
           						<input name="need_inv" type="checkbox" class="radioclass input" id="checkbox">
         					</div>
         					<label for="checkbox">短信通知收款方</label>
					</div>
					<div class="clear"></div>
				</div>
				<a class="stepBtn stepFir">下一步</a>
			</div>
			<!--提现第二步-->
			<div class="withdrawalStep withdrawalSec">
				<!--提现信息-->
				<div class="box withdrawalSure">
					<h1 class="box-title" style="margin-bottom: 30px;">提现信息</h1>
					<div class="group">
						<label>收款账户：</label>
						<div class="group-right">
							<div class="chooseAccount">
								<p class="bankName">【招商银行基本户】</p>
								<img src="img/logo.png" alt="银行logo" />
								<p class="bankLastNumber">尾号：<span>0201</span></p>
								<div class="clear"></div>
							</div>
						</div>
						<div class="clear"></div>
					</div>
					<div class="group">
						<label>转账金额：</label>
						<div class="group-right">
							<span class="color-orange rechangeAccount"></span>元
						</div>
						<div class="clear"></div>
					</div>
					<div class="group">
						<label>备注信息：</label>
						<div class="group-right noteInformationDiv">
							暂无备注信息
						</div>
						<div class="clear"></div>
					</div>
					<div class="group">
						<label>手续费：</label>
						<div class="group-right" style="width: auto;">
							<span class="serviceCharge serviceCharges">10.00</span>元
						</div>
						<div class="messageNotification">
							<label style="margin-left: 60px;">短信告知：</label>
							<div class="group-right font-bold">
								${sessionScope.currentQcbOperator.mobile}
							</div>
						</div>

						<div class="clear"></div>
					</div>
				</div>
				<!--身份验证-->
				<div class="box authentication">
					<h1 class="box-title">身份验证</h1>
					<div class="group">
						<label>绑定手机：</label>
						<div class="group-right font-bold" style="font-weight:500;">
							${sessionScope.currentQcbOperator.mobile}
						</div>
						<div class="clear"></div>
					</div>
					<div class="group sendCode">
						<label>验证码：</label>
						<div class="group-right">
							<div class="form-item">
								<input type="text" class="smsCodeinput" name="code" placeholder="请输入验证码" />
								<a class="smsCode">获取验证码</a>
								<div class="clear"></div>
							</div>
						</div>
						<p class="tips color-red" style="padding-left:0;">验证码不能为空</p>
						<div class="clear"></div>
					</div>
					<div class="group">
						<label>登录密码：</label>
						<div class="group-right">
							<div class="form-item">
								<input type="password" class="loginPassword" placeholder="请输入登录密码" style="width:346px;" />
								<div class="clear"></div>
							</div>
						</div>
						<p class="tips color-red" style="padding-left:0;">登录密码不能为空</p>
						<div class="clear"></div>
					</div>
					<div class="btnGroup">
						<a class="btn backBtn">返回修改</a>
						<a class="btn sureBtn transferBtn">确认转账</a>
						<div class="clear"></div>
					</div>
				</div>
			</div>
			<div class="main-right-body box">
				<div class="main-right-body-header">
					<h3 class="box-title">企业提现－完成</h3>
					<a href="" class="with-draw-list">查看提现记录</a>
				</div>
				<div class="main-right-body-title">
					<img src="./img/withdrawLogo.png" alt="" />
					<h2>转账成功！</h2>
					<p class="reachTime"></p>
				</div>

				<table cellspacing="0" cellpadding="0">
					<tr>
						<td rowspan="2" class="table-title">付款账户</td>
						<td rowspan="2">
							<!-- <p class="color-gray">企业id：213123213</p> -->
							<h4 class="companyAccountName"></h4>
						</td>
						<td rowspan="2" class="table-title">转账金额</td>
						<td rowspan="2">
							<span class="color-orange rechangeAccountPrice"></span>
							<small>元</small>
						</td>
					</tr>
					<tr>	</tr>
					<tr>
						<td rowspan="2" class="table-title">收款账户</td>
						<td rowspan="2">
							<h4>${sessionScope.currentQcbOperator.qcbCompanyName}</h4>
							<p class="nm-b"></p>
							<span class="color-gray qcbCompanyBankcardName"></span>
						</td>
						<td rowspan="2" class="table-title">手续费</td>
						<td rowspan="2" class="poundage"></td>
					</tr>
					<tr>
					</tr>
					<tr>
						<td class="table-title">流水号</td>
						<td class="orderNum"></td>	
						<td class="table-title">审批人</td>
						<td>
							<h5 class="creator"></h5>
							<small class="color-gray getNowFormatDate"></small>
						</td>					
					</tr>
					<!-- <tr>
						<td class="table-title">经办人</td>
						<td>
							<h5>荣景峰</h5>
							&nbsp;&nbsp;<small class="color-gray getNowFormatDate">2017-12-12 18:00:00</small>
						</td> 
						
					</tr> -->
				</table>
			</div>
			</form:form>
			<!-- 引导认证 -->
			<div class="guideDBox box">
				<div class="guideInner">
					<p>1分钟完成企业认证，享受更多特权与服务。</p>
					<a href="companyOperate.jsp" class="btn">去认证</a>
				</div>
			</div>
			
			<!-- loading -->
			<div class="loadingDiv"></div>
			
		</div>
		<form:form id="getCodesubmit" action="../rest/qcb/sms/sendCodeToCheck" method="post">
		</form:form>
	</main>
	<jsp:include page="footer.jsp"/>
	<script type="text/javascript" src="js/base64.js" ></script>
	<script type="text/javascript" src="js/companyWithdrawals.js?v=<%=Math.random()%>" ></script>
	<script>
		<%
			String status = "";
			if(session.getAttribute("currentQcbOperator") != null){
				AdminVO admin = (AdminVO)session.getAttribute("currentQcbOperator");
				if(admin.getQcbCompanyStatus() != null){
					status = admin.getQcbCompanyStatus();
				}
			}
		%>
		var status="<%=status%>";
		if(status!="normal"){
			$(".box").hide();
			$(".guideDBox").css({"height":$(window).height()-194+"px"}).show();
			$(".loadingDiv").hide();
		}else{
			/* $(".withdrawalFir").show();
			$(".topBox").show(); */
			$(".guideDBox").hide();
		    getbankInfo();
		}
	</script>
</body>
</html>
