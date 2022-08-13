<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">学员报送管理</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
<script src="../js/iframe.js"></script>
<script src="../js/jsrender.min.js"></script>


<div class="main">

	<div class="listwrap">
		<div class="list_bar">报送任务详细子任务</div>
		<div class="list-content clearfix" style="padding-top:15px;">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form id="reprtTask" class="form-horizontal" role="form">
				<div class="row clearfix">
					<div class="col-md-6">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">年份</label>
							<div class="col-sm-9">
								<select class="form-control" name="year" onchange="changeYear(this)">
									<option value="0">全部</option>
									<s:iterator value="searchYear" id="yt">
										<option value='<s:property value="#yt" />' <s:if test="#yt==selectYear"> selected </s:if> ><s:property
												value="#yt" /></option>
									</s:iterator>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">培训科目</label>
							<div class="col-sm-9">
								<select id="subjectName" class="form-control" name="subjectName">
									<option value="0">全部</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">项目名称</label>
							<div class="col-sm-9">
								<select class="form-control" name="projectName" id="projectName" onchange="changeProject(this)">
									<option value="0">全部</option>
									<s:iterator value="searchReportTask" id="rt">
										<option value='<s:property value="#rt.getId()" />'><s:property
												value="#rt.getName()" /></option>
									</s:iterator>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="row actionbar">
					<div class="text-center" style="padding-bottom:10px">
						<button class="btn btn-primary" id="findRecord" type="submit">查询</button>
					</div>
				</div>

			</form>

			<div id="list-content" class="list-content clearfix"></div>

			<script id="ttRecordTaskTpl" type="text/x-jsrender">
					<div id="{{:key}}" class="list-item" class="success">
						<div class="list-item-hd">
							<table class="table table-bordered">
								<tbody>
									<tr>
										<!--td width="80px"><span>ID:{{:id}}</span></td-->
										<td style="min-width:220px;" width="auto"><span class="text-primary">项目名称：</span><span >{{:projectName}}</span></td>
										<td width="180"><span class="text-primary">培训科目：</span><span >{{:subjectName}}</span></td>
										<td width="230"><span class="text-primary">承训单位：</span><span >{{:trainingName}}</span></td>
										<td width="160"><span class="text-primary">：</span><span >{{:organizationName}}</span></td>
										<td width="320">
										{{if status==1 && timeup_status==1}}
											<a href="../admin/ttRecord_initReportPage.action?projectId={{:projectId}}&subjectId={{:subjectId}}&collegeId={{:tcid}}&gorganizationId={{:gorganizationId}}&&porganizationId={{:porganizationId}}" data-status={{:status}} data-timestatus="{{:timeup_status}}">添加报送学员</a>&nbsp;&nbsp;&nbsp;
										{{else}}
											<a href="javascript:void(0)" onclick="showMessage({{:status}},{{:timeup_status}})">添加报送学员</a>&nbsp;&nbsp;&nbsp;
										{{/if}}
											<a  href="../admin/ttRecord_initAduPage.action?key={{:key}}" >查看已报送学员</a>
										</td>
									</tr>
								</tbody>
							</table>
						</div>

						<div class="list-item-bd clearfix">
							
							<div class="list-item-col list-5-05" style="width:70px">
								<div class="num-square" data-org="{{:totalNum}}-{{:teacherNum}}-{{:noPass}}">
									
								</div>
							</div>
							<div class="list-item-col list-5-1">
								<ul>
									<li><span class="text-primary">任务计划报送人数：</span><span class="num-grn">{{:teacherNum}}</span></li>
									<li><span class="text-primary">所辖区域上报人数：</span><span class="num-grn">{{:totalNum}}</span></li>
								</ul>
							</div>
							<div class="list-item-col list-5-08" >
								<ul>
									<li><span class="text-primary">审核已通过人数：</span> <span class="num-grn">{{:passNum}}</span></li>
									<li><span class="text-primary">审核未通过人数：</span> <span class="num-red">{{:noPass}}</span></li>
								</ul>
							</div>
							<div class="list-item-col list-5-08">
								<ul>
									<li><span class="text-primary">报送截止时间：</span> {{:timeup}}</li>
									<li><span class="text-primary">报送任务状态：</span> 
									{{if status==1 && timeup_status==1}}
									    <font color="#4cae4c">{{:status_name}}</font>
									{{else}}  
									    {{if status==1 && timeup_status==0}}
                                           已过截止日期
                  						{{else}} 
										   {{:status_name}}
                                        {{/if}}
										
									{{/if}}
									</li>
								</ul>
							</div>
							<div class="list-item-col">
								<ul>
							<!--	<li><span class="text-primary">任务创建时间：</span> {{:creattime}}</li> -->
                                    <li><span class="text-primary">开班时间：</span> {{:training_starttime}}</li>
									<li><span class="text-primary">任务发起人：</span> {{:creator}}</li>
								</ul>
							</div>
						</div>
					</div>
					
				</script>
			<div id="pagination" style="float:right;" class="pull-right"></div>

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
			$('#subjectName').html('<option value="0">全部<option>');
		})
	}

	$(function() {

		function ttrecordAdu(o, pp) {
			var pp = (typeof (pp) == 'undefined') ? 1 : pp;
			var str = $(o).serialize() + '&page=' + pp;
			$('#list-content').html('').addClass('loading');
			$.getJSON('../admin/ttRecord_searchReportAssPage.action?' + str,
				function(data) {
					if (data.status == 'OK') {
						var template = $.templates('#ttRecordTaskTpl');
						var html = template.render(data.records);
						
						$('#list-content').html(html).removeClass('loading');

						//弹出层
						/*$(".ifrmbox").colorbox({
							iframe : true,
							width : "90%",
							height : "95%",
							opacity : '0.5',
							escKey : true,
							overlayClose : false,
							onClosed:function(){ttrecordAdu('#reprtTask', pp); }
						});*/
						
						//分页
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
								ttrecordAdu('#reprtTask', pp);
								$(window).scrollTop();
							}
						}
						$('#pagination').bootstrapPaginator(options);
		
						//计算未报人数
						$('.num-square').each(function() {
							var t = $(this),
								_ = t.attr('data-org').split('-'),
								r = parseInt(_[0]) - parseInt(_[1]) - parseInt(_[2]);
								if(r > 0 || r == 0) {
									t.addClass('label-danger')
								}
							t.html(r);	
						})
					} else {
						$('#list-content').html('未搜索到数据').removeClass('loading');
					}
				})
		}
		//初始化
		ttrecordAdu('#reprtTask');
		//查询
		$('#reprtTask').submit(function() {
			ttrecordAdu(this);
			return false;
		});

	})
	
	
	//添加报送学员按钮 判断是否可以报送
	function showMessage (s,t) {
		if(s == 2 && t == 1){
			alert('抱歉。管理员已停止该项目的报送');
			return;
		}
		if(s == 0) {
			alert('任务已报送');
			return;
		}
		if(t == 0 ) alert('抱歉该项目已过报送截止日期！')
	}
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
					$('#subjectName').html('<option value="0">暂无数据<option>');
				}
				
				if (data.trainingUnits.length > 0) {
					var cLis = '';
					$.each(data.trainingUnits, function(i, c) {
						cLis += '<option value="' +c.id + '">' + c.name+ '</option>';
					});
					$('#trainingUnit').html(cLis);
				} else {
					$('#trainingUnit').html('<option value="0">暂无数据<option>');
				}

			});
	}
</script>
<body>
</html>