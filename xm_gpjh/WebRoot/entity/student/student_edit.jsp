<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
</head>

<script type="text/javascript">
	function checkEmail()
	{
		if(document.getElementById('email').value==''){
			return true;
		}
		var email=document.getElementById('email').value;
		var pattern= /^([a-z.A-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
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
	
	function checkWorkplace() {
		if(document.getElementById('workplace').value.length > 25) {
			alert('工作单位字数不能超过25个字符。');
			document.getElementById('workplace').focus();
		}
	}
	
	function checkZip() {
		var reg = /^\d{6}$/;
		if(!reg.test(document.getElementById('zip').value)){
			alert('邮编输入不正确。');
			document.getElementById('zip').focus();
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
	
	function checkPhone() {
		var reg = /(^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[3]\d{9}$)/;
		if(!reg.test(document.getElementById('phone').value)) {
			alert('固定电话号码不合法。');
			document.getElementById('phone').focus();
		}
	}
</script>
<body>
<div id="main_content">
	<div class="content_title">您当前的位置是：学生<s:property value="peStudent.getTrueName()"/>的工作室 &gt; 个人信息 &gt; 修改个人信息</div>
    <div class="student_cntent_k">
    	<div class="k_cc">
    	<form method="post" action="/entity/workspaceStudent/student_editexe.action">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
			  <tr>
			      <td height="28" colspan="2" align="center" valign="middle" class="14title2"><div align="center">个人信息修改                            </div></td>
			  </tr>
				  <tr>
				  <td colspan="2" ><table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
	                             <tr valign="middle"> 
	                               <td width="242"  height="30" align="left"><div align="left"><span class="name">所属学习中心：</span></div></td>
	                               <td width="420" class="12texthei"><div align="left">
	                                 <s:property value="peStudent.getPeSite().getName()"/>&nbsp;
	                                 </div></td>
	                             </tr>
	                             <tr valign="middle"> 
	                               <td width="242"  height="30" align="left"><div align="left"><span class="name">所属年级：</span></div></td>
	                               <td class="12texthei"><div align="left">
	                                 <s:property value="peStudent.getPeGrade().getName()"/>&nbsp;
	                                 </div></td>
	                             </tr>
	                             <tr valign="middle"> 
	                               <td width="242"  height="30" align="left"><div align="left"><span class="name">所属层次：</span></div></td>
	                               <td class="12texthei"><div align="left">
	                                 	<s:property value="peStudent.getPeEdutype().getName()"/>&nbsp;
	                                 </div></td>
	                             </tr>
	                             <tr valign="middle"> 
	                               <td width="242"  height="30" align="left"><div align="left"><span class="name">所属专业：</span></div></td>
	                               <td class="12texthei"><div align="left">
	                                 	<s:property value="peStudent.getPeMajor().getName()"/>&nbsp;
	                                 </div></td>
	                             </tr>
	                             <tr valign="middle"> 
	                               <td width="242"  height="30" align="left"><div align="left"><span class="name">学&nbsp;&nbsp;&nbsp;&nbsp;号：</span></div></td>
	                               <td class="12texthei"><div align="left">
	                                 	<s:property value="peStudent.getRegNo()"/>&nbsp;
	                                 </div></td>
	                             </tr>
	                             <tr valign="middle"> 
	                               <td height="30" align="left"><div align="left"><span class="name">姓&nbsp;&nbsp;&nbsp;&nbsp;名：</span></div></td>
	                               <td class="12texthei"><div align="left">
	                                 	<s:property value="peStudent.getTrueName()"/>&nbsp;
	                                 </div></td>
	                             </tr>
	                             <tr valign="middle"> 
	                               <td height="30" align="left"><div align="left"><span class="name">性&nbsp;&nbsp;&nbsp;&nbsp;别：</span></div></td>
	                               <td class="12texthei"><div align="left">
	                                 	<s:property value="peStudent.getPrStudentInfo().getGender()"/>&nbsp;
	                                 </div></td>
	                             </tr>
	                             <tr valign="middle"> 
	                               <td height="30" align="left"><div align="left"><span class="name">民&nbsp;&nbsp;&nbsp;&nbsp;族：</span></div></td>
	                               <td class="12texthei"><div align="left">
	                                 	<s:property value="peStudent.getPrStudentInfo().getFork()"/>&nbsp;
	                                 </div></td>
	                             </tr>
	                             <tr valign="middle"> 
	                               <td height="30" align="left"><div align="left"><span class="name">出生日期：</span></div></td>
	                               <td class="12texthei"><div align="left">
	                                 	<s:property value="birthday"/>&nbsp;
	                                 </div></td>
	                             </tr>
	                             <tr valign="middle"> 
	                               <td height="30" align="left"><div align="left"><span class="name">政治面貌：</span></div></td>
	                               <td class="12texthei"><div align="left">
	                                 	<s:property value="peStudent.getPrStudentInfo().getZzmm()"/>&nbsp;
	                                 </div></td>
	                             </tr>
	                             <tr valign="middle"> 
	                               <td height="30" align="left"><div align="left"><span class="name">文化程度：</span></div></td>
	                               <td class="12texthei"><div align="left">
	                                 	<s:property value="peStudent.getPrStudentInfo().getXueli()"/>&nbsp;
	                                 </div></td>
	                             </tr>
	                             <tr valign="middle"> 
	                               <td height="30" align="left"><div align="left"><span class="name">证件类型：</span></div></td>
	                               <td class="12texthei"><div align="left">
	                                 	<s:property value="peStudent.getPrStudentInfo().getCardType()"/>&nbsp;
	                                 </div></td>
	                             </tr>
	                             <tr valign="middle"> 
	                               <td height="30" align="left"><div align="left"><span class="name">证件号码：</span></div></td>
	                               <td class="12texthei"><div align="left">
	                                 	<s:property value="peStudent.getPrStudentInfo().getCardNo()"/>&nbsp;
	                                 </div></td>
	                             </tr>
	                             <tr valign="middle"> 
	                               <td height="30" align="left"><div align="left"><span class="name">通信地址：</span></div></td>
	                               <td class="12texthei">
	                               	
                               		   <div align="left">
	                               		  <input type="text" id="address" name="peStudent.prStudentInfo.address" value="<s:property value="peStudent.getPrStudentInfo().getAddress()"/>" maxlength="40" onblur="checkAddress()"/>&nbsp; </div></td>
	                             </tr>
	                             <tr valign="middle"> 
	                               <td height="30" align="left"><div align="left"><span class="name">邮政编码：</span></div></td>
	                               <td class="12texthei">
	                               	
                               		   <div align="left">
	                               		  <input type="text" name="peStudent.prStudentInfo.zip" value="<s:property value="peStudent.getPrStudentInfo().getZip()"/>" maxlength="10" id="zip" onblur="checkZip()"/>&nbsp; </div></td>
	                             </tr>
	                             <tr valign="middle"> 
	                               <td height="30" align="left"><div align="left"><span class="name">工作单位：</span></div></td>
	                               <td class="12texthei">
	                               	
                               		   <div align="left">
	                               		  <input type="text" id="workplace" name="peStudent.prStudentInfo.workplace" value="<s:property value="peStudent.getPrStudentInfo().getWorkplace()"/>" maxlength="30" onblur="checkWorkplace()"/>&nbsp; </div></td>
	                             </tr>
	                             <tr valign="middle"> 
	                             <tr valign="middle"> 
	                               <td height="30" align="left"><div align="left"><span class="name">移动电话：</span></div></td>
	                               <td class="12texthei">
	                               	
                               		   <div align="left">
	                               		  <input type="text" name="peStudent.prStudentInfo.mobilephone" value="<s:property value="peStudent.getPrStudentInfo().getMobilephone()"/>" maxlength="11" id="mobilephone"  onblur="checkMobilePhone()" />&nbsp; </div></td>
	                             </tr>
	                             <tr valign="middle"> 
	                               <td height="30" align="left"><div align="left"><span class="name">固定电话：</span></div></td>
	                               <td class="12texthei">
	                               	
                               		   <div align="left">
	                               		  <input type="text" id="phone" name="peStudent.prStudentInfo.phone" value="<s:property value="peStudent.getPrStudentInfo().getPhone()"/>" maxlength="20" onblur="checkPhone()"/>&nbsp; </div></td>
	                             </tr>
	                             <tr valign="middle"> 
	                               <td height="30" align="left"><div align="left"><span class="name"> 
	                                 电子邮件：</span></div></td>
	                               <td class="12texthei">
	                               	
                               		   <div align="left">
	                               		  <input type="text" name="peStudent.prStudentInfo.email" id="email" value="<s:property value="peStudent.getPrStudentInfo().getEmail()"/>" maxlength="50" onblur="checkEmail()"/>&nbsp; </div></td>
	                             </tr>
	                           </table></td>
				  </tr>
				  <tr>
                        <td colspan="2" height="8" align= "center"><input type="submit" name="submit" value="提交"/></td>
			      </tr>
			</table>
		</form>
    </div>
  </div>
</div>
</body>
</html>
