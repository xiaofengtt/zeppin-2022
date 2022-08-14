<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>牛投帮-后台管理系统</title>
		<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" href="./css/bootstrap.css" />
		<link rel="stylesheet" href="./css/fundEdit.css" />
		<link rel="stylesheet" href="./css/style.css" />
		<%-- <script type="text/javascript" charset="utf-8" src="js/utf8-jsp/ueditor.config.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/utf8-jsp/ueditor.all.min.js"> </script>
		<script type="text/javascript" charset="utf-8" src="js/utf8-jsp/lang/zh-cn/zh-cn.js"></script> --%>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00900091" />
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
				<div class="location">
					<div class="locationLeft"><a href="fundList.jsp">活期理财信息管理</a><span>></span><a class="current">添加活期理财</a></div>
					<div class="clear"></div>
				</div>
				<form:form id="formsubmit" role="form" action="#" method="post">
					<%--title-contain--%>
					<div class="title-contain">
						<div class="content-item">
							<div class="logo">
								<img id="custodiansLogo"/>
							</div>
							<div class="content-item-edit" style="display: block;">
								<div class="form-group col-md-12">
									<label>资金托管方:</label>
									<div class="content-items">
										<select class="form-control" id="gp" name="gp">
											<option value="">请选择</option>
										</select>
									</div>
									<div class="clear"></div>
								</div>
								<div class="form-group col-md-12">
									<label>活期理财简称:</label>
									<div class="content-items">
										<input class="form-control" id="shortname" name="shortname"/>
									</div>
									<div class="clear"></div>
								</div>

								<div class="form-group col-md-12">
									<label>活期理财全称:</label>
									<div class="content-items">
										<input class="form-control" id="name" name="name"/>
									</div>
									<div class="clear"></div>
								</div>

								<div class="form-group col-md-12">
									<label>活期理财编号:</label>
									<div class="content-items">
										<input class="form-control" id="iscode" name="scode"/>
									</div>
									<div class="clear"></div>
								</div>

								<div class="form-group col-md-12">
									<label>活期理财类型:</label>
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
					<%--title-contain--%>

					<%-----------------------%>

					<%--main-contain--%>
					<div class="main-contain">
						<div class="contentDiv">
							<p class="title">基本信息</p>
							<div class="content-item">
								<div class="content-item-edit" style="display: block;">
									<div class="form-group col-md-6">
										<label class="label_left"><b class="red">*</b> 成立日期：</label>
										<div class="content-items">
											<input class="form-control datepicker laydate-icon" id="setuptime" name="setuptime" autocomplete="off"/>
										</div>
										<div class="clear"></div>
									</div>

									<div class="form-group col-md-6">
										<label class="label_left"><b class="red">*</b> 业务等级：</label>
										<div class="content-items">
											<select class="form-control" name="performanceLevel">
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
												<option value="4">4</option>
												<option value="5">5</option>
											</select>
										</div>
										<div class="clear"></div>
									</div>

									<div class="form-group col-md-6">
										<label class="label_left"><b class="red">*</b> 分级状态：</label>
										<div class="content-items">
											<select class="form-control" name="flagStructured">
												<option value="false">普通型</option>
												<option value="true">分级型</option>
											</select>
										</div>
										<div class="clear"></div>
									</div>

									<div class="form-group col-md-6">
										<label class="label_left"><b class="red">*</b> 分级类别：</label>
										<div class="content-items">
											<select class="form-control" name="structuredType">
												<option value="priority">优先型</option>
												<option value="lag">劣后型</option>
											</select>
										</div>
										<div class="clear"></div>
									</div>

									<div class="form-group col-md-6">
										<label class="label_left"><b class="red">*</b> 分级描述：</label>
										<div class="content-items">
											<input class="form-control" name="structuredRemark"/>
										</div>
										<div class="clear"></div>
									</div>

									<div class="form-group col-md-6">
										<label class="label_left"><b class="red">*</b> 投资风格：</label>
										<div class="content-items">
											<select class="form-control" name="style">
												<option value="profit">收益型</option>
												<option value="balance">平衡型</option>
											</select>
										</div>
										<div class="clear"></div>
									</div>

									<div class="form-group col-md-6">
										<label class="label_left"><b class="red">*</b> 风险等级：</label>
										<div class="content-items">
											<select class="form-control" name="riskLevel">
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
										<label class="label_left"><b class="red">*</b> 信用等级：</label>
										<div class="content-items">
											<select class="form-control" name="creditLevel">
												<option value="high">高</option>
												<option value="middle">中</option>
												<option value="low">低</option>
											</select>
										</div>
										<div class="clear"></div>
									</div>

									<div class="form-group col-md-6">
										<label class="label_left"><b class="red">*</b> 总募集规模：</label>
										<div class="content-items">
											<input class="form-control defaultkey1" id="planingScale" name="planingScale" value="0.00"/>
										</div>
										<div class="clear"></div>
									</div>


									<div class="form-group col-md-6">
										<label class="label_left"><b class="red">*</b> 募集起始日：</label>
										<div class="content-items">
											<input class="form-control datepicker laydate-icon" id="collectStarttime" name="collectStarttime" autocomplete="off"/>
										</div>
										<div class="clear"></div>
									</div>

									<div class="form-group col-md-6">
										<label class="label_left"><b class="red">*</b> 募集截止日：</label>
										<div class="content-items">
											<input class="form-control datepicker laydate-icon" id="collectEndtime" name="collectEndtime" autocomplete="off"/>
										</div>
										<div class="clear"></div>
									</div>

									<div class="form-group col-md-6">
										<label class="label_left"><b class="red">*</b> 日常申购起始日：</label>
										<div class="content-items">
											<input class="form-control datepicker laydate-icon" id="purchaseStarttime" name="purchaseStarttime" autocomplete="off"/>
										</div>
										<div class="clear"></div>
									</div>

									<div class="form-group col-md-6">
										<label class="label_left"><b class="red">*</b> 日常申购截止日：</label>
										<div class="content-items">
											<input class="form-control datepicker laydate-icon" id="purchaseEndtime" name="purchaseEndtime" autocomplete="off"/>
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
								<div class="content-item-edit" style="display: block;">
									<div class="form-group col-md-12 ">
										<label><b class="red">*</b> 灵活期限：</label>
										<div class="col-md-9">
											<textarea id="goal" name="goal"></textarea>
										</div>
										<div class="clear"></div>
									</div>

									<div class="form-group col-md-12 ">
										<label><b class="red">*</b> 投资策略：</label>
										<div class="col-md-9">
											<textarea id="investStaregy" name="investStaregy"></textarea>
										</div>
										<div class="clear"></div>
									</div>

									<div class="form-group col-md-12 ">
										<label><b class="red">*</b> 投资标准：</label>
										<div class="col-md-9">
											<textarea id="investStandard" name="investStandard"></textarea>
										</div>
										<div class="clear"></div>
									</div>

									<div class="form-group col-md-12 ">
										<label><b class="red">*</b> 投资理念：</label>
										<div class="col-md-9">
											<textarea id="investIdea" name="investIdea"></textarea>
										</div>
										<div class="clear"></div>
									</div>

									<div class="form-group col-md-12 ">
										<label><b class="red">*</b> 投资范围：</label>
										<div class="col-md-9">
											<textarea id="investScope" name="investScope"></textarea>
										</div>
										<div class="clear"></div>
									</div>

									<div class="form-group col-md-12 ">
										<label><b class="red">*</b> 风险收益特征：</label>
										<div class="col-md-9">
											<textarea id="revenueFeature" name="revenueFeature"></textarea>
										</div>
										<div class="clear"></div>
									</div>

									<div class="form-group col-md-12 ">
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


						<div class="btnGroup text-center">
							<button class="btn sureBtn" type="submit">提交</button>
							<button class="btn cancleBtn" type="button" onclick="window.location.href = document.referrer">取消</button>
						</div>
					</div>
					<%--main-contain--%>
				</form:form>

			</div>
		</div>
		<script type="text/javascript" src="./js/jquery-1.11.1.js" ></script>
		<script type="text/javascript" src="./js/jquery.colorbox.js"></script>
		<script src="./js/bootstrap-datepicker.js" type="text/javascript"></script>
		<script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="./js/bootstrap.js" ></script>
		<script type="text/javascript" src="./js/laydate/laydate.js" ></script>
		<script type="text/javascript" src="./js/tinymce/dropzone/lib/dropzone.js"></script>
		<script type="text/javascript" src="./js/tinymce/tinymce.min.js"></script>
		<script type="text/javascript" src="./js/tinymce/tinymce_zhcn.js"></script>
		<script type="text/javascript" src="./js/tinymce/tinymce_tool.js"></script>
		<script type="text/javascript" src="./js/floatNumberFormat.js"></script>
		<script type="text/javascript" src="./js/fundAdd.js" ></script>
		<script type="text/javascript" src="./js/flagSubmit.js"></script>
	</body>
</html>
