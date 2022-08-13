<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>国培计划项目管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link type="text/css" href="/entity/bzz-students/css/box.css" rel="stylesheet" />
<link type="text/css" href="/entity/bzz-students/css/css2.css" rel="stylesheet" />
</head>

<link href="/entity/bzz-students/css1.css" rel="stylesheet" type="text/css" />
<link href="/entity/manager/statistics/css/admincss.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="/entity/bzz-students/js/script.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1"/>	
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
<script language="javascript" src="/js/JSKit.js"></script>
<link href="/entity/bzz-students/css.css" rel="stylesheet" type="text/css" />
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
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
		var regPassword = /^[A-Za-z0-9!@#$%^&*()]{4,16}$/;
		if(!regPassword.test(document.pass_chg.password_new.value)) {
		alert("请输入4-16位字符！");
		return false;
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
</head><body style="margin:0px " id="study"><br/><br/>
<div align="center" style="text-align: center;">
<span style="color:red;padding-bottom:20px;text-align:center;width:100%;font-size:16px;">温馨提示：首次登录时请先修改您的初始密码</span>
</div> 
<div class="studyright" style="width:100%; margin-top:80px;text-align:center" align="center">
  
    <form method="post" action="/entity/basic/userInfoEditAction_savePw.action" name="pass_chg" onsubmit="return check();">
		<table border="0" cellpadding="0" cellspacing="0" align="center"  width="30%" >
		  
		  <tr style="height:40px;">
		    <td class="table_bg1" style="padding-left:20px;">旧密码：</td>
		    <td class="table_bg1">
		    	<input type="password" value="" name="password_old" />
		    	<input type="hidden" name="ssoid" value="<s:property value="peBzzStudent.ssoUser.id" />" />
		    </td>
		  </tr>
		  <tr style="height:40px;">
		    <td class="table_bg1" style="padding-left:20px;">新密码：</td>
		    <td class="table_bg1"><input type="password" value="" name="password_new" /></td>
		  </tr>
		  <tr style="height:40px;">
		    <td class="table_bg1" style="padding-left:20px;">确认新密码：</td>
		    <td class="table_bg1"><input type="password" value="" name="password_recheck" /></td>
		  </tr>
		  <tr class="table_bg1" style="height:40px;">
		    <td colspan="2" align="center"><input type="submit" value="提交" name="submit" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    		    </td>
		   </tr>
		</table>
	</form>
</div>
</body>
<s:if test="msg!=null">
<script>
alert('<s:property value="msg"/>');
  <s:if test="succ">
		if(top.frames.length==0){
			top.location.href="/entity/manager/index.jsp";
		}
  </s:if>
</script>
</s:if>
</html>