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
<link rel="stylesheet" href="./css/paging.css" />
<link rel="stylesheet" href="./css/page.css" />
<link rel="stylesheet" href="./css/table.css" />
<link rel="stylesheet" href="css/uploadfile.css" />
<link rel="stylesheet" href="css/datepicker3.css" >
<link rel="stylesheet" href="./css/extendWages.css" />
<!--[if lte IE 8]>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.js"></script>
<![endif]-->
</head>
<body>
	<input id="pageId" type="hidden" value="3ffdb50c-ff27-11e7-ac6d-fcaa14314cbe"/>
	<jsp:include page="header.jsp"/>
	<main>
		<jsp:include page="contentLeft.jsp"/>
		<div class="main-right">
				<!-- 上传薪资单form -->
				<form:form id="upload" action="../rest/qcb/companyPayroll/upload" method="post">
				<!--上传福利单-->
				<div class="box module-1 loadingOver">
					<h3 class="box-title">福利发放<small>-上传福利单</small></h3>
					<div class="bar">
						<div class="bar-item">
							<img src="./img/上传-r.png" alt="" />
							<p class="color-orange">上传福利单</p>
						</div>
						<div class="bar-item">
							<img src="./img/已完成-g.png" alt="" />
							<p class="color-gray">请核对数据</p>
						</div>
						<div class="bar-item">
							<img src="./img/发送-g.png" alt="" />
							<p class="color-gray">预览并发送</p>
						</div>
						<div class="bar-pro">
							<div class="bar-pro-inner" style="width:33%"></div>
						</div>
					</div>

					<h3 class="box-title spmg">福利单信息</h3>
					<div class="module-1-form">
						<form action="">
							<div class="module-1-form-item">
								<span class="module-1-form-item-title">
									<i>*</i>
									标题：
								</span>
								<input type="text" name="title" placeholder="请输入标题" />
								<small class="color-gray">例如：2018年1月绩效工资、2017年终奖金、2018年第一季度销售部门提成等</small>
							</div>
							<div class="module-1-form-item">
								<span class="module-1-form-item-title">
									<i>*</i>
									发放时间：
								</span>
								<input class="form-control datepicker" id="collectStarttime" name="payTime"
								data-provide="datepicker" readonly='readonly' placeholder="请选择发放时间" />
							</div>
							<div class="module-1-form-item">
								<span class="module-1-form-item-title">
									<i>*</i>
									发放类型：
								</span>
								<select name="type">
									<option value="">请选择发放类型...</option>
									<option value="wage">工资</option>
									<option value="bonus">奖金</option>
									<option value="subsidy">补贴</option>
									<option value="reimbursement">报销</option>
									<option value="commission">提成</option>
									<option value="reissue">补发</option>
									<option value="other">其他</option>
								</select>
							</div>
							<div class="module-1-form-item">
								<span class="module-1-form-item-title">
									<i></i>
									激励话语：
								</span>
								<input type="text" name="remark" placeholder="写点对员工的激励话语吧~" />
								<small class="color-gray">若填写该信息，该信息会显示在员工接收的消息中</small>
							</div>
						</form>
					</div>
				</div>
				<!--box module-1-top-->

				<div class="box module-1 module-1-bottom loadingOver">
					<div class="module-1-bottom-header">
						<h3 class="box-title">导入福利表</h3>
						<a href="../resource/model.xlsx" target="_blank" class="download-excel">下载Excel模版</a>
					</div>
					<div class="module-1-bottom-content">
						<div class="module-1-bottom-content-left">
							<p>图例：</p>
							<div>
								<img src="./img/excelimg.png" alt="" />
								<label class="uploadFile uploadSalary">上传福利表</label>
								<!-- <input type="file" id="file" /> -->
								<div class="uploadLogo" style="text-align:center;border:none;">
									<img id="bussnessLicessShow" style="border:0;max-width:100%;max-height:150px;margin:15px 0;display:none;">
									<div id="bussnessLicessId" style="border:0;max-width:100%;max-height:150px;margin:15px 0;display:none;">

									</div>
								</div>
								<div id="bussnessLicessAdd"><input type="hidden" name="file" id="bussnessLicess" value=""></div>
							</div>
						</div>
						<div class="module-1-bottom-content-right">
							<p>上传要求：</p>
							<div>
								<p>1.支持xls/xlsx格式文件。</p>
								<p>2.必须包含列 <span class="color-orange">姓名、身份证号、手机号、实发金额</span>字段。</p>
								<p>3.首行务必为标题项，目前只支持单行表头，多行表头请拆分为单行。</p>
								<p>4.文件大小尽量不要超过2MB，数据超过1000行的建议分开上传</p>
							</div>
						</div>
					</div>
				</div>
				<!--box module-1-bottom-->
				</form:form>

				<!--薪金发放－核对数据-->
				<div class="box module-2">
					<h3 class="box-title">福利发放<small>-核对数据</small></h3>
					<div class="bar">
						<div class="bar-item">
							<img src="./img/上传-r.png" alt="" />
							<p class="color-orange">上传福利单</p>
						</div>
						<div class="bar-item">
							<img src="./img/已完成-r.png" alt="" />
							<p class="color-orange">请核对数据</p>
						</div>
						<div class="bar-item">
							<img src="./img/发送-g.png" alt="" />
							<p class="color-gray">预览并发送</p>
						</div>
						<div class="bar-pro">
							<div class="bar-pro-inner" style="width:66%"></div>
						</div>
					</div>
					<div class="module-2-tablebox">
						<table cellspacing="0" cellpadding="0" class="table">

						</table>
					</div>
					<p class="totalCount">本次导入记录<span class="bold">40</span>条</p>
				</div>
				<!--module-2-top-->

				<div class="box module-2 module-2-form module-2-bottom">
					<h3 class="box-title">其他设置</h3>
					<form:form id="uploadForm" action="../rest/qcb/companyPayroll/update" method="post">
						<div class="module-2-form-item">
							<span class="module-2-form-item-title">
								<i></i>
								特殊奖励：
							</span>
							<select name="">
								<option>请选择特殊奖励列...</option>
							</select>
							<img src="./img/tip.png" alt="" style="vertical-align:middle;"/>
						</div>
						<div class="module-2-form-item" style="width:290px;margin-right:70px;display: inline-block;">
							<span class="module-2-form-item-title">
								<i></i>
								短信通知：
							</span>
							<div class="module-2-form-item-switch module-2-form-item-switch-flagSms">
								<div class="switch-true">否</div>
								<div class="switch-false">是</div>
								<div class="switchbar flagSms" data-boolean="false"></div>
							</div>
							<img src="./img/tip.png" alt="" class="tips" />
						</div>
						<div class="module-2-form-item" style="width:290px;display: inline-block;">
							<span class="module-2-form-item-title">
								<i></i>
								隐藏空数据：
							</span>
							<div class="module-2-form-item-switch">
								<div class="switch-true">是</div>
								<div class="switch-false">否</div>
								<div class="switchbar flagHide" data-boolean="true"></div>
							</div>
							<img src="./img/warn-g.png" alt="" class="tips" />
						</div>
						<div class="btn-g">
							<input type="button" class="btn-g-button" value="重新上传" id="reUpload" />
							<input type="button" class="btn-g-button preview" value="预览/发送" />
						</div>
					</form:form>
				</div>
				<!--module-2-bottom-->

				<!--预览并发送-->
				<div class="box module-3">
					<h3 class="box-title">福利发放<small>预览并发送</small></h3>
					<div class="bar">
						<div class="bar-item">
							<img src="./img/上传-r.png" alt="" />
							<p class="color-orange">上传福利单</p>
						</div>
						<div class="bar-item">
							<img src="./img/已完成-r.png" alt="" />
							<p class="color-orange">请核对数据</p>
						</div>
						<div class="bar-item">
							<img src="./img/发送-r.png" alt="" />
							<p class="color-orange">预览并发送</p>
						</div>
						<div class="bar-pro">
							<div class="bar-pro-inner" style="width:100%"></div>
						</div>
					</div>
					<div class="module-3-bottom">
						<p>总计发放<span class="color-orange">380,000</span>元</p>
						<div class="module-3-bottom-left">
							<div class="module-3-bottom-left-tablebox">
								<table class="table" cellspacing="0" cellpadding="0">
									<tr class="first-tr">
										<th class="td-padding-item" width="10%">姓名</th>
										<th width="20%">身份证</th>
										<th width="15%">手机</th>
										<th width="10%">实发工资</th>
									</tr>
									<tr>
										<td class="td-padding-item">荣景峰</td>
										<td>321321198910166529</td>
										<td>18360189283</td>
										<td> 50,000</td>
									</tr>
									<tr>
										<td class="td-padding-item">荣景峰</td>
										<td>321321198910166529</td>
										<td>18360189283</td>
										<td> 50,000</td>
									</tr>
									<tr>
										<td class="td-padding-item">荣景峰</td>
										<td>321321198910166529</td>
										<td>18360189283</td>
										<td> 50,000</td>
									</tr>
									<tr>
										<td class="td-padding-item">荣景峰</td>
										<td>321321198910166529</td>
										<td>18360189283</td>
										<td> 50,000</td>
									</tr>
									<tr>
										<td class="td-padding-item">荣景峰</td>
										<td>321321198910166529</td>
										<td>18360189283</td>
										<td> 50,000</td>
									</tr>
									<tr>
										<td class="td-padding-item">荣景峰</td>
										<td>321321198910166529</td>
										<td>18360189283</td>
										<td> 50,000</td>
									</tr>
									<tr>
										<td class="td-padding-item">荣景峰</td>
										<td>321321198910166529</td>
										<td>18360189283</td>
										<td> 50,000</td>
									</tr>
								</table>
							</div>
							
						</div>
						<!--module-3-bottom-left-->

						<div class="module-3-bottom-right">
							<div class="phone">
								<div class="hidden-box">
									<div class="phone-box">
										<div class="phone-top">
											<h3>2018年1月绩效工资</h3>
											<p><span>5,000</span>元</p>
											<span>感谢辛苦的你！</span>
										</div>
										<p class="info">明细</p>
										<div class="msg-listDiv">
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="btn-g" style="display:block;">
							<input type="button" class="btn-g-button stepPre" value="上一步" />
							<input type="button" class="btn-g-button" id="confirm" value="确认发放" />
						</div>
					</div>
				</div>

				<!-- 预览并 发送 end-->
				<div class="main-right-body box module-4">
					<div class="main-right-body-header">
						<h3 class="box-title">福利发放－发放成功</h3>
						<!--<a href="" class="with-draw-list">查看提现记录</a>-->
					</div>
					<div class="main-right-body-title">
						<img src="./img/withdrawLogo.png" alt="" />
						<h2>发放成功！</h2>
						<p class="grantSuccess">2018年1月绩效工资，已发放！</p>
					</div>
					<div class="main-right-content">
						<p class="content-title">
							提示：员工如何查看福利发放
						</p>
						<div class="main-right-content-bottom">
							<span>1.员工可在微信搜索“企财宝”公众号，点击个人账户，绑定身份后，可查看福利发放情况。</span>
							<span>2.员工可绑定任意本人银行卡进行福利提取。</span>
							<span>3.如使用短信通知功能，员工登记的手机上会收到一条本次福利发放的短信内容。</span>
						</div>
					</div>


				</div>
				<!-- 引导认证 -->
				<div class="guideDBox box">
					<div class="guideInner">
						<p>1分钟完成企业认证，享受更多特权与服务。</p>
						<a href="companyOperate.jsp" class="btn">去认证</a>
					</div>
				</div>
				<!-- 表格警告 -->
				<div class="box module-5">
					<!-- 错误枚举 -->
					<h3 class="box-title">
						错误信息
					</h3>
					<div class="errorBox module-2-tablebox">					
						<table class="table tableContent">
							<tr class="first-tr fixTr"><th class="td-padding-item" width="30%">所在行数</th><th width="70%">错误原因</th></tr>
						</table>
					</div>
					<a class="lookMore packDown" title="点击查看更多"></a>
					<a class="lookMore packUp" title="点击收起"></a>
				</div>
				<div class="box module-5">
					<!-- 错误枚举 -->
					<h3 class="box-title">福利发放<small>-核对数据</small></h3>
					<div class="errorList module-2-tablebox">
						<table class="table tableContent">
							
						</table>
						
					</div>
					<div class="btn-g">
						<input type="button" class="btn-g-button" value="重新上传" id="reUploads" />
					</div>
				</div>
				
				<!-- loading -->
				<div class="loadingDiv"></div>
				
			</div>
			
	</main>
	<!--弹出框-->
	<div class="bg">
		<div class="modal iframeInner">
			<div class="modal-header">
				<h3 class="box-title iframe-title">发放福利</h3>
				<a id="modal-close"></a>
			</div>
			<h4>为保障账户资金安全，请获取短信验证码进行支付</h4>
			<div class="groupBox" style="border:none;">
				<div class="group">
					<label>手机号：</label>
					<span style="display:block;text-align:left;line-height:42px;">${sessionScope.currentQcbOperator.mobile}</span>
					<input type="hidden" class="oldphone" value="${sessionScope.currentQcbOperator.mobile}"
					readonly="readonly" />
					<div class="clear"></div>
				</div>
				<div class="group">
					<label>验证码：</label>
					<input type="text" class="smsCode" name="code" />
					<a class="getCode color-orange">获取验证码</a>
					<div class="clear"></div>
					<p class="tip color-red" style="padding-left:130px;">验证码不能为空</p>
				</div>
				<a class="sureBtn distributionBtn" style="margin-left:125px;margin-top:15px;">确认</a>
			</div>
			<!-- 发送验证码 -->
			<form:form id="sendCodeToCheck" action="../rest/qcb/sms/sendCodeToCheck" method="post">
			</form:form>
			<!-- 确认薪资发放 -->
			<form:form id="sureSend" action="../rest/qcb/companyPayroll/submit" method="post">
			</form:form>
		</div>
	</div>
	<div class="iframe submitGrant">
		<div class="iframeInner">
			<a class="closeIframe"></a>
			<p>工资发放中。。。</p>
		</div>			
	</div>
	<a id="wechatBtn" href="" target="_blank" rel="noopener noreferrer"></a>
	<jsp:include page="footer.jsp"/>
	<script src="./js/query.js"></script>
	<script src="./js/paging.js"></script>
	<script type="text/javascript" src="js/jquery.uploadfile.min.js" ></script>
	<script type="text/javascript" src="laydate-v1.1/laydate/laydate.js" ></script>
	<script src="./js/extendWages.js?v=<%=Math.random()%>"></script>
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
			/* $(".module-1").show(); */
			$(".guideDBox").hide();
			$(".loadingOver").show();
        	$(".loadingDiv").hide();
		}
	</script>
</body>
</html>
