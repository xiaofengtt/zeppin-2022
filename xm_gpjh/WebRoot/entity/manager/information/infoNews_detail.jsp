<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>生殖健康咨询师培训网</title>
<link href="/entity/first/css.css" rel="stylesheet" type="text/css" />
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
</script>
</head>

<body>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="98" align="center" valign="bottom" background="/entity/first/images/bg.jpg"><table width="977" cellspacing="0" cellpadding="0">
      <tr>
        <!-- <td height="22" colspan="2" align="right"><img src="/entity/first/images/gc.jpg" width="44" height="14" />&nbsp;&nbsp;</td> -->
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
            <td width="53" height="26" align="center" valign="bottom" background="/entity/first/images/index.jpg"><a href="/">首 页</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="/entity/first/images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=PTJS">平台介绍</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="/entity/first/images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=XWZX">新闻中心</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="/entity/first/images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=PXDT">培训动态</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="/entity/first/images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=ZCFG">政策法规</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="/entity/first/images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=KCTX">课程体系</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="/entity/first/images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=XXZY">学习资源</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="/entity/first/images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=PXSFC">培训师风采</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="/entity/first/images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=ZXBM">在线报名</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="/entity/first/images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=CJWT">常见问题</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="/entity/first/images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=XZZX">下载中心</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="/entity/first/images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=BKZTC">报考直通车</a></td>
            <td width="2" align="center" valign="bottom"></td>
            <td width="75" align="center" valign="bottom" background="/entity/first/images/top.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=SJTJ">书籍推荐</a></td>
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
    <td><img name="two_r3_c2" src="/entity/first/images/two/two_r3_c2.jpg" width="977" height="148" border="0" id="two_r3_c2" alt="" /></td>
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
          <td width="33%" align="left" valign="middle"><input type="image" name="imageField" src="/entity/first/images/search.jpg" /></td>
          <td width="47%" align="right">首 页&gt; <s:property value="type_name"/></td>
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
    	<td width="228" height="189" align="center" valign="top" background="/entity/first/images/two/two_r6_c2.jpg"><table width="220" cellspacing="0" cellpadding="0">
		<tr>
			<td height="10"></td>
		</tr>
		<tr>
            <td height="110" align="center" valign="bottom" background="/entity/first/images/index/index_r3_c6.jpg"><table width="82%" cellspacing="0" cellpadding="0">
              <form id="loginform" name="loginform" method="post" action="/sso/login_login.action">
                <tr>
                	<td height="30"></td>
                </tr>
                <tr>
                  <td width="29%" height="30" style="font-size:12px">用户名：<br /></td>
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
                  <td align="right"><input type="image" name="imageField" src="/entity/first/images/logins.jpg" />
                      <a href="/entity/first/pageDispatch_dispatch.action?type=ZXBM"><img src="/entity/first/images/reds.jpg" border="0" width="42" height="21" /></a></td>
                </tr>
              </form>
            </table></td>
          </tr>
          <tr>
            <td height="5"></td>
          </tr>
          <tr>
            <td height="63" align="center" background="/entity/first/images/indexbg.jpg"><a href="/entity/first/pageDispatch_dispatch.action?type=ZXBM""><img src="/entity/first/banner/ff.jpg" border="0" width="205" height="49" /></a></td>
          </tr>
    </table></td>
    <td width="749" align="center" valign="top" background="/entity/first/images/two/two_r6_c3.jpg"><table width="693" cellspacing="0" cellpadding="0">
      <tr>
        <td height="30" valign="bottom"><table width="100%" cellpadding="0" cellspacing="0">
          <tr>
            <td width="28" align="center" valign="middle"><img src="/entity/first/images/d.jpg" width="5" height="15" /></td>
            <td width="635" height="25" align="left" class="greens"><font>新闻预览</font><!--<s:property value="type_name"/>--></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td><img src="/entity/first/images/lin.jpg" width="693" height="10" /></td>
      </tr>
      <tr>
        <td height="40"><br /></td>
      </tr>
      <tr>
        <td align="center"><table width="98%" cellspacing="0" cellpadding="0">
          <tr>
            <td height="20" align="center" class="blue"><s:property value="news.title"escape="false"/></td>
          </tr>
          <tr>
            <td height="35" align="center" class="blue"></td>
          </tr>
         <tr>
         <td align="right" class="secondtitle"><s:date name="news.reportDate" format="yyyy-MM-dd" />  【<a href="javascript:window.print()"><span class="orange">打印</span></a>】  【点击次数：<s:property value="news.readCount"/>】</td>
          </tr>
          <tr><td>&nbsp;</td></tr>
          <tr>
            <td align="left" class="cen">
            <p>
            	<s:property value="news.note" escape="false"/>
            </p>
            </td>
          </tr>
          <tr>
            <td align="left" class="cen">&nbsp;</td>
          </tr>
        </table></td>
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
