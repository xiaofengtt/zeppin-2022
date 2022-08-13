<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>添加基金</title>
		<link rel="stylesheet" href="css/bootstrap.css" />
		<link rel="stylesheet" href="css/style.css" />
		<script type="text/javascript" charset="utf-8" src="js/utf8-jsp/ueditor.config.js"></script>
		<script type="text/javascript" charset="utf-8" src="js/utf8-jsp/ueditor.all.min.js"> </script>
		<script type="text/javascript" charset="utf-8" src="js/utf8-jsp/lang/zh-cn/zh-cn.js"></script>
	</head>
	<body>
		<jsp:include page="header.jsp"/>
		<jsp:include page="navigation.jsp"/>
		<input id="scode" type="hidden" value="00000002" />  
		<div class="contain">
			<jsp:include page="contentLeft.jsp"/>
			<div class="contain-right">
		<div class="location">
			<div class="locationLeft"><a href="fundList.jsp">基金信息</a><span>></span><a class="current">添加基金</a></div>
			<div class="clear"></div>
		</div>
		<div class="layerOpen">
			<div class="contents">
				<ul class="order text-center">
					<li class="on"><img src="img/on.png">基金名称</li>
					<li><img src="img/Oval.png">基本信息</li>
					<li><img src="img/Oval.png">详细信息</li>
					<li><img src="img/Oval.png">费率信息</li>
					<div class="clear"></div>
				</ul>
				<div id="formsubmit">
				<div class="layerContent">
					<div class="firststep step">
						<form:form id="formsubmit1" role="form" action="#" method="post">
							<input type="hidden" name="step" value="1" />
							<input type="hidden" class="uuid" name="uuid" value="" />
							<div class="form-group col-xs-6">
								<label class="col-xs-12">基金简称:</label>
								<div class="col-md-9">
									<input class="form-control" id="shortname" name="shortname"/>
								</div>
								<span class="tips">基金简称不能为空</span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">基金全称:</label>
								<div class="col-md-9">
									<input class="form-control" id="name" name="name"/>
								</div>
								<span class="tips">基金全称不能为空</span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">基金编号:</label>
								<div class="col-md-9">
									<input class="form-control" id="iscode" name="scode"/>
								</div>
								<span class="tips">基金编号不能为空</span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">基金类型:</label>
								<div class="col-md-9">
									<select class="form-control" id="type" name="type">
										<option value="currency">货币型</option>
										<option value="bond">债券型</option>
										<option value="stock">股票型</option>
										<option value="mix">混合型</option>
									</select>
								</div>
								<span class="tips">请选择基金类型</span>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<button class="prev" type="button" onclick="window.location.href=history.go(-1);">取消</button>
							<button class="sureBtn next" type="submit">下一步</button>
						</form:form>
					</div>
					<div class="secondstep step">
						<form:form id="formsubmit2" role="form" action="#" method="post">
							<input type="hidden" name="step" value="2" />
							<input type="hidden" class="uuid" name="uuid" value="" />
							<div class="form-group col-xs-6">
								<label class="col-xs-12">成立日期:</label>
								<div class="col-md-9">
									<input class="form-control datepicker laydate-icon" id="setuptime" name="setuptime"/>
								</div>
								<span class="tips">成立日期不能为空</span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">业务等级:</label>
								<div class="col-md-9">
									<select class="form-control" name="performanceLevel">
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
									</select>
								</div>
								<span class="tips"></span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">管理方:</label>
								<div class="col-md-9">
									<input class="form-control" name="gp"/>
								</div>
								<span class="tips"></span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">资金托管人:</label>
								<div class="col-md-9">
									<input class="form-control" name="custodian" />
								</div>
								<span class="tips"></span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">分级状态:</label>
								<div class="col-md-9">
									<select class="form-control" name="flagStructured">
										<option value="false">普通型</option>
										<option value="true">分级型</option>
									</select>
								</div>
								<span class="tips"></span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">分级类别:</label>
								<div class="col-md-9">
									<select class="form-control" name="structuredType">
										<option value="priority">优先型</option>
										<option value="lag">劣后型</option>
									</select>
								</div>
								<span class="tips"></span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">分级描述:</label>
								<div class="col-md-9">
									<input class="form-control" name="structuredRemark"/>
								</div>
								<span class="tips"></span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">投资风格:</label>
								<div class="col-md-9">
									<select class="form-control" name="style">
										<option value="profit">收益型</option>
										<option value="balance">平衡型</option>
									</select>
								</div>
								<span class="tips"></span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">风险等级:</label>
								<div class="col-md-9">
									<select class="form-control" name="riskLevel">
										<option value="R1">R1（谨慎型）</option>
										<option value="R2">R2（稳健型）</option>
										<option value="R3">R3（平衡型）</option>
										<option value="R4">R4（进取型）</option>
										<option value="R5">R5（激进型）</option>
									</select>
								</div>
								<span class="tips"></span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">信用等级:</label>
								<div class="col-md-9">
									<select class="form-control" name="creditLevel">
										<option value="high">高</option>
										<option value="middle">中</option>
										<option value="low">低</option>
									</select>
								</div>
								<span class="tips"></span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">总募集规模:</label>
								<div class="col-md-9">
									<input class="form-control defaultkey1" id="planingScale" name="planingScale" value="0.00"/>
								</div>
								<span class="tips">总募集规模不能为空</span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">募集起始日:</label>
								<div class="col-md-9">
									<input class="form-control datepicker laydate-icon" id="collectStarttime" name="collectStarttime"/>
								</div>
								<span class="tips">募集起始日不能为空</span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">募集截止日:</label>
								<div class="col-md-9">
									<input class="form-control datepicker laydate-icon" id="collectEndtime" name="collectEndtime"/>
								</div>
								<span class="tips">募集截止日不能为空</span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">日常申购起始日:</label>
								<div class="col-md-9">
									<input class="form-control datepicker laydate-icon" id="purchaseStarttime" name="purchaseStarttime"/>
								</div>
								<span class="tips">日常申购起始日不能为空</span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">日常申购截止日:</label>
								<div class="col-md-9">
									<input class="form-control datepicker laydate-icon" id="purchaseEndtime" name="purchaseEndtime"/>
								</div>
								<span class="tips">日常申购截止日不能为空</span>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
								<button type="button" class="prev2">上一步</button>
								<button class="sureBtn next" type="submit">下一步</button>
						</form:form>
					</div>
					<div class="thirdstep step">
						<form:form id="formsubmit3" role="form" action="#" method="post">
							<input type="hidden" name="step" value="3" />
							<input type="hidden" class="uuid" name="uuid" value="" />
							<div class="form-group col-xs-12">
								<label class="col-md-9">投资目标:</label>
								<div class="col-md-9">
									<textarea id="goal" name="goal"></textarea>
								</div>
								<span class="tips"></span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-12">
								<label class="col-md-9">投资策略:</label>
								<div class="col-md-9">
									<textarea id="investStaregy" name="investStaregy"></textarea>
								</div>
								<span class="tips"></span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-12">
								<label class="col-md-9">投资标准:</label>
								<div class="col-md-9">
									<textarea id="investStandard" name="investStandard"></textarea>
								</div>
								<span class="tips"></span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-12">
								<label class="col-md-9">投资理念:</label>
								<div class="col-md-9">
									<textarea id="investIdea" name="investIdea"></textarea>
								</div>
								<span class="tips"></span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-12">
								<label class="col-md-9">投资范围:</label>
								<div class="col-md-9">
									<textarea id="investScope" name="investScope"></textarea>
								</div>
								<span class="tips"></span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-12">
								<label class="col-md-9">风险收益特征:</label>
								<div class="col-md-9">
									<textarea id="revenueFeature" name="revenueFeature"></textarea>
								</div>
								<span class="tips"></span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-12">
								<label class="col-md-9">具体目标：</label>
								<div class="col-md-9">
									<textarea id="riskManagement" name="riskManagement"></textarea>
								</div>
								<span class="tips"></span>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<button type="button" class="prev2">上一步</button>
							<button class="sureBtn next" type="submit">下一步</button>
						</form:form>
					</div>
					<div class="forthstep step">
						<form:form id="formsubmit4" role="form" action="#" method="post">
							<input type="hidden" name="step" value="4" />
							<input type="hidden" class="uuid" name="uuid" value="" />
							<div class="form-group col-xs-6">
								<label class="col-xs-12">费率类型:</label>
								<div class="col-md-9">
									<select class="form-control" id="fundRateType">
										<option value="apply">申购费率</option>
										<option value="buy">认购费率</option>
										<option value="redeem">赎回费率</option>
									</select>
								</div>
								<span class="tips"></span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">费率:</label>
								<div class="col-md-9">
									<input class="form-control defaultkey1" id="fundRate" value="0.00"/>
								</div>
								<span class="tips"></span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">资金下限:</label>
								<div class="col-md-9">
									<input class="form-control defaultkey1" id="fundRateLowlimit" value="0.00"/>
								</div>
								<span class="tips"></span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">优惠费率:</label>
								<div class="col-md-9">
									<input class="form-control defaultkey1"  id="fundOpenRate" value="0.00"/>
								</div>
								<span class="tips"></span>
								<div class="clear"></div>
							</div>
							<div class="form-group col-xs-6">
								<label class="col-xs-12">资金上限:</label>
								<div class="col-md-9">
									<input class="form-control defaultkey1" id="fundRateUpperlimit" value="0.00"/>
								</div>
								<span class="tips"></span>
								<div class="clear"></div>
							</div>
							<div class="clear"></div>
							<button class="enter addNew" type="button" onclick="addFundRate()" >+&ensp;添加</button>
	
							<table class="table table-hover text-center cssa2f666e566995" id="fundRateTable">
								<tr>
									<th class="typeTh" width="21.1607%">费率类型</th>
									<th width="27.2321%">条件</th>
									<th width="16.6071%">费率</th>
									<th width="20.5357%">优惠费率</th>
									<th width="13.8393%">操作</th>
								</tr>
							</table>
							<div id="fundRateDiv"></div>
							<div class="clear"></div>
							<button type="button" class="prev2">上一步</button>
							<button class="sureBtn next" type="submit">保存</button>
						</form:form>
					</div>
				</div>
				</div>
			</div>
		</div>
		</div>
			<div class="clear"></div>
		</div>
		<script type="text/javascript" src="js/jquery-1.11.1.js" ></script>
		<script type="text/javascript" src="js/jquery.colorbox.js"></script>
		<script src="js/bootstrap-datepicker.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
		<script type="text/javascript" src="js/bootstrap.js" ></script>
		<script type="text/javascript" src="js/laydate/laydate.js" ></script>
		<script type="text/javascript" src="js/floatNumberFormat.js"></script>
		<script type="text/javascript" src="js/fundAdd.js" ></script>
	</body>
</html>
