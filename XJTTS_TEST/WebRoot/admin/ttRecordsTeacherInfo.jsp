<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">教师培训全信息统计</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
	
<script src="../js/jquery.stickytableheaders.js"></script>
<style>
	.table td,.table th {white-space:nowrap;}
</style>
	
<div class="main">
	<div class="listwrap">
		<div class="list_bar">教师培训全信息统计</div>
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
		//实际人数	
		$('.sjhTotal').each(function(i) {
			var sjhTotal = 0;
			obj.find('tbody tr,tfoot tr').eq(i).find('td.sjnum:not([data-editable="no"])')
				.each(function() {
					var num = ($(this).text()!='') ? parseFloat($(this).text()) : 0;
					
					sjhTotal += parseFloat(num);
				})
			
				$(this).text(sjhTotal);
		});
		//计划人数	
		$('.jhhTotal').each(function(i) {
			var jhhTotal = 0;
			
			obj.find('tbody tr,tfoot tr').eq(i).find('td.jhnum:not([data-editable="no"])')
				.each(function() {
					var num = ($(this).text()!='') ? parseFloat($(this).text()) : 0;
					jhhTotal += parseFloat(num);
				})
			
				$(this).text(jhhTotal);
		});
		
		$('.shhTotal').each(function(i) {
			var shhTotal = 0;
			
			obj.find('tbody tr,tfoot tr').eq(i).find('td.shnum:not([data-editable="no"])')
				.each(function() {
					var num = ($(this).text()!='') ? parseFloat($(this).text()) : 0;
					shhTotal += parseFloat(num);
				})
			
				$(this).text(shhTotal);
		});
		$('.bdhTotal').each(function(i) {
			var bdhTotal = 0;
			
			obj.find('tbody tr,tfoot tr').eq(i).find('td.bdnum:not([data-editable="no"])')
				.each(function() {
					var num = ($(this).text()!='') ? parseFloat($(this).text()) : 0;
					bdhTotal += parseFloat(num);
				})
			
				$(this).text(bdhTotal);
		});
		$('.jyhTotal').each(function(i) {
			var jyhTotal = 0;
			
			obj.find('tbody tr,tfoot tr').eq(i).find('td.jynum:not([data-editable="no"])')
				.each(function() {
					var num = ($(this).text()!='') ? parseFloat($(this).text()) : 0;
					jyhTotal += parseFloat(num);
				})
			
				$(this).text(jyhTotal);
		});
		
		
		
		
		//纵向
		footer.find('td.csum').each(function(i) {
			var vTotal = 0, column = $(this).index()+1;
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
		$.getJSON('../admin/ttRecord_teacherInfo.action?'+str,
			function(data) {
				if (data.status == 'OK') {
					var html = '<thead><tr><th style="vertical-align:middle" rowspan="2">学科</th><th style="vertical-align:middle"  rowspan="2">承训单位/派送单位</th>';
					if(data.organization.length > 0) {
						$.each(data.organization,function(i,v){
							html += '<th colspan="5" class="uneditable">'+v.name+'</th>';
						})
						html += '<th  style="vertical-align:middle" colspan="5">合计</th></tr>';
					}
					
					if(data.organization.length > 0) {
						html += '<tr>';
						$.each(data.organization,function(i,v){
							html += '<th>计划人数</th><th class="">实际人数</th><th class="">审核通过人数</th><th class="">报到人数</th><th class="">结业人数</th>';
						})
						html += '<th>计划人数</th><th class="">实际人数</th><th class="">审核通过人数</th><th class="">报到人数</th><th class="">结业人数</th></tr>';
					}
					
					html +='</thead><tbody>';
					if(data.trainingsubject.length > 0) {
						$.each(data.trainingsubject,function(i,v){
							html += '<tr><td data-editable="no" class="uneditable"><b>'+ v.subjectname +'</b></td><td data-editable="no"  class="uneditable"><b>'+v.trainingname+'</b></td>';
							var tid = v.trainingid;
							var sid = v.subjectid;
							var sname = v.subjectname;
							var tname = v.trainingname;
							$.each(data.organization,function(i,v){
							
								if(typeof(data.value[tid+'_'+sid +'_'+ v.id]) == 'undefined') {
									html += '<td style="background:#fcf8e3"></td><td></td><td></td><td></td><td></td>';
								}else {
									html += '<td class="jhnum" data-toggle="tooltip" data-placement="top" title='+ sname + '-'+ tname + ' style="background:#fcf8e3">'+ data.value[tid+'_'+sid +'_'+ v.id][0] +'</td><td class="sjnum" data-toggle="tooltip" data-placement="top"  title='+ sname + '-'+ tname + '>'+ data.value[tid+'_'+sid +'_'+ v.id][1] +'</td><td class="shnum" data-toggle="tooltip" data-placement="top"  title='+ sname + '-'+ tname + '>'+ data.value[tid+'_'+sid +'_'+ v.id][2] +'</td><td class="bdnum" data-toggle="tooltip" data-placement="top"  title='+ sname + '-'+ tname + '>'+ data.value[tid+'_'+sid +'_'+ v.id][3] +'</td><td class="jynum" data-toggle="tooltip" data-placement="top"  title='+ sname + '-'+ tname + '>'+ data.value[tid+'_'+sid +'_'+ v.id][4] +'</td>';
								}
							})
							html +='<td class="uneditable jhhTotal"></td><td class="uneditable sjhTotal"></td><td class="uneditable shhTotal"></td><td class="uneditable bdhTotal"></td><td class="uneditable jyhTotal"></td></tr>';
							
						})
					}
					
					html +='</tbody>';
					html += '<tfoot><tr><td data-editable="no" class="uneditable" colspan="2">合计</td><td class="csum"></td>';
					if(data.organization.length > 0) {
						$.each(data.organization,function(i,v){
							html += '<td class="uneditable csum"></td><td class="uneditable csum"></td></td><td class="uneditable csum"></td></td><td class="uneditable csum"></td></td><td class="uneditable csum"></td>';
						})
						html += '<td class="uneditable csum"></td><td class="uneditable csum"></td><td class="uneditable csum"></td><td class="uneditable csum"></td></tr></tfoot>';
					}
					
					$('#sumtable').html(html).show();
					$('#list-content').removeClass('loading');
					$('.errorM').hide();
					suminit('#sumtable');
					
					$('#sumtable').stickyTableHeaders('destroy');
					$('#sumtable').stickyTableHeaders();
					
					$('.jhnum,.sjnum,.shnum,.bdnum,.jynum').tooltip({container:'body'})
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