<%@ page language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page import="java.util.*,java.text.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生殖健康咨询师培训网</title>
    <link href="/web/css/css.css" rel="stylesheet" type="text/css" />
    <script language="JavaScript">
function check()
{

if (document.getElementById('cardNo').value=="") 
{
	 alert("请输入您的证件号码!")
	 document.form1.cardNo.focus();
	return false;
}

if (document.form1.pwd.value=="")
{
    alert("请输入您的密码!")
	document.form1.pwd.focus();
	return false;
} 
return true;
}
</script>

  </head>
  

<body >

<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" class="newsbg">
  <tr>
    <td>
	<table width="703" height="100%" border="0" align="center" cellpadding="0" cellspacing="0" valign="top">
  <tr>
    <td  width="703" height="210" valign="top"><object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="703" height="163">
      <param name="movie" value="/web/news/images/02.swf" />
      <param name="quality" value="high" />
      <embed src="/web/news/images/02.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="703" height="163"></embed>
    </object>
      <table width="703" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td  valign="top" width="703" height="11"><img src="/web/news/images/1_05.jpg" width="703" height="11" /></td>
        </tr>
        <tr>
          <td valign="top" width="670" height="100%"  style="background:url(/web/news/images/1_07.jpg); background-repeat:repeat-y;" >
		  <table width="670" border="0" cellspacing="0" cellpadding="0" style="margin:2px 15px;">
		  <tr>
			<td class="positions">当前位置：<a href="/entity/first/firstInfoNews_toIndex.action"><font class="positions">首页</font></a></td>
		  </tr>
		  <tr>
			<td align="center">
			<table width="670" border="0" cellpadding="0" cellspacing="0" background="/web/news/images/1_12.jpg">
              <tr>
                <td width="4%" align="left"><img src="/web/news/images/1_1.jpg" width="18" height="30" /></td>
                <td width="96%" class="newsbtitle" align="left">报名信息查询与修改（登陆）</td>
              </tr>
            </table></td>
		  </tr>
		  <tr>
			<td style="padding-top:5px;">
			<s:if test="loginErrMessage==null">
				<form method="POST" name="form1" action="/entity/first/firstStudentInfo_turnToList.action" onSubmit="return check();">
        				  <table width="523" border="0" align="center" cellpadding="0" cellspacing="3" style="margin-top:5px;">
                    <tr>
                      <td width="25%"  class="leftround"><img src="/web/images/round.jpg" width="5" height="5" /></td>
                      <td width="21%" class="fontl">证件号码</td>
                      <td width="54%" align="left"><input id="cardNo" name="student.cardNo" type="text" class="text" />                      </td>
                    </tr>
                    <tr>
                      <td width="25%"  class="leftround"><img src="/web/images/round.jpg" width="5" height="5" /></td>
                      <td width="21%" class="fontl">密码</td>
                      <td width="54%" align="left"><input id="pwd" name="student.password" type="password" class="text" />                      </td>
                    </tr>                    
                    <tr>
                      <td height="20" colspan="3"  class="leftround">                    <table width="100%" align="center">
                    <tr>
                    <td width="50%" align="right"><input  

type="submit" value="提交"/>
                    </td>
                    <td width="50%" align="left"><input  

type="reset" value="重置"/>
                    </td>
                    </tr>
                    </table></td>
                      </tr>
                  </table>

                      
			<p align="center"><span style="color:#777777; font-size:12px; font-family:'宋体'; line-height:27px;">
			【<span><a href="#" onClick="javascript:window.close()" style="color:#FE7E01; text-decoration:none;">关闭</a></span>】
			</span></p>
			</form> 
			 </s:if>
			 </td>
		  </tr>
		</table>

		  </td>
        </tr>
        <tr>
          <td  valign="top" width="703" height="14"><img src="/web/news/images/1_08.jpg" width="703" height="14" /></td>
        </tr>
      </table></td>
  </tr>
  
  <tr>
    <td width="703"  height="68" valign="top" style="background-image:url(/web/news/images/1_11.jpg); background-repeat:repeat-x;" align="center" class="banquan">© GDOU.COM  华南师范大学网络教育学院<br />
院办:020-85210992 传真:020-85210991<br />
粤ICP备05028605号
	
	</td>
  </tr>
</table>
	</td>
  </tr>
</table>

</body>
</html>
