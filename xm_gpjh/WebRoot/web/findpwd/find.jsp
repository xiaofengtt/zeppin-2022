<%@ page language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.MyResultSet"/>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page import="java.util.*,java.text.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
		if(document.findpwd.reg_no.value=="")
		{
			alert("请输入学号！");
			document.findpwd.reg_no.focus();
			return false;
		}
		if(document.findpwd.name.value=="")
		{
			alert("请输入姓名！");
			document.findpwd.name.focus();
			return false;
		}
		if(document.findpwd.mobilephone.value==""&&document.findpwd.phone.value=="")
		{
			alert("手机号码或固定电话至少填写一个！");
			document.findpwd.mobilephone.focus();
			return false;
		}
		if(document.findpwd.enterprise_id.value=="")
		{
			alert("请选择一级企业！");
			document.findpwd.enterprise_id.focus();
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
          <td align="right"><table width="45%" height="23" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td class="newsbtitle1" align="center">重置密码</td>
              </tr>
            </table></td>
        </tr>
         <form name="findpwd" action="/web/findpwd/findexe.jsp" method="post" onsubmit="return check();">
         <tr valign="top"> 
          <td ><table width="45%" height="23" border="1" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="40%"><FONT class="newsp">登录帐号：</FONT></td><td><FONT class="newsp"><input type="text" name="reg_no" value="" /></FONT></td>
              </tr>
              <tr>
                <td width="40%"><FONT class="newsp">姓名：</FONT></td><td><FONT class="newsp"><input type="text" name="name" value="" /></FONT></td>
              </tr>
              <tr>
                <td width="40%"><FONT class="newsp">手机号码：</FONT></td><td><FONT class="newsp"><input type="text" name="mobilephone" value="" /></FONT></td>
              </tr>
              <tr>
                <td width="40%"><FONT class="newsp">固定电话：</FONT></td><td><FONT class="newsp"><input type="text" name="phone" value="" /></FONT></td>
              </tr>
              <tr>
                <td width="40%"><FONT class="newsp">所在一级企业：</FONT></td>
                <td><FONT class="newsp">
                	<select name="enterprise_id">
                		<option value="">请选择...</option>
              <%
              	dbpool db = new dbpool();
              	String sql = "select * from pe_enterprise where fk_parent_id is null order by name";
              	String id = "";
              	String name = "";
              	MyResultSet rs = db.executeQuery(sql);
              	while(rs!=null&&rs.next())
              	{
              %>
              			<option value="<%=rs.getString("id") %>"><%=rs.getString("name") %></option>
              <%
              	}
              	db.close(rs);
               %>
                	</select>
                </FONT></td>
              </tr>
            </table></td>
        </tr>
        <tr valign="top"> 
          <td align="center"><input type="submit" value="提交" name="submit"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="reset" value="清空" name="reset"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="关闭" name="button1" onclick="javascript:window.close()"/></td>
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
