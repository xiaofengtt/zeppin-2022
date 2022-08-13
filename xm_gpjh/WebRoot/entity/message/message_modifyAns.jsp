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
<script language="javascript">
function check(){
    var detail=document.getElementById('Detail');
    if(detail.value.length>500)
         {
	         alert('您输入的字符数超过了指定长度，请检查重新输入！');
		     detail.focus();
		     return false;
	
	     }
	document.getElementById('quesform').submit();
}
</script>
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
</head>
<body bgcolor="#E0E0E0">
<div style="overflow-y:scroll;border:1px solid;height:300px;margin-right:15px;word-break:break-all;padding-right:0px;">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
      	<td>&nbsp;</td>
      </tr>
       <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0" style="border:solid 2px #9FC3FF; padding:5px; margin:5px 0px 5px 0px;">
  <tr>
    <td>
    <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D7D7D7">
    <form action="/entity/basic/messageBoardAction_saveAns.action" method="post" id='quesform' >
      <tr bgcolor="#ECECEC">
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1" width="20%"><font color="red" size="3"></font> <strong>标题：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> <s:property value="peAnswers.peQuestions.title"/></td>
        </tr>
        <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><font color="red" size="3"></font> <strong>提问内容</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"><s:property value="peAnswers.peQuestions.detail" escape="false"/></td>
        </tr>
        <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><font color="red" size="3"></font> <strong>回复内容</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> <textarea name="detail" id="Detail" rows="10"  cols="46"><s:property value="peAnswers.detail"/></textarea></td>
        </tr>
        <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"><font color="red" size="3"></font> <strong>操作</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp;
        <input type="hidden" name="ansID" value="<s:property value="peAnswers.id"/>"/>&nbsp;
        <input type="button" onclick='check()' value="保存"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="重置"/></td>
        </tr>
      </form>
    </table></td>
  </tr>
</table>
    </table>
 </div>
</body>
</html>