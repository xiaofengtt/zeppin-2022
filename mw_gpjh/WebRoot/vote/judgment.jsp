<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!doctype html>
<html>
<head>

<title>“国培计划”专家评审</title>

<link rel="stylesheet" href="css/stylejudg.css">
<!--<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script> -->
<script src="js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script>

	function ctClick(v){
		$('input[name="score"]').val(v);
	}
	
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

		if( $("#advice").val().replace(/(^\s*)|(\s*$)/g, "") == ""){
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
					<h1 class="list-title">“国培计划”专家评审</h1>
					<s:set name="title" value="" />
					<s:iterator value="judgmentStands" id="judg">

						<s:set name="uptype" value="#judg[1]" />
						<s:if test="%{#judg[1] != #title}">
							<h2 class="list-title2">
								<span><s:property value="type" /><b class="arrow"></b></span>
							</h2>
							<table>
								<tbody>
									<s:iterator value="judgmentStands" id="judi">
										<s:if test="%{#judi[1] == #uptype}">
											<tr>
												<td width="10%"><strong><s:property
															value="#judi[2]" /></strong></td>
												<td><strong class="fl">描述：</strong>
													<div style="overflow:hidden;">
														<s:property value="#judi[8]" escape="false" />
													</div></td>
												<!-- <td width="10%"><strong>分值：&nbsp;&nbsp;&nbsp;&nbsp;</strong><span
													class="records"><s:property value="#judi[7]" /></span></td> -->
												<td width="1%">
													<strong style="display:none">得分：&nbsp;&nbsp;&nbsp;&nbsp;</strong>
													<input	type="hidden" name="score"
														value='<s:if test="%{message == 'edit'}"><s:property value='#judi[9]'/></s:if>' />
														
													<input type="hidden" name="id" value='<s:property value='#judi[0]' />'  />
												</td>
												
											</tr>
										</s:if>
									</s:iterator>
								</tbody>
							</table>
						</s:if>

						<s:set name="title" value="#judg[1]" />
					</s:iterator>
					<table class="table-blue">
						<tbody>
							<tr>
								<td width="65%" colspan="2"></td>
								<td width="15%"><strong style="display:none">合计：&nbsp;&nbsp;&nbsp;</strong><span
									style="display:none" class="red sumrecords"></span></td>
								<td width="20%"><strong>总分：&nbsp;&nbsp;&nbsp;</strong><span
									style="display:none" class="red gethowrecord"><s:property
											value="total" /></span> <span style="font-size:16px"> <input
										type="radio" onclick="ctClick(4)"
										<s:if test="total==4">checked</s:if> id="c1" name="c"
										value="4">优 <input type="radio" onclick="ctClick(3)"
										<s:if test="total==3">checked</s:if> id="c2" name="c"
										value="3">良 <input type="radio" onclick="ctClick(2)"
										<s:if test="total==2">checked</s:if> id="c3" name="c"
										value="2">中 <input type="radio" onclick="ctClick(1)"
										<s:if test="total==1">checked</s:if> id="c4" name="c"
										value="1">差
								</span></td>
							</tr>


						</tbody>
					</table>

					<h2 class="list-title2 list-title3">
						<span>评价：<b class="arrow"></b></span>
					</h2>
					<div class="pingjia">
						<p>
							<textarea cols="80" id="advice" name="advice" rows="10">
								<s:property value="advice" />
							</textarea>
						</p>
					</div>
					<p class="center">
						<input type="submit" value="确&nbsp;&nbsp;&nbsp;定" class="okbtn"><input
							type="reset" value="取&nbsp;&nbsp;&nbsp;消" class="cancelbtn">
					</p>
				</div>
			</div>
			<div class="footer">版权所有&copy;国培计划项目办</div>
		</div>
		<input type="hidden" name="edit" value="<s:property value="message"/>" />
		<input type="hidden" name="expid" value="<s:property value="expid"/>" />
	</form>
</body>
</html>
