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
			<h3>补录项目申报结果</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form id="addProjectApply" class="form-horizontal" role="form"
				action="../admin/projectApplyOpt_addProjectApply.action"
				method="post">
				<input type="hidden" name="id" value="<s:property value="id" />">
				<div class="clearfix">
					<div class="span10">
						<div class="control-group">
							<label class="control-label" for="">项目年份</label>
							<div class="controls">
								<select class="span3" name="year" onchange="getprojects();">
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
									<!--button type="button" onclick="resUbtn('protype');getprojects()"
										class="btn btn-default frbtn resBtn disabledbtn"
										disabled="true">确认</button-->
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
						</div>

						<div class="control-group">
							<label class="control-label" for="">项目名称</label>
							<div class="controls">
								<select class="span3" name="project">
									<option value="0">全部</option>
									<s:iterator value="lsProject" id="ya">
										<option <s:if test="%{projectApply.project.id==#ya.getId()}">selected</s:if>  value="<s:property value="#ya.getId()" />" ><s:property
												value="#ya.getName()" /></option>
									</s:iterator>
								</select>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">学科</label>
							<div class="controls">
								<select class="span3" name="trainingSubject">
									<option value="0">全部</option>
								</select>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">承训单位</label>
							<div class="controls">
								<select class="span3" name="trainingCollege">
								</select>
							</div>
						</div>
						<s:if test="projectApply.project.enrollType == 2">
						<div class="control-group">
							<label class="control-label" for="">报名截止日期</label>
							<div class="controls">
								<div class="input-daterange input-group" id="datepicker">
									<input type="text" readonly class="input-sm form-control"
										name="enrollEndTime" value="<s:date name="projectApply.enrollEndTime" format="yyyy-MM-dd"/>">
										
								</div>


							</div>
						</div>
						</s:if>

						<div class="control-group">
							<label class="control-label" for="">培训时间</label>
							<div class="controls">
								<div class="input-daterange input-group" id="datepicker">
									<input type="text" readonly class="input-sm form-control"
										name="trainingStarttime" value="<s:date name="projectApply.trainingStarttime" format="yyyy-MM-dd"/>"> <span
										class="input-group-addon"> 至 </span> <input type="text" readonly 
										class="input-sm form-control" name="trainingEndtime"  value="<s:date name="projectApply.trainingEndtime" format="yyyy-MM-dd"/>">
								</div>


							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">培训课时</label>
							<div class="controls">
								<div class="input-append">
									<input type="text" id="" name="trainingClasshour"
										placeholder="" value="<s:property value="projectApply.trainingClasshour"/>">
								</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">培训人数</label>
							<div class="controls">

								<input type="text" id="" name="approveNumber" placeholder="" value="<s:property value="projectApply.approveNumber"/>">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">联系人</label>
							<div class="controls">
								<input type="text" id="" name="contacts" value="<s:property value="projectApply.contacts"/>">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">联系电话</label>
							<div class="controls">
								<input type="text" id="" name="phone" value="<s:property value="projectApply.phone"/>">
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
		function getprojects() {
			var year = $('select[name="year"]').val();
			var projectType = ($('input[name="restrictRightId"]').val()) ? $(
					'input[name="restrictRightId"]').val() : '';
			$.getJSON('../admin/projectBase_getProjectsByPramers.action', {
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
			// if (!id) {
				$('#projecttypecnt').show();
				resCon3();

			//}

			//获取学科
			$
					.getJSON(
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
						var str = $(this).serialize();
						$
								.get(
										'../admin/projectApplyOpt_addProjectApply.action?'
												+ str, function(data) {
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

			//项目类型 helper 函数
			$(document).on("click", function(e) {
				if (!$(e.target).parents().is('.ufc'))
					$('.uul').hide();
			});

		})
	</script>
<body>
</html>
