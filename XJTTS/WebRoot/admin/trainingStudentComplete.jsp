<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title>添加项目管理员</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="../css/bootstrap2.css">
<link rel="stylesheet" href="../css/iframe.css">
<link href="../css/bootstrap-switch.min.css" rel="stylesheet">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/iframe.js"></script>
<script src="../js/bootstrap-switch.min.js"></script>
<style type="text/css">
.form-horizontal .control-label{width: 190px;    color: #044d79;
    font-weight: bold;
    white-space: nowrap;}
.form-horizontal .controls{margin-left: 200px}
</style>
</head>
<body>
	<div class="ifrcnt container">
		<div class="hd">
			<h3>学员评价及结业</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display: none; margin: 0 15px 15px;"></div>
			<form id="addprojectAdmin" class="form-horizontal" role="form"
				action="../admin/trainingStudentOpt_complete.action" method="post">
				<input type="hidden" name="id"
					value="<s:property	value="teacherTrainingRecords.id" />">
				<div class="clearfix">

					<div class="span5">
						<div class="control-group">
							<label class="control-label" for="">姓名</label>
							<div class="controls">
								<input type="text" id="" name="name" class="readonly"
									readonly="readonly"
									value="<s:property	value="teacherTrainingRecords.teacher.name" />"
									placeholder="姓名">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">身份证号</label>
							<div class="controls">
								<input type="text" id="" name="idcard" class="readonly"
									readonly="readonly"
									value="<s:property	value="teacherTrainingRecords.teacher.idcard" />"
									placeholder="身份证号">
							</div>
						</div>


<!-- 						<div class="control-group"> -->
<!-- 							<label class="control-label" for="">总学时</label> -->
<!-- 							<div class="controls"> -->
<!-- 								<input type="text" class="readonly" readonly="readonly" -->
<%-- 									name="maxHour" value="<s:property	value="maxHour" />"> --%>
<!-- 							</div> -->
<!-- 						</div> -->

<!-- 						<div class="control-group"> -->
<!-- 							<label class="control-label" for="">获得学时</label> -->
<!-- 							<div class="controls"> -->
<!-- 								<input type="text" name="classHour" -->
<%-- 									value="<s:property	value="classHour" />"> --%>
<!-- 							</div> -->
<!-- 						</div> -->

<!-- 						<div class="control-group"> -->
<!-- 							<label class="control-label" for="">获得网络研修学时</label> -->
<!-- 							<div class="controls"> -->
<!-- 								<input type="text" name="onlineHour" -->
<%-- 									value="<s:property	value="onlineHour" />"> --%>
<!-- 							</div> -->
<!-- 						</div> -->

						<!-- 显示学时>0的 -->
						<div class="paStudyhourList">
							<s:if test="paStudyhourList != null">
								<s:iterator value="paStudyhourList" id="pros">
<!-- 									<div class="control-group" -->
<!-- 										<s:if test="%{value<=0}"> style="display:none"</s:if>> -->
<%-- 										<label class="control-label" for=""><s:property value="nameCN"/></label> --%>
<!-- 										<div class="controls"> -->
<%-- 											<input type="text" id="input_<s:property value="name"/>"  class="readonly" readonly="readonly" --%>
<%-- 												value="<s:property value="value"/>" /> --%>
<!-- 										</div> -->
<!-- 									</div> -->
									<!-- 显示学时>0的 -->
									<s:if test="ttrStudyhourList != null">
										<s:iterator value="ttrStudyhourList" id="ttrpros">
											<div class="control-group" id="<s:property value="name"/>"
												<s:if test="%{#pros.value<=0 || #pros.name!=name}"> style="display:none"</s:if>>
												<label class="control-label" for="">获得<s:property value="nameCN"/></label>
												<div class="controls">
													<input type="text" id="input_<s:property value="name"/>" name="<s:property value="name"/>" 
														<s:if test="%{#pros.value<=0 || #pros.name!=name}">  disabled="disabled"</s:if>
														value="<s:property value="value"/>" />
												</div>
											</div>
										</s:iterator>
									</s:if>
									<s:else>
										<div class="control-group" id="<s:property value="name"/>"
												<s:if test="%{value<=0}"> style="display:none"</s:if>>
												<label class="control-label" for="">获得<s:property value="nameCN"/></label>
												<div class="controls">
													<input type="text" id="input_<s:property value="name"/>" name="<s:property value="name"/>" 
														value="0" />
												</div>
											</div>
									</s:else>
									
								</s:iterator>
							</s:if>
						</div>

						<div class="control-group">
							<label class="control-label" for="">总分</label>
							<div class="controls">
								<input type="text" id="" class="readonly" name="address"
									readonly="readonly" value="<s:property	value="maxScore" />"><br>

							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">成绩</label>
							<div class="controls">
								<input type="text" id="" class="readonly" name="address"
									readonly="readonly"
									value="<s:property	value="teacherTrainingRecords.trainingScore" />"><br>
<s:if test=""></s:if>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">是否合格</label>
							<div class="controls">
<!-- 								<input name="trainingStatus" type="checkbox" value="1" checked> -->
<!-- 								<div class="span2"> -->
<!-- 										<input id="restrict" name="trainingStatus" type="checkbox" -->
<!-- 											data-on="primary" data-off="default" data-on-label="合格" -->
<!-- 											data-off-label="不合格" checked value="1"> -->
<!-- 									</div> -->
							<select class="form-control" name="trainingStatus">
								<option value="5" <s:if test="teacherTrainingRecords.trainingStatus == 5"> selected </s:if>>优秀</option>
								<option value="6" <s:if test="teacherTrainingRecords.trainingStatus == 6"> selected </s:if>>良好</option>
								<option value="3" <s:if test="teacherTrainingRecords.trainingStatus == 3"> selected </s:if> >合格</option>
								<option value="4" <s:if test="teacherTrainingRecords.trainingStatus == 4"> selected </s:if>>不合格</option>
							</select>
							</div>
						</div>
						

						<div class="control-group">
							<label class="control-label" for="">结业证编号</label>
							<div class="controls">
								<input type="text" name="certificate"
									value="<s:property	value="teacherTrainingRecords.certificate" />"><br>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">不合格原因</label>
							<div class="controls">
								<input type="text" id="" name="trainingReason"
									value="<s:property	value="teacherTrainingRecords.trainingReason" />">
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">备注</label>
							<div class="controls">
								<input type="text" id="" name="remarks"
									value="<s:property	value="teacherTrainingRecords.remark1" />">
							</div>
						</div>
					</div>


				</div>




				<div class="row actionbar">
					<div class="offset8">
						<button class="btn btn-primary btn-myself" type="submit">确定</button>
						<button id="colorboxcancel" onclick="parent.$.colorbox.close()"
							class="btn btn-default btn-myself" type="button">取消</button>
					</div>

				</div>
			</form>
		</div>

	</div>

	<script>
		$(function() {
			/*$('body').click(function(e) {
				var o = $(e.target);
				if (o.hasClass('clId') || o.parent('.clId').length || o.hasClass('usel') || o.parent('.usel').length) {
					return;
				}
				if (!o.parents('.listSub').length || !o.parents('.uul').length) {
					$('.listSub,.uul').hide();
				}
			});*/
			$('body').click(function(e) {
				var o = $(e.target);
				if (o.hasClass('usel') || o.parent('.usel').length) {
					return;
				}
				if (!o.parents('.uul').length) {
					$('.uul').hide();
				}
			});

			//工作单位 helper 函数
			$(document).on("click", function(e) {
				if (!$(e.target).parents().is('.companylocation'))
					$('.listSub').hide();
			});

// 			$('input[name="trainingStatus"],input[name="createuser"]')
// 					.bootstrapSwitch();
// 			$('input[name="trainingStatus"]').on('switch-change',
// 					function(e, data) {
// 						var $element = $(data.el), value = data.value;
// 						if (value)
// 							$('.restrictcnt').show();
// 						else
// 							$('.restrictcnt').hide();
// 						showform();
// 						resCon();

// 					});

		})
		
		$(function() {
			var submitFlag = true;
			$('#addprojectAdmin').submit(function() {
				if(submitFlag){
					submitFlag = false;
					var str = $(this).serialize();
					$.get('../admin/trainingStudentOpt_complete?'+ str, function(data) {
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

		})
	</script>
<body>
</html>
