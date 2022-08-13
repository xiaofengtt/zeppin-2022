<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<?xml version="1.0" encoding="utf-8"?><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>生殖健康咨询师培训网</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
a {
font-size:12px;
color:#0765B6;
line-height:22px;
text-decoration:none;
}
-->
</style>
<link href="/web/bzz_index/css.css" rel="stylesheet" type="text/css" />
<link href="/web/css/css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {
	color: #FF9000;
	font-weight: bold;
}
.link a{
color:#3399cc;}
-->
</style>
<script> 
	function search(){
  	 document.forms[0].submit(); 
   
   }
</script>
</head>

<body bgcolor="#E0E0E0">
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td><table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr> 
          <td colspan="10"><img name="index_r1_c1" src="/web/bzz_index/images/index/index_r1_c1.jpg" width="1002" height="154" border="0" id="index_r1_c1" alt="" /></td>
        </tr>
        <tr> 
          <td height="7" colspan="10"></td>
        </tr>
        <tr> 
          <td><img name="index_r3_c1" src="/web/bzz_index/images/index/index_r3_c1.jpg" width="31" height="29" border="0" id="index_r3_c1" alt="" /></td>
          <td><img name="index_r3_c2" src="/web/bzz_index/images/top_06.jpg" width="109" height="29" border="0" id="index_r3_c2" alt="" /></td>
          <td><img name="index_r3_c4" src="/web/bzz_index/images/top_08.jpg" width="109" height="29" border="0" id="index_r3_c4" alt="" /></td>
          <td><img name="index_r3_c6" src="/web/bzz_index/images/top_10.jpg" width="109" height="29" border="0" id="index_r3_c6" alt="" /></td>
          <td><img name="index_r3_c9" src="/web/bzz_index/images/top_12.jpg" width="109" height="29" border="0" id="index_r3_c9" alt="" /></td>
          <td><img name="index_r3_c10" src="/web/bzz_index/images/top_14.jpg" width="109" height="29" border="0" id="index_r3_c10" alt="" /></td>
          <td><img name="index_r3_c11" src="/web/bzz_index/images/top_16.jpg" width="109" height="29" border="0" id="index_r3_c11" alt="" /></td>
          <td><img name="index_r3_c15" src="/web/bzz_index/images/top_18.jpg" width="109" height="29" border="0" id="index_r3_c15" alt="" /></td>
          <td><img name="index_r3_c16" src="/web/bzz_index/images/top_20.jpg" width="109" height="29" border="0" id="index_r3_c16" alt="" /></td>
          <td><img name="index_r3_c17" src="/web/bzz_index/images/index/index_r3_c17.jpg" width="25" height="29" border="0" id="index_r3_c17" alt="" /></td>
        </tr>
        <tr> 
          <td height="14" colspan="10"></td>
        </tr>
      </table>
      <table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="63"></td>
          <td width="860"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="27" background="/web/bzz_index/images/index/index_r5_c007.jpg"> 
                  <div align="left">
                    <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
                      <tr> 
                        <td width="6%">&nbsp;</td>
                        <td width="94%"><font color="#000066" size="3"><strong>公告列表</strong></font></td>
                      </tr>
                    </table>
                  </div></td>
              </tr>
              <tr> 
                <td height="54" valign="top"><table width="100%" height="22" border="0" cellpadding="0" cellspacing="0" >
                    <tr> 
                      <td align="center"> <table width="90%" border="0" cellpadding="0" cellspacing="0" style="margin:18px 0 3px 0px;border-bottom:2px solid #F2F2F2;">
                          <tr  > 
                            <td width="5%" height="26" align="center"></td>
                            <td width="73%" height="26"  class="newstitle ">公告标题
                              <br /></td>
                            <td width="22%" height="26" class="newstitle rightborder">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发布时间</td>
                          </tr>
                          </table>
                          
                          <s:iterator value="pebulletin">
                          <table width="100%" height="22" border="0" cellpadding="0" cellspacing="0"  class="newslist ">
                          <tr> 
                            <td width="5%" height="26" class="newsfontcenter1" align="right"><img src="/web/bzz_index/images/3.jpg" width="4" height="6" /></td>
                            <td width="73%" height="26" class="newsfontleft newstyle1" align="center"><a href="/entity/first/firstBulletin_viewDetail.action?bean.id=<s:property value="id"/>" Style="color: #0765B6" target="_blank"><s:property value="title" /></a>
                              <br /></td>
                            <td width="22%" height="26" class="newsfontcenter " aligh=right><s:date name="publishDate" format="yyyy-MM-dd" /></td>
                          </tr>
                            </table>
                        </s:iterator>
                      
                        <p>[上一页]|[下一页]</p></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
          <td width="79"></td>
        </tr>
        <tr> 
          <td></td>
          <td height="75"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td align="center"><img src="/web/bzz_index/images/logo/1.jpg" width="121" height="56" /></td>
                <td align="center"><img src="/web/bzz_index/images/logo/2.jpg" width="121" height="56" /></td>
                <td align="center"><img src="/web/bzz_index/images/logo/3.jpg" width="121" height="56" /></td>
                <td align="center"><img src="/web/bzz_index/images/logo/4.jpg" width="121" height="56" /></td>
                <td align="center"><img src="/web/bzz_index/images/logo/5.jpg" width="121" height="56" /></td>
                <td align="center"><img src="/web/bzz_index/images/logo/6.jpg" width="121" height="56" /></td>
                <td align="center"><img src="/web/bzz_index/images/logo/7.jpg" width="121" height="56" /></td>
              </tr>
            </table></td>
          <td></td>
        </tr>
      </table>
      <table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr> 
          <td height="54" align="center" background="/web/bzz_index/images/index/index_r23_c2.jpg" class="down">版权所有：生殖健康咨询师培训网 
            京ICP备05046845号<br />
            投诉：010-62786820 传真：010-62789770 技术客服热线电话： 010-58731118转254</td>
        </tr>
      </table>
</td>
  </tr>
</table>
</body>
</html>
