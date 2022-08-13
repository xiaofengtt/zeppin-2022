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
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/mustache.js"></script>

<script src="../js/bootstrap-datepicker.js"></script>
<script src="../js/bootstrap-datepicker.zh-CN.js"></script>

</head>
<body>
	<div class="ifrcnt container">
		<div class="hd">
			<h3>分配报送任务</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form id="assignallotTeacherTask" class="form-horizontal" role="form"
				action="../admin/assignTeacherTaskOpt_addAllotAssignTeacherTask.action"
				method="post">
				<div class="row clearfix">
					<div class="span5">
						<div class="control-group">
							<label class="control-label" for="">项目名称</label>
							<div class="controls">
								<input type="hidden" name="allotProjectId" value="<s:property	value="allotProjectId" />">
								<input type="text" disabled value="<s:property value="allotProjectName" />">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">报送截止日期</label>
							<div class="controls">
								<div class="input-append">
									<input type="text" data-provide="datepicker" class="datepicker"
										id="" name="timeup" value="<s:property	value="timeup" />">
									<span class="add-on"><span class="icon-th"></span></span>

								</div>
							</div>
						</div>

					</div>
					
					<div class="span3">
						<div class="control-group">
							<label class="control-label" for="">培训科目</label>
							<div class="controls">
								<input type="hidden" name="allotSubjectId" value="<s:property	value="allotSubjectId" />">
								<input type="text" disabled value="<s:property value="allotSubjectName" />">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">任务状态</label>
							<div class="controls">
								<select class="span2" name="status">
									<option <s:if test="status==1">selected</s:if> value="1">可报送</option>
									<option <s:if test="status==2">selected</s:if> value="2">停止报送</option>
								</select>
							</div>
						</div>
					</div>
					<input type="hidden" name="allotLevel" value="<s:property	value="allotLevel" />">
					<input type="hidden" name="pageType" value="<s:property	value="pageType" />">
				</div>
				
				<div class="clearfix" style="margin:0 20px">
					<div id="tableTplWrap"></div>
					<table id="sumtable" class="table table-bordered table-assign" style="display:none"><table>
						
					<script id="tableTpl" type="text/template">
						
							<thead>
								<tr>
									<th width="150">派送单位/承训单位</th>
									<th width="50">合计</th>
									{{#top}} <th>{{name}}</th> {{/top}}
									
								</tr>
								<tr class="plan-total">
									<th data-editable='no'>计划报送学员人数</th>
									<th data-editable='no' class="planTotal">0</th>
									{{#top}} <th>{{number}}</th> {{/top}}
								</tr>
							</thead>
							<tbody>
								{{#left}}
								<tr data-left-id="{{lid}}">
									<td data-editable='no' data-left-id="{{lid}}" class="uneditable">{{name}}</td>
									<td data-editable='no' class="rsum uneditable">0</td>
									{{#data}} <td data-top-id="{{id}}">{{value}}</td> {{/data}}
								</tr>
								
								{{/left}}
							</tbody>
						
							<tfoot>
								<tr>
									<td data-editable='no'>合计</td>
									<td data-editable='no' class="realTotal">0</td>
									{{#top}} <td class="csum">0</td> {{/top}}
								</tr>
							</tfoot>	
						
					</script>
					<input type="hidden" value="" name="assginTeacher">
					
				</div>
				<div class="row actionbar">
					<div class="offset7">
						<button class="btn btn-primary" id="savebtn"  type="button">确定</button>
						<button id="colorboxcancel" onclick="parent.$.colorbox.close()"
							class="btn btn-default" type="button">取消</button>
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
			
			$('#savebtn').click(function(){
				if(compareData()) {
					$('input[name="assginTeacher"]').val(getData());

					var str = $('#assignallotTeacherTask').serialize();
					$.post('../admin/assignTeacherTaskOpt_addAllotAssignTeacherTask.action?'+ str, function(data) {
						var Result = data.Result;
						var message = data.Message;
						if (Result == "OK") {
							window.top.location.reload();
						} else {
							$('.alert-danger').html(message).show();
						}
					})
					
				}else {
					$('.alert-danger').html('您的分配人数未达到计划报送人数').show();
				}
				return false;
			})
			var projectId = $('input[name="allotProjectId"]').val();
			var subjectId = $('input[name="allotSubjectId"]').val();
			
			$.getJSON('../admin/assignTeacherTaskOpt_getAllotTaskHeader.action?projectId='+projectId+'&subjectId='+subjectId, function(data) {
					if(data.status == 'OK') {
					    var template = $('#tableTpl').html();
					    var html = Mustache.render(template, data);
					    $('#sumtable').html(html).show();
						$('.alert-danger').hide();
						
					}else {
						$('.alert-danger').html(data.message).show();
						$('#sumtable').hide();
						$('#savebtn').hide();
					}
				});
			
			

		})
		
	</script>

<body>
</html>
