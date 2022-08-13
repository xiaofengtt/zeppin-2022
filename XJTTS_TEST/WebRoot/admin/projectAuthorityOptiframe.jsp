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
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" >
<link href="../css/metro/blue/jtable.css" rel="stylesheet" type="text/css" >
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/iframe.js"></script>
<script src="../js/jquery-ui.js"></script>
<script src="../js/jquery.jtable.js"></script>
<script src="../js/jquery.jtable.zh-CN.js"></script>
<script src="../js/bootstrap-paginator.min.js"></script>
<script src="../js/bootstrap.js"></script>
<script src="../js/jquery.stickytableheaders.js"></script>
<% 
	String project = request.getParameter("id"); 
%>
</head>
<body>
	<div class="ifrcnt container" style="width:auto">
		<div class="bd">
			<div id="list-content" class="list-content clearfix">
				<table id="sumtable" class="table table-bordered table-assign table-hover" style="display:none">
				
				</table>
				<div class="errorM" style="display:none"></div>
			
			</div>
		</div>

	</div>
<script>
function assginTaskView() {
	$('#list-content').addClass('loading').find('table').html('');
	$.getJSON('../admin/projectBaseOpt_getAdminList.action?id=<%=project%>',
		function(data) {
			if (data.Result == 'OK') {
				var html = '<thead><tr><th style="vertical-align:middle;width:20%">工作单位</th><th style="vertical-align:middle;width:10%">姓名</th><th class="uneditable" style="vertical-align:middle;width:5%">性别</th>';
				html += '<th class="uneditable" style="vertical-align:middle;width:10%">工作部门</th><th class="uneditable" style="vertical-align:middle;width:10%">工作职务</th><th class="uneditable" style="vertical-align:middle;width:10%">办公电话</th><th class="uneditable" style="vertical-align:middle;width:10%">手机</th>';	
				html +='</thead><tbody>';
				if(data.Records.length > 0) {
					$.each(data.Records,function(i,v){
						$.each(v.admin,function(i1,v1){
							if(i1 == 0){
								html += '<tr><td data-editable="no" class="uneditable"  style="vertical-align:middle" rowspan="'+ v.admin.length +'"><b>'+ v.organization +'</b></td>';
							}else{
								html += '<tr>';
							}
							html += '<td data-editable="no" class="jhnum" style="background:#fcf8e3">' + v1.name + '</td>';
							html += '<td data-editable="no" class="jhnum" style="background:#fcf8e3">' + v1.sex + '</td>';
							html += '<td data-editable="no" class="jhnum" style="background:#fcf8e3">' + v1.department + '</td>';
							html += '<td data-editable="no" class="jhnum" style="background:#fcf8e3">' + v1.jobDuty + '</td>';
							html += '<td data-editable="no" class="jhnum" style="background:#fcf8e3">' + v1.phone + '</td>';
							html += '<td data-editable="no" class="jhnum" style="background:#fcf8e3">' + v1.mobile + '</td>';
							html +='</tr>';
						})
					})
				}
				
				html +='</tbody>';
				
				$('#sumtable').html(html).show();
				$('#list-content').removeClass('loading');
				$('.errorM').hide();
				
				$('#sumtable').stickyTableHeaders('destroy');
				$('#sumtable').stickyTableHeaders();
				
				$('.jhnum,.sjnum').tooltip({container:'body'})
			} else {
				$('.errorM').html('<p style="text-align:center;font-size:18px;height:150px;line-height:150px;">暂无数据</p>').show();
				
			}
		});
}

assginTaskView('#reprtTask');
	
</script>
<body>
</html>
