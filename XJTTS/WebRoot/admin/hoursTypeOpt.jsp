<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!-- <link rel="stylesheet" href="../css/app.css">	 -->
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" >
<link href="../css/colorbox.css" rel="stylesheet" type="text/css">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery-ui.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/bootstrap-paginator.min.js"></script>
<script src="../js/bootstrap.js"></script>
<script src="../js/url.min.js"></script>
<script src="../js/app.js"></script> 

<link rel="stylesheet" href="../css/bootstrap2.css">
<link rel="stylesheet" href="../css/uploadfile.css">
<link href="../css/datepicker3.css" rel="stylesheet">
<link href="../css/bootstrap-switch.min.css" rel="stylesheet">


<script src="../js/iframe.js"></script>
<script src="../js/bootstrap-datepicker.js"></script>
<script src="../js/bootstrap-datepicker.zh-CN.js"></script>
<script src="../js/upload/jquery.uploadfile.min.js"></script>
<script src="../js/bootstrap-switch.min.js"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>
<link rel="stylesheet" href="../css/iframe.css">
<link rel="stylesheet" href="../css/projectBaseInfoOpt.css">
<style>
input{height:34px !important;}
select{height:34px !important;}
.ajax-file-upload{height:30px !important;}
h5{width:120px;text-align:right;color:#000;font-weight:bold;padding-bottom:20px;font-size:16px;}
.offset7{margin-left:360px;}
.container, .navbar-static-top .container, .navbar-fixed-top .container, .navbar-fixed-bottom .container{width:100%;}
.navbar{margin-bottom:0;}
 .select2-container .select2-choice > .select2-chosen{line-height:34px;}
.restrictcntSubject{display:block;border: 0px solid #428bca;}
.ifrcnt .hd h3{border-bottom:1px solid #e5e5e5;font-size:15px;font-weight:normal;line-height:22px;padding:15px;}
.restrictcntSubject{margin-left:10px;}
#calListBox{background:#fff;display:none;}
</style>
<script type="text/javascript" src="../js/layer-v2.2/layer/layer.js"></script>
<div class="main">
	<div class="ifrcnt container">
		<div class="hd">
			<h3>??????</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form id="addProjectBaseInfo" class="form-horizontal" role="form"
				action="../admin/projectBaseOpt_opProjectBaseInfoOpt.action"
				method="post">
				<input type="hidden" name="id" value="<s:property value="id" />">
				<div class="clearfix" style="margin-left:100px">

						<div class="control-group">
							<label class="control-label" for="">????????????</label>
							
							<div class="controls">
								<div class="companylocation">
									<span class="clId" id="clId" style="height: 22px;" onclick="getsnode();changeClbtn($(this));"><s:property
											value="organizationName" /></span>
									<div id="clListBox" class="listSub">
										<div id="clList" class="list_sub sm_icon">
											<div id="bido"></div>
										</div>
									</div>
								</div>

								<input type="hidden" id="organization" name="projectTypeId"
									value="<s:property	value="organization" />" placeholder="????????????">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">????????????</label>
							<div class="controls">

								<div class="row">
									<div class="restrictcntSubject clearfix span6">
										<div class="ufcwrapSubject" id="resConSubject" style="display: inline;">
											<select name="trainingSubjectId" id="subjectRight" class="">
												<option value="-1">?????????</option>
												<s:iterator value="subjectList" id="s">
													<option value="<s:property value="#s.id" />" search="<s:property value="#s.searchString" />"><s:property value="#s.name" /></option>
												</s:iterator>
											</select>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">??????????????????</label>
							<div class="controls">

								<input class="input input1" type="text" id="" 
									value="<s:property value="number"/>" placeholder="">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">??????????????????</label>
							<div class="controls">

								<input class="input input2" type="text" id=""
									value="<s:property value="number" />" placeholder="">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">??????????????????</label>
							<div class="controls">

								<input class="input input3" type="text" id="" 
									value="<s:property value="number" />" placeholder="">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">??????????????????</label>
							<div class="controls">

								<input class="input input4" type="text" id="" 
									value="<s:property value="number" />" placeholder="">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">???????????????</label>
							<div class="controls">

								<input class="input input5" type="text" id="" 
									value="<s:property value="number" />" placeholder="">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">???????????????</label>
							<div class="controls">

								<input class="input input6" type="text" id="" 
									value="<s:property value="number" />" placeholder="">
							</div>
						</div>

				</div>

				<div class="row actionbar">
					<div class="offset7">
						<button class="btn btn-primary btn-myself" type="submit">??????</button>
						<button id="colorboxcancel" onclick="parent.$.colorbox.close()"
							class="btn btn-default btn-myself" type="button">??????</button>
					</div>

				</div>
			</form>
		</div>

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
						+ '" name="projectTypeId" value="' + lastid + '">');
		showform();
		
		$('#projecttypecnt').hide();
		
	}
	
	function setCategory(id, name) {
		$('#cabido').html('');
		$('#calId').html(name);
		$('#category').val(id);
		$('.listCate').hide();
	}
	
	function changeCategorybtn(e) {
		e.next('.listCate').toggle();
	}
	
	
	function delRess(t, type) {
		type = (typeof (type) == 'undefined') ? '' : type;
		t.parent().remove();
		$('#rightid_' + t.attr('dataid')).remove();
		if (type == "protype") {
			$('#projecttypecnt').show();
			showform();
		} 
	}
	
	function deleteSubject(t){
		t.parent().remove();
		$('#subjectid_' + t.attr('dataid')).remove();
	}
	function deleteCollege(t){
		t.parent().remove();
		$('#collegeid_' + t.attr('dataid')).remove();
	}
	function selectSubject(){
		var lastid = $('#subjectRight').val();
		if(lastid>0){
			var lastname = $('#subjectRight').find("option:selected").text();
			aClick = ' onclick="deleteSubject($(this))"';
	
			if ($('input[name="restrictSubjectId"]').length > 0) {// ???????????????????????????
				var subjectid_array = [];
	
				$('input[name="restrictSubjectId"]').each(function() {
					subjectid_array.push($(this).val());
				});
				if ($.inArray(lastid.toString(), subjectid_array) > -1) {
					alert('??????????????????');
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
			$(".input").blur(function(){
				var value = $(this).val();
				value=value.replace(/(^\s*)|(\s*$)/g, "");
				var reg=/^\d+$/; 
				if(reg.test(value)==true){
					$(this).removeAttr("style");
				    return true;
				}else{
					if(value==""){
						$(this).removeAttr("style");
					    return true;
					}else{
						$(this).css({"border-color":"#f00","box-shadow":"none"});
						layer.confirm('??????????????????????????????????????????', {
							  btn: ['??????'] //??????
							}, function(){
							  layer.closeAll();
							});
					    return false;
					}
					
				}
			})
		});
		
		
			var id = $('input[name="id"]').val();
			if (!id) {
				$('#projecttypecnt').show();

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
			
			//???????????? helper ??????
			$(document).on("click",function(a) {
				if(!$(a.target).parents().is('.ufc'))
					$('.uul').hide();
				if(!$(a.target).parents().is('.companylocation'))
					$('.listSub').hide();
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
// 			changeTraintype();
		})
		
		
function tirm(value){
		var values=value.replace(/(^\s*)|(\s*$)/g, "");
			var reg=/^\d+$/; 
			if(reg.test(values)==true){
			    return true;
			}else{
				if(values==""){
					return true;
				}else{
					$(this).css({
						"border-color" : "#f00",
						"box-shadow" : "none"
					});
					return false;
				}			
			}
		}
		
		$(function() {
			$('#addProjectBaseInfo').submit(
				function() {
					var rs1=tirm($(".input1").val().replace(/(^\s*)|(\s*$)/g, ""));
					var rs2=tirm($(".input2").val().replace(/(^\s*)|(\s*$)/g, ""));
					var rs3=tirm($(".input3").val().replace(/(^\s*)|(\s*$)/g, ""));
					var rs4=tirm($(".input4").val().replace(/(^\s*)|(\s*$)/g, ""));
					var rs5=tirm($(".input5").val().replace(/(^\s*)|(\s*$)/g, ""));
					var rs6=tirm($(".input6").val().replace(/(^\s*)|(\s*$)/g, ""));
					if(rs1&&rs2&&rs3&&rs4&&rs5&&rs6){
						var str = $(this).serialize();
						$.get('../admin/identifyClasshours_specialAdd.action?method=add&'+ str+'&centralizeCH='+$(".input1").val().replace(/(^\s*)|(\s*$)/g, "")+'&informationCH='+$(".input2").val().replace(/(^\s*)|(\s*$)/g, "")+'&regionalCH='+$(".input3").val().replace(/(^\s*)|(\s*$)/g, "")+'&schoolCH='+$(".input4").val().replace(/(^\s*)|(\s*$)/g, "")+'&totalhours='+$(".input5").val().replace(/(^\s*)|(\s*$)/g, "")+'&credit='+$(".input6").val().replace(/(^\s*)|(\s*$)/g, ""), function(data) {
							var Result = data.status;
							var message = data.message;
							if (Result == "OK") {
								window.top.location.reload();
							} else {
								layer.confirm(message, {
									  btn: ['??????'] //??????
									}, function(){
									  layer.closeAll();
									});
// 								$('.alert-danger').html(message).show();
							}
						})
					}else{
						layer.confirm('??????????????????????????????????????????', {
							  btn: ['??????'] //??????
							}, function(){
							  layer.closeAll();
							});
					}
					return false;
				});

		})
function changeClbtn(e) {
	e.next('.listSub').toggle();
}

// ???????????? ???????????????
function getsnode(bid) {
	var cUrl = "../admin/projectType_getListByPid";
	bid = (typeof (bid) == 'undefined') ? '' : bid;
	var e = (bid) ? $('#bido' + bid) : $('#bido');
	cUrl += (bid) ? '?id=' + bid : '?id=0';
	if (bid)
		e.css('display') == 'none' ? e.show() : e.hide();
		$.getJSON(cUrl,
		function(data) {
			var cLis = '????????????';
			if (data.length > 0) {
				var cLis = '';
				$.each(
					data,
					function(i, c) {
						emClass = (c.haschild == 1) ? ' class="c"'
								: '';
						emClick = (c.haschild == 1) ? ' onclick="getsnode(\''
								+ c.id
								+ '\');changeIcon($(this))"'
								: '';
						aClick = (c.haschild == 1) ?' onclick="getsnode(\''
								+ c.id
								+ '\');changeIcon($(this))"':' onclick="setnode(\''
								+ c.id + '\', \''
								+ c.name + '\')"';
						cLis += '<div class="listnode" id="'
								+ c.id
								+ '"><em'
								+ emClass
								+ emClick
								+ '></em><a href="javascript:void(0)" '
								+ aClick
								+ '>'
								+ c.name
								+ '</a><div id="bido'
								+ c.id
								+ '" class="cSub" style="display:none">?????????...</div></div>';

					});
			}
			e.html(cLis)
	});
}
	</script>
