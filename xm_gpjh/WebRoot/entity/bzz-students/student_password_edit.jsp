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
<title>生殖健康咨询师培训网</title>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
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
-->
</style>
</head>
<script>
	function check()
	{
		var result ='';
		var flag = true;
		if(document.pass_chg.password_old.value == '')
		{
			result +='请输入旧密码!\n';
			flag = false;
		}
		
		if(document.pass_chg.password_new.value=="")
		{
			result +='新密码不能为空!\n';
			flag = false;
		}
		if(document.pass_chg.password_new.value.length < 4)
		{
			result += '新密码长度不能少于4位!\n';
			flag = false;
		}
		/*
		var regPassword = /^[A-Za-z0-9_]{4,16}$/;
		if(!regPassword.test(document.pass_chg.password_new.value)) {
			result += '新密码输入非法字符！\n';
			flag = false;
		}
		*/
		if(document.pass_chg.password_recheck.value=="")
		{
			result += '确认密码不能为空\n';
			flag = false;
		}
		if(document.pass_chg.password_recheck.value!=document.pass_chg.password_new.value)
		{
			result += '密码与确认密码不符!\n';
			flag = false;
		}
		if(!flag){
			result += '请确认后重新添加!';
			alert(result);
			document.pass_chg.password_recheck.value="";
			document.pass_chg.password_new.value="";
			return false;
		}
		return true;
	}
</script>
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
    <td width="237" valign="top"><IFRAME NAME="leftA" width=237 height=500 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/left.jsp" scrolling=no allowTransparency="true"></IFRAME></td>
   <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17" align="left" class="twocentop"><div id="main_content">
	<div class="content_title"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" />您当前的位置是：学生<font color="red"><s:property value="peBzzStudent.getTrueName()"/></font>的工作室 &gt; 个人信息 &gt; 修改密码</div>
</div>


</td>
      </tr>
      <tr>
          <td><br><img name="two2_r5_c5" src="/entity/bzz-students/images/two/two2_r5_c5.jpg" width="750" height="72" border="0" id="two2_r5_c5" alt="" /> </td>
      </tr>
     <tr>
     	<td>   <div class="student_cntent_k">
    	<div class="k_cc">
    <form method="post" action="/entity/workspaceStudent/bzzstudent_passwordexe.action" name="pass_chg" onsubmit="return check();">
		<table border="0" cellpadding="0" cellspacing="0" align="center"  width="50%">
		  
		  <tr>
		    <td colspan="2" class="table_bg1">修改密码 </td>
		    </tr>
		  <tr>
		    <td>旧密码：</td>
		    <td>
		    	<input type="password" value="" name="password_old" />
		    	<input type="hidden" name="ssoid" value="<s:property value="peBzzStudent.ssoUser.id" />" />
		    </td>
		  </tr>
		  <tr>
		    <td class="table_bg1">新密码：</td>
		    <td class="table_bg1"><input type="password" value="" name="password_new" /></td>
		  </tr>
		  <tr >
		    <td>确认新密码：</td>
		    <td><input type="password" value="" name="password_recheck" /></td>
		  </tr>
		  <tr class="table_bg1">
		    <td colspan="2"><input type="submit" value="提交" name="submit" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    <input type="button" value="返回" onclick="javascript:window.location='<%=basePath%>/entity/workspaceStudent/bzzstudent_StudentInfo.action'"/>
		    </td>
		   </tr>
		</table>
	</form>
    </div>
  </div></td>
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
</html>