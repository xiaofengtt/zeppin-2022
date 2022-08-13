<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
<title></title>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link href="../assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="../assets/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../assets/css/bootstrap2.css">
<link rel="stylesheet" href="../assets/css/iframe.css">
<link rel="stylesheet" href="../assets/css/uploadfile_n.css">
<link href="../assets/css/jquery-ui.css" rel="stylesheet" type="text/css" >
<link href="../assets/css/colorbox.css" rel="stylesheet" type="text/css">


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
			<h3>添加关联商品</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form id="formsubmit" class="form-horizontal" role="form" action="#" method="post">
				<input type="hidden" name="id" value="">
				<div class="row">		
					<div id="HtmlTpl" class="span10">
					
					</div>
				</div>
				<script id="DataTpl" type="text/template">
				{{#data}}
				<div class="clearfix">
					<div class="control-group">
						<label class="control-label" for="">帧位图</label>
						<div class="controls">
							<img src="..{{iframePath}}" styly="width:500px">
						</div>
					</div>

					<div class="control-group">
						<label class="control-label" for="">关联商品</label>
						<div class="controls">
							<select name="commodity" id="commoditySelect">
							</select>
						</div>
					</div>
					<input type="hidden"  name="showType" value="1">
					<div class="control-group">
						<label class="control-label" for="">提示内容</label>
						<div class="controls">
							<textarea id="" name="showMessage">{{showMessage}}</textarea>
						</div>
					</div>
					<div class="control-group" id="uploadcon1">
						<label class="control-label" for="">提示banner</label>
						<div class="controls">
							<div id="resourceId"><span>&nbsp;选择文件&nbsp;&nbsp;</span>(支持jpg、png、gif、jpeg格式上传)
								</div>
							<img id="bannerImg" src="..{{showBannerUrl}}" style="max-width:90%;max-height:200px;"><a id="change" style="margin-left: 20px;"><font color=red>X删除</font></a>
						</div>
					</div>
					<div id="resourceAdd"><input type="hidden" name="showBanner" value="{{showBanner}}"></div>
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
			$.get('../rest/videoPoint/loadVO?id='+id,function(r){
				if(r.status == 'success') {
					var st = JSON.stringify(r);
				    var template = $('#DataTpl').html();
				    var html = Mustache.render(template, $.parseJSON(st));
				    $('#HtmlTpl').html(html);
				    $.get('../rest/commodity/search?status=normal',function(result){
						if(result.status == 'success') {
							var commodityHtml='';
							$.each(result.data,function(i,v){
								if(v.id==r.data.commodity){
									commodityHtml+="<option name='video' value='"+v.id+"' selected>"+v.name+"</option>";
								}else{
									commodityHtml+="<option name='video' value='"+v.id+"'>"+v.name+"</option>";
								}
							})
							$('#commoditySelect').html(commodityHtml);
						}else {
							alert(r.message);
						}
					})
				}else {
					alert(r.message);
				}
			}).done(function(){
				$('#formsubmit').submit(function() {
					var str = $(this).serialize();
					$.post('../rest/videoPoint/edit?'+ str, function(data) {
						if (data.status == "success") {
							window.parent.getList();
							parent.$.colorbox.close();
						} else {
							$('.alert-danger').html(data.message).show();
						}
					})
					return false;
				});
				$("#resourceId").uploadFile({
					url:"../rest/resource/add",
					allowedTypes:"jpg,png,jpeg,bmp,tiff",
					maxFileSize:1024*1024*1024*10,
					fileName:"video",
					maxFileCount : 1,
					dragDropStr: "",
					extErrorStr:"文件格式不正确，请上传指定类型类型的视频文件",
					multiple:false,
					showDelete: true,
					showStatusAfterSuccess:true,
					showProgress: true,
					deletelStr : '删除',
					showAbort:false,
					showDone:false,
					maxFileCountErrorStr: "文件数量过多，请先删除。",
					onSuccess:function(files,data,xhr)
					{
						if(data.status=="success"){
							$('input[name="showBanner"]').val(data.data.id);
							
							$("#bannerImg").remove();
							$("#change").hide();
						}
					}
				});
				$("#change").click(function(){
					$("#bannerImg").remove();
					$('input[name="showBanner"]').val('');
					$(this).hide();
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
