<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<!--<script src="../js/main_left.js"></script>-->
<!--<script type="text/javascript" src="http://cdn.webfont.youziku.com/wwwroot/js/wf/youziku.api.min.js"></script>-->


</head>
<body>

	<div id="mainLeft">
		<ul class="mainLeftUl" id="mainLeftUl">
			<li class="light">
				<a  class="bd_t examManage" style="background:url(../img/examManage.png) 15px center no-repeat #F0F0F0;background-size:22px auto">考试管理</a>
				<ul>
					<li href="" class="light"><a class="PingFangSC_R">考试管理中心</a></li>
				</ul>
			</li>
			<li class="light">
				<a class="teacherManage" style="background:url(../img/teacherManage.png) 15px center no-repeat #F0F0F0;background-size:22px auto">监考教师管理</a>
				<ul>
					<li><a href="../admin/teachersMessage.jsp" class="PingFangSC_R" id="teachersMessage">监考教师信息管理</a></li>
					<li><a href="teachersAuditing.jsp" class="PingFangSC_R">监考教师资格审核</a></li>
				</ul>
			</li>
			<li>
				<a class="examHistoryManage" style="background:url(../img/examHistoryManage.png) 15px center no-repeat #F0F0F0;background-size:22px auto">历史考试管理</a>
				<ul>
					<li><a href="../admin/historyInfo.jsp" class="PingFangSC_R bd_b">历史考试信息查询</a></li>
				</ul>
			</li>
		</ul>
	</div>
	
	

</body>
</html>
<script>
	var exam = url('?exam') == null ? '' : url('?exam');
		var status;

//		
		$.ajax({
			type:"get",
			url:"../admin/examGetCurrent",
			async:true,
			success:function(r){
				if (r.Status == 'end') {
					exam = r.Records.id;
					status = 'end';
//					window.location.href = "../admin/menu_f.jsp?exam=" + exam;
				} else if (r.Status == 'empty'){
					status = 'empty';
//					alert(r.Message);
//					window.location.href = "../admin/main.jsp";
				} else if (r.Status == 'success'){
					status = 'success';
					exam = r.Records.id;
				}
			}
		});
		$(".PingFangSC_R").eq(0).click(function(){
			$(".PingFangSC_R").parent().removeClass("light");
			$(this).parent().addClass("light").parent().parent().prev("a");
			switch (status) {
			case 'end':
				window.location.href = "../admin/menu_f.jsp?exam=" + exam;
				break;
			case 'empty':
				window.location.href = "../admin/main.jsp";
				break;
			case 'success':
				window.location.href = "../admin/menu.jsp?exam=" + exam;
				break;
			}
			
//			$('#PingFangSC_R').attr('href',$('#PingFangSC_R').attr('href')+ "?exam="+exam);

//		$youziku.load(".teacherName,.PingFangSC_R", "c6da775181ca42f7a7c023bb366de971", "PingFangSC_R");
//		$youziku.load(".examHistoryManage,.teacherManage,.examManage", "da21d882d8c44aba953de645cc83cdd8", "PingFangSC_L");
//		$youziku.draw();
		});
</script>