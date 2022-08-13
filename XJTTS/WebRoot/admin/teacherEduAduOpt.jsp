<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="../css/bootstrap2.css">
<link rel="stylesheet" href="../css/iframe.css">
<link href="../css/datepicker3.css" rel="stylesheet">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/layer-v2.2/layer/layer.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/iframe.js"></script>
<script src="../js/bootstrap-datepicker.js"></script>
<script src="../js/bootstrap-datepicker.zh-CN.js"></script>
<script src="../js/url.min.js"></script>
<style>
.form-horizontal .control-label{color:gray;font-weight:normal;}
input{width:90%;max-width:300px;}
input[name="starttime"],input[name="endtime"]{width:40%;max-width:132px;}
.ifrcnt{width:100%;}
.span10{width:50%;margin-left:0;}
.offset7{margin-left:auto;text-align:right;margin-right:20px;}
#images{padding-right:20px;}
#images img{max-width:100%;}
.layui-layer-content{background:rgba(0,0,0,0) !important;}
body{background:rgba(0,0,0,0) !important;}
.layui-layer{background:transparent;}
</style>

</head>
<body>
	<div class="ifrcnt container">
		<div class="hd">
			<h3>教师学历提升审核</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form id="aduTeacheredu" class="form-horizontal" role="form" method="post">
				<input type="hidden" name="id" value="">
				<div class="clearfix">
					<div class="span10">
						<div class="control-group">
							<label class="control-label" for="">教师名称</label>
							<div class="controls">
								<input type="text" id="" name="name" class="readonly" readonly placeholder="" value="">
							</div>
						</div>
						
						
						<div class="control-group">
							<label class="control-label" for="">身份证号</label>
							<div class="controls">
								<input type="text" id="" name="idcard" class="readonly" readonly placeholder="" value="">
							</div>
						</div>

						
						
						<div class="control-group">
							<label class="control-label" for="">进修专业</label>
							<div class="controls">
								<input type="text" id="" name="major" class="readonly" readonly placeholder="" value="">
							</div>
						</div>
						
						
						
						<div class="control-group">
							<label class="control-label" for="">结业证书编号</label>
							<div class="controls">
								<input type="text" id="" name="certificate" class="readonly" readonly placeholder="" value="">
							</div>
						</div>
						
						
						<div class="control-group">
							<label class="control-label" for="">申请提升至学历</label>
							<div class="controls">
								<div class="input-group">
									<input type="text" readonly class="readonly" name="educationBackground"  value="">
								</div>


							</div>
						</div>

						

					</div>
					<div class="span10">
						<div class="control-group">
							<label class="control-label" for="">所属单位</label>
							<div class="controls">
								<input type="text" id="" name="organization" class="readonly" readonly placeholder="" value="">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">进修学校</label>
							<div class="controls">
								<input type="text" id="" name="graduation" class="readonly" readonly placeholder="" value="">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">进修时间</label>
							<div class="controls">
								<div class="input-group">
									<input type="text" readonly class="readonly" name="starttime" value=""> 
									<span class="input-group-addon"> 至 </span> 
									<input type="text" readonly class="readonly" name="endtime"  value="">
								</div>


							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="">原学历</label>
							<div class="controls">
								<div class="input-group">
									<input type="text" readonly class="readonly" name="odlEducationBackground" value=""> 
								</div>


							</div>
						</div>
					</div>
					<div style="clear:both"></div>
					<div class="control-group" style="width:100%;">
						<label class="control-label" for="" style="text-align:center;">认证资料<br>(点击图片查看大图）</label>
						<div class="controls">
							<div id="images"></div>
						</div>
					</div>

				</div>

				<div class="row actionbar">
					<div class="offset7">
						<button class="btn btn-primary btn-myself" type="button" onclick="difaction('../admin/teacheredu_aduRecords.action','pass')">审核通过</button>
						<button class="btn btn-default btn-myself" type="button" onclick="difaction('../admin/teacheredu_aduRecords.action','nopass')">审核不通过</button>
						<button id="colorboxcancel" onclick="parent.layer.closeAll()"
							class="btn btn-default btn-myself" type="button">取消</button>
					</div>

				</div>
			</form>
		</div>

	</div>
	<script>
	var indexs
	var id=(url('?id') != null) ?url('?id'):'';
	var isAdmin=(url('?isAdmin') != null) ?url('?isAdmin'):'';
	function difaction(action,type) {
		id=(url('?id') != null) ?url('?id'):'';
		isAdmin=(url('?isAdmin') != null) ?url('?isAdmin'):'';
		if(type == 'nopass'){
			indexs=layer.prompt({
				title: '审核不通过原因',
				},function(value, index, elem){
					value=value.replace(/(^\s*)|(\s*$)/g, "");
					$.getJSON(action+'?id='+id+'&method='+type+'&isAdmin='+isAdmin+'&reason='+value,function(r) {
						if(r.Result == 'OK'){
							layer.msg("操作成功！");
			            	setTimeout('window.top.location.reload()',1000);
						}else{
							var message = r.Message;
							$('.alert-danger').html(message).show();
							$("html,body").animate({ "scrollTop": 0 });
						}
									
						
					});
				  layer.close(indexs);
				});
			
		}else{
			$.getJSON(action+'?id='+id+'&method='+type+'&isAdmin='+isAdmin,function(r) {
				if(r.Result == 'OK'){
					layer.msg("操作成功！");
	            	setTimeout('window.top.location.reload()',1000);
				}else{
					var message = r.Message;
					$('.alert-danger').html(message).show();
					$("html,body").animate({ "scrollTop": 0 });
				}
							
				
			});
		}
		
	}

	$(function() {

		$('.input-daterange').datepicker({
			language : "zh-CN",
			format : 'yyyy-mm-dd',
			startDate : "1d",
			autoclose : true

		});


		id=url('?id');
		isAdmin=(url('?isAdmin') != null) ?url('?isAdmin'):'';
		//获取学科
		$.getJSON('../admin/teacheredu_loadRecordInfo.action?id='+id,function(r) {
			if(r.Result == 'OK'){
				id=r.Records.id;
				var name=r.Records.name;
				var idcard=r.Records.idcard;
				var organization=r.Records.organization;
				var graduation=r.Records.graduation;
				var major=r.Records.major;
				var starttime=r.Records.starttime;
				var endtime=r.Records.endtime;
				var certificate=r.Records.certificate;
				var educationBackground=r.Records.educationBackground;
				var odlEducationBackground=r.Records.odlEducationBackground;
				
				var eivdenceStr='';
				if(r.Records.eivdence.length>0){
					for(var i=0; i<r.Records.eivdence.length; i++ ){
						eivdenceStr += '<img title="点击查看大图" src="..'+r.Records.eivdence[i].path+'" onclick="LookBigImg(\''+r.Records.eivdence[i].path+'\')" style="cursor:pointer;margin-bottom:10px;"> <br>';
					}
				}
				$('input[name="id"]').val(id);
				$('input[name="name"]').val(name);
				$('input[name="idcard"]').val(idcard);
				$('input[name="organization"]').val(organization);
				$('input[name="graduation"]').val(graduation);
				$('input[name="major"]').val(major);
				$('input[name="starttime"]').val(starttime);
				$('input[name="endtime"]').val(endtime);
				$('input[name="certificate"]').val(certificate);
				$('input[name="educationBackground"]').val(educationBackground);
				$('input[name="odlEducationBackground"]').val(odlEducationBackground);
					$('#images').html(eivdenceStr);
			}else{
				var message = data.Message;
				$('.alert-danger').html(message).show();
			}
						
			
		});
						
	})
	//查看大图
	function LookBigImg(img){
		var img=top.layer.open({
			  type: 1,
			  title: false,
			  shadeClose: true,
			  shade: 0.6,
			  closeBtn: 1,
			  scrollbar: false,
			  area: ['90%', '95%'],
			  content: '<table style="text-align:center;vertical-align: middle;width:100%;height:100%;"><tr><td><img src="../'+img+'" alt="大图" style="max-width:100%;max-height:100%;"/></td></tr></table>' 
			  
			}); 
			$(".layui-layer-setwin .layui-layer-close2").css("display", "none");
		   	$(".layui-layer-rim").css({"border":"none","border-radius":"0px"});
		   	$(".layui-layer").css("background","rgba(0,0,0,0)");
		   	$(".layui-layer-content").css("background","rgba(0,0,0,0)");
	}
	
	</script>
<body>
</html>
