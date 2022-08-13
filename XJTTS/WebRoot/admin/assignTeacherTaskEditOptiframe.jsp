<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="../css/bootstrap2.css">
<link rel="stylesheet" href="../css/iframe.css">
<link href="../css/datepicker3.css" rel="stylesheet">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/bootstrap.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/mustache.js"></script>
<script src="../js/bootstrap-datepicker.js"></script>
<script src="../js/bootstrap-datepicker.zh-CN.js"></script>

<script src="../js/jquery.stickytableheaders.js"></script>

</head>
<body>
	<input type="hidden" id="enrolltype" value="<s:property value="enrolltype" />">
	<div class="ifrcnt container" style="width:100%">
		<div class="hd">
			<h3>编辑报送任务</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form id="assignEditTeacherTask" class="form-horizontal" role="form"
				action="../admin/assignTeacherTaskOpt_editAssignTeacherTask.action"
				method="post">
				<input type="hidden" id="" name="assId" value="<s:property value="assId" />">
				<div class="row clearfix">
					<div class="span8">
						<div class="control-group">
							<label class="control-label" for="">项目名称</label>
							<div class="controls">
								<input type="hidden" name="allotProjectId"
									value="<s:property	value="allotProjectId" />"> <input
									type="text" class="span6" disabled
									value="<s:property value="allotProjectName" />">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">报送截止日期</label>
							<div class="controls">
								<s:if test='isEdit=="0"'>
									<input type="hidden" name="timeup"
										value="<s:property	value="timeup" />">
									<input type="text" class="span6" disabled
										value="<s:property value="timeup" />">
								</s:if>
								<s:else>
									<input type="text" data-provide="datepicker" class="datepicker"
										name="timeup" value="<s:property	value="timeup" /> ">
								</s:else>

							</div>
						</div>

					</div>

					<div class="span3">
						<div class="control-group">
							<label class="control-label" for="">培训科目</label>
							<div class="controls">
								<input type="hidden" name="allotSubjectId"
									value="<s:property	value="allotSubjectId" />"> <input
									type="text" disabled
									value="<s:property value="allotSubjectName" />">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">任务状态</label>
							<div class="controls">
<%-- 								<s:if test='adminLevel!="1"'> --%>
								<s:if test='isEdit=="0"'>
									<input type="hidden" name="status"	value="<s:property	value="status" />">
								</s:if>
							
								<select class="span2" name="status"
<%-- 									<s:if test='adminLevel!="1"'>  --%>
									<s:if test='isEdit=="0"'>
									disabled="disabled" </s:if>>
									<option <s:if test="status==1">selected</s:if> value="1">可报送</option>
									<option <s:if test="status==2">selected</s:if> value="2">停止报送</option>
								</select>
							</div>
							<input type="hidden" name="allotLevel"
								value="<s:property	value="allotLevel" />">
						</div>

					</div>



				</div>
				<s:if test="enrolltype==1">
				<div class="clearfix" style="margin:0 20px">
					<div class="alert alert-warning">
						<b>*编辑报送任务表单白色区域可以编辑!</b>
					</div>
					<div id="tableTplWrap"></div>
					<table id="sumtable"
						class="table table-bordered table-assign table-hover"
						style="display:none"></table>

					<script id="tableTpl" type="text/template">
						
							<thead>
								<tr>
									<th width="280">派送单位/承训单位</th>
									<th width="50">合计</th>
									{{#top}} <th>{{name}}</th> {{/top}}
									
								</tr>
								<tr class="plan-total">
									<th data-editable='no'>计划报送学员人数</th>
									<th data-editable='no' class="planTotal hTotal">0</th>
									{{#top}} <th>{{number}}</th> {{/top}}
								</tr>
							</thead>
							<tbody>
								{{#left}}
								<tr data-left-id="{{lid}}">
									<td data-editable='no' data-left-id="{{lid}}" class="uneditable">{{name}}</td>
									<td data-editable='no' class="rsum uneditable hTotal">0</td>
									{{#data}} <td title="{{name}}" class="tooooltip" data-toggle="tooltip" data-placement="top" data-top-id="{{id}}">{{value}}</td> {{/data}}
								</tr>
								
								{{/left}}
							</tbody>
						
							<tfoot>
								<tr>
									<td data-editable='no'>合计</td>
									<td data-editable='no' class="realTotal hTotal">0</td>
									{{#top}} <td class="csum">0</td> {{/top}}
								</tr>
							</tfoot>	
						
					</script>
					<input type="hidden" value="" name="assginTeacher">

				</div>
				</s:if>
				

				<div class="alert alert-danger"
					style="display:none;margin:0 15px 15px;"></div>
				<div class="row actionbar">
					<div class="text-center">
						<button class="btn btn-primary btn-myself" id="savebtn" type="button">确定</button>
						<button id="colorboxcancel" onclick="parent.$.colorbox.close()"
							class="btn btn-default btn-myself" type="button">取消</button>
					</div>

				</div>
			</form>
		</div>

	</div>
	<script src="../js/iframe.js"></script>
	<script>
		$(function() {

			$('.datepicker').datepicker({
				language : "zh-CN",
				format : 'yyyy-mm-dd',
				startDate : "1d",
				autoclose : true

			});
			var flag = true;
			var enrolltype=$('#enrolltype').val();
			$('#savebtn').click(function() {
				if(flag){
					flag = false;
					if(enrolltype == 1){
						if (compareData()) {
							var obj = $(this);
							obj.prop('disabled', true);
							$('input[name="assginTeacher"]').val(
									getData());
							var str = $('#assignEditTeacherTask')
									.serializeArray();
							//get 改成post 表单数据太大 
							$.ajax({
								type : "POST",
								url : "../admin/assignTeacherTaskOpt_editAssignTeacherTask.action",
								data : str,
								dataType : "json",
								success : function(data) {
									var Result = data.Result;
									var message = data.Message;
									if (Result == "OK") {
										window.top.location.reload();
									} else {
										obj.prop('disabled', false);
										$('.alert-danger').html(message).show();
									}
								},
								error : function(errormessage) {
	
								}
							}).done(function() {
								obj.removeAttr('disabled');
								flag = true;
							});
	
						} else {
							$('.alert-danger').html('您的分配人数与计划报送人数不相符，请继续分配！').show();
							flag = true;
						}
					}else{
						var obj = $(this);
						obj.prop('disabled', true);
						$('input[name="assginTeacher"]').val(
								getData());
						var str = $('#assignEditTeacherTask')
								.serializeArray();
						//get 改成post 表单数据太大 
						$.ajax({
							type : "POST",
							url : "../admin/assignTeacherTaskOpt_editAssignTeacherTask.action",
							data : str,
							dataType : "json",
							success : function(data) {
								var Result = data.Result;
								var message = data.Message;
								if (Result == "OK") {
									window.top.location.reload();
								} else {
									obj.prop('disabled', false);
									$('.alert-danger').html(message).show();
								}
							},
							error : function(errormessage) {
	
							}
						}).done(function() {
							obj.removeAttr('disabled');
							flag = true;
						});
					}
				}
				return false;
			})
			var projectId = $('input[name="allotProjectId"]').val();
			var subjectId = $('input[name="allotSubjectId"]').val();
			var level = $('input[name="allotLevel"]').val();

			$.getJSON(
					'../admin/assignTeacherTaskOpt_getEditTaskHeader.action?projectId='
							+ projectId + '&subjectId=' + subjectId + '&level='
							+ level, function(data) {
						if (data.status == 'OK') {
							var template = $('#tableTpl').html();
							var html = Mustache.render(template, data);
							$('#sumtable').html(html).show();
							$('#sumtable').initsumtable();//初始化报送人数

							$('#sumtable').stickyTableHeaders();
							$('.tooooltip').tooltip({container:'body'})

							$('.alert-danger').hide();

						} else {
							$('.alert-danger').html(data.message).show();
							$('#sumtable').hide();
							$('#savebtn').hide();
						}
					});

		})
	</script>
<body>
</html>
