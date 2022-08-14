<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="./css/fundList.css">
		<link rel="stylesheet" href="./css/bootstrap.css" />
		<link rel="stylesheet" href="./css/bootstrap2.css" />
		<link rel="stylesheet" href="./css/style.css" />
		<link rel="stylesheet" href="./css/colorbox.css" />

		<script id="DataTpl" type="text/template">
				<table class="inverstor_msg" cellspacing="0" cellpadding="0" border="1">
					<tr>
						<td width="15%" class="t_right">企业ID：</td>
						<td width="25%" title="{{:realname}}">{{:merchantId}}</td>
						<td width="15%" class="t_right">企业名称：</td>
						<td width="25%" title="{{:mobile}}">{{:name}}</td>
						<td width="20%" rowspan="3">
							{{if logoUrl != null}}
								<img src="..{{:logoUrl}}" class="head_photo">
							{{else}}
								<img src="../resource/qcb_company_default.png" class="head_photo">
							{{/if}}
						</td>
					</tr>

					<tr>
						<td width="15%" class="t_right">法定代表人：</td>
						<td width="25%" title="">
							{{if corporation == null}}
								-
							{{else}}
								{{:corporation}}
							{{/if}}

						</td>
						<td width="15%" class="t_right">联系电话：</td>
						<td width="25%">{{:phone}}</td>
					</tr>

					<tr>
						<td width="15%" class="t_right">注册时间：</td>
						<td width="25%" title="{{:createtimeCN}}">{{:createtimeCN}}</td>
						<td width="15%" class="t_right">状态：</td>
						<td width="25%" title="">
							{{if status == "normal"}}
								<span style="color:green;">{{:statusCN}}</span>
							{{else status == "uncheck"}}
								<span style="color:#74B2FF;">{{:statusCN}}</span>
							{{else status == "unauth"}}
								<span style="color:red;">{{:statusCN}}</span>
							{{else status == "nopass"}}
								<span style="color:orange;">{{:statusCN}}</span>
							{{else status == "deleted"}}
								<span style="color:#C1C1C1;">{{:statusCN}}</span>
							{{/if}}
						</td>
					</tr>

					<tr>
						<td width="15%" class="t_right">认证材料：</td>
						<td width="25%" title="">
							{{if status != "unauth"}}
								<a class="confirm-page" href="./QCBcompanyConfirm.jsp?uuid={{:uuid}}">点击查看</a>
							{{else}}
								无
							{{/if}}
						</td>
						<td width="15%" class="t_right">地址：</td>
						<td width="25%"  colspan="2">{{:bkAreaCN}}{{:address}}</td>
					</tr>
                    <!-- <tr>
						<td width="15%" class="t_right">税务识别号：</td>
						<td width="25%" title="{{:lastLoginTimeCN}}">{{:taxIdentificationNum}}</td>
						<td width="15%" class="t_right">企业名称：</td>
						<td width="25%"  colspan="2">{{:taxCompany}}</td>
					</tr>
                    <tr>
						<td width="15%" class="t_right">电话：</td>
						<td width="25%" title="{{:lastLoginTimeCN}}">{{:taxPhone}}</td>
						<td width="15%" class="t_right">地址：</td>
						<td width="25%"  colspan="2">{{:taxAddress}}</td>
					</tr>
                    <tr>
						<td width="15%" class="t_right">开户行：</td>
						<td width="25%" title="{{:lastLoginTimeCN}}">{{:openBank}}</td>
						<td width="15%" class="t_right">银行账号：</td>
						<td width="25%"  colspan="2">{{:openBankCardnum}}</td>
					</tr> -->
                    <tr>
						<td width="15%" class="t_right">企业联系人：</td>
						<td width="25%" title="{{:lastLoginTimeCN}}">{{:contacts}}</td>
						<td width="15%" class="t_right">联系人电话：</td>
						<td width="25%"  colspan="2">{{:contactsMobile}}</td>
					</tr>
                    <tr>
						<td width="15%" class="t_right">联系人身份证号：</td>
						<td width="25%" title="{{:lastLoginTimeCN}}">{{:contactsIdcard}}</td>
						<td width="15%" class="t_right">联系人邮箱：</td>
						<td width="25%"  colspan="2">{{:contactsEmail}}</td>
					</tr>
				</table>

				<p class="msg_title" style="margin-top:20px;">开票信息</p>

				<table class="inverstor_msg" cellspacing="0" cellpadding="0" border="1">
					<tr>
						<td width="15%" class="t_right">企业名称：</td>
						<td width="25%"  colspan="2">{{:taxCompany}}</td>
						<td width="15%" class="t_right">税务识别号：</td>
						<td width="25%" title="{{:lastLoginTimeCN}}">{{:taxIdentificationNum}}</td>
					</tr>
                    <tr>
						<td width="15%" class="t_right">电话：</td>
						<td width="25%" title="{{:lastLoginTimeCN}}">{{:taxPhone}}</td>
						<td width="15%" class="t_right">地址：</td>
						<td width="25%"  colspan="2">{{:taxAddress}}</td>
					</tr>
                    <tr>
						<td width="15%" class="t_right">开户行：</td>
						<td width="25%" title="{{:lastLoginTimeCN}}">{{:openBank}}</td>
						<td width="15%" class="t_right">银行账号：</td>
						<td width="25%"  colspan="2">{{:openBankCardnum}}</td>
					</tr>
				</table>

				<p class="confirm_time">
					{{if status == !uncheck}}
						 <span class="confirm-time-right">于 {{:checktimeCN}} {{:checkerName}}审核</span>
					{{/if}}
				</p>

				<table cellspacing="0" cellpadding="0" border="1" class="inverstor_money">
                    <tr style="height:40px;">
                        <td colspan="4" style="padding-left:14px;">账户总资产：{{:totalAmountCN}}</td>
                    </tr>
					<tr>
						<td>
							<p>账户余额</p>
							<p title="{{:totalAmountCN}}">{{:accountBalanceCN}}</p>
						</td>

						<td>
							<p>可提现余额</p>
							<p title="{{:accountBalance}}">{{:accountPayCN}}</p>
						</td>

						<td>
							<p>当前投资</p>
							<p title="{{:totalInvest}}">{{:totalInvestCN}}</p>
						</td>

						<td>
							<p>历史总收益</p>
							<p style="color:orange;" title="{{:totalReturn}}">{{:totalReturnCN}}</p>
						</td>
					</tr>
                    <tr style="height:40px;">
                        <td colspan="4" style="padding-left:14px;">充值账号：{{:companyAccountNum}}<span style="color:orange"> {{:accountNum}}</span></td>
                    </tr>
                    <tr style="height:40px;text-align:center;">
                        <td>银行信息：</td>
                        <td>
                        	{{if branchBankName != null}}
								{{:branchBankName}}
							{{else}}
								无
							{{/if}}
                        </td>
                        <td>汇款名称：</td>
                        <td>{{:companyAccountName}}</td>
                    </tr>
				</table>
		</script>


		<script id="billTpl" type="text/template">
			<tr {{if #index % 2 == 1}}style="background:#F7FBFF;"{{/if}}>
				<td>{{:createtimeCN}}</td>

				{{if priceflag == true}}
					<td class="price">{{:price}}</td>
				{{else priceflag == false}}
					<td class="price">-{{:price}}</td>
				{{/if}}

				<td>{{:typeCN}}</td>
				<td>{{:orderTypeCN}}</td>
				<td>{{:statusCN}}</td>
				<td>
					<a class="bill_message" href="./QCBcompanyDetailMsg.jsp?&uuid={{:uuid}}" style="color:#0088cc;">查看</a>
				</td>
			</tr>
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00800081" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a href="./QCBcompanyList.jsp">企业用户管理</a><span>></span><a class="current">企业用户详情</a></div>
					<div class="clear"></div>
				</div>
				<div class="layerOpen">
					<div id="formsubmit">
						<div class="contents">
							<p class="msg_title">企业资料</p>
							<div id="DataCnt"></div>

							<div class="inverstor_history">
								<p class="inverstor_history_title">交易记录</p>
								<%-- <div class="condition" style="margin-bottom:20px">
			                        <div class="statusDiv shortStatusDiv filter">
			                            <div>
			                                <a class="statusLight" id="all">全部<span id="allCount">(0)</span></a>
			                                <a id="transfer">转账<span id="transferCount">(0)</span></a>
			                                <a id="recharge">企业充值<span id="rechargeCount">(0)</span></a>
			                                <a id="expend">支出<span id="expendCount">(0)</span></a>
			                                <a id="invest">投资<span id="investCount">(0)</span></a>
			                                <a id="redeem">赎回<span id="redeemCount">(0)</span></a>
			                                <a id="return">收益<span id="returnCount">(0)</span></a>
			                                <a id="takeout">用户提现<span id="takeoutCount">(0)</span></a>
			                                <a id="fillin">用户充值<span id="fillinCount">(0)</span></a>
			                                <a id="qcb_takeout">企财宝企业提现<span id="qcb_takeoutCount">(0)</span></a>
			                                <a id="qcb_recharge">企财宝企业充值<span id="qcb_rechargeCount">(0)</span></a>
			                                <a id="qcb_payroll">企财宝薪资发放<span id="qcb_payrollCount">(0)</span></a>
			                                <a id="emp_takeout">企财宝员工提现<span id="emp_takeoutCount">(0)</span></a>
			                                <a id="emp_fillin">企财宝员工充值<span id="emp_fillinCount">(0)</span></a>
			                            </div>
			                        </div>
			                    </div> --%>
								<table cellspacing="0" cellpadding="0">
									<tr style="background:#F7FBFF;">
										<td>交易时间</td>
										<td>交易金额</td>
										<td>类型</td>
										<td>第三方通道</td>
										<td>状态</td>
										<td>操作</td>
									</tr>
									<tbody id="bill_box">

									</tbody>
								</table>
							</div>
							<div id="pageTool" style="width:90%;margin-top:30px;">

							</div>
							<div class="clear"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<script type="text/javascript" src="js/jquery-1.11.1.js" ></script>
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/url.min.js"></script>
        <script type="text/javascript" src="js/flagSubmit.js"></script>
		<script type="text/javascript" src="js/jsrender.min.js"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/bootstrap.js" ></script>
		<script src="js/paging.js"></script>
		<script type="text/javascript" src="./js/QCBcompanyDetail.js" ></script>

	</body>
</html>
