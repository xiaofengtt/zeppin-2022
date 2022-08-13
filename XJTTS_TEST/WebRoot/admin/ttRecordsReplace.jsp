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
		<div class="list_bar">替换学员</div>
		<div class="list-content clearfix" style="padding-top:15px;">
		
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
				<div class="fliter-btn-wrap" style="display:none">
					<a href="../admin/ttRecord_initAduPage.action" class="btn btn-primary">&larr;返回学员审核</a>
					<button class="btn btn-primary fliter-btn"  type="button">查找学员</button>
				</div>
			<form id="ttRecordsReport" class="form-horizontal" role="form">
				
				<div class="row clearfix">
					<div class="col-md-6">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">身份证号</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" name="teacherIdCard">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">所属学校</label>
							<div class="col-sm-9">
								<input name="organization" id="organization" type="hidden"
									value='<s:property value="organizationp.id"/>'>
							</div>

						</div>

					</div>

					<div class="col-md-6">
						<div class="form-group">
							<label class="col-sm-2 control-label" for="">姓名</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" name="teacherName">
							</div>

						</div>

					</div>
				</div>

				<div class="clearfix" style="margin:0 40px 0 20px">
					<div id="list-content" class="list-content clearfix"></div>
				</div>

				<div class="row actionbar">
					<div style="text-align:center">
						<button class="btn btn-primary" id="findRecord" type="submit">查询</button>
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
								<td width="auto"><span class="text-primary">所属学校：</span>{{:school}}</td>
								<td width="200px" class="text-primary">姓名：<span>{{:name}}</span></td>
								
								<td width="160px"><span id="status_{{:id}}">
									{{if record_status}}
									<a href="../admin/ttRecord_addReplaceTeacherInit.action?teacherId={{:teacherId}}&replacedTtrId={{:replacedTtrId}}&projectId={{:projectId}}&subjectId={{:subjectId}}&collegeId={{:tcid}}&gorganizationId={{:gorganizationId}}&&porganizationId={{:porganizationId}}" data-fancybox-type="iframe" class="btn-create">替换为该学员 </a>
									{{else}}
									已报名
									{{/if}}
									</span>
								</td>
							</tr>
						</tbody>
					</table>
				</div>

				<div class="list-item-bd clearfix">
		
					<div class="list-item-col list-5-05" style="width:100px;text-align:center">
						<img src="../img/u{{:sexIndex}}.png" width="80">
					</div>
		
					<div class="list-item-col list-5-05">
						<ul>
							<li><span class="text-primary">性别：</span>{{:sex}}</li>
							<li><span class="text-primary">年龄：</span> {{:age}}</li>
							<li><span class="text-primary">民族：</span> {{:ethnic}}</li>
							<li><span class="text-primary">政治面貌：</span> {{:politic}}</li>

						</ul>
					</div>
					<div class="list-item-col list-5-05" style="width:150px;">
						<ul>
							<li><span class="text-primary">教龄：</span> {{:tage}}</li>
							<li><span class="text-primary">职称：</span> {{:jobDuty}}</li>
							<li><span class="text-primary">行政职务：</span> {{:jobTitle}}</li>
							<li><span class="text-primary">是否双语教学：</span>{{:isMu}}</li>

						</ul>
					</div>
					<div class="list-item-col list-5-1">
						<ul>
							<li><span class="text-primary">主要教学学段：</span>{{:grade}} </li>
							<li><span class="text-primary">主要教学学科：</span>{{:subject}} </li>
							<li><span class="text-primary">主要教学语言：</span>{{:language}} </li>
							<li><span class="text-primary">所属地区：</span><div onmouseout="this.className='longtxt'" onmouseover="this.className='longtxt longtxt_hover'" class="longtxt">{{:area}}</div></li>

						</ul>
					</div>
					<div class="list-item-col list-5-8">
						<ul>
							<li><span class="text-primary">最高学历：</span> {{:edubackground}}</li>
							<li><span class="text-primary">汉语言水平：</span> {{:languageLevel}}</li>
							<li><span class="text-primary">身份证: </span>{{:idcard}}</li>
							<li>
								<span class="text-primary">手机：</span>{{:mobile}}
							</li>

						</ul>
					</div>
		

				</div>
			</div>
		</script>





	</div>
	</div>
	</div>
	<script>
		$(function() {
			var recordReport = function(o, pp) {
				var pp = (typeof (pp) == 'undefined') ? 1 : pp;
				
				var ttrId = url('?ttrId');
				var str = $(o).serialize() + '&page=' + pp + '&ttrId=' + ttrId;
				$('#list-item-cnt').html('<p style="font-size:20px;text-align:center;height:100px;line-height:100px;">数据加载中....</p>');
				$.getJSON('../admin/ttRecord_searchReplaceTeachers.action?' + str,
						function(data) {
							if (data.status == 'OK') {
								var template = $.templates('#ttRecordTpl');
								var html = template.render(data.records);
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
										recordReport('#ttRecordsReport', pp);
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
			$('#ttRecordsReport').submit(function() {
				recordReport(this);
				return false;
			});
			
			$('.fliter-btn').click(function(e){
				e.preventDefault();
				$('.fliter-btn-wrap').hide();
				$('#ttRecordsReport').slideDown('slow');
				
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

			//所属学校
			$("#organization").select2({
				placeholder : "请输入学校名称或拼音首字母",
				minimumInputLength : 0,
				quietMillis : 1000,
				allowClear : true,
				ajax : {
					url : "../base/organization_searchSchool.action",
					dataType : 'json',
					data : function(term, page) {
						return {
							search : term, // search term
							page : 10
						};
					},
					results : function(data, page) {
						return {
							results : data.Options
						};

					}
				},
				initSelection : function(element, callback) {
					var id = $(element).val();
					callback(data.Options);
				},
				formatResult : movieFormatResult,
				formatSelection : movieFormatSelection,
				dropdownCssClass : "bigdrop",
				escapeMarkup : function(m) {
					return m;
				}
			});

			function movieFormatResult(Options) {
				var html = Options.DisplayText;
				return html;

			}
			function movieFormatSelection(Options) {
				return Options.DisplayText;
			}

		})
// 		// 添加学员
// 		function replaceRecord(t) {
// 			var obj = $(t);
// 			var cUrl = obj.attr('data-url');
// 			$.getJSON(cUrl, function(ret) {
// 				if (ret.Result == 'OK') {
// 					$('#status_' + obj.attr('data-id')).text('替换成功');
// 					window.location.href='../admin/ttRecord_initAduPage.action';
// 					//infotip(obj, ret.Message);
// 				} else if (ret.Result == 'WARNING') {
// 					if (confirm("人数已经达到上限，是否继续添加?")) {
// 						var tcUrl = cUrl + "&goon=1";
// 						$.getJSON(tcUrl, function(ret) {
// 							if (ret.Result == 'OK') {
// 								$('#status_' + obj.attr('data-id')).html('替换成功').addClass('sok');
// 								window.location.href='../admin/ttRecord_initAduPage.action';
// 							} else {
// 								alert('失败,' + ret.Message);
// 							}
// 						});
// 					}
// 				}else if (ret.Result == "ERROR" || ret.Result == 'FAIL') {
// 					alert('失败,' + ret.Message);
// 				}
// 			})
// 			return false;
// 		}
	</script>
	<script src="../js/iframe.js"></script>
<body>
</html>