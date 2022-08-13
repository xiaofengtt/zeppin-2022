<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!doctype html>
<html>
<head>

<title>专家意见</title>

<link rel="stylesheet" href="css/stylejudg.css">
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>
   function sum(arr) {//求和函数
	  var result = 0, n = arr.length || 0; 
	  while(n--) {
		result += +arr[n]; 
	  }
	  return result;
	}
	
	$(function(){
		var arr1 = [];
		var arr2 = [];
		$('.records').each(function(){
			arr1.push($(this).text());
		});
		$('.sumrecords').html(sum(arr1));//初始化
		
		$('.getrecord').change(function(){
			var re = /^[0-9]+\.{0,1}[0-9]{0,1}$/
			var v = parseFloat($(this).val());
			var t = parseFloat($(this).parent().prev('td').find('.records').text());

			if(!re.test(v)){
				alert('得分必须是数字 #.#');
				return;
			}
			if((v > t)) {
				alert('得分不能大于分值');
				$(this).focus();
				return
			}
			arr2 = [];
			$('.getrecord').each(function(){
				arr2.push($(this).val());
			});
			$('.gethowrecord').html(sum(arr2));
		})
		
	})
	
	$(document).ready(function(){
	 $("form").submit(function(e){
	  
	    //$('.getrecord').each(function(){
	    
		var scores = $(".getrecord");
		for(var i =0;i<scores.length;i++ ){
			//arr2.push($(this).val());
			//var re = /^[0-9]\d*$/;
			var re = /^[0-9]+\.{0,1}[0-9]{0,1}$/
			var v = parseFloat($(scores[i]).val());
			var t = parseFloat($(scores[i]).parent().prev('td').find('.records').text());
			
			if(v == ""){
				alert("请填写打分数据");
				$(scores[i]).focus();
			 	return false;
			}
			
			if(!re.test(v)){
				alert('得分必须是数字');
				$(scores[i]).focus();
				return false;
			}
			if((v > t)) {
				alert('得分不能大于分值');
				$(scores[i]).focus();
				return false;
			}
		}
		
		if( $("#advice").val() == ""){
			alert("请填写意见");
			$("#advice").focus();
			return false;
		}
		
		return true;
	  });
	});	

  </script>
</head>

<body>

	<form action="exp_expertSave.action" method="POST">
		<div id="container">
			<div class="header"></div>
			<div class="main">

				<div class="table table-list">
					<h1 class="list-title">国培中西部项目专家评分意见</h1>
					<table>
						<tbody>
							<tr>
								<td><strong>专家</strong></td>
								<td><strong>分值</strong></td>
								<td><strong>意见</strong></td>
							</tr>
							<s:iterator value="yijian" id="yij">
								<tr>
									<td><s:property value="#yij[2]" /></td>
									<td><s:property value="#yij[0]" /></td>
									<td><s:property value="#yij[1]" /></td>
								</tr>
							</s:iterator>
						</tbody>
					</table>

				</div>
			</div>
			<div class="footer">版权所有&copy;国培计划项目办</div>
		</div>
		<input type="hidden" name="edit" value="<s:property value="message"/>" />
		<input type="hidden" name="expid" value="<s:property value="expid"/>" />
	</form>
</body>
</html>
