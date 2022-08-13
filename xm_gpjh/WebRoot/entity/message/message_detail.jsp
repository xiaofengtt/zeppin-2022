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
	
</script>
<script language="javascript" >

function   Del(Word)   { 
a   =   Word.indexOf( " < "); 
b   =   Word.indexOf( "> "); 
len   =   Word.length; 
c   =   Word.substring(0,   a); 
if(b   ==   -1) 
b   =   a; 
d   =   Word.substring((b   +   1),   len); 
Word   =   c   +   d; 
tagCheck   =   Word.indexOf( " < "); 
if(tagCheck   !=   -1) 
Word   =   Del(Word); 
return   Word; 
} 
function   Check()   { 
ToCheck   =   document.form.text.value; 
Checked   =   Del(ToCheck); 
document.form.text.value   =   Checked; 
return   true; 
} 
</script>

</head>
<body bgcolor="#E0E0E0">
<div style="overflow-y:scroll;border:1px solid;height:300px;margin-right:15px;word-break:break-all;padding-right:0px;">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
      	<td>&nbsp;</td>
      </tr>
       <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" style="border:solid 2px #9FC3FF; padding:5px; margin:5px 0px 5px 20px;">
  <tr>
    <td>
    <form action="/entity/workspaceStudent/bzzstudent_modifyInfo.action" method="post" enctype="multipart/form-data" name="infoform" onsubmit="return checknull();">
    <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D7D7D7">
      <tr bgcolor="#ECECEC">
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1" width="20%"><font color="red" size="3"></font> <strong>标题：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:property value="messageList[0][2]"/></td>
        </tr>
        <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><font color="red" size="3"></font> <strong>提问者</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"  > &nbsp; <s:property value="messageList[0][1]"/></td>
        </tr>
        <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><font color="red" size="3"></font> <strong>提问内容</strong></td>
       <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:property value="detail_q" escape="false"/></td>
        </tr>
        <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><font color="red" size="3"></font> <strong>提问时间</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:property value="messageList[0][4]"/></td>
        </tr>
        <s:if test="answered">
        <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><font color="red" size="3"></font> <strong>回复内容</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:property value="detail"/></td>
        </tr>
        <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><font color="red" size="3"></font> <strong>回复者</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:property value="messageList[0][5]"/></td>
        </tr>
        <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><font color="red" size="3"></font> <strong>回复时间</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:property value="messageList[0][6]"/></td>
        </tr>
        </s:if>
        <s:if test="CanEdit">
        <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><font color="red" size="3"></font> <strong>操作</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
        	<a href="/entity/basic/messageBoardAction_modifyQues.action?ids=<s:property value="messageList[0][0]"/>">修改</a>
        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="if(confirm('确定要删除吗？')){window.location.href='/entity/basic/messageBoardAction_delQues.action?ids=<s:property value="messageList[0][0]"/>';}">删除</a></td>
        </tr>
        </s:if>
        <s:if test="ansCanEdit">
        <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><font color="red" size="3"></font> <strong>操作</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2">
        	<a href="/entity/basic/messageBoardAction_modifyAns.action?ids=<s:property value="messageList[0][0]"/>">修改</a>
        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="if(confirm('确定要删除吗？')){window.location.href='/entity/basic/messageBoardAction_delAns.action?ids=<s:property value="messageList[0][0]"/>';}">删除</a></td>
        </tr>
        </s:if>
        <tr>
        <td align="center" colspan="2" height="30px;" bgcolor="#E9F4FF" class="kctx_zi1"><font color="red" size="3"></font><div onclick="window.close();" style="cursor:hand;"> <strong>关闭</strong></div></td>
        </tr>
    </table></td>
  </tr>
</table>
    </table>
    </form>
</div>
</body>
</html>