<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>添加培训教学专家信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script>
	<script type="text/javascript">
		function checkAll(){
			//验证姓名
			var name = document.getElementById("bean.name");
			if(name.value.length<1){
				alert("姓名不能为空!");
				flag = false;
				name.focus();
				return false;
			}
			//验证工作单位
			var workplace = document.getElementById("bean.workplace");
			if(workplace.value.length<1){
				alert("工作单位不能为空!");
				flag = false;
				workplace.focus();
				return false;
			}
			//验证通信地址
			var address=document.getElementById('bean.address');
			if(address.value.length > 125) {
				alert('通信地址字数不能超过125个字符。');
				address.select();
				return false;
			}
			//验证年龄
			/*
			var age = document.getElementById("bean.age");
			var agePattern = /^[1-9]\d{0,2}$/;
			if(age.value!=""&&!agePattern.test(age.value)) {
				alert("年龄必须为数字，并且需要合法的年龄");
				age.select();
				return false;
			}else if(age.value!=""){
				var ageTemp = parseInt(age.value);
				if(ageTemp>120) {
					alert("年龄不合法");
					age.select();
					return false;
				}
			}
			//验证邮政编码
			var zip=document.getElementById('bean.zip');
			var myreg=/^\d{6}$/;
			if(zip.value!=""&&!myreg.test(zip.value)){
				alert("邮政编码不合法！\n请重新输入");
				zip.select();
				return false;
			}
			//验证办公电话、家庭电话及传真
			var officePhone=document.getElementById('bean.officePhone');
			var homePhone=document.getElementById('bean.homePhone');
			var fax=document.getElementById('bean.fax');
			//var reg = /(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[3]\d{9}$)/;
			var reg = /^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
			if(officePhone.value!=''&&(!reg.test(officePhone.value)||officePhone.value.length<7)){
				return checkPhone("办公电话不合法",officePhone);
			}
			if(homePhone.value!=""&&(!reg.test(homePhone.value)||homePhone.value.length<7)) {
				return checkPhone("家庭电话不合法",homePhone);
			}
			if(fax.value!=""&&(!reg.test(fax.value)||fax.value.length<7)) {
				return checkPhone("传真格式不正确",fax);
			}
			//验证手机号码
			var mobilephone=document.getElementById('bean.telephone');
			var myreg=/^\d{11}$/;
			if(mobilephone.value!=''&&!myreg.test(mobilephone.value)){
				alert("手机号码不合法！\n请重新输入");
				mobilephone.select();
				return false;
			}
			*/
			//验证身份证号
			var idCard = document.getElementById("bean.idcard");
			var birthday = document.getElementById("bean.birthdate");
			var tempBirth = birthday.value.replace("-","");
			if(idCard.value.indexOf(tempBirth)==-1) {
				alert("您输入的出生年月与身份证号不符合\n请核对仔细重新输入！");
				birthday.select();
				return false;
			}
			/*
			var idCard = document.getElementById("bean.idcard");
			if(idCard.value == ""){
				alert("身份证号不能为空！");
				idCard.focus();
				return false;
			}else {
				var idPattern = /^[1-9](\\d{14}||(\\d{16}([0-9]|X)))$/;
				if(!idPattern.test(idCard.value)) {
					alert("身份证号不合法，需要为18位或15位的身份证号！");
					idCard.focus();
					return false;
				}else {
					var birthday = document.getElementById("bean.birthdate");
					var tempBirth = birthday.value.replace("-","");
					if(idCard.value.indexOf(tempBirth)==-1) {
						alert("您输入的出生年月与身份证号不符合\n请核对仔细重新输入！");
						birthday.select();
						birthday.focus();
						return false;
					}
				}
			}
			*/
			return true;
		}
		function checkPhone(msg,focusDest) {
			alert(msg);
			focusDest.select();
			return false;
		}
		//处理输入的身份证号
		function operateIdentityNo() {
			document.getElementById('idCardInfo').innerHTML='';
			var idCard = document.getElementById("bean.idcard");
			var idPattern = /^[1-9](\d{14}||(\d{16}([0-9]|X|x)))$/;
			if(idCard.value!=""&&!idPattern.test(idCard.value)) {
				document.getElementById('idCardInfo').innerHTML="身份证号不合法，需要为18位或15位的身份证号！";
				idCard.focus();
			}else if(idCard.value!=""){
				var year = "19";
				if(idCard.value.length==18) {
					year=idCard.value.substr(6,4);
				}else {
					var subYear = idCard.value.substr(6,2);
					if(parseInt(subYear)<10) {
						year="20" + subYear;
					}else {
						year+=subYear;
					}
				}
				var month = idCard.value.length==18?idCard.value.substr(10,2):idCard.value.substr(8,2);
				var sexInfo = idCard.value.length==18?idCard.value.substr(idCard.value.length-2,1):idCard.value.substr(idCard.value.length-1,1);
				document.getElementById("bean.birthdate").value=year+"-"+month;
				var sexSel = document.getElementById("bean.enumConstByFkGender");
				if(sexInfo%2==1) {
					sexSel.selectedIndex=1;
				}else {
					sexSel.selectedIndex=2;
				}
				document.getElementById("bean.age").value=(new Date()).getFullYear()-parseInt(year);
			}
		}
		function checkOffcePhoneNo(){
			document.getElementById('offcePhoneInfo').innerHTML='';
			var offcePhone=document.getElementById('bean.officePhone');
			var reg = /^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
		 	if(offcePhone.value!=''&&!reg.test(offcePhone.value)){
				document.getElementById('offcePhoneInfo').innerHTML="办公电话格式不正确,格式：区号-电话号码，本地电话可以省略区号！";
				offcePhone.select();
				return false;
			}
		}
		function checkHomePhoneNo(){
			document.getElementById('homePhoneInfo').innerHTML='';
			var homePhone=document.getElementById('bean.homePhone');
			var reg = /^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
		 	if(homePhone.value!=''&&!reg.test(homePhone.value)){
				document.getElementById('homePhoneInfo').innerHTML="住宅电话格式不正确,格式：区号-电话号码，本地电话可以省略区号！";
				homePhone.select();
				return false;
			}
		}
		function checkAge(){
			document.getElementById('ageInfo').innerHTML='';
			var age = document.getElementById("bean.age");
			var agePattern = /^[1-9]\d{0,2}$/;
			if(age.value!=""&&!agePattern.test(age.value)) {
				document.getElementById('ageInfo').innerHTML="年龄必须为数字，并且需要合法的年龄";
				age.select();
				return false;
			}else if(age.value!=""){
				var ageTemp = parseInt(age.value);
				if(ageTemp>120) {
					document.getElementById('ageInfo').innerHTML="年龄不合法";
					age.select();
					return false;
				}
			}
		}
		function checkFaxPhoneNo(){
			document.getElementById('faxPhoneInfo').innerHTML='';
			var faxPhone=document.getElementById('bean.fax');
			var reg = /^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
		 	if(faxPhone.value!=''&&!reg.test(faxPhone.value)){
				document.getElementById('faxPhoneInfo').innerHTML="传真格式不正确,格式：区号-电话号码，本地电话可以省略区号！";
				faxPhone.select();
				return false;
			}
		}
		//验证邮箱
		function checkEmail(){
			document.getElementById('emailInfo').innerHTML='';
			var email=document.getElementById('bean.email');
			var pattern= /^\w+([-+.]\w?)*@\w+([-]\w+)*(\.\w+([-]\w+)*){1,3}$/;///^([a-z.A-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
		 	if(email.value!=''&&!pattern.test(email.value)){
				document.getElementById('emailInfo').innerHTML="邮箱格式不正确,请重新输入!";
				email.select();
				return false;
			}
		}
		function checkZip(){
			document.getElementById('zipInfo').innerHTML='';
			var zip=document.getElementById('bean.zip');
			var myreg=/^\d{6}$/;
			if(zip.value!=""&&!myreg.test(zip.value)){
				document.getElementById('zipInfo').innerHTML="邮政编码不合法，请重新输入！";
				zip.select();
				return false;
			}
		}
		function checkTelephoneNo(){
			document.getElementById('telephoneInfo').innerHTML='';
			var mobilephone=document.getElementById('bean.telephone');
			var myreg=/^\d{11}$/;
			if(mobilephone.value!=''&&!myreg.test(mobilephone.value)){
				document.getElementById('telephoneInfo').innerHTML="手机号码不合法,请重新输入!";
				mobilephone.select();
				return false;
			}
		}
		//验证出生年月的合法性
		function checkBirthday(inputObj) {
			document.getElementById('birthInfo').innerHTML='';
			var inputVal = inputObj.value;
			var birPattern = /^[0-9]{4}\-[0-9]{1,2}$/;
			if(inputVal!=""&&!birPattern.test(inputVal)) {
				document.getElementById('birthInfo').innerHTML="出生年月格式不正确,格式：'2010-01'";
				inputObj.select();
				inputObj.focus();
			}else if(inputVal!=""){
				var valArr = inputVal.split("-");
				var year = parseInt(valArr[0]);
				var month = parseInt(valArr[1]);
				if(year > 2010 || year < 1900) {
					document.getElementById('birthInfo').innerHTML="出生年份应该在1900-2010之间";
					inputObj.select();
				}else if(month>12 || month<1) {
					document.getElementById('birthInfo').innerHTML="出生月份应该在1-12之间";
					inputObj.select();
				}
			}
		}
		//进入页面后将姓名选定，定位焦点在该输入域
		function focusname(){
			var name=document.getElementById('bean.name');
			name.select();
		}
		//点击提交按钮后对各输入域作验证，通过验证则将表单提交
		function submitForm(){
			var isCheckSuc = checkAll();
			if(isCheckSuc){
				document.getElementById('teacherInfo').submit();
			}
		}
		//将状态下拉表单默认选中项设置为“待审核”
		function statusLocate() {
			var statusSel = document.getElementById("bean.enumConstByFkStatus");
			var allOption = statusSel.options;
			var option;
			for(var i=0;i<allOption.length;i++) {
				option=allOption.item(i);
				if(option.text=="待审核") {
					statusSel.selectedIndex=option.index;
				}
			}
		}
		//将弹出窗口居中显示
		function moveWindow() {
			if(!window.XMLHttpRequest) {
				window.moveTo((screen.width-document.body.clientWidth)/2,0);
				window.resizeTo(document.body.clientWidth,screen.availHeight);
			}
		}
	</script>
</head>
<body topmargin="0" leftmargin="0"  bgcolor="#FAFAFA" onload="moveWindow();statusLocate();focusname()">

<div id="main_content">
    <div class="content_title">&nbsp;添加教学培训专家信息</div>
    <div class="cntent_k">
   	  <div class="k_cc">
		<form id='teacherInfo' action="/entity/basic/peTrainingExpertsAction_addExpert.action" method="post">
			<table width="554" border="0" align="center" cellpadding="0" cellspacing="0">
            	<tr>
                	<td colspan="3" height="26" align="center" valign="middle">&nbsp;
                	<input type="hidden" name="bean.id" value="<s:property value="bean.id"/>" />
                	</td>
            	</tr>
  				<tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;*：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.name' name="bean.name" maxlength="10"/></td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;工作单位&nbsp;*：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.workplace' name="bean.workplace" style="width: 300px;" maxlength="50"/></td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;身&nbsp;份&nbsp;证&nbsp;号：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.idcard' name="bean.idcard" onblur="operateIdentityNo()" maxlength="20"/> <SPAN style="padding-left:10px;color:#ff0000" id="idCardInfo"></SPAN>  </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span></td>
	                <td class="postFormBox" style="padding-left:10px">
	                  	<select id='bean.enumConstByFkGender' name="bean.enumConstByFkGender.id">
	                  		<option value="" selected="selected">===请选择===</option>
	                  		<s:iterator value="genderList" id="gender">
	                  			<option value='<s:property value="#gender[0]"/>'>
	                  				<s:property value="#gender[1]"/>
	                  			</option>
	                  		</s:iterator>
	                  	</select>
                	</td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;科：</span></td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<select id='bean.peSubject' name="bean.peSubject.id" />
	                		<option value="" selected="selected">===请选择===</option>
							<s:iterator value="subjectList" id="subject">
								<option value='<s:property value="#subject[0]"/>'>
									<s:property value="#subject[1]"/>
								</option>
							</s:iterator>
						</select>
					</td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;省&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份：</span></td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<select id ='bean.peProvince' name="bean.peProvince.id" />
	                		<option value="" selected="selected">===请选择===</option>
	                		<s:iterator value="provinceList" id="province">
	                			<option value='<s:property value="#province[0]"/>'>
	                				<s:property value="#province[1]"/>
	                			</option>
	                		</s:iterator>
	                	</select>
	                </td>
                </tr>
                <tr valign="middle"> 
	                 <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态&nbsp;*：</span></td>
	                 <td class="postFormBox" style="padding-left:10px">
	                 	<select id='bean.enumConstByFkStatus' name="bean.enumConstByFkStatus.id" />
	                 		<s:iterator value="statusList" id="status">
	                 			<option value="<s:property value="#status[0]"/>">
	                 				<s:property value="#status[1]"/>
	                 			</option>
	                 		</s:iterator>
	                 	</select>
	                 </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.telephone' name="bean.telephone" maxlength="25"onblur="checkTelephoneNo()"/><SPAN style="padding-left:10px;color:#ff0000" id="telephoneInfo"></SPAN></td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.folk' name="bean.folk" maxlength="10"/>  </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;出&nbsp;生&nbsp;年&nbsp;月：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.birthdate' name="birthYearMonth" onblur="checkBirthday(this)" /> <SPAN style="padding-left:10px;color:#ff0000" id="birthInfo"></SPAN> </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;所&nbsp;学&nbsp;专&nbsp;业：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.major' name="bean.major" maxlength="15"/>  </td>
                </tr>
                <tr valign="middle">
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.email' name="bean.email" onblur="checkEmail()" maxlength="50"/> <SPAN style="padding-left:10px;color:#ff0000" id="emailInfo"></SPAN> </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;办&nbsp;公&nbsp;电&nbsp;话：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.officePhone' name="bean.officePhone" maxlength="15" onblur="checkOffcePhoneNo()"/><SPAN style="padding-left:10px;color:#ff0000" id="offcePhoneInfo"></SPAN> </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.age' name="bean.age" maxlength="3"onblur="checkAge()"/><SPAN style="padding-left:10px;color:#ff0000" id="ageInfo"></SPAN> </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.education' name="bean.education" maxlength="25"/>  </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;政&nbsp;治&nbsp;面&nbsp;貌：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.politics' name="bean.politics" maxlength="25"/>  </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.zhiwu' name="bean.zhiwu" maxlength="25"/>  </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.zhicheng' name="bean.zhicheng" style="width:400px;" maxlength="25"/>  </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;研&nbsp;究&nbsp;专&nbsp;长：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.researchArea' name="bean.researchArea" style="width:400px;" maxlength="100"/>  </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;教&nbsp;学&nbsp;专&nbsp;长：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.trainingArea' name="bean.trainingArea" style="width:400px;" maxlength="125"/>  </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;通&nbsp;讯&nbsp;地&nbsp;址：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.address' name="bean.address" style="width:400px;" maxlength="50"/>  </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编：</span></td>
	                <td class="postFormBox" style="padding-left:10px">
	                	<input type="text" id='bean.zip' name="bean.zip" maxlength="6"onblur="checkZip()"/><SPAN style="padding-left:10px;color:#ff0000" id="zipInfo"></SPAN>
	                </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;住&nbsp;宅&nbsp;电&nbsp;话：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.homePhone' name="bean.homePhone" maxlength="50" onblur="checkHomePhoneNo()"/><SPAN style="padding-left:10px;color:#ff0000" id="homePhoneInfo"></SPAN> </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.fax' name="bean.fax" maxlength="50"onblur="checkFaxPhoneNo()"/><SPAN style="padding-left:10px;color:#ff0000" id="faxPhoneInfo"></SPAN> </td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><input type="text" id='bean.note' name="bean.note" style="width:400px;" maxlength="100"/></td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">&nbsp;&nbsp;个&nbsp;&nbsp;人&nbsp;&nbsp;简&nbsp;&nbsp;历：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><textarea  class="smallarea"  name="bean.personalResume" cols="70" rows="12" id="bean.personalResume"></textarea></td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">教育教学成果：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><textarea  class="smallarea"  name="bean.trainingResult" cols="70" rows="12" id="bean.trainingResult"></textarea></td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">教师培训经验：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><textarea  class="smallarea"  name="bean.trainingExperience" cols="70" rows="12" id="bean.trainingExperience"></textarea></td>
                </tr>
                <tr valign="middle"> 
	                <td width="140" height="30" align="left" class="postFormBox" style="padding-left:50px"><span class="name">其他说明事项：</span></td>
	                <td class="postFormBox" style="padding-left:10px"><textarea  class="smallarea"  name="bean.otherItems" cols="70" rows="12" id="bean.otherItems"></textarea></td>
                </tr>
               	<tr>
                    <td height="40" align="center" colspan="2">
                      <input type="button" value="&nbsp;添&nbsp;&nbsp;加" onClick="submitForm()" style="width:80px;cursor:pointer" />
                      <input type="reset" value="&nbsp;重&nbsp;&nbsp;置" style="width:80px;cursor:pointer" />
                    </td>
				</tr>
        	</table>
		</form>
	  </div>
    </div>
</div>
<div class="clear"></div>
<script type="text/javascript"> 
	<!-- 
	// Automatically calculates the editor base path based on the _samples directory. 
	// This is usefull only for these samples. A real application should use something like this: 
	// oFCKeditor.BasePath = '/fckeditor/' ; // '/fckeditor/' is the default value. 
	
	var oFCKeditor1 = new FCKeditor( 'bean.personalResume' ) ; 
	var oFCKeditor2 = new FCKeditor( 'bean.trainingResult' ) ; 
	var oFCKeditor3 = new FCKeditor( 'bean.trainingExperience' ) ; 
	var oFCKeditor4 = new FCKeditor( 'bean.otherItems' ) ; 
	
	
	oFCKeditor1.Height = 300 ; 
	oFCKeditor1.Width  = 700 ; 
	
	oFCKeditor1.Config['ImageBrowserURL']=oFCKeditor1.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
	oFCKeditor1.Config['ImageUploadURL']=oFCKeditor1.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';
	
	oFCKeditor1.Config['LinkBrowserURL']=oFCKeditor1.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
	oFCKeditor1.Config['LinkUploadURL']=oFCKeditor1.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';
	
	oFCKeditor1.Config['FlashBrowserURL']=oFCKeditor1.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
	oFCKeditor1.Config['FlashUploadURL']=oFCKeditor1.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';
	
	oFCKeditor1.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; 
	oFCKeditor1.ReplaceTextarea() ; 
	
	
	oFCKeditor2.Height = 300 ; 
	oFCKeditor2.Width  = 700 ; 
	
	oFCKeditor2.Config['ImageBrowserURL']=oFCKeditor2.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
	oFCKeditor2.Config['ImageUploadURL']=oFCKeditor2.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';
	
	oFCKeditor2.Config['LinkBrowserURL']=oFCKeditor2.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
	oFCKeditor2.Config['LinkUploadURL']=oFCKeditor2.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';
	
	oFCKeditor2.Config['FlashBrowserURL']=oFCKeditor2.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
	oFCKeditor2.Config['FlashUploadURL']=oFCKeditor2.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';
	
	oFCKeditor2.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; 
	oFCKeditor2.ReplaceTextarea() ;
	
	
	oFCKeditor3.Height = 300 ; 
	oFCKeditor3.Width  = 700 ; 
	
	oFCKeditor3.Config['ImageBrowserURL']=oFCKeditor3.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
	oFCKeditor3.Config['ImageUploadURL']=oFCKeditor3.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';
	
	oFCKeditor3.Config['LinkBrowserURL']=oFCKeditor3.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
	oFCKeditor3.Config['LinkUploadURL']=oFCKeditor3.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';
	
	oFCKeditor3.Config['FlashBrowserURL']=oFCKeditor3.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
	oFCKeditor3.Config['FlashUploadURL']=oFCKeditor3.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';
	
	oFCKeditor3.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; 
	oFCKeditor3.ReplaceTextarea() ;
	
	
	oFCKeditor4.Height = 300 ; 
	oFCKeditor4.Width  = 700 ; 
	
	oFCKeditor4.Config['ImageBrowserURL']=oFCKeditor4.BasePath+'editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector';
	oFCKeditor4.Config['ImageUploadURL']=oFCKeditor4.BasePath+'editor/filemanager/upload/whatyuploader?Type=Image';
	
	oFCKeditor4.Config['LinkBrowserURL']=oFCKeditor4.BasePath+'editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector';
	oFCKeditor4.Config['LinkUploadURL']=oFCKeditor4.BasePath+'editor/filemanager/upload/whatyuploader?Type=File';
	
	oFCKeditor4.Config['FlashBrowserURL']=oFCKeditor4.BasePath+'editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector';
	oFCKeditor4.Config['FlashUploadURL']=oFCKeditor4.BasePath+'editor/filemanager/upload/whatyuploader?Type=Flash';
	
	oFCKeditor4.Value = 'This is some <strong>sample text</strong>. You are using FCKeditor.' ; 
	oFCKeditor4.ReplaceTextarea() ;
	//--> 
	<s:if test="msg!=null">
		var flag = confirm('<s:property value="msg" />');
		if(!flag) {
			window.close();
		}
	</s:if>
</script>
</body>
</html>