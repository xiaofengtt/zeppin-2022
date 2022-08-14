<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="renderer" content="webkit">
		<title>历史考试信息详情</title>
		<link rel="stylesheet" href="../css/paging.css" />
		<link rel="stylesheet" href="../css/historyExam.css" />
		<link rel="stylesheet" href="../css/mainBox.css" />
		<script id="template" type="text/template">


				<div class="main_bottom_room">
					<p title="{{:roomIndex}} - {{:roomAddress}}">{{:roomIndex}} - {{:roomAddress}}</p>
					<table>
						{{for examnation}}
							<tr>
								<td title="{{:examnationInformation}}">{{:examnationInformation}}</td>
								<td title="{{:examnationTime}}">{{:examnationTime}}</td>
							</tr>
						{{/for}}
					</table>
					{{for teacher}}
						<a href="javascript:;" title="{{:name}}">{{:name}}</a>
					{{/for}}
				</div>
		</script>
	</head>

	<body>
		<%@ include file="header.jsp"%>
            <%@ include file="mainLeft.jsp"%>

            <input id="pagename" type="hidden" value="historyInfo" />
		<div class="main">
			<div class="back_confirm">
	            <i class="iconfont"><img src="../img/back_r.png" alt="返回"></i>
	            <a href="./historyInfo.jsp?exam=1">返回历史考试信息</a>
			</div>
			<div class="main_bottom">
				<p class="title"></p>
				<!--<p class="title">考试时间：2017.09.11  10:20</p>-->
				<div id="select_page"></div>

				<div id="msg"></div>
			</div>
		</div>


		<%@ include file="footer.jsp"%>

		<script type="text/javascript" src="../js/jsrender.min.js"></script>
		<script src="../js/minigrid.js"></script>
		<script src="../js/query.js"></script>
		<script src="../js/paging.js"></script>
		<script src="../js/app.js"></script>
		<script src="../js/historyExam.js"></script>

	</body>

</html>
