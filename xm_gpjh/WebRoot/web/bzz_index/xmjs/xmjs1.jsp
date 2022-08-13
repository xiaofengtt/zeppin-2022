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
}
//-->
</SCRIPT> 
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
        <iframe id="xytd" name="xytd" allowtransparency="yes" frameborder="0" width="100%" height="100%" scrolling="no" src="/web/bzz_index/xmjs/subpage.jsp?type=_ldtc&title=领导题词" onLoad="resize()"></iframe></td>
      </tr>
      <tr bgcolor="#9FC3FF">
        <td valign="top"><br>
            <table width="100" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="/web/bzz_index/xmjs/images/list_t.jpg" width="206" height="70"></td>
          </tr>
          <tr>
            <td background="/web/bzz_index/xmjs/images/list_bg.jpg"><table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
             <tr>
                <td height="30" id="menu0" status="off" class="cd_1-off" onMouseOver="overMenu(this)" onMouseOut="outMenu(this)" onClick="registMenu(this);window.open('subpage.jsp?type=_lxtc&title=领导题词','xytd');parent.resize();">　 领导题词</td>
              </tr>
              <tr>
                <td height="6"></td>
              </tr>
              <tr>
                <td height="30" id="menu1" status="off" class="cd_1-off" onMouseOver="overMenu(this)" onMouseOut="outMenu(this)" onClick="registMenu(this);window.open('subpage.jsp?type=_xmbj&title=项目背景','xytd');parent.resize();">　 项目背景</td>
              </tr>
              <tr>
                <td height="6"></td>
              </tr>
              <tr>
                <td height="30" id="menu3" status="off" class="cd_1-off" onMouseOver="overMenu(this)" onMouseOut="outMenu(this)" onClick="registMenu(this);window.open('subpage.jsp?type=_xmss&title=项目实施','xytd');parent.resize();">　 项目实施</td>
              </tr>
              <tr>
                <td height="6"></td>
              </tr>
              <tr>
                <td height="30" id="menu4" status="off" class="cd_1-off" onMouseOver="overMenu(this)" onMouseOut="outMenu(this)" onClick="registMenu(this);window.open('subpage.jsp?type=_xmpxdg&title=培训大纲','xytd');parent.resize();">　 培训大纲</td>
              </tr>
              <tr>
                <td height="6"></td>
              </tr>
              <tr>
                <td height="30" id="menu5" status="off" class="cd_1-off" onMouseOver="overMenu(this)" onMouseOut="outMenu(this)" onClick="registMenu(this);window.open('subpage.jsp?type=_kctx&title=课程体系','xytd');parent.resize();">　 课程体系</td>
              </tr>
              <tr>
                <td height="6"></td>
              </tr>
              <tr>
                <td height="30" id="menu4" status="off" class="cd_1-off" onMouseOver="overMenu(this)" onMouseOut="outMenu(this)" onClick="registMenu(this);window.open('subpage.jsp?type=_xmsz&title=项目师资','xytd');parent.resize();">　 项目师资</td>
              </tr>
              <tr>
                <td height="6"></td>
              </tr>
              <tr>
                <td height="30" id="menu4" status="off" class="cd_1-off" onMouseOver="overMenu(this)" onMouseOut="outMenu(this)" onClick="registMenu(this);window.open('subpage.jsp?type=_xxjc&title=学习教材','xytd');parent.resize();">　 学习教材</td>
              </tr>
              <tr>
                <td height="6"></td>
              </tr>
              <tr>
                <td height="30" id="menu4" status="off" class="cd_1-off" onMouseOver="overMenu(this)" onMouseOut="outMenu(this)" onClick="registMenu(this);window.open('subpage.jsp?type=_xxfs&title=学习方式','xytd');parent.resize();">　 学习方式</td>
              </tr>
              <tr>
                <td height="6"></td>
              </tr>
              <tr>
                <td height="30" id="menu4" status="off" class="cd_1-off" onMouseOver="overMenu(this)" onMouseOut="outMenu(this)" onClick="registMenu(this);window.open('subpage.jsp?type=_khrz&title=考核认证','xytd');parent.resize();">　 考核认证</td>
              </tr>
              <tr>
                <td height="6"></td>
              </tr>
              <tr>
                <td height="30" id="menu4" status="off" class="cd_1-off" onMouseOver="overMenu(this)" onMouseOut="outMenu(this)" onClick="registMenu(this);window.open('subpage.jsp?type=_jxpg&title=教学调研评估','xytd');parent.resize();">　 教学调研评估</td>
              </tr>
              <tr>
                <td height="6"></td>
              </tr>
                <tr>
                <td height="30" id="menu4" status="off" class="cd_1-off" onMouseOver="overMenu(this)" onMouseOut="outMenu(this)" onClick="registMenu(this);window.open('subpage.jsp?type=_xyfw&title=学员服务','xytd');parent.resize();">　 学员服务</td>
              </tr>
              <tr>
                <td height="6"></td>
              </tr>
                <tr>
                <td height="30" id="menu4" status="off" class="cd_1-off" onMouseOver="overMenu(this)" onMouseOut="outMenu(this)" onClick="registMenu(this);window.open('subpage.jsp?type=_rxlc&title=入学流程','xytd');parent.resize();">　 入学流程</td>
              </tr>
            </table></td>
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
