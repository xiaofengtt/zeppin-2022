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
</head>
<body>
	<div class="ifrcnt container">
		<div class="hd">
			<h3>编辑多阶段项目信息</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form id="addProjectApply" class="form-horizontal" role="form"
				action="../admin/projectBase_addStageProject.action"
				method="post">
				<input type="hidden" name="nextproject" value="<s:if test="stageProject != null"><s:property value="stageProject.id" /></s:if><s:else>0</s:else>">
				<div class="clearfix">
					<div class="span10">
						
						<div style="margin-left: 200px;" id="projectname">
							<div class="control-group">
								<label class="control-label" for="">项目名称</label>
								<div class="controls">
									<s:iterator value="searchReportTask" id="ya">
										<input type="hidden" name="project"  value="<s:property value="#ya.getId()" />" ><h5 style="margin-top: 5px"><s:property value="#ya.getName()" /></h5>
									</s:iterator>
								</div>
							</div>
	
							<!-- 先绑定，后显示 -->
							<div class="control-group" id="subjects">
								<label class="control-label" for="">培训学科及承训单位</label>
								<div class="restrictcnt" style="margin-top: 30px;display: block;">
								<s:if test="lstProjectApply.size > 0">
									<ul id="projectApplies" style="margin-left: 110px;">
										<li><input type="checkbox" id="checkall" name="checkall" onclick="CheckAll(this.checked)" value="" />全选</li>
										
										<s:iterator value="lstProjectApply" id="pa">
											<li><input type="checkbox" name="projectApplys" value="<s:property value="#pa.getId()" />"><s:property value="#pa.trainingSubject.name" />>><s:property value="#pa.trainingCollege.name" /></li>
										</s:iterator>
									</ul>
								</s:if>
								<s:else>
								<ul id="projectApplies" style="margin-left: 110px;">
									<li>已全部继承或暂无可选项</li>
								</ul>
								</s:else>
								</div>
								
							</div>
	
							<div class="row">
									<div class="" style="margin-left: 170px; margin-top: 20px">
										<button class="btn btn-primary btn-myself" type="submit" id="submitButton">确定</button>
										<button id="colorboxcancel" onclick="parent.$.colorbox.close()"
											class="btn btn-default btn-myself" type="button">取消</button>
									</div>
				
							</div>
						</div>
						
					</div>
				</div>

				
			</form>
		</div>

	</div>
	<script>
		
		function CheckAll(val) { 
				$("input[name='projectApplys']").each(function() { 
				this.checked = val; 
		 	}); 
		 } 
		
		$(function() {
			$('#addProjectApply').submit(
					function() {
						if(!$("input[name='projectApplys']").is(':checked')){
							$('.alert-danger').html("请选择学科及承训单位").show();
							return false;
						}
						$('#submitButton').html('处理中。。。');
						$('#submitButton').attr("disabled", true);
						$('#colorboxcancel').attr("disabled", true);
						var str = $(this).serialize();
						var nextproject = $("input[name='nextproject']");
						if(nextproject == '0'){
							str += '&method=add';
						}else{
							str += '&method=update';
						}
						
						$.get('../admin/projectBase_addStageProject.action?'+ str, function(data) {
							var Result = data.Result;
							var message = data.Message;
							if (Result == "OK") {
								
								$('#submitButton').html('确定');
								alert('操作成功');
								$('#submitButton').attr("disabled", false);
								$('#colorboxcancel').attr("disabled", false);
								window.top.location.reload();
							} else {
								$('.alert-danger').html(message).show();
								$('#submitButton').html('确定');
								$('#submitButton').attr("disabled", false);
								$('#colorboxcancel').attr("disabled", false);
							}
						})
						return false;
					});
		})
	</script>
<body>
</html>
