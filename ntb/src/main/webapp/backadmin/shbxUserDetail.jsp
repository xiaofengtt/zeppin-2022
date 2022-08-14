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
		<link rel="stylesheet" href="./css/auditing.css">
		<link rel="stylesheet" href="./css/shbxUserDetail.css">		

		<script id="DataTpl" type="text/template">
			<table cellspacing="0" cellpadding="0" style="border:0;margin-left: 20px;" >
				<tr>
					<td>{{:realname}}</td>
					<td>
						{{if status == 'normal'}}
							<span style="background:#0b0;color:#fff;border-radius:10px;padding:0 10px;">正常</span>
        				  {{else}}
        				    <span style="background:#b00;color:#fff;border-radius:10px;padding:0 10px;">停用</span>
           				{{/if}}
					</td>
				</tr>
				<tr>
					<td>手机号：</td>
					<td>{{:mobile}}</td>
				</tr>
				<tr>
					<td>身份证号：</td>
					<td>{{:idcard}}</td>
				</tr>
			</table>
		</script>

		<script id="insuredTpl" type="text/template">
			<div class="product_item">
		        <div class="product_content">
		            <div class="product_content_item">
		                <span>姓名：{{:name}}</span>
		                <span>手机号：{{:mobile}}</span>
		                <span>身份证号：{{:idcard}}</span>
		            </div>
		            <div class="product_content_item">
						<span>民族：{{:nationality}}</span>
		                <span>户口所在地：{{:householdareaName}}</span>
		                <span>户口性质：{{:householdtype}}</span>
		            </div>
					<div class="product_content_item">
						<span>学历：{{:education}}</span>
						<span>个人身份：{{:duty}}</span>
		                <span>参加工作时间：{{:worktimeCN}}</span>
		            </div>
					<div class="product_content_item">
		                <span>邮箱：{{:email}}</span>
						<span>状态：{{if status == 'normal'}}正常{{else}}停用{{/if}}</span>
		            </div>
		        </div>
		    </div>
		</script>
		<script id="orderTpl" type="text/template">
			<tr>
				<td><span>{{:shbxInsuredName}}</span></td>
				<td><span>{{:createtimeCN}}</span></td>
				<td><span>{{:duration}}个月</span></td>
				<td><span>{{:durationCN}}</span></td>
				<td><span>{{:cardinalNumberCN}}元</span></td>
				<td><span>{{:benefitsCN}}元</span></td>
				<td><span>{{:serviceFeeCN}}元</span></td>
			</tr>
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="01000101" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a href="./shbxUserList.jsp">社保熊用户管理</a><span>></span><a class="current">社保熊用户详情</a></div>
					<div class="clear"></div>
				</div>
				<div class="main-contain pt-13 pl-14 pr-16">
					<div id="formsubmit">
						<div class="contents">
							<img src='./img/true_header.png' class="head_photo" />
							<div id="DataCnt"></div>
							<div class="clear"></div>
							<div class="selectBar">
								<button class="light" data="cbrInfo">参保人信息</button><button data="jfInfo">缴费信息</button>
							</div>
							<div class="select-item cbrInfo">
								<div id="insured_box"></div>
								<div id="pageTool" style="width:90%;margin-top:30px;">
	
								</div>
							</div>
							<div class="select-item jfInfo">
								<table class="table table-bordered table-hover text-center" style="margin-top: 20px;font-size:14px;">
									<tr class="first_tr">
										<th width="15%" class="text-center">参保人</th>
										<th width="20%" class="text-center">缴费时间</th>
										<th width="15%" class="text-center">参保时长</th>
										<th width="20%" class="text-center">参保时间</th>
										<th width="10%" class="text-center">参保基数</th>
										<th width="10%" class="text-center">缴费金额</th>
										<th width="10%" class="text-center">服务费</th>
									</tr>
									<tbody id="order_box"></tbody>
								</table>
								<div id="pageTool1" style="width:90%;margin-top:30px;"></div>
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
		<script type="text/javascript" src="js/paging.js"></script>
		<script type="text/javascript" src="./js/shbxUserDetail.js" ></script>

	</body>
</html>
