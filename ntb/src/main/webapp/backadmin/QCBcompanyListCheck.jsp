<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="css/fundList.css" />
		<script id="queboxTpl" type="text/template">
			<div class="msg_box">
				<div class="msg_box_head">
					<table>
						<tr>
							<td class="nameTd">企业ID：<span>{{:merchantId}}</span></td>
							<td>企业名称：<span>{{:name}}</span></td>
							{{if authStatus == "normal"}}
								<td>状态：<span style="color:green;">{{:authStatusCN}}</span></td>
							{{else authStatus == "uncheck"}}
								<td>状态：<span style="color:#74B2FF;">{{:authStatusCN}}</span></td>
							{{else authStatus == "unauth"}}
								<td>状态：<span style="color:red;">{{:authStatusCN}}</span></td>
							{{else authStatus == "nopass"}}
								<td>状态：<span style="color:orange;">{{:authStatusCN}}</span></td>
							{{else authStatus == "deleted"}}
								<td>状态：<span style="color:#C1C1C1;">{{:authStatusCN}}</span></td>
							{{/if}}
							<td class="operation text-center">
								{{if authStatus == "uncheck"}}
									<a class="editBtn btn-edit" href="./QCBcompanyDetailCheck.jsp?uuid={{:uuid}}">审核</a>
								{{/if}}
									<a class="editBtn btn-edit" href="./QCBcompanyDetailCheck.jsp?uuid={{:uuid}}">查看</a>
							</td>
						</tr>
					</table>
				</div>

				<div class="msg_box_content">
					<div class="msg_box_photo">
						{{if logoUrl != null}}
							<img src="..{{:logoUrl}}" class="head_photo">
						{{else}}
							<img src="../resource/qcb_company_default.png" class="head_photo">
						{{/if}}
					</div>

					<div class="msg_box_info">
						<div class="msg_box_info_item">
							<span>账户总资产</span>
							<span>账户余额</span>
                            <span>可提现余额</span>
							<span>账户当前投资额</span>
							<span>历史总收益</span>
							<span>平台手续费率</span>
						</div>
						<div class="msg_box_info_item">
							<span>{{:totalAmountCN}}</span>
							<span>{{:accountBalanceCN}}</span>
							<span>{{:accountPayCN}}</span>
                            <span>{{:totalInvestCN}}</span>
							<span>{{:totalReturnCN}}</span>
							<span>{{:feeTicketCN}}%</span>
						</div>
					</div>
					<!-- <p class="create_time_p">{{:createtimeCN}} 注册</p> -->
				</div>

				<!-- <td><span>{{:statusCN}}</span></td>
				<td><span>{{:totalInvest}}</span></td>
				<td><span>{{:totalReturn}}</span></td>
				<td><span>{{:accountBalance}}</span></td>
				<td><span>{{:createtimeCN}}</span></td>
				<td class="operation">
					<a class="editBtn btn-edit" href="investorDetail.jsp?uuid={{:uuid}}">详情</a>
				</td> -->
			</div>
		</script>
        <style>
            .contain-right .msg_box .msg_box_content .msg_box_info{
                width:86%;
            }
            .contain-right .msg_box .msg_box_content .msg_box_info .msg_box_info_item span{
                width:16%;
            }
            .contain-right .msg_box .msg_box_content .msg_box_photo .head_photo{
                margin-right:0;
            }
            .contain-right .msg_box .msg_box_content .msg_box_photo{
                width:12%;
            }
        </style>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00800085" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a class="current">企业用户审核</a></div>
					<div class="clear"></div>
				</div>
				<div class="main-contain pt-13 pl-14 pr-16">

					<div class="condition">
						<div class="statusDiv shortStatusDiv filter">
							<label>审核状态：</label>
							<div>
								<a id="all" class="statusLight">全部<span id="allCount">(0)</span></a>
								<a id="normal">已认证<span id="normalCount">(0)</span></a>
								<a id="uncheck">申请中<span id="uncheckCount">(0)</span></a>
								<a id="unauth">未认证<span id="unauthCount">(0)</span></a>
								<a id="nopass">未通过<span id="nopassCount">(0)</span></a>
								<a id="deleted">已删除<span id="deletedCount">(0)</span></a>
							</div>
						</div>

						<div class="searchDiv">
							<div class="input-group">
								<input id="search" class="form-control" type="text" value="" placeholder="搜索" onkeypress="if(event.keyCode==13) {searchBtn();return false;}">
								<label class="input-group-addon" onclick="searchBtn()"></label>
							</div>
						</div>
					</div>

					<div style="margin-top:20px;">
						<%-- <thead>
							<tr>
								<th class="nameTh" width="15%">昵称</th>
								<th class="text-center" width="10%">状态</th>
								<th class="text-center" width="20%">账户总投资（元）</th>
								<th class="text-center" width="20%">历史总收益（元）</th>
								<th class="text-center" width="17%">账户余额（元）</th>
								<th class="text-center" width="20%">创建时间</th>
								<th class="text-center" width="13%">操作</th>
							</tr>
						</thead> --%>
						<div id="queboxCnt"></div>
					</div>
					<div id="pageTool"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<script type="text/javascript" src="./js/getHtmlDocName.js"></script>
		<script type="text/javascript" src="./js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="./js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
		<script type="text/javascript" src="./js/jsrender.min.js"></script>
		<script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js" ></script>
        <script type="text/javascript" src="./js/QCBcompanyListCheck.js"></script>

	</body>
</html>
