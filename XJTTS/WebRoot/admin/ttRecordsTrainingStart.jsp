<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">查看开班时间</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
<link href="../css/select2.css" rel="stylesheet" />
<link href="../css/datepicker3.css" rel="stylesheet">

<script src="../js/bootstrap-datepicker.js"></script>
<script src="../js/bootstrap-datepicker.zh-CN.js"></script>
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>
<style>
.select2-container .select2-choice{width:auto;height:28px;line-height:28px;border:none;}
.select2-container{padding:0;}
.select2-container .select2-choice > .select2-chosen{line-height:34px;}
</style>

<script src="../js/iframe.js"></script>
<script src="../js/jsrender.min.js"></script>


<div class="main">

	<div class="listwrap">
		<div class="list_bar">查看开班时间</div>
		<div class="iconDiv">
			<a class="btn btnMyself btn-screen">
				<span><img src="../img/sousuo.png" alt="icon">
					<b>筛选</b>
				</span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角">
					<b>筛选报送任务</b>
				</p>
			</a>

		</div>
		<div class="list-content clearfix" style="">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form id="reprtTask" class="form-horizontal" role="form" style="padding-top:15px;display:none;">
				<div class="row clearfix">
					<div class="col-md-6">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">开班时间</label>
							<div class="col-sm-9">
								<input type="text" readonly data-provide="datepicker" class="datepicker" id="starttime" name="starttime">
							</div>
						</div>
					</div>
				</div>
				<div class="row actionbar">
					<div class="text-center" style="padding-bottom:10px">
						<button class="btn btn-primary btn-myself" id="findRecord" type="submit">查询</button>
					</div>
				</div>

			</form>

			<div id="list-content" class="list-content clearfix" style="padding-top:15px;"></div>

			<script id="ttRecordTaskTpl" type="text/x-jsrender">
					<div id="{{:key}}" class="list-item" class="success">
						<div class="list-item-hd">
							<table class="table table-bordered">
								<tbody>
									<tr>
										<!--td width="80px"><span>ID:{{:id}}</span></td-->
										<td style="min-width:220px;" width="30%"><span class="text-primary">项目名称：</span><span >{{:projectName}}{{if enrollType==2}}<font color="red">(自主报名)</font>{{/if}}</span></td>
										<td width="15%"><span class="text-primary">培训科目：</span><span >{{:subjectName}}</span></td>
										<td width="20%"><span class="text-primary">承训单位：</span><span >{{:trainingName}}</span></td>
										<td width="20%" class="text-center">
										{{if enrollType==2}}
											<a  href="../admin/ttRecord_initReviewPage.action?key={{:key}}">查看已报送学员</a>
										{{else}}
											<a  href="../admin/ttRecord_initAduPage.action?key={{:key}}">查看已报送学员</a>
										{{/if}}
										</td>
									</tr>
								</tbody>
							</table>
						</div>

						<div class="list-item-bd clearfix">
							
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
                                    <li><span class="text-primary">开班时间：</span> {{:training_starttime}}</li>
								</ul>
							</div>
							<div class="list-item-col">
								<ul>
                                    <li><span class="text-primary">结束时间：</span> {{:training_endtime}}</li>
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
	$(".btn-screen").click(function(e){
		e.preventDefault();
		$('#reprtTask').toggle();
	})

	$(function() {
		$('.datepicker').datepicker({
			language : "zh-CN",
			format : 'yyyy-mm-dd',
			autoclose : true
		})
		
		function ttrecordAdu(o, pp) {
			var pp = (typeof (pp) == 'undefined') ? 1 : pp;
			var str = $(o).serialize() + '&page=' + pp;
			$('#list-content').html('').addClass('loading');
			$.getJSON('../admin/ttRecord_searchTrainingStartPage.action?' + str,
				function(data) {
					if (data.status == 'OK') {
						var template = $.templates('#ttRecordTaskTpl');
						var html = template.render(data.records);
						
						$('#list-content').html(html).removeClass('loading');

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
</script>
<body>
</html>