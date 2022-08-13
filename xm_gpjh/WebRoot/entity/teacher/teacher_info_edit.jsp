<%@ page language="java"  pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
response.setHeader("expires", "0");
%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>欢迎光临华南师范大学现代远程教育平台</title>
<link href="./css/css.css" rel="stylesheet" type="text/css">
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript">
	function checkEmail()
	{
		
		var email=document.getElementById('email').value;
		var pattern= /^([a-z.A-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
		if(!pattern.test(email))
		{
			alert("邮箱格式不正确！\n请重新输入");
			document.getElementById('email').focus();
		}
		return true;
	}
	
	function checkWorkplace() {
		if(document.getElementById('workplace').value.length > 25) {
			alert('工作单位字数不能超过25个字符。');
			document.getElementById('workplace').focus();
		}
	}
	
	function checkPhone() {
		var reg = /(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[3]\d{9}$)/;
		if(!reg.test(document.getElementById('phone').value)) {
			alert('固定电话号码不合法。');
			document.getElementById('phone').focus();
		}
	}
	
	function checkMobilePhone()
	{
		
		var myMobile_no=document.getElementById('mobilephone').value;
		var myreg=/^\d{11}$/;
		if(!myreg.test(myMobile_no))
		{
			alert("手机号码不合法！\n请重新输入");
			document.getElementById('mobilephone').focus();
		}
		
		return true;
	}
</script>
<body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">


<div id="main_content">
    <div class="content_title">教师<s:property value="peTeacher.getTrueName()"/>的工作室</div>
    <div class="cntent_k">
   	  <div class="k_cc">
<form id='teacherInfo' action="/entity/workspaceTeacher/teacher_editexe.action" method="post">
<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
                          
                          <tr>
                            <td height="26" align="center" valign="middle" colspan="2">您的个人资料</td>
                          </tr>
                           <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value = "peTeacher.getTrueName()"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value = "peTeacher.getEnumConstByGender().getName()"/></td>
                                </tr>
                                
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name"> 
                                    身&nbsp;&nbsp;份&nbsp;&nbsp;证：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value = "peTeacher.getIdCard()"/></td>
                                </tr>
	                             <tr valign="middle"> 
	                               <td height="30" align="left" class="postFormBox"><div align="left"><span class="name">工作单位：</span></div></td>
	                               <td class="postFormBox" style="padding-left:18px">
	                               	
                               		   <div align="left">
	                               		  <input type="text" name="peTeacher.workplace" value="<s:property value="peTeacher.getWorkplace()"/>" maxlength="30" id="workplace" onbur="checkWorkplace()"/>&nbsp; </div></td>
	                             </tr>
	                             <tr valign="middle"> 
	                             <tr valign="middle"> 
	                               <td height="30" align="left" class="postFormBox"><div align="left"><span class="name">移动电话：</span></div></td>
	                               <td class="postFormBox" style="padding-left:18px">
	                               	
                               		   <div align="left">
	                               		  <input type="text" id="mobilephone" name="peTeacher.mobilephone" value="<s:property value="peTeacher.getMobilephone()"/>" maxlength="11" onblur="checkMobilePhone()" />&nbsp; </div></td>
	                             </tr>
	                             <tr valign="middle"> 
	                               <td height="30" align="left" class="postFormBox"><div align="left"><span class="name">固定电话(办公)：</span></div></td>
	                               <td class="postFormBox" style="padding-left:18px">
	                               	
                               		   <div align="left">
	                               		  <input type="text" name="peTeacher.phoneOffice" value="<s:property value="peTeacher.getPhoneOffice()"/>" maxlength="20" id="phone" onblur="checkPhone()"/>&nbsp; </div></td>
	                             </tr>
	                             <tr valign="middle"> 
	                               <td height="30" align="left" class="postFormBox"><div align="left"><span class="name"> 
	                                 电子邮件：</span></div></td>
	                               <td class="postFormBox" style="padding-left:18px">
	                               	
                               		   <div align="left">
	                               		  <input type="text" name="peTeacher.email" id="email" value="<s:property value="peTeacher.getEmail()"/>" maxlength="50" onblur="checkEmail()"/>&nbsp; </div></td>
	                             </tr>
                                
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">简&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;介：</span></td>
                                  <td class="postFormBox" style="padding-left:18px">
                                  <textarea name=peTeacher.note cols="40" class=selfScale><s:property value = "peTeacher.getNote()"/></textarea></td>
                                </tr>
						    <tr>
                            <td  height="10"> </td>
                          </tr>
                           <tr>
                            <td height="50" align="center" colspan="2">
                              <input type="submit" value="提交"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="button" value="返回" onClick="javascript:window.history.back()"/></td>
						  </tr>
						    <tr>
                            <td  height="10"> </td>
                          </tr>
        </table>

</form>
	  </div>
    </div>
</div>
<div class="clear"></div>
</body>
</html>
