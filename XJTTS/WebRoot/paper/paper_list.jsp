<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">学评教问卷</s:param>
</s:action>

<link href="../css/iframe.css" rel="stylesheet" type="text/css">
	<style>
	.ufc .uul {
		bottom: auto
	}
	.col-sm-2{width:16.66666667%;float:left;}
	.col-sm-5{width:41.66666667%;float:left;}
	.form-horizontal .control-label{text-align:right;}
	</style>
<script src="../js/paper.js"></script>
<script type="text/javascript">
	//改变状态
	function change_status(id, s) {
		$.get('../paper/paper_changeStatus.action?id=' + id + '&s=' + s
				+ '&t=3', function(r) {
			if (r.status) {
				window.location = window.location.href;
			} else {
				alert(r.message);
			}
		})
	}

	
</script>

<div class="main">
<div class="iconDiv">
			<a class="btn btnMyself btn-screen" href="../paper/paper_add?type=3" target="_blank">
				<span><img src="../img/kaishexiangmu.png" alt="icon">
					<b>设计</b>
				</span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角">
					<b>设计新问卷</b>
				</p>
			</a>
			<div class="btn-group" style="margin-top:-1px;">
			<a class="btn btnMyself"  onclick="showaduRecord(this)">
				<span><img src="../img/fuzhiwenjuan.png" alt="icon">
					<b>复制</b>
				</span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角">
					<b>复制问卷</b>
				</p>
			</a>
			<div class="popover bottom" style="width:140px;margin-left:-55px;top:29px;">
			   		<div class="arrow"></div>
			      
			      	<div class="popover-content">
			        	<div><input type="text" name="paperid" class="form-control" placeholder="请输入试卷ID"></div>
						<div style="padding:10px 0 0;border-top:1px solid #e5e5e5">
							<button type="button" onclick="$(this).closest('.popover').hide()" class="btn btn-default">关闭</button>
							<button type="button" onclick="copy(this)"
								data-url="../paper/paper_copy?type=3" class="btn btn-primary">复制</button>
						
						</div>
			    	</div>
			    </div>
			</div>
			<a class="btn btnMyself" href="../paper/paper_getReportUserList.action">
				<span><img src="../img/denglumaliebiao.png" alt="icon">
					<b>登录码</b>
				</span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角">
					<b>登录码列表</b>
				</p>
			</a>	

		</div>
	<div class="listwrap">
		<div class="list_bar">我的问卷</div>
		<div class="cui-menu2" style="height:30px">
<!-- 			<a href="../paper/paper_add?type=3" target="_blank" class="btn btn-primary btn-create"> + 设计新问卷 </a>  -->
<!-- 			<div class="btn-group" style="margin-top:-1px;"> -->
<!-- 				<a href="javascript:void(0)" class="btn btn-primary btn-create" onclick="showaduRecord(this)">复制问卷 </a> -->
<!-- 				<div class="popover bottom" style="width:140px;margin-left:-30px;top:20px;"> -->
<!-- 			   		<div class="arrow"></div> -->
			      
<!-- 			      	<div class="popover-content"> -->
<!-- 			        	<p><input type="text" name="paperid" class="form-control" placeholder="请输入试卷ID"></p> -->
<!-- 						<div style="padding:10px 0 0;border-top:1px solid #e5e5e5"> -->
<!-- 							<button type="button" onclick="$(this).closest('.popover').hide()" class="btn btn-default">关闭</button> -->
<!-- 							<button type="button" onclick="copy(this)" -->
<!-- 								data-url="../paper/paper_copy?type=3" class="btn btn-primary">复制</button> -->
						
<!-- 						</div> -->
<!-- 			    	</div> -->
<!-- 			    </div> -->
<!-- 		    </div> -->
<!-- 			<a href="../paper/paper_getReportUserList.action" class="btn btn-primary btn-search"> <b>登录码列表</b></a> -->
			<form id="reprtTask" class="form-horizontal" role="form" action="../paper/paper_list.action"  style="position:absolute;width:80%;display:inline;max-width:700px;">
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
						<div class="col-sm-5">
							<select class="form-control" name="projectName" id="projectName">
								<option value="0">全部</option>
								<s:iterator value="searchReportTask" id="rt">
									<option value='<s:property value="#rt.getId()" />' <s:if test="#rt.getId()==selectProjectId"> selected </s:if> ><s:property
											value="#rt.getName()" /></option>
								</s:iterator>
							</select>
						</div>
						<input type="hidden" name="type" value="3">
						<button class="btn btn-primary btn-myself" id="findRecord" type="submit">查询</button>
					</div>
				</div>
			</div>
			</form>
		</div>
		<div id="stuff" class="list-content clearfix">
			<s:iterator value="learnList" id="learn">
				<div id="" class="list-item">
					<div class="list-item-hd">
						<table class="table table-bordered">
							<tbody>
								<tr>
									<td class="text-primary" width="100">ID: <s:property
											value="id" /></td>
									<td width="auto"><a target="_blank"
										href="../paper/paper_view.action?type=3&id=<s:property
											value="id" />&t=x123"><s:property
												value="title" /></a></td>

									<td width="400px">
										<div id="projecttypecnt_<s:property value="id" />"
											class="clearfix"
											style="<s:if test="%{status == 2 }">display:none;</s:if>">
											<div class="ufcwrap" id="resCon">
												<div class="ufc">
													<span class="usel"
														onclick="showul($(this));getul(this,'3','<s:property value="id" />')"><strong>请选择项目...</strong></span>
													<ul class="uul"></ul>
												</div>

											</div>

										</div>
									</td>

									<td width="80px" style="text-align:center;"
										class="text-success text-center"><s:if
											test="%{status == 1}">
											草稿
										</s:if> <s:if test="%{status == 2}">
											运行中
										</s:if> <s:if test="%{status == 3}">
											停止
										</s:if></td>
								</tr>
							</tbody>
						</table>
					</div>

					<div class="list-item-bd clearfix">

						<div class="list-item-col list-5-05" style="width:70px">
							<div class="num-square label-danger">
								<s:property value="submits.size()" />
							</div>
						</div>

						<div class="list-item-col list-5-1 paper-btns" style="width:50%">
							<ul>
								<li><s:if test="%{status != 2 && submits.size()==0}">
										<a class="view" target="_blank" title="设计问卷" editversion="0"
											hasvalue="" value="<s:property value="id"/>"
											href="../paper/paper_edit?type=3&curid=<s:property value="id"/>">
											<span class="spanLeftTxt">设计问卷</span>
										</a>
									</s:if> <s:else>
										<a class="view" onclick="alert('试卷在运行中或者已经提交过不能进行编辑！')"
											title="试卷在运行中或者已经提交过不能进行编辑！" href="javascript:void(0)"><span
											class="spanLeftTxt">设计问卷</span></a>
									</s:else></li>
								<li><s:if
										test="%{projectsForEvaluationTrainingPsq.size()>0}">
										<a
											href="../paper/report_getReportPaper.action?paperid=<s:property value="id"/>"
											id="ctl01_ContentPlaceHolder1_grvwActivity_ctl02_hrefReport"
											class="report" title="答卷统计分析"> <span class="spanLeftTxt">分析&amp;下载</span></a>
									</s:if> <s:else>
										<a onclick="alert('没有关联项目，无法统计！')"
											id="ctl01_ContentPlaceHolder1_grvwActivity_ctl02_hrefReport"
											class="report" title="答卷统计分析"> <span class="spanLeftTxt">分析&amp;下载</span></a>
									</s:else></li>
							</ul>
							<ul>

								<li><s:if test="%{status == 1}">
										<a onclick="return confirm('状态设为“发布”后将不能修改试卷，是否继续？');"
											id="ctl01_ContentPlaceHolder1_grvwActivity_ctl02_btnChangeStatus"
											title="此问卷正在草稿状态，点击发布问卷" class="run link-444"
											href='javascript:change_status(<s:property value="id"/>,2)'>发布</a>
									</s:if> <s:if test="%{status == 2}">
										<a onclick="return confirm('状态设为“停止”后将不能投票，是否继续？');"
											id="ctl01_ContentPlaceHolder1_grvwActivity_ctl02_btnChangeStatus"
											title="此问卷正在草稿状态，点击发布问卷" class="pause link-444"
											href='javascript:change_status(<s:property value="id"/>,3)'>停止</a>
									</s:if> <s:if test="%{status == 3 }">
										<a onclick="return confirm('状态设为“发布”后将不能修改试卷，是否继续？');"
											id="ctl01_ContentPlaceHolder1_grvwActivity_ctl02_btnChangeStatus"
											title="此问卷正在草稿状态，点击发布问卷" class="run link-444"
											href="javascript:change_status(<s:property value='id'/>,2)">发布</a>
									</s:if></li>
								<li><a onclick="return confirm('试卷删除后后将不能恢复，是否继续？');"
									id="ctl01_ContentPlaceHolder1_grvwActivity_ctl02_btnChangeStatus"
									title="此试卷删除后将不可恢复！" class="delete link-444"
									href="javascript:change_status(<s:property value="id"/>,0)">删除</a></li>
							</ul>
						</div>
						<div class="list-item-col " style="width:40%">

							<div id="resmtr_<s:property value="id" />" class="promtc">
								<s:iterator value="projectsForEvaluationTrainingPsq"
									id="project">
									<div class="mtc">
										<s:property value="name" />
										<span
											onclick="delP(this,'<s:property value="id" />','3','<s:property value="#learn.getId()" />')"
											class="uclose">&times;</span>
									</div>
								</s:iterator>
							</div>


						</div>
						
					</div>
				</div>
			</s:iterator>
		</div>

		<div id="pagination" style="float:right;" class="pagination">
			<ul>
				<li><a title="Go to first page">首页</a></li>
				<li><a title="Go to previous page">&lt;&lt;</a></li>
				<li class="active"><a title="Current page is 1">1</a></li>
				<li><a title="Go to next page">&gt;&gt;</a></li>
				<li><a title="Go to last page">末页</a></li>
			</ul>
		</div>

	</div>

</div>

<script type="text/javascript">
function showaduRecord(obj) {
	$(obj).closest('.main').find('.popover').show();
}

function copy(t) {
	$(t).closest('.popover').hide();
	var obj = $(t);
	var cUrl = obj.attr('data-url');
	var popover=obj.closest('.popover'),paperid = popover.find('input[name="paperid"]').val();
	var keyarr = [];
	window.open(cUrl+'&curid='+paperid,'_blank') ;
	return false;
}

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
			$('#projectName').html('<option value="0">暂无数据</option>');
		}
	})
}

	var page = url('?page');
	var type = url('?type');
	var year = url('?year');
	var projectId = url('?projectId');
	$.getJSON('../paper/paper_getPageJson.action?', {
		page : page,
		type : type,
		year : year,
		projectId : projectId
	}, function(r) {
		var options = {
			currentPage : r.currentPage,
			totalPages : r.totalPage,
			shouldShowPage : function(type, page, current) {
				switch (type) {
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
</script>

<jsp:include page="../admin/foot.jsp"></jsp:include>