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
			<h3>合并学校（该操作将批量调出被合并学校教师）</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form class="form-horizontal" id="teacherManage" action="teacherManage_addAdjust.action"
				method="post" name="teahcer">
				<input type="hidden" name="id" value="<s:property	value="orgn.id" />">
				<input type="hidden" name="pid" value="<s:property	value="orgn.organization.id" />">
				<div class="clearfix">

					<div class="span5" style="text-align: center;">
						<div class="control-group">
							<label class="control-label" for="">被合并学校</label>
							<div class="controls">
							<input data-val="true" data-val-number="<s:property value="orgn.id"/>" data-name="<s:property value="orgn.name"/>" type="text" class="readonly" readonly  value='<s:property value="orgn.name"/>'>	

							</div>
						</div>
					</div>
					<div class="span5">
						
						
						<div class="control-group">
							<label class="control-label" for="">请选择预并入学校</label>
							<div class="controls">
							<input name="organization" data-val="true" data-val-number="" data-name="" id="organization" type="text" class="span3"  value=''>	

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
			$('#teacherManage').submit(
					function() {
						if(confirm("警告：确定要合并该学校？")){
							$('.btn-primary').html('处理中，请稍后。。。');
							$('.btn').attr('disabled','disabled');
							var str =$(this).serialize();
							str=encodeURI(str);
							//alert(str);
							$.post('../admin/organization_merge.action?'+ str, function(data) {
								var Result = data.Result;
								var message = data.Message;
								if (Result == "OK") {
									alert(message);
									$('.btn-primary').html('确定');
									$('.btn').removeAttr('disabled');
									window.top.location.reload();
								} else {
									$('.btn-primary').html('确定');
									$('.btn').removeAttr('disabled');
									$('.alert-danger').html(message).show();
								}
							})
						}
						return false;
					});

		})
		
		
	</script>
<body>
</html>
