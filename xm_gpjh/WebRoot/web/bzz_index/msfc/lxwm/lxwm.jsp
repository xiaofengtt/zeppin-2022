<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.MyResultSet"/>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>
<%
String path1 = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>生殖健康咨询师培训网</title>
<link href="/web/bzz_index/style/index.css" rel="stylesheet" type="text/css">
<link href="/web/bzz_index/style/xytd.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/web/bzz_index/js/wsMenu.js"></script>
<script language="javascript">
function resize()
{
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
}
</script>
</head>

<body>
<table width="993" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td height="153"><img src="/web/bzz_index/lxwm/images/index_02.jpg" width="993" height="153"></td>
  </tr>
  <tr>
    <td align="center" style="background-image:url(/web/bzz_index/lxwm/images/index_03.jpg); background-position:top; background-repeat:no-repeat;"><div style="background-image:url(/web/bzz_index/lxwm/images/bottom_05.gif); background-position:bottom right; background-repeat:no-repeat; font-size:12px; color:#000000; text-align:left; padding:65px 0px 50px 100px; line-height:30px;">
    	地址：清华大学建筑馆北楼312<br> 
    	邮编：100084<br>
      	传真：010-62789770<br>
      	E-mail:bzzbm@mail.itsinghua.com<br>
      	报名咨询：010-62797946，010-62788908转897<br>
		教学教务：010-62797946，010-62788908转897<br>
		考试认证：010-62797946，010-62788908转897<br>
		技术支持：010-58731118转254
</div></td>
  </tr>
  <tr>
    <td height="54" align="center" valign="middle" background="/web/bzz_index/images/bottom_08.jpg"><span class="down">版权所有：生殖健康咨询师培训网 京ICP备05046845号
      <BR>
      投诉：010-62786820 
      传真：010-62789770 技术客服热线电话： 
010-58731118转254</span></td>
  </tr>
</table>


</body>
</html>
