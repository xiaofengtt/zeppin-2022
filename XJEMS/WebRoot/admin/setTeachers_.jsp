<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="renderer" content="webkit">
		<title>编辑监考老师</title>
		<link rel="stylesheet" href="../css/mainBox.css" />
		<link rel="stylesheet" href="../css/setTeachers.css">
		<script id="template" type="text/template">
			

				
				<input type="hidden" name="id" id="t_id" value="{{:id}}"/>
				<input type="hidden" name="photo" id="photo" value="{{:photo.id}}" />
				<div class="main_top">

					<div class="out_box">
						<div class="inner_box">
							<label>姓名：</label>
							<input type="text" id="name" name="name" value="{{:name}}">
							<p class="warning">请正确填写姓名！</p>
							
						</div>
						<div class="inner_box b_r">
							<label>身份证号：</label>
							<input type="text" id="idcard" name="idcard" value="{{:idcard}}">
							<p class="warning">请正确填写身份证号！</p>
						</div>
					</div>

					<div class="out_box">
						<div class="inner_box">
							<label>性别：</label>
							<select name="sex" id="sex" value="{{:sex}}">
								<option value="1">男</option>
								<option value="2">女</option>
							</select>
						</div>
						<div class="inner_box b_r">
							<label>民族：</label>
							<select name="ethnic" id="ethnic">
								<option value="0">请选择民族...</option>
							</select>
						</div>
					</div>

					<div class="out_box">
						<div class="inner_box">
							<label>专业：</label>
							<input type="text" id="major" name="major" value="{{:major}}">
							<p class="warning">请正确填写专业！</p>
						</div>
						<div class="inner_box b_r">
							<label>联系方式：</label>
							<input type="text" id="mobile" name="mobile" value="{{:mobile}}">
							<p class="warning">请正确填写联系方式！</p>
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
							<select name="type" id="type" value="">
								<option value="0">请选择身份类别...</option>
								<option value="1">考务组</option>
								<option value="2">研究生</option>
								<option value="3">教工</option>
							</select>
						</div>
						<div class="inner_box b_r">
							<label>所在学校或部门：</label>
							<input type="text" id="organization" name="organization" value="{{:organization}}">
							<p class="warning">请正确填写所在学校或部门！</p>
						</div>
					</div>

					<div class="out_box">
						<div class="inner_box">
							<label>入校时间：</label>
							<input readonly type="text" id="inschooltime" name="inschooltime" value="{{:inschooltime}}">
							<p class="warning">请正确填写入校时间！</p>
						</div>
						<div class="inner_box b_r">
							<label>监考积分：</label>
							<input type="text" id="intgral" name="intgral" value="{{:intgral}}">
							<p class="warning">请正确填写监考积分！</p>
						</div>
					</div>

					<div class="out_box">
						<div class="inner_box">
							<label>主考：</label>
							<select name="isChiefExaminer" id="isChiefExaminer" value="{{:isChiefExaminer}}">
								<option value="1">有</option>
								<option value="0">无</option>
							</select>
						</div>
						<div class="inner_box b_r">
							<label>混合监考经验：</label>
							<select name="isMixedExaminer" id="isMixedExaminer" value="{{:isMixedExaminer}}">
								<option value="1">有</option>
								<option value="0">无</option>
							</select>
						</div>
					</div>

					<div class="out_box">
						<div class="inner_box">
							<label>交通银行卡号：</label>
							<input type="text" id="bankCard" name="bankCard" value="{{:bankCard}}">
							<p class="warning">请正确填写交通银行卡号！</p>
						</div>
						<div class="inner_box b_r">
							<label>学/工号：</label>
							<input type="text" id="sid" name="sid" value="{{:sid}}">
							<p class="warning">请正确填写学/工号！</p>
						</div>
					</div>

					<div class="out_box">
						<div class="inner_box">
							<label>职务：</label>
							<input type="text" id="jobDuty" name="jobDuty" value="{{:jobDuty}}">
							<p class="warning">请正确填写职务！</p>
						</div>
					</div>
					<div class="out_box">
						<div class="inner_box">
							<label>所在年级：</label>
							<input type="text" id="studyGrade" name="studyGrade" value="{{:studyGrade}}">
							<p class="warning">请正确填写所在年级！</p>
						</div>
					</div>

					<div class="out_box">
						<div class="inner_box" id="school">
							<label>监考校区：</label>
							
							<label for="sc_1" class="check" data-value="1" data-index='1'>温泉校区</label>
							<label for="sc_2" class="check" data-value="2" data-index='1'>昆仑校区</label>
							<label for="sc_3" class="check" data-value="3" data-index='1'>文光校区</label>

							<input type="hidden" name="invigilateCampus" class="hidden_value" />
						</div>
						<div class="inner_box b_r" id="type">
							<label>监考类型：</label>
							<label for="type_1" class="check" data-value='1' data-index='1'>笔试</label>
							<label for="type_2" class="check" data-value='2' data-index='1'>无纸化</label>
							<label for="type_3" class="check" data-value='3' data-index='1'>测试</label>

							<input type="hidden" name="invigilateType" class="hidden_value" />
						</div>
					</div>

					<div class="last_box">
						<label>特长：</label>
						<textarea class="specialty" id="specialty" name="specialty">{{:specialty}}</textarea>
						<p class="warning">请正确填写特长！</p>
					</div>
				</div>

				<div class="sub">
                    <input type="hidden" name="id" class="id" value="{{:id}}"/>
					<input type="button" id="subForm" value="保存">
					<a href="">返回</a>
				</div>

		</script>
	</head>

	<body>
	<input id="pagename" type="hidden" value="teachersMessage" />
		
		<%@ include file="header.jsp"%>
		<%@ include file="mainLeft.jsp"%>
		<input id="pagename" type="hidden" value="teachersMessage" />
		<div class="main">
			<p class="title_1">
				<i></i> 基本信息
			</p>
			<div class="up_head" id="teacherinfo">
					<form action="../admin/resourceAdd?type=1" class="up_head" method="post" id="teachersinfo">
                        <div class="head_photo">
	                        	<div class="img_bd">
	                        		<img src="img_bd" class="file_img">
	                        	</div>
							<p>点击上传</p>
							<span id="warning">文件类型必须为：.jpg .jpeg .png</span>
                            <input type="file" name="img" class="sub_head">
                        </div>
                    </form>
			</div>
			<form action="../admin/teacherUpdate" method="post" id="teachers_msg">
				<div id="tpl_main"></div>
			</form>
		</div>
		<div class="modal">
        		<p></p>
        		<div class="button_g">
				<input type="button" value="关闭"/>
        		</div>
		</div>
		<%@ include file="footer.jsp"%>
		<script src="../laydate-v1.1/laydate/laydate.js"></script>
		<script src="../js/jquery-form.js"></script>
		<script src="../js/input_test.js"></script>
		<script type="text/javascript" src="../js/jsrender.min.js"></script>
		<script type="text/javascript" src="../js/setTeachers_.js"></script>
		<script type="text/javascript" src="../js/app.js"></script>
	</body>

</html>
<script>

</script>