<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">学员全信息管理</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">

<script src="../js/iframe.js"></script>
<script src="../js/jsrender.min.js"></script>
<link rel="stylesheet" href="../css/ui-dialog.css">
<script src="../js/dialog-min.js"></script>
<style>
	.pull-left {
		background: none !important;
	}
</style>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>
<style>
.select2-container .select2-choice{width:auto;height:28px;line-height:28px;border:none;}
.select2-container{padding:0;}
.select2-container .select2-choice > .select2-chosen{line-height:34px;}
</style>
<div class="main">
<div class="iconDiv">
			<a class="btn btnMyself fliter-btn">
				<span><img src="../img/sousuo.png" alt="icon">
					<b>筛选</b>
				</span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角">
					<b>筛选学员</b>
				</p>
			</a>
	</div>
	<div class="listwrap">
		<div class="list_bar">查看学员信息</div>
		<div class="list-content clearfix">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<div class="ttRecordsAduBtn" style="padding-bottom:15px;">
<!-- 				<button class="btn btn-primary fliter-btn" id="" type="button">筛选学员</button> -->
			</div>
			<div class="fliters-result-container clearfix">
				<span class="title">学员审查搜索条件：</span><span class="fliters-result"></span><a
					onclick="$('.fliters-result-container').hide();$('.more-filters-container').show();"
					style="padding:4px 0;margin:0 5px" href="javascript:void(0)">编辑</a>
			</div>
			<form id="aduTaskTeacher" class="form-horizontal"
				style="display:none" action="../admin/trainingStudentOpt_outputTeacherInfoList.action">
				<input type="hidden" value="1" name="enrollType" />
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
									<option value="-1" search="全部">全部</option>
									<s:iterator value="lsttTrainingSubjects" id="ts">
									<option value="<s:property value="#ts.getId()" />" search="<s:property value="#ts.getName()" />"><s:property
												value="#ts.getName()" /></option>
									</s:iterator>
								</select>
							</div>
						</div>

<!-- 						<div class="form-group"> -->
<!-- 							<label class="col-sm-2 control-label" for="">姓名/身份证号</label> -->
<!-- 							<div class="col-sm-9"> -->
<!-- 								<input class="form-control" name="teacherName" type="text"> -->
<!-- 							</div> -->
<!-- 						</div> -->
					</div>

					<div class="col-md-6">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">项目名称</label>
							<div class="col-sm-9">
								<select class="form-control" name="projectName" id="projectName" onchange="changeCheck(this);">
									<option value="-1" search="全部">全部</option>
									<s:iterator value="lstProjects" id="rt">
										<option value='<s:property value="#rt.getId()" />' search='<s:property value="#rt.getName()" />'><s:property
												value="#rt.getName()" /></option>
									</s:iterator>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">班级</label>
							<div class="col-sm-9">
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
					<input name="searchKey" type="hidden"
						value="<s:property value="#parameters.key" />">
					<div class="text-center" style="margin-bottom:20px;">
						<button class="btn btn-primary btn-myself" id="findRecord" type="button">查询</button>
					
						<button class="btn btn-default btn-myself" id="outputRecord" type="submit">导出</button>
					</div>
				</div>
			</form>


			<div class="clearfix">
<!-- 				<div class="cui-menu2 clearfix" style="margin-left:9px"> -->

<!-- 				</div> -->

				<div id="list-content" class="list-content clearfix"></div>

				<script id="ttRecordAduTpl" type="text/x-jsrender">
					<div id="adu_{{:id}}" class="list-item">
						<div class="list-item-hd">
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td width="200px" class="text-primary"> &nbsp;<span>{{:schoolName}}—{{:name}}</span>
										</td>
										<td width="200px"><span class="text-primary">{{:projectName}}—{{:subjectName}}—{{:trainingName}}</span>(<span class="text-primary">备注：</span>{{:reversalStr}})</td>
										<td width="50px" class="text-center"><a href="javascript:void(0)" onclick="audRecordslist(this)"
											data-url="../admin/ttRecord_getTeacherReportInfomation.action?key={{:id}}-{{:level}}">审核记录</a></td>
								
										<td width="150px" class="text-center">
											<span class="text-primary">培训状态：</span><span id="status_{{:id}}"
												class="text-danger">{{:trainingStatus}}</span>
											<span class="text-primary">是否测评：</span><span class="text-danger">{{:isPeTest}}</span>
										</td>								

									</tr>
								</tbody>
							</table>
						</div>

						<div class="list-item-bd clearfix">
		
							<div class="list-item-col list-5-05" style="width:10%;text-align:center">
								<img src="../img/u{{:sex}}.png" width="80">
							</div>
		
							<div class="list-item-col list-5-05" style="width:30%">
								<ul>
									<li><span class="text-primary">性别：</span>{{:sexName}}</li>
									<li class="age"><span class="text-primary">年龄：</span> {{:age}}</li>
									<li><span class="text-primary">民族：</span> {{:ethnic}}</li>
									<li><span class="text-primary">政治面貌：</span> {{:politics}}</li>
									<li><span class="text-primary">所属地区：</span><div onmouseout="this.className='longtxt'" onmouseover="this.className='longtxt longtxt_hover'" class="longtxt">{{:area}}</div></li>
									
								</ul>
							</div>
							<div class="list-item-col list-5-05">
								<ul>
									<li class="teachAge"><span class="text-primary">教龄：</span> {{:teachAge}}</li>
									<li class="jobTitle"><span class="text-primary ">职称：</span> {{:jobTitle}}</li>
									<li class="jobDuty"><span class="text-primary">行政职务：</span> {{:jobDuty}}</li>
									<li><span class="text-primary">最高学历：</span> {{:edubackground}}</li>
									<li><span class="text-primary">是否双语教学：</span>{{:muLanguage}}</li>

								</ul>
							</div>
							<div class="list-item-col list-5-05">
								<ul>
									<li><span class="text-primary">状态：</span> {{:status}}</li>
									<li><span class="text-primary">编制：</span>{{:authorized}}</li>
									<li class="chineseLanguageLevel"><span class="text-primary">汉语言水平：</span> {{:chineseLanguageLevel}}</li>
									<li><span class="text-primary">主要教学学段：</span>{{:mainGrade}} </li>
									<li><span class="text-primary">主要教学学科：</span>{{:mainSubject}} </li>
									
								</ul>
							</div>
							<div class="list-item-col list-5-05">
								<ul>
									<li><span class="text-primary">主要教学语言：</span>{{:mainLanguage}} </li>
									<li><span class="text-primary">培训成绩：</span>{{:trainingScore}} </li>
									<!-- li><span class="text-primary">所修学时：</span>{{:trainingHour}} </li -->
									<li><span class="text-primary">不合格原因：</span>{{:trainingReason}} </li>
									<li><span class="text-primary">证书编号：</span>{{:certificate}}</li>
									
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
		$.getJSON('../admin/trainingStudentOpt_getTeacherInfoList.action?' + str,
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
					$(".longtxt").css("width",$(".list-item-bd .list-5-05 li").width()-90+"px");
					$(".longtxt_hover").css("width",$(".list-item-bd .list-5-05 li").width()-50+"px");
					$(window).resize(function(){
						$(".longtxt").css("width",$(".list-item-bd .list-5-05 li").width()-90+"px");
						$(".longtxt_hover").css("width",$(".list-item-bd .list-5-05 li").width()-50+"px");
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
		$('#projectName').select2(); 
		$('#subjectName').select2(); 
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

	function changeYear(t){
		var year = $(t).val();
		
		var cis = '<option value="0" search="全部">全部</option>';
		$('#subjectName').html(cis);
		
		$.getJSON('../admin/trainingStudentOpt_getProject.action?year=' + year, function(data){
			if (data.projects.length > 0) {
				var cLis = '';
				$.each(data.projects, function(i, c) {
					cLis += '<option value="' +c.id + '" search="'+c.name+'">' + c.name	+ '</option>';
				});
				$('#projectName').html(cLis);
			} else {
				$('#projectName').html('<option value="0">暂无数据</option>');
			}
		})
	}
	function changeCheck(t){
		var pid = $(t).val();
		if(pid == 0){
			var cis = '<option value="0" search="全部">全部</option>';
			$('#subjectName').html(cis);
			return;
		}
		$.getJSON('../admin/trainingStudentOpt_getSubject.action?projectId=' + pid, function(data){
			if (data.status == "OK") {
				var cLis = '';
				$.each(data.subjects, function(i, c) {
					cLis += '<option value="' +c.id + '" search="'+c.name+'">' + c.name	+ '</option>';
				});
				$('#subjectName').html(cLis);
			} else {
				$('#subjectName').html('<option value="0">暂无数据</option>');
			}
		})
	}
	
	// 审核记录
	function audRecordslist(t) {
		var obj = $(t);
		var cUrl = obj.attr('data-url');
		var tt_offset = obj.offset();
		var tt_top = tt_offset.top;
		var tt_left = tt_offset.left;

		$.getJSON(
			cUrl,
			function(ret) {
				if (ret.Result == 'OK') {
// 					var html = '<table class="table table-striped"><thead><tr><th width="80">序号</th><th width="160">审核时间</th></tr></thead><tbody>';
					var html = '<table class="table table-striped"><tbody>';
					if (ret.rows.length > 0) {
// 						$.each(ret.rows, function(i, c) {
// 							html += '<tr><td><b>' + ret.rows[0].id + '</b></td><td>'
// 									+ ret.rows[0].time + '</td></tr>';
							html += '<tr><td><b>最终审核时间</b></td><td>'
									+ ret.rows[0].time + '</td></tr>';
// 						})
					} else {
						html += '<tr><td colspan=3>暂无审核记录</td></tr>';
					}
					html += '</tbody></table>'
					$('#recordlistcnt').html(html);
					var new_div_left = tt_left
							- $('.recordlist').outerWidth() - 16;
					var new_div_top = tt_top
							- $('.recordlist').outerHeight() / 2 + 6;
					$('.recordlist').css({
						'top' : new_div_top + 'px',
						'left' : new_div_left + 'px'
					}).toggle();

				} else {
					alert('失败,' + ret.Message);
				}
			})

	}
</script>

<body>
</html>