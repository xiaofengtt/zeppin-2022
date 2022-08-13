<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
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
		if(document.pass_chg.password_new.value.length < 6)
		{
			result += '新密码长度不能少于6位!\n';
			flag = false;
		}
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

<body>
<div id="main_content">
	<div class="content_title">您当前的位置是：学生<s:property value="peStudent.getTrueName()"/>的工作室 &gt; 个人信息 &gt; <span class="table_bg1">修改密码</span></div>
    <div class="student_cntent_k">
    	<div class="k_cc">
    <form action="/entity/workspaceStudent/student_passwordexe.action" name="pass_chg" onsubmit="return check();">
		<table border="0" cellpadding="0" cellspacing="0" align="center"  width="50%">
		  
		  <tr>
		    <td colspan="2" class="table_bg1">修改密码 </td>
		    </tr>
		  <tr>
		    <td>旧密码：</td>
		    <td><input type="password" value="" name="password_old" /></td>
		  </tr>
		  <tr >
		    <td class="table_bg1">新密码：</td>
		    <td class="table_bg1"><input type="password" value="" name="password_new" /></td>
		  </tr>
		  <tr >
		    <td>确认新密码：</td>
		    <td><input type="password" value="" name="password_recheck" /></td>
		  </tr>
		  <tr class="table_bg1">
		    <td colspan="2"><input type="submit" value="提交" name="submit" />
		    </td>
		   </tr>
		</table>
	</form>
    </div>
  </div>
</div>
</body>
</html>
