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
<link href="../css/bootstrap-switch.min.css" rel="stylesheet">
<link href="../css/datepicker3.css" rel="stylesheet">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/iframe.js"></script>
<script src="../js/bootstrap-switch.min.js"></script>
<script src="../js/bootstrap-datepicker.js"></script>
<script src="../js/bootstrap-datepicker.zh-CN.js"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>


</head>
<body>
	<div class="ifrcnt container">
		<div class="hd">
			<h3>调出教师</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form class="form-horizontal" id="teacherManage" action="teacherManage_addAdjust.action"
				method="post" name="teahcer">
				<input type="hidden" name="id" value="<s:property	value="teacher.id" />">
				<div class="clearfix">

					<div class="span5" style="text-align: center;">
						<div class="control-group">
							<label class="control-label" for="">姓名</label>
							<div class="controls">
								<input type="text"
									value="<s:property	value="teacher.name" />" class="readonly" readonly  />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">身份证号</label>
							<div class="controls">
								<input type="text" id="" autocomplete="off"
									value="<s:property	value="teacher.idcard" />" class="readonly" readonly placeholder="">
							</div>
						</div>

					</div>
					<div class="span5">
						<div class="control-group">
							<label class="control-label" for="">调出学校</label>
							<div class="controls">
							<input data-val="true" id='outOrganization' data-val-number="<s:property value="organizationp.id"/>" data-name="<s:property value="organizationp.name"/>" type="text" class="readonly" readonly  value='<s:property value="organizationp.name"/>'>	

							</div>
						</div>
						
<!-- 						<div class="control-group">20180314取消选择调入学校 -->
<!-- 							<label class="control-label" for="">请选择调入学校</label> -->
<!-- 							<div class="controls"> -->
<!-- 							<input name="organization" data-val="true" data-val-number="" data-name="" id="organization" type="text" class="span3"  value=''>	 -->

<!-- 							</div> -->
<!-- 						</div> -->

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
				startView: 2,
				endDate : '1d',
				autoclose : true
			});

			$('.switch').bootstrapSwitch();

			$('#mainTeachingSubject,#mainTeachingGrades,#mainTeachingLanguage').select2();
			$('#unMainTeachingSubject,#unMainTeachingGrades,#unMainTeachingLanguage').select2();
			
			$("#organization").select2({
			    placeholder: "请输入学校名称或拼音首字母",
			    minimumInputLength: 0,
				quietMillis : 1000,
				allowClear : true,
			    ajax: { 
			        url: "../base/organization_searchSchool.action",
			        dataType: 'json',
			        data: function (term, page) {
			            return {
			                search: term, // search term
			                page_limit: 10
			            };
			        },
			        results: function (data, page) {
			            return {results: data.Options};
						
			        }
			    },
				
				initSelection: function (element, callback) {
                    element = $(element);
                	  var data = {id: element.val(), DisplayText: element.attr('data-name')};
				    callback(data);
                },
			    formatResult: movieFormatResult, 
			   formatSelection: movieFormatSelection, 
				dropdownCssClass: "bigdrop",
			    escapeMarkup: function (m) { return m; } 
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
			var submitFlag = true;
			$('#teacherManage').submit(function() {
				if(submitFlag){
					submitFlag = false;
					var oo = $('#outOrganization').attr('data-val-number');
// 				var on = $('#organization').val();
// 				if(oo == on) {
// 					$('.alert-danger').html("不能选择调出学校").show();
// 					return false;
// 				}
					var str =$(this).serialize();
					str=encodeURI(str);
					//alert(str);
					$.post('../admin/teacherManage_addAdjust.action?'+ str, function(data) {
						var Result = data.Result;
						var message = data.Message;
						if (Result == "OK") {
							alert('申请调出成功，请等待相关管理员审核调入。。。');
							window.top.location.reload();
						} else {
							$('.alert-danger').html(message).show();
							submitFlag = true;
						}
					})
				}
				return false;
			});

		})
		
		
	</script>
<body>
</html>
