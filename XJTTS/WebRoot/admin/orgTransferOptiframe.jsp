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
			<h3>学校迁移（该操作将调整学校的从属关系）</h3>
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
							<label class="control-label" for="">被迁移学校</label>
							<div class="controls">
							<input data-val="true" data-val-number="<s:property value="orgn.id"/>" data-name="<s:property value="orgn.name"/>" type="text" class="readonly" readonly  value='<s:property value="orgn.name"/>'>	

							</div>
						</div>
					</div>
					<div class="span5">
						
						
<!-- 						<div class="control-group"> -->
<!-- 							<label class="control-label" for="">请选择预迁入地区</label> -->
<!-- 							<div class="controls"> -->
<!-- 							<input name="organization" data-val="true" data-val-number="" data-name="" id="organization" type="text" class="span3"  value=''>	 -->

<!-- 							</div> -->
<!-- 						</div> -->
						<div class="control-group">
							<label class="control-label" for="">预迁入地区</label>
							<div class="controls">
								<div class="companylocation">
									<span class="clId" id="clId"
										onclick="getsnode();changeClbtn($(this));">请选择</span>
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
	//工作单位
	function changeClbtn(e) {
		e.next('.listSub').toggle();
	}

	// 工作单位 请求子元素
	function getsnode(bid) {
		var cUrl = "../admin/organization_getOrganizationLevel.action";
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
							aClick = ' onclick="setnode(\''
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
									+ '" class="cSub" style="display:none">加载中...</div></div>';

						});
				}
				e.html(cLis)
		});
	}
	// 设定工作单位
	function setnode(id, name) {
		$('#bido').html('');
		$('#clId').html(name);
		$('#organization').val(id);
		$('.listSub').hide();
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
			

// 			$("#organization").select2({
// 			    placeholder: "请输入学校名称或拼音首字母",
// 			    minimumInputLength: 0,
// 				quietMillis : 1000,
// 				allowClear : true,
// 			    ajax: { 
// 			        url: "../base/organization_searchSchool.action",
// 			        dataType: 'json',
// 			        data: function (term, page) {
// 			            return {
// 			                search: term, // search term
// 			                page_limit: 10
// 			            };
// 			        },
// 			        results: function (data, page) {
// 			            return {results: data.Options};
						
// 			        }
// 			    },
				
// 				initSelection: function (element, callback) {
//                     element = $(element);
//                 	  var data = {id: element.val(), DisplayText: element.attr('data-name')};
// 				    callback(data);
//                 },
// 			    formatResult: movieFormatResult, 
// 			   formatSelection: movieFormatSelection, 
// 				dropdownCssClass: "bigdrop",
// 			    escapeMarkup: function (m) { return m; } 
// 			})
			
// 			function movieFormatResult(Options) {
// 				var html = Options.DisplayText;
// 				return html;
				
// 			}
// 			function movieFormatSelection(Options) {
// 				return Options.DisplayText;
// 			}
		})
		
		$(function() {
			$(document).on("click",function(e) {
				if(!$(e.target).parents().is('.companylocation'))
					$('.listSub').hide();
			});
			$('#teacherManage').submit(
					function() {
						if(confirm("警告：确定要迁移该学校？")){
							$('.btn-primary').html('处理中，请稍后。。。');
							$('.btn').attr('disabled','disabled');
							var pid = $('input[name="pid"]').val();
							var str =$(this).serialize();
							str=encodeURI(str);
							//alert(str);
							$.post('../admin/organization_transfer.action?'+ str, function(data) {
								var Result = data.Result;
								var message = data.Message;
								if (Result == "OK") {
									alert(message);
									$('.btn-primary').html('确定');
									$('.btn').removeAttr('disabled');
// 									window.top.location.reload();
									window.top.location.href='../admin/organization_initPage.action?'+'&pid='+data.pid;
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
		//工作单位
// function changeClbtn(e) {
// 	e.next('.listSub').toggle();
// }

// // 工作单位 请求子元素
// function getsnode(bid) {
// 	var cUrl = "../admin/projectAdm_getOrganizationLevel.action";
// 	bid = (typeof (bid) == 'undefined') ? '' : bid;
// 	var e = (bid) ? $('#bido' + bid) : $('#bido');
// 	cUrl += (bid) ? '?id=' + bid : '?id=0';
// 	if (bid)
// 		e.css('display') == 'none' ? e.show() : e.hide();
// 		$.getJSON(cUrl,
// 		function(data) {
// 			var cLis = '没有找到';
// 			if (data.length > 0) {
// 				var cLis = '';
// 				$.each(
// 					data,
// 					function(i, c) {
// 						emClass = (c.haschild == 1) ? ' class="c"'
// 								: '';
// 						emClick = (c.haschild == 1) ? ' onclick="getsnode(\''
// 								+ c.id
// 								+ '\');changeIcon($(this))"'
// 								: '';
// 						aClick = ' onclick="setnode(\''
// 								+ c.id + '\', \''
// 								+ c.name + '\')"';
// 						cLis += '<div class="listnode" id="'
// 								+ c.id
// 								+ '"><em'
// 								+ emClass
// 								+ emClick
// 								+ '></em><a href="javascript:void(0)" '
// 								+ aClick
// 								+ '>'
// 								+ c.name
// 								+ '</a><div id="bido'
// 								+ c.id
// 								+ '" class="cSub" style="display:none">加载中...</div></div>';

// 					});
// 			}
// 			e.html(cLis)
// 	});
// }
// function setnode(id, name) {
// 	$('#bido').html('');
// 	$('#clId').html(name);
// 	$('#organization').val(id);
// 	$('.listSub').hide();
// }
		
	</script>
<body>
</html>
