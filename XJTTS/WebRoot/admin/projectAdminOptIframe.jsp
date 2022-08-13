<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title>添加项目管理员</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="../css/bootstrap2.css">
<link rel="stylesheet" href="../css/iframe.css">
<link href="../css/bootstrap-switch.min.css" rel="stylesheet">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/iframe.js"></script>
<script src="../js/bootstrap-switch.min.js"></script>
</head>
<body>
	<div class="ifrcnt container">
		<div class="hd">
			<h3>添加项目管理员</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form id="addprojectAdmin" class="form-horizontal" role="form"
				action="../admin/projectAdm_opProjectAdmin.action" method="post">
				<input type="hidden" name="id" value="<s:property	value="id" />">
				<div class="clearfix">

					<div class="span5">
						<div class="control-group">
							<label class="control-label" for="">姓名</label>
							<div class="controls">
								<input type="text" id="" name="name"
									value="<s:property	value="name" />" placeholder="姓名">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">身份证号</label>
							<div class="controls">
								<input type="text" id="" name="idcard"
									value="<s:property	value="idcard" />" placeholder="身份证号">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">所属地区</label>
							<s:set name="retArea" value="area" />
							<div id="foriebug" class="controls">
								<select onchange="areacityy(this,'city')" style="width:90px;"
									class="span1" name="area">
									<s:iterator value="provinceIds" id="pro">
										<option value="<s:property value="#pro[0]" />"
											<s:if test="#retArea[0]==#pro[0]" >selected</s:if>><s:property
												value="#pro[1]" /></option>
									</s:iterator>

								</select> <select id="areacity" style="width:80px;"
									onchange="areacityy(this,'county')" class="span1" name="area">
									<s:iterator value="cityIds" id="pro">
										<option value="<s:property value="#pro[0]" />"
											<s:if test="#retArea[1]==#pro[0]" >selected</s:if>><s:property
												value="#pro[1]" /></option>
									</s:iterator>

								</select> <select id="areacounty" style="width:80px;" class="span1"
									name="area">
									<s:iterator value="countyIds" id="pro">
										<option value="<s:property value="#pro[0]" />"
											<s:if test="#retArea[2]==#pro[0]" >selected</s:if>><s:property
												value="#pro[1]" /></option>
									</s:iterator>

								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">工作单位</label>
							<div class="controls">
								<div class="companylocation">
									<span class="clId" id="clId"
										onclick="getsnode();changeClbtn($(this));"><s:property
											value="organizationName" /></span>
									<div id="clListBox" class="listSub">
										<div id="clList" class="list_sub sm_icon">
											<div id="bido"></div>
										</div>
									</div>
								</div>

								<input type="hidden" id="organization" name="organization"
									value="<s:property	value="organization" />" placeholder="工作单位">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">工作职务</label>
							<div class="controls">
								<input type="text" id="" name="jobDuty"
									value="<s:property	value="jobDuty" />">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">地址</label>
							<div class="controls">
								<input type="text" id="" name="address"
									value="<s:property	value="address" />">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">传真</label>
							<div class="controls">

								<input type="text" id="" name="fax"
									value="<s:property	value="fax" />">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">是否可创建用户</label>
							<div class="controls">
								<input name="createuser" type="checkbox" data-text-label=""
									data-on="primary" data-off="default" data-on-label="是"
									data-off-label="否" <s:if test="createuser==1" >checked</s:if>>
							</div>
						</div>

					</div>

					<div class="span5">
						<div class="control-group">
							<label class="control-label" for="">性别</label>
							<div class="controls">
								<select name="sex">
									<option value="1" <s:if test="sex==1" >selected</s:if>>男</option>
									<option value="2" <s:if test="sex==2" >selected</s:if>>女</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">手机</label>
							<div class="controls">
								<input type="text" id="" name="mobile"
									value="<s:property	value="mobile" />">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">民族</label>
							<div class="controls">
								<select name="ethnic">
									<s:iterator value="listEthnic" id="et">
										<option value="<s:property value="#et.getId()" />"
											<s:if test="#et.getId()==ethnic" >selected</s:if>><s:property
												value="#et.getName()" /></option>
									</s:iterator>
								</select>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">工作部门</label>
							<div class="controls">
								<input type="text" id="" name="department"
									value="<s:property value="department" />">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">邮箱</label>
							<div class="controls">
								<input type="text" id="" name="email"
									value="<s:property value="email" />">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">办公电话</label>
							<div class="controls">
								<input type="text" id="" name="phone"
									value="<s:property value="phone" />">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">状态</label>
							<div class="controls">
								<select name="status">
									<option value="1" <s:if test="status==1" >selected</s:if>>正常</option>
									<option value="2" <s:if test="status==2" >selected</s:if>>停用</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="clearfix">
					<div class="span10">
						<div class="control-group">
							<label class="control-label" for="">备注</label>
							<div class="controls">
								<input class="span5" type="text" name="remark"
									value="<s:property value="remark" />">
							</div>
						</div>
					</div>
					<div class="span10">
						<div class="control-group">
							<label class="control-label" for="">设置项目权限范围</label>
							<div class="controls" style="padding-top:10px;">

								<div class="row">
									<div class="span2">
										<input id="restrict" name="restrictRight" type="checkbox"
											data-on="primary" data-off="default" data-on-label="开"
											data-off-label="关"<s:if test="restrictRight==1" >checked</s:if>>
									</div>
								</div>
								<div class="restrictcnt" style="margin-top:10px;">
									<ul>
										<s:iterator value="projectTypeList" id="ptl">
											<li>
												<s:if test="#ptl.level>1">
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												</s:if>
												<input type="checkbox" id="right_<s:property value="#ptl.id" />" name="restrictRightId" value="<s:property value="#ptl.id"/>" style="margin:0px" 
													<s:iterator value="restrictRightId" id="rri">
														<s:if test="#rri==#ptl.id">
															checked
														</s:if>
													</s:iterator>
												>
												&nbsp;<s:property value="#ptl.name" />
											</li>
										</s:iterator>
									</ul>
								</div>

<!-- 								<div class="hidden" id="resIds"> -->
<!-- 									<input type="hidden" name="restrictRightId" value=""> -->
<%-- 									<s:if test="restrictRightId!=null"> --%>
<%-- 										<s:iterator value="restrictRightId" id="rs"> --%>
<%-- 											<input type="hidden" id="rightid_<s:property value="#rs" />" --%>
<%-- 												name="restrictRightId" value="<s:property value="#rs" />"> --%>
<%-- 										</s:iterator> --%>
<%-- 									</s:if> --%>
<!-- 								</div> -->
							</div>
						</div>
					</div>
<%-- 					<s:if test="projectAdminLevel == '1'"> --%>
<!-- 						<div class="span10"> -->
<!-- 							<div class="control-group"> -->
<!-- 								<label class="control-label" for="">设置管理学校范围</label> -->
<!-- 								<div class="controls" style="padding-top:10px;"> -->
	
<!-- 									<div class="row"> -->
<!-- 										<div class="span2"> -->
<!-- 											<input id="paorg" name="projectAdminOrg" type="checkbox" -->
<!-- 												data-on="primary" data-off="default" data-on-label="开" -->
<!-- 												data-off-label="关"<s:if test="projectAdminOrg==1" >checked</s:if>> -->
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 									<div class="restrictcnt" style="margin-top:10px;"> -->
<!-- 										<ul> -->
<%-- 											<s:iterator value="projectTypeList" id="ptl"> --%>
<!-- 												<li> -->
<%-- 													<s:if test="#ptl.level>1"> --%>
<!-- 														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<%-- 													</s:if> --%>
<%-- 													<input type="checkbox" id="right_<s:property value="#ptl.id" />" name="restrictRightId" value="<s:property value="#ptl.id"/>" style="margin:0px"  --%>
<!-- 														<s:iterator value="restrictRightId" id="rri"> -->
<!-- 															<s:if test="#rri==#ptl.id"> -->
<!-- 																checked -->
<!-- 															</s:if> -->
<!-- 														</s:iterator> -->
<!-- 													> -->
<%-- 													&nbsp;<s:property value="#ptl.name" /> --%>
<!-- 												</li> -->
<%-- 											</s:iterator> --%>
<!-- 										</ul> -->
<!-- 									</div> -->
	
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
<%-- 					</s:if> --%>



				</div>



				<div class="row actionbar">
					<div class="offset8">
						<button class="btn btn-primary btn-myself" type="submit">确定</button>
						<button id="colorboxcancel" onclick="parent.$.colorbox.close()"
							class="btn btn-default btn-myself" type="button">取消</button>
					</div>

				</div>
			</form>
		</div>

	</div>
	
	<script>
		$(function() {
			$('body').click(function(e) {
				var o = $(e.target);
				if (o.hasClass('usel') || o.parent('.usel').length) {
					return;
				}
				if (!o.parents('.uul').length) {
					$('.uul').hide();
				}
			});
			
			//工作单位 helper 函数
			$(document).on("click",function(e) {
				if(!$(e.target).parents().is('.companylocation'))
					$('.listSub').hide();
			});

			$('input[name="restrictRight"],input[name="createuser"]')
					.bootstrapSwitch();
			$('input[name="restrictRight"]').on('switch-change',
					function(e, data) {
						var $element = $(data.el), value = data.value;
						if (value){
							$('.restrictcnt').show();
						}
						else{
							$('.restrictcnt input').prop("checked",false);
							$('.restrictcnt').hide();
						}
					});

		})

		$(function() {
			$('#addprojectAdmin').submit(
				function() {
					var str = $(this).serialize();
					//var str = decodeURIComponent($(this).serialize());
					$.get('../admin/projectAdm_opProjectAdmin.action?'+ str, function(data) {
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
		$(document).ready(function() {
			if(document.getElementById("restrict").checked==true){
				$('.restrictcnt').show();
			}
		})
	</script>
<body>
</html>
