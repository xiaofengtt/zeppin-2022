<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>

<script src="../js/jsrender.min.js"></script>
<script src="../js/highcharts.js"></script>
<script src="../js/data.js"></script>


<style>
* {
	margin: 0 auto;
	padding: 0;
}

.list-content {
	margin-left: 225px;
}

.busi_in {
	width: 20%;
	height: 100px;
	border: 1px solid #000;
	float: left;
	vertical-align: middle;
	line-height: 100px
}

h6 {
	width: 70%;
	margin: 0 auto;
	font-size: 17px;
	color: #333;
	padding: 30px 0 20px 0;
	font-family: 'Microsoft Yahei';
	font-weight: bold;
}

.todoBox {
	width: 70%;
	margin: 0 auto;
}

.todoDiv {
	width: 33.3333%;
	float: left;
	text-align: center;
	border: 1px solid #999;
	font-size: 14px;
	color: #666;
	padding-bottom: 8.5%;
	cursor: pointer;
}
/* .todoDiv:hover{ */
/* border-color:#428bca; */
/* } */
.todoDiv:hover span {
	color: #039be5;
}

.todoDiv:hover span a {
	color: #039be5;
	text-decoration: underline;
}

.todoDiv p {
	text-align: left;
	padding: 30px 0 20px 30px;
	font-size:15px;
}

.todoDiv span {
	color: #aeb9be;
	cursor: pointer;
	display: block;
	padding-top: 5%;
}

.todoDiv span a {
	font-size: 50px;
	color: #aeb9be;
	cursor: pointer;
	color:#039be5;
}

.todoDiv span:hover {
	color: #039be5;
}

.todoDiv span:hover a {
	color: #039be5;
	text-decoration: underline;
}

ul.circularUl {
	display: block;
	float: right;
	padding: 0 10px;
	height: 40px;
	margin-bottom: 0;
	margin-top: 45px;
}

ul.circularUl li {
	width: 12px;
	height: 12px;
	float: left;
	border-radius: 6px;
	border: 1px solid #999;
	list-style: none;
	margin: 24px 3px;
	cursor: pointer;
}

ul.circularUl li.light {
	background: #428bca;
	border-color: #428bca;
}

.circular {
	width: 70%;
	margin: 0 auto;
	position: relative;
}

.circular section {
	width: 100%;
	font-size: 14px;
	padding-bottom: 5px;
	position: absolute;
	left: 0;
	top: 0;
	display: none;
}

.circular div p.title {
	/* 	background: #1e88e5; */
	/* 	height: 40px; */
	/* 	line-height: 40px; /* color:#3276b1; */ */
	/* 	color: #fff; */
	/* 	padding-left: 10px; */
	
}

.circular div p.contentP {
	padding: 10px 10px 0px 10px;
	line-height: 28px;
	text-indent: 2em;
	color: #428bca;
	margin-bottom: 60px;
}

.circular div p.inscribed {
	text-align: right;
	padding-right: 10px;
	line-height: 28px;
	color: #428bca;
	margin-bottom: 0px;
}

.circular div p.data {
	text-align: right;
	padding-right: 15px;
	line-height: 20px;
	color: #428bca;
	margin-bottom: 40px;
}

.list-item {
	box-shadow: none;
	margin-bottom: 0px;
}
@media screen and (max-width: 1444px) {
    .todoDiv p{font-size:15px;color:#1a1a1a;}
    .todoDiv span a{font-size:50px;}
}

@media screen and (min-width: 1445px) and (max-width: 1660px) {
    .todoDiv p{font-size:16px;color:#1a1a1a;}
    .todoDiv span a{font-size:54px;}
}
@media screen and (min-width: 1660px) {
    .todoDiv p{font-size:16px;color:#1a1a1a;}
    .todoDiv span a{font-size:58px;}
}

</style>
<div id="list-content" class="list-content clearfix"></div>
<script id="main_count" type="text/x-jsrender">
<div style="display: none">
	<table id="datatable" style="display: block; width: 60%;">
		<thead>
			<tr>
				<th>时间</th>
				<th>每日人数</th>
			</tr>
		</thead>
		<tbody>
			{{for graph}}
			<tr>
				<td>{{:date}}</td>
				<td>{{:count}}</td>
			</tr>
			{{/for}}
		</tbody>
	</table>
</div>

<div id="div_notice" style="width: 70%; margin: 0 auto; height: 60px;">
	<h6 style="float: left; margin: 0; margin-top: 25px;">通知公告</h6>

	<ul class="circularUl">
		{{for notice}}
		<li></li> {{/for}}
	</ul>
</div>
<div style="clear: both;"></div>
<div class="circular">
	{{for notice}}
	<section>
		<div class="panel panel-primary">
			<div class="title panel-heading">{{:title}}</div>
			<div class="panel-body">
				<p class="contentP">{{:content}}</p>
				<p class="inscribed">{{:creator}}</p>
				<p class="data">{{:createtime}}</p>
			</div>

		</div>
	</section>
	{{/for}}
</div>

<h6>待办事项</h6>
<div class="todoBox">
	   <div class="todoDiv list-item" id="find2"><p>正开展的培训项目</p><span><a href="../admin/projectBase_initPage.action?status=2">{{:notReleaseForTrainingProject}}</a>个</span></div>
	   <div class="todoDiv list-item" id="find1"><p>未处理的项目申报</p><span><a href="../admin/projectApply_initPage.action?year=0&projectType=0&projectName=0&subjectName=0&status=1">{{:notAuditRecords}}</a>个</span></div>
	   <div class="todoDiv list-item" id="find3"><p>学员报送任务</p><span><a href="../admin/ttRecord_initReortAsstPage.action?">{{:teacherToSubmitTask}}</a>项</span></div>
	   <div class="todoDiv list-item" id="find4"><p>待审核的培训学员</p><span><a href="../admin/ttRecord_initAdu65Page.action?status=1">{{:notAuditForTrainingTeacher}}</a>名</span></div>
	   <!--<div class="todoDiv list-item" id="find5"><p>未读站内信</p><span><a href="../admin/mail_getInboxListInit.action?">{{:countToUnReadMessae}}</a>封</span></div>-->
       <!--<div class="todoDiv list-item" id="find7"><p>待处理的下级申请</p><span><a href="../admin/serviceApply_init.action?status=0">{{:countForServiceApply}}</a>个</span></div>-->
	   <div class="todoDiv list-item" id="find9" style="display:none;"><p>未审核的替换学员</p><span><a href="../admin/ttRecord_initReplaceAdu.action?status=1">{{:teacherTrainingReplace}}</a>个</span></div>
	   <div class="todoDiv list-item" id="find8"><p>未审核的教师学历提升申请</p><span><a href="../admin/teacherEduAduList.jsp?status=-1">{{:teacherEduAdvances}}</a>条</span></div>
       <div class="todoDiv list-item" id="find6"><p>可以申报的项目</p><span><a href="../admin/trainingUnitProjectApply_initPage.action?">{{:canApplyForTrainingProject}}</a>个</span></div>
	   <div style="clear:both;"></div>
</div>
<h6 id = "graph_title" style="margin-top:25px; ">培训情况</h6>
<div id="aa" style="width: 70%; height: 400px; margin: 0 auto"></div>
</script>



<script>
	$(function() {
		function getCount() {
			var role;
			var organizationLevel;
			$('#list-content').html('').addClass('loading');
			$.getJSON('../admin/adminMain_init.action?', function(data) {
				if (data.status == 'OK') {
					var template = $.templates('#main_count');
					var html = template.render(data.records);
					role = template.render(data.role);
					organizationLevel = template.render(data.organizationLevel);
					$('#list-content').html(html).removeClass('loading');
				} else {
					$('#list-content').html('未搜索到数据').removeClass('loading');
				}
				role = data.role;
				organizationLevel = data.organizationLevel;
				if (role == '2') { //承训管理员
					$('#find6').css("display", "block");
					$('#find1').css("display", "none");
					$('#find2').css("display", "none");
					$('#find3').css("display", "none");
					$('#find4').css("display", "none");
					$('#find8').css("display", "none");
					$('#find9').css("display", "none");
// 					$('#find5').css("display", "none");
// 					$('#find7').css("display", "none");
				} else 	if (role == '1') {//项目管理员
					$('#find6').css("display", "none");
					$('#find1').css("display", "block");
					$('#find2').css("display", "block");
					$('#find3').css("display", "block");
					$('#find4').css("display", "block");
					$('#find8').css("display", "block");
					if(organizationLevel == '1'){
						$('#find9').css("display", "block");
					}
// 					$('#find5').css("display", "block");
// 					$('#find7').css("display", "block");
				}else{
					$('#find1').css("display", "none");
					$('#find2').css("display", "none");
					$('#find3').css("display", "none");
					$('#find4').css("display", "none");
// 					$('#find5').css("display", "none");
					$('#find6').css("display", "none");
					$('#find8').css("display", "none");
					$('#find9').css("display", "none");
// 					$('#find7').css("display", "none");
				}
// 				if (data.organizationLevel == 1) {//顶级项目管理员，才有“统计下级申请”的功能
// 					$('#find7').css("display", "block");
// 				} else {
// 					$('#find7').css("display", "none");
// 				}
				if (data.records.notice == null) {
					$('#div_notice').css("display", "none");
				} else {
					$('#div_notice').css("display", "block");
				}
			}).done(function() {
				//////////////////////////////////
				if (role == '1') {
					getGraph();
					$('#graph_title').css("display", "block");
				} else {
					$('#graph_title').css("display", "none");
				}
				//////////////////////////////////////////////
				var length = $('.circularUl').children('li').length - 1;
				var i = 0;
				$(".todoBox div").each(function(index, element) {
					if ($(this).css("display") == "block") {
						i += 1;
						if (i == 1) {

						} else if (i == 2 || i == 3) {
							$(this).css("border-left", "none");
						} else if (i > 3 && i % 3 != 1) {
							$(this).css("border-left", "none");
							$(this).css("border-top", "none");
						} else {
							$(this).css("border-top", "none");
						}
					} else {
						i = i;
					}
				})
				$("ul.circularUl li:eq(0)").addClass("light");
				$(".circular section:eq(0)").css({
					"position" : "static",
					"display" : "block"
				});
				$(".circular section").hover(function() {
					if (length > 0) {
						clearInterval(t);
					}
				}, function() {
					if (length > 0) {
						t = setInterval(autoChange, 15000);
					}
				})
				$("ul.circularUl li").click(function() {

					$(this).addClass("light").siblings().removeClass("light");
					num = $(this).index();
					$(".circular section:eq(" + num + ")").css({
						"position" : "static"
					}).fadeIn(500).siblings().css({
						"position" : "absolute"
					}).fadeOut(500);
					if (length > 0) {
						clearInterval(t);
						t = setInterval(autoChange, 15000);
					}
				})
				var num = 0;
				if (length > 0) {
					t = setInterval(autoChange, 15000);
				}
				function autoChange() {
					num = (num == length) ? 0 : ++num;
					$(".circular section:eq(" + num + ")").css({
						"position" : "static"
					}).fadeIn(500).siblings().css({
						"position" : "absolute"
					}).fadeOut(500);
					$('ul.circularUl li:first').addClass('light');
					$('ul.circularUl li').removeClass('light');
					$('ul.circularUl li:eq(' + num + ')').addClass('light');
				}

			})

		}
		//初始化
		getCount();
	})

	function getGraph() {
		//曲线图 
		$('#aa').highcharts({
			data : {
				table : document.getElementById('datatable')
			},
			legend : {
				enabled : false
			},
			title : {
				text : '每日新增培训人数',
				style : {
					"fontSize" : "15px",

				}
			},
			yAxis : {
				allowDecimals : false,
				title : {
					text : '每日新增培训人数',
					style : {
						"fontSize" : "12px",

					}
				}
			},
			xAxis : {
				tickInterval : 4
			},
			plotOptions : {
				line : {
					dataLabels : {
						enabled : true
					},
					enableMouseTracking : true
				}
			},
		});
	}
</script>
<jsp:include page="foot.jsp"></jsp:include>