<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.MyResultSet"/>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>
<%
String path1 = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>生殖健康咨询师培训网</title>
<link href="/web/bzz_index/style/index.css" rel="stylesheet" type="text/css">
<link href="/web/bzz_index/style/xytd.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/web/bzz_index/js/wsMenu.js"></script>
<script language="javascript">
function resize()
{
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
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
        <td width="218" valign="top" bgcolor="#9FC3FF"><img src="/web/bzz_index/cjzlxz/images/l_t.jpg" width="218" height="8"></td>
        <td width="16" rowspan="3"></td>
        <td width="696" rowspan="3" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
                <td height="27" background="/web/bzz_index/images/index/index_r5_c007.jpg"> 
                  <div align="left">
                    <table width="100%" height="25" border="0" cellpadding="0" cellspacing="0">
                      <tr> 
                        <td width="6%">&nbsp;</td>
                        <td width="94%"><font color="#000066" size="3"><strong>常用资料下载</strong></font></td>
                      </tr>
                    </table>
                  </div></td>
              </tr>
        </table>
        <br>
        <iframe id="xytd" name="xytd" allowtransparency="yes" frameborder="0" width="100%" height="100%" scrolling="no" src="/web/bzz_index/cjzlxz/zlxz01.jsp" onLoad="resize()"></iframe></td>
      </tr>
      <tr bgcolor="#9FC3FF">
        <td valign="top"><br>
            <table width="100" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td><img src="/web/bzz_index/cjzlxz/images/zlxz_03.gif" width="206" height="70"></td>
          </tr>
          <tr>
            <td background="/web/bzz_index/cjzlxz/images/list_bg.jpg"><table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
               <tr>
                <td height="30" id="menu0" status="off" class="cd_7-off" onMouseOver="overMenu(this)" onMouseOut="outMenu(this)" onClick="registMenu(this);window.open('/web/bzz_index/cjzlxz/zlxz01.jsp','xytd');parent.resize();">　 常用资料下载</td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td><img src="/web/bzz_index/cjzlxz/images/list_b.jpg" width="206" height="31"></td>
          </tr>
        </table></td>
      </tr>
      <tr bgcolor="#9FC3FF">
        <td valign="bottom"><img src="/web/bzz_index/cjzlxz/images/l_b.jpg" width="218" height="7"></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><iframe id="foot" name="foot" frameborder="0" width="1002" height="147" scrolling="no" src="/web/bzz_index/bottom1.jsp"></iframe></td>
  </tr>
</table>


<map name="Map"><area shape="rect" coords="640,13,693,28" href="#"></map></body>
</html>

