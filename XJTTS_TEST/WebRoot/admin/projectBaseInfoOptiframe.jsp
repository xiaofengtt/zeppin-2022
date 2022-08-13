<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="../css/bootstrap2.css">
<link rel="stylesheet" href="../css/iframe.css">
<link rel="stylesheet" href="../css/uploadfile.css">
<link href="../css/datepicker3.css" rel="stylesheet">
<link href="../css/bootstrap-switch.min.css" rel="stylesheet">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/iframe.js"></script>
<script src="../js/bootstrap-datepicker.js"></script>
<script src="../js/bootstrap-datepicker.zh-CN.js"></script>
<script src="../js/upload/jquery.uploadfile.min.js"></script>
<script src="../js/bootstrap-switch.min.js"></script>
</head>
<body>
	<div class="ifrcnt container">
		<div class="hd">
			<h3>开设项目</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form id="addProjectBaseInfo" class="form-horizontal" role="form"
				action="../admin/projectBaseOpt_opProjectBaseInfoOpt.action"
				method="post">
				<input type="hidden" name="id" value="<s:property value="id" />">
				<div class="clearfix">
					<div class="span10">
						<div class="control-group">
							<label class="control-label" for="">开设年份</label>
							<div class="controls">
								<select class="span3" name="year">
									<s:iterator value="yearArray" id="ya">
										<option <s:if test="year==#ya" >selected</s:if>
											value="<s:property value="#ya" />"><s:property
												value="#ya" /></option>
									</s:iterator>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">项目类型</label>
							<div class="controls">

								<div id="projecttypecnt" class="clearfix"
									style="display:none;width:544px;">
									<div class="ufcwrap" id="resCon"></div>
									
								</div>
								<div id="resmtr" class="promtc">
									<s:if test="restrictRightList!=null">

										<s:set name="flag" value="restrictRightList.length-2" />
										<div class="mtc">
											<s:iterator value="restrictRightList" id="rr" status="st">
												<s:set name="index" value="#st.index" />
												<s:if test="#index==#flag">
													<s:property value="#rr" />
												</s:if>
												<s:elseif test="#index>#flag"></s:elseif>
												<s:else>
													<s:property value="#rr" />
													<span class="sn"> &gt; </span>
												</s:else>
											</s:iterator>
											<span onclick="delRes($(this),'protype')"
												dataid="<s:property value="restrictRightList[restrictRightList.length-1]" />"
												class="uclose">&times;</span>
										</div>
									</s:if>
								</div>

								<div id="resIds" class="hidden">
									<!--input type="hidden" name="restrictRightId" value=""-->
									
									<s:if test="restrictRightId!=null">
										<s:iterator value="restrictRightId" id="rr">
											<input type="hidden" id="rightid_<s:property value="#rr" />"
												name="restrictRightId" value="<s:property value="#rr" />">
										</s:iterator>
									</s:if>
									
								</div>

							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">项目名称</label>
							<div class="controls">
								<input type="text" class="input-xxlarge" id="" name="name"
									value="<s:property value="name" />" placeholder="项目名称">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">简称</label>
							<div class="controls">
								<input type="text" class="input-xxlarge" id="" name="shortname"
									value="<s:property value="shortname" />" placeholder="简称">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">申报截止日期</label>
							<div class="controls">
								<input type="text" readonly data-provide="datepicker" class="datepicker"
									id="" name="timeup" value="<s:property value="timeup" />">
								
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">报名方式</label>
							<div class="controls">
								<select class="span3" name="enrollType">
									<option value="1" <s:if test="enrollType==1" >selected</s:if>>计划式报名</option>
									<option value="2" <s:if test="enrollType==2" >selected</s:if>>自主式报名</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">培训方式</label>
							<div class="controls">
								<select class="span3" name="traintype">
									<option value="1" <s:if test="traintype==1" >selected</s:if>>集中面授</option>
									<option value="2" <s:if test="traintype==2" >selected</s:if>>远程培训</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">人均经费标准</label>
							<div class="controls">
								<div class="input-append">
									<input type="text" id="" name="funds"
										value="<s:property value="funds" />" placeholder=""> <span
										class="add-on">元/人/天</span>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">计划培训人数</label>
							<div class="controls">

								<input type="text" id="" name="number"
									value="<s:property value="number" />" placeholder="">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">最大申报学科</label>
							<div class="controls">
								<input type="text" id="" name="subjectMax"
									value="<s:property value="subjectMax" />" >
							</div>
						</div>
						<div class="control-group" style="width:600px">
							<label class="control-label" for="">上传申报模板</label>
							<div class="controls" >
								<div id="applydoc" class="">&nbsp;上传申报模板&nbsp;&nbsp;</div>
								
									<script>
										$("#applydoc").uploadFile({
											url:"../base/fileUpload_upload.action",
											allowedTypes:"doc,docx",
											maxFileSize:1024*1024*50,
											fileName:"ReportMode",
											maxFileCount : 1,
											dragDropStr: "<span><b>上传</b></span>",
											extErrorStr:"文件格式不正确，请上传word文档类型的文件",
											showDone:false,
											showDelete : true,
											deletelStr : '删除',
											showAbort:false
										});
									</script>
							</div>
						</div>
						<div class="row">
					<div class="span6">
					<div class="control-group">
							<label class="control-label" for=""></label>
							<div class="controls attachment">
							<p><a href="..<s:property value="doPath"/>"><s:property value=" doTitle"/></a>
							</div>
						</div>
					</div>

				</div>
					</div>
					<div class="span10">
						<div class="control-group">
							<label class="control-label" for="">设置培训学科范围</label>
							<div class="controls" style="padding-top:10px;">

								<div class="row">
									<div class="span2">
										<input id="restrict" name="restrictSubject" type="checkbox"
											data-on="primary" data-off="default" data-on-label="开"
											data-off-label="关"<s:if test="restrictSubject==1" >checked</s:if>>
									</div>
									<div class="restrictcntSubject clearfix span6">
										<div class="ufcwrapSubject" id="resConSubject"></div>
										<button type="button" onclick="resUbtnSubject();"
											class="btn btn-default frbtn resBtnSubject disabledbtnSubject"
											disabled="disabled">确认</button>
									</div>
								</div>
								<div id="subjectresmtr" style="margin-top:10px;">
									<s:if test="restrictSubjectList!=null">
										<s:iterator value="restrictSubjectList" id="rslist">
										<s:set name="flag" value="#rslist.length-2" />
											<div class="subjectmtc">
												<s:iterator value="#rslist" id="rsi" status="st">																								
														<s:set name="index" value="#st.index" />
													<s:if test="#index==#flag">
														<s:property value="#rsi" />
													</s:if>
													<s:elseif test="#index>#flag"></s:elseif>
													<s:else>
														<s:property value="#rsi" />
														<span class="sn"> &gt; </span>
													</s:else>								
												</s:iterator>
												<span onclick="delResSubject($(this),'')"
													dataid="<s:property value="#rslist[#rslist.length-1]" />"
													class="uclose">&times;</span>
											</div>
										</s:iterator>
									</s:if>
								</div>

								<div class="hidden" id="subjectIds">
									<s:if test="restrictSubjectId!=null">
										<s:iterator value="restrictSubjectId" id="rsi">
											<input type="hidden" id="subjectid_<s:property value="#rsi" />"
												name="restrictSubjectId" value="<s:property value="#rsi" />">
										</s:iterator>
									</s:if>
								</div>
							</div>
						</div>
					</div>
					<div class="span10">
						<div class="control-group">
							<label class="control-label" for="">设置承训院校范围</label>
							<div class="controls" style="padding-top:10px;">

								<div class="row">
									<div class="span2">
										<input id="restrict" name="restrictCollege" type="checkbox"
											data-on="primary" data-off="default" data-on-label="开"
											data-off-label="关"<s:if test="restrictCollege==1" >checked</s:if>>
									</div>
									<div class="restrictcntCollege clearfix span6">
										<div class="ufcwrapCollege" id="resConCollege"></div>
										<button type="button" onclick="resUbtnCollege();"
											class="btn btn-default frbtn resBtnCollege disabledbtnCollege"
											disabled="disabled">确认</button>
									</div>
								</div>
								<div id="collegeresmtr" style="margin-top:10px;">
									<s:if test="restrictCollegeList!=null">
										<s:iterator value="restrictCollegeList" id="rclist">
											<s:set name="flag" value="#rclist.length-2" />
											<div class="collegemtc">
												<s:iterator value="#rclist" id="rci" status="st">
													<s:set name="index" value="#st.index" />
													<s:if test="#index==#flag">
														<s:property value="#rci" />
													</s:if>
													<s:elseif test="#index>#flag"></s:elseif>
													<s:else>
														<s:property value="#rci" />
														<span class="sn"> &gt; </span>
													</s:else>
												</s:iterator>
												<span onclick="delResCollege($(this),'')"
													dataid="<s:property value="#rclist[#rclist.length-1]" />"
													class="uclose">&times;</span>
											</div>
										</s:iterator>
									</s:if>
								</div>

								<div class="hidden" id="collegeIds">
									<s:if test="restrictCollegeId!=null">
										<s:iterator value="restrictCollegeId" id="rci">
											<input type="hidden" id="collegeid_<s:property value="#rci" />"
												name="restrictCollegeId" value="<s:property value="#rci" />">
										</s:iterator>
									</s:if>
								</div>
							</div>
						</div>
					</div>
					<div class="span10">
						<div class="control-group">
							<label class="control-label" for="">是否参与信息技术<br>能力提升工程评测</label>
							<div class="controls">
								<input name="isTest" type="checkbox" data-text-label=""
									data-on="primary" data-off="default" data-on-label="是"
									data-off-label="否"
									<s:if test="isTest==1" >checked</s:if>
								value="1"	class="switch">
							</div>
						</div>
					</div>

				</div>

				<div class="row actionbar">
					<div class="offset7">
						<button class="btn btn-primary" type="submit">确定</button>
						<button id="colorboxcancel" onclick="parent.$.colorbox.close()"
							class="btn btn-default" type="button">取消</button>
					</div>

				</div>
			</form>
		</div>

	</div>
	<script>

	$(function() {
		$('.switch').bootstrapSwitch();
		$(document).ready(function() {
			var restrictSubject = <s:property value="restrictSubject" />;
			var restrictCollege = <s:property value="restrictCollege" />;
			if(restrictSubject=="1"){
				$('.restrictcntSubject').show();
				showformSubject();
				resConSubject();
			}
			if(restrictCollege=="1"){
				$('.restrictcntCollege').show();
				showformCollege();
				resConCollege();
			}
		});
		
			$('.datepicker').datepicker({
				language : "zh-CN",
				format : 'yyyy-mm-dd',
				startDate : "1d",
				autoclose : true
			})
			var id = $('input[name="id"]').val();
			if (!id) {
				$('#projecttypecnt').show();
				resCon2();

			}
			$('body').click(function(e) {
				var o = $(e.target);
				if (o.hasClass('usel') || o.parent('.usel').length) {
					return;
				}
				if (!o.parents('.uul').length) {
					$('.uul').hide();
				}
			});
			
			//项目类型 helper 函数
			$(document).on("click",function(a) {
				if(!$(a.target).parents().is('.ufc'))
					$('.uul').hide();
			});

			

			$('input[name="restrictSubject"]')
					.bootstrapSwitch();
			$('input[name="restrictSubject"]').on('switch-change',
					function(e, data) {
						var $element = $(data.el), value = data.value;
						if (value)
							$('.restrictcntSubject').show();
						else
							$('.restrictcntSubject').hide();
						showformSubject();
						resConSubject();

					});
			$('input[name="restrictCollege"]')
					.bootstrapSwitch();
			$('input[name="restrictCollege"]').on('switch-change',
					function(e, data) {
						var $element = $(data.el), value = data.value;
						if (value)
							$('.restrictcntCollege').show();
						else
							$('.restrictcntCollege').hide();
						showformCollege();
						resConCollege();

			});
			
		})
		
		

		
		$(function() {
			$('#addProjectBaseInfo').submit(
				function() {
					var str = $(this).serialize();
					$.get('../admin/projectBaseOpt_opProjectBaseInfoOpt.action?'+ str, function(data) {
						var Result = data.Result;
						var message = data.Message;
						if (Result == "OK") {
							window.top.location.reload();
						} else {
							$('.alert-danger').html(message).show();
						}
					})
					return false;
				});

		})
		
	</script>
<body>
</html>
