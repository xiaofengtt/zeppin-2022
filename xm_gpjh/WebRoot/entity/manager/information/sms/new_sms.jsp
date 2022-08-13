<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>


 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="../../css/admincss.css" rel="stylesheet" type="text/css">
<script language=javascript src="../../js/check.js"></script>
<script language=javascript src="../../js/settime.js" type="text/javascript" charset="gb2312"></script>
<script>
	function doCommit() {
		if(document.sms.mobilePhone.value == "") {
			alert("手机号不能为空!");
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
	var str = "";
	function alertPhone(x)
	{ 
		for (var i=0;i<x.length;i++)
		{
		
			var k = x.charAt(i);
			if (((k<'0')||(k>'9')||(k='')) && k!=',')
			{
			   
				alert("电话号码只能由0~9或半角逗号组成！");
				document.sms.mobilePhone.value=str;
				return;
			}
		}
		str = x;
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
          if   (obj.style)   {   obj=obj.style;   v=(v=='show')?'visible':(v='hide')?'hidden':v;   }   
          obj.visibility=v;   }   
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
	<form name='sms' action='/entity/information/peSms_save.action' method='post' class='nomargin' onSubmit="">
        <div class="cntent_k" id="zlb_content_start">
          <table width="90%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">手机号：</span></td>
              <td>
              	<input name="mobilePhone" type="text" class="selfScale" onpropertychange="alertPhone(this.value)" onKeyUp="CtoH(this);"><!-- onblur="charChange()"--> &nbsp;&nbsp; 
              	<span class="link" onClick="window.open('mobile_batch_add.jsp','','width=500,height=200')">批量导入号码</span>
              </td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"></td>
              <td>
              	<span class="name" style="cursor:hand" onClick="window.open('/entity/information/studentPhoneView.action?search=true','','width=800,height=600,scrollbars=yes')">学生库</span>&nbsp;
<%--              	<span class="name" style="cursor:hand" onclick="window.open('unregstudent_list.jsp','','width=800,height=600,scrollbars=yes')">未注册学生库</span>&nbsp;--%>
              	<span class="name" style="cursor:hand"  onclick="window.open('/entity/information/teacherPhoneView.action','','width=800,height=600,scrollbars=yes')">教师库</span>&nbsp;
              	<span class="name" style="cursor:hand"  onclick="window.open('/entity/information/managerPhoneView.action','','width=800,height=600,scrollbars=yes')">总站管理员</span>&nbsp;
              	<span class="name" style="cursor:hand"  onclick="window.open('/entity/information/siteManagerPhoneView.action','','width=800,height=600,scrollbars=yes')">分站管理员</span>
              </td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="top" style='white-space: nowrap;'><span class="name">短信类型：</span></td>
              <td style='white-space: nowrap;'>&nbsp;&nbsp;&nbsp;普通短信
              <input type="hidden" name="type" value="0"/>
              <!--  <SELECT name="type" onChange="onSelectChange(this)">
                  <OPTION value='0' selected>普通短信</OPTION>
                  <OPTION value='1'>定时短信</OPTION>
                  <!-- OPTION value='2'>系统短信</OPTION-->
              <!--   </SELECT>
                <div  id="menu" style="position:absolute;left:300px;top:95px;width:100px;height:1px;z-index:1000;visibility:hidden">   
                  &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;
                  时间设定:<INPUT name="setTime" value="" onFocus="calendartime()">
                </div>-->
              </td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="top"><span class="name">短信内容：</span></td>
              <td>
              <textarea name="msgContent" cols="40" rows="8" onPropertyChange="textCounter(sms.msgContent,62)" ></textarea> &nbsp;
              <br>字符数:<input name=remLen value=0 readonly type=text size=4 maxlength=3 style="background-color: #eaffe0; border: 0; color: red">
              <input name=infos  readonly type=text size=80 style="background-color: #eaffe0; border: 0; color: gray">
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
          
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>