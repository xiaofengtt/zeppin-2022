<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
<title>编辑商品</title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="../assets/css/bootstrap2.css">
<link rel="stylesheet" href="../assets/css/iframe.css">

<link href="../assets/css/jquery-ui.css" rel="stylesheet" type="text/css" >
<link href="../assets/css/colorbox.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="../assets/css/uploadfile_n.css">
<script src="../assets/plugins/jquery-1.10.2.min.js"></script>
<script src="../assets/plugins/jquery-ui.js"></script>
<script src="../assets/plugins/jquery.colorbox.js"></script>

<script src="../assets/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="../assets/plugins/url.min.js"></script>
<script src="../assets/plugins/mustache.js"></script>
<script src="../assets/plugins/jquery.cokie.min.js"></script>
<script src="../assets/plugins/jquery.uploadfile.min.js"></script>
<style>
	textarea{resize: none;width: 400px;height: 100px;max-width: 400px;max-height: 100px;font-size:18px;}
	.controls input{width:400px;font-size:18px;}
</style>
</head>
<body>
	<div class="ifrcnt container">
		<div class="hd">
			<h3>编辑商品</h3>
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
						<label class="control-label" for="">商品名称</label>
						<div class="controls">
							<input type="text"  name="name" value="{{name}}"><span class="alert alert-danger3" style="display: none; margin: 0 15px 15px;"></span>
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="">原价</label>
						<div class="controls">
							<input type="text"  name="originalPrice" value="{{originalprice}}"><span class="alert alert-danger1" style="display: none; margin: 0 15px 15px;"></span>
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="">销售价格</label>
						<div class="controls">
							<input type="text" name="price" value="{{price}}"><span class="alert alert-danger2" style="display: none; margin: 0 15px 15px;"></span>
						</div>
					</div>
		
					<div class="control-group" id="uploadcon1">
						<label class="control-label" for="">封面资源</label>
						<div class="controls">
							<div id="resourceId"><span>&nbsp;选择文件&nbsp;&nbsp;</span>(支持jpg、png、gif、jpeg格式上传)
								</div>
							<img id="coverImg" src="../{{coverURL}}" style="max-width:90%;max-height:200px;"><a style="margin-left: 20px;"><font color=red id="change">X删除并替换</font></a>
						</div>
					</div>
					<div id="resourceAdd"><input type="hidden" name="cover" value="{{cover}}"></div>

					<div class="control-group">
						<label class="control-label" for="">商品URL</label>
						<div class="controls">
							<input type="text"  name="url" value="{{url}}"><span class="alert alert-danger4" style="display: none; margin: 0 15px 15px;"></span>
						</div>
					</div>

					<div class="control-group" id="uploadcon2">
						<label class="control-label" for="">商品360度展示</label>
						<div class="controls">
							<div id="resourceId2"><span>&nbsp;选择文件&nbsp;&nbsp;</span>
							</div>
							<span id="i1"></span><span id="i2" style="padding-left:10%;padding-right:10%"></span><span id="i3"></span>
						</div>
					</div>
					<div id="displayAdd"><input type="hidden" name="displays" value="" /></div>

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
		function isMoney(obj){
		   if (! obj) return false;
		   return (/^\d+(\.\d{1,2})?$/).test(obj);   
		}
		$(function() {
			var id = url('?id');
				$('input[name="id"]').val(id);
			$.get('../rest/commodity/load?id='+url('?id'),function(r){
				if(r.status == 'success') {
					var st = JSON.stringify(r);
				    var template = $('#DataTpl').html();
				    var html = Mustache.render(template, $.parseJSON(st));
				    $('#HtmlTpl').html(html);
				    $("#resourceId").uploadFile({
						url:"../rest/resource/add",
						allowedTypes:"jpg,png,jpeg,bmp,tiff",
						maxFileSize:1024*1024*1024*10,
						fileName:"video",
						maxFileCount : 1,
						dragDropStr: "",
						extErrorStr:"文件格式不正确，请上传指定类型类型的视频文件",
						showStatusAfterSuccess:true,
						showDelete : true,
						showDone : false,
						showProgress: true,
						deletelStr : '删除',
						showAbort:true,
						onSuccess:function(files,data,xhr){
							if(data.status=="success"){
								$('input[name="cover"]').val(data.data.id);
								
								$("#coverImg").remove();
								$("#change").hide();
							}
						}
					});
					
					$("#resourceId2").uploadFile({
						url:"../rest/resource/displayAdd",
						allowedTypes:"rar,zip",
						maxFileSize:1024*1024*1024*10,
						fileName:"display",
						maxFileCount : 1,
						dragDropStr: "",
						extErrorStr:"文件格式不正确，请上传指定类型类型的视频文件",
						showStatusAfterSuccess:false,
						showDelete : false,
						showProgress: true,
						deletelStr : '删除',
						showAbort:false,
						onSuccess:function(files,data,xhr){
							if(data.status=="success"){
								
								$('input[name="displays"]').val(data.data);
								
							}
						}
					});
					
					$("#change").click(function(){
						$("#coverImg").remove();
						$('input[name="cover"]').val('');
						$(this).hide();
					});
				}else {
					alert('服务端出错！请稍后重试！');
				}
			}).done(function(){
				$('#formsubmit').submit(function() {
					$('.alert-danger1').hide();
					$('.alert-danger2').hide();
					$('.alert-danger3').hide();
					$('.alert-danger4').hide();
					var name = $('input[name="name"]').val();
					var url = $('input[name="url"]').val();
					var price = $('input[name="price"]').val();
					var oprice = $('input[name="originalPrice"]').val();
					
					if(name == ""){
						$('.alert-danger3').html("请填写名称").show();
						return false;
					}
					
					if(oprice == ""){
						$('.alert-danger1').html("请填写原价").show();
						return false;
					}
					if(price == ""){
						$('.alert-danger2').html("请填写销售价格").show();
						return false;
					}
					
					if(!isMoney(oprice)){
						$('.alert-danger1').html("格式非法（只允许填写整数、小数）").show();
						return false;
					}
					if(!isMoney(price)){
						$('.alert-danger2').html("格式非法（只允许填写整数、小数）").show();
						return false;
					}
					if(url == ""){
						$('.alert-danger4').html("请填写商品URL").show();
						return false;
					}
					var str = $(this).serialize();
					$.post('../rest/commodity/edit?'+ str, function(data) {
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
