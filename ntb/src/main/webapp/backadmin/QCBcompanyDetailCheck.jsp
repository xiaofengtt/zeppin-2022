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
		<link rel="stylesheet" href="./css/QCBcompanyDetailCheck.css">

		<script id="DataTpl" type="text/template">
			{{if type == "add"}}
			<!--添加  start-->
				<div class="item-box">
					<p class="item-box-title">企业信息</p>
					<div class="msg-item" style="text-align:center">
						{{if newData.logoUrl != null}}
							<img src="..{{:newData.logoUrl}}" class="head_photo">
						{{else}}
							<img src="../resource/qcb_company_default.png" class="head_photo">
						{{/if}}
					</div>
					<div class="msg-item">
						<span class="msg-item-key">企业ID：</span>
						<span class="msg-item-value">{{:newData.merchantId}}</span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">企业名称：</span>
						<span class="msg-item-value">{{:newData.name}}</span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">地址：</span>
						<span class="msg-item-value">{{:newData.bkAreaCN}}{{:newData.address}}</span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">联系电话：</span>
						<span class="msg-item-value">{{:newData.phone}}</span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">法定代表人：</span>
						<span class="msg-item-value">
							{{if newData.corporation == null}}
								-
							{{else}}
								{{:newData.corporation}}
							{{/if}}
						</span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">注册时间：</span>
						<span class="msg-item-value">{{:newData.createtimeCN}}</span>
					</div>
				</div>


				<div class='item-box'>
					<p class="item-box-title">开票信息</p>
					<div class="msg-item">
						<span class="msg-item-key">税务识别号：</span>
						<span class="msg-item-value">{{:newData.taxIdentificationNum}}</span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">企业名称：</span>
						<span class="msg-item-value">{{:newData.taxCompany}}</span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">地址：</span>
						<span class="msg-item-value">{{:newData.taxAddress}}</span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">电话：</span>
						<span class="msg-item-value">{{:newData.taxPhone}}</span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">开户行：</span>
						<span class="msg-item-value">{{:newData.openBank}}</span>
					</div>

					<div class="msg-item">
						<span class="msg-item-key">银行账号：</span>
						<span class="msg-item-value">{{:newData.openBankCardnum}}</span>
					</div>
				</div>

				<div class="item-box">
					<p class="item-box-title">联系人信息</p>
					<div class="msg-item">
						<span class="msg-item-key">企业联系人：</span>
						<span class="msg-item-value">{{:newData.contacts}}</span>
					</div>

					<div class="msg-item">
						<span class="msg-item-key">联系人电话：</span>
						<span class="msg-item-value">{{:newData.contactsMobile}}</span>
					</div>

					<div class="msg-item">
						<span class="msg-item-key">联系人身份证号：</span>
						<span class="msg-item-value">{{:newData.contactsIdcard}}</span>
					</div>

					<div class="msg-item">
						<span class="msg-item-key">联系人邮箱：</span>
						<span class="msg-item-value">{{:newData.contactsEmail}}</span>
					</div>
				</div>

				<div class="item-box">
					<p class="item-box-title">营业执照</p>
					<div class="msg-item">
						{{if newData.businessLicence == ""}}
							&nbsp;&nbsp;&nbsp;<span class="msg-item-value">无</span>
						{{else}}
							<a href="..{{:newData.businessLicenceURL}}" target="_blank">
								<img src="..{{:newData.businessLicenceURL}}" alt="" class="confirm-img">
							</a>
						{{/if}}

					</div>
				</div>

				<div class="item-box">
					<p class="item-box-title">企业授权材料</p>
					<div class="msg-item">
						{{if newData.evidence == ""}}
							&nbsp;&nbsp;&nbsp;<span class="msg-item-value">无</span>
						{{else}}
							<a href="..{{:newData.evidenceURL}}" target="_blank">
								<img src="..{{:newData.evidenceURL}}" alt="" class="confirm-img">
							</a>
						{{/if}}
					</div>
				</div>

				<div class="item-box">
					<p class="item-box-title">授权人身份证</p>
					<div class="msg-item">
						{{if newData.idcardFace == "" || newData.idcardBack == ""}}
							&nbsp;&nbsp;&nbsp;<span class="msg-item-value">无</span>
						{{else}}
							<a href="..{{:idcardFaceURL}}" class="id-card" target="_blank">
								<img src="..{{:idcardFaceURL}}" alt="" class="">
							</a>
							<a href="..{{:idcardBackURL}}" class="id-card" target="_blank">
								<img src="..{{:idcardBackURL}}" alt="" class="">
							</a>
						{{/if}}
					</div>
				</div>
			<!--添加 end-->
			{{else type == "edit"}}
			<!--修改 start-->
				<div class="item-box">
					<p class="item-box-title">企业信息</p>
					<div class="msg-item" style="text-align:center">
						{{if oldData.logoUrl == newData.logoUrl}}
							{{if oldData.logoUrl != null}}
								<img src="..{{:newData.logoUrl}}" class="head_photo">
							{{else}}
								<img src="../resource/qcb_company_default.png" class="head_photo">
							{{/if}}

						{{else}}
							{{if oldData.logoUrl != null}}
								<img src="..{{:oldData.logoUrl}}" class="head_photo">
							{{else}}
								<img src="../resource/qcb_company_default.png" class="head_photo">
							{{/if}}
							&nbsp;&nbsp;
							<span class="color-red">变更为：</span>
							&nbsp;&nbsp;
							<img src="..{{:newData.logoUrl}}" class="head_photo">
						{{/if}}
					</div>

					<div class="msg-item">
						<span class="msg-item-key">企业ID：</span>
						<span class="msg-item-value">{{:oldData.merchantId}}</span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">企业名称：</span>
						{{if newData.name == oldData.name}}
							<span class="msg-item-value">{{:newData.name}}</span>
						{{else}}
							<span class="msg-item-value">{{:oldData.name}}</span>
							<span class="msg-item-value color-red">变更为：</span>
							<span class="msg-item-value color-red">{{:newData.name}}</span>
						{{/if}}
					</div>
					<div class="msg-item">
						<span class="msg-item-key">地址：</span>
						{{if newData.bkAreaCN == oldData.bkAreaCN && newData.address == oldData.address}}
							<span class="msg-item-value">{{:newData.bkAreaCN}}{{:newData.address}}</span>
						{{else}}
							<span class="msg-item-value">{{:oldData.bkAreaCN}}{{:oldData.address}}</span>
							<span class="msg-item-value color-red">变更为：</span>
							<span class="msg-item-value color-red">{{:newData.bkAreaCN}}{{:newData.address}}</span>
						{{/if}}
					</div>
					<div class="msg-item">
						<span class="msg-item-key">联系电话：</span>
						{{if newData.phone == oldData.phone}}
							<span class="msg-item-value">{{:newData.phone}}</span>
						{{else}}
							<span class="msg-item-value">{{:oldData.phone}}</span>
							<span class="msg-item-value color-red">变更为：</span>
							<span class="msg-item-value color-red">{{:newData.phone}}</span>
						{{/if}}
					</div>
					<div class="msg-item">
						<span class="msg-item-key">法定代表人：</span>
						{{if newData.corporation == oldData.corporation}}
							<span class="msg-item-value">
								{{if newData.corporation == null}}
									无
								{{else}}
									{{:newData.corporation}}
								{{/if}}
							</span>
						{{else}}
							<span class="msg-item-value">
								{{if oldData.corporation == null}}
									无
								{{else}}
									{{:oldData.corporation}}
								{{/if}}
							</span>
							<span class="msg-item-value color-red">变更为：</span>
							<span class="msg-item-value color-red">
								{{if newData.corporation == null}}
									无
								{{else}}
									{{:newData.corporation}}
								{{/if}}
							</span>
						{{/if}}

					</div>
					<div class="msg-item">
						<span class="msg-item-key">注册时间：</span>
						{{if newData.createtimeCN == oldData.createtimeCN}}
							<span class="msg-item-value">{{:newData.createtimeCN}}</span>
						{{else}}
							<span class="msg-item-value">{{:oldData.createtimeCN}}</span>
							<span class="msg-item-value color-red">变更为：</span>
							<span class="msg-item-value color-red">{{:newData.createtimeCN}}</span>
						{{/if}}
					</div>
				</div>


				<div class='item-box'>
					<p class="item-box-title">开票信息</p>
					<div class="msg-item">
						<span class="msg-item-key">税务识别号：</span>
						{{if newData.taxIdentificationNum == oldData.taxIdentificationNum}}
							<span class="msg-item-value">{{:newData.taxIdentificationNum}}</span>
						{{else}}
							<span class="msg-item-value">{{:oldData.taxIdentificationNum}}</span>
							<span class="msg-item-value color-red">变更为：</span>
							<span class="msg-item-value color-red">{{:newData.taxIdentificationNum}}</span>
						{{/if}}
					</div>
					<div class="msg-item">
						<span class="msg-item-key">企业名称：</span>
						{{if newData.taxCompany == oldData.taxCompany}}
							<span class="msg-item-value">{{:newData.taxCompany}}</span>
						{{else}}
							<span class="msg-item-value">{{:oldData.taxCompany}}</span>
							<span class="msg-item-value color-red">变更为：</span>
							<span class="msg-item-value color-red">{{:newData.taxCompany}}</span>
						{{/if}}
					</div>
					<div class="msg-item">
						<span class="msg-item-key">地址：</span>
						{{if newData.taxAddress == oldData.taxAddress}}
							<span class="msg-item-value">{{:newData.taxAddress}}</span>
						{{else}}
							<span class="msg-item-value">{{:oldData.taxAddress}}</span>
							<span class="msg-item-value color-red">变更为：</span>
							<span class="msg-item-value color-red">{{:newData.taxAddress}}</span>
						{{/if}}
					</div>
					<div class="msg-item">
						<span class="msg-item-key">电话：</span>
						{{if newData.taxPhone == oldData.taxPhone}}
							<span class="msg-item-value">{{:newData.taxPhone}}</span>
						{{else}}
							<span class="msg-item-value">{{:oldData.taxPhone}}</span>
							<span class="msg-item-value color-red">变更为：</span>
							<span class="msg-item-value color-red">{{:newData.taxPhone}}</span>
						{{/if}}
					</div>
					<div class="msg-item">
						<span class="msg-item-key">开户行：</span>
						{{if newData.openBank == oldData.openBank}}
							<span class="msg-item-value">{{:newData.openBank}}</span>
						{{else}}
							<span class="msg-item-value">{{:oldData.openBank}}</span>
							<span class="msg-item-value color-red">变更为：</span>
							<span class="msg-item-value color-red">{{:newData.openBank}}</span>
						{{/if}}
					</div>

					<div class="msg-item">
						<span class="msg-item-key">银行账号：</span>
						{{if newData.openBankCardnum == oldData.openBankCardnum}}
							<span class="msg-item-value">{{:newData.openBankCardnum}}</span>
						{{else}}
							<span class="msg-item-value">{{:oldData.openBankCardnum}}</span>
							<span class="msg-item-value color-red">变更为：</span>
							<span class="msg-item-value color-red">{{:newData.openBankCardnum}}</span>
						{{/if}}
					</div>
				</div>

				<div class="item-box">
					<p class="item-box-title">联系人信息</p>
					<div class="msg-item">
						<span class="msg-item-key">企业联系人：</span>
						{{if newData.contacts == oldData.contacts}}
							<span class="msg-item-value">{{:newData.contacts}}</span>
						{{else}}
							<span class="msg-item-value">{{:oldData.contacts}}</span>
							<span class="msg-item-value color-red">变更为：</span>
							<span class="msg-item-value color-red">{{:newData.contacts}}</span>
						{{/if}}
					</div>

					<div class="msg-item">
						<span class="msg-item-key">联系人电话：</span>
						{{if newData.contactsMobile == oldData.contactsMobile}}
							<span class="msg-item-value">{{:newData.contactsMobile}}</span>
						{{else}}
							<span class="msg-item-value">{{:oldData.contactsMobile}}</span>
							<span class="msg-item-value color-red">变更为：</span>
							<span class="msg-item-value color-red">{{:newData.contactsMobile}}</span>
						{{/if}}
					</div>

					<div class="msg-item">
						<span class="msg-item-key">联系人身份证号：</span>
						{{if newData.contactsIdcard == oldData.contactsIdcard}}
							<span class="msg-item-value">{{:newData.contactsIdcard}}</span>
						{{else}}
							<span class="msg-item-value">{{:oldData.contactsIdcard}}</span>
							<span class="msg-item-value color-red">变更为：</span>
							<span class="msg-item-value color-red">{{:newData.contactsIdcard}}</span>
						{{/if}}
					</div>

					<div class="msg-item">
						<span class="msg-item-key">联系人邮箱：</span>
						{{if newData.contactsEmail == oldData.contactsEmail}}
							<span class="msg-item-value">{{:newData.contactsEmail}}</span>
						{{else}}
							<span class="msg-item-value">{{:oldData.contactsEmail}}</span>
							<span class="msg-item-value color-red">变更为：</span>
							<span class="msg-item-value color-red">{{:newData.contactsEmail}}</span>
						{{/if}}
					</div>
				</div>

				<div class="item-box">
					<p class="item-box-title">营业执照</p>
					<div class="msg-item">
						{{if newData.businessLicence == oldData.businessLicence}}
							{{if newData.businessLicence == ""}}
								&nbsp;&nbsp;&nbsp;<span class="msg-item-value">无</span>
							{{else}}
								<a href="..{{:newData.businessLicenceURL}}" target="_blank">
									<img src="..{{:newData.businessLicenceURL}}" alt="" class="confirm-img">
								</a>
							{{/if}}
						{{else}}
							{{if oldData.businessLicence == ""}}
								&nbsp;&nbsp;&nbsp;<span class="msg-item-value" style="vertical-align:middle">无</span>
							{{else}}
								<a href="..{{:oldData.businessLicenceURL}}" target="_blank">
									<img src="..{{:oldData.businessLicenceURL}}" alt="" class="confirm-img">
								</a>
							{{/if}}
							<span class="msg-item-value color-red" style="vertical-align:middle">变更为：</span>
							<a href="..{{:newData.businessLicenceURL}}" target="_blank">
								<img src="..{{:newData.businessLicenceURL}}" alt="" class="confirm-img">
							</a>
						{{/if}}
					</div>
				</div>

				<div class="item-box">
					<p class="item-box-title">企业授权材料</p>
					<div class="msg-item">
						{{if newData.evidence == oldData.evidence}}
							{{if newData.evidence == ""}}
								&nbsp;&nbsp;&nbsp;<span class="msg-item-value">无</span>
							{{else}}
								<a target="_blank" href="..{{:newData.evidenceURL}}">
									<img src="..{{:newData.evidenceURL}}" alt="" class="confirm-img">
								</a>
							{{/if}}
						{{else}}
							{{if oldData.evidence == ""}}
								&nbsp;&nbsp;&nbsp;<span class="msg-item-value" style="vertical-align:middle">无</span>
							{{else}}
								<a target="_blank" href="..{{:oldData.evidenceURL}}">
									<img src="..{{:oldData.evidenceURL}}" alt="" class="confirm-img">
								</a>
							{{/if}}
							<span class="msg-item-value color-red" style="vertical-align:middle">变更为：</span>
							<a target="_blank" href="..{{:newData.evidenceURL}}">
								<img src="..{{:newData.evidenceURL}}" alt="" class="confirm-img">
							</a>
						{{/if}}
					</div>
				</div>

				<div class="item-box">
					<p class="item-box-title">授权人身份证</p>
					<div class="msg-item">
						{{if newData.idcardFace == oldData.idcardFace && newData.idcardBack == oldData.idcardBack}}
							{{if newData.idcardFace == "" || newData.idcardBack == ""}}
								&nbsp;&nbsp;&nbsp;<span class="msg-item-value">无</span>
							{{else}}
								<a href="..{{:newData.idcardFaceURL}}" target="_blank" class="id-card">
									<img src="..{{:newData.idcardFaceURL}}" alt="" class="">
								</a>
								<a href="..{{:newData.idcardBackURL}}" target="_blank" class="id-card">
									<img src="..{{:newData.idcardBackURL}}" alt="" class="">
								</a>
							{{/if}}
						{{else}}
							{{if oldData.idcardFace == "" || oldData.idcardBack == ""}}
								&nbsp;&nbsp;&nbsp;<span class="msg-item-value" style="vertical-align:middle">无</span>
							{{else}}
								<a target="_blank" class="id-card" href="..{{:oldData.idcardFaceURL}}">
									<img src="..{{:oldData.idcardFaceURL}}" alt="">
								</a>
								<a target="_blank" class="id-card" href="..{{:oldData.idcardFaceURL}}">
									<img src="..{{:oldData.idcardBackURL}}" alt="">
								</a>
							{{/if}}
							<br/>
							<br/>
							&nbsp;&nbsp;<span class="msg-item-value color-red">变更为：</span>
							<br/>
							<br/>
							<a target="_blank" href="..{{:newData.idcardFaceURL}}" class="id-card">
								<img src="..{{:newData.idcardFaceURL}}" alt="">
							</a>
							<a target="_blank" href="..{{:newData.idcardBackURL}}" class="id-card">
								<img src="..{{:newData.idcardBackURL}}" alt="">
							</a>
						{{/if}}
					</div>
				</div>
			<!--修改 end-->
			{{/if}}

			{{if status != "unchecked"}}
				<div class="item-box">
					<p class="item-box-title">审核信息</p>
					<div class="msg-item">
						<span class="msg-item-key">审核人：</span>
						<span class="msg-item-value">{{:checkerName}}</span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">审核时间：</span>
						<span class="msg-item-value">{{:checktimeCN}}</span>
					</div>
					<div class="msg-item">
						<span class="msg-item-key">审核状态：</span>
						{{if status == "checked"}}
							<span class="msg-item-value color-green">{{:statusCN}}</span>
						{{else}}
							<span class="msg-item-value color-red" style="margin:0">{{:statusCN}}</span>
						{{/if}}
					</div>
					<div class="msg-item">
						<span class="msg-item-key">审核信息：</span>
						<span class="msg-item-value">{{:reason}}</span>
					</div>
				</div>
			{{/if}}

			{{if status != "unchecked"}}
				<div class="btn_group">
					<a onclick="window.location.href=document.referrer;">返回</a>
				</div>
			{{else}}
				<div class="btn_group">
					<a onclick="window.location.href=document.referrer;">返回</a>
					<a id="allRight">审核通过</a>
					<a href="./QCBcompanyConfirmPassreason.jsp?uuid={{:uuid}}" id="pass">审核不通过</a>
				</div>
				<form:form id="checked" action="../rest/backadmin/qcbcompany/operateCheck" method="post">
			        <input type="hidden" name="qcbCompanyOperate" value="{{:uuid}}">
			        <input type="hidden" name="status" value="checked">
			    </form:form>
			{{/if}}
		</script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00800085" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a href="./QCBcompanyListCheck.jsp">企业用户审核</a><span>></span><a class="current">企业用户详情</a></div>
					<div class="clear"></div>
				</div>
				<div class="layerOpen">
					<div id="formsubmit">
						<div class="contents">
							<div id="DataCnt"></div>





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
		<script type="text/javascript" src="js/jquery.form.js" ></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/bootstrap.js" ></script>
		<script src="js/paging.js"></script>
		<script type="text/javascript" src="./js/QCBcompanyDetailCheck.js" ></script>

	</body>
</html>
