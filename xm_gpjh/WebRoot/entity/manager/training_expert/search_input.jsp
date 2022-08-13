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
		if(name.value==''&&subject.value=='')
		{
			alert('请至少添写一项');
			name.focus();
			return false;
		}
		if(name.value!=''&&name.value.length<2){
			alert('专家姓名不能少于两个字');
			name.focus();
			return false;
		}
		
	
		document.getElementById('teacherInfo').submit();
	}
</script>
  
  <body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA">


<div id="main_content">
    <div class="content_title">查找培训教学专家</div>
    <div class="cntent_k">
   	  <div class="k_cc">
<form id='teacherInfo' action="/entity/basic/peTrainingExpertsSearchAction.action" method="get">
<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
                          
                          <tr>
                            <td colspan="3" height="26" align="center" valign="middle" >&nbsp;</td>
                          </tr>
                          <tr>
                            <td height="8"> </td>
                          </tr>
						 
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span></td>
                                  <td class="postFormBox" style="padding-left:10px"><input type="text" id='expname' name="search1_name" value=""/>  </td>
                                </tr>
                             
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name"> 学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;科：</span></td>
                                  <td class="postFormBox" style="padding-left:10px">
                                  
                                  	<select name="search1_peSubject.id" id='expsubject'>
                                  		<option value = "">请选择...</option>
                                  		<s:iterator value="subjectList" id="subject">
                                  		<option value="<s:property value="#subject[0]"/>"><s:property value="#subject[1]"/></option>
                                  		</s:iterator>
                                  	</select>
                                  </td>
                                </tr>
                               
                           <tr>
                            <td height="50" align="center" colspan="2">
                              <input type="button" value="查找" onClick="chk()"/></td>
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
<s:if test="msg!=null">
<script>
alert('<s:property value="msg"/>');
</script>
</s:if>
</body>
</html>
