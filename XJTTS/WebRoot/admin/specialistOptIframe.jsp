<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title>添加授课专家</title>
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
			<h3>添加授课专家</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form id="addspecialist" class="form-horizontal" role="form"
				action="../admin/specialist_opSpecialist.action" method="post">
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
								<select name="organization">
									<s:iterator value="listOrganization" id="ot">
										<option value="<s:property value="#ot.getId()" />"
											<s:if test="#ot.getId()==ethnic" >selected</s:if>><s:property
												value="#ot.getName()" /></option>
									</s:iterator>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">职称</label>
							<div class="controls">
								<input type="text" id="" name="jobTitle"
									value="<s:property	value="jobTitle" />">
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
							<label class="control-label" for="">研究专长</label>
							<div class="controls">
								<input type="text" id="" name="research"
									value="<s:property	value="research" />">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">个人简历</label>
							<div class="controls">
							<textarea  style="resize:none" rows="2" name="resume" cols="20" id="" ><s:property value="resume" /></textarea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">教师培训经验</label>
							<div class="controls">
							<textarea  style="resize:none" rows="2" name="experience" cols="20" id="" ><s:property value="experience" /></textarea>
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
							<label class="control-label" for="">政治面貌</label>
							<div class="controls">
								<select name="politicsId">
									<s:iterator value="listPoliticsId" id="po">
										<option value="<s:property value="#po.getId()" />"
											<s:if test="#po.getId()==politicsId" >selected</s:if>><s:property
												value="#po.getName()" /></option>
									</s:iterator>
								</select>
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
							<label class="control-label" for="">最高学历</label>
							<div class="controls">
								<select name="eduction">
									<s:iterator value="listEduction" id="edu">
										<option value="<s:property value="#edu.getId()" />"
											<s:if test="#edu.getId()==eduction" >selected</s:if>><s:property
												value="#edu.getName()" /></option>
									</s:iterator>
								</select>
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
							<label class="control-label" for="">教学专长</label>
							<div class="controls">
								<input type="text" id="" name="teach"
									value="<s:property value="teach" />">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">教学成果</label>
							<div class="controls">
							<textarea  style="resize:none" rows="2" name="achievement" cols="20" id="" ><s:property value="achievement" /></textarea>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">备注</label>
							<div class="controls">
							<textarea  style="resize:none" rows="2" name="remark" cols="20" id="" ><s:property value="remark" /></textarea>
							</div>
						</div>
					</div>
				</div>
			
				<div class="clearfix">
					<div class="span10">
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

		})

		$(function() {
			$('#addspecialist').submit(
				function() {
					var str = $(this).serialize();
					$.get('../admin/specialist_opSpecialist.action?'+ str, function(data) {
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

