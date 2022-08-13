<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>生殖健康咨询师培训课堂</title>
<link href="/entity/bzz-students/css1.css" rel="stylesheet" type="text/css" />
<link href="/entity/manager/statistics/css/admincss.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" media="all" href="/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1"/>	
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
<script language="javascript" src="/js/JSKit.js"></script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {font-size: 12px; color: #0041A1; font-weight: bold; }
.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}
.kctx_zi1 {
	font-size: 12px;
	color: #0041A1;
}
.kctx_zi2 {
	font-size: 12px;
	color: #54575B;
	line-height: 24px;
	padding-top:2px;
}
-->
</style>
<script language="javascript" >
	var jk=new JSKit("/js/");
	jk.Import("Region");
	jk.Run(function(){
		var region=new Region();
		/*三级联动*/
		region.create({
			"name":"region3",
			"level":"3",
			"provinceObj":document.getElementById("sltProvince3"),
			"cityObj":document.getElementById("sltCity3"),
			"countryCityObj":document.getElementById("sltCountryCity3"),
			"defaultValue":"<s:property value="peTrainee.peAreaByFkProvince.name"/>|<s:property value="peTrainee.peAreaByFkCity.name"/>|<s:property value="peTrainee.peAreaByFkPrefecture.name"/>"
		});
		region.load("region3");
	});
	

	function checknull(){
		var Phone = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,8}(\-\d{1,5})?$/;
		var Mobile = /^((\(\d{2,3}\))|(\d{3}\-))?1[3|5|8]\d{9}$/;
		var Email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
		var CardNo = /(^\d{15}$)|(\d{17}(?:\d|x|X))$/;
		
		if(document.getElementById("cardNo").value == "") {
			alert("请输入您的身份证号码！");
			return false;
		}
		if(!CardNo.test(document.getElementById("cardNo").value)) {
			alert("您的身份证号码输入错误！");
			return false;
		}
		if(document.getElementById("address").value == "") {
			alert("请输入您的通信地址！");
			return false;
		}
		if(document.getElementById("zip").value == "" || document.getElementById("zip").value.length < 6) {
			alert("请正确输入您住址所在地的邮编，共6位！");
			return false;
		}
		if(!checkZIP(document.getElementById("zip"))){
			return false;
		}
		if(document.getElementById("sltProvince3").value == "选择省份" || document.getElementById("sltCity3").value == "选择市区" || document.getElementById("sltCountryCity3").value == "选择县区") {
			alert("请选择您的户籍所在地！");
			return false;
		}
		if(document.getElementById("injob").value == "在职" && document.getElementById("workPlace").value == "") {
			alert("请输入您的工作单位！");
			return false;
		}
		if(document.getElementById("mobile").value == "") {
			alert("请输入您的手机号码！");
			return false;
		}
		if(!Mobile.test(document.getElementById("mobile").value)) {
			alert("您输入的手机号码格式错误！");
			return false;
		}
		if(document.getElementById("phoneHome").value != "" && !Phone.test(document.getElementById("phoneHome").value)) {
			alert("您输入的联系电话号码格式错误！");
			return false;
		}
		if(document.getElementById("email").value != "" && !Email.test(document.getElementById("email").value)) {
			alert("您输入的电子邮件格式错误！");
			return false;
		}
		var g = document.getElementById("photo").value;
		if(g != "" && g.substring(g.lastIndexOf(".")+1, g.length) !="jpg") {
			alert("您要上传的图片格式错误！");
			return false;
		}
		
	}
	
	function show(objID) {
		var tempObj;
		if(document.getElementById(objID)) {
			tempObj = document.getElementById(objID);
		}
		tempObj.style.display = "block";
	}
	
	function hide(objID) {
		var tempObj;
		if(document.getElementById(objID)) {
			tempObj = document.getElementById(objID);
		}
		tempObj.style.display = "none";
	}
	
	function checkCardNo(c_no){
		var CardNo = /(^\d{15}$)|(\d{17}(?:\d|x|X))$/;
		if(!CardNo.test(c_no.value)) {
			alert("您的身份证号码输入错误！");
			return false;
		}
	}
	function checkZIP(zip){
		var regZIP = /^\d{6}$/;
		if(!regZIP.test(zip.value)) {
			alert("邮政编码只能为6数字！");
			return false;
		}
		return true;
	}
	function checkMobile(mobileNum){
		var Mobile = /^((\(\d{2,3}\))|(\d{3}\-))?1[3|5|8]\d{9}$/;
		if(!Mobile.test(mobileNum.value)) {
			alert("您输入的手机号码格式错误！");
			return false;
		}
	}
	function checkPhone(phoneNum){
		var Phone = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,8}(\-\d{1,5})?$/;
		if(!Phone.test(phoneNum.value)){
			alert("您输入的联系电话号码格式错误！");
			return false;
		}
	}
	function checkEmail(emailValue){
		var Email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
		if(!Email.test(emailValue.value)) {
			alert("您输入的电子邮件格式错误！");
			return false;
		}
	}
	
</script>
</head>
<body bgcolor="#E0E0E0">
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <IFRAME NAME="top" width=100% height=200 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/top.jsp" scrolling=no allowTransparency="true"></IFRAME>
  <tr>
    <td height="13"></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="237" valign="top"><IFRAME NAME="leftA" width=237 height=520 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/left.jsp" scrolling=no allowTransparency="true"></IFRAME></td>
   <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17" align="left" class="twocentop">	<div class="content_title"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" />当前位置：个人信息</div>
	</td>
      </tr>
      <tr>
      	<td>&nbsp;</td>
      </tr>
   <tr align="center">
        <td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr>
          <td width="5%" align="center"></td>
            <td width="45%" align="left"> <strong style="color:red"><s:property value="peTrainee.trueName"/></strong> 的个人信息: </td>
          </tr>
        </table></td>
      </tr>
       <table width="75%" border="0" align="center" cellpadding="0" cellspacing="0" style="border:solid 2px #9FC3FF; padding:5px; margin:15px 0px 15px 0px;">
  <tr>
    <td>
    <form action="/entity/workspaceStudent/bzzstudent_modifyInfo.action" method="post" enctype="multipart/form-data" name="infoform" onsubmit="return checknull();">
    <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D7D7D7">
      <tr bgcolor="#ECECEC">
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"> <strong>报 名 号：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:property value="peTrainee.userName"/><input type="hidden" name="pid" value="<s:property value='peTrainee.id'/>"/></td>
        </tr>
        <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"> <strong>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:property value="peTrainee.trueName"/></td>
        </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi1"> <strong>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</strong></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"><input type="radio" name="gender" value="男" <s:if test='peTrainee.enumConstByGender==null or peTrainee.enumConstByGender.name=="男"'>checked</s:if> />男  &nbsp;  <input name="gender" type="radio" value="女" <s:if test='peTrainee.enumConstByGender.name=="女"'>checked</s:if> />女</td>
      </tr> 
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="STYLE1"> 所属班级：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <s:if test="peTrainee.peTrainingClass.name == null ">不详</s:if>
                                  		<s:property value="peTrainee.peTrainingClass.name"/> </td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="STYLE1">身份证号：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <input type="text" name="cardNo" onblur="checkCardNo(this)" value="<s:property value="peTrainee.cardNo"/>" maxlength=18/></td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="STYLE1"> 通信地址：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <input type="text" name="address" value="<s:property value="peTrainee.address"/>" maxlength=50/><font color="red" size="2px">50个字以内</font></td>
      </tr>
      <tr>                             		
        <td align="center" bgcolor="#E9F4FF" class="STYLE1"> 邮政编码：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <input type="text" name="zip" onblur="checkZIP(this)" value="<s:property value="peTrainee.zip"/>" maxlength=6/></td>  
      </tr>
      <tr>                             		
        <td align="center" bgcolor="#E9F4FF" class="STYLE1">户&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;籍：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; 
				<select id="sltProvince3" name="prov" style="width:120px"></select>
				<select id="sltCity3" name="city" style="width:120px"></select>
				<select id="sltCountryCity3" name="prefec" style="width:120px"></select>
        </td>  
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="STYLE1">是否在职：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"><input type="radio" onclick="show('cnt2')" name="injob" value="在职" <s:if test='peTrainee.enumConstByFlagInJob.name=="在职"'>checked</s:if> />是  &nbsp;  <input name="injob" type="radio" onclick="hide('cnt2')" value="待业" <s:if test='peTrainee.enumConstByFlagInJob==null or peTrainee.enumConstByFlagInJob.name=="待业"'>checked</s:if> />否&nbsp;
        	<div id="cnt2" style="width:280px; height:100px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td class="STYLE1"> 工作单位：<input type="text" name="workPlace" value="<s:property value='peTrainee.workPlace'/>" maxlength=50></td>
              	</tr>
              	<tr>
              		<td class="STYLE1" align="center"><font color="red" size="2px">50个字以内</font></td>
              	</tr>
              	<tr>
                  <td class="STYLE1">是否计划生育系统职工：<input type="radio" name="isJswJob" value="1" <s:if test='peTrainee.enumConstByFlagJswJob.code=="1"'>checked</s:if> />是&nbsp;&nbsp;<input type="radio" name="isJswJob" value="0" <s:if test='peTrainee.enumConstByFlagJswJob==null or peTrainee.enumConstByFlagJswJob.code=="0"'>checked</s:if> />否</td>
              	</tr>
                <tr><td><input type='button' onclick="hide('cnt2')" value="确定" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;">
                </td></tr>
                </table>
              </div>
        </td>
      </tr>
      <tr>                             		
        <td align="center" bgcolor="#E9F4FF" class="STYLE1"> 移动电话：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <input type="text" name="mobile" onblur="checkMobile(this)" value="<s:property value="peTrainee.mobile"/>" maxlength=20/><font color="red" size="2px">格式:13911111111,20个字以内</font></td>  
      </tr>
      <tr>                            		
        <td align="center" bgcolor="#E9F4FF" class="kctx_zi2"><DIV align="center" class="STYLE1">&nbsp;&nbsp; 联系电话：</DIV></td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <input type="text" name="phoneHome" onblur="checkPhone(this)" value="<s:property value="peTrainee.phoneHome"/>" maxlength=20/><font color="red" size="2px"> 格式:010-11111111,20个字以内</font></td>
     </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="STYLE1">&nbsp;&nbsp; 电子邮件：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <input type="text" name="email" onblur="checkEmail(this)" value="<s:property value="peTrainee.email"/>" maxlength=50/><font color="red" size="2px"> 50个字以内</font></td>
      </tr>
      <tr>
        <td align="center" bgcolor="#E9F4FF" class="STYLE1">&nbsp;&nbsp; 相&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;片：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2"> &nbsp; <input type="file" name="photo"/>&nbsp;&nbsp;<img src='<s:property value="peTrainee.photoLink"/>?dt=<s:property value="time"/>' width="60" height="80" onerror="this.src='/error/d4.jpg'"/>
        <font color="red" size="2px"> 请上传格式为jpg的图片</font>&nbsp;&nbsp;&nbsp;&nbsp;<font color="red" size="2px"><s:property value="uploadMsg"/></font></td>
      </tr>
    </table></td>
  </tr>
</table>
<table border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
	 <td width="20"><input type="submit" value="修 &nbsp;改" stype="cursor:hand"/>&nbsp;&nbsp;</td>
	<!--  <td><a href="/entity/workspaceStudent/bzzstudent_StudentInfo.action"><img src="/entity/bzz-students/images/two/an_05.gif" width="71" height="23" border="0"></a></td>  -->
  	 <td width="20"><input type="button" value="返 &nbsp;回" stype="cursor:hand" onclick="javascript:window.navigate('/entity/workspaceStudent/bzzstudent_StudentInfo.action')"/></td> 
  </tr>
</table>
    </table>
    </form>
    </td>
    <td width="13">&nbsp;</td>
  </tr>
</table>
<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no allowTransparency="true" align=center></IFRAME>
</td>
</tr>
</table>
</body>
<script type="text/javascript">
	var tempObj = document.getElementById('injob');
		if(tempObj.value == "在职") {
			document.getElementById('cnt2').style.display = "block";
		}
</script>
</html>