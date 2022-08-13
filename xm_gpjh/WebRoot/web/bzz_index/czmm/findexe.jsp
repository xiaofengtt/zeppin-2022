<%@ page language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.MyResultSet"/>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page import="java.util.*,java.text.*" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String reg_no = request.getParameter("reg_no");
String name = request.getParameter("name");
String mobilephone = request.getParameter("mobilephone");
String phone = request.getParameter("phone");
String enterprise_id = request.getParameter("enterprise_id");
dbpool db = new dbpool();
String sql = "";
String sql1 = "select id from sso_user where login_id='"+reg_no+"' and fk_role_id='0'";
String sql2 = "select id from sso_user where login_id='"+reg_no+"' and fk_role_id not in('0','3')";
if(db.countselect(sql1)>0)
{
	sql = "select id from pe_bzz_student where reg_no='"+reg_no+"' and true_name='"+name+"' and (mobile_phone='"+mobilephone+"' or phone='"+phone+"') and (fk_enterprise_id ='"+enterprise_id+"' or fk_enterprise_id in(select id from pe_enterprise where fk_parent_id='"+enterprise_id+"'))";
}
if(db.countselect(sql2)>0)
{
	sql = "select id from pe_enterprise_manager where login_id='"+reg_no+"' and name='"+name+"' and (mobile_phone='"+mobilephone+"' or phone='"+phone+"') and (fk_enterprise_id ='"+enterprise_id+"' or fk_enterprise_id in(select id from pe_enterprise where fk_parent_id='"+enterprise_id+"'))";
}
if(db.countselect(sql)<1)
{
%>
<script type="text/javascript">
	alert("您输入的信息不存在！");
	window.history.back();
</script>
<%
	return;
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <base href="<%=basePath%>">
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
<script language="javascript">
	function check()
	{
		if(document.findpwd.password.value=="")
		{
			alert("请输入新密码！");
			document.findpwd.password.focus();
			return false;
		}
		if(document.findpwd.confirmpassword.value=="")
		{
			alert("请再次输入新密码！");
			document.findpwd.confirmpassword.focus();
			return false;
		}
		if(document.findpwd.password.value!=document.findpwd.confirmpassword.value)
		{
			alert("两次输入密码不一致！");
			document.findpwd.password.focus();
			return false;
		}
	}
</script>
<style>
.font_czmm{
font-size:12px;
color:#0A0A64;
padding:0px 0px 0px 10px;
line-height:30px;
}
input{
border:solid 1px #1E61C9;
width:150px;
height:20px;
margin:0px 0px 0px 10px;
}
select{
border:solid 1px #1E61C9;
width:180px;
height:20px;
margin:0px 0px 0px 10px;
font-size:12px;
color:#000000;
}
</style>
</head>

<body>
<table width="993" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td height="153"><img src="/web/bzz_index/czmm/images/czmm_02.jpg" width="993" height="153"></td>
  </tr>
  <tr>
    <td align="center" style=" background-image:url(/web/bzz_index/czmm/images/czmm_05.jpg); background-position:bottom; background-repeat:repeat-x; padding:30px 0px 30px 0px;"><table width="367" height="150" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="40" align="center"><img src="/web/bzz_index/czmm/images/czmm_08.jpg" width="60" height="18"></td>
      </tr>
       <form name="findpwd" action="/web/bzz_index/czmm/setexe.jsp" method="post" onsubmit="return check();">
         <input type="hidden" name="reg_no" value="<%=reg_no %>" />
         <input type="hidden" name="name" value="<%=name %>" />
         <input type="hidden" name="mobilephone" value="<%=mobilephone %>" />
         <input type="hidden" name="phone" value="<%=phone %>" />
         <input type="hidden" name="enterprise_id" value="<%=enterprise_id %>" />
      <tr>
        <td><table width="100%" border="3" cellpadding="0" cellspacing="1" bordercolor="#CCE2FF" bgcolor="#FFFFFF">
          <tr>
            <td width="32%" bgcolor="#A1CBFF" class="font_czmm">新密码：</td>
            <td width="68%"><input type="text" name="password" value=""></td>
          </tr>
          <tr>
            <td bgcolor="#A1CBFF" class="font_czmm">再次输入新密码：</td>
            <td><input type="text" name="confirmpassword"></td>
          </tr>
        </table></td>
      </tr></form>
      <tr>
        <td height="50" align="center"><a href="javascript:window.document.findpwd.submit();"><img src="/web/bzz_index/czmm/images/czmm_12.jpg" width="71" height="23" border="0"></a><a href="javascript:window.document.findpwd.reset();"><img src="/web/bzz_index/czmm/images/czmm_14.jpg" width="71" height="23" hspace="10" border="0"></a><a href="javascript:window.close();"><img src="/web/bzz_index/czmm/images/czmm_16.jpg" width="71" height="23" border="0"></a></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="54" align="center" valign="middle" background="web/bzz_index/images/bottom_08.jpg"><span class="down">版权所有：生殖健康咨询师培训网 京ICP备05046845号
      <BR>
      投诉：010-62786820 
      传真：010-62789770 技术客服热线电话： 
010-58731118转254</span></td>
  </tr>
</table>


</body>
</html>

