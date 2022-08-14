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
							<td class="nameTd">姓名：<span>{{:realname}}</span></td>
							<td>手机号：<span>{{:mobile}}</span></td>
							<td>状态：<span {{if statusCN == "正常"}}style="color:green"{{else}}style="color:red"{{/if}}>{{:statusCN}}</span></td>
							<td class="operation text-center">
								<!-- {{if checkstatus == "unchecked"}} -->
								<a class="editBtn btn-edit checkbtn" href="investorIdcardImgCheckDetail.jsp?uuid={{:idcardImg}}">审核证件</a>
								<!-- {{/if}} -->
								<a class="editBtn btn-edit" href="investorDetail.jsp?uuid={{:uuid}}">查看</a>
							</td>
						</tr>
					</table>
				</div>

				<div class="msg_box_content">
					<div class="msg_box_photo">
						<img src="./img/true_header.png" class="head_photo">
						<div class="msg_icon">
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

						</div>
					</div>

					<div class="msg_box_info">
						<div class="msg_box_info_item">
							<span>账户总资产</span>
							<span>账户余额</span>
							<span>账户投资额</span>
							<span>历史总收益</span>
						</div>
						<div class="msg_box_info_item">
							<span>{{:totalAmount}}</span>
							<span>{{:accountBalanceCN}}</span>
							<span>{{:totalInvestCN}}</span>
							<span>{{:totalReturnCN}}</span>
						</div>
					</div>
					<p class="create_time_p">{{:createtimeCN}} 注册</p>
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
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00700002" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a href="">后台用户管理</a><span>></span><a class="current">投资者用户管理</a></div>
					<div class="clear"></div>
				</div>
				<div class="main-contain pt-13 pl-14 pr-16">

					<div class="condition">
						<div class="statusDiv shortStatusDiv filter">
							<label>审核状态：</label>
							<div>
								<a id="all">全部<span id="allCount">(0)</span></a>
								<a id="notupload">未上传<span id="notuploadCount">(0)</span></a>
								<a id="checked">审核通过<span id="checkedCount">(0)</span></a>
								<a id="unchecked" class="statusLight">待审核<span id="uncheckedCount">(0)</span></a>
								<a id="unpassed">审核不通过<span id="unpassedCount">(0)</span></a>
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
		<script type="text/javascript" src="./js/investorList.js" ></script>

	</body>
</html>
