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
<link href="/web/bzz_index/style/msfc.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/web/bzz_index/js/wsMenu.js"></script>
<script language="javascript">
function resize()
{
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
}
</script>
<style type="text/css">
#contact{
background:url(/web/bzz_index/lxwm_new/images/contact_12.jpg) no-repeat;
width:647px;
height:249px;
}
#contact div{
float:left;
width:200px;
color:#256485;
font-size:12px;
margin-left:90px;
}
#contact div p{
margin:0;
padding:0;
line-height:24px;}
</style>
</head>

<body>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td height="209"><iframe id="top" name="top" frameborder="0" width="1002" height="209" scrolling="no" src="/web/bzz_index/top.jsp"></iframe></td>
  </tr>
  <tr>
    <td align="center"><table width="932" align="center" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="199" valign="top" >
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="/web/bzz_index/lxwm_new/images/contact_03.jpg" width="199" height="5"></td>
  </tr>
  <tr>
    <td align="center" style="padding:15px 0; background:#F2F2F2"><img src="/web/bzz_index/lxwm_new/images/contact_06_1.jpg"></td>
  </tr>
  <tr>
    <td><img src="/web/bzz_index/lxwm_new/images/contact_15.jpg"></td>
  </tr>
</table> </td>
                <td align="center" valign="top" style="padding-left:30px;" >
                <table width="91%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="667"><img src="/web/bzz_index/lxwm_new/images/contact_003.jpg" width="667" height="26"></td>
  </tr>
  <tr>
    <td height="267" id="contact" >
    <div style="width:200px; text-align:left; margin-left:20px;"><p>单 位：生殖健康咨询师培训网</p>
<p>地 址：清华大学建筑馆北楼</p>
<p>邮 编：100084</p>
<p>传 真：010-62789770</p>
<p>E-mail：bzzbm@mail.itsinghua.com</p></div>
    <div  style="width:250px;"><p>服务咨询热线：（暂空）</p>
<p>报名咨询：010-62797946，010-62788908转897</p>
<p>教学教务：010-62797946，010-62788908转897</p>
<p>技术支持：010-62796475</p></div>
    </td>
  </tr>
</table>

                
                </td>
              </tr>
            </table>			  </td>
		  </tr>
		</table>
	</td>
  </tr>
  <tr>
    <td><iframe id="foot" name="foot" frameborder="0" width="1002" height="147" scrolling="no" src="/web/bzz_index/bottom1.jsp"></iframe></td>
  </tr>
</table>


</body>
</html>
