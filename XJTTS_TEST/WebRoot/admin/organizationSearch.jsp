<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title>组织架构查询</title>
<meta charset="utf-8">
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
	<div class="ifrcnt container" style="width:450px">
		<div class="hd">
			<h3>组织架构查询</h3>
		</div>
		<div class="bd">
			<form id="addprojectAdmin" class="form-horizontal" role="form">
				<div class="clearfix">
					<div class="span5">
						<div class="control-group">
							<label class="control-label" for="">所属学校</label>
							<div class="controls">
							<input name="organization" data-val="true" data-val-number="<s:property value="organizationp.id"/>" data-name="<s:property value="organizationp.name"/>" id="organization" type="text" class="span3"  value='<s:property value="organizationp.id"/>'>	

							</div>
						</div>
					</div>
				</div>
			</form>
		</div>

	</div>
	
	<script>
	$(function() {
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
		
	</script>
<body>
</html>
