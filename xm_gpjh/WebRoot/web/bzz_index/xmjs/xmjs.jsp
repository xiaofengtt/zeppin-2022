<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<base href="<%=basePath%>">
<title>生殖健康咨询师培训网</title>
<link href="/web/bzz_index/style/index.css" rel="stylesheet" type="text/css">
<link href="/web/bzz_index/style/xmjs.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/web/bzz_index/js/wsMenu.js"></script>
<script language="javascript">
function resize()
{
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
}
</script>
<script type="text/javascript"> 
function copyText(obj) 
{ 
var rng = document.body.createTextRange(); 
rng.moveToElementText(obj); 
rng.scrollIntoView(); 
rng.select(); 
rng.execCommand("Copy"); 
rng.collapse(false);} 

function reinitIframe(){

var iframe = document.getElementById("xytd");

try{

var bHeight = iframe.contentWindow.document.body.scrollHeight;

var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;

var height = Math.max(bHeight, dHeight);

iframe.height = height;

}catch (ex){}

}

window.setInterval("reinitIframe()", 200);

</script>
 <SCRIPT   LANGUAGE="JavaScript">
<!--
olddiv="9"
function menuclick(str){
document.getElementById("td"+olddiv).className="topmenu03";
document.getElementById("td"+str).className="topmenu031";
olddiv=str;
}
function menuover(str){
if(str!=olddiv)
{
document.getElementById("td"+str).className="topmenu031";
}
}
function menuout(str){

if(str!=olddiv)
{document.getElementById("td"+str).className="topmenu03";}

}

function clearAllChild()
{
	for(var i=1;i<5;i++)
	{
		document.getElementById("child"+i).style.display="none";
	}
	document.getElementById("child3img").src="/web/bzz_index/xmjs/images/zx_07.gif";
}
/-->
</SCRIPT> 
<script language="javascript">
var preObj = new Object();
function overM(obj)
{
	obj.className = "top2";
}
function outM(obj)
{
	if(obj.on != "true")
		obj.className = "top1";
}
function gotoM(URL)
{
	if(preObj && preObj!=event.srcElement)
	{
		preObj.on = false;
		outM(preObj);
	}
	if(event.srcElement)
	{
		preObj = event.srcElement;
	event.srcElement.on = "true";
	}
	//
	var tempURL = parent.document.getElementById("proBox").src;
	tempURL = tempURL.substring(0,tempURL.indexOf("/")+1);
	parent.document.getElementById("pageBox").src = tempURL+URL;
}
//
function SH(id)
{
	if(document.getElementById(id))
	document.getElementById(id).style.display = (document.getElementById(id).style.display=="none")?"block":"none";
}
</script>
<script language="JavaScript">
function show(DivId)
{
	if(document.all[DivId].style.display=='none')
	  { 
	  document.all[DivId].style.display='' 
	  	 if(DivId=="child3"){
		 document.getElementById("child3img").src="/web/bzz_index/xmjs/images/zx_071.gif";
	     }
	  }
	else
	  { 
	  document.all[DivId].style.display='none'
	 document.getElementById("child3img").src="/web/bzz_index/xmjs/images/zx_07.gif"; 	
	     
	  }
	  

	return 0;
}
</script>
</head>

<body>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td height="209"><iframe id="top" name="top" frameborder="0" width="1002" height="209" scrolling="no" src="/web/bzz_index/top.jsp"></iframe></td>
  </tr>
  <tr>
    <td align="center" class="body_box"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="218" valign="top" bgcolor="#9FC3FF"><img src="/web/bzz_index/xmjs/images/l_t.jpg" width="218" height="8"></td>
        <td width="16" rowspan="3"></td>
        <td width="696" rowspan="3" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
           <tr> 
                <td height="27" background="/web/bzz_index/images/index/index_r5_c007.jpg"> 
                  <div align="left">
                    <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
                      <tr> 
                        <td width="6%">&nbsp;</td>
                        <td width="94%"><font color="#000066" size="3"><strong>项目介绍</strong></font></td>
                      </tr>
                    </table>
                  </div></td>
              </tr>
        </table>
        <iframe id="xytd" name="xytd" allowtransparency="yes" frameborder="0" width="100%" height="100%" scrolling="no" src="/web/bzz_index/xmjs/subpage.jsp?type=_ycjyjs&title=清华大学现代远程教育简介" onLoad="resize()"></iframe></td>
      </tr>
     <tr bgcolor="#9FC3FF">
        <td valign="top"><br>
            <table width="100" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="/web/bzz_index/xmjs/images/list_t.jpg" width="206" height="70"></td>
          </tr>
          <tr>
            <td background="/web/bzz_index/xmjs/images/list_bg.jpg">
				<div onClick="clearAllChild();document.all.child1.style.display=(document.all.child1.style.display =='none')?'':'none';" style="cursor:pointer;">
<table width="160" border="0" align="center" cellpadding="0" cellspacing="0" style="margin:0px 0px 0px 2px;">
  <tr>
    <td height="35"  class="zj"><a href="/web/bzz_index/xmjs/subpage.jsp?type=_ycjyjs&title=清华大学现代远程教育简介" target="xytd"><img src="/web/bzz_index/xmjs/images/fwzx_07.gif" width="11" height="11" border="0" align="absmiddle"> 清华远程教育简介</a></td>
  </tr>
  <tr><td height="3" class="bottomline"> </td></tr>
</table>
</div>
<div id="child1" style="display:none;"></div>

<!--第二章-->
<div onClick="clearAllChild();document.all.child2.style.display=(document.all.child2.style.display =='none')?'':'none';" style="cursor:pointer;">
  <table width="160" border="0" align="center" cellpadding="0" cellspacing="0" style="margin:0px 0px 0px 2px;">
    <tr>
      <td width="160" height="35"  class="zj"><a href="/web/bzz_index/xmjs/subpage.jsp?type=_xmbj&title=项目背景" target="xytd"><img src="/web/bzz_index/xmjs/images/fwzx_07.gif" width="11" height="11" border="0" align="absmiddle"> 项目背景</a></td>
    </tr>
    <tr>
      <td height="3" class="bottomline"></td>
    </tr>
  </table>
</div>
<div id="child2" style="display:none;"></div>
<!--第三章-->
<div  onclick="show('child3');" style="cursor:pointer;">
  <table width="160" border="0" align="center" cellpadding="0" cellspacing="0" style="margin:0px 0px 0px 2px;">
    <tr>
      <td width="160" height="35"  class="zj"><a href="/web/bzz_index/xmjs/zynr.jsp" target="xytd"><img src="/web/bzz_index/xmjs/images/zx_07.gif" name="child3img" width="11" height="11" border="0" align="absmiddle" id="child3img"> 项目主要内容</a></td>
    </tr>
    <tr>
      <td height="3" class="bottomline"></td>
    </tr>
  </table>
</div>
<div id="child3" style="display:none;">
<table width="160" border="0" align="center" cellpadding="0" cellspacing="1" bordercolor="#CCCCCC" bgcolor="#E1E1E1" class="Snav" style="margin:0px 0px 0px 2px;">
    <tr>
    <td width="160" height="30" bordercolor="#CCCCCC" background="/web/bzz_index/xmjs/images/part-1-1_08.jpg" bgcolor="#FFFFFF" class="topmenu03" id="td1">　<img src="/web/bzz_index/xmjs/images/xj.gif" width="10" height="7" align="absmiddle"> &nbsp;<a href="/web/bzz_index/xmjs/subpage.jsp?type=_sjsl&title=课程设计思路" target="xytd">课程设计思路</a></td>
  </tr>
  <tr background="/web/bzz_index/xmjs/images/part-1-1_08.jpg">
    <td width="160" height="30" background="/web/bzz_index/xmjs/images/part-1-1_08.jpg" bgcolor="#FFFFFF"><span class="topmenu03">　<img src="/web/bzz_index/xmjs/images/xj.gif" width="10" height="7" align="absmiddle"> &nbsp;</span><a href="/web/bzz_index/xmjs/subpage.jsp?type=_kcmb&title=课程目标" target="xytd">课程目标</a></td>
  </tr>
  <tr background="/web/bzz_index/xmjs/images/part-1-1_08.jpg">
    <td width="160" height="30" background="/web/bzz_index/xmjs/images/part-1-1_08.jpg" bgcolor="#FFFFFF"><span class="topmenu03">　<img src="/web/bzz_index/xmjs/images/xj.gif" width="10" height="7" align="absmiddle"> &nbsp;</span><a href="/web/bzz_index/xmjs/subpage.jsp?type=_kcnr&title=课程内容" target="xytd">课程内容</a></td>
  </tr>
  <tr background="/web/bzz_index/xmjs/images/part-1-1_08.jpg">
    <td width="160" height="30" background="/web/bzz_index/xmjs/images/part-1-1_08.jpg" bgcolor="#FFFFFF"><span class="topmenu03">　<img src="/web/bzz_index/xmjs/images/xj.gif" width="10" height="7" align="absmiddle"> &nbsp;</span><a href="/web/bzz_index/xmjs/subpage.jsp?type=_kcsz&title=课程师资" target="xytd">课程师资</a></td>
  </tr>
  <tr background="/web/bzz_index/xmjs/images/part-1-1_08.jpg">
    <td width="160" height="30" background="/web/bzz_index/xmjs/images/part-1-1_08.jpg" bgcolor="#FFFFFF"><span class="topmenu03">　<img src="/web/bzz_index/xmjs/images/xj.gif" width="10" height="7" align="absmiddle"> &nbsp;</span><a href="/web/bzz_index/xmjs/subpage.jsp?type=_bxxx&title=课件表现形式" target="xytd">课件表现形式</a></td>
  </tr>
  <tr background="/web/bzz_index/xmjs/images/part-1-1_08.jpg">
    <td width="160" height="30" background="/web/bzz_index/xmjs/images/part-1-1_08.jpg" bgcolor="#FFFFFF"><span class="topmenu03">　<img src="/web/bzz_index/xmjs/images/xj.gif" width="10" height="7" align="absmiddle"> &nbsp;</span><a href="/web/bzz_index/xmjs/subpage.jsp?type=_xxfs&title=学习方式" target="xytd">学习方式</a></td>
  </tr>
  <tr background="/web/bzz_index/xmjs/images/part-1-1_08.jpg">
    <td width="160" height="30" background="/web/bzz_index/xmjs/images/part-1-1_08.jpg" bgcolor="#FFFFFF"><span class="topmenu03">　<img src="/web/bzz_index/xmjs/images/xj.gif" width="10" height="7" align="absmiddle"> &nbsp;</span><a href="/web/bzz_index/xmjs/subpage.jsp?type=_jxzlbz&title=教学质量保障" target="xytd">教学质量保障</a></td>
  </tr>
  <tr background="/web/bzz_index/xmjs/images/part-1-1_08.jpg">
    <td width="160" height="30" background="/web/bzz_index/xmjs/images/part-1-1_08.jpg" bgcolor="#FFFFFF"><span class="topmenu03">　<img src="/web/bzz_index/xmjs/images/xj.gif" width="10" height="7" align="absmiddle"> &nbsp;</span><a href="/web/bzz_index/xmjs/subpage.jsp?type=_khrz&title=考试认证" target="xytd">考试认证</a></td>
  </tr>
</table>
</div>

<!--第四章-->
<div  onClick="clearAllChild();document.all.child4.style.display=(document.all.child4.style.display =='none')?'':'none';" style="cursor:pointer;">
  <table width="160" border="0" align="center" cellpadding="0" cellspacing="0" style="margin:0px 0px 0px 2px;">
    <tr>
      <td width="160" height="35"  class="zj" style="letter-spacing:normal;"><a href="/web/bzz_index/xmjs/xmys01.jsp" target="xytd"><img src="/web/bzz_index/xmjs/images/fwzx_07.gif" width="11" height="11" border="0" align="absmiddle" style="letter-spacing:normal"> &nbsp;项目优势及鼓励政策</a></td>
    </tr>
    <tr>
      <td height="3" class="bottomline"></td>
    </tr>
  </table>
</div>
<div id="child4" style="display:none;">
</div>

<!--第五章-->
<div onClick="clearAllChild();document.all.child5.style.display=(document.all.child5.style.display =='none')?'':'none';" style="cursor:pointer;">
  <table width="160" border="0" align="center" cellpadding="0" cellspacing="0" style="margin:0px 0px 0px 2px;">
    <tr>
      <td width="160" height="35"  class="zj"><a href="/web/bzz_index/xmjs/subpage.jsp?type=_ssbz&title=项目实施步骤" target="xytd"><img src="/web/bzz_index/xmjs/images/fwzx_07.gif" width="11" height="11" border="0" align="absmiddle"> 项目实施步骤</a></td>
    </tr>
    <tr>
      <td height="3" class="bottomline"></td>
    </tr>
  </table>
</div>
<div id="child5" style="display:none;"></div>
 </tr>
          <tr>
            <td><img src="/web/bzz_index/xmjs/images/list_b.jpg" width="206" height="31"></td>
          </tr>
        </table></td>
      </tr>
      <tr bgcolor="#9FC3FF">
        <td valign="bottom"><img src="/web/bzz_index/xmjs/images/l_b.jpg" width="218" height="7"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><iframe id="foot" name="foot" frameborder="0" width="1002" height="147" scrolling="no" src="/web/bzz_index/bottom1.jsp"></iframe></td>
  </tr>
</table>


<map name="Map"><area shape="rect" coords="640,13,693,28" href="#"></map></body>
</html>
