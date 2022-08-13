<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">项目统计分析</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
	
<script src="../js/jquery.stickytableheaders.js"></script>
<style>
		.table td,.table th {white-space:nowrap;}
</style>
	
<div class="main">
	<div class="listwrap">
		<div class="list_bar">项目统计分析</div>
		<div id="list-content" class="list-content clearfix">
			<table id="sumtable" class="table table-bordered table-assign table-hover" style="display:none;margin-top:15px;">
				
			</table>
			<div class="errorM" style="display:none"></div>
			
		</div>
		
	</div>

</div>
<script>
	function projectAnalysis() {
		$('#list-content').addClass('loading').find('table').html('');
		$.getJSON('../admin/projectBase_analysisList.action', function(data) {
			if (data.status == 'OK') {
				var html = '<thead><tr><th style="vertical-align:middle;width:300px" rowspan="2">项目类型</th>';
				if(data.year.length > 0) {
					$.each(data.year,function(i,v){
						html += '<th colspan="2" class="uneditable">'+v+'年</th>';
					})
					html += '</tr>';
				}
				
				if(data.year.length > 0) {
					html += '<tr>';
					$.each(data.year,function(i,v){
						html += '<th>项目数</th><th class="">学科数</th>';
					})
				}
				html +='</thead><tbody>';
				if(data.projectType.length > 0) {
					$.each(data.projectType,function(i,v){
						html += '<tr><td data-editable="no" class="uneditable" style="text-align:left"><b>'+ v.name +'</b></td>';
						var ptid = v.id;
						var ptname = v.name;
						$.each(data.year,function(i,v){
							if(typeof(data.value[v+'_'+ptid]) == 'undefined') {
								html += '<td style="background:#fcf8e3">0</td><td>0</td>';
							}else {
								html += '<td style="background:#fcf8e3"><a href="../admin/projectBase_initPage.action?year='+v+'&projectType='+ptid+'">'+ data.value[v+'_'+ptid][0] +'</td><td><a href="../admin/projectApply_initPage.action?year='+v+'&projectType='+ptid+'">'+ data.value[v+'_'+ptid][1] +'</a></td>';
							}
						})
						html +='</tr>';
					})
				}
				html +='</tbody>';
				$('#sumtable').html(html).show();
				$('#list-content').removeClass('loading');
				$('.errorM').hide();
			} else {
				$('.errorM').html('<p style="text-align:center;font-size:18px;height:150px;line-height:150px;">暂无数据</p>').show();
				
			}
		});
	};

	projectAnalysis();
</script>


<jsp:include page="foot.jsp"></jsp:include>