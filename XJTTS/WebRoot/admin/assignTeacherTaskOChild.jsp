<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">其他培训项目名额分配</s:param>
</s:action>
<!-- <link rel="stylesheet" href="../css/bootstrap2.css"> -->
<link rel="stylesheet" href="../css/iframe.css">
<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>
<style>
.select2-container .select2-choice{width:auto;height:28px;line-height:28px;border:none;}
.select2-container{padding:0;}
.select2-container .select2-choice > .select2-chosen{line-height:34px;}
</style>

<div class="main" style="">
	<div class="listwrap">
		<div class="list_bar">名额分配管理</div>
		<div class="iconDiv">
			<a class="btn btnMyself btn-screen">
				<span><img src="../img/sousuo.png" alt="icon">
					<b>筛选</b>
				</span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角">
					<b>筛选名额分配任务</b>
				</p>
			</a>
		</div>
		<div  class="list-content clearfix">
<!-- 		<a class="btn btn-primary btn-screen"> -->
<!-- 				筛选名额分配任务 </a> -->
		<form id="reprtTask" class="form-horizontal" role="form" action="../admin/assignTeacherTask_initPageChild.action" style="padding-top:15px;display:none;margin:0;">
		<input type="hidden" id="leveltype" name="leveltype" value="" />
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
						<select class="form-control" id="subjectName" name="subjectName" >
							<s:iterator value="lstteacTeachingSubjectExs" id="e">
							<option value="<s:property value="#e.id" />" search="<s:property value="#e.searchString" />"
								<s:if test="%{#e.id==0}" >selected</s:if>>
								<s:property value="#e.name" />
							</option>
							</s:iterator>
						</select>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="form-group">
					<label class="col-sm-2 control-label" for="">项目名称</label>
					<div class="col-sm-9">
						<select class="form-control" name="projectName" id="projectName" onchange="changeProject(this)">
							<option value="0" search="全部">全部</option>
							<s:iterator value="searchReportTask" id="rt">
								<option value='<s:property value="#rt.getId()" />' search='<s:property value="#rt.getName()" />' <s:if test="#rt.getId()==selectProjectId"> selected </s:if> ><s:property
										value="#rt.getName()" /></option>
							</s:iterator>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="">是否自主报名</label>
					<div class="col-sm-9">
						<select class="form-control" name="enrollType">
							<option value="0">全部</option>
							<option value="1"<s:if test="selectEnrollType==1"> selected </s:if>>计划报名</option>
							<option value="2"<s:if test="selectEnrollType==2"> selected </s:if>>自主报名</option>
						</select>
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
		</div>
		<div class="cui-menu2">
			<s:if test="organizationLevel==1">

				<a style="display:none" href="../admin/assignTeacherTaskOpt_initPage.action"
					data-fancybox-type="iframe" class="btn btn-primary btn-create">
					+ 添加分配任务 </a>

			</s:if>
		</div>
		<div class="sorting clearfix">
			<div class="checkall-btn">
				<input onchange="toCheckAll(this)" type="checkbox" name="checkall">
			</div>
			<div class="order-option">排序 :</div>

			<ul class="sorting-btns">
				<li id="sorting-timeup"><a
					href="../admin/assignTeacherTask_initPage.action?sort=timeup-asc"
					data-name="timeup"><span>截止时间</span></a></li>
			<%-- 	<li id="sorting-name" class=""><a
					href="../admin/assignTeacherTask_initPage.action?sort=name-asc"
					data-name="name"><span>名称</span></a></li> --%>
				<li id="sorting-status" class=""><a
					href="../admin/assignTeacherTask_initPage.action?sort=status-asc"
					data-name="status"><span>任务状态</span></a></li>
				<li id="sorting-subject" class=""><a
					href="../admin/assignTeacherTask_initPage.action?sort=subject-asc"
					data-name="subject"><span>学科</span></a></li>
				<li id="sorting-creator" class=""><a
					href="../admin/assignTeacherTask_initPage.action?sort=creator-asc"
					data-name="creator"><span>创建人</span></a></li>
				<li id="sorting-creattime" class=""><a
					href="../admin/assignTeacherTask_initPage.action?sort=creattime-asc"
					data-name="creattime"><span>创建时间</span></a></li>

			</ul>

		</div>


		<div id="list-content" class="list-content clearfix">
 

			<s:iterator value="projectApplyHash" id="pros">
				<div id="list_<s:property value="key" />" class="list-item">
					<div class="list-item-hd">
						<table class="table table-bordered">
							<tbody>
								<tr>
									<td style="width:20px;"><input class="listcheck"
										onchange="toCheck(this)" type="checkbox" name="traininfo"
										value=""> <span style="display:none">ID:<s:property value="key" /></span></td>
									<td width="auto"><span class="text-primary list-title">项目名称：</span>
										<s:property value="value[0]" /></td>
									<td width="280px"><span class="text-primary">学科：</span><s:property value="value[1]" /></td>
							        <td width="130px"><span class="text-primary">报送状态：</span><s:property value="value[11]" /></td>  
									<!--	<td width="130px"><span class="text-primary">任务状态：</span><s:property value="value[6]" /></td> -->	
									<td width="25%"><span class="text-primary">
									<a class="ifrmbox" data-fancybox-type="iframe"
												href="../admin/assignTeacherTaskOpt_viewPage.action?key=<s:property value="key" />">查看报送情况</a>
										&nbsp;
										<s:if test="value[10]==2">
											<!-- 管理员添加者 -->
											
												&nbsp;&nbsp;
											<a class="ifrmbox" data-fancybox-type="iframe" 
												href="../admin/assignTeacherTaskOpt_editPage.action?key=<s:property value="key" />">分配报送任务</a>
												&nbsp;&nbsp;
											<a class="ifrmbox" data-fancybox-type="iframe" 
												href="../admin/projectBaseOpt_authorityPage.action?id=<s:property value="key" />">查看下级权限</a>
											<%-- <a class="delbtn" data-id="<s:property value="key" />" 
												data-url="../admin/assignTeacherTask_delteTeacherTask.action" href="javascript:void(0)">删除</a>	 --%>											
										</s:if> 
										<s:elseif test="value[10]==3">
											<!-- 下级部门管理者 -->
											&nbsp;&nbsp;
											<a class="ifrmbox" data-fancybox-type="iframe"
												href="../admin/assignTeacherTaskOpt_editPage.action?key=<s:property value="key" />">分配报送任务</a>
											&nbsp;&nbsp;
											<a class="bigifrmbox" data-fancybox-type="iframe" 
												href="../admin/projectBaseOpt_authorityPage.action?id=<s:property value="key" />">查看下级权限</a>
										</s:elseif> 
										<s:else>
											<!-- 学校管理者 -->
										</s:else>
									</td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="list-item-bd clearfix">
						<div class="list-item-col list-5-05" style="width:60px">
							<div class="num-square" data-org= "<s:property value="value[3]" />-<s:property value="value[2]" />-<s:property value="value[5]" />">
								
							</div>
						</div>
						
						<div class="list-item-col list-5-1">
							<ul>
								<li><span class="text-primary">任务计划报送人数：</span> <span class="num-grn num-plan"><s:property
										value="value[2]" /></span></li>
								<li><span class="text-primary">所辖区域上报人数：</span> <span class="num-grn num-real"><s:property
										value="value[3]" /></span></li>
							</ul>
						</div>
						<div class="list-item-col list-5-08">
							<ul>
								<li><span class="text-primary">已审核人数：</span> <span class="num-grn"><s:property
										value="value[4]" /></span></li>
								<li><span class="text-primary">审核未通过人数：</span> <span class="num-red"><s:property
										value="value[5]" /></span></li>
							</ul>
						</div>
						<div class="list-item-col list-5-08" >
							<ul>
								<li><span class="text-primary">任务状态：</span> 
							  	 	<s:if test="value[6]=='可报送'"> 
									   <font color="#4cae4c"><s:property value="value[6]" /></font>
									</s:if>
									<s:else>
								        <font color="#ff2900"><s:property value="value[6]" /></font>
									</s:else>
									
								</li>
								<li><span class="text-primary">报送截止时间：</span> <s:property
										value="value[7]" /></li>
							</ul>
						</div>
						<div class="list-item-col">
							<ul>
								<li><span class="text-primary">任务发起人：</span> <s:property
										value="value[8]" /></li>
								<li><span class="text-primary">任务创建时间：</span> <s:property
										value="value[9]" /></li>
							</ul>
						</div>

					</div>
				</div>
			</s:iterator>
		</div>

		<div id="pagination" style="float:right;" class="pull-right"></div>

	</div>

</div>
<script>

$(".btn-screen").click(function(e){
	e.preventDefault();
	$('#reprtTask').toggle();
})
	function getUrlParam(name) { 
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r != null) return unescape(r[2]); return null; 
	} 
	function changeProject(t) {
		var vid = $(t).val();
		$.getJSON(
			'../admin/ttRecord_getAssignTaskSubject.action?projectId='
					+ vid, function(data) {
				if (data.subjects.length > 0) {
					var cLis = '';
					$.each(data.subjects, function(i, c) {
						cLis += '<option value="' +c.id + '" ' + 'search="'+ c.search +'">' + c.name	+ '</option>';
					});
					$('#subjectName').html(cLis);
				} else {
					$('#subjectName').html('<option value="0">暂无数据</option>');
				}
			});
	}

	function changeYear(t){
		var year = $(t).val();
		$.getJSON('../admin/ttRecord_getAssignTaskProjects.action?year=' + year, function(data){
			if (data.projects.length > 0) {
				var cLis = '';
				$.each(data.projects, function(i, c) {
					cLis += '<option value="' +c.id + '" search="'+c.name+'">' + c.name	+ '</option>';
				});
				$('#projectName').html(cLis);
			} else {
				$('#projectName').html('<option value="0">暂无数据</option>');
			}
			$('#subjectName').html('<option value="0">全部</option>');
		})
	}
	
	
	$(function() {//添加按钮
		$(".btn-create,.ifrmbox").colorbox({
			iframe : true,
			width : "90%",
			height : "95%",
			maxWidth : '1600px',
			opacity : '0.5',
			overlayClose : false,
			escKey : true
		});
		
		
		//计算未报人数
		$('.num-square').each(function() {
			var t = $(this),
				_ = t.attr('data-org').split('-');
			r = parseInt(_[0]) - parseInt(_[1]) -parseInt(_[2]);
			if(r > 0 || r == 0) {
				t.addClass('label-danger')
			}
			t.html(r);	
		})
		var vid = $('[name="projectName"]').val();
		var sid = getUrlParam('subjectName');
		$.getJSON(
			'../admin/ttRecord_getAssignTaskSubject.action?projectId='
					+ vid, function(data) {
				
				if (data.subjects.length > 0) {
					var cLis = '';
					$.each(data.subjects, function(i, c) {
						if(c.id==sid){
							cLis += '<option value="' +c.id + '" ' + 'search="'+ c.search + '" selected>' + c.name	+ '</option>';
						}else{
							cLis += '<option value="' +c.id + '" ' + 'search="'+ c.search +'">' + c.name	+ '</option>';
						}
					});
					$('#subjectName').html(cLis);
				} else {
					$('#subjectName').html('<option value="0">暂无数据</option>');
				}
				$('#subjectName').select2();
			});
		$('#projectName').select2();
	})

	var page = url('?page');
	$.getJSON('../admin/assignTeacherTask_getPageJson.action?', {
		page : page
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
		var sort = (url('?sort')) ? url('?sort') : 'timeup-asc';
		var _ = sort.split('-');
		$('#sorting-' + _[0]).addClass('crt');
		$('#sorting-' + _[0]).find('span').addClass(_[1]);

		var leveltype = (url('?leveltype')) ? url('?leveltype') : '2';
		$('#leveltype').val(leveltype);
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