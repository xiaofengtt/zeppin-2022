<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">项目中标统计</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
<style>
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
<div class="main">
	<div class="listwrap">
		<div class="list_bar"><a style="margin-right: 20px" href='../admin/projectBase_initProjectResult.action'>项目中标人数统计</a>多阶段项目中标人数统计</div>
		<div style="padding-top:15px;">
		<form id="reprtTask" class="form-horizontal" role="form" >
		<div class="clearfix">
			<div class="">
				<div class="form-group" style="margin-bottom:15px;max-width:900px;">
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
		
		<div id="list-content" class="list-content clearfix table-responsive">
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
		$('#sumtable').html('').addClass('loading');
		$.getJSON('../admin/projectBase_projectGroupResult.action?'+str,
			function(data) {
				if (data.status == 'OK') {
					if(data.records.length > 0){
						var html = '';
						$.each(data.records,function(j,k){
							var index = data.records.length - j;
							html += '<table id="sumtable'+j+'" class="table table-bordered table-assign table-hover">';
							html += '<thead><tr><th>第'+index+'阶段</th></tr><thead>';
							html += '<thead><tr><th>学科/承训单位</th><th>合计</th>';
							if(k.training.length > 0) {
								$.each(k.training,function(i,v){
									html += '<th class="uneditable">'+v.name+'</th>';
								})
								html += '</tr></thead>';
							}
							html +='<tbody>';
							if(k.subject.length > 0) {
								$.each(k.subject,function(i,v){
									html += '<tr><td data-editable="no" class="uneditable">'+v.name+'</td><td class="uneditable hTotal"></td>';
									var tid = v.id;
									$.each(k.training,function(i,v){
										
										if(typeof(k.value[tid+'_'+v.id]) == 'undefined') {
											html += '<td></td>';
										}else {
											html += '<td>'+ k.value[tid+"_"+v.id] +'</td>';
										}
									})
									
								})
							}
							html +='</tbody>';
							
							html += '<tfoot><tr><td data-editable="no" class="uneditable">合计</td><td class="csum"></td>';
							if(k.training.length > 0) {
								$.each(k.training,function(i,v){
									html += '<td class="uneditable csum"></td>';
								})
								html += '</tr></tfoot>';
							}
							html += '</table>';
							
						})
						$('#list-content').html(html);
						$.each(data.records,function(j,k){
							suminit('#sumtable'+j);
						})
						
						$('.errorM').hide();
						
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