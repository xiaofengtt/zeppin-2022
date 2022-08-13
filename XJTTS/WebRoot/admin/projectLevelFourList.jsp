<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">学员成绩管理</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">

<script src="../js/iframe.js"></script>
<script src="../js/jsrender.min.js"></script>
<script src="../js/underscore-min.js"></script>
<link rel="stylesheet" href="../css/ui-dialog.css">
<script src="../js/dialog-min.js"></script>
<script type="text/javascript" src="../js/layer-v2.2/layer/layer.js"></script>	
<div class="main">

	<div class="listwrap">
		<div class="list_bar">学员成绩管理</div>
		<div class="iconDiv">
<!-- 			<a class="btn btnMyself btn-screen" onclick="importPage('../admin/ttResult_uploadInit.action')"> -->
<%-- 				<span><img src="../img/daoru.png" alt="icon"> --%>
<!-- 					<b style="color:#ff6f39;">导入</b> -->
<%-- 				</span> --%>
<!-- 				<p> -->
<!-- 					<img src="../img/lanse.png" alt="蓝色三角"> -->
<!-- 					<b>导入培训记录</b> -->
<!-- 				</p> -->
<!-- 			</a> -->
			<a class="btn btnMyself btn-screen fliter-btn">
				<span><img src="../img/sousuo.png" alt="icon">
					<b>筛选</b>
				</span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角">
					<b>筛选学员</b>
				</p>
			</a>
			<a class="btn btnMyself" id="more-fliter-btn" onclick="moreFliterHelp();">
				<span><img src="../img/xiaozhushou.png" alt="icon">
					<b>小助手</b>
				</span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角">
					<b>学员审查小助手</b>
				</p>
			</a>

		</div>
		<div class="list-content clearfix">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<div class="ttRecordsAduBtn">
			</div>
			<div class="fliters-result-container clearfix">
				<span class="title">学员审查搜索条件：</span><span class="fliters-result"></span><a
					onclick="$('.fliters-result-container').hide();$('.more-filters-container').show();"
					style="padding:4px 0;margin:0 5px" href="javascript:void(0)">编辑</a>
			</div>
			<div class="more-filters-container clearfix">
				<div class="more-fliters-btn">
					<a class="apply-filters btn btn-default btn-sm"
						onclick="Adufilters();" href="javascript:void(0)">筛选</a> <a
						class="clear-filters btn btn-default btn-sm"
						href="javascript:void(0)" onclick="clearAdufilters();"
						style="display: inline-block;">清空</a>
				</div>

				<div class="filters-group">
					<h4 class="feature-name">教龄</h4>
					<ol class="feature-group">
						<li><input type="checkbox" name="teachAge" value="0-0">
							<label for="">0年</label></li>
						<li><input type="checkbox" name="teachAge" value="1-5">
							<label for="">1-5年</label></li>
						<li><input type="checkbox" name="teachAge" value="6-10">
							<label for="">6-10年</label></li>
						<li><input type="checkbox" name="teachAge" value="11-20">
							<label for="feature-id-black">11-20年</label></li>
					</ol>
				</div>
				<div class="filters-group">
					<h4 class="feature-name">年龄</h4>
					<ol class="feature-group">
						<li><input type="checkbox" name="age" value="21-30">
							<label for="feature-id-black">21-30岁</label></li>
						<li><input type="checkbox" name="age" value="31-40">
							<label for="feature-id-black">31-40岁</label></li>
						<li><input type="checkbox" name="age" value="41-50">
							<label for="feature-id-black">41-50岁</label></li>
					</ol>
				</div>
				<div class="filters-group">
					<h4 class="feature-name">职称</h4>
					<ol class="feature-group">
						<s:iterator value="lstJobTitles" id="jobTitle">
							<li><input type="checkbox" name="jobTitle"
								value="<s:property value="name" />"> <label
								for="jobTitle"><s:property value="name" /></label></li>
						</s:iterator>
					</ol>
				</div>
				<div class="filters-group">
					<h4 class="feature-name">职务</h4>
					<ol class="feature-group">
						<s:iterator value="lstJobDuties" id="jobTitle">

							<li><input type="checkbox" name="jobDuty"
								value="<s:property value="name" />"> <label
								for="jobDuty"><s:property value="name" /></label></li>

						</s:iterator>
					</ol>
				</div>
				<div class="filters-group wide-filters-group">
					<h4 class="feature-name">汉语言水平</h4>
					<ol class="feature-group">

						<s:iterator value="lstChineseLanguageLevels" id="jobTitle">
							<li><input type="checkbox" name="chineseLanguageLevel"
								value="<s:property value="name" />"> <label
								for="chineseLanguageLevel"><s:property value="name" /></label></li>
						</s:iterator>

					</ol>
				</div>



			</div>
			<form id="aduTaskTeacher" class="form-horizontal"
				style="display:none" action="../admin/ttResult_outputScore.action">
				<input type="hidden" value="1" name="enrollType" />
				<input type="hidden" value="1" name="projectCycle" />
				<div class="row clearfix">
					<div class="col-md-6">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">项目名称</label>
							<div class="col-sm-9">
								<select class="form-control" name="projectName"
									onchange="changeProject(this)">
									<option value="0">全部</option>
									<s:iterator value="searchProject" id="rt">
										<option value='<s:property value="#rt.getId()" />'><s:property
												value="#rt.getName()" /></option>
									</s:iterator>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label" for="">姓名/身份证号</label>
							<div class="col-sm-9">
								<input class="form-control" name="teacherName" type="text">
							</div>
						</div>
<!-- 						<div class="form-group"> -->
<!-- 							<label class="col-sm-2 control-label" for="">报送单位</label> -->
<!-- 							<div class="col-sm-9"> -->
<%-- 								<select class="form-control" id="orgas" name="organizationName"> --%>
<!-- 									<option value="0">全部</option> -->
<%-- 								</select> --%>
<!-- 							</div> -->
<!-- 						</div> -->

<!-- 						<div class="form-group"> -->
<!-- 							<label class="col-sm-2 control-label" for="">承训单位</label> -->
<!-- 							<div class="col-sm-9"> -->
<%-- 								<select class="form-control" id="trainingUnit" --%>
<%-- 									name="trainingUnit"> --%>
<!-- 									<option value="0">全部</option> -->
<%-- 								</select> --%>
<!-- 							</div> -->
<!-- 						</div> -->

					</div>

					<div class="col-md-6">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">培训科目</label>
							<div class="col-sm-9">
								<select id="subjectName" class="form-control" name="subjectName">
									<option value="0">全部</option>
								</select>
							</div>
						</div>
<!-- 						<div class="form-group"> -->
<!-- 							<label class="col-sm-2 control-label" for="">学员审核状态</label> -->
<!-- 							<div class="col-sm-9"> -->
<%-- 								<select class="form-control" id="aduStatus" name="status"> --%>
<!-- 									<option value="-1">全部</option> -->
<!-- 									<option value="2">已审核</option> -->
<!-- 									<option value="1">未审核</option> -->
<!-- 									<option value="-2">通过</option> -->
<!-- 									<option value="0">未通过</option> -->
<%-- 								</select> --%>
<!-- 							</div> -->
<!-- 						</div> -->
						
						
					</div>
				</div>

				<div class="row actionbar">
					<input name="searchKey" type="hidden"
						value="<s:property value="#parameters.key" />">
					<div class="text-center" style="margin-bottom:10px;">				
						<button class="btn btn-default btn-myself" id="outputRecord" type="submit">导出</button>
						<button class="btn btn-primary btn-myself" id="findRecord" type="button">查询</button>
					</div>
				</div>
			</form>
			
			<div class="stateDiv">
				<label style="width:auto;">状态：</label>
				<a class="light" value="-1">全部<span id="span1">(0)</span></a>
				<a class="" value="1">未审核<span id="span3">(0)</span></a>
				<a class="" value="2">已通过<span id="span2">(0)</span></a>
				<a class="" value="0">未通过<span id="span5">(0)</span></a>
				<a class="" value="3">已撤回<span id="span6">(0)</span></a>
			</div>

			<div class="clearfix">
				<div class="cui-menu2 clearfix" style="margin:0;">
<style>
	.pull-left {
		background: none !important;
	}
</style>
					<div class="pull-left">

						<div class="btn-group" style="margin-top:-2px;">
							<ul class="dropdown-menu" role="menu" style="min-width:100px;">
								<li><a href="javascript:void(0)"
									onclick="aduRecordall(this)"
									data-url="../admin/ttResult_aduTeacherReport.action?method=adu">通过</a></li>
								<li><a href="javascript:void(0)" onclick="showadu3Record(this)">不通过</a></li>
							</ul>
							<div class="popover bottom" style="width:160px;margin-left:-70px;top:20px;">
						   		<div class="arrow"></div>
						      
						      	<div class="popover-content">
						        	<p><input type="text" name="allreason" class="form-control" ></p>
									<div style="padding:10px 0 0;border-top:1px solid #e5e5e5">
										<button type="button" onclick="$(this).closest('.popover').hide()" class="btn btn-default">关闭</button>
										<button type="button" onclick="aduRecordall(this)"
											data-url="../admin/ttResult_aduTeacherReport.action?method=noadu" class="btn btn-primary">不通过</button>
									
									</div>
						    	</div>
						    </div>
						</div>
					</div>
				</div>

				<div id="list-content" class="list-content clearfix"></div>

 				<script id="ttRecordAduTpl" type="text/x-jsrender"> 
					<div id="adu_{{:id}}" class="list-item" style="position:relative;">
						<div class="list-item-hd">
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td width="200px" class="text-primary"><input class="listcheck" onclick="toCheck(this)" type="checkbox" name="traininfo" value="{{:id}}-{{:level}}"> &nbsp;<span>{{:schoolName}}—{{:name}}</span>
										{{if teacherStatus==1}}<font color=red>（被替换学员）</font>{{else teacherStatus==2}}<font color=green>（替换学员）</font>{{else}}{{/if}}
										</td>
										<td width="200px"><span class="text-primary">{{:projectName}}—{{:subjectName}}—{{:trainingName}}</span></td>
								
										<td width="110px" align="center"><a href="javascript:void(0)" onclick="audlist(this)"
											data-url="../admin/ttRecord_getTeacherReportInfomation.action?key={{:id}}-{{:level}}">报送记录</a>
											
											<div class="btn-group" style="margin-top:-2px;">
												<button type="button"
													style="font-size:13px;background:none;border:0;color:#4365be;padding:1px 5px 0"
													class="btn btn-xs btn-default dropdown-toggle"
													data-toggle="dropdown">审核 <span class="caret"></span>
												</button>
												<ul class="dropdown-menu" role="menu" style="min-width:100px;">
													<li><a href="javascript:void(0)" onclick="aduRecord(this)" data-id="{{:id}}"
															data-url="../admin/ttResult_aduTeacherReport.action?method=adu&key={{:id}}-{{:level}}">通过
														</a>
													</li>
									
													<li><a href="javascript:void(0)" onclick="showaduRecord(this)">不通过</a></li>
												</ul>
												<div class="popover bottom" style="width:160px;margin-left:-70px;top:20px;">
											      <div class="arrow"></div>
											      
											      <div class="popover-content">
											        <p><input type="text" name="reason" class="form-control" ></p>
													<div style="padding:10px 0 0;border-top:1px solid #e5e5e5">
														<button type="button" onclick="$(this).closest('.popover').hide()" class="btn btn-default">关闭</button>
														<button type="button"  data-id="{{:id}}" onclick="aduRecord2(this)"
															data-url="../admin/ttResult_aduTeacherReport.action?method=noadu&key={{:id}}-{{:level}}" class="btn btn-primary">不通过</button>
														
													</div>
											      </div>
											    </div>
											</div>
											
											 {{if add}}
											 <a href="javascript:void(0)" onclick="delRecord(this)" data-id="{{:id}}"  data-url="../admin/ttResult_aduTeacherReport.action?method=del&key={{:id}}-{{:level}}">撤回</a>
											 {{/if}}
										</td>								

									</tr>
								</tbody>
							</table>
						</div>

						<div class="list-item-bd clearfix">
		
							<div class="list-item-col list-5-05" style="width:10%;text-align:center">
								<img src="../img/u{{:sex}}.png" width="80">
							</div>
		
							<div class="list-item-col list-5-05" style="width:20%">
								<ul>
									<li><span class="text-primary">性别：</span>{{:sexName}}</li>
									<li class="age"><span class="text-primary">年龄：</span> {{:age}}</li>
									<li><span class="text-primary">民族：</span> {{:ethnic}}</li>
									<li><span class="text-primary">政治面貌：</span> {{:politics}}</li>
									<li><span class="text-primary">教师状态：</span> {{:workStatus}}</li>
								</ul>
							</div>
							<div class="list-item-col list-5-05" style="width:20%">
								<ul>
									<li class="teachAge"><span class="text-primary">教龄：</span> {{:teachAge}}</li>
									<li class="jobTitle"><span class="text-primary ">职称：</span> {{:jobTitle}}</li>
									<li class="jobDuty"><span class="text-primary">行政职务：</span> {{:jobDuty}}</li>
									<li><span class="text-primary">最高学历：</span> {{:edubackground}}</li>
									<li><span class="text-primary">编制：</span>{{:authorized}}</li>
								</ul>
							</div>
							<div class="list-item-col list-5-05" style="width:24%">
								<ul>
									<li><span class="text-primary">主要教学学段：</span>{{:mainGrade}} </li>
									<li><span class="text-primary">主要教学学科：</span>{{:mainSubject}} </li>
									<li><span class="text-primary">所属地区：</span><div onmouseout="this.className='longtxt'" onmouseover="this.className='longtxt longtxt_hover'" class="longtxt">{{:area}}</div></li>
									<li><span class="text-primary">主要教学语言：</span>{{:mainLanguage}} </li>
									<li><span class="text-primary">是否双语教学：</span>{{:muLanguage}}</li>									
								</ul>
							</div>
							<div class="list-item-col list-5-05" style="width:23%">
								<ul>
									<li><span class="text-primary">培训状态：</span> {{:trainingStatus}}</li>
									<li><span class="text-primary">培训成绩：</span> {{:score}}</li>
									<li><span class="text-primary">结业证编号：</span> {{:certificate}}</li>
									<li><span class="text-primary">培训不合格原因：</span> {{:reason}}</li>
									<li><span class="text-primary">填报人：</span>{{:creator}}</li>
								</ul>
							</div>

						</div>
						<div  class="levelFour{{:status}} statusDiv"></div>
					</div>
			
			
				</script>



				<div id="pagination" style="float:right;" class="pull-right"></div>

			</div>
		</div>

	</div>
</div>
</div>
<div class="recordlist left">
	<div class="arrow"></div>
	<div class="bd" id="recordlistcnt"></div>
</div>
<div class="hide">
	<input type="hide" name="jobTitle_Json" value=""> <input
		type="hide" name="jobDuty_Json" value=""> <input type="hide"
		name="age_Json" value=""> <input type="hide"
		name="teachAge_Json" value=""> <input type="hide"
		name="chineseLanguageLevel_Json" value="">
</div>

<script>
function importPage(obj){
	var index=layer.open({
	  type: 2,
	  title: false,
	  shadeClose: true,
	  shade: 0.6,
	  scrollbar: false,
	  area: ['70%', '80%'],
	  content: obj 
	}); 
	$(".layui-layer-setwin .layui-layer-close2").css("display", "block");
   	$(".layui-layer-rim").css({"border":"none","border-radius":"0px"});
}
	var jobTitle_Json = [], age_Json = [], teachAge_Json = [], jobDuty_Json = [], chineseLanguageLevel_Json = [];
	var aPage = '';
	
	var projectStatus = url('?status');
	$(function() {
		$(".stateDiv a").each(function(index, val) {
			if ($(this).attr("value") == projectStatus) {
				$(this).addClass("light").siblings().removeClass("light");
			}
		})
		if(projectStatus == null){
			projectStatus = $(".stateDiv a").attr("value");
		}
		$(".stateDiv a").click(
				function(e) {
					$(this).addClass("light").siblings().removeClass("light");
					e.preventDefault();
					projectStatus = $('.stateDiv a.light').attr("value");
// 					window.location.href = "../admin/ttRecord_searchAduPage.action?status=" + status; 
					ttrecordAdu('#aduTaskTeacher');
				})

	})

	
	function ttrecordAdu(o, pp) {
		var pps = (typeof (pp) == 'undefined') ? 1 : pp;
		var strs = $("#aduTaskTeacher").serialize() + '&page=' + pps;
		var enrollTypes = url();
		$('#list-content').html('').addClass('loading');
		$.getJSON('../admin/ttResult_getAduPageStatusCount.action?' + strs,
				function(r) {
					$("#span1").html("(" +r.totalCount+ ")");
					$("#span2").html("(" +r.check+ ")");
					$("#span3").html("(" +r.noCheck+ ")");
					$("#span5").html("(" +r.noPass+ ")");
					$("#span6").html("(" +r.revote+ ")");
				});
		
		
		
		var pp = (typeof (pp) == 'undefined') ? 1 : pp;
		var str = $(o).serialize() + '&page=' + pp+'&status='+projectStatus;
		var enrollType = url();
		$('#list-content').html('').addClass('loading');
		$.getJSON('../admin/ttResult_searchAduPage.action?' + str,
				function(data) {
					if (data.status == 'OK') {
						var template = $.templates('#ttRecordAduTpl');
						var html = template.render(data.records);
						$('#list-content').html(html).removeClass('loading');

						jobTitle_Json.length = 0, jobDuty_Json.length = 0,
								age_Json.length = 0, teachAge_Json.length = 0,
								chineseLanguageLevel_Json.length = 0;
						$.each(data.records, function(i, v) {
							jobTitle_Json.push(v.jobTitle);
							jobDuty_Json.push(v.jobDuty);
							age_Json.push(v.age);
							teachAge_Json.push(v.teachAge);
							chineseLanguageLevel_Json
									.push(v.chineseLanguageLevel);
						});

						Adufilters();//初始化的时候筛选学员
						var options = {
							currentPage : data.page.currentPage,
							totalPages : data.page.totalPage,
							shouldShowPage : function(type, page, current) {
								switch (type) {
								default:
									return true;
								}
							},
							onPageClicked : function(e, originalEvent, type,
									page) {
								var pp = page;
								aPage = page;
								ttrecordAdu('#aduTaskTeacher', pp);
							}

						}
						$('#pagination').bootstrapPaginator(options);
						//筛选
						$(o).slideUp('slow', function() {
							$('.fliter-btn').show();
						})
					} else {
						//$('.alert-danger').html(data.message).show();
						$('#list-content').html(
								'<p style="height:100px;text-align:center;line-height:100px;font-size:20px;">'
										+ data.message + '</p>').removeClass(
								'loading');
						$('#pagination').html('');
					}
				}).done(function(){
					$(".longtxt").css("width",$(".list-item").width()*0.24-90+"px");
					$(".longtxt_hover").css("width",$(".list-item").width()*0.24+150+"px");
					$(window).resize(function(){
						$(".longtxt").css("width",$(".list-item").width()*0.24-90+"px");
						$(".longtxt_hover").css("width",$(".list-item").width()*0.24+150+"px");
					})
				});
	}
	
	
	function showaduRecord(obj) {
		$(obj).closest('.btn-group').find('.popover').show();
	}
	function showadu3Record(obj) {
		$(obj).closest('.btn-group').find('.popover').show();
	}
	function aduRecord2(t) {
		var obj = $(t),cUrl = obj.attr('data-url'),popover=obj.closest('.popover'),reason = popover.find('input[name="reason"]').val();
		$.getJSON(cUrl+'&reason='+reason, function(ret) {
			if (ret.Result == 'OK') {
				$('#status_' + obj.attr('data-id')).text(ret.status);
				infotip(obj, ret.Message);
				
			} else {
				alert('失败,' + ret.Message);
			}
			popover.hide();
		})
		return false;
	}

	$(function() {
		
		
		
// 		$(".stateDiv a").click(function(e){
// 			$(this).addClass("light").siblings().removeClass("light");
// 			e.preventDefault();
// 			ttrecordAdu('#aduTaskTeacher');
// 			return false;
// 		})
		$(document).on("click", function(e) {
			if (!$(e.target).parents().is('.recordlist'))
				$('.recordlist').hide();
		});

		$('.feature-group label').click(function() {
			var ele = $(this).prev();
			if (ele.is(':checked')) {
				ele.prop('checked', false);
			} else {
				ele.prop('checked', true);
			}
		})

		ttrecordAdu('#aduTaskTeacher');

		$('#findRecord').click(function(e) {
			e.preventDefault();
			ttrecordAdu('#aduTaskTeacher');
			return false;
		});
// 		$('#AduTeacherRecord').click(function(e) {//批量审核
// 			e.preventDefault();
// 			var str = $('#aduTaskTeacher').serialize();
// 			var d = dialog({
// 			    title: '提交审核中...',
// 			    content: '请耐心等待'
// 			});
// 			d.showModal();				
// 			$.getJSON('../admin/ttRecord_aduTeachers.action?' + str,function(data){
// 				if (data.status == 'OK') {
// 					ttrecordAdu('#aduTaskTeacher');
					
// 					d.content('<b style="color:green;font-size:16px">' + data.message + '</b>');
// 					setTimeout(function () {
// 			            d.close().remove();
// 			        }, 2000);
// 				}else{
// 					d.content('<b style="color:red;font-size:16px">' + data.message + '</b>');
// 					//alert('失败,'+data.message);
					
// 				}
// 			})
// 			return false;
// 		});

		$('.fliter-btn').click(function(e) {
			e.preventDefault();
			$('#aduTaskTeacher').toggle();
			$("#uploads").toggle();
			$('.more-filters-container').hide();
			$('.fliters-result-container').hide();

		})

	});

	function Adufilters() {
		$('.rb').removeClass('rb');
		var jobTitle = [], age = [], teachAge = [], jobDuty = [], chineseLanguageLevel = [];

		$.each($('input[name=jobTitle]').serializeArray(), function(i, v) {
			jobTitle.push(v.value);
		});
		$.each($('input[name=age]').serializeArray(), function(i, v) {
			age.push(v.value);
		});
		$.each($('input[name=teachAge]').serializeArray(), function(i, v) {
			teachAge.push(v.value);
		});
		$.each($('input[name=jobDuty]').serializeArray(), function(i, v) {
			jobDuty.push(v.value);
		});
		$.each($('input[name=chineseLanguageLevel]').serializeArray(),
				function(i, v) {
					chineseLanguageLevel.push(v.value);
				});

		//职称
		if (jobTitle.length > 0) {
			var rest_jobTitle = _.uniq(_.difference(jobTitle_Json, jobTitle));
			$.each(rest_jobTitle, function(i, v) {
				var v1 = v;
				$.each(jobTitle_Json, function(i, v) {
					if (v1 == v) {
						$('.list-item').eq(i).find('.jobTitle').addClass('rb');
					}
				})
			})
		}
		//职务
		if (jobDuty.length > 0) {
			var rest_jobDuty = _.uniq(_.difference(jobDuty_Json, jobDuty));
			$.each(rest_jobDuty, function(i, v) {
				var v1 = v;
				$.each(jobDuty_Json, function(i, v) {
					if (v1 == v) {
						$('.list-item').eq(i).find('.jobDuty').addClass('rb');
					}
				})
			})
		}

		//汉语言水平
		if (chineseLanguageLevel.length > 0) {
			var rest_chineseLanguageLevel = _.uniq(_.difference(
					chineseLanguageLevel_Json, chineseLanguageLevel));
			$.each(rest_chineseLanguageLevel, function(i, v) {
				var v1 = v;
				$.each(chineseLanguageLevel_Json, function(i, v) {
					if (v1 == v) {
						$('.list-item').eq(i).find('.chineseLanguageLevel')
								.addClass('rb');
					}
				})
			})
		}
		//教龄
		if (teachAge.length > 0) {

			$.each(teachAge,
					function(i, v) {
						var v1 = v.split('-');
						var temArray = _.range(parseInt(v1[0]),
								parseInt(v1[1]) + 1, 1);
						$.each(teachAge_Json, function(i, v) {
							if (!_.contains(temArray, parseInt(v))) {
								$('.list-item').eq(i).find('.teachAge')
										.addClass('rb');
							}
						})
					})
		}
		//年龄
		if (age.length > 0) {
			$.each(age,
					function(i, v) {
						var v1 = v.split('-');
						var temArray = _.range(parseInt(v1[0]),
								parseInt(v1[1]) + 1, 1);
						$.each(age_Json, function(i, v) {
							if (!_.contains(temArray, parseInt(v))) {
								$('.list-item').eq(i).find('.age').addClass(
										'rb');
							}
						})
					})
		}
		if (jobTitle.length > 0 || age.length > 0 || teachAge.length > 0
				|| chineseLanguageLevel.length > 0 || jobDuty.length > 0) {
			$('.fliters-result').html('');
			$('.more-filters-container input:checked').each(
					function() {
						var html = $(this).next().html();
						$('.fliters-result').append(
								'<span class="label label-info">' + html
										+ '</span>');
					})
			$('.more-filters-container').hide();

			$('.fliters-result-container').show();
		}

		return false;

	}

	function clearAdufilters() {
		$('.more-filters-container input').attr('checked', false);
		$('.rb').removeClass('rb');
	}

	function moreFliterHelp() {
		$('.more-filters-container').toggle();
		$('#aduTaskTeacher').hide();
		$('.fliters-result-container').hide();
	}
	function changeProject(t) {
		var vid = $(t).val();
		$.getJSON(
				'../admin/ttRecord_getAssignTaskOrganization.action?projectId='
						+ vid, function(data) {
					if (data.organizations.length > 0) {
						var cLis = '';
						$.each(data.organizations, function(i, c) {
							cLis += '<option value="' +c.id + '">' + c.name
									+ '</option>';
						});
						$('#orgas').html(cLis);
					} else {
						$('#orgas').html('<option value="0">暂无数据</option>');
					}
					if (data.subjects.length > 0) {
						var cLis = '';
						$.each(data.subjects, function(i, c) {
							cLis += '<option value="' +c.id + '">' + c.name
									+ '</option>';
						});
						$('#subjectName').html(cLis);
					} else {
						$('#subjectName')
								.html('<option value="0">暂无数据</option>');
					}
					//console.log(data.trainingUnits)
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
	//替换学员
	function replace(t) {
		var obj = $(t);
		var cUrl = obj.attr('data-url');
		var ttrId = obj.attr('data-id');
		$.getJSON(cUrl, function(ret) {
			if (ret.status == 'OK') {
// 				infotip(obj, ret.Message);
// 				$('#adu_'+obj.attr('data-id')).remove();
				window.location.href="../admin/ttRecordsReplace.jsp?ttrId="+ttrId;
			} else {
				alert('失败,' + ret.message);
			}
		})
		return false;
	}
</script>

<body>
</html>