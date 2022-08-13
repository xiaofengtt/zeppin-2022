<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
<title></title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="../assets/css/bootstrap2.css">
<link rel="stylesheet" href="../assets/css/iframe.css">

<link href="../assets/css/jquery-ui.css" rel="stylesheet" type="text/css" >
<link href="../assets/css/colorbox.css" rel="stylesheet" type="text/css">

<script src="../assets/plugins/jquery-1.10.2.min.js"></script>
<script src="../assets/plugins/jquery-ui.js"></script>
<script src="../assets/plugins/jquery.colorbox.js"></script>

<script src="../assets/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="../assets/plugins/url.min.js"></script>
<script src="../assets/plugins/mustache.js"></script>
<script src="../assets/plugins/jquery.cokie.min.js"></script>
<style>
	textarea{resize: none;width: 400px;height: 100px;max-width: 400px;max-height: 100px;font-size:18px;}
	.controls input{width:400px;font-size:18px;}
</style>
</head>
<body>
	<div class="ifrcnt container">
		<div class="hd">
			<h3>编辑视频</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form id="formsubmit" class="form-horizontal" role="form"
				action="#"
				method="post">
				<input type="hidden" name="id" value="">
				<div class="row">		
					<div id="HtmlTpl" class="span10">
					
					</div>
				</div>
				<script id="DataTpl" type="text/template">
				{{#data}}
				<div class="clearfix">
					<div class="control-group">
						<label class="control-label" for="">标题</label>
						<div class="controls">
							<input type="text"  name="title" value="{{title}}">
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="">标签</label>
						<div class="controls">
							<input type="text"  name="tag" value="{{tag}}">
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="">描述</label>
						<div class="controls">
							<textarea id="" name="context">{{context}}</textarea>
						</div>
					</div>
		
					<div class="control-group">
						<label class="control-label" for="">来源</label>
						<div class="controls">
							<input type="text"  name="source" value="{{source}}">
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="">版权方</label>
						<div class="controls">
							<input type="text"  name="copyright" value="{{copyright}}">
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="">作者</label>
						<div class="controls">
							<input type="text"  name="author" value="{{author}}">
						</div>
					</div>
				</div>
				{{/data}}
				</script>
			
				<div class="row actionbar">
					<div class="offset7" style="margin-left:150px;">
						<button class="btn" type="submit">确定</button>
						<button id="colorboxcancel" onclick="parent.$.colorbox.close()"
							class="btn" type="button">取消</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script>
		$(function() {
			var id = url('?id');
				$('input[name="id"]').val(id);
			$.get('../rest/videoinfo/load?id='+url('?id'),function(r){
				if(r.status == 'success') {
					var st = JSON.stringify(r);
				    var template = $('#DataTpl').html();
				    var html = Mustache.render(template, $.parseJSON(st));
				    $('#HtmlTpl').html(html);
				}else {
					alert('服务端出错！请稍后重试！');
				}
			}).done(function(){
				$('#formsubmit').submit(function() {
					var str = $(this).serialize();
					$.post('../rest/videoinfo/edit?'+ str, function(data) {
						if (data.status == "success") {
							window.top.location.reload();
						} else {
							$('.alert-danger').html(data.message).show();
						}
					})
					return false;
				});
			})
			
			//项目类型 helper 函数
			$(document).on("click",function(e) {
				if(!$(e.target).parents().is('.ufc'))
					$('.uul').hide();
			});
		})
	</script>
<body>
</html>
