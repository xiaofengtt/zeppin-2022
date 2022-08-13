<%@ page language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title></title>
    <link href="/web/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function myonload(){
<s:if test="loginErrMessage!=null">
	// window.document.loginform.loginType.value="<s:property value='loginType'/>";
	window.document.loginform.loginId.value="<s:property value='loginId'/>";
	window.top.alert("<s:property value='loginErrMessage'/>");
</s:if>
}
</script>
  </head>
  

<body onload="myonload()">
	<form method="POST" name="loginform" action="/sso/login_login.action" target="_self" >
		  <table width="239" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="8"><img src="/web/images/index_31.jpg" width="8" height="201" /></td>
              <td  valign="top">
			  <table width="100%" height="201" border="0" cellpadding="0" cellspacing="0" bgcolor="#ECF5FE">
                <tr>
                  <td height="36" style="background-image:url(/web/images/index_33.jpg);background-repeat:repeat-x;height:36px;"  align="left"><img src="/web/images/index_37.jpg" width="48" height="21" class="loginimg" /></td>
                </tr>
                <tr>
                  <td valign="top" height="119"  style=" padding-top:3px;">
				  <table width="223" border="0" cellpadding="0" cellspacing="3" style="margin-top:5px;">
                    <tr>
                      <td width="12%"  class="leftround"><img src="/web/images/round.jpg" width="5" height="5" /></td>
                      <td width="18%" class="fontl">用户名</td>
                      <td width="70%" align="left"><input name="loginId" type="text" class="text" />                      </td>
                    </tr>
                    <tr>
                      <td width="12%"  class="leftround"><img src="/web/images/round.jpg" width="5" height="5" /></td>
                      <td width="18%" class="fontl">密&nbsp; 码</td>
                      <td width="70%" align="left"><input name="passwd" type="password" class="text" />                      </td>
                    </tr>
                    <tr>
                      <td width="12%"  class="leftround"><img src="/web/images/round.jpg" width="5" height="5" /></td>
                      <td width="18%" class="fontl">验证码</td>
                      <td width="70%"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="50%" align="left"><input name="authCode" type="text" class="textshort" /></td>
                            <td width="50%"><img src="/sso/authimg" width="59" height="20" /></td>
                          </tr>
                      </table></td>
                    </tr>
                  <!--   <tr>
                      <td width="12%" height="20"  class="leftround"><img src="/web/images/round.jpg" width="5" height="5" /></td>
                      <td width="18%" class="fontl">分&nbsp; 类</td>
                      <td width="70%" align="left">
                      <select name="loginType" class="texts">	
                      		<option value="0" >&nbsp;学&nbsp;生 </option>
				            <option value="1">&nbsp;教&nbsp;师 </option>
				            <option value="2">&nbsp;分站管理员</option>
				            <option value="3">&nbsp;管理员</option>
           
                      </select>                      </td>
                    </tr>  -->
                  </table></td>
                </tr>
                <tr>
                  <td valign="bottom" height="46"  style="background-image:url(/web/images/index_42.jpg);  background-repeat:repeat-x; height:46px;" align="center" >
				  <table width="90%" height="43" border="0" cellspacing="0" cellpadding="0" style="margin-top:3px;">
                    <tr>
                      <td ><INPUT name=Submit type=image src="/web/images/index_43.jpg" width="45" height="19" border="0" /></td>
                      <td><a href="/web/help/get_pwd.jsp" target="_blank"><img src="/web/images/index_55.jpg" width="69" height="19" border="0" /></a></td>
                      <td><a href="/entity/first/firstRegister_turnToAdd.action" target="_blank"><img src="/web/images/index_47.jpg" width="69" height="19" border="0" /></a></td>
                    </tr>
                  </table></td>
                </tr>
              </table></td>
              <td width="8"><img src="/web/images/index_34.jpg" width="8" height="201" /></td>
            </tr>
          </table>
          </form>
</body>
</html>
