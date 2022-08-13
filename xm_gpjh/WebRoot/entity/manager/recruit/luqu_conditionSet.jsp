<%@ page language="java" pageEncoding="UTF-8"%>  
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		
		<% String path = request.getContextPath();%>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		
		<script language="javascript">
		function showcourses() {
			//document.getElementById("courses").innerHTML="123";
		}
		
		</script>
		<script> 
	function checkScore(score){ 
		if(score==''){
		alert('成绩不能为空！');
		} else {
		var patrn=/^\d{1,3}(\.\d)?$/; 
		 if (!patrn.test(score)) {
		  alert('只能输入1-3位整数，0-1位小数');
		 }
		}
   }
   function showcheck(){
			var flag=true;     
	var obj = document.getElementsByName("score");
	for(i=0; i<obj.length; i++) {
		if(!isRight(obj[i].value)){
		flag=false;
		}
	}
	var num = document.getElementsByName("totalScore");
		if(!isRight(num[0].value)){
		flag=false;
		}
	if (!flag){
	alert('成绩为空或者格式不正确！');
	}
	return flag ;
   }
   function isRight(num){
		if(num==''){
		return false;
		} else {
		var patrn=/^\d{1,3}(\.\d)?$/; 
		 if (!patrn.test(num)) {
		  return false;
		 }
		 return true;
		}
   }
   </script>			
	</head>
	<body>
		<form name="print" id= "print" method="get" action="/entity/recruit/examresultcalculate.action" onsubmit="return showcheck();" >
			<div id="main_content">
			   <div class="content_title">统计考试录取人数</div>
			   <div class="cntent_k" align="center">
			   <div class="k_cc">
			   <table width="80%">
			   		<tr>
			   			<td colspan="2">
							<div align="center" class="postFormBox">请设置单科最低分及总分</div>
			   			</td>
			   		</tr>
			   		
			   		<tr>
			   			<td colspan='2'>
			   				<div align="left" class="postFormBox"><s:property value="siteName"/> &nbsp 层次：<s:property value="edutypeName"/> &nbsp 专业：<s:property value="majorName"/></div>
			   				<input type="hidden" name="siteName" value="<s:property value="siteName"/> " />
			   				<input type="hidden" name="edutypeName" value="<s:property value="edutypeName"/> " />
			   				<input type="hidden" name="majorName" value="<s:property value="majorName"/> " />
			   			</td>
			   		</tr>

			   		<s:iterator value="course"> 

						<tr> 
							<td> 
								<div align="left" class="postFormBox">
										<span class="name"><label>
												<s:property value="name"/> 
										<font color="#ff0000">*</font></label> </span>
								</div>
							</td> 
			   		
			   				<td><input type="hidden" name="courseId" value="<s:property value="id"/> " />
			   					<div align="left" class="postFormBox"><input type="text" name="score" id="highMath"  onblur="checkScore(this.value);"/> </div>
			   				</td>
						</tr> 
					</s:iterator> 
			   		<tr>
			   			<td>
			   				<div align="left" class="postFormBox"><span class="name"><label>总分<font color="#ff0000">*</font></label></span></div>
			   			</td>
			   			<td>
			   				<div align="left" class="postFormBox"><input type="text" name="totalScore" id="total"  onblur="checkScore(this.value);"/> </div>
			   			</td>
			   		</tr>
			   		<tr>
			   			<td colspan="2">
			   				<div align="center" class="postFormBox"><input type="submit" value="提交"/>&nbsp&nbsp
			   				<input type="button" onclick="javascript:window.history.back()" value="返回"/></div>
			   			</td>
			   		</tr>
			   </table>
			   </div>
		   </div>
		   </div>
		</form>
	</body>
</html>