<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="../css/bootstrap2.css">
<link rel="stylesheet" href="../css/iframe.css">
<link href="../css/datepicker3.css" rel="stylesheet">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/iframe.js"></script>
<script src="../js/bootstrap-datepicker.js"></script>
<script src="../js/bootstrap-datepicker.zh-CN.js"></script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>
<style type="text/css">
</style>
</head>
<body>
	<div class="ifrcnt container">
		<div class="hd">
			<h3>补录项目申报结果</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display: none; margin: 0 15px 15px;"></div>
			<form id="addProjectApply" class="form-horizontal" role="form"
				action="../admin/projectApplyOpt_addProjectApply.action"
				method="post">
				<input type="hidden" name="id" value="<s:property value="id" />">
				<div class="clearfix">
					<div class="span10">
						<div class="control-group">
							<label class="control-label" for="">项目年份</label>
							<div class="controls">
								<select class="span3" name="year" onchange="getprojects();">
									<s:iterator value="yearArray" id="ya">
										<option <s:if test="year==#ya" >selected</s:if>
											value="<s:property value="#ya" />"><s:property
												value="#ya" /></option>
									</s:iterator>
								</select>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">项目名称</label>
							<div class="controls">
								<select class="span3" name="project" id="project"
									onchange="changeProject(this)">
									<option value="0">全部</option>
									<s:iterator value="lsProject" id="ya">
										<option
											<s:if test="%{projectApply.project.id==#ya.getId()}">selected</s:if>
											value="<s:property value="#ya.getId()" />" search="<s:property value="#ya.getName()" />"><s:property
												value="#ya.getName()" /></option>
									</s:iterator>
								</select>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">学科</label>
							<div class="controls">
								<select class="span3" name="trainingSubject"
									onchange="changeClassHour()">
									<option value="0" search="全部">全部</option>
								</select>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">承训单位</label>
							<div class="controls">
								<select class="span3" name="trainingCollege">
								</select>
							</div>
						</div>
						<s:if test="projectApply.project.enrollType == 2">
							<div class="control-group">
								<label class="control-label" for="">报名截止日期</label>
								<div class="controls">
									<div class="input-daterange input-group" id="datepicker">
										<input type="text" readonly class="input-sm form-control"
											name="enrollEndTime"
											value="<s:date name="projectApply.enrollEndTime" format="yyyy-MM-dd"/>">

									</div>


								</div>
							</div>
						</s:if>

						<div class="control-group">
							<label class="control-label" for="">培训时间</label>
							<div class="controls">
								<div class="input-daterange input-group" id="datepicker">
									<input type="text" readonly class="input-sm form-control"
										name="trainingStarttime"
										value="<s:date name="projectApply.trainingStarttime" format="yyyy-MM-dd"/>">
									<span class="input-group-addon"> 至 </span> <input type="text"
										readonly class="input-sm form-control" name="trainingEndtime"
										value="<s:date name="projectApply.trainingEndtime" format="yyyy-MM-dd"/>">
								</div>


							</div>
						</div>
<!-- 						<div class="control-group" id="trainingClasshour" -->
<!-- 							<s:if test="%{projectApply.project.traintype==2}"> style="display:none"</s:if>> -->
<!-- 							<label class="control-label" for="">集中培训课时</label> -->
<!-- 							<div class="controls"> -->
<!-- 								<div class="input-append"> -->
<!-- 									<input type="text" id="" name="trainingClasshour" -->
<!-- 										placeholder="" -->
<%-- 										value="<s:property value="projectApply.trainingClasshour"/>"> --%>
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="control-group" id="trainingOnlineHour" -->
<!-- 							<s:if test="%{projectApply.project.traintype==1}"> style="display:none"</s:if>> -->
<!-- 							<label class="control-label" for="">远程培训课时</label> -->
<!-- 							<div class="controls"> -->
<!-- 								<div class="input-append"> -->
<!-- 									<input type="text" id="" name="trainingOnlineHour" -->
<!-- 										placeholder="" -->
<%-- 										value="<s:property value="projectApply.trainingOnlineHour"/>"> --%>
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
						<div class="control-group">
							<label class="control-label" for="">培训人数</label>
							<div class="controls">

								<input type="text" id="" name="approveNumber" placeholder=""
									value="<s:property value="projectApply.approveNumber"/>">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">联系人</label>
							<div class="controls">
								<input type="text" id="" name="contacts"
									value="<s:property value="projectApply.contacts"/>">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">联系电话</label>
							<div class="controls">
								<input type="text" id="" name="phone"
									value="<s:property value="projectApply.phone"/>">
							</div>
						</div>
						<!-- 显示学时>0的 -->
						
						<div class="control-group" >
							<label class="control-label" for="">培训学时类型</label>
							<div class="studyhoursdiv">
								<s:if test="studyhourList != null">
									<s:iterator value="studyhourList" id="pros">
										<div class="controls" <s:if test="%{value<=0}"> style="display:none"</s:if>>
											<s:property value="nameCN"/>
											<input type="hidden" id="input_<s:property value="name"/>" name="<s:property value="name"/>" class="readonly" readonly="readonly"
												value="<s:property value="value"/>" />
										</div>
									</s:iterator>
								</s:if>			
							</div>					
						</div>

						<div class="control-group" id="remark">
							<label class="control-label" for="">备注</label>
							<div class="controls">
								<input type="text" id="input_remark" name="remark"
									value="<s:property value="projectApply.remark"/>">
							</div>
						</div>
					</div>

				</div>

				<div class="row actionbar">
					<div class="offset7">
						<button class="btn btn-primary btn-myself" type="submit">确定</button>
						<button id="colorboxcancel" onclick="parent.$.colorbox.close()"
							class="btn btn-default btn-myself" type="button">取消</button>
					</div>

				</div>
			</form>
		</div>

	</div>
	<script>
		//学时
		function changeClassHour() {
			var project = $('select[name="project"]').val();
			var trainingSubject = $('select[name="trainingSubject"]').val();
			var years = $('select[name="year"]').val();
			$
					.getJSON(
							'../admin/projectApplyOpt_getClassHoursForTeacher.action?',
							{
								year : years,
								project : project,
								trainingSubject : trainingSubject
							},
							function(data) {
								if(data.studyhour != null && data.studyhour.length > 0){
									var html = '';
									for(var i = 0; i < data.studyhour.length; i++){
										if(data.studyhour[i].value > 0){
											html += '<div class="controls">'
													+data.studyhour[i].nameCN
													+'<input type="hidden" id="input_'+data.studyhour[i].name+'" name="'+data.studyhour[i].name
													+'" class="readonly" readonly="readonly" value="'+data.studyhour[i].value+'" />'
													+'</div></div>'
										}
									}
									$('.studyhoursdiv').html(html);
								}else {
									$('.studyhoursdiv').html('');
								}

							});
		}
		function changeProject(t) {
			var vid = $(t).val();
// 			$.getJSON('../admin/projectApplyOpt_getProjectTraintype.action?id='
// 					+ vid, function(data) {
// 				if (data.traintype == 1) {
// 					$('#trainingClasshour').css('display', 'block');
// 					$('#trainingOnlineHour').css('display', 'none');
// 				} else if (data.traintype == 2) {
// 					$('#trainingClasshour').css('display', 'none');
// 					$('#trainingOnlineHour').css('display', 'block');
// 				} else if (data.traintype == 3) {
// 					$('#trainingClasshour').css('display', 'block');
// 					$('#trainingOnlineHour').css('display', 'block');
// 				}
// 			});
			changeClassHour();
		}

		function getprojects() {
			var year = $('select[name="year"]').val();
			var projectType = ($('input[name="restrictRightId"]').val()) ? $(
					'input[name="restrictRightId"]').val() : '';
			$.getJSON('../admin/projectBase_getProjectsByPramers.action', {
				year : year,
				projectType : projectType
			}, function(r) {
				if (r.length > 0) {
					var ufc = '';
					$.each(r,
							function(i, c) {
								ufc += '<option value="'+ c.id +'">' + c.name
										+ '</li>';
							});
					$('select[name="project"]').html(ufc);
				} else {
					$('select[name="project"]').html('<option>无数据</option>');
				}
			}).done(function(){
				changeClassHour();
			})
		}

		$(function() {
			$('.input-daterange').datepicker({
				language : "zh-CN",
				format : 'yyyy-mm-dd',
				//startDate : "1d",
				autoclose : true

			})
			
			$('#project').select2();
			
			var id = $('input[name="id"]').val();
			// if (!id) {
			$('#projecttypecnt').show();
			resCon3();

			//}

			//获取学科
			$
					.getJSON(
							'../admin/trainingSubject_getTrainingSubject.action?type=1&jtStartIndex=0&jtPageSize=1000',
							function(r) {
								if (r.Records.length > 0) {
									var ufc = '';
									var tsid = '<s:property value="projectApply.trainingSubject.id" />';
									$
											.each(
													r.Records,
													function(i, c) {
														if (tsid == c.id) {
															ufc += '<option value="'+ c.id +'" selected search="' + c.name + '">'
																	+ c.name
																	+ '</li>';
														} else {
															ufc += '<option value="'+ c.id +'" search="' + c.name + '" >'
																	+ c.name
																	+ '</li>';
														}

													});
									$('select[name="trainingSubject"]').html(
											ufc);
									$('select[name="trainingSubject"]').select2();
								}
							});

			$
					.getJSON(
							'../admin/trainingCollege_getTrainingCollegeListByProject.action?type=1&projectId=0',
							function(r) {
								if (r.Records.length > 0) {
									var ufc = '';
									var tsid = '<s:property value="projectApply.trainingCollege.id" />';

									$
											.each(
													r.Records,
													function(i, c) {
														if (tsid == c.id) {
															ufc += '<option value="'+ c.id +'" selected search="' + c.name + '" >'
																	+ c.name
																	+ '</li>';
														} else {
															ufc += '<option value="'+ c.id +'" search="' + c.name + '" >'
																	+ c.name
																	+ '</li>';
														}

													});

									$('select[name="trainingCollege"]').html(
											ufc);
									$('select[name="trainingCollege"]').select2();
								}
							});
// 			changeClassHour();

		})

		$(function() {
			var submitFlag = true;
			$('#addProjectApply').submit(function() {
				if(submitFlag){
					submitFlag = false;
					var str = $(this).serialize();
					$.get('../admin/projectApplyOpt_addProjectApply.action?' + str, function(data) {
						var Result = data.Result;
						var message = data.Message;
						if (Result == "OK") {
							window.top.location.reload();
						} else {
							$('.alert-danger').html(message).show();
							submitFlag = true;
						}
					})
				}
				return false;
			});

			//项目类型 helper 函数
			$(document).on("click", function(e) {
				if (!$(e.target).parents().is('.ufc'))
					$('.uul').hide();
			});

		})
	</script>
<body>
</html>
