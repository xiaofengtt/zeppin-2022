<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName"></s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">

<script src="../js/iframe.js"></script>
<script src="../js/jsrender.min.js"></script>
<div class="main">

	<div class="listwrap">
		<div class="list_bar">学员登陆码列表</div>
		<div class="list-content clearfix" style="padding-top:15px;">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<div><button class="btn btn-primary fliter-btn" id="" type="button">筛选登陆码</button></div>
			<form id="aduTaskTeacher" class="form-horizontal"
				style="display:none" action="../paper/paper_outputUserList.action">
				<div class="row clearfix">
					<div class="col-md-6">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">项目名称</label>
							<div class="col-sm-6">
								<select class="form-control" name="projectName"
									onchange="changeProject(this)">
									<option value="0">全部</option>
									<s:iterator value="searchReportTask" id="rt">
										<option value='<s:property value="#rt.getId()" />'><s:property
												value="#rt.getName()" /></option>
									</s:iterator>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">承训单位</label>
							<div class="col-sm-6">
								<select class="form-control" id="trainingUnit" name="trainingUnit">
									<option value="0">全部</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">身份证号</label>
							<div class="col-sm-6">
								<input name="idcard"  class="form-control" >
							</div>
						</div>
					</div>

					<div class="col-md-6">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">培训科目</label>
							<div class="col-sm-6">
								<select id="subjectName" class="form-control" name="subjectName">
									<option value="0">全部</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">所在班级</label>
							<div class="col-sm-6">
								<select class="form-control" id="classes" name="classes">
									<option value="0">全部</option>
									<option value="1">一班</option>
									<option value="2">二班</option>
									<option value="3">三班</option>
									<option value="4">四班</option>
									<option value="5">五班</option>
									<option value="6">六班</option>
									<option value="7">七班</option>
									<option value="8">八班</option>
									<option value="9">九班</option>
									<option value="10">十班</option>									
								</select>
							</div>
						</div>
					</div>
				</div>

				<div class="row actionbar">
					<div class="text-center">
						<button class="btn btn-primary" id="findRecord" type="button">查询</button>
					
						<button class="btn btn-primary" id="outputRecord" type="submit">导出登陆码列表</button>
					</div>
				</div>
			</form>

			

			<div class="clearfix">
				<div class="cui-menu2 clearfix" style="margin-left:9px">
					<style>
						.pull-left {background:none !important;}
					</style>
					
				</div>
				
				<table class="table  table-hover">
					<thead>
						<tr>
							<th>登陆码</th>
							<th>项目</th>
							<th>承训机构</th>
							<th>培训科目</th>
							<th>身份证</th>
							<th>姓名</th>
							<th>手机号</th>						
						</tr>
					</thead>
					<tbody id="list-content">
						
					</tbody>
				</table>
				
				<script id="ttRecordAduTpl" type="text/x-jsrender">
					<tr>
						<td>{{:loginKey}}</td>
						<td>{{:projectName}}</td>
						<td>{{:trainingName}}</td>
						<td>{{:subjectName}}</td>
						<td>{{:idcard}}</td>
						<td>{{:name}}</td>
						<td>{{:mobile}}</td>
					</tr>
				</script>
				

				<div id="pagination" style="float:right;" class="pull-right"></div>

			</div>
		</div>

	</div>
</div>
<div class="recordlist left">
	<div class="arrow"></div>
	<div class="bd" id="recordlistcnt"></div>
</div>

<script>
	$(function() {
		$(document).on("click", function(e) {
			if (!$(e.target).parents().is('.recordlist'))
				$('.recordlist').hide();
		});
		function ttrecordAdu(o, pp) {
			var pp = (typeof (pp) == 'undefined') ? 1 : pp;
			var str = $(o).serialize() + '&page=' + pp;
			$('#list-content').html('').addClass('loading');
			$.getJSON('../paper/paper_searchUserList.action?' + str,
					function(data) {
						if (data.status == 'OK') {
							var template = $.templates('#ttRecordAduTpl');
							var html = template.render(data.records);
							$('#list-content').html(html).removeClass('loading');;
							var options = {
								currentPage : data.page.currentPage,
								totalPages : data.page.totalPage,
								shouldShowPage:function(type, page, current){
					                switch(type)
					                {
					                    default:
					                        return true;
					                }
					            },
								onPageClicked : function(e, originalEvent,
										type, page) {
									var pp = page;
									ttrecordAdu('#aduTaskTeacher', pp);
								}

							}
							$('#pagination').bootstrapPaginator(options);
							//筛选
							$(o).slideUp('slow',function(){
								$('.fliter-btn').show();
							})
						} else {
							
							$('#list-content').html('<tr><td align="center" colspan="4">'+data.message+'</td></tr>').removeClass('loading');
							$('#pagination').html('');
						}
					});
		}

		ttrecordAdu('#aduTaskTeacher');

		$('#findRecord').click(function(e) {
			e.preventDefault();
			ttrecordAdu('#aduTaskTeacher');
			return false;
		});
		
		$('.fliter-btn').click(function(e){
			e.preventDefault();
			$(this).hide();
			$('#aduTaskTeacher').slideToggle('slow');
			
		})

	});

	function changeProject(t) {
		var vid = $(t).val();
		$.getJSON(
				'../admin/ttRecord_getAssignTaskOrganization.action?projectId='
						+ vid, function(data) {
					
					if (data.subjects.length > 0) {
						var cLis = '';
						$.each(data.subjects, function(i, c) {
							cLis += '<option value="' +c.id + '">' + c.name	+ '</option>';
						});
						$('#subjectName').html(cLis);
					} else {
						$('#subjectName').html('<option value="0">暂无数据</option>');
					}
					
					if (data.trainingUnits.length > 0) {
						var cLis = '';
						$.each(data.trainingUnits, function(i, c) {
							cLis += '<option value="' +c.id + '">' + c.name+ '</option>';
						});
						$('#trainingUnit').html(cLis);
					} else {
						$('#trainingUnit').html('<option value="0">暂无数据</option>');
					}

				});
	}
</script>

<body>
</html>