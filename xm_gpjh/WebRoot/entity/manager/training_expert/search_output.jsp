<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>查找培训专家</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
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
		var pattern= /^\w+([-+.]\w+)*@\w+([-]\w+)*(\.\w+([-]\w+)*){1,3}$/;///^([a-z.A-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
		if(!pattern.test(email))
		{
			alert("邮箱格式不正确！\n请重新输入");
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
		if(!reg.test(document.getElementById('phone').value)||document.getElementById('phone').value.length<7) {
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
  
	function checkEmail()
	{
		if(document.getElementById('email').value==''){
			return true;
		}
		var email=document.getElementById('email').value;
		//var pattern= /^([a-z.A-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
		var pattern=/^\w+([-+.]\w+)*@\w+([-]\w+)*(\.\w+([-]\w+)*){1,3}$/;
		if(!pattern.test(email))
		{
			alert("邮箱格式不正确！\n请重新输入");
			document.getElementById('email').focus();
		}
		return true;
	}
	
	function checkAddress(){
		if(document.getElementById('address').value.length > 25) {
			alert('通信地址字数不能超过25个字符。');
			document.getElementById('address').focus();
		}
	}
	
	function checkMobilePhone()
	{
		
		var myMobile_no=document.getElementById('mobilephone').value;
		var myreg=/^\d{11}$/;
		if(myMobile_no=='')
		{
			alert("移动电话不能为空");
			document.getElementById('mobilephone').focus();
			return false;
		}
		if(!myreg.test(myMobile_no))
		{
			alert("移动电话不合法！\n请重新输入");
			document.getElementById('mobilephone').focus();
		}
		//return true;
	}
	
	function checkPhone() {
		//var reg =/(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/
		var reg = /(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[3]\d{9}$)/;
		var phone=document.getElementById('phone').value;
		if(phone=='')
		{
			alert("固定电话不能为空");
			document.getElementById('phone').focus();
			return false;
		}
		
		if(!reg.test(phone)) {
			alert('固定电话号码不合法。');
			document.getElementById('phone').focus();
			return false;
		}
		if(phone.length<7||phone.length>12) {
			alert('固定电话号码必须在7位--12位之间！');
			document.getElementById('phone').focus();
			return false;
		}
	}
	function chk(){
		var name=document.getElementById('expname');
		var unit=document.getElementById('expunit');
		var subject=document.getElementById('expsubject');
		if(name.value==''){
			alert('专家姓名不能为空');
			name.focus();
			return false;
		}
		else if(name.value.length<3){
			alert('专家姓名不能少于两个字');
			name.focus();
			return false;
		}
		
		if(unit.value==''){
			alert('学校不能为空');
			unit.focus();
			return false;
		}
		if(subject.value=''){
			alert('专业不能为空');
			subject.focus();
			return false;
		}
		document.getElementById('teacherInfo').submit();
	}
</script>
  
  <body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">


<div id="main_content">
    <div class="content_title">查找培训专家</div>
    <div class="cntent_k">
   	  <div class="k_cc">
<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
                          
                          <tr>
                            <td colspan="3" height="26" align="center" valign="middle" >&nbsp;</td>
                          </tr>
						 <s:iterator value="expertList" id="expert">
		                          <tr>
		                            <td height="8"> </td>
		                          </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="#expert.name"/> </td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name"> 性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="#expert.enumConstByFkGender.name"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name"> 民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族：</td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="#expert.folk"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name"> 出生年月：</td>
                                  <td class="postFormBox" style="padding-left:18px"><s:date name="#expert.birthdate" format="yyyy-MM"/></td>
                                </tr>
                                
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name"> 学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="#expert.education"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name"> 学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;科：</span></td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="#expert.peSubject.name"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name"> 所学专业：</td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="#expert.major"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name"> 政治面貌：</td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="#expert.politics"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name"> 职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务：</td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="#expert.zhiwu"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name"> 职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="#expert.zhicheng"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name"> 身份证号：</td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="#expert.idcard"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name"> 工作单位：</td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="#expert.peWorkplace.name"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name"> 研&nbsp;究&nbsp;专&nbsp;长：</td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="#expert.researchArea"/></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name"> 教&nbsp;学&nbsp;专&nbsp;长：</td>
                                  <td class="postFormBox" style="padding-left:18px"><s:property value="#expert.trainingArea"/></td>
                                </tr>
                               </s:iterator>
                           <tr>
                            <td height="50" align="center" colspan="2">
                              <input type="button" value="返回" onclick="history.back();"/> </td>
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
