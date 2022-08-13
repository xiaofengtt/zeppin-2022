<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
 %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<base href="<%=basePath%>">
<title>生殖健康咨询师培训网</title>
<link href="/web/bzz_index/style/index.css" rel="stylesheet" type="text/css">
<link href="/web/zhibo/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
<script type="text/javascript">
function myonload(){
MM_preloadImages('/web/bzz_index/images/anniu_over_03.jpg','/web/bzz_index/images/anniu_over_05.jpg');
<s:if test="loginErrMessage!=null&& loginErrMessage!='clear'">
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

<body onLoad="myonload()">
<div id="pic1" style="position:absolute; visibility:visible; left:0px; top:0px; z-index:5; width: 110; height: 97;">
<a href="http://www.thbzzpx.org/entity/first/firstBulletin_viewDetail.action?bean.id=ff80808125d2fe470125d32b8f13006a" target="_blank"><img src="web/bzz_index/images/fudong_03_5.jpg" width="110" height="97" border="0"></a>
<map name="Map">
  <area shape="rect" coords="6,40,105,60" href="http://www.thbzzpx.org/entity/first/firstBulletin_viewDetail.action?bean.id=ff80808125d2fe470125d32b8f13006a" target="_blank">
<!--<area shape="rect" coords="6,36,105,59" href="yyfw/jpkj/hudong/index.html" target="_blank">
<area shape="rect" coords="5,64,106,89" href="whatyflash/kjgj.html" target="_blank">
<area shape="rect" coords="39,56,40,57" href="#"><area shape="rect" coords="109,8,172,91" href="http://jpkc.whaty.cn/ljysb/index.html" target="_blank"> -->
</map>  </div>
<SCRIPT LANGUAGE="JavaScript">
<!-- Begin
var x = 50,y = 60 
var xin = true, yin = true 
var step = 1 
var delay = 10 
var obj=document.getElementById("pic1") 
function getwindowsize() { 
var L=T=0
//by www.qpsh.com
var R= document.body.clientWidth-obj.offsetWidth 
var B = document.body.clientHeight-obj.offsetHeight 
obj.style.left = x + document.body.scrollLeft 
obj.style.top = y + document.body.scrollTop 
x = x + step*(xin?1:-1) 
if (x < L) { xin = true; x = L} 
if (x > R){ xin = false; x = R} 
y = y + step*(yin?1:-1) 
if (y < T) { yin = true; y = T } 
if (y > B) { yin = false; y = B } 
} 
var itl= setInterval("getwindowsize()", delay) 
obj.onmouseover=function(){clearInterval(itl)} 
obj.onmouseout=function(){itl=setInterval("getwindowsize()", delay)} 
//  End -->
</script>
<!-- <div id="pic1" style="position:absolute; visibility:visible; left:0px; top:0px; z-index:5; width: 110; height: 97;"><img src="web/bzz_index/images/zlkj1.jpg" width="110" height="97" border="0" usemap="#Map">
<map name="Map">
  <area shape="rect" coords="6,8,105,32" href="http://chat16.live800.com/live800/chatClient/chatbox.jsp?companyID=96531&jid=4313005661" target="_blank">
<area shape="rect" coords="6,36,105,59" href="yyfw/jpkj/hudong/index.html" target="_blank">
<area shape="rect" coords="5,64,106,89" href="whatyflash/kjgj.html" target="_blank">
<area shape="rect" coords="39,56,40,57" href="#"></map>  </div>  
<SCRIPT LANGUAGE="JavaScript">
<!-- Begin
var x = 50,y = 60 
var xin = true, yin = true 
var step = 1 
var delay = 10 
var obj=document.getElementById("pic1") 
function getwindowsize() { 
var L=T=0
//by www.qpsh.com
var R= document.body.clientWidth-obj.offsetWidth 
var B = document.body.clientHeight-obj.offsetHeight 
obj.style.left = x + document.body.scrollLeft 
obj.style.top = y + document.body.scrollTop;
x = x + step*(xin?1:-1) 
if (x < L) { xin = true; x = L} 
if (x > R){ xin = false; x = R} 
y = y + step*(yin?1:-1) 
if (y < T) { yin = true; y = T } 
if (y > B) { yin = false; y = B } 
} 

var itl= setInterval("getwindowsize()", delay) 
obj.onmouseover=function(){clearInterval(itl)} 
obj.onmouseout=function(){itl=setInterval("getwindowsize()", delay)} 
//  End -- >
</script>-->
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td height="209"><iframe id="top" name="top" frameborder="0" width="1002" height="209" scrolling="no" src="/web/bzz_index/top.jsp"></iframe></td>
  </tr>
  <tr>
    <td align="center"><table width="932" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="295" align="left" valign="top"><table width="280" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="41" valign="top" background="/web/bzz_index/images/left_03.jpg" class="font" id="topTimeBox"  style="padding:0px 0px 0px 5px;">
            <script language=JavaScript>
<!--
var d=new initArray("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
var today=new Date();
var date;
function initArray()
{
	this.length=initArray.arguments.length;
	for(var i=0;i<this.length;i++)
	{
		this[i+1]=initArray.arguments[i];
	}
}
date = today.getYear()+"年"+(today.getMonth()+1)+"月"+today.getDate()+"日 "+d[today.getDay()+1];
function showtime()
{
	if (!document.layers&&!document.all)
	return;
	var mydate=new Date();
	var hours=mydate.getHours();
	var minutes=mydate.getMinutes();
	var dn=" AM";
	if (hours>12){
	dn=" PM";
	hours=hours-12;
	}
	if (hours==0)
	hours=12;
	if (minutes<=9)
	minutes="0"+minutes;
	//change font size here to your desire           
	myclock="  "+hours+":"+minutes+dn;
	if (document.layers)
	{
		document.layers.liveclock.document.write(myclock);
		document.layers.liveclock.document.close();
	}
	else if (document.all)
	{
		document.getElementById("topTimeBox").innerText = date+myclock;
	}
}
showtime();
setInterval("showtime()",5000);
//-->           
          </script></td>
          </tr>
          <tr>
            <td align="center" background="/web/bzz_index/images/left_05.jpg"><table width="90%" border="0" cellspacing="0" cellpadding="0">
 	
 	<s:if test="Bulletin.size() > 0">
 	<s:append id="list3">
				<s:param value="Bulletin"></s:param>	
						<s:param value="{0,0,0,0,0}"></s:param>
			</s:append>
	</s:if><s:else>
				<s:append id="list3">
				<s:param value="{0,0,0,0,0,0,0}"></s:param>
				</s:append>
	</s:else>								
									<s:iterator id="BulletinList" value="#list3"
										status="bulletin">
										<s:if test="#bulletin.getIndex()<6">
										<s:if test="#this==0">
										<tr>
											<td align="left" ></td>
										</tr>
										</s:if>
										<s:else >
										<tr>
										<td class="font" align="left"><a href="/entity/first/firstBulletin_viewDetail.action?bean.id=<s:property value="id"/>" target="_blank"><span class="font_bq">■</span><s:if test="title.length()>18"><s:property value="title.substring(0,18)" />...</s:if><s:else><s:property value="title" /></s:else></a></td>
										</tr>
										</s:else>
										</s:if>
									</s:iterator>
</table>
<div class="font" align="right" style="padding:0px 10px 0px 0px;"><a href="/entity/first/firstBulletin_allBulletin.action" target="_blank">更多＞＞</a></div>
</td>
          </tr>
          <tr>
            <td><img src="/web/bzz_index/images/left_07.jpg" width="280" height="19"></td>
          </tr>
        </table>
          <table width="279" border="0" cellspacing="0" cellpadding="0" style="margin:5px 0px;">
            <tr>
              <td><img src="/web/bzz_index/images/left_10.jpg" width="279" height="30"></td>
            </tr>
            <tr><form id="loginform" name="loginform" method="post" action="/sso/login_login.action" target="_self" >
              <td align="center" background="/web/bzz_index/images/left_12.jpg" style="padding:5px 0px;"><table width="78%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="27%" align="center" class="dengl_font">帐　号：</td>
                  <td width="73%" height="25">                    <input name="loginId" type="text" class="input">                </td>
                </tr>
                <tr>
                  <td align="center" class="dengl_font">密　码：</td>
                  <td height="28"><input name="passwd" type="password" class="input"></td>
                </tr>
                <tr>
                  <td align="center" class="dengl_font">验　证：</td>
                  <td height="25"><input name="authCode" type="text" class="input" style="width:96px;">  <img src="/sso/authimg" width="50" height="20" align="absmiddle"></td>
                </tr>
				<tr>
				 <td colspan="2" align="center" height="25">
				 <input type="image" src="/web/bzz_index/images/denglu_out_05.jpg" width="84" height="25" hspace="5" vspace="5" border="0" id="Image2" onMouseOver="MM_swapImage('Image2','','/web/bzz_index/images/anniu_over_05.jpg',1)" onMouseOut="MM_swapImgRestore()"/>
                  <a href="/web/bzz_index/czmm/find.jsp" target="_blank"><img src="/web/bzz_index/images/denglu_out_03.jpg" width="84" height="25" hspace="5" vspace="5" border="0" id="Image1" onMouseOver="MM_swapImage('Image1','','/web/bzz_index/images/anniu_over_03.jpg',1)" onMouseOut="MM_swapImgRestore()"></a>
                <!--   <a href="javascript:window.document.loginform.submit()"><img src="/web/bzz_index/images/denglu_out_05.jpg" width="84" height="25" hspace="5" vspace="5" border="0" id="Image2" onMouseOver="MM_swapImage('Image2','','/web/bzz_index/images/anniu_over_05.jpg',1)" onMouseOut="MM_swapImgRestore()"></a> -->
                  </td>
                </tr>
              </table></td>
            </tr></form>
            <tr>
              <td height="14"><img src="/web/bzz_index/images/left_14.jpg" width="279" height="14"></td>
            </tr>
          </table>
          <table width="282" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="/web/bzz_index/images/left_16.jpg" width="282" height="31" border="0" usemap="#Map"></td>
            </tr>
            <tr>
              <td><table width="95%" border="0" align="center" cellpadding="0" cellspacing="2" bgcolor="#DEECF9" style="margin:3px 0px; padding:10px 0px;">
                <tr>
                  <td align="center" bgcolor="#FFFFFF"><table width="88%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td height="27" class="xz_font" align="left"><a href="/web/bzz_index/files/xydjb.xls"><img src="/web/bzz_index/images/left_20.jpg" width="4" height="6" border="0" align="absmiddle"> 学员报名信息登记表</a></td>
                        <td class="xz_font"><a href="/web/bzz_index/files/bksqb.doc"><img src="/web/bzz_index/images/left_20.jpg" width="4" height="6" border="0" align="absmiddle"> 补考申请表</a></td>
                      </tr>
                      <tr>
                        <td height="27" class="xz_font" align="left"><a href="/web/bzz_index/files/zpmb.doc"><img src="/web/bzz_index/images/left_20.jpg" width="4" height="6" border="0" align="absmiddle"> 照片模板 </a></td>
                        <td class="xz_font"><a href="/web/bzz_index/files/hksqb.doc"><img src="/web/bzz_index/images/left_20.jpg" width="4" height="6" border="0" align="absmiddle"> 缓考申请表</a></td>
                      </tr>
                     <tr>
                        <td height="27" class="xz_font" align="left"><a href="/web/bzz_index/player_download.jsp" target="_blank"><img src="/web/bzz_index/images/left_20.jpg" width="4" height="6" border="0" align="absmiddle"> 常用软件下载</a></td>
                        <td class="xz_font">&nbsp;</td>
                      </tr> 
                    </table></td>
                </tr>
              </table></td>
            </tr>
          </table>
          <table width="280" border="0" cellspacing="0" cellpadding="0" style="margin:5px 0px;">
            <tr>
              <td><img src="/web/bzz_index/images/left_24.jpg" width="280" height="31" border="0" usemap="#MapMap"></td>
            </tr>
            <tr>
              <td style="padding:10px 0px 0px 0px;"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                  <td align="center" class="boder"><img src="/web/bzz_index/msfc/images/dongquan.jpg" width="81" height="108"></td>
                  <td align="center">&nbsp;</td>
                  <td align="center" class="boder"><img src="/web/bzz_index/msfc/images/huangtie.jpg" width="81" height="108"></td>
                  <td align="center">&nbsp;</td>
                  <td align="center" class="boder"><img src="/web/bzz_index/msfc/images/liudacheng.jpg" width="81" height="108"></td>
                </tr>
                <tr>
                  <td height="28" align="center" class="xz_font"><a href="/web/bzz_index/msfc/dongquan.jsp" target="_blank">董&nbsp;&nbsp;权</a></td>
                  <td align="center" class="xz_font">&nbsp;</td>
                  <td align="center" class="xz_font"><a href="/web/bzz_index/msfc/huangtie.jsp" target="_blank">黄&nbsp;&nbsp;铁</a></td>
                  <td align="center" class="xz_font">&nbsp;</td>
                  <td align="center" class="xz_font"><a href="/web/bzz_index/msfc/liudacheng.jsp" target="_blank">刘大成</a></td>
                </tr>
              </table></td>
            </tr>
          </table>
          <map name="MapMap">
            <area shape="rect" coords="230,4,269,22" href="/web/bzz_index/msfc/msfc_new.jsp" target="_blank">
          </map>
          </td>
        <td valign="top"><table width="637" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><img src="/web/bzz_index/images/main_03.jpg" width="637" height="27" border="0" usemap="#Map2"></td>
          </tr>
          <tr>
            <td><table width="637" border="0" cellspacing="0" cellpadding="0" style="margin:5px 0px 0px 0px;">
               <tr>
                <td width="250"><table width="250" border="0" cellspacing="0" cellpadding="0">
                <s:if test="tpnewsList.size()>0">
                  <s:iterator id="tpnewsList" value="tpnewsList" status="tpnews">
                	<input id="pic" type="hidden" name="pic" value="<s:property value="pictrue"/>"/>
                	<input id="title" type="hidden" name="title" value="<s:if test="title.length()>16"><s:property value="title.substring(0,16)" />...</s:if><s:else><s:property value="title" /></s:else>"/>
                	<input id="pid" type="hidden" name="pid" value="<s:property value="id"/>"/>
                </s:iterator>
                </s:if><s:else>
                	<input id="pic" type="hidden" name="pic" value=""/>
                	<input id="title" type="hidden" name="title" value=""/>
                	<input id="pid" type="hidden" name="pid" value=""/>
                </s:else>
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
					var focus_width=250;
					var focus_height=184;
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
                  
                </table></td>
                <td align="right" valign="top"><table width="96%" border="0" cellspacing="0" cellpadding="0">
                 <s:iterator id="newsList" value="newsList" status="news">
                  <tr>
                    <td class="new_font01" align="left"><a href="/entity/first/firstInfoNews_toInfoNews.action?bean.id=<s:property value="id"/>" target="_blank"><img src="/web/bzz_index/images/left_20.jpg" width="4" height="6" border="0" align="absmiddle"><s:if test="title.length()>20"><s:property value="title.substring(0,20)" />...</s:if><s:else><s:property value="title" /></s:else></a>
                    <s:if test="isNew==1">
	                	<img alt="new" src="/web/bzz_index/images/new.gif" />
                	</s:if>
                	</td>
                    <td class="font">[<s:date name="submitDate"  format="yyyy-MM-dd"/>]</td>
                  </tr>
                  </s:iterator>
                </table></td>
              </tr>
            </table></td>
          </tr>
        </table>
          <table width="637" border="0" cellspacing="0" cellpadding="0" style="margin:5px 0px;">
            <tr>
              <td><img src="/web/bzz_index/images/main_10.jpg" width="637" height="19" border="0" usemap="#Map3"></td>
            </tr>
            <tr>
              <td><table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin:5px 0px;">
                <tr>
                  <td width="7"><img src="/web/bzz_index/images/main_13.jpg" width="7" height="91"></td>
                  <td align="center" background="/web/bzz_index/images/main_14.jpg"><img src="/web/bzz_index/images/main_19_1.jpg" width="606" height="55" border="0" usemap="#Map5"></td>
                  <td width="6" align="right"><img src="/web/bzz_index/images/main_16.jpg" width="6" height="91"></td>
                </tr>
              </table></td>
            </tr>
          </table>
          <table width="637" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><table width="429" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td><img src="/web/bzz_index/images/main_25.jpg" width="429" height="47"></td>
                </tr>
                <tr>
                  <td><table width="429" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                      <td width="14"><img src="/web/bzz_index/images/main_29.jpg" width="14" height="306"></td>
                      <td align="center" valign="top" background="/web/bzz_index/images/main_31.jpg" style="padding:8px 0px 0px 0px;"><table width="95%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                        <td width="10" align="left">&nbsp;&nbsp;&nbsp;&nbsp;</td>
                          <td width="38" align="left"><img src="/web/bzz_index/images/main_35.jpg" width="30" height="81"></td>
                          <td align="left" class="kczs">课程名称：<strong>企业文化与班组团队管理&nbsp;&nbsp;<a href="http://www.thbzzpx.org/CourseDemo/qywh_new/index.htm" target="_blank">[查看演示]</a></strong><br>
                            授课教师：<span class="kczs02"><strong>蒋勇</strong></span><br>                            <span class="font">本课程通过多媒体手段，多种形式并用，使课程更贴近企业基层的实际。</span><a href="#"><IMG src="/web/bzz_index/images/4.jpg" 
                              width=40 
                              height=11 border="0"></a></td>
                        </tr>
                        <tr>
                          <td height="8" colspan="2" align="left"></td>
                          </tr>
                        <tr>
                         <td width="10" align="left">&nbsp;&nbsp;&nbsp;&nbsp;</td>
                          <td align="left"><img src="/web/bzz_index/images/main_38.jpg" width="30" height="81"></td>
                          <td align="left"><span class="kczs">课程名称：<strong>认识班组长&nbsp;&nbsp;<a href="http://www.thbzzpx.org/CourseDemo/rsbzz/index.htm" target="_blank">[查看演示]</a></strong><br>
授课教师：</span><span class="kczs02"><strong>李飞龙</strong></span><span class="kczs"><br>
<span class="font">介绍班组长在企业中的定位与作用、班组长的基本任务与职责，掌握角色认知与转换的有关知识与技巧。</span><a href="#"><IMG src="/web/bzz_index/images/4.jpg" 
                              width=40 
                              height=11 border="0"></a></span></td>
                        </tr>
                        <tr>
                          <td height="8" colspan="2" align="left"></td>
                          </tr>
                        <tr>
                         <td width="10" align="left">&nbsp;&nbsp;&nbsp;&nbsp;</td>
                          <td align="left"><img src="/web/bzz_index/images/main_45.jpg" width="30" height="80"></td>
                          <td align="left" ><span class="kczs">课程名称：<strong>班组质量管理&nbsp;&nbsp;<a href="http://www.thbzzpx.org/CourseDemo/bzzlgl/3/index.htm" target="_blank">[查看演示]</a></strong><br>授课教师：</span><span class="kczs02"><strong>孙静</strong></span><span class="kczs"><br><span class="font">介绍当代的质量概念和质量管理理念，了解它们的发展历程，正确认识质量管理体系及ISO9000族标准的价值，讲解了服务的分析方法.。</span><a href="#"><IMG src="/web/bzz_index/images/4.jpg" 
                              width=40 
                              height=11 border="0"></a></span></td>
                        </tr>
                      </table></td>
                      <td width="12"><img src="/web/bzz_index/images/main_33.jpg" width="12" height="306"></td>
                    </tr>
                  </table></td>
                </tr>
              </table></td>
              <td align="right" valign="top"><table width="193" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td><img src="/web/bzz_index/images/main_27.jpg" width="193" height="31" vspace="5" border="0" usemap="#Map4"></td>
                </tr>
                <tr>
                  <td><table width="95%" border="0" align="center" cellpadding="0" cellspacing="2" bgcolor="#DEECF9" style="margin:5px 0px; padding:15px 0px;">
                    <tr>
                      <td align="center" bgcolor="#FFFFFF" style="padding:15px 0px 20px 0px;"><table width="88%" border="0" cellspacing="0" cellpadding="0">
                         <tr>
         <s:iterator value="bzfc" status="bz"> 
         <s:if test="#bz.getIndex()<2">
            <td align="center">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="indextea1">
                <tr>
                 <td height="25" class="yxbzz">
                  <s:if test="id==1">
                  	<a href="#" target="_blank"><img src="/web/bzz_index/images/mainbq_38.jpg" width="6" height="7" border="0" align="absmiddle"><s:property value="title"/></a>
                  </s:if><s:else>
                   <a target="_blank" href="/entity/first/firstInfoNews_toInfoDetail.action?bean.id=<s:property value="id"/>" target="_blank"><img src="/web/bzz_index/images/mainbq_38.jpg" width="6" height="7" border="0" align="absmiddle"><s:property value="title"/></a>
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
         <s:if test="#bz.getIndex()>1&&#bz.getIndex()<4">
            <td align="center">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="indextea1">
                <tr>
                  <td height="25" class="yxbzz">
                  <s:if test="id==1">
                  	<a href="#" target="_blank"><img src="/web/bzz_index/images/mainbq_38.jpg" width="6" height="7" border="0" align="absmiddle"><s:property value="title"/></a>
                  </s:if><s:else>
                   <a target="_blank" href="/entity/first/firstInfoNews_toInfoDetail.action?bean.id=<s:property value="id"/>" target="_blank"><img src="/web/bzz_index/images/mainbq_38.jpg" width="6" height="7" border="0" align="absmiddle"><s:property value="title"/></a>
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
         <s:if test="#bz.getIndex()>3&&#bz.getIndex()<6">
            <td align="center">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="indextea1">
                <tr>
                <td height="25" class="yxbzz">
                  <s:if test="id==1">
                  	<a href="#" target="_blank"><img src="/web/bzz_index/images/mainbq_38.jpg" width="6" height="7" border="0" align="absmiddle"><s:property value="title"/></a>
                  </s:if><s:else>
                   <a target="_blank" href="/entity/first/firstInfoNews_toInfoDetail.action?bean.id=<s:property value="id"/>" target="_blank"><img src="/web/bzz_index/images/mainbq_38.jpg" width="6" height="7" border="0" align="absmiddle"><s:property value="title"/></a>
                  </s:else>
                 </td>
                </tr>
            </table>
            </td>
            </s:if> 
        </s:iterator>           
          </tr>
                      </table></td>
                    </tr>
                  </table></td>
                </tr>
              </table>
                <table width="193" border="0" cellspacing="0" cellpadding="0" style="margin:6px 0px 0px 0px;">
                  <tr>
                    <td><img src="/web/bzz_index/images/main_39.jpg" width="193" height="31" vspace="6"></td>
                  </tr>
                  <tr>
                    <td><a href="http://www.sasac.gov.cn/" target="_blank"><img src="/web/bzz_index/images/main_47.jpg" width="190" height="60" vspace="7" border="0"></a></td>
                  </tr>
                  <tr>
                    <td><a href="http://www.tsinghua.edu.cn/qhdwzy/index.jsp" target="_blank"><img src="/web/bzz_index/images/main_42.jpg" width="190" height="58" vspace="7" border="0"></a></td>
                  </tr>
                </table></td>
            </tr>
          </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><iframe id="foot" name="foot" frameborder="0" width="1002" height="147" scrolling="no" src="/web/bzz_index/bottom.jsp"></iframe></td>
  </tr>
</table>
<map name="Map"><area shape="rect" coords="230,4,269,22" href="/web/bzz_index/cjzlxz/zlxz.jsp"></map>
<map name="Map2"><area shape="rect" coords="576,3,641,24" target="_blank" href="/entity/first/firstInfoNews_allNews.action"></map>
<map name="Map3">
<area shape="rect" coords="584,1,635,18" href="/web/bzz_index/xytd/xytd.jsp" target="_parent">
</map>
<map name="Map4"><area shape="rect" coords="145,6,185,24" href="/web/bzz_index/yxbzz/yxbzz.jsp" target="_blank"></map>
<map name="Map5">
<area shape="rect" coords="1,-1,90,57" href="/web/bzz_index/xytd/xytd.jsp?src=xxyxz&menu=menu0">
<area shape="rect" coords="111,0,190,58" href="/web/bzz_index/xytd/xytd.jsp?src=zxxx&menu=menu1">
<area shape="rect" coords="217,0,296,57" href="/web/bzz_index/xytd/xytd.jsp?src=zyzc&menu=menu2">
<area shape="rect" coords="323,-1,400,54" href="/web/bzz_index/xytd/xytd.jsp?src=fddy&menu=menu3">
<area shape="rect" coords="430,1,506,60" href="/web/bzz_index/xytd/xytd.jsp?src=khrz&menu=menu4">
<area shape="rect" coords="530,1,606,60" href="/web/bzz_index/xytd/xytd.jsp?src=xysc&menu=menu5"></map>
<!-- <map name="Map5"><area shape="rect" coords="0,0,105,55" href="/web/bzz_index/xytd/xytd.jsp?src=xxyxz&menu=menu0"><area shape="rect" coords="124,0,229,55" href="/web/bzz_index/xytd/xytd.jsp?src=pxdg&menu=menu1"><area shape="rect" coords="249,0,353,55" href="/web/bzz_index/xytd/xytd.jsp?src=kcjs&menu=menu3"><area shape="rect" coords="374,0,479,54" href="/web/bzz_index/xytd/xytd.jsp?src=xysc&menu=menu4"><area shape="rect" coords="501,0,606,55" href="/web/bzz_index/xytd/xytd.jsp?src=tjjc&menu=menu5"></map> -->
<!-- Live800在线客服图标:首页图标代码[浮动型] 开始-->
<div style='display:none;'><a href='http://www.live800.com'>在线客服</a></div><script language="javascript" src="http://chat16.live800.com/live800/chatClient/floatButton.js?jid=4313005661&companyID=96531&configID=4417&codeType=custom"></script><div style='display:none;'><a href='http://www.live800.com'>在线客服系统</a></div>
<!-- Live800在线客服图标:首页图标代码[浮动型] 结束-->
<!-- Live800默认跟踪代码: 开始-->
<script language="javascript" src="http://chat16.live800.com/live800/chatClient/monitor.js?jid=4313005661&companyID=96531&configID=3508&codeType=custom"></script>
<!-- Live800默认跟踪代码: 结束-->
<!-- 页面流量统计 -->
<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
try {
var pageTracker = _gat._getTracker("UA-5016971-3");
pageTracker._trackPageview();
} catch(err) {}</script> 

<div id="pic2" style="position:absolute; visibility:visible; left:0px; top:0px; z-index:5; width: 110; height: 97;">
 <form id="loginform" name="loginform" method="post" action="/web/zhibo/zhibo_voteexe.jsp" target="_blank" >
  <table width="290" height="230" border="0" cellpadding="0" cellspacing="1" bgcolor="#e28016">
    <tr>
      <td valign="top" bgcolor="#FFFFFF"><table width="100%" height="28" border="0" cellpadding="0" cellspacing="0" background="/web/zhibo/images/k_03.jpg">
        <tr>
          <td width="81%" class="diaocha">学费认领(重要!!)</td>
          <td width="19%" valign="top"><img src="/web/zhibo/images/k_04.jpg" width="34" height="19" onClick="pic2.style.display='none'" style="cursor:pointer"></td>
        </tr>
      </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="170" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="40" class="title3">　<br>以下汇款单位都是以姓名汇出，至今未收到汇款凭证传真件，学员发票无法开据。
                工作人员从系统中未查询到汇款人，现发布公告，请速与我们联系（62797946）或将汇款凭证传真（62789770）并写明发票抬头，收件人，联系电话，邮寄地址。
               <br>请下载附件查看。
                <br><br>
                <a href="/web/bzz_index/files/jfrl.xls">附件下载</a></td>
              </tr>
            </table></td>
          </tr>
        </table>
       </td>
    </tr>
  </table>
  </form>
</div>

</body>
</html>


