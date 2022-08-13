<%@page import="org.apache.struts2.components.Include"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:action name="head_init" namespace="/admin" executeResult="true"
	ignoreContextParams="false">
</s:action>
<s:action name="left_init" namespace="/admin" executeResult="true">
	<s:param name="leftName">账号权限管理</s:param>
</s:action>
<link rel="stylesheet" href="../css/iframe.css">
<script type="text/javascript" src="../js/layer-v2.2/layer/layer.js"></script>
<style type="text/css">
	*{margin: 0 auto;padding: 0;}
	table{width: 100%;text-align: left;border-collapse: collapse;color: #1a1a1a;font-size: 14px;margin-top:15px;}
	table tr{}
	table th{color:#333;background:#efefef;}
	tr th,td{border-bottom:1px solid #ddd;padding:10px 5px;text-align:center;}
	a{text-decoration: none;color: #4365be;cursor: pointer;}
	a:hover{text-decoration: underline;}
	table input{width: 90%;height: 30px;font-size: 14px;color: #666666;padding-left: 5px;}
	table div{line-height: 30px;text-align:center;}
	table div.td1{display: none;}
	.save{display: none;}
	.form-control{font-size:14px;}
	.td{text-align:left;}
	.stateDiv a:hover{text-decoration: none;}
	.delete{margin-left:8px;}
	.iconDiv p img{left:0;}
</style>
<div class="main">
	<div class="listwrap">
		<div class="list_bar">账号权限管理 </div>
		<div class="iconDiv">
			<a class="btn btn-create btnMyself btn-screen"
				href=""
				data-fancybox-type="iframe"> <span><img
					src="../img/kaishexiangmu.png" alt="icon"> <b>设置</b> </span>
				<p>
					<img src="../img/lanse.png" alt="蓝色三角"> <b>设置权限</b>
				</p>
			</a>
		</div>
			<div class="stateDiv stateDivyi">
<!-- 				<label>状态&nbsp;：</label> -->
				<a class="light" value="1">项目管理员</a>
				
				<a class="" value="2">承训单位管理员</a>
				<a class="" value="4">评审专家</a>
				<a class="" value="5">超级管理员</a>
			</div>
			<div class="stateDiv stateDiver">
<!-- 				<label>状态&nbsp;：</label> -->
				<a class="light" value="1">自治区教育厅管理员</a>
				
				<a class="" value="2">地市州教育局管理员</a>
				<a class="" value="3">县市区教育局管理员</a>
				<a class="" value="4">普通派出学校管理员</a>
			</div>
		</div>
		<div id="list-content" class="list-content clearfix">
			<table class="table">
			
		</table>
					
		</div>
	</div>


<script type="text/javascript">
	var firstUser = (url('?firstNumber') != null) ? url('?firstNumber') : '0';
	var secondUser = (url('?secondNumber') != null) ? url('?secondNumber') : '0';
			$(function(){
				if(firstUser!=0){
					$(".stateDiver").css("display","none");
				}else{
					$(".stateDiver").css("display","block");
				}
				$(".stateDivyi a:eq("+firstUser+")").addClass("light").siblings().removeClass("light");
				$(".stateDiver a:eq("+secondUser+")").addClass("light").siblings().removeClass("light");
				getTable("role="+$(".stateDivyi a.light").attr("value")+"&level="+$(".stateDiver a.light").attr("value"));
				$(".btn-create,.ifrmbox").colorbox({
					iframe : true,
					width : "620px",
					height : "600px",
					opacity : '0.5',
					overlayClose : false,
					escKey : true

				});
				var role = $(".stateDivyi a.light").attr("value");
				var level = $(".stateDiver a.light").attr("value");
				
					$(".btn-create").attr('href','../admin/left_initAdd.action?role='+role+'&level='+level);
				
			})
			function getTable(url,first,second){
				if(first!=undefined){
					$(".stateDivyi a:eq("+first+")").addClass("light");
				}
				if(second!=undefined){
					$(".stateDiver a:eq("+second+")").addClass("light");
				}
				var urlstr = "<tr><th width='30%' style='text-align:left;'>一级菜单</th><th width=’40%' style='text-align:left;'>二级菜单</th><th width='15%' align='center'>状态</th><th width='15%'>操作</th></tr>";
				$.getJSON('../admin/left_getList.action?'+url,
						function(data) {
					if(data.Result=="OK"){
						var statusFir;
						var option;
						for(var i=0;i<data.Records.length;i++){//<td class="td">'+data.Records[i].id+'</td>
						if(data.Records[i].status=="0"){
							statusFir="停用";
							option="<select class='form-control'><option value='1'>正常</option><option value='0' selected>停用</option><select>";
						}else if(data.Records[i].status=="1"){
							statusFir="正常";
							option="<select class='form-control'><option value='1' selected>正常</option><option value='0' >停用</option><select>";
						} 
							urlstr+='<tr><td class="td"><div class="td td2 td3">'+data.Records[i].name+'</div><div class="td1 td11"><input class="form-control" value="'+data.Records[i].name+'" type="text"></div></td><td class="td"></td><td class=""><div class="td2 td4">'+statusFir+'</div><div class="td1 td12">'+option+'</div></td><td><a href="javascript:" class="modify">修改</a><a class="save" name="'+ data.Records[i].id+ '" custom="'+ data.Records[i].id+ '" onclick="modify(this)">保存</a><a class="delete" onclick="dele('+data.Records[i].id+')">删除</a></td></tr>';
						 	if(data.Records[i]["childrens"]!=null){
						 		for(var j=0;j<data.Records[i]["childrens"].length;j++){
									var name = data.Records[i]["childrens"][j].name;
									var id = data.Records[i]["childrens"][j].id;
									var status = data.Records[i]["childrens"][j].status;
									if(data.Records[i]["childrens"][j].status=="0"){
										status="停用";
										option="<select class='form-control'><option value='1'>正常</option><option value='0' selected>停用</option><select>";
									}else if(data.Records[i]["childrens"][j].status=="1"){
										status="正常";
										option="<select class='form-control'><option value='1' selected>正常</option><option value='0' >停用</option><select>";
									}
									urlstr+='<tr><td class="td"></td><td class="td"><div class="td td2 td3">'+name+'</div><div class="td1 td11"><input class="form-control" value="'+name+'" type="text"></div></td><td class=""><div class="td2 td4">'+status+'</div><div class="td1 td12">'+option+'</div></td><td><a href="javascript:" class="modify">修改</a><a class="save" name="'+ data.Records[i]["childrens"][j].id+ '" custom="'+ data.Records[i]["childrens"][j].id+ '" onclick="modify(this)">保存</a><a class="delete" onclick="dele('+data.Records[i]["childrens"][j].id+')">删除</a></td></tr>';//<td class="td">'+id+'</td>
						 		}
						 	}
						 }
						 $("table").html(urlstr);
					}else{
						
					}
					
				}).done(function(){
					$(".modify").click(function(){
						$(this).parent().parent().find("div.td2").css("display","none");
						$(this).parent().parent().find("div.td1").css("display","inline");
						$(this).css("display","none");
						$(this).parent().parent().find(".save").css("display","inline");
					})
					$("input").blur(function(){
						var value = $(this).val();
						value=value.replace(/(^\s*)|(\s*$)/g, "");
					
							if(value!=""){
								$(this).removeAttr("style");
							    return true;
							}else{
								$(this).css({"border-color":"#f00","box-shadow":"none"});
								layer.confirm('填写菜单不能为空', {
									  btn: ['确定'] //按钮
									}, function(){
									  layer.closeAll();
									});
							    return false;
							}
					})
				})
			}
			$(".btn-create").click(function(){
				$(".btn-create").attr('href','../admin/left_initAdd.action?role='+$(".stateDivyi a.light").attr("value")+'&level='+$(".stateDiver a.light").attr("value")+'&firstUser='+$(".stateDivyi a.light").index()+'&secondUser='+$(".stateDiver a.light").index());
			})
			
			//修改
			function modify(obj){
				var rs1=tirm($(obj).parent().parent().find("div.td11 input").val());
				if(rs1){
					var id=$(obj).attr("name");
					var name=$(obj).parent().parent().find("div.td11 input").val().replace(/(^\s*)|(\s*$)/g, "");
					var state=$(obj).parent().parent().find("div.td12 select").val();
					var stateCN;
					if(state=="0"){
						stateCN="停用";
					}else if(state=="1"){
						stateCN="正常";
					}
					$.getJSON('../admin/left_setup.action?method=edit&id='+id+'&name='+name+'&status='+state,
							function(data) {
						if(data.Result=="OK"){
							$(obj).attr("name",data.id);
							$(obj).parent().parent().find("div.td3").html(name);
							$(obj).parent().parent().find("div.td4").html(stateCN);
							$(obj).parent().parent().find("div.td2").css("display","inline");
							$(obj).parent().parent().find("div.td1").css("display","none");
							$(obj).css("display","none");
							$(obj).parent().parent().find(".modify").css("display","inline");
							if($(".stateDiver").css("display")!="block"){
								url="role="+$(".stateDivyi a.light").attr("value");
							}else{
								url="role=1&level="+$(".stateDiver a.light").attr("value");
							}
							getTable(url);
						}else{
							layer.confirm(data.Message, {
								  btn: ['确定'] //按钮
								}, function(){
								  layer.closeAll();
								});
						}
					})
				}else{
					layer.confirm('填写菜单不能为空', {
						  btn: ['确定'] //按钮
						}, function(){
						  layer.closeAll();
						});
				}
				
			}
			//验证
			function tirm(value){
				var values=value.replace(/(^\s*)|(\s*$)/g, "");
				if(values==""){
					$(this).css({
						"border-color" : "#f00",
						"box-shadow" : "none"
					});
					return false;
				}else{
					
					return true;
				}			
			}
			var url;
			//点击按钮筛选
			$(".stateDivyi a").click(function(){
				$(this).addClass("light").siblings().removeClass("light");
				if($(this).index()!=0){
					url="role="+$(this).attr("value");
					$(".stateDiver").css("display","none");
				}else{
					$(".stateDiver").css("display","block");
					url="role="+$(this).attr("value")+"&level="+$(".stateDiver a.light").attr("value");
				}
				getTable(url);
				
				var role = $(".stateDivyi a.light").attr("value");
				var level = $(".stateDiver a.light").attr("value");
				
				$(".btn-create").attr('href','../admin/left_initAdd.action?role='+role+'&level='+level);
			})
			$(".stateDiver a").click(function(){
				$(this).addClass("light").siblings().removeClass("light");
				url="role=1&level="+$(".stateDiver a.light").attr("value");
				getTable(url);
				
				var role = $(".stateDivyi a.light").attr("value");
				var level = $(".stateDiver a.light").attr("value");
				
				$(".btn-create").attr('href','../admin/left_initAdd.action?role='+role+'&level='+level);
			})
			//删除
			function dele(obj){
				layer.confirm('确定要删除吗？', {
					  btn: ['确定','取消'] //按钮
					}, function(){
						$.getJSON('../admin/left_delete.action?id='+obj,function(data){
							var firstNumber;
							var secondNumber;
							if(data.Result=="OK"){
								if($(".stateDiver").css("display")!="block"){
									url="role="+$(".stateDivyi a.light").attr("value");
									firstNumber=$(".stateDivyi a.light").index();
								}else{
									url="role=1&level="+$(".stateDiver a.light").attr("value");
									firstNumber=$(".stateDivyi a.light").index();
									secondNumber=$(".stateDiver a.light").index();
								}
								window.location.href="../admin/left_funCategoryInit.action?firstNumber="+$(".stateDivyi a.light").index()+"&secondNumber="+$(".stateDiver a.light").index();
								getTable(url);
								
							}else{
								layer.confirm(data.Message, {
									btn : [ '确定' ]
								}, function() {
									layer.closeAll();
								});
							}
						})
						layer.closeAll();
						
					}, function(){
					  layer.close();
					});
				
			}
		</script>


<jsp:include page="foot.jsp"></jsp:include>