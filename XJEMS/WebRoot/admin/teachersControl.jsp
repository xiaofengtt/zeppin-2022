<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<title>教师申报情况管理</title>
		<link rel="stylesheet" href="../css/tableStlye.css">
		<link rel="stylesheet" href="../css/button_group.css">
		<link rel="stylesheet" href="../css/mainBox.css" />
		<link rel="stylesheet" href="../css/paging.css" />
		<link rel="stylesheet" href="../css/teachersControl.css">

		<script id="queboxTpl" type="text/template">
			<tr {{if #index % 2==1 }}class="odd" {{/if}}>
				<td>{{:#index+1}}</td>
				<td title="{{:name}}">
					<a style="color:#E0615F;" href="./teachersHistoryMessage.jsp?id={{:tid}}">{{:name}}</a>
				</td>
				<td title="{{:mobile}}">{{:mobile}}</td>
				<td title="{{:ethnic}}">{{:ethnic}}</td>
				<td title="{{if sex=='1'}}男{{/if}}{{if sex=='2'}}女{{/if}}">{{if sex=='1'}}男{{/if}}{{if sex=='2'}}女{{/if}}</td>
				<td title="{{if isChiefExaminer=='1'}}是{{/if}}{{if isChiefExaminer=='0'}}否{{/if}}">{{if isChiefExaminer=='1'}}是{{/if}}{{if isChiefExaminer=='0'}}否{{/if}}</td>
				<td title="{{if isMixedExaminer=='1'}}是{{/if}}{{if isMixedExaminer=='0'}}否{{/if}}">{{if isMixedExaminer=='1'}}是{{/if}}{{if isMixedExaminer=='0'}}否{{/if}}</td>
				<td title="{{if type==1}}考务组{{/if}}
                           {{if type==2}}研究生{{/if}}
                           {{if type==3}}教工{{/if}}
                           {{if type==4}}本科{{/if}}
                           {{if type==5}}非师大人员{{/if}}
                         ">{{if type==1}}考务组{{/if}} {{if type==2}}研究生{{/if}} {{if type==3}}教工{{/if}} {{if type==4}}本科{{/if}} {{if type==5}}非师大人员{{/if}}
				</td>
				<td title="{{:major}}">{{:major}}</td>
				<td title="{{:studyGrade}}">{{:studyGrade}}</td>
				<td title="{{:intgral}}">{{:intgral}}</td>
				<td title="{{if distribute=='1'}}是{{/if}}{{if distribute=='0'}}否{{/if}}">{{if distribute=='1'}}是{{/if}}{{if distribute=='0'}}否{{/if}}</td>
				<td title="{{if isconfirm=='1'}}是{{/if}}{{if isconfirm=='0'}}否{{/if}}">{{if isconfirm=='1'}}是{{/if}}{{if isconfirm=='0'}}否{{/if}}</td>
				<td title="{{:applytime}}">{{:applytime}}</td>
			</tr>
		</script>
	</head>

	<body>
		<input id="pagename" type="hidden" value="main" />
		<%@ include file="header.jsp"%>
		<%@ include file="mainLeft.jsp"%>

		<div class="main">
			<p class="title"></p>

			<div class="main_header">
				<div class="filter-applyStatus">
					<span>申报状态：</span>
					<a class="lighting" data-value="1" href="javascript:;">初次申报</a>
					<a data-value="3" href="javascript:;">安排监考</a>
					<a data-value="2" href="javascript:;">未安排监考</a>
					<a data-value="5" href="javascript:;">二次确认</a>
					<a data-value="4" href="javascript:;">未二次确认</a>
				</div>
				<div class="filter-type">
					<span>身份类别：</span>
					<a class="lighting" data-value="0" href="javascript:;">全部</a>
					<a data-value="3" href="javascript:;">教工</a>
					<a data-value="1" href="javascript:;">考务组</a>
					<a data-value="2" href="javascript:;">研究生</a>
					<a data-value="4" href="javascript:;">本科</a>
					<a data-value="5" href="javascript:;">非师大人员</a>
				</div>

				<div class="filter-teacherStatus">
					<span>教师状态：</span>
					<a class="lighting" data-value="-1" href="#">全部</a>
					<a data-value="1" href="#">正常</a>
					<a data-value="0" href="#">停用</a>
				</div>

				<div class="filter-isChief">
					<span>主考经验：</span>
					<a class="lighting" data-value="-1" href="javascript:;">全部</a>
					<a data-value="1" href="javascript:;">是</a>
					<a data-value="0" href="javascript:;">否</a>
				</div>

				<div class="filter-isMixed">
					<span>副考经验：</span>
					<a class="lighting" data-value="-1" href="javascript:;">全部</a>
					<a data-value="1" href="javascript:;">是</a>
					<a data-value="0" href="javascript:;">否</a>
				</div>
				<div class="filter-studyGrade">
					<span>所在年级：</span>
					<a class="lighting" href="javascript:;" data-value="">全部</a>
				</div>
			</div>
			<div class="more">
				<p>
					<span>查看更多筛选<i class="iconfont"><img src="../img/bottom_r.png"></i></span>

				</p>
			</div>

			<div class="main_content">
				<div class="search_body">
					<input type="text" class="search" placeholder="姓名/拼音/手机号" onkeypress="if(event.keyCode==13) {searchBtn();return false;}" autocomplete="off">
					<i class="iconfont" onClick="searchBtn()"><a href="javascript:;"><img
						src="../img/search.png" alt="搜索"></a></i>
				</div>
				<div id="select_page"></div>
			</div>
			<div class="scroll-warp">
				<div class="scroll-inner">
					<table class="teachers_info" cellspacing="0" cellpadding="0" style="width:125%;max-width: 150%;table-layout: auto;">
						<tr class="first_tr">
							<th class="bd_tl">序号</th>
							<th class="sort_th">姓名<i class="iconfont"><img src=""></i></th>
							<th class="sort_th">手机号<i class="iconfont"><img src=""></i></th>
							<th class="sort_th">民族<i class="iconfont"><img src=""></i></th>
							<th class="sort_th">性别<i class="iconfont"><img src=""></i></th>
							<th class="sort_th">主考<i class="iconfont"><img src=""></i></th>
							<th class="sort_th">混考<i class="iconfont"><img src=""></i></th>
							<th class="sort_th">身份<i class="iconfont"><img src=""></i></th>
							<th class="sort_th">专业<i class="iconfont"><img src=""></i></th>
							<th class="sort_th">所在年级<i class="iconfont"><img src=""></i></th>
							<th class="sort_th">积分<i class="iconfont"><img src=""></i></th>
							<th>安排监考</th>
							<th class="sort_th">二次确认<i class="iconfont"><img src=""></i></th>
							<th class="sort_th">申报时间<i class="iconfont"><img src=""></i></th>
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
		<script src="../js/slide.js"></script>
		<script src="../js/teachersControl.js"></script>
	</body>

</html>