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
		<input id="scode" type="hidden" value="00900094" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location"><a href="fundListAuditing.jsp">活期理财信息申请</a><span>></span><a class="current" id="titlename"></a></div>
				<form:form id="formsubmit" role="form" action="#" method="post">
				<div class="title-contain">
						<input class="uuid" type="hidden" name="uuid"/>
						<div class="contentDiv firstContent">
						<div class="content-item">
							<div class="content-item-info">
								<div class="logo">
									<img id="custodiansLogo"/>
								</div>
								<div class="form-group noBorder">
									<label>资金托管方:</label>
									<div class="content-items"><span id="gps"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group noBorder">
									<label>活期理财简称:</label>
									<div class="content-items"><span id="shortnames"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group noBorder">
									<label>活期理财全称:</label>
									<div class="content-items"><span id="names"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group noBorder">
									<label>活期理财编号:</label>
									<div class="content-items"><span id="scodes"></span>
									</div>
									<div class="clear"></div>
								</div>
                                <div class="form-group noBorder">
                                    <label>活期理财类型:</label>
                                    <div class="content-items"><span id="types"></span>
                                    </div>
                                    <div class="clear"></div>
                                </div>
							</div>
							<div class="content-item-edit">
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 资金托管方：</label>
									<div class="content-items">
                                        <select class="form-control" id="gp" name="gp"/></select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 活期理财简称：</label>
									<div class="content-items">
                                        <input class="form-control" id="shortname" name="shortname"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 活期理财全称：</label>
									<div class="content-items">
										<input class="form-control" id="name" name="name"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>活期理财编号：</label>
									<div class="content-items">
										<input class="form-control" id="ascode" name="scode"/>
									</div>
									<div class="clear"></div>
								</div>
                                <div class="form-group col-md-12">
									<label>活期理财类型：</label>
									<div class="content-items">
                                        <select class="form-control" id="type" name="type">
											<option value="currency">货币型</option>
											<option value="bond">债券型</option>
											<option value="stock">股票型</option>
											<option value="mix">混合型</option>
										</select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
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
									<div class="content-items"><span id="setuptimes"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>业务等级：</label>
									<div class="content-items"><span id="performanceLevels"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>分级状态：</label>
									<div class="content-items"><span id="flagStructureds"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>分级类别：</label>
									<div class="content-items"><span id="structuredTypes"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>分级描述：</label>
									<div class="content-items"><span id="structuredRemarks"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>投资风格：</label>
									<div class="content-items"><span id="styles"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>风险等级：</label>
									<div class="content-items"><span id="riskLevels"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>信用等级：</label>
									<div class="content-items"><span id="creditLevels"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>总募集规模：</label>
									<div class="content-items"><span id="planingScales"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>募集起始日：</label>
									<div class="content-items"><span id="collectStarttimes"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>募集截止日：</label>
									<div class="content-items"><span id="collectEndtimes"></span>
									</div>
									<div class="clear"></div>
								</div>
                                <div class="form-group col-md-12">
									<label>日常申购起始日：</label>
									<div class="content-items"><span id="purchaseStarttimes"></span>
									</div>
									<div class="clear"></div>
								</div>
                                <div class="form-group col-md-12">
									<label>日常申购截止日：</label>
									<div class="content-items"><span id="purchaseEndtimes"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
							</div>
							<!-- 编辑 -->
							<div class="content-item-edit">
								<div class="form-group col-md-6 noBorder">
									<label><b class="red">*</b> 成立日期：</label>
									<div class="content-items">
										<input class="form-control datepicker laydate-icon" id="setuptime" name="setuptime"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6 noBorder">
									<label>业务等级：</label>
									<div class="content-items">
                                        <select class="form-control" name="performanceLevel" id="performanceLevel">
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                            <option value="4">4</option>
                                            <option value="5">5</option>
                                        </select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6 noBorder">
									<label>分级状态：</label>
									<div class="content-items">
                                        <select class="form-control" name="flagStructured" id="flagStructured">
                                            <option value=false>普通型</option>
                                            <option value=true>分级型</option>
                                        </select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6 noBorder">
									<label><b class="red">*</b> 分级类别：</label>
									<div class="content-items">
                                        <select class="form-control" name="structuredType" id="structuredType">
                                            <option value="priority">优先型</option>
                                            <option value="lag">劣后型</option>
                                        </select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6 noBorder">
									<label><b class="red">*</b> 分级描述：</label>
									<div class="content-items">
										<input class="form-control" name="structuredRemark" id="structuredRemark"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label><b class="red">*</b> 投资风格：</label>
									<div class="content-items">
                                        <select class="form-control" name="style" id="style">
                                            <option value="profit">收益型</option>
                                            <option value="balance">平衡型</option>
                                        </select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label>风险等级：</label>
									<div class="content-items">
                                        <select class="form-control" name="riskLevel" id="riskLevel">
                                            <option value="R1">R1（谨慎型）</option>
                                            <option value="R2">R2（稳健型）</option>
                                            <option value="R3">R3（平衡型）</option>
                                            <option value="R4">R4（进取型）</option>
                                            <option value="R5">R5（激进型）</option>
                                        </select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label><b class="red">*</b> 信用等级：</label>
									<div class="content-items">
                                        <select class="form-control" name="creditLevel" id="creditLevel">
                                            <option value="high">高</option>
                                            <option value="middle">中</option>
                                            <option value="low">低</option>
                                        </select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label><b class="red">*</b> 总募集规模：</label>
									<div class="content-items">
										<input class="form-control defaultkey1" id="planingScale" name="planingScale" value="0.00"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6 noBorder">
									<label><b class="red">*</b> 募集起始日：</label>
									<div class="content-items">
										<input class="form-control datepicker laydate-icon" id="collectStarttime" name="collectStarttime"/>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-6">
									<label><b class="red">*</b> 募集截止日：</label>
									<div class="content-items">
										<input class="form-control datepicker laydate-icon" id="collectEndtime" name="collectEndtime"/>
									</div>
									<div class="clear"></div>
								</div>
                                <div class="form-group col-md-6">
									<label><b class="red">*</b> 日常申购起始日：</label>
									<div class="content-items">
										<input class="form-control datepicker laydate-icon" id="purchaseStarttime" name="purchaseStarttime"/>
									</div>
									<div class="clear"></div>
								</div>
                                <div class="form-group col-md-6">
									<label><b class="red">*</b> 日常申购截止日：</label>
									<div class="content-items">
										<input class="form-control datepicker laydate-icon" id="purchaseEndtime" name="purchaseEndtime"/>
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
									<div class="content-items"><span id="goals"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>投资策略：</label>
									<div class="content-items"><span id="investStaregys"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>投资标准：</label>
									<div class="content-items"><span id="investStandards"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>投资理念：</label>
									<div class="content-items">
										<span id="investIdeas"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>投资范围：</label>
									<div class="content-items"><span id="investScopes"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>风险收益特征：</label>
									<div class="content-items"><span id="revenueFeatures"></span>
                                    </div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>具体目标：</label>
									<div class="content-items"><span id="riskManagements"></span>
									</div>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
							</div>
							<!-- 编辑 -->
							<div class="content-item-edit">
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 灵活期限：</label>
									<div class="col-md-9">
										<textarea id="goal" name="goal"></textarea>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 投资策略：</label>
									<div class="col-md-9">
										<textarea id="investStaregy" name="investStaregy"></textarea>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 投资标准：</label>
									<div class="col-md-9">
										<textarea id="investStandard" name="investStandard"></textarea>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 投资理念：</label>
									<div class="col-md-9">
										<textarea id="investIdea" name="investIdea"></textarea>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 投资范围：</label>
									<div class="col-md-9">
										<textarea id="investScope" name="investScope"></textarea>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 风险收益特征：</label>
									<div class="col-md-9">
										<textarea id="revenueFeature" name="revenueFeature"></textarea>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label><b class="red">*</b> 具体目标：</label>
									<div class="col-md-9">
										<textarea id="riskManagement" name="riskManagement"></textarea>
									</div>
									<div class="clear"></div>
								</div>
								<div class="clear"></div>
							</div>
						</div>
					</div>
					<div class="text-center">
						<a class="btn sureBtn edit">修改</a>
					</div>
					<div class="btnGroup text-center save">
						<button class="btn sureBtn submitBtn" type="submit">提交</button>
						<button class="btn cancleBtn" type="button" id="backBtn">取消</button>
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
        <script type="text/javascript" src="./js/fundListAuditingMsg.js"></script>
	</body>
</html>
