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
<link rel="stylesheet" href="../css/menu.css">
<link rel="stylesheet" href="../css/easyModal.css" />
<script id="queboxTpl" type="text/template">
	<span>{{:name}}</span><i>{{if status=='-1'}}待发布{{/if}}{{if status=='0'}}已结束{{/if}}{{if status=='1'}}已发布{{/if}}{{if status=='2'}}进行中{{/if}}</i>
</script>
</head>

<body>
	<input id="pagename" type="hidden" value="main" />
	<%@ include file="header.jsp"%>
	<%@ include file="mainLeft.jsp"%>

	<div class="nav">
		<div class="nav_title">
			<div id="queboxCnt"></div>
		</div>
		<div class="btn_group">
			<a id="edit">编辑</a>
			<a id="start" href="javascript:;">开始申报</a>
			<a id="stop" href="javascript:;">停止申报</a>
			<a id="end" href="javascript:;">结束考试</a>
		</div>
	</div>

	<div class="main">
		<p class="main_title">本场考试管理</p>
		<hr>

		<div class="menu">
			<a id="roomInfo"> <img src="../img/logo_1_r.png">
				<p>本场考试考场管理</p>
			</a> 
			<a id="teachersControl"> <img src="../img/logo_2_r.png">
				<p>教师申报情况管理</p>
			</a> 
			<a id="roomMessage" class="right_none"> <img
				src="../img/logo_3_r.png">
				<p>设置考场监考老师</p>
			</a> 
			<a id="teachersConfirm"> <img src="../img/logo_4_r.png">
				<p>监考老师二次确认</p>
			</a> 
			<a id="dTeacherInfo"> <img src="../img/logo_5_r.png">
				<p>导出教师考场安排</p>
			</a> 
			<a id="dTeacherInfoEnd"> <img src="../img/logo_5_r.png">
				<p>导出本次监考教师</p>
			</a> 
			<a id="dUndTeacher" class="right_none"> <img
				src="../img/logo_6_r.png">
				<p>导出未录用人员名单</p>
			</a> 
			<a id="teacherAssess"> <img src="../img/logo_7_r.png">
				<p>监考老师评价</p>
			</a>
			<a id="OutTeacherPdf" class="right_none" target="_blank"> <img
				src="../img/logo_6_r.png">
				<p>导出执考通知单</p>
			</a>
		</div>
			
			<a id="down"  target="_blank"><span id="clickDown" ></span></a>
			
			<div class="modal_assess">
        		<input type="hidden" id="assess_id"/>
        		<p>存在未二次确认教师，是否继续？</p>
        		<div class="button_g">
        			<input type="button" value="确认" id="sub_assess"/>
        			<input type="button" value="取消" id="close_assess"/>
       	</div>
      </div>
		
		
		
	</div>
	<div id="bg">
		<img src="../img/select2-spinner.gif" alt="" />
	</div>

	
	<div class="easy_modal">
		<p></p>
		<div class="button_g">
			<input type="button" value="关闭"/>
		</div>
	</div>
		<%@ include file="footer.jsp"%>

	<script src="../js/app.js"></script>
	<script type="text/javascript" src="../js/jsrender.min.js"></script>
	<script type="text/javascript" src="../js/menu.js"></script>
</body>

</html>
