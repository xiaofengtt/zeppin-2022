<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="../css/bootstrap2.css">
<link rel="stylesheet" href="../css/iframe.css">
<link href="../css/datepicker3.css" rel="stylesheet">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/iframe.js"></script>
<script src="../js/bootstrap-datepicker.js"></script>
<script src="../js/bootstrap-datepicker.zh-CN.js"></script>
</head>
<body>
	<div class="ifrcnt container">
		<div class="hd">
			<h3>编辑多阶段项目信息</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form id="addProjectApply" class="form-horizontal" role="form"
				action="../admin/projectBase_addStageProject.action"
				method="post">
				<input type="hidden" name="id" value="<s:property value="id" />">
				<div class="clearfix">
					<div class="span10">
						<div class="control-group" id="years" style="margin-left: 200px;">
							<label class="control-label" for="">项目年份</label>
							<div class="controls">
								<select class="span3" name="year" onchange="getprojects();">
									<s:iterator value="searchYear" id="ya">
										<option <s:if test="year==#ya" >selected</s:if>
											value="<s:property value="#ya" />"><s:property
												value="#ya" /></option>
									</s:iterator>
								</select>
							</div>
							<div class="row">
								<div class="" style="margin-left: 170px; margin-top: 20px">
									<button id="colorboxcancel" onclick="parent.$.colorbox.close()"
											class="btn btn-default" type="button">取消</button>
									<button class="btn btn-primary" type="button" onclick="next1();">下一步</button>
								</div>
			
							</div>
						</div>
						
						<div class="control-group" id="projecttype" style="margin-left: 200px;display: none;">
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
											<span onclick="delRes($(this),'proapply')"
												dataid="<s:property value="restrictRightList[restrictRightList.length-1]" />"
												class="uclose">&times;</span>
										</div>
									</s:if>
								</div>

								<div id="resIds" class="hidden">
									<s:if test="restrictRightId!=null">

										<input type="hidden"
											id="rightid_<s:property value="restrictRightId" />"
											name="restrictRightId"
											value="<s:property value="restrictRightId" />">

									</s:if>
								</div>

							</div>
							<div class="row">
								<div class="" style="margin-left: 170px; margin-top: 20px">
									<button class="btn btn-primary" type="button" onclick="renext2();">上一步</button>
									<button class="btn btn-primary" type="button" onclick="next2();">下一步</button>
								</div>
			
							</div>
						</div>
						
						<div style="margin-left: 200px;display: none;" id="projectname">
							<div class="control-group">
								<label class="control-label" for="">项目名称</label>
								<div class="controls">
									<select class="span3" name="project" onchange="getSubjects();">
										<option value="0">请选择</option>
										<s:iterator value="lsProject" id="ya">
											<option <s:if test="%{projectApply.project.id==#ya.getId()}">selected</s:if>  value="<s:property value="#ya.getId()" />" ><s:property
													value="#ya.getName()" /></option>
										</s:iterator>
									</select>
								</div>
							</div>
	
							<!-- 先绑定，后显示 -->
							<div class="control-group" id="subjects" style="display: none;">
							<div>
								<label class="control-label" for="">培训学科及承训单位</label>
								</div>
								<div class="restrictcnt" style="margin-top: 30px;">
									<ul id="projectApplies" style="margin-left: 110px">
									
									</ul>
								</div>
								
							</div>
	
							<div class="row">
									<div class="" style="margin-left: 170px; margin-top: 20px">
										<button class="btn btn-primary btn-myself" type="button" onclick="renext3();">上一步</button>
										<button class="btn btn-primary btn-myself" type="submit" id="submitButton">确定</button>
										<button id="colorboxcancel" onclick="parent.$.colorbox.close()"
											class="btn btn-default btn-myself" type="button">取消</button>
									</div>
				
							</div>
						</div>
						
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
	
	
		function next1(){
			var year = $('select[name="year"]').val();
			if(year == '请选择'){
				$('.alert-danger').html("请选择年份").show();
				return;
			}
			$('#years').hide();
			$('#projecttype').show();
			$('.alert-danger').html('').hide();
		}
		function next2(){
			$('#projecttype').hide();
			$('#projectname').show();
			$('.alert-danger').html('').hide();
		}
		function renext2(){
			$('.alert-danger').html('').hide();
			$('#years').show();
			$('#projecttype').hide();
		}
		function renext3(){
			$('.alert-danger').html('').hide();
			$('#projectname').hide();
			$('#subjects').hide();
			$('#projecttype').show();
		}
		
		function CheckAll(val) { 
				$("input[name='projectApplys']").each(function() { 
				this.checked = val; 
		 	}); 
		 } 
		function getprojects() {
			var year = $('select[name="year"]').val();
			var projectType = ($('input[name="restrictRightId"]').val()) ? $(
					'input[name="restrictRightId"]').val() : '';
			$.getJSON('../admin/projectBase_getProjectsByPramer.action', {
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
					$('select[name="project"]').html(ufc);
				} else {
					$('select[name="project"]').html('<option>无数据</option>');
					$('#subjects').hide();
					$('.restrictcnt').hide();
				}
			})
		}

		function getSubjects() {
			$('.alert-danger').html('').hide();
			var projectId = $('select[name="project"]').val();
			$.getJSON('../admin/projectBase_getprojectApply.action', {
				projectId : projectId
			}, function(r) {
				if(r.status == 'ERROR'){
					$('#subjects').hide();
					$('.restrictcnt').hide();
					return;
				}
				if (r.records.length > 0) {
					var ufc = '<li><input type="checkbox" id="checkall" name="checkall" onclick="CheckAll(this.checked)" value="" />全选</li>';
					$.each(r.records,
							function(i, c) {
								ufc += '<li><input type="checkbox" name="projectApplys" value="'+c.id+'">'+c.paStr+'</li>'
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
		
		$(function() {

			$('.input-daterange').datepicker({
				language : "zh-CN",
				format : 'yyyy-mm-dd',
				startDate : "1d",
				autoclose : true

			})

			var id = $('input[name="id"]').val();
			$('#projecttypecnt').show();
			resCons();

			//获取学科
			$.getJSON(
					'../admin/trainingSubject_getTrainingSubject.action?type=1&jtStartIndex=0&jtPageSize=1000',
					function(r) {
						if (r.Records.length > 0) {
							var ufc = '';
							var tsid = '<s:property value="projectApply.trainingSubject.id" />';
							$.each(r.Records, function(i, c) {
								if(tsid==c.id){
									ufc += '<option value="'+ c.id +'" selected>'
									+ c.name + '</li>';
								}
								else
									{
									ufc += '<option value="'+ c.id +'" >'
									+ c.name + '</li>';
									}
								
							});
							$('select[name="trainingSubject"]').html(
									ufc);
						}
					});
							
							
			$.getJSON('../admin/trainingCollege_getTrainingCollegeListByProject.action?type=1&projectId=0', function(r) {
						if (r.Records.length > 0) {
							var ufc = '';
							var tsid='<s:property value="projectApply.trainingCollege.id" />';
							
							$.each(r.Records, function(i, c) {
								if(tsid==c.id){
									ufc += '<option value="'+ c.id +'" selected>'
									+ c.name + '</li>';
								}
								else
									{
									ufc += '<option value="'+ c.id +'" >'
									+ c.name + '</li>';
									}
								
							});
								
							$('select[name="trainingCollege"]').html(ufc);
						}
					});

		})

		$(function() {
			$('#addProjectApply').submit(
					function() {
						var projectId = $('select[name="project"]').val();
						if(typeof(projectId) == "undefined" || projectId == "0"){
							$('.alert-danger').html("请选择项目").show();
							return false;
						}
// 						var size = $("input[name='projectApplys']").length;
// 						if(typeof(size) == "undefined" || size == 0){
// 							$('.alert-danger').html("请选择学科及承训单位").show();
// 							return false;
// 						}
						if(!$("input[name='projectApplys']").is(':checked')){
							$('.alert-danger').html("请选择学科及承训单位").show();
							return false;
						}
						$('#submitButton').html('处理中。。。');
						$('#submitButton').attr("disabled", true);
						$('#colorboxcancel').attr("disabled", true);
						var str = $(this).serialize();
						str += '&method=add';
						$.get('../admin/projectBase_addStageProject.action?'+ str, function(data) {
							var Result = data.Result;
							var message = data.Message;
							if (Result == "OK") {
								
								$('#submitButton').html('确定');
								alert('操作成功');
								$('#submitButton').attr("disabled", false);
								$('#colorboxcancel').attr("disabled", false);
								window.top.location.reload();
							} else {
								$('.alert-danger').html(message).show();
								$('#submitButton').html('确定');
								$('#submitButton').attr("disabled", false);
								$('#colorboxcancel').attr("disabled", false);
							}
						})
						return false;
					});

			//项目类型 helper 函数
			$(document).on("click", function(e) {
				if (!$(e.target).parents().is('.ufc'))
					$('.uul').hide();
			});

		})
	</script>
<body>
</html>
