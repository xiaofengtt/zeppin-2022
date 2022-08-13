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

<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>
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
											<span onclick="delRess($(this),'protype')"
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
								<select class="span3" name="enrollType" id="enrollType">
									<option value="1" <s:if test="enrollType==1" >selected</s:if>>计划式报名</option>
									<option value="2" <s:if test="enrollType==2" >selected</s:if>>自主式报名</option>
								</select>
								<span <s:if test="enrollType!=2" >style="display: none;"</s:if> id="isAdvance">是否先报后分
								<input name="isAdvance" type="checkbox" data-text-label=""
									data-on="primary" data-off="default" data-on-label="是"
									data-off-label="否"
									<s:if test="isAdvance==1" >checked</s:if>
								value="1"	class="switch"></span>
							</div>
						</div>
						<div id="endtime" class="control-group" <s:if test="isAdvance!=1" >style="display: none;"</s:if>>
							<label class="control-label" for="">教师报名截止日期</label>
							<div class="controls">
								<input type="text" readonly data-provide="datepicker" class="datepicker"
									id="" name="endtime" value="<s:property value="endtime" />">
								
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">培训方式</label>
							<div class="controls">
								<select class="span3" name="traintype"  onchange="changeTraintype(this)">
									<option value="1" <s:if test="traintype==1" >selected</s:if>>集中面授（同步在线）</option>
									<option value="2" <s:if test="traintype==2" >selected</s:if>>远程培训</option>
									<option value="3" <s:if test="traintype==3" >selected</s:if>>混合培训方式</option>
								</select>
							</div>
						</div>
						<div class="control-group" id="fundsType1" <s:if test="traintype!=1 && traintype!=3">style="display:none"</s:if>>
							<label class="control-label" for="">集中培训经费标准</label>
							<div class="controls">
								<div class="input-append">
									<input type="text" id="" name="funds1" value="<s:property value="funds" />" placeholder=""> 
									<select id="fundsType1"  class="span2" name="fundsType1">
										<option value="1" <s:if test="fundsType==1">selected</s:if>>元/人/天</option>
										<option value="2" <s:if test="fundsType==2">selected</s:if>>元/人/年</option>
									</select>
								</div>
							</div>
						</div>
						<div class="control-group" id="fundsType2" <s:if test="traintype!=2">style="display:none"</s:if>>
							<label class="control-label" for="">网络研修经费标准</label>
							<div class="controls">
								<div class="input-append">
									<input type="text" id="" name="funds2" value="<s:property value="funds" />" placeholder=""> 
									<select id="fundsType2"  class="span2" name="fundsType2">
										<option value="1">元/人/学时</option>
									</select>
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
						<div class="control-group">
							<label class="control-label" for="">备注</label>
							<div class="controls">
								<input type="text" class="input-xxlarge" id="" name="remark"
									value="<s:property value="remark" />">
							</div>
						</div>
						<div class="control-group" style="width:600px">
							<label class="control-label" for="">上传红头文件</label>
							<div class="controls" >
								<div id="applyreddoc" class="">&nbsp;上传红头文件&nbsp;&nbsp;</div>
								
									<script>
										$("#applyreddoc").uploadFile({
											url:"../base/fileUpload_upload.action",
											allowedTypes:"doc,docx,pdf,zip",
											maxFileSize:1024*1024*50,
											fileName:"redHeadModel",
											maxFileCount : 1,
											dragDropStr: "<span><b>上传</b></span>",
											extErrorStr:"文件格式不正确，请上传word,pdf文档或zip压缩包类型的文件",
											showDone:false,
											showDelete : false,
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
									<p><a href="..<s:property value="redPath"/>"><s:property value=" redTitle"/></a>
									</div>
								</div>
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
											fileName:"applyReportModle",
											maxFileCount : 1,
											dragDropStr: "<span><b>上传</b></span>",
											extErrorStr:"文件格式不正确，请上传word文档类型的文件",
											showDone:false,
											showDelete : false,
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
										<div class="ufcwrapSubject" id="resConSubject" style="display: inline;">
											<select name="subjectRight" id="subjectRight" class="span3">
												<option value="-1">请选择</option>
												<s:iterator value="subjectList" id="s">
													<option value="<s:property value="#s.id" />" search="<s:property value="#s.searchString" />"><s:property value="#s.name" /></option>
												</s:iterator>
											</select>
										</div>
										<button type="button" onclick="selectSubject();"
											class="btn btn-default frbtn disabledbtnSubject"
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
										<div class="ufcwrapCollege" id="resConCollege" style="display: inline;">
											<select name="collegeRight" id="collegeRight" class="span3">
												<option value="-1">请选择</option>
												<s:iterator value="collegeList" id="c">
													<option value="<s:property value="#c.id" />" search="<s:property value="#c.searchString" />"><s:property value="#c.name" /></option>
												</s:iterator>
											</select>
										</div>
										<button type="button" onclick="selectCollege();"
											class="btn btn-default frbtn disabledbtnCollege"
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
						<button class="btn btn-primary btn-myself" type="submit">确定</button>
						<button id="colorboxcancel" onclick="parent.$.colorbox.close()"
							class="btn btn-default btn-myself" type="button">取消</button>
					</div>

				</div>
			</form>
		</div>

	</div>
	<script>
	function resUbtns(type) {
		var id = [];
		var names = [];
		type = (typeof (type) == 'undefined') ? '' : type;
		$('#resCon .usel').each(function() {
			if ($(this).attr('dataname')) {
				names.push($(this).attr('dataname'));
				id.push($(this).attr('dataid'));
			}
		});
		var lastid = id.pop();
		aClick = ' onclick="delRess($(this),\'' + type + '\')"';

		$('#resmtr').append(
				'<div class="mtc">' + names.join('<span class="sn"> &gt; </span>')
						+ '<span' + aClick + ' dataid="' + lastid
						+ '" class="uclose">&times;</span></div>');
		$('#resIds').append(
				'<input type="hidden" id="rightid_' + lastid
						+ '" name="restrictRightId" value="' + lastid + '">');
		showform();
		
		$('#projecttypecnt').hide();
		
	}
	function resCons(rid) {
		var cUrl = "../admin/projectType_getTypeListByPid";
		rid = (typeof (rid) == 'undefined') ? '' : rid;
		cUrl += (rid) ? '?id=' + rid : '?id=0';
		$
				.getJSON(
						cUrl,
						function(data) {

							if (data.length > 0) {
								var ufc = '<div class="ufc"><span class="usel" onclick="showul($(this))"><strong>请选择...</strong></span><ul class="uul">';
								$
										.each(
												data,
												function(i, c) {
													aClick = (c.haschild == 1) ? ' onclick="resCons(\''
															+ c.id
															+ '\');resspan($(this));"'
															: ' onclick="resspan($(this));resUbtns(\'protype\')"';
													ufc += '<li ' + aClick
															+ ' dataid="' + c.id
															+ '" dataname="'
															+ c.name + '">'
															+ c.name + '</li>';
												});
							}
							ufc += '</ul></div>';
							$("#resCon").append(ufc);
						});
		// $('.resBtn').prop('disabled', true).addClass('disabledbtn');
	}
	
	function delRess(t, type) {
		type = (typeof (type) == 'undefined') ? '' : type;
		t.parent().remove();
		$('#rightid_' + t.attr('dataid')).remove();
		if (type == "protype") {
			$('#projecttypecnt').show();
			showform();
			resCons();
		} 
	}
	
	function deleteSubject(){
		t.parent().remove();
		$('#subjectid_' + t.attr('dataid')).remove();
	}
	function deleteCollege(){
		t.parent().remove();
		$('#collegeid_' + t.attr('dataid')).remove();
	}
	function selectSubject(){
		var lastid = $('#subjectRight').val();
		if(lastid>0){
			var lastname = $('#subjectRight').find("option:selected").text();
			aClick = ' onclick="deleteSubject($(this))"';
	
			if ($('input[name="restrictSubjectId"]').length > 0) {// 避免添加相同的权限
				var subjectid_array = [];
	
				$('input[name="restrictSubjectId"]').each(function() {
					subjectid_array.push($(this).val());
				});
				if ($.inArray(lastid.toString(), subjectid_array) > -1) {
					alert('已存在该学科');
					return;
				}
			}
			$('#subjectresmtr').append(
					'<div class="subjectmtc">' + '<span class="sn">'+lastname+'</span>'
					+ '<span' + aClick + ' dataid="' + lastid
					+ '" class="uclose">&times;</span></div>');
			$('#subjectIds').append(
					'<input type="hidden" id="subjectid_' + lastid
							+ '" name="restrictSubjectId" value="' + lastid + '">');
		}
	}
	function selectCollege(){
		var lastid = $('#collegeRight').val();
		if(lastid>0){
			var lastname = $('#collegeRight').find("option:selected").text();
			aClick = ' onclick="deleteCollege($(this))"';
	
			if ($('input[name="restrictCollegeId"]').length > 0) {// 避免添加相同的权限
				var collegeid_array = [];
	
				$('input[name="restrictCollegeId"]').each(function() {
					collegeid_array.push($(this).val());
				});
				if ($.inArray(lastid.toString(), collegeid_array) > -1) {
					alert('已存在该承训院校');
					return;
				}
			}
			$('#collegeresmtr').append(
					'<div class="collegemtc">' + '<span class="sn">'+lastname+'</span>'
					+ '<span' + aClick + ' dataid="' + lastid
					+ '" class="uclose">&times;</span></div>');
			$('#collegeIds').append(
					'<input type="hidden" id="collegeid_' + lastid
							+ '" name="restrictCollegeId" value="' + lastid + '">');
		}
	}
	function changeTraintype(t){
		var traintype = $(t).val();
		if(traintype==1 || traintype==3){
			$('#fundsType1').css("display","block");
			$('#fundsType2').css("display","none");
		}else{
			$('#fundsType1').css("display","none");
			$('#fundsType2').css("display","block");
		}
	}
	
	$(function() {
		$('#subjectRight,#collegeRight').select2();
		$("#subjectRight").change(function(){
			$('.disabledbtnSubject').removeAttr("disabled");
		});
		$("#collegeRight").change(function(){
			$('.disabledbtnCollege').removeAttr("disabled");
		});
		$('.switch').bootstrapSwitch();
		$(document).ready(function() {
			var restrictSubject = <s:property value="restrictSubject" />;
			var restrictCollege = <s:property value="restrictCollege" />;
			if(restrictSubject=="1"){
				$('.restrictcntSubject').show();
			}
			if(restrictCollege=="1"){
				$('.restrictcntCollege').show();
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
				resCons();

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
			});
// 			changeTraintype();s

			$('#isAdvance').on('switch-change',
					function(e, data) {
						var $element = $(data.el), value = data.value;
						if (value)
							$('#endtime').show();
						else
							$('#endtime').hide();
					});
			
			$('select[name="enrollType"]').change(function(){
				var enrolltype = $('#enrollType').val();
				if(enrolltype == 2){
					$('#isAdvance').show();
				}else{
					$('#isAdvance').hide();
				}
			})
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
