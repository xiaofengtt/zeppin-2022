<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>生殖健康咨询师网络培训课堂</title>
<link href="/entity/bzz-students/css1.css" rel="stylesheet" type="text/css" />
<link href="/entity/manager/statistics/css/admincss.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" media="all" href="/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1"/>	
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
<script language="javascript" src="/js/JSKit.js"></script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {font-size: 12px; color: #0041A1; font-weight: bold; }
.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}
.kctx_zi1 {
	font-size: 12px;
	color: #0041A1;
}
.kctx_zi2 {
	font-size: 12px;
	color: #54575B;
	line-height: 24px;
	padding-top:2px;
}
-->
</style>
<script language="javascript" >
	var jk=new JSKit("/js/");
	jk.Import("Region");
	jk.Run(function(){
		var region=new Region();
		/*三级联动*/
		region.create({
			"name":"region3",
			"level":"3",
			"provinceObj":document.getElementById("sltProvince3"),
			"cityObj":document.getElementById("sltCity3"),
			"countryCityObj":document.getElementById("sltCountryCity3"),
			"defaultValue":"<s:property value="peTrainee.peAreaByFkProvince.name"/>|<s:property value="peTrainee.peAreaByFkCity.name"/>|<s:property value="peTrainee.peAreaByFkPrefecture.name"/>"
		});
		region.load("region3");
	});
	
	function show(objID) {
		var tempObj;
		if(document.getElementById(objID)) {
			tempObj = document.getElementById(objID);
		}
		tempObj.style.display = "block";
	}
	
	function hide(objID) {
		var tempObj;
		if(document.getElementById(objID)) {
			tempObj = document.getElementById(objID);
		}
		tempObj.style.display = "none";
	}
	function checkLength(obj,len){
		if(obj.value.length >(len)){
			alert('您输入的字符数超过了指定长度,请检查重新输入！');
			obj.value = obj.value.substr(0,len);
			return false;
		  }
		}
	
</script>
</head>
<body bgcolor="#E0E0E0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
      	<td>&nbsp;</td>
      </tr>
       <table width="75%" border="0" align="center" cellpadding="0" cellspacing="0" style="border:solid 2px #9FC3FF; padding:5px; margin:15px 0px 15px 0px;">
  <tr>
    <td>
    <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D7D7D7">
    <form action="/entity/basic/messageBoardAction_saveMod.action" method="post">
      <tr bgcolor="#ECECEC">
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1" width="20%"><font color="red" size="3"></font> <strong>标题：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> <input type="text" name="title" value="<s:property value="messageList[0][2]" />" maxlength='25'/></td>
        </tr>
        <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><font color="red" size="3"></font> <strong>提问内容</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> <textarea name="detail" onkeyup="checkLength(this,500)" rows="6" cols="18"><s:property value="messageList[0][3]"/></textarea></td>
        </tr>
        <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><font color="red" size="3"></font> <strong>操作</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp;
        <input type="hidden" name="ids" value="<s:property value="messageList[0][0]"/>"/>&nbsp;
        <input type="submit" value="保存"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="重置"/></td>
        </tr>
      </form>
    </table></td>
  </tr>
</table>
    </table>
</body>
</html>