<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>查看我的结业申请</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
<!--
function del(){
	var s=document.getElementsByName('applyId');
	var str="";
	for(var i=0;i<s.length;i++){
		if(s[i].checked){
			str=str+s[i].value+",";
		}
	}
	if(str.length<1){
		alert('请先选择要删除的申请');
		return false;
	}
	window.location.href="/entity/study/peEndCourseApplyAction_delApply.action?ids="+str;
}
//-->
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {
	color: #FF0000;
	font-weight: bold;
}
.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}

a:link { text-decoration: none;color: #000000}
a:active { text-decoration:blink}
a:hover { text-decoration:underline;color: red} 
a:visited { text-decoration: none;color: #000000}

-->
</style></head>

<body bgcolor="#E0E0E0">
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <IFRAME NAME="top" width=100% height=200 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/top.jsp" scrolling=no allowTransparency="true"></IFRAME>
  <tr>
    <td height="13"></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="237" valign="top"><IFRAME NAME="leftA" width=237 height=520 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/left.jsp" scrolling=no allowTransparency="true"></IFRAME></td>
   <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17" align="left" class="twocentop"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" /> 当前位置：培训级别申请</td>
      </tr>
      <tr>
          <td><br><!-- <img name="two2_r5_c5" src="images/two/two2_r5_c5.jpg" width="750" height="72" border="0" id="two2_r5_c5" alt="" /> --></td>
      </tr>
       <tr>
            <td align="left"><img src="/entity/bzz-students/images/two/vote.jpg" width="124" height="25" onclick="window.open('/entity/study/peEndCourseApplyAction_addApply.action','','left=500,top=200,resizable=yes,scrollbars=no,height=200,width=400,location=no')"/></td>
          </tr>
      <tr align="center">
        <td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg">
        <table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr>
            <td width="5%" align="center"><a onclick="del();return false;" style="color:red">删除</a></td>
            <td width="15%" align="center">申请时间</td>
            <td width="15%" align="center">申请状态</td>
            <td width="15%" align="center">申请备注</td>
            <td width="15%" align="center">申请理由</td>
            <td width="20%" align="center">审核日期</td>
            <td width="15%" align="center">审核备注</td>
          </tr>
        </table>
        </td>
      </tr>
      <tr valign="top" align="center">
        <td style="padding-left:10px;padding-right:10px;padding-top:10px;"><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop" style="word-break:break-all;">
        <s:iterator id="apply" value="applyList" status="s">
          <tr style="color:<s:if test="#apply.enumConstByFlagApplyStatus.code==1">green</s:if><s:else>red</s:else>">
          	<td width="2%" align="center"><input type=checkbox name='applyId' value="<s:property value="#apply.id"/>" />&nbsp;</td>
            <td width="18%" align="center"><s:date name="#apply.applyDate" format="yyyy-MM-dd" />&nbsp;</td>
            <td width="15%" align="center"><s:property value="#apply.enumConstByFlagApplyStatus.name"/>&nbsp;</td>
            <td width="15%" align="center"><s:property value="#apply.applyNote"/>&nbsp;</td>
            <td width="15%" align="center"><s:property value="#apply.applyInfo"/>&nbsp;</td>
            <td width="20%" align="center"><s:date name="#apply.checkDate" format="yyyy-MM-dd" />&nbsp;</td>
            <td width="15%" align="center"><s:property value="#apply.checkNote"/>&nbsp;</td>
          </tr>
          <tr>
            <td colspan="7"><img src="/entity/bzz-students/images/two/7.jpg" width="752" height="4" /></td>
          </tr>
         </s:iterator> 
          </table></td>
      </tr>
      <tr>
       <td width="13">&nbsp;</td>
      </tr>
    </table></td>
    <td width="13">&nbsp;</td>
  </tr>
</table>
<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no allowTransparency="true" align=center></IFRAME>
</td>
</tr>
</table>
</body>
<s:if test="msg!=null">
<script type="text/javascript">
<!--
	alert('<s:property value="msg"/>');
//-->
</script>
</s:if>
</html>