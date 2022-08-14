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
		<link rel="stylesheet" href="css/datepicker3.css" >
		<link rel="stylesheet" href="css/uploadfile.css">
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00900093" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location"><a href="fundListAuditingCheckList.jsp">活期理财信息审核</a><span>></span><a class="current" id="titlename"></a></div>
				<form:form id="formsubmit" role="form" action="#" method="post">
				<div class="title-contain">
						<input class="uuid" type="hidden" name="uuid"/>
						<div class="contentDiv firstContent">
						<div class="content-item">
							<div class="content-item-info">
								<div class="form-group noBorder">
                                    <label>活期理财简称:</label>
                                    <div class="content-items">
                                        <span id="gps"></span>
                                        <span class="editSpan" id="gp">
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                <div class="form-group noBorder">
                                    <label>活期理财简称:</label>
                                    <div class="content-items">
                                        <span id="shortnames"></span>
                                        <span class="editSpan" id="shortname">
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                <div class="form-group noBorder">
                                    <label>活期理财全称:</label>
                                    <div class="content-items">
                                        <span id="names"></span>
                                        <span class="editSpan" id="name">
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                <div class="form-group noBorder">
                                    <label>活期理财编号:</label>
                                    <div class="content-items">
                                        <span id="scodes"></span>
                                        <span class="editSpan" id="ascode">
                                    </div>
                                    <div class="clear"></div>
                                </div>
                                <div class="form-group noBorder">
                                    <label>活期理财类型:</label>
                                    <div class="content-items">
                                        <span id="types"></span>
                                        <span class="editSpan" id="type">
                                    </div>
                                    <div class="clear"></div>
                                </div>
							</div>
						</div>
					</div>
				</div>
				<div class="main-contain">
					<div class="contentDiv">
						<p class="title">基本信息</p>
						<div class="content-item">
							<!-- 信息 -->
							<div class="content-item-info">
                                <div class="form-group col-md-12">
									<label>成立日期：</label>
									<div class="content-items">
                                        <span id="setuptimes"></span>
                                        <span class="editSpan" id="setuptime"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>业务等级：</label>
									<div class="content-items">
                                        <span id="performanceLevels"></span>
                                        <span class="editSpan" id="performanceLevel"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>分级状态：</label>
									<div class="content-items">
                                        <span id="flagStructureds"></span>
                                        <span class="editSpan" id="flagStructured"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>分级类别：</label>
									<div class="content-items">
                                        <span id="structuredTypes"></span>
                                        <span class="editSpan" id="structuredType"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>分级描述：</label>
									<div class="content-items">
                                        <span id="structuredRemarks"></span>
                                        <span class="editSpan" id="structuredRemark"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>投资风格：</label>
									<div class="content-items">
                                        <span id="styles"></span>
                                        <span class="editSpan" id="style"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>风险等级：</label>
									<div class="content-items">
                                        <span id="riskLevels"></span>
                                        <span class="editSpan" id="riskLevel"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>信用等级：</label>
									<div class="content-items">
                                        <span id="creditLevels"></span>
                                        <span class="editSpan" id="creditLevel"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>总募集规模：</label>
									<div class="content-items">
                                        <span id="planingScales"></span>
                                        <span class="editSpan" id="planingScale"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>募集起始日：</label>
									<div class="content-items">
                                        <span id="collectStarttimes"></span>
                                        <span class="editSpan" id="collectStarttime"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>募集截止日：</label>
									<div class="content-items">
                                        <span id="collectEndtimes"></span>
                                        <span class="editSpan" id="collectEndtime"></span>
									</div>
									<div class="clear"></div>
								</div>
                                <div class="form-group col-md-12">
									<label>日常申购起始日：</label>
									<div class="content-items">
                                        <span id="purchaseStarttimes"></span>
                                        <span class="editSpan" id="purchaseStarttime"></span>
									</div>
									<div class="clear"></div>
								</div>
                                <div class="form-group col-md-12">
									<label>日常申购截止日：</label>
									<div class="content-items">
                                        <span id="purchaseEndtimes"></span>
                                        <span class="editSpan" id="purchaseEndtime"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
							</div>
						</div>
					</div>
					<div class="contentDiv">
						<p class="title">详细信息</p>
						<div class="content-item">
							<!-- 信息 -->
							<div class="content-item-info">
                                <div class="form-group col-md-12">
									<label>灵活期限：</label>
									<div class="content-items">
                                        <span id="goals"></span>
                                        <span class="editSpan" id="goal"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>投资策略：</label>
									<div class="content-items">
                                        <span id="investStaregys"></span>
                                        <span class="editSpan" id="investStaregy"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>投资标准：</label>
									<div class="content-items">
                                        <span id="investStandards"></span>
                                        <span class="editSpan" id="investStandard"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>投资理念：</label>
									<div class="content-items">
										<span id="investIdeas"></span>
                                        <span class="editSpan" id="investIdea"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>投资范围：</label>
									<div class="content-items">
                                        <span id="investScopes"></span>
                                        <span class="editSpan" id="investScope"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>风险收益特征：</label>
									<div class="content-items">
                                        <span id="revenueFeatures"></span>
                                        <span class="editSpan" id="revenueFeature"></span>
                                    </div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>具体目标：</label>
									<div class="content-items">
                                        <span id="riskManagements"></span>
                                        <span class="editSpan" id="riskManagement"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
							</div>
						</div>
					</div>
				</div>
                <div class="foot-contain">
					<div class="content-item">
						<div class="contentDiv">
							<div class="operateBar">操作类型：<span id="operateType"></span></div>
							<div class="content-item">
								<div class="content-item-info">
									<div class="form-group col-md-12 noBorder">
										<label>提交人：</label>
										<div class="content-items"><span id="submitName"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="clear"></div>
									<div class="form-group col-md-12 noBorder">
										<label>提交时间：</label>
										<div class="content-items"><span id="submitTime"></span>
										</div>
										<div class="clear"></div>
									</div>
									<div class="form-group col-md-12 noBorder" id="reasonItem">
										<label>审核原因：</label>
										<div class="content-items"><span id="reason"></span>
										</div>
										<div class="clear"></div>
									</div>
								</div>
								<div class="clear"></div>
							</div>
							<div class="operateCheck">
								审核原因：<input type="text" id="checkReason"/>
								<div>
									<button class="btn btn-primary" type="button" onclick="checked(this)">通过</button>
									<button class="btn btn-primary" type="button" onclick="unpassed(this)">不通过</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				</form:form>
			</div>
			<div class="clear"></div>
		</div>

		<script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="./js/laydate/laydate.js" ></script>
		<script type="text/javascript" src="./js/url.min.js"></script>
        <script type="text/javascript" src="./js/flagSubmit.js"></script>
		<script type="text/javascript" src="./js/jquery.uploadfile.min.js"></script>
		<script type="text/javascript" src="./js/tinymce/dropzone/lib/dropzone.js"></script>
		<script type="text/javascript" src="./js/tinymce/tinymce.min.js"></script>
		<script type="text/javascript" src="./js/tinymce/tinymce_zhcn.js"></script>
		<script type="text/javascript" src="./js/tinymce/tinymce_tool.js"></script>
		<script type="text/javascript" src="./js/flagSubmit.js"></script>
		<script type="text/javascript" src="./js/changeMoneyToChinese.js"></script>
        <script type="text/javascript" src="./js/fundListAuditingMsgCheck.js"></script>
	</body>
</html>
