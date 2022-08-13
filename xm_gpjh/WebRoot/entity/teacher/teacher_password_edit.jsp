<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>欢迎光临华南师范大学现代远程教育平台</title></title></title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function checkForm(form){
	if(form.password_old.value=="" || form.password_old.value==null){
		form.password_old.focus();
		alert("请填入原密码！");
		return false;
	}
	if(form.password_new.value=="" || form.password_new.value==null){
		form.password_new.focus();
		alert("新密码不能为空！");
		return false;
	}
	if(form.password_new.value!=form.reNewPassword.value){
		form.reNewPassword.focus();
		alert("新密码两次输入不一致！");
		return false;
	}
	return true;
}
</script>
</head>
<body>
<div id="main_content">
    <div class="content_title">教师<s:property value="peTeacher.getTrueName()"/>的工作室</div>
    <div class="cntent_k">
   	  <div class="k_cc">
              <form name="teacher_password_editForm" action="/entity/workspaceTeacher/teacher_passwordexe.action" method="post" onSubmit="return checkForm(this);">
                      <table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
                          
                            <td height="26" align="center" valign="middle" class="14title2">修改密码 
                            </td>
                          </tr>
                          <tr>
                            <td height="8"> </td>
                          </tr>
						  <tr>
						  <td ><table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr valign="middle"> 
                                  <td width="30%" height="30" align="left" class="postFormBox"><span class="name">旧密码：</span></td>
                                  <td class="postFormBox" style="padding-left:10px"><input class=selfScale type="password" name="password_old" /></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td height="30" align="left" class="postFormBox"><span class="name">新密码：</span></td>
                                  <td class="postFormBox" style="padding-left:10px"><input type="password" class=selfScale name="password_new" /></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td height="30" align="left" class="postFormBox"><span class="name">确认新密码：</span></td>
                                  <td class="postFormBox" style="padding-left:10px"><input type="password"  class=selfScale name="reNewPassword" /></td>
                                </tr>
                                
                              </table></td>
						  </tr>
						  <tr>
                            <td height="8"> </td>
                          </tr>
                          <tr>
                            <td height="53" align="center" >
                                <input type="submit" name="submit" value="提交"/>&nbsp;
             					<input type="button" name="goback" value="返回" onClick="javascript:history.back()" />
                            </td>
                          </tr>
                        </table>
              </form> 
			    </div>
    </div>
</div>
<div class="clear"></div>         
</body>
</html>
