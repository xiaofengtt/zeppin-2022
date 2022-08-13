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
	<div class="ifrcnt container" style="width:100%">
		<div class="hd">
			<h3>添加名额分配任务</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form id="assignTeacherTask" class="form-horizontal" role="form"
				action="../admin/assignTeacherTaskOpt_addAssignTeacherTask.action"
				method="post">
				<div class="row clearfix">
					<div class="span9">
						<div class="control-group">
							<label class="control-label" for="">项目名称</label>
							<div class="controls">
								<select id="projectname" class="span7" name="projectId">
									<option>请选择项目名称...</option>
									<s:iterator value="listProject" id="project" >
										<option value="<s:property value="#project.getId()" />" ><s:property value="#project.getName()" /></option>
									</s:iterator>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">报送截止日期</label>
							<div class="controls">
								<input type="text" readonly data-provide="datepicker" class="datepicker"
									id="" name="timeup" value="">
								
							</div>
						</div>

					</div>
					
					<div class="span3">
						<div class="control-group">
							<label class="control-label" for="">培训科目</label>
							<div class="controls">
								<select id="kemu" class="span2" name="subjectId">
									<option>请选择培训科目</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">任务状态</label>
							<div class="controls">
								<select class="span2" name="status">
									<option value="1">可报送</option>
									<option value="2">停止报送</option>
								</select>
							</div>
						</div>
						
						
					</div>
					
					

				</div>
				
				
				<div class="clearfix" style="margin:0 20px">
					<div class="alert alert-warning hide">
					  	<b>*报送任务表单白色区域可以编辑!</b>
					</div>
					<table id="sumtable" class="table table-bordered table-assign" style="display:none"></table>
						
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
								<tr data-left-id="{{id}}">
									<td data-editable='no' data-left-id="{{id}}" class="uneditable">{{name}}</td>
									<td data-editable='no' class="rsum uneditable">0</td>
									{{#top}} <td data-top-id="{{id}}">0</td> {{/top}}
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
					<div class="text-center">
						<button class="btn btn-primary btn-myself" id="savebtn"  type="button">确定</button>
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
		
		$('#savebtn').click(function(){
			if(flag){
				flag = false;
				$('input[name="assginTeacher"]').val(getData());

				var str = $('#assignTeacherTask').serialize();
				$.get('../admin/assignTeacherTaskOpt_addAssignTeacherTask.action?'+ str, function(data) {
					var Result = data.Result;
					var message = data.Message;
					if (Result == "OK") {
						window.top.location.reload();
					} else {
						$('.alert-danger').html(message).show();
						flag = true;
					}
				})
			}
				/* 	}else {
				$('.alert-danger').html('您的分配人数未达到计划报送人数').show();
			}
			return false; */
		}) 
			
			//获取培训科目
			$('select[name="projectId"]').on('change',function(){
				$.getJSON('../admin/assignTeacherTaskOpt_getTrainingSubjectWithProject.action?projectId='+$(this).val(), function(r) {
					var ufc = (r.length) ? '<option value>请选择培训科目</option>' : '<option value>暂无择培训科目</option>';
					$.each(r, function(i, c){
						ufc += '<option value="'+ c.id +'">'+ c.name +'</option>';
					});
					$('select[name="subjectId"]').html(ufc);
					
				});
			});
			/* $('select[name="subjectId"]').on('change',function(){
				var projectId = $('select[name="projectId"]').val();
				var subjectId = $(this).val();
				$.getJSON('../admin/assignTeacherTaskOpt_getTaskHeader.action?projectId='+projectId+'&subjectId='+subjectId, function(data) {
					
					if(data.status == 'OK') {
					    var template = $('#tableTpl').html();
					    var html = Mustache.render(template, data);
					    $('#sumtable').html(html).show();
						$('#sumtable').initsumtable();//初始化报送人数
						$('.alert-warning').show();
						$('.alert-danger').hide();
						
					}else {
						$('.alert-danger').html(data.message).show();
						 $('#sumtable').hide();
					}
				});
			})*/
			
			

		}) 
		
	</script>

<body>
</html>
