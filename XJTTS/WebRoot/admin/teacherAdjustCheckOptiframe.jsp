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
			<form class="form-horizontal" id="teacherManage" action=""
				method="post" name="teahcer">
				<input type="hidden" id="teacherAId" name="id" value="<s:property	value="teacherAdjust.id" />">
				<div class="clearfix">

					<div class="span5" style="text-align: center;">
						<div class="control-group">
							<label class="control-label" for="">姓名</label>
							<div class="controls">
								<input type="text"
									value="<s:property	value="teacherAdjust.teacher.name" />" class="readonly" readonly  />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">身份证号</label>
							<div class="controls">
								<input type="text" id="" autocomplete="off"
									value="<s:property	value="teacherAdjust.teacher.idcard" />" class="readonly" readonly placeholder="">
							</div>
						</div>

					</div>
					<div class="span5">
						<div class="control-group">
							<label class="control-label" for="">调出学校</label>
							<div class="controls">
							<input data-val="true" data-val-number="<s:property value="teacherAdjust.oorganization.id"/>" data-name="<s:property value="teacherAdjust.oorganization.name"/>" type="text" class="readonly" readonly  value='<s:property value="teacherAdjust.oorganization.name"/>'>	

							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">调入学校</label>
							<div class="controls">
							<s:if test="teacherAdjust.norganization != null">
								<input type="hidden" id="norganization_flag" value="0"/>
								<input class="norganization" data-val="true" data-val-number="<s:property value="teacherAdjust.norganization.id"/>" data-name="<s:property value="teacherAdjust.norganization.name"/>" type="text" class="readonly" readonly  value='<s:property value="teacherAdjust.norganization.name"/>'>
							</s:if>
							<s:else>
								<input type="hidden" id="norganization_flag" value="1"/>
								<input class="norganization" name="organization" data-val="true" data-val-number="" data-name="" id="organization" type="text" class="span3"  value=''>
							</s:else>

							</div>
						</div>

					</div>
				</div>
				



				<div class="row actionbar">
					<div align="center" style="margin-left: 50px;padding-top: 20px">
						<a class="btn btn-default" onclick="adunopass();" id="adunopass" href="javascript:void(0)" data-url="../admin/teacherAdjust_checked.action?method=nocheck">审核不通过</a>
						<a class="btn btn-default btn-primary" onclick="adupass();" id="adupass" href="javascript:void(0)" data-url="../admin/teacherAdjust_checked.action?method=check">审核通过</a>    
						<button id="colorboxcancel" onclick="parent.$.colorbox.close()" class="btn btn-default" type="button">取消</button>
					</div>

				</div>
			</form>
		</div>
		
	</div>
	<script>
	
	var submitFlag = true;
	function adupass() {
		if(submitFlag){
			submitFlag = false;
			var obj = $('#adupass');
			var cUrl = obj.attr('data-url');
			var id = $('#teacherAId').val();
			var organization = $('.norganization').attr('data-val-number');
			if($('#norganization_flag').val() == 1){
				organization = $('#organization').val();
			}
			cUrl += ("&id=" + id);
			cUrl += ("&organization=" + organization);
			$.getJSON(cUrl, function(ret) {
				if (ret.Result == 'OK') {
					alert('成功,' + ret.Message);
					window.parent.$('#ProjectTableContainer').jtable('load',{
		            	organization:window.parent.$('#organization').val(),
		            	teacher : window.parent.$('input[name="teacherName"]').val(),
		            	assStatus : window.parent.$('.stateDiv a.light').attr("value")
		            });
					window.parent.getStatusCount();
					window.parent.getNumber();
					parent.$.colorbox.close();
				} else {
					alert('失败,' + ret.Message);
					submitFlag = true;
				}
			})
		}
		return false;
	}
	
	function adunopass() {
		if(submitFlag){
			submitFlag = false;
			var obj = $('#adunopass');
			var cUrl = obj.attr('data-url');
			var id = $('#teacherAId').val();
			cUrl += ("&id=" + id);
			$.getJSON(cUrl, function(ret) {
				if (ret.Result == 'OK') {
					alert('成功,' + ret.Message);
					window.parent.$('#ProjectTableContainer').jtable('load',{
		            	organization:window.parent.$('#organization').val(),
		            	teacher : window.parent.$('input[name="teacherName"]').val(),
		            	assStatus : window.parent.$('.stateDiv a.light').attr("value")
		            });
					window.parent.getStatusCount();
					window.parent.getNumber();
					parent.$.colorbox.close();
				} else {
					alert('失败,' + ret.Message);
					submitFlag = true;
				}
			})
		}
		return false;
	}
	
	
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
		
// 		$(function() {
// 			$('#teacherManage').submit(
// 					function() {
// 						var str =$(this).serialize();
// 						str=encodeURI(str);
// 						//alert(str);
// 						$.post('../admin/teacherManage_addAdjust.action?'+ str, function(data) {
// 							var Result = data.Result;
// 							var message = data.Message;
// 							if (Result == "OK") {
// 								alert('申请调出成功，请等待相关管理员审核调入。。。');
// 								window.top.location.reload();
// 							} else {
// 								$('.alert-danger').html(message).show();
// 							}
// 						})
// 						return false;
// 					});

// 		})
		
		
	</script>
<body>
</html>
