<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">项目申报结果监审</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">

<script src="../js/iframe.js"></script>
<script src="../js/jsrender.min.js"></script>
<script src="../js/underscore-min.js"></script>
<div class="main">
	<div class="listwrap">
		<div class="list_bar">项目申报信息管理</div>
		<div class="cui-menu2" style="position:relative">
			<a href="../admin/projectApplyOpt_initPage.action"
				data-fancybox-type="iframe" class="btn btn-primary btn-create">
				+ 补录项目中标结果 </a> 
				
				
		<form id="reprtTask" class="form-horizontal" role="form" action="../admin/projectApply_initPage.action" style="position:absolute;top:2px;right:10px;width:70%">
		<div class="row clearfix">
			<div class="">
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
						<select class="form-control" name="projectName" id="projectName">
							<option value="0">全部</option>
							<s:iterator value="searchReportTask" id="rt">
								<option value='<s:property value="#rt.getId()" />' <s:if test="#rt.getId()==selectProjectId"> selected </s:if> ><s:property
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
		
		<div class="sorting clearfix">
			<div class="checkall-btn">
				<input onchange="toCheckAll(this)" type="checkbox" name="checkall">
			</div>
			<div class="order-option">排序 :</div>

			<ul class="sorting-btns">
				<li id="sorting-year0"><a
					href="../admin/projectApply_initPage.action?sort=year0-asc"
					data-name="year0"><span>年份</span></a></li>
				<li id="sorting-name0" class=""><a
					href="../admin/projectApply_initPage.action?sort=name0-asc"
					data-name="name0"><span>名称</span></a></li>
				<li id="sorting-status" class=""><a
					href="../admin/projectApply_initPage.action?sort=status-asc"
					data-name="status"><span>状态</span></a></li>
				<li id="sorting-creattime" class=""><a
					href="../admin/projectApply_initPage.action?sort=creattime-asc"
					data-name="creattime"><span>创建时间</span></a></li>

			</ul>

		</div>


		<div id="list-content" class="list-content clearfix">


			<s:iterator value="projectApplyHash" id="pros">
				<div id="<s:property value="key" />" class="list-item">
					<div class="list-item-hd">
						<table class="table table-bordered">
							<tbody>
								<tr>
									<td width="140px"><input class="listcheck"
										onchange="toCheck(this)" type="checkbox" name="traininfo"
										value=""> <span class="text-primary">ID：</span><s:property value="key" /></td>
									<td width="auto" ><span class="text-primary">名称：</span><span><s:property
												value="value[0]" /><s:if test="value[21]==2" ><font color=red>(自主报名)</font></s:if></span></td>
									<td width="130px"><span class="text-primary">学科：</span><s:property value="value[1]" /></td>
									<td width="180px"><span class="text-primary">申报单位：</span><s:property value="value[2]" /></td>
									<td width="220px">
									<a href="../admin/projectApplyOpt_initExpertPage.action?id=<s:property value="key" />" >添加评审专家</a>&nbsp;&nbsp;
									<a  href="../admin/projectApplyOpt_initPage.action?id=<s:property value="key" />" data-fancybox-type="iframe"  class="ifrmbox">编辑</a>&nbsp;&nbsp;
									<a class="ProjectApplyDelete"  href="javascript:void(0)" data-url="../admin/projectApplyOpt_delete.action?id=<s:property value="key" />">删除</a>&nbsp;&nbsp; 
									<a href="../admin/projectApplyOpt_initPassPage.action?id=<s:property value="key" />" data-fancybox-type="iframe"  class="ifrmbox">审核</a></td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="list-item-bd clearfix">
						<div class="list-item-col list-5-2">
							<ul>
								<li><span class="text-primary">项目年份：</span> <s:property
										value="value[3]" /></li>
								<li><span class="text-primary">项目类型：</span> 
									<div onmouseout="this.className='longtxt2'" onmouseover="this.className='longtxt2 longtxt2_hover'" class="longtxt2">
										<s:property value="value[4]" />
									</div>
								
								</li>
								<li><span class="text-primary">所属地区：</span> <s:property
										value="value[5]" /></li>
								<li><span class="text-primary">培训开始时间：</span> <s:property
										value="value[7]" /></li>
								<li><span class="text-primary">培训结束时间：</span> <s:property
										value="value[8]" /></li>
							</ul>
						</div>
						<div class="list-item-col list-5-08">
							<ul>
								<s:if test="value[21]==2">
								<li><span class="text-primary">报名截止日期：</span> <s:property
										value="value[22]" /></li>
								</s:if>
								<li><span class="text-primary">培训课时：</span> <s:property
										value="value[9]" /></li>
								<li><span class="text-primary">培训人数：</span> <s:property
										value="value[10]" /></li>
								<li><span class="text-primary">培训形式：</span> <s:property
										value="value[6]" /></li>

							</ul>
						</div>
						<div class="list-item-col list-5-08">
							<ul>
								<li><span class="text-primary">提交申报：</span> <s:property
										value="value[11]" />
										
										<s:if test='%{value[20]!=null}'>
										<a href="..<s:property value="value[20]" />">申报书下载</a>
										</s:if>
										</li>
								<li><span class="text-primary">联系人：</span> <s:property
										value="value[12]" /></li>
								<li><span class="text-primary">联系电话：</span> <s:property
										value="value[13]" /></li>
								<li><span class="text-primary">申报时间：</span> <div onmouseout="this.className='longtxt'" onmouseover="this.className='longtxt longtxt_hover'" class="longtxt"><s:property
										value="value[14]" /></div></li>

							</ul>
						</div>
						<div class="list-item-col list-5-08">
							<ul>
								<li><span class="text-primary">专家评分：</span> <s:property
										value="value[15]" />
										<a href="javascript:void(0)" onclick="scorelist(this)"
											data-url="../admin/projectApply_getExpertViewList.action?paid=<s:property value="key" />">详细</a>
								</li>
								<li><span class="text-primary">申报状态：</span> <s:property
										value="value[16]" /></li>
								<li><span class="text-primary">审核人：</span> <s:property
										value="value[17]" /></li>
								<li><span class="text-primary">审核时间：</span> <div onmouseout="this.className='longtxt'" onmouseover="this.className='longtxt longtxt_hover'" class="longtxt"><s:property
										value="value[18]" /></div></li>

							</ul>
						</div>

					</div>
				</div>
			</s:iterator>
		</div>

		<div id="pagination" style="float:right;" class="pull-right"></div>
		
		
		


	</div>

</div>
<div class="scorelist left">
	<div class="arrow"></div>
	<div class="bd" id="scorelistcnt"></div>
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

	$(function() {//添加按钮
		$(".btn-create,.ifrmbox").colorbox({
			iframe : true,
			width : "860px",
			height : "600px",
			opacity : '0.5',
			overlayClose : false,
			escKey : true
		});
		$(document).on("click", function(e) {
			if (!$(e.target).parents().is('.scorelist'))
				$('.scorelist').hide();
		});
	
	
		$('.ProjectApplyDelete').click(function(){
			var url = $(this).attr('data-url');
			if(confirm('你确定要删除吗？')) {
				$.get(url,function(r) {
					if(r.Result == 'OK') {
						window.location.href = window.location.href;
					}else {
						alert(r.Message);
					}
				})
			}
			
		});
		
		
	})

	var page = url('?page');
	var projectName = url('?projectName');
	$.getJSON('../admin/projectApply_getPageJson.action?', {
		page : page,
		projectName : projectName
	}, function(r) {
		var options = {
			currentPage : r.currentPage,
			totalPages : r.totalPage,
			shouldShowPage:function(type, page, current){
                switch(type)
                {
                   default:
                      return true;
                }
            },
			onPageClicked : function(e, originalEvent, type, page) {
				window.location = updateURLParameter(url(), 'page', page);
			}

		}
		$('#pagination').bootstrapPaginator(options);
	})

	$(function() {
		var sort = (url('?sort')) ? url('?sort') : 'year-asc';
		var _ = sort.split('-');
		$('#sorting-' + _[0]).addClass('crt');
		$('#sorting-' + _[0]).find('span').addClass(_[1]);

	})

	$('.sorting-btns a').click(function() {
		var name = $(this).attr('data-name');
		sortasc(name);
		return false;
	})

	function sortasc(name) {
		var order = (url('?sort').split('-')[1] == 'asc') ? 'desc' : 'asc';
		window.location = url('protocol') + '://' + url('hostname')
				+ url('path') + '?sort=' + name + '-' + order;
	}
</script>


<jsp:include page="foot.jsp"></jsp:include>