<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>生殖健康咨询师培训网</title>
<link href="/entity/first/css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {color: #FF7E00}
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
	if(document.loginform.authCode.value==''&&false){
		alert("请输入验证码！");
		document.loginform.authCode.focus();
		return false;
	}
	return true;
}
function clearVal() {
	document.getElementById('searchVal').value="";
}
</script>
</head>

<body>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="78" align="center" valign="bottom"><table width="977" cellspacing="0" cellpadding="0">
      
		<tr>
        <td width="378" align="left" class="logo"><font size="4" style="font-family: 华文新魏">　生殖健康咨询师培训网</font></td>
        <td width="597" align="right" valign="middle">
          <table cellspacing="0" cellpadding="0">
            <form id="searchForm" name="searchForm" method="post" action="/entity/first/pageDispatch_search.action" onSubmit="return checkForm()"><tr>
              <td width="54" align="left" valign="middle"><!-- img src="/entity/first/images/gc.jpg" width="44" height="14" / --></td>
              <td width="160"><input type="text" name="searchVal" value="请输入关键字" onfocus="clearVal();" style="color:#CCCCCC; border:solid 1px #CCCCCC;; height:19px; width:160px; background:#000080" /></td>
              <td><input type="image" name="imageField2" src="/entity/first/images/search.jpg" /></td>
            </tr> </form>
          </table>
               
        </td>
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
    <td width="13" rowspan="2"></td>
    <td><img name="two_r3_c2" src="/entity/first/images/indxban.jpg" width="977" height="133" border="0" id="two_r3_c2" alt="" /></td>
    <td width="12" rowspan="2"></td>
  </tr>
  <tr>
    <td height="40"><table width="100%" border="0" cellpadding="0" cellspacing="0" background="/entity/first/images/topbg.jpg">
      <tr>
        <td height="30" align="center"><table width="96%" cellspacing="0" cellpadding="0">
          <tr>
            <td width="5%" height="25" align="center" class="indextopleft">通知：</td>
            <td width="86%" class="indexcen"><table width="96%" cellspacing="0" cellpadding="0">
              <s:iterator value="%{getNewsContentList('TZ',20,60,1,2)}" id="tz_news">
          	<tr>
            	<td width="4%"></td>
            	<td width="92%"><a href="/entity/first/pageDispatch_show.action?type=TZ&&id=<s:property value="#tz_news.id"/>" class="indexhui"><s:property value="#tz_news.note" escape="false"/></a></td>
          	</tr>
          </s:iterator>
            </table></td>
            <td width="9%" align="right" class="indextopright"><a href="/entity/first/pageDispatch_dispatch.action?type=TZ">更多通知 &gt;</a></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="13"></td>
    <td width="239" valign="top"><table width="100%" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="/entity/first/images/index/index_r2_c2.jpg" alt="" name="index_r2_c2" width="239" height="25" border="0" usemap="#index_r2_c2Map" id="index_r2_c2" /></td>
      </tr>
      <tr>
        <td height="166" background="/entity/first/images/index/index_r11_c2.jpg"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="indexhui">
          <s:iterator value="%{getNewsInfoList('PXDT',15,6,2)}" id="pxdt_news">
          	<tr>
            	<td width="6%"></td>
            	<td width="8%" height="25"><img src="/entity/first/images/ss.gif" width="5" height="7" /></td>
            	<td width="86%"><a href="/entity/first/pageDispatch_show.action?type=PXDT&&id=<s:property value="#pxdt_news.id"/>" class="indexhui"><s:property value="#pxdt_news.title"/></a></td>
          	</tr>
          </s:iterator>
        </table></td>
      </tr>
      <tr>
        <td><img src="/entity/first/images/index/index_r8_c2.jpg" alt="" name="index_r8_c2" width="239" height="26" border="0" usemap="#index_r8_c2Map" id="index_r8_c2" /></td>
      </tr>
      <tr>
        <td height="166" background="/entity/first/images/index/index_r11_c2.jpg"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="indexcen">
          <s:iterator value="%{getNewsInfoList('XXZY',15,6,2)}" id="xxzy_news">
          	<tr>
            	<td width="6%"></td>
            	<td width="8%" height="25"><img src="/entity/first/images/ss.gif" width="5" height="7" /></td>
            	<td width="86%"><a href="/entity/first/pageDispatch_show.action?type=XXZY&&id=<s:property value="#xxzy_news.id"/>" class="indexhui"><s:property value="#xxzy_news.title"/></a></td>
          	</tr>
          </s:iterator>
        </table></td>
      </tr>
      <tr>
        <td><img src="/entity/first/images/index/index_r15_c2.jpg" alt="" name="index_r15_c2" width="239" height="33" border="0" usemap="#index_r15_c2Map" id="index_r15_c2" /></td>
      </tr>
      <tr>
        <td height="180" background="/entity/first/images/index/index_r18_c2.jpg"><table width="100%" cellspacing="0" cellpadding="0">
          <s:iterator value="%{getNewsInfoList('BKZTC',15,7,2)}" id="bkztc_news">
          	<tr>
            	<td width="6%"></td>
            	<td width="8%" height="25"><img src="/entity/first/images/ss.gif" width="5" height="7" /></td>
            	<td width="86%"><a href="/entity/first/pageDispatch_show.action?type=BKZTC&&id=<s:property value="#bkztc_news.id"/>" class="indexhui"><s:property value="#bkztc_news.title"/></a></td>
          	</tr>
          </s:iterator>
        </table></td>
      </tr>
      <tr>
        <td><img name="index_r21_c2" src="/entity/first/images/index/index_r21_c2.jpg" width="239" height="12" border="0" id="index_r21_c2" alt="" /></td>
      </tr>
    </table></td>
    <td width="4"></td>
    <td width="490" valign="top"><table width="100%" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="/entity/first/images/index/index_r2_c4.jpg" alt="" name="index_r2_c4" width="490" height="32" border="0" usemap="#index_r2_c4Map" id="index_r2_c4" /></td>
      </tr>
      <tr>
        <td height="148" align="center" background="/entity/first/images/index/index_r5_c4.jpg"><table width="100%" cellspacing="0" cellpadding="0">
          <tr>
            <td width="73" height="110"></td>
            <td width="415" valign="middle"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="indexcen">
              <s:iterator value="%{getNewsInfoList('XWZX',25,6,2)}" id="xwzx_news">
              <tr>
                <td width="4%" height="22"><img src="/entity/first/images/ss.gif" width="5" height="7" /></td>
                <td width="77%" align="left"><a href="/entity/first/pageDispatch_show.action?type=XWZX&&id=<s:property value="#xwzx_news.id"/>" class="indexcen"><s:property value="#xwzx_news.title"/></a></td>
                <td width="19%" align="left" class="indexhui"><s:date name="#xwzx_news.reportDate" format="yyyy-MM-dd"/></td>
              </tr>
              </s:iterator>
            </table></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td><img name="index_r8_c4" src="/entity/first/images/index/index_r8_c4.jpg" width="490" height="7" border="0" id="index_r8_c4" alt="" /></td>
      </tr>
      <tr>
        <td><img src="/entity/first/images/index/index_r9_c4.jpg" alt="" name="index_r9_c4" width="490" height="27" border="0" usemap="#index_r9_c4Map" id="index_r9_c4" /></td>
      </tr>
      <tr>
        <td height="165" align="center" background="/entity/first/images/index/index_r12_c4.jpg"><table width="96%" border="0" cellpadding="0" cellspacing="0" class="indexhuihu">
          <s:iterator value="%{getNewsInfoList('KCTX',26,6,2)}" id="kctx_news">
              <tr>
                <td width="4%" height="22"><img src="/entity/first/images/ss.gif" width="5" height="7" /></td>
                <td width="77%" align="left"><a href="/entity/first/pageDispatch_show.action?type=KCTX&&id=<s:property value="#kctx_news.id"/>" class="indexcen"><s:property value="#kctx_news.title"/></a></td>
                <td width="19%" align="left" class="indexhui"><s:date name="#xwzx_news.reportDate" format="yyyy-MM-dd"/></td>
              </tr>
          </s:iterator>
        </table></td>
      </tr>
      <tr>
        <td height="5"></td>
      </tr>
      <tr>
        <td><img name="index_r16_c4" src="/entity/first/images/index/index_r16_c4.jpg" width="490" height="27" border="0" id="index_r16_c4" alt="" /></td>
      </tr>
      <tr>
        <td height="198" align="center" background="/entity/first/images/index/index_r18_c4.jpg"><table width="100%" cellspacing="0" cellpadding="0">
          <tr>
            <td width="83%" align="center" valign="top"><br />
              <table width="100%" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="120" align="center" valign="top"><table width="98%" cellspacing="0" cellpadding="0">
                      <s:set name="pxsfc_first" value="getNewsContentList('PXSFC',4,150,1,2)"/>
                      <tr>
                        <td align="center">
                        <s:if test="getFlagList('PXSFC')">
                        <a href="/entity/first/pageDispatch_show.action?type=PXSFC&&id=<s:property value="#pxsfc_first[0].id"/>">
                        <img src="<s:property value="#pxsfc_first[0].pictrue"/>" width="91" height="110" border="0" /></a>
                       </s:if>
                        </td>
                      </tr>
                      <tr>
                        <td height="35" align="center" class="indexyellow"><strong><a href="/entity/first/pageDispatch_show.action?type=PXSFC&&id=<s:property value="#pxsfc_first[0].id"/>" class="STYLE1"><s:property value="#pxsfc_first[0].title"/></a></strong></td>
                      </tr>
                  </table></td>
                  <td width="295" align="left" valign="top" class="indexhuihu"><s:property value="#pxsfc_first[0].note" escape="false"/></td>
                </tr>
              </table><br /></td>
            <td width="17%" valign="top"><table width="98%" cellspacing="0" cellpadding="0">
            	
            		<tr>
                		<td align="center">
                			<marquee direction=up onmouseover=this.stop() scrollamount=5 onmouseout=this.start()><center>
                			<s:iterator value="%{getNewsInfoList('PXSFC',4,10,2)}" id="pxsfc_list">
                				<a href="/entity/first/pageDispatch_show.action?type=PXSFC&&id=<s:property value="#pxsfc_list.id"/>"><img src="<s:property value="#pxsfc_list.pictrue"/>" width="60" height="75" border="0" /></a>
                				&nbsp;&nbsp;<strong><a href="/entity/first/pageDispatch_show.action?type=PXSFC&&id=<s:property value="#pxsfc_list.id"/>"><s:property value="#pxsfc_list.title"/></a></strong>
                			</s:iterator>
                			</center></marquee>
                		</td>
              		</tr>
            </table></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
    <td width="6"></td>
    <td valign="top"><table width="237" cellspacing="0" cellpadding="0">
      <tr>
        <td height="6"></td>
      </tr>
      <tr>
        <td height="189" align="center" valign="middle" background="/entity/first/images/index/index_r3_c6.jpg"><table width="220" cellspacing="0" cellpadding="0">
          <tr>
            <td height="110" align="center" valign="bottom"><table width="82%" cellspacing="0" cellpadding="0">
              <form id="loginform" name="loginform" method="post" action="/sso/login_login.action" onsubmit="return formtest()">
                <tr>
                	<td height="30"></td>
                </tr>
                <tr>
                  <td width="29%" height="30" style="font-size:12px">用户名：<br /></td>
                  <td width="71%" align="right"><input name="loginId" type="text" value='001' style="width:122px; height:18px; border: solid 1px #B8C5CE;"/></td>
                </tr>
                <tr>
                  <td height="30" style="font-size:12px">密    码：</td>
                  <td align="right"><input name="passwd" type="password" value='111111' style="width:122px; height:18px; border: solid 1px #B8C5CE;"/></td>
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
      </tr>
      <tr>
        <td align="center" valign="middle" height="6"></td>
      </tr>

      <tr>
        <td><img src="/entity/first/images/index/index_r10_c6.jpg" alt="" name="index_r10_c6" width="237" height="28" border="0" usemap="#index_r10_c6Map" id="index_r10_c6" /></td>
      </tr>
      <tr>
        <td height="155" background="/entity/first/images/index/index_r13_c6.jpg"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="indexhui">
          <s:iterator value="%{getNewsInfoList('ZCFG',15,6,2)}" id="zcfg_news">
          <tr>
            <td width="6%"></td>
            <td width="8%" height="22"><img src="/entity/first/images/ss.gif" width="5" height="7" /></td>
            <td width="86%"><a href="/entity/first/pageDispatch_show.action?type=ZCFG&&id=<s:property value="#zcfg_news.id"/>" class="indexhui"><s:property value="#zcfg_news.title"/></a></td>
          </tr>
          </s:iterator>
        </table></td>
      </tr>
      <tr>
        <td><img name="index_r17_c6" src="/entity/first/images/index/index_r17_c6.jpg" width="237" height="8" border="0" id="index_r17_c6" alt="" /></td>
      </tr>
      <tr>
        <td><img src="/entity/first/images/index/index_r18_c6.jpg" alt="" name="index_r18_c6" width="237" height="29" border="0" usemap="#index_r18_c6Map" id="index_r18_c6" /></td>
      </tr>
      <tr>
        <td height="160" align="center" valign="top" background="/entity/first/images/index/index_r13_c6.jpg"><table width="100%" cellspacing="0" cellpadding="0">
          <s:set name="sjtj_list" value="getNewsContentList('SJTJ',18,30,3,2)"/>
          <tr>
            <td width="41%" rowspan="2" align="center">
            <s:if test="getFlagList('SJTJ')">
            <a href="/entity/first/pageDispatch_show.action?type=SJTJ&&id=<s:property value="#sjtj_list[0].id" escape="false"/>"><img src="<s:property value="#sjtj_list[0].pictrue"/>" width="73" height="95" border="0" /></a>
            </s:if>
            </td>
            <td width="59%" height="35" align="left" class="indexcen"><a href="/entity/first/pageDispatch_show.action?type=SJTJ&&id=<s:property value="#sjtj_list[0].id"escape="false"/>"  class="indexcen"><s:property value="#sjtj_list[0].title"/></a></td>
          </tr>
          <tr>
            <td align="left" valign="top"><a href="/entity/first/pageDispatch_show.action?type=SJTJ&&id=<s:property value="#sjtj_list[0].id"escape="false"/>"><s:property value="#sjtj_list[0].note"escape="false"/></a></td>
          </tr>
          <tr>
            <td height="10" colspan="2">&nbsp;</td>
            </tr>
          <tr>
            <td height="22" colspan="2" align="left" class="indexhui indextex"><a href="/entity/first/pageDispatch_show.action?type=SJTJ&&id=<s:property value="#sjtj_list[1].id"/>" class="indexhui">&nbsp;&nbsp;<s:property value="#sjtj_list[1].title"/></a></td>
          </tr>
          <tr>
            <td height="22" colspan="2" align="left" class="indexhui indextex"><a href="/entity/first/pageDispatch_show.action?type=SJTJ&&id=<s:property value="#sjtj_list[2].id"/>" class="indexhui">&nbsp;&nbsp;<s:property value="#sjtj_list[2].title"/></a></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td><img name="index_r17_c6" src="/entity/first/images/index/index_r17_c6.jpg" width="237" height="8" border="0" id="index_r17_c6" alt="" /></td>
      </tr>
    </table></td>
    <td width="12"></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="13"></td>
    <td align="center"><table width="99%" cellspacing="0" cellpadding="0">
      <tr>
    <td height="20" colspan="4"></td>
  </tr>
	  <tr>
        <td align="left"><img src="/entity/first/banner/s.jpg" width="81" height="45" /></td>
        <td align="center"><a target="_blank" href="http://www.chinapop.gov.cn"><img border=0 src="/entity/first/banner/1.jpg" width="117" height="34" /></td>
       <td align="center"><a target="_blank" href="http://www.most.gov.cn"><img border=0 border=0 src="/entity/first/banner/2.jpg" width="117" height="34" /></td>
	   <td align="center"><a target="_blank" href="http://www.moh.gov.cn"><img border=0 src="/entity/first/banner/3.jpg" width="121" height="35" /></td>
	   <td align="center"><a target="_blank" href="http://www.21wecan.com.cn"><img border=0 src="/entity/first/banner/4.jpg" width="117" height="34" /></td>
	   <td align="center"><a target="_blank" href="http://www.who.int/en/"><img border=0 src="/entity/first/banner/5.jpg" width="118" height="35" /></td>
	   <td align="center"><a target="_blank" href="http://www.unfpa.org"><img border=0 border=0 src="/entity/first/banner/6.jpg" width="116" height="34" /></td>
	   <td align="center"><a target="_blank" href="http://www.cnic.ac.cn/"><img border=0 src="/entity/first/banner/7.jpg" width="118" height="35" /></td>
      </tr>
    </table></td>
    <td width="12"></td>
  </tr>
  <tr>
    <td height="20" colspan="3"></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="66" align="center" valign="top" background="/entity/first/images/indwo.jpg"><table width="92%" cellpadding="0" cellspacing="0" class="downde">
      <tr>
        <td height="20" colspan="2"></td>
      </tr>
      <tr>
        <td height="13" align="left">京ICP备06049188号　Copyright&reg;2009 生殖健康咨询师培训网</td>
        <td align="right"> 技术支持： 北京网梯科技发展有限公司</td>
      </tr>
    </table></td>
  </tr>
</table>
<s:if test="loginErrMessage != '' and loginErrMessage != null">
              		<script>
              			alert("<s:property value="loginErrMessage"/>");
					</script>  
              	</s:if>
<map name="index_r2_c2Map" id="index_r2_c2Map"><area shape="rect" coords="201,3,235,21" href="/entity/first/pageDispatch_dispatch.action?type=PXDT"/></map>
<map name="index_r8_c2Map" id="index_r8_c2Map"><area shape="rect" coords="202,7,235,24" href="/entity/first/pageDispatch_dispatch.action?type=XXZY" /></map>
<map name="index_r2_c4Map" id="index_r2_c4Map"><area shape="rect" coords="434,9,477,29" href="/entity/first/pageDispatch_dispatch.action?type=XWZX" /></map>
<map name="index_r9_c4Map" id="index_r9_c4Map"><area shape="rect" coords="431,4,479,23" href="/entity/first/pageDispatch_dispatch.action?type=KCTX" /></map>
<map name="index_r10_c6Map" id="index_r10_c6Map"><area shape="rect" coords="180,7,225,25" href="/entity/first/pageDispatch_dispatch.action?type=ZCFG" /></map>
<map name="index_r18_c6Map" id="index_r18_c6Map"><area shape="rect" coords="180,6,227,25" href="/entity/first/pageDispatch_dispatch.action?type=SJTJ" /></map>
<map name="index_r15_c2Map" id="index_r15_c2Map"><area shape="rect" coords="189,7,222,26" href="/entity/first/pageDispatch_dispatch.action?type=BKZTC" /></map>
</body>
</html>
