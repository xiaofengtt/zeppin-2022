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
	<style>#sumtable th,#sumtable td {min-width:80px; vertical-align: middle}</style>
<script src="../js/jquery.stickytableheaders.js"></script>
<div class="main">
	<div class="listwrap">
		<div class="list_bar">项目名额分配统计</div>
		<div style="padding-top:15px;">
		<form id="reprtTask" class="form-horizontal" role="form" >
		<div class="row clearfix">
			<div class="col-md-9">
				<div class="form-group">
					<label class="col-sm-2 control-label" for="">年份</label>
					<div class="col-sm-2">
						<select class="form-control" name="year" onchange="changeYear(this)">
							<option value="0">全部</option>
							<s:iterator value="searchYear" id="yt">
								<option value='<s:property value="#yt" />' <s:if test="#yt==selectYear"> selected </s:if> ><s:property
										value="#yt" /></option>
							</s:iterator>
						</select>
					</div>
					<label class="col-sm-2 control-label" for="">项目名称</label>
					<div class="col-sm-6">
						<select class="form-control" name="id" id="projectName">
							<s:iterator value="searchReportTask" id="rt">
								<option value='<s:property value="#rt.getId()" />'><s:property
										value="#rt.getName()" /></option>
							</s:iterator>
						</select>
					</div>
					<button class="btn btn-primary" id="findRecord" type="submit">查询</button>
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
	function changeYear(t){
		var year = $(t).val();
		$.getJSON('../admin/ttRecord_getAssignTaskProject.action?year=' + year, function(data){
			if (data.projects.length > 0) {
				var cLis = '';
				$.each(data.projects, function(i, c) {
					cLis += '<option value="' +c.id + '">' + c.name	+ '</option>';
				});
				$('#projectName').html(cLis);
			} else {
				$('#projectName').html('<option value="0">暂无数据<option>');
			}
		})
	}

	function suminit(table) {
		var obj = $(table), footer = $(table).find('tfoot tr');
		//横向
		$('.hTotal').each(function(i) {
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
		$('#list-content').addClass('loading').find('table').html('');
		$.getJSON('../admin/assignTeacherTask_assignResult.action?'+str,
			function(data) {
				if (data.status == 'OK') {
					
					var html = '<thead><tr><th style=" vertical-align: middle" rowspan="2" >派送单位</th><th>承训单位</th>';
					
					if(data.trainingsubject.length > 0) {
						$.each(data.trainingsubject,function(i,v){
							html += '<th class="uneditable">'+v.trainingname+'</th>';
							
						})
						html += '<th rowspan="2">合计</th></tr>';
						html += '<tr><th>学科</th>'
						$.each(data.trainingsubject,function(i,v){
							html += '<th class="uneditable">'+v.subjectname+'</th>';
							
						})
						html += '</tr></thead>';
						
						
					}
					html +='<tbody>';
					if(data.organization.length > 0) {
						$.each(data.organization,function(i,v){
							html += '<tr><td data-editable="no" colspan="2" class="uneditable"><b>'+ v.name +'</b></td>';
							
							var orgid = v.id;
							var orgname = v.name;
							$.each(data.trainingsubject,function(i,v){
								if(typeof(data.value[v.trainingid+'_'+v.subjectid +'_'+ orgid]) == 'undefined') {
									html += '<td></td>';
								}else {
									html += '<td class="tooooltip" data-toggle="tooltip" data-placement="top" title="'+ orgname +'">' + data.value[v.trainingid+'_'+v.subjectid +'_'+ orgid] +'</td>';
								}
							})
							html +='<td class="uneditable hTotal"></td></tr>';
							
						})
					}
					html +='</tbody>';
					html += '<tfoot><tr><td data-editable="no" class="uneditable" colspan="2">合计</td><td class="csum"></td>';
					if(data.trainingsubject.length > 0) {
						$.each(data.trainingsubject,function(i,v){
							html += '<td class="uneditable csum"></td>';
						})
						html += '</tr></tfoot>';
					}
					
					$('#sumtable').html(html).show();
					$('#list-content').removeClass('loading');
					
					$('.errorM').hide();
					suminit('#sumtable');
					
					$('#sumtable').stickyTableHeaders('destroy');
					$('#sumtable').stickyTableHeaders();
					$('.tooooltip').tooltip({container:'body'})
					
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
		
		
	
	})
</script>


<jsp:include page="foot.jsp"></jsp:include>