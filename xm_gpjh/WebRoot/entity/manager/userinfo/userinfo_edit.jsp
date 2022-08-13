<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
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
  function checkzip_showred(){
  
		var myMobile_no=document.getElementById('bean.zip').value;
		var myreg=/^\d{6}$/;
		if(!myreg.test(myMobile_no))
		{	document.getElementById('bean.zips1').style.display="";
		}else{
			document.getElementById('bean.zips1').style.display="none";
		}
  }
  	function checkzip(){
  
		var myMobile_no=document.getElementById('bean.zip').value;
		var myreg=/^\d{6}$/;
		if(!myreg.test(myMobile_no))
		{	document.getElementById('bean.zips1').focus();
		return false;
		}
		return true;
  }
	function checkEmail()
	{
		var email=document.getElementById('bean.email').value;
		//var pattern= /^([a-z.A-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
		var pattern=/^\w+([-+.]\w+)*@\w+([-]\w+)*(\.\w+([-]\w+)*){1,3}$/;
		if(!pattern.test(email))
		{
			document.getElementById('bean.email').focus();
			return false;
		}
		return true;
	}
	function checkEmail_showred()
	{
		var email=document.getElementById('bean.email').value;
		//var pattern= /^([a-z.A-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
		var pattern=/^\w+([-+.]\w+)*@\w+([-]\w+)*(\.\w+([-]\w+)*){1,3}$/;
		if(!pattern.test(email))
		{
			document.getElementById('bean.emails1').style.display="";
		}else{
			document.getElementById('bean.emails1').style.display="none";
		}
	}
	
	function checkAddress(){
		if(document.getElementById('address').value.length > 25) {
			alert('通信地址字数不能超过25个字符。');
			document.getElementById('address').focus();
		}
	}
	function checkMobilePhone()
	{
		var myMobile_no=document.getElementById('bean.telephone').value;
		var myreg=/^\d{11}$/;
		if(myMobile_no=='')
		{
			document.getElementById('bean.telephone').focus();
			return false;
		}
		if(!myreg.test(myMobile_no))
		{
			document.getElementById('bean.telephone').focus();
			return false;
		}
		return true;
	}
	function checkMobilePhone_showred()
	{
		var myMobile_no=document.getElementById('bean.telephone').value;
		var myreg=/^\d{11}$/;
		if(myMobile_no=='')
		{
			document.getElementById('bean.telephones').style.display="";
		}else{
			document.getElementById('bean.telephones').style.display="none";
		}
		if(!myreg.test(myMobile_no))
		{
			document.getElementById('bean.telephones1').style.display="";
		}else{
			document.getElementById('bean.telephones1').style.display="none";
		}
	}
	
	function checkPhone() {
		//var reg =/(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/
		var reg = /(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[3]\d{9}$)/;
		var phone=document.getElementById('bean.officePhone').value;
		if(phone=='')
		{
			document.getElementById('bean.officePhone').focus();
			return false;
		}
		
		if(!reg.test(phone)) {
			document.getElementById('bean.officePhone').focus();
			return false;
		}
		if(phone.length<7||phone.length>12) {
			document.getElementById('bean.officePhone').focus();
			return false;
		}
		return true;
	}
	function checkPhone_showred() {
		//var reg =/(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/
		var reg = /(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[3]\d{9}$)/;
		var phone=document.getElementById('bean.officePhone').value;
		if(phone=='')
		{
			document.getElementById('bean.officePhones').style.display="";
		}else{
			document.getElementById('bean.officePhones').style.display="none";
		}
		
		if(!reg.test(phone)) {
			document.getElementById('bean.officePhones1').style.display="";
		}else{
			document.getElementById('bean.officePhones1').style.display="none";
		}
		
		if(phone.length<7||phone.length>12) {
			document.getElementById('bean.officePhones2').style.display="";
		}else{
			document.getElementById('bean.officePhones2').style.display="none";
		}
		return true;
	}
	function checkPhone1() {
		//var reg =/(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/
		var reg = /(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[3]\d{9}$)/;
		var phone=document.getElementById('bean.fax').value;
		if(phone=='')
		{
			document.getElementById('bean.fax').focus();
			return false;
		}
		
		if(!reg.test(phone)) {
			document.getElementById('bean.fax').focus();
			return false;
		}
		if(phone.length<7||phone.length>12) {
			document.getElementById('bean.fax').focus();
			return false;
		}
		return true;
	}
	function checkPhone1_showred() {
		//var reg =/(^[0-9]{3,4}\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/
		var reg = /(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[3]\d{9}$)/;
		var phone=document.getElementById('bean.fax').value;
		if(phone=='')
		{
			document.getElementById('bean.faxs').style.display="";
		}else{
			document.getElementById('bean.faxs').style.display="none";
		}
		
		if(!reg.test(phone)) {
			document.getElementById('bean.faxs1').style.display="";
		}else{
			document.getElementById('bean.faxs1').style.display="none";
		}
		
		if(phone.length<7||phone.length>12) {
			document.getElementById('bean.faxs2').style.display="";
		}else{
			document.getElementById('bean.faxs2').style.display="none";
		}
		return true;
	}
	function chk(){
		chk_showred();//显示所有输入地方是否输入正常
		
		var name=document.getElementById('bean.name');
		
		var department=document.getElementById('bean.department');
		var telephone=document.getElementById('bean.telephone');
		var email=document.getElementById('bean.email');
		var officePhone=document.getElementById('bean.officePhone');
		var fax=document.getElementById('bean.fax');
		var zhiwuzhicheng=document.getElementById('bean.zhiwuzhicheng');
		var address=document.getElementById('bean.address');
		var zip=document.getElementById('bean.zip');
		
		var names=document.getElementById('bean.names');
		var departments=document.getElementById('bean.departments');
		var telephones=document.getElementById('bean.telephones');
		var emails=document.getElementById('bean.emails');
		var officePhones=document.getElementById('bean.officePhones');
		var faxs=document.getElementById('bean.faxs');
		var zhiwuzhichengs=document.getElementById('bean.zhiwuzhichengs');
		var addresss=document.getElementById('bean.addresss');
		var zips=document.getElementById('bean.zips');
		if(name.value==''){
			names.style.display="";
			name.focus();
			return false;
		}
		
		if(department.value==''){
			departments.style.display="";
			department.focus();
			return false;
		}
		if(telephone.value==''){
			telephones.style.display="";
			telephone.focus();
			return false;
		}
		if(!checkMobilePhone()) return false;;
		if(email.value==''){
			emails.style.display="";
			email.focus();
			return false;
		}
		if(!checkEmail()) return false;
		if(officePhone.value==''){
			officePhones.style.display="";
			officePhone.focus();
			return false;
		}
		if(!checkPhone()) return false;
		if(fax.value==''){
			faxs.style.display="";
			fax.focus();
			return false;
		}
		if(!checkPhone1()) return false;
		if(zhiwuzhicheng.value==''){
			zhiwuzhichengs.style.display="";
			zhiwuzhicheng.focus();
			return false;
		}
		if(address.value==''){
			addresss.style.display="";
			address.focus();
			return false;
		}
		if(!checkzip()) return false;
		if(zip.value==''){
			zips.style.display="";
			zip.focus();
			return false;
		}
		return true;
		//alert(document.getElementById('teacherInfo').action);
	}
	function chk_showred(){
	
		var name=document.getElementById('bean.name');
		
		var department=document.getElementById('bean.department');
		var telephone=document.getElementById('bean.telephone');
		var email=document.getElementById('bean.email');
		var officePhone=document.getElementById('bean.officePhone');
		var fax=document.getElementById('bean.fax');
		var zhiwuzhicheng=document.getElementById('bean.zhiwuzhicheng');
		var address=document.getElementById('bean.address');
		var zip=document.getElementById('bean.zip');
		
		var names=document.getElementById('bean.names');
		var departments=document.getElementById('bean.departments');
		var telephones=document.getElementById('bean.telephones');
		var emails=document.getElementById('bean.emails');
		var officePhones=document.getElementById('bean.officePhones');
		var faxs=document.getElementById('bean.faxs');
		var zhiwuzhichengs=document.getElementById('bean.zhiwuzhichengs');
		var addresss=document.getElementById('bean.addresss');
		var zips=document.getElementById('bean.zips');
		
		if(name.value==''){
			names.style.display="";
		}else{
			names.style.display="none";
		}
		
		if(department.value==''){
			departments.style.display="";
		}else{
			departments.style.display="none";
		}
		
		if(telephone.value==''){
			telephones.style.display="";
		}else{
			telephones.style.display="none";
		}
		
		checkMobilePhone_showred();
		/*if(!checkMobilePhone()){
			telephones.style.display="";
		}else{
			telephones.style.display="none";
		}*/
		
		if(email.value==''){
			emails.style.display="";
		}else{
			emails.style.display="none";
		}
		
		checkEmail_showred();
		checkzip_showred();
		/*
		if(!checkEmail()){
			emails.style.display="";
		}else{
			emails.style.display="none";
		}*/
		
		if(officePhone.value==''){
			officePhones.style.display="";
		}else{
			officePhones.style.display="none";
		}
		
		
		checkPhone_showred();
		/*
		if(!checkPhone()){
			officePhones.style.display="";
		}else{
			officePhones.style.display="none";
		}*/
		
		if(fax.value==''){
			faxs.style.display="";
		}else{
			faxs.style.display="none";
		}
		
		checkPhone1_showred();
		/*
		if(!checkPhone1()){
			faxs.style.display="";
		}else{
			faxs.style.display="none";
		}*/
		
		if(zhiwuzhicheng.value==''){
			zhiwuzhichengs.style.display="";
		}else{
			zhiwuzhichengs.style.display="none";
		}
		
		if(address.value==''){
			addresss.style.display="";
		}else{
			addresss.style.display="none";
		}
		if(zip.value==''){
			zips.style.display="";
		}else{
			zips.style.display="none";
		}
	//	var zip=document.getElementById('bean.zip');
			
		//alert(document.getElementById('teacherInfo').action);
	}
	function focusname(){
		var name=document.getElementById('bean.name');
			name.focus();
	}
	function subt(){
		if(chk()){
			document.getElementById('teacherInfo').submit();
		}
	}
</script>
  
  <body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA" onload="focusname()">
<div id="main_content">
    <div class="content_title">修改联系人资料</div>
    <div class="cntent_k">
   	  <div class="k_cc">
<form id='teacherInfo' action="/entity/basic/userInfoEditAction_saveUserInfo.action" method="post">
<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td colspan="3" height="26" align="center" valign="middle" >&nbsp;</td>
                          </tr>
                          <tr>
                            <td height="8" colspan="3" style="color:red;padding-bottom:20px;text-align:center">温馨提示：首次登录时请先完善联系人信息，之后再做其他操作，其中带“*”的为必填项</td>
                          </tr>
                                <tr valign="middle"> 
                                  <td width="100" height="30" align="left" class="postFormBox" style="padding-left:50px" width="20%"><span class="name">用&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;名：</span></td>
                                  <td class="postFormBox" width="140" style="padding-left:10px"><s:property value="bean.loginId"/></td>
                                  <td class="postFormBox" style="padding-left:10px" width="100">&nbsp;</td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位：</span></td>
                                  <td class="postFormBox" style="padding-left:10px"><s:property value="bean.peUnit.name"/> </td>
                                  <td class="postFormBox" style="padding-left:10px">&nbsp;</td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名*：</span></td>
                                  <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.name' name="bean.name" value="<s:property value="bean.name"/>" onblur="chk()" maxlength="20"/></td>
                                  <td class="postFormBox" style="padding-left:10px"><span style="color:red;display:none;" id="bean.names">姓名不能为空</span></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别*：</span></td>
                                  <td class="postFormBox" style="padding-left:10px"><select id='bean.enumConstByFkGender' name="bean.enumConstByFkGender.id" onblur="chk()"><s:iterator value="genderList" id="gender"><option value="<s:property value="#gender[0]"/>" <s:if test="bean.enumConstByFkGender.id==#gender[0]">selected</s:if>><s:property value="#gender[1]"/></option></s:iterator></select>  </td>
                                  <td class="postFormBox" style="padding-left:10px"><span style="color:red;display:none;" id="enumConstByFkGenders">性别不能为空</span></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族：</span></td>
                                  <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.folk' name="bean.folk" value="<s:property value="bean.folk"/>" onblur="chk();" maxlength="10"/>  </td>
                                  <td class="postFormBox" style="padding-left:10px"><span style="color:red;display:none;" id="bean.folks">民族不能为空</span></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门*：</span></td>
                                  <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.department' name="bean.department" value="<s:property value="bean.department"/>" onblur="chk()" maxlength="50"/>  </td>
                                  <td class="postFormBox" style="padding-left:10px"><span style="color:red;display:none;" id="bean.departments">部门不能为空</span></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机*：</span></td>
                                  <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.telephone' name="bean.telephone" value="<s:property value="bean.telephone"/>" onblur="chk()"/>  </td>
                                  <td class="postFormBox" style="padding-left:10px"><span style="color:red;display:none;" id="bean.telephones">手机不能为空</span><span style="color:red;display:none;" id="bean.telephones1">&nbsp;手机号码不合法</span></td>
                                </tr>
                                <tr valign="middle">
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱*：</span></td>
                                  <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.email' name="bean.email" value="<s:property value="bean.email"/>" onblur="chk()" maxlength="25"/>  </td>
                                  <td class="postFormBox" style="padding-left:10px"><span style="color:red;display:none;" id="bean.emails">邮箱不能为空</span><span style="color:red;display:none;" id="bean.emails1">&nbsp;邮箱格式不正确</span></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">办公电话*：</span></td>
                                  <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.officePhone' name="bean.officePhone" value="<s:property value="bean.officePhone"/>" onblur="chk()"/>  </td>
                                  <td class="postFormBox" style="padding-left:10px"><span style="color:red;display:none;" id="bean.officePhones">办公电话不能为空</span><span style="color:red;display:none;" id="bean.officePhones1">&nbsp;办公电话号码不合法</span><span style="color:red;display:none;" id="bean.officePhones2">&nbsp;办公电话号码必须在7位--12位之间！</span></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真*：</span></td>
                                  <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.fax' name="bean.fax" value="<s:property value="bean.fax"/>" onblur="chk()"/>  </td>
                                  <td class="postFormBox" style="padding-left:10px"><span style="color:red;display:none;" id="bean.faxs">传真不能为空</span><span style="color:red;display:none;" id="bean.faxs1">&nbsp;传真号码不合法</span><span style="color:red;display:none;" id="bean.faxs2">&nbsp;传真号码必须在7位--12位之间</span></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">职务职称*：</span></td>
                                  <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.zhiwuzhicheng' name="bean.zhiwuzhicheng" value="<s:property value="bean.zhiwuzhicheng"/>" onblur="chk()" maxlength="50"/>  </td>
                                  <td class="postFormBox" style="padding-left:10px"><span style="color:red;display:none;" id="bean.zhiwuzhichengs">职务职称不能为空</span></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历：</span></td>
                                  <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.education' name="bean.education" value="<s:property value="bean.education"/>" onblur="chk()" maxlength="25"/>  </td>
                                  <td class="postFormBox" style="padding-left:10px"><span style="color:red;display:none;" id="bean.educations">学历不能为空</span></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;政治面貌：</span></td>
                                  <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.politics' name="bean.politics" value="<s:property value="bean.politics"/>" onblur="chk()" maxlength="25"/>  </td>
                                  <td class="postFormBox" style="padding-left:10px"><span style="color:red;display:none;" id="bean.politicss">政治面貌不能为空</span></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">通讯地址*：</span></td>
                                  <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.address' name="bean.address" value="<s:property value="bean.address"/>" onblur="chk()" maxlength="50"/>  </td>
                                  <td class="postFormBox" style="padding-left:10px"><span style="color:red;display:none;" id="bean.addresss">通讯地址不能为空</span></td>
                                </tr>
                                <tr valign="middle"> 
                                  <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编*：</span></td>
                                  <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.zip' name="bean.zip" value="<s:property value="bean.zip"/>" onblur="chk()"/>  </td>
                                  <td class="postFormBox" style="padding-left:10px"><span style="color:red;display:none;" id="bean.zips">邮编不能为空</span><span style="color:red;display:none;" id="bean.zips1">&nbsp;邮政编码不合法</span></td>
                                </tr>
                           <tr>
                            <td height="50" align="center" colspan="3">
                              <input type="button" value="确定" onClick="subt()"/></td>
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
  <s:if test="succ">
		if(top.frames.length==0){
			top.location.href="/entity/manager/index.jsp";
		}
  </s:if>
</script>
</s:if>
</body>
</html>
