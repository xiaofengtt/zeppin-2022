<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="renderer" content="webkit">

		<title>发布考试</title>
		<link rel="stylesheet" href="../css/mainBox.css" />
		<link rel="stylesheet" href="../css/publish.css">
		<script src="../js/utf8-jsp/ueditor.config.js"></script>
		<script src="../js/utf8-jsp/ueditor.all.min.js"></script>
		<script src="../js/utf8-jsp/lang/zh-cn/zh-cn.js"></script>
		<script type="text/template" id="template">
					<input type="hidden" name="id" value="{{:id}}">
					<div class="box">
						<label>考试名称：</label>
						<input type="text" class="name" name="name" value="{{:name}}" id="name" data-msg="请填写考试名称！">
						<p class="warning">请正确填写考试名称！</p>
					</div>

					<div class="time_num box">
						<div class="time">
							<label class="bottom_margin">考试时间：</label>
							<div class="time_bd">
								<input type="text" name="starttime" id="starttime" value="{{:starttime}}" data-msg="请填写考试时间！" readonly>
								<i><img src="../img/date.png"></i>
								<p class="warning">请正确填写考试开始时间！</p>
							</div>
							<span>至</span>
							<div class="time_bd">
								<input type="text" class="time" name="endtime" id="endtime" value="{{:endtime}}" data-msg="请填写考试时间！" readonly>
								<i><img src="../img/date.png"></i>
								<p class="warning">请正确填写考试结束时间！</p>
							</div>
						</div>

						<div class="num box">
							<label>考试积分：</label>
							<input type="text" name="integral" id="integral" value="{{:integral}}" data-msg="请正确填写考试积分！">
							<p class="warning">请正确填写考试积分！</p>
						</div>
					</div>
					<div class="time_num box">
						<div class="time">
							<label class="bottom_margin">申报截止时间：</label>
							<div class="time_bd">
								<input type="text" id="applyEndTime" name="applyEndTime" value="{{:applyendtime}}" data-msg="请填写申报截止时间！" readonly>
								<i><img src="../img/date.png"></i>
								<p class="warning">请正确填写申报截止时间！</p>
							</div>
						</div>

						<div class="time">
							<label class="bottom_margin">确认截止时间：</label>
							<div class="time_bd">
								<input type="text" id="checkEndTime" name="checkEndTime" value="{{:checkendtime}}" data-msg="请填写确认截止时间！" readonly>
								<i><img src="../img/date.png"></i>
								<p class="warning">请正确填写确认截止时间！</p>
							</div>
						</div>
						
						<div class="time status">
							<label>考试状态：</label>
							<div class="time_bd">
								<select name="status" id="status" class="exam_status" value="{{:status}}">
									<option value="-1">待发布</option>
									<option value="0">已结束</option>
									<option value="1">已发布</option>
									<option value="2">进行中</option>
								</select>
							</div>
						</div>
					</div>
	</script>
	
	</head>
	
	<body>
	<input id="pagename" type="hidden" value="main" />
		<%@ include file="header.jsp"%>
		<%@ include file="mainLeft.jsp"%>
		<div class="main">
			<div class="main_content">
				<form action="../admin/examUpdate" method="post" id="examinfo">
					<div id="show">
						
					</div>
					

					<div class="box1">
						<label class="bottom_margin">考试信息:</label>
						<textarea class="info" id="info" name="information"></textarea>
					</div>
					
					<div class="box1">
						<label class="bottom_margin">申报注意事项:</label>
						<textarea class="applyNotice" id="applyNotice" name="applyNotice"></textarea>
					</div>
					
					<div class="box1">
						<label class="bottom_margin">监考注意事项:</label>
						<textarea class="invigilationNotice" id="invigilationNotice" name="invigilationNotice"></textarea>
					</div>
					
					<div class="box1">
						<label class="bottom_margin">监考责任书：</label>
						<select name="" id="" class="select_msg">
							<option>请选择模版...</option>
						</select>
						<textarea class="statement" id="statement" height="225px" name="invigilationContract"></textarea>
					</div>
					<div class="sub">
						<input type="button" value="保存" class="sub_publish">
						<input type="button" value="返回" class="back">
					</div>

				</form>

			</div>
		</div>
        <div id="modal">
        		<p></p>
        		<div class="button_g">
				<input type="button" value="关闭"/>
        		</div>
		</div>
		<%@ include file="footer.jsp"%>
		<script src="../js/jquery-form.js"></script>
		<script src="../js/input_test.js"></script>
		<script type="text/javascript" src="../js/app.js"></script>
		<script type="text/javascript" src="../js/jsrender.min.js"></script>
		<script src="../laydate-v1.1/laydate/laydate.js"></script>
		<script type="text/javascript" src="../js/publish_.js"></script>

	</body>

</html>
<script>
	var info = new UE.getEditor('info', {
		initialFrameHeight: 225
	});
	var statement = new UE.getEditor('statement', {
		initialFrameHeight: 225
	});
	var applyNotice = new UE.getEditor('applyNotice', {
		initialFrameHeight: 225
	});
	var invigilationNotice = new UE.getEditor('invigilationNotice', {
		initialFrameHeight: 225
	});
</script>
<script>
	
</script>
