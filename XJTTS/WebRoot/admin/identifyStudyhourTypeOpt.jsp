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
.form-horizontal .control-label {
	width: 160px;
	color: gray;
	font-weight: bold;
	white-space:nowrap;
}
.form-horizontal .controls {
	margin-left: 180px;
}
</style>
<script type="text/javascript" src="../js/layer-v2.2/layer/layer.js"></script>
<div class="main">
	<div class="ifrcnt container">
		<div class="hd">
			<h3>添加</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form id="addProjectBaseInfo" class="form-horizontal" role="form"
				method="post">
				<input type="hidden" name="id" value="<s:property value="id" />">
				<div class="clearfix" style="margin-left:100px">
					<div class="control-group">
						<label class="control-label" for="">项目类型</label>
						
						<div class="controls">
							<div class="companylocation">
								<span class="clId" id="clId" style="height: 22px;" onclick="getsnode();changeClbtn($(this));"></span>
								<div id="clListBox" class="listSub">
									<div id="clList" class="list_sub sm_icon">
										<div id="bido"></div>
									</div>
								</div>
							</div>

							<input type="hidden" id="organization" name="projectTypeId"
								value="" placeholder="项目类型">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="">项目年份</label>
						<div class="controls">
							<select class="span3" name="year" onchange="getprojects();">
								<option value="0">全部</option>
								<s:iterator value="yearArray" id="ya">
									<option value="<s:property value="#ya" />"><s:property
											value="#ya" /></option>
								</s:iterator>
							</select>
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="">项目名称</label>
						<div class="controls">
							<select class="span3" name="projectId">
								<option value="0">全部</option>
							</select>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="">培训学科</label>
						<div class="controls">

							<div class="row">
								<div class="restrictcntSubject clearfix span6">
									<div class="ufcwrapSubject" id="resConSubject" style="display: inline;">
										<select name="trainingSubjectId" id="subjectRight" class="">
											<option value="0">请选择</option>
											<s:iterator value="subjectList" id="s">
												<option value="<s:property value="#s.id" />" search="<s:property value="#s.searchString" />"><s:property value="#s.name" /></option>
											</s:iterator>
										</select>
									</div>
								</div>
							</div>
						</div>
					</div>						
					<s:iterator value="studyHourList" id="study">
						<div class="control-group">
							<label class="control-label" for=""><s:property value="#study.nameCN" /></label>
							<div class="controls">

								<input class="input input2" type="number" 
									name="<s:property value="#study.name" />" placeholder="">
							</div>
						</div>		
					</s:iterator>
					<div class="control-group">
							<label class="control-label" for="">培训总学分</label>
							<div class="controls">

								<input class="input input6" type="text" name="credit" 
									value="" placeholder="">
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
	</div>
	<script>

	var projectCycle = url("?projectCycle") != null ? url("?projectCycle") : "";
	function getprojects(pt) {
		var year = $('select[name="year"]').val();
		var projectType = pt == null ? $('#organization').val() : pt;
		$.getJSON('../admin/identifyClasshours_getProjectsByPramers.action', {
			year : year,
			projectType : projectType
		}, function(r) {
			if (r.length > 0) {
				var ufc = '';
				$.each(r,
						function(i, c) {
							ufc += '<option value="'+ c.id +'">' + c.name
									+ '</li>';
						});
				$('select[name="projectId"]').html(ufc);
			} else {
				$('select[name="projectId"]').html('<option>无数据</option>');
			}
		})
	}
	
	
	$(function() {		
		$('#subjectRight,#collegeRight').select2();
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
			
			//项目类型 helper 函数
			$(document).on("click",function(a) {
				if(!$(a.target).parents().is('.ufc'))
					$('.uul').hide();
				if(!$(a.target).parents().is('.companylocation'))
					$('.listSub').hide();
			});

			
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
	function tirm3(value) {
		var values =$(value).val().replace(/(^\s*)|(\s*$)/g, "");
		if (values == "0") {
			$(value).parent().css({
				"border-color" : "#f00",
				"box-shadow" : "none"
			}).parent().parent().find("b").html("请选择年份");
			return false;
		} else {
			$(value).removeAttr("style").parent().parent().find("b").html("");
			return true;
		}
	}
		
		$(function() {
			$('#addProjectBaseInfo').submit(
				function() {
					var rs7 = tirm3('select[name="year"] option:selected');
					var rs1 = $("#organization").val();
					if(rs1==""){
						layer.msg("请选择项目类型");
						return false;
					}
					if(!rs7){
						$('select[name="year"]').css({"border-color":"#f00","box-shadow":"none"}).parent().parent().find("b").html("请选择年份");
						flag=false;
					}
						var str = $(this).serialize();
						console.log(str)
						$.get('../admin/identifyStudyhour_add.action?method=add&projectCycleId='+projectCycle+'&'+ str, function(data) {
							var Result = data.status;
							var message = data.message;
							if (Result == "OK") {
								layer.msg("添加成功",{
				        			  icon: 1,
				        			  time: 2000 
				        			},function(){
									window.top.location.reload();
								});
								
							} else {
								layer.confirm(message, {
									  btn: ['确定'] //按钮
									}, function(){
									  layer.closeAll();
									});
// 								$('.alert-danger').html(message).show();
							}
						}) 
					return false;
				});

		})
		function changeClbtn(e) {
			e.next('.listSub').toggle();
		}
		// 工作单位 请求子元素
		function getsnode(bid) {
			var cUrl = "../admin/projectType_getListByPid";
			bid = (typeof (bid) == 'undefined') ? '' : bid;
			var e = (bid) ? $('#bido' + bid) : $('#bido');
			cUrl += (bid) ? '?id=' + bid : '?id=0';
			if (bid)
				e.css('display') == 'none' ? e.show() : e.hide();
				$.getJSON(cUrl,
				function(data) {
					var cLis = '没有找到';
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
										+ c.name + '\');getprojects(\''+c.id+'\')"';
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
										+ '" class="cSub" style="display:none">加载中...</div></div>';
		
							});
					}
					e.html(cLis)
			});
		}
	</script>
