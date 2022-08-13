<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <style type="text/css">
<!--
.STYLE1 {
	color: #FF0000;
	font-weight: bold;
	font-family: "新宋体";
}
-->
</style>
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
	  <script type="text/javascript">
		function chk(){
			var applyno=document.getElementById('applyno');
			var unit=document.getElementById('peunit');
			var subject=document.getElementById('expsubject');
			var province = document.getElementById('expprovince');
			
			if(applyno.value==''){
				alert('培训项目为必填项，请确认选择！');
				applyno.focus();
				return false;
			}
			if(unit.value !='' && province.value !=''){
				alert('选择错误，请您重新选择！ ');
				unit.focus();
				return false;
			}
			document.getElementById('searchVote').submit();
		}
		// 创建XMLHttpRequest对象并返回
		function getXmlHttpRequest() {
			var xhr;
			if(window.XMLHttpRequest) {
				xhr = new XMLHttpRequest();
			}else {
				xhr = new ActiveXObject("Microsoft.XMLHTTP");
			}
			return xhr;
		}
		/**
		*@description 在追加option之前清除select标签中的option
		*@param 需要处理的select标签id号
		*/
		function clearOptions(selElementId) {
			var options = document.getElementById(selElementId).options;
			var length = options.length;
			for(var i=1;i<length;i++) {
				options.remove(1);
			}
		}
		/**
		*@description 处理数据，并创建option加到select列表中
		*@param selID 需要处理的select标签id号
		*@param jsonData 目标数据，需要符合一定规则
		*/
		function operateSel(selID,jsonData){
			var sel = document.getElementById(selID);
			clearOptions(selID);
			var option;
			if(jsonData.length==0) {
				//option = document.createElement("<option>没有符合条件的数据</option>");
				option = new Option('没有符合条件的数据',null);
				sel.add(option);
				return;
			}
			var jsonArr = jsonData.split(";");
			var json;
			for(var index in jsonArr) {
				json = jsonArr[index].split(",");
				//option = document.createElement("<option value='"+json[0]+"'>"+json[1]+"</option>");
				option = new Option(json[1],json[0]);
				sel.add(option);
			}
		}
		// 初始化单位下拉列表
		function initUnit(proApplyNo) {
			clearOptions('expsubject');
			var xhr = getXmlHttpRequest();
			xhr.open("get","/entity/information/peVotePaper_initUnit.action?proApplyNo="+encodeURI(proApplyNo));
			xhr.onreadystatechange=function () {
				var state = xhr.readyState;
				if(state == 4) {
					operateSel('peunit',xhr.responseText);
				}
			};
			xhr.send(null);
			
			initProvince(proApplyNo);
		}
		// 初始化学科下拉列表
		function initSubject(unitValue,proApplyId) {
			var xhr = getXmlHttpRequest();
			var proApply = document.getElementById(proApplyId);
			//alert(proApply.options[proApply.selectedIndex].value);
			var proApplyValue = proApply.options[proApply.selectedIndex].value;
			xhr.open("get","/entity/information/peVotePaper_initSubject.action?unitValue="+encodeURI(unitValue)+"&proApplyNo="+proApplyValue);
			xhr.onreadystatechange = function() {
				var state = xhr.readyState;
				if(state == 4) {
					operateSel('expsubject',xhr.responseText);
				}
			}
			xhr.send(null);
		}
		
		// 初始化省份下拉列表
		function initProvince(proApplyNo) {
		//clearOptions('expprovince');
			var xhr = getXmlHttpRequest();
			//alert(proApply.options[proApply.selectedIndex].value);
			//var proApplyValue = proApply.options[proApply.selectedIndex].value;
			xhr.open("get","/entity/information/peVotePaper_initProvince.action?proApplyNo="+encodeURI(proApplyNo));
			xhr.onreadystatechange = function() {
				var state = xhr.readyState;
				if(state == 4) {
					operateSel('expprovince',xhr.responseText);
				}
			}
			xhr.send(null);
		}
	</script>
</head>
  
<body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">
<div id="main_content">
    <div class="content_title">查看调查问卷列表</div>
   	<div class="k_cc">
		<form id='searchVote' action="/entity/information/peVotePaper.action" method="get">
		<table width="554" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:50px">
            <tr valign="middle"> 
				<td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name"> 培训项目：*</span></td>
				<td class="postFormBox" style="padding-left:10px">
					<select name="search1_peProApplyno.id" id='applyno' onchange="initUnit(this.value)">
						<option value = "">请选择...</option>
						<s:iterator value="paperType" id="proapply">
							<option value="<s:property value="#proapply[0]"/>"><s:property value="#proapply[1]"/></option>
						</s:iterator>
					</select>
				</td>
             </tr>
            <tr valign="middle"> 
               <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
					<span class="name"> 培训单位：&nbsp;&nbsp;</span>
               </td>
               <td class="postFormBox" style="padding-left:10px">
               	<select name="peUnit" id='peunit' onchange="initSubject(this.value,'applyno')">
               		<option value = "">请选择...</option>
               	</select>
               </td>
             </tr>
			<tr valign="middle"> 
				<td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
					<span class="name"> 学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;科：&nbsp;&nbsp;</span>
				</td>
				<td class="postFormBox" style="padding-left:10px">
					<select name="peSubject" id='expsubject'>
						<option value = "">请选择...</option>
					</select>
			   </td>
			</tr>
			<tr valign="middle"> 
				<td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px">
					<span class="name"> 省&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份：&nbsp;&nbsp;</span>
				</td>
				<td class="postFormBox" style="padding-left:10px">
					<select name="peProvince" id='expprovince' >
						<option value = "">请选择...</option>
					</select>
					<span class="STYLE1">&nbsp;&nbsp;&nbsp;*仅供中西部项目选择</span>
			   </td>
			</tr>
			<tr>
				<td height="50" align="center" colspan="2">
					<input type="button" value="查&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;看" style="width:100" onClick="chk()"/>
				</td>
			</tr>
        </table>
	</form>
	</div>
</div>
</body>
</html>
