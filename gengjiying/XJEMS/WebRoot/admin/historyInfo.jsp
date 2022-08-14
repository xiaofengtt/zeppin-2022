<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="renderer" content="webkit">
		<title>历史考试信息</title>
		<link rel="stylesheet" href="../css/tableStlye.css">
		<link rel="stylesheet" href="../css/paging.css" />
		<link rel="stylesheet" href="../css/mainBox.css" />
		<link rel="stylesheet" href="../css/historyInfo.css">
		<link rel="stylesheet" href="../css/select_paging.css" />

		<script id="queboxTpl" type="text/template">
			<tr {{if #index % 2==1 }}class="odd" {{/if}}>
				<td>{{:#index+1}}</td>
				<td title="{{:name}}" class="spc">{{:name}}</td>
				<td title="{{:time}}">{{:time}}</td>
				<td>{{if status=='-1'}}待发布{{/if}}{{if status=='0'}}已结束{{/if}}{{if status=='1'}}已发布{{/if}}{{if status=='2'}}进行中{{/if}}</td>
				<td>
					<a href="../admin/historyExam.jsp?exam={{:id}}" class="first_a" target="_blank">详情</a>
					<a href="javascript:;" class="change">修改</a>
					<input type="hidden" id="{{:id}}" />
				</td>
			</tr>
		</script>
	</head>

	<body>
		<input id="pagename" type="hidden" value="historyInfo" />
		<%@ include file="header.jsp"%>
		<%@ include file="mainLeft.jsp"%>
		<div class="main">
			<p class="title">历史考试信息</p>
			<div class="main_content">
				<div class="search_body">
					<input type="text" class="search" placeholder="请输入考试名称..." onkeypress="if(event.keyCode==13) {searchBtn();return false;}">
					<i class="iconfont" onClick="searchBtn()"><img src="../img/search.png"></i>
				</div>
				<div id="select_page">
				</div>
			</div>

			<div class="scroll-warp">
				<div class="scroll-inner">
					<table class="teachers_info" cellspacing="0" cellpadding="0" style="width:100%;max-width: 150%;table-layout: auto;">
						<tr class="first_tr">
							<td>序号</td>
							<td>考试名称</td>
							<td>时间</td>
							<td>状态</td>
							<td>操作</td>
						</tr>
						<tbody id="queboxCnt"></tbody>
					</table>
				</div>
			</div>
			<div class="back_top">
				<a href="#">↑返回页面顶部</a>
			</div>
		</div>
		<%@ include file="footer.jsp"%>
		<script src="../js/app.js"></script>
		<script type="text/javascript" src="../js/jsrender.min.js"></script>
		<script type="text/javascript" src="../js/query.js"></script>
		<script src="../js/paging.js"></script>
		<script type="text/javascript" src="../js/historyInfo.js"></script>
	</body>

</html>