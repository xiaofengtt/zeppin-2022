<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="css/bootstrap2.css">
<link rel="stylesheet" href="css/iframe.css">
<link href="css/bootstrap-switch.min.css" rel="stylesheet">
<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/jquery.colorbox.js"></script>
<script src="js/iframe.js"></script>
<script src="js/bootstrap-switch.min.js"></script>
<script src="js/base64.js"></script>
</head>
<body>
	<div class="ifrcnt container" style="width:400px;margin: 0 auto;">
		<div class="hd">
			<h3>本次登录请修改密码</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form id="modifyPassword" class="form-horizontal" role="form"
				action="login_modifyPasswd.action" method="post">
				<input type="hidden" name="id" value="<s:property	value="id" />">

						<div class="control-group">
							<div class="control-group">
							<label class="control-label" for="">密码</label>
							<div class="controls">
								<input type="password" id="" name="passwd" value='<s:property	value="" />'>
							</div>
							</div>
							<div class="control-group">
							<label class="control-label" for="">新密码</label>
							<div class="controls">
								<input type="password" id="" name="newpasswd" placeholder="密码长度至少8位" value='<s:property	value="" />'>
							</div>
							</div>
							<div class="control-group">
							<label class="control-label" for="">确认密码</label>
							<div class="controls">
								<input type="password" id="" name="confimpasswd" placeholder="密码长度至少8位" value='<s:property	value="" />'>
							</div>
							</div>
						</div>



				<div class="row actionbar">
					<div class="text-center">
						<button class="btn btn-primary" type="submit" onclick="check();">确定</button>
						<button class="btn btn-default" type="button" onclick="window.location.href='login_logout.action';return false;">取消</button>
					</div>

				</div>
			</form>
		</div>

	</div>
	<script>
	var base = new Base64();

	function check(){
		var pwdstr = $('input[name="passwd"]').val();
		$('input[name="passwd"]').val(base.encode(pwdstr.replace(/(^\s*)|(\s*$)/g, "")));
		var pwdstrnew = $('input[name="newpasswd"]').val();
		$('input[name="newpasswd"]').val(base.encode(pwdstrnew.replace(/(^\s*)|(\s*$)/g, "")));
		var confimpasswd = $('input[name="confimpasswd"]').val();
		$('input[name="confimpasswd"]').val(base.encode(confimpasswd.replace(/(^\s*)|(\s*$)/g, "")));
	}
	function close(){
		window.location.href="login_logout.action";
	}
	$('body').click(function(e) {
				var o = $(e.target);
				if (o.hasClass('usel') || o.parent('.usel').length) {
					return;
				}
				if (!o.parents('.uul').length) {
					$('.uul').hide();
				}
			});

			$('input[name="restrictRight"]').on('switch-change',
					function(e, data) {
						var $element = $(data.el), value = data.value;
						if (value)
							$('.restrictcnt').show();
						else
							$('.restrictcnt').hide();
						showform();
						resCon();

					});

	$(function() {
			$('#modifyPassword').submit(
					function() {
						var str = $(this).serialize();
						$.get('login_modifyPasswd.action?'+ str, function(data) {
							var Result = data.Result;
							var message = data.Message;
							if (Result == "OK") {
								window.location.reload();
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
