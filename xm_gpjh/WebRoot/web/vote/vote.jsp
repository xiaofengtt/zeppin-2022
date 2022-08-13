<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
<base href="<%=basePath%>">
<title>调查问卷</title>
<link href="/web/vote/css.css" rel="stylesheet" type="text/css">


<script type="text/javascript">
	function toresult(){
	//	var id = '<s:property value="peVotePaper.id"/>' ;
	//	window.location="/entity/first/firstPeVotePaper_voteResult.action?bean.id=" + id;
	}
	function checkVoteTeacher(){
		var questSize = <s:property value="questionList.size"/>;
		for (var b=1;b <= questSize;b++){
			var flag = false; //alert(b);return false;
			var id = document.getElementById('hidden'+b).value;
			var radioName = document.getElementsByName(id);
			for(var i=0;i<radioName.length;i++){
				if(radioName[i].checked){
					flag = true;
				}
			}
			if(!flag){
				alert("第"+b+"题未选，请选择！");
				return false;
			}
		}
		//alert(radioName);
		//return false;
		var questtype = <s:property value="getAddQuestType()"/>;
		if(questtype=='1'){
			var bestTrain = document.getElementById("bestTraining");
			if(bestTrain.value.length<=1){
				alert("请确保您正确填写了附加题！");
				return false;
			}
		}
		
		var firstVote=document.getElementById("firstTheme");
		var secondVote=document.getElementById("secondTheme");
		var thirdVote=document.getElementById("thirdTheme");
		var fouthVote=document.getElementById("fouthTheme");
		var fifthVote=document.getElementById("fifthTheme");
		//if(firstVote.value==""||secondVote.value==""||thirdVote.value==""||fouthVote.value==""||fifthVote.value==""){
		//	alert("请确保您正确选了前5名最受您欢迎的老师和专题！");
		//	return false;
		//}
	//	if(firstVote.value==secondVote.value||firstVote.value==thirdVote.value||firstVote.value==fouthVote.value||firstVote.value==fifthVote.value){
		//	alert("请确保您选择的老师和专题没有重复！");
	//		return false;
	//	}
	
		var s=document.getElementById('voteResult');
		
	    s.submit();
	}
	function checkLength(obj,len){
		if(obj.value.length >(len)){
			alert('您输入的字符数超过了指定长度,请检查重新输入！');
			obj.value = obj.value.substr(0,len);
			return false;
		}
	}
	function getAjax() {
		var a = null;
		try {
			a = new ActiveXObject("Msxml2.XMLHTTP");
		}catch (b) {
			try {
				a = new ActiveXObject("Microsoft.XMLHTTP");
			}catch (c) {
				a = null;
			}
		}
		if (!a && typeof XMLHttpRequest != "undefined") {
			a = new XMLHttpRequest;
		}
		return a;
	}
	function bindingFun(){
		var selArr = document.getElementsByTagName("select");
		var selectElement;
		//alert(selArr.length);
		for(var i=0;i<selArr.length;i=i+2) {
			selectElement = selArr[i];
			selectElement.onchange=function() {
				alert(selectElement.selectedIndex);
			}
		}
	}
	function selectOpt(parentSel,value) {
		var selLength = parentSel.nextSibling.nextSibling.options.length;
			for(var j=selLength-1;j>0;j--){
				parentSel.nextSibling.nextSibling.remove(j);
		}
		if(value=="") {
			return;
		}
		var xhr = getAjax();
		//alert(parentSel.nextSibling.nextSibling.nodeType);
		xhr.open("get", "/entity/information/peVotePaper_searchTheme.action?selData="+encodeURI(value),true);
		xhr.onreadystatechange = function() {
			var state = xhr.readyState;
			if(state == 4) {
				var result = xhr.responseText;
				var resultArr = result.split(";");
				for(var i=0;i<resultArr.length;i++) {
					var option = new Option();
					var temp = resultArr[i];
					var temp_result = temp.split(",");//alert(temp_result[0]);alert(temp_result[1]);
					option.value = temp_result[0];
					option.text = temp_result[1];
					//var option = document.createElement("<option value='" + i + "'>" + resultArr[i] + "</option>");
					parentSel.nextSibling.nextSibling.add(option);
				}
			}
		}
		xhr.send(null);
	}
</script>			
</head>
  
<body topmargin="0" leftmargin="0" bgcolor="#EEEEEE">
  <form name="vote" id="voteResult" method=post action="/entity/first/firstPeVotePaper_vote.action">
    <input type="hidden" name="peVotePaper.id" value="<s:property value="peVotePaper.id"/>" />
	<table width="780" border="1" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td height="108" align="center" background="/entity/manager/images/votebg.gif" class="16title">
				<s:property value="peVotePaper.pictitle"/>
			</td>
		</tr>
		<tr>
			<td bgcolor="#FFFFFF">
				<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
					<tr>
						<td align="center" valign="bottom" class="14title">
							<s:property value="peVotePaper.title"/>
						</td>
					</tr>
					<tr>
						<td align="center" valign="bottom" class="12content">
							<s:date name="peVotePaper.startDate" format="yyyy-MM-dd" />~~<s:date name="peVotePaper.endDate" format="yyyy-MM-dd" />
							&nbsp;&nbsp;<s:if test="pastDue==1">还未开始</s:if><s:elseif test="pastDue==2">已过期</s:elseif> 
						</td>
					</tr>
					<tr>
						<td align="center"><img src="/entity/manager/images/votebian.gif" width="417" height="4"></td>
					</tr>
					<tr>
						<td class="12texthei" style="padding-top:15px"><s:property value="peVotePaper.note" escape="false"/></td>
					</tr>
					<tr>
						<td>
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="12content">
								<%@ include file="/web/vote/vote_include.jsp" %>
								<tr> 
									<td bgcolor="#F9F9F9" height=10></td>
								</tr>
								<s:if test="getAddQuestType()==1">
								<tr>
									<td bgcolor="#F9F9F9" class="14title">
										<font size=:30px color="red">附加题：</font>参加本次国家级培训活动，您感到效果最好的培训内容是:
									</td>
								</tr>
								<tr>
									<td bgcolor="#F9F9F9">
								  	<textarea id="bestTraining" name="bestTraining" onkeyup="checkLength(this,500)" cols="65" rows="6"></textarea>
								  </td>
								</tr></s:if>
								<s:else>
								<tr>
									<td bgcolor="#F9F9F9" class="14title">
										<font size=:30px color="red">附加题：</font>请选择前五名您最喜欢的老师和专题！
									</td>
								</tr>
								<tr>
									<td>第一名：
									
										<select onchange="selectOpt(this,this.value)" name="firstList" id="firstList" class="input6303">
											<option value="">请选择老师</option>
							                <s:iterator value="traineeTeacherAndTheme" id="first_teacher_">
							                   <option value="<s:property value="#first_teacher_[0]"/>,<s:property value="#first_teacher_[1]"/>,<s:property value="#first_teacher_[2]"/>,<s:property value="#first_teacher_[3]"/>" ><s:property value="#first_teacher_"/></option>
							                </s:iterator>
                						</select>
                						 
						               <select name="firstListTheme" id='firstTheme' >
						               		<option value="">请选择专题</option>
									   </select>
									   
									</td>
              					</tr>
								<tr><td>&nbsp;</td></tr>
					           <tr>
									<td>第二名：
										<select  onchange="selectOpt(this,this.value)" name="secondList" id="secondList" class="input6303">
											<option value="">请选择老师</option>
							                <s:iterator value="traineeTeacherAndTheme" id="first_teacher_">
							                   <option value="<s:property value="#first_teacher_[0]"/>,<s:property value="#first_teacher_[1]"/>,<s:property value="#first_teacher_[2]"/>,<s:property value="#first_teacher_[3]"/>"><s:property value="#first_teacher_"/></option>
							                </s:iterator>
						                </select>
										<select name="secondListTheme" id='secondTheme' class="input6303">
                							<option value="">请选择专题</option>
			   							</select>
               						</td>
              					</tr>
             					<tr><td>&nbsp;</td></tr>
              					<tr>
					               <td>第三名：
					               		<select onchange="selectOpt(this,this.value)" name="thirdList" id="thirdList" class="input6303">
						               		<option value="">请选择老师</option>
							                <s:iterator value="traineeTeacherAndTheme"  id="first_teacher_">
							                   <option value="<s:property value="#first_teacher_[0]"/>,<s:property value="#first_teacher_[1]"/>,<s:property value="#first_teacher_[2]"/>,<s:property value="#first_teacher_[3]"/>"><s:property value="#first_teacher_"/></option>
							                </s:iterator>
					                	</select>
						                <select name="thirdListTheme" id='ThirdTheme' class="input6303">
						                	<option value="">请选择专题</option>
									   	</select>
					               </td>
								</tr>
              					<tr><td>&nbsp;</td></tr>
              					<tr>
               						<td>第四名：
               							<select onchange="selectOpt(this,this.value)" name="fouthList" id="fouthList" class="input6303">
						                	<option value="">请选择老师</option>
							                <s:iterator value="traineeTeacherAndTheme"  id="first_teacher_">
							                   <option value="<s:property value="#first_teacher_[0]"/>,<s:property value="#first_teacher_[1]"/>,<s:property value="#first_teacher_[2]"/>,<s:property value="#first_teacher_[3]"/>" ><s:property value="#first_teacher_"/></option>
							                </s:iterator>
						                </select>
						                <select name="fouthListTheme" id='fouthTheme' class="input6303">
						                 <option value="">请选择专题</option>
									   </select>
               						</td>
              					</tr>
             					<tr><td>&nbsp;</td></tr>
              					<tr>
               						<td>第五名：
               							<select onchange="selectOpt(this,this.value)" name="fifthList" id="fifthList" class="input6303">
						               		<option value="">请选择老师</option>
							                <s:iterator value="traineeTeacherAndTheme"  id="first_teacher_">
							                   <option value="<s:property value="#first_teacher_[0]"/>,<s:property value="#first_teacher_[1]"/>,<s:property value="#first_teacher_[2]"/>,<s:property value="#first_teacher_[3]"/>"><s:property value="#first_teacher_"/></option>
							                </s:iterator>
						                </select>
						                <select name="fifthListTheme" id='fifthTheme' class="input6303">
						                 <option value="">请选择专题</option>
									   </select>
               						</td>
              					</tr>
              					</s:else>
             					<s:if test="peVotePaper.enumConstByFlagCanSuggest.code == 1">
             					<tr> 
                					<td bgcolor="#F9F9F9">请输入您的意见或建议！</td>
              					</tr>
								<tr> 
								  <td bgcolor="#F9F9F9">
								  	<textarea name="suggest" onkeyup="checkLength(this,500)" cols="65" rows="6"></textarea>
								  </td>
								</tr>
								</s:if>
								<s:else>
									<tr> 
									  <td bgcolor="#F9F9F9"></td>
									</tr>
								</s:else>
            				</table>
          				</td>
        			</tr>
        			<tr>
          				<td height="40" align="center" valign="bottom"> 
	          				<s:if test="canVote == 1">
	          				<s:if test="pastDue == 0">
					          	<input name="Submit1" type="button" id="Submit1" value="提交结果" onclick="checkVoteTeacher()">
					          	<input name="Submit2" type="reset" id="Submit2" value="重    填" >
	          				</s:if>
	          				</s:if>
							<s:else> 您已经做过本调查问卷！
							</s:else>
	            				<input type="button" id="close" value="关    闭"  onclick="javascript:window.close();">
          				</td>
					</tr>          
				</table>
			</td>
		</tr>
		</table>
     </form>
  </body>
</html>