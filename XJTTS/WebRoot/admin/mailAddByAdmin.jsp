<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">管理员发送站内信</s:param>
</s:action>
<div class="main">
	<div class="ifrcnt container">
		<div class="hd">
			<h3>编辑站内信</h3>
		</div>
		<div class="hd">
			<div class="alert alert-danger" align="center"
				style="display: none; margin: 0 15px 15px;"></div>
		</div>
		<div class="bd">
			<form class="form-horizontal" id="changeSubject" action="" method="post">
				<div class="row actionbar">
					<div align="center" style="margin-left: 30px">
						<label style="text-align: center;color: #044d79;font-weight: bold;margin-bottom: 30px" for="">请选择调整的信息</label>
						<div class="clearfix" style="margin-bottom: 20px">
							
							<div class="span5">
								<div class="control-group">
									<label class="control-label" for="">收件人角色</label>
									<div class="controls">
										<select class="form-control" name="addressee" onchange="changeSource(this)">
											<option value="0">请选择</option>
											<option value="1">项目管理员</option>
											<option value="2">承训单位</option>
											<option value="3">评审专家</option>
											<option value="4">培训教师</option>
										</select>
									</div>
								</div>
							</div>	
							
							<div class="span5">
								
								<div class="control-group" id="subjects" style="display: none;">
									<div>
										<label class="control-label" for="">机构</label>
									</div>
									<div class="restrictcnt" style="margin-top: 30px;">
										<ul id="projectApplies" style="margin-left: 110px">
										
										</ul>
									</div>
										
								</div>
							</div>
							
							<div class="span5">
								<div class="control-group">
									<label class="control-label" for="">标题</label>
									<div class="controls">
										<input type="text" name="title" value="<s:property	value="mailInformation.title" />" />
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label" for="">正文</label>
									<div class="controls">
										<textarea style="resize: none;width: 500px;height: 200px;max-width: 500px;max-height: 200px;" id="" name="content" value="<s:property	value="mailInformation.text" />" ></textarea>
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label" for="">落款</label>
									<div class="controls">
										<input type="text" name="inscription" value="<s:property value="mailInformation.inscription"/>" />
									</div>
								</div>
								
								<div class="control-group">
									<br>
									<div class="controls">
										<input type="checkbox" id="sendM" />发送站内信
										<input type="checkbox" id="sendS" />发送站短信
										
									</div>
								</div>
							</div>
						</div>
						
						<button class="btn btn-primary" type="submit">确定</button>
						<button id="colorboxcancel" onclick="parent.$.colorbox.close()"
							class="btn btn-default" type="button">取消</button>
					</div>

				</div>
			</form>
		</div>
	</div>
</div>
	<script>
		$(function() {
			/*$('body').click(function(e) {
				var o = $(e.target);
				if (o.hasClass('clId') || o.parent('.clId').length || o.hasClass('usel') || o.parent('.usel').length) {
					return;
				}
				if (!o.parents('.listSub').length || !o.parents('.uul').length) {
					$('.listSub,.uul').hide();
				}
			});*/
			$('body').click(function(e) {
				var o = $(e.target);
				if (o.hasClass('usel') || o.parent('.usel').length) {
					return;
				}
				if (!o.parents('.uul').length) {
					$('.uul').hide();
				}
			});

			$('.datepicker').datepicker({
				language : "zh-CN",
				format : 'yyyy-mm-dd',
				startView : 2,
				endDate : '1d',
				autoclose : true
			});

			$('.switch').bootstrapSwitch();

			$('#mainTeachingSubject,#mainTeachingGrades,#mainTeachingLanguage')
					.select2();
			$(
					'#unMainTeachingSubject,#unMainTeachingGrades,#unMainTeachingLanguage')
					.select2();

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
							page_limit : 10
						};
					},
					results : function(data, page) {
						return {
							results : data.Options
						};

					}
				},

				initSelection : function(element, callback) {
					element = $(element);
					var data = {
						id : element.val(),
						DisplayText : element.attr('data-name')
					};
					callback(data);
				},
				formatResult : movieFormatResult,
				formatSelection : movieFormatSelection,
				dropdownCssClass : "bigdrop",
				escapeMarkup : function(m) {
					return m;
				}
			})

			function movieFormatResult(Options) {
				var html = Options.DisplayText;
				return html;

			}
			function movieFormatSelection(Options) {
				return Options.DisplayText;
			}
		})

		$(function() {
			$('#changeSubject')
					.submit(
							function() {
								var ttrId = url("?id")
								var str = $(this).serialize();
								$
										.post(
												'../admin/trainingStudentOpt_addchangeSubjectRecords.action?'
														+ str + '&ttrId='
														+ ttrId,
												function(ret) {
													if (ret.Result == 'OK') {
														alert('成功,'
																+ ret.Message);
														top.location.href = '../admin/trainingStudentOpt_changeSubjectInit.action';
													} else {
														$('.alert-danger')
																.html(
																		ret.Message)
																.show();
													}
												})
								// 						var projectId = url("?projectId");
								// 						var subjectId = url("?subjectId");
								// 						var collegeId = url("?collegeId");
								// 						var gorganizationId = url("?gorganizationId");
								// 						var porganizationId = url("?porganizationId");
								// 						var teacherId = url("?teacherId");
								// 						var replacedTtrId = url("?replacedTtrId");
								// 						var str ="projectId="+projectId+"&subjectId="+subjectId+"&collegeId="+collegeId+"&gorganizationId="+gorganizationId+"&porganizationId="+porganizationId+"&teacherId="+teacherId+"&replacedTtrId="+replacedTtrId;
								// 						$.post('../admin/ttRecord_addReplaceTeacher.action?'+ str, function(ret) {
								// 							if (ret.Result == 'OK') {
								// 								alert('成功,' + ret.Message + ',确定将回到学员审核。。。');
								// 								top.location.href='../admin/ttRecord_initAduPage.action';
								// 							} else if (ret.Result == 'WARNING') {
								// 								if (confirm("人数已经达到上限，是否继续添加?")) {
								// 									var tcUrl = '../admin/ttRecord_addReplaceTeacher.action?'+ str + '&goon=1';
								// 									$.getJSON(tcUrl, function(ret) {
								// 										if (ret.Result == 'OK') {
								// 											alert('成功,' + ret.Message + ',确定将回到学员审核。。。');
								// 											top.location.href='../admin/ttRecord_initAduPage.action';
								// 										} else if (ret.Result == "ERROR" || ret.Result == 'FAIL') {
								// 											$('.alert-danger').html(ret.Message).show();
								// 										}else if (ret.Result == "REPLACE") {
								// 											alert('失败,' + ret.Message);
								// 											top.location.href='../admin/ttRecord_initAduPage.action';
								// 										}
								// 									});
								// 								}
								// 							}else if (ret.Result == "ERROR" || ret.Result == 'FAIL') {
								// 								$('.alert-danger').html(ret.Message).show();
								// 							}else if (ret.Result == "REPLACE") {
								// 								alert('失败,' + ret.Message);
								// 								top.location.href='../admin/ttRecord_initAduPage.action';
								// 							}
								// 						})
								return false;
							});

		})
		
		function changeSource(t){
			var source = $(t).val();
			if(source == 1){
				getSubjects();
			}else if(source == 2){
				$('#sourceLabel').html("链接地址");
				$('#sourcePath').css('display','block');
				$('#sourceTime').css('display','none');
			}else if(source == 3){
				$('#sourceLabel').html("采集地点");
				$('#sourcePath').css('display','block');
				$('#sourceTime').css('display','block');
			}else if(source == 4){
				$('#sourceLabel').html("出版物名称");
				$('#sourcePath').css('display','block');
				$('#sourceTime').css('display','none');
			}
		}
		
		function getSubjects() {
			$('.alert-danger').html('').hide();
			$.getJSON('../admin/mail_getNextOrganization.action', function(r) {
				if(r.status == 'ERROR'){
					$('#subjects').hide();
					$('.restrictcnt').hide();
					return;
				}
				if (r.records.length > 0) {
					var ufc = '<li><input type="checkbox" id="checkall" name="checkall" onclick="CheckAll(this.checked)" value="" />全选</li>';
					$.each(r.records,
							function(i, c) {
								ufc += '<li><input type="checkbox" name="projectApplys" value="'+c.id+'">'+c.name+'</li>'
							});
					$('#projectApplies').html(ufc);
					$('#subjects').show();
					$('.restrictcnt').show();
					$('#trainTime').show();
					
				} else {
					$('#projectApplies').html('<li>暂无数据<li>');
					$('#subjects').hide();
					$('.restrictcnt').hide();
				}
			})
		}
	</script>
<jsp:include page="foot.jsp"></jsp:include>
