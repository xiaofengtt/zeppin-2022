<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">名额分配统计</s:param>
</s:action>
<!-- <script src="../js/iframe.js"></script> -->
<link rel="stylesheet" href="../css/iframe.css">
	<style>#sumtable0 th,#sumtable0 td {min-width:80px; vertical-align: middle}
	.col-sm-2{width:16.66666667%;float:left;}
	.col-sm-6{width:50%;float:left;}
	.form-horizontal .control-label{text-align:right;}
	</style>
		<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>
<style>
.select2-container .select2-choice{width:auto;height:28px;line-height:28px;border:none;}
.select2-container{padding:0;}
.select2-container .select2-choice > .select2-chosen{line-height:34px;}
</style>
<script src="../js/jquery.stickytableheaders.js"></script>
<div class="main">
	<div class="listwrap">
		<div class="list_bar"><a style="margin-right: 20px" href='../admin/assignTeacherTask_initAssignResult.action'>项目名额分配统计</a>多阶段项目名额分配统计</div>
		<div style="padding-top:15px;">
		<form id="reprtTask" class="form-horizontal" role="form" >
		<div class="row clearfix">
			<div class="col-md-9" style="max-width:700px;">
				<div class="form-group">
					<label class="col-sm-2 control-label" for="" style="width:150px;">多阶段项目名称</label>
					<div class="col-sm-6">
						<select class="form-control" name="id" id="projectName">
							<s:iterator value="lstGroup" id="rt">
								<option value='<s:property value="#rt.getId()" />' search='<s:property value="#rt.getName()" />'><s:property
										value="#rt.getName()" /></option>
							</s:iterator>
						</select>
					</div>
					<button class="btn btn-primary btn-myself" id="findRecord" type="submit">查询</button>
				</div>
			</div>
		</div>


		</form>
		</div>
		
		<div id="list-content" class="list-content clearfix">
			<table id="sumtable" class="table table-bordered table-assign table-hover" style="display:none">
				
			</table>
			<div class="errorM" style="display:none"></div>
			
		</div>
		
	</div>

</div>
<script>

	function suminit(table) {
		var obj = $(table), footer = $(table).find('tfoot tr');
		//横向
		obj.find('.hTotal').each(function(i) {
			var hTotal = 0;
			obj.find('tbody tr,tfoot tr').eq(i).find('td:not([data-editable="no"]),th:not([data-editable="no"])')
				.each(function() {
					var num = ($(this).text()!='') ? parseFloat($(this).text()) : 0;
					
					hTotal += parseFloat(num);
				})
				$(this).text(hTotal);
		});
		//纵向
		footer.find('td.csum').each(function(i) {
			var vTotal = 0, column = $(this).index();
			obj.find('tbody tr').each(function(i) {
				var row = $(this);
				var num = (row.children().eq(column).text()!='') ? parseFloat(row.children().eq(column).text()) : 0;
				vTotal += num;
			});
			$(this).text(vTotal);

		})
	}
	
	function assginTaskView(o) {
		var str = $(o).serialize();
// 		$('#list-content').addClass('loading').find('table').html('');
		$('#sumtable').html('').addClass('loading');
		$.getJSON('../admin/assignTeacherTask_assignGroupResult.action?'+str,
			function(data) {
				if (data.status == 'OK') {
					if(data.records.length > 0){
						var html = '';
						$.each(data.records,function(j,k){
							var index = data.records.length - j;
							html += '<table id="sumtable'+j+'" class="table table-bordered table-assign table-hover">';
							html += '<thead><tr><th>第'+index+'阶段</th></tr><thead>';
							html += '<thead><tr><th style=" vertical-align: middle" rowspan="2" >派送单位</th><th>承训单位</th>';
							
							if(k.trainingsubject.length > 0) {
								$.each(k.trainingsubject,function(i,v){
									html += '<th class="uneditable">'+v.trainingname+'</th>';
									
								})
								html += '<th rowspan="2">合计</th></tr>';
								html += '<tr><th>学科</th>'
								$.each(k.trainingsubject,function(i,v){
									html += '<th class="uneditable">'+v.subjectname+'</th>';
									
								})
								html += '</tr></thead>';
							}
							html +='<tbody>';
							if(k.organization.length > 0) {
								$.each(k.organization,function(i,v){
									html += '<tr><td data-editable="no" colspan="2" class="uneditable"><b>'+ v.name +'</b></td>';
									
									var orgid = v.id;
									var orgname = v.name;
									$.each(k.trainingsubject,function(i,v){
										if(typeof(k.value[v.trainingid+'_'+v.subjectid +'_'+ orgid]) == 'undefined') {
											html += '<td></td>';
										}else {
											html += '<td class="tooooltip" data-toggle="tooltip" data-placement="top" title="'+ orgname +'">' + k.value[v.trainingid+'_'+v.subjectid +'_'+ orgid] +'</td>';
										}
									})
									html +='<td class="uneditable hTotal"></td></tr>';
									
								})
							}
							html +='</tbody>';
							html += '<tfoot><tr><td data-editable="no" class="uneditable" colspan="2">合计</td><td class="csum"></td>';
							if(k.trainingsubject.length > 0) {
								$.each(k.trainingsubject,function(i,v){
									html += '<td class="uneditable csum"></td>';
								})
								html += '</tr></tfoot>';
							}
							html += '</table>';
						})
						
// 						$('#sumtable').html(html).show();
						$('#list-content').html(html);
						
						$('.errorM').hide();
						$.each(data.records,function(j,k){
							suminit('#sumtable'+j);
							
							$('#sumtable'+j).stickyTableHeaders('destroy');
							$('#sumtable'+j).stickyTableHeaders();
						})
						$('.tooooltip').tooltip({container:'body'})
					} else {
						$('.errorM').html('<p style="text-align:center;font-size:18px;height:150px;line-height:150px;">暂无数据</p>').show();
						
					}
					
				} else {
					$('.errorM').html('<p style="text-align:center;font-size:18px;height:150px;line-height:150px;">暂无数据</p>').show();
					
				}
			});
	}

	assginTaskView('#reprtTask');

	$(function() {//
		$('#reprtTask').submit(function() {
			assginTaskView(this);
			return false;
		});
		
		$('#projectName').select2();
	
	})
</script>


<jsp:include page="foot.jsp"></jsp:include>