<%@page import="org.apache.struts2.components.Include"%>
<%@page import="cn.zeppin.action.sso.UserSession"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>个人信息能力列表</title>

<link rel="stylesheet" href="../css/bootstrap.css">
<!--[if lt IE 9]>
  <script src="../js/html5shiv.js"></script>
  <script src="../js/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" href="../css/app.css">
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css">
<link href="../css/metro/blue/jtable.css" rel="stylesheet"
	type="text/css">
<link href="../css/colorbox.css" rel="stylesheet" type="text/css">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery-ui.js"></script>
<script src="../js/jquery.jtable.js"></script>
<script src="../js/jquery.jtable.zh-CN.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/bootstrap-paginator.min.js"></script>
<script src="../js/bootstrap.js"></script>
<script src="../js/url.min.js"></script>
<script src="../js/app.js"></script>
<script type="text/javascript" src="../js/Chart.js"></script>
<script src="../js/excanvas.js" type="text/javascript"></script>

<script src="../js/jsrender.min.js"></script>
<script src="../js/underscore-min.js"></script>
<link rel="stylesheet" href="../css/ui-dialog.css">
<script src="../js/dialog-min.js"></script>

</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
	
	<div id="container" class="container-fluid">
		<jsp:include page="left.jsp"></jsp:include>
		<div class="main">

			<div class="cui-menu2">
				<a class="btn-search btn btn-primary" href="javascript:void(0);" id="gotest"
					onclick="gotestClick();">+ 去测评</a>

			</div>

			<div id="list-content" class="list-content clearfix"></div>

			<script id="ttRecordAduTpl" type="text/x-jsrender">
				
				<div id="hsd_{{:id}}" class="list-item">
				
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td width="100px"><span class="text-primary">ID号：</span><span class="text-danger">{{:id}}</span></td>
							<td width="200px"><span class="text-primary">年份：</span><span class="text-danger">{{:year}}</span></td>
							<td width="auto"><span class="text-primary">时间：</span><span class="text-danger">{{:createtime}}</span></td>
							<td widht="auto" style="text-align:right;padding-right:50px;" ><a target="_blank" href="../teacher/HsdTestInfo.jsp?id={{:id}}">详细</a></td>
						</tr>
					</tbody>
				</table>
				
				<table width="100%" cellspacing="0" cellpadding="0">
					<tbody>
						<tr>
							<td>
								<table width="200" border="1" cellspacing="0" cellpadding="0"
									style="margin-left: 25px;">
									<tbody>
										<tr style="background-color: #4f81bd;">
											<td style="width: 100px;"></td>
											<td style="width: 50px;">&nbsp;&nbsp;I</td>
											<td style="width: 50px;">&nbsp;&nbsp;II</td>
										</tr>
										<tr>
											<td style="background: #e4e4e4;">技术素养</td>
											<td id="nl1_{{:id}}"></td>
											<td id="nl6_{{:id}}"></td>
										</tr>
										<tr>
											<td style="background: #e4e4e4;">计划与准备</td>
											<td id="nl2_{{:id}}"></td>
											<td id="nl7_{{:id}}"></td>
										</tr>
										<tr>
											<td style="background: #e4e4e4;">组织与管理</td>
											<td id="nl3_{{:id}}"></td>
											<td id="nl8_{{:id}}"></td>
										</tr>
										<tr>
											<td style="background: #e4e4e4;">评估与诊断</td>
											<td id="nl4_{{:id}}"></td>
											<td id="nl9_{{:id}}"></td>
										</tr>
										<tr>
											<td style="background: #e4e4e4;">学习与发展</td>
											<td id="nl5_{{:id}}"></td>
											<td id="nl10_{{:id}}"></td>
										</tr>
									</tbody>
								</table> <br>
								<div style="margin-left: 25px;">
									注：I代表应用信息技术优化课堂<br> 教学，II代表应用信息技术转变学<br>
									习方式；红色区域代表不合格，<br> 黄色区域代表合格；绿色区域代<br> 表优秀。
								</div>
							</td>
							<td style="padding-bottom: 10px;"><canvas id="canvas1_{{:id}}"
									height="250" width="300" style="margin-left: -50px;"></canvas>
								<br> I，应用信息技术优化课堂教学</td>
							<td style="padding-bottom: 10px;"><canvas id="canvas2_{{:id}}"
									height="250" width="300" style="margin-left: -30px;"></canvas>
								<br> II，应用信息技术转变学习方式</td>
						</tr>
					</tbody>
				</table>

				</div>
				
			</script>

			<div id="pagination" style="float: right;" class="pull-right"></div>


		</div>


	</div>

	<script>
		function hsdTests(page) {

			$.getJSON('../teacher/hsdTest_getHsdTest.action?page=' + page,
			function(data) {

				if (data.status == 'OK') {

					var template = $.templates('#ttRecordAduTpl');
					var html = template.render(data.records);
					$('#list-content').html(html).removeClass('loading');

					$.each(data.records,function(i, v) {

						var f_data = [];
						var s_data = [];
						var id = v.id;

						var score = v.score
								.split(",");
						var jhyzb1 = parseFloat(score[1]); //1计划与准备
						var zzygl1 = parseFloat(score[2]); //1组织与管理
						var pgyzd1 = parseFloat(score[3]);//1评估与诊断
						var jssy1 = parseFloat(score[0]); //1技术素养
						var xxyfz1 = parseFloat(score[4]);//1学习与发展

						var jhyzb2 = parseFloat(score[6]); //2计划与准备
						var zzygl2 = parseFloat(score[7]); //2组织与管理
						var pgyzd2 = parseFloat(score[8]); //2评估与诊断
						var jssy2 = parseFloat(score[5]); //2技术素养
						var xxyfz2 = parseFloat(score[9]); //2学习与发展
						var str = [ jssy1,jhyzb1, zzygl1,pgyzd1, xxyfz1,jssy2, jhyzb2,zzygl2, pgyzd2,xxyfz2 ];
						f_data[0] = jhyzb1;
						f_data[1] = zzygl1;
						f_data[2] = pgyzd1;
						f_data[4] = jssy1;
						f_data[3] = xxyfz1;
						s_data[0] = jhyzb2;
						s_data[1] = zzygl2;
						s_data[2] = pgyzd2;
						s_data[4] = jssy2;
						s_data[3] = xxyfz2;

						for (var i = 1; i <= str.length; i++) {
							if (str[i - 1] <= 0.4) {
								document.getElementById("nl"+ i + "_"+ id).style.background = "#ff0000";
							} else if (str[i - 1] >= 0.8) {
								document.getElementById("nl"+ i+ "_"+ id).style.background = "#00ff00";
							} else {
								document.getElementById("nl"+ i+ "_"+ id).style.background = "#ffff00";
							}
						}

						//雷达图
						var radarChartData_1 = {
							labels : [
									"I计划与准备",
									"I组织与管理",
									"I评估与诊断",
									"I学习与发展",
									"I技术素养" ],
							datasets : [ {
								label : "",
								fillColor : "rgba(0,102,0,0.2)",
								strokeColor : "#006600",
								pointColor : "#006600",
								pointStrokeColor : "#006600",
								pointHighlightFill : "#006600",
								pointHighlightStroke : "#006600",
								data : f_data

							} ]
						};

						var radarChartData_2 = {
							labels : [
									"II计划与准备",
									"II组织与管理",
									"II评估与诊断",
									"II学习与发展",
									"II技术素养" ],
							datasets : [ {
								label : "",
								fillColor : "rgba(0,51,225,0.2)",
								strokeColor : "#0033FF",
								pointColor : "#0033FF",
								pointStrokeColor : "#0033FF",
								pointHighlightFill : "#0033FF",
								pointHighlightStroke : "#0033FF",
								data : s_data

							} ]
						};

						//雷达图
						var myRadar = new Chart(document.getElementById("canvas1_"+ id).getContext("2d")).Radar(radarChartData_1,{
							animation : false,

							//Boolean - 是否显示出每个点线表
							scaleShowLine : true,

							//Boolean - 是否显示角线的雷达
							angleShowLineOut : true,

							//Boolean - Whether to show labels on the scale
							scaleShowLabels : true,

							// Boolean - 是否显示标尺上的标签
							scaleBeginAtZero : true,

							//String - 角线的颜色
							angleLineColor : "rgba(0,0,0,0.3)",

							scaleLineColor : "rgba(0,0,0,0.3)",

							//Number - 像素宽度的线角
							angleLineWidth : 1,

							//String - 标签
							pointLabelFontFamily : "'Arial'",

							//String - 点标签的字体重量

							pointLabelFontStyle : "normal",

							//Number - 在像素点标签的字体大小
							pointLabelFontSize : 10,

							//String - 点颜色标签
							pointLabelFontColor : "#000000",

							//Boolean - Whether to show a dot for each point
							pointDot : true,

							//Number - 每个像素点半径
							pointDotRadius : 3,

							//Number - 点点像素宽度
							pointDotStrokeWidth : 1,

							//Number - 大量额外添加到半径为迎合命中检测外画点
							pointHitDetectionRadius : 20,

							//Boolean - 是否要显示一个中风的数据集
							datasetStroke : false,

							//Number - 数据集笔划像素宽度
							datasetStrokeWidth : 2,

							//Boolean - 是否有一种颜色填充数据集
							datasetFill : true,
							//onAnimationComplete: function () { alert("OK")},
							scaleOverride : true,
							scaleSteps : 5,
							// Number - The value jump in the hard coded scale
							scaleStepWidth : 0.2,
							// Number - The scale starting value
							scaleStartValue : 0
						});
						var myRadar_2 = new Chart(document.getElementById("canvas2_"+ id).getContext("2d")).Radar(radarChartData_2,{
							animation : false,
	
							//Boolean - 是否显示出每个点线表
							scaleShowLine : true,
	
							//Boolean - 是否显示角线的雷达
							angleShowLineOut : true,
	
							//Boolean - Whether to show labels on the scale
							scaleShowLabels : true,
	
							// Boolean - 是否显示标尺上的标签
							scaleBeginAtZero : true,
	
							//String - 角线的颜色
							angleLineColor : "rgba(0,0,0,0.3)",
	
							scaleLineColor : "rgba(0,0,0,0.3)",
	
							//Number - 像素宽度的线角
							angleLineWidth : 1,
	
							//String - 标签
							pointLabelFontFamily : "'Arial'",
	
							//String - 点标签的字体重量
	
							pointLabelFontStyle : "normal",
	
							//Number - 在像素点标签的字体大小
							pointLabelFontSize : 10,
	
							//String - 点颜色标签
							pointLabelFontColor : "#000000",
	
							//Boolean - Whether to show a dot for each point
							pointDot : true,
	
							//Number - 每个像素点半径
							pointDotRadius : 3,
	
							//Number - 点点像素宽度
							pointDotStrokeWidth : 1,
	
							//Number - 大量额外添加到半径为迎合命中检测外画点
							pointHitDetectionRadius : 20,
	
							//Boolean - 是否要显示一个中风的数据集
							datasetStroke : false,
	
							//Number - 数据集笔划像素宽度
							datasetStrokeWidth : 2,
	
							//Boolean - 是否有一种颜色填充数据集
							datasetFill : true,
							scaleOverride : true,
							scaleSteps : 5,
							// Number - The value jump in the hard coded scale
							scaleStepWidth : 0.2,
							// Number - The scale starting value
							scaleStartValue : 0
						});

					});

					var options = {
						currentPage : data.page.currentPage,
						totalPages : data.page.totalPage,
						shouldShowPage : function(type, page,current) {
							switch (type) {
							default:
								return true;
							}
						},
						onPageClicked : function(e,originalEvent, type, page) {
							var pp = page;
							aPage = page;
							hsdTests(pp);
						}

					}
					$('#pagination').bootstrapPaginator(options);

				} else {
					$('#list-content').html(
						'<p style="height:100px;text-align:center;line-height:100px;font-size:20px;">'
								+ '您还没有测评信息技术能力，现在测评请点击“去测评”' + '</p>').removeClass('loading');
					$('#pagination').html('');
				}

			});

		}

		$(function() {

			hsdTests(1);

		});

		function gotestClick() {

			var flag = false;

			$.getJSON('../teacher/hsdTest_checkHsdTest.action', function(data) {

				if (data.status == 'OK') {
					window.open("../HSDTesting/index.html","_self");
				} else {
					alert(data.message);
				}

			});

		}
	</script>
<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>