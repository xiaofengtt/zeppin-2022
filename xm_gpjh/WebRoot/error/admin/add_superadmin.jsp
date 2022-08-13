<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.config.PlatformConfig" %>
<%@ include file="./pub/priv.jsp"%>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals("null"))
			str = "";
			return str;
	}
%>


<html>
<head>
<link href="<%=request.getContextPath() %>/admin/css.css" rel="stylesheet" type="text/css">
<title>权限管理</title>
<link rel="stylesheet" type="text/css" media="all" href="../js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1">

<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script> 
</head>
<script language="javascript">

Ext.onReady(function(){
	
	 var submitDate = new Ext.form.DateField({
			        fieldLabel: '<@s.text name="毕业时间" />',           
			        name: 'peManager.graduationDate',
			        format:'Y-m-d',
			    //    applyTo:'gdate',
			        allowBlank:false,
			        anchor: '60%'   
			    });  
		
		submitDate.render('gdate');
			  

});


function alertPhone(x)
{
	for (var i=0;i<x.length;i++)
	{
		var k = x.charAt(i);
		if (((k<'0')||(k>'9')) && k!=',')
		{
			alert("电话号码只能由0~9或半角逗号组成！");
			document.change.mobilephone.focus();
			document.change.mobilephone.value =document.change.mobilephone.value.substring(0,document.change.mobilephone.value.length-1);
			return;
		}
	}
}
 function checkIdcard(idcard){
  var Errors=new Array("验证通过!","身份证号码位数不对!","身份证号码出生日期超出范围或含有非法字符!","身份证号码校验错误!","身份证地区非法!");
  var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}
  var Y,JYM;
  var S,M;
  var idcard_array = new Array();
  idcard_array = idcard.split("");
  if(area[parseInt(idcard.substr(0,2))]==null)
   return Errors[4];
  if(idcard.length==15){
      if ((parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){
        ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性
      }
      else{
        ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性
      }
      if(ereg.test(idcard)){
       return Errors[0];
       }
     else
       return Errors[2];
    }
 else if(idcard.length==18){
    if ( parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){
      ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//闰年出生日期的合法性正则表达式
    }
    else{
    ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//平年出生日期的合法性正则表达式
    }
    if(ereg.test(idcard)){
      S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9 + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5 + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8 + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4 + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2 + parseInt(idcard_array[7]) * 1 + parseInt(idcard_array[8]) * 6 + parseInt(idcard_array[9]) * 3 ;
      Y = S % 11;
      M = "F";
      JYM = "10X98765432";
      M = JYM.substr(Y,1);
      if(M = idcard_array[17])
       return Errors[0];
     else
        return Errors[3];
    }
    else
      return Errors[2];
   }
   else{
   	 return Errors[1];
  }
}

   
   function pageGuarding()
   {
   
    if (document.change.admin_id.value.length==0)
       {
           alert("请填写管理员ID！");
           document.change.admin_id.focus();
           return false;
       }
       
      var patrn=/^[0-9]{6}$/;
      if(patrn.exec(document.change.admin_id.value)){
      		alert('管理员帐号不能为6位数字,请重新填写!');
           document.change.admin_id.focus();
           return false;
      }
      
        if (document.change.password.value.length==0)
       {
           alert("请填写密码！");
           document.change.password.focus();
           return false;
       }
        if (document.change.admin_name.value.length==0)
       {
           alert("请填写管理员姓名！");
           document.change.admin_name.focus();
           return false;
       }
        if (document.change.mobilephone.value.length==0)
       {
           alert("请填写移动电话！");
           document.change.mobilephone.focus();
           return false;
       }
      
        if (document.change.phone.value.length==0)
       {
          // alert("请填写固定电话！");
           //document.change.phone.focus();
           //return false;
       }
        if (document.change.email.value.length==0)
       {
           //alert("请填写email！");
           //document.change.email.focus();
           //return false;
       }
       if (document.change.idcard.value.length==0)
       {
           alert("请填写身份证号！");
           document.change.idcard.focus();
           return false;
       }
       
        if (document.change.idcard.value.length!=0)
       {
       	var str = checkIdcard(document.change.idcard.value);       
          if (str!="验证通过!")
          {
          	alert(str);
           document.change.idcard.focus();
           document.change.idcard.select();
           return false;
      	 } 
       
       }
       
      document.change.action='<%=request.getContextPath()%>/sso/admin/managerOper_addUpdateManager.action';
      document.change.submit();
   }

</script>
<body>

<form action="" method="post" name="change">

<table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">
<input type=hidden name="type" value="SUPERADMIN">

<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%" colspan="2" align="center">添加总站管理员</td>
	
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="25%">登录帐号*(帐号不能为6位的数字):</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="text" id="admin_id" name="peManager.ssoUser.loginId" size="30" lang="30"></td>
</tr>

<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">登陆密码*:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="password" id="password" name="peManager.ssoUser.password" size="30" lang="30"></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">姓名*:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="text" id="admin_name" name="peManager.name" size="30" lang="30"></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">移动电话号码*:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="text" id="mobilephone" name="peManager.mobilePhone" size="30" onpropertychange="alertPhone(this.value)"></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">固定电话号码:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="text" id="phone" name="peManager.phone" size="30"></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">电子信箱:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="text" id="email" name="peManager.email" size="30"></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">性别:</td>
	<td bgcolor="#D4E4EB" class="zhengwen">
	<select name=peManager.gender>
		<option value="男">男</option>
		<option value="女">女</option>
	</select>
	</td>
</tr>
 
  <tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">身份证号*:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="text" id="idcard" name="peManager.idCard" size="30"></td>
</tr>

<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">毕业院校及专业层次:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="text" id="info" name="peManager.graduationInfo" size="30"></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">毕业时间:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><div id="gdate" class="gdate"/></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">职称:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="text" id="zhiCheng" name="peManager.zhiCheng" size="30"></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">从事成人教育时间:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="text" name="peManager.workTime" size="30"></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">通信地址及邮编:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="text" name="peManager.address" size="30"></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">状态:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><select name="peManager.enumConstByFlagIsvalid.code">
          <option selected value="1">有效</option>
          <option value="0">无效</option>
        </select></td>
</tr>



</table>
<br>
<div align="center" bgcolor="#D4E4EB" class="zhengwen"><a href="#" onclick="javascript:pageGuarding();">确定</a> &nbsp;&nbsp; <a href="javascript:history.back();">返回</a></div>

</form>
  
</body>
</html>