<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>教师培训项目管理平台--登陆</title>
<link href="/tmplogin/cs.css" rel="stylesheet" type="text/css" />
<script language="javascript">
function formtest(){
	if(document.loginform.loginId.value==''){
		alert("请输入用户名！");
		document.loginform.loginId.focus();
		return false;
	}
	if(document.loginform.passwd.value==''){
		alert("请输入密码！");
		document.loginform.passwd.focus();
		return false;
	}
	if(document.loginform.authCode.value==''){
		alert("请输入验证码！");
		document.loginform.authCode.focus();
		return false;
	}
	return true;
}
</script>
</head>
<body>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
   <td height="176" colspan="2"></td>
    <td width="743" rowspan="2" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td></td>
      </tr>
      <tr>
        <td height="365" valign="top"><table width="617" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="56" height="90">&nbsp;</td>
            <td width="561" valign="middle"></td>
          </tr>
          <tr>
            <td height="299" colspan="2" valign="top" background="/tmplogin/images/bg.jpg"><form id="loginform" name="loginform" method="post" action="/sso/login_login.action" onsubmit="return formtest();">
              <table width="265" border="0" cellspacing="0" cellpadding="0" style="margin-left:70px; margin-top:50px;">
                <tr>
                  <td width="63" height="40" align="center">用 户 名：</td>
                  <td width="202" align="right"><input type="text" name="loginId" style=" border:solid 1px #ccc; width:180px; height:20px;" /></td>
                </tr>
                <tr>
                  <td height="40" align="center">密　　码：</td>
                  <td align="right"><input type="password" name="passwd" style=" border:solid 1px #ccc; width:180px; height:20px;" /></td>
                </tr>
                <tr>
                  <td height="40" align="center">验 证 码：</td>
                  <td align="right"><input type="text" name="authCode" maxlength="4" style=" border:solid 1px #ccc; width:120px; height:20px;" /><img src="/sso/authimg"  border="0" align="absmiddle"/></td>
                </tr>
                <tr>
                  <td height="40" colspan="2" align="right"><input type="image" name="imageField" src="/tmplogin/images/login.jpg" /></td>
                  </tr>
              </table>
                          </form>
              </td>
          </tr>
        </table></td>
      </tr>
      
    </table></td>
    <td colspan="2"></td>
  </tr>
  <tr>
    
  </tr>
  <tr>
  </tr>
  <tr>
    <td height="45" colspan="3">&nbsp;</td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#E9B4D0">
  <tr>
    <td height="50" style="font-size:16px;color:#333;text-align:center;">系统开放时间：“中小学教师培训项目管理系统”的开放时间为工作日8:00—18:00，其他时间和周末及国家法定节假日不开放。</td>
  </tr>
</table>
</body>
</html>
