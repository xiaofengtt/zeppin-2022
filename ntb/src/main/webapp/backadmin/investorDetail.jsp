<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="css/bootstrap.css" />
		<link rel="stylesheet" href="css/bootstrap2.css" />
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/colorbox.css" />

		<script id="DataTpl" type="text/template">
				<table class="inverstor_msg" cellspacing="0" cellpadding="0" border="1">
					<tr>
						<td width="15%" class="t_right">姓名：</td>
						<td width="25%" title="{{:realname}}">{{:realname}}</td>
						<td width="15%" class="t_right">手机号：</td>
						<td width="25%" title="{{:mobile}}">{{:mobile}}</td>
						<td width="20%" rowspan="5"><img src="./img/true_header.png" class="head_photo"></td>
					</tr>

					<tr>
						<td width="15%" class="t_right">身份证号：</td>
						<td width="25%" title="{{:idcard}}">{{:idcard}}</td>
						<td width="15%" class="t_right">昵称：</td>
						<td width="25%" title="{{if nickname == null}}-{{else}}{{:nickname}}{{/if}}">
							{{if nickname == null}}
								-
							{{else}}
								{{:nickname}}
							{{/if}}
						</td>
					</tr>

					<tr>
						<td width="15%" class="t_right">电子邮箱：</td>
						<td width="25%" title="{{if email == null}}-{{else}}{{:email}}{{/if}}">
							{{if email == null}}
								-
							{{else}}
								{{:email}}
							{{/if}}
						</td>
						<td width="15%" class="t_right">绑定状态：</td>
						<td width="25%">
							<!--银行卡-->
							{{if bindingBankcardFlag == true}}
								<img src="./img/card_c.png">
							{{else}}
								<img src="./img/card_g.png">
							{{/if}}

							<!--手机号-->
							{{if bindingMobileFlag == true}}
								<img src="./img/phone_c.png">
							{{else}}
								<img src="./img/phone_g.png">
							{{/if}}

							<!--邮箱-->
							{{if bindingEmailFlag == true}}
								<img src="./img/mail_c.png">
							{{else}}
								<img src="./img/mail_g.png">
							{{/if}}

							<!--实名认证-->
							{{if realnameAuthFlag == true}}
								<img src="./img/confirm_c.png">
							{{else}}
								<img src="./img/confirm_g.png">
							{{/if}}
						</td>
					</tr>

					<tr>
						<td width="15%" class="t_right">注册时间：</td>
						<td width="25%" title="{{:createtimeCN}}">{{:createtimeCN}}</td>
						<td width="15%" class="t_right">推荐人：</td>
						<td width="25%" title="{{if referrerName == null}}-{{else}}{{:referrerName}}{{/if}}">
							{{if referrerName == null}}
								-
							{{else}}
								{{:referrerName}}
							{{/if}}
						</td>
					</tr>

					<tr>
						<td width="15%" class="t_right">最后登录时间：</td>
						<td width="25%" title="{{:lastLoginTimeCN}}">{{:lastLoginTimeCN}}</td>
						<td width="15%" class="t_right">状态：</td>
						<td width="25%"{{if statusCN == "正常"}}style="color:green"{{else}}style="color:red"{{/if}}>{{:statusCN}}</td>
					</tr>
				</table>

				<!-- <p class="confirm_time">
					于2017-08-21 21:43:13 秦龙审核
				</p> -->

				<table cellspacing="0" cellpadding="0" border="1" class="inverstor_money">
					<tr>
						<td>
							<p>账户总资产</p>
							<p title="{{:accountBalance + totalInvest}}">{{:totalAmount}}</p>
						</td>

						<td>
							<p>余额</p>
							<p title="{{:accountBalance}}">{{:accountBalanceCN}}</p>
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
				</table>
				<!-- <div class="form-group form-group-double col-xs-6">
					<label class="col-xs-12">昵称：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:nickname}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group col-xs-6">
					<label class="col-xs-12">身份证号：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:idcard}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
				<div class="form-group form-group-double col-xs-6">
					<label class="col-xs-12">电子邮箱：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:email}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group col-xs-6">
					<label class="col-xs-12">账户总投资（元）：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:totalInvest}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
				<div class="form-group form-group-double col-xs-6">
					<label class="col-xs-12">账户余额（元）：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:accountBalance}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group col-xs-6">
					<label class="col-xs-12">推荐人：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:referrerName}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
				<div class="form-group form-group-double col-xs-6">
					<label class="col-xs-12">真实姓名：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:realname}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group col-xs-6">
					<label class="col-xs-12">手机号：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:mobile}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
				<div class="form-group form-group-double col-xs-6">
					<label class="col-xs-12">状态：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:statusCN}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form-group col-xs-6">
					<label class="col-xs-12">历史总收益（元）：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:totalReturn}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
				<div class="form-group form-group-double col-xs-6">
					<label class="col-xs-12">创建时间：</label>
					<div class="col-xs-12">
						<input type="text" disabled="disabled" class="form-control disabled" value="{{:createtimeCN}}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
				<div class="btnGroup">
					<button class="btn cancleBtn" type="button" onclick="history.go(-1)">返回</button>
				</div> -->
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
				<td>{{:application}}</td>
				<td>{{:statusCN}}</td>
				<td>
					<a class="bill_message" href="./investorDetailMsg.jsp?uuid={{:investor}}&billuuid={{:uuid}}" style="color:#0088cc;">查看</a>
				</td>
			</tr>
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00100015" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a href="">后台用户管理</a><span>></span><a href="investorList.jsp">投资者用户管理</a><span>></span><a class="current">投资者用户详情</a></div>
					<div class="clear"></div>
				</div>
				<div class="layerOpen">
					<div id="formsubmit">
						<div class="contents">
							<p class="msg_title">详细信息</p>
							<div id="DataCnt"></div>

							<div class="inverstor_history">
								<p class="inverstor_history_title">交易记录</p>
								<table cellspacing="0" cellpadding="0">
									<tr style="background:#F7FBFF;">
										<td>交易时间</td>
										<td>交易金额</td>
										<td>类型</td>
										<td>第三方通道</td>
										<td>应用</td>
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
		<script type="text/javascript" src="./js/jquery-1.11.1.js" ></script>
		<script type="text/javascript" src="./js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="./js/url.min.js"></script>
		<script type="text/javascript" src="./js/flagSubmit.js"></script>
		<script type="text/javascript" src="./js/jsrender.min.js"></script>
		<script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="./js/bootstrap.js" ></script>
		<script src="./js/paging.js"></script>
		<script type="text/javascript" src="./js/investorDetail.js" ></script>
	</body>
</html>
