<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>当前考试</title>
<link rel="stylesheet" href="../css/mainBox.css" />
<link rel="stylesheet" href="../css/menu_f.css">
<script id="queboxTpl" type="text/template">
	<span>{{:name}}</span><i>{{if status=='-1'}}待发布{{/if}}{{if status=='0'}}已结束{{/if}}{{if status=='1'}}已发布{{/if}}{{if status=='2'}}进行中{{/if}}</i>
</script>
</head>

<body>
	<!--<input id="pagename" type="hidden" value="main" />-->
	<%@ include file="header.jsp"%>
	<%@ include file="mainLeft.jsp"%>

	<div class="nav">
		<div class="nav_title">
			<div id="queboxCnt"></div>
		</div>

		<div class="btn_group">
			<a href="">编辑</a> <a href="javascript:;">开始申报</a> <a href="javascript:;">停止申报</a> <a href="javascript:;">结束考试</a>
		</div>
	</div>

	<div class="main">
		<p class="main_title">本场考试管理</p>
		<hr>

		<div class="menu">
			<div>
				<img src="../img/logo_1.png">
				<p>本场考试考场管理</p>
			</div>
			<div>
				<img src="../img/logo_2.png">
				<p>教师申报情况管理</p>
			</div>
			<div>
				<img src="../img/logo_3.png">
				<p>设置考场监考老师</p>
			</div>
			<div>
				<img src="../img/logo_4.png">
				<p>监考老师二次确认</p>
			</div>
			<div>
				<img src="../img/logo_5.png">
				<p>导出教师考场安排</p>
			</div>
			<div>
				<img src="../img/logo_6.png">
				<p>导出未录用人员名单</p>
			</div>
			<div>
				<img src="../img/logo_7.png">
				<p>监考老师评价</p>
			</div>
		</div>
	</div>
		<%@ include file="footer.jsp"%>
	
	<script src="../js/app.js"></script>
	<script type="text/javascript" src="../js/jsrender.min.js"></script>
</body>

</html>
<script>
	//数据加载
	$(document).ready(function() {
		var examId = url("?exam");
		var mUrl;
		if (examId == null) {
			mUrl = '../admin/examGetCurrent?';
			$.get(mUrl, function(r) {
				if (r.Status == 'end') {
					var template = $.templates('#queboxTpl');
					var html = template.render(r.Records);
					$('#queboxCnt').html(html);
				}
			}).done(function(r) {
				$(".btn_group a").eq(0).attr("href", "publish_.jsp?id=" + examId);
			});
		} else {
			mUrl = '../admin/examGet?id='+examId;
			$.get(mUrl, function(r) {
				if (r.Status == 'success') {
					var template = $.templates('#queboxTpl');
					var html = template.render(r.Records);
					$('#queboxCnt').html(html);
				}
			}).done(function(r) {
				$(".btn_group a").eq(0).attr("href", "publish_.jsp?id=" + examId);
			});
		}
		
	})
</script>

