<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<jsp:directive.page import="com.whaty.platform.sms.SmsManagerPriv"/>
<%@page import="com.whaty.platform.database.oracle.standard.entity.user.OracleSiteManager"%>
<%@ include file="/entity/manager/pub/priv.jsp"%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<script language=javascript src="/entity/manager/js/check.js"></script>
<script language=javascript src="/entity/manager/js/settime.js" type="text/javascript" charset="gb2312"></script>
<script>

function showStuButton(){
 		document.getElementById("site_button").disabled=false;
}



function clickstu()
	{
		var stuObj;
		var siteObj;
		if(document.getElementById("studentS"))
		{
			stuObj = document.getElementById("studentS");
		}
		if(document.getElementById("subadmins"))
		{
			siteObj = document.getElementById("subadmins");
		}
		if(stuObj.checked)
		{
			document.getElementById("site_button").disabled=false;
			document.getElementById("grade_button").disabled=false;
			document.getElementById("major_button").disabled=false;
			document.getElementById("edutype_button").disabled=false;
		}
		else if(siteObj.checked)
		{
			document.getElementById("site_button").disabled=false;
			document.getElementById("grade_button").disabled=true;
			document.getElementById("major_button").disabled=true;
			document.getElementById("edutype_button").disabled=true;
		}
		else
		{
			document.getElementById("site_button").disabled=true;
			listSelect("site_id");
			document.getElementById("grade_button").disabled=true;
			document.getElementById("major_button").disabled=true;
			document.getElementById("edutype_button").disabled=true;
		}
	}


function showDiv(objID)
	{
		var tempObj;
		if(document.getElementById(objID))	tempObj = document.getElementById(objID);
		hideOther(objID.substring(3,objID.length));
		if(tempObj.style.display == "none")	tempObj.style.display = "block";
		else	tempObj.style.display = "none";
		
	}
	
	function listSelectSite() {
		var form = document.forms['sms'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'trainingType')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.site_all.checked;
				}
			}
		}
		return true;
	}
	
	function listSelectSiteManager(){
		var form = document.forms['sms'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'siteManager_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.siteManager_all.checked;
				}
			}
		}
		return true;

	}
	
	function listSelectMajor() {
		var form = document.forms['sms'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'major_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.major_all.checked;
				}
			}
		}
		return true;
	}
	
	function listSelectEdutype() {
		var form = document.forms['sms'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'level_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.edutype_all.checked;
				}
			}
		}
		return true;
	}
	
	function listSelectGrade() {
		var form = document.forms['sms'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'grade_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.grade_all.checked;
				}
			}
		}
		return true;
	}
	
	function hideOther(par)
	{
		for(var i=1;i<6;i++)
		{
			if(document.getElementById("cnt"+String(i)) && document.getElementById("cnt"+String(i)).style.display == "block" && i!=par)
			{
				document.getElementById("cnt"+String(i)).style.display = "none"
			}
		}
	}



	function doCommit() {
		if(document.sms.mobilePhone.value == "") {
		    var training_type = false;
		    var types = document.getElementsByName("trainingType");
		    for(var i = 0; i < types.length; i++){
		    	if(types[i].checked = true){
		    	 training_type = true;
		    	 break;
		    	 }
		    }
		    if(!training_type){
		    	alert("您没有选择任何学生，且没有输入任何号码，提交不成功")
		    	document.sms.mobilePhone.focus();
				return;
		    }
		}
		if(!alertPhone(document.sms.mobilePhone.value)){
			document.sms.mobilePhone.focus();
			return;
		}
		if(document.sms.msgContent.value == "") {
			alert("短信内容不能为空!");
			document.sms.msgContent.focus();
			return;
		}
		if(parseInt(document.sms.type.selectedIndex)==1&&document.sms.setTime.value == ""){
		  alert("定置时间不能为空!");
			document.sms.setTime.focus();
			return;
		}
		document.forms["sms"].submit();
	}
	
	function strLength(str){

			var re,resultStr;

			re=new RegExp("[^\x00-\xff]","g");

			resultStr=str.replace(re,"aa");

			return resultStr.length;

	}
	
	function textCounter(field, maxlimit) {
	    var lengthNum =  field.value.length;
		document.sms.remLen.value = lengthNum;
		if(parseInt(lengthNum)>maxlimit){
		  document.sms.infos.style.display="block";
		  document.sms.infos.value ="请将短信内容控制在62个字符以内，否则将被拆分发送（一个汉字、英文、数字或标点符号都相当于一个字符）";
		}else{
		  document.sms.infos.value ='';
		}
	}
	function DBC2SBC(str)
	{
		 var result = '';
		 for (i=0 ; i<str.length; i++)
		 {
		  code = str.charCodeAt(i);//获取当前字符的unicode编码
		  if (code >= 65281 && code <= 65373)//在这个unicode编码范围中的是所有的英文字母已经各种字符
		  {
		   result += String.fromCharCode(str.charCodeAt(i) - 65248);//把全角字符的unicode编码转换为对应半角字符的unicode码
		  }else if (code == 12288)//空格
		  {
		   result += String.fromCharCode(str.charCodeAt(i) - 12288 + 32);
		  }else
		  {
		   result += str.charAt(i);
		  }
		 }
		 return result;
	}
	
	function isIntPhone(x)
	{
	var isInt= "";
	for (var i=0;i<x.length;i++)
	{
		var k = x.charAt(i);
		if (((k>='0')&&(k<='9')) || k==',')
		{
			isInt=isInt + k;
		}
	}
	return isInt;
	}
	
	function alertPhone(x)
	{ 
		var flag = false;
		var strs = x.split(",");
		var value="";
		for(var i=0;i< strs.length;i++  ){
		    if(strs[i].indexOf("|")>=0){
		    value +=strs[i].substring(0,strs[i].indexOf("|"))+",";
		    }else{
		    	value +=strs[i]+",";
		    }
		}
		if(null!=value && ""!=value && value.length>0)
		value = value.substring(0,value.length-1);
		var x = value;
		
		for (var i=0;i<x.length;i++)
		{
			var k = x.charAt(i);
			if (((k<'0')||(k>'9')||(k='')) && k!=',')
			{
				flag = true;
			}
		}
		if(flag){
			alert("电话号码只能由0~9或半角逗号组成！");
			return false;
		}
		return true;
	}
	
	function alertPhoneX(x)
	{ 
		var str = "";
		var flag = false;
		var index = 0;
		for (var i=0;i<x.length;i++)
		{
			var k = x.charAt(i);
			if (((k<'0')||(k>'9')||(k='')) && k!=',')
			{
				flag = true;
				str += x.substring(index,i);
				index = i + 1;
			}
		}
		if(index < x.length){
				str += x.substring(index);
			}
		if(flag){
			alert("电话号码只能由0~9或半角逗号组成！");
			document.sms.mobilePhone.value=str;
			return;
		}
	}
	
	function charChange(){
		document.sms.mobilePhone.value=isIntPhone(DBC2SBC(document.sms.mobilePhone.value));
	}
	
	function CtoH(obj)
	{ 
	var str=obj.value;
	var result="";
	for (var i = 0; i < str.length; i++)
	{
	if (str.charCodeAt(i)==12288)
	{
	result+= String.fromCharCode(str.charCodeAt(i)-12256);
	continue;
	}
	if (str.charCodeAt(i)>65280 && str.charCodeAt(i)<65375)
	result+= String.fromCharCode(str.charCodeAt(i)-65248);
	else result+= String.fromCharCode(str.charCodeAt(i));
	} 
	obj.value=result;
	} 
	
	
	/* function changeMobile(flag){
	var values = document.sms.mvalues.value ;
	if(flag !=null && flag == "0"){
		document.sms.mobilePhone.value = values;
		return;
	} else if (flag=="1"){
	   if(document.sms.mobilePhone.value != null){
	   var vals = document.sms.mobilePhone.value.split(",");
	   var mobiles ="";
	    for(var i = 0;i<vals.length;i++){
	    	var vs = vals[i].split("|");
		    mobiles += vs[0]+",";
		}
		if(mobiles.length>0) mobiles.substring(0,mobiles.length-1);
		document.getElementById("mobilePhone").vlaue = mobiles;
		alert(document.getElementById("mobilePhone").vlaue);
	    return;
	    }
	}
	} */
  
	
	
</script>
<script   language="JavaScript">   
  <!--   
  <!--   
  function   MM_reloadPage(init)   {     //reloads   the   window   if   Nav4   resized   
      if   (init==true)   with   (navigator)   {if   ((appName=="Netscape")&&(parseInt(appVersion)==4))   {   
          document.MM_pgW=innerWidth;   document.MM_pgH=innerHeight;   onresize=MM_reloadPage;   }}   
      else   if   (innerWidth!=document.MM_pgW   ||   innerHeight!=document.MM_pgH)   location.reload();   
  }   
  MM_reloadPage(true);   
  //   -->   
    
  function   MM_findObj(n,   d)   {   //v4.0   
      var   p,i,x;     if(!d)   d=document;   if((p=n.indexOf("?"))>0&&parent.frames.length)   {   
          d=parent.frames[n.substring(p+1)].document;   n=n.substring(0,p);}   
      if(!(x=d[n])&&d.all)   x=d.all[n];   for   (i=0;!x&&i<d.forms.length;i++)   x=d.forms[i][n];   
      for(i=0;!x&&d.layers&&i<d.layers.length;i++)   x=MM_findObj(n,d.layers[i].document);   
      if(!x   &&   document.getElementById)   x=document.getElementById(n);   return   x;   
  }   
    
  function   MM_showHideLayers()   {   //v3.0   
      var   i,p,v,obj,args=MM_showHideLayers.arguments;   
      for   (i=0;   i<(args.length-2);   i+=3)   if   ((obj=MM_findObj(args[i]))!=null)   {   v=args[i+2];   
          if   (obj.style)   {   obj=obj.style;   v=(v=='show')?'block':(v='hide')?'none':v;   }   
          obj.display=v;   }   
  }   

  
  function onSelectChange(control){
       if(parseInt(control.selectedIndex)==1){
         MM_showHideLayers('menu','','show');
       }else{
         MM_showHideLayers('menu','','hide');
          document.sms.setTime.value ='';
       }
  }
  //-->   
  </script>   


</head>
<body leftmargin="0" topmargin="0" class="scllbar">
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">短信发送</div id="zlb_title_end"></td>
  </tr>
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 
	<form name='sms' action='/entity/information/smsNewAction_sms_send.action' method='post' class='nomargin' onSubmit="">
        <div class="cntent_k" id="zlb_content_start">
          <table width="90%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">发送对象：</span></td>
              <td>
               <input name="mobilePhone" type="text" class="selfScale" onblur="alertPhone(this.value)" onKeyUp="CtoH(this);"> 	<!-- onblur="charChange()"--> &nbsp;&nbsp; 
              	<input name="mvalues" type="hidden"  value="" />
              	<span class="link" onClick="window.open('/entity/manager/sms/mobile_batch_add.jsp','','width=500,height=200')">批量导入号码</span>
              </td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"></td>
              <td>	
          	 <!-- <span class="name" style="cursor:hand" onClick="window.open('/entity/information/studentPhoneView.action?search=true','','width=950,height=600,scrollbars=yes')">学生库</span>&nbsp; -->
          	 
            <span class="name" style="cursor:hand" onClick="showStuButton();">学生库</span>&nbsp;

<%--              	<span class="name" style="cursor:hand" onclick="window.open('unregstudent_list.jsp','','width=800,height=600,scrollbars=yes')">未注册学生库</span>&nbsp;--%>
<!--               	<span class="name" style="cursor:hand"  onclick="window.open('teacher_list.jsp','','width=900,height=600,scrollbars=yes')">教师库</span>&nbsp; -->
              	<span class="name" style="cursor:hand"  onclick="window.open('/entity/information/smsNewAction.action','','width=900,height=600,scrollbars=yes')">管理员</span>&nbsp;
<!--               	<span class="name" style="cursor:hand"  onclick="window.open('site_manager_list.jsp','','width=900,height=600,scrollbars=yes')">分站管理员</span> -->
              </td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">培训计划类型：</span></td>
              <td>
			<input id="site_button" type="button" name="site" onClick="showDiv('cnt1')" value="培训计划类型" disabled>&nbsp;
			<div id="cnt1" style="width:350px; height:180px; position:absolute; border:1px solid #006699; background-color:#EFF3F5; padding=6px; overflow:auto;display:none">
                <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                 <tr>
                    <td height="22"><input id="site_id_all" type="checkbox" name="trainingType_all" value="" onclick=listSelectSite();>全选</td>
                </tr>
<s:iterator id="type" value="trainingType">
                  <tr>
                    <td height="22"><input id="site_id_check" type="checkbox" name="trainingType" value="<s:property value="#type.id"/>"><s:property value="#type.name"/></td>
                    </tr>
</s:iterator>
                </table>
              </div>
              </td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="top" style='white-space: nowrap;'><span class="name">短信类型：</span></td>
              <td style='white-space: nowrap;'>
                <SELECT name="type" onChange="onSelectChange(this)">
                  <!-- OPTION value='2'>系统短信</OPTION-->
<s:iterator value="smsType" id="type">
                  <OPTION value='<s:property value="#type.id"/>'><s:property value="#type.name"/></OPTION>
</s:iterator>
                </SELECT>
                <div  id="menu" style="left:300px;top:95px;width:100px;height:1px;z-index:1000;display:none">   
               时间设定:<INPUT name="setTime" value="" onFocus="calendartime()">
                </div>
              </td>
            </tr>
            <tr>
             <td valign="top" style='white-space: nowrap;'><span class="name">显示姓名：</span></td>
              <td style='white-space: nowrap;'>
                 <input type="radio" name ="isorName" value="1" checked="checked" />是
                 <input type="radio" name ="isorName" value="0" />否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（是，则在最终发送的短信内容前加上收信人姓名；否，则不加收信人姓名）
              </td>
            </tr> 
            
            <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">短信内容：</span></td>
              <td>
              <textarea name="msgContent" cols="40" rows="8" onPropertyChange="textCounter(sms.msgContent,62)" ></textarea> &nbsp;
              <br>字符数:<input name=remLen value=0 readonly type=text size=4 maxlength=3 style="background-color: #eaffe0; border: 0; color: red"><br/>
              <input name=infos  readonly type=text size="100" style="background-color: #eaffe0; border: 0; color: gray;display: none">
              </td>
            </tr>
            
          </table>
      </div>
</form>
    </td>
  </tr>
  <tr> 
    <td align="center" id="pageBottomBorder_zlb"><div class="content_bottom"> <table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0"><tr>
          <td align="center" valign="middle"><a href="#" title="发送" class="button"><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onClick="doCommit()" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'"><span class="text">发送</span></span></a></td>
          <td align="center" valign="middle"><a href="#" title="返回" class="button"><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onClick="history.back();" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'"><span class="text">返回</span></span></a></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>