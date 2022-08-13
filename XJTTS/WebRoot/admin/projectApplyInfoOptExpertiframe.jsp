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

<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>

<div class="main">

	<div class="listwrap">
		<div class="list_bar">添加评审专家</div>
		<div class="list-content clearfix" style="padding-top:15px;">	
			<div class="alert alert-danger" style="display:none;margin:0 15px 15px;"></div>
			<div class="fliter-btn-wrap" style="display:none">
				<a href="../admin/projectApply_initPage.action" class="btn btn-primary">&larr;返回项目申报结果监审</a>
				<button class="btn btn-primary fliter-btn"  type="button">查找评审专家</button>
			</div>
			<form id="projectApplyExpert" class="form-horizontal" role="form">
				<div class="row clearfix">
					<div class="col-md-6">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">项目年份</label>
							<div class="col-sm-9">
								<input readonly class="form-control" type="text" id="" autocomplete="off" name="year"
									value="<s:property	value="year"  />" />
							</div>
						</div>	
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">项目名称</label>
							<div class="col-sm-9">
								<input readonly class="form-control" type="text" id="" autocomplete="off" name="project"
									value="<s:property	value="project"  />" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">评审专家姓名</label>
							<div class="col-sm-9">
								<input  class="form-control" type="text" id="" name="expertName" />
							</div>
						</div>
					</div>
					<div class="col-md-6">	
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">学科</label>
							<div class="col-sm-9">
								<input readonly class="form-control" type="text" id="" autocomplete="off" name="trainingsubject"
									value="<s:property	value="trainingSubject"  />"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">承训单位</label>
							<div class="col-sm-9">
								<input readonly class="form-control" type="text" id="" autocomplete="off" name="trainingcollege"
									value="<s:property	value="trainingCollege"  />"/>
							</div>
						</div>
					</div>			
					<input type="hidden" name="evaluateType"	value="<s:property	value="evaluateType"  />" />	
				</div>
				<div class="row actionbar">
						<div style="text-align:center">
						<button class="btn btn-primary btn-myself" id="findRecord" type="submit">查询</button>
					</div>
				</div>
			</form>
		</div>
		<div id="list-item-cnt" style="margin-top:10px;"></div>
		<div id="pagination" style="float:right;margin-right:10px" class="pull-right"></div>
		<script id="ttRecordTpl" type="text/x-jsrender">
			<div id="{{:id}}" class="list-item">
				<div class="list-item-hd">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td width="29%" class="text-primary">ID：<span>{{:id}}</span></td>
								<td width="29%" class="text-primary">姓名：<span>{{:name}}</span></td>
								<td width="29%" class="text-primary">手机：<span>{{:phone}}</span></td>
								<td width="180px"><span id="status_{{:id}}">
									{{if record_status}}
									<a onclick="addExpert(this)" data-id="{{:id}}" data-url="../admin/projectApplyOpt_addProjectApplyExpert.action?id={{:id}}&paid=<s:property value="id" />&evaluateType=<s:property value="evaluateType" />" href="javascript:void(0)">添加评审权限</a>
									{{else}}
									<a onclick="deleteExpert(this)" data-id="{{:id}}" data-url="../admin/projectApplyOpt_deleteProjectApplyExpert.action?id={{:id}}&paid=<s:property value="id" />&evaluateType=<s:property value="evaluateType" />" href="javascript:void(0)">删除评审权限</a>
									{{/if}}
									</span>
								</td>
							</tr>
						</tbody>
					</table>
				</div>

				<div class="list-item-bd clearfix">
		
		
					<div class="list-item-col list-5-05" style="width:25%;">
						<ul>
							<li><span class="text-primary">工作单位：</span> {{:organization}}</li>
						</ul>
					</div>
					<div class="list-item-col list-5-05" style="width:25%;">
						<ul>
							<li><span class="text-primary">工作职位：</span> {{:jobtitle}}</li>
						</ul>
					</div>
					<div class="list-item-col list-5-1" style="width:25%;">
						<ul>
							<li><span class="text-primary">研究专长：</span>{{:research}} </li>
						</ul>
					</div>
					<div class="list-item-col list-5-1" style="width:25%;">
						<ul>
							<li><span class="text-primary">教学专长：</span>{{:teach}} </li>
						</ul>
					</div>
				</div>
			</div>
		</script>
	</div>
</div>
<script>
$(function() {
	var recordReport = function(o, pp) {
		var pp = (typeof (pp) == 'undefined') ? 1 : pp;
		var paid = url('?id');
		var str = $(o).serialize() + '&paid=' + paid+'&page=' + pp;
		$('#list-item-cnt').html('<p style="font-size:20px;text-align:center;height:100px;line-height:100px;">数据加载中....</p>');
		$.getJSON('../admin/projectApplyOpt_getProjectApplyExpert.action?' + str,
				function(data) {
					if (data.status == 'OK') {
						var template = $.templates('#ttRecordTpl');
						var html = template.render(data.records);
						$('#list-item-cnt').html(html);
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
								recordReport('#projectApplyExpert', pp);
							}
						}
						$('#pagination').bootstrapPaginator(options);
						
						$(o).slideUp('slow',function(){
							$('.fliter-btn-wrap').show();
						})
						$('.alert-danger').hide();
					} else {
						$('.alert-danger').html(data.message).show();
						$('#list-item-cnt,#pagination').html('');
						
					}
		}).fail(function(){
			$('#list-item-cnt').html('系统错误请稍后重试');
		});
	}
	$('#projectApplyExpert').submit(function() {
		recordReport(this);
		return false;
	});
	
	$('.fliter-btn').click(function(e){
		e.preventDefault();
		$('.fliter-btn-wrap').hide();
		$('#projectApplyExpert').slideDown('slow');
		
	})

	var sort = (url('?sort')) ? url('?sort') : 'year-asc';
	var _ = sort.split('-');
	$('#sorting-' + _[0]).addClass('crt');
	$('#sorting-' + _[0]).find('span').addClass(_[1]);

	$('.sorting-btns a').click(function() {
		var name = $(this).attr('data-name');
		sortasc(name);
		return false;
	})


	function movieFormatResult(Options) {
		var html = Options.DisplayText;
		return html;

	}
	function movieFormatSelection(Options) {
		return Options.DisplayText;
	}

})
	</script>
<body>
</html>
