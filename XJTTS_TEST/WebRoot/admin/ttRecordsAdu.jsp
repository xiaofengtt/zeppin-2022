<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">学员审核管理</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">

<script src="../js/iframe.js"></script>
<script src="../js/jsrender.min.js"></script>
<script src="../js/underscore-min.js"></script>
<link rel="stylesheet" href="../css/ui-dialog.css">
<script src="../js/dialog-min.js"></script>
	
<div class="main">

	<div class="listwrap">
		<div class="list_bar">审核报送学员</div>
		<div class="list-content clearfix" style="padding-top:15px;">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<div class="ttRecordsAduBtn">
				<button class="btn btn-primary fliter-btn" id="" type="button">筛选学员</button>
				<button class="btn btn-primary" id="more-fliter-btn"
					onclick="moreFliterHelp();" type="button">学员审查小助手</button>
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
				style="display:none" action="../admin/ttRecord_outputAduPage.action">
				<input type="hidden" value="1" name="enrollType" />
				<div class="row clearfix">
					<div class="col-md-6">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">项目名称</label>
							<div class="col-sm-9">
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
							<label class="col-sm-2 control-label" for="">报送单位</label>
							<div class="col-sm-9">
								<select class="form-control" id="orgas" name="organizationName">
									<option value="0">全部</option>
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

					<div class="col-md-6">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">培训科目</label>
							<div class="col-sm-9">
								<select id="subjectName" class="form-control" name="subjectName">
									<option value="0">全部</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">学员审核状态</label>
							<div class="col-sm-9">
								<select class="form-control" id="aduStatus" name="status">
									<option value="-1">全部</option>
									<option value="2">已审核</option>
									<option value="1">未审核</option>
									<option value="-2">通过</option>
									<option value="0">未通过</option>
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
				</div>

				<div class="row actionbar">
					<input name="searchKey" type="hidden"
						value="<s:property value="#parameters.key" />">
					<div class="text-center">
					<s:if test="level==26124">
						<button class="btn btn-primary" id="AduTeacherRecord" type="button">批量审核</button>
					</s:if>
						<button class="btn btn-primary" id="findRecord" type="button">查询</button>
					
						<button class="btn btn-primary" id="outputRecord" type="submit">导出</button>
						<!--  a class="ifrmbox" href="../admin/reviewInput.jsp" data-fancybox-type="iframe" >
						<button type="button" id="output" class="btn btn-primary">上传未通过学员名单</button></a>-->
					</div>
				</div>
			</form>


			<div class="clearfix">
				<div class="cui-menu2 clearfix" style="margin-left:9px">
					<style>
.pull-left {
	background: none !important;
}
</style>
					<div class="pull-left">

						<input onclick="toCheckAll(this)" type="checkbox" name="checkall">

						<div class="btn-group" style="margin-top:-2px;">
							<button type="button"
								style="font-size:13px;background:none;border:0;color:#428bca;padding:3px 5px 0"
								class="btn btn-xs btn-default dropdown-toggle"
								data-toggle="dropdown">
								批量审核 <span class="caret"></span>
							</button>
							<ul class="dropdown-menu" role="menu" style="min-width:100px;">
								<li><a href="javascript:void(0)"
									onclick="aduRecordall(this)"
									data-url="../admin/ttRecord_aduTeacherReport.action?method=adu">通过</a></li>
								<li><a href="javascript:void(0)"
									onclick="aduRecordall(this)"
									data-url="../admin/ttRecord_aduTeacherReport.action?method=noadu">不通过</a></li>
							</ul>
						</div>

					</div>

				</div>

				<div id="list-content" class="list-content clearfix"></div>

				<script id="ttRecordAduTpl" type="text/x-jsrender">
					<div id="adu_{{:id}}" class="list-item">
						<div class="list-item-hd">
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td width="200px" class="text-primary"><input class="listcheck" onclick="toCheck(this)" type="checkbox" name="traininfo" value="{{:id}}-{{:level}}"> &nbsp;<span>{{:schoolName}}—{{:name}}</span>
										{{if teacherStatus==1}}<font color=red>（被替换学员）</font>{{else teacherStatus==2}}<font color=green>（替换学员）</font>{{else}}{{/if}}
										</td>
										<td width="200px"><span class="text-primary">{{:projectName}}—{{:subjectName}}—{{:trainingName}}</span></td>
										<td width="90px"><span class="text-primary">审核状态：</span><span id="status_{{:id}}"
												class="text-danger">{{:status}}</span></td>
								
										<td width="110px"><a href="javascript:void(0)" onclick="audlist(this)"
											data-url="../admin/ttRecord_getTeacherReportInfomation.action?key={{:id}}-{{:level}}">报送记录</a>
											
											<div class="btn-group" style="margin-top:-5px;">
												<button type="button"
													style="font-size:13px;background:none;border:0;color:#4365be;padding:1px 5px 0"
													class="btn btn-xs btn-default dropdown-toggle"
													data-toggle="dropdown">审核 <span class="caret"></span>
												</button>
												<ul class="dropdown-menu" role="menu" style="min-width:100px;">
													<li><a href="javascript:void(0)" onclick="aduRecord(this)" data-id="{{:id}}"
															data-url="../admin/ttRecord_aduTeacherReport.action?method=adu&key={{:id}}-{{:level}}">通过
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
												data-url="../admin/ttRecord_aduTeacherReport.action?method=noadu&key={{:id}}-{{:level}}" class="btn btn-primary">不通过</button>
														
													</div>
											      </div>
											    </div>
											</div>
											
											<a href="javascript:void(0)" onclick="replace(this)" data-id="{{:id}}" data-url="../admin/ttRecord_replaceTeacherInit?ttrId={{:id}}">替换学员</a>&nbsp;&nbsp;&nbsp; 
											 {{if add}}
											 <a href="javascript:void(0)" onclick="delRecord(this)" data-id="{{:id}}"  data-url="../admin/ttRecord_aduTeacherReport.action?method=del&key={{:id}}-{{:level}}">删除</a>
											 {{/if}}
										</td>								

									</tr>
								</tbody>
							</table>
						</div>

						<div class="list-item-bd clearfix">
		
							<div class="list-item-col list-5-05" style="width:100px;text-align:center">
								<img src="../img/u{{:sex}}.png" width="80">
							</div>
		
							<div class="list-item-col list-5-05">
								<ul>
									<li><span class="text-primary">性别：</span>{{:sexName}}</li>
									<li class="age"><span class="text-primary">年龄：</span> {{:age}}</li>
									<li><span class="text-primary">民族：</span> {{:ethnic}}</li>
									<li><span class="text-primary">政治面貌：</span> {{:politics}}</li>

								</ul>
							</div>
							<div class="list-item-col list-5-08">
								<ul>
									<li class="teachAge"><span class="text-primary">教龄：</span> {{:teachAge}}</li>
									<li class="jobTitle"><span class="text-primary ">职称：</span> {{:jobTitle}}</li>
									<li class="jobDuty"><span class="text-primary">行政职务：</span> {{:jobDuty}}</li>
									<li><span class="text-primary">是否双语教学：</span>{{:muLanguage}}</li>
								</ul>
							</div>
							<div class="list-item-col list-5-05">
								<ul>
									<li><span class="text-primary">主要教学学段：</span>{{:mainGrade}} </li>
									<li><span class="text-primary">主要教学学科：</span>{{:mainSubject}} </li>
									<li><span class="text-primary">主要教学语言：</span>{{:mainLanguage}} </li>
									<li><span class="text-primary">所属地区：</span><div onmouseout="this.className='longtxt'" onmouseover="this.className='longtxt longtxt_hover'" class="longtxt">{{:area}}</div></li>

								</ul>
							</div>
							<div class="list-item-col list-5-8">
								<ul>
									<li><span class="text-primary">最高学历：</span> {{:edubackground}}</li>
									<li class="chineseLanguageLevel"><span class="text-primary">汉语言水平：</span> {{:chineseLanguageLevel}}</li>
									<li><span class="text-primary">填报人：</span>{{:creator}}</li>
							
								</ul>
							</div>
		

						</div>
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
	var jobTitle_Json = [], age_Json = [], teachAge_Json = [], jobDuty_Json = [], chineseLanguageLevel_Json = [];
	var aPage = '';
	
	function ttrecordAdu(o, pp) {
		var pp = (typeof (pp) == 'undefined') ? 1 : pp;
		var str = $(o).serialize() + '&page=' + pp;
		var enrollType = url();
		$('#list-content').html('').addClass('loading');
		$.getJSON('../admin/ttRecord_searchAduPage.action?' + str,
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
				});
	}
	
	
	function showaduRecord(obj) {
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
		$('#AduTeacherRecord').click(function(e) {//批量审核
			e.preventDefault();
			var str = $('#aduTaskTeacher').serialize();
			var d = dialog({
			    title: '提交审核中...',
			    content: '请耐心等待'
			});
			d.showModal();				
			$.getJSON('../admin/ttRecord_aduTeachers.action?' + str,function(data){
				if (data.status == 'OK') {
					ttrecordAdu('#aduTaskTeacher');
					
					d.content('<b style="color:green;font-size:16px">' + data.message + '</b>');
					setTimeout(function () {
			            d.close().remove();
			        }, 2000);
				}else{
					d.content('<b style="color:red;font-size:16px">' + data.message + '</b>');
					//alert('失败,'+data.message);
					
				}
			})
			return false;
		});

		$('.fliter-btn').click(function(e) {
			e.preventDefault();
			$('#aduTaskTeacher').toggle();
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
						$('#orgas').html('<option value="0">暂无数据<option>');
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
								.html('<option value="0">暂无数据<option>');
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
								'<option value="0">暂无数据<option>');
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