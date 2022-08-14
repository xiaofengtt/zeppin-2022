<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="css/fundEdit.css" /> 
		<link rel="stylesheet" href="css/uploadfile.css">
		<link rel="stylesheet" href="css/base.css" />
		<link rel="stylesheet" href="css/shbxOrderDetail.css?v=1.0" />

		<script id="headTpl" type="text/template">
			<div class="content-item">	
					<div class="main-mid-content clearfix">
					<div>
						<h5>用户{{:creatorName}} 创建订单</h5>
						<div class="border border-o"></div>
						<div class="circle circle-o"></div>
						<p>{{:createtimeCN}}</p>
					</div>
				
				
					<div>
						<h5>用户{{:creatorName}} 支付订单</h5>
						<div class="border border-o"></div>
						<div class="circle circle-o"></div>
						<p>{{:createtimeCN}}</p>
					</div>
					{{if status == 'normal'}}
						<div>
							<h5>管理员{{:processCreatorName}} 处理订单</h5>
							<div class="border border-o"></div>
							<div class="circle circle-o"></div>
							<p>{{:processCreatetimeCN}}</p>
						</div>
					{{/if}}
					{{if status == 'unprocess'}}
						<div>
							<h5>处理订单</h5>
							<div class="border border-g"></div>
							<div class="circle circle-g"></div>
							<p>未处理</p>
						</div>
					{{/if}}
				</div>
			</div>
		</script>
		
		<script id="bodyTpl" type="text/template">
			<div class="contentDiv text_box">
				<p class="text_title">订单信息</p>
				<div class="content-item">
					<!-- 信息 -->
					<div class="content-item-info">
						<div class="form-group col-md-6">
							<label>参保人</label>
							<div class="content-items"><span>{{:shbxInsuredName}}</span></div>
							<div class="clear"></div>
						</div>
						<div class="form-group col-md-6">
							<label>订单号</label>
							<div class="content-items"><span>{{:orderNumber}}</span></div>
							<div class="clear"></div>
						</div>
						<div class="form-group col-md-6">
							<label>参保类型</label>
							<div class="content-items"><span>{{:insuredTypeCN}}</span></div>
							<div class="clear"></div>
						</div>
						<div class="form-group col-md-6">
							<label>开始时间</label>
							<div class="content-items"><span>{{:starttime}}</span></div>
							<div class="clear"></div>
						</div>
						<div class="form-group col-md-6">
							<label>参保时长</label>
							<div class="content-items"><span>{{:duration}}个月（{{:durationCN}}）</span></div>
							<div class="clear"></div>
						</div>
						<div class="form-group col-md-6">
							<label>参保地区</label>
							<div class="content-items"><span>北京市</span></div>
							<div class="clear"></div>
						</div>
						<div class="form-group col-md-6">
							<label>参保基数</label>
							<div class="content-items"><span>{{:cardinalNumberCN}}元</span></div>
							<div class="clear"></div>
						</div>
						<div class="form-group col-md-6">
							<label>代缴金额</label>
							<div class="content-items"><span>{{:benefitsCN}}</span></div>
							<div class="clear"></div>
						</div>
						<div class="form-group col-md-6">
							<label>服务费</label>
							<div class="content-items"><span>{{:serviceFeeCN}}</span></div>
							<div class="clear"></div>
						</div>
						<div class="form-group col-md-6">
							<label>支付总额</label>
							<div class="content-items"><span>{{:totalAmountCN}}</span></div>
							<div class="clear"></div>
						</div>
						<div class="form-group col-md-6">
							<label>订单状态</label>
							<div class="content-items"><span>{{:statusCN}}</span></div>
							<div class="clear"></div>
						</div>
						<div class="form-group col-md-6">
							<label>支付时间</label>
							<div class="content-items"><span>{{:createtimeCN}}</span></div>
							<div class="clear"></div>
						</div>
						<div class="form-group col-md-6">
							<label>原工作单位</label>
							<div class="content-items"><span>{{:sourceCompany}}</span></div>
							<div class="clear"></div>
						</div>
						<div class="clear"></div>
					</div>
				</div>
			</div>
			{{if status == 'normal'}}
			<div class="contentDiv text_box">
				<p class="text_title">处理信息</p>
				<div class="content-item">
					<!-- 信息 -->
					<div class="content-item-info">
						<div class="form-group col-md-12">
							<label>参保公司：</label>
							<div class="content-items"><span>{{:company}}</span></div>
							<div class="clear"></div>
						</div>
						<div class="form-group col-md-12">
							<label>参保凭证：</label>
							<div class="content-items">
								{{for receipts}}
									<img src="../{{:url}}" style="padding:10px;max-width:100%;"/>
								{{/for}}
							</div>
							<div class="clear"></div>
						</div>
						<div class="clear"></div>
					</div>
				</div>
			</div>
			{{/if}}
				{{if status == 'unprocess'}}
					<div class="btnGroup" style="text-align:center">
						<a href="shbxOrderProcess.jsp?uuid={{:uuid}}" class="process sureBtn btn">处理</a>
						<a class="btn cancleBtn" href="shbxOrderList.jsp">返回</a>
					</div>
				{{/if}}			
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="01000102" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location"><a href="shbxOrderList.jsp">社保熊订单管理</a><span>></span><a class="current">社保熊订单详情</a></div>
				<div class="title-contain" id="head_box">
					
				</div>
				<div class="main-contain" id="body_box">
					
				</div>
			</div>
			<div class="clear"></div>
		</div>
		
		<script type="text/javascript" src="js/jquery-1.11.1.js" ></script>
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/jsrender.min.js"></script>
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script type="text/javascript" src="js/url.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.js" ></script>
		<script type="text/javascript" src="js/flagSubmit.js"></script>
		<script type="text/javascript" src="js/shbxOrderDetail.js" ></script>
	</body>
</html>
