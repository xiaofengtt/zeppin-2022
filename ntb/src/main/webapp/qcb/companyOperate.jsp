<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css" />
<link rel="stylesheet" href="./css/table.css" />
<link rel="stylesheet" href="css/uploadfile.css" />
<link rel="stylesheet" href="./css/companyMsg.css" />
<link rel="stylesheet" href="./css/companyOperate.css" />
<!--[if lte IE 8]>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.js"></script>
<![endif]-->
</head>
<body>
	<input id="pageId" type="hidden" value="a051ed00-ff27-11e7-ac6d-fcaa14314cbe"/>
	<jsp:include page="header.jsp"/>
	<main>
		<jsp:include page="contentLeft.jsp"/>
		<div class="main-right">
		<form:form id="editBase" action="../rest/qcb/companyAccount/editBase" method="post">
			<div class="main-right-body box loadingOver">
				<div class="main-right-body-header">
					<h3 class="box-title">基本信息</h3>
				</div>

				<div class="msg-box msg-box-edit editCompanyBox">
					<div id="imageShows" ></div>
					<div class="fileDiv">
						<label class="btn" style="padding:0;margin-left:0;height:33px;line-height:33px;">
							更换LOGO
							<div class="uploadLogo" style="opacity:0;fillter:alpha(opacity=0);position:absolute;top:0;
							width:100%;text-align:center;height:30px;overflow:hidden;border:none;">
								<div id="resourceId">

								</div>
							</div>
						</label>

						<div id="resourceAdd"><input type="hidden" name="logo" id="logo" value=""></div>
					</div>
					<div class="msg-item">
						<label class="msg-item-key">企业ID：</label>
						<span class="msg-item-value companyIDEdit"></span>
						<!-- <input class="companyIDEdit" type="text" value="" readonly="readonly" style="background-color:#eee;" /> -->
						<span class="tips color-red">企业ID不能为空</span>
					</div>
					<div class="msg-item">
						<label class="msg-item-key"><i>*</i>企业名称：</label>
						<input class="companyNameEdit" type="text" value="" name="company" placeholder="请输入企业名称" />
						<span class="tips color-red">企业名称不能为空</span>
					</div>
					<div class="msg-item">
						<label class="msg-item-key"><i>*</i>地址：</label>
						<select class="province">
							<option value="0">请选择</option>
						</select>
						<select class="city">
							<option value="0">请选择</option>
						</select>
						<select class="county" name="area">
							<option value="0">请选择</option>
						</select>
						<input class="companyAddressEdit" type="text" value="" name="address" placeholder="请输入详细地址" />
						<span class="tips color-red">地址不能为空</span>
					</div>
					<div class="msg-item">
						<label class="msg-item-key"><i>*</i>电话：</label>
						<input class="companyTelEdit" type="text" value="" name="phone" placeholder="请输入电话" />
						<span class="tips color-red">请填写正确的电话</span>
					</div>
					<div class="msg-item">
						<label class="msg-item-key"><i>*</i>法定代表人：</label>
						<input class="companyLegalEdit" type="text" value="" name="corporation" placeholder="请输入法定代表人" />
						<span class="tips color-red">法定代表人不能为空</span>
					</div>
				</div>

			</div>
			<div class="box main-right-body loadingOver">
				<div class="main-right-body-header">
					<h3 class="box-title">开票信息</h3>
				</div>
				<div class="msg-box msg-box-edit editBillingBox">
					<div class="msg-item">
						<label class="msg-item-key">公司名称：</label>
						<input class="billingNameEdit" type="text" value="" name="taxCompany" placeholder="请输入公司名称" />
						<span class="tips color-red">公司名称不能为空</span>
					</div>
					<div class="msg-item">
						<label class="msg-item-key">纳税人识别号：</label>
						<input class="billingNumberEdit" type="text" value="" name="taxIdentificationNum" placeholder="请输入纳税人识别号，若三证合一，填写统一社会信用代码即可" />
						<span class="tips color-red">请填写正确的纳税人识别号</span>
					</div>
					<div class="msg-item">
						<label class="msg-item-key">注册地址：</label>
						<input class="billingAddressEdit" type="text" value="" name="taxAddress" placeholder="请输入注册地址" />
						<span class="tips color-red">注册地址不能为空</span>
					</div>
					<div class="msg-item">
						<label class="msg-item-key">注册电话：</label>
						<input class="billingTelEdit" type="text" value="" name="taxPhone" placeholder="请输入注册电话" />
						<span class="tips color-red">请填写正确的注册电话，例：010-XXXXXXXX</span>
					</div>
					<div class="msg-item">
						<label class="msg-item-key">银行账号：</label>
						<input class="billingAccountEdit" type="text" value="" placeholder="请输入银行账号" 
						onkeyup="this.value =this.value.replace(/\s/g,'').replace(/[^\d]/g,'').replace(/(\d{4})(?=\d)/g,'$1 ');" />
						<span class="tips color-red">请填写正确的银行账号</span>
					</div>
					<div class="msg-item">
						<label class="msg-item-key">银行开户行：</label>
						<input class="billingBankEdit" type="text" value="" name="openBank" placeholder="请输入银行开户行，具体到分行／支行" />
						<span class="tips color-red">请填写正确的银行开户行</span>
					</div>
				</div>
			</div>

			<div class="box main-right-body loadingOver">
				<div class="main-right-body-header">
					<h3 class="box-title">联系人信息</h3>
				</div>
				<div class="msg-box msg-box-edit editContactBox">
					<div class="msg-item">
						<label class="msg-item-key"><i>*</i>联系人：</label>
						<input class="contactNameEdit" type="text" value="" name="name" placeholder="请输入联系人" />
						<span class="tips color-red">联系人不能为空</span>
					</div>
					<div class="msg-item">
						<label class="msg-item-key"><i>*</i>联系电话：</label>
						<input class="contactTelEdit" type="text" value="" name="mobile" placeholder="请输入联系电话" />
						<span class="tips color-red">请填写正确的联系电话</span>
					</div>
					<div class="msg-item">
						<label class="msg-item-key"><i>*</i>身份证号：</label>
						<input class="contactIDnumberEdit" type="text" value="" name="idcard" placeholder="请输入身份证号" />
						<span class="tips color-red">请填写正确的身份证号</span>
					</div>
					<div class="msg-item">
						<label class="msg-item-key"><i>*</i>电子邮箱：</label>
						<input class="contactEmailEdit" type="text" value="" name="email" placeholder="请输入电子邮箱" />
						<span class="tips color-red">请填写正确的电子邮箱</span>
					</div>
				</div>

			</div>
			<div class="box main-right-body loadingOver">
					<div class="main-right-body-header">
						<h3 class="box-title">资质信息</h3>
					</div>
					<div class="msg-boxs first">
						<p><i>*</i>营业执照：</p>
						<div class="upload-left">
							<div class="uploadLogo" style="text-align:center;">
								<img id="bussnessLicessShow" style="border:0;max-width:100%;max-height:150px;margin:15px 0;">
								<div id="bussnessLicessId">

								</div>
							</div>
							<div id="bussnessLicessAdd"><input type="hidden" name="businessLicence" id="bussnessLicess" value=""></div>
							<a class="reupload">重新上传</a>
						</div>
						<div class="upload-right">
							<label>说明：</label>
							<p>请上传加盖公司公章的营业执照扫描件，格式支持：JPG/PNG（大小不超过10M）。</p>
						</div>
						<div class="clear"></div>
					</div>
					<div class="msg-boxs second" style="position:relative;">
						<p><i>*</i>企业授权材料：</p>
						<a href="../resource/applicationLetter.docx" target="_blank" class="downloadModel">点击下载模板</a>
						<div class="upload-left">
							<div class="uploadLogo" style="text-align:center;">
								<img id="evidenceShow" style="border:0;max-width:100%;max-height:150px;margin:15px 0;">
								<div id="evidenceId">

								</div>
							</div>
							<div id="evidenceAdd"><input type="hidden" name="evidence" id="evidence" value=""></div>
							<a class="reupload">重新上传</a>
						</div>
						<div class="upload-right">
							<label>说明：</label>
							<p>请下载企业认证申请公函模版，填写相关内容，签字并加盖公章后上传扫描件，格式支持：JPG/PNG（大小不超过10M）。</p>
						</div>
						<div class="clear"></div>
					</div>
					<div class="msg-boxs third">
						<p><i>*</i>授权人身份证正面：</p>
						<div class="upload-left">
							<div class="uploadLogo" style="text-align:center;">
								<img id="idcardFaceShow" style="border:0;max-width:100%;max-height:150px;margin:15px 0;">
								<div id="idcardFaceId">

								</div>
							</div>
							<div id="idcardFacedd"><input type="hidden" name="idcardFace" id="idcardFace" value=""></div>
							<a class="reupload">重新上传</a>
						</div>
						<div class="upload-right">
							<label>说明：</label>
							<p>请上传企业管理员身份证正面扫描件，需保证证件号码清晰完整可见，格式支持：JPG/PNG（大小不超过2M）。</p>
						</div>
						<div class="clear"></div>
					</div>
					<div class="msg-boxs forth">
						<p><i>*</i>授权人身份证反面：</p>
						<div class="upload-left">
							<div class="uploadLogo" style="text-align:center;">
								<img id="idcardbackShow" style="border:0;max-width:100%;max-height:150px;margin:15px 0;">
								<div id="idcardbackId">

								</div>
							</div>
							<div id="idcardbackAdd"><input type="hidden" name="idcardBack" id="idcardback" value=""></div>
							<a class="reupload">重新上传</a>
						</div>
						<div class="upload-right">
							<label>说明：</label>
							<p>请上传企业管理员身份证反面扫描件，需保证证件号码清晰完整可见，格式支持：JPG/PNG（大小不超过2M）。</p>
						</div>
						<div class="clear"></div>
					</div>

				</div>
				<div class="box main-right-body loadingOver" style="margin-top:30px;">
					<div class="main-right-body-header">
						<h3 class="box-title">历史审核记录</h3>

					</div>
					<div style="padding:30px 30px 50px 30px;">
						<table class="table" cellspacing="0" cellpadding="0">
							<tr class="first-tr">
								<th class="td-padding-item" width="30%">审核时间</th>
								<th width="25%">审核结果</th>
								<th width="45%">审核原因</th>
							</tr>
							<!-- <tr><td colspan='4' style='text-align:center'>暂无审核记录</td></tr> -->
						</table>
					</div>

				</div>
				<div class="btn-g loadingOver" style="width:200px;margin:0 auto;">
					<a class="btn gobackBtn">返回</a>
					<a class="btn submitBtn sure">提交</a>
					<div class="clear"></div>
				</div>
				
				<!-- loading -->
				<div class="loadingDiv"></div>
			</form:form>
		</div>

	</main>

	<jsp:include page="footer.jsp"/>
	<script type="text/javascript" src="js/jquery.uploadfile.min.js" ></script>
	<script src="./js/companyOperate.js?v=<%=Math.random()%>"></script>
</body>
</html>
