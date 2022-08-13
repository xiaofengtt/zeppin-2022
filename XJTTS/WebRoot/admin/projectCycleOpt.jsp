<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!-- <link rel="stylesheet" href="../css/app.css">	 -->
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" >
<link href="../css/colorbox.css" rel="stylesheet" type="text/css">
<script src="../js/jquery-1.9.1.min.js"></script>
<script src="../js/jquery-ui.js"></script>
<script src="../js/jquery.colorbox.js"></script>
<script src="../js/bootstrap-paginator.min.js"></script>
<script src="../js/bootstrap.js"></script>
<script src="../js/url.min.js"></script>
<script src="../js/app.js"></script> 

<link rel="stylesheet" href="../css/bootstrap2.css">
<link rel="stylesheet" href="../css/uploadfile.css">
<link href="../css/datepicker3.css" rel="stylesheet">
<link href="../css/bootstrap-switch.min.css" rel="stylesheet">


<script src="../js/iframe.js"></script>
<script src="../js/bootstrap-datepicker.js"></script>
<script src="../js/bootstrap-datepicker.zh-CN.js"></script>
<script src="../js/upload/jquery.uploadfile.min.js"></script>
<script src="../js/bootstrap-switch.min.js"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../js/select2.js"></script>
<script src="../js/select2_locale_zh-CN.js"></script>
<link rel="stylesheet" href="../css/iframe.css">
<link rel="stylesheet" href="../css/projectBaseInfoOpt.css">
<style>
input{height:34px !important;}
select{height:34px !important;}
.ajax-file-upload{height:30px !important;}
h5{width:120px;text-align:right;color:#000;font-weight:bold;padding-bottom:20px;font-size:16px;}
.offset7{margin-left:360px;}
.container, .navbar-static-top .container, .navbar-fixed-top .container, .navbar-fixed-bottom .container{width:100%;}
.navbar{margin-bottom:0;}
 .select2-container .select2-choice > .select2-chosen{line-height:34px;}
.restrictcntSubject{display:block;border: 0px solid #428bca;}
.ifrcnt .hd h3{border-bottom:1px solid #e5e5e5;font-size:15px;font-weight:normal;line-height:22px;padding:15px;}
.restrictcntSubject{margin-left:10px;}
#calListBox{background:#fff;display:none;}
.controls{width:220px;float:left;margin-left:5px !important;}
input, textarea, .uneditable-input{width:220px;}
.tipsTxt{color:#f00;font-size:12px;line-height:34px;display:block;float:left;margin-left:5px;}
</style>
<script type="text/javascript" src="../js/layer-v2.2/layer/layer.js"></script>
<div class="main">
	<div class="ifrcnt container">
		<div class="hd">
			<h3>添加</h3>(周期内，包含起始年份和结束年份)
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form id="addProjectCycleInfo" class="form-horizontal" role="form"
				action=""
				method="post">
				<input type="hidden" name="id" value="<s:property value="id" />">
				<div class="clearfix" style="margin-left:100px">

						<div class="control-group">
							<label class="control-label" for="">项目周期名称</label>
							<div class="controls">
								<input type="text" class="input" id="" name="name"
									value="" placeholder="项目名称">
							</div>
							<b class="tipsTxt"></b>
						</div>

						<div class="control-group">
							<label class="control-label" for="">起始年份</label>
							<div class="controls">
								<select class="span3" name="startyear" onchange="selectEndYearList(this);">
									<s:iterator value="yearArray" id="ya">
										<option <s:if test="year==#ya" >selected</s:if>
											value="<s:property value="#ya" />"><s:property
												value="#ya" /></option>
									</s:iterator>
								</select>
							</div>
							<b class="tipsTxt"></b>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">结束年份</label>
							<div class="controls">
								<select class="span3" name="endyear">
									<option value="0" selected>请选择</option>
								</select>
							</div>
							<b class="tipsTxt"></b>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="">集中培训学时</label>
							<div class="controls">

								<input class="input input1" type="text" id="" name="centralizeCH"
									value="" placeholder="">
							</div>
							<b class="tipsTxt"></b>
						</div>
						<div class="control-group">
							<label class="control-label" for="">信息技术学时</label>
							<div class="controls">

								<input class="input input2" type="text" id="" name="informationCH"
									value="" placeholder="">
							</div>
							<b class="tipsTxt"></b>
						</div>
						<div class="control-group">
							<label class="control-label" for="">区域特色学时</label>
							<div class="controls">

								<input class="input input3" type="text" id="" name="regionalCH"
									value="" placeholder="">
							</div>
							<b class="tipsTxt"></b>
						</div>
						<div class="control-group">
							<label class="control-label" for="">校本培训学时</label>
							<div class="controls">

								<input class="input input4" type="text" id="" name="schoolCH"
									value="" placeholder="">
							</div>
							<b class="tipsTxt"></b>
						</div>
						<div class="control-group">
							<label class="control-label" for="">总培训学时</label>
							<div class="controls">

								<input class="input input5" type="text" id="" name="totalhoursCH"
									value="" placeholder="">
							</div>
							<b class="tipsTxt"></b>
						</div>

				</div>

				<div class="row actionbar">
					<div class="offset7">
						<button class="btn btn-primary btn-myself" type="submit">确定</button>
						<button id="colorboxcancel" onclick="parent.$.colorbox.close()"
							class="btn btn-default btn-myself" type="button">取消</button>
					</div>

				</div>
			</form>
		</div>

	</div>
	</div>
	<script>
	
	function selectEndYearList(e){
		var this_ = $(e);
		var thisYear = this_.find("option:selected").val();
		$.getJSON('../admin/projectCycle_getYearList.action?startyear='+thisYear,function(data){
			if(data.Result == 'OK'){
				var yearStr = '';
				for(var i=0;i< data.Options.length;i++){
					var options = '<option value="'+data.Options[i].Value+'">'+data.Options[i].DisplayText+'</option>'
					
					yearStr += options;
				}
				
				$('select[name="endyear"]').html(yearStr);
				
			}
			
		})
	}
	
	
	$(function() {
		$(document).ready(function() {
			selectEndYearList('select[name="startyear"]');
			$(".input").blur(function(){
				var value = $(this).val();
				value=value.replace(/(^\s*)|(\s*$)/g, "");
				var reg=/^\d+$/; 
				if($(this).attr("name") == 'name'){
					if(value != ''){
						$(this).removeAttr("style").removeAttr("style").parent().parent().find("b").html("");;
					    return true;
					}else{
						$(this).css({"border-color":"#f00","box-shadow":"none"}).parent().parent().find("b").html("项目名称不能为空");						
					    return false;
					}
				}else{
					if(reg.test(value)==true){
						$(this).removeAttr("style").parent().parent().find("b").html("");
					    return true;
					}else{
						if(value==""){
							$(this).removeAttr("style").parent().parent().find("b").html("");
						    return true;
						}else{
							$(this).css({"border-color":"#f00","box-shadow":"none"}).parent().parent().find("b").html("填写必须全部为数字且为正整数");
						    return false;
						}
						
					}
				}
			})
			$("select[name='endyear']").blur(function(){
				if($(this).val()!="0"){
					$(this).removeAttr("style").parent().parent().find("b").html("");
				}else{
					$('select[name="endyear"]').css({"border-color":"#f00","box-shadow":"none"}).parent().parent().find("b").html("请选择截止年份");
				}
			})
		});
		
		
			$('body').click(function(e) {
				var o = $(e.target);
				if (o.hasClass('usel') || o.parent('.usel').length) {
					return;
				}
				if (!o.parents('.uul').length) {
					$('.uul').hide();
				}
			});
			
			//项目类型 helper 函数
			$(document).on("click",function(a) {
				if(!$(a.target).parents().is('.ufc'))
					$('.uul').hide();
				if(!$(a.target).parents().is('.companylocation'))
					$('.listSub').hide();
			});

		})
		
		//验证学时
		function tirm(value){
			var values=$(value).val().replace(/(^\s*)|(\s*$)/g, "");
			var reg=/^\d+$/; 
			if(reg.test(values)==true){
			    return true;
			}else{
				if(values==""){
					$(value).removeAttr("style").parent().parent().find("b").html("");
					return true;
				}else{
					$(value).css({
						"border-color" : "#f00",
						"box-shadow" : "none"
					}).parent().parent().find("b").html("填写必须全部为数字且为正整数");
					return false;
				}			
			}
		}
		
	//验证项目名称
	function tirm2(value){
		var values=$(value).val().replace(/(^\s*)|(\s*$)/g, "");
		if (values == "") {
			$(value).css({
				"border-color" : "#f00",
				"box-shadow" : "none"
			}).parent().parent().find("b").html("填写必须全部为数字且为正整数");
			return false;
		} else {
			$(value).removeAttr("style").parent().parent().find("b").html("");
			return true;
		}
	}
	
	//验证
	function tirm3(value) {
		var values =$(value).val().replace(/(^\s*)|(\s*$)/g, "");
		if (values == "0") {
			$(value).parent().css({
				"border-color" : "#f00",
				"box-shadow" : "none"
			}).parent().parent().find("b").html("请选择截止年份");
			return false;
		} else {
			$(value).removeAttr("style").parent().parent().find("b").html("");
			return true;
		}
	}
	
	
		$(function() {
			$('#addProjectCycleInfo').submit(
				function() {
					var rs = tirm2("input[name='name']");
					var rs1=tirm(".input1");
					var rs2=tirm(".input2");
					var rs3=tirm(".input3");
					var rs4=tirm(".input4");
					var rs7=tirm(".input5");
					var rs5 = tirm3('select[name="startyear"] option:selected');
					var rs6 = tirm3('select[name="endyear"] option:selected');
					var flag=true;
					if(!rs){
						$("input[name='name']").css({"border-color":"#f00","box-shadow":"none"}).parent().parent().find("b").html("项目名称不能为空");	
						flag=false;
					}
					if(!rs5){
						$('select[name="startyear"]').css({"border-color":"#f00","box-shadow":"none"}).parent().parent().find("b").html("请选择起始年份");
						flag=false;
					}
					if(!rs6){
						$('select[name="endyear"]').css({"border-color":"#f00","box-shadow":"none"}).parent().parent().find("b").html("请选择截止年份");
						flag=false;
					}
					
					if(rs1&&rs2&&rs3&&rs4&&rs7&&flag==true){
						var str = $(this).serialize();
						$.get('../admin/projectCycle_add.action?method=add&'+ str, function(data) {
							var Result = data.Result;
							var message = data.Message;
							if (Result == "OK") {
								window.top.location.reload();
							} else {
								$('.alert-danger').html(message).show();
							}
						})
					}else{
						if(!rs1){
							$(".input1").css({"border-color":"#f00","box-shadow":"none"}).parent().parent().find("b").html("填写必须全部为数字且为正整数");
						}else{
							$(".input1").removeAttr("style").parent().parent().find("b").html("");
						}
						if(!rs2){
							$(".input2").css({"border-color":"#f00","box-shadow":"none"}).parent().parent().find("b").html("填写必须全部为数字且为正整数");
						}else{
							$(".input2").removeAttr("style").parent().parent().find("b").html("");
						}
						if(!rs3){
							$(".input3").css({"border-color":"#f00","box-shadow":"none"}).parent().parent().find("b").html("填写必须全部为数字且为正整数");
						}else{
							$(".input3").removeAttr("style").parent().parent().find("b").html("");
						}
						if(!rs4){
							$(".input4").css({"border-color":"#f00","box-shadow":"none"}).parent().parent().find("b").html("填写必须全部为数字且为正整数");
						}else{
							$(".input4").removeAttr("style").parent().parent().find("b").html("");
						}
						if(!rs7){
							$(".input5").css({"border-color":"#f00","box-shadow":"none"}).parent().parent().find("b").html("填写必须全部为数字且为正整数");
						}else{
							$(".input5").removeAttr("style").parent().parent().find("b").html("");
						}
					}
					return false;
				});

		})

	</script>
