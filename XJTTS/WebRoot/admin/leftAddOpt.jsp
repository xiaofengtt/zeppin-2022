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


<%-- <script src="../js/iframe.js"></script> --%>
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
input.span5, textarea.span5, .uneditable-input.span5{width:380px;}
input[type="checkbox"]{height:20px !important;}
label{display:inline;margin-left:5px;}
#childlist{max-height:280px;overflow:auto;width:366px;border:1px solid #ccc;border-radius:4px;padding:4px 6px;}
</style>
<script type="text/javascript" src="../js/layer-v2.2/layer/layer.js"></script>
<div class="main">
	<div class="ifrcnt container">
		<div class="hd">
			<h3>添加（为当前角色添加未设置的操作权限）</h3>
		</div>
		<div class="bd">
			<div class="alert alert-danger"
				style="display:none;margin:0 15px 15px;"></div>
			<form id="addFuncategoryInfo" class="form-horizontal" role="form"
				method="post">
				<input type="hidden" name="role" value="<s:property value="role" />">
				<input type="hidden" name="level" value="<s:property value="level" />">
				
				<div class="clearfix">

						<div class="control-group">
							<label class="control-label" for="">管理员角色</label>
							<div class="controls">

								<input type="text" readonly  class="span5 readonly"
									value="<s:property value="manager" />" placeholder="">
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="">一级功能菜单</label>
							<div class="controls">
								<select class="span5" name="funcategory" onchange="getchildren(this);">
									<s:iterator value="lstfc" id="funcategory">
										<option value="<s:property value="#funcategory.getId()" />"><s:property
												value="#funcategory.getName()" /></option>
									</s:iterator>
								</select>
							</div>
						</div>
						<div class="control-group" id="child" style="display: none;">
							<label class="control-label" for="">二级功能菜单</label>
							<div class="controls" style="">

								<div class="" style="">
									<ul>
											<li id="childlist">
												
											</li>
									</ul>
								</div>
							</div>
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
	<script type="text/javascript" src="../js/jquery-1.8.3.min.js" ></script>
	<script>
	var firstUser = (url('?firstUser') != null) ? url('?firstUser') : '';
	var secondUser = (url('?secondUser') != null) ? url('?secondUser') : '';
	$(function() {
		
		$(document).ready(function() {
			$(".input").blur(function(){
				var value = $(this).val();
				value=value.replace(/(^\s*)|(\s*$)/g, "");
				var reg=/^\d+$/; 
				if(reg.test(value)==true){
					$(this).removeAttr("style");
				    return true;
				}else{
					if(value==""){
						$(this).removeAttr("style");
					    return true;
					}else{
						$(this).css({"border-color":"#f00","box-shadow":"none"});
						layer.confirm('填写必须全部为数字且为正整数', {
							  btn: ['确定'] //按钮
							}, function(){
							  layer.closeAll();
							});
					    return false;
					}
					
				}
			})
		});
		
		
			var id = $('input[name="id"]').val();
			if (!id) {
				$('#projecttypecnt').show();

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
			
			//项目类型 helper 函数
			$(document).on("click",function(a) {
				if(!$(a.target).parents().is('.ufc'))
					$('.uul').hide();
				if(!$(a.target).parents().is('.companylocation'))
					$('.listSub').hide();
			});


		})
		
		
	function tirm(value){
		var values=value.replace(/(^\s*)|(\s*$)/g, "");
			var reg=/^\d+$/; 
			if(reg.test(values)==true){
			    return true;
			}else{
				if(values==""){
					return true;
				}else{
					$(this).css({
						"border-color" : "#f00",
						"box-shadow" : "none"
					});
					return false;
				}			
			}
		}
		
		$(function() {
			$('#addFuncategoryInfo').submit(
				function() {
// 					var str = $(this).serialize();
					var obj = $('select[name="funcategory"]').find('option:selected').val();
					if(obj=='0'){
						layer.confirm('请选择一级功能菜单', {
							  btn: ['确定'] //按钮
							}, function(){
							  layer.closeAll();
							});
						return false;
					}
					var role = $('input[name="role"]').val();
					var level = $('input[name="level"]').val();
					var all = $('input[name="children"]');
					var checkstr = '';
					if(all.length>0){
						for(var i=0; i<all.length; i++){
							if(all[i].checked == true){
								checkstr+=all[i].value+",";
							 }
						}
						if(checkstr == ''){
							layer.confirm('请选择二级功能菜单再提交', {
								  btn: ['确定'] //按钮
								}, function(){
								  layer.closeAll();
								});
							return false;
						}else{
							checkstr = checkstr.substring(0,checkstr.length-1);
						}
					}
// 					var str = 'role='+role+'&level='+level+'children='+checkstr
					$.post('../admin/left_setup.action?method=add',{
						role:role,
						level:level,
						children:checkstr
					}, function(data) {
						var Result = data.Result;
						var message = data.Message;
						if (Result == "OK") {
							layer.msg('操作成功');
							setTimeout("window.top.location.href='../admin/left_funCategoryInit.action?firstNumber="+firstUser+"&secondNumber="+secondUser+"'",1000);
						} else {
							layer.confirm(message, {
								  btn: ['确定'] //按钮
								}, function(){
								  layer.closeAll();
								});
						}
					})
					return false;
				});

		})
	function changeClbtn(e) {
		e.next('.listSub').toggle();
	}
		
	function getchildren(e) {
		var obj = $(e).find('option:selected').val();
		var role = $('input[name="role"]').val();
		var level = $('input[name="level"]').val();
		if(obj=='0'){
			$("#child").hide();
			return false;
		}
		$.getJSON('../admin/left_getOtherChildFuncategory.action?funcategory='+obj+'&role='+role+'&level='+level,function(data){
			if(data.Result=="OK"){
// 				<li id="childlist">
// 				<input type="checkbox" id="" name="children" value="0" style="margin:0px" checked>请选择
// 			</li>
				var childstr = '<p style="margin-bottom:5px;"><input type="checkbox" id="selectall" value="0" style="margin:0px" checked><label for="selectall">全选</label></p>';
				if(data.Records.length > 0){
					for(var i = 0; i < data.Records.length; i++){
						childstr += '<p style="margin-bottom:5px;"><input class="checkbox" type="checkbox" id="'+i+'" name="children" value="'+data.Records[i].id+'" style="margin:0px" checked><label for='+i+'>'+data.Records[i].name+'</label></p>';
					}
					$("#childlist").html(childstr);
					$("#child").show();
				}else{
					$("#child").hide();
					layer.confirm('下属功能已全部添加，无需再次添加', {
						btn : [ '确定' ]
					}, function() {
						layer.closeAll();
					});
					return false;
				}
				
			}else{
				layer.confirm(data.Message, {
					btn : [ '确定' ]
				}, function() {
					layer.closeAll();
				});
			}
		}).done(function(){
			$("#selectall").click(function(){ 
				if(this.checked){    
					$(":checkbox").attr("checked","checked"); 
				}else{    
					$(":checkbox").attr("checked",false); 
				}    
			});
			$("#childlist .checkbox").click(function(){ 
			    allchk(); 
			}); 
			function allchk(){ 
			    var chknum = $("#childlist .checkbox").size();//选项总个数 
			    var chk = 0; 
			   	$("#childlist .checkbox").each(function () {   
			        if($(this).attr("checked")=="checked"){ 
			            chk++; 
			        } 
			    }); 
			    if(chknum==chk){//全选 
			        $("#selectall").attr("checked",true); 
			    }else{//不全选 
			        $("#selectall").attr("checked",false); 
			    } 
			}
		})
	}
	
	
	

	</script>
