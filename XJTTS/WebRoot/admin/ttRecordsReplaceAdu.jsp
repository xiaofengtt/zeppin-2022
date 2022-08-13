<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">审核替换学员</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
<link rel="stylesheet" href="../css/colorbox.css">
<script src="../js/iframe.js"></script>
<script src="../js/jsrender.min.js"></script>
<script src="../js/jquery.colorbox.js"></script>
	<style>
	a{cursor:pointer;}
	.stateDiv label{width:120px;}
	.list-5-05{width:33% !important;}
	</style>
	
<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>
<style>
.select2-container .select2-choice{width:auto;height:28px;line-height:28px;border:none;}
.select2-container{padding:0;}
.select2-container .select2-choice > .select2-chosen{line-height:34px;}
</style>

<script src="../js/iframe.js"></script>
<div class="main">
<div class="listwrap">
		<div class="list_bar">审核替换学员</div>
		<div class="iconDiv">
			<a class="btn btnMyself btn-screen">
				<span><img src="../img/sousuo.png" alt="icon">
					<b>筛选</b>
				</span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角">
					<b>筛选学员替换记录</b>
				</p>
			</a>
		</div>
	<div class="listwrap">
		<div class="list-content clearfix" >
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			
			<div class="tablewrap">
					<form id="aduReplace" class="form-horizontal" action="../admin/ttRecord_outputReplaceTeacher.action" method="post"
					role="form" style="display:none;padding-top:15px;">
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
								
								<div class="form-group">
									<label class="col-sm-2 control-label" for="">姓名/身份证号</label>
									<div class="col-sm-9">
										<input class="form-control" name="teacherName" type="text">
									</div>
								</div>
							</div>

							<div class="col-md-6">
								<div class="form-group">
									<label class="col-sm-2 control-label" for="">项目名称</label>
									<div class="col-sm-9">
										<select class="form-control" name="projectName" id="projectName"
											onchange="changeProject(this)">
											<option value="0" search="全部">全部</option>
											<s:iterator value="searchReportTask" id="rt">
												<option value='<s:property value="#rt.getId()" />' search='<s:property value="#rt.getName()" />'><s:property
														value="#rt.getName()" /></option>
											</s:iterator>
										</select>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label" for="">承训单位</label>
									<div class="col-sm-9">
										<select class="form-control" id="trainingUnit"
											name="trainingUnit">
											<option value="0">全部</option>
										</select>
									</div>
								</div>
							</div>
							
						</div>
						
						<div class="row actionbar">
							
						</div>
						<div class="text-center" style="padding-bottom:10px">
								<button class="btn btn-primary btn-myself" id="findRecord" type="button">查询</button>
								<s:if test="organizationLevel == 1">
									<button class="btn btn-default btn-myself" id="outputRecord" type="submit">导出</button>
								</s:if>
							</div>
					</form>
					<div class="stateDiv" style="margin-top:15px;">
						<label>学员审核状态：</label>
						<a class="light" value="-1">全部<span id="span1">(0)</span></a>
						<a class="" value="1">未审核<span id="span2">(0)</span></a>
						<a class="" value="2">已通过<span id="span3">(0)</span></a>
						
						<a class="" value="0">未通过<span id="span4">(0)</span></a>
					</div>
				<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
				<div id="list-item-cnt" style="margin-top:15px;"></div>
				<div id="ProjectTableContainer"></div>
				<script id="ttRecordTpl" type="text/x-jsrender">
					<div id="{{:id}}" class="list-item">
						<div class="list-item-hd">
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td width="40px"><span class="text-primary">ID：</span>{{:id}}</td>
										<td width="auto" class="text-primary"<span>
										{{:trainingCollege}}-{{:project}}的“{{:trainingSubject}}”学员替换记录</span></td>
										<td width="80px" class="text-center">
											{{if organizationLevel == '1'}}
											<a data-fancybox-type="iframe" class="btn-create cboxElement" href="../admin/ttRecord_reviewReplaceTeacherInit.action?id={{:id}}">审核</a>
											{{/if}}
										</td> 
									</tr>
								</tbody>
							</table>
						</div>

						<div class="list-item-bd clearfix" style="position: relative">
							<div class="list-item-col list-5-05">
								<ul>
									<li><span class="text-primary">发起人：</span>{{:creator}} </li>
									<li><span class="text-primary">发起时间：</span>{{:createtime}} </li>
									<li><span class="text-primary">申请替换原因：</span> {{:replaceReason}}</li>
								</ul>
							</div>
							<div class="list-item-col list-5-05">
								<ul>
									<li><span class="text-primary">原报送学员：</span>{{:name}}</li>
									<li><span class="text-primary">原报送学员身份证号：</span> {{:idCard}}</li>
								</ul>
							</div>
							<div class="list-item-col list-5-05">
								<ul>
									<li><span class="text-primary">替换学员：</span> {{:reName}}</li>
									<li><span class="text-primary">替换学员身份证号：</span> {{:reIdCard}}</li>
									{{if reStatus==0}}
										<li><span class="text-primary">未通过原因：</span> {{:nopassReason}}</li>
									{{/if}}
								</ul>
							</div>
							
							<div class="statusDiv chuo{{:reStatus}}"></div>
						</div>
					</div>
				</script>
				<div id="pagination" style="float:right;margin-right:10px" class="pull-right"></div>
				<script type="text/javascript">
				var page = (url('?page') != null) ? url('?page') : '1'; 
				$(function(){
					$(".btn-create").colorbox({
						iframe : true,
						width : "865px",
						height : "750px",
						maxWidth : '1600px',
						opacity : '0.5',
						overlayClose : false,
						escKey : true
					});
					$(".stateDiv a").each(function(index, val) {
						if ($(this).attr("value") == projectStatus) {
							$(this).addClass("light").siblings().removeClass("light");
						}
					});
					if(projectStatus == null){
						projectStatus = $(".stateDiv a").attr("value");
					}
					ttrecordAdu("#aduReplace");
				});
				var projectStatus = (url('?status')!= null) ? url('?status') : '-1';
				var status = (url('?status')!= null) ? url('?status') : '-1';
				function ttrecordAdu(o,pp){
					var pp = (url('?page')!= null) ? url('?page') : '1';
					var projectId = (url('?projectId')!= null) ? url('?projectId') : '0';
					var subjectId =(url('?subjectId')!= null) ? url('?subjectId') : '0';
					var tcId =(url('?tcId')!= null) ? url('?tcId') : '0';
					var teacherName =(url('?teacherName')!= null) ?decodeURI(url('?teacherName')) : '';
					var str = 'projectId='+projectId +'&subjectId='+subjectId+'&tcId='+tcId+ '&page=' + pp+'&status='+projectStatus + '&teacherName='+teacherName;
					$('#list-item-cnt').html('<p style="font-size:20px;text-align:center;height:100px;line-height:100px;">数据加载中....</p>');
					$.getJSON('../admin/ttRecord_getReplaceTeacherList.action?' + str,
							function(data) {
								if (data.Result == 'OK') {
									var template = $.templates('#ttRecordTpl');
									var html = template.render(data.Records);
									$('#list-item-cnt').html(html);
									$(".btn-create").colorbox({
										iframe : true,
										width : "865px",
										height : "750px",
										maxWidth : '1600px',
										opacity : '0.5',
										overlayClose : false,
										escKey : true
									});
									var totlePage;
									if(data.TotalRecordCount==0){
										totlePage=1;
									}else{
										totlePage=Math.ceil(data.TotalRecordCount/10);
									}
									var options = {
											currentPage : page,
											totalPages : totlePage,
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
												window.location = updateURLParameter(url(), 'page', page);
												//ttrecordAdu('#aduReplace', pp);
											}
										}
										$('#pagination').bootstrapPaginator(options);
								}else {
									$('#list-item-cnt').html('<p style="height:100px;text-align:center;line-height:100px;font-size:20px;">'
											+ data.message + '</p>').show();
									$('#pagination').html('');
									
								}
					})
				}
					
					$(".stateDiv a").click(
							function(e) {
								projectStatus = $(this).attr("value");
								/* ttrecordAdu('#aduReplace'); */
								$(this).addClass("light").siblings().removeClass(
								"light");
								var projectId = $('select[name="projectName"]').val();
								var subjectId =$('#subjectName').val();
								var tcId = $('#trainingUnit').val();
								var year = $('select[name="year"]').val();
								var teacherName = encodeURI($('input[name="teacherName"]').val());
								var str = 'projectId='+projectId +'&subjectId='+subjectId+'&tcId='+tcId+'&status='+projectStatus+'&year='+year+'&teacherName='+teacherName;
								window.location.href = "../admin/ttRecord_initReplaceAdu.action?"+str;
							});
					//搜索
			        $('#findRecord').click(function (e) {
			        	var projectId = $('select[name="projectName"]').val();
						var subjectId =$('#subjectName').val();
						var tcId = $('#trainingUnit').val();
						var year = $('select[name="year"]').val();
						var teacherName = encodeURI($('input[name="teacherName"]').val());
						var str = 'projectId='+projectId +'&subjectId='+subjectId+'&tcId='+tcId+'&status='+projectStatus+'&year='+year+'&teacherName='+teacherName;
						window.location.href = "../admin/ttRecord_initReplaceAdu.action?"+str;
						return false;
			        });
				</script>
				
				
			</div>
		</div>
	</div>
	</div>
</div>
<div class="recordlist left">
	<div class="arrow"></div>
	<div class="bd" id="recordlistcnt"></div>
</div>

<script>
$(".btn-screen").click(function(e){
	e.preventDefault();
	$('#aduReplace').toggle();
	if($('#aduReplace').css("display")=="block"){
		$("div.jtable-main-container table.jtable").css("margin-top","220px");
	}else{
		$("div.jtable-main-container table.jtable").css("margin-top","60px");
	}
})
	function adupass() {
		var obj = $('#adupass');
		var cUrl = obj.attr('data-url');
		$.getJSON(cUrl, function(ret) {
			if (ret.Result == 'OK') {
	//				infotip(obj, ret.Message);
	//				$('#adu_'+obj.attr('data-id')).remove();
				alert('成功,' + ret.Message);
				$('#ProjectTableContainer').jtable('load');
			} else {
				alert('失败,' + ret.Message);
			}
		})
		return false;
	}
	
	function adunopass() {
		var obj = $('#adunopass');
		var cUrl = obj.attr('data-url');
		$.getJSON(cUrl, function(ret) {
			if (ret.Result == 'OK') {
	//				infotip(obj, ret.Message);
	//				$('#adu_'+obj.attr('data-id')).remove();
				alert('成功,' + ret.Message);
				$('#ProjectTableContainer').jtable('load');
			} else {
				alert('失败,' + ret.Message);
			}
		})
		return false;
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
						cLis += '<option value="' +c.id + '">' + c.name
								+ '</option>';
					});
					$('#trainingUnit').html(cLis);
				} else {
					$('#trainingUnit').html(
							'<option value="0">暂无数据</option>');
				}

			});
	}
	var projectId = (url('?projectId')!= null) ? url('?projectId') : '0';
	var subjectId =(url('?subjectId')!= null) ? url('?subjectId') : '0';
	var tcId =(url('?tcId')!= null) ? url('?tcId') : '0';
	var year =(url('?year')!= null) ? url('?year') : '0';
	var teacherName =(url('?teacherName')!= null) ? decodeURI(url('?teacherName')) : '';
	$(function(){
		$("select[name='year']").val(year);
		$("#projectName").val(projectId);
		$("#subjectName").val(subjectId);
		$("#trainingUnit").val(tcId);
		$("input[name='teacherName']").val(teacherName);
		$.getJSON('../admin/ttRecord_getReplaceTeacherStatus.action?', {
			projectId : projectId,
			subjectId : subjectId,
			tcId : tcId,
			teacherName : teacherName
		}, function(r) {
			$("#span1").html("(" +r.totalCount+ ")");
			$("#span2").html("(" +r.noCheck+ ")");
			$("#span3").html("(" +r.checkPass+ ")");
			$("#span4").html("(" +r.noComplete+ ")");
		});
		
		$('#projectName').select2(); 
	});
</script>

<jsp:include page="foot.jsp"></jsp:include>