<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>个人信息修改</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="./css/css.css" rel="stylesheet" type="text/css">
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">


  </head>
  <script type="text/javascript">
  function checkall(){
  	if(document.getElementById('email').value==''){
  			window.alert("邮箱不能为空！");
  			document.getElementById('email').focus();
			return false;
	}else{
		var email=document.getElementById('email').value;
		var pattern= /^([a-z.A-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
		if(!pattern.test(email))
		{
			alert("邮箱格式不正确！\n请重新输入");
			document.getElementById('email').focus();
			return false;
		}
		if(document.getElementById('email').value.length>40){
  			window.alert("您输入的邮箱地址长度太长请换用其他邮箱！");
  			document.getElementById('email').focus();
			return false;
	}
	}
	
	if(document.getElementById('phone').value==''){
			window.alert("固定电话号码不能为空！");
  			document.getElementById('phone').focus();
			return false;
	}else{
	var reg = /(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[3]\d{9}$)/;
		if(!reg.test(document.getElementById('phone').value)) {
			alert('固定电话号码不合法。');
			document.getElementById('phone').focus();
			return false;
		}
	}
	
	if(document.getElementById('mobilephone').value==''){
			window.alert("手机号码不能为空！");
  			document.getElementById('mobilephone').focus();
			return false;
	}else{
		var myMobile_no=document.getElementById('mobilephone').value;
		var myreg=/^\d{11}$/;
		if(!myreg.test(myMobile_no))
		{
			alert("手机号码不合法！\n请重新输入");
			document.getElementById('mobilephone').focus();
			return false;
		}
	}
	
  }
  
</script>
  <body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">


<div id="main_content">
    <div class="content_title"><s:property value="peEnterpriseManager.Name"/> 的工作室</div>
    <div class="cntent_k">
   	  <div class="k_cc">
<form id='teacherInfo' action="/entity/information/personalInfo_editInfo.action" method="post" onsubmit="return checkall();">
<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
                          
                          <tr>
                            <td height="26" align="center" valign="middle" >您的个人资料                            </td>
                          </tr>
                          <tr>
                            <td height="8"> </td>
                          </tr>
						 
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="peEnterpriseManager.name"/>  </td>
                                </tr>
                               <!--  <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="peEnterpriseManager.enumConstByGender.name"/></td>
                                </tr>  -->
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name"> 
                                    电子邮件：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><input class=selfScale name='email' id='email'   value='<s:property value="peEnterpriseManager.email" />'/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">固定电话：</span></td>
                                  <td class="postFormBox" style="padding-left:18px">
                                    <input class=selfScale name='phone' value='<s:property value="peEnterpriseManager.phone"/>' id='phone' maxlength="11"/>                                  </td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">移动电话：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><input  class=selfScale name='mobilePhone' id='mobilephone' value='<s:property value="peEnterpriseManager.mobilePhone"/>' maxlength="11"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox"><span class="name">所属企业：</span></td>
                                  <td class="postFormBox" style="padding-left:18px">
                                   <s:property id='address' value="peEnterpriseManager.peEnterprise.name"/>
                                 </td>
                                </tr>
                                
                           <tr>
                            <td height="50" align="center" colspan="2">
                              <input type="submit" value="提交"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="button" value="返回" onClick="javascript:window.location.href='/entity/information/personalInfo_viewInfo.action';"/>                            </td>
						  </tr>
						    <tr>
                            <td  height="10"> </td>
                          </tr>
        </table>
<input type="hidden" name="id" value='<s:property value="peEnterpriseManager.id"/>'/>
</form>
	  </div>
    </div>
</div>
<div class="clear"></div>
</body>
</html>
