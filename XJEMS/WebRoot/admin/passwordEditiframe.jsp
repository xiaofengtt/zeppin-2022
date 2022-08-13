<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新疆师范大学考务管理系统</title>
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/mustache.js"></script>
<script src="../js/url.min.js"></script>	
<style>
	
	body{
		border-radius:5px;
	}
	.hd{
		width:100%;
		margin:0;
		font-size:14px;
		color:#111;
		border-bottom:1px solid #e5e5e5;
		font-weight:400px;
		line-height:22px;
	}
	.bd{
		padding:15px 0px 10px;
		font-size:13px;
		line-height:20px;
		color:#333333;
	}
	.control-group{
		margin-bottom:16px;
		text-align: right;
	}
	.control-label{
		width:100px;
		color:#E0615F;
		font-weight:bold;
		margin-right:35px;
		white-space: nowrap;
		vertical-align: middle;
	}
	.controls{
		margin-right:66px;
		display: inline-block;
		
	}
	input{
		display: inline-block;
		vertical-align: middle;
		margin-bottom:0;
		zoom:1;
		-webkit-border-radius: 5px;
		-moz-border-radius: 5px;
		border-radius: 5px;
		outline: none;
		height:20px;
		border:1px solid #C1C1C1;
	}
	.btn-primary{
		padding:4px 11px 4px 11px;
		background:#E0615F;
		color:#FFFFFF;
		-webkit-border-radius: 5px;
		-moz-border-radius: 5px;
		border-radius: 5px;
		border:none;
		font-size:14px;
		margin-right:10px;
		cursor: pointer;
	}
	
	.btn-default{
		padding:4px 11px 4px 11px;
		background:#C1C1C1;
		-webkit-border-radius: 5px;
		-moz-border-radius: 5px;
		border-radius: 5px;
		border:none;
		color:#FFFFFF;
		font-size:14px;
		cursor: pointer;
	}
	.actionbar{
		padding-top:15px;
		border-top:1px solid #efefef;
		width:100%;
	}
	
</style>	
</head>
<body>
	<div class="container" style="width:386px;height:250px;">
		<div class="hd">
			<h3>修改密码</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;color:#E0615F;font-size:12px"></div>
			<form id="formsubmit" class="form-horizontal" role="form"
				action="#"
				method="post">
				<input type="hidden" name="knowledge.id" value="">
				<div class="clearfix">
					<div id="HtmlTpl">
						<div class="control-group">
							<label class="control-label" for="">原密码</label>
							<div class="controls">
								<input type="password" id="" name="password"
									value="">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">新密码</label>
							<div class="controls">
								
								<input type="password" id="" name="newpassword"
									value="">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">确认新密码</label>
							<div class="controls">
								
								<input type="password" id="" name="confimpassword"
									value="">
							</div>
						</div>
					</div>					
				</div>

				<div class="row actionbar">
					<div class="offset7" style="margin-left:133px;margin-top:12px">
						<button class="btn-primary" type="submit">确定</button>
						<button id="colorboxcancel" onclick="parent.$.colorbox.close()" class="btn-default" type="button">取消</button>
					</div>

				</div>
			</form>
		</div>

	</div>
	<script>
		$(function() {			
			$('#formsubmit').submit(function() {
				var str = $(this).serialize();
				$.get('../admin/passwordEdit?'+ str, function(data) {
					var Result = data.Status;
					var message = data.Message;
					if (Result == "success") {
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