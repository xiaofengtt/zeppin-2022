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
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/jquery.stickytableheaders.js"></script>
</head>
<body>
	<div class="ifrcnt container" style="width:100%">
		<div class="hd">
			<h3>查看报送任务</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form id="assignTeacherTask" class="form-horizontal" role="form"
				action="../admin/assignTeacherTaskOpt_addAssignTeacherTask.action"
				method="post">
				<div class="row clearfix">
					<div class="span7">
						<div class="control-group">
							<label class="control-label" for="">项目名称</label>
							<div class="controls">
								<input type="text" class="span5" disabled value="<s:property value="baseInfo[0]" />">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">报送截止日期</label>
							<div class="controls">
								<input type="text" class="span5" disabled value="<s:property value="baseInfo[2]" />">
							</div>
							
						</div>

					</div>

					<div class="span3">
						<div class="control-group">
							<label class="control-label" for="">培训科目</label>
							<div class="controls">
								<input type="text" disabled value="<s:property value="baseInfo[1]" />">
							</div>
							
						</div>
						<div class="control-group">
							<label class="control-label" for="">任务状态</label>
							<div class="controls">
								<input type="text" disabled value="<s:property value="baseInfo[3]" />">
							</div>
							
						</div>
					</div>
				</div>

				<div class="clearfix" style="margin:10px 20px 0 20px">
					<div id="tableTplWrap"></div>
					<table class="table table-bordered table-assign table-hover">

						<thead>
							<tr>
								<th rowspan=2 width="280" style="vertical-align:middle">派送单位/承训单位</th>
								<s:iterator value="tchashMap.get('top')" id="top">
									<th colspan="2"><s:property value="value" /></th>
								</s:iterator>
								<th colspan="2">合计</th>
							</tr>
							<tr class="plan-total">
								<s:iterator value="tchashMap.get('top')" id="top">
									<th data-editable='no'>计划人数</th>
									<th data-editable='no'>实报人数</th>
								</s:iterator>

								<th data-editable='no'>计划人数</th>
								<th data-editable='no'>实报人数</th>

							</tr>
						</thead>
						<tbody>

							<s:iterator value="tchashMap.get('left')" id="top">
								<tr>
									<td class="uneditable"><s:property value="value" /></td>
									<s:set value="key" name="key1" />
									<s:iterator value="tchashMap.get('top')" id="top">
										<td><s:property
												value="tchashMap.get('value').get(#key1+'-'+key)[0]" /></td>
										<td><s:property
												value="tchashMap.get('value').get(#key1+'-'+key)[1]" /></td>
									</s:iterator>

									<td><s:property value="tchashMap.get('oz').get(#key1)[0]" /></td>
									<td><s:property value="tchashMap.get('oz').get(#key1)[1]" /></td>
								</tr>
							</s:iterator>

						</tbody>

						<tfoot>
							<tr>
								<td rowspan=2 data-editable='no'>合计</td>
								<s:iterator value="tchashMap.get('top')" id="top">
									<td data-editable='no'><s:property
											value="tchashMap.get('tc').get(key)[0]" /></td>
									<td class="csum"><s:property
											value="tchashMap.get('tc').get(key)[1]" /></td>
								</s:iterator>

								<td data-editable='no'><s:property
										value="tchashMap.get('total').get('total')[0]" /></td>
								<td class="csum"><s:property
										value="tchashMap.get('total').get('total')[1]" /></td>

							</tr>
						</tfoot>
					</table>
				</div>
				<div class="row actionbar">
					<div class="text-center">
						<button id="colorboxcancel" onclick="parent.$.colorbox.close()"
							class="btn btn-default" type="button">关闭</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script src="../js/iframe.js"></script>
	<script>
		$(function(){
			$('.table-assign').stickyTableHeaders();
		})
		
	</script>
<body>
</html>