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

<div class="main">
	<div class="listwrap">
		<div class="list_bar">项目中标人数统计</div>
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
		
		<div id="list-content" class="list-content clearfix table-responsive">
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
		$('#sumtable').html('').addClass('loading');
		$.getJSON('../admin/projectBase_projectResult.action?'+str,
			function(data) {
				if (data.status == 'OK') {
					var html = '<thead><tr><th>学科/承训单位</th><th>合计</th>';
					if(data.training.length > 0) {
						$.each(data.training,function(i,v){
							html += '<th class="uneditable">'+v.name+'</th>';
						})
						html += '</tr></thead>';
					}
					html +='<tbody>';
					if(data.subject.length > 0) {
						$.each(data.subject,function(i,v){
							html += '<tr><td data-editable="no" class="uneditable">'+v.name+'</td><td class="uneditable hTotal"></td>';
							var tid = v.id;
							$.each(data.training,function(i,v){
								
								if(typeof(data.value[tid+'_'+v.id]) == 'undefined') {
									html += '<td></td>';
								}else {
									html += '<td>'+ data.value[tid+"_"+v.id] +'</td>';
								}
							})
							
						})
					}
					html +='</tbody>';
					
					html += '<tfoot><tr><td data-editable="no" class="uneditable">合计</td><td class="csum"></td>';
					if(data.training.length > 0) {
						$.each(data.training,function(i,v){
							html += '<td class="uneditable csum"></td>';
						})
						html += '</tr></tfoot>';
					}
					
					$('#sumtable').html(html).show()
					$('.errorM').hide();
					suminit('#sumtable')
					
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