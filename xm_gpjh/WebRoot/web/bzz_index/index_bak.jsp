<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
 %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<base href="<%=basePath%>">
<title>生殖健康咨询师培训网</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background:#FFFFFF;
}
a:link { text-decoration: none;color: #000000}
a:active { text-decoration:blink}
a:hover { text-decoration:underline;color: red} 
a:visited { text-decoration: none;color: #000000}

-->
</style>
<link href="/web/bzz_index/css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {
	color: #FF9000;
	font-weight: bold;
}
-->
</style>
<script type="text/javascript">
function myonload(){
<s:if test="loginErrMessage!=null">
	// window.document.loginform.loginType.value="<s:property value='loginType'/>";
	window.document.loginform.loginId.value="<s:property value='loginId'/>";
	window.alert("<s:property value='loginErrMessage'/>");
</s:if>
}

function goUrl()
{	
	window.open(document.form1.select.value,"newwindow");
}
</script>
</head>

<body onload="myonload()" >
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="10"><img name="index_r1_c1" src="/web/bzz_index/images/index/index_r1_c1.jpg" width="1002" height="154" border="0" id="index_r1_c1" alt="" /></td>
  </tr>
  <tr>
    <td height="7" colspan="10"></td>
  </tr>
  <tr>
   <td><img name="index_r3_c1" src="/web/bzz_index/images/index/index_r3_c1.jpg" width="31" height="33" border="0" id="index_r3_c1" alt="" /></td>
   <td><img name="index_r3_c2" src="/web/bzz_index/images/index/index_r3_c2.jpg" width="115" height="33" border="0" id="index_r3_c2" alt="" /></td>
   <td><img name="index_r3_c4" src="/web/bzz_index/images/index/index_r3_c4.jpg" width="116" height="33" border="0" id="index_r3_c4" alt="" /></td>
   <td><img name="index_r3_c6" src="/web/bzz_index/images/index/index_r3_c6.jpg" width="118" height="33" border="0" id="index_r3_c6" alt="" /></td>
   <td><img name="index_r3_c9" src="/web/bzz_index/images/index/index_r3_c9.jpg" width="118" height="33" border="0" id="index_r3_c9" alt="" /></td>
   <td><img name="index_r3_c10" src="/web/bzz_index/images/index/index_r3_c10.jpg" width="119" height="33" border="0" id="index_r3_c10" alt="" /></td>
   <td><img name="index_r3_c11" src="/web/bzz_index/images/index/index_r3_c11.jpg" width="120" height="33" border="0" id="index_r3_c11" alt="" /></td>
   <td><img name="index_r3_c15" src="/web/bzz_index/images/index/index_r3_c15.jpg" width="116" height="33" border="0" id="index_r3_c15" alt="" /></td>
   <td><img name="index_r3_c16" src="/web/bzz_index/images/index/index_r3_c16.jpg" width="124" height="33" border="0" id="index_r3_c16" alt="" /></td>
   <td><img name="index_r3_c17" src="/web/bzz_index/images/index/index_r3_c17.jpg" width="25" height="33" border="0" id="index_r3_c17" alt="" /></td>
  </tr>
  <tr>
    <td height="14" colspan="10"></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="31"></td>
    <td width="290"><table width="290" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><table width="290" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="181" height="37" align="center" background="/web/bzz_index/images/index/index_r5_c2.jpg" class="indexcen">2009年5月20日  星期三</td>
            <td width="109"><img name="index_r5_c5" src="/web/bzz_index/images/index/index_r5_c5.jpg" width="109" height="37" border="0" id="index_r5_c5" alt="" /></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="180" align="center" valign="bottom" background="/web/bzz_index/images/index/index_r7_c2.jpg"><table width="90%" border="0" cellpadding="0" cellspacing="0" class="indexleft" >

									<s:append id="list3">
										<s:param value="Bulletin"></s:param>
										<s:param value="{0,0,0,0,0}"></s:param>
									</s:append>
									<s:iterator id="BulletinList" value="#list3"
										status="bulletin">
										<s:if test="#bulletin.getIndex()<7">
										<s:if test="#this==0">
										<tr>
											<td align="left" > </td>
										</tr>
										</s:if>
										<s:else >
										<tr>
											<td align="left">
												·
												<a
													href="/entity/first/firstBulletin_viewDetail.action?bean.id=<s:property value="id"/>"
													Style="color: #0765B6" target="_blank"><s:property
														value="title" />
												</a>
											</td>
										</tr>
										</s:else>
										</s:if>
										

									</s:iterator>
									<tr>
            							<td align="right" ><a href="/entity/first/firstBulletin_allBulletin.action" target="_blank">MORE &gt;&gt;</a></td>
          								</tr>
        </table></td>
      </tr>
      <tr>
        <td><img name="index_r9_c2" src="/web/bzz_index/images/index/index_r9_c2.jpg" width="290" height="30" border="0" id="index_r9_c2" alt="" /></td>
      </tr>
      <tr>
        <td><img name="index_r11_c2" src="/web/bzz_index/images/index/index_r11_c2.jpg" width="290" height="36" border="0" id="index_r11_c2" alt="" /></td>
      </tr>
      <tr><form id="loginform" name="loginform" method="post" action="/sso/login_login.action" target="_self" >
        <td height="109" align="center" valign="bottom" background="/web/bzz_index/images/index/index_r12_c2.jpg"><table width="75%" border="0" cellspacing="0" cellpadding="0">
           <tr>
            <td height="5"></td>
            <td align="left">&nbsp;</td>
          </tr>
          <tr>
            <td width="26%" height="25">用户名： </td>
            <td width="74%" align="left"><input type="text" name="loginId" /></td>
          </tr>
          <tr>
            <td height="25">              密  码：</td>
            <td align="left">
              <input type="password" name="passwd" />
            </td>
          </tr>
         <tr>
            <td height="25">验证码：</td>
            <td align="left"><input name="authCode" type="text" size=3/>&nbsp;<img src="/sso/authimg" width="50" height="20" />&nbsp;&nbsp;&nbsp;<input type="submit" value="登录"></td>
          </tr>
        </table>  
       </td> </form>
      </tr>
      <tr>
        <td><img name="index_r15_c2" src="/web/bzz_index/images/index/index_r15_c2.jpg" width="290" height="31" border="0" id="index_r15_c2" alt="" /></td>
      </tr>
      <tr>
        <td><img name="index_r16_c2" src="/web/bzz_index/images/index/index_r16_c2.jpg" width="290" height="45" border="0" id="index_r16_c2" alt="" /></td>
      </tr>
      <tr>
        <td height="315" align="center"><table width="96%" border="0" cellspacing="1" cellpadding="1">
          <tr>
           <s:iterator value="jsfc" status="js">
           	<s:if test="#js.getIndex()<3">
           	<td align="center"><table width="89" height="116" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="center" background="/web/bzz_index/images/5.jpg">
                	<s:if test="id==1">
                  	<a href="#" target="_blank"><img src="..<s:property value="pictrue"/>" width="81" height="108" border="0" /></a>
                  </s:if>
                  <s:else>
                  		 <a target="_blank" href="/entity/first/firstInfoNews_toInfoNews.action?bean.id=<s:property value="id"/>"><img src="..<s:property value="pictrue"/>" width="81" height="108" border="0" /></a>
                  </s:else>
                </td>
              </tr>
            </table>
            </td>
           	</s:if>
           </s:iterator>
          </tr>
          <tr>
           <s:iterator value="jsfc" status="js"> 
         <s:if test="#js.getIndex()<3">
            <td align="center">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="indextea1">
                <tr>
                  <td height="24" align="center"><s:if test="id==1">
                  	<a href="#" target="_blank"><s:property value="title"/></a>
                  </s:if><s:else>
                   <a target="_blank" href="/entity/first/firstInfoNews_toInfoNews.action?bean.id=<s:property value="id"/>" target="_blank"><s:property value="title"/></a>
                  </s:else></td>
                </tr>
            </table>
            </td>
            </s:if> 
        </s:iterator>   
            </tr>
          <tr>
            <s:iterator value="jsfc" status="js">
           	<s:if test="#js.getIndex()>2">
           	<td align="center"><table width="89" height="116" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="center" background="/web/bzz_index/images/5.jpg">
				  <s:if test="id==1">
                  	<a href="#" target="_blank"><img src="..<s:property value="pictrue"/>" width="81" height="108" border="0" /></a>
                  </s:if>
                  <s:else>
                  		 <a target="_blank" href="/entity/first/firstInfoNews_toInfoNews.action?bean.id=<s:property value="id"/>"><img src="..<s:property value="pictrue"/>" width="81" height="108" border="0" /></a>
                  </s:else>
				</td>
              </tr>
            </table>
            </td>
           	</s:if>
           </s:iterator>
          </tr>
          <tr>
          <s:iterator value="jsfc" status="js">
             <s:if test="#js.getIndex()>2">
            <td align="center">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="indextea1">
                <tr>
                  <td height="24" align="center"><s:if test="id==1">
                  	<a href="#" target="_blank"><s:property value="title"/></a>
                  </s:if><s:else>
                   <a target="_blank" href="/entity/first/firstInfoNews_toInfoNews.action?bean.id=<s:property value="id"/>" target="_blank"><s:property value="title"/></a>
                  </s:else></td>
                </tr>
            </table>
            </td>
            </s:if> 
        </s:iterator>   
            </tr>
        </table></td>
      </tr>
      <tr>
        <td><img name="index_r21_c2" src="/web/bzz_index/images/index/index_r21_c2.jpg" width="290" height="30" border="0" id="index_r21_c2" alt="" /></td>
      </tr>
      
    </table></td>
    <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td colspan="2"><a href="/entity/first/firstInfoNews_allNews.action" target="_blank"><img name="index_r5_c7" src="/web/bzz_index/images/index/index_r5_c7.jpg" width="656" height="27" border="0" id="index_r5_c7" alt=""/></a></td>
      </tr>
      <tr>
        <td height="207" colspan="2" align="center"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td width="41%" align="center">
                <s:iterator id="tpnewsList" value="tpnewsList" status="tpnews">
                	<input id="pic" type="hidden" name="pic" value="<s:property value="pictrue"/>"/>
                	<input id="title" type="hidden" name="title" value="<s:property value="title"/>"/>
                	<input id="pid" type="hidden" name="pid" value="<s:property value="id"/>"/>
                </s:iterator>
            <script type="text/javascript"> 
            		var basePath="<%=basePath%>"
                    var pic =document.getElementsByName("pic").length;
                    var pics = basePath+document.getElementsByName("pic")[0].value+"|";
                    var purl ="/entity/first/firstInfoNews_toInfoNews.action?bean.id=";
                    var links = purl+document.getElementsByName("pid")[0].value+"|";
                    var texts= document.getElementsByName("title")[0].value.substring(0,15)+"|";
            		for(var k=1;k<pic;k++){
            			pics = basePath+document.getElementsByName("pic")[k].value+"|"+pics;
            			links =purl+document.getElementsByName("pid")[k].value+"|"+links;
            			texts =document.getElementsByName("title")[k].value+"|"+texts
            		}
            				
            		pics = pics.substring(0,pics.length-1);
                    links= links.substring(0,links.length-1);     
                	texts= texts.substring(0,texts.length-1);
					var focus_width=252;
					var focus_height=180;
					var text_height=15;
					//alert("pics:"+pics+"texts:"+texts );
					var swf_height = focus_height+text_height;
					document.write('<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0" width="'+ focus_width +'" height="'+ swf_height +'">');
					document.write('<param name="allowScriptAccess" value="sameDomain"><param name="movie" value="/web/bzz_index/images/pixviewer.swf"><param name="quality" value="high"><param name="bgcolor" value="#F0F0F0">');
					document.write('<param name="menu" value="false"><param name=wmode value="opaque">');
					document.write('<param name="FlashVars" value="pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height+'">');
					document.write('<embed src="/web/bzz_index/images/pixviewer.swf" wmode="opaque" FlashVars="pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height+'" menu="false" bgcolor="#F0F0F0" quality="high" width="'+ focus_width +'" height="'+ focus_height +'" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />');  
					document.write('</object>');
				</script>
             </td> 
			
            <td width="59%" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <s:iterator id="newsList" value="newsList" status="news">
              <tr>
                <td width="5%" align="center"><img src="/web/bzz_index/images/3.jpg" width="4" height="6" /></td>
                <td width="73%" align="left"><a href="/entity/first/firstInfoNews_toInfoNews.action?bean.id=<s:property value="id"/>" target="_blank"><s:property value="title" /></a>
	               <s:if test="isNew==1">
	                <img alt="new" src="/web/bzz_index/images/new.gif" />
                	</s:if>
                </td>
                <td width="22%" class="indexleft">[<s:date name="submitDate"  format="yyyy-MM-dd"/>]</td>
              </tr>
              </s:iterator>          
            </table></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td colspan="2"><img name="index_r9_c7" src="/web/bzz_index/images/index/index_r9_c7.jpg" width="656" height="22" border="0" id="index_r9_c7" alt="" /></td>
      </tr>
      <tr>
        <td colspan="2"><img name="index_r10_c7" src="/web/bzz_index/images/index/index_r10_c7.jpg" width="656" height="65" border="0" id="index_r10_c7" alt="" /></td>
      </tr>
      <tr>
        <td height="102" colspan="2" background="/web/bzz_index/images/index/6.jpg" >
		  <table width="45%" border="0" align="left" cellpadding="0" cellspacing="0" style="margin-left:25px; margin-top:26px;" class="link">
              <tr> 
                <td height="20"><img src="/web/bzz_index/images/3.jpg" width="4" height="6" />&nbsp;&nbsp;<a href="#"><font color="#3399cc">建站指南</font></a></td>
                <td height="20"><img src="/web/bzz_index/images/3.jpg" width="4" height="6" />&nbsp;&nbsp;<a href="#"><font color="#3399cc">站点动态</font></a></td>
  </tr>
              <tr> 
                <td height="20"><img src="/web/bzz_index/images/3.jpg" width="4" height="6" />&nbsp;&nbsp;<a href="#"><font color="#3399cc">站点一览</font></a></td>
                <td height="20"><img src="/web/bzz_index/images/3.jpg" width="4" height="6" />&nbsp;&nbsp;<a href="#"><font color="#3399cc">北京清华总站</font></a></td>
  </tr>
              <tr> 
                <td height="20">&nbsp;</td>
                <td height="20">&nbsp;</td>
  </tr>
</table>
            <table width="45%" border="0" align="left" cellpadding="0" cellspacing="0" style="margin-left:25px;  margin-top:20px;">
              <tr> 
                <td  height="20"><img src="/web/bzz_index/images/3.jpg" width="4" height="6" />&nbsp;&nbsp;<a href="#"><font color="#3399cc">企业信息登记表</font></a></td>
                <td  height="20"><img src="/web/bzz_index/images/3.jpg" width="4" height="6" />&nbsp;&nbsp;<a href="#"><font color="#3399cc">缓考申请表</font></a></td>
  </tr>
              <tr> 
                <td height="20"><img src="/web/bzz_index/images/3.jpg" width="4" height="6" />&nbsp;&nbsp;<a href="#"><font color="#3399cc">学员报名信息登记表</font></a></td>
                <td height="20"><img src="/web/bzz_index/images/3.jpg" width="4" height="6" />&nbsp;&nbsp;<a href="#"><font color="#3399cc">补考申请表</font></a></td>
  </tr>
              <tr> 
                <td height="20"><img src="/web/bzz_index/images/3.jpg" width="4" height="6" />&nbsp;&nbsp;<a href="#"><font color="#3399cc">缓修申请表</font></a></td>
                <td height="20">&nbsp;</td>
  </tr>
</table>
</td>
      </tr>
      <tr>
        <td height="9" colspan="2"></td>
      </tr>
      <tr>
        <td width="338" rowspan="3" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td colspan="3"><img name="index_r17_c7" src="/web/bzz_index/images/index/index_r17_c7.jpg" width="338" height="53" border="0" id="index_r17_c7" alt="" /></td>
          </tr>
          <tr>
            <td width="20"><img name="index_r19_c7" src="/web/bzz_index/images/index/index_r19_c7.jpg" width="20" height="298" border="0" id="index_r19_c7" alt="" /></td>
            <td width="294" align="center" background="/web/bzz_index/images/index/index_r19_c12.jpg"><table width="96%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="15%" align="center"><img src="/web/bzz_index/images/6.jpg" width="30" height="81" /></td>
                <td width="85%"><table width="96%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="20" align="left">课程名称:<strong>企业竞争力与企业发展</strong> </td>
                  </tr>
                  <tr>
                    <td height="20" align="left"> 授课教师:<span class="STYLE1">王忠明</span></td>
                  </tr>
                  <tr>
                    <td align="left" class="indexcen">介绍企业竞争力与企业发展关系，详细讲解<br />
两者之间关系。<img src="/web/bzz_index/images/4.jpg" width="40" height="11" /></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td align="center">&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td align="center"><img src="/web/bzz_index/images/7.jpg" width="30" height="81" /></td>
                <td><table width="96%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="20" align="left">课程名称:<strong> 班组基础管理</strong> </td>
                  </tr>
                  <tr>
                    <td height="20" align="left"> 授课教师:<span class="STYLE1">郭丽华</span></td>
                  </tr>
                  <tr>
                    <td align="left" class="indexcen">介绍规章制度、定额管理、标准化工作、<br />
                      计量工作，原始记录。<img src="/web/bzz_index/images/4.jpg" width="40" height="11" /></td>
                  </tr>
                </table></td>
              </tr>
              <tr>
                <td align="center">&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
              <tr>
                <td align="center"><img src="/web/bzz_index/images/8.jpg" width="30" height="80" /></td>
                <td><table width="96%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="20" align="left">课程名称:<strong>班组现场管理</strong> </td>
                  </tr>
                  <tr>
                    <td height="20" align="left"> 授课教师:<span class="STYLE1">李忠辉</span></td>
                  </tr>
                  <tr>
                    <td align="left" class="indexcen">介绍如何掌握“六何”分析方法和“5S”活<br />
                     动方法，如何有效进行现场管理。<img src="/web/bzz_index/images/4.jpg" width="40" height="11" /></td>
                  </tr>
                </table></td>
              </tr>
            </table></td>
            <td width="24"><img name="index_r19_c13" src="/web/bzz_index/images/index/index_r19_c13.jpg" width="24" height="298" border="0" id="index_r19_c13" alt="" /></td>
          </tr>
          <tr>
            <td colspan="3"><img name="index_r21_c7" src="/web/bzz_index/images/index/index_r21_c7.jpg" width="338" height="30" border="0" id="index_r21_c7" alt="" /></td>
          </tr>
        </table></td>
        <td><img name="index_r17_c14" src="/web/bzz_index/images/index/index_r17_c14.jpg" width="318" height="36" border="0" id="index_r17_c14" alt="" /></td>
      </tr>
      <tr>
        <td height="291"><table width="96%" border="0" cellspacing="1" cellpadding="1">
          <tr>
         <s:iterator value="bzfc" status="bz">
         <s:if test="#bz.getIndex()<2">
        	 <td align="center">
            <table width="89" height="116" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td align="center" background="/web/bzz_index/images/5.jpg">
                  <s:if test="id==1">
                  	<a href="#" target="_blank"><img src="..<s:property value="pictrue"/>" width="81" height="108" border="0" /></a>
                  </s:if>
                  <s:else>
                  		 <a target="_blank" href="/entity/first/firstInfoNews_toInfoNews.action?bean.id=<s:property value="id"/>"><img src="..<s:property value="pictrue"/>" width="81" height="108" border="0" /></a>
                  </s:else>
                  </td>
                </tr>
            </table>
            </td>
         </s:if>
         </s:iterator>
         </tr> 
         <tr>
         <s:iterator value="bzfc" status="bz"> 
         <s:if test="#bz.getIndex()<2">
            <td align="center">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="indextea1">
                <tr>
                  <td height="24" align="center">
                  <s:if test="id==1">
                  	<a href="#" target="_blank"><s:property value="title"/></a>
                  </s:if><s:else>
                   <a target="_blank" href="/entity/first/firstInfoNews_toInfoNews.action?bean.id=<s:property value="id"/>" target="_blank"><s:property value="title"/></a>
                  </s:else>
                 </td>
                </tr>
            </table>
            </td>
            </s:if> 
        </s:iterator>           
          </tr>
          <tr>
          <s:iterator value="bzfc" status="bz">
         <s:if test="#bz.getIndex()>2">
        	 <td align="center">
            	<table width="89" height="116" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td align="center" background="/web/bzz_index/images/5.jpg"> <s:if test="id==1">
                  	<a style="link:none" href="#" target="_blank"><img src="..<s:property value="pictrue"/>" width="81" height="108" border="0"/></a>
                  </s:if>
                  <s:else>
                  	<a target="_blank" href="/entity/first/firstInfoNews_toInfoNews.action?bean.id=<s:property value="id"/>"><img border="0" src="..<s:property value="pictrue"/>" width="81" height="108" border="0" /></a>
                  </s:else>
                  </td>
                </tr>
            </table>
            </td>
         </s:if>
         </s:iterator>
         </tr>
          <tr>
           <s:iterator value="bzfc" status="bz">
           <s:if test="#bz.getIndex()>2">
            <td align="center"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="indextea1">
                <tr>
                  <td height="24" align="center"><s:if test="id==1">
                  	<a href="#" target="_blank"><s:property value="title"/></a>
                  </s:if><s:else>
                   <a style="link:none" target="_blank" href="/entity/first/firstInfoNews_toInfoNews.action?bean.id=<s:property value="id"/>" target="_blank"><s:property value="title"/></a>
                  </s:else></td>
                </tr>
            </table>
            </td>
            </s:if>
            </s:iterator>
          </tr>
        </table></td>
      </tr>
      <tr><form id="form1" name="form1" method="post" action="">
        <td height="54" align="center" valign="top" background="/web/bzz_index/images/index/index_r20_c14.jpg"><table width="80%" border="0" cellspacing="0" cellpadding="0">
            <tr>
            <td colspan="3" height="15"></td>
            </tr><tr>
            <td width="23%">网站链接</td>
            <td width="67%"><select name="select" style="width:155px; height:20px;">
            <option value="http://www.sasac.gov.cn/n1180/index.html">国务院国有资产监督管理委员会</option>
            <option value="http://www.tsinghua.edu.cn">清华大学</option>
            </select>            </td>
            <td width="10%"><img src="/web/bzz_index/images/go.jpg" width="23" height="17" onclick="javascript:goUrl()"/></td>
          </tr>
        
        </table></td>
      </form>
      </tr>
    </table></td>
    <td width="25"></td>
  </tr>
  <tr>
    <td></td>
    <td height="75" colspan="2"><DIV align="center" id="demo" style="OVERFLOW: hidden; WIDTH: 950px; HEIGHT: 62px">
   <TABLE cellSpacing=0 cellPadding=0 align=left border=0 cellspace="0">
      <TBODY>
         <TR>
           <TD id="demo1" vAlign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="center"><a href="http://www.sasac.gov.cn/n1180/n2381219/n2381237/index.html" target="_blank"><img src="/web/bzz_index/images/logo/1.jpg" width="121" height="56" border="0"/></a></td>
        <td align="center"><a href="http://www.sasac.gov.cn/n1180/n2381219/n6271021/index.html" target="_blank"><img src="/web/bzz_index/images/logo/2.jpg" width="121" height="56"  border="0"/></a></td>
        <td align="center"><a href="http://kxfzg.chinamobile.com/" target="_blank"><img src="/web/bzz_index/images/logo/3.jpg" width="121" height="56"  border="0"/></a></td>
        <td align="center"><a href="http://www.airchinagroup.com/" target="_blank"><img src="/web/bzz_index/images/logo/4.jpg" width="121" height="56"  border="0"/></a></td>
        <td align="center"><a href="http://www.airchinagroup.com/" target="_blank"><img src="/web/bzz_index/images/logo/5.jpg" width="121" height="56"  border="0"/></a></td>
        <td align="center"><a href="http://www.cnooc.com.cn/" target="_blank"><img src="/web/bzz_index/images/logo/6.jpg" width="121" height="56"  border="0"/></a></td>
        <td align="center"><a href="http://www.cnpc.com.cn/" target="_blank"><img src="/web/bzz_index/images/logo/7.jpg" width="121" height="56"  border="0"/></a></td>
        <td align="center"><a href="http://www.airchinagroup.com/" target="_blank"><img src="/web/bzz_index/images/logo/5.jpg" width="121" height="56"  border="0"/></a></td>
        <td align="center"><a href="http://www.cnooc.com.cn/" target="_blank"><img src="/web/bzz_index/images/logo/6.jpg" width="121" height="56"  border="0"/></a></td>
        <td align="center"><a href="http://www.cnpc.com.cn/" target="_blank"><img src="/web/bzz_index/images/logo/7.jpg" width="121" height="56"  border="0"/></a></td>
      </tr>
    </table></TD>
                <TD id="demo2" vAlign="top"></TD></TR></TABLE></DIV>
          
 <SCRIPT language="javascript">
        var speed=5
        demo2.innerHTML=demo1.innerHTML
        function Marquee(){
        if(demo2.offsetWidth-demo.scrollLeft<=0)
        demo.scrollLeft-=demo1.offsetWidth
        else{
        demo.scrollLeft++
        }
        }
        var MyMar=setInterval(Marquee,speed)
        demo.onmouseover=function() {clearInterval(MyMar)}
        demo.onmouseout=function() {MyMar=setInterval(Marquee,speed)}

</SCRIPT></td>
    <td></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="54" align="center" background="/web/bzz_index/images/index/index_r23_c2.jpg" class="down">版权所有：生殖健康咨询师培训网    京ICP备05046845号<br />报名注册：010-62797946  网络课程：010-62796133  扩展服务：010-62786820   投诉监督：010-62796475<br/>
    投诉：010-62786820     传真：010-62789770     技术客服热线电话： 010-58731118转254</td>
  </tr>
</table>
</body>
</html>
