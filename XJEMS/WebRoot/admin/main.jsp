<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新疆师范大学考务管理系统</title>
<link rel="stylesheet" href="../css/style.css">
<link rel="stylesheet" href="../css/mainBox.css" />
<link rel="stylesheet" href="../css/main.css" />
</head>

<body>
	<input id="pagename" type="hidden" value="main" />
	<jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="mainLeft.jsp"></jsp:include>

	<div class="main">
		<div class="content">
			<p>当前无正在展开的考试信息,您可以发布最新考试信息或查看历史考试记录！</p>
			<a href="../admin/publish.jsp">发布最新考试信息</a> <a href="../admin/historyInfo.jsp">查看历史考试记录</a>
		</div>
	</div>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
<script src="../js/app.js"></script>
</html>

<script type="text/javascript">
	$(document).ready(function() {
		var mUrl = '../admin/examGetCurrent?';
		$.get(mUrl, function(r) {
			var status = r.Status;
			if (r.Status == 'success') {//有考试
				var id = r.Records.id;
				var name = r.Records.name;
				window.location.href = "../admin/menu.jsp?exam=" + id;
			} 
		}).done(function(r) {
		});
	})
</script>