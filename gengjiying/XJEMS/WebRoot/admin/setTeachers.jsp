<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="renderer" content="webkit">
		<title>添加监考老师</title>
		<link rel="stylesheet" href="../css/mainBox.css" />
		<link rel="stylesheet" href="../css/setTeachers.css">
	</head>

	<body>

		<%@ include file="header.jsp"%>
		<%@ include file="mainLeft.jsp"%>
		<input id="pagename" type="hidden" value="teachersMessage" />
		<div class="main">
			<p class="title_1">
				<i></i> 基本信息
			</p>
			<form action="../admin/resourceAdd?type=1" class="up_head" method="post" id="teacherinfo" autocomplete="off">
				<div class="head_photo">
					<div class="img_bd">
						<img src="../img/headPhoto.png" class="file_img">
					</div>
					<p>点击上传</p>
					<span id="warning">文件类型必须为：.jpg .jpeg .png</span>
					<input type="file" name="img" class="sub_head">
					<input type="hidden" name="type" value="1">
				</div>
			</form>
			<form action="../admin/teacherAdd" method="post" id="teachers_msg" autocomplete="off">
				<input type="hidden" name="photo" id="photo" value="1" />
				<div class="main_top">

					<div class="out_box">
						<div class="inner_box">
							<label>姓名：</label>
							<input type="text" id="name" name="name">
							<p class="warning">请正确填写姓名！</p>
						</div>
						<div class="inner_box b_r">
							<label>身份证号：</label>
							<input type="text" id="idcard" name="idcard">
							<p class="warning">请正确填写身份证号！</p>
						</div>
					</div>

					<div class="out_box">
						<div class="inner_box">
							<label>民族：</label>
							<select name="ethnic" id="ethnic">
								<option value="0">请选择民族...</option>
							</select>
						</div>
						<div class="inner_box b_r">
							<label>联系方式：</label>
							<input type="text" id="mobile" name="mobile">
							<p class="warning">请正确填写联系方式！</p>
						</div>
					</div>

					<div class="out_box">
						<div class="inner_box">
							<label>专业：</label>
							<input type="text" id="major" name="major">
							<p class="warning">请正确填写专业！</p>
						</div>
					</div>

				</div>

				<p class="title_2">
					<i></i> 考务信息
				</p>
				<div class="main_bottom">
					<div class="out_box">
						<div class="inner_box">
							<label>身份类别：</label>
							<select name="type" id="type">
								<option value="0">请选择身份类别...</option>
								<option value="1">考务组</option>
								<option value="2">研究生</option>
								<option value="3">教工</option>
								<option value="4">本科</option>
								<option value="5">非师大人员</option>
							</select>
						</div>
						<div class="inner_box b_r hide">
							<label>所在学院或部门：</label>
							<select name="organization" id="organization-3">
								<option value="">请选择学校或部门</option>
								<option value="马克思主义学院">马克思主义学院</option>
								<option value="商学院">商学院</option>
								<option value="法学院">法学院</option>
								<option value="政治与公共管理学院">政治与公共管理学院</option>
								<option value="民族学与社会学院">民族学与社会学院</option>
								<option value="教育科学学院">教育科学学院</option>
								<option value="文学院">文学院</option>
								<option value="历史学院">历史学院</option>
								<option value="语言学院">语言学院</option>
								<option value="外国语学院">外国语学院</option>
								<option value="国际文化交流学院">国际文化交流学院</option>
								<option value="数学科学学院">数学科学学院</option>
								<option value="物理与电子工程学院">物理与电子工程学院</option>
								<option value="计算机科学技术学院">计算机科学技术学院</option>
								<option value="地理科学与旅游学院">地理科学与旅游学院</option>
								<option value="化学化工学院">化学化工学院</option>
								<option value="生命科学学院">生命科学学院</option>
								<option value="体育学院">体育学院</option>
								<option value="音乐学院">音乐学院</option>
								<option value="美术学院">美术学院</option>
								<option value="初等教育学院">初等教育学院</option>
								<option value="预科教育学院">预科教育学院</option>
								<option value="成人（继续、网络）教育学院">成人（继续、网络）教育学院</option>
								<option value="教师培训学院">教师培训学院</option>
								<option value="新疆旅游培训学院">新疆旅游培训学院</option>
								<option value="组织部">组织部</option>
								<option value="宣传部">宣传部</option>
								<option value="纪检委">纪检委</option>
								<option value="工会">工会</option>
								<option value="团委">团委</option>
								<option value="保卫处（温泉）">保卫处（温泉）</option>
								<option value="保卫处（昆仑）">保卫处（昆仑）</option>
								<option value="保卫处（文光）">保卫处（文光）</option>
								<option value="人事处">人事处</option>
								<option value="教务处">教务处</option>
								<option value="科研处">科研处</option>
								<option value="研究生处">研究生处</option>
								<option value="学生处">学生处</option>
								<option value="大学生就业指导中心">大学生就业指导中心</option>
								<option value="审计处">审计处</option>
								<option value="计财处">计财处</option>
								<option value="后勤管理处">后勤管理处</option>
								<option value="国有资产管理处">国有资产管理处</option>
								<option value="实验室与设备管理处">实验室与设备管理处</option>
								<option value="信息管理中心">信息管理中心</option>
								<option value="图书馆">图书馆</option>
								<option value="师大附中">师大附中</option>
								<option value="后勤服务中心">后勤服务中心</option>
								<option value="学生舍区服务部">学生舍区服务部</option>
								<option value="教学服务中心">教学服务中心</option>
								<option value="物业管理中心">物业管理中心</option>
								<option value="后勤服务部">后勤服务部</option>
								<option value="校医院">校医院</option>
								<option value="派出所">派出所</option>
								<option value="学术交流中心">学术交流中心</option>
							</select>
							<p class="warning">请选择所在学院或部门！</p>
						</div>
						<div class="inner_box b_r hide">
							<label>学院名称：</label>
							<select name="organization" id="organization-24">
								<option value="">请选择学院名称</option>
								<option value="马克思主义学院">马克思主义学院</option>
								<option value="商学院">商学院</option>
								<option value="法学院">法学院</option>
								<option value="政治与公共管理学院">政治与公共管理学院</option>
								<option value="民族学与社会学院">民族学与社会学院</option>
								<option value="教育科学学院">教育科学学院</option>
								<option value="文学院">文学院</option>
								<option value="历史学院">历史学院</option>
								<option value="语言学院">语言学院</option>
								<option value="外国语学院">外国语学院</option>
								<option value="国际文化交流学院">国际文化交流学院</option>
								<option value="数学科学学院">数学科学学院</option>
								<option value="物理与电子工程学院">物理与电子工程学院</option>
								<option value="计算机科学技术学院">计算机科学技术学院</option>
								<option value="地理科学与旅游学院">地理科学与旅游学院</option>
								<option value="化学化工学院">化学化工学院</option>
								<option value="生命科学学院">生命科学学院</option>
								<option value="体育学院">体育学院</option>
								<option value="音乐学院">音乐学院</option>
								<option value="美术学院">美术学院</option>
								<option value="初等教育学院">初等教育学院</option>
								<option value="预科教育学院">预科教育学院</option>
								<option value="成人（继续、网络）教育学院">成人（继续、网络）教育学院</option>
								<option value="教师培训学院">教师培训学院</option>
								<option value="新疆旅游培训学院">新疆旅游培训学院</option>
							</select>
							<p class="warning">请选择学院名称！</p>
						</div>
						<div class="inner_box b_r hide">
							<label>所在学校部门：</label>
							<input type="text" id="organization-5" name="organization">
							<p class="warning">请填写所在学校部门！</p>
						</div>

					</div>

					<div class="out_box">
						<div class="inner_box">
							<label>入校时间：</label>
							<input readonly type="text" id="inschooltime" name="inschooltime">
							<p class="warning">请正确填写入校时间！</p>
						</div>
						<div class="inner_box b_r">
							<label>监考积分：</label>
							<input type="text" id="intgral" name="intgral">
							<p class="warning">请正确填写监考积分！</p>
						</div>
					</div>

					<div class="out_box">
						<div class="inner_box">
							<label>主考：</label>
							<select name="isChiefExaminer" id="isChiefExaminer">
								<option value="1">有</option>
								<option value="0">无</option>
							</select>
						</div>
						<div class="inner_box b_r">
							<label>混合监考经验：</label>
							<select name="isMixedExaminer" id="isMixedExaminer">
								<option value="1">有</option>
								<option value="0">无</option>
							</select>
						</div>
					</div>

					<div class="out_box">
						<div class="inner_box">
							<label>交通银行卡号：</label>
							<input type="text" id="bankCard" name="bankCard">
							<p class="warning">请正确填写交通银行卡号！</p>
						</div>
						<div class="inner_box b_r">
							<label>学/工号：</label>
							<input type="text" id="sid" name="sid">
							<p class="warning">请正确填写学/工号！</p>
						</div>
					</div>

					<div class="out_box">
						<div class="inner_box">
							<label>职务：</label>
							<input type="text" id="jobDuty" name="jobDuty">
							<p class="warning">请正确填写职务！</p>
						</div>
					</div>
					<div class="out_box">
						<div class="inner_box">
							<label>所在年级：</label>
							<select name="studyGrade" id="studyGrade">
								<option value="0">请选择所在年级...</option>
							</select>
							<p class="warning">请选择所在年级！</p>
						</div>
						<div class="inner_box b_r">
							<label>学制信息：</label>
							<select name="studyLength" id="studyLength">
								<option value="0">请选择学制信息...</option>
								<option value="1">一年制</option>
								<option value="2">二年制</option>
								<option value="3">三年制</option>
							</select>
							<p class="warning">请选择学制度信息！</p>
						</div>
					</div>

					<div class="out_box">
						<div class="inner_box" id="school">
							<label>监考校区：</label>

							<label for="sc_1" class="check" data-value="1" data-index="1">温泉校区</label>
							<label for="sc_2" class="check" data-value="2" data-index="1">昆仑校区</label>
							<label for="sc_3" class="check" data-value="3" data-index="1">文光校区</label>

							<input type="hidden" name="invigilateCampus" class="hidden_value" />
						</div>
						<div class="inner_box b_r" id="type">
							<label>监考类型：</label>
							<label for="type_1" class="check" data-value='1' data-index="1">笔试</label>
							<label for="type_2" class="check" data-value='2' data-index="1">无纸化</label>
							<label for="type_3" class="check" data-value='3' data-index="1">测试</label>

							<input type="hidden" name="invigilateType" class="hidden_value" />
						</div>
					</div>

					<div class="last_box">
						<label>特长：</label>
						<textarea class="specialty" name="specialty" id="specialty"></textarea>
						<p class="warning">请正确填写特长！</p>
					</div>
				</div>

				<div class="sub">
					<input type="button" id="subForm" value="保存" style="cursor: pointer;">
					<a href="">返回</a>
				</div>
			</form>
		</div>

		<div class="modal">
			<p></p>
			<div class="button_g">
				<input type="button" value="关闭" />
			</div>
		</div>
		<%@ include file="footer.jsp"%>
		<script src="../laydate-v1.1/laydate/laydate.js"></script>
		<script src="../js/jquery-form.js"></script>
		<script src="../js/input_test.js"></script>
		<script type="text/javascript" src="../js/setTeacher.js"></script>
		<script type="text/javascript" src="../js/app.js"></script>

	</body>

</html>
<script>
	;
	! function() {
		laydate({
			elem: '#inschooltime',
			format: 'YYYY-MM-DD hh:mm:ss',
			istime: true
		});
	}();
</script>