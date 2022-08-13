<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.entity.BasicRightManage,com.whaty.platform.sso.*"%>
<%@ page import="java.io.File,com.whaty.platform.entity.*,com.whaty.platform.entity.user.*,com.whaty.platform.entity.right.*" %>
<%@ page import="com.whaty.platform.config.PlatformConfig" %>
<%@ include file="./pub/priv.jsp"%>

<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>

<%
	PlatformFactory platformfactory = PlatformFactory.getInstance();
	BasicRightManage rightManage = platformfactory.creatBasicRightManage();
	Manager mana = rightManage.getAdmin(request.getParameter("id"));
	String login_id= java.net.URLDecoder.decode(fixnull(request.getParameter("login_id")),"UTF-8");
	String status=java.net.URLDecoder.decode(fixnull(request.getParameter("status")),"UTF-8");
	String pwd=fixnull(rightManage.getPassword(login_id));
	SsoFactory sso = SsoFactory.getInstance();
	SsoManagerPriv managerPriv = sso.creatSsoManagerPriv();
	managerPriv.getUser = 1;
	SsoManage ssoManage = sso.creatSsoManage(managerPriv);
	SsoUser ssoUser = ssoManage.getSsoUserByLogin(login_id);


%>
<html>
<head>
<link href="css.css" rel="stylesheet" type="text/css">
<title>权限管理</title>
<link rel="stylesheet" type="text/css" media="all" href="../js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1">
<script type="text/javascript" src="../js/calendar/calendar.js"></script>
<script type="text/javascript" src="../js/calendar/calendar-zh.js"></script>
<script type="text/javascript" src="../js/calendar/calendar-setup.js"></script>
</head>
<script language="javascript">
   
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
       
       

       
      
      document.change.submit();
   }

</script>
<body>

<form action="edit_superadminexe.jsp" method=post name="change">
<input type="hidden" name="admin_id"  value="<%=login_id%>">

<table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">


<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%" colspan="2" align="center">修改总站管理员</td>
	
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">登录帐号:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><%=mana.getLoginId()%></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">登陆密码:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="password" name="password" size="30" value="<%=pwd%>"></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">管理员姓名:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="text" name="admin_name" size="30" value="<%=fixnull(mana.getName())%>"></td>
</tr>

<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">移动电话号码:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="text" name="mobilephone" size="30" value="<%=fixnull(mana.getMobilePhone())%>"></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">固定电话号码:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="text" name="phone" size="30" value="<%=fixnull(mana.getRedundace().getRe2())%>"></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">电子信箱:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="text" name="email" size="30" value="<%=fixnull(ssoUser.getEmail()) %>"></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">性别:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><%=fixnull(mana.getRedundace().getRe1()) %>
	<!--  
	<select name=gender>
		<option value="男" <% if( "男".equals(mana.getRedundace().getRe1())) out.print("selected"); %>>男</option>
		<option value="女" <% if( "女".equals(mana.getRedundace().getRe1())) out.print("selected"); %>>女</option>
	-->	
	</select>
	</td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">出生日期:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><%=fixnull(mana.getRedundace().getRe3())%>
	<!--  <input type=text name=birthday id="birthday" size=15 readonly  value="<%=fixnull(mana.getRedundace().getRe3())%>">&nbsp;&nbsp;-->
              
	</td>
</tr>

<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">身份证号:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="text" name="idcard" size="30" value="<%=fixnull(mana.getRedundace().getRe9())%>"></td>
</tr>

<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">毕业院校及专业层次:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="text" name="graduate_sch" size="30" value="<%=fixnull(mana.getRedundace().getRe4())%>"></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">毕业时间:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="text" name="graduate_date" size="30" value="<%=fixnull(mana.getRedundace().getRe5())%>"></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">职称:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="text" name="title" size="30" value="<%=fixnull(mana.getRedundace().getRe6())%>"></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">从事成人教育时间:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="text" name="work_time" size="30" value="<%=fixnull(mana.getRedundace().getRe7())%>"></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">通信地址及邮编:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><input type="text" name="address" size="30" value="<%=fixnull(mana.getRedundace().getRe8())%>"></td>
</tr>
<tr>
	<td bgcolor="#D4E4EB" class="zhengwen" width="20%">状态:</td>
	<td bgcolor="#D4E4EB" class="zhengwen"><select name="status">
          <option selected value="1" <% if(status.equals("1")){ out.println("selected");}%>>有效</option>
          <option value="0" <% if(status.equals("0")){ out.println("selected");}%>>无效</option>
        </select></td>
</tr>



</table>
<br>
<div align="center" bgcolor="#D4E4EB" class="zhengwen"><a href="#" onclick="javascript:pageGuarding();">确定</a> &nbsp;&nbsp; <a href="right_admin.jsp">返回</a></div>

</form>
<!--  
<script type="text/javascript">
    Calendar.setup({
        inputField     :    "birthday",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_a",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
	</script>
-->	
</body>
</html>