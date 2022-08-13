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

<?xml version="1.0" encoding="gb2312"?><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>生殖健康咨询师培训网</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<link href="/web/bzz_index/css.css" rel="stylesheet" type="text/css" />
<link href="/web/css/css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {
	color: #FF9000;
	font-weight: bold;
}
.link a{
color:#3399cc;}
-->
</style>
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
</head>

<body bgcolor="#E0E0E0">
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td><table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr> 
          <td><img name="index_r1_c1" src="/web/bzz_index/images/index/index_r1_c1.jpg" width="1002" height="154" border="0" id="index_r1_c1" alt="" /></td>
        </tr>
        <tr> 
          <td height="7">&nbsp;<br><br></td>
        </tr>
         <tr valign="top"> 
          <td align="right"><table width="40%" height="23" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td class="newsbtitle1" align="center">输入新密码</td>
              </tr>
            </table></td>
        </tr>
         <form name="findpwd" action="/web/findpwd/setexe.jsp" method="post" onsubmit="return check();">
         <input type="hidden" name="reg_no" value="<%=reg_no %>" />
         <input type="hidden" name="name" value="<%=name %>" />
         <input type="hidden" name="mobilephone" value="<%=mobilephone %>" />
         <input type="hidden" name="phone" value="<%=phone %>" />
         <input type="hidden" name="enterprise_id" value="<%=enterprise_id %>" />
         <tr valign="top"> 
          <td ><table width="40%" height="23" border="1" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="40%"><FONT class="newsp">新密码：</FONT></td><td><FONT class="newsp"><input type="password" name="password" value="" /></FONT></td>
              </tr>
              <tr>
                <td width="40%"><FONT class="newsp">再次输入新密码：</FONT></td><td><FONT class="newsp"><input type="password" name="confirmpassword" value="" /></FONT></td>
              </tr>
            </table></td>
        </tr>
        <tr valign="top"> 
          <td align="center"><input type="submit" value="提交" name="submit"/></td>
        </tr>
        </form>
      </table>
      <table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr> 
          <td height="54" align="center" background="/web/bzz_index/images/index/index_r23_c2.jpg" class="down">版权所有：生殖健康咨询师培训网 
            京ICP备05046845号<br />
            投诉：010-62786820 传真：010-62789770 技术客服热线电话： 010-58731118转254</td>
        </tr>
      </table>
</td>
  </tr>
</table>
</body>
</html>
