<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>生殖健康咨询师培训网</title>
<link href="css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
.STYLE2 {color: #004DB2}
-->
</style>

<script language="javascript">
function checkForm(){
	if(document.getElementById('searchVal').value=='请输入关键字' || document.getElementById('searchVal').value==''){
		alert('请输入关键字');
		return false;
	}else
		return true
}
function formtest(){
	if(document.loginform.loginId.value==''){
		alert("请输入用户名！");
		document.loginform.loginId.focus();
		return false;
	}
	if(document.loginform.passwd.value==''){
		alert("请输入密码！");
		document.loginform.passwd.focus();
		return false;
	}
	if(document.loginform.authCode.value==''){
		alert("请输入验证码！");
		document.loginform.authCode.focus();
		return false;
	}
	return true;
}
function checkRegsitForm(){
	var logID = document.getElementById('logID').value;
	var reglogID = /^\w{3,15}[A-Za-z0-9]$/;
	if(!reglogID.test(logID)) {
		alert("用户名：用户名输入错误！");
		return false;
	}
	
	var password = document.getElementById('password').value;
	var regPassword = /^[A-Za-z0-9!@#$%^&*()]{4,16}$/;
	if(!regPassword.test(password)) {
		alert("密码：密码输入错误！");
		return false;
	}
	
	var passwordCheck = document.getElementById('rePassword').value;
	if(passwordCheck != password) {
		alert("确认密码：两次输入的密码不同！");
		return false;
	}
	
	var realName = document.getElementById('realName').value;
	if(realName == "") {
		alert("姓名：姓名不能为空！");
		return false;
	}
	
	var cardno = document.getElementById('cardId').value;
	var regCardno =/(^\d{15}$)|(\d{17}(?:\d|x|X))$/;
	if(cardno == "" || !regCardno.test(cardno)) {
		alert("身份证：身份证号码输入错误！");
		return false;
	}
	var email = document.getElementById('email').value;
	var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
	if(email==""||!emailReg.test(email)){
	alert("注册邮箱：邮箱填写有误！");
	return false;
		}
	
}
</script>

</head>

<body>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="98" align="center" valign="bottom" background="images/bg.jpg"><table width="977" cellspacing="0" cellpadding="0">
      <tr>
        <!--  <td height="22" colspan="2" align="right"><img src="images/gc.jpg" width="44" height="14" />&nbsp;&nbsp;</td>-->
        </tr>
		<tr>
        <td width="378" align="left" class="logo"><font size="4" style="font-family: 华文新魏">　生殖健康咨询师培训网</font></td>
      </tr>
      <tr>
        <td height="15" colspan="2"></td>
        </tr>
      <tr>
        <td colspan="2"><table width="100%" cellspacing="0" cellpadding="0">
          <tr>
            <td width="53" height="26" align="center" valign="bottom" background="images/index_1.jpg"><a href="/">首 页</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=PTJS">平台介绍</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=XWZX">新闻中心</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=PXDT">培训动态</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=ZCFG">政策法规</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=KCTX">课程体系</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=XXZY">学习资源</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=PXSFC">培训师风采</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="images/top_1.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=ZXBM">在线报名</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=CJWT">常见问题</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=XZZX">下载中心</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=BKZTC">报考直通车</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=SJTJ">书籍推荐</a></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="6" bgcolor="#004EB2"></td>
    <td bgcolor="#004EB2"></td>
    <td bgcolor="#004EB2"></td>
  </tr>
  <tr>
    <td width="13"></td>
    <td><img name="two_r3_c2" src="images/two/two_r3_c2.jpg" width="977" height="148" border="0" id="two_r3_c2" alt="" /></td>
    <td width="12"></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="13"></td>
    <td height="36" valign="middle" style="border-left:solid 1px #014CB2; border-right: solid 1px #014CB2; background:#EDEDED;"><table width="100%" cellspacing="0" cellpadding="0">
      <form id="searchForm" name="searchForm" method="post" action="/entity/first/pageDispatch_search.action" onSubmit="return checkForm()">
        <tr>
          <td width="1%"></td>
          <td width="18%" align="right" valign="middle"><input type="text" name="searchVal" value="请输入关键字" style="color:#CCCCCC; border:solid 1px #7b7b7b; border-right:0; height:17px; width:160px;" /></td>
          <td width="33%" align="left" valign="middle"><input type="image" name="imageField" src="images/search.jpg" /></td>
          <td width="47%" align="right">首 页 &gt; 在线报名</td>
          <td width="1%" align="right"></td>
        </tr>
      </form>
    </table></td>
    <td width="12"></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="13" rowspan="2"></td>
    <td width="228" height="189" align="center" valign="top" background="images/two/two_r6_c2.jpg"><table width="220" cellspacing="0" cellpadding="0">
      <tr>
			<td height="10"></td>
		</tr>
		<tr>
            <td height="110" align="center" valign="bottom" background="images/index/index_r3_c6.jpg"><table width="82%" cellspacing="0" cellpadding="0">
              <form id="loginform" name="loginform" method="post" action="/sso/login_login.action">
                <tr>
                	<td height="30"></td>
                </tr>
                <tr>
                  <td width="29%" height="30" style="font-size:12px">用户名：<br/></td>
                  <td width="71%" align="right"><input name="loginId" type="text"  style="width:122px; height:18px; border: solid 1px #B8C5CE;"/></td>
                </tr>
                <tr>
                  <td height="30" style="font-size:12px">密    码：</td>
                  <td align="right"><input name="passwd" type="password"  style="width:122px; height:18px; border: solid 1px #B8C5CE;"/></td>
                </tr>
                <tr>
                 <td height="30" style="font-size:12px;">验证码：</td>
                 <td align="right"><input type="text" name="authCode" style="width:62px; height:18px; border: solid 1px #B8C5CE;"><img src="/sso/authimg"  border="0" align="absmiddle"/></td>
               </tr>
                <tr>
                  <td height="25"></td>
                  <td align="right"><input type="image" name="imageField" src="images/logins.jpg" />
                      <a href="/entity/first/pageDispatch_dispatch.action?type=ZXBM"><img src="images/reds.jpg" border="0" width="42" height="21" /></a></td>
                </tr>
              </form>
            </table></td>
          </tr>
          <tr>
            <td height="5"></td>
          </tr>
          <tr>
            <td height="63" align="center" background="images/indexbg.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=ZXBM"><img src="banner/ff.jpg" border="0" width="205" height="49" /></a></td>
          </tr>
    </table></td>
    <td width="749" align="center" valign="top" background="images/two/two_r6_c3.jpg"><table width="693" cellspacing="0" cellpadding="0">
      <tr>
        <td height="30" valign="bottom"><table width="100%" cellpadding="0" cellspacing="0">
          <tr>
            <td width="28" align="center" valign="middle"><img src="images/d.jpg" width="5" height="15" /></td>
            <td width="635" height="25" align="left" class="greens">用户注册</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td><img src="images/lin.jpg" width="693" height="10" /></td>
      </tr>
      <tr>
        <td height="20"></td>
      </tr>
      <tr>
      <s:if test="flag">
      	<td align="center">
      		<font color="red"><s:actionmessage/></font>
          <table width="680" align="center" cellspacing="0" cellpadding="3" style="font-size:14px; color:gray; font-weight:bold; font-family:simsun;">
            <tr align="center">
              <td width="133" height="35" align="right">用 户 名:</td>
              <td align="left"><s:property value="logID"/></td>
            </tr>
            <tr align="center">
              <td height="35" align="right">密　　码:</td>
              <td align="left"><s:property value="password"/></td>
            </tr>
            <tr align="center">
              <td height="35" align="right">姓　　名:</td>
              <td align="left"><s:property value="realName"/></td>
            </tr>
            <tr align="center">
              <td height="35" align="right">身 份 证:</td>
              <td align="left"><s:property value="cardId"/></td>
              <tr align="center">
              <td height="35" align="right">注册邮箱:</td>
              <td align="left"><s:property value="email"/></td>
            </tr>
            </tr>
            <tr align="center">
              <td height="35" align="right">培训级别:</td>
              <td align="left"><s:property value="trainLevel_name"/></td>
            </tr>
          </table>
         </form>
        </td>
      </s:if>
      <s:else>
        <td align="center"><form id="registForm" name="registForm" method="post" action="/entity/first/userRegist_regist.action" onSubmit="return checkRegsitForm()">
          <table width="680" cellspacing="0" cellpadding="3" style="font-size:12px; font-family:simsun;">
            <font color="red"><s:actionmessage/></font>
            <tr>
              <td width="133" height="35" align="right">用 户 名</td>
              <td width="6" align="left"><span class="STYLE1">*</span></td>
              <td colspan="2" align="left"><input type="text" name="logID" class="inputs"/>
                (注：限英文,数字或下划线，4-16位，不能以下划线结尾)</td>
            </tr>
            <tr>
              <td height="35" align="right">密　　码</td>
              <td height="35" align="left"><span class="STYLE1">*</span></td>
              <td colspan="2" align="left"><input type="password" name="password" class="inputs"/>
                (注：限字符、数字等，4-16位)</td>
            </tr>
            <tr>
              <td height="35" align="right">确认密码</td>
              <td height="35" align="left"><span class="STYLE1">*</span></td>
              <td colspan="2" align="left"> <input type="password" name="rePassword" class="inputs"/>
                (注：限字符、数字等，4-16位) </td>
            </tr>
            <tr>
              <td height="35" align="right">姓　　名</td>
              <td height="35" align="left"><span class="STYLE1">*</span></td>
              <td colspan="2" align="left"> <input type="text" name="realName" class="inputs"/>
                (注：真实姓名)</td>
            </tr>
            <tr>
              <td height="35" align="right">身 份 证</td>
              <td height="35" align="left"><span class="STYLE1">*</span></td>
              <td colspan="2" align="left"><input type="text" name="cardId" class="inputs"/></td>
            </tr>
             <tr>
              <td height="35" align="right">注册邮箱</td>
              <td height="35" align="left"><span class="STYLE1">*</span></td>
              <td colspan="2" align="left"><input type="text" name="email" class="inputs"/>
              (注：请填写您的常用邮箱，此注册信息成功后会发送此邮箱)</td>
            </tr>
            <tr>
              <td height="35" align="right">培训级别 </td>
              <td height="35" align="left"><span class="STYLE1">*</span></td>
              <td colspan="2" align="left"><select name="trainLevel" class="selects">
              	<s:iterator value="trainingLevel" id="level">
                <option value="<s:property value="#level.id"/>"><s:property value="#level.name"/></option>
                </s:iterator>
               </select></td>
            </tr>
            <tr>
            	<td height="35" colspan="4"></td>
            </tr>
            <tr>
              <td height="35" colspan="2" align="right"></td>
              <td width="194" align="right"><span class="STYLE2">
                <input type="image" name="imageField3" src="images/si.jpg" />
              </span></td>
              <td width="321" align="center"><span class="STYLE2">（ 注：以上信息只限于本站基本数据，决不用于它处）</span></td>
            </tr>
          </table>
         </form>
        </td>
      </s:else>
      </tr>
      <tr>
        <td align="center">&nbsp;</td>
      </tr>
    </table></td>
    <td width="12" rowspan="2"></td>
  </tr>
  <tr>
    <td height="71" colspan="2" align="center" valign="top" bgcolor="#004EB2"><table width="86%" cellpadding="0" cellspacing="0" class="down">
      <tr>
        <td height="20" colspan="2"></td>
      </tr>
      <tr>
        <td height="13" align="left">京ICP备06049188号　Copyright&reg;2009 生殖健康咨询师培训网</td>
        <td align="right">                                                     技术支持： 北京网梯科技发展有限公司</td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
