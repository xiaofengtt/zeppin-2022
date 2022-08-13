<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
		*{margin:0 auto;padding: 0;}
		#select1{border:1px solid #CCCCCC; border-radius: 6px; display: inline-block;width: 528px;position: relative;margin: 10px 0;}
		.options{ z-index:2;display: none;position: absolute;top: 0;left: 0;background: #FFFFFF;border: 1px solid #313131;border-radius: 6px;min-width: 528px;height:400px;overflow-y:auto;}
		.options p label{margin-left: 5px;}
		.optionTitle{vertical-align: top; line-height: 32px; margin: 10px 5px;display: inline-block;}
		.optionTitle1{margin-top: 0;}
		#select1 p{line-height: 30px;padding-left: 10px;}
		table{margin: 0;border: 1px solid #313131;border-collapse:collapse;}
		table th,td{border: 1px solid #313131;padding: 10px 20px;}
		#select2{border:1px solid #CCCCCC; border-radius: 6px; display: inline-block;width: 528px;position: relative;}
		#select2 p{line-height: 30px;padding-left: 10px;}
		#select2 .options1{display: none;z-index: 1;position: absolute;top: 0;left: 0;background: #FFFFFF;border: 1px solid #313131;border-radius: 6px;min-width: 528px;height:400px;overflow-y:auto;}
		a{width: 90px;height: 30px; background: #2E91DA;border-radius: 4px; color: #fff; display: inline-block;text-align: center;line-height: 30px;margin:10px 30px;cursor:pointer;}
		</style>
		<script src="js/adminTraineeNumForMW.js" type="text/javascript"></script>
	</head>
	<body onclick="closeSelect()">
		<label class="optionTitle">选择项目</label>
		<div id="select1">
			<p class="choose">选择</p>
			<div class="options">
				<p onclick="closeSelect()"><input type="checkbox" id="all" value="全选"/><label for="all">全选</label></p>
			</div>
			
		</div>
		<br>
		<label class="optionTitle optionTitle1">选择学科</label>
		<div id="select2">
			<p class="choose1">选择</p>
			<div class="options1">
				<p onclick="closeSelect()"><input type="checkbox" id="all1" value="全选"/><label for="all1">全选</label></p>
			</div>
			
		</div>
		<br>
		<a class="screen">筛选</a><a class="export">导出</a>
		<table>
			<tr><th>省份</th><th>报送人数</th><th>审核人数</th><th>报道人数</th><th>结业人数</th></tr>
			<tr><td>北京</td><td></td><td></td><td></td><td></td></tr>
		</table>
		<div class="container">
			<div class="content">
				<div class="list">
					<table id="list2">
					</table>
					<div id="pager2"></div>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="js/jquery-1.7.2.min.js" ></script>
		<script type="text/javascript">
			$(function(){
				getProject();
				
			})
			
			function closeSelect(){
//				event.stopPropagation();
//				$(".options").css("display","none");
			}
			function stopPropagation(e) {
				if (e.stopPropagation)
				e.stopPropagation();
				else
				e.cancelBubble = true;
			} 
			function allchk(){ 
			    var chknum = $(".options1 .checkbox").size();//选项总个数 
			    var chk = 0; 
			   	$(".options1 .checkbox").each(function () {   
			        if($(this).attr("checked")=="checked"){ 
			            chk++; 
			        } 
			    }); 
			    if(chknum==chk){//全选 
			        $("#all1").attr("checked",true); 
			    }else{//不全选 
			        $("#all1").attr("checked",false); 
			    } 
			}
			function allchk1(){ 
			    var chknum = $(".options .checkbox").size();//选项总个数 
			    var chk = 0; 
			   	$(".options .checkbox").each(function () {   
			        if($(this).attr("checked")=="checked"){ 
			            chk++; 
			        } 
			    }); 
			    if(chknum==chk){//全选 
			        $("#all").attr("checked",true); 
			    }else{//不全选 
			        $("#all").attr("checked",false); 
			    } 
			}
			//获取项目
			function getProject(){
				$.get('../mw_gpjh/trainee_headsearchLv.action?userid=shengting&role=1',function(r){
					var str="";
					var strs="";
					for ( var i = 1, l = r.preInitData.parent.length; i < l; i++ ) {						
						str+="<p onclick='closeSelect()'><input class='checkbox' type='checkbox' id='"+r.preInitData.parent[i].id+"' value='"+r.preInitData.parent[i].name+"'/><label for='"+r.preInitData.parent[i].id+"'>"+r.preInitData.parent[i].name+"</label></p>";						
					}
					$(".options").append(str);
					for ( var i = 0, l = r.preInitData.subject.length; i < l; i++ ) {						
						strs+="<p onclick='closeSelect()'><input class='checkbox' type='checkbox' id='"+r.preInitData.subject[i].id+"' value='"+r.preInitData.subject[i].name+"'/><label for='"+r.preInitData.subject[i].id+"'>"+r.preInitData.subject[i].name+"</label></p>";					
					}
					$(".options1").append(strs);
				}).done(function(){
					$("#select1").click(function(e){
						stopPropagation(e);
						$(".options").stop().fadeIn();
					})
					$("#select2").click(function(e){
						stopPropagation(e);
						$(".options1").stop().fadeIn();
					})
					$("#all").click(function(){ 
		   				if(this.checked){    
		        				$(".options :checkbox").attr("checked", "checked"); 
		    				}else{    
		       				$(".options :checkbox").attr("checked", false); 
		    				}    
					});
					
					$("#all1").click(function(){ 
		   				if(this.checked){    
		        				$(".options1 :checkbox").attr("checked", "checked"); 
		    				}else{    
		       				$(".options1 :checkbox").attr("checked", false); 
		    				}    
					});
					$(".options1 .checkbox").click(function(){ 
					    allchk(); 
					}); 
					$(".options .checkbox").click(function(){ 
					    allchk1(); 
					}); 
	                $(document).click(function(e){
	                     stopPropagation(e);
	                    $(".options").hide();
	                    $(".options1").hide();
	                    var valArr = new Array; 
					    $(".options :checkbox[checked]").each(function(i){ 
					        valArr[i] = $(this).val(); 
					    }); 
					    var vals = valArr.join(',');//转换为逗号隔开的字符串 
					    $(".choose").html(valArr[0]); 
					    var valArr1 = new Array; 
					    $(".options1 :checkbox[checked]").each(function(i){ 
					        valArr1[i] = $(this).val(); 
					    }); 
					    var vals1 = valArr1.join(',');//转换为逗号隔开的字符串 
					    $(".choose1").html(valArr1[0]); 
	                })
				})
			}
		</script>
	</body>
</html>