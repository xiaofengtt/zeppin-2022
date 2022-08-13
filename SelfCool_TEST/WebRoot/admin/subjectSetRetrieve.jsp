<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="../css/bootstrap2.css">
<link rel="stylesheet" href="../css/iframe.css">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/iframe.js"></script>
<script src="../js/mustache.js"></script>
<script src="../js/url.min.js"></script>

</head>
<body>
	<div class="ifrcnt container">
		<div class="hd">
			<h3>学科检索属性</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger" style="display: none; margin: 0 15px 15px;"></div>
			<form id="formsubmit" class="form-horizontal" role="form" action="#" method="post">
				<input type="hidden" name="subject.id" value='<s:property value="subjectId" />'>
				<div class="clearfix">
					<div id="HtmlTpl" class="span10">
						
						<s:iterator value="hashRetrieves" id="retrieve">
							
							<div class="control-group">
								<label class="control-label" for=""><s:property value='key.name' /></label>
								<s:set name="typeId" value="hashSubRete.get(key.id)" />
								
								<div class="controls">
									<select class="span3" name="retrieveId">
										<s:iterator value="value" id="va">
											
											<option <s:if test="#typeId==#va.id" >selected</s:if> value='<s:property value="#va.id" />' > <s:property value="#va.name" /> </option>
											
										</s:iterator>
									</select>
								</div>
							</div>
							
						</s:iterator>
						
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
	
		$(function() {
			$('#formsubmit').submit(function() {
				var str = $(this).serialize();
				$.get('../subjectAddRetrieve?' + str, function(data) {
					if (data.Status == "success") {
						window.top.location.reload(true);
					} else {
						$('.alert-danger').html(data.Message).show();
					}
				})
				return false;
			});

		})

	</script>
<body>
</html>