<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置密码</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/entity/manager/js/check.js"></script>
<script>
	function doCommit() {
		if(isNull(document.chg_pwd.old_pwd.value))
		{
		}
		else
		{
			alert("请填写原密码!");
			document.chg_pwd.old_pwd.focus();
			return false;
		}
		if(isNull(document.chg_pwd.new_pwd.value))
		{
		}
		else
		{
			alert("请填写新密码!");
			document.chg_pwd.new_pwd.focus();
			return false;
		}
		if(document.chg_pwd.new_pwd.value != document.chg_pwd.cfmnew_pwd.value)
		{
			alert("新密码不一致!");
			document.chg_pwd.cfmnew_pwd.focus();
			return false;
		}if(document.chg_pwd.new_pwd.value.length<6)
		{
		    alert("密码不能低于6位!");
			document.chg_pwd.cfmnew_pwd.focus();
			return false;
		}
			
		document.forms["chg_pwd"].submit();
	}
	
</script>	
</head>
<body leftmargin="0" topmargin="0" class="scllbar">
<s:if test="message!=null && message != ''">
  <script type="text/javascript">
  alert('<s:property value="message"/>');
  </script>
  </s:if>

<br>
  <form name='chg_pwd' action='/entity/information/personalInfo_editPwd.action' method='post' class='nomargin'>
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">修改密码</div id="zlb_title_end"></td>
  </tr>

  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 
        <div class="cntent_k" id="zlb_content_start">
        		
        	
          <table width="90%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td width="100" valign="bottom"><span class="name">原密码：&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
            <td> <input name="oldPwd" id="old_pwd" type="password" class="selfScale"  maxlength="20">            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="100" valign="bottom"><span class="name">新密码：&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
            <td> <input name="pwd" id="new_pwd" type="password" class="selfScale"  maxlength="20">            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td width="100" valign="bottom"><span class="name">确认新密码：</span></td>
            <td> <input name="pwd1" id="cfmnew_pwd" type="password" class="selfScale"  maxlength="20">            </tr>
            <tr valign="bottom" class="postFormBox"> 
               <td align="center" valign="middle" colspan=2><a href="#" title="提交" class="button"><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onClick="doCommit()" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'"><span class="text">提交</span></span></a></td>      </tr>
          </table>
      </div>
   </td>
  </tr>
 
   
</table>
</form>
</body>
</html>