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
		<script src="../js/utf8-jsp/ueditor.config.js"></script>
		<script src="../js/utf8-jsp/ueditor.all.min.js"></script>
		<script src="../js/utf8-jsp/lang/zh-cn/zh-cn.js"></script>
		<script src="../laydate-v1.1/laydate/laydate.js"></script>
		<link rel="stylesheet" href="../css/publish.css">
	</head>

	<body>
	<input id="pagename" type="hidden" value="main" />
		<%@ include file="header.jsp"%>
		<%@ include file="mainLeft.jsp"%>
		<div class="main">
			<div class="main_content">
				<form action="../admin/examAdd" method="post" id="examinfo" autocomplete="off">
					<div class="box">
						<label>考试名称：</label>
						<input type="text" name="name" id="name">
						<p class="warning">请正确填写考试名称！</p>
					</div>

					<div class="time_num box">
						<div class="time">
							<label class="bottom_margin">考试时间：</label>
							<div class="time_bd">
								<input type="text" name="starttime" id="starttime" readonly>
								<i><img src="../img/date.png"></i>
								<p class="warning">请正确填写考试开结束时间！</p>
							</div>
							<span>至</span>
							<div class="time_bd">
								<input type="text" class="time" name="endtime" id="endtime" readonly>
								<i><img src="../img/date.png"></i>
								<p class="warning">请正确填写考试开结束时间！</p>
							</div>
						</div>

						<div class="num box">
							<label>考试积分：</label>
							<input type="text"  name="integral" id="integral">
							<p class="warning">请正确填写考试积分！</p>
						</div>
					</div>
					<div class="time_num box">
						<div class="time">
							<label class="bottom_margin">申报截止时间：</label>
							<div class="time_bd">
								<input type="text" id="applyEndTime" name="applyEndTime" readonly>
								<i><img src="../img/date.png"></i>
								<p class="warning">请正确填写申报截止时间！</p>
							</div>

						</div>
 
						<div class="time">
							<label class="bottom_margin">确认截止时间：</label>
							<div class="time_bd">
								<input type="text" id="checkEndTime" name="checkEndTime" readonly>
								<i><img src="../img/date.png"></i>
								<p class="warning">请正确填写申报截止时间！</p>
							</div>

						</div>
						
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
							<option value="">请选择模版...</option>
						</select>
						<input id="msg_id" type="hidden" value="" />
						<textarea class="statement" id="statement" height="225px" name="invigilationContract"></textarea>
					</div>
					<div class="sub">
						<input type="button" value="发布考试" class="sub_publish">
						<input type="button" value="返回" class="back">
					</div>

				</form>

			</div>
		</div>
		<div class="modal">
        		<p>考试发布成功！</p>
        		<span>是否保存监考责任书为模板？</span>
        		<div class="button_g">
				<div>
					<label for="">模板名称:</label>
					<input type="text" name="title" id="m_name"/>
					<input type="button" value="保存"/>
					<input type="button" value="不保存"/>
				</div>
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
		<script type="text/javascript" src="../js/app.js"></script>
		<script type="text/javascript" src="../js/jsrender.min.js"></script>
		<script src="../js/input_test.js"></script>
		<script type="text/javascript" src="../js/publish.js"></script>

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
	;
	! function() {

		//laydate.skin('molv');

		laydate({
			elem: '#applyEndTime',
			format: 'YYYY-MM-DD hh:mm:ss',
			istime: true
		});
		laydate({
			elem: '#checkEndTime',
			format: 'YYYY-MM-DD hh:mm:ss',
			istime: true
		});
		laydate({
			elem: '#starttime'
		});
		laydate({
			elem: '#endtime'
		});
	}();
	

</script>